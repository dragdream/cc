package com.tianee.oa.core.system.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 移动设备设备码校验
 * @author kakalion
 *
 */
public class TeeMobileDeviceLoginValidator  implements TeeLoginValidator {


  public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json, Map sysParamap) throws Exception {
	String qDevice = TeeStringUtil.getString(request.getParameter("qDevice") , "1");//登录方式  ？？ 1-android  2-IOS  3-IM  4 - ipad
	if("PC".equals(qDevice)){
		return true;
	}
	String deviceId = TeeStringUtil.getString(person.getDeviceId());
	String deviceNo = TeeStringUtil.getString(request.getParameter("deviceNo"));
	deviceNo = deviceNo.replace("<", "").replace(">", "").replace("-", "");
	if(!"".equals(deviceId) && !deviceId.equals(deviceNo)){
		return false;
	}
//	if("".equals(deviceId)){
//		person.setDeviceId(deviceNo);
//	}
    return true;
  }
 
  public String getValidatorType() {
    return TeeLoginErrorConst.DEVICE_NO_VALIDATION;
  }

  public int getValidatorCode() {
    return TeeLoginErrorConst.DEVICE_NO_VALIDATION_CODE;
  }

  public String getValidatorMsg() {
    return null;
  }

}
