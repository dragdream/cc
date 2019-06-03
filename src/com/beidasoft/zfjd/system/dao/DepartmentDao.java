package com.beidasoft.zfjd.system.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.system.bean.Department;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.beidasoft.zfjd.system.model.DepartmentModel;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 系统部门表DAO类
 */
@Repository
public class DepartmentDao extends TeeBaseDao<Department> {
	
	@Autowired
	 private TeeDeptDao dao;
		
	 public List<Department>  findUsers(){
		  return super.find("from Department", null);	  
		  }	 
	 
    public List<Department> listByPage(int firstResult,int rows,DepartmentModel queryModel){
			  String hql = " from Department where 1=1 ";
			  
			return super.pageFind(hql, firstResult, rows, null);
		  }		 
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> listByPage2(int firstResult,int rows,DepartmentModel queryModel){
		  String sql = "select "
			  	+ "a.uuid as uuid,"
		  		+ "a.dept_name as deptName,"
		  		+ "b.business_dept_name as businessDeptName,"
		  		+ "b.business_sup_dept_name as businessSupDeptName,"
		  		+ "b.business_subject_name as businessSubjectName"
		  		+ " from department a LEFT JOIN tbl_sys_bussiness_relation b on a.uuid=b.sys_org_id where 1=1 ";
		  if(!TeeUtility.isNullorEmpty(queryModel.getDeptName())){
			  sql+=" and a.dept_name like '%"+queryModel.getDeptName()+"%'";
		  }
		  if(!TeeUtility.isNullorEmpty(queryModel.getBusinessDeptName())){
			  sql+=" and b.business_dept_name like '%"+queryModel.getBusinessDeptName()+"%'";
		  }
		  if(!TeeUtility.isNullorEmpty(queryModel.getBusinessSubjectName())){
			  sql+=" and b.business_subject_name like '%"+queryModel.getBusinessSubjectName()+"%'";
		  }
		  if(!TeeUtility.isNullorEmpty(queryModel.getBusinessSupDeptName())){
			  sql+=" and b.business_sup_dept_name like '%"+queryModel.getBusinessSupDeptName()+"%'";
		  }
		  	sql+=" order by sys_org_id desc ";
		  return  (List<Map>) super.executeNativeQuery(sql, null , firstResult,rows );
	  }
    
	public long getTotal(){
			  return super.count("select count(id) from Department",null);
		  }
		  
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public long getTotal(DepartmentModel queryModel){
		//查询SQL字符串
        StringBuffer hql = new StringBuffer();
        //参数集合
        ArrayList list = new ArrayList();
//        hql.append(" select count(uuid) from Department a where exists (select 1 from department d where exists (select 1 from tbl_sys_bussiness_relation b where d.uuid = b.sys_org_id)) ");
        hql.append(" select count(uuid) from Department where 1=1 ");
        
        if (!TeeUtility.isNullorEmpty(queryModel.getDeptName())) {
            hql.append(" and a.dept_name like ? ");
            list.add("%" + queryModel.getDeptName().trim() + "%");
        }
        if (!TeeUtility.isNullorEmpty(queryModel.getBusinessDeptName())) {
            hql.append(" and b.business_dept_name like ? ");
            list.add("%" + queryModel.getBusinessDeptName().trim() + "%");
        }
        if (!TeeUtility.isNullorEmpty(queryModel.getBusinessSubjectName())) {
            hql.append(" and b.business_subject_name like ? ");
            list.add("%" + queryModel.getBusinessSubjectName().trim() + "%");
        }
        if (!TeeUtility.isNullorEmpty(queryModel.getBusinessSupDeptName())) {
            hql.append(" and b.business_sup_dept_name like ? ");
            list.add("%" + queryModel.getDeptName().trim() + "%");
        }
        Object[] params = list.toArray();
        return super.count(hql.toString(), params);
	}
    
    /**
     * 监督业务管理 分页
     * @param start
     * @param length
     * @param cbModel
     * @param orgCtrl
     * select count(uuid) from department
		 where exists (select 1 from department a
	         where exists (select 1 from tbl_sys_bussiness_relation b
	                 where a.uuid = b.sys_org_id))
     * @return
     * @date:2019年3月7日下午7:55:27
     * @author:yxy
     */
    @SuppressWarnings("unchecked")
    public List<Department> findSearchListBypage(int start, int length, DepartmentModel cbModel, OrgCtrlInfoModel orgCtrl){
        //获取session
        Session session = super.getSession();
        //主表tbl_base_supperson 取别名为 cBase
        Criteria crit = session.createCriteria(Department.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //第一层exists 表 tbl_base_supervise_supperson 取别名为 vp
        DetachedCriteria deptCrit = DetachedCriteria.forClass(Department.class,"dept");
        //第二层exists 表 tbl_base_supervise 取别名为 sup 
        DetachedCriteria sysCrit = DetachedCriteria.forClass(SysBusinessRelation.class, "sys");
        //设置表 tbl_base_supervise 和 表tbl_base_supervise_supperson关联的字段
        deptCrit.add(Property.forName("dept.uuid").eqProperty("sys.sysOrgId"));
        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
        deptCrit.add(Subqueries.exists(sysCrit.setProjection(Projections.property("sys.id"))));
        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
//        crit.add(Subqueries.exists(deptCrit.setProjection(Projections.property("dept.uuid"))));
        //查询条件
        if (!TeeUtility.isNullorEmpty(cbModel.getDeptName())) {
            crit.add(Restrictions.like("cBase.deptName", cbModel.getDeptName().trim(),MatchMode.ANYWHERE));
        }
        if (!TeeUtility.isNullorEmpty(cbModel.getBusinessDeptName())) {
            crit.add(Restrictions.like("cBase.businessDeptName", cbModel.getBusinessDeptName().trim(),MatchMode.ANYWHERE));
        }
        if (!TeeUtility.isNullorEmpty(cbModel.getBusinessSubjectName())) {
            crit.add(Restrictions.like("cBase.businessSubjectName", cbModel.getBusinessSubjectName().trim(),MatchMode.ANYWHERE));
        }
        if (!TeeUtility.isNullorEmpty(cbModel.getBusinessSupDeptName())) {
            crit.add(Restrictions.like("cBase.businessSupDeptName", cbModel.getBusinessSupDeptName().trim(),MatchMode.ANYWHERE));
        }
        //分页条件
        crit.setFirstResult(start);
        crit.setMaxResults(length);
        //排序
        return crit.list();
    }
    
    /**
     * 监督业务管理  总数
     * @param cbModel
     * @param orgCtrl
     * @return
     * @date:2019年3月7日下午7:56:00
     * @author:yxy
     */
    public long listSearchCount(DepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
    	//获取session
    	Session session = super.getSession();
        //主表tbl_base_supperson 取别名为 cBase
        Criteria crit = session.createCriteria(Department.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //第一层exists 表 tbl_base_supervise_supperson 取别名为 vp
        DetachedCriteria deptCrit = DetachedCriteria.forClass(Department.class,"dept");
        //第二层exists 表 tbl_base_supervise 取别名为 sup 
        DetachedCriteria sysCrit = DetachedCriteria.forClass(SysBusinessRelation.class, "sys");
        //设置表 tbl_base_supervise 和 表tbl_base_supervise_supperson关联的字段
        deptCrit.add(Property.forName("dept.uuid").eqProperty("sys.sysOrgId"));
        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
        deptCrit.add(Subqueries.exists(sysCrit.setProjection(Projections.property("sys.id"))));
        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
//        crit.add(Subqueries.exists(deptCrit.setProjection(Projections.property("dept.uuid"))));
        //查询条件
        if (!TeeUtility.isNullorEmpty(cbModel.getDeptName())) {
            crit.add(Restrictions.like("cBase.deptName", cbModel.getDeptName().trim(),MatchMode.ANYWHERE));
        }
        if (!TeeUtility.isNullorEmpty(cbModel.getBusinessDeptName())) {
            crit.add(Restrictions.like("cBase.businessDeptName", cbModel.getBusinessDeptName().trim(),MatchMode.ANYWHERE));
        }
        if (!TeeUtility.isNullorEmpty(cbModel.getBusinessSubjectName())) {
            crit.add(Restrictions.like("cBase.businessSubjectName", cbModel.getBusinessSubjectName().trim(),MatchMode.ANYWHERE));
        }
        if (!TeeUtility.isNullorEmpty(cbModel.getBusinessSupDeptName())) {
            crit.add(Restrictions.like("cBase.businessSupDeptName", cbModel.getBusinessSupDeptName().trim(),MatchMode.ANYWHERE));
        }
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.intValue();
    }
}
