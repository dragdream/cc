package com.tianee.oa.core.system.filters;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.secure.TeePassEncrypt;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;

public class TeePasswordValidator implements TeeLoginValidator {
	String isSecureCard = "0";
	String returnValue = "0";
	String password = "";

	public TeePasswordValidator(String password) {
		this.password = password;
	}

	public boolean isValid(HttpServletRequest request, TeePerson person,
			TeeJson json, Map sysParamap) throws Exception {
		if (person.getPassword() == null) {
			person.setPassword("");
		}
		
		if (this.password!=null && person != null && TeePassEncryptMD5.checkCryptDynamic(this.password, person.getPassword().trim())) {
			return true;
		} else {
			String pwdCrypt = request.getParameter("pwdCrypt");
			if(person.getPassword().trim().equals(pwdCrypt)){
				return true;
			}
			return false;
		}
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
