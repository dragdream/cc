package com.beidasoft.zfjd.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.service.SubjectService;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.beidasoft.zfjd.system.model.SysBusinessRelationModel;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.service.TblDepartmentService;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/commonCtrl")
public class CommonController {

    @Autowired
    private TeeDeptService deptService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TeeAttachmentService attachmentService;

    @Autowired
    private TblDepartmentService tblDepartmentService;

    @Autowired
    private TeePersonService personService;

    @Autowired
    private SubjectService subjectService;

    @ResponseBody
    @RequestMapping("/getRelations.action")
    public TeeJson getRelationSubject(HttpServletRequest request) {
        TeeJson result = new TeeJson();

        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeDepartment department = deptService
                .selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));

        List<SysBusinessRelation> relations = new ArrayList<SysBusinessRelation>();
        SysBusinessRelation relation = null;
        for (int i = 0; i < department.getRelations().size(); i++) {
            relation = department.getRelations().get(i);
            relation.setDeptRelation(null);

            relations.add(relation);
        }

        result.setRtState(true);
        result.setRtData(relations);

        return result;
    }

    @ResponseBody
    @RequestMapping("/getRelationAndOrgSys.action")
    public JSONObject getRelationAndOrgSys(HttpServletRequest request) {
        JSONObject jsonObj = new JSONObject();

        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
        SysBusinessRelation relation = null;
        if (department.getRelations() != null && department.getRelations().size() > 0) {
            relation = department.getRelations().get(0);
        }else{
        	jsonObj.put("rtState", false);
        }
        String orgSys = "";
        if (relation != null) {
            if (relation.getBusinessSubjectId() != null && !"".equals(relation.getBusinessSubjectId())) {
                // 该账号为主体账号
                Subject subejct = subjectService.getById(relation.getBusinessSubjectId());
                if (subejct != null && subejct.getOrgSys() != null) {
                    orgSys = subejct.getOrgSys();
                }

            } else {
                if (relation.getBusinessDeptId() != null && !"".equals(relation.getBusinessDeptId())) {
                    // 该账号为部门账号
                    TblDepartmentInfo departmentInfo = tblDepartmentService.getById(relation.getBusinessDeptId());
                    if (departmentInfo != null && departmentInfo.getOrgSys() != null) {
                        orgSys = departmentInfo.getOrgSys();
                    }
                }
            }
        }
        SysBusinessRelationModel relationModel = new SysBusinessRelationModel();
        BeanUtils.copyProperties(relation, relationModel);
        jsonObj.put("rtState", true);
        jsonObj.put("relation", relationModel);
        jsonObj.put("orgSys", orgSys);

        return jsonObj;
    }

    @ResponseBody
    @RequestMapping("/getMenuGroupNames.action")
    public TeeJson getMenuGroupNames(HttpServletRequest request) {
        TeeJson result = new TeeJson();

        TeePerson personTemp = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeePerson person = personService.selectByUuid(personTemp.getUuid());

        // 菜单组
        String menuGroupStrNames = "";
        List<TeeMenuGroup> menuGroupList = person.getMenuGroups();

        if (menuGroupList != null && menuGroupList.size() > 0) {// 辅助角色
            for (int i = 0; i < menuGroupList.size(); i++) {
                menuGroupStrNames = menuGroupStrNames + menuGroupList.get(i).getMenuGroupName() + ",";
            }
            menuGroupStrNames = menuGroupStrNames.substring(0, menuGroupStrNames.length() - 1);
        }
        result.setRtState(true);
        result.setRtData(menuGroupStrNames);

        return result;
    }

    @RequestMapping("/varValue")
    @ResponseBody
    public TeeJson varValue(HttpServletRequest request) {
        TeeJson result = new TeeJson();
        int runId = TeeStringUtil.getInteger(request.getParameter("RUN_ID"), 0);
        String primaryId = TeeStringUtil.getString(request.getParameter("PRIMARY_ID"));
        String key = TeeStringUtil.getString(request.getParameter("KEY"));
        // 根据key和runId更新流程变量
        commonService.updateFlowRunVars(primaryId, runId, key);
        result.setRtState(true);
        return result;
    }

    /**
     * 获取附件列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/attachmentList")
    @ResponseBody
    public TeeJson getAttachmentList(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String model = TeeStringUtil.getString(request.getParameter("model"), "");
        String modelId = TeeStringUtil.getString(request.getParameter("modelId"), "");
        // 获取附件列表
        List<TeeAttachmentModel> attachmt = attachmentService.getAttacheModels(model, modelId);

        json.setRtData(attachmt);
        json.setRtState(true);
        return json;
    }
    
    /**
     * 获取附件列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/attachmentByIds")
    @ResponseBody
    public TeeJson attachmentByIds(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String ids = TeeStringUtil.getString(request.getParameter("ids"), "");
        // 获取附件列表
        List<TeeAttachmentModel> attachmt = attachmentService.getAttachmentModelsByIds(ids);

        json.setRtData(attachmt);
        json.setRtState(true);
        return json;
    }

    /**
     * 
     * @Function: findDepartment()
     * @Description: 通过名称查询部门信息
     *
     * @param: q
     *             搜索关键字参数
     * @return：返回json对象
     * @throws：异常描述
     *
     * @author: songff
     * @date: 2019年1月25日 下午2:50:20
     *
     */
    @RequestMapping("/findDepartment")
    @ResponseBody
    public JSONObject findDepartment(String q) {
        JSONObject json = new JSONObject();
        try {
            if (q != null && !"".equals(q.trim())) {
                List<TblDepartmentInfo> deptList = tblDepartmentService.findDepartment(q.trim());
                JSONArray jsonArray = new JSONArray();
                for (TblDepartmentInfo dept : deptList) {
                    JSONObject object = new JSONObject();
                    object.put("id", dept.getId());
                    object.put("name", dept.getName());
                    jsonArray.add(object);
                }
                json.put("data", jsonArray);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return json;
    }

    /**
     * 
     * @Function: CommonController.java
     * @Description: 通过ID查询部门名称
     *
     * @param: id
     *             部门ID参数
     * @return：返回json对象
     * @throws：异常描述
     *
     * @author: songff
     * @date: 2019年1月25日 下午2:50:03
     *
     */
    @RequestMapping("/findDepartmentById")
    @ResponseBody
    public TeeJson findDepartmentById(String id) {
        TeeJson json = new TeeJson();
        try {
            if (id != null && !"".equals(id.trim())) {
                JSONObject jsonObj = new JSONObject();
                TblDepartmentInfo dept = tblDepartmentService.getById(id);
                jsonObj.put("id", dept.getId());
                jsonObj.put("name", dept.getName());
                json.setRtData(jsonObj);
                json.setRtState(true);
            }
        } catch (Exception e) {
            json.setRtState(false);
            System.out.println(e.getMessage());
        }
        return json;
    }
}
