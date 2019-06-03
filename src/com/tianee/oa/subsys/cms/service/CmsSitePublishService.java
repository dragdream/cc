package com.tianee.oa.subsys.cms.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.core.CmsTemplateUtil;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
@Transactional(propagation=Propagation.REQUIRED,readOnly=true,rollbackFor=Exception.class)
public class CmsSitePublishService {
	@Autowired
	private CmsSiteService siteService;

	@Autowired
	private CmsSiteTemplateService siteTemplateService;

	@Autowired
	private CmsChannelService channelService;

	@Autowired
	private CmsChannelPublishService channelPublishService;
	
	@Autowired
	private CmsDocumentService documentService;
	
	@Autowired
	private TeeAttachmentService attachmentService;

	/**
	 * 全站发布
	 * 
	 * @param siteId
	 * @param b
	 */
	@Transactional(readOnly=true)
	public void toPub(int siteId) {
		
		// 获取站点信息
		SiteInfoModel siteModel = siteService.getSiteInfo(siteId);
		// 获取站点模板
		SiteTemplateModel indexTpl = siteTemplateService.getSiteTemplate(siteModel.getIndexTpl());
		if (indexTpl != null) {// 如果存在首页模板，则进行渲染
			String path = TeeSysProps.getSiteTemplatePath() + File.separator+ indexTpl.getSiteId();
			Configuration config = new Configuration();
			ByteArrayOutputStream baos = null;
			Writer out = null;
			FileOutputStream fos=null;
			try {
				config.setDirectoryForTemplateLoading(new File(path));

				// 注入服务
				config.setCustomAttribute("attachmentService", attachmentService);
				config.setCustomAttribute("siteService", siteService);
				config.setCustomAttribute("siteTemplateService",siteTemplateService);
				config.setCustomAttribute("channelService", channelService);
				config.setCustomAttribute("documentService", documentService);
				
				// 附加参数
				config.setCustomAttribute("ROOT_PATH", siteModel.getContextPath());
				config.setCustomAttribute("SITE_ID", siteModel.getSid());

				Template template = config.getTemplate(indexTpl.getTplFileName(), "UTF-8");
				baos = new ByteArrayOutputStream();
				out = new OutputStreamWriter(baos, "UTF-8");
				config.setCustomAttribute("preview", false);
				// 输出参数
				template.process(CmsTemplateUtil.getInstance(), out);
				out.flush();
				out.close();
				
				
				//生成对应的静态文件
				String outPath = siteModel.getFolder()+"/";
				File file = new File(outPath);
				if(!file.exists()){
					file.mkdirs();
				}
				fos = new FileOutputStream(new File(outPath+"index."+siteModel.getPubFileExt()));
				baos.writeTo(fos);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (baos != null) {
					try {
						baos.close();
					} catch (IOException e) {
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
	
	@Transactional(readOnly=true)
	public void copyResource(int siteId){
		SiteInfoModel siteModel = siteService.getSiteInfo(siteId);
		String srcPath=TeeSysProps.getRootPath()+"cmstpls/"+siteId;
		String destPath=siteModel.getFolder()+"/";
		File file= new File(srcPath);
		File[] files = file.listFiles();
		if(null!=files){
			for(File f:files){
				if(f.isDirectory()){
					TeeFileUtility.copyDirs(f.getAbsolutePath(), destPath+"/"+f.getName());
				}
			}
		}
	}

}