package com.beidasoft.zfjd.supervise.dao;

import java.util.ArrayList;
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
import com.beidasoft.zfjd.supervise.bean.Supervise;
import com.beidasoft.zfjd.supervise.model.SuperviseModel;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class SuperviseDao extends TeeBaseDao<Supervise>{

	@Autowired
	private TeeDeptDao dao;
		
	 public List<Supervise>  findUsers(){
		  return super.find("from Supervise", null);	  
		  }
	 /**
	  * 获取监督部门名称 权限控制
	  * @param firstResult
	  * @param rows
	  * @param queryModel
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	public List<Supervise> listByDe(int firstResult,int rows,SuperviseModel queryModel,OrgCtrlInfoModel orgCtrl){
		 Object[] object = new Object[2];
		 StringBuffer hql = new StringBuffer("from Supervise where isDelete = 0 and isExamine = 1 ");
	     List<Object> params = new ArrayList<Object>();
		 if(!TeeUtility.isNullorEmpty(queryModel.getName())){
		     hql.append(" and name like ? ");
	         params.add("%" + queryModel.getName().trim() + "%");
	         if(!TeeUtility.isNullorEmpty(orgCtrl.getAdminDivisionCode())){
	        	 hql.append(" and administrativeDivision = ? ");
		         params.add(orgCtrl.getAdminDivisionCode());
	         }
	         object[0] = hql;
	         object[1] = params;
	         List<Supervise> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
	         return list;
		 }else{
			  if (!TeeUtility.isNullorEmpty(queryModel.getParentId()))
			  {		
				  hql.append(" and id = ? ");
			      params.add(queryModel.getParentId().trim());
			      if(!TeeUtility.isNullorEmpty(orgCtrl.getAdminDivisionCode())){
		        	 hql.append(" and administrativeDivision = ? ");
			         params.add(orgCtrl.getAdminDivisionCode());
			      }
			      object[0] = hql;
			      object[1] = params;
				  List<Supervise> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
				  return list;
			  }
		 }
		return null;
	  }
	 
	  /**
	   * 获取监督部门名称  全部
	   * @param firstResult
	   * @param rows
	   * @param queryModel
	   * @param orgCtrl
	   * @return
	   * @date:2019年3月14日下午3:47:30
	   * @author:yxy
	   */
	  @SuppressWarnings("unchecked")
		public List<Supervise> listByDeAll(int firstResult,int rows,SuperviseModel queryModel){
			 if(!TeeUtility.isNullorEmpty(queryModel.getName())){
				 Object[] object = new Object[2];
				 StringBuffer hql = new StringBuffer("from Supervise where isDelete = 0 ");
			     List<Object> params = new ArrayList<Object>();
			     hql.append(" and name like ? ");
		         params.add("%" + queryModel.getName().trim() + "%");
		         object[0] = hql;
		         object[1] = params;
		         List<Supervise> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
		         return list;
			 }else{
				  if (!TeeUtility.isNullorEmpty(queryModel.getParentId()))
				  {		
					  Object[] object = new Object[2];
					  StringBuffer hql = new StringBuffer("from Supervise where isDelete = 0 ");
					  List<Object> params = new ArrayList<Object>();
					  hql.append(" and id = ? ");
				      params.add(queryModel.getParentId().trim());
				      object[0] = hql;
				      object[1] = params;
					  List<Supervise> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
					  return list;
				  }
			 }
			return null;
		  }
	  
     public List<Supervise> listByPage(int firstResult,int rows,SuperviseModel queryModel){
			  String hql = " from Supervise where isDelete = 0";
			  
			  if (!TeeUtility.isNullorEmpty(queryModel.getName()))
			  {
				  hql +=" and name like '%"+queryModel.getName()+"%'";  
			  }
			  if(!TeeUtility.isNullorEmpty(queryModel.getNature())){
				  hql+=" and nature = "+queryModel.getNature();
			  }
			  if(!TeeUtility.isNullorEmpty(queryModel.getAdministrativeDivision())){
				  hql+=" and administrativeDivision = "+queryModel.getAdministrativeDivision();
			  }
			  
			  hql+=" order by createTime desc ";
			return super.pageFind(hql, firstResult, rows, null);
			  
		  }		  
	public long getTotal(){
			  return super.count("select count(id) from Supervise",null);
		  }
		  
	public long getTotal(SuperviseModel queryModel){
			  String hql = "select count(id) from Supervise where isDelete = 0";
			  
			  
			  if (!TeeUtility.isNullorEmpty(queryModel.getName()))
			  {
				  hql +=" and name like '%"+queryModel.getName()+"%'";  
			  }
			  if(!TeeUtility.isNullorEmpty(queryModel.getNature())){
				  hql+=" and nature = "+queryModel.getNature();
			  }
			  if(!TeeUtility.isNullorEmpty(queryModel.getIsExamine())){
				  hql+=" and administrativeDivision = "+queryModel.getAdministrativeDivision();
			  }
			   return super.count(hql,null);
		  }
	
	public long deletes(String[] ids) {
		// TODO Auto-generated method stub
		String hql = "";
		for(int i=0 ; i<ids.length;i++){
			Supervise supervise = new Supervise();
			hql = ("update Supervise set isDelete = '1' where id = '"+ids[i]+"'");
			supervise.setDeleteTime(new Date());
			this.executeUpdate(hql, null);
		}
		return this.executeUpdate(hql, null);
	}
	/**
     * 监督部门查询 分页
    * @Description: 通过区县权限表 TBL_ADMIN_DIVISION_DIVIDED，
    * *系统关系表TBL_SYS_BUSSINESS_RELATION，组合控制数据查询权限
    * @param: start 分页起始数
    * @param: length 每页显示条数
    * @param: cbModel 参数
    * @param: orgCtrl 权限控制
    * @throws：异常描述
    *	select * from tbl_base_supervise sup where exists (
	       select 1 from TBL_ADMIN_DIVISION_DIVIDED where PROVINCIAL_CODE = '520000' 
	       and  admin_division_Code = sup.administrative_division)
	       and sup.is_delete = 0
    * @author: yxy
    * @date: 2019年3月5日 下午2:20:52 
    *
     */
	@SuppressWarnings("unchecked")
    public List<Supervise> findListByPageSearch(int start, int length, SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl){
        //参考上述SQL理解下面代码逻辑。
        //获取session
        Session session = super.getSession();
        //主表tbl_base_supervise 取别名为 cBase
        Criteria crit = session.createCriteria(Supervise.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //第二层exists 表 TBL_ADMIN_DIVISION_DIVIDED 取别名为 division 
        DetachedCriteria divisionCrit = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        //设置区划权限表 TBL_ADMIN_DIVISION_DIVIDED 和 主体表tbl_base_subject关联的字段
        divisionCrit.add(Property.forName("division.adminDivisionCode").eqProperty("cBase.administrativeDivision"));
        //层级代码（002--省級层级编码code,003--市州級层级编码code,004--区县层级code）
        String levelCode = orgCtrl.getLevelCode();
        //行政区划(本质用来刷选出，主体表中，对应层级的主体ID，从而关联案件表中的主体ID，控制案件数据的查询范围。)
        String adminDivisionCode = orgCtrl.getAdminDivisionCode();
        //通过层级代码，控制数据查询范围 
        if(!TeeUtility.isNullorEmpty(levelCode)){
            if("0200".equals(levelCode)){
                // 控制省級层级，及省级以下层级
                divisionCrit.add(Restrictions.eq("provincialCode", adminDivisionCode));
            }
            if("0300".equals(levelCode)){
                // 控制市州級层级，及市州以下层级
                divisionCrit.add(Restrictions.eq("cityCode", adminDivisionCode));
            }
            if("0400".equals(levelCode)){
                // 控制区县层级
                divisionCrit.add(Restrictions.eq("districtCode", adminDivisionCode));
            }
            if("0500".equals(levelCode)){
                // 控制乡镇街道层级
                divisionCrit.add(Restrictions.eq("streetCode", adminDivisionCode));
            }
        }
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("division.adminDivisionCode"))));
        //监督删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //部门全称
        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
        }
        //部门编号
        if (!TeeUtility.isNullorEmpty(cbModel.getDepartmentCode())) {
            crit.add(Restrictions.like("cBase.departmentCode", cbModel.getDepartmentCode().trim(),MatchMode.ANYWHERE));
        }
        //法定代表人
        if (!TeeUtility.isNullorEmpty(cbModel.getRepresentative())) {
            crit.add(Restrictions.like("cBase.representative", cbModel.getRepresentative().trim(),MatchMode.ANYWHERE));
        }
        //分页条件
        crit.setFirstResult(start);
        crit.setMaxResults(length);
        //排序
        crit.addOrder(Order.desc("cBase.updateTime"));
        return crit.list();
    }
    /**
     * 
    * @Function: listSearchCount()
    * @Description: 监督部门查询总数
    *
    * @param: cbModel
    * @param: orgCtrl
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: yxy
    * @date: 2019年3月5日 下午4:46:01 
    *
     */
    public long listSearchCount(SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl) {
    	//获取session
        Session session = super.getSession();
        //主表tbl_base_supervise 取别名为 cBase
        Criteria crit = session.createCriteria(Supervise.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //第二层exists 表 TBL_ADMIN_DIVISION_DIVIDED 取别名为 division 
        DetachedCriteria divisionCrit = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        //设置区划权限表 TBL_ADMIN_DIVISION_DIVIDED 和 主体表tbl_base_subject关联的字段
        divisionCrit.add(Property.forName("division.adminDivisionCode").eqProperty("cBase.administrativeDivision"));
        //层级代码（002--省級层级编码code,003--市州級层级编码code,004--区县层级code）
        String levelCode = orgCtrl.getLevelCode();
        //行政区划(本质用来刷选出，主体表中，对应层级的主体ID，从而关联案件表中的主体ID，控制案件数据的查询范围。)
        String adminDivisionCode = orgCtrl.getAdminDivisionCode();
        //通过层级代码，控制数据查询范围 
        if(!TeeUtility.isNullorEmpty(levelCode)){
            if("0200".equals(levelCode)){
                // 控制省級层级，及省级以下层级
                divisionCrit.add(Restrictions.eq("provincialCode", adminDivisionCode));
            }
            if("0300".equals(levelCode)){
                // 控制市州級层级，及市州以下层级
                divisionCrit.add(Restrictions.eq("cityCode", adminDivisionCode));
            }
            if("0400".equals(levelCode)){
                // 控制区县层级
                divisionCrit.add(Restrictions.eq("districtCode", adminDivisionCode));
            }
            if("0500".equals(levelCode)){
                // 控制乡镇街道层级
                divisionCrit.add(Restrictions.eq("streetCode", adminDivisionCode));
            }
        }
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("division.adminDivisionCode"))));
        //监督删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //部门全称
        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
        }
        //部门编号
        if (!TeeUtility.isNullorEmpty(cbModel.getDepartmentCode())) {
            crit.add(Restrictions.like("cBase.departmentCode", cbModel.getDepartmentCode().trim(),MatchMode.ANYWHERE));
        }
        //法定代表人
        if (!TeeUtility.isNullorEmpty(cbModel.getRepresentative())) {
            crit.add(Restrictions.like("cBase.representative", cbModel.getRepresentative().trim(),MatchMode.ANYWHERE));
        }
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.intValue();
    }
    /**
     * 监督部门管理 分页
    *	select * from tbl_base_supervise sup where administrative_division= '520000' and is_delete = 0
    * @author: yxy
    * @date: 2019年3月5日 下午8:20:52 
    *
     */
	@SuppressWarnings("unchecked")
    public List<Supervise> findListByPage(int start, int length, SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl){
        //参考上述SQL理解下面代码逻辑。
        //获取session
        Session session = super.getSession();
        //主表tbl_base_organization 取别名为 cBase
        Criteria crit = session.createCriteria(Supervise.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //监督删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        if(gradeAdministrator){
        	//只能查看本级
            crit.add(Restrictions.eq("cBase.administrativeDivision",orgCtrl.getAdminDivisionCode()));
        }else{
        	return null;
        }
        //部门全称
        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
        }
        //部门编号
        if (!TeeUtility.isNullorEmpty(cbModel.getDepartmentCode())) {
            crit.add(Restrictions.like("cBase.departmentCode", cbModel.getDepartmentCode().trim(),MatchMode.ANYWHERE));
        }
        //法定代表人
        if (!TeeUtility.isNullorEmpty(cbModel.getRepresentative())) {
            crit.add(Restrictions.like("cBase.representative", cbModel.getRepresentative().trim(),MatchMode.ANYWHERE));
        }
        //分页条件
        crit.setFirstResult(start);
        crit.setMaxResults(length);
        //排序
        crit.addOrder(Order.desc("cBase.updateTime"));
        return crit.list();
    }
    /**
     * 
    * @Function: listSearchCount()
    * @Description: 监督部门管理 总数
    *
    * @param: cbModel
    * @param: orgCtrl
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: yxy
    * @date: 2019年3月5日 下午8:46:01 
    *
     */
    public long listCount(SuperviseModel cbModel, OrgCtrlInfoModel orgCtrl) {
    	//获取session
    	Session session = super.getSession();
        //主表tbl_base_organization 取别名为 cBase
        Criteria crit = session.createCriteria(Supervise.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //监督删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        if(gradeAdministrator){
        	//只能查看本级
            crit.add(Restrictions.eq("cBase.administrativeDivision",orgCtrl.getAdminDivisionCode()));
        }else{
        	Integer totalCount = 0;
            return totalCount.intValue();
        }
        //部门全称
        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
        }
        //部门编号
        if (!TeeUtility.isNullorEmpty(cbModel.getDepartmentCode())) {
            crit.add(Restrictions.like("cBase.departmentCode", cbModel.getDepartmentCode().trim(),MatchMode.ANYWHERE));
        }
        //法定代表人
        if (!TeeUtility.isNullorEmpty(cbModel.getRepresentative())) {
            crit.add(Restrictions.like("cBase.representative", cbModel.getRepresentative().trim(),MatchMode.ANYWHERE));
        }
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.intValue();
    }
    
    /**
     * 根据行政区划获取人民政府
     * @param id
     * @return
     * @date:2019年3月15日下午3:47:24
     * @author:yxy
     */
    public Map<String, Object> GovUuid(String id){
    	String sql = "select id from tbl_base_organization where is_government = 1 and administrative_division = '"+id+"'";
		return   super.executeNativeUnique(sql, null);
    }
    
    /**
     * 获取系统部门的UUID
     * @param id
     * @return
     * @date:2019年3月19日下午2:48:59
     * @author:yxy
     */
    public Map<String, Object> deptUuid(String id){
    	String sql = "select sys_org_id id from tbl_sys_bussiness_relation where business_sup_dept_id = '"+id+"' and rownum = 1";
		return   super.executeNativeUnique(sql, null);
    }
    
    /**
     * 获取监督部门的UUID
     * @return
     * @date:2019年3月19日下午2:53:21
     * @author:yxy
     */
    public Map<String, Object> deptId(){
    	String sql = "select uuid id from menu_group where menu_group_name = '监督部门'";
		return   super.executeNativeUnique(sql, null);
    }
    
    /**
     * 关系表中获取UUID
     * @param id
     * @return
     * @date:2019年3月19日下午2:55:01
     * @author:yxy
     */
    public Map<String, Object> getUuid(String id){
    	String sql = "select user_uuid id from tbl_sys_department_user where is_delete = 0 and supervise_id = '"+id+"'";
		return   super.executeNativeUnique(sql, null);
    }
}
