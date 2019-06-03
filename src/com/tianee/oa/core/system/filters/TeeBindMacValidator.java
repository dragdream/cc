package com.tianee.oa.core.system.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.secure.UdpGetClientMacAddr;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

public class TeeBindMacValidator implements TeeLoginValidator {
	/**
	 * 
	 * @author ny
	 * @date 2015-5-19
	 * mac绑定校验
	 */
	public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json , Map sysParamap) {
		String ip = request.getRemoteAddr();
		try {
			String bindMac = TeeStringUtil.getString(person.getBindMac());
			if(bindMac.equals("")){
				return true;
			}
			UdpGetClientMacAddr address = new UdpGetClientMacAddr(ip);
			String macAddr = address.GetRemoteMacAddr();
		    String macArray[] = bindMac.split(",");//以逗号分隔
			for(int i = 0; i<macArray.length ; i++){
				String macStr = macArray[i];
				if(macStr.equals(macAddr)){
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			 return false;
		}
	    return false;
	}

	public int getValidatorCode() {
		return TeeLoginErrorConst.LOGIN_BIND_MAC_CODE;
	}

	public String getValidatorType() {
		return TeeLoginErrorConst.LOGIN_BIND_MAC;
	}

	public String getValidatorMsg() {
		return null;
	}


}
