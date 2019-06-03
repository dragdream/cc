package com.tianee.oa.core.system.filters;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeIpRule;
import com.tianee.oa.core.general.dao.TeeIpRuleDao;
import com.tianee.oa.core.general.service.TeeIpRuleService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.system.common.TeeLoginErrorConst;
import com.tianee.oa.core.system.imp.TeeLoginValidator;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

public class TeeSetUserRoleValidator implements TeeLoginValidator {

	public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json , Map sysParamap) throws Exception {
		if(person.getUserRole() == null){//如果角色为空
			return false;
		}
	    return true;
  }

	public int getValidatorCode() {
		// TODO Auto-generated method stub
		return TeeLoginErrorConst.LOGIN_SET_USER_ROLE_CODE;
	}

	public String getValidatorType() {
		// TODO Auto-generated method stub
		return TeeLoginErrorConst.LOGIN_SET_USER_ROLE;
	}


	public String getValidatorMsg() {
		// TODO Auto-generated method stub
		return null;
	}


}
