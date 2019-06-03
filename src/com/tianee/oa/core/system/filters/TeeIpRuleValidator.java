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

@Service
public class TeeIpRuleValidator implements TeeLoginValidator {
	@Autowired
	private TeeIpRuleDao ipRuleDao;

	public boolean isValid(HttpServletRequest request, TeePerson person, TeeJson json , Map sysParamap) throws Exception {
		String IP_UNLIMITED_USER = TeeStringUtil.getString(sysParamap.get("IP_UNLIMITED_USER"));//不限IP登录的人员ID串
		
		IP_UNLIMITED_USER = "," + IP_UNLIMITED_USER + ",";	
		String seqId = "," + person.getUuid() + ",";
		if (IP_UNLIMITED_USER.contains(seqId)){//如果登录人员属于范围内
		      return true;
		}
		List<TeeIpRule> list = ipRuleDao.getAll();
		String ip = request.getRemoteAddr();
		//ip = "192.168.0.3";
		if(TeeUtility.isServerIpStr(ip)){//如果是服务器登录通过
			//return true;
		}
	    if (list.size() == 0){//如果没有限制范围true
	        return true;
	    }else{
	        for(TeeIpRule ir : list){
	            if (TeeUtility.betweenIP(ip, ir.getBeginIp(), ir.getEndIp())){//如果在这之间就正常登录
	                return true;
	            }
	        }
	       
	    }
	    return false;
  }

	public int getValidatorCode() {
		// TODO Auto-generated method stub
		return TeeLoginErrorConst.LOGIN_IP_RULE_LIMIT_CODE;
	}

	public String getValidatorType() {
		// TODO Auto-generated method stub
		return TeeLoginErrorConst.LOGIN_IP_RULE_LIMIT;
	}

	/*
	 * public void addSysLog(HttpServletRequest request, T9Person person,
	 * Connection conn) throws Exception { //系统日志-非法ip登陆
	 * T9SysLogLogic.addSysLog(conn, T9LogConst.ILLEGAL_IP_LOGIN, "非法ip登录",
	 * person.getSeqId(),request.getRemoteAddr()); }
	 */

	public String getValidatorMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setIpRuleDao(TeeIpRuleDao ipRuleDao) {
		this.ipRuleDao = ipRuleDao;
	}

}
