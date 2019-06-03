package com.tianee.oa.subsys.cms.core.directive;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.model.SiteInfoModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.oa.subsys.cms.service.CmsSiteService;
import com.tianee.webframe.util.file.TeeUploadHelper;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsAttachDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String chnlIdentity = TeeStringUtil.getString(params.get("id"));
		
		Writer writer = env.getOut();
		int siteId = TeeStringUtil.getInteger(env.getCustomAttribute("SITE_ID"),0);
		int docId = TeeStringUtil.getInteger(env.getCustomAttribute("docId"),0);
		boolean preview = (Boolean) env.getCustomAttribute("preview");
		
		Object loopObj = env.getCustomAttribute("loopObj");//上层循环变量
		
		//根据siteIdentity查询出站点信息
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		CmsDocumentService documentService = (CmsDocumentService) env.getCustomAttribute("documentService");
		CmsSiteService siteService = (CmsSiteService) env.getCustomAttribute("siteService");
		Integer channelId = (Integer) env.getCustomAttribute("CHANNEL_ID");
		
		ChannelInfo chnl = channelService.getChannelByIdentity(chnlIdentity);
		if(chnl==null){
			if(null!=channelId){
				chnl = channelService.getChannelInfo((Integer)channelId);
			}else{
				channelId = 0;
			}
		}
		
		SiteInfoModel siteInfo = siteService.getSiteInfo(siteId);
		//如果栏目ID为0，则应该直到默认为首页
		String channelPath = null;
		if(channelId!=0){
			channelPath = channelService.getChannelRootPath((Integer)channelId);
		}else{
			channelPath = "./";
		}
		
		//处理文章中的附件
		Map data = new HashMap();
		List<TeeAttachment> attaches =null;
		if(docId>0){
			 attaches =documentService.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.cms, String.valueOf(docId));
		}else{
			attaches =documentService.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.cmsChannel, String.valueOf(chnl.getSid()));
		}
		for (TeeAttachment attach : attaches) {
			if(!preview){//如果不是预览，则输出绝对路径
//				String srcPath = attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
//				InputStream ins = new FileInputStream(srcPath);
//				String destPath=siteInfo.getFolder()+"/attach/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
				String realPath=channelPath+"attach/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
				data.put("attachPath",realPath);
				data.put("attachName",attach.getFileName());
				env.setCustomAttribute("loopObj", data);
				if(body!=null){
					body.render(writer);
				}
				env.setCustomAttribute("loopObj", loopObj);
//				TeeUploadHelper.saveFile(ins, destPath);
//				ins.close();
			}else{//如果是预览，则输出在线预览路径
				data.put("attachPath","/attachmentController/downFile.action?id="+attach.getSid());
				data.put("attachName",attach.getFileName());
				env.setCustomAttribute("loopObj", data);
				if(body!=null){
					body.render(writer);
				}
				env.setCustomAttribute("loopObj", loopObj);
			}
			
		}
		
	}

}
