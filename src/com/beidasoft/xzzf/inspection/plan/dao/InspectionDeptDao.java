package com.beidasoft.xzzf.inspection.plan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionDept;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;

@Repository
public class InspectionDeptDao extends TeeBaseDao<InspectionDept>{
	/**
	 * 根据主表id查询参检部门集合
	 * @param inspectionId
	 * @return
	 */
	public List<InspectionDept> getByinspectionId(String inspectionId){
		inspectionId = TeeDbUtility.formatString(inspectionId);
		return super.find("from InspectionDept where inspectionId = '"+inspectionId+"'",null);
	}
	/**
	 * 根据部门所查企业类型查询
	 * @param inspectionId
	 * @param departmentInspectType
	 * @return
	 */
	public List<InspectionDept> getListByDepartmentInspectType(String inspectionId,String departmentInspectType){
		return super.find("from InspectionDept where inspectionId = '"+inspectionId+"' and departmentInspectType like '%"+departmentInspectType+"%'", null);
	}
}
