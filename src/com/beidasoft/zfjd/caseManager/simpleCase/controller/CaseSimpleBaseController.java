package com.beidasoft.zfjd.caseManager.simpleCase.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimpleBaseModel;
import com.beidasoft.zfjd.caseManager.simpleCase.service.CaseSimpleBaseService;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("caseSimpleBaseCtrl")
public class CaseSimpleBaseController {
    @Autowired
    private CaseSimpleBaseService baseService;
    @Autowired
    private TeeDeptService deptService;
    @Autowired
    private TeePersonService personService;
    
    /**
     * 
    * @Function: simpleCaseIndex()
    * @Description: 简易案件填报首页
    *
    * @param: request
    * @param: response
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月16日 下午3:24:48 
    *
     */
    @RequestMapping("simpleCaseIndex")
    public void simpleCaseIndex(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            boolean deptIdExists = false; //执法部门存在
            boolean subIdExists = false; //执法主体存在
            boolean supDeptIdExists = false; //监督部门存在
            TeePerson personSession = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(personSession.getDept().getUuid(), ""));
            TeePerson person = personService.selectByUuid(personSession.getUuid());
            
            List<SysBusinessRelation> relations = department.getRelations();
            if (relations != null && relations.size() > 0) {
                for (SysBusinessRelation relation : relations) {
                    if (!TeeUtility.isNullorEmpty(relation.getBusinessDeptId())) {
                        deptIdExists = true;
                    }
                    if (!TeeUtility.isNullorEmpty(relation.getBusinessSubjectId())) {
                        subIdExists = true;
                    }
                    if (!TeeUtility.isNullorEmpty(relation.getBusinessSupDeptId())) {
                        supDeptIdExists = true;
                    }
                }
            }
           //权限组
            String menuGroupStrNames = "";
            List<TeeMenuGroup> menuGroupList = person.getMenuGroups();
            if(menuGroupList != null  && menuGroupList.size() > 0){//辅助权限
                for (int i = 0; i < menuGroupList.size(); i++) {
                    menuGroupStrNames = menuGroupStrNames + menuGroupList.get(i).getMenuGroupName() +",";
                }
                menuGroupStrNames = menuGroupStrNames.substring(0, menuGroupStrNames.length() - 1);
            }
            request.setAttribute("deptIdExists", deptIdExists); //执法部门
            request.setAttribute("subIdExists", subIdExists); //执法主体
            request.setAttribute("supDeptIdExists", supDeptIdExists);//监督部门
            request.setAttribute("menuGroupStrNames", menuGroupStrNames);//权限名称
            request.getRequestDispatcher("/supervise/caseManager/simpleCase/case_simple_index.jsp").forward(request, response);
        }catch (Exception e) {
            System.out.println("CaseSimpleBaseController.simpleCaseIndex() " + e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: simpleCaseSearchIndex()
    * @Description: 简易案件查询首页
    *
    * @param: request
    * @param: response
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月16日 下午3:25:53 
    *
     */
    @RequestMapping("simpleCaseSearchIndex")
    public void simpleCaseSearchIndex(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            
            TeePerson personSession = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            TeePerson person = personService.selectByUuid(personSession.getUuid());
           //权限组
            String menuGroupStrNames = "";
            List<TeeMenuGroup> menuGroupList = person.getMenuGroups();
            if(menuGroupList != null  && menuGroupList.size() > 0){//辅助权限
                for (int i = 0; i < menuGroupList.size(); i++) {
                    menuGroupStrNames = menuGroupStrNames + menuGroupList.get(i).getMenuGroupName() +",";
                }
                menuGroupStrNames = menuGroupStrNames.substring(0, menuGroupStrNames.length() - 1);
            }
            request.setAttribute("menuGroupStrNames", menuGroupStrNames);//权限名称
            request.getRequestDispatcher("/supervise/caseManager/simpleCase/case_simple_search_index.jsp").forward(request, response);
        }catch (Exception e) {
            System.out.println("CaseSimpleBaseController.simpleCaseSearchIndex() " + e.getMessage());
        }
    }
    
    /**
     ** 分页查询
     * 
     * @param tModel
     * @param cbModel
     * @param request
     * @return TeeEasyuiDataGridJson
     */
    @ResponseBody
    @RequestMapping("/findListBypage.action")
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel,
            CaseSimpleBaseModel cbModel, HttpServletRequest request) {
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
        String subjectIds = "";
        String menuNames =TeeStringUtil.getString(request.getParameter("menuNames"));
        if(!"".equals(menuNames) && !menuNames.contains("系统管理权限组")) {
            if(department.getRelations() != null && department.getRelations().size() != 0) {
                if(department.getRelations().size() == 1) {
                    subjectIds = department.getRelations().get(0).getBusinessSubjectId();
                    cbModel.setSubjectId(subjectIds);
                } else {
                    for(int i = 0; i < department.getRelations().size(); i++) {
                        subjectIds = subjectIds + department.getRelations().get(i).getBusinessSubjectId() + ",";
                    }
                    subjectIds = subjectIds.substring(0, subjectIds.length() - 1);
                    cbModel.setSubjectIds(subjectIds);
                }
            }
        }
        TeeEasyuiDataGridJson json = baseService.findListBypage(tModel,
                cbModel, request);
        return json;
    }
    
    /**
     ** 简易查询功能分页查询
     * 
     * @param tModel
     * @param cbModel
     * @param request
     * @return TeeEasyuiDataGridJson
     */
    @ResponseBody
    @RequestMapping("/findSearchListBypage.action")
    public TeeEasyuiDataGridJson findSearchListBypage(TeeDataGridModel tModel,CaseSimpleBaseModel cbModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson json = baseService.findSearchListBypage(tModel,cbModel, request);
        return json;
    }

    /**
     ** 生成案件的caseId
     * 
     * @param request
     * @return TeeJson
     */
    @ResponseBody
    @RequestMapping("/initCaseId.action")
    public TeeJson initCaseId(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        json.setRtData(UUID.randomUUID().toString());
        json.setRtState(true);
        return json;
    }

    /**
     ** 保存
     * 
     * @param request
     * @return TeeJson
     */
    @ResponseBody
    @RequestMapping("/saveSimpleBase.action")
    public TeeJson saveSimpleBase(HttpServletRequest request,CaseSimpleBaseModel baseModel) {
        TeeJson json = baseService.saveSimpleBase(request, baseModel);
        return json;
    }
    
    /**
     ** 修改
     * 
     * @param request
     * @return TeeJson
     */
    @ResponseBody
    @RequestMapping("/updateSimpleBase.action")
    public TeeJson updateSimpleBase(HttpServletRequest request,CaseSimpleBaseModel baseModel) {
        TeeJson json = baseService.updateSimpleBase(request, baseModel);
        return json;
    }
    /**
     ** 逻辑删除
     * 
     * @param request
     * @return TeeJson
     */
    @ResponseBody
    @RequestMapping("/updateDelete.action")
    public TeeJson updateDelete(String id){
        TeeJson updateDelete = baseService.updateDelete(id);
        return updateDelete;
    }
    /**
     ** 修改和查询跳转方法
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/simpleBaseAdd")
    public void commonCaseAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setAttribute("caseId", request.getParameter("id"));//获取id
        request.setAttribute("editFlag", TeeStringUtil.getInteger(request.getParameter("editFlag"), 0));
        request.setAttribute("modelId", TeeStringUtil.getInteger(request.getParameter("modelId"), 0));
        request.getRequestDispatcher("/supervise/caseManager/simpleCase/case_simple_add.jsp").forward(request, response);
    }
    /**
     ** 回写数据
     * @param request
     * @return TeeJson
     */
    @ResponseBody
    @RequestMapping("/findSimpleBaseById.action")
    public TeeJson findSimpleBaseById(HttpServletRequest request,String id){
        TeeJson json = baseService.findSimpleBaseById(request, id);
        return json;
    }
    
    @ResponseBody
    @RequestMapping("/simpleBaseSubmit.action")
    public TeeJson simpleBaseSubmit(HttpServletRequest request,String baseIds){
        TeeJson json = baseService.simpleBaseSubmit(baseIds);
        return json;
    }
    
    /**
     * 
    * @Function: updateOrdeleteOrSubmit()
    * @Description: 提交，退回，删除方法
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月28日 下午4:37:50 
    *
     */
    @ResponseBody
    @RequestMapping("updateOrdeleteOrSubmit")
    public TeeJson updateOrdeleteOrSubmit(CaseSimpleBaseModel cbModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            baseService.updateOrdeleteOrSubmitByIds(cbModel);
            json.setRtState(true);
        } catch (Exception e) {
            json.setRtState(false);
            System.out.println("CaseSimpleBaseController.updateOrdeleteOrSubmit() "+ e.getMessage());
        }
        return json;
    }
    
}
