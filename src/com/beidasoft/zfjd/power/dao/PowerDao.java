package com.beidasoft.zfjd.power.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.model.PowerModel;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;



/**
 * 
* 2018 
* @ClassName: PowerDao.java
* @Description: 该类的功能描述
*
* @author: chenqr
* @date: 2018年12月24日 下午3:19:55 
*
 */
@Repository
public class PowerDao extends TeeBaseDao<Power>{

    /**
     * 
    * @see com.tianee.webframe.dao.TeeBaseDao#saveOrUpdate(java.lang.Object)  
    * @Function: PowerDao.java
    * @Description: 保存或更新power信息
    *
    * @param:Power
    * @return：void
    * @throws：
    *
    * @author: chenqr
    * @date: 2018年12月24日 下午2:28:41 
    *
     */
    public void saveOrUpdate(Power power) {
        super.saveOrUpdate(power);
    }
    /**
     * 
    * @Function: PowerDao.java
    * @Description: 分页查询职权列表
    *
    * @param: 职权列表信息
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenqr
    * @date: 2018年12月24日 下午3:57:56 
    *
     */
    @SuppressWarnings("unchecked")
    public List<Power> listByPage(int start, int length, PowerModel powerModel, OrgCtrlInfoModel orgCtrl) {
        //获取session
        Session session = super.getSession();
        
        //主表tbl_power_list 取别名为 power
        Criteria crit = session.createCriteria(Power.class, "power");
        
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        
        //第一层exists 表 tbl_base_subject 取别名为 sub
        DetachedCriteria subCrit = DetachedCriteria.forClass(Subject.class,"sub");
        
        //第二层exists 表 TBL_ADMIN_DIVISION_DIVIDED 取别名为 division 
        DetachedCriteria divisionCrit = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        
        //设置区划权限表 TBL_ADMIN_DIVISION_DIVIDED 和 主体表tbl_base_subject关联的字段
        divisionCrit.add(Property.forName("division.adminDivisionCode").eqProperty("sub.area"));
        
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
        }
        
        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
        subCrit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("division.adminDivisionCode"))));
        
        //关联条件--关联字段 power.SUBJECT_ID = sub.ID
        subCrit.add(Property.forName("power.subjectId").eqProperty(Property.forName("sub.id")));
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        
        //机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        int orgType = orgCtrl.getOrgType();
        
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (2 == orgType || 3 == orgType) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
            }
        }
        //主体删除标志
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        //案件删除标志
        crit.add(Restrictions.eq("power.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        
        //案件名称
        if(powerModel.getCode() != null && !"".equals(powerModel.getCode())) {
            crit.add(Restrictions.like("power.code", powerModel.getCode().trim(),MatchMode.ANYWHERE));
        }
        // 职权状态
        if(powerModel.getCurrentState() != null && !"".equals(powerModel.getCurrentState())) {
            crit.add(Restrictions.eq("power.currentState", powerModel.getCurrentState()));
        }
        if(powerModel.getName() != null && !"".equals(powerModel.getName())) {
            crit.add(Restrictions.like("power.name", powerModel.getName().trim(),MatchMode.ANYWHERE));
        }
        if(powerModel.getPowerType() != null && !"-1".equals(powerModel.getPowerType())) {
            crit.add(Restrictions.eq("power.powerType", powerModel.getPowerType()));
        }
        if(powerModel.getPowerDetail() != null && !"".equals(powerModel.getPowerDetail())) {
            crit.add(Restrictions.sqlRestriction(" exists (select 1 from PowerDetail as pd where pd.powerDetail.id = power.id and code in ('" + powerModel.getPowerDetail().replace(",", "','") + "'))"));
        }
        if(powerModel.getIsDeiscretionary() != null && "01".equals(powerModel.getIsDeiscretionary()) ) {
            crit.add(Restrictions.sqlRestriction(" exists (select 1 from PowerDiscretionary as pdt where pdt.powerDeiscretionary.id = power.id)" ));
        } else if(powerModel.getIsDeiscretionary() != null && "02".equals(powerModel.getIsDeiscretionary()) ) {
            crit.add(Restrictions.sqlRestriction(" not exists (select 1 from PowerDiscretionary as pdt where pdt.powerDeiscretionary.id = power.id)" ));
        }
        if(powerModel.getSubjectId() != null && !"".equals(powerModel.getSubjectId())) {
            crit.add(Restrictions.eq("power.subjectId", powerModel.getSubjectId().trim()));
        }
        if(powerModel.getSubjectIds() != null && !"".equals(powerModel.getSubjectIds())) {
            crit.add(Restrictions.in("power.subjectId", powerModel.getSubjectIds().split(",")));
        }
        if(powerModel.getAdjustFlag() != null && !"".equals(powerModel.getAdjustFlag())) {
            crit.add(Restrictions.sqlRestriction(" not exists ( select 1 from PowerOperation po where po.powerOpt.id = p.id and po.powerOptTemp.currentState in ('10', '20') )" ));
        }
        //分页条件
        crit.setFirstResult(start);
        crit.setMaxResults(length);
        crit.addOrder(Order.desc("power.code"));
        return crit.list();
    }
    /**
     * 
    * @Function: PowerDao.java
    * @Description: 分页查询总数
    *
    * @param: 分页查询的总数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2018年12月24日 下午3:58:29 
    *
     */
    public long listCount(PowerModel powerModel, OrgCtrlInfoModel orgCtrl) {
      //获取session
        Session session = super.getSession();
        
        //主表tbl_power_list 取别名为 power
        Criteria crit = session.createCriteria(Power.class, "power");
        
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        
        //第一层exists 表 tbl_base_subject 取别名为 sub
        DetachedCriteria subCrit = DetachedCriteria.forClass(Subject.class,"sub");
        
        //第二层exists 表 TBL_ADMIN_DIVISION_DIVIDED 取别名为 division 
        DetachedCriteria divisionCrit = DetachedCriteria.forClass(AdminDivisionDivided.class, "division");
        
        //设置区划权限表 TBL_ADMIN_DIVISION_DIVIDED 和 主体表tbl_base_subject关联的字段
        divisionCrit.add(Property.forName("division.adminDivisionCode").eqProperty("sub.area"));
        
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
        }
        
        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
        subCrit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("division.adminDivisionCode"))));
        
        //关联条件--关联字段 power.SUBJECT_ID = sub.ID
        subCrit.add(Property.forName("power.subjectId").eqProperty(Property.forName("sub.id")));
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        
        //机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        int orgType = orgCtrl.getOrgType();
        
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (2 == orgType || 3 == orgType) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
            }
        }
        //主体删除标志
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        //案件删除标志
        crit.add(Restrictions.eq("power.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        
        //案件名称
        if(powerModel.getCode() != null && !"".equals(powerModel.getCode())) {
            crit.add(Restrictions.like("power.code", powerModel.getCode().trim(),MatchMode.ANYWHERE));
        }
        // 职权状态
        if(powerModel.getCurrentState() != null && !"".equals(powerModel.getCurrentState())) {
            crit.add(Restrictions.eq("power.currentState", powerModel.getCurrentState()));
        }
        if(powerModel.getName() != null && !"".equals(powerModel.getName())) {
            crit.add(Restrictions.like("power.name", powerModel.getName().trim(),MatchMode.ANYWHERE));
        }
        if(powerModel.getPowerType() != null && !"-1".equals(powerModel.getPowerType())) {
            crit.add(Restrictions.eq("power.powerType", powerModel.getPowerType()));
        }
        if(powerModel.getPowerDetail() != null && !"".equals(powerModel.getPowerDetail())) {
            crit.add(Restrictions.sqlRestriction(" exists (select 1 from PowerDetail as pd where pd.powerDetail.id = power.id and code in ('" + powerModel.getPowerDetail().replace(",", "','") + "'))"));
        }
        if(powerModel.getIsDeiscretionary() != null && "01".equals(powerModel.getIsDeiscretionary()) ) {
            crit.add(Restrictions.sqlRestriction(" exists (select 1 from PowerDiscretionary as pdt where pdt.powerDeiscretionary.id = power.id)" ));
        } else if(powerModel.getIsDeiscretionary() != null && "02".equals(powerModel.getIsDeiscretionary()) ) {
            crit.add(Restrictions.sqlRestriction(" not exists (select 1 from PowerDiscretionary as pdt where pdt.powerDeiscretionary.id = power.id)" ));
        }
        if(powerModel.getSubjectId() != null && !"".equals(powerModel.getSubjectId())) {
            crit.add(Restrictions.eq("power.subjectId", powerModel.getSubjectId().trim()));
        }
        if(powerModel.getSubjectIds() != null && !"".equals(powerModel.getSubjectIds())) {
            crit.add(Restrictions.in("power.subjectId", powerModel.getSubjectIds().split(",")));
        }
        if(powerModel.getAdjustFlag() != null && !"".equals(powerModel.getAdjustFlag())) {
            crit.add(Restrictions.sqlRestriction(" not exists ( select 1 from PowerOperation po where po.powerOpt.id = p.id and po.powerOptTemp.currentState in ('10', '20') )" ));
        }
        
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.intValue();
    }
    /**
     * 
    * @Function: PowerDao.java
    * @Description: 通过ID查询职权详细信息
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2018年12月25日 下午2:49:46 
    *
     */
    public Power get(String id) {
        return super.get(id);
    }
    
    public List<Power> findPowerByIds(int firstResult, int rows, PowerModel powerModel) {
        String hql = "from Power p where isDelete = 0 ";
        if(powerModel.getCode() != null && !"".equals(powerModel.getCode())) {
            hql = hql + " and code like '%" + powerModel.getCode() + "%' ";
        }
        if(powerModel.getName() != null && !"".equals(powerModel.getName())) {
            hql = hql + " and name like '%" + powerModel.getName() + "%' ";
        }
        if(powerModel.getPowerType() != null && !"".equals(powerModel.getPowerType())) {
            hql = hql + " and powerType = '" + powerModel.getPowerType() + "' ";
        }
        if(powerModel.getIds() != null && !"".equals(powerModel.getIds())) {
            hql = hql + " and id in ('" + powerModel.getIds().replace(",", "','") + "') ";
        }
        return super.pageFind(hql + " order by code", firstResult, rows, null);
    }
    
    public Long findPowerByIdsCount(PowerModel powerModel) {
        String hql = "select count(*) from Power p where isDelete = 0 ";
        if(powerModel.getCode() != null && !"".equals(powerModel.getCode())) {
            hql = hql + " and code = '" + powerModel.getCode() + "' ";
        }
        if(powerModel.getName() != null && !"".equals(powerModel.getName())) {
            hql = hql + " and name like '%" + powerModel.getName() + "%' ";
        }
        if(powerModel.getPowerType() != null && !"".equals(powerModel.getPowerType())) {
            hql = hql + " and powerType = '" + powerModel.getPowerType() + "' ";
        }
        if(powerModel.getIds() != null && !"".equals(powerModel.getIds())) {
            hql = hql + " and id in ('" + powerModel.getIds().replace(",", "','") + "') ";
        }
        return super.count(hql, null);
    }
    
    public List<Power> getPowerByActSubject(int firstResult, int rows, PowerModel powerModel) {
        String hql = " from Power p where isDelete = 0 ";
        if(powerModel.getPowerType() != null && !"".equals(powerModel.getPowerType())) {
            hql = hql + " and p.powerType = '" + powerModel.getPowerType() + "'"; 
        }
        if(powerModel.getCode() != null && !"".equals(powerModel.getCode())) {
            hql = hql + " and p.code = '" + powerModel.getCode() + "'"; 
        }
        if(powerModel.getName() != null && !"".equals(powerModel.getName())) {
            hql = hql + " and p.name like '%" + powerModel.getName() + "%'"; 
        }
        if(powerModel.getIds() != null && !"".equals(powerModel.getIds())) {
            hql = hql + " and p.id in ('" + powerModel.getIds().replace(",", "','") + "') ";
        }
        if(powerModel.getActSubject() != null && !"".equals(powerModel.getActSubject())) {
            hql = hql + " and exists (select 1 from PowerSubject ps where ps.powerSubject.id = p.id and ps.subject.id = '" + powerModel.getActSubject() + "')"; 
        }
        return super.pageFind(hql, firstResult, rows, null);
    }
    
    public Long getPowerCountByActSubject(PowerModel powerModel) {
        String hql = "select count(*) from Power p where isDelete = 0 ";
        if(powerModel.getPowerType() != null && !"".equals(powerModel.getPowerType())) {
            hql = hql + " and p.powerType = '" + powerModel.getPowerType() + "'"; 
        }
        if(powerModel.getCode() != null && !"".equals(powerModel.getCode())) {
            hql = hql + " and p.code = '" + powerModel.getCode() + "'"; 
        }
        if(powerModel.getName() != null && !"".equals(powerModel.getName())) {
            hql = hql + " and p.name like '%" + powerModel.getName() + "%'"; 
        }
        if(powerModel.getIds() != null && !"".equals(powerModel.getIds())) {
            hql = hql + " and p.id in ('" + powerModel.getIds().replace(",", "','") + "') ";
        }
        if(powerModel.getActSubject() != null && !"".equals(powerModel.getActSubject())) {
            hql = hql + " and exists (select 1 from PowerSubject ps where ps.powerSubject.id = p.id and ps.subject.id = '" + powerModel.getActSubject() + "')"; 
        }
        return super.count(hql, null);
    }
    
    public void revokePower(String batchCode, String ids) {
        List<Object> paramsList = new ArrayList<Object>();
        // 调整时间
        paramsList.add(new Date());
        // 批次
        paramsList.add(batchCode);
        // 删除日期
        paramsList.add(new Date());
        String hql = " update Power set isDelete = 1,revokeDate =?, batchCode =?, deleteDate =? where id in ('" + ids.replace(",", "','") + "')";
        deleteOrUpdateByQuery(hql, paramsList.toArray());
    }
    
    public List<Power> getPowerByActSubjects(int firstResult, int rows, PowerModel powerModel) {
        String hql = " from Power p where isDelete = 0 ";
        List<Object> params = new ArrayList<Object>();
        if(powerModel.getPowerType() != null && !"".equals(powerModel.getPowerType())) {
            hql = hql + " and p.powerType = ? "; 
            params.add(powerModel.getPowerType().trim());
        }
        if(powerModel.getCode() != null && !"".equals(powerModel.getCode())) {
            hql = hql + " and p.code = ? "; 
            params.add(powerModel.getCode().trim());
        }
        if(powerModel.getName() != null && !"".equals(powerModel.getName())) {
            hql = hql + " and p.name like ? "; 
            params.add("%" + powerModel.getName().trim() + "%");
        }
        if(powerModel.getIds() != null && !"".equals(powerModel.getIds())) {
            hql = hql + " and p.id in ('" + powerModel.getIds().replace(",", "','") + "') ";
        }
        if(powerModel.getActSubject() != null && !"".equals(powerModel.getActSubject())) {
            hql = hql + " and exists (select 1 from PowerSubject ps where ps.powerSubject.id = p.id and ps.subject.id in ( '" + powerModel.getActSubject().replace(",", "','") + "') )"; 
        }
        return super.pageFind(hql, firstResult, rows, params.toArray());
    }
    
    public Long getPowerCountByActSubjects(PowerModel powerModel) {
        String hql = "select count(*) from Power p where isDelete = 0 ";
        List<Object> params = new ArrayList<Object>();
        if(powerModel.getPowerType() != null && !"".equals(powerModel.getPowerType())) {
            hql = hql + " and p.powerType = ? "; 
            params.add(powerModel.getPowerType().trim());
        }
        if(powerModel.getCode() != null && !"".equals(powerModel.getCode())) {
            hql = hql + " and p.code = ? "; 
            params.add(powerModel.getCode().trim());
        }
        if(powerModel.getName() != null && !"".equals(powerModel.getName())) {
            hql = hql + " and p.name like ? "; 
            params.add("%" + powerModel.getName().trim() + "%");
        }
        if(powerModel.getIds() != null && !"".equals(powerModel.getIds())) {
            hql = hql + " and p.id in ('" + powerModel.getIds().replace(",", "','") + "') ";
        }
        if(powerModel.getActSubject() != null && !"".equals(powerModel.getActSubject())) {
            hql = hql + " and exists (select 1 from PowerSubject ps where ps.powerSubject.id = p.id and ps.subject.id in ( '" + powerModel.getActSubject().replace(",", "','") + "') )"; 
        }
        return super.count(hql, params.toArray());
    }
}
