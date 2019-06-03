package com.beidasoft.zfjd.caseManager.simpleCase.dao;

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

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimpleBase;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimpleBaseModel;
import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class CaseSimpleBaseDao extends TeeBaseDao<CaseSimpleBase>{
    
    /**
     ** 分页查询列表
     * @param start
     * @param length
     * @param cbModel
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<CaseSimpleBase> findListByPage(int start, int length, CaseSimpleBaseModel simpleBaseModel) {
        //查询SQL字符串
        StringBuffer hql = new StringBuffer();
        //参数集合
        ArrayList list = new ArrayList();
        hql.append(" from CaseSimpleBase where isDelete = 0 ");
        //案件名称
        if (simpleBaseModel.getName() != null && !"".equals(simpleBaseModel.getName().trim())) {
            hql.append(" and name like ? ");
            list.add("%"+simpleBaseModel.getName().trim()+"%");
        }
        //处罚决定书文号
        if (simpleBaseModel.getPunishmentCode() != null && !"".equals(simpleBaseModel.getPunishmentCode().trim())) {
            hql.append(" and punishment_code like ? ");
            list.add("%"+simpleBaseModel.getPunishmentCode().trim()+"%");
        }
        //主体名称
        if (simpleBaseModel.getSubjectId() != null && !"".equals(simpleBaseModel.getSubjectId().trim())) {
            hql.append(" and subject_id = ? ");
            list.add(simpleBaseModel.getSubjectId().trim());
        }
        //主体名称
        if (simpleBaseModel.getSubjectIds() != null && !"".equals(simpleBaseModel.getSubjectIds().trim())) {
            hql.append(" and subject_id in ('"+simpleBaseModel.getSubjectIds().trim().replace(",", "','")+"') ");
        }
       //提交状态
        if (simpleBaseModel.getIsSubmit() != null) {
            hql.append(" and is_submit = ? ");
            list.add(simpleBaseModel.getIsSubmit());
        }
        //入库日期
        if (simpleBaseModel.getCreateStartDateStr() != null && !"".equals(simpleBaseModel.getCreateStartDateStr().trim())) {
            //大于等于起止时间
            hql.append(" and create_date >= ? ");
            list.add(TeeDateUtil.format(simpleBaseModel.getCreateStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (simpleBaseModel.getCreateEndDateStr() != null && !"".equals(simpleBaseModel.getCreateEndDateStr().trim())) {
            //小于等于截至时间
            hql.append(" and create_date <= ? ");
            list.add(TeeDateUtil.format(simpleBaseModel.getCreateEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        //决定书日期
        if (simpleBaseModel.getPunishmentStartDateStr() != null && !"".equals(simpleBaseModel.getPunishmentStartDateStr().trim())) {
            //大于等于起止时间
            hql.append(" and create_date >= ? ");
            list.add(TeeDateUtil.format(simpleBaseModel.getPunishmentStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (simpleBaseModel.getPunishmentEndDateStr() != null && !"".equals(simpleBaseModel.getPunishmentEndDateStr().trim())) {
            //小于等于截至时间
            hql.append(" and create_date <= ? ");
            list.add(TeeDateUtil.format(simpleBaseModel.getPunishmentEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        hql.append(" order by create_date desc, id ");
        Object[] params = list.toArray();
        return super.pageFind(hql.toString(), start, length, params);
    }
    
    /**
     ** 分页查询记录数
     * @param simpleBaseModel
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public long listCount(CaseSimpleBaseModel simpleBaseModel) {
        // 查询SQL字符串
        StringBuffer hql = new StringBuffer();
        // 参数集合
        ArrayList list = new ArrayList();
        hql.append("select count(*) from CaseSimpleBase where isDelete = 0 ");
        // 案件名称
        if (simpleBaseModel.getName() != null && !"".equals(simpleBaseModel.getName().trim())) {
            hql.append(" and name like ? ");
            list.add("%"+simpleBaseModel.getName().trim()+"%");
        }
        // 处罚决定书文号
        if (simpleBaseModel.getPunishmentCode() != null && !"".equals(simpleBaseModel.getPunishmentCode().trim())) {
            hql.append(" and punishment_code like ? ");
            list.add("%"+simpleBaseModel.getPunishmentCode().trim()+"%");
        }
        // 主体名称
        if (simpleBaseModel.getSubjectId() != null && !"".equals(simpleBaseModel.getSubjectId().trim())) {
            hql.append(" and subject_id = ? ");
            list.add(simpleBaseModel.getSubjectId().trim());
        }
        // 主体名称
        if (simpleBaseModel.getSubjectIds() != null && !"".equals(simpleBaseModel.getSubjectIds().trim())) {
            hql.append(" and subject_id in ('"+simpleBaseModel.getSubjectIds().trim().replace(",", "','")+"') ");
        }
        //提交状态
        if (simpleBaseModel.getIsSubmit() != null) {
            hql.append(" and is_submit = ? ");
            list.add(simpleBaseModel.getIsSubmit());
        }
        //入库日期
        if (simpleBaseModel.getCreateStartDateStr() != null && !"".equals(simpleBaseModel.getCreateStartDateStr().trim())) {
            //大于等于起止时间
            hql.append(" and create_date >= ? ");
            list.add(TeeDateUtil.format(simpleBaseModel.getCreateStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (simpleBaseModel.getCreateEndDateStr() != null && !"".equals(simpleBaseModel.getCreateEndDateStr().trim())) {
            //小于等于截至时间
            hql.append(" and create_date <= ? ");
            list.add(TeeDateUtil.format(simpleBaseModel.getCreateEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        //决定书日期
        if (simpleBaseModel.getPunishmentStartDateStr() != null && !"".equals(simpleBaseModel.getPunishmentStartDateStr().trim())) {
            //大于等于起止时间
            hql.append(" and create_date >= ? ");
            list.add(TeeDateUtil.format(simpleBaseModel.getPunishmentStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (simpleBaseModel.getPunishmentEndDateStr() != null && !"".equals(simpleBaseModel.getPunishmentEndDateStr().trim())) {
            //小于等于截至时间
            hql.append(" and create_date <= ? ");
            list.add(TeeDateUtil.format(simpleBaseModel.getPunishmentEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        Object[] params = list.toArray();
        return super.count(hql.toString(), params);
    }
    
    /**
     * 
    * @Function: findListByPageSearch()
    * @Description: 通过区县权限表 TBL_ADMIN_DIVISION_DIVIDED，
    * *系统关系表TBL_SYS_BUSSINESS_RELATION，组合控制数据查询权限
    * 
    * SELECT *
        FROM TBL_CASE_SIMPLE_BASE cBase
        WHERE EXISTS
          (
              SELECT 1
              FROM TBL_BASE_SUBJECT subject
              WHERE EXISTS
                (
                    SELECT 1
                    FROM TBL_ADMIN_DIVISION_DIVIDED 
                    WHERE DISTRICT_CODE = '522601'  -- 分3类情况，对应字段PROVINCIAL_CODE、CITY_CODE、DISTRICT_CODE 
                                                    --    控制层级，若为市级，则刷选admin_division_Code为市级及市级以下区划，以此类推
                    AND admin_division_Code = subject.area （admin_division_Code行政区划，且唯一）
                )
              and subject.id = cBase.subject_id  -- 主体ID关联，刷选在行政区划之内的所有主体ID
              AND subject.org_Sys = '02' -- 若为执法主体，执法部门，则查询本单位及单位以下数据，为监督部门，则去掉该条件
              AND subject.is_delete = 0
          )
          AND cBase.is_Delete = 0
          ...更多条件往后加
    * @param: start 分页起始数
    * @param: length 每页显示条数
    * @param: cbModel 参数
    * @param: orgCtrl 权限控制
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月26日 下午9:20:52 
    *
     */
    @SuppressWarnings("unchecked")
    public List<CaseSimpleBase> findListByPageSearch(int start, int length, CaseSimpleBaseModel cbModel, OrgCtrlInfoModel orgCtrl){
        //参考上述SQL理解下面代码逻辑。
        //获取session
        Session session = super.getSession();
        //主表tbl_case_common_base 取别名为 cBase
        Criteria crit = session.createCriteria(CaseSimpleBase.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        int orgType = 0;
        if(orgCtrl.getOrgType() != null)
            orgType = orgCtrl.getOrgType();
        if(orgType == 0)
            return new ArrayList<>();
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
        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
        subCrit.add(Property.forName("cBase.subjectId").eqProperty(Property.forName("sub.id")));
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        //是否是分级管理员（true是，false否）
        /*boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (2 == orgType || 3 == orgType) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
            }
        }*/
        String orgSys = orgCtrl.getOrgSysCode();
        if(orgType != 0){
            if(orgType == 2 || orgType == 3 || orgCtrl.getIsLawPerson()){
                if(!orgCtrl.getGradeAdministrator()){
                    if(!TeeUtility.isNullorEmpty(orgSys)){
                        String[] orgSysArray = orgSys.split(",");
                        if(orgSysArray.length > 1){
                            LogicalExpression logicalExpression = Restrictions.or(Restrictions.like("orgSys", orgSysArray[0], MatchMode.ANYWHERE), Restrictions.like("orgSys", orgSysArray[1], MatchMode.ANYWHERE));
                            for (int i = 2; i < orgSysArray.length; i++) {
                                logicalExpression = Restrictions.or(logicalExpression, Restrictions.like("orgSys", orgSysArray[i], MatchMode.ANYWHERE));
                            }
                            subCrit.add(logicalExpression);
                        }else{
                            subCrit.add(Restrictions.like("orgSys", orgSysArray[0], MatchMode.ANYWHERE));
                        }
                    }
                }
            }
        }
        //主体删除标志
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        //案件删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //案件名称
        if (cbModel.getName() != null && !"".equals(cbModel.getName().trim())) {
            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
        }
        //处罚决定书文号
        if (cbModel.getPunishmentCode() != null && !"".equals(cbModel.getPunishmentCode().trim())) {
            crit.add(Restrictions.like("cBase.punishmentCode", cbModel.getPunishmentCode().trim(), MatchMode.ANYWHERE));
        }
        //提交状态
        if (cbModel.getIsSubmit() != null) {
            crit.add(Restrictions.eq("cBase.isSubmit", cbModel.getIsSubmit()));
        }
        //主体ID
        if (cbModel.getSubjectId() != null && !"".equals(cbModel.getSubjectId().trim())) {
            crit.add(Restrictions.eq("cBase.subjectId", cbModel.getSubjectId().trim()));
        }
        //主体IDs
        if (cbModel.getSubjectIds() != null && !"".equals(cbModel.getSubjectIds().trim())) {
            crit.add(Restrictions.in("cBase.subjectId", cbModel.getSubjectIds().split(",")));
        }
        //部门ID
        if (cbModel.getDepartmentId() != null && !"".equals(cbModel.getDepartmentId().trim())) {
            crit.add(Restrictions.eq("cBase.departmentId", cbModel.getDepartmentId().trim()));
        }
        //入库起始日期
        if (cbModel.getCreateStartDateStr() != null && !"".equals(cbModel.getCreateStartDateStr().trim())) {
            //大于等于起止时间
            Date createDate = TeeDateUtil.format(cbModel.getCreateStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.ge("cBase.createDate", createDate));
        }
        //入库截至时间
        if (cbModel.getCreateEndDateStr() != null && !"".equals(cbModel.getCreateEndDateStr().trim())) {
            //小于等于截至时间
            Date createDate = TeeDateUtil.format(cbModel.getCreateStartDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.le("cBase.createDate", createDate));
        }
        //处罚决定书日期 起始日期
        if (cbModel.getPunishmentStartDateStr() != null && !"".equals(cbModel.getPunishmentStartDateStr().trim())) {
            //大于等于起止时间
            Date punishmentDate = TeeDateUtil.format(cbModel.getPunishmentStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.ge("cBase.punishmentDate", punishmentDate));
        }
        //处罚决定书日期 截至时间
        if (cbModel.getPunishmentEndDateStr() != null && !"".equals(cbModel.getPunishmentEndDateStr().trim())) {
            //小于等于截至时间
            Date punishmentDate = TeeDateUtil.format(cbModel.getPunishmentEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.le("cBase.punishmentDate", punishmentDate));
        }
        //立案日期 起始日期
        if (cbModel.getFilingStartDateStr() != null && !"".equals(cbModel.getFilingStartDateStr().trim())) {
            //大于等于起止时间
            Date filingDate = TeeDateUtil.format(cbModel.getFilingStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.ge("cBase.filingDate", filingDate));
        }
        //立案日期 截至时间
        if (cbModel.getFilingEndDateStr() != null && !"".equals(cbModel.getFilingEndDateStr().trim())) {
            //小于等于截至时间
            Date filingDate = TeeDateUtil.format(cbModel.getFilingEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.le("cBase.filingDate", filingDate));
        }
        //结案日期 起始日期
        if (cbModel.getClosedStartDateStr() != null && !"".equals(cbModel.getClosedStartDateStr().trim())) {
            //大于等于起止时间
            Date closedDate = TeeDateUtil.format(cbModel.getClosedStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.ge("cBase.closedDate", closedDate));
        }
        //结案日期 截至时间
        if (cbModel.getClosedEndDateStr() != null && !"".equals(cbModel.getClosedEndDateStr().trim())) {
            //小于等于截至时间
            Date closedDate = TeeDateUtil.format(cbModel.getClosedEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.le("cBase.closedDate", closedDate));
        }
        //分页条件
        crit.setFirstResult(start);
        crit.setMaxResults(length);
        //排序
        crit.addOrder(Order.desc("cBase.updateDate"));
        return crit.list();
    }
    
    /**
     * 
    * @Function: listSearchCount()
    * @Description: 一般案件查询总数
    *
    * @param: cbModel
    * @param: orgCtrl
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月28日 上午10:46:01 
    *
     */
    public long listSearchCount(CaseSimpleBaseModel cbModel, OrgCtrlInfoModel orgCtrl) {
      //参考上述SQL理解下面代码逻辑。
        //获取session
        Session session = super.getSession();
        //主表tbl_case_common_base 取别名为 cBase
        Criteria crit = session.createCriteria(CaseSimpleBase.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        int orgType = 0;
        if(orgCtrl.getOrgType() != null)
            orgType = orgCtrl.getOrgType();
        if(orgType == 0)
            return 0;
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
        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
        subCrit.add(Property.forName("cBase.subjectId").eqProperty(Property.forName("sub.id")));
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        //是否是分级管理员（true是，false否）
        /*boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (2 == orgType || 3 == orgType) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
            }
        }*/
        String orgSys = orgCtrl.getOrgSysCode();
        if(orgType != 0){
            if(orgType == 2 || orgType == 3 || orgCtrl.getIsLawPerson()){
                if(!orgCtrl.getGradeAdministrator()){
                    if(!TeeUtility.isNullorEmpty(orgSys)){
                        String[] orgSysArray = orgSys.split(",");
                        if(orgSysArray.length > 1){
                            LogicalExpression logicalExpression = Restrictions.or(Restrictions.like("orgSys", orgSysArray[0], MatchMode.ANYWHERE), Restrictions.like("orgSys", orgSysArray[1], MatchMode.ANYWHERE));
                            for (int i = 2; i < orgSysArray.length; i++) {
                                logicalExpression = Restrictions.or(logicalExpression, Restrictions.like("orgSys", orgSysArray[i], MatchMode.ANYWHERE));
                            }
                            subCrit.add(logicalExpression);
                        }else{
                            subCrit.add(Restrictions.like("orgSys", orgSysArray[0], MatchMode.ANYWHERE));
                        }
                    }
                }
            }
        }
        //主体删除标志
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        //案件删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        //案件名称
        if (cbModel.getName() != null && !"".equals(cbModel.getName().trim())) {
            crit.add(Restrictions.like("cBase.name", cbModel.getName().trim(),MatchMode.ANYWHERE));
        }
        //处罚决定书文号
        if (cbModel.getPunishmentCode() != null && !"".equals(cbModel.getPunishmentCode().trim())) {
            crit.add(Restrictions.like("cBase.punishmentCode", cbModel.getPunishmentCode().trim(), MatchMode.ANYWHERE));
        }
        //提交状态
        if (cbModel.getIsSubmit() != null) {
            crit.add(Restrictions.eq("cBase.isSubmit", cbModel.getIsSubmit()));
        }
        //主体ID
        if (cbModel.getSubjectId() != null && !"".equals(cbModel.getSubjectId().trim())) {
            crit.add(Restrictions.eq("cBase.subjectId", cbModel.getSubjectId().trim()));
        }
        //主体IDs
        if (cbModel.getSubjectIds() != null && !"".equals(cbModel.getSubjectIds().trim())) {
            crit.add(Restrictions.in("cBase.subjectId", cbModel.getSubjectIds().split(",")));
        }
        //部门ID
        if (cbModel.getDepartmentId() != null && !"".equals(cbModel.getDepartmentId().trim())) {
            crit.add(Restrictions.eq("cBase.departmentId", cbModel.getDepartmentId().trim()));
        }
        //入库起始日期
        if (cbModel.getCreateStartDateStr() != null && !"".equals(cbModel.getCreateStartDateStr().trim())) {
            //大于等于起止时间
            Date createDate = TeeDateUtil.format(cbModel.getCreateStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.ge("cBase.createDate", createDate));
        }
        //入库截至时间
        if (cbModel.getCreateEndDateStr() != null && !"".equals(cbModel.getCreateEndDateStr().trim())) {
            //小于等于截至时间
            Date createDate = TeeDateUtil.format(cbModel.getCreateStartDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.le("cBase.createDate", createDate));
        }
        //处罚决定书日期 起始日期
        if (cbModel.getPunishmentStartDateStr() != null && !"".equals(cbModel.getPunishmentStartDateStr().trim())) {
            //大于等于起止时间
            Date punishmentDate = TeeDateUtil.format(cbModel.getPunishmentStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.ge("cBase.punishmentDate", punishmentDate));
        }
        //处罚决定书日期 截至时间
        if (cbModel.getPunishmentEndDateStr() != null && !"".equals(cbModel.getPunishmentEndDateStr().trim())) {
            //小于等于截至时间
            Date punishmentDate = TeeDateUtil.format(cbModel.getPunishmentEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.le("cBase.punishmentDate", punishmentDate));
        }
        //立案日期 起始日期
        if (cbModel.getFilingStartDateStr() != null && !"".equals(cbModel.getFilingStartDateStr().trim())) {
            //大于等于起止时间
            Date filingDate = TeeDateUtil.format(cbModel.getFilingStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.ge("cBase.filingDate", filingDate));
        }
        //立案日期 截至时间
        if (cbModel.getFilingEndDateStr() != null && !"".equals(cbModel.getFilingEndDateStr().trim())) {
            //小于等于截至时间
            Date filingDate = TeeDateUtil.format(cbModel.getFilingEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.le("cBase.filingDate", filingDate));
        }
        //结案日期 起始日期
        if (cbModel.getClosedStartDateStr() != null && !"".equals(cbModel.getClosedStartDateStr().trim())) {
            //大于等于起止时间
            Date closedDate = TeeDateUtil.format(cbModel.getClosedStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.ge("cBase.closedDate", closedDate));
        }
        //结案日期 截至时间
        if (cbModel.getClosedEndDateStr() != null && !"".equals(cbModel.getClosedEndDateStr().trim())) {
            //小于等于截至时间
            Date closedDate = TeeDateUtil.format(cbModel.getClosedEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
            crit.add(Restrictions.le("cBase.closedDate", closedDate));
        }
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.intValue();
    }
    
    /**
     * 
    * @Function: updateOrDeleteOrSubmitByIds()
    * @Description: 提交，退回，删除
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月28日 下午4:20:32 
    *
     */
    public void updateOrDeleteOrSubmitByIds(Map<String, Object> columns, String idStr) {
        try {
            if (idStr != null && idStr.length() > 0) {
                String[] ids = idStr.split(",");
                for (String id: ids) {
                    super.update(columns, id);
                }
            }
        }catch (Exception e) {
            System.out.println("CaseCommonBaseDao.updateOrDeleteOrSubmitByIds() "+e.getMessage());
        }
    }
}
