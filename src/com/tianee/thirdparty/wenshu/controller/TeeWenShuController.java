
package com.tianee.thirdparty.wenshu.controller;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swetake.util.Qrcode;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.cache.RedisClient;
import com.tianee.webframe.util.file.TeeImageBase64Util;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/wenShuController")
public class TeeWenShuController {
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private TeePersonService personService;
	
	

	/**
	 * PDF公章
	 * @param request
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("/gzPdfSign.action")
	@ResponseBody
	public TeeJson gzPdfSign(HttpServletRequest request) throws Exception{
		TeeJson json=new TeeJson();
		//案件id
		String docId=TeeStringUtil.getString(request.getParameter("docId"));
		//获取前台传来的参数
		int attachId=TeeStringUtil.getInteger(request.getParameter("attachId"),0);	
		String bussRuleNum=TeeStringUtil.getString(request.getParameter("bussRuleNum"));//签章规则
		String templateNum=TeeStringUtil.getString(request.getParameter("templateNum"));//签章模板
		String creditCode=TeeStringUtil.getString(request.getParameter("creditCode"));//组织机构代码
		String userName=TeeStringUtil.getString(request.getParameter("userName"));//企业用户名称	
		String docName=TeeStringUtil.getString(request.getParameter("docName"));//文件描述信息
		TeeAttachment att=attachmentService.getById(attachId);
		TeeAttachment pdfAttach=wenShuService.gZPdfSign(docId,att, bussRuleNum, templateNum, creditCode, userName,docName);
		if(pdfAttach!=null){
			json.setRtData(pdfAttach.getSid());
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}*/
	/**
	 * PDF公章
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/gzPdfSign.action")
	@ResponseBody
	public TeeJson gzPdfSign(HttpServletRequest request) throws Exception{
		TeeJson json=new TeeJson();
		//案件id
		String docId=TeeStringUtil.getString(request.getParameter("docId"));
		//获取前台传来的参数
		int attachId=TeeStringUtil.getInteger(request.getParameter("attachId"),0);	
		
		String docName=TeeStringUtil.getString(request.getParameter("docName"));//文件描述信息
		TeeAttachment att=attachmentService.getById(attachId);
		TeeAttachment pdfAttach = wenShuService.gZPdfSign(docId, att, docName, request);
		if(pdfAttach!=null){
			json.setRtData(pdfAttach.getSid());
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	
	
	
	
	
	
	/**
	 * PDF个人章
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/grPdfSign.action")
	@ResponseBody
	public TeeJson grPdfSign(HttpServletRequest request) throws Exception{
		TeeJson json=new TeeJson();
		//获取参数
		int attachId=TeeStringUtil.getInteger(request.getParameter("attachId"),0);
		String fileUniqueId=TeeStringUtil.getString(request.getParameter("fileUniqueId"));//文档编号
		String msspId=TeeStringUtil.getString(request.getParameter("msspId"));//用户编号
		String ruleNum=TeeStringUtil.getString(request.getParameter("ruleNum"));//签章规则
		TeeAttachment att=attachmentService.getById(attachId);
		TeeAttachment pdfAttach=wenShuService.gRPdfSign(att, fileUniqueId, msspId, ruleNum);
		if(pdfAttach!=null){
			json.setRtData(pdfAttach.getSid());
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		
		return json;
	}
	
	
//####################################信步云扫码获取个人签章图片数据#########################################
	/**
	 * 发起个人网页扫码签章   返回将要生成二维码的json字符串  
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/signData4ESS.action")
	@ResponseBody
	public TeeJson signData4ESS(HttpServletRequest request) throws Exception{
		TeeJson json=new TeeJson();
		//获取参数
		String yw=TeeStringUtil.getString(request.getParameter("yw"));//原文内容
		String description=TeeStringUtil.getString(request.getParameter("description"));//描述
		String msspId=TeeStringUtil.getString(request.getParameter("msspId"));//用户编号
		String jsonStr=wenShuService.signData4ESS(yw, description, msspId);
		json.setRtData(jsonStr);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 获取签名结果  个人章图片数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSignSealData4ESS.action")
	@ResponseBody
	public TeeJson getSignSealData4ESS(HttpServletRequest request) throws Exception{
		TeeJson json=new TeeJson();
		//获取参数
		String signId=TeeStringUtil.getString(request.getParameter("signId"));//签章id
		String picData=wenShuService.getSignSealData4ESS(signId);
		json.setRtData(picData);
		json.setRtState(true);
		return json;
	}
	
	
//#####################################信步云扫码往PDF上盖章#####################################	
	/**
	 * 1 扫码个人PDF签章  返回将要生成二维码的字符串
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cloudSealUploadDocWithKeyID.action")
	@ResponseBody
	public TeeJson cloudSealUploadDocWithKeyID(HttpServletRequest request) throws Exception{
		TeeJson json=new TeeJson();
		//获取参数
		int attachId=TeeStringUtil.getInteger(request.getParameter("attachId"),0);//需要盖章的PDF文档 所对应的附件对象
		String msspId=TeeStringUtil.getString(request.getParameter("msspId"));//用户编号
		String ruleNum=TeeStringUtil.getString(request.getParameter("ruleNum"));//签章规则
		TeeAttachment att=attachmentService.getById(attachId);
		String jsonStr=wenShuService.cloudSealUploadDocWithKeyID(att, msspId, ruleNum);
		json.setRtState(true);
		json.setRtData(jsonStr);
		return json;
	}
	
	
	/**
	 * 获取结果
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cloudSealCommitSign.action")
	@ResponseBody
	public TeeJson cloudSealCommitSign(HttpServletRequest request) throws Exception{
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取参数
		String fileName=TeeStringUtil.getString(request.getParameter("fileName"));//文件名称
		String signId=TeeStringUtil.getString(request.getParameter("signId"));//签章ID
		//返回的pdf附件
		TeeAttachment  returnAtt=wenShuService.cloudSealCommitSign(signId, fileName, loginUser);
		json.setRtState(true);
		if(returnAtt!=null){
			json.setRtData(returnAtt.getSid());
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	
	
	
//######################################信手书盖到PDF上##################################################	
	
	
	/**
	 * 1 移动端做手写签名 生成加密包 传给服务端   2 服务端调用信手书服务接口 生成PDF文件
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/anySignEncPackage.action")
	@ResponseBody
	public TeeJson anySignEncPackage(HttpServletRequest request) throws Exception{
		TeeJson json=new TeeJson();
		//获取当前登录人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取参数
		String fileName=TeeStringUtil.getString(request.getParameter("fileName"));//文件名称
		String encodeStr=TeeStringUtil.getString(request.getParameter("encodeStr"));//加密包文件内容
		String docId=TeeStringUtil.getString(request.getParameter("docId"));
		//返回的pdf附件
		TeeAttachment  returnAtt=wenShuService.anySignEncPackage(encodeStr, fileName, loginUser,docId);
		json.setRtState(true);
		if(returnAtt!=null){
			json.setRtData(returnAtt.getSid());
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	
	
	
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@个人签名章相关的接口  和  OA相关   与CA无关@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	/**
	 * 签名章二维码图片生成
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/qMZQrCodeImage")
	public void qrCodeLoginImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String guid = request.getParameter("guid");
		System.out.println("http://127.0.0.1/wenShuController/qrCodeQmzCheck.action?guid="+guid);
		Qrcode rcode = new Qrcode();
		rcode.setQrcodeVersion(6); // 这个值最大40，值越大可以容纳的信息越多，够用就行了
		String url = "/qrcode_qmz.jsp?guid="+guid;
		byte[] content = null;
		
		content = url.getBytes("utf-8");
		BufferedImage bufImg = new BufferedImage(127, 127,
				BufferedImage.TYPE_INT_RGB); // 图片的大小
		Graphics2D gs = bufImg.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.clearRect(0, 0, 127, 127);
		gs.setColor(Color.BLACK);

		// 输出内容> 二维码
		if (content.length > 0 && content.length < 800) {
			boolean[][] codeOut = rcode.calQrcode(content);
			for (int i = 0; i < codeOut.length; i++) {
				for (int j = 0; j < codeOut.length; j++) {
					if (codeOut[j][i]) {
						gs.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
					}
				}
			}
		} else {
		}
		gs.dispose();
		bufImg.flush();
		
		response.setHeader("Pragma", "no-cache");         
        response.setHeader("Cache-Control", "no-cache");         
        response.setDateHeader("Expires", 0);         
        response.setContentType("image/jpeg");         
		
        // 将图像输出到Servlet输出流中。         
        ServletOutputStream sos = response.getOutputStream();         
        ImageIO.write(bufImg, "jpeg", sos);         
        sos.close();         
	} 
	
	
	
	/**
	 * 轮询获取扫码结果
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/qrCodeQmzCheck")
	@ResponseBody
	public TeeJson qrCodeLoginCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = new TeeJson();
		String guid = request.getParameter("guid");
		Map<String,String> identity = (Map<String, String>) RedisClient.getInstance().getObject("QR_CODE_"+guid);
		if(identity!=null){
			
			//获取签名图片  base64
			String userId=identity.get("userId");
			TeePerson person=personService.getPersonByUserId(userId);
			//获取签名图片对应的附件对象
			TeeAttachment qmPic=person.getAttach();
			Map<String,Object> map=new HashMap<String,Object> ();
			map.put("uuid", person.getUuid());
			map.put("userName", person.getUserName());
			map.put("performCode", person.getPerformCode());
			map.put("personNo", person.getUserNo());
			if(qmPic!=null){//存在签名图片
				
				//将图片转换成base64的格式
				InputStream input=new FileInputStream(qmPic.getFilePath());  
				String  base64Str=TeeImageBase64Util.GetImageStr(input);
				map.put("base64Str", base64Str);
				json.setRtData(map);
				//System.out.println(base64Str);
				json.setRtState(true);
				RedisClient.getInstance().delObject("QR_CODE_"+guid);
			}else{
				json.setRtState(false);
			}
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	
	/**
	 * 二维码   扫码者身份写入
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/qrCodeQmzIdentityWrite")
	@ResponseBody
	public TeeJson qrCodeLoginIdentityWrite(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		String guid = request.getParameter("guid");
		String userId = person.getUserId();
		person = personService.getPersonByUserId(userId);
		Map<String,String> identity = new HashMap();
		identity.put("userId", userId);
		
		//往redis中存入标识信息
		RedisClient.getInstance().setExObject("QR_CODE_"+guid,identity,60);
		
		return json;
	}
	

	
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&移动端CA登陆&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	/**
	 * 移动端pin码登陆  发起请求
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/beginPinCodeLogin")
	@ResponseBody
	public TeeJson beginPinCodeLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json=new TeeJson();
		json=wenShuService.beginPinCodeLogin(request);
		return json;
	}
	
	
	
	/**
	 * 判断登陆失败还是成功
	 */
	@RequestMapping("/checkPinCodeLogin")
	@ResponseBody
	public TeeJson checkPinCodeLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json=new TeeJson();
		json=wenShuService.checkPinCodeLogin(request);
		return json;
	}
	
}
