package com.beidasoft.xzzf.inspection.plan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionTask;
import com.beidasoft.xzzf.inspection.plan.model.InspectionTaskModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class InspectionTaskDao extends TeeBaseDao<InspectionTask>{
	/**
	 * 根据计划id查询
	 * @param id
	 * @return
	 */
	public InspectionTask getObjById(String id){
		List<InspectionTask> list = super.find("from InspectionTask where id = '"+id+"'", null);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据主表id查询
	 * @param inspectionId
	 * @return
	 */
	public List<InspectionTask> getListByInsId(String inspectionId){
		return super.find("from InspectionTask where inspectionId = '"+inspectionId+"'", null);
	}
	/**
	 * 根据主表id分页查询
	 * @param firstResult
	 * @param rows
	 * @param inspectionId
	 * @return
	 */
	public List<InspectionTask> getListByPage(int firstResult,int rows,InspectionTaskModel taskModel){
		String hql = "from InspectionTask where 1 = 1 ";
		if (!"".equals(taskModel.getOrgName())&&!TeeUtility.isNullorEmpty(taskModel.getOrgName())) {
			hql += " and orgName like '%"+taskModel.getOrgName()+"%' ";
		}
		if (taskModel.getIsFiling() != 0) {
			hql += " and isFiling = '"+taskModel.getIsFiling()+"' ";
		}
		if (taskModel.getResult() != 0) {
			hql += " and result = '"+taskModel.getResult()+"' ";
		}
		if (taskModel.getStatus() != -1) {
			hql += " and status = '"+taskModel.getStatus()+"' ";
		}
		if (!"".equals(taskModel.getInspectionId())&&!TeeUtility.isNullorEmpty(taskModel.getInspectionId())) {
			hql += " and inspectionId = '"+taskModel.getInspectionId()+"' ";
		}
		if (taskModel.getDeptUuid() != 0) {
			hql += " and deptId = '"+taskModel.getDeptUuid()+"' ";
		}
		return super.pageFind(hql , firstResult , rows , null);
	}
	/**
	 * 返回总记录数
	 * @param inspectionId
	 * @return
	 */
	public long getTotal(InspectionTaskModel taskModel){
		String hql = "select count(id) from InspectionTask where 1 = 1 ";
		if (!"".equals(taskModel.getOrgName())&&!TeeUtility.isNullorEmpty(taskModel.getOrgName())) {
			hql += " and orgName like '%"+taskModel.getOrgName()+"%' ";
		}
		if (taskModel.getIsFiling() != 0) {
			hql += " and isFiling = '"+taskModel.getIsFiling()+"' ";
		}
		if (taskModel.getResult() != 0) {
			hql += " and result = '"+taskModel.getResult()+"' ";
		}
		if (taskModel.getStatus() != -1) {
			hql += " and status = '"+taskModel.getStatus()+"' ";
		}
		if (!"".equals(taskModel.getInspectionId())&&!TeeUtility.isNullorEmpty(taskModel.getInspectionId())) {
			hql += " and inspectionId = '"+taskModel.getInspectionId()+"' ";
		}
		return super.count(hql , null);
	}
	/**
	 * 根据主表id删除相关所有任务
	 * @param inspectionId
	 * @return
	 */
	public TeeJson delListByInsId(String inspectionId){
		TeeJson json = new TeeJson();
		List<InspectionTask> taskList = getListByInsId(inspectionId);
		if (!taskList.isEmpty()) {
			for (InspectionTask inspectionTask : taskList) {
				super.deleteByObj(inspectionTask);
				json.setRtState(true);
			}
		}
		return json;
	}
}
