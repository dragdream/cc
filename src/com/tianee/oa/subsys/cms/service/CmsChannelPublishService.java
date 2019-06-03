package com.tianee.oa.subsys.cms.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.SiteInfo;
import com.tianee.oa.subsys.cms.core.CmsTemplateUtil;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.webframe.util.file.TeeUploadHelper;
import com.tianee.webframe.util.global.TeeSysProps;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly=true,rollbackFor=Exception.class)
public class CmsChannelPublishService{
	@Autowired
	private CmsSiteService siteService;

	@Autowired
	private CmsSiteTemplateService siteTemplateService;

	@Autowired
	private CmsChannelService channelService;

	@Autowired
	private CmsDocumentService documentService;
	
	@Autowired
	private CmsDocumentPublishService docPublishService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	/**
	 * 栏目发布（包括栏目下所有文章）
	 * @param channelId
	 * @param fullPub
	 */
	@Transactional(readOnly=true)
	public void toPub(int channelId) {
		//获取站点信息
		ChannelInfo channelInfo = channelService.getChannelInfo(channelId);
		
		SiteInfoModel siteInfo = siteService.getSiteInfo(channelInfo.getSiteId());
		//获取站点模板
		SiteTemplateModel indexTpl = siteTemplateService.getSiteTemplate(channelInfo.getIndexTpl());
		if(indexTpl!=null){//如果存在首页模板，则进行渲染
			String path = TeeSysProps.getSiteTemplatePath()+File.separator+indexTpl.getSiteId();
			Configuration config=new Configuration();
			ByteArrayOutputStream baos = null;
			Writer out = null;
			FileOutputStream fos=null;
			try {
				List<Map> docList = documentService.getPublishableDocumentsByChnlId(channelId,0,Integer.MAX_VALUE,null,null);
				int pageSize=channelInfo.getPageSize()==0?Integer.MAX_VALUE:channelInfo.getPageSize();
				int pageNums =0;
				int curPage=1;
				pageNums = (docList.size())%pageSize==0?(docList.size())/pageSize:(docList.size())/pageSize+1;
				config.setDirectoryForTemplateLoading(new File(path));
				//注入服务
				config.setCustomAttribute("siteService", siteService);
				config.setCustomAttribute("siteTemplateService", siteTemplateService);
				config.setCustomAttribute("channelService", channelService);
				config.setCustomAttribute("documentService", documentService);
				config.setCustomAttribute("attachmentService", attachmentService);
				config.setCustomAttribute("preview", false);
				
				//附加参数
				config.setCustomAttribute("ROOT_PATH", siteInfo.getContextPath());
				config.setCustomAttribute("SITE_ID", channelInfo.getSiteId());
				config.setCustomAttribute("CHANNEL_ID", channelId);
				
				//每一页数据条数
				config.setCustomAttribute("pageSize", pageSize);
				config.setCustomAttribute("pageNums", pageNums);
				
				config.setSharedVariable("curChannelId", channelId);
				
				Template template=config.getTemplate(indexTpl.getTplFileName(),"UTF-8");
			
				//生成对应的静态文件
			    String outPath = channelService.getChannelAbsolutePath(channelInfo.getSid())+"/";
				File file = new File(outPath);
				if(!file.exists()){
					file.mkdirs();
				}
				String destPath="";
				for(int k=0;k<pageNums;k++){
					if(k==0){
						destPath=outPath+"index."+siteInfo.getPubFileExt();
					}else{
						destPath=outPath+"index"+k+"."+siteInfo.getPubFileExt();
					}
					baos = new ByteArrayOutputStream();
				    out = new OutputStreamWriter(baos,"UTF-8");
				    config.setCustomAttribute("curPage", curPage);
					template.process(CmsTemplateUtil.getInstance(), out);
					fos = new FileOutputStream(new File(destPath));
					baos.writeTo(fos);
					curPage++;
					out.flush();
					baos.flush();
				}
				if(pageNums==0){
					destPath=outPath+"index."+siteInfo.getPubFileExt();
					baos = new ByteArrayOutputStream();
				    out = new OutputStreamWriter(baos,"UTF-8");
					template.process(CmsTemplateUtil.getInstance(), out);
					fos = new FileOutputStream(new File(destPath));
					baos.writeTo(fos);
					out.flush();
					baos.flush();
				}
				
				//获取附件并发布到对应的目录中
			    List<TeeAttachment> attaches =null;
			    attaches =channelService.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.cmsChannel, String.valueOf(channelId));
			    for (TeeAttachment attach : attaches) {
			    	String destPath0=siteInfo.getFolder()+"/attach/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
					File destFile = new File(destPath0);
					if(!destFile.exists()){
						String srcPath = attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
						InputStream ins = null;
						try{
							ins = new FileInputStream(srcPath);
							TeeUploadHelper.saveFile(ins, destPath0);
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
	
}