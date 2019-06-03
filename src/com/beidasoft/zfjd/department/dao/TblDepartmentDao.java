package com.beidasoft.zfjd.department.dao;

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
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class TblDepartmentDao extends TeeBaseDao<TblDepartmentInfo> {

    public List<TblDepartmentInfo> findUsers() {
        return super.find("from TblDepartmentInfo", null);
    }

    /**
     * 查询部门名称 权限控制
     * 
     * @param firstResult
     * @param rows
     * @param queryModel
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<TblDepartmentInfo> listByDeQ(int firstResult, int rows, TblDepartmentModel queryModel,OrgCtrlInfoModel orgCtrl) {
        if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
        	 Object[] object = new Object[2];
			 StringBuffer hql = new StringBuffer("from TblDepartmentInfo where isDelete = 0 and isExamine = 1 ");
		     List<Object> params = new ArrayList<Object>();
		     hql.append(" and name like ? ");
	         params.add("%" + queryModel.getName().trim() + "%");
	         if(!TeeUtility.isNullorEmpty(orgCtrl.getAdminDivisionCode())){
	        	 hql.append(" and administrativeDivision = ? ");
		         params.add(orgCtrl.getAdminDivisionCode());
	         }
	         object[0] = hql;
	         object[1] = params;
	         List<TblDepartmentInfo> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
	         return list;
        } else {
            if (!TeeUtility.isNullorEmpty(queryModel.getParentId())) {
            	 Object[] object = new Object[2];
				 StringBuffer hql = new StringBuffer("from TblDepartmentInfo where isDelete = 0 ");
			     List<Object> params = new ArrayList<Object>();
			     hql.append(" and id = ? ");
		         params.add(queryModel.getParentId());
		         if(!TeeUtility.isNullorEmpty(orgCtrl.getAdminDivisionCode())){
		        	 hql.append(" and administrativeDivision = ? ");
			         params.add(orgCtrl.getAdminDivisionCode());
		         }
		         object[0] = hql;
		         object[1] = params;
		         List<TblDepartmentInfo> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
		         return list;
            }
        }
        return null;

    }

    /**
     * 查询部门名称 全部
     * @param firstResult
     * @param rows
     * @param queryModel
     * @param orgCtrl
     * @return
     * @date:2019年3月14日下午3:42:18
     * @author:yxy
     */
    public List<TblDepartmentInfo> listByDe(int firstResult, int rows, TblDepartmentModel queryModel) {
        if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
        	 Object[] object = new Object[2];
			 StringBuffer hql = new StringBuffer("from TblDepartmentInfo where isDelete = 0 ");
		     List<Object> params = new ArrayList<Object>();
		     hql.append(" and name like ? ");
	         params.add("%" + queryModel.getName().trim() + "%");
	         object[0] = hql;
	         object[1] = params;
	         List<TblDepartmentInfo> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
	         return list;
        } else {
            if (!TeeUtility.isNullorEmpty(queryModel.getParentId())) {
            	 Object[] object = new Object[2];
				 StringBuffer hql = new StringBuffer("from TblDepartmentInfo where isDelete = 0 ");
			     List<Object> params = new ArrayList<Object>();
			     hql.append(" and id = ? ");
		         params.add(queryModel.getParentId());
		         object[0] = hql;
		         object[1] = params;
		         List<TblDepartmentInfo> list = super.pageFind(object[0].toString(), firstResult, rows, ((List<Object>) object[1]).toArray());
		         return list;
            }
        }
        return null;

    }
    
    /**
     * 查询归属人民政府 权限控制
     * 
     * @param firstResult
     * @param rows
     * @param queryModel
     * @return
     */
    public List<TblDepartmentInfo> listByGov(int firstResult, int rows, TblDepartmentModel queryModel,OrgCtrlInfoModel orgCtrl) {
        if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
            String hql = " select new TblDepartmentInfo(info.id,info.name) from TblDepartmentInfo info where isGovernment = 1 and isDelete = 0";
            hql += " and name like '%" + queryModel.getName() + "%'";
            List<TblDepartmentInfo> list = super.pageFind(hql, firstResult, rows, null);
            return list;
        } else {
            if (!TeeUtility.isNullorEmpty(queryModel.getParentId())) {
                String hql = " select new TblDepartmentInfo(info.id,info.name) from TblDepartmentInfo info where isGovernment = 1 and isDelete = 0";
                hql += " and id = '" + queryModel.getParentId() + "'";
                return super.pageFind(hql, firstResult, rows, null);
            }
        }
        return null;

    }

    /**
     * 根据parentid获取归属人民政府
     * 
     * @param firstResult
     * @param rows
     * @param id
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Map> listByPI(int firstResult, int rows, String parentId) {
        String sql = " select name from tbl_base_organization  where is_Delete = 0 and id = '" + parentId + "'";
        return (List<Map>) super.executeNativeQuery(sql, null, firstResult, rows);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Map> listByOrg(int firstResult, int rows, String id) {
        String sql = " select org_sys from tbl_base_organization  where is_Delete = 0 and id = '" + id + "'";
        return (List<Map>) super.executeNativeQuery(sql, null, firstResult, rows);
    }

    /**
     * 获取部门地区
     * 
     * @param firstResult
     * @param rows
     * @param q
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Map> listBySysCode(int firstResult, int rows, String q) {
        if (!TeeUtility.isNullorEmpty(q)) {
            String sql = " select code_no as id,code_name as name from sys_code where parent_id = '4040' and code_name like '%" + q + "%'";
            return (List<Map>) super.executeNativeQuery(sql, null, firstResult, rows);
        }
        return null;
    }

    /**
     * 所属地区  权限控制
     * @param firstResult
     * @param rows
     * @param q
     * @param id
     * @param orgCtrl
     * @return
     * @date:2019年3月13日下午4:20:57
     * @author:yxy
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Map> listBySysCode2(int firstResult, int rows, String q, String id,OrgCtrlInfoModel orgCtrl) {
    	Object[] object = new Object[2];
    	List<Object> params = new ArrayList<Object>();
    	if (!TeeUtility.isNullorEmpty(q)) {
        	StringBuffer hql = new StringBuffer("select * from sys_code r where exists(select 1 from tbl_admin_division_divided e where e.admin_division_code=r.code_no");
        	//通过层级代码，控制数据查询范围 
            if(!TeeUtility.isNullorEmpty(orgCtrl.getLevelCode())){
//                if("0200".equals(orgCtrl.getLevelCode())){
//                    // 控制省級层级，及省级以下层级
//                	hql.append(" and e.provincial_code = ? ) and parent_id = '4040' ");
//                    params.add(orgCtrl.getAdminDivisionCode());
//                }
//                if("0300".equals(orgCtrl.getLevelCode())){
//                    // 控制市州級层级，及市州以下层级
//                	hql.append(" and e.city_code = ? ) and parent_id = '4040' ");
//                    params.add(orgCtrl.getAdminDivisionCode());
//                }
//                if("0400".equals(orgCtrl.getLevelCode())){
//                    // 控制区县层级
//                	hql.append(" and e.district_code = ? ) and parent_id = '4040' ");
//                    params.add(orgCtrl.getAdminDivisionCode());
//                }
//                if("0500".equals(orgCtrl.getLevelCode())){
//                    // 控制乡镇街道层级
//                	hql.append(" and e.street_code = ? ) and parent_id = '4040' ");
//                    params.add(orgCtrl.getAdminDivisionCode());
//                }
            	hql.append(" and e.admin_division_code = ? ) and parent_id = '4040' ");
            	params.add(orgCtrl.getAdminDivisionCode());
            }else{
            	hql.append(") and parent_id = '4040' ");
            }
        	hql.append(" and code_name like ? ");
            params.add("%" + q.trim() + "%");
            object[0] = hql;
            object[1] = params;
            List<Map> maps = (List<Map>) super.executeNativeQuery(object[0].toString(),((List<Object>) object[1]).toArray(), firstResult, rows);
            return maps;
        } else {
        	StringBuffer hql = new StringBuffer( "select * from sys_code r where exists(select 1 from tbl_admin_division_divided e where e.admin_division_code=r.code_no");
        	//通过层级代码，控制数据查询范围 
            if(!TeeUtility.isNullorEmpty(orgCtrl.getLevelCode())){
//                if("0200".equals(orgCtrl.getLevelCode())){
//                    // 控制省級层级，及省级以下层级
//                	hql.append(" and e.provincial_code = ? ) and parent_id = '4040' ");
//                    params.add(orgCtrl.getAdminDivisionCode());
//                }
//                if("0300".equals(orgCtrl.getLevelCode())){
//                    // 控制市州級层级，及市州以下层级
//                	hql.append(" and e.city_code = ? ) and parent_id = '4040' ");
//                    params.add(orgCtrl.getAdminDivisionCode());
//                }
//                if("0400".equals(orgCtrl.getLevelCode())){
//                    // 控制区县层级
//                	hql.append(" and e.district_code = ? ) and parent_id = '4040' ");
//                    params.add(orgCtrl.getAdminDivisionCode());
//                }
//                if("0500".equals(orgCtrl.getLevelCode())){
//                    // 控制乡镇街道层级
//                	hql.append(" and e.street_code = ? ) and parent_id = '4040' ");
//                    params.add(orgCtrl.getAdminDivisionCode());
//                }
            	hql.append(" and e.admin_division_code = ? ) and parent_id = '4040' ");
            	params.add(orgCtrl.getAdminDivisionCode());
            }else{
            	hql.append(") and parent_id = '4040' ");
            }
        	if (!TeeUtility.isNullorEmpty(id)) {
            	hql.append(" and code_no like ? ");
                params.add("%" + id.trim() + "%");
                object[0] = hql;
                object[1] = params;
            }
            return (List<Map>) super.executeNativeQuery(object[0].toString(),((List<Object>) object[1]).toArray(), firstResult, rows);
        }
    }

    /**
     * 执法部门分页
     * 
     * @param firstResult
     * @param rows
     * @param queryModel
     * @return
     */
    public List<TblDepartmentInfo> listByPage(int firstResult, int rows, TblDepartmentModel queryModel) {
        String hql = " from TblDepartmentInfo where isDelete = 0";

        if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
            hql += " and name like '%" + queryModel.getName() + "%'";
        }
        if (!TeeUtility.isNullorEmpty(queryModel.getNature())) {
            hql += " and nature = " + queryModel.getNature();
        }
        if (!TeeUtility.isNullorEmpty(queryModel.getIsExamine())) {
            hql += " and is_examine = " + queryModel.getIsExamine();
        }
        hql += " order by createTime desc ";
        return super.pageFind(hql, firstResult, rows, null);

    }

    public long getTotal() {
        return super.count("select count(id) from TblDepartmentInfo", null);
    }

    public long getTotal(TblDepartmentModel queryModel) {
        String hql = "select count(id) from TblDepartmentInfo where isDelete = 0";

        if (!TeeUtility.isNullorEmpty(queryModel.getName())) {
            hql += " and name like '%" + queryModel.getName() + "%'";
        }
        if (!TeeUtility.isNullorEmpty(queryModel.getNature())) {
            hql += " and nature = " + queryModel.getNature();
        }
        if (!TeeUtility.isNullorEmpty(queryModel.getIsExamine())) {
            hql += " and is_examine = " + queryModel.getIsExamine();
        }
        return super.count(hql, null);
    }

    /**
     * 多删
     * 
     * @param ids
     * @return
     */
    public long deletes(String[] ids) {
        // TODO Auto-generated method stub
        String hql = "";
        for (int i = 0; i < ids.length; i++) {
            TblDepartmentInfo department = new TblDepartmentInfo();
            hql = ("update TblDepartmentInfo set isDelete = '1' where id = '" + ids[i] + "'");
            department.setDeleteTime(new Date());
            this.executeUpdate(hql, null);
        }
        return this.executeUpdate(hql, null);
    }

    /**
     * 
     * @Function: findDepartment()
     * @Description: 查询部门ID，名称
     *
     * @param: q
     *             前台传递参数
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: songff
     * @date: 2019年1月13日 下午7:57:11
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<TblDepartmentInfo> findDepartment(String q) {
        // 定义hql
        StringBuffer hql = new StringBuffer();
        // 参数集合
        ArrayList list = new ArrayList();
        hql.append(" from TblDepartmentInfo where isDelete = 0 ");
        if (q != null && !"".equals(q)) {
            hql.append(" and name like ? ");
            list.add("%" + q + "%");
        }
        hql.append(" order by name desc");
        Object[] params = list.toArray();
        return super.find(hql.toString(), params);
    }

    /**
     * 
     * @Function: findDepartmentList()
     * @Description: 查询部门
     *
     * @param:描述1描述
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: songff
     * @date: 2019年2月15日 下午6:22:49
     *
     */
    public List<TblDepartmentInfo> findDepartmentList(TblDepartmentModel model) {
        String sql = " from TblDepartmentInfo where isDelete = 0";
        if (!TeeUtility.isNullorEmpty(model.getName())) {
            sql = sql + " and name like '%" + model.getName() + "%'";
        }
        if (!TeeUtility.isNullorEmpty(model.getId())) {
            sql = sql + " and id = '" + model.getId() + "'";
        }
        if (!TeeUtility.isNullorEmpty(model.getOrgSys())) {
            sql = sql + " and orgSys = '" + model.getOrgSys() + "'";
        }
        if (!TeeUtility.isNullorEmpty(model.getIds())) {
            sql = sql + " and id in ('" + model.getIds().replace(",", "','") + "')";
        }
        sql = sql + " order by name desc";
        return super.find(sql, null);
    }

    /**
     * 
     * @Function: findListCountByPage
     * @Description: 执法部门查询条数
     *
     * @param departmentModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午7:15:05
     */
    public Long findListCountByPage(TblDepartmentModel departmentModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(departmentModel);
        @SuppressWarnings("unchecked")
        long count = count("select count(*) " + object[0].toString(), ((List<Object>) object[1]).toArray());
        return count;
    }

    /**
     * 
     * @Function: findListByPage
     * @Description: 执法部门条件查询
     *
     * @param firstResult
     * @param rows
     * @param departmentModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午7:15:31
     */
    public List<TblDepartmentInfo> findListByPage(int firstResult, int rows, TblDepartmentModel departmentModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(departmentModel);
        @SuppressWarnings("unchecked")
        List<TblDepartmentInfo> departments = pageFind(object[0].toString(), firstResult, rows,
                ((List<Object>) object[1]).toArray());
        return departments;
    }

    /**
     * 
     * @Function: getHql
     * @Description: 获取HQL及参数集合
     *
     * @param departmentModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月20日 下午7:17:12
     */
    private Object[] getHql(TblDepartmentModel departmentModel) {
        // TODO Auto-generated method stub
        Object[] object = new Object[2];
        // 创建HQL查询语句
        StringBuffer hql = new StringBuffer("from TblDepartmentInfo where isDelete = 0 ");
        // 创建参数集合
        List<Object> params = new ArrayList<Object>();
        // 判断是否添加查询条件：执法部门名称
        if (!TeeUtility.isNullorEmpty(departmentModel.getName())) {
            hql.append(" and name like ? ");
            params.add("%" + departmentModel.getName().trim() + "%");
        }
        // 判断是否添加查询条件：部门性质
        if (!TeeUtility.isNullorEmpty(departmentModel.getNature())) {
            hql.append(" and nature = ? ");
            params.add(departmentModel.getNature().trim());
        }
        // 判断是否添加查询条件：审核状态
        if (!TeeUtility.isNullorEmpty(departmentModel.getIsExamine())) {
            hql.append(" and isExamine = ? ");
            params.add(departmentModel.getIsExamine());
        }
        object[0] = hql;
        object[1] = params;
        return object;
    }

    /**
     * 
     * @Function: findListByPageRoles
     * @Description: 执法部门分页查询（带数据权限）
     *
     * @param firstResult
     * @param rows
     * @param departmentModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月28日 下午8:58:47
     */
    @SuppressWarnings("unchecked")
    public List<TblDepartmentInfo> findListByPageRoles(int firstResult, int rows, TblDepartmentModel departmentModel,
            OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Criteria criteria = getCriteria(departmentModel, orgCtrlInfoModel);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(rows);
        criteria.addOrder(Order.asc("department.isExamine"));
        criteria.addOrder(Order.asc("department.orgSys"));
        criteria.addOrder(Order.asc("department.updateTime"));
//        criteria.addOrder(Order.asc("department.departmentCode"));
        return criteria.list();
    }

    /**
     * 
     * @Function: findListCountByPageRoles
     * @Description: 执法部门查询总条数（带数据权限）
     *
     * @param departmentModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月28日 下午8:58:54
     */
    public Long findListCountByPageRoles(TblDepartmentModel departmentModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Criteria criteria = getCriteria(departmentModel, orgCtrlInfoModel);
        criteria.setProjection(Projections.rowCount());
        Integer total = Integer.parseInt(criteria.uniqueResult().toString());
        return total.longValue();
    }

    /**
     * 
     * @Function: getCriteria
     * @Description: 查询条件处理
     *
     * @param departmentModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月28日 下午8:59:12
     */
    private Criteria getCriteria(TblDepartmentModel departmentModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        Session session = super.getSession();
        // 主查询对象：执法部门
        Criteria criteria = session.createCriteria(TblDepartmentInfo.class, "department");
        // 将SQL查询结果放入集合
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // exists条件：TBL_ADMIN_DIVISION_DIVIDED --关联行政区划表
        DetachedCriteria divisionCriteria = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        // 查询条件，关联关系
        divisionCriteria.add(Property.forName("division.adminDivisionCode").eqProperty("department.administrativeDivision"));
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
        // 删除标志
        criteria.add(Restrictions.eq("department.isDelete", 0));
        // 判断是否添加查询条件：执法部门名称
        if (!TeeUtility.isNullorEmpty(departmentModel.getName())) {
            criteria.add(Restrictions.like("department.name", departmentModel.getName().trim(), MatchMode.ANYWHERE));
        }
        // 判断是否添加查询条件：部门性质
        if (!TeeUtility.isNullorEmpty(departmentModel.getNature())) {
            criteria.add(Restrictions.eq("department.nature", departmentModel.getNature().trim()));
        }
        // 判断是否添加查询条件：审核状态
        if (!TeeUtility.isNullorEmpty(departmentModel.getIsExamine())) {
            criteria.add(Restrictions.eq("department.isExamine", departmentModel.getIsExamine()));
        }
        return criteria;
    }
    @SuppressWarnings("unchecked")
    public List<TblDepartmentInfo> getDepartmentRoles(OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        int orgType = 0;
        if(orgCtrlInfoModel.getOrgType() != null)
            orgType = orgCtrlInfoModel.getOrgType();
        if(orgType == 0)
            return new ArrayList<>();
        Criteria criteria = getCriteria(orgCtrlInfoModel);
        criteria.addOrder(Order.asc("department.departmentCode"));
        return criteria.list();
    }

    private Criteria getCriteria(OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Session session = super.getSession();
        // 主查询对象：执法部门
        Criteria criteria = session.createCriteria(TblDepartmentInfo.class, "department");
        // 将SQL查询结果放入集合
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // exists条件：TBL_ADMIN_DIVISION_DIVIDED --关联行政区划表
        DetachedCriteria divisionCriteria = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        // 查询条件，关联关系
        divisionCriteria
                .add(Property.forName("division.adminDivisionCode").eqProperty("department.administrativeDivision"));
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
        criteria.add(Restrictions.eq("department.isDelete", 0));
        return criteria;
    }
    /**
     * 执法部门分页
    * @Function: findListByPageSearch()
    * @Description: 通过区县权限表 TBL_ADMIN_DIVISION_DIVIDED，
    * *系统关系表TBL_SYS_BUSSINESS_RELATION，组合控制数据查询权限
    *	select * from tbl_base_organization org where administrative_division= '520000' and is_delete = 0
    * @author: yxy
    * @date: 2019年3月5日 下午6:20:52 
    *
     */
	@SuppressWarnings("unchecked")
    public List<TblDepartmentInfo> findListByPageSearch(int start, int length, TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl){
        //参考上述SQL理解下面代码逻辑。
        //获取session
        Session session = super.getSession();
        //主表tbl_base_organization 取别名为 cBase
        Criteria crit = session.createCriteria(TblDepartmentInfo.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //监督删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        if(!gradeAdministrator){
        	return null;
        }
        //只能查看本级
        crit.add(Restrictions.eq("cBase.administrativeDivision",orgCtrl.getAdminDivisionCode()));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //部门全称
        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
        }
        //部门性质
        if (!TeeUtility.isNullorEmpty(cbModel.getNature())) {
            crit.add(Restrictions.eq("cBase.nature", cbModel.getNature()));
        }
        //审核状态
        if (!TeeUtility.isNullorEmpty(cbModel.getIsExamine())) {
            crit.add(Restrictions.eq("cBase.isExamine", cbModel.getIsExamine()));
        }
        //分页条件
        crit.setFirstResult(start);
        crit.setMaxResults(length);
        //排序
        crit.addOrder(Order.asc("cBase.isExamine"));
        crit.addOrder(Order.asc("cBase.orgSys"));
        crit.addOrder(Order.asc("cBase.updateTime"));
        return crit.list();
    }
    /**
     * 
    * @Function: listSearchCount()
    * @Description: 执法部门总数
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
    public long listSearchCount(TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
    	//获取session
    	Session session = super.getSession();
        //主表tbl_base_organization 取别名为 cBase
        Criteria crit = session.createCriteria(TblDepartmentInfo.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //监督删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        if(!gradeAdministrator){
        	Integer totalCount = 0;
            return totalCount.intValue();
        }
        //只能查看本级
        crit.add(Restrictions.eq("cBase.administrativeDivision",orgCtrl.getAdminDivisionCode()));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //部门全称
        if (!TeeUtility.isNullorEmpty(cbModel.getName())) {
            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
        }
        //部门性质
        if (!TeeUtility.isNullorEmpty(cbModel.getNature())) {
            crit.add(Restrictions.eq("cBase.nature", cbModel.getNature()));
        }
        //审核状态
        if (!TeeUtility.isNullorEmpty(cbModel.getIsExamine())) {
            crit.add(Restrictions.eq("cBase.isExamine", cbModel.getIsExamine()));
        }
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.intValue();
    }
    
    /**
     * 获取归属政府，部门层级，和地区
     * @param firstResult
     * @param rows
     * @param orgCtrl
     * @return
     * @date:2019年3月14日下午2:34:13
     * @author:yxy
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> parentIdToLevel(int firstResult, int rows,OrgCtrlInfoModel orgCtrl){
		String hql = "select id,name,administrative_division,dept_level from tbl_base_organization where administrative_division = '"+orgCtrl.getAdminDivisionCode()+"' and is_government = 1 ";
	 	return (List<Map>) super.executeNativeQuery(hql, null, firstResult, rows); 
	}
	
    /**
     * 获取下级的执法部门列表
    * @Function: findDeptsNextLevel()
    * @param: tblDepartmentModel
    * @author: hoax
    * @date: 2019年3月5日 下午6:20:52 
    *
     */
    @SuppressWarnings("unchecked")
    public List<TblDepartmentInfo> findDeptsNextLevel(TblDepartmentModel tblDepartmentModel){
        //参考上述SQL理解下面代码逻辑。
        //获取session
        Session session = super.getSession();
        //主表tbl_base_organization 取别名为 cBase
        Criteria crit = session.createCriteria(TblDepartmentInfo.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        //第一层exists 表 TBL_ADMIN_DIVISION_DIVIDED 取别名为 division 
        DetachedCriteria divisionCrit = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        //设置区划权限表 TBL_ADMIN_DIVISION_DIVIDED 和 主体表tbl_base_subject关联的字段
        divisionCrit.add(Property.forName("division.adminDivisionCode").eqProperty("cBase.administrativeDivision"));
        //层级代码（002--省級层级编码code,003--市州級层级编码code,004--区县层级code）
        //检验参数，原部门层级不可为空
        String levelCode = "";
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getDeptLevel())) {
            levelCode = tblDepartmentModel.getDeptLevel();
        }else{
            return null;
        }
        //行政区划(本质用来刷选出，主体表中，对应层级的主体ID，从而关联案件表中的主体ID，控制案件数据的查询范围。)
        //检验参数，原部门行政区划代码不可为空
        String adminDivisionCode = "";
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getAdministrativeDivision())) {
            adminDivisionCode = tblDepartmentModel.getAdministrativeDivision();
        }else{
            return null;
        }
        //通过层级代码，控制数据查询范围 
        if(!TeeUtility.isNullorEmpty(levelCode)){
            if("0100".equals(levelCode)){
                // 控制省級层级，及省级以下层级
                divisionCrit.add(Restrictions.eq("levelCode", "0200"));
            }
            if("0200".equals(levelCode)){
                // 控制省級层级，及省级以下层级
                divisionCrit.add(Restrictions.eq("provincialCode", adminDivisionCode));
                divisionCrit.add(Restrictions.eq("levelCode", "0300"));
            }
            if("0300".equals(levelCode)){
                // 控制市州級层级，及市州以下层级
                divisionCrit.add(Restrictions.eq("cityCode", adminDivisionCode));
                divisionCrit.add(Restrictions.eq("levelCode", "0400"));
            }
            if("0400".equals(levelCode)){
                // 控制区县层级
                divisionCrit.add(Restrictions.eq("districtCode", adminDivisionCode));
                divisionCrit.add(Restrictions.eq("levelCode", "0500"));
            }
            if("0500".equals(levelCode)){
                // 控制区县层级
                return null;
            }
        }
        crit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("adminDivisionCode"))));
        //检验参数，传入id则进行筛选，去除自己本身
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getId())) {
            crit.add(Restrictions.ne("cBase.id", tblDepartmentModel.getId()));
        }
        //删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //是否人民政府部门
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getIsGovernment())) {
            crit.add(Restrictions.eq("cBase.isGovernment", tblDepartmentModel.getIsGovernment()));
        }
        //是否垂管
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getIsManubrium())) {
            crit.add(Restrictions.eq("cBase.isManubrium", tblDepartmentModel.getIsManubrium()));
        }
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //审核状态
        crit.add(Restrictions.eq("cBase.isExamine", 1));
        //排序
        crit.addOrder(Order.asc("cBase.administrativeDivision"));
        return crit.list();
    } 
    
    /**
     * 获取本级执法部门
    * @Function: findDeptsSameLevel()
    * @param: tblDepartmentModel
    * @author: hoax
    * @date: 2019年3月5日 下午6:20:52 
    *
     */
    @SuppressWarnings("unchecked")
    public List<TblDepartmentInfo> findDeptsSameLevel(TblDepartmentModel tblDepartmentModel){
        String sql = " from TblDepartmentInfo where isDelete = 0 ";
        //检验参数，传入id则进行筛选，去除自己本身
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getId())) {
            sql = sql + " and id <> '" + tblDepartmentModel.getId() + "' ";
        }
        //检验参数，行政区划代码不可为空
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getAdministrativeDivision())) {
            sql = sql + " and administrativeDivision ='" + tblDepartmentModel.getAdministrativeDivision() + "' ";
        }else{
            return null;
        }
        //是否人民政府部门参数
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getIsGovernment())) {
            sql = sql + " and isGovernment = '" + tblDepartmentModel.getIsGovernment() + "' ";
        }
        //是否垂管参数
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getIsManubrium())) {
            sql = sql + " and isManubrium = '" + tblDepartmentModel.getIsManubrium() + "' ";
        }
        sql = sql + "and isExamine = 1 ";
        sql = sql + " order by name desc";
        return super.find(sql, null);
    } 
    
    /**
     * 通过parentId查询执法部门
    * @Function: findDeptsSameLevel()
    * @param: tblDepartmentModel
    * @author: hoax
    * @date: 2019年3月5日 下午6:20:52 
    *
     */
    @SuppressWarnings("unchecked")
    public List<TblDepartmentInfo> findManuDeptsByParentIds(TblDepartmentModel tblDepartmentModel){
        String sql = " from TblDepartmentInfo where isDelete = 0 ";
        //检验参数，传入id则进行筛选，去除自己本身
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getId())) {
            sql = sql + " and id <> '" + tblDepartmentModel.getId() + "' ";
        }
        //行政区划代码参数
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getAdministrativeDivision())) {
            sql = sql + " and administrativeDivision ='" + tblDepartmentModel.getAdministrativeDivision() + "' ";
        }
        //parentId字段不可为空
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getDroopId())) {
            sql = sql + " and droopId = '" + tblDepartmentModel.getDroopId() + "' ";
        }else{
            return null;
        }
        //是否垂管参数
        if (!TeeUtility.isNullorEmpty(tblDepartmentModel.getIsManubrium())) {
            sql = sql + " and isManubrium = '" + tblDepartmentModel.getIsManubrium() + "' ";
        }
        sql = sql + "and isExamine = 1 ";
        sql = sql + " order by name desc";
        return super.find(sql, null);
    } 
    
    /**
     * 获取指定行政区划地区的人民政府（居委会）
    * @Function: findOrderLvGovByAdminDivisionCode()
    * @Description: 通过区县权限表 TBL_ADMIN_DIVISION_DIVIDED，
    * *系统关系表TBL_SYS_BUSSINESS_RELATION，组合控制数据查询权限
    *   select * from tbl_base_organization org where administrative_division= '520000' and is_delete = 0
    * @author: yxy
    * @date: 2019年3月5日 下午6:20:52 
    *
     */
    public List<TblDepartmentInfo> findOrderLvGovByAdminDivisionCode(TblDepartmentModel queryModel){
        if (!TeeUtility.isNullorEmpty(queryModel.getAdministrativeDivision())) {
            String hql = " select new TblDepartmentInfo(info.id,info.name) from TblDepartmentInfo info where isDelete = 0";
            hql = hql + " and isGovernment = 1 ";
            hql = hql + " and administrativeDivision = '" + queryModel.getAdministrativeDivision() + "' ";
            
            List<TblDepartmentInfo> list = super.find(hql, null);
            return list;
        }else{
            return null;
        }
    }
    
    /**
     * 获取执法部门的UUID
     * @return
     * @date:2019年3月18日下午5:54:03
     * @author:yxy
     */
    public Map<String, Object> deptId(){
    	String sql = "select uuid id from menu_group where menu_group_name = '执法部门'";
		return   super.executeNativeUnique(sql, null);
    }
    
    /**
     * 获取系统部门的UUID
     * @param id
     * @return
     * @date:2019年3月18日下午6:39:52
     * @author:yxy
     */
    public Map<String, Object> deptUuid(String id){
    	String sql = "select sys_org_id id from tbl_sys_bussiness_relation where business_dept_id = '"+id+"' and rownum = 1";
		return   super.executeNativeUnique(sql, null);
    }
    
    /**
     * 关系表中获取UUID
     * @param id
     * @return
     * @date:2019年3月18日下午6:47:03
     * @author:yxy
     */
    public Map<String, Object> getUuid(String id){
    	String sql = "select user_uuid id from tbl_sys_department_user where is_delete = 0 and department_id = '"+id+"'";
		return   super.executeNativeUnique(sql, null);
    }
    
    
    /**
     * 执法机关综合查询 分页 权限控制
     * @param start
     * @param length
     * @param cbModel
     * @param orgCtrl
     * @return
     * @date:2019年3月22日下午4:15:15
     * @author:yxy
     */
	@SuppressWarnings("unchecked")
    public List<TblDepartmentInfo> generalListByPageSearch(int start, int length, TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl){
		Object[] object = new Object[2];
        // 创建HQL查询语句
        StringBuffer hql = new StringBuffer("from TblDepartmentInfo dept where exists (select dd.adminDivisionCode from AdminDivisionDivided dd where dd.adminDivisionCode = dept.administrativeDivision ");
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
        // 判断主体名称组织名称职权名称  
        if ((TeeUtility.isNullorEmpty(cbModel.getSubName()))&&(TeeUtility.isNullorEmpty(cbModel.getOrgName()))&&(TeeUtility.isNullorEmpty(cbModel.getPowerName()))) {
        	
        }else{
        	hql.append(" and exists (select sub.id from Subject sub where sub.isDepute = 0 and dept.id = sub.departmentCode ");
        	if(!TeeUtility.isNullorEmpty(cbModel.getSubName())){
        		hql.append(" and sub.subName like ? ");
                params.add("%" + cbModel.getSubName().trim() + "%");
            }
        	if(!TeeUtility.isNullorEmpty(cbModel.getOrgName())){
        		hql.append("  and exists (select org.id from Subject org where org.subName like ? and org.isDepute = 1 and org.isDelete = 0 and org.parentId = sub.id) ");
                params.add("%" + cbModel.getOrgName().trim() + "%");
        	}
        	if(!TeeUtility.isNullorEmpty(cbModel.getPowerName())){
        		hql.append(" and exists (select lis.id from Power lis where lis.name like ? and lis.isDelete = 0 and dept.id = lis.departmentId) ");
                params.add("%" + cbModel.getPowerName().trim() + "%");
        	}
        	hql.append(" ) ");
        }
        //所属领域
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgSys())){
        	hql.append(" and exists (select sys.deptId from OrganizationOrgsys sys where sys.fieldId in ('"+cbModel.getOrgSys().trim().replace(",", "','")+"') and sys.deptId=dept.id ) ");
        }
        //部门名称
        if(!TeeUtility.isNullorEmpty(cbModel.getName())){
        	hql.append(" and dept.name like ? ");
            params.add("%" + cbModel.getName().trim() + "%");
        }
        //是否垂管
        if(!TeeUtility.isNullorEmpty(cbModel.getIsManubrium())){
        	hql.append(" and dept.isManubrium = ? ");
            params.add(cbModel.getIsManubrium());
        }
        //部门性质
        if(!TeeUtility.isNullorEmpty(cbModel.getNature())){
        	hql.append(" and dept.nature in ('"+cbModel.getNature().trim().replace(",", "','")+"') ");
        }
        //部门地区
        if(!TeeUtility.isNullorEmpty(cbModel.getAdministrativeDivision())){
        	hql.append(" and dept.administrativeDivision in ? ");
            params.add(cbModel.getAdministrativeDivision());
        }
        //部门层级
        if(!TeeUtility.isNullorEmpty(cbModel.getDeptLevel())){
        	hql.append(" and dept.deptLevel in ('"+cbModel.getDeptLevel().trim().replace(",", "','")+"') ");
        }
        //执法主体个数 0 无 1单 2多
        if(!TeeUtility.isNullorEmpty(cbModel.getSubNo())){
        	if("0".equals(cbModel.getSubNo())){
        		hql.append(" and exists ( select os.organizationId ,count(os.organizationId)  from OrganizationSubject os where dept.id = os.organizationId group by organizationId having count(os.organizationId) = 0 ) ");
        	}
        	if("1".equals(cbModel.getSubNo())){
        		hql.append(" and exists ( select os.organizationId ,count(os.organizationId)  from OrganizationSubject os where dept.id = os.organizationId group by organizationId having count(os.organizationId) = 1 ) ");
        	}
        	if("2".equals(cbModel.getSubNo())){
        		hql.append(" and exists ( select os.organizationId ,count(os.organizationId)  from OrganizationSubject os where dept.id = os.organizationId group by organizationId having count(os.organizationId) > 1 ) ");
        	}
        }
        //执法人员个数 1 小于10  2 10-50  3 51-100  4 101-200  5 大于200
        if(!TeeUtility.isNullorEmpty(cbModel.getPersonNo())){
        	if(cbModel.getPersonNo() == 1){
        		hql.append(" and not exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) >= 10 ) ");
        	}
        	if(cbModel.getPersonNo() == 2){
        		hql.append(" and exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) >= 10 and count(son.deptId)<=50 ) ");
        	}
        	if(cbModel.getPersonNo() == 3){
        		hql.append(" and exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) > 50 and count(son.deptId)<=100 ) ");
        	}
        	if(cbModel.getPersonNo() == 4){
        		hql.append(" and exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) > 100 and count(son.deptId)<=200 ) ");
        	}
        	if(cbModel.getPersonNo() == 5){
        		hql.append(" and exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) > 200 ) ");
        	}
        }
        
        //职权个数  1 小于100  2 100-300  3 301-500  4 501-1000  5 大于1000
        if(!TeeUtility.isNullorEmpty(cbModel.getPowerNo())){
        	if(cbModel.getPowerNo() == 1){
        		hql.append(" and not exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) >= 100 ) ");
        	}
        	if(cbModel.getPowerNo() == 2){
        		hql.append(" and exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) >= 100 and count(departmentId) <= 300 ) ");
        	}
        	if(cbModel.getPowerNo() == 3){
        		hql.append(" and exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) > 300 and count(departmentId) <= 500 ) ");
        	}
        	if(cbModel.getPowerNo() == 4){
        		hql.append(" and exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) > 500 and count(departmentId) <= 1000 ) ");
        	}
        	if(cbModel.getPowerNo() == 5){
        		hql.append(" and exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) > 1000 ) ");
        	}
        }
        //委托组织个数  1 无  2 1-3  3 4-7  4 大于7
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgNo())){
        	if(cbModel.getOrgNo() == 1){
        		hql.append(" and not exists (select os.id from OrganizationSubject os where exists (select org.parentId,count(org.parentId) from Subject org where os.subjectId = org.parentId group by org.parentId having count(org.parentId) > 0)and dept.id = organizationId) ");
        	}
        	if(cbModel.getOrgNo() == 2){
        		hql.append(" and exists (select os.id from OrganizationSubject os where exists (select org.parentId,count(org.parentId) from Subject org where os.subjectId = org.parentId group by org.parentId having count(org.parentId) >= 1 and count(org.parentId) <= 3)and dept.id = organizationId) ");
        	}
        	if(cbModel.getOrgNo() == 3){
        		hql.append(" and exists (select os.id from OrganizationSubject os where exists (select org.parentId,count(org.parentId) from Subject org where os.subjectId = org.parentId group by org.parentId having count(org.parentId) >= 4 and count(org.parentId) <= 7)and dept.id = organizationId) ");
        	}
        	if(cbModel.getOrgNo() == 4){
        		hql.append(" and exists (select os.id from OrganizationSubject os where exists (select org.parentId,count(org.parentId) from Subject org where os.subjectId = org.parentId group by org.parentId having count(org.parentId) > 7 )and dept.id = organizationId) ");
        	}
        }
        object[0] = hql;
        object[1] = params;
        List<TblDepartmentInfo> departments = pageFind(object[0].toString(), start, length,((List<Object>) object[1]).toArray());
        return departments;
    }
	
	/**
	 * sql
	 * @param cbModel
	 * @param orgCtrl
	 * @return
	 * @date:2019年3月26日下午8:25:04
	 * @author:yxy
	 */
	public List<TblDepartmentInfo> generalListByPage(TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl){
		Object[] object = new Object[2];
        // 创建HQL查询语句
        StringBuffer hql = new StringBuffer(" select * from tbl_base_organization dept where exists (select dd.admin_division_code from tbl_admin_division_divided dd where dd.admin_division_code = dept.administrative_division ");
        // 创建参数集合
        List<Object> params = new ArrayList<Object>();
        //地区  
	    if(!TeeUtility.isNullorEmpty(orgCtrl.getLevelCode())){
		    if("0200".equals(orgCtrl.getLevelCode())){
		        // 控制省級层级，及省级以下层级
		    	hql.append(" and dd.provincial_code  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
		    if("0300".equals(orgCtrl.getLevelCode())){
		        // 控制市州級层级，及市州以下层级
		    	hql.append(" and dd.city_code  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
		    if("0400".equals(orgCtrl.getLevelCode())){
		        // 控制区县层级
		    	hql.append(" and dd.district_code  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
		    if("0500".equals(orgCtrl.getLevelCode())){
		        // 控制乡镇街道层级
		    	hql.append(" and dd.street_code  = ?) ");
	            params.add(orgCtrl.getAdminDivisionCode());
		    }
	    }
        // 判断主体名称组织名称职权名称  
        if ((!TeeUtility.isNullorEmpty(cbModel.getSubName()))&&(!TeeUtility.isNullorEmpty(cbModel.getOrgName()))&&(!TeeUtility.isNullorEmpty(cbModel.getPowerName()))) {
        	hql.append(" and exists (select sub.id from tbl_base_subject sub where sub.is_depute = 0 and dept.id = sub.department_code ");
        	if(!TeeUtility.isNullorEmpty(cbModel.getSubName())){
        		hql.append(" and sub.sub_name like ? ");
                params.add("%" + cbModel.getSubName().trim() + "%");
            }
        	if(!TeeUtility.isNullorEmpty(cbModel.getOrgName())){
        		hql.append("  and exists (select org.id from tbl_base_subject org where org.sub_name like ? and org.is_depute = 1 and org.is_delete = 0 and org.parent_id = sub.id) ");
                params.add("%" + cbModel.getOrgName().trim() + "%");
        	}
        	if(!TeeUtility.isNullorEmpty(cbModel.getPowerName())){
        		hql.append(" and exists (select lis.id from tbl_power_list lis where lis.name like ? and lis.is_delete = 0 and dept.id = lis.department_id) ");
                params.add("%" + cbModel.getPowerName().trim() + "%");
        	}
        	hql.append(" ) ");
        }
        //所属领域
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgSys())){
        	hql.append(" and exists (select sys.dept_id from tbl_base_organization_orgsys sys where sys.field_id in (?) and sys.dept_id=dept.id ) ");
            params.add("%" + cbModel.getOrgSys().trim() + "%");
        }
        //部门名称
        if(!TeeUtility.isNullorEmpty(cbModel.getName())){
        	hql.append(" and dept.name like ? ");
            params.add("%" + cbModel.getName().trim() + "%");
        }
        //是否垂管
        if(!TeeUtility.isNullorEmpty(cbModel.getIsManubrium())){
        	hql.append(" and dept.is_manubrium = ? ");
            params.add(cbModel.getIsManubrium());
        }
        //部门性质
        if(!TeeUtility.isNullorEmpty(cbModel.getNature())){
        	hql.append(" and dept.nature in (?) ");
        	String naString = cbModel.getNature().replaceAll("\\,","\\'\\,\\'");
            params.add(naString);
        }
        //部门地区
        if(!TeeUtility.isNullorEmpty(cbModel.getAdministrativeDivision())){
        	hql.append(" and dept.administrative_division in ? ");
            params.add(cbModel.getAdministrativeDivision());
        }
        //部门层级
        if(!TeeUtility.isNullorEmpty(cbModel.getDeptLevel())){
        	hql.append(" and dept.dept_level in ? ");
            params.add(cbModel.getDeptLevel());
        }
        //执法主体个数 0 无 1单 2多
        if(!TeeUtility.isNullorEmpty(cbModel.getSubNo())){
        	if("0".equals(cbModel.getSubNo())){
        		hql.append(" and exists (select organization_id from ( select os.organization_id,count(os.organization_id) subno from tbl_base_organization_subject os group by organization_id order by subno)where subno = 0 and organization_id=dept.id ) ");
        	}
        	if("1".equals(cbModel.getSubNo())){
        		hql.append(" and exists (select organization_id from ( select os.organization_id,count(os.organization_id) subno from tbl_base_organization_subject os group by organization_id order by subno)where subno = 1 and organization_id=dept.id ) ");
        	}
        	if("2".equals(cbModel.getSubNo())){
        		hql.append(" and exists (select organization_id from ( select os.organization_id,count(os.organization_id) subno from tbl_base_organization_subject os group by organization_id order by subno)where subno > 1 and organization_id=dept.id ) ");
        	}
        }
        //执法人员个数 1 小于10  2 10-50  3 51-100  4 101-200  5 大于200
        if(!TeeUtility.isNullorEmpty(cbModel.getPersonNo())){
        	if("1".equals(cbModel.getPersonNo())){
        		hql.append(" and exists (select dept_id  from (select son.dept_id,count(son.deptId) sonno from tbl_base_organization_person son group by  son.dept_id order by sonno)where sonno < 10 and dept_id = dept.id) ");
        	}
        	if("2".equals(cbModel.getPersonNo())){
        		hql.append(" and exists (select dept_id  from (select son.dept_id,count(son.dept_id) sonno from tbl_base_organization_person son group by  son.dept_id order by sonno)where sonno >= 10 and sonno <=50 and dept_id = dept.id) ");
        	}
        	if("3".equals(cbModel.getPersonNo())){
        		hql.append(" and exists (select dept_id  from (select son.dept_id,count(son.dept_id) sonno from tbl_base_organization_person son group by  son.dept_id order by sonno)where sonno > 50 and sonno <=100 and dept_id = dept.id) ");
        	}
        	if("4".equals(cbModel.getPersonNo())){
        		hql.append(" and exists (select dept_id  from (select son.dept_id,count(son.dept_id) sonno from tbl_base_organization_person son group by  son.dept_id order by sonno)where sonno > 100 and sonno <=200 and dept_id = dept.id) ");
        	}
        	if("5".equals(cbModel.getPersonNo())){
        		hql.append(" and exists (select dept_id  from (select son.dept_id,count(son.dept_id) sonno from tbl_base_organization_person son group by  son.dept_id order by sonno)where sonno > 200 and dept_id = dept.id) ");
        	}
        }
        //职权个数  1 小于100  2 100-300  3 301-500  4 501-1000  5 大于1000
        if(!TeeUtility.isNullorEmpty(cbModel.getPowerNo())){
        	if("1".equals(cbModel.getPowerNo())){
        		hql.append(" and exists (select department_id from (select department_id,count(department_id) powno from tbl_power_list group by department_id order by powno) where  powno <100 and dept.id = department_id) ");
        	}
        	if("2".equals(cbModel.getPowerNo())){
        		hql.append(" and exists (select department_id from (select department_id,count(department_id) powno from tbl_power_list group by department_id order by powno) where  powno >=100 and powno <=300 and dept.id = department_id) ");
        	}
        	if("3".equals(cbModel.getPowerNo())){
        		hql.append(" and exists (select department_id from (select department_id,count(department_id) powno from tbl_power_list group by department_id order by powno) where  powno >300 and powno <=500 and dept.id = department_id) ");
        	}
        	if("4".equals(cbModel.getPowerNo())){
        		hql.append(" and exists (select department_id from (select department_id,count(department_id) powno from tbl_power_list group by department_id order by powno) where  powno >500 and powno <=1000 and dept.id = department_id) ");
        	}
        	if("5".equals(cbModel.getPowerNo())){
        		hql.append(" and exists (select department_id from (select department_id,count(department_id) powno from tbl_power_list group by department_id order by powno) where  powno >1000 and dept.id = department_id) ");
        	}
        }
        //委托组织个数  1 无  2 1-3  3 4-7  4 大于7
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgNo())){
        	if("1".equals(cbModel.getOrgNo())){
        		hql.append(" and exists (select os.id from tbl_base_organization_subject os where exists(select parent_id from (select org.parent_id,count(org.parent_id) orgNo from tbl_base_subject org group by org.parent_id )where orgNo = 0 and os.id = parent_id)and dept.id = organization_id) ");
        	}
        	if("2".equals(cbModel.getOrgNo())){
        		hql.append(" and exists (select os.id from tbl_base_organization_subject os where exists(select parent_id from (select org.parent_id,count(org.parent_id) orgNo from tbl_base_subject org group by org.parent_id )where orgNo >=1 and orgNo <=3 and os.id = parent_id)and dept.id = organization_id) ");
        	}
        	if("3".equals(cbModel.getOrgNo())){
        		hql.append(" and exists (select os.id from tbl_base_organization_subject os where exists(select parent_id from (select org.parent_id,count(org.parent_id) orgNo from tbl_base_subject org group by org.parent_id )where orgNo >3 and orgNo <=7 and os.id = parent_id)and dept.id = organization_id) ");
        	}
        	if("4".equals(cbModel.getOrgNo())){
        		hql.append(" and exists (select os.id from tbl_base_organization_subject os where exists(select parent_id from (select org.parent_id,count(org.parent_id) orgNo from tbl_base_subject org group by org.parent_id )where orgNo >7 and os.id = parent_id)and dept.id = organization_id) ");
        	}
        }
        object[0] = hql;
        object[1] = params;
        return super.getBySql(object[0].toString(),((List<Object>) object[1]).toArray());
    }
	
    /**
     * 执法机关综合查询总数 权限控制
     * @param cbModel
     * @param orgCtrl
     * @return
     * @date:2019年3月22日下午4:14:48
     * @author:yxy
     */
    @SuppressWarnings("unchecked")
	public long generallistSearchCount(TblDepartmentModel cbModel, OrgCtrlInfoModel orgCtrl) {
    	Object[] object = new Object[2];
        // 创建HQL查询语句
        StringBuffer hql = new StringBuffer("from TblDepartmentInfo dept where exists (select dd.adminDivisionCode from AdminDivisionDivided dd where dd.adminDivisionCode = dept.administrativeDivision ");
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
        // 判断主体名称组织名称职权名称  
        if ((TeeUtility.isNullorEmpty(cbModel.getSubName()))&&(TeeUtility.isNullorEmpty(cbModel.getOrgName()))&&(TeeUtility.isNullorEmpty(cbModel.getPowerName()))) {
        	
        }else{
        	hql.append(" and exists (select sub.id from Subject sub where sub.isDepute = 0 and dept.id = sub.departmentCode ");
        	if(!TeeUtility.isNullorEmpty(cbModel.getSubName())){
        		hql.append(" and sub.subName like ? ");
                params.add("%" + cbModel.getSubName().trim() + "%");
            }
        	if(!TeeUtility.isNullorEmpty(cbModel.getOrgName())){
        		hql.append("  and exists (select org.id from Subject org where org.subName like ? and org.isDepute = 1 and org.isDelete = 0 and org.parentId = sub.id) ");
                params.add("%" + cbModel.getOrgName().trim() + "%");
        	}
        	if(!TeeUtility.isNullorEmpty(cbModel.getPowerName())){
        		hql.append(" and exists (select lis.id from Power lis where lis.name like ? and lis.isDelete = 0 and dept.id = lis.departmentId) ");
                params.add("%" + cbModel.getPowerName().trim() + "%");
        	}
        	hql.append(" ) ");
        }
        //所属领域
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgSys())){
        	hql.append(" and exists (select sys.deptId from OrganizationOrgsys sys where sys.fieldId in ('"+cbModel.getOrgSys().trim().replace(",", "','")+"') and sys.deptId=dept.id ) ");
        }
        //部门名称
        if(!TeeUtility.isNullorEmpty(cbModel.getName())){
        	hql.append(" and dept.name like ? ");
            params.add("%" + cbModel.getName().trim() + "%");
        }
        //是否垂管
        if(!TeeUtility.isNullorEmpty(cbModel.getIsManubrium())){
        	hql.append(" and dept.isManubrium = ? ");
            params.add(cbModel.getIsManubrium());
        }
        //部门性质
        if(!TeeUtility.isNullorEmpty(cbModel.getNature())){
        	hql.append(" and dept.nature in ('"+cbModel.getNature().trim().replace(",", "','")+"') ");
        }
        //部门地区
        if(!TeeUtility.isNullorEmpty(cbModel.getAdministrativeDivision())){
        	hql.append(" and dept.administrativeDivision in ? ");
            params.add(cbModel.getAdministrativeDivision());
        }
        //部门层级
        if(!TeeUtility.isNullorEmpty(cbModel.getDeptLevel())){
        	hql.append(" and dept.deptLevel in ? ");
            params.add(cbModel.getDeptLevel());
        }
        //执法主体个数 0 无 1单 2多
        if(!TeeUtility.isNullorEmpty(cbModel.getSubNo())){
        	if("0".equals(cbModel.getSubNo())){
        		hql.append(" and exists ( select os.organizationId ,count(os.organizationId)  from OrganizationSubject os where dept.id = os.organizationId group by organizationId having count(os.organizationId) = 0 ) ");
        	}
        	if("1".equals(cbModel.getSubNo())){
        		hql.append(" and exists ( select os.organizationId ,count(os.organizationId)  from OrganizationSubject os where dept.id = os.organizationId group by organizationId having count(os.organizationId) = 1 ) ");
        	}
        	if("2".equals(cbModel.getSubNo())){
        		hql.append(" and exists ( select os.organizationId ,count(os.organizationId)  from OrganizationSubject os where dept.id = os.organizationId group by organizationId having count(os.organizationId) > 1 ) ");
        	}
        }
        //执法人员个数 1 小于10  2 10-50  3 51-100  4 101-200  5 大于200
        if(!TeeUtility.isNullorEmpty(cbModel.getPersonNo())){
        	if("1".equals(cbModel.getPersonNo())){
        		hql.append(" and not exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) >= 10 ) ");
        	}
        	if("2".equals(cbModel.getPersonNo())){
        		hql.append(" and exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) >= 10 and count(son.deptId)<=50 ) ");
        	}
        	if("3".equals(cbModel.getPersonNo())){
        		hql.append(" and exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) > 50 and count(son.deptId)<=100 ) ");
        	}
        	if("4".equals(cbModel.getPersonNo())){
        		hql.append(" and exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) > 100 and count(son.deptId)<=200 ) ");
        	}
        	if("5".equals(cbModel.getPersonNo())){
        		hql.append(" and exists (select son.deptId,count(son.deptId) from OrganizationPerson son where dept.id = son.deptId group by  son.deptId having count(son.deptId) > 200 ) ");
        	}
        }
        
        //职权个数  1 小于100  2 100-300  3 301-500  4 501-1000  5 大于1000
        if(!TeeUtility.isNullorEmpty(cbModel.getPowerNo())){
        	if("1".equals(cbModel.getPowerNo())){
        		hql.append(" and not exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) >= 100 ) ");
        	}
        	if("2".equals(cbModel.getPowerNo())){
        		hql.append(" and exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) >= 100 and count(departmentId) <= 300 ) ");
        	}
        	if("3".equals(cbModel.getPowerNo())){
        		hql.append(" and exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) > 300 and count(departmentId) <= 500 ) ");
        	}
        	if("4".equals(cbModel.getPowerNo())){
        		hql.append(" and exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) > 500 and count(departmentId) <= 1000 ) ");
        	}
        	if("5".equals(cbModel.getPowerNo())){
        		hql.append(" and exists (select departmentId from Power where dept.id = departmentId group by departmentId having count(departmentId) > 1000 ) ");
        	}
        }
        //委托组织个数  1 无  2 1-3  3 4-7  4 大于7
        if(!TeeUtility.isNullorEmpty(cbModel.getOrgNo())){
        	if("1".equals(cbModel.getOrgNo())){
        		hql.append(" and not exists (select os.id from OrganizationSubject os where exists (select org.parentId,count(org.parentId) from Subject org where os.subjectId = org.parentId group by org.parentId having count(org.parentId) > 0)and dept.id = organizationId) ");
        	}
        	if("2".equals(cbModel.getOrgNo())){
        		hql.append(" and exists (select os.id from OrganizationSubject os where exists (select org.parentId,count(org.parentId) from Subject org where os.subjectId = org.parentId group by org.parentId having count(org.parentId) >= 1 and count(org.parentId) <= 3)and dept.id = organizationId) ");
        	}
        	if("3".equals(cbModel.getOrgNo())){
        		hql.append(" and exists (select os.id from OrganizationSubject os where exists (select org.parentId,count(org.parentId) from Subject org where os.subjectId = org.parentId group by org.parentId having count(org.parentId) >= 4 and count(org.parentId) <= 7)and dept.id = organizationId) ");
        	}
        	if("4".equals(cbModel.getOrgNo())){
        		hql.append(" and exists (select os.id from OrganizationSubject os where exists (select org.parentId,count(org.parentId) from Subject org where os.subjectId = org.parentId group by org.parentId having count(org.parentId) > 7 )and dept.id = organizationId) ");
        	}
        }
        object[0] = hql;
        object[1] = params;
        return super.count("select count(*) " +object[0].toString(),( (List<Object>) object[1]).toArray());
    }
}
