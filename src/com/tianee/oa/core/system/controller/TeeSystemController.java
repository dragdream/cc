package com.tianee.oa.core.system.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.swetake.util.Qrcode;
import com.tianee.oa.core.general.bean.TeeInterface;
import com.tianee.oa.core.general.service.TeeInterfaceService;
import com.tianee.oa.core.general.service.TeeSmsService;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserOnlineService;
import com.tianee.oa.core.system.service.TeeSystemServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.cache.RedisClient;
import com.tianee.webframe.util.date.TeeLunarCalendarUtils;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.file.TeeUploadHelper;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/systemAction")
public class TeeSystemController {
	@Autowired
	TeeSystemServiceInterface systemServ;
	@Autowired
	TeePersonService personService;
	@Autowired
	TeeSmsService smsService;
	
	@Autowired
	private TeeInterfaceService interfaceService;
	
	@Autowired
	private TeeSysParaService sysParaService;
	
	
	@Autowired
	private TeeUserOnlineService onlineService;
	
	/**
	 * 单点登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doSSOLoginIn")
	@ResponseBody
	public TeeJson doSSOLoginIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = systemServ.doSSOLoginIn(request ,response);
		return json;
	}
	
	
	/**
	 * 系统登录方法
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
		TeeJson json = systemServ.doLoginInService(request ,response);
		return json;
	}
	
	/**
	 * 高速波云平台系统登录方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doCloudLoginIn")
	public String doCloudLoginIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = systemServ.doLoginInService(request ,response);
		if(json.isRtState()){
			Map data = (Map)json.getRtData();
			 String  theme = TeeStringUtil.getString(data.get("theme"));
			return "redirect:/system/frame/"+theme+"/index.jsp";
		}else{
			return "redirect:/" + request.getContextPath();
		}
	}
	/**
	 * 登录获取方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/prepareLoginIn")
	public  String prepareLoginIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ieTitle = "";
		TeeInterface inte = interfaceService.select();
		if(inte != null && !TeeStringUtil.getString(inte.getIeTitle()).equals("")){
			ieTitle = inte.getIeTitle();
		}
		//获取系统登录只需一次重新登录的参数
		Map map = sysParaService.getParaListToLogin();
		String SEC_USER_MEM = (String)map.get("SEC_USER_MEM");//是否记忆卡
		HttpSession session = request.getSession();
		session.setAttribute("SEC_USER_MEM", SEC_USER_MEM);//是否记忆卡
		session.setAttribute("IE_TITLE", ieTitle);//主界面IE标题
		//request.getRequestDispatcher("/login.jsp").forward(request, response);
		return "/login.jsp";
	}
	
	/**
	   * 退出登录
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	  @RequestMapping("/doLoginout")
	  @ResponseBody
	  public TeeJson doLoginout(HttpServletRequest request, HttpServletResponse response) {
		  String deviceNo = (String)request.getSession().getAttribute("deviceNo");
		  TeeJson json = systemServ.doLogoutService(request, response);
		  HttpSession session = request.getSession(false);
		  if (session != null){
			   session.invalidate();
		  }
		  return json;
	  }
	  
		/**
	   * 如果超过当天自动更新系统时间
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	  @RequestMapping("/getCurrLunarDate")
	  @ResponseBody
	  public TeeJson getCurrLunarDate(HttpServletRequest request, HttpServletResponse response) {
		  TeeJson json = new TeeJson();
		  String currDateInfo = TeeLunarCalendarUtils.today();
		  json.setRtState(true);
		  json.setRtData(currDateInfo);
		  return json;
	  }
	  
	  /**
	   * 获取个模块需要办理的记录数
	   * @author syl
	   * @date 2014-6-15
	   * @param request
	   * @param response
	   * @return
	 * @throws ParseException 
	   */
	  @RequestMapping("/getModelHandCount")
	  @ResponseBody
	  public TeeJson getModelHandCount(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		  TeeJson json = systemServ.getModelHandCount(request);
		  return json;
	  }
	
	  /**
	   * 获取有权限的子菜单  ---第二套风格
	   * @author syl
	   * @date 2014-3-19
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/getPostChildMenuToFrame2")
	  @ResponseBody
	  public TeeJson getPostChildMenuToFrame2(HttpServletRequest request, HttpServletResponse response) {
		  String type = TeeStringUtil.getString(request.getParameter("type"));//1-查询登录界面 已选的菜单
		  TeeJson json = null;
		  if(type.equals("1")){
			  json = systemServ.getPostSelectChildMenuInfoPerson(request);
		  }else{
			  json = systemServ.getPostChildMenuInfoByPerson(request);
		  }
		 
		  return json;
	  }
	  
	  /**
	   * 更新人员在线状态
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping("/updateUserOnlineService")
	  @ResponseBody
	  public TeeJson updateUserOnlineService(HttpServletRequest request, HttpServletResponse response) {
		  Map map = TeeServletUtility.getParamMap(request);
		  return onlineService.updateBySessionToken(map);
	  }
	  

	public static void main(String[] args) {
		String password = "p12233";
		// $1$kkPXXZa2$f8t/CnNl/v.kaKkq8c5/I1
		String password2 = DigestUtils.md5Hex(password);
		String password3 = DigestUtils.md5Hex(password);
		// System.out.println(PasswordEncode.isPasswordEnable("123456",
		// PasswordEncode.digestPassword("123456")));
		// //
		// DigestUtils.
		System.out.println(password2 + "\n" + password3);
	}
	
	
	
	

	public void setSystemServ(TeeSystemServiceInterface systemServ) {
		this.systemServ = systemServ;
	}

	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}

	public void setSmsService(TeeSmsService smsService) {
		this.smsService = smsService;
	}

	public void setInterfaceService(TeeInterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}
	public void setSysParaService(TeeSysParaService sysParaService) {
		this.sysParaService = sysParaService;
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
	@RequestMapping("/downPcFile")
	public String downPcFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filePath = TeeSysProps.getRootPath() + "appupdate/im/PC_Update.exe";
		OutputStream ops = null;
		InputStream is = null;
		try {
			File file = new File(filePath);
		    if(file.exists()){
		    	is = new FileInputStream(filePath);
			}
			String fileName = "PC_Update.exe";
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
	 * 二维码下载
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/qrCodeDownload")
	public void qrCodeDownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dlType = request.getParameter("dlType");
		Qrcode rcode = new Qrcode();
		rcode.setQrcodeVersion(6); // 这个值最大40，值越大可以容纳的信息越多，够用就行了
		String url = (request.getProtocol().toLowerCase().contains("https") ? "https"
				: "http")
				+ "://" + request.getServerName() + ":" + request.getServerPort();
		byte[] content = null;
		if ("Android".equals(dlType)) {
			url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zatp.app";
		} else if ("iOS".equals(dlType)) {
			url = "https://itunes.apple.com/us/app/%E7%A7%BB%E5%8A%A8%E5%8A%9E%E5%85%AC2017/id1203190020?mt=8";
		} else if ("Pc".equals(dlType)) {
			url += "/appupdate/im/PC_Setup.exe";
		}
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
	 * 二维码登录的图片渲染
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/qrCodeLoginImage")
	public void qrCodeLoginImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String guid = request.getParameter("guid");
		
		Qrcode rcode = new Qrcode();
		rcode.setQrcodeVersion(6); // 这个值最大40，值越大可以容纳的信息越多，够用就行了
		String url = "/qrcode_login.jsp?guid="+guid;
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
	 * 二维码登录的图片渲染
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/qrCodeLoginCheck")
	@ResponseBody
	public TeeJson qrCodeLoginCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = new TeeJson();
		String guid = request.getParameter("guid");
		Map<String,String> identity = (Map<String, String>) RedisClient.getInstance().getObject("QR_CODE_"+guid);
		if(identity!=null){
			json.setRtData(identity);
			json.setRtState(true);
			RedisClient.getInstance().delObject("QR_CODE_"+guid);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	
	/**
	 * 二维码登录身份写入
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/qrCodeLoginIdentityWrite")
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
		identity.put("pwdCrypt", person.getPassword());
		
		//往redis中存入标识信息
		RedisClient.getInstance().setExObject("QR_CODE_"+guid,identity,60);
		
		return json;
	}
	
	
	/**
	 * 刷新缓存
	 * @param request
	 * @return
	 */
	 @RequestMapping("/refreshCache")
	  @ResponseBody
	  public TeeJson refreshCache(HttpServletRequest request) {
		  TeeJson json = new TeeJson();
		  int cacheFlag = TeeStringUtil.getInteger(request.getParameter("cacheFlag"), 0);
		  systemServ.refreshCache(cacheFlag);
		  return json;
	  }
	 
	 /**
	  * 更新序列号
	  * @param request
	  * @return
	  */
	 @ResponseBody
	 @RequestMapping("/updateSeries")
		public TeeJson updateSeries(HttpServletRequest request){
			TeeJson json = new TeeJson();
			String series = TeeStringUtil.getString(request.getParameter("series"));
			String pwd = TeeStringUtil.getString(request.getParameter("pwd"));
			TeePerson adminUser = personService.getPersonByUserId("admin");
			if(TeePassEncryptMD5.checkCryptDynamic(pwd, adminUser.getPassword().trim())){
				sysParaService.updateSysPara("SERIAL", series);
				try {
					TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "initAuthInfo", null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				json.setRtState(true);
			}else{
				json.setRtState(false);
			}
			return json;
		}
	 
	 /**
	  * 更新组织机构
	  * @param request
	  * @return
	  */
	 @ResponseBody
	 @RequestMapping("/updateUnitName")
		public TeeJson updateUnitName(HttpServletRequest request){
			TeeJson json = new TeeJson();
			String pwd = TeeStringUtil.getString(request.getParameter("pwd"));
			TeePerson adminUser = personService.getPersonByUserId("admin");
			String unitName = TeeStringUtil.getString(request.getParameter("unitName"));
			if(TeePassEncryptMD5.checkCryptDynamic(pwd, adminUser.getPassword().trim())){
				systemServ.updateUnitName(unitName);
				try {
					TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "initAuthInfo", null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				json.setRtState(true);
			}else{
				json.setRtState(false);
			}
			return json;
		}
	 
	 /**
	  * 重新加载授权信息
	  * @param request
	  * @return
	  */
	 @ResponseBody
	 @RequestMapping("/reloadAuthInfo")
		public TeeJson reloadAuthInfo(HttpServletRequest request){
		 TeeJson json = new TeeJson();
			String pwd = TeeStringUtil.getString(request.getParameter("pwd"));
			TeePerson adminUser = personService.getPersonByUserId("admin");
			if(TeePassEncryptMD5.checkCryptDynamic(pwd, adminUser.getPassword().trim())){
				try {
					TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "initAuthInfo", null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				json.setRtState(true);
			}else{
				json.setRtState(false);
			}
			return json;
		}
	 
	 
	 /**
	 * 上传im客户端
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadImClient")
	@ResponseBody
	public TeeJson uploadImClient(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = new TeeJson();
		json.setRtState(true);
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		
		//获取安卓文件
		MultipartFile androidFile = multipartHttpServletRequest.getFile("android");
		if(androidFile!=null){
			String fileName = androidFile.getOriginalFilename();
			if(fileName.endsWith(".apk")){
				String sp[] = fileName.replace(".apk", "").split("_");
				//覆盖文件
				TeeUploadHelper.saveFile(androidFile.getInputStream(), TeeSysProps.getRootPath()+"appupdate/android/Android_Setup.apk");
				sysParaService.updateSysPara("ANDROID_CURR_VERSION", sp[2]);
			}
		}
		
		//获取pc文件
		MultipartFile pcFile = multipartHttpServletRequest.getFile("pc");
		if(pcFile!=null){
			String fileName = pcFile.getOriginalFilename();
			if(fileName.endsWith(".exe")){
				String sp[] = fileName.replace(".exe", "").split("_");
				//覆盖文件
				TeeUploadHelper.saveFile(pcFile.getInputStream(), TeeSysProps.getRootPath()+"appupdate/im/PC_Setup.exe");
				sysParaService.updateSysPara("PC_CURR_VERSION", sp[2]);
			}
		}
		
		return json;
	}
	
	
	
	 @RequestMapping("/generateWaterMark")
	 @ResponseBody
	 public void generateWaterMark(HttpServletRequest request, HttpServletResponse response) throws Exception{ 
		    //获取当前登陆人
		    TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	        String userName="";
	        if(loginUser!=null){
	        	userName=loginUser.getUserName();
	        }
	        
	        response.setContentType("image/png");

            int width = 200;  
            int height = 200;  
            int fontSize = 20;
            // 创建BufferedImage对象  
            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
            // 获取Graphics2D  
            Graphics2D g2d = image.createGraphics();
            
            //设置透明背景
            image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
            g2d.dispose();  
            g2d = image.createGraphics(); 
            
            // 画图  
//            g2d.setBackground(Color.WHITE); 
            g2d.setColor(Color.LIGHT_GRAY);
            
//            g2d.clearRect(0, 0, width, height);  
            Font font=new Font("宋体",Font.PLAIN,fontSize);  
            
            g2d.setFont(font);  
            // 抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics fm = g2d.getFontMetrics(font);
            int textWidth = fm.stringWidth(userName);
            int widthX = (width - textWidth) / 2;
            int heightY = (height-fontSize+50)/2;
            g2d.rotate(75, width/2, height/2);
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。 
            g2d.drawString(userName,widthX,heightY);  
            // 释放对象  
            g2d.dispose();  
            // 保存文件      
            ImageIO.write(image, "png", response.getOutputStream());  
			
	    } 

}
