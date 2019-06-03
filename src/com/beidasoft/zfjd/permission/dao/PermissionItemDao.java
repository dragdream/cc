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
import com.beidasoft.zfjd.permission.bean.PermissionItem;
import com.beidasoft.zfjd.permission.model.PermissionItemModel;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * @ClassName: PermissionItemDao.java
 * @Description: 
 *
 * @author: mixue
 * @date: 2019年2月26日 下午8:25:06
 */
@Repository("permissionItemDao")
public class PermissionItemDao extends TeeBaseDao<PermissionItem> {

    /**
     * 
     * @Function: findListByPage
     * @Description: 许可事项分页查询
     *
     * @param firstResult
     * @param rows
     * @param permissionItemModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午9:08:07
     */
    public List<PermissionItem> findListByPage(int firstResult, int rows, PermissionItemModel permissionItemModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(permissionItemModel);
        @SuppressWarnings("unchecked")
        List<PermissionItem> permissionItems = pageFind(object[0].toString(), firstResult, rows, ((List<Object>)object[1]).toArray());
        return permissionItems;
    }

    /**
     * 
     * @Function: findListCountByPage
     * @Description: 许可事项查询总条数
     *
     * @param permissionItemModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午9:08:17
     */
    public Long findListCountByPage(PermissionItemModel permissionItemModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(permissionItemModel);
        @SuppressWarnings("unchecked")
        long count = count("select count(*) " + object[0].toString(), ((List<Object>)object[1]).toArray());
        return count;
    }
    
    /**
     * 
     * @Function: getHql
     * @Description: 获取HQL及参数集合
     *
     * @param permissionItemModel
     * @return
     *
     * @author: mixue
     * @date: 2019年2月26日 下午9:08:39
     */
    private Object[] getHql(PermissionItemModel permissionItemModel) {
        // TODO Auto-generated method stub
        Object[] object = new Object[2];
        // 创建HQL查询语句
        StringBuffer hql = new StringBuffer("from PermissionItem where isDelete = 0 ");
        // 创建参数集合
        List<Object> params = new ArrayList<Object>();
        // 判断是否添加查询条件：事项编号
        if(!TeeUtility.isNullorEmpty(permissionItemModel.getXkSxbm())){
            hql.append(" and xkSxbm like ? ");
            params.add("%" + permissionItemModel.getXkSxbm().trim() + "%");
        }
        // 判断是否添加查询条件：事项名称
        if(!TeeUtility.isNullorEmpty(permissionItemModel.getXkSxmc())){
            hql.append(" and xkSxmc like ? ");
            params.add("%" + permissionItemModel.getXkSxmc().trim() + "%");
        }
        // 判断是否添加查询条件：办件类型
        if(!TeeUtility.isNullorEmpty(permissionItemModel.getXkBjlx())){
            hql.append(" and xkBjlx = ? ");
            params.add(permissionItemModel.getXkBjlx().trim());
        }
        // 判断是否添加查询条件：办理部门
        if(!TeeUtility.isNullorEmpty(permissionItemModel.getXkGsdw())){
            hql.append(" and xkGsdw = ? ");
            params.add(permissionItemModel.getXkGsdw().trim());
        }
        object[0] = hql;
        object[1] = params;
        return object;
    }

    /**
     * 
     * @Function: updateOrDeleteById
     * @Description: 修改、状态删除
     *
     * @param columns
     * @param idStr
     *
     * @author: mixue
     * @date: 2019年2月27日 上午10:33:31
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
     * @Function: findPermissionItem
     * @Description: 许可事项模糊查询
     *
     * @param q
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 上午11:47:20
     */
    public List<PermissionItem> findPermissionItem(String q) {
        // TODO Auto-generated method stub
        //定义hql
        StringBuffer hql = new StringBuffer();
        //参数集合
        List<Object> list = new ArrayList<>();
        hql.append(" from PermissionItem where isDelete = 0 ");
        if (q != null && !"".equals(q)) {
            hql.append(" and xkSxmc like ? ");
            list.add("%" + q + "%");
        }
        hql.append(" order by xkSxmc desc");
        Object[] params = list.toArray();
        return super.find(hql.toString(), params);
    }

    /**
     * 
     * @Function: findListByPageRoles
     * @Description: 许可事项分页查询(√)
     *
     * @param firstResult
     * @param rows
     * @param permissionItemModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 上午11:47:42
     */
    @SuppressWarnings("unchecked")
    public List<PermissionItem> findListByPageRoles(int firstResult, int rows, PermissionItemModel permissionItemModel,
            OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Integer orgType = 0;
        if(orgCtrlInfoModel.getOrgType() != null)
            orgType = orgCtrlInfoModel.getOrgType();
        if(orgType == 0)
            return new ArrayList<>();
        Criteria criteria = getCriteria(permissionItemModel, orgCtrlInfoModel);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(rows);
        //criteria.addOrder(Order.asc("department.departmentCode"));
        return criteria.list();
    }

    /**
     * 
     * @Function: findListCountByPageRoles
     * @Description: 许可事项查询总条数(√)
     *
     * @param permissionItemModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 上午11:48:00
     */
    public Long findListCountByPageRoles(PermissionItemModel permissionItemModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Integer orgType = 0;
        if(orgCtrlInfoModel.getOrgType() != null)
            orgType = orgCtrlInfoModel.getOrgType();
        if(orgType == 0)
            return 0L;
        Criteria criteria = getCriteria(permissionItemModel, orgCtrlInfoModel);
        criteria.setProjection(Projections.rowCount());
        Integer total = Integer.parseInt(criteria.uniqueResult().toString());
        return total.longValue();
    }
    
    /**
     * 
     * @Function: getCriteria
     * @Description: 查询条件处理
     *
     * @param permissionItemModel
     * @param orgCtrlInfoModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 下午1:36:45
     */
    private Criteria getCriteria(PermissionItemModel permissionItemModel, OrgCtrlInfoModel orgCtrlInfoModel) {
        // TODO Auto-generated method stub
        Session session = super.getSession();
        // 主查询对象：许可事项
        Criteria criteria = session.createCriteria(PermissionItem.class, "permissionItem");
        // 将SQL查询结果放入集合
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 第一层，关联部门
        DetachedCriteria departmentCriteria = DetachedCriteria.forClass(TblDepartmentInfo.class, "department");
        // 关联关系
        departmentCriteria.add(Property.forName("department.id").eqProperty("permissionItem.xkGsdw"));
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
        criteria.add(Restrictions.eq("permissionItem.isDelete", 0));
        // 判断是否添加查询条件：事项编号
        if(!TeeUtility.isNullorEmpty(permissionItemModel.getXkSxbm())){
            criteria.add(Restrictions.like("permissionItem.xkSxbm", permissionItemModel.getXkSxbm().trim(), MatchMode.ANYWHERE));
        }
        // 判断是否添加查询条件：事项名称
        if(!TeeUtility.isNullorEmpty(permissionItemModel.getXkSxmc())){
            criteria.add(Restrictions.like("permissionItem.xkSxmc", permissionItemModel.getXkSxmc().trim(), MatchMode.ANYWHERE));
        }
        // 判断是否添加查询条件：办件类型
        if(!TeeUtility.isNullorEmpty(permissionItemModel.getXkBjlx())){
            criteria.add(Restrictions.eq("permissionItem.xkBjlx", permissionItemModel.getXkBjlx().trim()));
        }
        // 判断是否添加查询条件：办理部门
        if(!TeeUtility.isNullorEmpty(permissionItemModel.getXkGsdw())){
            criteria.add(Restrictions.eq("permissionItem.xkGsdw", permissionItemModel.getXkGsdw().trim()));
        }
        return criteria;
    }

    /**
     * 
     * @Function: getPermissionItemByOneself
     * @Description: 本部门许可事项查询
     *
     * @param permissionItemModel
     * @return
     *
     * @author: mixue
     * @date: 2019年3月4日 下午1:49:15
     */
    public List<PermissionItem> getPermissionItemByOneself(PermissionItemModel permissionItemModel) {
        // TODO Auto-generated method stub
        Object[] object = getHql(permissionItemModel);
        @SuppressWarnings("unchecked")
        List<PermissionItem> permissionItems = pageFind(object[0].toString(), 0, 99999999, ((List<Object>)object[1]).toArray());
        return permissionItems;
    }

}
