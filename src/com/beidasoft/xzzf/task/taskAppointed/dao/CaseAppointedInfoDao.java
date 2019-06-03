package com.beidasoft.xzzf.task.taskAppointed.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.beidasoft.xzzf.task.taskAppointed.model.CaseAppointedInfoModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class CaseAppointedInfoDao extends TeeBaseDao<CaseAppointedInfo>{

	/**
	 * 更新对象操作
	 * 
	 * @param o
	 *            实体对象
	 */
	public void update(CaseAppointedInfo o) {
		Session session = this.getSession();
		session.update(o);
	}
	
	/**
	 * 保存或更新 操作
	 * 
	 * @param o
	 */
	public void saveOrUpdate(CaseAppointedInfo o) {
		Session session = this.getSession();
		session.saveOrUpdate(o);
	}
	
	/**
	 * 根据分页查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<CaseAppointedInfo> listByPage(int firstResult,int rows,CaseAppointedInfoModel queryModel, TeePerson loginUser){
		
		//获取登录人员的权限
		String roleName = loginUser.getUserRole().getRoleName();

		// 一.总队领导（总队领导）
		String hql = "from CaseAppointedInfo where 1 = 1 and isDelete = 0 ";
		if(!TeeUtility.isNullorEmpty(queryModel.getTaskName())){
			hql+= " and taskName like '%"+queryModel.getTaskName()+"%'";
		}
		if((queryModel.getTaskRec()!= -1)){
			hql+=" and taskRec = "+queryModel.getTaskRec();
		}
		if(queryModel.getDealType()== -1) {
			// -1代表全部案件
			hql += " and ( dealType = 0 or dealType = 10 or dealType = 11 or dealType = 9 ) ";
		}
		if((queryModel.getDealType() == 0)){
			//0代表未处理案件
			hql+=" and dealType = 0 ";
		}
		if((queryModel.getDealType() == 9)){
			//9代表处理中（还可以继续指派案件 ）
			hql+=" and dealType = 9 ";
		}
		if((queryModel.getDealType() == 10)){
			//10 已立案
			hql+=" and dealType = 10 ";
		}
		if((queryModel.getDealType() == 11)){
			//11不予立案
			hql+=" and dealType = 11 ";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getDisposeStartTimeStr())) {
			hql += " and disposeTime >= '"+ queryModel.getDisposeStartTimeStr() + "'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getDisposeEndTimeStr())) {
			hql += " and disposeTime <= '"+ queryModel.getDisposeEndTimeStr() + "'";
		}
		
		int deptId = loginUser.getDept().getUuid();
		if("系统管理员".equals(roleName)) {  
			
		}
		
		// 二.部门领导（部门负责人、部门副职）
		if(("部门负责人".equals(roleName) || "部门副职".equals(roleName))) {
			hql += " and organizationId = " + deptId;
			
			
		} 
		if ("工作人员".equals(roleName)) {
			hql += " and organizationId = " + deptId;
		}
		//如果detalType等于-1、0按照送审时间排序。如果为9、 10、 11按照处理时间排序
		int detalType = queryModel.getDealType();
		if (detalType == -1 || detalType == 0) {
			hql += " order by(taskSendTime) desc";
		}
		if (detalType == 9 || detalType == 10 || detalType == 11) {
			hql += " order by(disposeTime) desc";
		}
		return super.pageFind(hql, firstResult, rows, null);//最后一个参数是条件查询的条件
	}
	
	/**
	 * 返回总记录结果
	 * @return
	 */
	public long getTotal(CaseAppointedInfoModel queryModel, TeePerson loginUser){

		//获取登录人员的权限
		String roleName = loginUser.getUserRole().getRoleName();
		String hql = "select count(id) from CaseAppointedInfo where 1=1 and isDelete = 0 ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getTaskName())){
			hql+= " and taskName like '%"+queryModel.getTaskName()+"%'";
		}
		if((queryModel.getTaskRec()!= -1)){
			hql+=" and taskRec = "+queryModel.getTaskRec();
		}
		
		if(queryModel.getDealType()== -1) {
			// -1代表全部案件
			hql += " and ( dealType = 0 or dealType = 10 or dealType = 11 or dealType = 9 ) ";
		}
		if((queryModel.getDealType() == 0)){
			//0代表未处理案件
			hql+=" and dealType = 0 ";
		}
		if((queryModel.getDealType() == 9)){
			//9代表处理中（还可以继续指派案件 ）
			hql+=" and dealType = 9 ";
		}
		if((queryModel.getDealType() == 10)){
			//10 已立案
			hql+=" and dealType = 10 ";
		}
		if((queryModel.getDealType() == 11)){
			//11不予立案
			hql+=" and dealType = 11 ";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getDisposeStartTimeStr())) {
			hql += " and disposeTime >= '"+ queryModel.getDisposeStartTimeStr() + "'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getDisposeEndTimeStr())) {
			hql += " and disposeTime <= '"+ queryModel.getDisposeEndTimeStr() + "'";
		}
		
		int deptId = loginUser.getDept().getUuid();
		// 一.总队领导（总队领导）
		if("系统管理员".equals(roleName)) {  
			
		}
		// 二.部门领导（部门负责人、部门副职）
		if(("部门负责人".equals(roleName) || "部门副职".equals(roleName))) {
			hql += " and organizationId = " + deptId;
		} 
		if ("工作人员".equals(roleName)) {
			hql += " and organizationId = " + deptId;
		}
		return super.count(hql, null);//最后一个参数是条件查询的条件
		
	}

	/**
	 * 根据baseId查询戴指派案件信息
	 */
	public CaseAppointedInfo getByTaskRecId(String taskRecId) {
		Session session = this.getSession();
		String hql = "from CaseAppointedInfo where taskRecId=:taskRecId";
		CaseAppointedInfo caseAppointedInfo = (CaseAppointedInfo) session.createQuery(hql).setString("taskRecId", taskRecId).uniqueResult();
		return caseAppointedInfo;
	}

	/**
	 * 移动端获取 指派表List  非datagrid
	 * 
	 * @param request
	 * @return
	 */
	public List<CaseAppointedInfo> getCaseAppointList(int taskRec, int dealType, TeePerson person) {
		//获取登录人员的权限
		String roleName = person.getUserRole().getRoleName();
		
		String hql = "";
		if (taskRec == 20) { //如果是检查 （包括现场检查和双随机）
			hql += "from CaseAppointedInfo where taskRec in (20, 30) " ;
		} else if (taskRec == 10){
			hql += "from CaseAppointedInfo where taskRec =" + taskRec ;
		} else if (taskRec == -1) {
			hql += "from CaseAppointedInfo where 1 = 1";
		} 
		
		if (dealType == -1) { //查询全部
			hql += "";
		} else if (dealType == 0) { //查询未办理
			hql += " and dealType in (0, 9) ";
		} else { //查询已立案 和  不予立案 （10 ， 11）
			hql += " and dealType ="+ dealType;
		}
		
		int deptId = person.getDept().getUuid();
		if("系统管理员".equals(roleName)) {  
			
		}
		
		// 二.部门领导（部门负责人、部门副职）
		if(("部门负责人".equals(roleName) || "部门副职".equals(roleName))) {
			hql += " and organizationId = " + deptId;
		} 
		if ("工作人员".equals(roleName)) {
			hql += " and organizationId = " + deptId;
		}
		hql += " order by createTime ";
		return super.find(hql, null);
	}

	
}
