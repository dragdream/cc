/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.dao 
 * @author: songff   
 * @date: 2018年12月26日 下午2:47:31 
 */
package com.beidasoft.zfjd.caseManager.commonCase.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.caseManager.commonCase.controller.CaseCommonBaseController;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonBaseModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonDataModel;
import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**   
* 2018 
* @ClassName: CaseCommonBasicDao.java
* @Description: 该类的功能描述
*
* @author: songff
* @date: 2018年12月26日 下午2:47:31 
*
*/
@Repository("caseCommonBaseDao")
public class CaseCommonBaseDao extends TeeBaseDao<CaseCommonBase>{
    
    //获取日志记录器Logger，名字为本类类名
    private static Logger logger = LogManager.getLogger(CaseCommonBaseController.class);
    /**
     * 
    * @Function: findListByPage
    * @Description: 分页查询一般案件信息
    *
    * @param: start 起始位置
    * @param: length 查询长度
    * @param: caseCommonBasic 查询条件参数
    * @return：返回一般案件list集合
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2018年12月26日 下午3:18:31 
    *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<CaseCommonBase> findListByPage(int start, int length, CaseCommonBaseModel cbModel) {
        //查询SQL字符串
        StringBuffer hql = new StringBuffer();
        //参数集合
        ArrayList list = new ArrayList();
        hql.append(" from CaseCommonBase where is_delete = 0 ");
        //案件名称
        if (cbModel.getName() != null && !"".equals(cbModel.getName().trim())) {
            hql.append(" and name like ? ");
            list.add("%"+cbModel.getName().trim()+"%");
        }
        //处罚决定书文号
        if (cbModel.getPunishmentCode() != null && !"".equals(cbModel.getPunishmentCode().trim())) {
            hql.append(" and punishment_code like ? ");
            list.add("%"+cbModel.getPunishmentCode().trim()+"%");
        }
        //提交状态
        if (cbModel.getIsSubmit() != null) {
            hql.append(" and is_submit = ? ");
            list.add(cbModel.getIsSubmit());
        }
        //主体ID
        if (cbModel.getSubjectId() != null && !"".equals(cbModel.getSubjectId().trim())) {
            hql.append(" and subject_id = ? ");
            list.add(cbModel.getSubjectId().trim());
        }
        //主体IDs
        if (cbModel.getSubjectIds() != null && !"".equals(cbModel.getSubjectIds().trim())) {
            hql.append(" and subject_id in ('"+cbModel.getSubjectIds().trim().replace(",", "','")+"') ");
        }
        //部门ID
        if (cbModel.getDepartmentId() != null && !"".equals(cbModel.getDepartmentId().trim())) {
            hql.append(" and department_id = ? ");
            list.add(cbModel.getDepartmentId().trim());
        }
        //入库起始日期
        if (cbModel.getCreateStartDateStr() != null && !"".equals(cbModel.getCreateStartDateStr().trim())) {
            //大于等于起止时间
            hql.append(" and create_date >= ? ");
            list.add(TeeDateUtil.format(cbModel.getCreateStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        //入库结束日期
        if (cbModel.getCreateEndDateStr() != null && !"".equals(cbModel.getCreateEndDateStr().trim())) {
            //小于等于截至时间
            hql.append(" and create_date <= ? ");
            list.add(TeeDateUtil.format(cbModel.getCreateEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        hql.append(" order by create_date desc, id");
        Object[] params = list.toArray();
        return super.pageFind(hql.toString(), start, length, params);
    }
    
    /**
     * 
    * @Function: listCount
    * @Description: 分页查询案件总数
    *
    * @param: caseCommonBasic 查询条件参数
    * @return：返回查询的案件总数
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2018年12月26日 下午3:27:52 
    *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public long listCount(CaseCommonBaseModel cbModel) {
        //查询SQL字符串
        StringBuffer hql = new StringBuffer();
        //参数集合
        ArrayList list = new ArrayList();
        hql.append("select count(id) from CaseCommonBase where is_delete = 0 ");
        //案件名称
        if (cbModel.getName() != null && !"".equals(cbModel.getName().trim())) {
            hql.append(" and name like ? ");
            list.add("%" + cbModel.getName().trim() + "%");
        }
        //处罚决定书文号
        if (cbModel.getPunishmentCode() != null && !"".equals(cbModel.getPunishmentCode().trim())) {
            hql.append(" and punishment_code like ? ");
            list.add("%" + cbModel.getPunishmentCode().trim() + "%");
        }
        //提交状态
        if (cbModel.getIsSubmit() != null) {
            hql.append(" and is_submit = ? ");
            list.add(cbModel.getIsSubmit());
        }
        //主体ID
        if (cbModel.getSubjectId() != null && !"".equals(cbModel.getSubjectId().trim())) {
            hql.append(" and subject_id = ? ");
            list.add(cbModel.getSubjectId().trim());
        }
        //主体IDs
        if (cbModel.getSubjectIds() != null && !"".equals(cbModel.getSubjectIds().trim())) {
            hql.append(" and subject_id in ('"+cbModel.getSubjectIds().trim().replace(",", "','")+"') ");
        }
        //部门ID
        if (cbModel.getDepartmentId() != null && !"".equals(cbModel.getDepartmentId().trim())) {
            hql.append(" and department_id = ? ");
            list.add(cbModel.getDepartmentId().trim());
        }
        //入库起始日期
        if (cbModel.getCreateStartDateStr() != null && !"".equals(cbModel.getCreateStartDateStr().trim())) {
            //大于等于起止时间
            hql.append(" and create_date >= ? ");
            list.add(TeeDateUtil.format(cbModel.getCreateStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        //入库起始日期
        if (cbModel.getCreateEndDateStr() != null && !"".equals(cbModel.getCreateEndDateStr().trim())) {
            //小于等于截至时间
            hql.append(" and create_date <= ? ");
            list.add(TeeDateUtil.format(cbModel.getCreateEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
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
        FROM TBL_CASE_COMMON_BASE cBase
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
    public List<CaseCommonBase> findListByPageSearch(int start, int length, CaseCommonBaseModel cbModel, OrgCtrlInfoModel orgCtrl){
        //参考上述SQL理解下面代码逻辑。
        //获取session
        Session session = super.getSession();
        //主表tbl_case_common_base 取别名为 cBase
        Criteria crit = session.createCriteria(CaseCommonBase.class, "cBase");
        // 设置查询结果为实体对象
        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        Integer orgType = 0;
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
        //机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (orgType != null && (2 == orgType || 3 == orgType)) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
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
    public long listSearchCount(CaseCommonBaseModel cbModel, OrgCtrlInfoModel orgCtrl) {
        Session session = super.getSession();
        //主表tbl_case_common_base 取别名 cBase
        Criteria crit = session.createCriteria(CaseCommonBase.class, "cBase");
//        crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        Integer orgType = 0;
        if(orgCtrl.getOrgType() != null)
            orgType = orgCtrl.getOrgType();
        if(orgType == 0)
            return 0;
        //第一层exists关联表 tbl_base_subject 取别名 sub
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
                // 控制省級层级，及省级以下数据
                divisionCrit.add(Restrictions.eq("provincialCode", adminDivisionCode));
            }
            if("0300".equals(levelCode)){
                // 控制市州級层级，及市州以下数据
                divisionCrit.add(Restrictions.eq("cityCode", adminDivisionCode));
            }
            if("0400".equals(levelCode)){
                // 控制区县层级，及区县以下数据
                divisionCrit.add(Restrictions.eq("districtCode", adminDivisionCode));
            }
        }
        //exists中的select 1 from类似，select division.admin_division_code from， 必须设置，不设置查询会报语法错误
        subCrit.add(Subqueries.exists(divisionCrit.setProjection(Projections.property("division.adminDivisionCode"))));
        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
        subCrit.add(Property.forName("cBase.subjectId").eqProperty(Property.forName("sub.id")));
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        //机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (orgType != null && (2 == orgType || 3 == orgType)) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
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
    * @Function: findCaseCommonBaseById();
    * @Description: 通过ID查询案件信息
    *
    * @param: cbModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月14日 下午2:46:48 
    *
     */
    public CaseCommonBase findCaseCommonBaseById(CaseCommonBaseModel cbModel) {
        CaseCommonBase cBase = null;
        try {
            cBase = super.get(cbModel.getId());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return cBase;
    }
    /**
     * 
    * @Function: saveFilingStage()
    * @Description: 立案阶段保存案件信息
    *
    * @param: cBase 保存信息对象
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月10日 下午5:15:48 
    *
     */
    public void saveFilingStage(CaseCommonBase cBase) throws Exception{
        try {
            super.save(cBase);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: updateCaseInfoByColumnsAndId()
    * @Description: 通过案件ID，信息项，更新案件信息
    *
    * @param: columns 需要更新的字段及值
    * @param: id 更新的案件ID
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 上午12:26:56 
    *
     */
    public void updateCaseInfoByColumnsAndId(Map<String, Object> columns, String id) throws Exception{
        try {
            super.update(columns, id);
        }catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: updateOrDeleteOrSubmitByIds()
    * @Description: 通过案件ID，删除，提交或退回案件
    *
    * @param: columns 需要更新的字段及值
    * @param: idStr 更新的案件ID字符串
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月16日 上午12:26:56 
    *
     */
    public void updateOrDeleteOrSubmitByIds(Map<String, Object> columns, String idStr) throws Exception{
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
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<CaseCommonBase> findListByPageSearch(TeeDataGridModel tModel, CaseCommonBaseModel cbModel) {
        String hqlS = "";
        //查询SQL字符串
        StringBuffer hql = new StringBuffer();
        //参数集合
        ArrayList list = new ArrayList();
        hql.append(" from CaseCommonBase where is_delete = 0 ");
        //案件名称
        if (cbModel.getName() != null && !"".equals(cbModel.getName().trim())) {
            hql.append(" and name like ? ");
            list.add("%"+cbModel.getName().trim()+"%");
        }
        //处罚决定书文号
        if (cbModel.getPunishmentCode() != null && !"".equals(cbModel.getPunishmentCode().trim())) {
            hql.append(" and punishment_code like ? ");
            list.add("%"+cbModel.getPunishmentCode().trim()+"%");
        }
        //提交状态
        if (cbModel.getIsSubmit() != null) {
            hql.append(" and is_submit = ? ");
            list.add(cbModel.getIsSubmit());
        }
        //主体ID
        if (cbModel.getSubjectId() != null && !"".equals(cbModel.getSubjectId().trim())) {
            hql.append(" and subject_id = ? ");
            list.add(cbModel.getSubjectId().trim());
        }
        //主体IDs
        if (cbModel.getSubjectIds() != null && !"".equals(cbModel.getSubjectIds().trim())) {
            hql.append(" and subject_id in ('"+cbModel.getSubjectIds().trim().replace(",", "','")+"') ");
        }
        //部门ID
        if (cbModel.getDepartmentId() != null && !"".equals(cbModel.getDepartmentId().trim())) {
            hql.append(" and department_id = ? ");
            list.add(cbModel.getDepartmentId().trim());
        }
        //入库起始日期
        if (cbModel.getCreateStartDateStr() != null && !"".equals(cbModel.getCreateStartDateStr().trim())) {
            //大于等于起止时间
            hql.append(" and create_date >= ? ");
            list.add(TeeDateUtil.format(cbModel.getCreateStartDateStr().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        //入库结束日期
        if (cbModel.getCreateEndDateStr() != null && !"".equals(cbModel.getCreateEndDateStr().trim())) {
            //小于等于截至时间
            hql.append(" and create_date <= ? ");
            list.add(TeeDateUtil.format(cbModel.getCreateEndDateStr().trim()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        if(!TeeUtility.isNullorEmpty(tModel.getSort())){
            hql.append(" order by ");
            String[] sorts = tModel.getSort().split(",");
            String[] orders = tModel.getOrder().split(",");
            for (int i = 0; i < sorts.length; i++) {
                hql.append(sorts[i].replace("DateStr", "Date") + " " + orders[i] + ",");
            }
            hqlS = hql.substring(0, hql.length() - 1);
        }else{
            hql.append(" order by create_date desc, id");
            hqlS = hql.toString();
        }
        Object[] params = list.toArray();
        return super.pageFind(hqlS, tModel.getFirstResult(), tModel.getRows(), params);
    }

    @SuppressWarnings("unchecked")
    public List<CaseCommonBase> findListByPageSearch(TeeDataGridModel tModel,
            Map<String, Object> requestData, List<CaseCommonDataModel> caseCommonDataModels, OrgCtrlInfoModel orgCtrl) {
        // TODO Auto-generated method stub
        //获取session
        Session session = super.getSession();
        //主表tbl_case_common_base 取别名为 cBase
        Criteria crit = session.createCriteria(CaseCommonBase.class, "cBase");
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
        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
        subCrit.add(Property.forName("cBase.subjectId").eqProperty(Property.forName("sub.id")));
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        //机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        Integer orgType = orgCtrl.getOrgType();
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (orgType != null && (2 == orgType || 3 == orgType)) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
            }
        }
        //主体删除标志
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        //案件删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        
        CaseCommonDataModel cModel = new CaseCommonDataModel();
        for (int i = 0; i < caseCommonDataModels.size(); i++) {
            cModel = caseCommonDataModels.get(i);
            if("DATE".equals(cModel.getDataType())){
                if(!TeeUtility.isNullorEmpty(requestData.get("begin" + cModel.getField()).toString())){
                    crit.add(Restrictions.ge("cBase." + cModel.getField(), requestData.get("begin" + cModel.getField())));
                }
                if(!TeeUtility.isNullorEmpty(requestData.get("end" + cModel.getField()).toString())){
                    crit.add(Restrictions.le("cBase." + cModel.getField(), requestData.get("end" + cModel.getField())));
                }
            }else if("01".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.eq("cBase." + cModel.getField(), requestData.get(cModel.getField())));
                }
            }else if("02".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.like("cBase." + cModel.getField(), requestData.get(cModel.getField()).toString(), MatchMode.ANYWHERE));
                }
            }else if("03".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.le("cBase." + cModel.getField(), requestData.get(cModel.getField())));
                }
            }else if("04".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.ge("cBase." + cModel.getField(), requestData.get(cModel.getField())));
                }
            }else if("05".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.in("cBase." + cModel.getField(), requestData.get(cModel.getField()).toString().split(",")));
                }
            }
        }
        
        //分页条件
        crit.setFirstResult(tModel.getFirstResult());
        crit.setMaxResults(tModel.getRows());
        //排序
        if(!TeeUtility.isNullorEmpty(tModel.getSort())){
            String[] sorts = tModel.getSort().split(",");
            String[] orders = tModel.getOrder().split(",");
            for (int i = 0; i < sorts.length; i++) {
                if("asc".equals(orders[i])){
                    crit.addOrder(Order.asc("cBase." + sorts[i]));
                }else{
                    crit.addOrder(Order.desc("cBase." + sorts[i]));
                }
            }
        }else{
            crit.addOrder(Order.desc("cBase.updateDate"));
        }
        return crit.list();
    }

    public Long listCount(Map<String, Object> requestData, List<CaseCommonDataModel> caseCommonDataModels, OrgCtrlInfoModel orgCtrl) {
        // TODO Auto-generated method stub
        //获取session
        Session session = super.getSession();
        //主表tbl_case_common_base 取别名为 cBase
        Criteria crit = session.createCriteria(CaseCommonBase.class, "cBase");
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
        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
        subCrit.add(Property.forName("cBase.subjectId").eqProperty(Property.forName("sub.id")));
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        //机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        Integer orgType = orgCtrl.getOrgType();
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (orgType != null && (2 == orgType || 3 == orgType)) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
            }
        }
        //主体删除标志
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        //案件删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        
        CaseCommonDataModel cModel = new CaseCommonDataModel();
        for (int i = 0; i < caseCommonDataModels.size(); i++) {
            cModel = caseCommonDataModels.get(i);
            if("DATE".equals(cModel.getDataType())){
                if(!TeeUtility.isNullorEmpty(requestData.get("begin" + cModel.getField()).toString())){
                    crit.add(Restrictions.ge("cBase." + cModel.getField(), requestData.get("begin" + cModel.getField())));
                }
                if(!TeeUtility.isNullorEmpty(requestData.get("end" + cModel.getField()).toString())){
                    crit.add(Restrictions.le("cBase." + cModel.getField(), requestData.get("end" + cModel.getField())));
                }
            }else if("01".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.eq("cBase." + cModel.getField(), requestData.get(cModel.getField())));
                }
            }else if("02".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.like("cBase." + cModel.getField(), requestData.get(cModel.getField()).toString(), MatchMode.ANYWHERE));
                }
            }else if("03".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.le("cBase." + cModel.getField(), requestData.get(cModel.getField())));
                }
            }else if("04".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.ge("cBase." + cModel.getField(), requestData.get(cModel.getField())));
                }
            }else if("05".equals(cModel.getMatchingMode())){
                if(!TeeUtility.isNullorEmpty(requestData.get(cModel.getField())) && !TeeUtility.isNullorEmpty(requestData.get(cModel.getField()).toString())){
                    crit.add(Restrictions.in("cBase." + cModel.getField(), requestData.get(cModel.getField()).toString().split(",")));
                }
            }
        }
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.longValue();
    }

    @SuppressWarnings("unchecked")
    public List<CaseCommonBase> findListByPageSearch(TeeDataGridModel tModel, Map<String, Object> requestData,
            OrgCtrlInfoModel orgCtrl) {
        // TODO Auto-generated method stub
        //获取session
        Session session = super.getSession();
        //主表tbl_case_common_base 取别名为 cBase
        Criteria crit = session.createCriteria(CaseCommonBase.class, "cBase");
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
        
        
        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
        subCrit.add(Property.forName("cBase.subjectId").eqProperty(Property.forName("sub.id")));
        
        
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        //机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        Integer orgType = orgCtrl.getOrgType();
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (orgType != null && (2 == orgType || 3 == orgType)) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
            }
        }
        //主体删除标志
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        // 行政区划
        if(!TeeUtility.isNullorEmpty(requestData.get("administrativeDivision")) && !TeeUtility.isNullorEmpty(requestData.get("administrativeDivision").toString())){
            subCrit.add(Restrictions.in("sub.area", requestData.get("administrativeDivision").toString().split(",")));
        }
        // 所属领域
        if(!TeeUtility.isNullorEmpty(requestData.get("orgSys")) && !TeeUtility.isNullorEmpty(requestData.get("orgSys").toString())){
            subCrit.add(Restrictions.in("sub.orgSys", requestData.get("orgSys").toString().split(",")));
        }
        
        //案件删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        // 入库日期起始日期
        if(!TeeUtility.isNullorEmpty(requestData.get("begincreateDate")) && !TeeUtility.isNullorEmpty(requestData.get("begincreateDate").toString())){
            crit.add(Restrictions.ge("cBase.createDate", requestData.get("begincreateDate")));
        }
        // 入库日期截至日期
        if(!TeeUtility.isNullorEmpty(requestData.get("endcreateDate")) && !TeeUtility.isNullorEmpty(requestData.get("endcreateDate").toString())){
            crit.add(Restrictions.le("cBase.createDate", requestData.get("endcreateDate")));
        }
        // 立案日期起始日期
        if(!TeeUtility.isNullorEmpty(requestData.get("beginfilingDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginfilingDate").toString())){
            crit.add(Restrictions.ge("cBase.filingDate", requestData.get("beginfilingDate")));
        }
        // 立案日期截至日期
        if(!TeeUtility.isNullorEmpty(requestData.get("endfilingDate")) && !TeeUtility.isNullorEmpty(requestData.get("endfilingDate").toString())){
            crit.add(Restrictions.le("cBase.filingDate", requestData.get("endfilingDate")));
        }
        // 结案日期起始日期
        if(!TeeUtility.isNullorEmpty(requestData.get("beginclosedDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginclosedDate").toString())){
            crit.add(Restrictions.ge("cBase.closedDate", requestData.get("beginclosedDate")));
        }
        // 结案日期截至日期
        if(!TeeUtility.isNullorEmpty(requestData.get("endclosedDate")) && !TeeUtility.isNullorEmpty(requestData.get("endclosedDate").toString())){
            crit.add(Restrictions.le("cBase.closedDate", requestData.get("endclosedDate")));
        }
        // 当事人类型
        if(!TeeUtility.isNullorEmpty(requestData.get("partyType")) && !TeeUtility.isNullorEmpty(requestData.get("partyType").toString())){
            crit.add(Restrictions.in("cBase.partyType", requestData.get("partyType").toString().split(",")));
        }
        // 案件来源
        if(!TeeUtility.isNullorEmpty(requestData.get("caseSource")) && !TeeUtility.isNullorEmpty(requestData.get("caseSource").toString())){
            crit.add(Restrictions.in("cBase.caseSource", requestData.get("caseSource").toString().split(",")));
        }
        // 办理状态
        if(!TeeUtility.isNullorEmpty(requestData.get("currentState")) && !TeeUtility.isNullorEmpty(requestData.get("currentState").toString())){
            crit.add(Restrictions.in("cBase.currentState", requestData.get("currentState").toString().split(",")));
        }
        // 结案状态
        if(!TeeUtility.isNullorEmpty(requestData.get("closedState")) && !TeeUtility.isNullorEmpty(requestData.get("closedState").toString())){
            crit.add(Restrictions.in("cBase.closedState", requestData.get("closedState").toString().split(",")));
        }
        // 执法机关
        if(!TeeUtility.isNullorEmpty(requestData.get("departmentId")) && !TeeUtility.isNullorEmpty(requestData.get("departmentId").toString())){
            crit.add(Restrictions.in("cBase.departmentId", requestData.get("departmentId").toString().split(",")));
        }
        // 执法主体
        if(!TeeUtility.isNullorEmpty(requestData.get("subjectId")) && !TeeUtility.isNullorEmpty(requestData.get("subjectId").toString())){
            crit.add(Restrictions.in("cBase.subjectId", requestData.get("subjectId").toString().split(",")));
        }
        // 案件名称
        if(!TeeUtility.isNullorEmpty(requestData.get("name")) && !TeeUtility.isNullorEmpty(requestData.get("name").toString())){
            crit.add(Restrictions.like("cBase.name", requestData.get("name").toString(), MatchMode.ANYWHERE));
        }
        
        // 提交状态
        if(!TeeUtility.isNullorEmpty(requestData.get("isSubmit")) && TeeStringUtil.getInteger(requestData.get("isSubmit"), -1) != -1){
            crit.add(Restrictions.eq("cBase.isSubmit", TeeStringUtil.getInteger(requestData.get("isSubmit"), -1)));
        }
        
        // 是否法制审核
        if(!TeeUtility.isNullorEmpty(requestData.get("isFilingLegalReview")) && TeeStringUtil.getInteger(requestData.get("isFilingLegalReview"), -1) != -1){
            crit.add(Restrictions.eq("cBase.isFilingLegalReview", TeeStringUtil.getInteger(requestData.get("isFilingLegalReview"), -1)));
        }
        
        // 是否重大案件
        if(!TeeUtility.isNullorEmpty(requestData.get("isMajorCase")) && TeeStringUtil.getInteger(requestData.get("isMajorCase"), -1) != -1){
            crit.add(Restrictions.eq("cBase.isMajorCase", TeeStringUtil.getInteger(requestData.get("isMajorCase"), -1)));
        }
        
        
        /*if((!TeeUtility.isNullorEmpty(requestData.get("officeName")) && !TeeUtility.isNullorEmpty(requestData.get("officeName").toString()))
                || (!TeeUtility.isNullorEmpty(requestData.get("cardCode")) && !TeeUtility.isNullorEmpty(requestData.get("cardCode").toString()))){
            //第一层2exists 表tbl_case_common_staff 取别名为 staff
            DetachedCriteria staffCrit = DetachedCriteria.forClass(CaseCommonStaff.class,"staff");
            //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
            staffCrit.add(Property.forName("cBase.id").eqProperty(Property.forName("staff.caseCommonStaff.id")));
            // 执法人员
            if(!TeeUtility.isNullorEmpty(requestData.get("officeName")) && !TeeUtility.isNullorEmpty(requestData.get("officeName").toString())){
                staffCrit.add(Restrictions.like("staff.officeName", requestData.get("officeName").toString(), MatchMode.ANYWHERE));
            }
            
            //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
            crit.add(Subqueries.exists(staffCrit.setProjection(Projections.property("staff.caseCommonStaff.id"))));
        }*/
        // 执法人员&执法证号
        if((!TeeUtility.isNullorEmpty(requestData.get("officeName")) && !TeeUtility.isNullorEmpty(requestData.get("officeName").toString()))
                || (!TeeUtility.isNullorEmpty(requestData.get("cardCode")) && !TeeUtility.isNullorEmpty(requestData.get("cardCode").toString()))){
            crit.createAlias("caseCommonStaffs", "staff");
            if(!TeeUtility.isNullorEmpty(requestData.get("officeName")) && !TeeUtility.isNullorEmpty(requestData.get("officeName").toString())){
                crit.add(Restrictions.like("staff.officeName", requestData.get("officeName").toString().trim(), MatchMode.ANYWHERE));
            }
            if(!TeeUtility.isNullorEmpty(requestData.get("cardCode")) && !TeeUtility.isNullorEmpty(requestData.get("cardCode").toString())){
                crit.add(Restrictions.like("staff.cardCode", requestData.get("cardCode").toString().trim(), MatchMode.ANYWHERE));
            }
        }
        // 职权名称&职权编号
        if((!TeeUtility.isNullorEmpty(requestData.get("powerName")) && !TeeUtility.isNullorEmpty(requestData.get("powerName").toString()))
                || (!TeeUtility.isNullorEmpty(requestData.get("powerCode")) && !TeeUtility.isNullorEmpty(requestData.get("powerCode").toString()))){
            crit.createAlias("caseCommonPowers", "power");
            if(!TeeUtility.isNullorEmpty(requestData.get("powerName")) && !TeeUtility.isNullorEmpty(requestData.get("powerName").toString())){
                crit.add(Restrictions.like("power.powerName", requestData.get("powerName").toString().trim(), MatchMode.ANYWHERE));
            }
            if(!TeeUtility.isNullorEmpty(requestData.get("powerCode")) && !TeeUtility.isNullorEmpty(requestData.get("powerCode").toString())){
                crit.add(Restrictions.like("power.powerCode", requestData.get("powerCode").toString().trim(), MatchMode.ANYWHERE));
            }
        }
        String punishState = requestData.get("punishState").toString();
        if("1".equals(punishState)){
            if(!TeeUtility.isNullorEmpty(requestData.get("beginpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishDate").toString())){
                crit.add(Restrictions.ge("cBase.punishmentDate", requestData.get("beginpunishDate")));
            }
            if(!TeeUtility.isNullorEmpty(requestData.get("endpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishDate").toString())){
                crit.add(Restrictions.le("cBase.punishmentDate", requestData.get("endpunishDate")));
            }
            String punishExecutState = requestData.get("punishExecutState").toString();
            if("1".equals(punishExecutState)){
                if(!TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate").toString())){
                    crit.add(Restrictions.ge("cBase.punishDecisionExecutDate", requestData.get("beginpunishExecutDate")));
                }
                if(!TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate").toString())){
                    crit.add(Restrictions.le("cBase.punishDecisionExecutDate", requestData.get("endpunishExecutDate")));
                }
            }else if("2".equals(punishExecutState)){
                if((!TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate").toString()))
                        || (!TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate").toString()))){
                    crit.createAlias("caseCommonRevokePunishs", "revokepunish");
                    if(!TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate").toString())){
                        crit.add(Restrictions.ge("revokepunish.revokePunishmentDate", requestData.get("beginpunishExecutDate")));
                    }
                    if(!TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate").toString())){
                        crit.add(Restrictions.le("revokepunish.revokePunishmentDate", requestData.get("endpunishExecutDate")));
                    }
                }
            }else if("3".equals(punishExecutState)){
                if((!TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate").toString()))
                        || (!TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate").toString()))){
                    crit.createAlias("caseCommonEnds", "end");
                    if(!TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishExecutDate").toString())){
                        crit.add(Restrictions.ge("end.endDate", requestData.get("beginpunishExecutDate")));
                    }
                    if(!TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishExecutDate").toString())){
                        crit.add(Restrictions.le("end.endDate", requestData.get("endpunishExecutDate")));
                    }
                }
            }
        }else if("2".equals(punishState)){
            if((!TeeUtility.isNullorEmpty(requestData.get("beginpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishDate").toString()))
                || (!TeeUtility.isNullorEmpty(requestData.get("endpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishDate").toString()))){
                crit.createAlias("caseCommonNopunishments", "nopunish");
                if(!TeeUtility.isNullorEmpty(requestData.get("beginpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishDate").toString())){
                    crit.add(Restrictions.ge("nopunish.noPunishmentDate", requestData.get("beginpunishDate")));
                }
                if(!TeeUtility.isNullorEmpty(requestData.get("endpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishDate").toString())){
                    crit.add(Restrictions.le("nopunish.noPunishmentDate", requestData.get("endpunishDate")));
                }
            }
        }else if("3".equals(punishState)){
            if((!TeeUtility.isNullorEmpty(requestData.get("beginpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishDate").toString()))
                    || (!TeeUtility.isNullorEmpty(requestData.get("endpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishDate").toString()))){
                crit.createAlias("caseCommonRevokes", "revoke");
                if(!TeeUtility.isNullorEmpty(requestData.get("beginpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginpunishDate").toString())){
                    crit.add(Restrictions.ge("revoke.revokeRegisterDate", requestData.get("beginpunishDate")));
                }
                if(!TeeUtility.isNullorEmpty(requestData.get("endpunishDate")) && !TeeUtility.isNullorEmpty(requestData.get("endpunishDate").toString())){
                    crit.add(Restrictions.le("revoke.revokeRegisterDate", requestData.get("endpunishDate")));
                }
            }
        }
        
        //分页条件
        crit.setFirstResult(tModel.getFirstResult());
        crit.setMaxResults(tModel.getRows());
        //排序
        if(!TeeUtility.isNullorEmpty(tModel.getSort())){
            String[] sorts = tModel.getSort().split(",");
            String[] orders = tModel.getOrder().split(",");
            for (int i = 0; i < sorts.length; i++) {
                if("asc".equals(orders[i])){
                    crit.addOrder(Order.asc("cBase." + sorts[i]));
                }else{
                    crit.addOrder(Order.desc("cBase." + sorts[i]));
                }
            }
        }else{
            crit.addOrder(Order.desc("cBase.updateDate"));
        }
        return crit.list();
    }

    public Long listCount(Map<String, Object> requestData, OrgCtrlInfoModel orgCtrl) {
        // TODO Auto-generated method stub
      //获取session
        Session session = super.getSession();
        //主表tbl_case_common_base 取别名为 cBase
        Criteria crit = session.createCriteria(CaseCommonBase.class, "cBase");
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
        
        
        //关联条件--关联字段 cBase.SUBJECT_ID = sub.ID
        subCrit.add(Property.forName("cBase.subjectId").eqProperty(Property.forName("sub.id")));
        
        
        //exists中的select 1 from类似，select sub.id from， 必须设置，不设置查询会报语法错误
        crit.add(Subqueries.exists(subCrit.setProjection(Projections.property("sub.id"))));
        //机构类型，用来控制是查询本级及本级以下，还是查询本单位及本单位以下的数据（1监督部门，2执法部门，3执法主体）
        Integer orgType = orgCtrl.getOrgType();
        //是否是分级管理员（true是，false否）
        boolean gradeAdministrator = orgCtrl.getGradeAdministrator();
        //执法主体，执法部门，查询本单位，及本单位以下数据，监督部门查询本级，及本级以下的数据，分级管理员查询本级及本级以下的数据
        if (orgType != null && (2 == orgType || 3 == orgType)) {
            if (!gradeAdministrator) {
                //执法主体，查询本单位，及本单位以下数据
                subCrit.add(Restrictions.eq("orgSys", orgCtrl.getOrgSysCode()));
            }
        }
        //主体删除标志
        subCrit.add(Restrictions.eq("sub.isDelete", 0));
        // 行政区划
        if(!TeeUtility.isNullorEmpty(requestData.get("administrativeDivision")) && !TeeUtility.isNullorEmpty(requestData.get("administrativeDivision").toString())){
            subCrit.add(Restrictions.in("sub.area", requestData.get("administrativeDivision").toString().split(",")));
        }
        // 所属领域
        if(!TeeUtility.isNullorEmpty(requestData.get("orgSys")) && !TeeUtility.isNullorEmpty(requestData.get("orgSys").toString())){
            subCrit.add(Restrictions.in("sub.orgSys", requestData.get("orgSys").toString().split(",")));
        }
        
        //案件删除标志
        crit.add(Restrictions.eq("cBase.isDelete", 0));
        //Restrictions语法规则参考 WebContent/supervise/common/hibernate_Restrictions.txt
        // 入库日期起始日期
        if(!TeeUtility.isNullorEmpty(requestData.get("begincreateDate")) && !TeeUtility.isNullorEmpty(requestData.get("begincreateDate").toString())){
            crit.add(Restrictions.ge("cBase.createDate", requestData.get("begincreateDate")));
        }
        // 入库日期截至日期
        if(!TeeUtility.isNullorEmpty(requestData.get("endcreateDate")) && !TeeUtility.isNullorEmpty(requestData.get("endcreateDate").toString())){
            crit.add(Restrictions.le("cBase.createDate", requestData.get("endcreateDate")));
        }
        // 立案日期起始日期
        if(!TeeUtility.isNullorEmpty(requestData.get("beginfilingDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginfilingDate").toString())){
            crit.add(Restrictions.ge("cBase.filingDate", requestData.get("beginfilingDate")));
        }
        // 立案日期截至日期
        if(!TeeUtility.isNullorEmpty(requestData.get("endfilingDate")) && !TeeUtility.isNullorEmpty(requestData.get("endfilingDate").toString())){
            crit.add(Restrictions.le("cBase.filingDate", requestData.get("endfilingDate")));
        }
        // 结案日期起始日期
        if(!TeeUtility.isNullorEmpty(requestData.get("beginclosedDate")) && !TeeUtility.isNullorEmpty(requestData.get("beginclosedDate").toString())){
            crit.add(Restrictions.ge("cBase.closedDate", requestData.get("beginclosedDate")));
        }
        // 结案日期截至日期
        if(!TeeUtility.isNullorEmpty(requestData.get("endclosedDate")) && !TeeUtility.isNullorEmpty(requestData.get("endclosedDate").toString())){
            crit.add(Restrictions.le("cBase.closedDate", requestData.get("endclosedDate")));
        }
        // 当事人类型
        if(!TeeUtility.isNullorEmpty(requestData.get("partyType")) && !TeeUtility.isNullorEmpty(requestData.get("partyType").toString())){
            crit.add(Restrictions.in("cBase.partyType", requestData.get("partyType").toString().split(",")));
        }
        // 案件来源
        if(!TeeUtility.isNullorEmpty(requestData.get("caseSource")) && !TeeUtility.isNullorEmpty(requestData.get("caseSource").toString())){
            crit.add(Restrictions.in("cBase.caseSource", requestData.get("caseSource").toString().split(",")));
        }
        // 办理状态
        if(!TeeUtility.isNullorEmpty(requestData.get("currentState")) && !TeeUtility.isNullorEmpty(requestData.get("currentState").toString())){
            crit.add(Restrictions.in("cBase.currentState", requestData.get("currentState").toString().split(",")));
        }
        // 结案状态
        if(!TeeUtility.isNullorEmpty(requestData.get("closedState")) && !TeeUtility.isNullorEmpty(requestData.get("closedState").toString())){
            crit.add(Restrictions.in("cBase.closedState", requestData.get("closedState").toString().split(",")));
        }
        // 执法机关
        if(!TeeUtility.isNullorEmpty(requestData.get("departmentId")) && !TeeUtility.isNullorEmpty(requestData.get("departmentId").toString())){
            crit.add(Restrictions.in("cBase.departmentId", requestData.get("departmentId").toString().split(",")));
        }
        // 执法主体
        if(!TeeUtility.isNullorEmpty(requestData.get("subjectId")) && !TeeUtility.isNullorEmpty(requestData.get("subjectId").toString())){
            crit.add(Restrictions.in("cBase.subjectId", requestData.get("subjectId").toString().split(",")));
        }
        // 案件名称
        if(!TeeUtility.isNullorEmpty(requestData.get("name")) && !TeeUtility.isNullorEmpty(requestData.get("name").toString())){
            crit.add(Restrictions.like("cBase.name", requestData.get("name").toString(), MatchMode.ANYWHERE));
        }
        
        // 提交状态
        if(!TeeUtility.isNullorEmpty(requestData.get("isSubmit")) && TeeStringUtil.getInteger(requestData.get("isSubmit"), -1) != -1){
            crit.add(Restrictions.eq("cBase.isSubmit", TeeStringUtil.getInteger(requestData.get("isSubmit"), -1)));
        }
        
        // 是否法制审核
        if(!TeeUtility.isNullorEmpty(requestData.get("isFilingLegalReview")) && TeeStringUtil.getInteger(requestData.get("isFilingLegalReview"), -1) != -1){
            crit.add(Restrictions.eq("cBase.isFilingLegalReview", TeeStringUtil.getInteger(requestData.get("isFilingLegalReview"), -1)));
        }
        
        // 是否重大案件
        if(!TeeUtility.isNullorEmpty(requestData.get("isMajorCase")) && TeeStringUtil.getInteger(requestData.get("isMajorCase"), -1) != -1){
            crit.add(Restrictions.eq("cBase.isMajorCase", TeeStringUtil.getInteger(requestData.get("isMajorCase"), -1)));
        }
        
        // 执法人员
        /*if(!TeeUtility.isNullorEmpty(requestData.get("officeName")) && !TeeUtility.isNullorEmpty(requestData.get("officeName").toString())){
            crit.add(Restrictions.like("cBase.caseCommonStaffs.officeName", requestData.get("officeName").toString(), MatchMode.ANYWHERE));
        }*/
        
        
        crit.setProjection(Projections.rowCount());
        Integer totalCount = Integer.parseInt(crit.uniqueResult().toString());
        return totalCount.longValue();
    }
}
