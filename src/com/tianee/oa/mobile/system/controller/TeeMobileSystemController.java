package com.tianee.oa.mobile.system.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.system.service.TeeMobileSystemServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;




/**
 * 手机端
 * @author syl
 */
@Controller
@RequestMapping("/mobileSystemAction")
public class TeeMobileSystemController {

	

	@Autowired
	TeeMobileSystemServiceInterface mobileSystemService;
	/**
	 * 手机登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doLoginIn")
	@ResponseBody
	public TeeJson doLoginIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = mobileSystemService.doLoginInService(request ,response);
//		if(json.isRtState()){
//			//发送用户上线通知
//			Map map = new HashMap();
//			map.put("t", QType.USER_ONLINE);
//			map.put("id", request.getParameter("userName"));
//			ClientSocketRunner.sendMsg(TeeJsonUtil.mapToJson(map));
//			MessagePusher.push2Im(map);
//		}

		return json;
	}
	
	/**
	 * 获取新推送消息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNewPush")
	@ResponseBody
	public TeeJson getNewPush(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(mobileSystemService.getNewPush(loginUser,request.getParameter("module")));
		return json;
	}
	
	/**
	 * session维持
	 */
	@RequestMapping("/sessionHolding")
	@ResponseBody
	public void sessionHolding(){
		
	}
	
	
	/**
	 * 下载安卓更新文件
	 * 
	 * @author syl
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downAndroidFile")
	public String downAndroidFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filePath = TeeSysProps.getRootPath() + "appupdate/android/Android_Setup.apk";
		OutputStream ops = null;
		InputStream is = null;
		try {
			File file = new File(filePath);
		    if(file.exists()){
		    	is = new FileInputStream(filePath);
			}
			String fileName = "Android_Setup.apk";
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setContentType("application/octet-stream");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setHeader("Accept-Length", String.valueOf(file.length()));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ URLEncoder.encode(fileName, "UTF-8") + "\"");
			ops = response.getOutputStream();
			if (is != null) {
				byte[] buff = new byte[8192];
				int byteread = 0;
				while ((byteread = is.read(buff)) != -1) {
					ops.write(buff, 0, byteread);
					ops.flush();
					//ops.close();
				}
			} 
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (is != null) {
				
			
				is.close();
			}
		}
		return null;
	}
	
	/**
	 * 下载IOS更新文件
	 * 
	 * @author syl
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downIOSFile")
	public String downIOSFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filePath = TeeSysProps.getRootPath() + "appupdate/ios/IOS_Setup.ipa";
		OutputStream ops = null;
		InputStream is = null;
		try {
			File file = new File(filePath);
		    if(file.exists()){
		    	is = new FileInputStream(filePath);
			}
			String fileName = "IOS_Setup.ipa";
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setContentType("application/octet-stream");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setHeader("Accept-Length", String.valueOf(file.length()));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ URLEncoder.encode(fileName, "UTF-8") + "\"");
			ops = response.getOutputStream();
			if (is != null) {
				byte[] buff = new byte[8192];
				int byteread = 0;
				while ((byteread = is.read(buff)) != -1) {
					ops.write(buff, 0, byteread);
					ops.flush();
					//ops.close();
				}
			} 
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (is != null) {
				
			
				is.close();
			}
		}
		return null;
	}
	
	
	/**
	 * 绑定设备号
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bindDeviceId")
	@ResponseBody
	public TeeJson bindDeviceId(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String deviceId = request.getParameter("deviceId");
		String serialNo = request.getParameter("serialNo");
		deviceId=deviceId==null?"":deviceId;
		deviceId = deviceId.replace("<", "").replace(">", "").replace("-", "");
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = mobileSystemService.bindDeviceId(loginUser, deviceId,serialNo);
		return json;
	}

}
