package com.tianee.oa.subsys.cms.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.DocumentInfo;
import com.tianee.oa.subsys.cms.core.CmsTemplateUtil;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.webframe.util.file.TeeUploadHelper;
import com.tianee.webframe.util.global.TeeSysProps;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly=true,rollbackFor=Exception.class)
public class CmsDocumentPublishService{
	
	@Autowired
	private CmsSiteService siteService;
	
	@Autowired
	private CmsSiteTemplateService siteTemplateService;
	
	@Autowired
	private CmsChannelService channelService;
	
	@Autowired
	private CmsDocumentService documentService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	/**
	 * 文章发布
	 * @param chnlDocId
	 * @param fullPub
	 */
	@Transactional(readOnly=true)
	public void toPub(int docId) {
		DocumentInfo doc = documentService.getDocumentByDocId(docId);
		ChannelInfo channelInfo = null;
		channelInfo = channelService.getChannelInfo(doc.getChnlId());
		SiteInfoModel siteInfoModel = siteService.getSiteInfo(channelInfo.getSiteId());
		//获取站点模板
		SiteTemplateModel detailTpl = siteTemplateService.getSiteTemplate(channelInfo.getDetailTpl());
		if(detailTpl!=null){//如果存在首页模板，则进行渲染
			String path = TeeSysProps.getSiteTemplatePath()+File.separator+detailTpl.getSiteId();
			Configuration config=new Configuration();
			ByteArrayOutputStream baos = null;
			Writer out = null;
			FileOutputStream fos=null;
			try {
				config.setDirectoryForTemplateLoading(new File(path));
				
				//注入服务
				config.setCustomAttribute("siteService", siteService);
				config.setCustomAttribute("siteTemplateService", siteTemplateService);
				config.setCustomAttribute("channelService", channelService);
				config.setCustomAttribute("documentService", documentService);
				config.setCustomAttribute("attachmentService", attachmentService);
				config.setCustomAttribute("preview", false);
				//附加参数
				config.setCustomAttribute("ROOT_PATH", siteInfoModel.getContextPath());
				config.setCustomAttribute("SITE_ID", channelInfo.getSiteId());
				config.setCustomAttribute("CHANNEL_ID", channelInfo.getSid());
				config.setSharedVariable("curChannelId", channelInfo.getSid());
				config.setCustomAttribute("DOCUMENT_ID", doc.getSid());
				
				
				Template template=config.getTemplate(detailTpl.getTplFileName(),"UTF-8");
				baos = new ByteArrayOutputStream();
			    out = new OutputStreamWriter(baos,"UTF-8");
			    
			    //输出参数
			    template.process(CmsTemplateUtil.getInstance(), out);
			    out.flush();
			    out.close();
			    
				//生成对应的静态文件
				String outPath = channelService.getChannelAbsolutePath(channelInfo.getSid())+"/";
				File file = new File(outPath);
				if(!file.exists()){
					file.mkdirs();
				}
				fos = new FileOutputStream(new File(outPath+doc.getSid()+"."+siteInfoModel.getPubFileExt()));
				baos.writeTo(fos);
			    
				/**
				 * 更新文章状态为已发
				 */
				doc.setStatus(4);
			    
			    /**
			     * 创建索引
			     */
			    String cmsIndexPath = TeeSysProps.getProps().getProperty("CMS_CONTENT_INDEX_PATH");
			    File indexDir = new File(cmsIndexPath);
			    if(!indexDir.exists()){
			    	indexDir.mkdir();
			    }
			    
			    int index = outPath.indexOf("/", 2);
			    outPath = outPath.substring(index+1);
			    String url = channelService.getChannelRootPath(channelInfo.getSid())+outPath+doc.getSid()+"."+siteInfoModel.getPubFileExt();
			    //System.out.println(url);
//			    createIndex(cmsIndexPath,documentInfo,url);
			    
			    
			    SiteInfoModel siteInfo = siteService.getSiteInfo(channelInfo.getSiteId());
			    //获取附件并发布到对应的目录中
			    List<TeeAttachment> attaches =null;
			    attaches =documentService.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.cms, String.valueOf(doc.getSid()));
			    for (TeeAttachment attach : attaches) {
			    	String destPath=siteInfo.getFolder()+"/attach/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
					File destFile = new File(destPath);
					if(!destFile.exists()){
						String srcPath = attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
						InputStream ins = null;
						try{
							ins = new FileInputStream(srcPath);
							TeeUploadHelper.saveFile(ins, destPath);
						}catch(Exception ex){
							
						}finally{
							if(ins!=null){
								ins.close();
							}
						}
						
					}
				}
			    
			    //获取缩略图并发布到对应目录
			    TeeAttachment thumbnail = attachmentService.getById(doc.getThumbnail());
			    if(thumbnail!=null){
			    	
					String destPath=siteInfo.getFolder()+"/attach/"+thumbnail.getModel()+"/"+thumbnail.getAttachmentPath()+"/"+thumbnail.getAttachmentName();
					File destFile = new File(destPath);
					if(!destFile.exists()){
						String srcPath = thumbnail.getAttachSpace().getSpacePath()+"/"+thumbnail.getModel()+"/"+thumbnail.getAttachmentPath()+"/"+thumbnail.getAttachmentName();
						InputStream ins = null;
						try{
							ins = new FileInputStream(srcPath);
							TeeUploadHelper.saveFile(ins, destPath);
						}catch(Exception ex){
							
						}finally{
							if(ins!=null){
								ins.close();
							}
						}
						
					}
			    }
			    
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(baos!=null){
					try {
						baos.close();
					} catch (IOException e) {
					}
				}
				if(out!=null){
					try {
						out.close();
					} catch (IOException e) {
					}
				}
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
	
	
	private static void createIndex(String indexPath,DocumentInfo documentInfo,String path) throws IOException {
		IndexWriter writer = null;
		try {
			// 1、创建索引库IndexWriter
			writer = getIndexWriter(indexPath);
			index(writer, documentInfo,path);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null!=writer){
				writer.close();
			}
		}
	}

	private static IndexWriter getIndexWriter(String indexPath)throws IOException {
		Directory indexDir = FSDirectory.open(new File(indexPath));
		Analyzer analyzer = new IKAnalyzer(true);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48,analyzer);
		iwc.setOpenMode( OpenMode.CREATE_OR_APPEND );
		IndexWriter writer = new IndexWriter(indexDir, iwc);
		return writer;
	}

	private static void index(IndexWriter writer, DocumentInfo documentInfo,String path) throws IOException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Document doc = new Document();
		Field indexId = new StringField("ID", String.valueOf(documentInfo.getSid()),Field.Store.YES);
		Field pathField = new StringField("path", path,Field.Store.YES);
		Field titleField = new StringField("title", documentInfo.getDocTitle(), Field.Store.YES); 
		Field abstractsField = new StringField("abstracts", documentInfo.getAbstracts(), Field.Store.YES); 
		doc.add(indexId);
		doc.add(pathField);
		doc.add(titleField);
		doc.add(abstractsField);
		doc.add(new StringField("time", sf.format(documentInfo.getCrTime().getTime()),Field.Store.YES));
		doc.add(new TextField("contents", documentInfo.getContent(), Field.Store.YES));
		Term tr = new Term("ID",String.valueOf(documentInfo.getSid()));
		if(null!=tr){
			writer.updateDocument(tr, doc);
		}else{
			writer.addDocument(doc);
		}
		writer.forceMerge(1);

	}
	
	public static String FileReaderAll(String FileName, String charset)throws IOException {      
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FileName), charset));      
        String line = new String();      
        String temp = new String();      
        while ((line = reader.readLine()) != null) {   
            temp += line;      
        }      
        reader.close();      
        return temp;      
    }   
	
}