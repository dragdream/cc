package com.tianee.oa.core.base.attend.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.attend.bean.TeeAttendLeaderRule;
import com.tianee.oa.core.base.attend.dao.TeeAttendLeaderRuleDao;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaderRuleModel;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;
/**
 * 
 * @author syl
 *
 */
@Service
public class TeeAttendLeaderRuleService  extends TeeBaseService{
	@Autowired
	private TeeAttendLeaderRuleDao attendLeaderRuleDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeAttendLeaderRuleModel model) {
		TeeSysLog sysLog = TeeSysLog.newInstance();
		String remark = "【管辖部门:"+ model.getDeptNames() + ",管辖人员："+ model.getUserNames() + ",审批人员:" + model.getUserNames() + "】";
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeAttendLeaderRule rule = new TeeAttendLeaderRule();
		if(!TeeUtility.isNullorEmpty(model.getDeptIds())){
			List<TeeDepartment> deptList = deptDao.getDeptListByUuids(model.getDeptIds());
			
			rule.setDepts(deptList);
		}
		
		if(!TeeUtility.isNullorEmpty(model.getUserIds())){
			List<TeePerson> userList = personDao.getPersonByUuids(model.getUserIds());
			rule.setUsers(userList);
		}
		if(model.getSid() > 0){
			TeeAttendLeaderRule ruleOld  = attendLeaderRuleDao.getById(model.getSid());
			if(ruleOld != null){
				BeanUtils.copyProperties(model, ruleOld);
				ruleOld.setDepts(rule.getDepts());
				ruleOld.setUsers(rule.getUsers());
				attendLeaderRuleDao.updateAttend(ruleOld);
				json.setRtState(true);
				json.setRtData(model);
				json.setRtMsg("编辑成功！");
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				remark = "该考勤审批规则未找到！";
			}
			sysLog.setType("023O");
		}else{
			BeanUtils.copyProperties(model, rule);
		
			attendLeaderRuleDao.addAttendLeader(rule);
			sysLog.setType("023N");
			sysLog.setRemark("新建考勤申请规则,");
			json.setRtState(true);
			json.setRtData(model);
			json.setRtMsg("保存成功！");
		}
		//创建日志
		sysLog.setRemark(remark);
		TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		return json;
	}
	
	/**
	 * 
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getRule(HttpServletRequest request, TeeAttendLeaderRuleModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeAttendLeaderRule> list = attendLeaderRuleDao.selectRule(person, model);
		List<TeeAttendLeaderRuleModel> listModel = new ArrayList<TeeAttendLeaderRuleModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}

	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public TeeAttendLeaderRuleModel parseModel(TeeAttendLeaderRule rule){
		TeeAttendLeaderRuleModel model = new TeeAttendLeaderRuleModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(rule == null){
			return null;
		}
		BeanUtils.copyProperties(rule, model);
		String deptIds = "";//部门
		String deptNames = "";
		String userIds = "";//用户
		String userNames = "";
		String leaderIds =  "";//领导审批人员
		String leaderNames = "";
		List<TeeDepartment> deptList  = rule.getDepts();
		for (int i = 0; i < deptList.size(); i++) {
			deptIds = deptIds + deptList.get(i).getUuid() + ",";
			deptNames = deptNames + deptList.get(i).getDeptName() + ",";
		}
		List<TeePerson> userList  = rule.getUsers();
		for (int i = 0; i < userList.size(); i++) {
			userIds = userIds + userList.get(i).getUuid() + ",";
			userNames = userNames + userList.get(i).getUserName() + ",";
		}
		
		if(!TeeUtility.isNullorEmpty(rule.getLeaderIds())){
			String[] userArray = personDao.getPersonNameAndUuidByUuids(rule.getLeaderIds());
			leaderIds = userArray[0];
			leaderNames = userArray[1];
		}
		model.setDeptIds(deptIds);
		model.setDeptNames(deptNames);
		model.setUserIds(userIds);
		model.setUserNames(userNames);
		model.setLeaderIds(leaderIds);
		model.setLeaderNames(leaderNames);
		return model;
	}
	
	
	/**
	 * 
	 * @author syl 删除ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	@TeeLoggingAnt(template="删除考勤审批规则,  {logModel.leaderName}",type="023P")
	public TeeJson deleteByIdService(HttpServletRequest request, TeeAttendLeaderRuleModel model) {
		TeeJson json = new TeeJson();
		String leaderName = "";
		if(model.getSid() > 0){
			TeeAttendLeaderRule rule = attendLeaderRuleDao.getById(model.getSid());
			if(rule == null){
				leaderName = "该考勤审批规则已被删除！";
				json.setRtState(false);
				json.setRtMsg(leaderName);
			}else{
				json.setRtState(true);
				json.setRtMsg("删除成功!");
				leaderName = "删除成功！";
				attendLeaderRuleDao.deleteByObj(rule);
			}	
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("leaderName", leaderName);//添加其他参数
		return json;
	}
	
	/**
	 * 
	 * @author syl 
	 *  查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeAttendLeaderRuleModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeAttendLeaderRule out = attendLeaderRuleDao.getById(model.getSid());
			if(out !=null){
				model = parseModel(out);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关考勤审批规则记录！");
		return json;
	}
	
	/**
	 * 获取审批规则的 审批人员
	 * @author syl
	 * @date 2014-2-5
	 * @param request
	 * @return
	 */
	public TeeJson selectLeaderRule(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String[] userArray  = new String[2];
		TeeAttendLeaderRuleModel model = new TeeAttendLeaderRuleModel();
		List<TeeAttendLeaderRule> ruleList = attendLeaderRuleDao.selectLeaderRule(person , model);
		String userIds = "";
		for (int i = 0; i < ruleList.size(); i++) {
			if(!TeeUtility.isNullorEmpty(ruleList.get(i).getLeaderIds())){
				if(!userIds.endsWith(",")&&!("").equals(userIds)){
					userIds+=",";
				}
				userIds = userIds + ruleList.get(i).getLeaderIds();
			}
		}
		
		if(!TeeUtility.isNullorEmpty(userIds)){
			userArray = personDao.getPersonNameAndUuidByUuids(userIds);
		}
		Map map = new HashMap();
		map.put("userIds", TeeStringUtil.getString(userArray[0]));
		map.put("userNames", TeeStringUtil.getString(userArray[1]));
		json.setRtState(true);
		json.setRtData(map);
		return json;
	}

}