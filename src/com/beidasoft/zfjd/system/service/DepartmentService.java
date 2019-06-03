package com.beidasoft.zfjd.system.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.system.bean.Department;
import com.beidasoft.zfjd.system.dao.DepartmentDao;
import com.beidasoft.zfjd.system.model.DepartmentModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 系统部门表SERVICE类
 */
@Service
public class DepartmentService extends TeeBaseService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private SysBusinessRelationService sysBusinessRelationService;
	/*
	 * 保存用户信息
	 */
	public void save(Department department) {
		departmentDao.save(department);
	}

	/*
	 * 更新用户信息
	 */
	public TeeJson update(HttpServletRequest request,DepartmentModel departmentModel) {
		TeeJson json = new TeeJson();
		//判断是否存在该数据 有则更新 无则新增
		Integer a =simpleDaoSupport.executeNativeUpdate("select sys_org_id from tbl_sys_bussiness_relation where sys_org_id =?", new Object[]{departmentModel.getUuid()});
		if (a==0){
			//使用本地sql插入
			departmentModel.setId(UUID.randomUUID().toString());
			simpleDaoSupport.executeNativeUpdate("insert into tbl_sys_bussiness_relation ("
					+ "id,"
					+ "sys_org_id,"
					+ "sys_org_name,"
					+ "business_dept_id,"
					+ "business_dept_name,"
					+ "business_sup_dept_id,"
					+ "business_sup_dept_name,"
					+ "business_subject_id,"
					+ "business_subject_name,"
					+ "org_type)"
					+ "values (?,?,?,?,?,?,?,?,?,?)",
					new Object[]{
							departmentModel.getId(),
							departmentModel.getUuid(),
							departmentModel.getDeptName(),
							departmentModel.getBusinessDeptId(),
							departmentModel.getBusinessDeptName(),
							departmentModel.getBusinessSupDeptId(),
							departmentModel.getBusinessSupDeptName(),
							departmentModel.getBusinessSubjectId(),
							departmentModel.getBusinessSubjectName(),
							departmentModel.getOrgType()});
		}else{
			//使用本地sql更新
			simpleDaoSupport.executeNativeUpdate("update tbl_sys_bussiness_relation "
					+ "set business_dept_id=?,"
					+ "business_dept_name=?,"
					+ "business_sup_dept_id=?,"
					+ "business_sup_dept_name=?,"
					+ "business_subject_id=?,"
					+ "business_subject_name=?,"
					+ "org_type=? where sys_org_id=?",
					new Object[]{
							departmentModel.getBusinessDeptId(),
							departmentModel.getBusinessDeptName(),
							departmentModel.getBusinessSupDeptId(),
							departmentModel.getBusinessSupDeptName(),
							departmentModel.getBusinessSubjectId(),
							departmentModel.getBusinessSubjectName(),
							departmentModel.getOrgType(),
							departmentModel.getUuid()});
		}
		//departmentDao.update(department);
		json.setRtState(true);
		return json;
	}

	/*
	 * 删除关联关系
	 */
	public TeeJson delete(HttpServletRequest request,DepartmentModel departmentModel) {
		TeeJson json = new TeeJson();
			simpleDaoSupport.executeNativeUpdate("delete from tbl_sys_bussiness_relation where sys_org_id =?",
					new Object[]{ departmentModel.getUuid() });
		json.setRtState(true);
		return json;
	}
	
	/*
	 * 通过对象删除用户信息
	 */
	public void deleteByObject(Department department) {
		departmentDao.deleteByObj(department);
	}

	/*
	 * 通过主键删除用户信息
	 */
	public void deleteById(String id) {

		departmentDao.delete(id);
	}

	/*
	 * 通过主键查询用户信息
	 */
	public Department getById(Integer id) {
		return departmentDao.get(id);
	}

	/*
	 * 返回所有用户信息
	 */
	public List<Department> getAllUsers() {
		return departmentDao.findUsers();
	}

	/*
	 * 根据分页进行查询
	 */
	public List<Department> listByPage(int firstResult, int rows) {
		return departmentDao.listByPage(firstResult, rows, null);
	}

	public List<Department> listByPage(int firstResult, int rows, DepartmentModel queryModel) {
		return departmentDao.listByPage(firstResult, rows, queryModel);

	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> listByPage2(int firstResult, int rows, DepartmentModel queryModel) {
		return departmentDao.listByPage2(firstResult, rows, queryModel);
	}

	public List<Department> findSearchListBypage(TeeDataGridModel tModel, DepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
	       return departmentDao.findSearchListBypage(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
	   }

	public long listSearchCount(DepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
       return departmentDao.listSearchCount(cbModel, orgCtrl);
   }
	
	public long getTotal() {
		return departmentDao.getTotal();
	}

	public long getTotal(DepartmentModel queryModel) {
		return departmentDao.getTotal(queryModel);
	}

}
