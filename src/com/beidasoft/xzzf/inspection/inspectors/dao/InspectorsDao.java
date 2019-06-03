package com.beidasoft.xzzf.inspection.inspectors.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.inspectors.bean.Inspectors;
import com.beidasoft.xzzf.inspection.inspectors.model.InspectorsModel;
import com.sun.org.apache.regexp.internal.recompile;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


@Repository
public class InspectorsDao extends TeeBaseDao<Inspectors> {
	
	/**
	 * 分页查询检查人员
	 * @param inspectorsModel
	 * @param dataGridModel
	 * @return
	 */
	public List<Inspectors> getInspectors(InspectorsModel inspectorsModel,TeeDataGridModel dataGridModel) {
		String hql = " from Inspectors Where 1=1 ";
		if (!TeeUtility.isNullorEmpty(inspectorsModel.getDepartmentName())) {
			hql += " and departmentName='" + inspectorsModel.getDepartmentName() + "'";
		}
		if (!TeeUtility.isNullorEmpty(inspectorsModel.getStaffName())) {
			hql += " and staffName like '%" + inspectorsModel.getStaffName() + "%'";
		}
		List<Inspectors> inspectors = super.pageFind(hql, dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
		return inspectors;
	}
	
	/**
	 * 查询检查人员
	 * @param inspectorsModel
	 * @return
	 */
	public List<Inspectors> getInspectors(InspectorsModel inspectorsModel) {
		String hql = " from Inspectors Where 1=1 ";
		if (!TeeUtility.isNullorEmpty(inspectorsModel.getDepartmentName())) {
			hql += " and departmentName='" + inspectorsModel.getDepartmentName() + "'";
		}
		List<Inspectors> inspectors = super.find(hql, null);
		return inspectors;
	}
	
	/**
	 * 按名字查询
	 * @param staffName
	 * @return
	 */
	public Inspectors getByName(String  staffName) {
		String hql = "from Inspectors Where staffName='" + staffName+ "' ";
		List<Inspectors> list= super.find(hql, null);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据部门Id查询
	 * @param departmentId
	 * @return
	 */
	public List<Inspectors> getByDeptId(int departmentId) {
		String hql = "from Inspectors Where departmentId='" + departmentId+ "' ";
		List<Inspectors> list = super.find(hql, null);
		return list;
	}
	
	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal() {
		return super.count("select count(id) from Inspectors", null);
	}
	
	/**
	 * 返回具备条件记录数
	 * @param inspectorsModel
	 * @return
	 */
	public long getTotal(InspectorsModel inspectorsModel) {
		String hql = "select count(id) from Inspectors Where 1=1 ";
		
		if (!TeeUtility.isNullorEmpty(inspectorsModel.getDepartmentName())) {
			hql += " and departmentName= '" + inspectorsModel.getDepartmentName() + "'";
		}
		if (!TeeUtility.isNullorEmpty(inspectorsModel.getStaffName())) {
			hql += " and staffName like '%" + inspectorsModel.getStaffName() + "%'";
		}
		return super.count(hql, null);
	}
	
	/**
	 * 根据Id查询数据库是否存在该人员
	 * @param hql
	 * @return
	 */
	public Inspectors getObjById(String hql){
		List<Inspectors> list = super.find(hql, null);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}


