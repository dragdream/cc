package com.beidasoft.zfjd.subject.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.model.SubjectModel;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class SubjectDao extends TeeBaseDao<Subject>{
    /**
     * 
    * @Function: SubjectDao.java
    * @Description: 分页查询主体信息
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年1月2日 下午8:23:55 
    *
     */
    public List<Subject> listByPage(int firstResult, int rows, SubjectModel subjectModel) {
        String hql = " from Subject where isDelete = 0 ";
        
        if(subjectModel.getSubName() != null && !"".equals(subjectModel.getSubName())) {
            hql = hql + " and subName like '%" + subjectModel.getSubName() + "%' ";
        }
        
        if(!TeeUtility.isNullorEmpty(subjectModel.getNature())){
			  hql+=" and nature = "+subjectModel.getNature();
		  }
        
        if(!TeeUtility.isNullorEmpty(subjectModel.getSubLevel())){
			  hql+=" and sub_Level = "+subjectModel.getSubLevel();
		  }
        
        if(!TeeUtility.isNullorEmpty(subjectModel.getExamine())){
			  hql+=" and examine = "+subjectModel.getExamine();
		  }
        
        if(subjectModel.getIsDepute() != null) {
            hql = hql + " and isDepute = " + TeeStringUtil.getInteger(subjectModel.getIsDepute(), 0);
        }
        
        if(subjectModel.getIds() != null && !"".equals(subjectModel.getIds())) {
            hql = hql + " and id in (" + subjectModel.getIds() + ")";
        }
        if(subjectModel.getType() != null && !"".equals(subjectModel.getType())) {
            if("01".equals(subjectModel.getType())) {
                hql = hql + " and orgSys in (select orgSys from Subject where id = '" + subjectModel.getId() + "')";
            } else if("02".equals(subjectModel.getType())) {
                hql = hql + " and orgSys not in (select orgSys from Subject where id = '" + subjectModel.getId() + "')";
            }
        }
        hql+=" order by createTime desc ";
        return super.pageFind(hql, firstResult, rows, null);
    }
    
    /**
     * 计算主体总数
    * @Function: SubjectDao.java
    * @Description: 该函数的功能描述
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年1月2日 下午8:24:07 
    *
     */
    public long listCount(SubjectModel subjectModel) {
        String hql = " select count(*) from Subject where isDelete = 0 ";
        
        if(subjectModel.getSubName() != null && !"".equals(subjectModel.getSubName())) {
            hql = hql + " and subName like '%" + subjectModel.getSubName() + "%'";
        }
        
        if(!TeeUtility.isNullorEmpty(subjectModel.getNature())){
			  hql+=" and nature = "+subjectModel.getNature();
		  }
      
	      if(!TeeUtility.isNullorEmpty(subjectModel.getSubLevel())){
				  hql+=" and sub_Level = "+subjectModel.getSubLevel();
			  }
	      
	      if(!TeeUtility.isNullorEmpty(subjectModel.getExamine())){
			  hql+=" and examine = "+subjectModel.getExamine();
		  }
	      
        if(subjectModel.getIsDepute() != null) {
            hql = hql + " and isDepute = " + TeeStringUtil.getInteger(subjectModel.getIsDepute(), 0);
        }
        
        if(subjectModel.getIds() != null && !"".equals(subjectModel.getIds())) {
            hql = hql + " and id in (" + subjectModel.getIds() + ")";
        }

        if(subjectModel.getType() != null && !"".equals(subjectModel.getType())) {
            if("01".equals(subjectModel.getType())) {
                hql = hql + " and orgSys in (select orgSys from Subject where id = '" + subjectModel.getId() + "')";
            } else if("02".equals(subjectModel.getType())) {
                hql = hql + " and orgSys not in (select orgSys from Subject where id = '" + subjectModel.getId() + "')";
            }
        }
        
        return super.count(hql, null);
    }
    
    
    
    public List<Subject> getSubjectByIds(String ids) {
        String hql = " from Subject where isDelete = 0 and id in (" + ids + ")";
        return super.find(hql);
    }
    
	  public long getTotal(){
		  return super.count("select count(id) from Subject",null);
	  }
	  
	  
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> areaToLevel(int firstResult, int rows,String id){
		  String hql = "select level_code from tbl_admin_division_divided  where admin_division_code = '"+id+"'";
	 		return (List<Map>) super.executeNativeQuery(hql, null, firstResult, rows); 
	 		}
	  /**
	   * 多删
	   * @param ids
	   * @return
	   */
	  public long deletes(String[] ids) {
			String hql = "";
			for(int i=0 ; i<ids.length;i++){
				Subject subject = new Subject();
				hql = ("update Subject set isDelete = '1' where id = '"+ids[i]+"'");
				subject.setDeleteTime(new Date());
				this.executeUpdate(hql, null);
			}
			return this.executeUpdate(hql, null);
		}

	/**
	 * 组织分页
	 */
	  public List<Subject> listByPageOrg(int firstResult, int rows, SubjectModel subjectModel) {
	        String hql = " from Subject s1 where s1.isDelete = 0 ";
	        
	        if(!TeeUtility.isNullorEmpty(subjectModel.getIsDepute())){
	            hql = hql + " and s1.isDepute = "+ subjectModel.getIsDepute();
	        }
	        
	        if(subjectModel.getSubName() != null && !"".equals(subjectModel.getOrganizationCode())) {
	            hql = hql + " and s1.organizationCode like '%" + subjectModel.getOrganizationCode() + "%' ";
	        }
	        
	        if(!TeeUtility.isNullorEmpty(subjectModel.getEntrustNature())){
				  hql+=" and s1.entrustNature = "+subjectModel.getEntrustNature();
			  }
	        
	        if(!TeeUtility.isNullorEmpty(subjectModel.getParentId())){
				  hql+=" and s1.parentId like '%"+subjectModel.getParentId()+ "%' ";
			  }
	        
	        if(!TeeUtility.isNullorEmpty(subjectModel.getSubName())){
				  hql+=" and s1.subName like '%" +subjectModel.getSubName()+ "%' ";
			  }
			  hql+=" order by createTime desc ";
	        return super.pageFind(hql, firstResult, rows, null);
	    }
	  /**
	   * 组织个数
	   * @param subjectModel
	   * @return
	   */
	  public long listCountOrg(SubjectModel subjectModel) {
		  String hql = " select count(*) from Subject s1 where s1.isDelete = 0  ";
	        
	        if(subjectModel.getSubName() != null && !"".equals(subjectModel.getOrganizationCode())) {
	            hql = hql + " and s1.organizationCode like '%" + subjectModel.getOrganizationCode() + "%' ";
	        }
	        
	        if(!TeeUtility.isNullorEmpty(subjectModel.getEntrustNature())){
				  hql+=" and s1.entrustNature = "+subjectModel.getEntrustNature();
			  }
	        
	        if(!TeeUtility.isNullorEmpty(subjectModel.getParentId())){
				  hql+=" and s1.parentId like '%"+subjectModel.getParentId()+ "%' ";
			  }
	        
	        if(!TeeUtility.isNullorEmpty(subjectModel.getSubName())){
				  hql+=" and s1.subName like '%" +subjectModel.getSubName()+ "%' ";
			  }
	        return super.count(hql, null);
	    }
	  /**
	   * 根据id查询name
	   */
		  public Subject getNameById(String parentId) {
		        String hql = " from Subject where isDelete = 0 and id = '" +parentId+"'";
				//return hql;
		        List<Subject> subjects = super.find(hql);
		        if(subjects == null || subjects.size() == 0){
		        	return null;
		        }
		        return subjects.get(0);
		        
		    }
		  /**
		   * 查询委托组织名称
		   * @param firstResult
		   * @param rows
		   * @param queryModel
		   * @return
		   */
		  @SuppressWarnings("unchecked")
		public List<Subject> listByDe(int firstResult,int rows,SubjectModel queryModel){
			  if(!TeeUtility.isNullorEmpty(queryModel.getParentId())){
				  if (!TeeUtility.isNullorEmpty(queryModel.getSubName()))
				  {
					  Object[] object = new Object[2];
					  StringBuffer hql = new StringBuffer(" from Subject  where isDelete = 0 and isDepute = 1 ");
					  List<Object> params = new ArrayList<Object>();
					  hql.append(" and subName like ?");
					  params.add("%"+queryModel.getSubName()+"%");
					  if(!TeeUtility.isNullorEmpty(queryModel.getParentId())){
						  hql.append(" and parentId = ?");
						  params.add(queryModel.getParentId());
					  }
					  object[0] = hql;
				      object[1] = params;
					  List<Subject> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
					  return list;
				  }else{
					  if(!TeeUtility.isNullorEmpty(queryModel.getId())){
						  Object[] object = new Object[2];
						  StringBuffer hql = new StringBuffer(" from Subject  where isDelete = 0 and isDepute = 1 ");
						  List<Object> params = new ArrayList<Object>();
						  hql.append(" and id = ?");
						  params.add(queryModel.getId());
						  object[0] = hql;
					      object[1] = params;
						  List<Subject> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
						  return list;
					  }else{
						  return null;
					  }
				  }
			  }
			  return null;
		  }	
		  
		  /**
		   * 委托组织查询执法主体，权限控制
		   * @param firstResult
		   * @param rows
		   * @param queryModel
		   * @param orgCtrl
		   * @return
		   * @date:2019年3月14日上午9:07:34
		   * @author:yxy
		   */
		  @SuppressWarnings("unchecked")
		public List<Subject> listByDe2(int firstResult,int rows,SubjectModel queryModel,OrgCtrlInfoModel orgCtrl){
			  Object[] object = new Object[2];
			  StringBuffer hql = new StringBuffer(" from Subject  where isDelete = 0 and isDepute = 0 and examine = 1 ");
			  List<Object> params = new ArrayList<Object>();
			  if(!TeeUtility.isNullorEmpty(queryModel.getSubName())){
				  hql.append(" and subName like ?");
				  params.add("%"+queryModel.getSubName()+"%");
				  if(TeeUtility.isNullorEmpty(orgCtrl.getAdminDivisionCode())){
					  hql.append(" and area = ?");
					  params.add(orgCtrl.getAdminDivisionCode());
				  }else{
					  if(orgCtrl.getOrgType()== 2){
						  hql.append(" and departmentCode = ?");
						  params.add(orgCtrl.getDepartId());
					  }
					  if(orgCtrl.getOrgType()==3){
						  hql.append(" and id = ?");
						  params.add(orgCtrl.getSubjectId());
					  }
				  }
				  object[0] = hql;
			      object[1] = params;
				  List<Subject> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
				  return list;
			  }else{
				  if (!TeeUtility.isNullorEmpty(queryModel.getParentId()))
				  {
					  hql.append(" and id = ?");
					  params.add(queryModel.getParentId());
					  if(TeeUtility.isNullorEmpty(orgCtrl.getAdminDivisionCode())){
						  hql.append(" and area = ?");
						  params.add(orgCtrl.getAdminDivisionCode());
					  }
					  object[0] = hql;
				      object[1] = params;
					  List<Subject> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
					  return list;
				  }
				  
			  }
			return null;
			  
		  }	
		  /**
		   * 根据id查询部门下的主体
		   * @param firstResult
		   * @param rows
		   * @param queryModel
		   * @return
		   */
		  @SuppressWarnings("unchecked")
		public List<Subject> listByOrgSub(int firstResult,int rows,SubjectModel queryModel,OrgCtrlInfoModel orgCtrl){
			  Object[] object = new Object[2];
			  StringBuffer hql = new StringBuffer(" from Subject  where isDelete = 0 and isDepute = 0 and examine = 1 ");
			  List<Object> params = new ArrayList<Object>();
			  if(orgCtrl.getGradeAdministrator()){
				  hql.append(" and area = ?");
				  params.add(orgCtrl.getAdminDivisionCode());
				  object[0] = hql;
			      object[1] = params;
				  List<Subject> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
				  return list;
			  }else {
			  if (!TeeUtility.isNullorEmpty(queryModel.getSubName())){	
				  hql.append(" and subName like ?");
				  params.add("%"+queryModel.getSubName()+"%");
				  if(!TeeUtility.isNullorEmpty(queryModel.getDepartmentCode())){
					  hql.append(" and departmentCode = ?");
					  params.add(queryModel.getDepartmentCode());
				  }
				  object[0] = hql;
			      object[1] = params;
				  List<Subject> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
				  return list;
			  }else{
					  if(!TeeUtility.isNullorEmpty(queryModel.getDepartmentCode())){
						  hql.append(" and departmentCode = ?");
						  params.add(queryModel.getDepartmentCode());
						  object[0] = hql;
					      object[1] = params;
						  List<Subject> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
						  return list;
					  }else{
						  return null;
					  }
			  }
			  }
		  }	
		  
		  /**
		   * 根据委托主体查询执法系统
		   * @param firstResult
		   * @param rows
		   * @param queryModel
		   * @return
		   */
		  public List<Subject> listByDeSys(int firstResult,int rows,String id){
			  String hql = " select orgSys from Subject where isDepute = 0 and id = '"+id+"'";
			  List<Subject> orgSys = super.pageFind(hql, firstResult, rows, null);
			  return orgSys;
		  }
		  //主体
		   public List<Subject> listBySubject(int firstResult,int rows,SubjectModel queryModel){
				  String hql = " select new Subject(sub.id,sub.subName) from Subject sub where isDelete = 0 and isDepute = 0 ";
				  if (!TeeUtility.isNullorEmpty(queryModel.getSubName()))
				  {
					  hql +=" and subName like '%"+queryModel.getSubName()+"%'";  
				  }
				  List<Subject> list = super.pageFind(hql, firstResult, rows, null);
				  return list;
			  }
    /**
     * 
    * @Function: subjectList()
    * @Description: 查询所有主体
    *
    * @param: subjectModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月15日 下午4:46:42 
    *
     */
    public List<Subject> subjectList(SubjectModel subjectModel) {
        List<Subject> subejctList = null;
        String sql = " from Subject where isDelete = 0 ";
        if (!TeeUtility.isNullorEmpty(subjectModel.getIsDepute())) {
            sql = sql + " and isDepute = "+ subjectModel.getIsDepute();
        }
        if (!TeeUtility.isNullorEmpty(subjectModel.getSubName())) {
            sql = sql + " and subName like '%" + subjectModel.getSubName() + "%'";
        }
        if (!TeeUtility.isNullorEmpty(subjectModel.getId())) {
            sql = sql + " and id = '" + subjectModel.getId()+"'";
        }
        if (!TeeUtility.isNullorEmpty(subjectModel.getIds())) {
            sql = sql + " and id in ('" + subjectModel.getIds().replace(",", "','") + "')";
        }
        if (!TeeUtility.isNullorEmpty(subjectModel.getOrgSys())) {
            sql = sql + " and orgSys = '" + subjectModel.getOrgSys() + "'";
        }
        sql = sql + " order by subName desc ";
        subejctList = super.find(sql);
        return subejctList;
    }
    
    /**
     * 
     * @Function: findListByPageOrg
     * @Description: 委托组织条件查询
     *
     * @param firstResult 起始条数
     * @param rows 每页条数
     * @param subjectModel 查询条件
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午3:07:44
     */
    public List<Subject> findListByPageOrg(int firstResult, int rows, SubjectModel subjectModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(subjectModel);
        @SuppressWarnings("unchecked")
        List<Subject> subjects = pageFind(object[0].toString(), firstResult, rows, ((List<Object>)object[1]).toArray());
        return subjects;
    }
    
    /**
     * 
     * @Function: findListCountByPageOrg
     * @Description: 委托组织查询总条数
     *
     * @param subjectModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午4:45:12
     */
    public long findListCountByPageOrg(SubjectModel subjectModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(subjectModel);
        @SuppressWarnings("unchecked")
        long count = count("select count(*) " + object[0].toString(), ((List<Object>)object[1]).toArray());
        return count;
    }

    /**
     * 
     * @Function: getHql
     * @Description: 获取HQL及参数集合
     *
     * @param subjectModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午4:34:40
     */
    private Object[] getHql(SubjectModel subjectModel) {
        // TODO Auto-generated method stub
        Object[] object = new Object[2];
        // 创建HQL查询语句
        StringBuffer hql = new StringBuffer("from Subject where isDelete = 0 ");
        // 创建参数集合
        List<Object> params = new ArrayList<Object>();
        // 判断是否添加查询条件：是否是受委托组织
        if(!TeeUtility.isNullorEmpty(subjectModel.getIsDepute())){
            hql.append(" and isDepute = ? ");
            params.add(subjectModel.getIsDepute());
        }
        // 判断是否添加查询条件：委托组织性质
        if(!TeeUtility.isNullorEmpty(subjectModel.getEntrustNature())){
            hql.append(" and entrustNature = ? ");
            params.add(subjectModel.getEntrustNature().trim());
        }
        // 判断是否添加查询条件：执法主体名称（委托组织名称）
        if(!TeeUtility.isNullorEmpty(subjectModel.getSubName())){
            hql.append(" and subName like ? ");
            params.add("%" + subjectModel.getSubName().trim() + "%");
        }
        // 判断是否添加查询条件：统一社会信用码
        if(!TeeUtility.isNullorEmpty(subjectModel.getOrganizationCode())){
            hql.append(" and organizationCode = ? ");
            params.add(subjectModel.getOrganizationCode().trim());
        }
        // 判断是否添加查询条件：主体性质
        if(!TeeUtility.isNullorEmpty(subjectModel.getNature())){
            hql.append(" and nature = ? ");
            params.add(subjectModel.getNature().trim());
        }
        // 判断是否添加查询条件：审核状态
        if(!TeeUtility.isNullorEmpty(subjectModel.getExamine())){
            hql.append(" and examine = ? ");
            params.add(subjectModel.getExamine());
        }
        object[0] = hql;
        object[1] = params;
        return object;
    }

    /**
     * 查看界面 获取委托组织
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> listOrgById(int firstResult, int rows,String id){
    	String hql = "select sub_name from tbl_base_subject where is_delete = 0 and is_depute = 1 and  id = '"+id+"'";
    	return (List<Map>) super.executeNativeQuery(hql, null,firstResult,rows);
    }

    @SuppressWarnings("unchecked")
    public List<Subject> findListByPageOrg(int firstResult, int rows, SubjectModel subjectModel,
            OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Criteria criteria = null;
        if(!TeeUtility.isNullorEmpty(subjectModel.getIsDepute())){
            if(subjectModel.getIsDepute() == 0){
                criteria = getCriteria(subjectModel, orgCtrlInfoModel);
                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(rows);
                criteria.addOrder(Order.asc("subject.organizationCode"));
            }else if(subjectModel.getIsDepute() == 1){
                criteria = getCriteriaOrg(subjectModel, orgCtrlInfoModel);
                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(rows);
                criteria.addOrder(Order.asc("entrustOrganization.organizationCode"));
            }
        }
        
        return criteria.list();
    }

    public Long findListCountByPageOrg(SubjectModel subjectModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Criteria criteria = null;
        if(!TeeUtility.isNullorEmpty(subjectModel.getIsDepute())){
            if(subjectModel.getIsDepute() == 0){
                criteria = getCriteria(subjectModel, orgCtrlInfoModel);
            }else if(subjectModel.getIsDepute() == 1){
                criteria = getCriteriaOrg(subjectModel, orgCtrlInfoModel);
            }
        }
        criteria.setProjection(Projections.rowCount());
        Integer total = Integer.parseInt(criteria.uniqueResult().toString());
        return total.longValue();
    }
    
    private Criteria getCriteria(SubjectModel subjectModel, OrgCtrlInfoModel orgCtrlInfoModel){
        Session session = super.getSession();
        // 主查询对象：执法主体
        Criteria criteria = session.createCriteria(Subject.class, "subject");
        // 将SQL查询结果放入集合
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // exists条件：TBL_ADMIN_DIVISION_DIVIDED --关联行政区划表
        DetachedCriteria divisionCriteria = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        // 查询条件，关联关系
        divisionCriteria.add(Property.forName("division.adminDivisionCode").eqProperty("subject.area"));
        /// 内层查询条件-开始
        // 获取登录账户部门层级
        String levelCode = orgCtrlInfoModel.getLevelCode();
        // 获取登陆账户地区编号
        String adminDivisionCode = orgCtrlInfoModel.getAdminDivisionCode();
        if(!TeeUtility.isNullorEmpty(levelCode)){
            if("0200".equals(levelCode)){
                // 省级及以下
                divisionCriteria.add(Restrictions.eq("provincialCode", adminDivisionCode));
            }else if("0300".equals(levelCode)){
                // 市级及以下
                divisionCriteria.add(Restrictions.eq("cityCode", adminDivisionCode));
            }else if("0400".equals(levelCode)){
                // 区级及以下
                divisionCriteria.add(Restrictions.eq("districtCode", adminDivisionCode));
            }else if("0500".equals(levelCode)){
                // 乡镇街道
                divisionCriteria.add(Restrictions.eq("streetCode", adminDivisionCode));
            }
        }
        // 内层查询条件-结束
        // exists(select ...)
        criteria.add(Subqueries.exists(divisionCriteria.setProjection(Projections.property("division.adminDivisionCode"))));
        String orgSys = orgCtrlInfoModel.getOrgSysCode();
        Integer orgType = orgCtrlInfoModel.getOrgType();
        if(orgType != null){
            if(orgType == 2 || orgType == 3 || orgCtrlInfoModel.getIsLawPerson()){
                if(!orgCtrlInfoModel.getGradeAdministrator()){
                    if(!TeeUtility.isNullorEmpty(orgSys)){
                        String[] orgSysArray = orgSys.split(",");
                        criteria.add(Restrictions.in("orgSys", orgSysArray));
                    }
                }
            }
        }
        // 删除标志
        criteria.add(Restrictions.eq("subject.isDelete", 0));
        // 判断是否添加查询条件：是否是受委托组织
        if(!TeeUtility.isNullorEmpty(subjectModel.getIsDepute())){
            criteria.add(Restrictions.eq("subject.isDepute", subjectModel.getIsDepute()));
        }
        // 判断是否添加查询条件：执法主体名称（委托组织名称）
        if(!TeeUtility.isNullorEmpty(subjectModel.getSubName())){
            criteria.add(Restrictions.like("subject.subName", subjectModel.getSubName().trim(), MatchMode.ANYWHERE));
        }
        // 判断是否添加查询条件：统一社会信用码
        if(!TeeUtility.isNullorEmpty(subjectModel.getOrganizationCode())){
            criteria.add(Restrictions.eq("subject.organizationCode", subjectModel.getOrganizationCode().trim()));
        }
        // 判断是否添加查询条件：主体性质
        if(!TeeUtility.isNullorEmpty(subjectModel.getNature())){
            criteria.add(Restrictions.eq("subject.nature", subjectModel.getNature().trim()));
        }
        // 判断是否添加查询条件：审核状态
        if(!TeeUtility.isNullorEmpty(subjectModel.getExamine())){
            criteria.add(Restrictions.eq("subject.examine", subjectModel.getExamine()));
        }
        return criteria;
    }
    
    private Criteria getCriteriaOrg(SubjectModel subjectModel, OrgCtrlInfoModel orgCtrlInfoModel){
        Session session = super.getSession();
        // 主查询对象：受委托组织
        Criteria criteria = session.createCriteria(Subject.class, "entrustOrganization");
        // 将SQL查询结果放入集合
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 第一层，关联主体
        DetachedCriteria subjectCriteria = DetachedCriteria.forClass(Subject.class, "subject");
        // 关联关系
        subjectCriteria.add(Property.forName("subject.id").eqProperty("entrustOrganization.parentId"));
        // exists条件：TBL_ADMIN_DIVISION_DIVIDED --关联行政区划表
        DetachedCriteria divisionCriteria = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        // 查询条件，关联关系
        divisionCriteria.add(Property.forName("division.adminDivisionCode").eqProperty("subject.area"));
        /// 内层查询条件-开始
        // 获取登录账户部门层级
        String levelCode = orgCtrlInfoModel.getLevelCode();
        // 获取登陆账户地区编号
        String adminDivisionCode = orgCtrlInfoModel.getAdminDivisionCode();
        if(!TeeUtility.isNullorEmpty(levelCode)){
            if("0200".equals(levelCode)){
                // 省级及以下
                divisionCriteria.add(Restrictions.eq("provincialCode", adminDivisionCode));
            }else if("0300".equals(levelCode)){
                // 市级及以下
                divisionCriteria.add(Restrictions.eq("cityCode", adminDivisionCode));
            }else if("0400".equals(levelCode)){
                // 区级及以下
                divisionCriteria.add(Restrictions.eq("districtCode", adminDivisionCode));
            }else if("0500".equals(levelCode)){
                // 乡镇街道
                divisionCriteria.add(Restrictions.eq("streetCode", adminDivisionCode));
            }
        }
        // 内层查询条件-结束
        // exists(select ...)
        subjectCriteria.add(Subqueries.exists(divisionCriteria.setProjection(Projections.property("division.adminDivisionCode"))));
        String orgSys = orgCtrlInfoModel.getOrgSysCode();
        Integer orgType = orgCtrlInfoModel.getOrgType();
        if(orgType != null){
            if(orgType == 2 || orgType == 3 || orgCtrlInfoModel.getIsLawPerson()){
                if(!orgCtrlInfoModel.getGradeAdministrator()){
                    if(!TeeUtility.isNullorEmpty(orgSys)){
                        String[] orgSysArray = orgSys.split(",");
                        subjectCriteria.add(Restrictions.in("orgSys", orgSysArray));
                    }
                }
            }
        }
        // 内层主体 删除标志
        subjectCriteria.add(Restrictions.eq("subject.isDelete", 0));
        // exists(select ...)
        criteria.add(Subqueries.exists(subjectCriteria.setProjection(Projections.property("subject.id"))));
        // 删除标志
        criteria.add(Restrictions.eq("entrustOrganization.isDelete", 0));
        // 判断是否添加查询条件：是否是受委托组织
        if(!TeeUtility.isNullorEmpty(subjectModel.getIsDepute())){
            criteria.add(Restrictions.eq("entrustOrganization.isDepute", subjectModel.getIsDepute()));
        }
        // 判断是否添加查询条件：委托组织性质
        if(!TeeUtility.isNullorEmpty(subjectModel.getEntrustNature())){
            criteria.add(Restrictions.eq("entrustOrganization.entrustNature", subjectModel.getEntrustNature().trim()));
        }
        // 判断是否添加查询条件：委托组织名称
        if(!TeeUtility.isNullorEmpty(subjectModel.getSubName())){
            criteria.add(Restrictions.like("entrustOrganization.subName", subjectModel.getSubName().trim(), MatchMode.ANYWHERE));
        }
        // 判断是否添加查询条件：统一社会信用码
        if(!TeeUtility.isNullorEmpty(subjectModel.getOrganizationCode())){
            criteria.add(Restrictions.eq("entrustOrganization.organizationCode", subjectModel.getOrganizationCode().trim()));
        }
        return criteria;
    }
    
    /**
     * 执法主体管理 分页 权限控制
     * @param start
     * @param length
     * @param cbModel
     * @param orgCtrl
     * @return
     * @date:2019年3月6日上午11:33:53
     * @author:yxy
     */
    @SuppressWarnings("unchecked")
    public List<Subject> findListByPageSearch(int start, int length, SubjectModel cbModel, OrgCtrlInfoModel orgCtrl){
        //获取session
        Session session = super.getSession();
        //主表tbl_base_subject 取别名为 cBase
        Criteria crit = session.createCriteria(Subject.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if(gradeAdministrator){
        	//只能查看本级
            crit.add(Restrictions.eq("cBase.area",orgCtrl.getAdminDivisionCode()));
        }else{
        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
            int orgType = orgCtrl.getOrgType();
        	if(2 == orgType){
        		//执法部门查询本单位数据
        		crit.add(Restrictions.eq("cBase.departmentCode", orgCtrl.getDepartId()));
        	}else{
        		return null;
        	}
        }
        //主体删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        crit.add(Restrictions.eq("cBase.isDepute", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //主体名称
        if (!TeeUtility.isNullorEmpty(cbModel.getSubName())) {
            crit.add(Restrictions.like("cBase.subName", cbModel.getSubName().trim(),MatchMode.ANYWHERE));
        }
        //主体性质
        if (!TeeUtility.isNullorEmpty(cbModel.getNature())) {
            crit.add(Restrictions.eq("cBase.nature", cbModel.getNature()));
        }
        //审核状态
        if (!TeeUtility.isNullorEmpty(cbModel.getExamine())) {
            crit.add(Restrictions.eq("cBase.examine", cbModel.getExamine()));
        }
        //分页条件
        crit.setFirstResult(start);
        crit.setMaxResults(length);
        //排序
        crit.addOrder(Order.desc("cBase.updateTime"));
        return crit.list();
    }
    
    /**
     * 执法主体总数  权限控制
     * @param cbModel
     * @param orgCtrl
     * @return
     * @date:2019年3月6日上午11:37:02
     * @author:yxy
     */
    public long listSearchCount(SubjectModel cbModel, OrgCtrlInfoModel orgCtrl) {
    	//获取session
    	Session session = super.getSession();
        //主表tbl_base_subject 取别名为 cBase
        Criteria crit = session.createCriteria(Subject.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if(gradeAdministrator){
        	//只能查看本级
            crit.add(Restrictions.eq("cBase.area",orgCtrl.getAdminDivisionCode()));
        }else{
        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
            int orgType = orgCtrl.getOrgType();
        	if(2 == orgType){
        		//执法部门查询本单位数据
        		crit.add(Restrictions.eq("cBase.departmentCode", orgCtrl.getDepartId()));
        	}else{
        		Integer totalCount = 0;
                return totalCount.intValue();
        	}
        }
        //主体删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        crit.add(Restrictions.eq("cBase.isDepute", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //主体名称
        if (!TeeUtility.isNullorEmpty(cbModel.getSubName())) {
            crit.add(Restrictions.like("cBase.subName", cbModel.getSubName().trim(),MatchMode.ANYWHERE));
        }
        //主体性质
        if (!TeeUtility.isNullorEmpty(cbModel.getNature())) {
            crit.add(Restrictions.eq("cBase.nature", cbModel.getNature()));
        }
        //审核状态
        if (!TeeUtility.isNullorEmpty(cbModel.getExamine())) {
            crit.add(Restrictions.eq("cBase.examine", cbModel.getExamine()));
        }
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.intValue();
    }
    
    /**
     * 委托组织管理分页 权限控制
     * @param start
     * @param length
     * @param cbModel
     * @param orgCtrl
     * @return
     * @date:2019年3月6日下午3:18:14
     * @author:yxy
     */
    @SuppressWarnings("unchecked")
    public List<Subject> findListByPageManOrg(int start, int length, SubjectModel cbModel, OrgCtrlInfoModel orgCtrl){
        //获取session
    	Session session = super.getSession();
        //主表组织tbl_base_subject 取别名为 cBase
        Criteria crit = session.createCriteria(Subject.class, "cBase");
        //第一层表主体tbl_base_subject 取别名为 sub
    	DetachedCriteria subCrit = DetachedCriteria.forClass(Subject.class, "sub");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 查询条件，关联关系
        subCrit.add(Property.forName("cBase.parentId").eqProperty("sub.id"));
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if(gradeAdministrator){
        	//只能查看本级
        	subCrit.add(Restrictions.eq("sub.area",orgCtrl.getAdminDivisionCode()));
        }else{
        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
            int orgType = orgCtrl.getOrgType();
        	if(2 == orgType){
        		//执法部门查询本单位数据
        		subCrit.add(Restrictions.eq("sub.departmentCode", orgCtrl.getDepartId()));
        	}else if(3 == orgType){
        		crit.add(Restrictions.eq("cBase.parentId", orgCtrl.getSubjectId()));
        	}else{
        		return null;
        	}
        }
        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        //组织删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        crit.add(Restrictions.eq("cBase.isDepute", 1));
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        subCrit.add(Restrictions.eq("sub.isDepute", 0));
       //受委托组织名称
        if (!TeeUtility.isNullorEmpty(cbModel.getSubName())) {
            crit.add(Restrictions.like("cBase.subName", cbModel.getSubName().trim(),MatchMode.ANYWHERE));
        }
        //性质
        if (!TeeUtility.isNullorEmpty(cbModel.getEntrustNature())) {
            crit.add(Restrictions.eq("cBase.entrustNature", cbModel.getEntrustNature()));
        }
        //统一社会信用代码
        if (!TeeUtility.isNullorEmpty(cbModel.getCode())) {
            crit.add(Restrictions.like("cBase.code", cbModel.getCode(),MatchMode.ANYWHERE));
        }
        //分页条件
        crit.setFirstResult(start);
        crit.setMaxResults(length);
        //排序
        crit.addOrder(Order.desc("cBase.updateTime"));
        return crit.list();
    }
    
    /**
     * 委托组织管理 总数  权限控制
     * @param cbModel
     * @param orgCtrl
     * @return
     * @date:2019年3月6日下午3:18:49
     * @author:yxy
     */
    public long listManOrgCount(SubjectModel cbModel, OrgCtrlInfoModel orgCtrl) {
    	//获取session
    	Session session = super.getSession();
        //主表组织tbl_base_subject 取别名为 cBase
        Criteria crit = session.createCriteria(Subject.class, "cBase");
        //第一层表主体tbl_base_subject 取别名为 sub
    	DetachedCriteria subCrit = DetachedCriteria.forClass(Subject.class, "sub");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 查询条件，关联关系
        subCrit.add(Property.forName("sub.parentId").eqProperty("cBase.id"));
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if(gradeAdministrator){
        	//只能查看本级
        	subCrit.add(Restrictions.eq("cBase.area",orgCtrl.getAdminDivisionCode()));
        }else{
        	//机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
            int orgType = orgCtrl.getOrgType();
            if(2 == orgType){
        		//执法部门查询本单位数据
        		subCrit.add(Restrictions.eq("sub.departmentCode", orgCtrl.getDepartId()));
        	}else if(3 == orgType){
        		crit.add(Restrictions.eq("cBase.parentId", orgCtrl.getSubjectId()));
        	}else{
        		Integer totalCount = 0;
	            return totalCount.intValue();
        	}
        }
        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        //组织删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        crit.add(Restrictions.eq("cBase.isDepute", 0));
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        subCrit.add(Restrictions.eq("sub.isDepute", 1));
       //受委托组织名称
        if (!TeeUtility.isNullorEmpty(cbModel.getSubName())) {
            crit.add(Restrictions.like("cBase.subName", cbModel.getSubName().trim(),MatchMode.ANYWHERE));
        }
        //性质
        if (!TeeUtility.isNullorEmpty(cbModel.getEntrustNature())) {
            crit.add(Restrictions.eq("cBase.entrustNature", cbModel.getEntrustNature()));
        }
        //统一社会信用代码
        if (!TeeUtility.isNullorEmpty(cbModel.getCode())) {
            crit.add(Restrictions.like("cBase.code", cbModel.getCode(),MatchMode.ANYWHERE));
        }
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.intValue();
    }
    
    /**
     * 根据部门id获取部门名称
     * @param id
     * @return
     * @date:2019年3月15日上午11:00:46
     * @author:yxy
     */
    public Map<String, Object> deptName(String id){
    	String sql = "select name id from tbl_base_organization where id = '"+id+"'";
		return   super.executeNativeUnique(sql, null);
    }
    /**
     * 获取执法主体的UUID
     * @return
     * @date:2019年3月19日上午9:38:03
     * @author:yxy
     */
    public Map<String, Object> subjectId(){
    	String sql = "select uuid id from menu_group where menu_group_name = '执法主体'";
		return   super.executeNativeUnique(sql, null);
    }
    
    /**
     * 关系表中获取UUID
     * @param id
     * @return
     * @date:2019年3月19日上午10:15:51
     * @author:yxy
     */
    public Map<String, Object> getUuid(String id){
    	String sql = "select user_uuid id from tbl_sys_department_user where is_delete = 0 and subject_id = '"+id+"'";
		return   super.executeNativeUnique(sql, null);
    }
    
    /**
     * 根据创建人id获取创建人姓名
     * @param id
     * @return
     * @date:2019年3月22日下午2:14:12
     * @author:yxy
     */
    public Map<String, Object> getuserName(String id){
    	String sql = " select user_name name from person where user_id = '"+id+"'";
		return   super.executeNativeUnique(sql, null);
    }
    
    /**
     * 获取系统部门的UUID
     * @param id
     * @return
     * @date:2019年3月19日上午10:29:09
     * @author:yxy
     */
    public Map<String, Object> deptUuid(String id){
    	String sql = "select sys_org_id id from tbl_sys_bussiness_relation where business_subject_id = '"+id+"' and rownum = 1";
    	Map<String, Object> aMap = super.executeNativeUnique(sql, null);
    	return  aMap ;
    }
    
    /**
     * 执法主体综合查询 分页 权限控制
     * @param start
     * @param length
     * @param cbModel
     * @param orgCtrl
     * @return
     * @date:2019年4月2日下午2:02:38
     * @author:yxy
     */
    public List<Subject> generalListByPageSubSearch(int start, int length, SubjectModel cbModel, OrgCtrlInfoModel orgCtrl){
		Object[] object = new Object[2];
        // 创建HQL查询语句
		StringBuffer hql = new StringBuffer("from Subject sub where exists (select dd.adminDivisionCode from AdminDivisionDivided dd where dd.adminDivisionCode = sub.area ");
        // 创建参数集合
        List<Object> params = new ArrayList<Object>();
        //地区  
	    if(!TeeUtility.isNullorEmpty(orgCtrl.getLevelCode())){
		    if("0200".equals(orgCtrl.getLevelCode())){
		        // 控制省級层级，及省级以下层级
		    	hql.append(" and dd.provincialCode  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
		    if("0300".equals(orgCtrl.getLevelCode())){
		        // 控制市州級层级，及市州以下层级
		    	hql.append(" and dd.cityCode  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
		    if("0400".equals(orgCtrl.getLevelCode())){
		        // 控制区县层级
		    	hql.append(" and dd.districtCode  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
		    if("0500".equals(orgCtrl.getLevelCode())){
		        // 控制乡镇街道层级
		    	hql.append(" and dd.streetCode  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
	    }
        //所属领域
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgSys())){
        	hql.append(" and sub.orgSys in ('"+cbModel.getOrgSys().trim().replace(",", "','")+"')");
        }
        //主体名称
        if(!TeeUtility.isNullorEmpty(cbModel.getSubName())){
        	hql.append(" and sub.subName like ? ");
            params.add("%" + cbModel.getSubName().trim() + "%");
        }
        //统一社会信用代码
        if(!TeeUtility.isNullorEmpty(cbModel.getCode())){
        	hql.append(" and sub.code like ? ");
            params.add("%" + cbModel.getCode().trim() + "%");
        }
        //部门性质
        if(!TeeUtility.isNullorEmpty(cbModel.getNature())){
        	hql.append(" and sub.nature in ('"+cbModel.getNature().trim().replace(",", "','")+"') ");
        }
        //部门地区
        if(!TeeUtility.isNullorEmpty(cbModel.getArea())){
        	hql.append(" and sub.area in ('"+cbModel.getArea().trim().replace(",", "','")+"') ");
        }
        //部门层级
        if(!TeeUtility.isNullorEmpty(cbModel.getSubLevel())){
        	hql.append(" and sub.subLevel in ('"+cbModel.getSubLevel().trim().replace(",", "','")+"') ");
        }
        //是否分配账号0未分配1已分配
        if(!TeeUtility.isNullorEmpty(cbModel.getIsUser())){
        	if(cbModel.getIsUser() == 1){
            	hql.append(" and sub.userName is not null ");
        	}else{
        		hql.append(" and sub.userName is null ");
        	}
        }
        //职权类型
        if(!TeeUtility.isNullorEmpty(cbModel.getSubjectPower())){
        	hql.append(" and exists (select sp.subjectId from SubjectSubpower sp where sp.subjectId=sub.id and sp.subjectPowerId in ('"+cbModel.getSubjectPower().trim().replace(",", "','")+"') )");
        }
        //执法人员个数 1 小于10  2 10-50  3 51-100  4 101-200  5 大于200
        if(!TeeUtility.isNullorEmpty(cbModel.getPersonNo())){
        	if(cbModel.getPersonNo() == 1){
        		hql.append(" and not exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) >= 10 ) ");
        	}
        	if(cbModel.getPersonNo() == 2){
        		hql.append(" and exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) >= 10 and count(subper.personId) <=50 ) ");
        	}
        	if(cbModel.getPersonNo() == 3){
        		hql.append(" and exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) > 50 and count(subper.personId) <=100 ) ) ");
        	}
        	if(cbModel.getPersonNo() == 4){
        		hql.append(" and exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) > 100 and count(subper.personId) <=200 ) ) ");
        	}
        	if(cbModel.getPersonNo() == 5){
        		hql.append(" and exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) > 200 ) ");
        	}
        }
        //委托组织个数  1 无  2 1-3  3 4-7  4 大于7
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgNo())){
        	if(cbModel.getOrgNo() == 1){
        		hql.append(" and not exists (select org.parentId,count(org.id) from Subject org where sub.id = org.parentId group by org.parentId having count(org.id) > 0) ");
        	}
        	if(cbModel.getOrgNo() == 2){
        		hql.append(" and exists (select org.parentId,count(org.id) from Subject org where sub.id = org.parentId group by org.parentId having count(org.id) >= 1 and count(org.id) <= 3 ) ");
        	}
        	if(cbModel.getOrgNo() == 3){
        		hql.append(" and exists (select org.parentId,count(org.id) from Subject org where sub.id = org.parentId group by org.parentId having count(org.id) >= 4 and count(org.id) <= 7 ) ");
        	}
        	if(cbModel.getOrgNo() == 4){
        		hql.append(" and exists (select org.parentId,count(org.id) from Subject org where sub.id = org.parentId group by org.parentId having count(org.id) > 7) ");
        	}
        }
        hql.append(" and sub.isDelete = 0 and sub.isDepute = 0 ");
        object[0] = hql;
        object[1] = params;
        List<Subject> subjects = pageFind(object[0].toString(), start, length,((List<Object>) object[1]).toArray());
        return subjects;
    }
	
    /**
     * 执法主体综合查询总数 权限控制
     * @param cbModel
     * @param orgCtrl
     * @return
     * @date:2019年4月2日下午2:03:06
     * @author:yxy
     */
    @SuppressWarnings("unchecked")
	public long generallistSearchSubCount(SubjectModel cbModel, OrgCtrlInfoModel orgCtrl) {
    	Object[] object = new Object[2];
        // 创建HQL查询语句
		StringBuffer hql = new StringBuffer("from Subject sub where exists (select dd.adminDivisionCode from AdminDivisionDivided dd where dd.adminDivisionCode = sub.area ");
        // 创建参数集合
        List<Object> params = new ArrayList<Object>();
        //地区  
	    if(!TeeUtility.isNullorEmpty(orgCtrl.getLevelCode())){
		    if("0200".equals(orgCtrl.getLevelCode())){
		        // 控制省級层级，及省级以下层级
		    	hql.append(" and dd.provincialCode  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
		    if("0300".equals(orgCtrl.getLevelCode())){
		        // 控制市州級层级，及市州以下层级
		    	hql.append(" and dd.cityCode  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
		    if("0400".equals(orgCtrl.getLevelCode())){
		        // 控制区县层级
		    	hql.append(" and dd.districtCode  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
		    if("0500".equals(orgCtrl.getLevelCode())){
		        // 控制乡镇街道层级
		    	hql.append(" and dd.streetCode  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
	    }
        //所属领域
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgSys())){
        	hql.append(" and sub.orgSys in ('"+cbModel.getOrgSys().trim().replace(",", "','")+"')");
        }
        //主体名称
        if(!TeeUtility.isNullorEmpty(cbModel.getSubName())){
        	hql.append(" and sub.subName like ? ");
            params.add("%" + cbModel.getSubName().trim() + "%");
        }
        //统一社会信用代码
        if(!TeeUtility.isNullorEmpty(cbModel.getCode())){
        	hql.append(" and sub.code like ? ");
            params.add("%" + cbModel.getCode().trim() + "%");
        }
        //部门性质
        if(!TeeUtility.isNullorEmpty(cbModel.getNature())){
        	hql.append(" and sub.nature in ('"+cbModel.getNature().trim().replace(",", "','")+"') ");
        }
        //部门地区
        if(!TeeUtility.isNullorEmpty(cbModel.getArea())){
        	hql.append(" and sub.area in ('"+cbModel.getArea().trim().replace(",", "','")+"') ");
        }
        //部门层级
        if(!TeeUtility.isNullorEmpty(cbModel.getSubLevel())){
        	hql.append(" and sub.subLevel in ('"+cbModel.getSubLevel().trim().replace(",", "','")+"') ");
        }
        //是否分配账号0未分配1已分配
        if(!TeeUtility.isNullorEmpty(cbModel.getIsUser())){
        	if(cbModel.getIsUser() == 1){
            	hql.append(" and sub.userName is not null ");
        	}else{
        		hql.append(" and sub.userName is null ");
        	}
        }
        //职权类型
        if(!TeeUtility.isNullorEmpty(cbModel.getSubjectPower())){
        	hql.append(" and exists (select sp.subjectId from SubjectSubpower sp where sp.subjectId=sub.id and sp.subjectPowerId in ('"+cbModel.getSubjectPower().trim().replace(",", "','")+"') )");
        }
        //执法人员个数 1 小于10  2 10-50  3 51-100  4 101-200  5 大于200
        if(!TeeUtility.isNullorEmpty(cbModel.getPersonNo())){
        	if(cbModel.getPersonNo() == 1){
        		hql.append(" and not exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) >= 10 ) ");
        	}
        	if(cbModel.getPersonNo() == 2){
        		hql.append(" and exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) >= 10 and count(subper.personId) <=50 ) ");
        	}
        	if(cbModel.getPersonNo() == 3){
        		hql.append(" and exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) > 50 and count(subper.personId) <=100 ) ) ");
        	}
        	if(cbModel.getPersonNo() == 4){
        		hql.append(" and exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) > 100 and count(subper.personId) <=200 ) ) ");
        	}
        	if(cbModel.getPersonNo() == 5){
        		hql.append(" and exists (select subper.subjectId,count(subper.subjectId) from TblSubjectPerson subper where sub.id = subper.subjectId group by  subper.subjectId having count(subper.personId) > 200 ) ");
        	}
        }
        //委托组织个数  1 无  2 1-3  3 4-7  4 大于7
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgNo())){
        	if(cbModel.getOrgNo() == 1){
        		hql.append(" and not exists (select org.parentId,count(org.id) from Subject org where sub.id = org.parentId group by org.parentId having count(org.id) > 0) ");
        	}
        	if(cbModel.getOrgNo() == 2){
        		hql.append(" and exists (select org.parentId,count(org.id) from Subject org where sub.id = org.parentId group by org.parentId having count(org.id) >= 1 and count(org.id) <= 3 ) ");
        	}
        	if(cbModel.getOrgNo() == 3){
        		hql.append(" and exists (select org.parentId,count(org.id) from Subject org where sub.id = org.parentId group by org.parentId having count(org.id) >= 4 and count(org.id) <= 7 ) ");
        	}
        	if(cbModel.getOrgNo() == 4){
        		hql.append(" and exists (select org.parentId,count(org.id) from Subject org where sub.id = org.parentId group by org.parentId having count(org.id) > 7) ");
        	}
        }
        hql.append(" and sub.isDelete = 0 and sub.isDepute = 0 ");
        object[0] = hql;
        object[1] = params;
        return super.count("select count(*) " +object[0].toString(),( (List<Object>) object[1]).toArray());
    }
    
    @SuppressWarnings("unchecked")
    public List<Subject> getSubjectRoles(OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        int orgType = 0;
        if(orgCtrlInfoModel.getOrgType() != null)
            orgType = orgCtrlInfoModel.getOrgType();
        if(orgType == 0)
            return new ArrayList<>();
        Criteria criteria = getCriteria(orgCtrlInfoModel);
        criteria.addOrder(Order.asc("subject.code"));
        return criteria.list();
    }

    private Criteria getCriteria(OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Session session = super.getSession();
        // 主查询对象：执法部门
        Criteria criteria = session.createCriteria(Subject.class, "subject");
        // 将SQL查询结果放入集合
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // exists条件：TBL_ADMIN_DIVISION_DIVIDED --关联行政区划表
        DetachedCriteria divisionCriteria = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        // 查询条件，关联关系
        divisionCriteria.add(Property.forName("division.adminDivisionCode").eqProperty("subject.area"));
        /// 内层查询条件-开始
        // 获取登录账户部门层级
        String levelCode = orgCtrlInfoModel.getLevelCode();
        // 获取登陆账户地区编号
        String adminDivisionCode = orgCtrlInfoModel.getAdminDivisionCode();
        if (!TeeUtility.isNullorEmpty(levelCode)) {
            if ("0200".equals(levelCode)) {
                // 省级及以下
                divisionCriteria.add(Restrictions.eq("provincialCode", adminDivisionCode));
            } else if ("0300".equals(levelCode)) {
                // 市级及以下
                divisionCriteria.add(Restrictions.eq("cityCode", adminDivisionCode));
            } else if ("0400".equals(levelCode)) {
                // 区级及以下
                divisionCriteria.add(Restrictions.eq("districtCode", adminDivisionCode));
            } else if ("0500".equals(levelCode)) {
                // 乡镇街道
                divisionCriteria.add(Restrictions.eq("streetCode", adminDivisionCode));
            }
        }
        // 内层查询条件-结束
        // exists(select ...)
        criteria.add(Subqueries.exists(divisionCriteria.setProjection(Projections.property("division.adminDivisionCode"))));
        String orgSys = orgCtrlInfoModel.getOrgSysCode();
        Integer orgType = orgCtrlInfoModel.getOrgType();
        if(orgType != null){
            if(orgType == 2 || orgType == 3 || orgCtrlInfoModel.getIsLawPerson()){
                if(!orgCtrlInfoModel.getGradeAdministrator()){
                    if(!TeeUtility.isNullorEmpty(orgSys)){
                        String[] orgSysArray = orgSys.split(",");
                        if(orgSysArray.length > 1){
                            LogicalExpression logicalExpression = Restrictions.or(Restrictions.like("orgSys", orgSysArray[0], MatchMode.ANYWHERE), Restrictions.like("orgSys", orgSysArray[1], MatchMode.ANYWHERE));
                            for (int i = 2; i < orgSysArray.length; i++) {
                                logicalExpression = Restrictions.or(logicalExpression, Restrictions.like("orgSys", orgSysArray[i], MatchMode.ANYWHERE));
                            }
                            criteria.add(logicalExpression);
                        }else{
                            criteria.add(Restrictions.like("orgSys", orgSysArray[0], MatchMode.ANYWHERE));
                        }
                    }
                }
            }
        }
        // 删除标志
        criteria.add(Restrictions.eq("subject.isDelete", 0));
        return criteria;
    }
}
