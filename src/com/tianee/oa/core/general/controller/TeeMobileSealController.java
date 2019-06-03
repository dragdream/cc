package com.tianee.oa.core.general.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.general.bean.TeeMobileSeal;
import com.tianee.oa.core.general.service.TeeMobileSealService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeImageBase64Util;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("mobileSeal")
public class TeeMobileSealController {
	
	@Autowired
	private TeeMobileSealService mobileSealService;
	
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws IOException{
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		TeeMobileSeal mobileSeal = (TeeMobileSeal) TeeServletUtility.request2Object(request, TeeMobileSeal.class);
		MultipartFile file = multipartRequest.getFile("file");
		
		String fileName = null;
		String ext = null;
		
		
		InputStream input = null;
		String imgData = null;
		try{
			fileName = file.getOriginalFilename();
			if(fileName.toLowerCase().endsWith(".jpg")){
				ext = "jpg";
			}else if(fileName.toLowerCase().endsWith(".png")){
				ext = "png";
			}else if(fileName.toLowerCase().endsWith(".gif")){
				ext = "gif";
			}
			
			input = file.getInputStream();
			imgData = TeeImageBase64Util.GetImageStr(input);
		}catch(Exception e){
			
		}finally{
			if(input!=null){
				input.close();
			}
		}
		
		//如果解析到文件的话，则将文件入库
		if(ext!=null){
			imgData = "data:image/"+ext+";base64,"+imgData;
		}
		
		if(imgData!=null&&!("").equals(imgData)){
			mobileSeal.setSealData(imgData);
		}
		//mobileSeal.setSealData(imgData);
		mobileSeal.setCrTime(Calendar.getInstance());
		mobileSeal.setCrUserId(loginUser.getUuid());
		
		mobileSealService.addOrUpdate(mobileSeal);
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("getBase64Image")
	public void getBase64Image(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		TeeMobileSeal seal =  mobileSealService.get(uuid);
		String imgData = seal.getSealData();
		int index = imgData.indexOf(";base64,");
		imgData = imgData.substring(index+8, imgData.length());
		
		byte b [] =TeeImageBase64Util.GenerateImage(imgData);
		response.getOutputStream().write(b);
		
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public TeeJson delete(HttpServletRequest request){
        TeeJson json=new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		mobileSealService.delete(uuid);
		json.setRtState(true);
		json.setRtMsg("删除成功！");
		return json;
	}
	
	@RequestMapping("setFlag")
	@ResponseBody
	public TeeJson setFlag(HttpServletRequest request){
		TeeJson json=new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		int flag = TeeStringUtil.getInteger(request.getParameter("flag"), 0);
		mobileSealService.setFlag(uuid,flag);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("get")
	@ResponseBody
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtData(mobileSealService.get(TeeStringUtil.getString(request.getParameter("uuid"))));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("getByUuid")
	@ResponseBody
	public TeeJson getByUuid(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtData(mobileSealService.getByUuid(TeeStringUtil.getString(request.getParameter("uuid"))));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 确认绑定
	 * @param request
	 * @return
	 */
	@RequestMapping("binding")
	@ResponseBody
	public TeeJson binding(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		String deviceNo = TeeStringUtil.getString(request.getParameter("deviceNo"));
		String pwd = TeeStringUtil.getString(request.getParameter("pwd"));
		mobileSealService.binding(uuid, loginUser.getUuid(), deviceNo,pwd);
		return json;
	}
	
	/**
	 * 确认绑定
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public TeeEasyuiDataGridJson list(HttpServletRequest request,TeeDataGridModel dm){
		return mobileSealService.list(TeeServletUtility.getParamMap(request),dm);
	}
	
	/**
	 * 确认绑定
	 * @param request
	 * @return
	 */
	@RequestMapping("myMobileSeals")
	@ResponseBody
	public TeeEasyuiDataGridJson myMobileSeals(HttpServletRequest request){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map data = TeeServletUtility.getParamMap(request);
		data.put(TeeConst.LOGIN_USER, loginUser);
		return mobileSealService.myMobileSeals(data);
	}
	
	@RequestMapping("myMobileSealsWithData")
	@ResponseBody
	public TeeEasyuiDataGridJson myMobileSealsWithData(HttpServletRequest request){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map data = TeeServletUtility.getParamMap(request);
		data.put(TeeConst.LOGIN_USER, loginUser);
		return mobileSealService.myMobileSealsWithData(data);
	}
	
	@RequestMapping("reset")
	@ResponseBody
	public TeeJson reset(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
		mobileSealService.reset(uuid);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("myMobileSealsWithDataForMobile")
	@ResponseBody
	public TeeEasyuiDataGridJson myMobileSealsWithDataForMobile(HttpServletRequest request){
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map data = TeeServletUtility.getParamMap(request);
		data.put(TeeConst.LOGIN_USER, loginUser);
		data.put("deviceNo", request.getSession().getAttribute("deviceNo"));
		return mobileSealService.myMobileSealsWithDataForMobile(data);
	}
	
	/**
	 * 修改图片签章的密码
	 * @param request
	 * @return
	 */
	@RequestMapping("updatePwd")
	@ResponseBody
	public TeeJson updatePwd(HttpServletRequest request){
		return mobileSealService.updatePwd(request);
	}
}
