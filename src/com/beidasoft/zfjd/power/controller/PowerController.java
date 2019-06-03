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
import com.beidasoft.zfjd.law.bean.TblLawDetail;
import com.beidasoft.zfjd.law.model.TblLawDetailModel;
import com.beidasoft.zfjd.law.service.TblLawDetailService;
import com.beidasoft.zfjd.power.bean.Power;
import com.beidasoft.zfjd.power.bean.PowerDetail;
import com.beidasoft.zfjd.power.bean.PowerFlowsheet;
import com.beidasoft.zfjd.power.bean.PowerGist;
import com.beidasoft.zfjd.power.bean.PowerLevel;
import com.beidasoft.zfjd.power.bean.PowerSubject;
import com.beidasoft.zfjd.power.model.PowerGistModel;
import com.beidasoft.zfjd.power.model.PowerModel;
import com.beidasoft.zfjd.power.model.PowerSubjectModel;
import com.beidasoft.zfjd.power.service.PowerGistService;
import com.beidasoft.zfjd.power.service.PowerService;
import com.beidasoft.zfjd.power.service.PowerSubjectService;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.service.SubjectService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/powerCtrl")
public class PowerController{
    
    @Autowired
    private PowerService powerService;
    
    @Autowired
    private TblLawDetailService lawDetailService;
    
    @Autowired
    private PowerGistService powerGistService;
    
    @Autowired
    private TeeDeptService deptService;
    
    @Autowired
    private PowerSubjectService powerSubjectService;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private CommonService commonService;
    
    @ResponseBody
    @RequestMapping("/listByPage.action")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dm, PowerModel powerModel, HttpServletRequest request) throws Exception{
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        try {
         // 通过request获取当前部门
            OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
            
            List<Power> powers = powerService.listByPage(dm, powerModel, orgCtrl);
            Long total = powerService.listCount(powerModel, orgCtrl);
            
            List<PowerModel> powerModels = new ArrayList<PowerModel>();
            List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");

            
            PowerModel rtModel = null;
            for(int i = 0; i < powers.size(); i++) {
                rtModel = new PowerModel();
                Power temp = powers.get(i);
                
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
                
                String powerDetail = "";
                for(int j = 0; j < temp.getDetails().size(); j++) {
                    powerDetail = powerDetail + temp.getDetails().get(j).getName() + ",";
                }
                if(powerDetail.length() > 0) {
                    rtModel.setPowerDetail(powerDetail.substring(0, powerDetail.length() - 1));
                }
                powerModels.add(rtModel);
            }
            
            gridJson.setRows(powerModels);
            gridJson.setTotal(total);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        
        return gridJson;
    }
    
    @RequestMapping("/showById")
    public void showById(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String id = TeeStringUtil.getString(request.getParameter("id"), "");
            if(!"".equals(id)) {
                Power power = powerService.get(id);
                                
                request.setAttribute("power", handlePower(power));                
                request.getRequestDispatcher("/supervise/power/power_formal_show.jsp").forward(request, response);;
            }
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    
    
    
    /**
     * 
    * @Function: PowerController.java
    * @Description: 将power类转换为powerModel
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2018年12月25日 下午4:10:07 
    *
     */
    private PowerModel handlePower(Power power) throws Exception {
        PowerModel powerModel = new PowerModel();
        BeanUtils.copyProperties(power, powerModel);
        
        Subject subject = subjectService.getNameById(power.getSubjectId());
        
        powerModel.setSubjectName(subject.getSubName());
        
        List<Map<String, Object>> typeCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
        
        for(Map<String, Object> code : typeCode) {
            if(powerModel.getPowerType().equals(code.get("codeNo").toString())) {
                powerModel.setPowerType(code.get("codeName").toString());
                break;
            }
        }
        
        String powerDetail = "";
        for(int j = 0; j < power.getDetails().size(); j++) {
            powerDetail = powerDetail + power.getDetails().get(j).getName() + ",";
        }
        if(powerDetail.length() > 0) {
            powerModel.setPowerDetail(powerDetail.substring(0, powerDetail.length() - 1));
        }
        
        List<Map<String, Object>> levelCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_LEVEL");
        JSONArray levelArray = new JSONArray();
        if(power.getLevels() != null && power.getLevels().size() != 0) {
            for(int i = 0; i < power.getLevels().size(); i++) {
                PowerLevel level = power.getLevels().get(i);
                for(Map<String, Object> map : levelCode) {
                    if(map.get("codeNo").equals(level.getPowerStage())) {
                        level.setPowerStage(map.get("codeName").toString());
                    }
                }
                level.setPowerLevel(null);
                JSONObject jsonObject = JSONObject.fromObject(level);
                
                levelArray.add(jsonObject);
            }
        }
        powerModel.setLevelArray(levelArray);
        
        List<Map<String, Object>> flowCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_FLOW");
        JSONArray flowsheetArray = new JSONArray();
        if(power.getFlows() != null && power.getFlows().size() != 0) {
            for(int i = 0; i < power.getFlows().size(); i++) {
                PowerFlowsheet powerFlowsheet = power.getFlows().get(i);
                for(Map<String, Object> map : flowCode) {
                    if(map.get("codeNo").equals(powerFlowsheet.getFlowsheetType())) {
                        powerFlowsheet.setFlowsheetType(map.get("codeName").toString());
                    }
                }
                
                powerFlowsheet.setPowerFlowsheet(null);
                flowsheetArray.add(JSONObject.fromObject(powerFlowsheet));
               
            }
        }
        powerModel.setFlowsheetArray(flowsheetArray);
        
//        List<Map<String, Object>> optCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_OPERATION");
//        JSONArray operationArray = new JSONArray();
//        PowerOperationModel operationModel = null;
//        if(power.getOperations() != null && power.getOperations().size() > 0) {
//            for(int i = 0; i < power.getOperations().size(); i++) {
//                PowerOperation powerOperation = power.getOperations().get(i);
//                operationModel = new PowerOperationModel();
//                
//                for(Map<String, Object> map : optCode) {
//                    if(map.get("codeNo").equals(powerOperation.getOptType())) {
//                        powerModel.setOptType(map.get("codeNo").toString());
//                        powerModel.setOptTypeName(map.get("codeName").toString());
//                        
//                        break;
//                    }
//                }
//                
//                if(powerOperation.getPowerOpt() != null) {
//                    operationModel.setPowerName(powerOperation.getPowerOpt().getName());
//                }
//                if(powerOperation.getPowerOpt() != null) {
//                    operationModel.setPowerFormalName(powerOperation.getPowerOpt().getName());
//                }
//                operationArray.add(JSONObject.fromObject(operationModel));
//            }
//            
//        }
//        
//        powerModel.setOptArray(operationArray);
//        powerModel.setOptCount(operationArray.size());
        
        return powerModel;
    }
    
    @ResponseBody
    @RequestMapping("/getById")
    public TeeJson getById(HttpServletRequest request, String id) throws Exception {
        TeeJson result = new TeeJson();
        try {
            // 通过ID获取职权信息
            Power power = powerService.get(id);
            // 将职权bean转换为model
            PowerModel powerModel = changeBeanToModel(power);
            
            result.setRtState(true);
            result.setRtData(powerModel);
        }catch (Exception e) {
            // TODO: handle exception
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
    private PowerModel changeBeanToModel(Power power) {
        PowerModel powerModel = new PowerModel();
        
        BeanUtils.copyProperties(power, powerModel);
        
        powerModel.setCreateDateStr(TeeDateUtil.format(power.getCreateDate(), "yyyy-MM-dd"));
        powerModel.setUpdateDateStr(TeeDateUtil.format(power.getUpdateDate(), "yyyy-MM-dd"));
        
        // 转换职权流程图类型、职权层级、职权分类型
        String powerDetail = "";
        String flowsheetType = "";
        String powerLevel = "";
        
        if(power.getDetails() != null && power.getDetails().size() != 0) {
            for(int i = 0; i < power.getDetails().size(); i++) {
                powerDetail = powerDetail + power.getDetails().get(i).getCode() + ",";
            }
            
            powerModel.setPowerDetail(powerDetail.substring(0, powerDetail.length() - 1));
        }
        
        JSONArray flowsheetArray = new JSONArray();
        JSONObject object = null;
        if(power.getFlows() != null && power.getFlows().size() != 0) {
            for(int i = 0; i < power.getFlows().size(); i++) {
                object = new JSONObject();
                power.getFlows().get(i).setPowerFlowsheet(null);
                object.put("fileType", power.getFlows().get(i).getFlowsheetType());
                object.put("fileId", power.getFlows().get(i).getFileId());
                
                flowsheetType = flowsheetType + power.getFlows().get(i).getFlowsheetType() + ",";
                flowsheetArray.add(object);
            }
            powerModel.setFlowPictureType(flowsheetType.substring(0, flowsheetType.length() - 1));
        }
        powerModel.setFlowsheetArray(flowsheetArray);
        
        JSONArray levelArray = new JSONArray();
        if(power.getLevels() != null && power.getLevels().size() != 0) {
            for(int i = 0; i < power.getLevels().size(); i++) {
                powerLevel = powerLevel + power.getLevels().get(i).getPowerStage() + ",";
                
                object = new JSONObject();
                object.put("powerStage", power.getLevels().get(i).getPowerStage());
                object.put("remark", power.getLevels().get(i).getRemark());
                
                levelArray.add(object);
            }
            
            powerModel.setPowerLevel(powerLevel.substring(0, powerLevel.length() - 1));
        }
        powerModel.setLevelArray(levelArray);
        
        // 将职权依据转换为ids字符串
        String gistIds = "";
        String punishIds = "";
        String settingIds = "";
        if(power.getGists() != null && power.getGists().size() != 0) {
            for(int i = 0; i < power.getGists().size(); i++) {
                PowerGist gist = power.getGists().get(i);
                
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
                powerModel.setGistIds(gistIds.substring(0, gistIds.length() - 1));
            } else {
                powerModel.setGistIds("");
            }
            if(!"".equals(punishIds)) {
                powerModel.setPunishIds(punishIds.substring(0, punishIds.length() - 1));
            } else {
                powerModel.setPunishIds("");
            }
            if(!"".equals(settingIds)) {
                powerModel.setSettingIds(settingIds.substring(0, settingIds.length() - 1));
            } else {
                powerModel.setSettingIds("");
            }
        }
        

        String subjectIds = "";
        if(power.getSubjects() != null  && power.getSubjects().size() != 0) {
            for(int i = 0; i < power.getSubjects().size(); i++) {
                PowerSubject subject = power.getSubjects().get(i);
                
                subjectIds = subjectIds + subject.getId() + ",";
            }
            
            if(!"".equals(subjectIds)) {
                powerModel.setSubjectIds(subjectIds.substring(0, subjectIds.length() - 1));
            } else {
                powerModel.setSubjectIds("");
            }
        }
        
        return powerModel;
    }
    
    @ResponseBody
    @RequestMapping("/save.action")
    public TeeJson save(PowerModel powerModel, HttpServletRequest request) throws Exception{
        TeeJson result = new TeeJson();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
            
            
            Power power = new Power();
            BeanUtils.copyProperties(powerModel, power);
            
            if(power.getId() == null || "".equals(power.getId())) {
                power.setId(UUID.randomUUID().toString());
            }
            
            power.setDepartmentId(department.getRelations().get(0).getBusinessDeptId());
            
            // 将职权分类型转换为list
            List<Map<String, Object>> detailList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_DETAIL");
            List<PowerDetail> powerDetails = new ArrayList<PowerDetail>();
            PowerDetail powerDetail = null;
            String[] details = powerModel.getPowerDetail().split(",");
            for(int i = 0; i < details.length; i++) {
                powerDetail = new PowerDetail();
                powerDetail.setId(UUID.randomUUID().toString());
                powerDetail.setPowerDetail(power);
                powerDetail.setCode(details[i]);
                
                for(int j = 0; j < detailList.size(); j++) {
                    Map map = detailList.get(j);
                    if(map.get("codeNo").equals(details[i])) {
                        powerDetail.setName(TeeStringUtil.getString(map.get("codeName"), ""));
                    }
                }
                
                powerDetails.add(powerDetail);
            }
            power.setDetails(powerDetails);
            
            // 职权层级数据转换
            List<PowerLevel> powerLevels = new ArrayList<PowerLevel>();
            if(powerModel.getPowerLevelJson() != null && !"".equals(powerModel.getPowerLevelJson())) {
                JSONArray levelArray = JSONArray.fromObject(powerModel.getPowerLevelJson());
                if(levelArray != null && levelArray.size() != 0) {
                    PowerLevel powerLevel = null;
                    for(int i = 0; i < levelArray.size(); i++) {
                        powerLevel = new PowerLevel();
                        powerLevel.setId(UUID.randomUUID().toString());
                        powerLevel.setPowerLevel(power);
                        powerLevel.setPowerStage(TeeStringUtil.getString(levelArray.getJSONObject(i).get("strage"), ""));
                        powerLevel.setRemark(TeeStringUtil.getString(levelArray.getJSONObject(i).get("remark"), ""));
                        
                        powerLevels.add(powerLevel);
                    }
                }
                
            }
            power.setLevels(powerLevels);
            
            //职权流程图转换
            List<PowerFlowsheet> flowsheets = new ArrayList<PowerFlowsheet>();
            if(powerModel.getPowerFlowsheet() != null && !"".equals(powerModel.getPowerFlowsheet())) {
                JSONArray flowArray = JSONArray.fromObject(powerModel.getPowerFlowsheet());
                if(flowArray != null && flowArray.size() != 0) {
                    PowerFlowsheet flowsheet = null;
                    for(int i = 0; i < flowArray.size(); i++) {
                        flowsheet = new PowerFlowsheet();
                        flowsheet.setId(UUID.randomUUID().toString());
                        flowsheet.setPowerFlowsheet(power);
                        flowsheet.setFileId(TeeStringUtil.getInteger(flowArray.getJSONObject(i).get("id"), 0));
                        flowsheet.setFileName(TeeStringUtil.getString(flowArray.getJSONObject(i).get("name"), ""));
                        flowsheet.setFlowsheetType(TeeStringUtil.getString(flowArray.getJSONObject(i).get("type"), ""));
                        
                        flowsheets.add(flowsheet);
                    }
                }
            }
            power.setFlows(flowsheets);
            
            TblLawDetailModel lawDetailModel = new TblLawDetailModel();
            
            if(power.getPowerType().equals("01")) {
                // 违法依据和处罚依据
                lawDetailModel.setIds("'" + powerModel.getGistIds().replace(",", "','") + "'");
                List<TblLawDetail> lawDetails = lawDetailService.getLawDetailByIds(lawDetailModel);
                
                List<PowerGist> gists = new ArrayList<PowerGist>();
                PowerGist gist = null;
                for(int i = 0; i < lawDetails.size(); i++) {
                    gist = new PowerGist();
                    gist.setId(UUID.randomUUID().toString());
                    gist.setPowerGist(power);
                    gist.setGistType("01");
                    gist.setLawName(lawDetails.get(i).getLawName());
                    gist.setLawDetailId(lawDetails.get(i).getId());
                    gist.setGistChapter(lawDetails.get(i).getDetailChapter());
                    gist.setGistSection(lawDetails.get(i).getDetailSection());
                    gist.setGistSeries(lawDetails.get(i).getDetailSeries());
                    gist.setGistStrip(lawDetails.get(i).getDetailStrip());
                    gist.setGistFund(lawDetails.get(i).getDetailFund());
                    gist.setGistItem(lawDetails.get(i).getDetailItem());
                    gist.setContent(lawDetails.get(i).getContent());
                    gist.setGistCode(lawDetails.get(i).getLawDetailIndex());
                    gist.setSubjectId(power.getSubjectId());
                    gist.setCreateTime(new Date());
                    
                    gists.add(gist);
                }
                
                lawDetailModel.setIds("'" + powerModel.getPunishIds().replace(",", "','") + "'");
                List<TblLawDetail> punishDetails = lawDetailService.getLawDetailByIds(lawDetailModel);
                
                PowerGist punish = null;
                for(int i = 0; i < punishDetails.size(); i++) {
                    punish = new PowerGist();
                    punish.setId(UUID.randomUUID().toString());
                    punish.setPowerGist(power);
                    punish.setGistType("02");
                    punish.setLawName(punishDetails.get(i).getLawName());
                    punish.setLawDetailId(punishDetails.get(i).getId());
                    punish.setGistChapter(punishDetails.get(i).getDetailChapter());
                    punish.setGistSection(punishDetails.get(i).getDetailSection());
                    punish.setGistSeries(punishDetails.get(i).getDetailSeries());
                    punish.setGistStrip(punishDetails.get(i).getDetailStrip());
                    punish.setGistFund(punishDetails.get(i).getDetailFund());
                    punish.setGistItem(punishDetails.get(i).getDetailItem());
                    punish.setContent(punishDetails.get(i).getContent());
                    punish.setGistCode(punishDetails.get(i).getLawDetailIndex());
                    punish.setSubjectId(power.getSubjectId());
                    punish.setCreateTime(new Date());
                    
                    gists.add(punish);
                }
                
                power.setGists(gists);
            } else {
                // 设定依据
                lawDetailModel.setIds("'" + powerModel.getSettingIds().replace(",", "','") + "'");
                List<TblLawDetail> lawDetails = lawDetailService.getLawDetailByIds(lawDetailModel);
                
                List<PowerGist> settings = new ArrayList<PowerGist>();
                PowerGist setting = null;
                for(int i = 0; i < lawDetails.size(); i++) {
                    setting = new PowerGist();
                    setting.setId(UUID.randomUUID().toString());
                    setting.setPowerGist(power);
                    setting.setGistType("03");
                    setting.setLawName(lawDetails.get(i).getLawName());
                    setting.setLawDetailId(lawDetails.get(i).getId());
                    setting.setGistChapter(lawDetails.get(i).getDetailChapter());
                    setting.setGistSection(lawDetails.get(i).getDetailSection());
                    setting.setGistSeries(lawDetails.get(i).getDetailSeries());
                    setting.setGistStrip(lawDetails.get(i).getDetailStrip());
                    setting.setGistFund(lawDetails.get(i).getDetailFund());
                    setting.setGistItem(lawDetails.get(i).getDetailItem());
                    setting.setContent(lawDetails.get(i).getContent());
                    setting.setGistCode(lawDetails.get(i).getLawDetailIndex());
                    setting.setSubjectId(power.getSubjectId());
                    setting.setCreateTime(new Date());
                    
                    settings.add(setting);
                }
                
                power.setGists(settings);
            }
            
            power.setCreateDate(new Date());
            
            
            powerService.savePower(power);
            
            result.setRtData(power.getId());
            result.setRtState(true);
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 
    * @Function: PowerController.java
    * @Description: 分页查询职权依据信息
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: chenq
    * @date: 2019年1月7日 下午4:31:05 
    *
     */
    @ResponseBody
    @RequestMapping("/findGist")
    public TeeEasyuiDataGridJson findGist(TeeDataGridModel dm, PowerGistModel powerGistModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        List<PowerGist> powerGists = powerGistService.listByPage(dm, powerGistModel);
        long total = powerGistService.listCount(powerGistModel);
        
        gridJson.setRows(powerGists);
        gridJson.setTotal(total);
        
        return gridJson;
    }
    
    @ResponseBody
    @RequestMapping("/findPowerByIds")
    public TeeEasyuiDataGridJson findPowerByIds(TeeDataGridModel dm, PowerModel powerModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        List<Power> powers = powerService.findPowerByIds(dm, powerModel);
        Long total = powerService.findPowerByIdsCount(powerModel);
        
        List<PowerModel> powerModels = new ArrayList<PowerModel>();
        List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");

        
        PowerModel rtModel = null;
        for(int i = 0; i < powers.size(); i++) {
            rtModel = new PowerModel();
            Power temp = powers.get(i);
            
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
            
            String powerDetail = "";
            for(int j = 0; j < temp.getDetails().size(); j++) {
                powerDetail = powerDetail + temp.getDetails().get(j).getName() + ",";
            }
            if(powerDetail.length() > 0) {
                rtModel.setPowerDetail(powerDetail.substring(0, powerDetail.length() - 1));
            }
            powerModels.add(rtModel);
        }
        
        gridJson.setRows(powerModels);
        gridJson.setTotal(total);
        
        return gridJson;
    }
    
    @ResponseBody
    @RequestMapping("/findSubjectsByPowerId")
    public TeeEasyuiDataGridJson findSubjectsByPowerId(TeeDataGridModel dm, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        String powerId = TeeStringUtil.getString(request.getParameter("powerId"), "");
        
        List<PowerSubject> subjects = powerSubjectService.findSubjectsByPowerId(dm, powerId);
        Long total = powerSubjectService.findSubjectsCountByPowerId(powerId);
        
        List<PowerSubjectModel> subjectModels = new ArrayList<PowerSubjectModel>();
        PowerSubjectModel subjectModel = null;
        for(int i = 0; i < subjects.size(); i++) {
            subjectModel = new PowerSubjectModel();
            
            subjectModel.setSubjectName(subjects.get(i).getSubjectName());
            subjectModel.setSubjectId(subjects.get(i).getSubject().getId());
            if(subjects.get(i).getIsDepute() == 1) {
                subjectModel.setDeputeType("受委托组织");
            } else {
                subjectModel.setDeputeType("执法主体");
            }
            
            subjectModels.add(subjectModel);
        }
        
        gridJson.setRows(subjectModels);
        gridJson.setTotal(total);
        
        return gridJson;
    }
    
    @ResponseBody
    @RequestMapping("/findGistsByPowerId")
    public TeeEasyuiDataGridJson findGistsByPowerId(TeeDataGridModel dm, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        String powerId = TeeStringUtil.getString(request.getParameter("powerId"), "");
        
        List<PowerGist> gists = powerGistService.findGistsByPowerId(dm, powerId);
        Long total = powerGistService.findGistsCountByPowerId(powerId);
        

        List<Map<String, Object>> gistCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("GIST_TYPE");
        
        List<PowerGistModel> gistModels = new ArrayList<PowerGistModel>();
        PowerGistModel gistModel = null;
        for(int i = 0; i < gists.size(); i++) {
            gistModel = new PowerGistModel();
            BeanUtils.copyProperties(gists.get(i), gistModel);
            
            for(Map<String, Object> map : gistCode) {
                if(map.get("codeNo").equals(gistModel.getGistType())) {
                    gistModel.setGistType(TeeStringUtil.getString(map.get("codeName"), ""));
                }
            }
            
            gistModels.add(gistModel);
        }
        
        gridJson.setRows(gistModels);
        gridJson.setTotal(total);
        
        return gridJson;
    }
    
    @ResponseBody
    @RequestMapping("/getPowerByActSubject")
    public TeeEasyuiDataGridJson getPowerByActSubject(TeeDataGridModel dm, HttpServletRequest request, PowerModel powerModel) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        List<Power> powers = powerService.getPowerByActSubject(dm, powerModel);
        Long total = powerService.getPowerCountByActSubject(powerModel);
        
        List<PowerModel> powerModels = new ArrayList<PowerModel>();
        List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
        
        PowerModel rtModel = null;
        for(int i = 0; i < powers.size(); i++) {
            rtModel = new PowerModel();
            Power temp = powers.get(i);
            
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
            
            String powerDetail = "";
            for(int j = 0; j < temp.getDetails().size(); j++) {
                powerDetail = powerDetail + temp.getDetails().get(j).getName() + ",";
            }
            if(powerDetail.length() > 0) {
                rtModel.setPowerDetail(powerDetail.substring(0, powerDetail.length() - 1));
            }
            powerModels.add(rtModel);
        }
        
        gridJson.setRows(powerModels);
        gridJson.setTotal(total);
        
        return gridJson;
    }
    
    @ResponseBody
    @RequestMapping("/findGistsByPowerIds")
    public TeeEasyuiDataGridJson findGistsByPowerIds(TeeDataGridModel dm, PowerGistModel pgModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        
        List<PowerGist> gists = powerGistService.findGistsByPowerIds(dm, pgModel);

        List<Map<String, Object>> gistCode = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("GIST_TYPE");
        
        List<PowerGistModel> gistModels = new ArrayList<PowerGistModel>();
        PowerGistModel gistModel = null;
        for(int i = 0; i < gists.size(); i++) {
            gistModel = new PowerGistModel();
            BeanUtils.copyProperties(gists.get(i), gistModel);
            
            for(Map<String, Object> map : gistCode) {
                if(map.get("codeNo").equals(gistModel.getGistType())) {
                    gistModel.setGistType(TeeStringUtil.getString(map.get("codeName"), ""));
                }
            }
            
            gistModels.add(gistModel);
        }
        
        gridJson.setRows(gistModels);
        
        return gridJson;
    }
}
