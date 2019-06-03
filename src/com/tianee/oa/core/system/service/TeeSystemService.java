package com.tianee.oa.core.system.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.attend.bean.TeeAttendConfig;
import com.tianee.oa.core.base.attend.dao.TeeAttendConfigDao;
import com.tianee.oa.core.base.calendar.dao.TeeCalendarAffairDao;
import com.tianee.oa.core.base.calendar.service.TeeCalAffairService;
import com.tianee.oa.core.base.email.service.TeeEmailService;
import com.tianee.oa.core.base.message.dao.TeeMessageDao;
import com.tianee.oa.core.general.bean.TeeInterface;
import com.tianee.oa.core.general.dao.TeeInterfaceDao;
import com.tianee.oa.core.general.dao.TeeSmsDao;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeePersonDynamicInfo;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserOnlineDao;
import com.tianee.oa.core.org.service.TeeUserOnlineService;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.oa.core.priv.dao.TeeMenuDao;
import com.tianee.oa.core.priv.model.TeeSysMenuModel;
import com.tianee.oa.core.system.adapter.TeeLoginAdapter;
import com.tianee.oa.core.system.adapter.TeePcValidator;
import com.tianee.oa.core.system.adapter.TeeRegistAuthValidator;
import com.tianee.oa.core.system.filters.TeeBindIpValidator;
import com.tianee.oa.core.system.filters.TeeBindMacValidator;
import com.tianee.oa.core.system.filters.TeeExistUserValidator;
import com.tianee.oa.core.system.filters.TeeForbidLoginValidator;
import com.tianee.oa.core.system.filters.TeeInitialPwdValidator;
import com.tianee.oa.core.system.filters.TeeIpRuleValidator;
import com.tianee.oa.core.system.filters.TeeOtherPasswordValidator;
import com.tianee.oa.core.system.filters.TeePasswordValidator;
import com.tianee.oa.core.system.filters.TeePwdErrorValidator;
import com.tianee.oa.core.system.filters.TeePwdExpiredValidator;
import com.tianee.oa.core.system.filters.TeeSetUserRoleValidator;
import com.tianee.oa.core.system.filters.TeeUkeyValidator;
import com.tianee.oa.core.system.filters.TeeVerificationCodeValidator;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.servlet.TeeSessionListener;
import com.tianee.webframe.util.cache.TeeCacheManager;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.openfire.TeeOpenfireUtil;
import com.tianee.webframe.util.secure.TeePassBase64;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeSystemService extends TeeBaseService implements TeeSystemServiceInterface {
	@Autowired
	TeeSysParaDao sysParaDao;
	@Autowired
	TeePersonDao personDao;
	
	@Autowired
	TeeInterfaceDao interfaceDao;
	
	@Autowired
	private TeeIpRuleValidator ipRuleValidator;
	
	@Autowired
	TeeUserOnlineService onlineService; 
	
	@Autowired
	TeeUserOnlineDao userOnlineDao;
	
	@Autowired
	TeeCalAffairService affairService;
	
	@Autowired
	private TeeMenuDao menuDao;
	
	@Autowired
	private TeeCalendarAffairDao calendarAffairDao;
	
	@Autowired
	private TeeSmsDao smsDao;
	@Autowired
	private TeeAttendConfigDao attendConfigDao;
	
	@Autowired
	private TeeEmailService emailService;
	
	@Autowired
	private TeeMessageDao  messageDao;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#doLoginInService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@TeeLoggingAnt(template="登录系统",type="003E")
    public TeeJson doLoginInService(HttpServletRequest request , HttpServletResponse response){
		TeeJson json = new TeeJson();
		Map map = new HashMap();
	    try {
		   String url = new String(request.getRequestURL());
		   Map<String,String> sysParamap = sysParaDao.getParaListToLogin();
		   String keyUser = TeeUtility.null2Empty(request.getParameter("KEY_USER"));
		   String caUser = TeeUtility.null2Empty(request.getParameter("CA_USER"));
		   //高速波云OA 账号名称 和MD5密码
		   String cloudUserName = TeeUtility.null2Empty(request.getParameter("cloudUserName"));
		   String cloudPwd = TeeUtility.null2Empty(request.getParameter("cloudPwd"));
		   
		   String userName = request.getParameter("userName");
		   String pwd = request.getParameter("pwd");
		   if (TeeUtility.isNullorEmpty(userName) && !TeeUtility.isNullorEmpty(keyUser)) {
		       userName = keyUser;
		   }
		   if(!TeeUtility.isNullorEmpty(cloudUserName)){//高速波云平台登录
			   userName = cloudUserName;
			   pwd = cloudPwd;
		   }
	      TeePerson person = null;
	      if (TeeUtility.isNullorEmpty(userName)) {
	          userName = "";
	      }else {
	    	  
	    	   if(!TeeUtility.isNullorEmpty(cloudUserName)){
	    		   person = personDao.getPersonByCloudUserId(userName);
	    	   }else{
	    		   person = personDao.getPersonByUserId(userName);
	    	   }
	    	   if(person != null){
		    	  // person.getMenuGroups().size();
	    		   person.getPostDept().size();
	    		   if(person.getDept() != null){
	    			   person.getDept().getDeptName();
	    		   }
	    		   if(person.getUserRole()!=null){
	    			   person.getUserRole().getRoleName();
	    		   }
	    		   person.getUserRoleOther().size();
	    	   }
	      }
	   
	      
	      //登录验证
	      TeeLoginAdapter loginAdapter = new TeeLoginAdapter(request, person,json , sysParamap);
	      //验证软件是否过期

	      if(!loginAdapter.validate(new TeeRegistAuthValidator())){
	    	  return json;
	      }
	      
	      //验证用户是否存在
	      if (!loginAdapter.validate(new TeeExistUserValidator())){
	    	  return json;
	      }
	      TeeRequestInfoContext.getRequestInfo().setUserName(person.getUserName());
	      TeeRequestInfoContext.getRequestInfo().setUserId(person.getUuid() + "");
	      //验证用户是否禁止登陆
	      if (!loginAdapter.validate(new TeeForbidLoginValidator())){
	          return  json;
	      }
	      
	      //验证Ip规则
	      if (!loginAdapter.validate( ipRuleValidator)){//注入方式
		        return json;
		  }
	  
	      
	      //验证用户绑定Ip
	      if (!loginAdapter.validate(new TeeBindIpValidator())){
	        return json;
	      }
	      
	      //验证用户绑定Mac地址
	      if (!loginAdapter.validate(new TeeBindMacValidator())){
	    	  return json;
	      }
	

	      //验证是否设置角色
	      if (!loginAdapter.validate(new TeeSetUserRoleValidator())){
		        return json;
		  }
	      
	      //校验登录验证码
	      if(!loginAdapter.validate(new TeeVerificationCodeValidator())){
	    	  return json;
	      }

	      //验证Ukey
	      if(!loginAdapter.validate(new TeeUkeyValidator())){
	    	  return json;
	      }
	      
	     /* //验证用户是否重复登陆
	      if (!loginAdapter.validate(new TeeRepeatLoginValidator())){
	        return "/core/inc/rtjson.jsp";
	      }
	      end = System.currentTimeMillis();
	   
	      if("1".equals(useingKey.trim()) && "1".equals(map.get("LOGIN_KEY"))){
	        
	        //当用户使用Usbkey登陆时,进行usbkey验证
	        if (!loginAdapter.validate(new TeeUsbkeyValidator())){
	          return "/core/inc/rtjson.jsp";
	        }
	      } else if(caUser != null && "1".equals(caUser.trim())){
	        //如果是ca用户则放行，默认其合法
	      }
	      else {
	        start = System.currentTimeMillis();
	        if (!loginAdapter.validate(new TeeVerificationCodeValidator())){
	          return "/core/inc/rtjson.jsp";
	        }
	        end = System.currentTimeMillis();
	        start = System.currentTimeMillis();**/
	      
	      //系统登录错误次数校验
	      	Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组 
	      	/*for (int i = 0; i < cookies.length; i++) {
				System.out.println(cookies[i].getName() +":"+ cookies[i].getValue());
			}*/
	        if (!loginAdapter.validate(new TeePwdErrorValidator())){  
	        	int SEC_BAN_TIME = TeeStringUtil.getInteger((String)sysParamap.get("SEC_PASS_TIME") , 0);//有效时间
	        	addCookie(cookies, response, SEC_BAN_TIME, person);
	        	return  json;
	        }
	    	
	       if(!TeeUtility.isNullorEmpty(cloudUserName)){//高速波云平台登录
	    	   if (!loginAdapter.validate(new TeeOtherPasswordValidator(pwd , "1"))){
	 	        	int SEC_BAN_TIME = TeeStringUtil.getInteger((String)sysParamap.get("SEC_PASS_TIME") , 0);//有效时间
	 	        	addCookie(cookies, response, SEC_BAN_TIME, person);
	 	        	return json;
	 	        }
	        }else{
	        	if (!loginAdapter.validate(new TeePasswordValidator(pwd))){
	 	        	int SEC_BAN_TIME = TeeStringUtil.getInteger((String)sysParamap.get("SEC_PASS_TIME") , 0);//有效时间
	 	        	addCookie(cookies, response, SEC_BAN_TIME, person);
	 	        	return json;
	 	        }
	        }
	       
	     /*}
	      */
	      
	      String loginType = TeeStringUtil.getString(request.getParameter("CLIENT"), "1");
	      
	      if("2".equals(loginType)){//PC终端通讯登陆人数判断
		      if (!loginAdapter.validate(new TeePcValidator())){
		    	  return json;
		      }
	      }
	      
	    //调用登陆成功的处理
	      this.loginSuccess( person, request  ,  response,loginType);
	
	  /*    try {
	        start = System.currentTimeMillis();
	        Cookie cookie = reportSSO(person, pwd, request);
	        end = System.currentTimeMillis();
	        if (cookie != null) { 
	          start = System.currentTimeMillis();
	          response.addCookie(cookie);
	          end = System.currentTimeMillis();
	        }
	        
	    
	      }catch(Exception ex) {
	      }*/
	      
	      //设置系统参数存在在session中
	      setSysParaSession(sysParamap, request.getSession());
	      
	      deleteCookie(cookies, response, person.getUuid());//销毁cookie
	      
	      //验证时候需要修改初始密码
	      if (!loginAdapter.validate(new TeeInitialPwdValidator())){
	        return json;
	      }
	      //验证密码是否过期,是否需要修改
	      if (!loginAdapter.validate(new TeePwdExpiredValidator())){
	        return json;
	      }
	      
//	      List<TeeInterface> list = interfaceDao.select();
//	      if(list.size() > 0){
//	    	  TeeInterface inte = list.get(0);
//	    	  setIntefaceSession(inte, request.getSession()) ;
//	      }
	      
	      TeeInterface itfc = (TeeInterface) simpleDaoSupport.get(TeeInterface.class,1);
          String title =  itfc.getIeTitle();
          map.put("imPic",TeeStringUtil.getString(itfc.getImPic()));
          map.put("title",title);
	      map.put("sid", person.getUuid());
	      map.put("userName", person.getUserName());
	      map.put("userId", person.getUserId());
	      map.put("JSESSIONID", request.getSession().getId());
	      map.put("PATH", request.getContextPath());
	      map.put("theme", person.getTheme());
	      map.put("autoPopSms", person.getAutoPopSms());
	      map.put("photoId", TeeStringUtil.getString(person.getAvatar()));//是附件Id （如：190）
	      map.put("userOnlineStatus", "1");//人员在线状态 空和0 -不在线，1- 在线 2-忙碌 3-离开,刚登录为在线
	      map.put("socketPort", TeeSysProps.getString("SOCKET_PORT"));
	      map.put("currVersion", TeeSysProps.getString("PC_CURR_VERSION"));
	      String deptName = "";
	      String userRoleName = "";
	      int userRoleId=0;
	      if( person.getDept() != null){
	    	  deptName =  person.getDept().getDeptName();
	      }
	      if( person.getUserRole() != null){
	    	  userRoleName = person.getUserRole().getRoleName();
	    	  userRoleId=person.getUserRole().getUuid();
	      }
	      map.put("deptName",deptName );
	      map.put("roleName", userRoleName);
	      map.put("roleId",userRoleId );
	      map.put("isAdmin", person.getIsAdmin());
	      
	      //处理登录样式
	      int STYLE_TYPE_INDEX_2 = 1;//第二套风格
	      String theme = TeeStringUtil.getString(person.getTheme());
	      int themeIndex = 1;
		  if(theme.length() == 2){
		      //themeIndex = theme.subString(0,1);
			  STYLE_TYPE_INDEX_2 = TeeStringUtil.getInteger(theme.substring(1,2) ,1);
		  }
		  request.getSession().setAttribute("STYLE_TYPE_INDEX_2", STYLE_TYPE_INDEX_2);
	      
	    } catch (Exception ex) {
	        String retData = "{\"code\":9999,\"msg\":\""+ex.getMessage()+"\"}";
	        ex.printStackTrace();
	        TeeJsonUtil jsonUtil = new TeeJsonUtil();
	        JSONObject jsonObj = jsonUtil.jsonString2Json(retData);
	    	json.setRtMsg(ex.getMessage());
	    	json.setRtState(false);
	    	json.setRtData(jsonObj);
	    	return json;
	    	//System.out.println(ex.getMessage());
	    }
     	json.setRtState(true);
     	json.setRtData(map);
     	json.setRtMsg("登录成功");
	    return json ;

    }

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#addCookie(javax.servlet.http.Cookie[], javax.servlet.http.HttpServletResponse, int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public void addCookie(Cookie[] cookies , HttpServletResponse response  ,int  SEC_BAN_TIME, TeePerson person){ 
		String LOGIN_ERROR_COUNT_VALUE = "0";
		if(cookies != null){
			for(Cookie cookie : cookies){ //获取cookie
		         String personLoginErrorName = cookie.getName();// get the cookie name 
		   		 if(personLoginErrorName.equals("LOGIN_ERROR_COUNT_" + person.getUuid())){
		   		      LOGIN_ERROR_COUNT_VALUE = cookie.getValue(); // get the cookie value
		   		      //System.out.println(LOGIN_ERROR_COUNT_VALUE +"----------");
		   		       break;
		   		   }
		     }
		}
	    
	     int LOGIN_ERROR_COUNT = TeeStringUtil.getInteger(LOGIN_ERROR_COUNT_VALUE, 0);
	      //重新设置cookie
	     Cookie newErrorCountCookie = new Cookie("LOGIN_ERROR_COUNT_" + person.getUuid(), (LOGIN_ERROR_COUNT + 1)  + "");
	     Cookie newErrorTimeCookie = new Cookie("LOGIN_LAST_ERROR_TIME_VALUE_" + person.getUuid(), new Date().getTime() + "");
	     if(SEC_BAN_TIME>0){
	    	 newErrorCountCookie.setMaxAge(SEC_BAN_TIME * 60 );
	    	 newErrorCountCookie.setPath("/");
	         newErrorTimeCookie.setMaxAge(SEC_BAN_TIME * 60); 
	         newErrorTimeCookie.setPath("/");
	      } 
	      response.addCookie(newErrorCountCookie);
	      response.addCookie(newErrorTimeCookie);	
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#deleteCookie(javax.servlet.http.Cookie[], javax.servlet.http.HttpServletResponse, int)
	 */
	@Override
	public void deleteCookie(Cookie[] cookies , HttpServletResponse response  , int personId){ 
		if(cookies!=null){
			for (Cookie ck : cookies) {  
				if(ck.getName().equals("LOGIN_ERROR_COUNT_" + personId)){
					ck.setMaxAge(0);  
					response.addCookie(ck);
					break;
				}
				if(ck.getName().equals("LOGIN_LAST_ERROR_TIME_VALUE_" + personId)){
					ck.setMaxAge(0);  
					response.addCookie(ck);
					break;
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#setSysParaSession(java.util.Map, javax.servlet.http.HttpSession)
	 */
	@Override
	public void setSysParaSession(Map sysParaMap , HttpSession session){
		if(sysParaMap != null ){
			String SEC_INIT_PASS = TeeStringUtil.getString((String)sysParaMap.get("SEC_INIT_PASS"), "0");//初始密码
			session.setAttribute("SEC_INIT_PASS", SEC_INIT_PASS);
			
			String SEC_PASS_FLAG = TeeStringUtil.getString((String)sysParaMap.get("SEC_PASS_FLAG"), "0");//是否过期
			session.setAttribute("SEC_PASS_FLAG", SEC_PASS_FLAG);
			
			String SEC_PASS_TIME = TeeStringUtil.getString((String)sysParaMap.get("SEC_PASS_TIME"), "0");//过期时间
			session.setAttribute("SEC_PASS_TIME", SEC_PASS_TIME);

			String SEC_SHOW_IP = TeeStringUtil.getString((String)sysParaMap.get("SEC_SHOW_IP"), "0");//是否显示IP
			session.setAttribute("SEC_SHOW_IP", SEC_SHOW_IP);
			
			String SEC_ON_STATUS = TeeStringUtil.getString((String)sysParaMap.get("SEC_ON_STATUS"), "0");//记录在线状态
			session.setAttribute("SEC_ON_STATUS", SEC_ON_STATUS);	
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#setIntefaceSession(com.tianee.oa.core.general.bean.TeeInterface, javax.servlet.http.HttpSession)
	 */
	@Override
	public void setIntefaceSession(TeeInterface inte , HttpSession session){
		String ieTitle = "中安科技办公管理平台";
		String logOutText = "欢迎您使用" + ieTitle + "，下次再见";
		int AVATAR_UPLOAD = 1;//是否允许上传图象
		int AVATAR_WIDTH = 0;//上传图象大小宽度
		int AVATAR_HEIGHT = 0;//上传图象大小高度
		
		String TOP_BANNER_FONT = "";//顶部字体样式
		String TOP_BANNER_TEXT = "";//顶部字
		String BOTTOM_STATUS_TEXT = "";//主界面底部文字
		
		String TOP_ATTACHMENT_ID = "";//主界面顶部图片
		if(inte != null ){
			ieTitle =  TeeStringUtil.getString(inte.getIeTitle(),ieTitle);
			logOutText =  TeeStringUtil.getString(inte.getLogOutText() , logOutText);
			AVATAR_UPLOAD =  inte.getAvatarUpload();
			AVATAR_WIDTH = inte.getAvatarWidth();
			AVATAR_HEIGHT = inte.getAvatarHeight();
			TOP_BANNER_FONT = inte.getTopBannerFont();
			TOP_BANNER_TEXT = inte.getTopBannerText();
			BOTTOM_STATUS_TEXT = inte.getBottomStatusText();
			TOP_ATTACHMENT_ID = TeeStringUtil.getString(inte.getTopAttachmentId() );
		}
		session.setAttribute("IE_TITLE", ieTitle);//主界面IE标题
		session.setAttribute("LOGIN_OUT_TEXT", logOutText);//退出
		session.setAttribute("AVATAR_UPLOAD", AVATAR_UPLOAD);//图像上床
		session.setAttribute("AVATAR_WIDTH", AVATAR_WIDTH);	//图像宽度
		session.setAttribute("AVATAR_HEIGHT", AVATAR_HEIGHT);	//图像高度
		
		session.setAttribute("TOP_BANNER_FONT", TOP_BANNER_FONT);	//顶部字体
		session.setAttribute("TOP_BANNER_TEXT", TOP_BANNER_TEXT);	//顶部文字
		session.setAttribute("BOTTOM_STATUS_TEXT", BOTTOM_STATUS_TEXT);	//底部文字
		
		session.setAttribute("TOP_ATTACHMENT_ID", TOP_ATTACHMENT_ID);	//顶部图片Id TeeAttachment.sid
	}

    /* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#loginSuccess(com.tianee.oa.core.org.bean.TeePerson, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
    @Override
	public void loginSuccess(TeePerson person, HttpServletRequest request , HttpServletResponse response ,String loginType) throws Exception{
      
      //获取用户当前的session,如果不存在就生成一个新的session
      HttpSession session = request.getSession(true);
      //加入session
      TeeSessionListener.data.put(session.getId(), session);
      
      session.setAttribute(TeeConst.SMS_FLAG, 1);//设置短信提醒标记
      String deviceNo = TeeStringUtil.getString(request.getParameter("deviceNo") , "");//设备号
      String userName = request.getParameter("userName");
      
      session.setAttribute("deviceNo", deviceNo);//session存储设备号
      session.setAttribute("theme", person.getTheme());
      //更新最后一次访问信息
      //personDao.updateLastVisitInfo( person.getUuid(), request.getRemoteAddr());
      //记录登陆的时间
      TeePersonDynamicInfo dynamicInfo = person.getDynamicInfo();
      if(dynamicInfo==null){
    	  dynamicInfo = new TeePersonDynamicInfo();
    	  person.setDynamicInfo(dynamicInfo);
      }
      dynamicInfo.setLastVisitTime(new Date());
      dynamicInfo.setLastVisitIp(request.getRemoteAddr());
      
      //登录时尝试到OpenFire中创建用户
      TeeOpenfireUtil.createUser(person.getUserId(), person.getUserName());
      
      //判断用户是否已经登录
      if (session.getAttribute(TeeConst.LOGIN_USER) == null){
    	  String sessionToken = session.getId();
    	  onlineService.addOrUpdateUserOnline(request, person, sessionToken);
    	  setUserInfoInSession(person, session, request.getRemoteAddr(), request , sessionToken);
      }else {
    	  TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
    	  //如果是新用户登录时,销毁原有的session
    	  if (loginPerson.getUuid() !=  person.getUuid() ){
	          //销毁session
	          session.invalidate();
	          //重新调用登录成功的处理
	          loginSuccess( person, request , response , loginType);
        }
      }
      if (TeeUtility.isNullorEmpty(loginType)) {
    	  loginType = "0";
      }
      
      //登录后检查今天是否需要提醒短信， syl
      //如果有则提醒，并且修改最后一次提醒时间为今天(周期性事物)
//      	affairService.selectCalAffairRemindByToday(request, response);
    }
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#setUserInfoInSession(com.tianee.oa.core.org.bean.TeePerson, javax.servlet.http.HttpSession, java.lang.String, javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
    @Override
	public void setUserInfoInSession(TeePerson person, HttpSession session, String ip, HttpServletRequest request ,String sessionToken) throws Exception {
//    session.setMaxInactiveInterval(-1);//永不过期
      //session.setMaxInactiveInterval(10*60);
      session.setAttribute(TeeConst.LOGIN_USER, person);
      session.setAttribute(TeeConst.SESSIONTOKEN, sessionToken);
      session.setAttribute("LOGIN_IP", ip);
      //session.setAttribute("STYLE_INDEX", getStyleIndex(request));
      String lockSecStr = TeeSysProps.getString("$OFFLINE_TIME_MIN");
      Long lockSec = null;
      try {
        lockSec = Long.valueOf(Integer.parseInt(lockSecStr) * 60 * 1000);
      } catch (Exception e) {
        lockSec = Long.valueOf(0);
      }
      session.setAttribute("OFFLINE_TIME_MIN", lockSec);
    }
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#doLogoutService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
    @Override
	@TeeLoggingAnt(template="退出系统",type="003F")
    public TeeJson doLogoutService(HttpServletRequest request , HttpServletResponse response){
		TeeJson json = new TeeJson();
//		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
//		if(loginUser==null){
//			json.setRtState(true);
//			json.setRtMsg("用户退出成功");
//			return json;
//		}
//		TeeUserOnline userOnline = userOnlineDao.getUseronlineByUserId(loginUser.getUuid());
//		if(userOnline != null){//删除用户在线纪录
//		    onlineService.deleteById(userOnline.getSid());
//		}
//	    TeeRequestInfoContext.getRequestInfo().setUserName(loginUser.getUserName());
//	    TeeRequestInfoContext.getRequestInfo().setUserId(loginUser.getUuid() + "");
		request.getSession().invalidate();
		json.setRtState(true);
		json.setRtMsg("用户退出成功");
		return json;
    }
    
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#getPostSelectChildMenuInfoPerson(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getPostSelectChildMenuInfoPerson(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeSysMenu>  list = new ArrayList<TeeSysMenu>(); 
		person = personDao.selectPersonById(person.getUuid());//重新从数据库取依稀
		List<TeeMenuGroup> groupList = person.getMenuGroups();
		String groupIds = "";
		for (int i = 0; i < groupList.size(); i++) {
			TeeMenuGroup group = groupList.get(i);
			groupIds = groupIds  + group.getUuid() + ",";
		}
		if(TeeUtility.isNullorEmpty(groupIds)){
			json.setRtState(true);
			json.setRtData("");
			return json;
		}
		/*获取系统登录人 有权限的所有叶子菜单 （URL 不为空）*/
	   list = menuDao.getChildSysMenuListByMenuGroupUuids(groupIds);
	   
	   //个人选中的菜单信息
	   String menuParamInfo = TeeStringUtil.getString(person.getMenuParamSet());//[{data:"25,27,28,33,36,43,46,13,98,52,53,54,105"},{data:"9,23,26,104,17"},{data:""}]
	   List<Map<String, String>> paramSetList  = new ArrayList<Map<String, String>>();
	   if(!TeeUtility.isNullorEmpty(menuParamInfo)){
		   paramSetList =  TeeJsonUtil.JsonStr2MapList(menuParamInfo);
	   }
	   //存放有权限的菜单
	   List<List<String>> useMenu = new ArrayList<List<String>>();
	  
	 //存放有权限的菜单 ----最后有权限的
	   List<List<TeeSysMenuModel>> usePostMenu = new ArrayList<List<TeeSysMenuModel>>();

	   //循环
	   for (int i = 0; i < paramSetList.size() ; i++) {
		   Map<String, String> menuParam =  paramSetList.get(i);
		   List<String> tempSet = new ArrayList<String>();
		   
		   List<TeeSysMenuModel> tempPostSet = new ArrayList<TeeSysMenuModel>();
		   String menuParamDesc = TeeStringUtil.getString(menuParam.get("data"));//{data:'1,34'}
		   if(!TeeUtility.isNullorEmpty(menuParamDesc)){
			   String[] temp = menuParamDesc.split(",");
			   for (int j = 0; j < temp.length; j++) {
				   if(!TeeUtility.isNullorEmpty(temp[j].equals(""))){
					 //循环所有菜单
					   for (int m = 0; m < list.size(); m++) {
						   TeeSysMenu sysMenu = list.get(m);
						   TeeSysMenuModel SysMenuModul  = new TeeSysMenuModel();
						   BeanUtils.copyProperties(sysMenu, SysMenuModul);
						   if(temp[j].equals(sysMenu.getUuid() + "")){//包含
							   tempPostSet.add(SysMenuModul);
							   break;
						   }else{
							   
						   }
					   }
					   /*tempSet.add(temp[j]);*/
				   }
			   }
		   }
		   useMenu.add(tempSet);
		   usePostMenu.add(tempPostSet);
	   }
		

	   Map map = new HashMap();
	   map.put("selectSysMenuModelList", usePostMenu);
	   json.setRtData(map);
	   json.setRtState(true);
	   return json;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#getPostChildMenuInfoByPerson(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getPostChildMenuInfoByPerson(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeSysMenu>  list = new ArrayList<TeeSysMenu>(); 
		person = personDao.selectPersonById(person.getUuid());//重新从数据库取依稀
		List<TeeMenuGroup> groupList = person.getMenuGroups();
		String groupIds = "";
		for (int i = 0; i < groupList.size(); i++) {
			TeeMenuGroup group = groupList.get(i);
			groupIds = groupIds  + group.getUuid() + ",";
		}
		if(TeeUtility.isNullorEmpty(groupIds)){
			json.setRtState(true);
			json.setRtData("");
			return json;
		}
		/*获取系统登录人 有权限的所有叶子菜单 （URL 不为空）*/
	   list = menuDao.getChildSysMenuListByMenuGroupUuids(groupIds);
	   
	   //个人选中的菜单信息
	   String menuParamInfo = TeeStringUtil.getString(person.getMenuParamSet());
	   List<Map<String, String>> paramSetList  = new ArrayList<Map<String, String>>();
	   if(!TeeUtility.isNullorEmpty(menuParamInfo)){
		  /* JSONArray jsonArray = JSONArray.fromObject(menuParamInfo);
		   
			List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
	    	Iterator iterator = jsonArray.iterator();
	    	JSONObject obj =null;
	    	while(iterator.hasNext()){
	    		obj = (JSONObject) iterator.next();
	    		mapList.add(JsonStr2Map(obj.toString()));
	    	}*/
		   paramSetList =  TeeJsonUtil.JsonStr2MapList(menuParamInfo);
	   }
	   
	   //存放有权限的菜单
	   List<Set<String>> useMenu = new ArrayList<Set<String>>();
	  
	 //存放有权限的菜单 ----最后有权限的
	   List<List<TeeSysMenuModel>> usePostMenu = new ArrayList<List<TeeSysMenuModel>>();
	   List<TeeSysMenuModel> notSelectSysMenuModelList = new ArrayList<TeeSysMenuModel>();//个人未选中的
	// List<TeeSysMenuModul> selectSysMenuModelList = new ArrayList<TeeSysMenuModul>();//个人选中的

	   //循环
	   for (int i = 0; i < paramSetList.size() ; i++) {
		   Map<String, String> menuParam =  paramSetList.get(i);
		   Set<String> tempSet = new HashSet<String>();
		   
		   List<TeeSysMenuModel> tempPostSet = new ArrayList<TeeSysMenuModel>();
		   String menuParamDesc = TeeStringUtil.getString(menuParam.get("data"));//{data:'1,34'}
		   if(!TeeUtility.isNullorEmpty(menuParamDesc)){
			   String[] temp = menuParamDesc.split(",");
			   for (int j = 0; j < temp.length; j++) {
				   if(!TeeUtility.isNullorEmpty(temp[j].equals(""))){
					   tempSet.add(temp[j]);
				   }
			   }
		   }
		   useMenu.add(tempSet);
		   usePostMenu.add(tempPostSet);
	   }
		

	   /*第一级菜单*/
	   Set<String> parentMenuId = new HashSet<String>();
	   //循环所有菜单
	   for (int i = 0; i < list.size(); i++) {
		   TeeSysMenu sysMenu = list.get(i);
		   TeeSysMenuModel SysMenuModul  = new TeeSysMenuModel();
		   String menuId = sysMenu.getMenuId();
		   if(!TeeUtility.isNullorEmpty(menuId)){
			   parentMenuId.add(menuId.substring(0, 3));//截取第一级菜单编号
		   }
		   BeanUtils.copyProperties(sysMenu, SysMenuModul);
		   boolean isTrue = false;
		   //循环校验是否选中
		   for (int j = 0; j < useMenu.size(); j++) {
			   Set<String> tempSet = useMenu.get(j);
			   //获取实际存放的菜单
			   List<TeeSysMenuModel> tempPostSet = usePostMenu.get(j);
			   if(tempSet.contains(sysMenu.getUuid() + "")){//包含
				   tempPostSet.add(SysMenuModul);
				   isTrue = true;
				   break;
			   }else{
				   
			   }
		   }
		   //没包含
		   if(!isTrue){
			   notSelectSysMenuModelList.add(SysMenuModul);
		   }
	   }
	   
	  //获取第一级菜单
	   /*第一级菜单*/

	   List<TeeSysMenuModel>  parentMenuList = new ArrayList<TeeSysMenuModel>();
	   
	   List<TeeSysMenu> parentMenu = menuDao.selectParentMenu();
	   for (int i = 0; i < parentMenu.size(); i++) {
		   if(parentMenuId.contains(parentMenu.get(i).getMenuId())){
			   TeeSysMenuModel SysMenuModul  = new TeeSysMenuModel();
			   BeanUtils.copyProperties(parentMenu.get(i), SysMenuModul);
			   parentMenuList.add(SysMenuModul);
		   }
		   
		   
	   }
	   Map map = new HashMap();
	   map.put("selectSysMenuModelList", usePostMenu);
	   map.put("notSelectSysMenuModelList", notSelectSysMenuModelList);
	   map.put("parentMenuList", parentMenuList);
	   
	   json.setRtData(map);
	   json.setRtState(true);
	   return json;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#getModelHandCount(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getModelHandCount(HttpServletRequest request) throws ParseException{
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		if(person == null){
			return json;
		}
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

		String stratTime = TeeUtility.getCurDateTimeStr("yyyy-MM-dd");
		long calendarCount = 0;//calendarAffairDao.selectCountByTime(person, stratTime, stratTime);//日程
		long emailCount = 0;//emailService.getNotReadingCountService(request);
		
		long smsCount = smsDao.getReceiveNoReadCount(person);
		long messageCount = 0;//messageDao.getComfireNoCount(person.getUserId());
		
		long workFlow = 0;
		Map map = new HashMap();
		map.put("calendarCount", calendarCount);//日程安排
		map.put("emailCount", emailCount);//邮件
		map.put("smsCount", smsCount);//短信
		map.put("workFlow", workFlow);//工作流；---
		map.put("messageCount", messageCount);//消息；---
		
		//考勤
		int dutyType = TeeStringUtil.getInteger(person.getDutyType() , 0);
		String dutyStartTime = "";//开始时间
		String dutyEndTime = "";//结束时间
		if(dutyType > 0){
			TeeAttendConfig config = attendConfigDao.get(dutyType);
			if(config != null){
				Calendar dutyTime1 = config.getDutyTime1();
				Calendar dutyTime2 = config.getDutyTime2();
				Calendar dutyTime3 = config.getDutyTime3();
				Calendar dutyTime4 = config.getDutyTime4();
				Calendar dutyTime5 = config.getDutyTime5();
				Calendar dutyTime6 = config.getDutyTime6();
				Calendar onDutys[] = new Calendar[]{dutyTime1,dutyTime3,dutyTime5};
				Calendar offDutys[] = new Calendar[]{dutyTime2,dutyTime4,dutyTime6};
				for(Calendar c:onDutys){
					if(c!=null){
						dutyStartTime = format.format(c.getTime());
						break;
					}
				}
				for(int i=offDutys.length-1;i>=0;i--){
					if(offDutys[i]!=null){
						dutyEndTime = format.format(offDutys[i].getTime());
						break;
					}
				}
			}
		}
		Map attaendInfo = new HashMap();
		attaendInfo.put("dutyStartTime" , dutyStartTime);
		attaendInfo.put("dutyEndTime" , dutyEndTime);
		map.put("attendDutyInfo", attaendInfo);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#refreshCache(int)
	 */
	@Override
	public void refreshCache(int cacheFlag){
		if(cacheFlag==1){//界面缓存
			TeeCacheManager.refreshThemeCache();
		}else if(cacheFlag==2){//组织机构缓存
			TeeCacheManager.refreshRedisOrg();
		}
	}
	

	public static String parseString(String s) {
		if (s == null) {
			return "";
		} else {
			return s.trim();
		}
	}

	
	public static String parseString(String s, String defaultValue) {
		if (s == null || s.trim().equals("")) {
			return defaultValue;
		} else {
			return s.trim();
		}
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#updateUnitName(java.lang.String)
	 */
	@Override
	public void updateUnitName(String unitName) {
		simpleDaoSupport.executeUpdate("update TeeOrganization set unitName=?", new Object[]{unitName});
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#setPersonDao(com.tianee.oa.core.org.dao.TeePersonDao)
	 */
	@Override
	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#setSysParaDao(com.tianee.oa.core.general.dao.TeeSysParaDao)
	 */
	@Override
	public void setSysParaDao(TeeSysParaDao sysParaDao) {
		this.sysParaDao = sysParaDao;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#setInterfaceDao(com.tianee.oa.core.general.dao.TeeInterfaceDao)
	 */
	@Override
	public void setInterfaceDao(TeeInterfaceDao interfaceDao) {
		this.interfaceDao = interfaceDao;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#setIpRuleValidator(com.tianee.oa.core.system.filters.TeeIpRuleValidator)
	 */
	@Override
	public void setIpRuleValidator(TeeIpRuleValidator ipRuleValidator) {
		this.ipRuleValidator = ipRuleValidator;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#setOnlineService(com.tianee.oa.core.org.service.TeeUserOnlineService)
	 */
	@Override
	public void setOnlineService(TeeUserOnlineService onlineService) {
		this.onlineService = onlineService;
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.system.service.TeeSystemServiceItf#doLoginInByUserIdService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@TeeLoggingAnt(template="登录系统",type="003E")
    public TeeJson doLoginInByUserIdService(HttpServletRequest request , HttpServletResponse response){
		TeeJson json = new TeeJson();
		Map map = new HashMap();
	    try {
		   String url = new String(request.getRequestURL());
		   Map<String,String> sysParamap = sysParaDao.getParaListToLogin();
		   String keyUser = TeeUtility.null2Empty(request.getParameter("KEY_USER"));
		   String caUser = TeeUtility.null2Empty(request.getParameter("CA_USER"));
		   //高速波云OA 账号名称 和MD5密码
		   String cloudUserName = TeeUtility.null2Empty(request.getParameter("cloudUserName"));
		   String cloudPwd = TeeUtility.null2Empty(request.getParameter("cloudPwd"));
		   
		   String userName = request.getParameter("userName");
		   String pwd = request.getParameter("pwd");
		   if (TeeUtility.isNullorEmpty(userName) && !TeeUtility.isNullorEmpty(keyUser)) {
		       userName = keyUser;
		   }
		   if(!TeeUtility.isNullorEmpty(cloudUserName)){//高速波云平台登录
			   userName = cloudUserName;
			   pwd = cloudPwd;
		   }
	      TeePerson person = null;
	      if (TeeUtility.isNullorEmpty(userName)) {
	          userName = "";
	      }else {
	    	  
	    	   if(!TeeUtility.isNullorEmpty(cloudUserName)){
	    		   person = personDao.getPersonByCloudUserId(userName);
	    	   }else{
	    		   person = personDao.getPersonByUserId(userName);
	    	   }
	    	   if(person != null){
		    	  // person.getMenuGroups().size();
	    		   person.getPostDept().size();
	    		   if(person.getDept() != null){
	    			   person.getDept().getDeptName();
	    		   }
	    		   if(person.getUserRole()!=null){
	    			   person.getUserRole().getRoleName();
	    		   }
	    		   person.getUserRoleOther().size();
	    	   }
	      }
	   
	      
	      //登录验证
	      TeeLoginAdapter loginAdapter = new TeeLoginAdapter(request, person,json , sysParamap);
	      //验证软件是否过期

	      if(!loginAdapter.validate(new TeeRegistAuthValidator())){
	    	  return json;
	      }
	      
	      //验证用户是否存在
	      if (!loginAdapter.validate(new TeeExistUserValidator())){
	    	  return json;
	      }
//	      TeeRequestInfoContext.getRequestInfo().setUserName(person.getUserName());
//	      TeeRequestInfoContext.getRequestInfo().setUserId(person.getUuid() + "");
	      //验证用户是否禁止登陆
	      if (!loginAdapter.validate(new TeeForbidLoginValidator())){
	          return  json;
	      }
	      
	      //验证Ip规则
	      if (!loginAdapter.validate( ipRuleValidator)){//注入方式
		        return json;
		  }
	  
	      
	      //验证用户绑定Ip
	      if (!loginAdapter.validate(new TeeBindIpValidator())){
	        return json;
	      }
	      
	      //验证用户绑定Mac地址
	      if (!loginAdapter.validate(new TeeBindMacValidator())){
	    	  return json;
	      }
	

	      //验证是否设置角色
	      if (!loginAdapter.validate(new TeeSetUserRoleValidator())){
		        return json;
		  }
	      
	      //校验登录验证码
//	      if(!loginAdapter.validate(new TeeVerificationCodeValidator())){
//	    	  return json;
//	      }

	      //验证Ukey
//	      if(!loginAdapter.validate(new TeeUkeyValidator())){
//	    	  return json;
//	      }
	      
	     /* //验证用户是否重复登陆
	      if (!loginAdapter.validate(new TeeRepeatLoginValidator())){
	        return "/core/inc/rtjson.jsp";
	      }
	      end = System.currentTimeMillis();
	   
	      if("1".equals(useingKey.trim()) && "1".equals(map.get("LOGIN_KEY"))){
	        
	        //当用户使用Usbkey登陆时,进行usbkey验证
	        if (!loginAdapter.validate(new TeeUsbkeyValidator())){
	          return "/core/inc/rtjson.jsp";
	        }
	      } else if(caUser != null && "1".equals(caUser.trim())){
	        //如果是ca用户则放行，默认其合法
	      }
	      else {
	        start = System.currentTimeMillis();
	        if (!loginAdapter.validate(new TeeVerificationCodeValidator())){
	          return "/core/inc/rtjson.jsp";
	        }
	        end = System.currentTimeMillis();
	        start = System.currentTimeMillis();**/
	      
	      //系统登录错误次数校验
	      	Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组 
	      	/*for (int i = 0; i < cookies.length; i++) {
				System.out.println(cookies[i].getName() +":"+ cookies[i].getValue());
			}*/
//	        if (!loginAdapter.validate(new TeePwdErrorValidator())){  
//	        	int SEC_BAN_TIME = TeeStringUtil.getInteger((String)sysParamap.get("SEC_PASS_TIME") , 0);//有效时间
//	        	addCookie(cookies, response, SEC_BAN_TIME, person);
//	        	return  json;
//	        }
	    	
//	       if(!TeeUtility.isNullorEmpty(cloudUserName)){//高速波云平台登录
//	    	   if (!loginAdapter.validate(new TeeOtherPasswordValidator(pwd , "1"))){
//	 	        	int SEC_BAN_TIME = TeeStringUtil.getInteger((String)sysParamap.get("SEC_PASS_TIME") , 0);//有效时间
//	 	        	addCookie(cookies, response, SEC_BAN_TIME, person);
//	 	        	return json;
//	 	        }
//	        }else{
//	        	if (!loginAdapter.validate(new TeePasswordValidator(pwd))){
//	 	        	int SEC_BAN_TIME = TeeStringUtil.getInteger((String)sysParamap.get("SEC_PASS_TIME") , 0);//有效时间
//	 	        	addCookie(cookies, response, SEC_BAN_TIME, person);
//	 	        	return json;
//	 	        }
//	        }
	       
	     /*}
	      */
	      
	      String loginType = TeeStringUtil.getString(request.getParameter("CLIENT"), "1");
	      
	      if("2".equals(loginType)){//PC终端通讯登陆人数判断
		      if (!loginAdapter.validate(new TeePcValidator())){
		    	  return json;
		      }
	      }
	      
	    //调用登陆成功的处理
	      this.loginSuccess( person, request  ,  response,loginType);
	
	  /*    try {
	        start = System.currentTimeMillis();
	        Cookie cookie = reportSSO(person, pwd, request);
	        end = System.currentTimeMillis();
	        if (cookie != null) { 
	          start = System.currentTimeMillis();
	          response.addCookie(cookie);
	          end = System.currentTimeMillis();
	        }
	        
	    
	      }catch(Exception ex) {
	      }*/
	      
	      //设置系统参数存在在session中
	      setSysParaSession(sysParamap, request.getSession());
	      
	      deleteCookie(cookies, response, person.getUuid());//销毁cookie
	      
	      //验证时候需要修改初始密码
//	      if (!loginAdapter.validate(new TeeInitialPwdValidator())){
//	        return json;
//	      }
	      //验证密码是否过期,是否需要修改
//	      if (!loginAdapter.validate(new TeePwdExpiredValidator())){
//	        return json;
//	      }
	      
//	      List<TeeInterface> list = interfaceDao.select();
//	      if(list.size() > 0){
//	    	  TeeInterface inte = list.get(0);
//	    	  setIntefaceSession(inte, request.getSession()) ;
//	      }
	      
	      TeeInterface itfc = (TeeInterface) simpleDaoSupport.get(TeeInterface.class,1);
          String title =  itfc.getIeTitle();
          map.put("imPic",TeeStringUtil.getString(itfc.getImPic()));
          map.put("title",title);
	      map.put("sid", person.getUuid());
	      map.put("userName", person.getUserName());
	      map.put("userId", person.getUserId());
	      map.put("JSESSIONID", request.getSession().getId());
	      map.put("PATH", request.getContextPath());
	      map.put("theme", person.getTheme());
	      map.put("autoPopSms", person.getAutoPopSms());
	      map.put("photoId", TeeStringUtil.getString(person.getAvatar()));//是附件Id （如：190）
	      map.put("userOnlineStatus", "1");//人员在线状态 空和0 -不在线，1- 在线 2-忙碌 3-离开,刚登录为在线
	      map.put("socketPort", TeeSysProps.getString("SOCKET_PORT"));
	      map.put("currVersion", TeeSysProps.getString("PC_CURR_VERSION"));
	      String deptName = "";
	      String userRoleName = "";
	      if( person.getDept() != null){
	    	  deptName =  person.getDept().getDeptName();
	      }
	      if( person.getUserRole() != null){
	    	  userRoleName = person.getUserRole().getRoleName();
	      }
	      map.put("deptName",deptName );
	      map.put("roleName", userRoleName);
	      
	      //处理登录样式
	      int STYLE_TYPE_INDEX_2 = 1;//第二套风格
	      String theme = TeeStringUtil.getString(person.getTheme());
	      int themeIndex = 1;
		  if(theme.length() == 2){
		      //themeIndex = theme.subString(0,1);
			  STYLE_TYPE_INDEX_2 = TeeStringUtil.getInteger(theme.substring(1,2) ,1);
		  }
		  request.getSession().setAttribute("STYLE_TYPE_INDEX_2", STYLE_TYPE_INDEX_2);
	      
	    } catch (Exception ex) {
	        String retData = "{\"code\":9999,\"msg\":\""+ex.getMessage()+"\"}";
	        ex.printStackTrace();
	        TeeJsonUtil jsonUtil = new TeeJsonUtil();
	        JSONObject jsonObj = jsonUtil.jsonString2Json(retData);
	    	json.setRtMsg(ex.getMessage());
	    	json.setRtState(false);
	    	json.setRtData(jsonObj);
	    	return json;
	    	//System.out.println(ex.getMessage());
	    }
     	json.setRtState(true);
     	json.setRtData(map);
     	json.setRtMsg("登录成功");
	    return json ;

    }

	
	/**
	 * 单点登录
	 */
	@Override
	public TeeJson doSSOLoginIn(HttpServletRequest request,
			HttpServletResponse response) {
		TeeJson json=new TeeJson();
		
		//获取传来的url
		String url=TeePassBase64.decodeStr(request.getParameter("url"));
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

}
