package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsDocumentsDirective implements TemplateDirectiveModel{

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		String chnlIds = TeeStringUtil.getString(params.get("chnlIds"));//栏目标识集合
		int top = TeeStringUtil.getInteger(params.get("top"), 0);//记录
		String orderBy = TeeStringUtil.getString(params.get("orderBy"));//获取排序规则
		String category = TeeStringUtil.getString(params.get("category"));//标签分类
		
		String isPage = TeeStringUtil.getString(params.get("pager"), "false");//是否分页
		Writer writer = env.getOut();
		int siteId = TeeStringUtil.getInteger(env.getCustomAttribute("SITE_ID"),0);
		
		CmsDocumentService documentService = (CmsDocumentService) env.getCustomAttribute("documentService");
		CmsChannelService channelService = (CmsChannelService) env.getCustomAttribute("channelService");
		CmsSiteService siteService = (CmsSiteService) env.getCustomAttribute("siteService");
		TeeAttachmentService attachmentService = (TeeAttachmentService) env.getCustomAttribute("attachmentService");
		CmsSiteTemplateService siteTemplateService = (CmsSiteTemplateService) env.getCustomAttribute("siteTemplateService");
		
		SiteInfoModel siteInfoModel = siteService.getSiteInfo(siteId);
		
		Map loopObj = (Map) env.getCustomAttribute("loopObj");//上层循环变量
		TemplateModel this0 = env.getVariable("this");
		int channelId = TeeStringUtil.getInteger(env.getCustomAttribute("CHANNEL_ID"), 0);//当前栏目上下文
		boolean preview = (Boolean) env.getCustomAttribute("preview");
		
		int pageSize = 0;
		int curPage = TeeStringUtil.getInteger(env.getCustomAttribute("curPage"),1);
		List<Map> docs = null;
		
		if("false".equals(isPage)){
			if("".equals(chnlIds)){//如果不是固定栏目的话，则引入当前栏目上下文
				docs = documentService.getPublishableDocumentsByChnlId(channelId,0, top==0?Integer.MAX_VALUE:top,orderBy,category);
			}else{
				docs = documentService.getDocumentByChnlIds(chnlIds, top==0?Integer.MAX_VALUE:top,orderBy,category);
			}
		}else{
//			int from = curPage==1?0:(curPage-1)*pageSize;
			ChannelInfo curChnl = channelService.getChannelInfo(channelId);
			pageSize = curChnl.getPageSize();
			int from = curPage==1?0:(curPage-1)*pageSize;
			if("".equals(chnlIds)){//如果不是固定栏目的话，则引入当前栏目上下文
				docs = documentService.getPublishableDocumentsByChnlId(channelId,from,pageSize,orderBy,category);
			}else{
				docs = documentService.getDocumentByChnlIds(chnlIds, top==0?Integer.MAX_VALUE:top,orderBy,category);
			}
		}
		
		int chnlId = 0;
		ChannelInfo ci = null;
		SiteTemplateModel st = null;
		String absPath = null;
		Integer docId = 0;
		Calendar curCalendar = Calendar.getInstance();
		if(docs!=null){
			int index0 = 0;
			String relPath = "";
			TeeAttachment thumbAttach = null;
			for(Map data:docs){
				chnlId = TeeStringUtil.getInteger(data.get("chnlId"), 0);
				ci = channelService.getChannelInfo(chnlId);
				st = siteTemplateService.getSiteTemplate(ci.getDetailTpl());
				absPath = channelService.getChannelAbsolutePath(chnlId);
				if(st!=null){
					data.put("ABS_PATH", absPath+"/"+data.get("docId")+"."+siteInfoModel.getPubFileExt());//绝对路径
					int index = absPath.indexOf("/", 2);
					absPath = absPath.substring(index+1);
					data.put("REL_PATH", env.getCustomAttribute("ROOT_PATH")+absPath+"/"+data.get("docId")+"."+siteInfoModel.getPubFileExt());//相对路径
					relPath = env.getCustomAttribute("ROOT_PATH")+"";
				}else{
					data.put("ABS_PATH", absPath);//绝对路径
					int index = absPath.indexOf("/", 2);
					absPath = absPath.substring(index+1);
					data.put("REL_PATH", env.getCustomAttribute("ROOT_PATH")+absPath);//相对路径
				}
				//处理日期
				data.put("writeTimeDesc", TeeDateUtil.format((Calendar)data.get("writeTime")));
				data.put("crTimeDesc", TeeDateUtil.format((Calendar)data.get("crTime")));
				
				//获取缩略图附件
				thumbAttach = attachmentService.getById((Integer)data.get("thumbnail"));
				if(thumbAttach!=null){
					//处理缩略图
					String realPath=relPath+"attach/"+thumbAttach.getModel()+"/"+thumbAttach.getAttachmentPath()+"/"+thumbAttach.getAttachmentName();
					data.put("thumb", realPath);
				}
				
				//如果是预览中
				if(preview){
					data.put("ABS_PATH", TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+siteId+"&channelId="+data.get("chnlId")+"&docId="+data.get("docId"));//绝对路径
					data.put("REL_PATH", TeeSysProps.getString("contextPath")+"/cmsPub/portal.action?siteId="+siteId+"&channelId="+data.get("chnlId")+"&docId="+data.get("docId"));//相对路径
					data.put("thumb", TeeSysProps.getString("contextPath")+"/attachmentController/downFile.action?id="+data.get("thumbnail"));//相对路径
				}
				
				Map _this = new HashMap();
				_this.put("instance", data);
				_this.put("index", index0);
				_this.put("docId",data.get("docId"));
				
				Calendar writeTime = (Calendar)data.get("writeTime");
				Calendar crTime = (Calendar)data.get("crTime");
				if(writeTime!=null){
					_this.put("daysDelta4Write",(curCalendar.getTimeInMillis()-writeTime.getTimeInMillis())/(1000*3600*24));
				}else{
					_this.put("daysDelta4Write",Integer.MAX_VALUE);
				}
				if(crTime!=null){
					_this.put("daysDelta4Create",(curCalendar.getTimeInMillis()-crTime.getTimeInMillis())/(1000*3600*24));
				}else{
					_this.put("daysDelta4Create",Integer.MAX_VALUE);
				}
				
				if(index0==docs.size()-1){
					_this.put("isLast", true);
				}else{
					_this.put("isLast", false);
				}
				env.setVariable("this", new DefaultObjectWrapper().wrap(_this));
				
				env.setCustomAttribute("loopObj", data);
				
				docId = (Integer)env.getCustomAttribute("docId");
				env.setCustomAttribute("docId", data.get("docId"));
				if(body!=null){
					body.render(writer);
				}
				env.setCustomAttribute("docId", docId);
				env.setVariable("this", this0);
				env.setCustomAttribute("loopObj", loopObj);
			}
		}
	}

}
