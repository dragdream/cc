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

import com.beidasoft.zfjd.power.bean.PowerAdjust;
import com.beidasoft.zfjd.power.bean.PowerAdjustAuthority;
import com.beidasoft.zfjd.power.bean.PowerAdjustTache;
import com.beidasoft.zfjd.power.bean.PowerAdjustTacheInfo;
import com.beidasoft.zfjd.power.bean.PowerTemp;
import com.beidasoft.zfjd.power.model.PowerAdjustAuthorityModel;
import com.beidasoft.zfjd.power.model.PowerAdjustModel;
import com.beidasoft.zfjd.power.model.PowerAdjustTacheInfoModel;
import com.beidasoft.zfjd.power.model.PowerAdjustTacheModel;
import com.beidasoft.zfjd.power.service.PowerAdjustAuthorityService;
import com.beidasoft.zfjd.power.service.PowerAdjustService;
import com.beidasoft.zfjd.power.service.PowerAdjustTacheInfoService;
import com.beidasoft.zfjd.power.service.PowerAdjustTacheService;
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

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/powerAdjustCtrl")
public class PowerAdjustController {
    
    @Autowired
    private PowerAdjustService adjustService;
    
    @Autowired
    private PowerAdjustAuthorityService adjustAuthorityService;
    
    @Autowired
    private PowerAdjustTacheService adjustTacheService;
    
    @Autowired
    private PowerAdjustTacheInfoService adjustTacheInfoService;
    
    @Autowired
    private TeeDeptService deptService;
    
    @ResponseBody
    @RequestMapping("/listByPage")
    public TeeEasyuiDataGridJson listByPage(HttpServletRequest request, TeeDataGridModel dm, PowerAdjustModel adjustModel) {
        TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
        
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));

        if(!"admin".equals(person.getUserId())) {
            if(department.getRelations() != null && department.getRelations().size() != 0) {
            	if("20".equals(department.getRelations().get(0).getOrgType())) {
            		adjustModel.setSupDeptId(department.getRelations().get(0).getBusinessSupDeptId());
            	} else if("10".equals(department.getRelations().get(0).getOrgType())) {
                    adjustModel.setDepartmentId(department.getRelations().get(0).getBusinessDeptId());

            	}
            }
        }
        List<PowerAdjust> adjusts = adjustService.listByPage(dm, adjustModel);
        Long total = adjustService.listCount(adjustModel);
        
        List<PowerAdjustModel> adjustModelds = new ArrayList<PowerAdjustModel>();
        PowerAdjustModel powerAdjustModel = null;
        
        List<Map<String, Object>> stateList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_ADJUST_STATE");
        
        for(int i = 0; i < adjusts.size(); i++) {
            powerAdjustModel = new PowerAdjustModel();
            BeanUtils.copyProperties(adjusts.get(i), powerAdjustModel);
            
            powerAdjustModel.setUserName(adjusts.get(i).getUser().getUserName());
            powerAdjustModel.setUserId(adjusts.get(i).getUser().getUuid());
            
            powerAdjustModel.setDepartmentName(adjusts.get(i).getSubmitDepartment().getName());
            powerAdjustModel.setDepartmentId(adjusts.get(i).getSubmitDepartment().getId());
            
            powerAdjustModel.setCreateDateStr(TeeDateUtil.format(adjusts.get(i).getCreateDate(), "yyyy-MM-dd"));

            JSONObject examineInfo = adjustAuthorityService.getExamineInfoById(powerAdjustModel.getId());
           
            for(Map<String, Object> code : stateList) {
                if(adjusts.get(i).getCurrentStatus().equals(code.get("codeNo").toString())) {
                    String state = code.get("codeName").toString();
                    state = state + 
                            "（通过：" + examineInfo.getInt("pass") + "；" +
                            "未通过：" + examineInfo.getInt("fail") + "；" +
                            "建议修改：" + examineInfo.getInt("edit") + "；" +
                            "未审核：" + examineInfo.getInt("none") + "；）";
                    powerAdjustModel.setCurrentStatus(state);
                    break;
                }
            }
            
            adjustModelds.add(powerAdjustModel);
        }
        
        datagrid.setRows(adjustModelds);
        datagrid.setTotal(total);
        
        return datagrid;
    }
    
    @ResponseBody
    @RequestMapping("/checkClosed")
    public TeeJson checkClosed(HttpServletRequest request) {
        TeeJson result = new TeeJson();
        
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        
        JSONObject examineInfo = adjustAuthorityService.getExamineInfoById(id);
        
        result.setRtData(examineInfo);
        result.setRtState(true);
        
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/getById")
    public TeeJson getById(HttpServletRequest request) {
        TeeJson result = new TeeJson();
        
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        Integer prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"), 0);
        String prcsName = TeeStringUtil.getString(request.getParameter("prcsName"), "");
        String isFineshed = TeeStringUtil.getString(request.getParameter("isFineshed"), "");
        
        PowerAdjust adjust = adjustService.getById(id);
        //获取审核环节列表,根据审核日期从大到小排列
        List<PowerAdjustTache> adjustTaches = adjustTacheService.getByAdjustId(adjust.getId());
        PowerAdjustModel adjustModel = new PowerAdjustModel();
        
        BeanUtils.copyProperties(adjust, adjustModel);
        
        adjustModel.setUserId(adjust.getUser().getUuid());
        adjustModel.setUserName(adjust.getUser().getUserName());
        adjustModel.setDepartmentId(adjust.getSubmitDepartment().getId());
        adjustModel.setDepartmentName(adjust.getSubmitDepartment().getName());
        adjustModel.setCreateDateStr(TeeDateUtil.format(adjust.getCreateDate(), "yyyy-MM-dd"));
        adjustModel.setAdjustReasonShow(adjust.getAdjustReason());
        
        PowerAdjustTacheModel adjustTacheModel = null;
        if(adjustTaches != null && adjustTaches.size() != 0) {
            //操作记录中最后一个环节对照
            PowerAdjustTache adjustTache = adjustTaches.get(0);
            adjustTacheModel = new PowerAdjustTacheModel();
            if("true".equals(isFineshed)) {
                BeanUtils.copyProperties(adjustTache, adjustTacheModel);
                adjustTacheModel.setExaminePersonId(adjustTache.getExaminePerson().getUuid());
                adjustTacheModel.setExaminePersonName(adjustTache.getExaminePerson().getUserName());
                adjustTacheModel.setExamineDateStr(TeeDateUtil.format(adjustTache.getExamineDate(), "yyyy-MM-dd HH:mm"));
                adjustTacheModel.setClosedDateStr(TeeDateUtil.format(adjustTache.getClosedDate(), "yyyy-MM-dd HH:mm"));
                adjustTacheModel.setExamineView(adjustTache.getExamineView());
            } else {
                if(adjustTache.getTacheId() == prcsId) {
                    // 相同为本环节记录,或本环节已经办理完毕
                    BeanUtils.copyProperties(adjustTache, adjustTacheModel);
                    adjustTacheModel.setExaminePersonId(adjustTache.getExaminePerson().getUuid());
                    adjustTacheModel.setExaminePersonName(adjustTache.getExaminePerson().getUserName());
                    adjustTacheModel.setExamineDateStr(TeeDateUtil.format(adjustTache.getExamineDate(), "yyyy-MM-dd HH:mm"));
                    adjustTacheModel.setClosedDateStr(TeeDateUtil.format(adjustTache.getClosedDate(), "yyyy-MM-dd HH:mm"));
                    adjustTacheModel.setExamineView(adjustTache.getExamineView());
                } else {
                    // 当前环节未开始
                    PowerAdjustTache powerAdjustTache = new PowerAdjustTache();
                    
                    powerAdjustTache.setId(UUID.randomUUID().toString());
                    powerAdjustTache.setPowerAdjustTache(adjust);
                    
                    adjustTacheModel = new PowerAdjustTacheModel();
                    powerAdjustTache.setTacheId(prcsId);
                    powerAdjustTache.setTacheName(prcsName);
                    
                    adjustTacheModel.setId(powerAdjustTache.getId());
                    adjustTacheModel.setTacheId(prcsId);
                    adjustTacheModel.setTacheName(prcsName);
                    
                    powerAdjustTache.setExaminePerson(person);
                    adjustTacheModel.setExaminePersonId(person.getUuid());
                    adjustTacheModel.setExaminePersonName(person.getUserName());
                    
                    powerAdjustTache.setExamineDate(new Date());
                    adjustTacheModel.setExamineDateStr(TeeDateUtil.format(powerAdjustTache.getExamineDate(), "yyyy-MM-dd HH:mm"));
                    
                    adjustTacheService.saveOrUpdate(powerAdjustTache);
                }
            }
            
        } else {
            PowerAdjustTache powerAdjustTache = new PowerAdjustTache();
            
            powerAdjustTache.setId(UUID.randomUUID().toString());
            powerAdjustTache.setPowerAdjustTache(adjust);
            
            adjustTacheModel = new PowerAdjustTacheModel();
            
            adjustTacheModel.setId(powerAdjustTache.getId());
            powerAdjustTache.setTacheId(prcsId);
            powerAdjustTache.setTacheName(prcsName);
            
            adjustTacheModel.setTacheId(prcsId);
            adjustTacheModel.setTacheName(prcsName);
            
            powerAdjustTache.setExaminePerson(person);
            adjustTacheModel.setExaminePersonId(person.getUuid());
            adjustTacheModel.setExaminePersonName(person.getUserName());
            
            powerAdjustTache.setExamineDate(new Date());
            adjustTacheModel.setExamineDateStr(TeeDateUtil.format(powerAdjustTache.getExamineDate(), "yyyy-MM-dd HH:mm"));
            
            adjustTacheService.saveOrUpdate(powerAdjustTache);
        }
        
        result.setRtState(true);
        JSONObject object = new JSONObject();
        object.put("adjustModel", adjustModel);
        object.put("adjustTacheModel", adjustTacheModel);
        
        result.setRtData(object);
        
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/getPowersByAdjustId")
    public TeeEasyuiDataGridJson getPowersByAdjustId(TeeDataGridModel dm, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        
        List<PowerAdjustAuthority> adjustAuthorities = adjustAuthorityService.listByPage(dm, id);
        Long total = adjustAuthorityService.listCount(id);
        
        PowerAdjustAuthorityModel adjustAuthorityModel = null;
        List<PowerAdjustAuthorityModel> adjustAuthorityModels = new ArrayList<PowerAdjustAuthorityModel>();
        
        List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
        List<Map<String, Object>> operationTypeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_OPERATION");
        List<Map<String, Object>> examineList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_EXAMINE");
        
        for(int i = 0; i < adjustAuthorities.size(); i++) {
            adjustAuthorityModel = new PowerAdjustAuthorityModel();
            PowerAdjustAuthority adjustAuthority = adjustAuthorities.get(i);
            
            BeanUtils.copyProperties(adjustAuthority, adjustAuthorityModel);
            PowerTemp powerTemp = adjustAuthority.getPowerTemp();
            
            
            adjustAuthorityModel.setPowerId(powerTemp.getId());
            adjustAuthorityModel.setPowerName(powerTemp.getName());
           
            for(Map<String, Object> code : codeList) {
                if(powerTemp.getPowerType().equals(code.get("codeNo").toString())) {
                    adjustAuthorityModel.setPowerType(code.get("codeName").toString());
                    break;
                }
            }
            for(Map<String, Object> examine : examineList) {
                if(adjustAuthority.getExamineState().equals(examine.get("codeNo").toString())) {
                    adjustAuthorityModel.setExamineState(examine.get("codeNo").toString());
                    adjustAuthorityModel.setExamineStateStr(examine.get("codeName").toString());
                    break;
                }
            }
            
            if(powerTemp.getOperationTemps() != null && powerTemp.getOperationTemps().size() > 0) {
                for(int j = 0; j < powerTemp.getOperationTemps().size(); j++) {
                    String optType = powerTemp.getOperationTemps().get(j).getOptType();
                    for(Map<String, Object> code : operationTypeList) {
                        if(optType.equals(code.get("codeNo").toString())) {
                            adjustAuthorityModel.setPowerOptType(code.get("codeName").toString());
                            break;
                        }
                    }
                }
            }
            
            adjustAuthorityModels.add(adjustAuthorityModel);
        }
        
        gridJson.setRows(adjustAuthorityModels);
        gridJson.setTotal(total);
        
        return gridJson;
    }
    
    @ResponseBody
    @RequestMapping("/bindRunId")
    public TeeJson bindRunId(HttpServletRequest request) {
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
        
        TeeJson result = new TeeJson();
        
        adjustService.bindRunId(id, runId);
        
        result.setRtState(true);
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/saveTache")
    public TeeJson saveTache(HttpServletRequest request, PowerAdjustTacheModel adjustTacheModel) {
        TeeJson result = new TeeJson();
        
        adjustTacheService.saveTache(adjustTacheModel);
        
        result.setRtState(true);
        return result;
    }
    
    @RequestMapping("/showExamineHis")
    public void showExamineHis(HttpServletRequest request, HttpServletResponse response) {
        try {
            String adjustId = TeeStringUtil.getString(request.getParameter("adjustId"), "");
            request.setAttribute("adjustId", adjustId);
            request.getRequestDispatcher("/supervise/power/power_examine_show.jsp").forward(request, response);
        }catch (Exception e) {
            // TODO: handle exception
        }
    }

    @RequestMapping("/showTacheInfo")
    public void showTacheInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            String tacheId = TeeStringUtil.getString(request.getParameter("tacheId"), "");
            request.setAttribute("tacheId", tacheId);
            request.getRequestDispatcher("/supervise/power/power_examine_show_tache.jsp").forward(request, response);
        }catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    @ResponseBody
    @RequestMapping("/tacheListByPage")
    public TeeEasyuiDataGridJson tacheListByPage(TeeDataGridModel dm, HttpServletRequest request) {
        TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
        
        String tacheId = TeeStringUtil.getString(request.getParameter("tacheId"), "");
        
        List<PowerAdjustTacheInfo> adjustTacheInfos = adjustTacheInfoService.listByPage(dm, tacheId);
//        Long total = adjustTacheInfoService.listCount(tacheId);
        
        List<PowerAdjustTacheInfoModel> adjustTacheInfoModels = new ArrayList<PowerAdjustTacheInfoModel>();
        PowerAdjustTacheInfoModel adjustTacheInfoModel = null;
        List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
        List<Map<String, Object>> operationTypeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_OPERATION");
        List<Map<String, Object>> examineList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_EXAMINE");
        
        for(int i = 0; i < adjustTacheInfos.size(); i++) {
            adjustTacheInfoModel = new PowerAdjustTacheInfoModel();
            PowerAdjustTacheInfo adjustTacheInfo = adjustTacheInfos.get(i);
            
            BeanUtils.copyProperties(adjustTacheInfo, adjustTacheInfoModel);
            PowerTemp powerTemp = adjustTacheInfo.getPowerTemp();
            
            adjustTacheInfoModel.setPowerId(powerTemp.getId());
            adjustTacheInfoModel.setPowerName(powerTemp.getName());
           
            for(Map<String, Object> code : codeList) {
                if(powerTemp.getPowerType().equals(code.get("codeNo").toString())) {
                    adjustTacheInfoModel.setPowerType(code.get("codeName").toString());
                    break;
                }
            }
            for(Map<String, Object> examine : examineList) {
                if(adjustTacheInfo.getExamineState().equals(examine.get("codeNo").toString())) {
                    adjustTacheInfoModel.setExamineState(examine.get("codeName").toString());
                    break;
                }
            }
            
            if(powerTemp.getOperationTemps() != null && powerTemp.getOperationTemps().size() > 0) {
                for(int j = 0; j < powerTemp.getOperationTemps().size(); j++) {
                    String optType = powerTemp.getOperationTemps().get(j).getOptType();
                    for(Map<String, Object> code : operationTypeList) {
                        if(optType.equals(code.get("codeNo").toString())) {
                            adjustTacheInfoModel.setPowerOpt(code.get("codeName").toString());
                            break;
                        }
                    }
                }
            }
            
            adjustTacheInfoModels.add(adjustTacheInfoModel);
        }
        
        datagrid.setRows(adjustTacheInfoModels);
//        datagrid.setTotal(total);
        
        return datagrid;
    }
    
    @ResponseBody
    @RequestMapping("/examineList")
    public TeeEasyuiDataGridJson examineList(HttpServletRequest request, TeeDataGridModel dm) {
        TeeEasyuiDataGridJson gridjson = new TeeEasyuiDataGridJson();
        String adjustId = TeeStringUtil.getString(request.getParameter("adjustId"), "");
        
        List<PowerAdjustTache> adjustTaches = adjustTacheService.listByPage(dm, adjustId);
//        Long total = adjustTacheService.listCount(adjustId);
        
        List<PowerAdjustTacheModel> adjustTacheModels = new ArrayList<PowerAdjustTacheModel>();
        PowerAdjustTacheModel adjustTacheModel = null;
        for(int i = 0; i < adjustTaches.size(); i++) {
            PowerAdjustTache adjustTache = adjustTaches.get(i);
            adjustTacheModel = new PowerAdjustTacheModel();
            BeanUtils.copyProperties(adjustTache, adjustTacheModel);
            
            adjustTacheModel.setExamineDateStr(TeeDateUtil.format(adjustTache.getExamineDate(), "yyyy-MM-dd HH:mm"));
            adjustTacheModel.setClosedDateStr(TeeDateUtil.format(adjustTache.getClosedDate(), "yyyy-MM-dd HH:mm"));
            
            adjustTacheModel.setExaminePersonId(adjustTache.getExaminePerson().getUuid());
            adjustTacheModel.setExaminePersonName(adjustTache.getExaminePerson().getUserName());
            
            adjustTacheModels.add(adjustTacheModel);

        }
        
        gridjson.setRows(adjustTacheModels);
//        gridjson.setTotal(total);
        
        return gridjson;
    }
    
    
    @ResponseBody
    @RequestMapping("/turnNextTache")
    public TeeJson turnNextTache(HttpServletRequest request, PowerAdjustTacheModel adjustTacheModel) {
        TeeJson result = new TeeJson();
        
        String tacheId = adjustTacheModel.getId();
        
        List<PowerAdjustTacheInfoModel> adjustTacheInfoModels = adjustTacheInfoService.getTacheInfos(tacheId);
        PowerAdjustTacheInfo adjustTacheInfo = null;
        List<PowerAdjustTacheInfo> adjustTacheInfos = new ArrayList<PowerAdjustTacheInfo>();
        for(int i = 0; i < adjustTacheInfoModels.size(); i++) {
            adjustTacheInfoModels.get(i).setId(UUID.randomUUID().toString());
            adjustTacheInfo = new PowerAdjustTacheInfo();
            
            BeanUtils.copyProperties(adjustTacheInfoModels.get(i), adjustTacheInfo);
            
            // 初始化职权信息
            PowerTemp powerTemp = new PowerTemp();
            powerTemp.setId(adjustTacheInfoModels.get(i).getPowerId());
            
            adjustTacheInfo.setPowerTemp(powerTemp);
            // 初始化环节信息
            PowerAdjustTache adjustTache = new PowerAdjustTache();
            adjustTache.setId(adjustTacheInfoModels.get(i).getTacheId());
            adjustTacheInfo.setAdjustTache(adjustTache);
            // 初始化批次信息
            PowerAdjust adjust = new PowerAdjust();
            adjust.setId(adjustTacheInfoModels.get(i).getAdjustId());
            adjustTacheInfo.setAdjustTacheInfo(adjust);
            
            adjustTacheInfos.add(adjustTacheInfo);
        }
        adjustTacheService.saveTache(adjustTacheModel);
        adjustTacheInfoService.batchSave(adjustTacheInfos);
        
        result.setRtState(true);
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/examinePower")
    public TeeJson examinePower(HttpServletRequest request) {
        TeeJson result = new TeeJson();
        
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        String examineState = TeeStringUtil.getString(request.getParameter("examineState"), "");
        
        adjustAuthorityService.examinePower(id, examineState);
        
        result.setRtState(true);
        
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/closedFlow")
    public TeeJson closedFlow(HttpServletRequest request, PowerAdjustTacheModel adjustTacheModel) {
        TeeJson result = new TeeJson();
        
        String tacheId = adjustTacheModel.getId();
        
        List<PowerAdjustTacheInfoModel> adjustTacheInfoModels = adjustTacheInfoService.getTacheInfos(tacheId);
        PowerAdjustTacheInfo adjustTacheInfo = null;
        List<PowerAdjustTacheInfo> adjustTacheInfos = new ArrayList<PowerAdjustTacheInfo>();
        for(int i = 0; i < adjustTacheInfoModels.size(); i++) {
            adjustTacheInfoModels.get(i).setId(UUID.randomUUID().toString());
            adjustTacheInfo = new PowerAdjustTacheInfo();
            
            BeanUtils.copyProperties(adjustTacheInfoModels.get(i), adjustTacheInfo);
            
            // 初始化职权信息
            PowerTemp powerTemp = new PowerTemp();
            powerTemp.setId(adjustTacheInfoModels.get(i).getPowerId());
            
            adjustTacheInfo.setPowerTemp(powerTemp);
            // 初始化环节信息
            PowerAdjustTache adjustTache = new PowerAdjustTache();
            adjustTache.setId(adjustTacheInfoModels.get(i).getTacheId());
            adjustTacheInfo.setAdjustTache(adjustTache);
            // 初始化批次信息
            PowerAdjust adjust = new PowerAdjust();
            adjust.setId(adjustTacheInfoModels.get(i).getAdjustId());
            adjustTacheInfo.setAdjustTacheInfo(adjust);
            
            adjustTacheInfos.add(adjustTacheInfo);
        }
        PowerAdjust powerAdjust = new PowerAdjust();
        powerAdjust.setId(adjustTacheModel.getAdjustId());
        powerAdjust.setCurrentStatus("20");
        
        adjustService.closedAdjust(powerAdjust);
        adjustTacheService.saveTache(adjustTacheModel);
        adjustTacheInfoService.batchSave(adjustTacheInfos);
        
        result.setRtState(true);
        return result;
    }
}
