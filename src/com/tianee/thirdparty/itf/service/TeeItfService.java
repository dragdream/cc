package com.tianee.thirdparty.itf.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeeDepartmentModel;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeItfService extends TeeBaseService{

	@Autowired
	private TeePersonDao teePersonDao;
	
	@Autowired
	private TeeDeptDao teeDeptDao;
	
	@Autowired
	private TeePersonService teePersonService;
	
	@Autowired
	private TeeDeptService teeDeptService;
	/**
	 * 添加人员
	 * @throws Exception 
	 * */
	public String addPerson(HttpServletRequest request) throws Exception {
		//String personInfo2="{\"userId\":\"admin\"}";
		//userId=用户登录名
		//userName=用户真实姓名
		//deptId=部门id
		//order=用户排序号
		//sex=用户性别
		//mobilNo=手机号
		//email=电子邮件
		//telNoDept=工作电话
		//{"userId":"sxx66","userName":"sxx66","deptId":3,"order":1,"sex":"女","mobilNo":"1111111111","email":"333333333333","telNoDept":"6666666666"}
		String msg="{\"msg\":\"添加失败\",\"status\":false}";
		String personInfo=request.getParameter("personInfo");
		JSONObject  jasonObject = JSONObject.fromObject(personInfo);
		Map map = (Map)jasonObject;
		if(map!=null){
			//用户登录名
			String userId=TeeStringUtil.getString(map.get("userId"));
			//用户姓名
			String userName=TeeStringUtil.getString(map.get("userName"));
			//用户部门id
			String deptId=TeeStringUtil.getString(map.get("deptId"));
			//用户排序号
			int order=TeeStringUtil.getInteger(map.get("order"),0);
			//用户性别
			String sex=TeeStringUtil.getString(map.get("sex"));
			//用户手机号
			String mobilNo=TeeStringUtil.getString(map.get("mobilNo"));
			//用户电子邮件
			String email=TeeStringUtil.getString(map.get("email"));
			//用户工作电话
			String telNoDept=TeeStringUtil.getString(map.get("telNoDept"));
			TeePersonModel personModul=new TeePersonModel();
			personModul.setUserId(userId);//用户登录名
			personModul.setUserName(userName);//用户姓名
			personModul.setUserNo(order);//用户排序号
			personModul.setSex(sex);//用户性别
			personModul.setMobilNo(mobilNo);//用户手机号
			personModul.setEmail(email);//用户电子邮件
			personModul.setTelNoDept(telNoDept);//用户工作电话
			//uniqueId
			//用户部门id
			List<TeeDepartment> find = teeDeptDao.find("from TeeDepartment where uniqueId=?", new Object[]{deptId});
			if(find!=null && find.size()>0){
				personModul.setDeptId(find.get(0).getUuid()+"");
			}
			personModul.setUserRoleStr("6");//用户角色
			//添加用户
			TeeJson json = teePersonService.addOrUpdate(personModul, request);
			if(json.isRtState()){
				msg="{\"msg\":\"添加成功\",\"status\":true}";
			}
		}
		return msg;
	}

	/**
	 * 修改人员
	 * @throws Exception 
	 * */
	public String updatePerson(HttpServletRequest request) throws Exception {
		String msg="{\"msg\":\"修改失败\",\"status\":false}";
		String personInfo=request.getParameter("personInfo");
		JSONObject  jasonObject = JSONObject.fromObject(personInfo);
		Map map = (Map)jasonObject;
		if(map!=null){
			//用户登录名
			String userId=TeeStringUtil.getString(map.get("userId"));
			//用户姓名
			String userName=TeeStringUtil.getString(map.get("userName"));
			//用户部门id
			String deptId=TeeStringUtil.getString(map.get("deptId"));
			//用户排序号
			int order=TeeStringUtil.getInteger(map.get("order"),0);
			//用户性别
			String sex=TeeStringUtil.getString(map.get("sex"));
			//用户手机号
			String mobilNo=TeeStringUtil.getString(map.get("mobilNo"));
			//用户电子邮件
			String email=TeeStringUtil.getString(map.get("email"));
			//用户工作电话
			String telNoDept=TeeStringUtil.getString(map.get("telNoDept"));
			TeePersonModel personModul=new TeePersonModel();
			personModul.setUserId(userId);//用户登录名
			personModul.setUserName(userName);//用户姓名
			personModul.setUserNo(order);//用户排序号
			personModul.setSex(sex);//用户性别
			personModul.setMobilNo(mobilNo);//用户手机号
			personModul.setEmail(email);//用户电子邮件
			personModul.setTelNoDept(telNoDept);//用户手机号
			//uniqueId
			//用户部门id
			List<TeeDepartment> find = teeDeptDao.find("from TeeDepartment where uniqueId=?", new Object[]{deptId});
			if(find!=null && find.size()>0){
				personModul.setDeptId(find.get(0).getUuid()+"");
			}
			personModul.setUserRoleStr("6");//用户角色id
			TeePerson person = teePersonService.getPersonByUserId(userId);
			personModul.setUuid(person.getUuid());//用户名id
			TeeJson json = teePersonService.addOrUpdate(personModul, request);
			if(json.isRtState()){
				msg="{\"msg\":\"修改成功\",\"status\":true}";
			}
		}
		return msg;
	}

	/**
	 * 删除人员
	 * */
	public String deletePerson(String userId) {
		String msg="{\"msg\":\"删除失败\",\"status\":false}";
		int query = teePersonDao.deleteOrUpdateByQuery("update TeePerson set deleteStatus = 1 where userId=?", new Object[]{userId});
		if(query>0){
			msg="{\"msg\":\"删除成功\",\"status\":false}";
		}
		return msg;
	}

	/**
	 * 添加部门
	 * @throws Exception 
	 * */
	public String addDept(HttpServletRequest request) throws Exception {
		String deptInfo=request.getParameter("deptInfo");
		//deptId=部门id
		//order=部门排序号
		//deptName=部门名称
		//deptParent=上级部门
		//{"deptId":1,"order":1,"deptName":"测试66","deptParent":}
		String msg="{\"msg\":\"添加失败\",\"status\":false}";
		JSONObject jsonObject = JSONObject.fromObject(deptInfo);
		Map map=(Map)jsonObject;
		if(map!=null){
			//部门id
			String deptId=TeeStringUtil.getString(map.get("deptId"));
			//部门排序号
			int order=TeeStringUtil.getInteger(map.get("order"),0);
			//部门名称
			String deptName=TeeStringUtil.getString(map.get("deptName"));
			//上级部门id
			String deptParent=TeeStringUtil.getString(map.get("deptParent"));
			TeeDepartmentModel model=new TeeDepartmentModel();
			model.setUniqueId(deptId);//对接的唯一标识
			model.setDeptNo(order);//部门排序号
			model.setDeptName(deptName);//部门名称
			//上级部门id
			List<TeeDepartment> find2 = teeDeptDao.find("from TeeDepartment where uniqueId=?", new Object[]{deptParent});
			if(find2!=null && find2.size()>0){
				model.setDeptParentId(find2.get(0).getUuid());
			}
			//model.setDeptParentId(deptParent);
			model.setDeptType(1);//部门类型
			TeeJson json = teeDeptService.addDeptService(model, request);
			if(json.isRtState()){
				msg="{\"msg\":\"添加成功\",\"status\":true}";
			}
		}
		return msg;
	}

	/**
	 * 修改部门
	 * @throws Exception 
	 * */
	public String updateDept(HttpServletRequest request) throws Exception {
		String deptInfo=request.getParameter("deptInfo");
		//deptId=部门id
		//order=部门排序号
		//deptName=部门名称
		//deptParent=上级部门
		String msg="{\"msg\":\"修改失败\",\"status\":false}";;
		JSONObject jsonObject = JSONObject.fromObject(deptInfo);
		Map map=(Map)jsonObject;
		if(map!=null){
			//部门id
			String deptId=TeeStringUtil.getString(map.get("deptId"));
			//部门排序号
			int order=TeeStringUtil.getInteger(map.get("order"),0);
			//部门名称
			String deptName=TeeStringUtil.getString(map.get("deptName"));
			//上级部门id
			String deptParent=TeeStringUtil.getString(map.get("deptParent"));
			TeeDepartmentModel model=new TeeDepartmentModel();
			model.setUniqueId(deptId);//部门id
			model.setDeptNo(order);//部门排序好
			model.setDeptName(deptName);//部门名称
			//上级部门id
			List<TeeDepartment> find2 = teeDeptDao.find("from TeeDepartment where uniqueId=?", new Object[]{deptParent});
			if(find2!=null && find2.size()>0){
				model.setDeptParentId(find2.get(0).getUuid());
			}
			model.setDeptType(1);//部门类型
			List<TeeDepartment> find = teeDeptDao.find("from TeeDepartment where uniqueId=?", new Object[]{deptId});
			//部门id
			if(find!=null && find.size()>0){
				model.setUuid(find.get(0).getUuid());
			}
			TeeJson json = teeDeptService.updateDeptService(model, request);
			if(json.isRtState()){
				msg="{\"msg\":\"修改成功\",\"status\":true}";
			}
		}
		return msg;
	}

	/**
	 * 删除部门
	 * @throws Exception 
	 * */
	public String deleteDept(HttpServletRequest request) throws Exception {
		String msg="{\"msg\":\"删除失败\",\"status\":false}";
		String deptId=request.getParameter("deptId");
		List<TeeDepartment> find = teeDeptDao.find("from TeeDepartment where uniqueId=?", new Object[]{deptId});
		if(find!=null && find.size()>0){
			teeDeptService.deleteDeptAndSubDept(find.get(0).getUuid(),request);
			msg="{\"msg\":\"删除成功\",\"status\":true}";
		}
		return msg;
	}

	/**
	 * 结束流程
	 * */
	public String endFlowRun(HttpServletRequest request) {
		String flowRunStr=request.getParameter("flowRunStr");
		String msg="{\"msg\":\"操作失败\",\"status\":false}";
		if(flowRunStr!=null && !"".equals(flowRunStr)){
			JSONObject object = JSONObject.fromObject(flowRunStr);
			//流程id
			int runId = TeeStringUtil.getInteger(object.get("userId"),0);
			//实际开始时间 
			String beginTime = TeeStringUtil.getString(object.get("beginTime"));
			//开始时间点
			String bTime = TeeStringUtil.getString(object.get("bTime"));
			//实际结束时间
			String endTime = TeeStringUtil.getString(object.get("endTime"));
			//实际结束时间点
			String eTime = TeeStringUtil.getString(object.get("eTime"));
			//使用本年度年假
			Double bNianJia = TeeStringUtil.getDouble(object.get("bNianJia"),0);
			//使用上年度年假
			Double sNianJia = TeeStringUtil.getDouble(object.get("sNianJia"),0);
			//共休了年假
			Double num = TeeStringUtil.getDouble(object.get("num"),0);
			String mark = TeeStringUtil.getString(object.get("mark"));
			//销假人
			String xjUserId = TeeStringUtil.getString(object.get("xjUserId"));
			if(runId>0){
				TeeFlowRun flowRun = (TeeFlowRun)simpleDaoSupport.get(TeeFlowRun.class,runId);
				int sid = flowRun.getFlowType().getSid();
				if(sid==1032){
					//DATA_35 实际开始实际 DATA_55 实际结束时间  DATA_56 实际开时间 DATA_57 实际结时间  
					//DATA_59请假点数   DATA_51 用上年度请假天数  DATA_52:用本年度请假天数   DATA_61 销假人
					String sql="update tee_f_r_d_1032 set DATA_35='"+beginTime+"',DATA_55='"+endTime+"',DATA_56='"+bTime+"',"
							+ "DATA_57='"+eTime+"',DATA_59='"+num+"',DATA_51='"+sNianJia+"',DATA_52='"+bNianJia+"',DATA_61='"+xjUserId+"',DATA_33='"+mark+"' "
							+ "where RUN_ID="+runId;
					int nativeUpdate = simpleDaoSupport.executeNativeUpdate(sql, null);
				}else{
					//DATA_35 实际开始时间  DATA_58 实际结束时间   DATA_59 实际开时间     DATA_60 实际结时间 
					//DATA_62   请假天数  DATA_54上年度的年假  DATA_55本年度的年假  DATA_64 销假人
					String sql="update tee_f_r_d_1051 set DATA_35='"+beginTime+"',DATA_58='"+endTime+"',DATA_59='"+bTime+"',"
							+ "DATA_60='"+eTime+"',DATA_62='"+num+"',DATA_54='"+sNianJia+"',DATA_55='"+bNianJia+"',DATA_64='"+xjUserId+"',DATA_33='"+mark+"' "
							+ "where RUN_ID="+runId;
					int nativeUpdate = simpleDaoSupport.executeNativeUpdate(sql, null);
				}
				List<TeeFlowRunPrcs> find = simpleDaoSupport.find("from TeeFlowRunPrcs where flowRun.runId=? order by sid desc", new Object[]{runId});
				if(find!=null && find.size()>0){
					TeeFlowRunPrcs prcs = find.get(0);
					prcs.setFlag(4);
					prcs.setEndTime(Calendar.getInstance());
					prcs.setEndTimeStamp(Calendar.getInstance().getTimeInMillis());
					simpleDaoSupport.update(prcs);
				}
				//更新流程实例为已办结
				simpleDaoSupport.executeUpdate("update TeeFlowRun fr set fr.endTime=? where fr.runId=?", 
						new Object[]{Calendar.getInstance(),runId});
				msg="{\"msg\":\"操作成功\",\"status\":true}";;
			}
		}
		return msg;
	}


}
