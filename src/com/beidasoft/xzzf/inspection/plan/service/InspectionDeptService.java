package com.beidasoft.xzzf.inspection.plan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.inspectors.bean.Inspectors;
import com.beidasoft.xzzf.inspection.inspectors.service.InspectorsService;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionDept;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionDeptDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class InspectionDeptService extends TeeBaseService{
	@Autowired
	InspectionDeptDao inspectionDeptDao;
	@Autowired
	InspectorsService inspectorsService;
	@Autowired
	TeeDeptDao deptDao;
	/**
	 * 新增/修改计划执行部门
	 * @param deptIds
	 * @param inspectionId
	 * @return
	 */
	public TeeJson saveDept(String deptIds,String inspectionId){
		TeeJson json = new TeeJson();
		//删除主表关联的所有数据
		if (!TeeUtility.isNullorEmpty(inspectionId)) {
			List<InspectionDept> list = inspectionDeptDao.getByinspectionId(inspectionId);
			if (!TeeUtility.isNullorEmpty(list)) {
				for (InspectionDept inspectionDept : list) {
					inspectionDeptDao.deleteByObj(inspectionDept);
				}
			}
		} else {
			return json;
		}
		//添加执行计划部门
		if (!TeeUtility.isNullorEmpty(deptIds)) {
			String[] deptIdArr = deptIds.split(",");
			for (String deptId : deptIdArr) {
				TeeDepartment dept = deptDao.get(TeeStringUtil.getInteger(deptId, 0));
				if (!TeeUtility.isNullorEmpty(dept)) {
					List<Inspectors> inspectors = inspectorsService.getByDeptId(TeeStringUtil.getInteger(deptId, 0));
					InspectionDept insDept = new InspectionDept();
					insDept.setId(TeeStringUtil.getString(UUID.randomUUID()));
					insDept.setInspectionId(inspectionId);
					insDept.setExecuteDepartment(dept.getUuid());
					insDept.setExecuteDepartmentName(dept.getDeptName());
					insDept.setExecuteStatus(0);
					insDept.setDepartmentInspectType(inspectors.get(0).getDepartmentInspectType());
					inspectionDeptDao.save(insDept);
					json.setRtState(true);
				}
			}
		}
		return json;
	}
	/**
	 * 查询检查计划执行部门信息
	 * @param inspectionId
	 * @return
	 */
	public TeeJson getDeptInfo(String inspectionId){
		TeeJson json = new TeeJson();
		List<InspectionDept> list = inspectionDeptDao.getByinspectionId(inspectionId);
		Map<String, String> map = new HashMap<>();
		String deptIds = "";
		String deptNames = "";
		if (!TeeUtility.isNullorEmpty(list)) {
			for (int i = 0 ; i<list.size() ; i++) {
				deptIds+=list.get(i).getExecuteDepartment()+(i<list.size()-1?",":"");
				deptNames+=list.get(i).getExecuteDepartmentName()+(i<list.size()-1?"、":"");
			}
			map.put("deptIds", deptIds);
			map.put("deptNames", deptNames);
			json.setRtData(map);
			json.setRtState(true);
		}
		return json;
	}
}
