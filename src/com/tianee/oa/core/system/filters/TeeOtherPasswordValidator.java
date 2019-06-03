package com.tianee.oa.core.system.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.secure.TeePassEncrypt;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;

public class TeeOtherPasswordValidator implements TeeLoginValidator {
	String isSecureCard = "0";
	String returnValue = "0";
	String password = "";
	String pwdValidatorType = "0" ;//系统校验类型 1-高速波云平台校验
	public TeeOtherPasswordValidator(String password ,  String pwdValidatorType) {
		this.password = password;
		this.pwdValidatorType = pwdValidatorType;
	}

	public boolean isValid(HttpServletRequest request, TeePerson person,
			TeeJson json, Map sysParamap) throws Exception {
		if(person == null){
			return false;
		}
		if(pwdValidatorType.equals("1")){
			if(this.password.equals(person.getGsbPassword())){
				return true;
			}
		}else{
			if (person.getPassword() == null) {
				person.setPassword("");
			}
			if ( TeePassEncryptMD5.checkCryptDynamic(this.password, person.getPassword().trim())) {
				return true;
			}
		}
		return false;
		
	}

	public int getValidatorCode() {
		// TODO Auto-generated method stub
		if ("1".equals(isSecureCard)) {
			if ("-1".equals(returnValue)) {
				return TeeLoginErrorConst.LOGIN_PASSWORD_ERROR_CODE_SECURE_CARD_1;
			} else if ("-2".equals(returnValue)) {
				return TeeLoginErrorConst.LOGIN_PASSWORD_ERROR_CODE_SECURE_CARD_2;
			} else if ("0".equals(returnValue)) {
				return TeeLoginErrorConst.LOGIN_PASSWORD_ERROR_CODE_SECURE_CARD_3;
			}
		}
		return TeeLoginErrorConst.LOGIN_PASSWORD_ERROR_CODE;
	}

	public String getValidatorType() {
		// TODO Auto-generated method stub
		if ("1".equals(isSecureCard)) {
			if ("-1".equals(returnValue)) {
				return TeeLoginErrorConst.LOGIN_PASSWORD_ERROR_SECURE_CARD_1;
			} else if ("-2".equals(returnValue)) {
				return TeeLoginErrorConst.LOGIN_PASSWORD_ERROR_SECURE_CARD_2;
			} else if ("0".equals(returnValue)) {
				return TeeLoginErrorConst.LOGIN_PASSWORD_ERROR_SECURE_CARD_3;
			}
		}
		return TeeLoginErrorConst.LOGIN_PASSWORD_ERROR;
	}

	public String getValidatorMsg() {
		// TODO Auto-generated method stub
		return null;
	}

}
