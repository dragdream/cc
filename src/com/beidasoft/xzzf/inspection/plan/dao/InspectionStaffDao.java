package com.beidasoft.xzzf.inspection.plan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionStaff;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class InspectionStaffDao extends TeeBaseDao<InspectionStaff>{
	/**
	 * 根据部门id查询
	 * @param inspectionId
	 * @param deptId
	 * @return
	 */
	public List<InspectionStaff> getListByDeptId(String inspectionId,int deptId){
		return super.find("from InspectionStaff where inspectionId = '"+inspectionId+"' and deptId = '"+deptId+"'", null);
	}
	/**
	 * 根据计划主表id查询
	 * @param inspectionId
	 * @return
	 */
	public List<InspectionStaff> getListByInsId(String inspectionId){
		return super.find("from InspectionStaff where inspectionId = '"+inspectionId+"'", null);
	}
	/**
	 * 根据执法人员姓名和主表id查询
	 * @param inspectionId
	 * @param staffName
	 * @return
	 */
	public InspectionStaff getObjByInsIdAndStaffName(String inspectionId,String staffName){
		String hql = "from InspectionStaff where inspectionId = '"+inspectionId+"' and staffName = '"+staffName+"'";
		List<InspectionStaff> list = super.find(hql, null);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据人员表id和计划主表id查询
	 * @param inspectionId
	 * @param staffId
	 * @return
	 */
	public InspectionStaff getObjByinsIdAndUserId(String inspectionId,int staffId){
		String hql = "from InspectionStaff where inspectionId = '"+inspectionId+"' and staffId = "+staffId;
		List<InspectionStaff> list = super.find(hql, null);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 分页查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<InspectionStaff> listByPage(int firstResult,int rows,String inspectionId){
		return super.pageFind("from InspectionStaff where inspectionId = '"+inspectionId+"'", firstResult, rows, null);
	}
	/**
	 * 返回分页总记录数
	 * @return
	 */
	public Long getTotal(String inspectionId){
		return super.count("select count(id) from InspectionStaff where inspectionId = '"+inspectionId+"'", null);
	}
	
	
}
