package com.tianee.oa.subsys.cms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.bean.ChannelInfoExt;
import com.tianee.oa.subsys.cms.model.ChannelInfoModel;
import com.tianee.oa.subsys.cms.service.CmsChannelInfoExtService;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.cache.RedisClient;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/cmsChannel")
public class CmsChannelController {
	
	@Autowired
	private CmsChannelService channelService;
	
	@ResponseBody
	@RequestMapping("/addChannelInfo")
	public TeeJson addChannelInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String privUserIds=request.getParameter("privUserIds");
		ChannelInfo channelInfo = (ChannelInfo) TeeServletUtility.request2Object(request, ChannelInfo.class);
		if(privUserIds.length()>0){
			channelInfo.setPrivUserIds(","+privUserIds+",");
		}
		
		channelService.addChannelInfo(channelInfo);
		
		Map<String,String> params = new HashMap();
		//获取前台传来的扩展字段数据
		Enumeration<String> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String key = enumeration.nextElement();
			if(key.indexOf("#EXT_")!=-1){
				params.put(key.replace("#EXT_", ""), request.getParameter(key));
			}
		}
		
		channelService.updateExtFields(params, channelInfo.getSid());
		
		
		String attachmentSidStr = request.getParameter("channelImg");
		List<TeeAttachment> attachments = channelService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(channelInfo.getSid()));
				channelService.getSimpleDaoSupport().update(attach);
			}
		}
		json.setRtState(true);
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateChannelInfo")
	public TeeJson updateChannelInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String attachmentSidStr = request.getParameter("channelImg");
		String privUserIds=request.getParameter("privUserIds");
		ChannelInfo channelInfo = (ChannelInfo) TeeServletUtility.request2Object(request, ChannelInfo.class);
		if(privUserIds.length()>0){
			//判断
			if(!privUserIds.startsWith(",")){
				privUserIds=","+privUserIds;
			}
			
			if(!privUserIds.endsWith(",")){
				privUserIds=privUserIds+",";
			}
		}
		
		channelInfo.setPrivUserIds(privUserIds);
		List<TeeAttachment> attachments = channelService.getAttachmentDao().getAttachmentsByIds(attachmentSidStr);
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(channelInfo.getSid()));
				channelService.getSimpleDaoSupport().update(attach);
			}
		}
		channelService.updateChannelInfo(channelInfo);
		
		Map<String,String> params = new HashMap();
		//获取前台传来的扩展字段数据
		Enumeration<String> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String key = enumeration.nextElement();
			if(key.indexOf("#EXT_")!=-1){
				params.put(key.replace("#EXT_", ""), request.getParameter(key));
			}
		}
		
		channelService.updateExtFields(params, channelInfo.getSid());
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getChannelInfo")
	public TeeJson getChannelInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		ChannelInfo channelInfo = (ChannelInfo) TeeServletUtility.request2Object(request, ChannelInfo.class);
		ChannelInfoModel model = new ChannelInfoModel();
		ChannelInfo info = channelService.getChannelInfo(channelInfo.getSid());
		BeanUtils.copyProperties(info, model);
		if(null!=info.getCreateTime()){
			model.setCreateTimeDesc(sf.format(info.getCreateTime().getTime()));
		}
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attaches =channelService.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.cmsChannel, String.valueOf(channelInfo.getSid()));
		for (TeeAttachment attach : attaches) {
			TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, attachmentModel);
			attachmentModel.setUserId(attach.getUser().getUuid() + "");
			attachmentModel.setUserName(attach.getUser().getUserName());
			attachmentModel.setPriv(1+2+4+8+16+32);// 一共五个权限好像
											// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(attachmentModel);
		}
		model.setAttachMentModel(attachmodels);
		if(model.getPrivUserIds()!=null && model.getPrivUserIds().length()>0){
			model.setPrivUserIds(model.getPrivUserIds().substring(1,model.getPrivUserIds().length()-1));
		}
		
		model.setExt(channelService.getExtFields(channelInfo.getSid()));
		json.setRtData(model);
		
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateChannelHtmlContent")
	public TeeJson updateChannelHtmlContent(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String htmlContent = TeeStringUtil.getString(request.getParameter("htmlContent"));
		channelService.updateChannelHtmlContent(sid, htmlContent);
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delChannelInfo")
	public TeeJson delChannelInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		ChannelInfo channelInfo = (ChannelInfo) TeeServletUtility.request2Object(request, ChannelInfo.class);
		channelService.delChannelInfo(channelInfo);
		json.setRtState(true);
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/moveToTrash")
	public TeeJson moveToTrash(HttpServletRequest request){
		TeeJson json = new TeeJson();
		ChannelInfo channelInfo = (ChannelInfo) TeeServletUtility.request2Object(request, ChannelInfo.class);
		channelService.moveToTrash(channelInfo);
		json.setRtState(true);
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/recycle")
	public TeeJson recycle(HttpServletRequest request){
		TeeJson json = new TeeJson();
		ChannelInfo channelInfo = (ChannelInfo) TeeServletUtility.request2Object(request, ChannelInfo.class);
		channelService.recycle(channelInfo);
		json.setRtState(true);
		
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/clearTrash")
	public TeeJson clearTrash(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int siteId = TeeStringUtil.getInteger(request.getParameter("siteId"), 0);
		int channelId = TeeStringUtil.getInteger(request.getParameter("channelId"), 0);
		channelService.clearTrash(siteId, channelId);
		json.setRtState(true);
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/moveChannel")
	public TeeJson moveChannel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		ChannelInfo channelInfo = (ChannelInfo) TeeServletUtility.request2Object(request, ChannelInfo.class);
		channelService.moveChannel(channelInfo);
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/copyChannel")
	public TeeJson copyChannel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		ChannelInfo channelInfo = (ChannelInfo) TeeServletUtility.request2Object(request, ChannelInfo.class);
		channelService.copyChannel(channelInfo);
		
		RedisClient.getInstance().del("SITE");
		RedisClient.getInstance().del("CHNL");
		RedisClient.getInstance().del("DOC");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/listChannels")
	public TeeJson listChannels(HttpServletRequest request){
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		int siteId = TeeStringUtil.getInteger(request.getParameter("siteId"), 0);
		int channelId = TeeStringUtil.getInteger(request.getParameter("channelId"), 0);
		json.setRtData(channelService.listChannels(siteId, channelId,loginUser));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/listChannelDataGrid")
	public TeeEasyuiDataGridJson listChannels(TeeDataGridModel dm,HttpServletRequest request){
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int siteId = TeeStringUtil.getInteger(request.getParameter("siteId"), 0);
		int channelId = TeeStringUtil.getInteger(request.getParameter("channelId"), 0);
		return channelService.listChannelDataGrid(dm,siteId, channelId,loginUser);
		
	}
	
	
	@ResponseBody
	@RequestMapping("/listTrashChannels")
	public TeeJson listTrashChannels(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int siteId = TeeStringUtil.getInteger(request.getParameter("siteId"), 0);
		int channelId = TeeStringUtil.getInteger(request.getParameter("channelId"), 0);
		json.setRtData(channelService.listTrashChannels(siteId, channelId));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/listTrashChannelsDatagrid")
	public TeeEasyuiDataGridJson listTrashChannelsDatagrid(TeeDataGridModel dm,HttpServletRequest request){
		TeeJson json = new TeeJson();
		int siteId = TeeStringUtil.getInteger(request.getParameter("siteId"), 0);
		int channelId = TeeStringUtil.getInteger(request.getParameter("channelId"), 0);
		return channelService.listTrashChannelsDatagrid(dm,siteId, channelId);
	}
}
