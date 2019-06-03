package com.beidasoft.zfjd.caseManager.simpleCase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimpleBase;
import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimpleGist;
import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimplePower;
import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimplePunish;
import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimpleStaff;
import com.beidasoft.zfjd.caseManager.simpleCase.dao.CaseSimpleBaseDao;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimpleBaseModel;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimpleGistModel;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimplePowerModel;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimplePunishModel;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimpleStaffModel;
import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.dao.TblDepartmentDao;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.dao.SubjectDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class CaseSimpleBaseService extends TeeBaseService {

    @Autowired
    private CaseSimpleBaseDao baseDao;
    @Autowired
    private CaseSimpleStaffService staffService;
    @Autowired
    private CaseSimpleGistService gistService;
    @Autowired
    private CaseSimplePowerService powerService;
    @Autowired
    private CaseSimplePunishService punishService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private TblDepartmentDao departmentDao;
    @Autowired
    private SubjectDao subjectDao;

    /**
     ** 分页查询
     * 
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     */
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel, CaseSimpleBaseModel cbModel,
            HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        // 接收查询集合 cList
        List<CaseSimpleBase> cList = null;
        // bean对应的model cModels
        List<CaseSimpleBaseModel> cModels = new ArrayList<CaseSimpleBaseModel>();
        // 查询集合总数
        Long count = null;
        cList = baseDao.findListByPage(tModel.getFirstResult(), tModel.getRows(), cbModel);
        count = baseDao.listCount(cbModel);
        // 定义model
        CaseSimpleBaseModel cModel = null;
        if (cList != null && cList.size() > 0) {
            for (int i = 0; i < cList.size(); i++) {
                cModel = new CaseSimpleBaseModel();
                CaseSimpleBase tempBasic = cList.get(i);
                // 将tempBasic赋值给cModel
                BeanUtils.copyProperties(tempBasic, cModel);
                // 将处罚决定书日期转化为格式化的字符串
                if (tempBasic.getPunishmentDate() != null) {
                    cModel.setPunishmentDateStr(TeeDateUtil.format(tempBasic.getPunishmentDate(), "yyyy-MM-dd"));
                } else {
                    // 处罚决定书日期为空时处理
                    cModel.setPunishmentDateStr("");
                }
                // 将入库日期转化为格式化的字符串
                if (tempBasic.getCreateDate() != null) {
                    cModel.setCreateDateStr(TeeDateUtil.format(tempBasic.getCreateDate(), "yyyy-MM-dd"));
                } else {
                    // 处罚决定书日期为空时处理
                    cModel.setCreateDateStr("");
                }
                cModels.add(cModel);
            }
        }
        json.setRows(cModels);
        json.setTotal(count);
        return json;
    }

    /**
     ** 分页查询
     * 
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     */
    public TeeEasyuiDataGridJson findSearchListBypage(TeeDataGridModel tModel, CaseSimpleBaseModel cbModel,
            HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        // 接收查询集合 cList
        List<CaseSimpleBase> cList = null;
        // bean对应的model cModels
        List<CaseSimpleBaseModel> cModels = new ArrayList<CaseSimpleBaseModel>();
        // 查询集合总数
        Long count = null;
        // 权限控制类
        OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
        cList = baseDao.findListByPageSearch(tModel.getFirstResult(), tModel.getRows(), cbModel, orgCtrl);
        count = baseDao.listSearchCount(cbModel, orgCtrl);
        // 定义model
        CaseSimpleBaseModel cModel = null;
        if (cList != null && cList.size() > 0) {
            for (int i = 0; i < cList.size(); i++) {
                cModel = new CaseSimpleBaseModel();
                CaseSimpleBase tempBasic = cList.get(i);
                // 将tempBasic赋值给cModel
                BeanUtils.copyProperties(tempBasic, cModel);
                // 将处罚决定书日期转化为格式化的字符串
                if (tempBasic.getPunishmentDate() != null) {
                    cModel.setPunishmentDateStr(TeeDateUtil.format(tempBasic.getPunishmentDate(), "yyyy-MM-dd"));
                } else {
                    // 处罚决定书日期为空时处理
                    cModel.setPunishmentDateStr("");
                }
                // 将入库日期转化为格式化的字符串
                if (tempBasic.getCreateDate() != null) {
                    cModel.setCreateDateStr(TeeDateUtil.format(tempBasic.getCreateDate(), "yyyy-MM-dd"));
                } else {
                    // 处罚决定书日期为空时处理
                    cModel.setCreateDateStr("");
                }
                cModels.add(cModel);
            }
        }
        json.setRows(cModels);
        json.setTotal(count);
        return json;
    }

    /**
     ** 保存
     * 
     * @param request
     * @param baseModel
     * @return
     */
    public TeeJson saveSimpleBase(HttpServletRequest request, CaseSimpleBaseModel baseModel) {
        TeeJson json = new TeeJson();
        CaseSimpleBase simpleBase = new CaseSimpleBase();
        BeanUtils.copyProperties(baseModel, simpleBase);
        simpleBase.setIsDelete(0);
        simpleBase.setDataSource(0);
        simpleBase.setCreateDate(new Date());
        simpleBase.setUpdateDate(new Date());
        // 处理日期类型
        // 执法日期
        if (!TeeUtility.isNullorEmpty(baseModel.getEnforcementDateStr())) {
            simpleBase.setEnforcementDate(TeeDateUtil.format(baseModel.getEnforcementDateStr(), "yyyy-MM-dd"));
        }
        // 登记日期
        if (!TeeUtility.isNullorEmpty(baseModel.getRegistrantDateStr())) {
            simpleBase.setRegistrantDate(TeeDateUtil.format(baseModel.getRegistrantDateStr(), "yyyy-MM-dd"));
        }
        // 行政处罚决定书日期
        if (!TeeUtility.isNullorEmpty(baseModel.getPunishmentDateStr())) {
            simpleBase.setPunishmentDate(TeeDateUtil.format(baseModel.getPunishmentDateStr(), "yyyy-MM-dd"));
        }
        // 复议日期
        if (!TeeUtility.isNullorEmpty(baseModel.getReconsiderationDateStr())) {
            simpleBase.setReconsiderationDate(TeeDateUtil.format(baseModel.getReconsiderationDateStr(), "yyyy-MM-dd"));
        }
        // 诉讼日期
        if (!TeeUtility.isNullorEmpty(baseModel.getLawsuitDateStr())) {
            simpleBase.setLawsuitDate(TeeDateUtil.format(baseModel.getLawsuitDateStr(), "yyyy-MM-dd"));
        }
        // 结案日期
        if (!TeeUtility.isNullorEmpty(baseModel.getClosedDateStr())) {
            simpleBase.setClosedDate(TeeDateUtil.format(baseModel.getClosedDateStr(), "yyyy-MM-dd"));
        }
        // 立案日期
        if (!TeeUtility.isNullorEmpty(baseModel.getFilingDateStr())) {
            simpleBase.setFilingDate(TeeDateUtil.format(baseModel.getFilingDateStr(), "yyyy-MM-dd"));
        }
        // 处罚决定执行日期
        if (!TeeUtility.isNullorEmpty(baseModel.getPunishDecisionExecutDateStr())) {
            simpleBase.setPunishDecisionExecutDate(
                    TeeDateUtil.format(baseModel.getPunishDecisionExecutDateStr(), "yyyy-MM-dd"));
        }
        simpleBase.setCreateDate(new Date());
        // 保存执法人员子表
        if (!TeeUtility.isNullorEmpty(baseModel.getStaffIds())) {
            CaseSimpleStaffModel staffModel = new CaseSimpleStaffModel();
            staffModel.setIds(baseModel.getStaffIds());
            staffModel.setCaseId(baseModel.getId());
            staffService.saveSimpleStaff(staffModel);
        }
        // 保存违法行为子表
        if (!TeeUtility.isNullorEmpty(baseModel.getPowerIds())) {
            CaseSimplePowerModel powerModel = new CaseSimplePowerModel();
            powerModel.setIds(baseModel.getPowerIds());
            powerModel.setCaseId(baseModel.getId());
            powerService.saveSimplePower(powerModel);
        }
        // 保存违法依据子表
        if (!TeeUtility.isNullorEmpty(baseModel.getGistIds())) {
            CaseSimpleGistModel gistModel = new CaseSimpleGistModel();
            gistModel.setIds(baseModel.getGistIds());
            gistModel.setCaseId(baseModel.getId());
            gistService.saveSimpleGist(gistModel);
        }
        // 保存处罚依据子表
        if (!TeeUtility.isNullorEmpty(baseModel.getPunishIds())) {
            CaseSimplePunishModel punishModel = new CaseSimplePunishModel();
            punishModel.setIds(baseModel.getPunishIds());
            punishModel.setCaseId(baseModel.getId());
            punishService.saveSimplePunish(punishModel);
        }
        // 保存主表
        baseDao.save(simpleBase);
        json.setRtState(true);
        return json;
    }

    /**
     ** 修改
     * 
     * @param request
     * @param baseModel
     * @return
     */
    public TeeJson updateSimpleBase(HttpServletRequest request, CaseSimpleBaseModel baseModel) {
        TeeJson json = new TeeJson();
        CaseSimpleBase simpleBase = baseDao.get(baseModel.getId());
        baseModel.setCreateDateStr(TeeDateUtil.format(simpleBase.getCreateDate(), "yyyy-MM-dd"));
        baseModel.setIsDelete(0);
        baseModel.setDataSource(1);
        BeanUtils.copyProperties(baseModel, simpleBase);
        // 处理日期类型
        // 创建时间
        if (!TeeUtility.isNullorEmpty(baseModel.getCreateDateStr())) {
            simpleBase.setCreateDate(TeeDateUtil.format(baseModel.getCreateDateStr(), "yyyy-MM-dd"));
        }
        // 执法日期
        if (!TeeUtility.isNullorEmpty(baseModel.getEnforcementDateStr())) {
            simpleBase.setEnforcementDate(TeeDateUtil.format(baseModel.getEnforcementDateStr(), "yyyy-MM-dd"));
        }
        // 登记日期
        if (!TeeUtility.isNullorEmpty(baseModel.getRegistrantDateStr())) {
            simpleBase.setRegistrantDate(TeeDateUtil.format(baseModel.getRegistrantDateStr(), "yyyy-MM-dd"));
        }
        // 行政处罚决定书日期
        if (!TeeUtility.isNullorEmpty(baseModel.getPunishmentDateStr())) {
            simpleBase.setPunishmentDate(TeeDateUtil.format(baseModel.getPunishmentDateStr(), "yyyy-MM-dd"));
        }
        // 复议日期
        if (!TeeUtility.isNullorEmpty(baseModel.getReconsiderationDateStr())) {
            simpleBase.setReconsiderationDate(TeeDateUtil.format(baseModel.getReconsiderationDateStr(), "yyyy-MM-dd"));
        }
        // 诉讼日期
        if (!TeeUtility.isNullorEmpty(baseModel.getLawsuitDateStr())) {
            simpleBase.setLawsuitDate(TeeDateUtil.format(baseModel.getLawsuitDateStr(), "yyyy-MM-dd"));
        }
        // 结案日期
        if (!TeeUtility.isNullorEmpty(baseModel.getClosedDateStr())) {
            simpleBase.setClosedDate(TeeDateUtil.format(baseModel.getClosedDateStr(), "yyyy-MM-dd"));
        }
        // 立案日期
        if (!TeeUtility.isNullorEmpty(baseModel.getFilingDateStr())) {
            simpleBase.setFilingDate(TeeDateUtil.format(baseModel.getFilingDateStr(), "yyyy-MM-dd"));
        }
        // 处罚决定执行日期
        if (!TeeUtility.isNullorEmpty(baseModel.getPunishDecisionExecutDateStr())) {
            simpleBase.setPunishDecisionExecutDate(
                    TeeDateUtil.format(baseModel.getPunishDecisionExecutDateStr(), "yyyy-MM-dd"));
        }
        simpleBase.setUpdateDate(new Date());
        // 修改执法人员子表
        staffService.deleteByCaseId(baseModel.getId());// 删除自表中相关数据重新添加
        if (!TeeUtility.isNullorEmpty(baseModel.getStaffIds())) {
            CaseSimpleStaffModel staffModel = new CaseSimpleStaffModel();
            staffModel.setIds(baseModel.getStaffIds());
            staffModel.setCaseId(baseModel.getId());
            staffService.saveSimpleStaff(staffModel);
        }

        // 修改违法行为子表
        if (!TeeUtility.isNullorEmpty(baseModel.getPowerIds())) {
            powerService.deleteByCaseId(baseModel.getId());// 删除自表中相关数据重新添加
            CaseSimplePowerModel powerModel = new CaseSimplePowerModel();
            powerModel.setIds(baseModel.getPowerIds());
            powerModel.setCaseId(baseModel.getId());
            powerService.saveSimplePower(powerModel);
        }
        // 修改违法依据子表
        if (!TeeUtility.isNullorEmpty(baseModel.getGistIds())) {
            gistService.deleteByCaseId(baseModel.getId());// 删除自表中相关数据重新添加
            CaseSimpleGistModel gistModel = new CaseSimpleGistModel();
            gistModel.setIds(baseModel.getGistIds());
            gistModel.setCaseId(baseModel.getId());
            gistService.saveSimpleGist(gistModel);
        }
        // 修改处罚依据子表
        if (!TeeUtility.isNullorEmpty(baseModel.getPunishIds())) {
            punishService.deleteByCaseId(baseModel.getId());// 删除自表中相关数据重新添加
            CaseSimplePunishModel punishModel = new CaseSimplePunishModel();
            punishModel.setIds(baseModel.getPunishIds());
            punishModel.setCaseId(baseModel.getId());
            punishService.saveSimplePunish(punishModel);
        }
        // 保存主表
        baseDao.update(simpleBase);
        json.setRtState(true);
        return json;
    }

    /**
     ** 逻辑删除
     * 
     * @return
     */
    public TeeJson updateDelete(String id) {
        TeeJson json = new TeeJson();
        CaseSimpleBase simpleBase = baseDao.get(id);
        if (!TeeUtility.isNullorEmpty(simpleBase)) {
            simpleBase.setIsDelete(1);
            simpleBase.setDeleteDate(new Date());
        }
        baseDao.update(simpleBase);
        json.setRtState(true);
        return json;
    }

    /**
     ** 根据id查询
     * 
     * @param id
     * @return
     */
    public CaseSimpleBase getById(String id) {
        return baseDao.get(id);
    }

    /**
     ** 回写数据
     * 
     * @param request
     * @return TeeJson
     */
    public TeeJson findSimpleBaseById(HttpServletRequest request, String id) {
        TeeJson json = new TeeJson();
        CaseSimpleBaseModel simpleBaseModel = new CaseSimpleBaseModel();
        if ("undefined".equals(id)) {
            json.setRtData(simpleBaseModel);
            json.setRtState(true);
            return json;
        }
        CaseSimpleBase simpleBase = getById(id);// 根据id查询base表
        BeanUtils.copyProperties(simpleBase, simpleBaseModel);
        // 处理日期类型
        // 执法日期
        if (!TeeUtility.isNullorEmpty(simpleBase.getEnforcementDate())) {
            simpleBaseModel.setEnforcementDateStr(TeeDateUtil.format(simpleBase.getEnforcementDate(), "yyyy-MM-dd"));
        }
        // 登记日期
        if (!TeeUtility.isNullorEmpty(simpleBase.getRegistrantDate())) {
            simpleBaseModel.setRegistrantDateStr(TeeDateUtil.format(simpleBase.getRegistrantDate(), "yyyy-MM-dd"));
        }
        // 行政处罚决定书日期
        if (!TeeUtility.isNullorEmpty(simpleBase.getPunishmentDate())) {
            simpleBaseModel.setPunishmentDateStr(TeeDateUtil.format(simpleBase.getPunishmentDate(), "yyyy-MM-dd"));
        }
        // 复议日期
        if (!TeeUtility.isNullorEmpty(simpleBase.getReconsiderationDate())) {
            simpleBaseModel
                    .setReconsiderationDateStr(TeeDateUtil.format(simpleBase.getReconsiderationDate(), "yyyy-MM-dd"));
        }
        // 诉讼日期
        if (!TeeUtility.isNullorEmpty(simpleBase.getLawsuitDate())) {
            simpleBaseModel.setLawsuitDateStr(TeeDateUtil.format(simpleBase.getLawsuitDate(), "yyyy-MM-dd"));
        }
        // 结案日期
        if (!TeeUtility.isNullorEmpty(simpleBase.getClosedDate())) {
            simpleBaseModel.setClosedDateStr(TeeDateUtil.format(simpleBase.getClosedDate(), "yyyy-MM-dd"));
        }
        // 立案日期
        if (!TeeUtility.isNullorEmpty(simpleBase.getFilingDate())) {
            simpleBaseModel.setFilingDateStr(TeeDateUtil.format(simpleBase.getFilingDate(), "yyyy-MM-dd"));
        }
        // 处罚决定执行日期
        if (!TeeUtility.isNullorEmpty(simpleBase.getPunishDecisionExecutDate())) {
            simpleBaseModel.setPunishDecisionExecutDateStr(
                    TeeDateUtil.format(simpleBase.getPunishDecisionExecutDate(), "yyyy-MM-dd"));
        }
        // 查询执法人员
        List<CaseSimpleStaff> staffList = staffService.getListByCaseId(id);
        String staffIds = "";
        if (!staffList.isEmpty()) {
            for (CaseSimpleStaff staff : staffList) {
                staffIds += staff.getIdentityId() + ",";
            }
            staffIds = staffIds.substring(0, staffIds.length() - 1);
        }
        simpleBaseModel.setStaffIds(staffIds);
        // 查询违法行为
        List<CaseSimplePower> powerList = powerService.getListByCaseId(id);
        String powerIds = "";
        if (!powerList.isEmpty()) {
            for (CaseSimplePower power : powerList) {
                powerIds += power.getPowerId() + ",";
            }
            powerIds = powerIds.substring(0, powerIds.length() - 1);
        }
        simpleBaseModel.setPowerIds(powerIds);
        // 查询违法依据
        List<CaseSimpleGist> gistList = gistService.getListByCaseId(id);
        String gistIds = "";
        if (!gistList.isEmpty()) {
            for (CaseSimpleGist gist : gistList) {
                gistIds += gist.getGistId() + ",";
            }
            gistIds = gistIds.substring(0, gistIds.length() - 1);
        }
        simpleBaseModel.setGistIds(gistIds);
        // 查询处罚依据
        List<CaseSimplePunish> punishList = punishService.getListByCaseId(id);
        String punishIds = "";
        if (!punishList.isEmpty()) {
            for (CaseSimplePunish punish : punishList) {
                punishIds += punish.getGistId() + ",";
            }
            punishIds = punishIds.substring(0, punishIds.length() - 1);
        }
        // 获取执法主体所属部门
        if(simpleBase.getDepartmentId() != null){
            TblDepartmentInfo departmentInfo = departmentDao.get(simpleBase.getDepartmentId());
            simpleBaseModel.setDepartmentName("");
            if(departmentInfo != null)
                simpleBaseModel.setDepartmentName(departmentInfo.getName());
        }
        // 获取执法主体名称
        if(simpleBase.getSubjectId() != null){
            Subject subject = subjectDao.get(simpleBase.getSubjectId());
            simpleBaseModel.setSubjectName("");
            if(subject != null)
                simpleBaseModel.setSubjectName(subject.getSubName());
        }
        simpleBaseModel.setPunishIds(punishIds);
        json.setRtData(simpleBaseModel);
        json.setRtState(true);
        return json;
    }

    /**
     ** 修改提交状态
     * 
     * @return
     */
    public TeeJson simpleBaseSubmit(String baseIds) {
        TeeJson json = new TeeJson();
        // 查分字符串
        String[] ids = baseIds.split(",");
        for (String id : ids) {
            CaseSimpleBase simpleBase = baseDao.get(id);
            // 将提交状态改为1
            simpleBase.setIsSubmit(1);
            // 更新数据库
            baseDao.update(simpleBase);
        }
        json.setRtState(true);
        return json;
    }

    /**
     * 
     * @Function: updateOrdeleteOrSubmitByIds()
     * @Description: 提交，退回，删除方法
     *
     * @param:描述1描述
     * @return：返回结果描述
     * @throws：异常描述
     *
     * @author: songff
     * @date: 2019年1月28日 下午4:22:43
     *
     */
    public void updateOrdeleteOrSubmitByIds(CaseSimpleBaseModel cbModel) {
        Map<String, Object> columns = new HashMap<String, Object>();
        Date data = new Date();
        if (!TeeUtility.isNullorEmpty(cbModel.getIsSubmit())) {
            columns.put("IS_SUBMIT", cbModel.getIsSubmit()); // 提交 或 退回 状态
            columns.put("SUBMIT_DATE", data); // 提交，退回时间
        }
        if (!TeeUtility.isNullorEmpty(cbModel.getIsDelete())) {
            columns.put("IS_DELETE", cbModel.getIsDelete()); // 删除状态
            columns.put("DELETE_DATE", data); // 删除时间
        }
        columns.put("UPDATE_DATE", data); // 更新时间
        baseDao.updateOrDeleteOrSubmitByIds(columns, cbModel.getId());
    }

}
