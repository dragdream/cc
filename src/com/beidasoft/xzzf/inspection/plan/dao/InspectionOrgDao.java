package com.beidasoft.xzzf.inspection.plan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionOrg;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class InspectionOrgDao extends TeeBaseDao<InspectionOrg>{
	/**
	 * 根据企业类型查询
	 * @param inspectionId
	 * @param orgType
	 * @return
	 */
	public List<InspectionOrg> getListByOrgType(String inspectionId,String orgType){
		List<InspectionOrg> list = super.find("from InspectionOrg where inspectionId = '"+inspectionId+"' and orgType like '%"+orgType+"%'", null);
		return list;
	}
	/**
	 * 根据计划主表id和企业id查找
	 * @param orgId
	 * @param inspectionId
	 * @return
	 */
	public InspectionOrg getObjByOrgId(String orgId,String inspectionId){
		List<InspectionOrg> list = super.find("from InspectionOrg where orgId = '"+orgId+"' and inspectionId = '"+inspectionId+"'", null);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据主表id查询列表
	 * @param inspectionId
	 * @return
	 */
	public List<InspectionOrg> getListByInsId(String inspectionId){
		return super.find("from InspectionOrg where inspectionId = '"+inspectionId+"'", null);
	}
	
	/**
	 * 根据计划主键查询对应列表(分页查询)
	 * @param inspectionId
	 * @return
	 */
	public List<InspectionOrg> getListByInspectionId(int firstResult,int rows,String inspectionId){
		return super.pageFind("from InspectionOrg where inspectionId = '"+inspectionId+"'",firstResult, rows, null);
	}
	
	/**
	 * 返回分页总记录数
	 * @return
	 */
	public Long getTotal(String inspectionId){
		String hql = "select count(id) from InspectionOrg where inspectionId = '"+inspectionId+"'";
		return super.count(hql, null);
	} 
}
