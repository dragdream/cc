package com.tianee.oa.core.system.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

public class TeeBindIpValidator implements TeeLoginValidator {
	/**
	 * 
	 * @author syl
	 * @date 2013-11-13
	 * Ip绑定校验
	 */
	public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json , Map sysParamap) {
		String IP_UNLIMITED_USER = TeeStringUtil.getString(sysParamap.get("IP_UNLIMITED_USER"));//不限IP登录的人员ID串
		
		IP_UNLIMITED_USER = "," + IP_UNLIMITED_USER + ",";	
		String seqId = "," + person.getUuid() + ",";
		if (IP_UNLIMITED_USER.contains(seqId)){//如果登录人员属于不需要控制范围内
		      return true;
		}
		String ip = request.getRemoteAddr();
		//ip = "192.168.0.3";
		if(TeeUtility.isServerIpStr(ip)){//如果是服务器登录通过
			//return true;
		}
		String bindIp = TeeStringUtil.getString(person.getBindIp());
	    if(bindIp.equals("")){
	    	return true;
	    }
	    
	    
/*	    ip = "192.168.3.2";
	    bindIp = "192.168.3.3-192.168.3.23";*/
	    String ipArray[] = bindIp.split(",");//以逗号分隔
		for(int i = 0; i<ipArray.length ; i++){
			String ipStr = ipArray[i];
			String ipArray2[] = ipStr.split("-");//可能存在IP之间,以"-"分隔
			if(ipArray2.length == 2){//如果是IP之间规则
				String beginIp = ipArray2[0];
				String endIp = ipArray2[1];
				if (TeeUtility.betweenIP(ip, beginIp, endIp)){//如果部在这之间就正常登录
	                return true;
	            }
			}else{//单个
				if(ipStr.equals(ip)){
					return true;
				}
			}
			
		}
	    return false;
	}

	public int getValidatorCode() {
		// TODO Auto-generated method stub
		return TeeLoginErrorConst.LOGIN_BIND_IP_CODE;
	}

	public String getValidatorType() {
		// TODO Auto-generated method stub
		return TeeLoginErrorConst.LOGIN_BIND_IP;
	}

	public String getValidatorMsg() {
		// TODO Auto-generated method stub
		return null;
	}


}
