package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.oa.subsys.cms.service.CmsSiteService;
import com.tianee.oa.subsys.cms.service.CmsSiteTemplateService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUrlToFile;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsDocumentDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		int docId = TeeStringUtil.getInteger(params.get("docId"),0);//指定chnlDocId
		
		int siteId = TeeStringUtil.getInteger(env.getCustomAttribute("SITE_ID"),0);
		int context_documentId = TeeStringUtil.getInteger(env.getCustomAttribute("DOCUMENT_ID"),0);
		
		Writer writer = env.getOut();
		boolean preview = (Boolean) env.getCustomAttribute("preview");
		
		CmsDocumentService documentService = (CmsDocumentService) env.getCustomAttribute("documentService");
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		CmsSiteService siteService = (CmsSiteService) env.getCustomAttribute("siteService");
		CmsSiteTemplateService siteTemplateService = (CmsSiteTemplateService) env.getCustomAttribute("siteTemplateService");
		
		TeeAttachmentService attachmentService = (TeeAttachmentService)env.getCustomAttribute("attachmentService");
		
		Object loopObj = env.getCustomAttribute("loopObj");//上层循环变量
		TemplateModel this0 = env.getVariable("this");
		Map data = null;
		if(context_documentId!=0){
			data = documentService.getDocumentMapByDocId(context_documentId);
		}else{
			data = documentService.getDocumentMapByDocId(docId);
		}
		
		if(data==null){
			return;
		}
		env.setCustomAttribute("docId", data.get("docId"));
		
		
		ChannelInfo channelInfo = channelService.getChannelInfo(TeeStringUtil.getInteger(data.get("chnlId"), 0));
		SiteTemplateModel st = siteTemplateService.getSiteTemplate(channelInfo.getDetailTpl());
		SiteInfoModel siteInfo = siteService.getSiteInfo(siteId);
		
		
		String absPath = channelService.getChannelAbsolutePath(channelInfo.getSid());
		
		if(st!=null){
			data.put("ABS_PATH", absPath+"/"+data.get("docId")+"."+siteInfo.getPubFileExt());//绝对路径
			int index = absPath.indexOf("/", 2);
			absPath = absPath.substring(index+1);
			data.put("REL_PATH", env.getCustomAttribute("ROOT_PATH")+absPath+"/"+data.get("docId")+"."+siteInfo.getPubFileExt());//相对路径
		}
		
		//处理日期
		data.put("writeTimeDesc", TeeDateUtil.format((Calendar)data.get("writeTime")));
		data.put("crTimeDesc", TeeDateUtil.format((Calendar)data.get("crTime")));
		
		
		if(!preview){
			//处理内容中的图片
			String htmlContent = (String)data.get("htmlContent");
			List oldUrls = new ArrayList();
			List newUrls = new ArrayList();
			String reg = "<img[^<>]*?\\ssrc=['\"]?(.*?)['\"].*?>";
			Pattern p=Pattern.compile(reg);
			Matcher m=p.matcher(htmlContent);
			String channelPath = channelService.getChannelRootPath(channelInfo.getSid());
			while(m.find()){
				try{
					String srcPath = m.group(1);
					String id = srcPath.substring(srcPath.lastIndexOf("=")+1,srcPath.length());
					TeeAttachment attach = attachmentService.getById(Integer.parseInt(id));
					String tempPath=attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
					String destPath=siteInfo.getFolder()+"/"+"attach/"+attach.getModel()+"/"+attach.getAttachmentPath();
					TeeFileUtility.copy(tempPath, destPath);
					String realPath=channelPath+"attach/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
					oldUrls.add(srcPath);
					newUrls.add(realPath);
				}catch(Exception ex){
					
				}
			}
			htmlContent = TeeUrlToFile.replaceImageUrl(oldUrls, htmlContent, newUrls);
			
			//处理视频，音频文件
			List oldUrls2 = new ArrayList();
			List newUrls2 = new ArrayList();
			String reg2 = "<embed[^<>]*?\\ssrc=['\"]?(.*?)['\"].*?>";
			Pattern p2=Pattern.compile(reg2);
			Matcher m2=p2.matcher(htmlContent);
			while(m2.find()){
				String srcPath = m2.group(1);
				String id = srcPath.substring(srcPath.lastIndexOf("=")+1,srcPath.length());
				TeeAttachment attach = attachmentService.getAttachmentDao().get(Integer.parseInt(id));
				String tempPath=attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
				String destPath=siteInfo.getFolder()+"/"+"attach/"+attach.getModel()+"/"+attach.getAttachmentPath();
				TeeFileUtility.copy(tempPath, destPath);
				String realPath=channelPath+"attach/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
				oldUrls2.add(srcPath);
				newUrls2.add(realPath);
			}
			htmlContent = TeeUrlToFile.replaceImageUrl(oldUrls2, htmlContent, newUrls2);
			
			
			data.put("htmlContent", htmlContent);
		}
		//如果是预览中
		if(preview){
			data.put("ABS_PATH", TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+siteId+"&channelId="+data.get("chnlId")+"&docId="+docId);//绝对路径
			data.put("REL_PATH", TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+siteId+"&channelId="+data.get("chnlId")+"&docId="+docId);//相对路径
		}
		
		Map _this = new HashMap();
		_this.put("instance", data);
		env.setVariable("this", new DefaultObjectWrapper().wrap(_this));
		env.setCustomAttribute("loopObj", data);
		
		if(body!=null){
			body.render(writer);
		}
		env.setVariable("this", this0);
		env.setCustomAttribute("loopObj", loopObj);
	}

}
