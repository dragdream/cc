package com.tianee.oa.core.system.common;

import com.tianee.webframe.util.global.TeeSysProps;



public class TeeLoginErrorConst {
  
  public static final String LOGIN_NOTEXIST_USER = "用户不存在";
  public static final int LOGIN_NOTEXIST_USER_CODE = 0;
  
  public static final String LOGIN_FORBID_LOGIN = "用户禁止登录";
  public static final int LOGIN_FORBID_LOGIN_CODE = 1;
  
  public static final String LOGIN_IP_RULE_LIMIT = "登陆IP不符合IP规则";
  public static final int LOGIN_IP_RULE_LIMIT_CODE = 2;
  
  public static final String LOGIN_BIND_IP = "登陆IP和用户绑定IP不符";
  public static final int LOGIN_BIND_IP_CODE = 3;
  
  public static final String LOGIN_USBKEY_ERROR = "使用USBKEY登录失败";
  public static final int LOGIN_USBKEY_ERROR_CODE = 4;
  
  public static final String LOGIN_PASSWORD_ERROR = "密码错误";
  public static final int LOGIN_PASSWORD_ERROR_CODE = 5;
  
  public static final String LOGIN_INITIAL_PW = "修改初始密码";
  public static final int LOGIN_INITIAL_PW_CODE = 6;
  
  public static final String LOGIN_PW_EXPIRED = "密码过期";
  public static final int LOGIN_PW_EXPIRED_CODE = 7;
  
  public static final String LOGIN_RETRY_ERROR = "重新登录错误";
  public static final int LOGIN_RETRY_ERROR_CODE = 8;
  
  public static final String REPEAT_LOGIN_ERROR = "用户已经登录,无法重复登录";
  public static final int REPEAT_LOGIN_ERROR_CODE = 9;
  
  public static final String LOGIN_PASSWORD_ERROR_SECURE_CARD_1 = "动态加密字符串有错";
  public static final int LOGIN_PASSWORD_ERROR_CODE_SECURE_CARD_1 = 10;
  
  public static final String LOGIN_PASSWORD_ERROR_SECURE_CARD_2 = "动态密码未知内部错误";
  public static final int LOGIN_PASSWORD_ERROR_CODE_SECURE_CARD_2 = 11;
  
  public static final String LOGIN_PASSWORD_ERROR_SECURE_CARD_3 = "动态密码错误";
  public static final int LOGIN_PASSWORD_ERROR_CODE_SECURE_CARD_3 = 12;
  
  public static final String VERIFICATION_CODE_ERROR = "验证码错误";
  public static final int VERIFICATION_CODE_CODE = 14;
  
  public static final String SOFTWARE_EXPIRED = "已经超过最大免费试用期限！\\n继续使用，请联系"+TeeSysProps.getString("orgName")+"。";
  public static final int SOFTWARE_EXPIRED_CODE = 13;
  
  
  public static final String LOGIN_SET_USER_ROLE= "当前用户没有赋给角色，请与管理员联系";
  public static final int LOGIN_SET_USER_ROLE_CODE = 15;
  
  public static final String LOGIN_AUTH = "注册文件校验失败！";
  public static final int LOGIN_AUTH_CODE = 16;
  
  public static final String LOGIN_BIND_MAC = "登陆MAC地址和用户绑定MAC地址不符";
  public static final int LOGIN_BIND_MAC_CODE = 17;
  
  public static final String LOGIN_IM_AUTH = "已超出IM终端连接最大数或已到期";
  public static final int LOGIN_IM_AUTH_CODE = 18;
  
  public static final String LOGIN_PC_AUTH = "已超出PC终端连接最大数或已到期";
  public static final int LOGIN_PC_AUTH_CODE = 19;
  
  public static final String DEVICE_NO_VALIDATION = "移动端设备ID校验失败";
  public static final int DEVICE_NO_VALIDATION_CODE = 20;
  
  public static final String UKEY_CODE_ERROR = "Ukey认证失败！";
  public static final int  UKEY_CODE = 21;
}
