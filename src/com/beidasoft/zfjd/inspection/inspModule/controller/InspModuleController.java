package com.beidasoft.zfjd.inspection.inspModule.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.department.service.TblDepartmentService;
import com.beidasoft.zfjd.inspection.inspModule.bean.InspModule;
import com.beidasoft.zfjd.inspection.inspModule.model.InspModuleModel;
import com.beidasoft.zfjd.inspection.inspModule.service.InspModuleService;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 职权基础信息表CONTROLLER类
 */
@Controller
@RequestMapping("inspectionCtrl")
public class InspModuleController {

    @Autowired
    private InspModuleService inspectionService;
    @Autowired
    private TblDepartmentService departmentService;
    
    

    /**
     * 分页获取检查单模版列表
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel, InspModuleModel queryModel) {
        TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
        long total = inspectionService.getTotal(queryModel);
        
        List<InspModuleModel> modelList = new ArrayList<>();
        List<InspModule> inspectionInfos = inspectionService.listByPage(dataGridModel.getFirstResult(),
                dataGridModel.getRows(), queryModel);
        if(inspectionInfos!=null){
            for (InspModule userInfo : inspectionInfos) {
                InspModuleModel infoModel = new InspModuleModel();
                BeanUtils.copyProperties(userInfo, infoModel);
                infoModel.setOrgSys(
                        TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD", userInfo.getOrgSys()));
                modelList.add(infoModel);
            }
        }
        dataGridJson.setTotal(total);
        dataGridJson.setRows(modelList);
        return dataGridJson;
    }
    /**
     * 保存检查单模版信息
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("save")
    public TeeJson save(InspModuleModel inspectionModel) {
        TeeJson json = new TeeJson();
        // 获取当前登录人
        TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
        // 当前登录人所在的部门
        TeeDepartment department = user.getDept();
        // 部门ID
        int deptId = department.getUuid();

        // 通过部门ID，查询到SysBusinessRelation对象（）
        SysBusinessRelation sysBusinessRelation = inspectionService.getSysBusinessRelationByDeptId(deptId);
        // 获取到机构ID
        String businessDeptId = sysBusinessRelation.getBusinessDeptId();
        String subjectId = sysBusinessRelation.getBusinessSubjectId();
        // 创建实例化实体类对象
        InspModule inspection = new InspModule();
        BeanUtils.copyProperties(inspectionModel, inspection);

        inspection.setId(UUID.randomUUID().toString());
        inspection.setModuleName(inspectionModel.getModuleName());
        ;
        inspection.setOrganizationId(businessDeptId);
        inspection.setIsDelete(0);

        Date now = new Date();
        inspection.setCreateTime(now);

        inspection.setSubject(subjectId);
        inspection.setOrgSys(inspectionModel.getOrgSys());
        // 设置主体信息
        /* inspection.setModuleName(user.getm); */
        inspectionService.save(inspection);

        json.setRtState(true);
        return json;
    }

    /**
     * 根据当前登录人，获取他有权限选择的执法系统
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("getOrgSystemByCurrentPerson.action")
    public List<Map<?,?>> getOrgSystemByCurrentPerson() {
        // 获取当前登录人
        TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
        // 当前登录人所在的部门
        TeeDepartment department = user.getDept();
        // 部门ID
        int deptId = department.getUuid();

        // 通过部门ID，查询到SysBusinessRelation对象（）
        SysBusinessRelation sysBusinessRelation = inspectionService.getSysBusinessRelationByDeptId(deptId);
        // 获取到机构ID
        String businessDeptId = sysBusinessRelation.getBusinessDeptId();

        // 根据businessDeptId，找到机构对象
        TblDepartmentInfo departmentInfo = departmentService.getById(businessDeptId);

        // 获取执法系统code，逗号分隔形式01,02,03
        String orgSys = departmentInfo.getOrgSys();

        if (orgSys == null || "".equals(orgSys)) {
            return null;
        }
        String orgSysSplited[] = orgSys.split(",");

        List<Map<?,?>> sysCodes = new ArrayList<>();
        for (String code : orgSysSplited) {
            Map<String,Object> codeMap = new HashMap<>();
            codeMap.put("id", code);
            codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD", code));
            sysCodes.add(codeMap);
        }
        return sysCodes;
    }

    /**
     * 通过id获取检查单模版信息
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("get")
    public TeeJson get(String id) {
        TeeJson json = new TeeJson();
        InspModule userInfo = inspectionService.getById(id);

        InspModuleModel infoModel = new InspModuleModel();
        BeanUtils.copyProperties(userInfo, infoModel);

        infoModel.setId(userInfo.getId());
        infoModel.setModuleName(userInfo.getModuleName());
        infoModel.setIsDelete(0);

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String nowtime = dateFormat.format(now);
        infoModel.setCreateTimeStr(nowtime);
        infoModel.setOrgSys(userInfo.getOrgSys());
        json.setRtData(infoModel);
        json.setRtState(true);
        return json;
    }

    /**
     * 打开检查单模版编辑页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/moduleInput")
    public void moduleInput(InspModuleModel inspectionModel, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            InspModule inspection = null;
            if (inspectionModel.getId() != null && !"".equals(inspectionModel.getId())) {
                inspection = inspectionService.getById(inspectionModel.getId());
            }
            request.setAttribute("inspInfo", inspection);
            request.getRequestDispatcher("/supervise/inspection/inspModule/inspection_add.jsp").forward(request, response);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    /**
     * 通过名称，检索适应部门
     *
     * @param q
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSysCodeTemp.action")
    public List<TblDepartmentInfo> getSysCodeTemp(String q, HttpServletRequest request) {
        TblDepartmentModel departmentModel = new TblDepartmentModel();
        departmentModel.setName(q);
        List<TblDepartmentInfo> codeList = departmentService.listByDe(0, 10, departmentModel);
        return codeList;
    }

    /**
     * 跟新检查模块数据
     *
     * @param inspectionModel
     * @return
     */
    @ResponseBody
    @RequestMapping("update")
    public TeeJson update(InspModuleModel inspectionModel) {
        TeeJson json = new TeeJson();

//        Date now = new Date();
        InspModule userInfo = inspectionService.getById(inspectionModel.getId());
        userInfo.setModuleName(inspectionModel.getModuleName());
        userInfo.setOrgSys(inspectionModel.getOrgSys());
        inspectionService.update(userInfo);
        
        json.setRtState(true);
        return json;
    }
    /**
     * @author lrn
     * @description 超链接删除检查模块
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public TeeJson delete(String id) {
        TeeJson json = new TeeJson();
        InspModule inspection = new InspModule();
        inspection = inspectionService.getById(id);
        inspection.setIsDelete(1);

        try {
            inspectionService.InspModeldel(inspection);
            json.setRtState(true);
        } catch (Exception e) {
        }
        return json;
    }
    /**
     * @author lrn
     * @description 多删除检查模块
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("deletes")
    public TeeJson deletes(HttpServletRequest request) {

        String id = request.getParameter("ids");
        TeeJson json = new TeeJson();
        inspectionService.deletes(id);
        json.setRtState(true);
        return null;
    }

    /**
     * 根据执法系统获取检查模块列表
     *
     * @param id
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping("/getInspModulesByOrgSys.action")
    public JSONArray getInspModulesByOrgSys(String orgSys, HttpServletRequest request) {

//        TeeJson json = new TeeJson();
        InspModuleModel model = new InspModuleModel();
        model.setOrgSys(orgSys);

        List<InspModule> moduleList = inspectionService.listByOrgSysCode(model);
        // list转jsonArray
        JsonConfig jsonConfig = new JsonConfig();
        // jsonConfig.setExcludes(new String[]{"caseCommonCaseSource"});
        // jsonConfig.setIgnoreDefaultExcludes(false); //设置默认忽略
        // jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray moduleJSONArray = JSONArray.fromObject(moduleList, jsonConfig);
        return moduleJSONArray;
    }
    
}



