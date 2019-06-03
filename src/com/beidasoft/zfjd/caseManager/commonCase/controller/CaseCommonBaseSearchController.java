package com.beidasoft.zfjd.caseManager.commonCase.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonBaseModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonBaseSearchModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonDataModel;
import com.beidasoft.zfjd.caseManager.commonCase.service.CaseCommonBaseService;
import com.beidasoft.zfjd.caseManager.commonCase.service.CaseCommonDataService;
import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.model.SubjectModel;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: CaseCommonBaseSearchController.java
 * @Description: 一般案件综合查询CONTROLLER层
 *
 * @author: mixue
 * @date: 2019年3月15日 上午11:16:08
 */
@Controller
@RequestMapping("/caseCommonBaseSearchCtrl")
public class CaseCommonBaseSearchController {
    //获取日志记录器Logger，名字为本类类名
    //private static Logger logger = LogManager.getLogger(CaseCommonBaseController.class);
    
    @Autowired
    private CaseCommonBaseService caseCommonBaseService;
    @Autowired
    private TeeDeptService deptService;
    @Autowired
    private TeePersonService personService;
    @Autowired
    private CommonService commonservivse;
    
    @Autowired
    private CaseCommonDataService caseCommonDataService;

    /**
     * 
    * @Function: commonCaseIndex()
    * @Description: 新增弹框
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月12日 下午6:53:36 
    *
     */
    @RequestMapping("/commonCaseIndex")
    public void commonCaseIndex(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
            request.getRequestDispatcher("/supervise/caseManager/commonCaseSearch/common_case_search.jsp").forward(request, response);
        }catch (Exception e) {
            System.out.println("CaseCommonBaseController.commonCaseIndex() " + e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: commonCaseSearchIndex()
    * @Description: 一般案件查询默认首页
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月16日 上午10:57:18 
    *
     */
    @RequestMapping("/commonCaseSearchIndex")
    public void commonCaseSearchIndex(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
            request.getRequestDispatcher("/supervise/caseManager/commonCaseSearch/common_case_search_index.jsp").forward(request, response);
        }catch (Exception e) {
            System.out.println("CaseCommonBaseController.commonCaseSearchIndex() " + e.getMessage());
        }
    }
    /**
     * 
    * @Function: selectSubjectList()
    * @Description: 查询主体
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    * 
    * @author: songff
    * @date: 2019年2月15日 下午5:44:56 
    *
     */
    @ResponseBody
    @RequestMapping("/selectSubjectList")
    public TeeJson selectSubjectList(HttpServletRequest request, SubjectModel subjectModel) {
        TeeJson json = new TeeJson();
        JSONArray jsonArray = new JSONArray();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
            String subjectIds = "";
            String menuNames =TeeStringUtil.getString(request.getParameter("menuNames"));
            if(!"".equals(menuNames) && !menuNames.contains("系统管理权限组")) {
                if(department.getRelations() != null && department.getRelations().size() != 0) {
                    if(department.getRelations().size() == 1) {
                        subjectIds = department.getRelations().get(0).getBusinessSubjectId();
                        subjectModel.setId(subjectIds);
                    } else {
                        for(int i = 0; i < department.getRelations().size(); i++) {
                            subjectIds = subjectIds + department.getRelations().get(i).getBusinessSubjectId() + ",";
                        }
                        subjectIds = subjectIds.substring(0, subjectIds.length() - 1);
                        subjectModel.setIds(subjectIds);
                    }
                }
            }
            List<Subject> subjects = caseCommonBaseService.subjectList(subjectModel);
            if (subjects != null && subjects.size() > 0) {
                for (Subject sub : subjects) {
                    JSONObject object = new JSONObject();
                    object.put("codeNo", sub.getId());
                    object.put("codeName", sub.getSubName());
                    jsonArray.add(object);
                }
            }
            json.setRtData(jsonArray);
            json.setRtState(true);
        } catch (Exception e) {
            json.setRtState(false);
        }
        return json;
    }
    
    
    /**
     * 
    * @Function: selectSubjectList()
    * @Description: 查询主体
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月15日 下午5:44:56 
    *
     */
    @ResponseBody
    @RequestMapping("/selectDepartmentList")
    public TeeJson selectDepartmentList(HttpServletRequest request, TblDepartmentModel model) {
        TeeJson json = new TeeJson();
        JSONArray jsonArray = new JSONArray();
        try {
            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
            TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
            String deptIds = "";
            String menuNames =TeeStringUtil.getString(request.getParameter("menuNames"));
            if(!"".equals(menuNames) && !menuNames.contains("系统管理权限组")) {
                if(department.getRelations() != null && department.getRelations().size() != 0) {
                    if(department.getRelations().size() == 1) {
                        deptIds = department.getRelations().get(0).getBusinessSubjectId();
                        model.setId(deptIds);
                    } else {
                        for(int i = 0; i < department.getRelations().size(); i++) {
                            deptIds = deptIds + department.getRelations().get(i).getBusinessSubjectId() + ",";
                        }
                        deptIds = deptIds.substring(0, deptIds.length() - 1);
                        model.setIds(deptIds);
                    }
                }
            }
            List<TblDepartmentInfo> depts = caseCommonBaseService.findDepartmentList(model);
            if (depts != null && depts.size() > 0) {
                for (TblDepartmentInfo dept : depts) {
                    JSONObject object = new JSONObject();
                    object.put("codeNo", dept.getId());
                    object.put("codeName", dept.getName());
                    jsonArray.add(object);
                }
            }
            json.setRtData(jsonArray);
            json.setRtState(true);
        } catch (Exception e) {
            json.setRtState(false);
        }
        return json;
    }
    
    /**
     * 
    * @Function: findListBypage
    * @Description: 前端表格请求后台数据方法
    *
    * @param: tModel datagrid表格属性集合
    * @param: cBasic 查询条件参数
    * @param: request 容器
    * @return：返回json，传递至前台
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2018年12月26日 下午4:39:13 
    *
     */
    @ResponseBody
    @RequestMapping("/findListBypage.action")
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel, HttpServletRequest request) {
        //返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
        //bean对应的model cModels
        List<CaseCommonBaseSearchModel> cModels = new ArrayList<>(); 
        //查询集合总数
        Long count = null;
        try {
            /*TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
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
            }*/
            Map<String, Object> requestData = getRequestData(request);
            
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
            cModels = caseCommonBaseService.findListByPage(tModel, requestData, orgCtrl);
            count = caseCommonBaseService.listCount(requestData, orgCtrl);
            
            json.setRows(cModels);
            json.setTotal(count);
        } catch (Exception e) {
            System.out.println("CaseCommonBaseSearchController.findListBypage() " + e.getMessage());
            e.printStackTrace();
        }
        return json;
    }
    
    @ResponseBody
    @RequestMapping("/findListBypageSearch.action")
    public TeeEasyuiDataGridJson findListBypage2(TeeDataGridModel tModel, HttpServletRequest request) {
      //返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
        
        // 创建map，用于向后台传递参数值（查询条件）
        Map<String, Object> requestData = new HashMap<>();
        //bean对应的model cModels
        List<CaseCommonBaseSearchModel> cModels = new ArrayList<CaseCommonBaseSearchModel>(); 
        //查询集合总数
        Long count = null;
        try {
//            TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
//            TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
//            String subjectIds = "";
//            String menuNames =TeeStringUtil.getString(request.getParameter("menuNames"));
            /*if(!"".equals(menuNames) && !menuNames.contains("系统管理权限组")) {
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
            }*/
            
            CaseCommonDataModel caseCommonDataModel = new CaseCommonDataModel();
            caseCommonDataModel.setIsSelect(1);
            List<CaseCommonDataModel> caseCommonDataModels = new ArrayList<>();
            caseCommonDataModels = caseCommonDataService.findListByPage(caseCommonDataModel);
            CaseCommonDataModel cModel = new CaseCommonDataModel();
            if(caseCommonDataModels != null && caseCommonDataModels.size() > 0){
                for (int i = 0; i < caseCommonDataModels.size(); i++) {
                    cModel = caseCommonDataModels.get(i);
                    if(!TeeUtility.isNullorEmpty(cModel.getDataType())){
                        if("DATE".equals(cModel.getDataType())){
                            if("06".equals(cModel.getMatchingMode())){
                                if(!"".equals(TeeStringUtil.getString(request.getParameter("begin" + cModel.getField()))))
                                    requestData.put("begin" + cModel.getField(), TeeStringUtil.getDate(request.getParameter("begin" + cModel.getField()).trim() + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
                                else
                                    requestData.put("begin" + cModel.getField(), "");
                                if(!"".equals(TeeStringUtil.getString(request.getParameter("end" + cModel.getField()))))
                                    requestData.put("end" + cModel.getField(), TeeStringUtil.getDate(request.getParameter("end" + cModel.getField()) + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
                                else
                                    requestData.put("end" + cModel.getField(), "");
                            }else if("01".equals(cModel.getMatchingMode())){
                                if(!"".equals(TeeStringUtil.getString(request.getParameter(cModel.getField())))){
                                    requestData.put("begin" + cModel.getField(), TeeDateUtil.format(request.getParameter(cModel.getField()).trim() + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
                                    requestData.put("end" + cModel.getField(), TeeDateUtil.format(request.getParameter(cModel.getField()).trim() + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
                                }else{
                                    requestData.put("begin" + cModel.getField(), "");
                                    requestData.put("end" + cModel.getField(), "");
                                }
                            }
                            //requestData.put(cModel.getField(), TeeDateUtil.format(request.getParameter(cModel.getField()).trim(), "yyyy-MM-dd"));
                        }else if("CHAR".equals(cModel.getDataType()) || "VARCHAR2".equals(cModel.getDataType())){
                            requestData.put(cModel.getField(), TeeStringUtil.getString(request.getParameter(cModel.getField())).trim());
                        }/*else if("NUMBER".equals(cModel.getDataType()) && cModel.getDataLength()>11){
                            requestData.put(cModel.getField(), TeeStringUtil.getDouble(request.getParameter(cModel.getField()),0.00));
                        }*/else if("NUMBER".equals(cModel.getDataType()) && cModel.getDataLength()==11){
                            //requestData.put(cModel.getField(), TeeStringUtil.getInteger2(request.getParameter(cModel.getField()),null));
                        }else{
                            requestData.put(cModel.getField(), TeeStringUtil.getString(request.getParameter(cModel.getField())).trim());
                        }
                    }
                }
            }
            
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
            cModels = caseCommonBaseService.findListByPage(tModel, requestData, caseCommonDataModels, orgCtrl);
            count = caseCommonBaseService.listCount(requestData, caseCommonDataModels, orgCtrl);
            
            
            json.setRows(cModels);
            json.setTotal(count);
        } catch (Exception e) {
            //System.out.println("CaseCommonBaseController.findListBypage() " + e.getMessage());
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 
    * @Function: findSearchListBypage()
    * @Description: 一般案件查询方法
    *
    * @param: tModel 表格参数
    * @param: cbModel 查询条件参数
    * @param: request 
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年2月28日 上午9:07:59 
    *
     */
    @ResponseBody
    @RequestMapping("/findSearchListBypage.action")
    public TeeEasyuiDataGridJson findSearchListBypage(TeeDataGridModel tModel, CaseCommonBaseModel cbModel, HttpServletRequest request) {
      //返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
        // 接收查询集合 cList
        List<CaseCommonBase> cList = null; 
        //bean对应的model cModels
        List<CaseCommonBaseModel> cModels = new ArrayList<CaseCommonBaseModel>(); 
        //查询集合总数
        Long count = null;
        try {
            //获取权限控制参数
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
            cList = caseCommonBaseService.findListByPageSearch(tModel, cbModel, orgCtrl);
            count = caseCommonBaseService.listSearchCount(cbModel, orgCtrl);
            //定义model
            CaseCommonBaseModel cModel = null;
            if (cList != null && cList.size() > 0) {
                for (int i = 0; i < cList.size(); i++) {
                    cModel = new CaseCommonBaseModel();
                    CaseCommonBase tempBasic = cList.get(i);
                    // 将tempBasic赋值给cModel
                    BeanUtils.copyProperties(tempBasic, cModel);
                    // 将处罚决定书日期转化为格式化的字符串
                    if (tempBasic.getPunishmentDate() != null) {
                        cModel.setPunishmentDateStr(TeeDateUtil.format(tempBasic.getPunishmentDate(), "yyyy-MM-dd"));    
                    }else {
                        // 处罚决定书日期为空时处理
                        cModel.setPunishmentDateStr("");
                    }
                    // 将立案日期转化为格式化的字符串
                    if (tempBasic.getFilingDate() != null) {
                        cModel.setFilingDateStr(TeeDateUtil.format(tempBasic.getFilingDate(), "yyyy-MM-dd"));    
                    }else {
                        //处罚决定书日期为空时处理
                        cModel.setFilingDateStr("");
                    }
                    // 获取案件当前状态代码值
                    cModel.setCurrentState(TeeSysCodeManager.getChildSysCodeNameCodeNo("COMMON_CURRENT_STATE", cModel.getCurrentState()));
                    // 获取案件结案状态代码值
                    cModel.setClosedState(TeeSysCodeManager.getChildSysCodeNameCodeNo("COMMON_CLOSED_STATE", cModel.getClosedState()));
                    cModels.add(cModel);
                }
            }
            
            json.setRows(cModels);
            json.setTotal(count);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("CaseCommonBaseController.findSearchListBypage() " + e.getMessage());
        }
        return json;
    }
    
    //@ResponseBody
    @RequestMapping(value = "/export.action", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
    public void export(HttpServletRequest request, HttpServletResponse response, String params) throws Exception{
//        TeeJson json = new TeeJson();
        List<CaseCommonBaseSearchModel> cModels = new ArrayList<>();
//        Long count = null;
        Map<String, Object> requestData = new HashMap<>();
        OutputStream os = null;
        JSONObject jsonobject = JSONObject.fromObject(params);
        try {
         // 入库日期
            requestData.put("begincreateDate", TeeStringUtil.getDate(jsonobject.get("begincreateDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
            requestData.put("endcreateDate", TeeStringUtil.getDate(jsonobject.get("endcreateDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
            // 立案日期
            requestData.put("beginfilingDate", TeeStringUtil.getDate(jsonobject.get("beginfilingDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
            requestData.put("endfilingDate", TeeStringUtil.getDate(jsonobject.get("endfilingDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
            // 结案日期
            requestData.put("beginclosedDate", TeeStringUtil.getDate(jsonobject.get("beginclosedDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
            requestData.put("endclosedDate", TeeStringUtil.getDate(jsonobject.get("endclosedDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
            // 处罚决定
            requestData.put("punishState", TeeStringUtil.getString(jsonobject.get("punishState")));
            // 处罚决定书日期// 不予处罚日期// 撤销立案日期
            requestData.put("beginpunishDate", TeeStringUtil.getDate(jsonobject.get("beginpunishDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
            requestData.put("endpunishDate", TeeStringUtil.getDate(jsonobject.get("endpunishDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
            // 处罚执行
            requestData.put("punishExecutState", TeeStringUtil.getString(jsonobject.get("punishExecutState")));
            // 决定执行日期// 撤销处罚日期// 终结日期
            requestData.put("beginpunishExecutDate", TeeStringUtil.getDate(jsonobject.get("beginpunishExecutDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
            requestData.put("endpunishExecutDate", TeeStringUtil.getDate(jsonobject.get("endpunishExecutDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
            
            // 案件名称
            requestData.put("name", TeeStringUtil.getString(jsonobject.get("name")));
            // 执法人员
            requestData.put("officeName", TeeStringUtil.getString(jsonobject.get("officeName")));
            // 执法证号
            requestData.put("cardCode", TeeStringUtil.getString(jsonobject.get("cardCode")));
            // 决定书文号
            requestData.put("punishmentCode", TeeStringUtil.getString(jsonobject.get("punishmentCode")));
            // 职权名称
            requestData.put("powerName", TeeStringUtil.getString(jsonobject.get("powerName")));
            // 职权编号
            requestData.put("powerCode", TeeStringUtil.getString(jsonobject.get("powerCode")));
            
            // 行政区划
            requestData.put("administrativeDivision", TeeStringUtil.getString(jsonobject.get("administrativeDivision")));
            // 所属领域
            requestData.put("orgSys", TeeStringUtil.getString(jsonobject.get("orgSys")));
            // 执法机关
            requestData.put("departmentId", TeeStringUtil.getString(jsonobject.get("departmentId")));
            // 执法主体
            requestData.put("subjectId", TeeStringUtil.getString(jsonobject.get("subjectId")));
            // 当事人类型
            requestData.put("partyType", TeeStringUtil.getString(jsonobject.get("partyType")));
            // 案件来源
            requestData.put("caseSource", TeeStringUtil.getString(jsonobject.get("caseSource")));
            // 办理状态
            requestData.put("currentState", TeeStringUtil.getString(jsonobject.get("currentState")));
            // 结案状态
            requestData.put("closedState", TeeStringUtil.getString(jsonobject.get("closedState")));
            
            // 是否提交
            requestData.put("isSubmit", TeeStringUtil.getInteger(jsonobject.get("isSubmit"), -1));
            // 是否委托
            requestData.put("isDepute", TeeStringUtil.getInteger(jsonobject.get("isDepute"), -1));
            // 是否重大案件
            requestData.put("isMajorCase", TeeStringUtil.getInteger(jsonobject.get("isMajorCase"), -1));
            // 是否涉刑
            requestData.put("isCriminal", TeeStringUtil.getInteger(jsonobject.get("isCriminal"), -1));
            // 是否法制审核
            requestData.put("isFilingLegalReview", TeeStringUtil.getInteger(jsonobject.get("isFilingLegalReview"), -1));
            
            //requestData = getRequestData(request);
            
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
            
            /*count = caseCommonBaseService.listCount(requestData, orgCtrl);
            if(count>1000){
                json.setRtState(false);
                json.setRtMsg("导出数据达"+count+"条！数据超限！");
            }else{*/
                TeeDataGridModel tModel = new TeeDataGridModel();
                tModel.setPage(1);
                tModel.setRows(1000);
                cModels = caseCommonBaseService.findListByPage(tModel, requestData, orgCtrl);
                String[] isShow = new String[0];
                String isShow1 = jsonobject.get("isShow") == null?"":TeeStringUtil.getString(jsonobject.get("isShow"));
                if(!"[]".equals(isShow1))
                    isShow = isShow1.replace("\"", "").replace("[", "").replace("]", "").split(",");
                os = response.getOutputStream();
                String fileName = new String(("一般案件综合查询"+TeeDateUtil.format(new Date(),"yyyyMMddHHmmss")).getBytes("gbk"), "iso8859-1") + ".xls";
                // 设置响应类型为一个可下载的文件
                response.setContentType("application/x-msdownload");
                // 设置下载文件的文件名
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                HSSFWorkbook workbook = createWorkbook(cModels, isShow);
                workbook.write(os);
                /*json.setRtState(true);
            }*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(os != null)
                os.close();
        }
        //return json;
    }
    
    public HSSFWorkbook createWorkbook(List<CaseCommonBaseSearchModel> cModels, String[] isShow){
     // 创建HSSFWorkbook对象，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 在wb中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet1");
        //设置表头样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//边框
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //创建字体样式
        HSSFFont titleFont = wb.createFont(); 
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        titleFont.setFontName("宋体");
        titleFont.setFontHeightInPoints((short) 11);//字号
        titleStyle.setFont(titleFont); //设置样式
        
        String[] rowCell = new String[7+isShow.length];
        rowCell[0] = "立案号";
        rowCell[1] = "处罚决定书文号";
        rowCell[2] = "案件名称";
        rowCell[3] = "当事人类型";
        rowCell[4] = "当事人名称";
        rowCell[5] = "办理状态";
        rowCell[6] = "结案状态";
        for (int i = 0; i < isShow.length; i++) {
            if("officeName".equals(isShow[i])){
                rowCell[7+i] = "执法人员";
                continue;
            }
            if("filingDate".equals(isShow[i])){
                rowCell[7+i] = "立案日期";
                continue;
            }
            if("surveyEndDate".equals(isShow[i])){
                rowCell[7+i] = "调查终结日期";
                continue;
            }
            if("punishmentDate".equals(isShow[i])){
                rowCell[7+i] = "行政处罚决定书日期";
                continue;
            }
            if("pdSentDate".equals(isShow[i])){
                rowCell[7+i] = "决定书送达日期";
                continue;
            }
            if("closedDate".equals(isShow[i])){
                rowCell[7+i] = "结案日期";
                continue;
            }
            if("caseTime".equals(isShow[i])){
                rowCell[7+i] = "案件总时长";
                continue;
            }
            if("filingTime".equals(isShow[i])){
                rowCell[7+i] = "立案周期";
                continue;
            }
            if("surveyTime".equals(isShow[i])){
                rowCell[7+i] = "调查取证周期";
                continue;
            }
            if("punishTime".equals(isShow[i])){
                rowCell[7+i] = "作出处罚决定周期";
                continue;
            }
            if("punishDecisionExecutTime".equals(isShow[i])){
                rowCell[7+i] = "处罚决定执行周期";
                continue;
            }
            if("closedTime".equals(isShow[i])){
                rowCell[7+i] = "结案周期";
                continue;
            }
            if("isMajorCase".equals(isShow[i])){
                rowCell[7+i] = "是否重大案件";
                continue;
            }
            if("isCollectiveDiscussion".equals(isShow[i])){
                rowCell[7+i] = "是否集体讨论";
                continue;
            }
            if("isFilingLegalReview".equals(isShow[i])){
                rowCell[7+i] = "是否法制审核";
                continue;
            }
            if("dataSource".equals(isShow[i])){
                rowCell[7+i] = "数据来源";
                continue;
            }
            if("createDate".equals(isShow[i])){
                rowCell[7+i] = "入库日期";
                continue;
            }
            if("isPlot".equals(isShow[i])){
                rowCell[7+i] = "情节从轻从重情况";
                continue;
            }
            if("isForce".equals(isShow[i])){
                rowCell[7+i] = "是否采取强制措施";
                continue;
            }
            if("isOrganEnforce".equals(isShow[i])){
                rowCell[7+i] = "是否强制执行";
                continue;
            }
        }
        
        // 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);// 创建表头1
        for (int i = 0; i < rowCell.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(rowCell[i]);
        }
        
        //设置表格样式
        HSSFCellStyle bodyStyle = wb.createCellStyle();//创建内容样式
        // 垂直方向顶端对齐 水平方向中间对齐(上下居中)
        bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 边框
        bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 创建字体样式
        HSSFFont bodyFont = wb.createFont();
        bodyFont.setFontName("宋体");
        // 字号
        bodyFont.setFontHeightInPoints((short) 11);
        // 将字体样式加入表格样式中
        bodyStyle.setFont(bodyFont);
        
        if(cModels != null && cModels.size() > 0){
            for (int i = 0; i < cModels.size(); i++) {
                // 创建新一行
                HSSFRow row2 = sheet.createRow(i + 1);
                CaseCommonBaseSearchModel cModel = cModels.get(i);
                abc : for (int j = 0; j < rowCell.length; j++) {
                    // 创建单元格
                    HSSFCell cell = row2.createCell(j);
                    // 为单元格赋予样式
                    cell.setCellStyle(bodyStyle);
                    // 向对应单元格填充内容
                    switch (j) {
                    case 0:
                        // 获取立案号
                        String filingNumber = cModel.getFilingNumber() == null ? "":cModel.getFilingNumber();
                        cell.setCellValue(filingNumber);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue abc;
                    case 1:
                        // 处罚决定书文号
                        String punishmentCode = cModel.getPunishmentCode() == null ? "":cModel.getPunishmentCode();
                        cell.setCellValue(punishmentCode);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue abc;
                    case 2:
                        // 获取案件名称
                        String name = cModel.getName() == null ? "":cModel.getName();
                        cell.setCellValue(name);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue abc;
                    case 3:
                        // 获取当事人类型
                        String partyType = cModel.getPartyTypeValue() == null ? "":cModel.getPartyTypeValue();
                        cell.setCellValue(partyType);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue abc;
                    case 4:
                        // 获取当事人名称
                        String citizenName = cModel.getCitizenName() == null ? "":cModel.getCitizenName();
                        cell.setCellValue(citizenName);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue abc;
                    case 5:
                        // 获取办理状态
                        String currentState = cModel.getCurrentStateValue() == null ? "":cModel.getCurrentStateValue();
                        cell.setCellValue(currentState);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue abc;
                    case 6:
                        // 获取结案状态
                        String closedState = cModel.getClosedStateValue() == null ? "":cModel.getClosedStateValue();
                        cell.setCellValue(closedState);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue abc;
                    }
                    if("officeName".equals(isShow[j-7])){
                        // 获取执法人员
                        String officeName = cModel.getOfficeName() == null ? "":cModel.getOfficeName();
                        cell.setCellValue(officeName);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("filingDate".equals(isShow[j-7])){
                        // 获取立案日期
                        String officeName = cModel.getFilingDateStr() == null ? "":cModel.getFilingDateStr();
                        cell.setCellValue(officeName);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("surveyEndDate".equals(isShow[j-7])){
                        // 获取调查终结日期
                        String surveyEndDate = cModel.getSurveyEndDateStr() == null ? "":cModel.getSurveyEndDateStr();
                        cell.setCellValue(surveyEndDate);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("punishmentDate".equals(isShow[j-7])){
                        // 获取行政处罚决定书日期
                        String punishmentDate = cModel.getPunishmentDateStr() == null ? "":cModel.getPunishmentDateStr();
                        cell.setCellValue(punishmentDate);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("pdSentDate".equals(isShow[j-7])){
                        // 获取决定书送达日期
                        String pdSentDate = cModel.getPdSentDateStr() == null ? "":cModel.getPdSentDateStr();
                        cell.setCellValue(pdSentDate);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("closedDate".equals(isShow[j-7])){
                        // 获取结案日期
                        String closedDate = cModel.getClosedDateStr() == null ? "":cModel.getClosedDateStr();
                        cell.setCellValue(closedDate);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("caseTime".equals(isShow[j-7])){
                        // 获取案件总时长
                        String caseTime = cModel.getCaseTime() == null ? "":cModel.getCaseTime().toString();
                        cell.setCellValue(caseTime);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("filingTime".equals(isShow[j-7])){
                        // 获取立案周期
                        String filingTime = cModel.getFilingTime() == null ? "":cModel.getFilingTime().toString();
                        cell.setCellValue(filingTime);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("surveyTime".equals(isShow[j-7])){
                        // 获取调查取证周期
                        String surveyTime = cModel.getSurveyTime() == null ? "":cModel.getSurveyTime().toString();
                        cell.setCellValue(surveyTime);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("punishTime".equals(isShow[j-7])){
                        // 获取作出处罚决定周期
                        String punishTime = cModel.getPunishTime() == null ? "":cModel.getPunishTime().toString();
                        cell.setCellValue(punishTime);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("punishDecisionExecutTime".equals(isShow[j-7])){
                        // 获取处罚决定执行周期
                        String punishDecisionExecutTime = cModel.getPunishDecisionExecutTime() == null ? "":cModel.getPunishDecisionExecutTime().toString();
                        cell.setCellValue(punishDecisionExecutTime);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("closedTime".equals(isShow[j-7])){
                        // 获取结案周期
                        String closedTime = cModel.getClosedTime() == null ? "":cModel.getClosedTime().toString();
                        cell.setCellValue(closedTime);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("isMajorCase".equals(isShow[j-7])){
                        // 获取是否重大案件
                        String isMajorCase = cModel.getIsMajorCase() == null ? "":(cModel.getIsMajorCase()==1?"是":(cModel.getIsMajorCase()==0?"否":""));
                        cell.setCellValue(isMajorCase);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("isCollectiveDiscussion".equals(isShow[j-7])){
                        // 获取是否集体讨论
                        String isCollectiveDiscussion = cModel.getIsCollectiveDiscussion() == null ? "":(cModel.getIsCollectiveDiscussion()==1?"是":(cModel.getIsCollectiveDiscussion()==0?"否":""));
                        cell.setCellValue(isCollectiveDiscussion);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("isFilingLegalReview".equals(isShow[j-7])){
                        // 获取是否法制审核
                        String isFilingLegalReview = cModel.getIsFilingLegalReview() == null ? "":(cModel.getIsFilingLegalReview()==1?"是":(cModel.getIsFilingLegalReview()==0?"否":""));
                        cell.setCellValue(isFilingLegalReview);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("dataSource".equals(isShow[j-7])){
                        // 获取数据来源
                        String dataSource = cModel.getDataSourceValue() == null ? "":cModel.getDataSourceValue();
                        cell.setCellValue(dataSource);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("createDate".equals(isShow[j-7])){
                        // 获取入库日期
                        String createDate = cModel.getCreateDateStr() == null ? "":cModel.getCreateDateStr();
                        cell.setCellValue(createDate);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("isPlot".equals(isShow[j-7])){
                        // 获取情节从轻从重情况
                        String isPlot = cModel.getIsPlot() == null ? "":(cModel.getIsPlot()==1?"无":(cModel.getIsPlot()==2?"具有从轻情节":(cModel.getIsPlot()==3?"具有减轻情节":(cModel.getIsPlot()==4?"具有从重情节":""))));
                        cell.setCellValue(isPlot);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("isForce".equals(isShow[j-7])){
                        // 获取是否采取强制措施
                        String isForce = cModel.getIsForce() == null ? "":(cModel.getIsForce()==1?"是":(cModel.getIsForce()==0?"否":""));
                        cell.setCellValue(isForce);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                    if("isOrganEnforce".equals(isShow[j-7])){
                        // 获取是否强制执行
                        String isOrganEnforce = cModel.getIsOrganEnforce() == null ? "":(cModel.getIsOrganEnforce()==1?"是":(cModel.getIsOrganEnforce()==0?"否":""));
                        cell.setCellValue(isOrganEnforce);
                        // 设置列宽
                        sheet.setColumnWidth(j, 40 * 256);
                        continue;
                    }
                }
            }
        }
        
        return wb;
    }
    
    private Map<String,Object> getRequestData(HttpServletRequest request){
     // 创建map，用于向后台传递参数值（查询条件）
        Map<String, Object> requestData = new HashMap<>();
        // 入库日期
        requestData.put("begincreateDate", TeeStringUtil.getDate(request.getParameter("begincreateDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        requestData.put("endcreateDate", TeeStringUtil.getDate(request.getParameter("endcreateDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
        // 立案日期
        requestData.put("beginfilingDate", TeeStringUtil.getDate(request.getParameter("beginfilingDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        requestData.put("endfilingDate", TeeStringUtil.getDate(request.getParameter("endfilingDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
        // 结案日期
        requestData.put("beginclosedDate", TeeStringUtil.getDate(request.getParameter("beginclosedDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        requestData.put("endclosedDate", TeeStringUtil.getDate(request.getParameter("endclosedDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
        // 处罚决定
        requestData.put("punishState", TeeStringUtil.getString(request.getParameter("punishState")));
        // 处罚决定书日期// 不予处罚日期// 撤销立案日期
        requestData.put("beginpunishDate", TeeStringUtil.getDate(request.getParameter("beginpunishDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        requestData.put("endpunishDate", TeeStringUtil.getDate(request.getParameter("endpunishDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
        // 处罚执行
        requestData.put("punishExecutState", TeeStringUtil.getString(request.getParameter("punishExecutState")));
        // 决定执行日期// 撤销处罚日期// 终结日期
        requestData.put("beginpunishExecutDate", TeeStringUtil.getDate(request.getParameter("beginpunishExecutDate") + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
        requestData.put("endpunishExecutDate", TeeStringUtil.getDate(request.getParameter("endpunishExecutDate") + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));
        
        // 案件名称
        requestData.put("name", TeeStringUtil.getString(request.getParameter("name")));
        // 执法人员
        requestData.put("officeName", TeeStringUtil.getString(request.getParameter("officeName")));
        // 执法证号
        requestData.put("cardCode", TeeStringUtil.getString(request.getParameter("cardCode")));
        // 决定书文号
        requestData.put("punishmentCode", TeeStringUtil.getString(request.getParameter("punishmentCode")));
        // 职权名称
        requestData.put("powerName", TeeStringUtil.getString(request.getParameter("powerName")));
        // 职权编号
        requestData.put("powerCode", TeeStringUtil.getString(request.getParameter("powerCode")));
        
        // 行政区划
        requestData.put("administrativeDivision", TeeStringUtil.getString(request.getParameter("administrativeDivision")));
        // 所属领域
        requestData.put("orgSys", TeeStringUtil.getString(request.getParameter("orgSys")));
        // 执法机关
        requestData.put("departmentId", TeeStringUtil.getString(request.getParameter("departmentId")));
        // 执法主体
        requestData.put("subjectId", TeeStringUtil.getString(request.getParameter("subjectId")));
        // 当事人类型
        requestData.put("partyType", TeeStringUtil.getString(request.getParameter("partyType")));
        // 案件来源
        requestData.put("caseSource", TeeStringUtil.getString(request.getParameter("caseSource")));
        // 办理状态
        requestData.put("currentState", TeeStringUtil.getString(request.getParameter("currentState")));
        // 结案状态
        requestData.put("closedState", TeeStringUtil.getString(request.getParameter("closedState")));
        
        // 是否提交
        requestData.put("isSubmit", TeeStringUtil.getInteger(request.getParameter("isSubmit"), -1));
        // 是否委托
        requestData.put("isDepute", TeeStringUtil.getInteger(request.getParameter("isDepute"), -1));
        // 是否重大案件
        requestData.put("isMajorCase", TeeStringUtil.getInteger(request.getParameter("isMajorCase"), -1));
        // 是否涉刑
        requestData.put("isCriminal", TeeStringUtil.getInteger(request.getParameter("isCriminal"), -1));
        // 是否法制审核
        requestData.put("isFilingLegalReview", TeeStringUtil.getInteger(request.getParameter("isFilingLegalReview"), -1));
        
        return requestData;
    }
    
}
