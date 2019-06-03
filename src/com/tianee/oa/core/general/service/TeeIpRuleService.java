package com.tianee.oa.core.general.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeIpRule;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.dao.TeeIpRuleDao;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeIpRuleService  extends TeeBaseService {
	@Autowired
	private TeeIpRuleDao ipRuleDao;
	
	/**
	 * 新建或者更新
	 * @param rule
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(TeeIpRule rule , HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeSysLog sysLog = TeeSysLog.newInstance();
		int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
		String ruleType = "系统登录";
		if(rule.getIpType().equals("1")){
			ruleType = "考勤登记";
		}
		if(id > 0){
			TeeIpRule oldRule = ipRuleDao.getById(id);
			if(oldRule != null){
				BeanUtils.copyProperties(rule, oldRule);
				oldRule.setSid(id);
				ipRuleDao.updaIpRule(oldRule);
				json.setRtMsg("保存成功！");
				sysLog.setType("013B");
				sysLog.setRemark("修改IP访问规则," + getLogInfo(rule));
			
			}
		}else{
			ipRuleDao.addIpRule(rule);
			json.setRtMsg("新建成功！");
			sysLog.setType("013A");
			sysLog.setRemark("新建IP访问规则," +  getLogInfo(rule));
		}
		json.setRtState(true);
	
		//创建日志
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		return json;
	}
	
	
	/**
	 * 查询  by Id
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
		if(id > 0){
			TeeIpRule ipRule = ipRuleDao.getById(id);
			if(ipRule != null){
				json.setRtData(ipRule);
				json.setRtState(true);
				json.setRtMsg("查询成功");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("没有查询到相关数据！");
		}
		return json;
	}
	
	
	
	/**
	 * 查询  所有
	 * @param request
	 * @return
	 */
	public TeeJson getAllIpRule(){
		TeeJson json = new TeeJson();
		List<TeeIpRule> list = ipRuleDao.getAll();
		json.setRtData(list);
		json.setRtState(true);
		json.setRtMsg("查询成功");
		return json;
	}
	
	
	/**
	 * 删除  by Id
	 * @param request
	 * @return
	 */
	@TeeLoggingAnt(template="删除IP访问规则，{logModel.remark}",type="013C")
	public TeeJson deleteByIdService(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
		String remark = "";
		if(id > 0 ){
			TeeIpRule rule = ipRuleDao.getById(id);
			remark = getLogInfo(rule);
			if(rule == null){
				json.setRtState(true);
				json.setRtMsg(remark);
				
			}else{
				ipRuleDao.deleteByObj(rule);
				json.setRtState(true);
			}
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("remark", remark);//添加其他参数
		return json;
	}
	
	/**
	 * 记录日志信息
	 * @author syl
	 * @date 2014-4-23
	 * @param rule
	 * @return
	 */
	public String getLogInfo(TeeIpRule rule){
		String logInfo = "";
		String ruleType = "系统登录访问";
		if(rule == null){
			logInfo = "该IP访问规则已被删除";
		}else{
			if(rule.getIpType().equals("1")){
				 ruleType = "考勤登记";
			}
			logInfo = "【起始IP:"+rule.getBeginIp() + ",结束IP:" + rule.getEndIp() + ",访问规则类型：" + ruleType + "，备注:" + rule.getRemark() +"】";
		}
		return logInfo;
	}
	public TeeIpRuleDao getIpRuleDao() {
		return ipRuleDao;
	}

	public void setIpRuleDao(TeeIpRuleDao ipRuleDao) {
		this.ipRuleDao = ipRuleDao;
	}
}



