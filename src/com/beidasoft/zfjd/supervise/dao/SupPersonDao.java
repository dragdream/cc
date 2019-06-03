package com.beidasoft.zfjd.supervise.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.supervise.bean.SupPerson;
import com.beidasoft.zfjd.supervise.bean.Supervise;
import com.beidasoft.zfjd.supervise.bean.SuperviseSupperson;
import com.beidasoft.zfjd.supervise.model.SupPersonModel;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 监督人员表DAO类
 */
@Repository
public class SupPersonDao extends TeeBaseDao<SupPerson> {
	
	@Autowired
	private TeeDeptDao dao;
		
	
	/**
	  * 分页
	  * @param firstResult
	  * @param rows
	  * @param queryModel
	  * @return
	  */
	  public List<SupPerson> listByPage(int firstResult,int rows,SupPersonModel queryModel){
 		  String hql = " from SupPerson where isDelete = 0";
		  if(!TeeUtility.isNullorEmpty(queryModel.getName()))
		  {
			  hql +=" and name like '%"+queryModel.getName()+"%'";  
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getPersonId())){
			  hql+=" and personId like '%"+queryModel.getPersonId()+"%'";
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getExamine())){
			  hql+=" and Examine ="+queryModel.getExamine();
		  }
		  hql+=" order by createTime desc ";
		return super.pageFind(hql, firstResult, rows, null);
		  
	  }
		  
	  public long getTotal(){
		  return super.count("select count(id) from SupPerson where  isDelete = 0",null);
	  }
	  /**
	   * 导航
	   * @param queryModel
	   * @return
	   */
	  public long getTotal(SupPersonModel queryModel){
		  String hql = "select count(id) from SupPerson where  isDelete = 0";
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getName()))
		  {
			  hql +="and name like '%"+queryModel.getName()+"%' ";  
		  }
		 
		  if(!TeeUtility.isNullorEmpty(queryModel.getPersonId())){
			  hql+=" and personId like '%"+queryModel.getPersonId()+"%'";
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getExamine())){
			  hql+=" and Examine ="+queryModel.getExamine();
		  }
		  
		   return super.count(hql,null);
	  }
	  
	 public List<SupPerson>  findUsers(){
		  return super.find("from SupPerson", null);	  
	 }
	 
	 /**
	   * 根据人员获取所属部门
	   */
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public List<Map> listByBus(int firstResult, int rows, String  id) {
	        String sql = " select id,name from tbl_base_supervise where is_delete = 0  and id = (select supervise_id from tbl_base_supervise_supperson where supperson_id = '"+id+"')";
	        return (List<Map>) super.executeNativeQuery(sql, null,firstResult,rows);
	    }
     /**
      * 多删
      */
	 public long deletes(String[] ids) {
			String hql = "";
			for(int i=0 ; i<ids.length;i++){
				SupPerson supPerson = new SupPerson();
				hql = ("update SupPerson  set isDelete = '1' where id = '"+ids[i]+"'");
				supPerson.setDeleteTime(new Date());
				this.executeUpdate(hql, null);
			}
			return this.executeUpdate(hql, null);
		}

	 /**
	  * 监督人员管理 权限控制分页
	  * @param start
	  * @param length
	  * @param cbModel
	  * @param orgCtrl
	  * select *
		  from tbl_base_supperson pe
		 where exists (select 1
		          from tbl_base_supervise_supperson vp
		         where exists (select 1
		                  from tbl_base_supervise sup
		                 where sup.administrative_division = '130000'
		                   and sup.id = vp.supervise_id
		                   and sup.is_delete = 0)
		           and pe.id = vp.supperson_id)
		 and pe.is_delete = 0
	  * @return
	  * @date:2019年3月6日上午9:13:40
	  * @author:yxy
	  */
	 @SuppressWarnings("unchecked")
	    public List<SupPerson> findListByPage(int start, int length, SupPersonModel cbModel, OrgCtrlInfoModel orgCtrl){
	        //获取session
	        Session session = super.getSession();
	        //主表tbl_base_supperson 取别名为 cBase
	        Criteria crit = session.createCriteria(SupPerson.class, "cBase");
	        // 设置查询结果为实体对象
	        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	        //第一层exists 表 tbl_base_supervise_supperson 取别名为 vp
	        DetachedCriteria subCrit = DetachedCriteria.forClass(SuperviseSupperson.class,"vp");
	        //第二层exists 表 tbl_base_supervise 取别名为 sup 
	        DetachedCriteria divisionCrit = DetachedCriteria.forClass(Supervise.class, "sup");
	        //设置表 tbl_base_supervise 和 表tbl_base_supervise_supperson关联的字段
	        divisionCrit.add(Property.forName("sup.id").eqProperty("vp.superviseId"));
	        //监督部门删除标志
	        divisionCrit.add(Restrictions.eq("sup.isDelete", 0));
	        //监督人员删除标志
	        crit.add(Restrictions.eq("cBase.isDelete", 0));
	        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
	        subCrit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("sup.id"))));
	        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
	        subCrit.add(Property.forName("cBase.id").eqProperty(Property.forName("vp.suppersonId")));
	        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
	        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("vp.id"))));
	        //是否是分级管理员（true是，false否）
	        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
	        //分级政府管理员和监督部门有权限
	        if(gradeAdministrator){
	        	//只能查看本级
		        divisionCrit.add(Restrictions.eq("sup.administrativeDivision",orgCtrl.getAdminDivisionCode()));
	        }else{
	        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
		        int orgType = orgCtrl.getOrgType();
		        if (1 == orgType) {
		        	//只能查看本级
			        divisionCrit.add(Restrictions.eq("sup.administrativeDivision",orgCtrl.getAdminDivisionCode()));
		        }else{
		        	return null;
		        }
	        }
	        //姓名
	        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
	            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
	        }
	        //身份证号
	        if (!TeeUtility.isNullorEmpty(cbModel.getPersonId())) {
	            crit.add(Restrictions.like("cBase.personId", cbModel.getPersonId().trim(),MatchMode.ANYWHERE));
	        }
	        //分页条件
	        crit.setFirstResult(start);
	        crit.setMaxResults(length);
	        //排序
	        crit.addOrder(Order.desc("cBase.updateTime"));
	        return crit.list();
	    }
	 /**
	  * 监督人员管理 权限控制  总数
	  * @param cbModel
	  * @param orgCtrl
	  * @return
	  * @date:2019年3月6日上午9:15:39
	  * @author:yxy
	  */
	 public long listCount(SupPersonModel cbModel, OrgCtrlInfoModel orgCtrl) {
	    	//获取session
		 Session session = super.getSession();
	        //主表tbl_base_supperson 取别名为 cBase
	        Criteria crit = session.createCriteria(SupPerson.class, "cBase");
	        // 设置查询结果为实体对象
	        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	        //第一层exists 表 tbl_base_supervise_supperson 取别名为 vp
	        DetachedCriteria subCrit = DetachedCriteria.forClass(SuperviseSupperson.class,"vp");
	        //第二层exists 表 tbl_base_supervise 取别名为 sup 
	        DetachedCriteria divisionCrit = DetachedCriteria.forClass(Supervise.class, "sup");
	        //设置表 tbl_base_supervise 和 表tbl_base_supervise_supperson关联的字段
	        divisionCrit.add(Property.forName("sup.id").eqProperty("vp.superviseId"));
	        //监督部门删除标志
	        divisionCrit.add(Restrictions.eq("sup.isDelete", 0));
	        //监督人员删除标志
	        crit.add(Restrictions.eq("cBase.isDelete", 0));
	        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
	        subCrit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("sup.id"))));
	        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
	        subCrit.add(Property.forName("cBase.id").eqProperty(Property.forName("vp.suppersonId")));
	        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
	        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("vp.id"))));
	        //是否是分级管理员（true是，false否）
	        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
	        //分级政府管理员和监督部门有权限
	        if(gradeAdministrator){
	        	//只能查看本级
		        divisionCrit.add(Restrictions.eq("sup.administrativeDivision",orgCtrl.getAdminDivisionCode()));
	        }else{
	        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
		        int orgType = orgCtrl.getOrgType();
		        if (1 == orgType) {
		        	//只能查看本级
			        divisionCrit.add(Restrictions.eq("sup.administrativeDivision",orgCtrl.getAdminDivisionCode()));
		        }else{
		        	Integer totalCount = 0;
		            return totalCount.intValue();
		        }
	        }
	        //姓名
	        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
	            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
	        }
	        //身份证号
	        if (!TeeUtility.isNullorEmpty(cbModel.getPersonId())) {
	            crit.add(Restrictions.like("cBase.personId", cbModel.getPersonId().trim(),MatchMode.ANYWHERE));
	        }
	        crit.setProjection(Projections.rowCount());
	        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
	        return totalCount.intValue();
	    }
	 
	 /**
	  * 监督人员查询 权限管理  分页
	  * @param start
	  * @param length
	  * @param cbModel
	  * @param orgCtrl
	  * @return
	  * @date:2019年3月6日上午10:47:38
	  * @author:yxy
	  */
	 @SuppressWarnings("unchecked")
	    public List<SupPerson> findSearchListBypage(int start, int length, SupPersonModel cbModel, OrgCtrlInfoModel orgCtrl){
	        //获取session
	        Session session = super.getSession();
	        //主表tbl_base_supperson 取别名为 cBase
	        Criteria crit = session.createCriteria(SupPerson.class, "cBase");
	        // 设置查询结果为实体对象
	        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	        //第一层exists 表 tbl_base_supervise_supperson 取别名为 vp
	        DetachedCriteria subCrit = DetachedCriteria.forClass(SuperviseSupperson.class,"vp");
	        //第二层exists 表 tbl_base_supervise 取别名为 sup 
	        DetachedCriteria divisionCrit = DetachedCriteria.forClass(Supervise.class, "sup");
	        //第三层exists 表 TBL_ADMIN_DIVISION_DIVIDED 取别名为 division 
	        DetachedCriteria divisionCrits = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
	        //设置区划权限表 TBL_ADMIN_DIVISION_DIVIDED 和 主体表tbl_base_subject关联的字段
	        divisionCrits.add(Property.forName("division.adminDivisionCode").eqProperty("sup.administrativeDivision"));
	        //层级代码（002--省級层级编码code,003--市州級层级编码code,004--区县层级code）
	        String levelCode = orgCtrl.getLevelCode();
	        //行政区划
	        String adminDivisionCode = orgCtrl.getAdminDivisionCode();
	        //通过层级代码，控制数据查询范围 
	        if(!TeeUtility.isNullorEmpty(levelCode)){
	            if("0200".equals(levelCode)){
	                // 控制省級层级，及省级以下层级
	                divisionCrits.add(Restrictions.eq("provincialCode", adminDivisionCode));
	            }
	            if("0300".equals(levelCode)){
	                // 控制市州級层级，及市州以下层级
	                divisionCrits.add(Restrictions.eq("cityCode", adminDivisionCode));
	            }
	            if("0400".equals(levelCode)){
	                // 控制区县层级
	                divisionCrits.add(Restrictions.eq("districtCode", adminDivisionCode));
	            }
	            if("0500".equals(levelCode)){
	                // 控制乡镇街道层级
	                divisionCrit.add(Restrictions.eq("streetCode", adminDivisionCode));
	            }
	        }
	        //设置表 tbl_base_supervise 和 表tbl_base_supervise_supperson关联的字段
	        divisionCrit.add(Property.forName("sup.id").eqProperty("vp.superviseId"));
	        //监督部门删除标志
	        divisionCrit.add(Restrictions.eq("sup.isDelete", 0));
	        //监督人员删除标志
	        crit.add(Restrictions.eq("cBase.isDelete", 0));
	        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
	        divisionCrit.add(Subqueries.exists(divisionCrits.setProjection(Projections.property("division.adminDivisionCode"))));
	        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
	        subCrit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("sup.id"))));
	        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
	        subCrit.add(Property.forName("cBase.id").eqProperty(Property.forName("vp.suppersonId")));
	        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
	        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("vp.id"))));
	        //是否是分级管理员（true是，false否）
	        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
	        //分级政府管理员和监督部门有权限
	        if(!gradeAdministrator){
	        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
		        int orgType = orgCtrl.getOrgType();
		        if (1 != orgType) {
		        	return null;
		        }
	        }
	        //姓名
	        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
	            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
	        }
	        //身份证号
	        if (!TeeUtility.isNullorEmpty(cbModel.getPersonId())) {
	            crit.add(Restrictions.like("cBase.personId", cbModel.getPersonId().trim(),MatchMode.ANYWHERE));
	        }
	        //分页条件
	        crit.setFirstResult(start);
	        crit.setMaxResults(length);
	        //排序
	        crit.addOrder(Order.desc("cBase.updateTime"));
	        return crit.list();
	    }
	 
	 /**
	  * 监督人员查询 权限管理 总数
	  * @param cbModel
	  * @param orgCtrl
	  * @return
	  * @date:2019年3月6日上午10:48:22
	  * @author:yxy
	  */
	 public long listSearchCount(SupPersonModel cbModel, OrgCtrlInfoModel orgCtrl) {
	    	//获取session
		 Session session = super.getSession();
	        //主表tbl_base_supperson 取别名为 cBase
	        Criteria crit = session.createCriteria(SupPerson.class, "cBase");
	        // 设置查询结果为实体对象
	        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	        //第一层exists 表 tbl_base_supervise_supperson 取别名为 vp
	        DetachedCriteria subCrit = DetachedCriteria.forClass(SuperviseSupperson.class,"vp");
	        //第二层exists 表 tbl_base_supervise 取别名为 sup 
	        DetachedCriteria divisionCrit = DetachedCriteria.forClass(Supervise.class, "sup");
	        //第三层exists 表 TBL_ADMIN_DIVISION_DIVIDED 取别名为 division 
	        DetachedCriteria divisionCrits = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
	        //设置区划权限表 TBL_ADMIN_DIVISION_DIVIDED 和 主体表tbl_base_subject关联的字段
	        divisionCrits.add(Property.forName("division.adminDivisionCode").eqProperty("sup.administrativeDivision"));
	        //层级代码（002--省級层级编码code,003--市州級层级编码code,004--区县层级code）
	        String levelCode = orgCtrl.getLevelCode();
	        //行政区划
	        String adminDivisionCode = orgCtrl.getAdminDivisionCode();
	        //通过层级代码，控制数据查询范围 
	        if(!TeeUtility.isNullorEmpty(levelCode)){
	            if("0200".equals(levelCode)){
	                // 控制省級层级，及省级以下层级
	                divisionCrits.add(Restrictions.eq("provincialCode", adminDivisionCode));
	            }
	            if("0300".equals(levelCode)){
	                // 控制市州級层级，及市州以下层级
	                divisionCrits.add(Restrictions.eq("cityCode", adminDivisionCode));
	            }
	            if("0400".equals(levelCode)){
	                // 控制区县层级
	                divisionCrits.add(Restrictions.eq("districtCode", adminDivisionCode));
	            }
	            if("0500".equals(levelCode)){
	                // 控制乡镇街道层级
	                divisionCrit.add(Restrictions.eq("streetCode", adminDivisionCode));
	            }
	        }
	        //设置表 tbl_base_supervise 和 表tbl_base_supervise_supperson关联的字段
	        divisionCrit.add(Property.forName("sup.id").eqProperty("vp.superviseId"));
	        //监督部门删除标志
	        divisionCrit.add(Restrictions.eq("sup.isDelete", 0));
	        //监督人员删除标志
	        crit.add(Restrictions.eq("cBase.isDelete", 0));
	        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
	        divisionCrit.add(Subqueries.exists(divisionCrits.setProjection(Projections.property("division.adminDivisionCode"))));
	        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
	        subCrit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("sup.id"))));
	        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
	        subCrit.add(Property.forName("cBase.id").eqProperty(Property.forName("vp.suppersonId")));
	        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
	        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("vp.id"))));
	        //是否是分级管理员（true是，false否）
	        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
	        //分级政府管理员和监督部门有权限
	        if(!gradeAdministrator){
	        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
		        int orgType = orgCtrl.getOrgType();
		        if (1 != orgType) {
		        	Integer totalCount = 0;
		            return totalCount.intValue();
		        }
	        }
	        //姓名
	        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
	            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
	        }
	        //身份证号
	        if (!TeeUtility.isNullorEmpty(cbModel.getPersonId())) {
	            crit.add(Restrictions.like("cBase.personId", cbModel.getPersonId().trim(),MatchMode.ANYWHERE));
	        }
	        crit.setProjection(Projections.rowCount());
	        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
	        return totalCount.intValue();
	    }
	 
	 /**
	  * 根据ID获取用户的UUID
	  * @param id
	  * @return
	  * @date:2019年3月12日下午6:04:36
	  * @author:yxy
	  */
	 public Map<String, Object> getUuid(String id){
	    	String sql = "select user_uuid id from tbl_sys_person_user where is_delete = 0 and supperson_id = '"+id+"'";
			return   super.executeNativeUnique(sql, null);
	 }
	 /**
	  * 监督人员根据所属部门获取系统部门uuid
	  * @param id
	  * @return
	  * @date:2019年3月14日下午9:06:22
	  * @author:yxy
	  */
	 public Map<String, Object> deptUuid(String id){
	    	String sql = "select sys_org_id id from tbl_sys_bussiness_relation where business_sup_dept_id =(select supervise_id from tbl_base_supervise_supperson where supperson_id = '"+id+"') and rownum = 1";
			return   super.executeNativeUnique(sql, null);
	    }
	 
	 /**
	  * 获取监督人员的权限组id
	  * @return
	  * @date:2019年3月15日上午10:13:35
	  * @author:yxy
	  */
	 public Map<String, Object> zfjdId(){
	    	String sql = "select uuid id from menu_group where menu_group_name = '监督人员zfjd'";
			return   super.executeNativeUnique(sql, null);
	    }
}
