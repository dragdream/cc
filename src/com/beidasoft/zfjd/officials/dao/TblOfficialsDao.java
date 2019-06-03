package com.beidasoft.zfjd.officials.dao;

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
import com.beidasoft.zfjd.department.bean.OrganizationPerson;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.officials.bean.TblOfficials;
import com.beidasoft.zfjd.officials.model.TblOfficialsModel;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 执法人员表DAO类
 */
@Repository
public class TblOfficialsDao extends TeeBaseDao<TblOfficials> {
	
	@Autowired
	private TeeDeptDao dao;
		
	
	/**
	  * 分页
	  * @param firstResult
	  * @param rows
	  * @param queryModel
	  * @return
	  */
	  public List<TblOfficials> listByPage(int firstResult,int rows,TblOfficialsModel queryModel){
 		  String hql = " from TblOfficials where isDelete = 0";
//		  String hql = "select * from tbl_base_person where id in (select person_id from tbl_base_organization_person where dept_id = '5f54ed1a-9e48-44d0-9e6b-a76b5b51578c')";
		  if(!TeeUtility.isNullorEmpty(queryModel.getName()))
		  {
			  hql +=" and name like '%"+queryModel.getName()+"%'";  
		  }
		  if(queryModel.getIds() != null && !"".equals(queryModel.getIds())) {
              hql = hql + " and id in (" + queryModel.getIds() + ")";
          }
		  if(!TeeUtility.isNullorEmpty(queryModel.getDepartmentCode())){
			  hql+=" and departmentCode like '%"+queryModel.getDepartmentCode()+"%'";
		  }
		  if(!TeeUtility.isNullorEmpty(queryModel.getDeptId())){
			  hql+=" and id in (" + queryModel.getDeptId() + ")";
		  }
		  if(!TeeUtility.isNullorEmpty(queryModel.getCityCode())){
			  hql+=" and cityCode like '%"+queryModel.getCityCode()+"%'";
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getPersonId())){
			  hql+=" and personId like '%"+queryModel.getPersonId()+"%'";
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getExamine())){
			  hql+=" and EXAMINE ="+queryModel.getExamine();
		  }
		  //增加执法证件号查询人员
          if (queryModel.getEnforcerCode() != null && !"".equals(queryModel.getEnforcerCode())) {
              hql = hql + " and (departmentCode like '%"+queryModel.getEnforcerCode()+"%' or cityCode like '%"+queryModel.getEnforcerCode()+"%')";
          }
          	  hql+=" order by createTime desc ";
		return super.pageFind(hql, firstResult, rows, null);
		  
	  }
		  
	  public long getTotal(){
		  return super.count("select count(id) from TblOfficials where isDelete = 0",null);
	  }
	  /**
	   * 获取所在部门下的人员id
	   */
//	   public String getDeptId(TblOfficialsModel queryModel){
//			  String sql = " select person_id from tbl_base_organization_person where dept_id = '"+queryModel.getDeptId()+"'";
//			return sql;
//			
//		 	}
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getDeptId(int firstResult, int rows, String  DeptId) {
	        String sql = " select person_id from tbl_base_organization_person where dept_id = '"+DeptId+"'";
	        return (List<Map>) super.executeNativeQuery(sql, null,firstResult,rows);
	    }
	  /**
	   * 根据人员获取所属主体和委托组织
	   */
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public List<Map> listByBus(int firstResult, int rows, String  id) {
	        String sql = " select id,sub_name from tbl_base_subject where is_delete = 0 and is_depute = 0 and id = (select subject_id from tbl_base_subject_person where person_id = '"+id+"')";
	        return (List<Map>) super.executeNativeQuery(sql, null,firstResult,rows);
	    }
	  /**
	   * 导航
	   * @param queryModel
	   * @return
	   */
	  public long getTotal(TblOfficialsModel queryModel){
		  String hql = "select count(id) from TblOfficials where isDelete = 0";
		  
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getName()))
		  {
			  hql +="and name like '%"+queryModel.getName()+"%' ";  
		  }
		 
		  if(queryModel.getIds() != null && !"".equals(queryModel.getIds())) {
              hql = hql + " and id in (" + queryModel.getIds() + ")";
          }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getDepartmentCode())){
			  hql+=" and departmentCode like '%"+queryModel.getDepartmentCode()+"%'";
		  }
		  if(!TeeUtility.isNullorEmpty(queryModel.getDeptId())){
			  hql+=" and id in (" + queryModel.getDeptId() + ")";
		  }
		  if(!TeeUtility.isNullorEmpty(queryModel.getCityCode())){
			  hql+=" and cityCode like '%"+queryModel.getCityCode()+"%'";
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getPersonId())){
			  hql+=" and personId like '%"+queryModel.getPersonId()+"%'";
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getExamine())){
			  hql+=" and EXAMINE ="+queryModel.getExamine();
		  }
		  
		  //增加执法证件号查询人员
          if (queryModel.getEnforcerCode() != null && !"".equals(queryModel.getEnforcerCode())) {
              hql = hql + " and (departmentCode like '%"+queryModel.getEnforcerCode()+"%' or cityCode like '%"+queryModel.getEnforcerCode()+"%')";
          }
		   return super.count(hql,null);
	  }
	  
	 public List<TblOfficials>  findUsers(){
		  return super.find("from TblOfficials", null);	  
	 }
     
   /**
    * 
    * @Function: findOfficialsById()
    * @Description: 通过条件查询人员信息
    *
    * @param: oModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月17日 下午3:03:28 
    *
    */
    public TblOfficials findOfficialsById(TblOfficialsModel oModel) {
        StringBuffer hql = new StringBuffer();
        List<TblOfficials> list = null;
        TblOfficials officials = null;
        try {
            hql.append(" from TblOfficials where isDelete = 0 ");
            if (oModel.getId() != null && !"".equals(oModel.getId())) {
                hql.append(" and id = '" + oModel.getId() + "'");
            }
            if (oModel.getName() != null && !"".equals(oModel.getName())) {
                hql.append(" and name like '%" + oModel.getName() + "%'");
            }
            if (oModel.getEnforcerCode() != null && !"".equals(oModel.getEnforcerCode())) {
                hql.append(" and (cityCode like '%" + oModel.getEnforcerCode() + "%' or departmentCode like '%" + oModel.getEnforcerCode() + "%')");
            }
            list  = super.find(hql.toString(), null);
            if (list != null && list.size() > 0) {
                officials = list.get(0);
            }else {
                officials = null;
            }
        } catch (Exception e) {
            
        }
        return officials;
    }
	 
	 public long deletes(String[] ids) {
			String hql = "";
			for(int i=0 ; i<ids.length;i++){
				TblOfficials officials = new TblOfficials();
				hql = ("update TblOfficials  set isDelete = '1' where id = '"+ids[i]+"'");
				officials.setDeleteTime(new Date());
				this.executeUpdate(hql, null);
			}
			return this.executeUpdate(hql, null);
		}
	 /**
	  * 查看界面获取所属主体
	  */
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public List<Map> listOrgById(int firstResult, int rows,String id){
	    	String hql = "select sub_name from tbl_base_subject where is_delete = 0 and is_depute = 0 and id = (select subject_id from tbl_base_subject_person where person_id = '"+id+"')";
	    	return (List<Map>) super.executeNativeQuery(hql, null,firstResult,rows);
	    }
	    
	    /**
	     * 执法人员管理 分页 权限控制
	     * @param start
	     * @param length
	     * @param cbModel
	     * @param orgCtrl
	     * select *
		      from tbl_base_person pe
		     where exists (select 1
		              from tbl_base_organization_person op
		             where exists (select 1
		                      from tbl_base_organization org
		                     where org.administrative_division = '440300'
		                       and org.id = op.dept_id
		                       and org.is_delete = 0)
		               and pe.id = op.person_id)
		     and pe.is_delete = 0
	     * @return
	     * @date:2019年3月6日下午3:54:53
	     * @author:yxy
	     */
	    @SuppressWarnings("unchecked")
	    public List<TblOfficials> findListByPageSearch(int start, int length, TblOfficialsModel cbModel, OrgCtrlInfoModel orgCtrl){
	        //获取session
	    	Session session = super.getSession();
	        //主表tbl_base_person 取别名为 cBase
	        Criteria crit = session.createCriteria(TblOfficials.class, "cBase");
	        // 设置查询结果为实体对象
	        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	        //第一层exists 表 tbl_base_organization_person 取别名为 op
	        DetachedCriteria opCrit = DetachedCriteria.forClass(OrganizationPerson.class,"op");
	        //第二层exists 表 tbl_base_organization 取别名为 org 
	        DetachedCriteria orgCrit = DetachedCriteria.forClass(TblDepartmentInfo.class, "org");
	        //设置表 tbl_base_organization 和 表tbl_base_organization_person关联的字段
	        orgCrit.add(Property.forName("org.id").eqProperty("op.deptId"));
	        //执法部门删除标志
	        orgCrit.add(Restrictions.eq("org.isDelete", 0));
	        //执法人员删除标志
	        crit.add(Restrictions.eq("cBase.isDelete", 0));
	        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
	        opCrit.add(Subqueries.exists(orgCrit.setProjection(Projections.property("org.id"))));
	        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
	        opCrit.add(Property.forName("cBase.id").eqProperty(Property.forName("op.personId")));
	        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
	        crit.add(Subqueries.exists(opCrit.setProjection(Projections.property("id"))));
	        //是否是分级管理员（true是，false否）
	        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
	        //分级政府管理员和监督部门有权限
	        if(gradeAdministrator){
	        	//只能查看本级
	        	orgCrit.add(Restrictions.eq("org.administrativeDivision",orgCtrl.getAdminDivisionCode()));
	        }else{
	        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
		        int orgType = orgCtrl.getOrgType();
	        	if(2 == orgType){
	        		//执法部门查询本单位数据
	        		orgCrit.add(Restrictions.eq("org.id", orgCtrl.getDepartId()));
	        	}else if (3 == orgType) {
	        		//执法主体查询本单位数据
	        		opCrit.add(Restrictions.eq("op.subjectId", orgCtrl.getSubjectId()));
				}
	        	else{
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
	        //省级证件号 行业证件号
	        if (!TeeUtility.isNullorEmpty(cbModel.getCityCode())) {
//	            crit.add(Restrictions.like("cBase.departmentCode", cbModel.getDepartmentCode().trim(),MatchMode.ANYWHERE));
//	            crit.add(Restrictions.like("cBase.cityCode", cbModel.getCityCode().trim(),MatchMode.ANYWHERE));
	            crit.add(Restrictions.or(Restrictions.like("cBase.departmentCode", cbModel.getCityCode().trim(),MatchMode.ANYWHERE),Restrictions.like("cBase.cityCode", cbModel.getCityCode().trim(),MatchMode.ANYWHERE)));
	        }
	        //审核状态
	        if (!TeeUtility.isNullorEmpty(cbModel.getExamine())) {
	            crit.add(Restrictions.like("cBase.examine", cbModel.getExamine()));
	        }
	        //分页条件
	        crit.setFirstResult(start);
	        crit.setMaxResults(length);
	        //排序
	        crit.addOrder(Order.desc("cBase.updateTime"));
	        return crit.list();
	    }
	    
	    /**
	     * 执法人员管理 总数 权限控制
	     * @param cbModel
	     * @param orgCtrl
	     * @return
	     * @date:2019年3月6日下午3:55:19
	     * @author:yxy
	     */
	    public long listSearchCount(TblOfficialsModel cbModel, OrgCtrlInfoModel orgCtrl) {
	    	//获取session
	    	Session session = super.getSession();
	        //主表tbl_base_person 取别名为 cBase
	        Criteria crit = session.createCriteria(TblOfficials.class, "cBase");
	        // 设置查询结果为实体对象
	        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	        //第一层exists 表 tbl_base_organization_person 取别名为 op
	        DetachedCriteria opCrit = DetachedCriteria.forClass(OrganizationPerson.class,"op");
	        //第二层exists 表 tbl_base_organization 取别名为 org 
	        DetachedCriteria orgCrit = DetachedCriteria.forClass(TblDepartmentInfo.class, "org");
	        //设置表 tbl_base_organization 和 表tbl_base_organization_person关联的字段
	        orgCrit.add(Property.forName("org.id").eqProperty("op.deptId"));
	        //执法部门删除标志
	        orgCrit.add(Restrictions.eq("org.isDelete", 0));
	        //执法人员删除标志
	        crit.add(Restrictions.eq("cBase.isDelete", 0));
	        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
	        opCrit.add(Subqueries.exists(orgCrit.setProjection(Projections.property("org.id"))));
	        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
	        opCrit.add(Property.forName("cBase.id").eqProperty(Property.forName("op.personId")));
	        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
	        crit.add(Subqueries.exists(opCrit.setProjection(Projections.property("id"))));
	        //是否是分级管理员（true是，false否）
	        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
	        //分级政府管理员和监督部门有权限
	        if(gradeAdministrator){
	        	//只能查看本级
	        	orgCrit.add(Restrictions.eq("org.administrativeDivision",orgCtrl.getAdminDivisionCode()));
	        }else{
	        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
		        int orgType = orgCtrl.getOrgType();
		        if(2 == orgType){
	        		//执法部门查询本单位数据
	        		orgCrit.add(Restrictions.eq("org.id", orgCtrl.getDepartId()));
	        	}else if (3 == orgType) {
	        		//执法主体查询本单位数据
	        		opCrit.add(Restrictions.eq("op.subjectId", orgCtrl.getSubjectId()));
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
	        //省级证件号 行业证件号
	        if (!TeeUtility.isNullorEmpty(cbModel.getCityCode())) {
	            crit.add(Restrictions.or(Restrictions.like("cBase.departmentCode", cbModel.getCityCode().trim(),MatchMode.ANYWHERE),Restrictions.like("cBase.cityCode", cbModel.getCityCode().trim(),MatchMode.ANYWHERE)));
	        }
	        //审核状态
	        if (!TeeUtility.isNullorEmpty(cbModel.getExamine())) {
	            crit.add(Restrictions.like("cBase.examine", cbModel.getExamine()));
	        }
	        crit.setProjection(Projections.rowCount());
	        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
	        return totalCount.intValue();
	    }

	    /**
	     * 执法人员查询 分页 权限控制
	     * @param start
	     * @param length
	     * @param cbModel
	     * @param orgCtrl
	     * @return
	     * @date:2019年3月6日下午5:30:37
	     * @author:yxy
	     */
	    @SuppressWarnings("unchecked")
	    public List<TblOfficials> findListByPageSearchQuery(int start, int length, TblOfficialsModel cbModel, OrgCtrlInfoModel orgCtrl){
	        //获取session
	    	Session session = super.getSession();
	        //主表tbl_base_person 取别名为 cBase
	        Criteria crit = session.createCriteria(TblOfficials.class, "cBase");
	        // 设置查询结果为实体对象
	        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	        //第一层exists 表 tbl_base_organization_person 取别名为 op
	        DetachedCriteria opCrit = DetachedCriteria.forClass(OrganizationPerson.class,"op");
	        //第二层exists 表 tbl_base_organization 取别名为 org 
	        DetachedCriteria orgCrit = DetachedCriteria.forClass(TblDepartmentInfo.class, "org");
	        //第三层exists 表 TBL_ADMIN_DIVISION_DIVIDED 取别名为 division 
	        DetachedCriteria divisionCrit = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
	        //设置区划权限表 TBL_ADMIN_DIVISION_DIVIDED 和 主体表tbl_base_subject关联的字段
	        divisionCrit.add(Property.forName("division.adminDivisionCode").eqProperty("org.administrativeDivision"));
	        //设置表 tbl_base_organization 和 表tbl_base_organization_person关联的字段
	        orgCrit.add(Property.forName("org.id").eqProperty("op.deptId"));
	        //是否是分级管理员（true是，false否）
	        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
	        //是否是执法人员（true是，false否）
	        boolean lawPerson = orgCtrl.getIsLawPerson();
	        //分级政府管理员和监督部门有权限
	        if(lawPerson){
	        	//执法人员只能查看本单位
//	        	orgCrit.add(Restrictions.eq("org.administrativeDivision",orgCtrl.getAdminDivisionCode()));
//        		orgCrit.add(Restrictions.eq("org.orgSys", orgCtrl.getOrgSysCode()));
	        	orgCrit.add(Restrictions.eq("org.id", orgCtrl.getDepartId()));
	        }else{
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
		        if(gradeAdministrator){
		        	orgCrit.add(Restrictions.eq("org.administrativeDivision",orgCtrl.getAdminDivisionCode()));
		        }else{
		        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
			        int orgType = orgCtrl.getOrgType();
		        	if(orgType == 1){
			        	orgCrit.add(Restrictions.eq("org.administrativeDivision",orgCtrl.getAdminDivisionCode()));
		        	}else if (orgType == 2) {
		        		//执法部门查看本单位及下级单位
//		        		opCrit.add(Restrictions.eq("op.deptId",orgCtrl.getDepartId()));
		        		String sysOrg = orgCtrl.getOrgSysCode();
		        		//执法部门和执法主体查看本单位及下级单位
//			        	orgCrit.add(Restrictions.eq("org.administrativeDivision",orgCtrl.getAdminDivisionCode()));
			        	if(!TeeUtility.isNullorEmpty(sysOrg)){
//			        		String[] orgSysArray = sysOrg.split(",");
			        		orgCrit.add(Restrictions.like("org.orgSys", orgCtrl.getOrgSysCode(),MatchMode.ANYWHERE));
			        	}
					}
		        	else{
		        		//执法主体查看本单位及下级单位
//		        		opCrit.add(Restrictions.eq("op.subjectId",orgCtrl.getSubjectId()));
		        		String sysOrg = orgCtrl.getOrgSysCode();
		        		//执法部门和执法主体查看本单位及下级单位
//			        	orgCrit.add(Restrictions.eq("org.administrativeDivision",orgCtrl.getAdminDivisionCode()));
			        	if(!TeeUtility.isNullorEmpty(sysOrg)){
//			        		String[] orgSysArray = sysOrg.split(",");
			        		orgCrit.add(Restrictions.like("org.orgSys", orgCtrl.getOrgSysCode(),MatchMode.ANYWHERE));
			        	}
		        	}
		        }
	        }
	        //执法部门删除标志
	        orgCrit.add(Restrictions.eq("org.isDelete", 0));
	        //执法人员删除标志
	        crit.add(Restrictions.eq("cBase.isDelete", 0));
	        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
	        opCrit.add(Subqueries.exists(orgCrit.setProjection(Projections.property("org.id"))));
	        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
	        orgCrit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("division.adminDivisionCode"))));
	        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
	        opCrit.add(Property.forName("cBase.id").eqProperty(Property.forName("op.personId")));
	        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
	        crit.add(Subqueries.exists(opCrit.setProjection(Projections.property("id"))));
	        //姓名
	        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
	            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
	        }
	        //身份证号
	        if (!TeeUtility.isNullorEmpty(cbModel.getPersonId())) {
	            crit.add(Restrictions.like("cBase.personId", cbModel.getPersonId().trim(),MatchMode.ANYWHERE));
	        }
	        //省级证件号 行业证件号
	        if (!TeeUtility.isNullorEmpty(cbModel.getCityCode())) {
	            crit.add(Restrictions.or(Restrictions.like("cBase.departmentCode", cbModel.getCityCode().trim(),MatchMode.ANYWHERE),Restrictions.like("cBase.cityCode", cbModel.getCityCode().trim(),MatchMode.ANYWHERE)));
	        }
	        //审核状态
	        if (!TeeUtility.isNullorEmpty(cbModel.getExamine())) {
	            crit.add(Restrictions.like("cBase.examine", cbModel.getExamine()));
	        }
	        //分页条件
	        crit.setFirstResult(start);
	        crit.setMaxResults(length);
	        //排序
	        crit.addOrder(Order.desc("cBase.updateTime"));
	        return crit.list();
	    }
	    
	    /**
	     * 执法人员查询 总数 权限控制
	     * @param cbModel
	     * @param orgCtrl
	     * @return
	     * @date:2019年3月6日下午5:31:03
	     * @author:yxy
	     */
	    public long listSearchCountQuery(TblOfficialsModel cbModel, OrgCtrlInfoModel orgCtrl) {
	    	//获取session
	    	Session session = super.getSession();
	        //主表tbl_base_person 取别名为 cBase
	        Criteria crit = session.createCriteria(TblOfficials.class, "cBase");
	        // 设置查询结果为实体对象
	        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	        //第一层exists 表 tbl_base_organization_person 取别名为 op
	        DetachedCriteria opCrit = DetachedCriteria.forClass(OrganizationPerson.class,"op");
	        //第二层exists 表 tbl_base_organization 取别名为 org 
	        DetachedCriteria orgCrit = DetachedCriteria.forClass(TblDepartmentInfo.class, "org");
	        //第三层exists 表 TBL_ADMIN_DIVISION_DIVIDED 取别名为 division 
	        DetachedCriteria divisionCrit = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
	        //设置区划权限表 TBL_ADMIN_DIVISION_DIVIDED 和 主体表tbl_base_subject关联的字段
	        divisionCrit.add(Property.forName("division.adminDivisionCode").eqProperty("org.administrativeDivision"));
	        //设置表 tbl_base_organization 和 表tbl_base_organization_person关联的字段
	        orgCrit.add(Property.forName("org.id").eqProperty("op.deptId"));
	        //是否是分级管理员（true是，false否）
	        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
	        //是否是执法人员（true是，false否）
	        boolean lawPerson = orgCtrl.getIsLawPerson();
	        //分级政府管理员和监督部门有权限
	        if(lawPerson){
	        	//执法人员只能查看本单位
	        	orgCrit.add(Restrictions.eq("org.id", orgCtrl.getDepartId()));
	        }else{
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
		        if(gradeAdministrator){
		        	orgCrit.add(Restrictions.eq("org.administrativeDivision",orgCtrl.getAdminDivisionCode()));
		        }else{
		        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
			        int orgType = orgCtrl.getOrgType();
			        if(orgType == 1){
			        	orgCrit.add(Restrictions.eq("org.administrativeDivision",orgCtrl.getAdminDivisionCode()));
		        	}else if (orgType == 2) {
		        		//执法部门查看本单位及下级单位
		        		opCrit.add(Restrictions.eq("op.deptId",orgCtrl.getDepartId()));
					}
		        	else{
		        		//执法主体查看本单位及下级单位
		        		opCrit.add(Restrictions.eq("op.subjectId",orgCtrl.getSubjectId()));
		        	}
		        }
	        }
	        //执法部门删除标志
	        orgCrit.add(Restrictions.eq("org.isDelete", 0));
	        //执法人员删除标志
	        crit.add(Restrictions.eq("cBase.isDelete", 0));
	        //exists中的select 1 from类似，select id from tbl_base_supervise， 必须设置，不设置查询会报语法错误
	        opCrit.add(Subqueries.exists(orgCrit.setProjection(Projections.property("org.id"))));
	        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
	        orgCrit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("division.adminDivisionCode"))));
	        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
	        opCrit.add(Property.forName("cBase.id").eqProperty(Property.forName("op.personId")));
	        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
	        crit.add(Subqueries.exists(opCrit.setProjection(Projections.property("id"))));
	        //姓名
	        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
	            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
	        }
	        //身份证号
	        if (!TeeUtility.isNullorEmpty(cbModel.getPersonId())) {
	            crit.add(Restrictions.like("cBase.personId", cbModel.getPersonId().trim(),MatchMode.ANYWHERE));
	        }
	        //省级证件号 行业证件号
	        if (!TeeUtility.isNullorEmpty(cbModel.getCityCode())) {
	            crit.add(Restrictions.or(Restrictions.like("cBase.departmentCode", cbModel.getCityCode().trim(),MatchMode.ANYWHERE),Restrictions.like("cBase.cityCode", cbModel.getCityCode().trim(),MatchMode.ANYWHERE)));
	        }
	        //审核状态
	        if (!TeeUtility.isNullorEmpty(cbModel.getExamine())) {
	            crit.add(Restrictions.like("cBase.examine", cbModel.getExamine()));
	        }
	        crit.setProjection(Projections.rowCount());
	        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
	        return totalCount.intValue();
	    }
	    
	    /**
	     * 获取person表中下一条数列的值
	     * @return
	     * @date:2019年3月11日下午7:52:04
	     * @author:yxy
	     */
		public Map<String, Object> nextUuid(){
	    	String sql = "select PERSON_SEQ.nextval  id from dual";
			return   super.executeNativeUnique(sql, null);
	    }
	    /**
	     * 执法人员根据所属主体获取系统部门uuid
	     * @return
	     * @date:2019年3月14日下午7:49:14
	     * @author:yxy
	     */
	    public Map<String, Object> deptUuid(String id){
	    	String sql = "select sys_org_id id from tbl_sys_bussiness_relation where business_dept_id = (select id from tbl_base_organization where id = (select department_code from tbl_base_subject where id = (select subject_id from tbl_base_subject_person where person_id = '"+id+"' and rownum = 1))) and business_subject_id =(select subject_id from tbl_base_subject_person where person_id = '"+id+"'and rownum = 1)and rownum = 1";
			return   super.executeNativeUnique(sql, null);
	    }
		/**
		 * 根据执法人员ID获取用户UUID
		 * @return
		 * @date:2019年3月12日下午3:43:58
		 * @author:yxy
		 */
		public Map<String, Object> getUuid(String id){
	    	String sql = "select user_uuid id from tbl_sys_person_user where is_delete = 0 and person_id = '"+id+"'";
			return   super.executeNativeUnique(sql, null);
	    }
		
		/**
		 * 获取执法人员zfjd的UUID
		 * @param id
		 * @return
		 * @date:2019年3月15日上午10:10:23
		 * @author:yxy
		 */
	    public Map<String, Object> zfjdId(){
	    	String sql = "select uuid id from menu_group where menu_group_name = '执法人员zfjd'";
			return   super.executeNativeUnique(sql, null);
	    }
}
