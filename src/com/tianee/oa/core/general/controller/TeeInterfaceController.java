package com.tianee.oa.core.general.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.bean.TeeInterface;
import com.tianee.oa.core.general.service.TeeInterfaceService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/interfaceController")
public class TeeInterfaceController {

	@Autowired
	private TeeInterfaceService interfaceService;
	
	@Autowired
	private TeeAttachmentService attachmentService;

	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addUpdate(HttpServletRequest request) throws Exception {
		TeeInterface intf = new TeeInterface();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, intf);
		TeeJson json = new TeeJson();
		interfaceService.addOrUpdate(intf , request);
		//更新对应的缓存
		TeeSysProps.getProps().setProperty("IE_TITLE", intf.getIeTitle());
    	TeeSysProps.getProps().setProperty("LOGIN_OUT_TEXT", intf.getLogOutText());
    	TeeSysProps.getProps().setProperty("AVATAR_UPLOAD", intf.getAvatarUpload()+"");
    	TeeSysProps.getProps().setProperty("AVATAR_WIDTH", intf.getAvatarWidth()+"");
    	TeeSysProps.getProps().setProperty("AVATAR_HEIGHT", intf.getAvatarHeight()+"");
    	TeeSysProps.getProps().setProperty("TOP_BANNER_FONT", intf.getTopBannerFont());
    	TeeSysProps.getProps().setProperty("TOP_BANNER_TEXT", intf.getTopBannerText());
    	TeeSysProps.getProps().setProperty("BOTTOM_STATUS_TEXT", intf.getBottomStatusText());
    	TeeSysProps.getProps().setProperty("TOP_ATTACHMENT_ID", intf.getTopAttachmentId());
    	TeeSysProps.getProps().setProperty("IM_PIC", intf.getImPic());
    	TeeSysProps.getProps().setProperty("M_LOGO", intf.getmLogo());
    	TeeSysProps.getProps().setProperty("M_PIC", intf.getmPic());
    	TeeSysProps.getProps().setProperty("IS_GOV", intf.getShowRss());
    	TeeSysProps.getProps().setProperty("WELCOME_PIC", TeeStringUtil.getString(intf.getWelcomePic()));
    	TeeSysProps.getProps().setProperty("APP_INDEX", TeeStringUtil.getString(intf.getAppIndex()));
    	TeeSysProps.getProps().setProperty("APP_TOP_BG", intf.getAppTopBg());
    	TeeSysProps.getProps().setProperty("APP_TOP_SIGN_SHOW", intf.getAppTopSignShow()+"");
    	
		json.setRtData(intf);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("selectById")
	@ResponseBody
	public TeeJson selectById(HttpServletRequest request) {
		//String sid = request.getParameter("sid");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String sid = multipartRequest.getParameter("sid");
		TeeJson json = new TeeJson();
		if(TeeUtility.isNullorEmpty(sid)){
			TeeInterface intf = interfaceService.selectById(sid);
			json.setRtData(intf);
			json.setRtState(true);
		}
		
		json.setRtState(false);
		json.setRtMsg("没有相关记录");
		return json;
	}
	
	
	@RequestMapping("select")
	@ResponseBody
	public TeeJson select(HttpServletRequest request) {
		TeeJson json = new TeeJson();	
		TeeInterface intf = interfaceService.select();
		if(intf != null){	
			json.setRtData(intf);
			json.setRtState(true);
			return json;
		}
		
		json.setRtState(false);
		json.setRtMsg("没有相关记录");
		return json;
	}
	
	
	
	
	public void setInterfaceService(TeeInterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}
	
	
	
}
