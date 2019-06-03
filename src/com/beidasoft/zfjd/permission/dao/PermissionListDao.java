package com.beidasoft.zfjd.permission.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.permission.bean.PermissionList;
import com.beidasoft.zfjd.permission.model.PermissionListModel;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * @ClassName: PermissionListDao.java
 * @Description: 许可清单DAO层
 *
 * @author: mixue
 * @date: 2019年2月21日 下午3:47:44
 */
@Repository("permissionListDao")
public class PermissionListDao extends TeeBaseDao<PermissionList> {

    /**
     * 
     * @Function: findListByPage
     * @Description: 许可清单分页查询
     *
     * @param firstResult
     * @param rows
     * @param permissionListModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月21日 下午6:44:57
     */
    public List<PermissionList> findListByPage(int firstResult, int rows, PermissionListModel permissionListModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(permissionListModel);
        @SuppressWarnings("unchecked")
        List<PermissionList> permissionLists = pageFind(object[0].toString(), firstResult, rows, ((List<Object>)object[1]).toArray());
        return permissionLists;
    }

    /**
     * 
     * @Function: findListCountByPage
     * @Description: 许可清单查询总条数
     *
     * @param permissionListModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月21日 下午6:46:22
     */
    public Long findListCountByPage(PermissionListModel permissionListModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(permissionListModel);
        @SuppressWarnings("unchecked")
        long count = count("select count(*) " + object[0].toString(), ((List<Object>)object[1]).toArray());
        return count;
    }

    /**
     * 
     * @Function: getHql
     * @Description: 获取HQL及参数集合
     *
     * @param permissionListModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月21日 下午6:46:38
     */
    private Object[] getHql(PermissionListModel permissionListModel) {
        // TODO Auto-generated method stub
        Object[] object = new Object[2];
        // 创建HQL查询语句
        StringBuffer hql = new StringBuffer("from PermissionList where isDelete = 0 ");
        // 创建参数集合
        List<Object> params = new ArrayList<Object>();
        // 判断是否添加查询条件：当前状态
        if(!TeeUtility.isNullorEmpty(permissionListModel.getXkZt())){
            hql.append(" and xkZt = ? ");
            params.add(permissionListModel.getXkZt().trim());
        }
        // 判断是否添加查询条件：许可类别
        if(!TeeUtility.isNullorEmpty(permissionListModel.getXkXklb())){
            hql.append(" and xkXklb = ? ");
            params.add(permissionListModel.getXkXklb().trim());
        }
        // 判断是否添加查询条件：许可决定文书号
        if(!TeeUtility.isNullorEmpty(permissionListModel.getXkWsh())){
            hql.append(" and xkWsh like ? ");
            params.add("%" + permissionListModel.getXkWsh().trim() + "%");
        }
        // 判断是否添加查询条件：许可部门
        if(!TeeUtility.isNullorEmpty(permissionListModel.getXkXkjg())){
            hql.append(" and xkXkjg = ? ");
            params.add(permissionListModel.getXkXkjg().trim());
        }
        object[0] = hql;
        object[1] = params;
        return object;
    }

    /**
     * 
     * @Function: updateOrDeleteById
     * @Description: 删除
     *
     * @param columns
     * @param idStr
     *
     * @author: mixue
     * @date: 2019年2月25日 上午9:19:30
     */
    public void updateOrDeleteById(Map<String, Object> columns, String idStr) {
        // TODO Auto-generated method stub
        try {
            if (idStr != null && idStr.length() > 0) {
                String[] ids = idStr.split(",");
                for (String id: ids) {
                    super.update(columns, id);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @Function: findListByPageRoles
     * @Description: 许可清单分页查询(√)
     *
     * @param firstResult
     * @param rows
     * @param permissionListModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 下午1:48:27
     */
    @SuppressWarnings("unchecked")
    public List<PermissionList> findListByPageRoles(int firstResult, int rows, PermissionListModel permissionListModel,
            OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Integer orgType = 0;
        if(orgCtrlInfoModel.getOrgType() != null)
            orgType = orgCtrlInfoModel.getOrgType();
        if(orgType == 0)
            return new ArrayList<>();
        Criteria criteria = getCriteria(permissionListModel, orgCtrlInfoModel);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(rows);
        //criteria.addOrder(Order.asc("department.departmentCode"));
        return criteria.list();
    }

    /**
     * 
     * @Function: findListCountByPageRoles
     * @Description: 许可清单查询总数量(√)
     *
     * @param permissionListModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 下午1:47:57
     */
    public Long findListCountByPageRoles(PermissionListModel permissionListModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Integer orgType = 0;
        if(orgCtrlInfoModel.getOrgType() != null)
            orgType = orgCtrlInfoModel.getOrgType();
        if(orgType == 0)
            return 0L;
        Criteria criteria = getCriteria(permissionListModel, orgCtrlInfoModel);
        criteria.setProjection(Projections.rowCount());
        Integer total = Integer.parseInt(criteria.uniqueResult().toString());
        return total.longValue();
    }
    
    /**
     * 
     * @Function: getCriteria
     * @Description: 查询条件处理
     *
     * @param permissionListModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 下午1:47:48
     */
    private Criteria getCriteria(PermissionListModel permissionListModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Session session = super.getSession();
        // 主查询对象：许可清单
        Criteria criteria = session.createCriteria(PermissionList.class, "permissionList");
        // 将SQL查询结果放入集合
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 第一层，关联部门
        DetachedCriteria departmentCriteria = DetachedCriteria.forClass(TblDepartmentInfo.class, "department");
        // 关联关系
        departmentCriteria.add(Property.forName("department.id").eqProperty("permissionList.xkXkjg"));
        // exists条件：TBL_ADMIN_DIVISION_DIVIDED --关联行政区划表
        DetachedCriteria divisionCriteria = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        // 查询条件，关联关系
        divisionCriteria.add(Property.forName("division.adminDivisionCode").eqProperty("department.administrativeDivision"));
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
        departmentCriteria.add(Subqueries.exists(divisionCriteria.setProjection(Projections.property("division.adminDivisionCode"))));
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
                            departmentCriteria.add(logicalExpression);
                        }else{
                            departmentCriteria.add(Restrictions.like("orgSys", orgSysArray[0], MatchMode.ANYWHERE));
                        }
                    }
                }
            }
        }
        // 内层主体 删除标志
        departmentCriteria.add(Restrictions.eq("department.isDelete", 0));
        // exists(select ...)
        criteria.add(Subqueries.exists(departmentCriteria.setProjection(Projections.property("department.id"))));
        // 删除标志
        criteria.add(Restrictions.eq("permissionList.isDelete", 0));
        // 判断是否添加查询条件：当前状态
        if(!TeeUtility.isNullorEmpty(permissionListModel.getXkZt())){
            criteria.add(Restrictions.eq("permissionList.xkZt", permissionListModel.getXkZt().trim()));
        }
        // 判断是否添加查询条件：许可类别
        if(!TeeUtility.isNullorEmpty(permissionListModel.getXkXklb())){
            criteria.add(Restrictions.eq("permissionList.xkXklb", permissionListModel.getXkXklb().trim()));
        }
        // 判断是否添加查询条件：许可决定文书号
        if(!TeeUtility.isNullorEmpty(permissionListModel.getXkWsh())){
            criteria.add(Restrictions.like("permissionList.xkWsh", permissionListModel.getXkWsh().trim(), MatchMode.ANYWHERE));
        }
        // 判断是否添加查询条件：许可部门
        if(!TeeUtility.isNullorEmpty(permissionListModel.getXkXkjg())){
            criteria.add(Restrictions.eq("permissionList.xkXkjg", permissionListModel.getXkXkjg().trim()));
        }
        return criteria;
    }

}
