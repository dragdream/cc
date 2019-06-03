package com.tianee.oa.core.general.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeSysParaService extends TeeBaseService{
    @Autowired
    TeeSysParaDao sysParaDao;
    
    @Autowired
    TeePersonDao personDao;
    
    /**
     * 新增或者更新
     * @param para
     * @return
     */
    public void addUpdatePara(TeeSysPara para){
    	TeeSysPara sysPara = sysParaDao.getSysPara(para.getParaName());
    	if(sysPara == null){
    		sysParaDao.addSysPara(para);
    	}else{
    		sysPara.setParaValue(para.getParaValue());
    		sysParaDao.update(sysPara);
    	}
    	TeeSysProps.getProps().setProperty(para.getParaName(), para.getParaValue());
    }   
    /**
     * 根据参数名称获取参数值
     * @param paraName
     * @return
     */
    public String getSysParaValue(String paraName){
    	return sysParaDao.getSysParaValue(paraName);
    }
    /**
     * 根据参数名称获取对象
     * @param paraName
     * @return
     */
    public TeeSysPara getSysPara(String paraName){
    	return sysParaDao.getSysPara(paraName);
    }
    
    
    /**
     * 批量保存
     * @param para
     * @return
     */
    public void addOrUpdateSysParaList(HttpServletRequest  request){
    	
    	//初始化登录修改密码
    	String SEC_INIT_PASS = request.getParameter("SEC_INIT_PASS");
    	if(SEC_INIT_PASS != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SEC_INIT_PASS");
    		sysPara.setParaValue(SEC_INIT_PASS);
    		addUpdatePara(sysPara);
    	}
    	
    	//密码是否过期及有效期
    	String SEC_PASS_FLAG = request.getParameter("SEC_PASS_FLAG");
    	String SEC_PASS_TIME = request.getParameter("SEC_PASS_TIME");
    	if(SEC_PASS_FLAG != null && SEC_PASS_TIME != null ){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SEC_PASS_FLAG");
    		sysPara.setParaValue(SEC_PASS_FLAG);
    		addUpdatePara(sysPara);
    		
    		
    		TeeSysPara sysPara2 = new TeeSysPara();//有效期
    		sysPara2.setParaName("SEC_PASS_TIME");
    		sysPara2.setParaValue(SEC_PASS_TIME);
    		addUpdatePara(sysPara2); 
    	}
    	
    	
    	//密码强度
    	String SEC_PASS_MIN = request.getParameter("SEC_PASS_MIN");//最小长度
    	String SEC_PASS_MAX = request.getParameter("SEC_PASS_MAX");//最大长度
    	
    	String SEC_PASS_SAFE = request.getParameter("SEC_PASS_SAFE");//密码必须同时包含字母和数字
    	String SEC_PASS_SAFE_SC = request.getParameter("SEC_PASS_SAFE_SC");//密码必须包含特殊字符
    	if(SEC_PASS_MIN != null && SEC_PASS_MAX != null 
    			&& SEC_PASS_SAFE != null && SEC_PASS_SAFE_SC != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SEC_PASS_MIN");
    		sysPara.setParaValue(SEC_PASS_MIN);
    		addUpdatePara(sysPara);
    		
    		
    		TeeSysPara sysPara2 = new TeeSysPara();//
    		sysPara2.setParaName("SEC_PASS_MAX");
    		sysPara2.setParaValue(SEC_PASS_MAX);
    		addUpdatePara(sysPara2); 
    		
    		
    		TeeSysPara sysPara3 = new TeeSysPara();
    		sysPara3.setParaName("SEC_PASS_SAFE");
    		sysPara3.setParaValue(SEC_PASS_SAFE);
    		addUpdatePara(sysPara3);
    		
    		
    		TeeSysPara sysPara4 = new TeeSysPara();//
    		sysPara4.setParaName("SEC_PASS_SAFE_SC");
    		sysPara4.setParaValue(SEC_PASS_SAFE_SC);
    		addUpdatePara(sysPara4); 
    	}
    	
    	
    	//登录次数
    	String SEC_RETRY_BAN = request.getParameter("SEC_RETRY_BAN");//是否
    	String SEC_RETRY_TIMES = request.getParameter("SEC_RETRY_TIMES");//多少次
    	
    	String SEC_BAN_TIME = request.getParameter("SEC_BAN_TIME");//多少分钟
    	if(SEC_RETRY_BAN != null && SEC_RETRY_TIMES != null 
    			&& SEC_BAN_TIME != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SEC_RETRY_BAN");
    		sysPara.setParaValue(SEC_RETRY_BAN);
    		addUpdatePara(sysPara);
    		
    		
    		TeeSysPara sysPara2 = new TeeSysPara();//
    		sysPara2.setParaName("SEC_RETRY_TIMES");
    		sysPara2.setParaValue(SEC_RETRY_TIMES);
    		addUpdatePara(sysPara2); 
    		
    		
    		TeeSysPara sysPara3 = new TeeSysPara();
    		sysPara3.setParaName("SEC_BAN_TIME");
    		sysPara3.setParaValue(SEC_BAN_TIME);
    		addUpdatePara(sysPara3);
   
    	}
    	
    	//允许登录时记忆用户名
    	String SEC_USER_MEM = request.getParameter("SEC_USER_MEM");//允许登录时记忆用户名
    	if(SEC_USER_MEM != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SEC_USER_MEM");
    		sysPara.setParaValue(SEC_USER_MEM);
    		addUpdatePara(sysPara);
    	}
    	
    	//登是否校验验证码
    	String VERIFICATION_CODE = request.getParameter("VERIFICATION_CODE");//允许登录时记忆用户名
    	if(VERIFICATION_CODE != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("VERIFICATION_CODE");
    		sysPara.setParaValue(VERIFICATION_CODE);
    		addUpdatePara(sysPara);
    	}
    	
    	//用户是否显示IP
    	String SEC_SHOW_IP = request.getParameter("SEC_SHOW_IP");//允许登录时记忆用户名
    	if(SEC_SHOW_IP != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SEC_SHOW_IP");
    		sysPara.setParaValue(SEC_SHOW_IP);
    		addUpdatePara(sysPara);
    	}
    	//记忆在线状态
    	String SEC_ON_STATUS = request.getParameter("SEC_ON_STATUS");//允许登录时记忆用户名
    	if(SEC_ON_STATUS != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SEC_ON_STATUS");
    		sysPara.setParaValue(SEC_ON_STATUS);
    		addUpdatePara(sysPara);
    	}
     	//是否开启文件加密
    	String ATTACH_ENCRY = request.getParameter("ATTACH_ENCRY");
    	if(ATTACH_ENCRY != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("ATTACH_ENCRY");
    		sysPara.setParaValue(ATTACH_ENCRY);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("ATTACH_ENCRY", ATTACH_ENCRY);
    	}
     	//需要加密的模块
    	String ENCRY_MODULES = request.getParameter("ENCRY_MODULES");
    	if(ENCRY_MODULES != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("ENCRY_MODULES");
    		if(ENCRY_MODULES.endsWith(",")){
    			ENCRY_MODULES = ENCRY_MODULES.substring(0,ENCRY_MODULES.length()-1);
    		}
    		sysPara.setParaValue(ENCRY_MODULES);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("ENCRY_MODULES", ENCRY_MODULES);
    	}
     	//文件类型限制
    	String ENCRY_FILES = request.getParameter("ENCRY_FILES");
    	if(ENCRY_FILES != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("ENCRY_FILES");
    		sysPara.setParaValue(ENCRY_FILES);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("ENCRY_FILES", ENCRY_FILES);
    	}
     	//文件加密方式
    	String ENCRY_TYPE = request.getParameter("ENCRY_TYPE");
    	if(ENCRY_TYPE != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("ENCRY_TYPE");
    		sysPara.setParaValue(ENCRY_TYPE);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("ENCRY_TYPE", ENCRY_TYPE);
    	}
     	
     	//文件大小限制
    	String ENCRY_SIZE = request.getParameter("ENCRY_SIZE");
    	if(ENCRY_SIZE != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("ENCRY_SIZE");
    		sysPara.setParaValue(ENCRY_SIZE);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("ENCRY_SIZE", ENCRY_SIZE);
    	}
    	//加密算法
    	String ENCRY_ALGO = request.getParameter("ENCRY_ALGO");
    	if(ENCRY_ALGO != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("ENCRY_ALGO");
    		sysPara.setParaValue(ENCRY_ALGO);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("ENCRY_ALGO", ENCRY_ALGO);
    	}
    	
    	//验证码登录
    	String LOGIN_BY_CODE = request.getParameter("LOGIN_BY_CODE");
    	if(LOGIN_BY_CODE != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("LOGIN_BY_CODE");
    		sysPara.setParaValue(LOGIN_BY_CODE);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("LOGIN_BY_CODE", LOGIN_BY_CODE);
    	}
    	
    	//USBkey登录
    	String USB_KEY = request.getParameter("USB_KEY");
    	if(USB_KEY != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("USB_KEY");
    		sysPara.setParaValue(USB_KEY);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("USB_KEY", USB_KEY);
    	}
    	
    	//移动端传输加密
    	String MOBILE_LOGIN_ENCODE = request.getParameter("MOBILE_LOGIN_ENCODE");
    	if(MOBILE_LOGIN_ENCODE != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("MOBILE_LOGIN_ENCODE");
    		sysPara.setParaValue(MOBILE_LOGIN_ENCODE);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("MOBILE_LOGIN_ENCODE", MOBILE_LOGIN_ENCODE);
    	}
    	/*Enumeration enu =  request.getParameterNames(); 
    	Map map =  request.getParameterMap(); 
    	
    	  Iterator it = map.entrySet().iterator();  

    	 while (it.hasNext()) {
    		Map.Entry mapObj = (Map.Entry)it.next();
    		Object key = mapObj.getKey();
			Object[] value = (Object[])mapObj.getValue();
			System.out.println("name:" + key + ";value:" + value[0].toString());
		}*/
  
    	
    	
    	//手机短信超时次数
    	String SMS_PHONE_SEND_NUMBER = request.getParameter("SMS_PHONE_SEND_NUMBER");
    	if(SMS_PHONE_SEND_NUMBER != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SMS_PHONE_SEND_NUMBER");
    		sysPara.setParaValue(SMS_PHONE_SEND_NUMBER);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("SMS_PHONE_SEND_NUMBER", SMS_PHONE_SEND_NUMBER);
    	}
    	
    	//手机短信发送超时时间
    	String SMS_PHONE_SEND_TIME_OUT = request.getParameter("SMS_PHONE_SEND_TIME_OUT");
    	if(SMS_PHONE_SEND_TIME_OUT != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SMS_PHONE_SEND_TIME_OUT");
    		sysPara.setParaValue(SMS_PHONE_SEND_TIME_OUT);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("SMS_PHONE_SEND_TIME_OUT", SMS_PHONE_SEND_TIME_OUT);
    	}
    	
    	//手机短信任务线程数量
    	String SMS_PHONE_THREAD_NUMBER = request.getParameter("SMS_PHONE_THREAD_NUMBER");
    	if(SMS_PHONE_THREAD_NUMBER != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SMS_PHONE_THREAD_NUMBER");
    		sysPara.setParaValue(SMS_PHONE_THREAD_NUMBER);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("SMS_PHONE_THREAD_NUMBER", SMS_PHONE_THREAD_NUMBER);
    	}
    	
    	
    	
    	//外部邮箱超时次数
    	String WEB_MAIL_SEND_NUMBER = request.getParameter("WEB_MAIL_SEND_NUMBER");
    	if(WEB_MAIL_SEND_NUMBER != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEB_MAIL_SEND_NUMBER");
    		sysPara.setParaValue(WEB_MAIL_SEND_NUMBER);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEB_MAIL_SEND_NUMBER", WEB_MAIL_SEND_NUMBER);
    	}
    	
    	//外部邮箱任务线程数量
    	String WEB_MAIL_SEND_THREAD_NUMBER = request.getParameter("WEB_MAIL_SEND_THREAD_NUMBER");
    	if(WEB_MAIL_SEND_THREAD_NUMBER != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEB_MAIL_SEND_THREAD_NUMBER");
    		sysPara.setParaValue(WEB_MAIL_SEND_THREAD_NUMBER);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEB_MAIL_SEND_THREAD_NUMBER", WEB_MAIL_SEND_THREAD_NUMBER);
    	}
    	
    	
    	//微信推送线程数
    	String WEI_XIN_THREAD_NUMBER = request.getParameter("WEI_XIN_THREAD_NUMBER");
    	if(WEI_XIN_THREAD_NUMBER != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEI_XIN_THREAD_NUMBER");
    		sysPara.setParaValue(WEI_XIN_THREAD_NUMBER);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEI_XIN_THREAD_NUMBER", WEI_XIN_THREAD_NUMBER);
    	}
    	
    	//微信超时时间
    	String WEI_XIN_TIME_OUT = request.getParameter("WEI_XIN_TIME_OUT");
    	if(WEI_XIN_TIME_OUT != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEI_XIN_TIME_OUT");
    		sysPara.setParaValue(WEI_XIN_TIME_OUT);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEI_XIN_TIME_OUT", WEI_XIN_TIME_OUT);
    	}
    	
    	
    	//钉钉推送线程数
    	String DING_DING_THREAD_NUMBER = request.getParameter("DING_DING_THREAD_NUMBER");
    	if(DING_DING_THREAD_NUMBER != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("DING_DING_THREAD_NUMBER");
    		sysPara.setParaValue(DING_DING_THREAD_NUMBER);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("DING_DING_THREAD_NUMBER", DING_DING_THREAD_NUMBER);
    	}
    	
    	//钉钉超时时间
    	String DING_DING_TIME_OUT = request.getParameter("DING_DING_TIME_OUT");
    	if(DING_DING_TIME_OUT != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("DING_DING_TIME_OUT");
    		sysPara.setParaValue(DING_DING_TIME_OUT);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("DING_DING_TIME_OUT", DING_DING_TIME_OUT);
    	}
    	
    	
    	//外部邮箱线程数
    	String WEB_MAIL_THREAD_NUMBER = request.getParameter("WEB_MAIL_THREAD_NUMBER");
    	if(WEB_MAIL_THREAD_NUMBER != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEB_MAIL_THREAD_NUMBER");
    		sysPara.setParaValue(WEB_MAIL_THREAD_NUMBER);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEB_MAIL_THREAD_NUMBER", WEB_MAIL_THREAD_NUMBER);
    	}
    	
    	//外部邮箱超时时间（秒）	
    	String WEB_MAIL_TIME_OUT = request.getParameter("WEB_MAIL_TIME_OUT");
    	if(WEB_MAIL_TIME_OUT != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEB_MAIL_TIME_OUT");
    		sysPara.setParaValue(WEB_MAIL_TIME_OUT);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEB_MAIL_TIME_OUT", WEB_MAIL_TIME_OUT);
    	}
    	
    	
    	
    	//外部邮件接收线程池大小
    	String WEB_MAIL_REC_POOL_SIZE = request.getParameter("WEB_MAIL_REC_POOL_SIZE");
    	if(WEB_MAIL_REC_POOL_SIZE != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEB_MAIL_REC_POOL_SIZE");
    		sysPara.setParaValue(WEB_MAIL_REC_POOL_SIZE);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEB_MAIL_REC_POOL_SIZE", WEB_MAIL_REC_POOL_SIZE);
    	}
    	
    	// 外部邮件接收时间间隔  （秒）	
    	String WEB_MAIL_REC_DELAY = request.getParameter("WEB_MAIL_REC_DELAY");
    	if(WEB_MAIL_REC_DELAY != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEB_MAIL_REC_DELAY");
    		sysPara.setParaValue(WEB_MAIL_REC_DELAY);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEB_MAIL_REC_DELAY", WEB_MAIL_REC_DELAY);
    	}
    	
    	//外部邮件接收超时时间 （秒）
    	String WEB_MAIL_REC_TIMEOUT = request.getParameter("WEB_MAIL_REC_TIMEOUT");
    	if(WEB_MAIL_REC_TIMEOUT != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEB_MAIL_REC_TIMEOUT");
    		sysPara.setParaValue(WEB_MAIL_REC_TIMEOUT);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEB_MAIL_REC_TIMEOUT", WEB_MAIL_REC_TIMEOUT);
    	}
    	
    	// 接收近多少天的邮件
    	String WEB_MAIL_REC_DAYS = request.getParameter("WEB_MAIL_REC_DAYS");
    	if(WEB_MAIL_REC_DAYS != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEB_MAIL_REC_DAYS");
    		sysPara.setParaValue(WEB_MAIL_REC_DAYS);
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEB_MAIL_REC_DAYS", WEB_MAIL_REC_DAYS);
    	}
    	
    	//允许接收邮件的大小  （兆）
    	String WEB_MAIL_REC_LIMIT  = request.getParameter("WEB_MAIL_REC_LIMIT");
    	if(WEB_MAIL_REC_LIMIT  != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WEB_MAIL_REC_LIMIT");
    		sysPara.setParaValue(WEB_MAIL_REC_LIMIT );
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WEB_MAIL_REC_LIMIT", WEB_MAIL_REC_LIMIT);
    	}
    	
    	
    	//水印功能
    	String WATER_MARK  = request.getParameter("WATER_MARK");
    	if(WATER_MARK  != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("WATER_MARK");
    		sysPara.setParaValue(WATER_MARK );
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("WATER_MARK", WATER_MARK);
    	}
    	
    	//附件上传类型
    	String GLOBAL_ATTACH_TYPE  = request.getParameter("GLOBAL_ATTACH_TYPE");
    	if(GLOBAL_ATTACH_TYPE!=null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("GLOBAL_ATTACH_TYPE");
    		sysPara.setParaValue(GLOBAL_ATTACH_TYPE );
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("GLOBAL_ATTACH_TYPE", GLOBAL_ATTACH_TYPE);
    	}
    	
    	
    	//名片识别appcode
    	String SCAN_CARD_APP_CODE  = request.getParameter("SCAN_CARD_APP_CODE");
    	if(SCAN_CARD_APP_CODE!=null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("SCAN_CARD_APP_CODE");
    		sysPara.setParaValue(SCAN_CARD_APP_CODE );
    		addUpdatePara(sysPara);
    		TeeSysProps.getProps().setProperty("SCAN_CARD_APP_CODE", SCAN_CARD_APP_CODE);
    	}
    }   
    
    
    
    
    
    
    /**
     * 批量保存 快捷展示区全局设置
     * @param para
     * @return
     */
    public void addOrUpdatePortletSysPara(HttpServletRequest  request){
    	
    	//				 
    	String IS_USER_SETTING_PORTLET_POS = request.getParameter("IS_USER_SETTING_PORTLET_POS");//是否允许用户调整各桌面位置
    	String IS_USER_SETTING_PORTLET_HEIGHT = request.getParameter("IS_USER_SETTING_PORTLET_HEIGHT");//是否允许用户调整栏目高度
    	String IS_USER_SETTING_PORTLET_FOLDED = request.getParameter("IS_USER_SETTING_PORTLET_FOLDED");//是否允许用户展开/折叠桌面模块
    	String PORTLET_COL = request.getParameter("PORTLET_COL");//几栏
    	String PORTLET_COL_TOTAL_WIDTH_VALUE = request.getParameter("PORTLET_COL_TOTAL_WIDTH_VALUE");//每个栏目宽度
    	System.out.println(PORTLET_COL);
    	
    	
    	if(IS_USER_SETTING_PORTLET_POS != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("IS_USER_SETTING_PORTLET_POS");
    		sysPara.setParaValue(IS_USER_SETTING_PORTLET_POS);
    		addUpdatePara(sysPara);
    	}
    	
    	if(IS_USER_SETTING_PORTLET_FOLDED != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("IS_USER_SETTING_PORTLET_FOLDED");
    		sysPara.setParaValue(IS_USER_SETTING_PORTLET_FOLDED);
    		addUpdatePara(sysPara);
    	}
    	
    	if(IS_USER_SETTING_PORTLET_HEIGHT != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("IS_USER_SETTING_PORTLET_HEIGHT");
    		sysPara.setParaValue(IS_USER_SETTING_PORTLET_HEIGHT);
    		addUpdatePara(sysPara);
    	}
    	
    	if(PORTLET_COL != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("PORTLET_COL");
    		sysPara.setParaValue(PORTLET_COL);
    		addUpdatePara(sysPara);
    	}
    	
    	if(PORTLET_COL_TOTAL_WIDTH_VALUE != null){
    		TeeSysPara sysPara = new TeeSysPara();
    		sysPara.setParaName("PORTLET_COL_TOTAL_WIDTH_VALUE");
    		sysPara.setParaValue(PORTLET_COL_TOTAL_WIDTH_VALUE);
    		addUpdatePara(sysPara);
    	}
    }
    /**
     * 获取portlet全局参数
     * @return
     */
    public Map getGlobalPortletSysPara(){
    	int  PORTLET_COL = 3;//总栏数
    	int firstColWidth = 40;//第一栏宽度
    	int secondColWidth = 30;//第二栏宽度
    	int thirdColWidth = 30;//第三栏宽度
    	String IS_USER_SETTING_PORTLET_POS = "1";//是否允许用户调整各桌面位置  1 -为允许
    	String IS_USER_SETTING_PORTLET_HEIGHT = "1";//是否允许用户调整栏目高度 1-为允许
    	String IS_USER_SETTING_PORTLET_FOLDED = "1";//是否允许用户展开/折叠桌面模块 1-为允许
    	Map map = new HashMap();
    	String paraNames = "IS_USER_SETTING_PORTLET_POS,IS_USER_SETTING_PORTLET_HEIGHT,IS_USER_SETTING_PORTLET_FOLDED,"
    			+"PORTLET_COL"
    			+ ",PORTLET_COL_TOTAL_WIDTH_VALUE";
    	 List<TeeSysPara> list=  sysParaDao.getSysParaByNames(paraNames);
    	 for (int i = 0; i < list.size(); i++) {
    		 TeeSysPara data = list.get(i);
    		 if(data.getParaName().equals("PORTLET_COL")){
    			 PORTLET_COL = Integer.parseInt(data.getParaValue());
			 }
			 if(data.getParaName().equals("PORTLET_COL_TOTAL_WIDTH_VALUE")  && !TeeUtility.isNullorEmpty(data.getParaValue())){
				 String[] arrParaValue = data.getParaValue().split("\\|");
				 for(int j=0 ; j < arrParaValue.length ; j++){
					 if(j==0){
						 firstColWidth = Integer.parseInt(arrParaValue[j]);
					 }else if(j == 1){
						 secondColWidth = Integer.parseInt(arrParaValue[j]);
					 }else if(j == 2){
						 thirdColWidth =  Integer.parseInt(arrParaValue[j]);
					 }
				 } 
			 }
			 if(data.getParaName().equals("IS_USER_SETTING_PORTLET_POS")){
				 IS_USER_SETTING_PORTLET_POS = data.getParaValue();
			 }
			 if(data.getParaName().equals("IS_USER_SETTING_PORTLET_HEIGHT")){
				 IS_USER_SETTING_PORTLET_HEIGHT = data.getParaValue();
			 }
			 if(data.getParaName().equals("IS_USER_SETTING_PORTLET_FOLDED")){
				 IS_USER_SETTING_PORTLET_FOLDED = data.getParaValue();
			 }
		}
    	map.put("portletCol", PORTLET_COL);
    	map.put("firstColWidth", firstColWidth);
    	map.put("secondColWidth", secondColWidth);
    	map.put("thirdColWidth", thirdColWidth);
    	
    	map.put("isUserSettingPortletPos", IS_USER_SETTING_PORTLET_POS);
    	map.put("isUserSettingPortletHeight", IS_USER_SETTING_PORTLET_HEIGHT);
    	map.put("isUserSettingPortletFolded", IS_USER_SETTING_PORTLET_FOLDED);
    	return map;
    }

    
    
    
    
    /**
     * 根据参数名称字符串获取对象List
     * @param request
     * @return
     */
    public List<TeeSysPara> getSysParaList(HttpServletRequest request){
    	String paraNames = request.getParameter("paraNames");
    	List<TeeSysPara> list = new ArrayList<TeeSysPara>();
    	if(!TeeUtility.isNullorEmpty(paraNames)){
    		return sysParaDao.getSysParaByNames(paraNames);
    	}
    	return list;
    }
    
    
    /***
	 * 获取系统参数表，系统登录
	 * @return
	 */
	public Map getParaListToLogin(){
		Map<String,String> map = sysParaDao.getParaListToLogin();
		return map;
	}
    
  
    /**
     * auther syl
     * 获取参数是存人员信息（一般处理以人员Id字符串，例如Ip登录规则）
     * @param paraName
     * @return
     */
    public TeeJson getParaIsUser(String paraName){
    	TeeJson json = new TeeJson();
    	TeeSysPara para = sysParaDao.getSysPara(paraName);
    	Map map = new HashMap();
    	if(para != null){
    		map.put("paraName", para.getParaName());
    		map.put("id", para.getUuid());
    		String paraValue = "";
    		String paraValueDesc ="";
    		String paraValueStr = para.getParaValue();
    		if(!TeeUtility.isNullorEmpty(paraValueStr)){
    			String[] personInfo  = personDao.getPersonNameAndUuidByUuids(paraValueStr);
    			paraValue = personInfo[0];
    			paraValueDesc = personInfo[1];
    		}
    		map.put("paraValue", paraValue);
    		map.put("paraValueDesc", paraValueDesc);
    		
    	}
    	json.setRtData(map);
    	json.setRtState(true);
    	return json;
    }
    /**
     * 获取所有的系统参数
     * @author zhp
     * @createTime 2014-1-17
     * @editTime 上午10:33:44
     * @desc
     */
    public List<TeeSysPara> getAllSysPara(){
    	return sysParaDao.find("from TeeSysPara", null);
    }
    
    /**
     * 更新系统参数
     */
    public void updateSysPara(String paraName,String paraValue){
    	simpleDaoSupport.executeUpdate("update TeeSysPara set paraValue=? where paraName=?", new Object[]{paraValue,paraName});
    	TeeSysProps.getProps().setProperty(paraName, paraValue);
    }
    
	

	public void setSysParaDao(TeeSysParaDao sysParaDao) {
		this.sysParaDao = sysParaDao;
	}
	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}
	
	
}
