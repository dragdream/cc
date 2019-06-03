package com.beidasoft.zfjd.power.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.law.bean.TblLawDetail;
import com.beidasoft.zfjd.law.model.TblLawDetailModel;
import com.beidasoft.zfjd.law.service.TblLawDetailService;
import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.bean.PowerAdjust;
import com.beidasoft.zfjd.power.bean.PowerAdjustAuthority;
import com.beidasoft.zfjd.power.bean.PowerAdjustTache;
import com.beidasoft.zfjd.power.bean.PowerDetailTemp;
import com.beidasoft.zfjd.power.bean.PowerFlowsheetTemp;
import com.beidasoft.zfjd.power.bean.PowerGistTemp;
import com.beidasoft.zfjd.power.bean.PowerLevelTemp;
import com.beidasoft.zfjd.power.bean.PowerOperation;
import com.beidasoft.zfjd.power.bean.PowerSubjectTemp;
import com.beidasoft.zfjd.power.bean.PowerTemp;
import com.beidasoft.zfjd.power.model.PowerAdjustModel;
import com.beidasoft.zfjd.power.model.PowerGistTempModel;
import com.beidasoft.zfjd.power.model.PowerOperationModel;
import com.beidasoft.zfjd.power.model.PowerSubjectTempModel;
import com.beidasoft.zfjd.power.model.PowerTempModel;
import com.beidasoft.zfjd.power.service.PowerAdjustAuthorityService;
import com.beidasoft.zfjd.power.service.PowerGistTempService;
import com.beidasoft.zfjd.power.service.PowerSubjectTempService;
import com.beidasoft.zfjd.power.service.PowerTempService;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.service.SubjectService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/powerTempCtrl")
public class PowerTempController {
    @Autowired
    private PowerTempService powerTempService;
    
    @Autowired
    private TblLawDetailService lawDetailService;
    
    @Autowired
    private PowerGistTempService powerGistService;
    
    @Autowired
    private TeeDeptService deptService;
    
    @Autowired
    private PowerSubjectTempService subjectTempService;
    
    @Autowired
    private TeePersonService personService;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private PowerAdjustAuthorityService adjustAuthorityService;
    
    @Autowired
    private CommonService commonService;
    
    @ResponseBody
    @RequestMapping("/listByPage.action")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dm, PowerTempModel powerTempModel, HttpServletRequest request) throws Exception{
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        try {
            // 通过request获取当前部门
            OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
            if(orgCtrl.getOrgType() == 3) {
                if(orgCtrl.getSubjectId() != null && !"".equals(orgCtrl.getSubjectId())) {
                    powerTempModel.setSubjectId(orgCtrl.getSubjectId());
                }
            } else if(orgCtrl.getOrgType() == 2) {
                if(orgCtrl.getDepartId() != null && !"".equals(orgCtrl.getDepartId())) {
                    powerTempModel.setDepartmentId(orgCtrl.getDepartId());
                }
            } else if(orgCtrl.getOrgType() == 1) {
                if(orgCtrl.getDepartId() != null && !"".equals(orgCtrl.getDepartId())) {
                    powerTempModel.setDepartmentId(orgCtrl.getDepartId());
                }
            }
            
            List<PowerTemp> powerTemps = powerTempService.listByPage(dm, powerTempModel);
            Long total = powerTempService.listCount(powerTempModel);
            
            List<PowerTempModel> powerModels = new ArrayList<PowerTempModel>();
            List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
            List<Map<String, Object>> operationTypeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_OPERATION");
            List<Map<String, Object>> stateList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_STATE");

            
            PowerTempModel rtModel = null;
            for(int i = 0; i < powerTemps.size(); i++) {
                rtModel = new PowerTempModel();
                PowerTemp temp = powerTemps.get(i);
                
                BeanUtils.copyProperties(temp, rtModel);
                rtModel.setCreateDateStr(TeeDateUtil.format(temp.getCreateDate(), "yyyy-MM-dd"));
                rtModel.setDeleteDateStr(TeeDateUtil.format(temp.getDeleteDate(), "yyyy-MM-dd"));
                rtModel.setRevokeDateStr(TeeDateUtil.format(temp.getRevokeDate(), "yyyy-MM-dd"));
                
                for(Map<String, Object> code : codeList) {
                    if(rtModel.getPowerType().equals(code.get("codeNo").toString())) {
                        rtModel.setPowerType(code.get("codeName").toString());
                        break;
                    }
                }
                
                String powerDetailTemp = "";
                for(int j = 0; j < temp.getDetails().size(); j++) {
                    powerDetailTemp = powerDetailTemp + temp.getDetails().get(j).getName() + ",";
                }
                if(powerDetailTemp.length() > 0) {
                    rtModel.setPowerDetail(powerDetailTemp.substring(0, powerDetailTemp.length() - 1));
                }
                
                if(temp.getOperationTemps() != null && temp.getOperationTemps().size() > 0) {
                    for(int j = 0; j < temp.getOperationTemps().size(); j++) {
                        String optType = temp.getOperationTemps().get(j).getOptType();
                        for(Map<String, Object> code : operationTypeList) {
                            if(optType.equals(code.get("codeNo").toString())) {
                                rtModel.setOperationType(code.get("codeName").toString());
                                break;
                            }
                        }
                    }
                }
                if(temp.getCurrentState() != null && !"".equals(temp.getCurrentState())) {
                    for(Map<String, Object> code : stateList) {
                        if(temp.getCurrentState().equals(code.get("codeNo").toString())) {
                            rtModel.setCurrentStateValue(code.get("codeName").toString());
                            break;
                        }
                    }
                }
                
                powerModels.add(rtModel);
            }
            
            gridJson.setRows(powerModels);
            gridJson.setTotal(total);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return gridJson;
    }
    
    @RequestMapping("/powerInput")
    public void powerInput(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            if(request.getParameter("id") == null || "".equals(request.getParameter("id"))) {
                String id = UUID.randomUUID().toString();
                request.setAttribute("id", id);
            } else {
                request.setAttribute("id", request.getParameter("id"));
            }
            request.setAttribute("type", request.getParameter("type"));
            request.setAttribute("isNext", TeeStringUtil.getInteger(request.getParameter("isNext"), 0));
            request.setAttribute("editFlag", TeeStringUtil.getInteger(request.getParameter("editFlag"), 0));
            request.setAttribute("powerFormalId", TeeStringUtil.getString(request.getParameter("powerFormalId"), ""));
            request.getRequestDispatcher("/supervise/power/power_input.jsp").forward(request, response);
            
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    
    @ResponseBody
    @RequestMapping("/getById")
    public TeeJson getById(PowerTempModel powerTempModel, HttpServletRequest request) throws Exception {
        TeeJson result = new TeeJson();
        try {
            // 通过ID获取职权信息
            PowerTemp powerTemp = powerTempService.get(powerTempModel.getId());
            if(powerTemp != null) {
                // 将职权bean转换为model
                PowerTempModel reModel = changeBeanToModel(powerTemp);
                result.setRtState(true);
                result.setRtData(reModel);
            } else {
                result.setRtState(false);
            }
            
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    /**
     * 
    * @Function: PowerController.java
    * @Description: power bean转换为 model，提供流程中获取业务数据方法
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年1月8日 上午9:56:00 
    *
     */
    private PowerTempModel changeBeanToModel(PowerTemp powerTemp) {
        PowerTempModel powerTempModel = new PowerTempModel();
        
        BeanUtils.copyProperties(powerTemp, powerTempModel);
        
        powerTempModel.setCreateDateStr(TeeDateUtil.format(powerTemp.getCreateDate(), "yyyy-MM-dd"));
        powerTempModel.setUpdateDateStr(TeeDateUtil.format(powerTemp.getUpdateDate(), "yyyy-MM-dd"));
        
        // 转换职权流程图类型、职权层级、职权分类型
        String powerDetail = "";
        String flowsheetType = "";
        String powerLevel = "";
        
        if(powerTemp.getDetails() != null && powerTemp.getDetails().size() != 0) {
            for(int i = 0; i < powerTemp.getDetails().size(); i++) {
                powerDetail = powerDetail + powerTemp.getDetails().get(i).getCode() + ",";
            }
            
            powerTempModel.setPowerDetail(powerDetail.substring(0, powerDetail.length() - 1));
        }
        
        JSONArray flowsheetArray = new JSONArray();
        JSONObject object = null;
        if(powerTemp.getFlows() != null && powerTemp.getFlows().size() != 0) {
            for(int i = 0; i < powerTemp.getFlows().size(); i++) {
                object = new JSONObject();
                powerTemp.getFlows().get(i).setPowerFlowsheetTemp(null);
                object.put("fileType", powerTemp.getFlows().get(i).getFlowsheetType());
                object.put("fileId", powerTemp.getFlows().get(i).getFileId());
                
                flowsheetType = flowsheetType + powerTemp.getFlows().get(i).getFlowsheetType() + ",";
                flowsheetArray.add(object);
            }
            powerTempModel.setFlowPictureType(flowsheetType.substring(0, flowsheetType.length() - 1));
        }
        powerTempModel.setFlowsheetArray(flowsheetArray);
        
        JSONArray levelArray = new JSONArray();
        if(powerTemp.getLevels() != null && powerTemp.getLevels().size() != 0) {
            for(int i = 0; i < powerTemp.getLevels().size(); i++) {
                powerLevel = powerLevel + powerTemp.getLevels().get(i).getPowerStage() + ",";
                
                object = new JSONObject();
                object.put("powerStage", powerTemp.getLevels().get(i).getPowerStage());
                object.put("remark", powerTemp.getLevels().get(i).getRemark());
                
                levelArray.add(object);
            }
            
            powerTempModel.setPowerLevel(powerLevel.substring(0, powerLevel.length() - 1));
        }
        powerTempModel.setLevelArray(levelArray);
        
        // 将职权依据转换为ids字符串
        String gistIds = "";
        String punishIds = "";
        String settingIds = "";
        if(powerTemp.getGists() != null && powerTemp.getGists().size() != 0) {
            for(int i = 0; i < powerTemp.getGists().size(); i++) {
                PowerGistTemp gist = powerTemp.getGists().get(i);
                
                if(gist.getGistType() != null && !"".equals(gist.getGistType())) {
                    switch(gist.getGistType()) {
                        case "01":
                            gistIds = gistIds + gist.getLawDetailId() + ",";
                            break;
                        case "02":
                            punishIds = punishIds + gist.getLawDetailId() + ",";
                            break;
                        case "03":
                            settingIds = settingIds + gist.getLawDetailId() + ",";
                            break;
                        default:
                            break;
                    }
                    
                }
            }
            
            if(!"".equals(gistIds)) {
                powerTempModel.setGistIds(gistIds.substring(0, gistIds.length() - 1));
            } else {
                powerTempModel.setGistIds("");
            }
            if(!"".equals(punishIds)) {
                powerTempModel.setPunishIds(punishIds.substring(0, punishIds.length() - 1));
            } else {
                powerTempModel.setPunishIds("");
            }
            if(!"".equals(settingIds)) {
                powerTempModel.setSettingIds(settingIds.substring(0, settingIds.length() - 1));
            } else {
                powerTempModel.setSettingIds("");
            }
        }
        

        String subjectIds = "";
        if(powerTemp.getSubjects() != null  && powerTemp.getSubjects().size() != 0) {
            for(int i = 0; i < powerTemp.getSubjects().size(); i++) {
                PowerSubjectTemp subject = powerTemp.getSubjects().get(i);
                
                subjectIds = subjectIds + subject.getSubject().getId() + ",";
            }
            
            if(!"".equals(subjectIds)) {
                powerTempModel.setSubjectIds(subjectIds.substring(0, subjectIds.length() - 1));
            } else {
                powerTempModel.setSubjectIds("");
            }
        }
        
        return powerTempModel;
    }
    
    @ResponseBody
    @RequestMapping("/save.action")
    public TeeJson save(PowerTempModel powerTempModel, HttpServletRequest request) throws Exception{
        TeeJson result = new TeeJson();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
            
            Integer editFlag = TeeStringUtil.getInteger(request.getParameter("editFlag"), 0);
            
            JSONObject resultObj = new JSONObject();
            
            PowerTemp powerTemp = new PowerTemp();
            BeanUtils.copyProperties(powerTempModel, powerTemp);
            
            if(powerTemp.getId() == null || "".equals(powerTemp.getId())) {
                powerTemp.setId(UUID.randomUUID().toString());
            }
            resultObj.put("powerTempId", powerTemp.getId());
            
            powerTemp.setDepartmentId(department.getRelations().get(0).getBusinessDeptId());
            
            List<PowerOperation> operations = new ArrayList<PowerOperation>();
            if(powerTempModel.getOperationArray() != null && !"".equals(powerTempModel.getOperationArray())) {
                JSONArray optArray = JSONArray.fromObject(powerTempModel.getOperationArray());
                if(optArray != null && optArray.size() > 0) {
                    PowerOperation operation = null;
                    for(int i = 0; i < optArray.size(); i++) {
                        operation = new PowerOperation();
                        JSONObject optObj = optArray.getJSONObject(i);
                        operation.setId(UUID.randomUUID().toString());
                        Power powerOpt = new Power();
                        powerOpt.setId(optObj.getString("powerFormalId"));
                        operation.setPowerOpt(powerOpt);
                        operation.setPowerOptTemp(powerTemp);
                        operation.setOptType(optObj.getString("optType"));
                        
                        operations.add(operation);
                    }
                    resultObj.put("powerFormalId", operations.get(0).getPowerOpt().getId());
                }
            }
            powerTemp.setOperationTemps(operations);
            
            // 将职权分类型转换为list
            List<Map<String, Object>> detailList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_DETAIL");
            List<PowerDetailTemp> powerDetailTemps = new ArrayList<PowerDetailTemp>();
            PowerDetailTemp powerDetailTemp = null;
            String[] details = powerTempModel.getPowerDetail().split(",");
            for(int i = 0; i < details.length; i++) {
                powerDetailTemp = new PowerDetailTemp();
                powerDetailTemp.setId(UUID.randomUUID().toString());
                powerDetailTemp.setPowerDetailTemp(powerTemp);
                powerDetailTemp.setCode(details[i]);
                
                for(int j = 0; j < detailList.size(); j++) {
                    Map map = detailList.get(j);
                    if(map.get("codeNo").equals(details[i])) {
                        powerDetailTemp.setName(TeeStringUtil.getString(map.get("codeName"), ""));
                    }
                }
                
                powerDetailTemps.add(powerDetailTemp);
            }
            powerTemp.setDetails(powerDetailTemps);
            
            // 职权层级数据转换
            List<PowerLevelTemp> powerLevelTemps = new ArrayList<PowerLevelTemp>();
            if(powerTempModel.getPowerLevelJson() != null && !"".equals(powerTempModel.getPowerLevelJson())) {
                JSONArray levelArray = JSONArray.fromObject(powerTempModel.getPowerLevelJson());
                if(levelArray != null && levelArray.size() != 0) {
                    PowerLevelTemp powerLevelTemp = null;
                    for(int i = 0; i < levelArray.size(); i++) {
                        powerLevelTemp = new PowerLevelTemp();
                        powerLevelTemp.setId(UUID.randomUUID().toString());
                        powerLevelTemp.setPowerLevelTemp(powerTemp);
                        powerLevelTemp.setPowerStage(TeeStringUtil.getString(levelArray.getJSONObject(i).get("strage"), ""));
                        powerLevelTemp.setRemark(TeeStringUtil.getString(levelArray.getJSONObject(i).get("remark"), ""));
                        
                        powerLevelTemps.add(powerLevelTemp);
                    }
                }
                
            }
            powerTemp.setLevels(powerLevelTemps);
            
            //职权流程图转换
            List<PowerFlowsheetTemp> flowsheetTemps = new ArrayList<PowerFlowsheetTemp>();
            if(powerTempModel.getPowerFlowsheet() != null && !"".equals(powerTempModel.getPowerFlowsheet())) {
                JSONArray flowArray = JSONArray.fromObject(powerTempModel.getPowerFlowsheet());
                if(flowArray != null && flowArray.size() != 0) {
                    PowerFlowsheetTemp flowsheetTemp = null;
                    for(int i = 0; i < flowArray.size(); i++) {
                        flowsheetTemp = new PowerFlowsheetTemp();
                        flowsheetTemp.setId(UUID.randomUUID().toString());
                        flowsheetTemp.setPowerFlowsheetTemp(powerTemp);
                        flowsheetTemp.setFileId(TeeStringUtil.getInteger(flowArray.getJSONObject(i).get("id"), 0));
                        flowsheetTemp.setFileName(TeeStringUtil.getString(flowArray.getJSONObject(i).get("name"), ""));
                        flowsheetTemp.setFlowsheetType(TeeStringUtil.getString(flowArray.getJSONObject(i).get("type"), ""));
                        
                        flowsheetTemps.add(flowsheetTemp);
                    }
                }
            }
            powerTemp.setFlows(flowsheetTemps);
            
            TblLawDetailModel lawDetailModel = new TblLawDetailModel();
            
            if(powerTempModel.getGistIds() != null && !"".equals(powerTempModel.getGistIds())) {
                if(powerTemp.getPowerType().equals("01")) {
                    
                    // 违法依据和处罚依据
                    lawDetailModel.setIds("'" + powerTempModel.getGistIds().replace(",", "','") + "'");
                    List<TblLawDetail> lawDetails = lawDetailService.getLawDetailByIds(lawDetailModel);
                    
                    List<PowerGistTemp> gistTemps = new ArrayList<PowerGistTemp>();
                    PowerGistTemp gistTemp = null;
                    for(int i = 0; i < lawDetails.size(); i++) {
                        gistTemp = new PowerGistTemp();
                        gistTemp.setId(UUID.randomUUID().toString());
                        gistTemp.setPowerGistTemp(powerTemp);
                        gistTemp.setGistType("01");
                        gistTemp.setLawName(lawDetails.get(i).getLawName());
                        gistTemp.setLawDetailId(lawDetails.get(i).getId());
                        gistTemp.setGistChapter(lawDetails.get(i).getDetailChapter());
                        gistTemp.setGistSection(lawDetails.get(i).getDetailSection());
                        gistTemp.setGistSeries(lawDetails.get(i).getDetailSeries());
                        gistTemp.setGistStrip(lawDetails.get(i).getDetailStrip());
                        gistTemp.setGistFund(lawDetails.get(i).getDetailFund());
                        gistTemp.setGistItem(lawDetails.get(i).getDetailItem());
                        gistTemp.setGistCatalog(lawDetails.get(i).getDetailCatalog());
                        gistTemp.setContent(lawDetails.get(i).getContent());
                        gistTemp.setGistCode(lawDetails.get(i).getLawDetailIndex());
                        gistTemp.setSubjectId(powerTemp.getSubjectId());
                        gistTemp.setCreateTime(new Date());
                        
                        gistTemps.add(gistTemp);
                    }
                    
                    lawDetailModel.setIds("'" + powerTempModel.getPunishIds().replace(",", "','") + "'");
                    List<TblLawDetail> punishDetails = lawDetailService.getLawDetailByIds(lawDetailModel);
                    
                    PowerGistTemp punishTemp = null;
                    for(int i = 0; i < punishDetails.size(); i++) {
                        punishTemp = new PowerGistTemp();
                        punishTemp.setId(UUID.randomUUID().toString());
                        punishTemp.setPowerGistTemp(powerTemp);
                        punishTemp.setGistType("02");
                        punishTemp.setLawName(punishDetails.get(i).getLawName());
                        punishTemp.setLawDetailId(punishDetails.get(i).getId());
                        punishTemp.setGistChapter(punishDetails.get(i).getDetailChapter());
                        punishTemp.setGistSection(punishDetails.get(i).getDetailSection());
                        punishTemp.setGistSeries(punishDetails.get(i).getDetailSeries());
                        punishTemp.setGistStrip(punishDetails.get(i).getDetailStrip());
                        punishTemp.setGistFund(punishDetails.get(i).getDetailFund());
                        punishTemp.setGistItem(punishDetails.get(i).getDetailItem());
                        punishTemp.setGistCatalog(punishDetails.get(i).getDetailCatalog());
                        punishTemp.setContent(punishDetails.get(i).getContent());
                        punishTemp.setGistCode(punishDetails.get(i).getLawDetailIndex());
                        punishTemp.setSubjectId(powerTemp.getSubjectId());
                        punishTemp.setCreateTime(new Date());
                        
                        gistTemps.add(punishTemp);
                    }
                    
                    powerTemp.setGists(gistTemps);
                } else {
                    // 设定依据
                    lawDetailModel.setIds("'" + powerTempModel.getSettingIds().replace(",", "','") + "'");
                    List<TblLawDetail> lawDetails = lawDetailService.getLawDetailByIds(lawDetailModel);
                    
                    List<PowerGistTemp> settingTemps = new ArrayList<PowerGistTemp>();
                    PowerGistTemp settingTemp = null;
                    for(int i = 0; i < lawDetails.size(); i++) {
                        settingTemp = new PowerGistTemp();
                        settingTemp.setId(UUID.randomUUID().toString());
                        settingTemp.setPowerGistTemp(powerTemp);
                        settingTemp.setGistType("03");
                        settingTemp.setLawName(lawDetails.get(i).getLawName());
                        settingTemp.setLawDetailId(lawDetails.get(i).getId());
                        settingTemp.setGistChapter(lawDetails.get(i).getDetailChapter());
                        settingTemp.setGistSection(lawDetails.get(i).getDetailSection());
                        settingTemp.setGistSeries(lawDetails.get(i).getDetailSeries());
                        settingTemp.setGistStrip(lawDetails.get(i).getDetailStrip());
                        settingTemp.setGistFund(lawDetails.get(i).getDetailFund());
                        settingTemp.setGistItem(lawDetails.get(i).getDetailItem());
                        settingTemp.setGistCatalog(lawDetails.get(i).getDetailCatalog());
                        settingTemp.setContent(lawDetails.get(i).getContent());
                        settingTemp.setGistCode(lawDetails.get(i).getLawDetailIndex());
                        settingTemp.setSubjectId(powerTemp.getSubjectId());
                        settingTemp.setCreateTime(new Date());
                        
                        settingTemps.add(settingTemp);
                    }
                    
                    powerTemp.setGists(settingTemps);
                }
            }
            
            
            List<PowerSubjectTemp> subjectTemps = new ArrayList<PowerSubjectTemp>();
            if(powerTempModel.getSubjectIds() != null && !"".equals(powerTempModel.getSubjectIds())) {
                List<Subject> subjects = subjectService.getSubjectByIds("'" + powerTempModel.getSubjectIds().replace(",", "','") + "'");
                PowerSubjectTemp subjectTemp = null;
                for(int i = 0; i < subjects.size(); i++) {
                    subjectTemp = new PowerSubjectTemp();
                    subjectTemp.setId(UUID.randomUUID().toString());
                    subjectTemp.setPowerSubjectTemp(powerTemp);
                    subjectTemp.setSubject(subjects.get(i));
                    subjectTemp.setSubjectName(subjects.get(i).getSubName());
                    subjectTemp.setCreateDate(new Date());
                    subjectTemp.setIsDepute(subjects.get(i).getIsDepute());
                    subjectTemp.setIsDelete(0);
                    
                    subjectTemps.add(subjectTemp);
                }
                powerTemp.setSubjects(subjectTemps);
            }
            
            if(editFlag == 0) {
                powerTemp.setCreateDate(new Date());
                powerTemp.setCurrentState("10");
            } else {
                powerTemp.setUpdateDate(new Date());
                powerTemp.setCreateDate(TeeDateUtil.format(powerTempModel.getCreateDateStr(), "yyyy-MM-dd"));
            }
            
            
            powerTempService.savePower(powerTemp);
            
            result.setRtData(resultObj);
            result.setRtState(true);
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/test")
    public JSONArray test(HttpServletRequest request) {
        JSONArray object = new JSONArray();
        List<Map<String, Object>> detailList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_DETAIL");
        object = JSONArray.fromObject(detailList);
        return object;
    }
    
//    
    @RequestMapping("/powerShow")
    public void powerShow(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = TeeStringUtil.getString(request.getParameter("id"), "");
            if(!"".equals(id)) {
                PowerTemp powerTemp = powerTempService.get(id);
                                
                request.setAttribute("power", handlePower(powerTemp));                
                request.getRequestDispatcher("/supervise/power/power_show.jsp").forward(request, response);;
            }
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    
    private PowerTempModel handlePower(PowerTemp powerTemp) throws Exception {
        PowerTempModel powerTempModel = new PowerTempModel();
        BeanUtils.copyProperties(powerTemp, powerTempModel);
        
        Subject subject = subjectService.getNameById(powerTemp.getSubjectId());
        
        powerTempModel.setSubjectName(subject.getSubName());
        
        List<Map<String, Object>> typeCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
        
        for(Map<String, Object> code : typeCode) {
            if(powerTempModel.getPowerType().equals(code.get("codeNo").toString())) {
                powerTempModel.setPowerType(code.get("codeName").toString());
                break;
            }
        }
        
        String powerDetail = "";
        for(int j = 0; j < powerTemp.getDetails().size(); j++) {
            powerDetail = powerDetail + powerTemp.getDetails().get(j).getName() + ",";
        }
        if(powerDetail.length() > 0) {
            powerTempModel.setPowerDetail(powerDetail.substring(0, powerDetail.length() - 1));
        }
        
        List<Map<String, Object>> levelCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_LEVEL");
        JSONArray levelArray = new JSONArray();
        if(powerTemp.getLevels() != null && powerTemp.getLevels().size() != 0) {
            for(int i = 0; i < powerTemp.getLevels().size(); i++) {
                PowerLevelTemp level = powerTemp.getLevels().get(i);
                for(Map<String, Object> map : levelCode) {
                    if(map.get("codeNo").equals(level.getPowerStage())) {
                        level.setPowerStage(map.get("codeName").toString());
                    }
                }
                level.setPowerLevelTemp(null);
                JSONObject jsonObject = JSONObject.fromObject(level);
                
                levelArray.add(jsonObject);
            }
        }
        powerTempModel.setLevelArray(levelArray);
        
        List<Map<String, Object>> moldList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_MOLD");
        if(powerTemp.getPowerMold() != null && !"".equals(powerTemp.getPowerMold())) {
            for(Map<String, Object> map : moldList) {
                if(map.get("codeNo").equals(powerTemp.getPowerMold())) {
                    powerTempModel.setPowerMold(map.get("codeName").toString());
                }
            }
        }
        
        List<Map<String, Object>> flowCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_FLOW");
        JSONArray flowsheetArray = new JSONArray();
        if(powerTemp.getFlows() != null && powerTemp.getFlows().size() != 0) {
            for(int i = 0; i < powerTemp.getFlows().size(); i++) {
                PowerFlowsheetTemp powerFlowsheet = powerTemp.getFlows().get(i);
                for(Map<String, Object> map : flowCode) {
                    if(map.get("codeNo").equals(powerFlowsheet.getFlowsheetType())) {
                        powerFlowsheet.setFlowsheetType(map.get("codeName").toString());
                    }
                }
                
                powerFlowsheet.setPowerFlowsheetTemp(null);
                flowsheetArray.add(JSONObject.fromObject(powerFlowsheet));
               
            }
        }
        powerTempModel.setFlowsheetArray(flowsheetArray);
        
        List<Map<String, Object>> optCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_OPERATION");
        JSONArray operationArray = new JSONArray();
        PowerOperationModel operationModel = null;
        if(powerTemp.getOperationTemps() != null && powerTemp.getOperationTemps().size() > 0) {
            for(int i = 0; i < powerTemp.getOperationTemps().size(); i++) {
                PowerOperation powerOperation = powerTemp.getOperationTemps().get(i);
                operationModel = new PowerOperationModel();
                
                for(Map<String, Object> map : optCode) {
                    if(map.get("codeNo").equals(powerOperation.getOptType())) {
                        powerTempModel.setOptType(map.get("codeNo").toString());
                        powerTempModel.setOptTypeName(map.get("codeName").toString());
                        
                        break;
                    }
                }
                
                if(powerOperation.getPowerOptTemp() != null) {
                    operationModel.setPowerTempName(powerOperation.getPowerOptTemp().getName());
                }
                if(powerOperation.getPowerOpt() != null) {
                    operationModel.setPowerFormalName(powerOperation.getPowerOpt().getName());
                    operationModel.setPowerFormalId(powerOperation.getPowerOpt().getId());
                }
                operationArray.add(JSONObject.fromObject(operationModel));
            }
            
        }
        
        powerTempModel.setOptArray(operationArray);
        powerTempModel.setOptCount(operationArray.size());
        
        return powerTempModel;
    }
    
    @ResponseBody
    @RequestMapping("/findSubjectsByPowerId")
    public TeeEasyuiDataGridJson findSubjectsByPowerId(TeeDataGridModel dm, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        String powerId = TeeStringUtil.getString(request.getParameter("powerId"), "");
        
        List<PowerSubjectTemp> subjectTemps = subjectTempService.findSubjectsByPowerId(dm, powerId);
        Long total = subjectTempService.findSubjectsCountByPowerId(powerId);
        
        List<PowerSubjectTempModel> subjectTempModels = new ArrayList<PowerSubjectTempModel>();
        PowerSubjectTempModel subjectTempModel = null;
        for(int i = 0; i < subjectTemps.size(); i++) {
            subjectTempModel = new PowerSubjectTempModel();
            
            subjectTempModel.setSubjectName(subjectTemps.get(i).getSubjectName());
            subjectTempModel.setSubjectId(subjectTemps.get(i).getSubject().getId());
            if(subjectTemps.get(i).getIsDepute() == 1) {
                subjectTempModel.setDeputeType("受委托组织");
            } else {
                subjectTempModel.setDeputeType("执法主体");
            }
            
            subjectTempModels.add(subjectTempModel);
        }
        
        gridJson.setRows(subjectTempModels);
        gridJson.setTotal(total);
        
        return gridJson;
    }
    
    @ResponseBody
    @RequestMapping("/findGistsByPowerId")
    public TeeEasyuiDataGridJson findGistsByPowerId(TeeDataGridModel dm, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        String powerId = TeeStringUtil.getString(request.getParameter("powerId"), "");
        
        List<PowerGistTemp> gistTemps = powerGistService.findGistTempsByPowerId(dm, powerId);
        Long total = powerGistService.findGistTempsCountByPowerId(powerId);
        

        List<Map<String, Object>> gistCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("GIST_TYPE");
        
        List<PowerGistTempModel> gistTempModels = new ArrayList<PowerGistTempModel>();
        PowerGistTempModel gistTempModel = null;
        for(int i = 0; i < gistTemps.size(); i++) {
            gistTempModel = new PowerGistTempModel();
            BeanUtils.copyProperties(gistTemps.get(i), gistTempModel);
            
            for(Map<String, Object> map : gistCode) {
                if(map.get("codeNo").equals(gistTempModel.getGistType())) {
                    gistTempModel.setGistType(TeeStringUtil.getString(map.get("codeName"), ""));
                }
            }
            
            gistTempModels.add(gistTempModel);
        }
        
        gridJson.setRows(gistTempModels);
        gridJson.setTotal(total);
        
        return gridJson;
    }
    
    @RequestMapping("/submitAdjust")
    public void submitAdjust(HttpServletRequest request, HttpServletResponse response) {
        try {
            String selectedPowerId = request.getParameter("ids");
            
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
            
            PowerAdjustModel adjustModel = new PowerAdjustModel();
            
            long batchCode = new Date().getTime();
            adjustModel.setBatchCode(TeeStringUtil.getString(batchCode));
            adjustModel.setUserId(person.getUuid());
            adjustModel.setUserName(person.getUserName());
            adjustModel.setDepartmentId(department.getRelations().get(0).getBusinessDeptId());
            adjustModel.setDepartmentName(department.getRelations().get(0).getBusinessDeptName());
            adjustModel.setCreateDateStr(TeeDateUtil.format(new Date(), "yyyy-MM-dd"));
            adjustModel.setExamineSum(selectedPowerId.split(",").length);
            adjustModel.setId(UUID.randomUUID().toString());
            
            request.setAttribute("selectedPowerId", selectedPowerId);
            request.setAttribute("adjustModel", adjustModel);
            request.setAttribute("adjust", JSONObject.fromObject(adjustModel));
            
            request.getRequestDispatcher("/supervise/power/power_adjust.jsp").forward(request, response);
            
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    
    @ResponseBody
    @RequestMapping("/getPowerByIds")
    public TeeEasyuiDataGridJson getPowerByIds(TeeDataGridModel dm, PowerTempModel powerTempModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        List<PowerTemp> powerTemps = powerTempService.getPowerByIds(dm, powerTempModel);
        Long total = powerTempService.getCountByIds(powerTempModel);
        
        List<PowerTempModel> powerModels = new ArrayList<PowerTempModel>();
        List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
        List<Map<String, Object>> operationTypeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_OPERATION");

        PowerTempModel rtModel = null;
        for(int i = 0; i < powerTemps.size(); i++) {
            rtModel = new PowerTempModel();
            PowerTemp temp = powerTemps.get(i);
            
            BeanUtils.copyProperties(temp, rtModel);
            rtModel.setCreateDateStr(TeeDateUtil.format(temp.getCreateDate(), "yyyy-MM-dd"));
            rtModel.setDeleteDateStr(TeeDateUtil.format(temp.getDeleteDate(), "yyyy-MM-dd"));
            rtModel.setRevokeDateStr(TeeDateUtil.format(temp.getRevokeDate(), "yyyy-MM-dd"));
            
            for(Map<String, Object> code : codeList) {
                if(rtModel.getPowerType().equals(code.get("codeNo").toString())) {
                    rtModel.setPowerType(code.get("codeName").toString());
                    break;
                }
            }
            
            String powerDetailTemp = "";
            for(int j = 0; j < temp.getDetails().size(); j++) {
                powerDetailTemp = powerDetailTemp + temp.getDetails().get(j).getName() + ",";
            }
            if(powerDetailTemp.length() > 0) {
                rtModel.setPowerDetail(powerDetailTemp.substring(0, powerDetailTemp.length() - 1));
            }
            
            if(temp.getOperationTemps() != null && temp.getOperationTemps().size() > 0) {
                for(int j = 0; j < temp.getOperationTemps().size(); j++) {
                    String optType = temp.getOperationTemps().get(j).getOptType();
                    for(Map<String, Object> code : operationTypeList) {
                        if(optType.equals(code.get("codeNo").toString())) {
                            rtModel.setOperationType(code.get("codeName").toString());
                            break;
                        }
                    }
                }
            }
            
            
            powerModels.add(rtModel);
        }
        
        gridJson.setRows(powerModels);
        gridJson.setTotal(total);
        
        return gridJson;
    }
    
    @ResponseBody
    @RequestMapping("/saveAdjust")
    public TeeJson saveAdjust(PowerAdjustModel adjustModel, HttpServletRequest request) {
        TeeJson result = new TeeJson();
        
        List<PowerTemp> powerTemps = powerTempService.getPowerTemps(adjustModel.getSelectedPowerId());
        PowerAdjust powerAdjust = new PowerAdjust();
        List<PowerAdjustAuthority> adjustAuthorities = new ArrayList<PowerAdjustAuthority>();
        
        BeanUtils.copyProperties(adjustModel, powerAdjust);
        
        TblDepartmentInfo department = new TblDepartmentInfo();
        department.setId(adjustModel.getDepartmentId());
        powerAdjust.setSubmitDepartment(department);
        
        TeePerson person = personService.selectByUuid(adjustModel.getUserId() + "");
        powerAdjust.setUser(person);
        powerAdjust.setCreateDate(TeeDateUtil.format(adjustModel.getCreateDateStr(), "yyyy-MM-dd"));
        
        PowerAdjustTache adjustTache = new PowerAdjustTache();
        adjustTache.setTacheId(0);
        adjustTache.setTacheName("职权调整");
        adjustTache.setId(UUID.randomUUID().toString());
        adjustTache.setPowerAdjustTache(powerAdjust);
        adjustTache.setClosedDate(new Date());
        adjustTache.setExamineDate(new Date());
        adjustTache.setExamineView(powerAdjust.getAdjustReason());
        adjustTache.setExaminePerson(person);
        
        PowerAdjustAuthority adjustAuthority = null;
        for(int i = 0;i < powerTemps.size(); i++) {
            adjustAuthority = new PowerAdjustAuthority();
            adjustAuthority.setId(UUID.randomUUID().toString());
            adjustAuthority.setPowerTemp(powerTemps.get(i));
            adjustAuthority.setPowerAdjust(powerAdjust);
            adjustAuthority.setExamineState("10");
            
            adjustAuthorities.add(adjustAuthority);
        }
        
        powerTempService.saveAdjust(powerAdjust, adjustTache, adjustAuthorities, adjustModel.getSelectedPowerId());
        
        result.setRtState(true);
        result.setRtData(powerAdjust.getId());
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/transferPower")
    public TeeJson transferPower(HttpServletRequest request) throws Exception{
        TeeJson result = new TeeJson();
        JSONObject jsonObject = new JSONObject();
        
        try {
            String adjustId = TeeStringUtil.getString(request.getParameter("adjustId"), "");
            List<PowerAdjustAuthority> adjustAuthorities = adjustAuthorityService.listByAdjustId(adjustId);
            List<PowerTempModel> powerTempModels = new ArrayList<PowerTempModel>();
            PowerTempModel powerTempModel = null;
            for(int i = 0; i < adjustAuthorities.size(); i++) {
                PowerTemp powerTemp = adjustAuthorities.get(i).getPowerTemp();
                powerTempModel = new PowerTempModel();
                
                BeanUtils.copyProperties(powerTemp, powerTempModel);
                
                // 职权批次号
                powerTempModel.setBatchCode(adjustAuthorities.get(i).getPowerAdjust().getBatchCode());
                
                List<PowerOperation> powerOpts = powerTempService.getPowerOpts(powerTemp.getId());
                // 职权操作类型
                String powerOpt = powerOpts.get(0).getOptType();
                // 职权的调整方式
                powerTempModel.setOperationType(powerOpt);
                
                String powerFormalIds = "";
                for(int j = 0; j < powerOpts.size(); j++) {
                    if(!"10".equals(powerOpt)) {
                        powerFormalIds = powerFormalIds + powerOpts.get(j).getPowerOpt().getId() + ",";
                    }
                }
                if(!"10".equals(powerOpt)) {
                    powerFormalIds = powerFormalIds.substring(0, powerFormalIds.length() - 1);
                }
                
                // 配套修改的正式职权
                powerTempModel.setPowerFormalIds(powerFormalIds);
                
                // 生成职权编号
                if(!"50".equals(powerOpt)) {
                    String code = produceCode(powerTemp.getPowerType(), powerTemp.getSubjectId());
                    powerTempModel.setCode(code);
                }
                
                if("20".equals(adjustAuthorities.get(i).getExamineState())) {
                    powerTempModel.setCurrentState("90");
                } else if("90".equals(adjustAuthorities.get(i).getExamineState())) {
                    powerTempModel.setCurrentState("99");
                }
                
                powerTempModels.add(powerTempModel);
               
            }
            
            powerTempService.transferPower(powerTempModels);
            result.setRtState(true);
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        
        
        return result;
    }
    
    public String produceCode(String powerType, String subjectId) {
        Date date = new Date();
        String code = date.getTime() + "";
        return code.substring(code.length() - 8, code.length());
    }
    /**
     * 
    * @Function: PowerTempController.java
    * @Description: 审核通过并迁移数据
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年3月5日 上午11:44:58 
    *
     */
    @ResponseBody
    @RequestMapping("updateState")
    public TeeJson updatePowertempState(HttpServletRequest request) {
        TeeJson rtInfo = new TeeJson();
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        String currentState = TeeStringUtil.getString(request.getParameter("currentState"), "");
        
        powerTempService.updatePowertempState(id, currentState);
        if("90".equals(currentState)) {
            transfer(id);
        }
        
        rtInfo.setRtState(true);
        return rtInfo;
    }
    
    public void transfer(String ids) {
        try {
            List<PowerTemp> powerTemps = powerTempService.getPowerTempList(ids);
            if(powerTemps != null && powerTemps.size() != 0) {
                List<PowerTempModel> powerTempModels = new ArrayList<PowerTempModel>();
                PowerTempModel powerTempModel = null;
                for(int i = 0; i < powerTemps.size(); i++) {
                    PowerTemp powerTemp = powerTemps.get(i);
                    powerTempModel = new PowerTempModel();
                    BeanUtils.copyProperties(powerTemp, powerTempModel);
                    
                    // 职权批次号
                    powerTempModel.setBatchCode("");
                    
                    powerTempModel.setOperationType("10");
                    powerTempModel.setCurrentState("90");
                    
                    // 生成职权编号
                    String code = produceCode(powerTemp.getPowerType(), powerTemp.getSubjectId());
                    powerTempModel.setCode(code);
                    
                    powerTempModels.add(powerTempModel);
                }
                
                powerTempService.transferPower(powerTempModels);
            }
        }catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    @ResponseBody
    @RequestMapping("getPowerExamineGrid")
    public TeeEasyuiDataGridJson getPowerExamineGrid(HttpServletRequest request, TeeDataGridModel dm, PowerTempModel powerTempModel) {
        TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
        
        OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
        List<PowerTempModel> powerModels = new ArrayList<PowerTempModel>();
        Long total = new Long(0);
        if(orgCtrl.getOrgType() == 1) {
            powerTempModel.setDepartmentId(orgCtrl.getSupDeptId());
            List<PowerTemp> powerTemps = powerTempService.getExaminePowerList(dm, powerTempModel);
            total = powerTempService.getExaminePowerCount(powerTempModel);
            
            List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
            List<Map<String, Object>> operationTypeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_OPERATION");
            List<Map<String, Object>> stateList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_STATE");

            
            PowerTempModel rtModel = null;
            for(int i = 0; i < powerTemps.size(); i++) {
                rtModel = new PowerTempModel();
                PowerTemp temp = powerTemps.get(i);
                
                BeanUtils.copyProperties(temp, rtModel);
                rtModel.setCreateDateStr(TeeDateUtil.format(temp.getCreateDate(), "yyyy-MM-dd"));
                rtModel.setDeleteDateStr(TeeDateUtil.format(temp.getDeleteDate(), "yyyy-MM-dd"));
                rtModel.setRevokeDateStr(TeeDateUtil.format(temp.getRevokeDate(), "yyyy-MM-dd"));
                
                for(Map<String, Object> code : codeList) {
                    if(rtModel.getPowerType().equals(code.get("codeNo").toString())) {
                        rtModel.setPowerType(code.get("codeName").toString());
                        break;
                    }
                }
                
                String powerDetailTemp = "";
                for(int j = 0; j < temp.getDetails().size(); j++) {
                    powerDetailTemp = powerDetailTemp + temp.getDetails().get(j).getName() + ",";
                }
                if(powerDetailTemp.length() > 0) {
                    rtModel.setPowerDetail(powerDetailTemp.substring(0, powerDetailTemp.length() - 1));
                }
                
                if(temp.getOperationTemps() != null && temp.getOperationTemps().size() > 0) {
                    for(int j = 0; j < temp.getOperationTemps().size(); j++) {
                        String optType = temp.getOperationTemps().get(j).getOptType();
                        for(Map<String, Object> code : operationTypeList) {
                            if(optType.equals(code.get("codeNo").toString())) {
                                rtModel.setOperationType(code.get("codeName").toString());
                                break;
                            }
                        }
                    }
                }
                if(temp.getCurrentState() != null && !"".equals(temp.getCurrentState())) {
                    for(Map<String, Object> code : stateList) {
                        if(temp.getCurrentState().equals(code.get("codeNo").toString())) {
                            rtModel.setCurrentStateValue(code.get("codeName").toString());
                            break;
                        }
                    }
                }
                
                powerModels.add(rtModel);
            }
        }
        
        
        datagrid.setRows(powerModels);
        datagrid.setTotal(total);
        return datagrid;
    }
}
