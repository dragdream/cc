/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.controller 
 * @author: songff   
 * @date: 2018年12月26日 下午2:41:09 
 */
package com.beidasoft.zfjd.caseManager.commonCase.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonCaseSource;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonDiscretionary;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonEnd;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonGist;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonNopunishment;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonPower;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonPunish;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonRevoke;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonRevokePunish;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonStaff;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonBaseModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonCaseSourceModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonDiscretionaryModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonEndModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonGistModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonNopunishmentModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonPowerModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonPunishModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonRevokeModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonRevokePunishModel;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonStaffModel;
import com.beidasoft.zfjd.caseManager.commonCase.service.CaseCommonBaseService;
import com.beidasoft.zfjd.common.controller.CommonController;
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
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**   
* 2018 
* @ClassName: CaseCommonBasicController.java
* @Description: 一般案件信息表CONTROLLER类
*
* @author: songff
* @date: 2018年12月26日 下午2:41:09 
*
*/
@Controller
@RequestMapping("/caseCommonBaseCtrl")
public class CaseCommonBaseController extends CommonController{
    
    //获取日志记录器Logger，名字为本类类名
    private static Logger logger = LogManager.getLogger(CaseCommonBaseController.class);
    
    @Autowired
    private CaseCommonBaseService caseCommonBaseService;
    @Autowired
    private TeeDeptService deptService;
    @Autowired
    private TeePersonService personService;
    @Autowired
    private CommonService commonservivse;

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
            request.getRequestDispatcher("/supervise/caseManager/commonCase/common_case_index.jsp").forward(request, response);
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
            request.getRequestDispatcher("/supervise/caseManager/commonCase/common_case_search_index.jsp").forward(request, response);
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
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel, CaseCommonBaseModel cbModel, HttpServletRequest request) {
        //返回前台json 
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
        // 接收查询集合 cList
        List<CaseCommonBase> cList = null; 
        //bean对应的model cModels
        List<CaseCommonBaseModel> cModels = new ArrayList<CaseCommonBaseModel>(); 
        //查询集合总数
        Long count = null;
        try {
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
            cList = caseCommonBaseService.findListByPage(tModel, cbModel);
            count = caseCommonBaseService.listCount(cbModel);
            //定义model
            CaseCommonBaseModel cModel = null;
            if (cList != null && cList.size() > 0) {
                for (int i = 0; i < cList.size(); i++) {
                    cModel = new CaseCommonBaseModel();
                    CaseCommonBase tempBasic = cList.get(i);
                    //将tempBasic赋值给cModel
                    BeanUtils.copyProperties(tempBasic, cModel);
                    //将处罚决定书日期转化为格式化的字符串
                    if (tempBasic.getPunishmentDate() != null) {
                        cModel.setPunishmentDateStr(TeeDateUtil.format(tempBasic.getPunishmentDate(), "yyyy-MM-dd"));    
                    }else {
                        //处罚决定书日期为空时处理
                        cModel.setPunishmentDateStr("");
                    }
                    //将立案日期转化为格式化的字符串
                    if (tempBasic.getFilingDate() != null) {
                        cModel.setFilingDateStr(TeeDateUtil.format(tempBasic.getFilingDate(), "yyyy-MM-dd"));    
                    }else {
                        //处罚决定书日期为空时处理
                        cModel.setFilingDateStr("");
                    }
                    //获取案件当前状态代码值
                    cModel.setCurrentState(TeeSysCodeManager.getChildSysCodeNameCodeNo("COMMON_CURRENT_STATE", cModel.getCurrentState()));
                    //获取案件结案状态代码值
                    cModel.setClosedState(TeeSysCodeManager.getChildSysCodeNameCodeNo("COMMON_CLOSED_STATE", cModel.getClosedState()));
                    cModels.add(cModel);
                }
            }
            
            json.setRows(cModels);
            json.setTotal(count);
        } catch (Exception e) {
            System.out.println("CaseCommonBaseController.findListBypage() " + e.getMessage());
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
    /**
     * 
    * @Function: commonCaseAdd()
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
    @RequestMapping("/commonCaseAdd")
    public void commonCaseAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            CaseCommonBaseModel cbModel = new CaseCommonBaseModel();
            if(request.getParameter("id") == null || "".equals(request.getParameter("id"))) {
                String id = UUID.randomUUID().toString();
                request.setAttribute("id", id);
            }else {
                request.setAttribute("id", request.getParameter("id"));
                cbModel.setId(request.getParameter("id"));
                CaseCommonBase cBase = caseCommonBaseService.findCaseCommonBaseById(cbModel);
                if (cBase.getIsNext() != null) {
                    request.setAttribute("isNext", cBase.getIsNext());
                }else {
                    request.setAttribute("isNext", 0);  
                }
            }
            request.setAttribute("editFlag", TeeStringUtil.getInteger(request.getParameter("editFlag"), 0));
            request.setAttribute("modelId", TeeStringUtil.getInteger(request.getParameter("modelId"), 0));
            request.getRequestDispatcher("/supervise/caseManager/commonCase/common_case_add.jsp").forward(request, response);
        }catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: commonCaseAddFiling()
    * @Description: 立案阶段
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月12日 下午6:53:36 
    *
     */
    @RequestMapping("/commonCaseAddGrading")
    public void commonCaseAddGrading(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            //一般案件model
            CaseCommonBaseModel cbModel = new CaseCommonBaseModel();
            String caseId = TeeStringUtil.getString(request.getParameter("caseId"), "");
            if(caseId != null && !"".equals(caseId)) {
                request.setAttribute("caseId", caseId);
            }
            cbModel.setId(caseId);
            
            
            CaseCommonBase cBase = new CaseCommonBase();
            //点击保存事件标志
            String saveEvent = TeeStringUtil.getString(request.getParameter("saveEvent"), "");
            int editFlag = TeeStringUtil.getInteger(request.getParameter("editFlag"), 0);
            request.setAttribute("editFlag", editFlag);
            if ("01".equals(saveEvent) || editFlag == 2 || editFlag == 3) {
                //当点击保存时，修改时，查询案件
                //通过ID查询一般案件信息
                cBase = caseCommonBaseService.findCaseCommonBaseById(cbModel);
            }
            //案件阶段
            String grading = TeeStringUtil.getString(request.getParameter("grading"), "");
            if (grading != null && !"".equals(grading) && cBase.getId() != null && !"".equals(cBase.getId())) {
                cbModel.setGrading(grading);
                cbModel = commonModel(cBase, cbModel);
            }
            //跳转路径
            String pageUrl = TeeStringUtil.getString(request.getParameter("pageUrl"), "");
            if (pageUrl == null || "".equals(pageUrl)) {
                //跳转到错误页面
                pageUrl = "";
            }
            
            if (cbModel.getIsNext() != null) {
                request.setAttribute("isNext", cbModel.getIsNext());
            }else {
                request.setAttribute("isNext", 0);  
            }
            request.setAttribute("cbModel", cbModel);
            request.setAttribute("modelId", TeeStringUtil.getInteger(request.getParameter("modelId"), 0));
            request.getRequestDispatcher(pageUrl).forward(request, response);
        }catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: commonCaseLook();
    * @Description: 一般案件查询功能
    *
    * @param: request
    * @param: response
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年3月7日 下午4:05:31 
    *
     */
    @RequestMapping("/commonCaseLook")
    public void commonCaseLook(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //一般案件model
        CaseCommonBaseModel cbModel = new CaseCommonBaseModel();
        String caseId = TeeStringUtil.getString(request.getParameter("caseId"), "");
        cbModel.setId(caseId);
        CaseCommonBase cBase = caseCommonBaseService.findCaseCommonBaseById(cbModel);
        //查询出来的对象cBase，赋值给cbModel
        BeanUtils.copyProperties(cBase, cbModel);
        //立案阶段
        cbModel.setGrading("01");
        cbModel = commonIndexModel(cBase, cbModel);
        //调查取证
        cbModel.setGrading("02");
        cbModel = commonIndexModel(cBase, cbModel);
        //审查决定
        cbModel.setGrading("03");
        cbModel = commonIndexModel(cBase, cbModel);
        //作出处罚
        cbModel.setGrading("04");
        cbModel = commonIndexModel(cBase, cbModel);
        //结案
        cbModel.setGrading("05");
        cbModel = commonIndexModel(cBase, cbModel);
        
        //跳转路径
        String pageUrl = TeeStringUtil.getString(request.getParameter("pageUrl"), "");
        if (pageUrl == null || "".equals(pageUrl)) {
            //跳转到错误页面
            pageUrl = "";
        }
        request.setAttribute("cbModel", cbModel);
        request.getRequestDispatcher(pageUrl).forward(request, response);
    }
    /**
     * 
    * @Function: findCaseCommonBaseById()
    * @Description: 通过ID查询案件信息
    *
    * @param: cbModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月15日 上午11:32:42 
    *
     */
    @ResponseBody
    @RequestMapping("/findCaseCommonBaseById")
    public TeeJson findCaseCommonBaseById(CaseCommonBaseModel cbModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            CaseCommonBase cBase = caseCommonBaseService.findCaseCommonBaseById(cbModel);
            cbModel = commonModel(cBase, cbModel);
            if (cbModel != null) {
                json.setRtData(cbModel);
                json.setRtState(true);
            }else {
                json.setRtState(false);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return json;
    }
    
    /**
     * 
    * @Function: saveFilingStage()
    * @Description: 立案阶段保存信息
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月10日 下午4:33:45 
    *
     */
    @ResponseBody
    @RequestMapping("/saveFilingStage")
    public TeeJson saveFilingStage(CaseCommonBaseModel cbModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            int editFlag = 0;
            if (cbModel.getEditFlag() != null) {
                editFlag = cbModel.getEditFlag();
            }
            CaseCommonBase cBase = commonBase(cbModel);
            if (cbModel.getGrading() != null && !"".equals(cbModel.getGrading())) {
                String grading = cbModel.getGrading();
                if ("01".equals(grading)) {
                    // 处理案件来源信息
                    List<CaseCommonCaseSource> caseSourceLists = caseSourceLists(cBase, cbModel);
                    cBase.setCaseSources(caseSourceLists);
                    // 处理执法人员
                    List<CaseCommonStaff> casesStaffs = casesStaffs(cBase, cbModel);
                    cBase.setCaseCommonStaffs(casesStaffs);
                } else if ("02".equals(grading)) {
                    
                } else if ("03".equals(grading)) {
                    List<CaseCommonPower> powerList = powerLists(cBase, cbModel);
                    cBase.setCaseCommonPowers(powerList);
                    List<CaseCommonGist> gistList = gistLists(cBase, cbModel);
                    cBase.setCaseCommonGists(gistList);
                    List<CaseCommonPunish> punishList = punishLists(cBase, cbModel);
                    cBase.setCaseCommonPunishs(punishList);
                    List<CaseCommonDiscretionary> discretionaryList = discretionaryLists(cBase, cbModel);
                    cBase.setCaseCommonDiscretionarys(discretionaryList);
                }
                if (2 == editFlag) {
                    // 修改
                    caseCommonBaseService.updateCaseInfoByColumnsAndId(cBase, cbModel);
                } else if (1 == editFlag) {
                    if ("01".equals(grading)) {
                        // 处理案件来源信息
                        caseCommonBaseService.saveFilingStage(cBase);
                    } else {
                        caseCommonBaseService.updateCaseInfoByColumnsAndId(cBase, cbModel);
                    }
                }
            }
            json.setRtState(true);
            json.setRtData(cBase.getId());
        } catch (Exception e) {
            logger.info(e.getMessage());
            json.setRtState(false);
        }
        return json;
    }
    
    /**
     * 
    * @Function: commonBase()
    * @Description: 保存时，一般案件信息时间格式处理
    *
    * @param: cbModel
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月11日 下午7:46:40 
    *
     */
    public CaseCommonBase commonBase(CaseCommonBaseModel cbModel) throws Exception{
        CaseCommonBase cBase = new CaseCommonBase();
        try {
            BeanUtils.copyProperties(cbModel, cBase);
            cBase.setDataSource(1);//数据来源（1系统录入，2系统对接）
            if (1 == cbModel.getEditFlag()) {
                cBase.setCreateDate(new Date());
                cBase.setUpdateDate(new Date());
            }else {
                cBase.setUpdateDate(new Date());
            }
            if ("01".equals(cbModel.getGrading())) {
                //立案阶段数据处理
                cBase.setIsDelete(0);
                cBase.setIsSubmit(0);
                //立案日期
                if (!TeeUtility.isNullorEmpty(cbModel.getFilingDateStr())) {
                    cBase.setFilingDate(TeeDateUtil.format(cbModel.getFilingDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setFilingDate(null);
                }
                //申请立案日期
                if (!TeeUtility.isNullorEmpty(cbModel.getApprovedDateStr())) {
                    cBase.setApprovedDate(TeeDateUtil.format(cbModel.getApprovedDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setApprovedDate(null);
                }
                //法制审查日期
                if (!TeeUtility.isNullorEmpty(cbModel.getFilingLegalReviewDateStr())) {
                    cBase.setFilingLegalReviewDate(TeeDateUtil.format(cbModel.getFilingLegalReviewDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setFilingLegalReviewDate(null);
                }
            }else if ("02".equals(cbModel.getGrading())) {
                //调查取证时间处理
                if (!TeeUtility.isNullorEmpty(cbModel.getSurveyEndDateStr())) {
                    cBase.setSurveyEndDate(TeeDateUtil.format(cbModel.getSurveyEndDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setSurveyEndDate(null);
                }
                //行政处罚事先告知书制发日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cbModel.getPunishHearingMakingDateStr())) {
                    cBase.setPunishHearingMakingDate(TeeDateUtil.format(cbModel.getPunishHearingMakingDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setPunishHearingMakingDate(null);
                }
                //行政处罚事先告知书送达日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cbModel.getPunishHearingSendDateStr())) {
                    cBase.setPunishHearingSendDate(TeeDateUtil.format(cbModel.getPunishHearingSendDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setPunishHearingSendDate(null);
                }
                //证据先行登记保存登记日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cbModel.getSavePriorEvidenceDateStr())) {
                    cBase.setSavePriorEvidenceDate(TeeDateUtil.format(cbModel.getSavePriorEvidenceDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setSavePriorEvidenceDate(null);
                }
                //证据先行登记保存处理日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cbModel.getDealPriorEvidenceDateStr())) {
                    cBase.setDealPriorEvidenceDate(TeeDateUtil.format(cbModel.getDealPriorEvidenceDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setDealPriorEvidenceDate(null);
                }
                //延期起始日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cbModel.getDelayStartDateSearchStr())) {
                    cBase.setDelayStartDateSearch(TeeDateUtil.format(cbModel.getDelayStartDateSearchStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setDelayStartDateSearch(null);
                }
                //延期结束日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cbModel.getDelayEndDateSearchStr())) {
                    cBase.setDelayEndDateSearch(TeeDateUtil.format(cbModel.getDelayEndDateSearchStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setDelayEndDateSearch(null);
                }
                //听证日期处理
                if (!TeeUtility.isNullorEmpty(cbModel.getHearingDateStr())) {
                    cBase.setHearingDate(TeeDateUtil.format(cbModel.getHearingDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setHearingDate(null);
                }
                //听证举行日期处理
                if (!TeeUtility.isNullorEmpty(cbModel.getHearingHoldDateStr())) {
                    cBase.setHearingHoldDate(TeeDateUtil.format(cbModel.getHearingHoldDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setHearingHoldDate(null);
                }
                //责令改正起止日期
                if (!TeeUtility.isNullorEmpty(cbModel.getCorrectStartdateStr())) {
                    cBase.setCorrectStartdate(TeeDateUtil.format(cbModel.getCorrectStartdateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setCorrectStartdate(null);
                }
                //责令改正结束日期
                if (!TeeUtility.isNullorEmpty(cbModel.getCorrectEnddateStr())) {
                    cBase.setCorrectEnddate(TeeDateUtil.format(cbModel.getCorrectEnddateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setCorrectEnddate(null);
                }
                //告知书送达日期
                if (!TeeUtility.isNullorEmpty(cbModel.getInformingbookDeliveryDateStr())) {
                    cBase.setInformingbookDeliveryDate(TeeDateUtil.format(cbModel.getInformingbookDeliveryDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setInformingbookDeliveryDate(null);
                }
            }else if ("03".equals(cbModel.getGrading())) {
                //案件处理呈批日期
                if (!TeeUtility.isNullorEmpty(cbModel.getCaseHandleDateStr())) {
                    cBase.setCaseHandleDate(TeeDateUtil.format(cbModel.getCaseHandleDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setCaseHandleDate(null);
                }
                //法制审核日期
                if (!TeeUtility.isNullorEmpty(cbModel.getLegalReviewDateStr())) {
                    cBase.setLegalReviewDate(TeeDateUtil.format(cbModel.getLegalReviewDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setLegalReviewDate(null);
                }
                //集体讨论日期
                if (!TeeUtility.isNullorEmpty(cbModel.getCollectiveDiscussionDateStr())) {
                    cBase.setCollectiveDiscussionDate(TeeDateUtil.format(cbModel.getCollectiveDiscussionDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setCollectiveDiscussionDate(null);
                }
                //处罚决定书日期
                if (!TeeUtility.isNullorEmpty(cbModel.getPunishmentDateStr())) {
                    cBase.setPunishmentDate(TeeDateUtil.format(cbModel.getPunishmentDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setPunishmentDate(null);
                }
                //处罚决定书送达日期
                if (!TeeUtility.isNullorEmpty(cbModel.getPdSentDateStr())) {
                    cBase.setPdSentDate(TeeDateUtil.format(cbModel.getPdSentDateStr(),"yyyy-MM-dd"));
                }else {
                    cBase.setPdSentDate(null);
                }
                //处罚缴款书送达日期
                if (!TeeUtility.isNullorEmpty(cbModel.getPpSentDateStr())) {
                    cBase.setPpSentDate(TeeDateUtil.format(cbModel.getPpSentDateStr(),"yyyy-MM-dd"));
                }else {
                    cBase.setPpSentDate(null);
                }
                //吊销许可证或营业执照起止日期
                if (!TeeUtility.isNullorEmpty(cbModel.getStartdateWithholdStr())) {
                    cBase.setStartdateWithhold(TeeDateUtil.format(cbModel.getStartdateWithholdStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setStartdateWithhold(null);
                }
                //吊销许可证或营业执照结束日期
                if (!TeeUtility.isNullorEmpty(cbModel.getEnddateWithholdStr())) {
                    cBase.setEnddateWithhold(TeeDateUtil.format(cbModel.getEnddateWithholdStr(),"yyyy-MM-dd"));
                }else {
                    cBase.setEnddateWithhold(null);
                }
                //责令限期改正起止时间
                if (!TeeUtility.isNullorEmpty(cbModel.getCorrectStartdateDecisionStr())) {
                    cBase.setCorrectStartdateDecision(TeeDateUtil.format(cbModel.getCorrectStartdateDecisionStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setCorrectStartdateDecision(null);
                }
                //责令限期改正结束日期
                if (!TeeUtility.isNullorEmpty(cbModel.getCorrectEnddateDecisionStr())) {
                    cBase.setCorrectEnddateDecision(TeeDateUtil.format(cbModel.getCorrectEnddateDecisionStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setCorrectEnddateDecision(null);
                }
                //拘留开始时间
                if (!TeeUtility.isNullorEmpty(cbModel.getStartdateDetainStr())) {
                    cBase.setStartdateDetain(TeeDateUtil.format(cbModel.getStartdateDetainStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setStartdateDetain(null);
                }
                //拘留结束日期
                if (!TeeUtility.isNullorEmpty(cbModel.getEnddateDetainStr())) {
                    cBase.setEnddateDetain(TeeDateUtil.format(cbModel.getEnddateDetainStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setEnddateDetain(null);
                }
            }else if ("04".equals(cbModel.getGrading())) {
                //行政处罚决定书执行日期
                if (!TeeUtility.isNullorEmpty(cbModel.getPunishDecisionExecutDateStr())) {
                    cBase.setPunishDecisionExecutDate(TeeDateUtil.format(cbModel.getPunishDecisionExecutDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setPunishDecisionExecutDate(null);
                }
                //整改日期
                if (!TeeUtility.isNullorEmpty(cbModel.getCorrectDateStr())) {
                    cBase.setCorrectDate(TeeDateUtil.format(cbModel.getCorrectDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setCorrectDate(null);
                }
                //延期截至时间
                if (!TeeUtility.isNullorEmpty(cbModel.getPostponedToDelayStr())) {
                    cBase.setPostponedToDelay(TeeDateUtil.format(cbModel.getPostponedToDelayStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setPostponedToDelay(null);
                }
                //延期申请日期
                if (!TeeUtility.isNullorEmpty(cbModel.getApplyDateDelayStr())) {
                    cBase.setApplyDateDelay(TeeDateUtil.format(cbModel.getApplyDateDelayStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setApplyDateDelay(null);
                }
                //延期批准日期
                if (!TeeUtility.isNullorEmpty(cbModel.getApprovalDateDelayStr())) {
                    cBase.setApprovalDateDelay(TeeDateUtil.format(cbModel.getApprovalDateDelayStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setApprovalDateDelay(null);
                }
                //分期截至时间
                if (!TeeUtility.isNullorEmpty(cbModel.getDeadlineStageStr())) {
                    cBase.setDeadlineStage(TeeDateUtil.format(cbModel.getDeadlineStageStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setDeadlineStage(null);
                }
                //分期申请日期
                if (!TeeUtility.isNullorEmpty(cbModel.getApplyDateStageStr())) {
                    cBase.setApplyDateStage(TeeDateUtil.format(cbModel.getApplyDateStageStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setApplyDateStage(null);
                }
                //分期批准日期
                if (!TeeUtility.isNullorEmpty(cbModel.getApprovalDateStageStr())) {
                    cBase.setApprovalDateStage(TeeDateUtil.format(cbModel.getApprovalDateStageStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setApprovalDateStage(null);
                }
                //当事人执行日期
                if (!TeeUtility.isNullorEmpty(cbModel.getPartyActivePerforDateStr())) {
                    cBase.setPartyActivePerforDate(TeeDateUtil.format(cbModel.getPartyActivePerforDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setPartyActivePerforDate(null);
                }
            }else if ("05".equals(cbModel.getGrading())) {
                if (!TeeUtility.isNullorEmpty(cbModel.getClosedDateStr())) {
                    cBase.setClosedDate(TeeDateUtil.format(cbModel.getClosedDateStr(), "yyyy-MM-dd"));
                }else {
                    cBase.setClosedDate(null);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return cBase;
    }
    
    /**
     * 
    * @Function: saveClosedState()
    * @Description:结案状态  02撤销立案,04撤销原处罚决定,98终结, 保存方法
    *
    * @param: cModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月18日 下午2:34:03 
    *
     */
    @ResponseBody
    @RequestMapping("/saveClosedState")
    public TeeJson saveClosedState(CaseCommonBaseModel cbModel) {
        TeeJson json = new TeeJson();
        try {
            CaseCommonBase cBase = dealClosedState(cbModel);
            String closedState = "";
            if (!TeeUtility.isNullorEmpty(cbModel.getClosedState())) {
                closedState = cbModel.getClosedState();
                cBase.setClosedState(closedState);
                if ("02".equals(closedState)) {
                    //撤销立案
                    caseCommonBaseService.saveRevoke(cBase);
                }else if ("03".equals(closedState)) {
                    //不予处罚
                    caseCommonBaseService.saveNopunishment(cBase);
                }else if ("04".equals(closedState)) {
                    //撤销原处罚决定
                    caseCommonBaseService.saveRevokePunish(cBase);
                }else if ("98".equals(closedState)) {
                    //终结
                    caseCommonBaseService.saveCaseEnd(cBase);
                }
            }
            json.setRtState(true);
        } catch (Exception e) {
            json.setRtState(false);
            logger.info(e.getMessage());
        }
        return json;
    }
    
    /**
     * 
    * @Function: dealClosedState()
    * @Description: 保存时 处理 02撤销立案， 03不予处罚, 04撤销原处罚决定, 98终结, 信息项
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月18日 下午3:05:38 
    *
     */
    @SuppressWarnings("unchecked")
    public CaseCommonBase dealClosedState(CaseCommonBaseModel cbModel) throws Exception{
        CaseCommonBase cBase = new CaseCommonBase();
        try {
            BeanUtils.copyProperties(cbModel, cBase);
            String closedState = "";
            if (!TeeUtility.isNullorEmpty(cbModel.getClosedState())) {
                closedState = cbModel.getClosedState();
                if ("02".equals(closedState)) {
                    //撤销立案
                    List<CaseCommonRevoke> revokeLists = new ArrayList<CaseCommonRevoke>();
                    //撤销立案信息处理
                    if (!TeeUtility.isNullorEmpty(cbModel.getRevokeJsonStr())) {
                        JSONArray revokeArray = JSONArray.fromObject(cbModel.getRevokeJsonStr());
                        List<CaseCommonRevokeModel> revokeModelList = (List<CaseCommonRevokeModel>)JSONArray.toCollection(revokeArray, CaseCommonRevokeModel.class);
                        for (CaseCommonRevokeModel revokeModel: revokeModelList) {
                            CaseCommonRevoke revoke = new CaseCommonRevoke();
                            //将 revokeModel 赋值 revoke
                            BeanUtils.copyProperties(revokeModel, revoke);
                            revoke.setId(UUID.randomUUID().toString());
                            revoke.setCaseCommonRevoke(cBase);
                            revoke.setCreateDate(new Date());
                            //时间处理结果
                            if (!TeeUtility.isNullorEmpty(revokeModel.getRevokeRegisterDateStr())) {
                                revoke.setRevokeRegisterDate(TeeDateUtil.format(revokeModel.getRevokeRegisterDateStr(), "yyyy-MM-dd"));
                            }else {
                                revoke.setRevokeRegisterDate(null);
                            }
                            if (!TeeUtility.isNullorEmpty(revokeModel.getApproveDateStr())) {
                                revoke.setApproveDate(TeeDateUtil.format(revokeModel.getApproveDateStr(), "yyyy-MM-dd"));
                            }else {
                                revoke.setApproveDate(null);
                            }
                            revokeLists.add(revoke);
                        } 
                        cBase.setCaseCommonRevokes(revokeLists);
                    }
                }else if ("03".equals(closedState)) {
                    //不予处罚
                    List<CaseCommonNopunishment> nopunishmentLists = new ArrayList<CaseCommonNopunishment>();
                    //撤销立案信息处理
                    if (!TeeUtility.isNullorEmpty(cbModel.getNopunishmentJsonStr())) {
                        JSONArray nopmnishmentArray = JSONArray.fromObject(cbModel.getNopunishmentJsonStr());
                        List<CaseCommonNopunishmentModel> nopunishmentModelList = (List<CaseCommonNopunishmentModel>)JSONArray.toCollection(nopmnishmentArray, CaseCommonNopunishmentModel.class);
                        for (CaseCommonNopunishmentModel nopunishmentModel: nopunishmentModelList) {
                            CaseCommonNopunishment nopunishment = new CaseCommonNopunishment();
                            //将 nopunishmentModel 赋值 nopunishment
                            BeanUtils.copyProperties(nopunishmentModel, nopunishment);
                            nopunishment.setId(UUID.randomUUID().toString());
                            nopunishment.setCaseCommonNopunishment(cBase);
                            nopunishment.setCreateDate(new Date());
                            //时间处理结果
                            if (!TeeUtility.isNullorEmpty(nopunishmentModel.getNoPunishmentDateStr())) {
                                nopunishment.setNoPunishmentDate(TeeDateUtil.format(nopunishmentModel.getNoPunishmentDateStr(), "yyyy-MM-dd"));
                            }else {
                                nopunishment.setNoPunishmentDate(null);
                            }
                            //申请批准日期
                            if (!TeeUtility.isNullorEmpty(nopunishmentModel.getApproveDateStr())) {
                                nopunishment.setApproveDate(TeeDateUtil.format(nopunishmentModel.getApproveDateStr(), "yyyy-MM-dd"));
                            }else {
                                nopunishment.setApproveDate(null);
                            }
                            //不予处罚决定书送达日期
                            if (!TeeUtility.isNullorEmpty(nopunishmentModel.getNoPunishmentSentDateStr())) {
                                nopunishment.setNoPunishmentSentDate(TeeDateUtil.format(nopunishmentModel.getNoPunishmentSentDateStr(), "yyyy-MM-dd"));
                            }else {
                                nopunishment.setNoPunishmentSentDate(null);
                            }
                            nopunishmentLists.add(nopunishment);
                        } 
                        cBase.setCaseCommonNopunishments(nopunishmentLists);
                    }
                }else if ("04".equals(closedState)) {
                    //撤销原处罚决定list
                    List<CaseCommonRevokePunish> revokePunishLists = new ArrayList<CaseCommonRevokePunish>();
                    //撤销原处罚决定
                    if (!TeeUtility.isNullorEmpty(cbModel.getRevokePunishJsonStr())) {
                        JSONArray revokePunishArray = JSONArray.fromObject(cbModel.getRevokePunishJsonStr());
                        List<CaseCommonRevokePunishModel> revokePunishModelList = (List<CaseCommonRevokePunishModel>)JSONArray.toCollection(revokePunishArray, CaseCommonRevokePunishModel.class);
                        for (CaseCommonRevokePunishModel revokePunishModel: revokePunishModelList) {
                            CaseCommonRevokePunish revokePunish = new CaseCommonRevokePunish();
                            //将 revokePunishModel 赋值 revokePunish
                            BeanUtils.copyProperties(revokePunishModel, revokePunish);
                            revokePunish.setId(UUID.randomUUID().toString());
                            revokePunish.setCaseCommonRevokePunish(cBase);
                            revokePunish.setCreateDate(new Date());
                            //时间处理结果
                            if (!TeeUtility.isNullorEmpty(revokePunishModel.getRevokePunishmentDateStr())) {
                                revokePunish.setRevokePunishmentDate(TeeDateUtil.format(revokePunishModel.getRevokePunishmentDateStr(), "yyyy-MM-dd"));
                            }else {
                                revokePunish.setRevokePunishmentDate(null);
                            }
                            if (!TeeUtility.isNullorEmpty(revokePunishModel.getApproveDateStr())) {
                                revokePunish.setApproveDate(TeeDateUtil.format(revokePunishModel.getApproveDateStr(), "yyyy-MM-dd"));
                            }else {
                                revokePunish.setApproveDate(null);
                            }
                            revokePunishLists.add(revokePunish);
                        } 
                        cBase.setCaseCommonRevokePunishs(revokePunishLists);
                    }
                }else if ("98".equals(closedState)) {
                    //撤销原处罚决定list
                    List<CaseCommonEnd> endLists = new ArrayList<CaseCommonEnd>();
                    //撤销原处罚决定
                    if (!TeeUtility.isNullorEmpty(cbModel.getEndJsonStr())) {
                        JSONArray endArray = JSONArray.fromObject(cbModel.getEndJsonStr());
                        List<CaseCommonEndModel> endModelList = (List<CaseCommonEndModel>)JSONArray.toCollection(endArray, CaseCommonEndModel.class);
                        for (CaseCommonEndModel endModel: endModelList) {
                            CaseCommonEnd end = new CaseCommonEnd();
                            //将 endModel 赋值 end
                            BeanUtils.copyProperties(endModel, end);
                            end.setId(UUID.randomUUID().toString());
                            end.setCaseCommonEnd(cBase);
                            end.setCreateDate(new Date());
                            //时间处理结果
                            if (!TeeUtility.isNullorEmpty(endModel.getEndDateStr())) {
                                end.setEndDate(TeeDateUtil.format(endModel.getEndDateStr(), "yyyy-MM-dd"));
                            }else {
                                end.setEndDate(null);
                            }
                            if (!TeeUtility.isNullorEmpty(endModel.getApproveDateStr())) {
                                end.setApproveDate(TeeDateUtil.format(endModel.getApproveDateStr(), "yyyy-MM-dd"));
                            }else {
                                end.setApproveDate(null);
                            }
                            endLists.add(end);
                        } 
                        cBase.setCaseCommonEnds(endLists);
                    }
                }
            }
        }catch (Exception e) {
            logger.info(e.getMessage());
        }
        return cBase;
    }
    
    /**
     * 
    * @Function: dealClosedStateModel()
    * @Description: 查询时，处理 02撤销立案， 03不予处罚, 04撤销原处罚决定, 98终结, 信息项,成json字符串
    *
    * @param: cBase 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月22日 上午9:39:07 
    *
     */
    public CaseCommonBaseModel dealClosedStateModel(CaseCommonBase cBase, CaseCommonBaseModel cbModel) {
        try {
            String closedState = cBase.getClosedState();
            if ("02".equals(closedState)) {
                //撤销立案
                if (cBase.getCaseCommonRevokes() != null && cBase.getCaseCommonRevokes().size() > 0) {
                    CaseCommonRevoke revoke = cBase.getCaseCommonRevokes().get(0);
                    CaseCommonRevokeModel revokeModel = new CaseCommonRevokeModel();
                    BeanUtils.copyProperties(revoke, revokeModel);
                    if (!TeeUtility.isNullorEmpty(revoke.getRevokeRegisterDate())) {
                        revokeModel.setRevokeRegisterDateStr(TeeDateUtil.format(revoke.getRevokeRegisterDate(), "yyyy-MM-dd"));
                    }else {
                        revokeModel.setRevokeRegisterDateStr("");
                    }
                    if (!TeeUtility.isNullorEmpty(revoke.getApproveDate())) {
                        revokeModel.setApproveDateStr(TeeDateUtil.format(revoke.getApproveDate(), "yyyy-MM-dd"));
                    }else {
                        revokeModel.setApproveDateStr("");
                    }
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setExcludes(new String[]{"caseCommonRevoke"});
                    jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
                    jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                    JSONObject revokeJson = JSONObject.fromObject(revokeModel, jsonConfig);
                    cbModel.setRevokeJsonStr(revokeJson.toString());
                }
            }else if ("03".equals(closedState)) {
                //不予处罚
                if (cBase.getCaseCommonNopunishments() != null && cBase.getCaseCommonNopunishments().size() > 0) {
                    CaseCommonNopunishment nopunishment = cBase.getCaseCommonNopunishments().get(0);
                    CaseCommonNopunishmentModel nopunishmentModel = new CaseCommonNopunishmentModel();
                    BeanUtils.copyProperties(nopunishment, nopunishmentModel);
                    if (!TeeUtility.isNullorEmpty(nopunishment.getNoPunishmentDate())) {
                        nopunishmentModel.setNoPunishmentDateStr(TeeDateUtil.format(nopunishment.getNoPunishmentDate(), "yyyy-MM-dd"));
                    }else {
                        nopunishmentModel.setNoPunishmentDateStr("");
                    }
                    if (!TeeUtility.isNullorEmpty(nopunishment.getApproveDate())) {
                        nopunishmentModel.setApproveDateStr(TeeDateUtil.format(nopunishment.getApproveDate(), "yyyy-MM-dd"));
                    }else {
                        nopunishmentModel.setApproveDateStr("");
                    }
                    //不予处罚决定书送达日期
                    if (!TeeUtility.isNullorEmpty(nopunishment.getNoPunishmentSentDate())) {
                        nopunishmentModel.setNoPunishmentSentDateStr(TeeDateUtil.format(nopunishment.getNoPunishmentSentDate(), "yyyy-MM-dd"));
                    }else {
                        nopunishmentModel.setNoPunishmentSentDateStr("");
                    }
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setExcludes(new String[]{"caseCommonNopunishment"});
                    jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
                    jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                    JSONObject nopunishmentJson = JSONObject.fromObject(nopunishmentModel, jsonConfig);
                    cbModel.setNopunishmentJsonStr(nopunishmentJson.toString());
                }
            }else if ("04".equals(closedState)) {
                //撤销原处罚决定
                if (cBase.getCaseCommonRevokePunishs() != null && cBase.getCaseCommonRevokePunishs().size() > 0) {
                    CaseCommonRevokePunish revokePunish = cBase.getCaseCommonRevokePunishs().get(0);
                    CaseCommonRevokePunishModel revokePunishModel = new CaseCommonRevokePunishModel();
                    BeanUtils.copyProperties(revokePunish, revokePunishModel);
                    if (!TeeUtility.isNullorEmpty(revokePunish.getRevokePunishmentDate())) {
                        revokePunishModel.setRevokePunishmentDateStr(TeeDateUtil.format(revokePunish.getRevokePunishmentDate(), "yyyy-MM-dd"));
                    }else {
                        revokePunishModel.setRevokePunishmentDateStr("");
                    }
                    if (!TeeUtility.isNullorEmpty(revokePunish.getApproveDate())) {
                        revokePunishModel.setApproveDateStr(TeeDateUtil.format(revokePunish.getApproveDate(), "yyyy-MM-dd"));
                    }else {
                        revokePunishModel.setApproveDateStr("");
                    }
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setExcludes(new String[]{"caseCommonRevokePunish"});
                    jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
                    jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                    JSONObject revokePunishJson = JSONObject.fromObject(revokePunishModel, jsonConfig);
                    cbModel.setRevokePunishJsonStr(revokePunishJson.toString());
                }
            }else if ("98".equals(closedState)) {
                //终结
                if (cBase.getCaseCommonEnds() != null && cBase.getCaseCommonEnds().size() > 0) {
                    CaseCommonEnd end = cBase.getCaseCommonEnds().get(0);
                    CaseCommonEndModel endModel = new CaseCommonEndModel();
                    BeanUtils.copyProperties(end, endModel);
                    if (!TeeUtility.isNullorEmpty(end.getEndDate())) {
                        endModel.setEndDateStr(TeeDateUtil.format(end.getEndDate(), "yyyy-MM-dd"));
                    }else {
                        endModel.setEndDateStr("");
                    }
                    if (!TeeUtility.isNullorEmpty(end.getApproveDate())) {
                        endModel.setApproveDateStr(TeeDateUtil.format(end.getApproveDate(), "yyyy-MM-dd"));
                    }else {
                        endModel.setApproveDateStr("");
                    }
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setExcludes(new String[]{"caseCommonEnd"});
                    jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
                    jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                    JSONObject endJson = JSONObject.fromObject(endModel, jsonConfig);
                    cbModel.setEndJsonStr(endJson.toString());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cbModel;
    }
    
    /**
     * 
    * @Function: caseSourceLists()
    * @Description: 保存时，处理案件来源信息
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月11日 下午5:55:35 
    *
     */
    @SuppressWarnings("unchecked")
    public List<CaseCommonCaseSource> caseSourceLists(CaseCommonBase cBase, CaseCommonBaseModel cbModel) throws Exception{
        //案件来源List对象
        List<CaseCommonCaseSource> caseSourceLists = new ArrayList<CaseCommonCaseSource>();
        try {
            //案件来源json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(cbModel.getCaseSourceJsonStr())) {
                JSONArray caseSourceArray = JSONArray.fromObject(cbModel.getCaseSourceJsonStr());
                List<CaseCommonCaseSourceModel> caseSourceModelList = (List<CaseCommonCaseSourceModel>)JSONArray.toCollection(caseSourceArray, CaseCommonCaseSourceModel.class); 
                for (CaseCommonCaseSourceModel caseSourceModel: caseSourceModelList) {
                    CaseCommonCaseSource caseSource = new CaseCommonCaseSource();
                    //将 caseSourceModel 赋值 caseSource
                    BeanUtils.copyProperties(caseSourceModel, caseSource);
                    caseSource.setId(UUID.randomUUID().toString());
                    caseSource.setCaseCommonCaseSource(cBase);
                    caseSource.setCreateDate(new Date());
                    //时间处理结果
                    if (caseSourceModel.getCommonCaseDateStr() != null && !"".equals(caseSourceModel.getCommonCaseDateStr())) {
                        caseSource.setCommonCaseDate(TeeDateUtil.format(caseSourceModel.getCommonCaseDateStr(), "yyyy-MM-dd"));
                    }else {
                        caseSource.setCommonCaseDate(null);
                    }
                    caseSourceLists.add(caseSource);
                }    
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return caseSourceLists;
    }
    
    /**
     * 
    * @Function: casesStaffs()
    * @Description: 执法人员信息处理
    *
    * @param: cBase 
    * @param: cbModel
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月18日 下午3:54:21 
    *
     */
    @SuppressWarnings("unchecked")
    public List<CaseCommonStaff> casesStaffs(CaseCommonBase cBase, CaseCommonBaseModel cbModel) throws Exception{
        List<CaseCommonStaff> casesStaffs = new ArrayList<CaseCommonStaff>();
        try {
            //执法人员json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(cbModel.getPersonJsonStr())) {
                JSONArray staffArray = JSONArray.fromObject(cbModel.getPersonJsonStr());
                List<CaseCommonStaffModel> caseStaffList = (List<CaseCommonStaffModel>)JSONArray.toCollection(staffArray, CaseCommonStaffModel.class);
                for(CaseCommonStaffModel csModel : caseStaffList){
                    CaseCommonStaff caseStaff = new CaseCommonStaff();
                    //将 csModel 赋值 caseStaff
                    BeanUtils.copyProperties(csModel, caseStaff);
                    caseStaff.setId(UUID.randomUUID().toString());
                    caseStaff.setCaseCommonStaff(cBase);
                    caseStaff.setCreateDate(new Date());
                    casesStaffs.add(caseStaff);
                }    
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return casesStaffs;
    }
    
    /**
     * 
    * @Function: powerLists()
    * @Description: 违法行为信息处理
    *
    * @param: cBase 
    * @param: cbModel
    * @return：返回违法行为信息list
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月18日 下午3:54:21 
    *
     */
    @SuppressWarnings("unchecked")
    public List<CaseCommonPower> powerLists(CaseCommonBase cBase, CaseCommonBaseModel cbModel) throws Exception{
        List<CaseCommonPower> powerLists = new ArrayList<CaseCommonPower>();
        try {
            //违法行为json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(cbModel.getPowerJsonStr())) {
                JSONArray powerArray = JSONArray.fromObject(cbModel.getPowerJsonStr());
                List<CaseCommonPowerModel> powerList = (List<CaseCommonPowerModel>)JSONArray.toCollection(powerArray, CaseCommonPowerModel.class);
                for(CaseCommonPowerModel powerModel : powerList){
                    CaseCommonPower power = new CaseCommonPower();
                    //将 powerModel 赋值 power
                    BeanUtils.copyProperties(powerModel, power);
                    power.setId(UUID.randomUUID().toString());
                    power.setCaseCommonPower(cBase);
                    power.setCreateDate(new Date());
                    powerLists.add(power);
                }    
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return powerLists;
    }
    
    /**
     * 
    * @Function: gistLists()
    * @Description: 违法依据信息处理
    *
    * @param: cBase 
    * @param: cbModel
    * @return：返回违法依据信息list
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月18日 下午3:54:21 
    *
     */
    @SuppressWarnings("unchecked")
    public List<CaseCommonGist> gistLists(CaseCommonBase cBase, CaseCommonBaseModel cbModel) throws Exception{
        List<CaseCommonGist> gistLists = new ArrayList<CaseCommonGist>();
        try {
            //违法依据json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(cbModel.getGistJsonStr())) {
                JSONArray gistArray = JSONArray.fromObject(cbModel.getGistJsonStr());
                List<CaseCommonGistModel> gistList = (List<CaseCommonGistModel>)JSONArray.toCollection(gistArray, CaseCommonGistModel.class);
                for(CaseCommonGistModel gistModel : gistList){
                    CaseCommonGist gist = new CaseCommonGist();
                    //将 gistModel 赋值 gist
                    BeanUtils.copyProperties(gistModel, gist);
                    gist.setId(UUID.randomUUID().toString());
                    gist.setCaseCommonGist(cBase);
                    gist.setCreateDate(new Date());
                    gistLists.add(gist);
                }    
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return gistLists;
    }
    

    /**
     * 
    * @Function: punishLists()
    * @Description: 处罚依据信息处理
    *
    * @param: cBase 
    * @param: cbModel
    * @return：返回处罚依据信息list
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月18日 下午3:54:21 
    *
     */
    @SuppressWarnings("unchecked")
    public List<CaseCommonPunish> punishLists(CaseCommonBase cBase, CaseCommonBaseModel cbModel) throws Exception{
        List<CaseCommonPunish> punishLists = new ArrayList<CaseCommonPunish>();
        try {
            //处罚依据json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(cbModel.getPunishJsonStr())) {
                JSONArray punishArray = JSONArray.fromObject(cbModel.getPunishJsonStr());
                List<CaseCommonPunishModel> punishList = (List<CaseCommonPunishModel>)JSONArray.toCollection(punishArray, CaseCommonPunishModel.class);
                for(CaseCommonPunishModel punishModel : punishList){
                    CaseCommonPunish punish = new CaseCommonPunish();
                    //将 punishModel 赋值 punish
                    BeanUtils.copyProperties(punishModel, punish);
                    punish.setId(UUID.randomUUID().toString());
                    punish.setCaseCommonPunish(cBase);
                    punish.setCreateDate(new Date());
                    punishLists.add(punish);
                }    
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return punishLists;
    }
    
    @SuppressWarnings("unchecked")
    public List<CaseCommonDiscretionary> discretionaryLists(CaseCommonBase cBase, CaseCommonBaseModel cbModel) throws Exception{
        List<CaseCommonDiscretionary> discretionaryLists = new ArrayList<CaseCommonDiscretionary>();
        try {
            //处罚依据json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(cbModel.getDiscretionaryJsonStr())) {
                JSONArray discretionaryArray = JSONArray.fromObject(cbModel.getDiscretionaryJsonStr());
                List<CaseCommonDiscretionaryModel> discretionaryList = (List<CaseCommonDiscretionaryModel>)JSONArray.toCollection(discretionaryArray, CaseCommonDiscretionaryModel.class);
                for(CaseCommonDiscretionaryModel discretionaryModel : discretionaryList){
                    CaseCommonDiscretionary discretionary = new CaseCommonDiscretionary();
                    //将 punishModel 赋值 punish
                    BeanUtils.copyProperties(discretionaryModel, discretionary);
                    discretionary.setId(UUID.randomUUID().toString());
                    discretionary.setCaseCommonDiscretionary(cBase);
                    discretionary.setCreateDate(new Date());
                    discretionaryLists.add(discretionary);
                }    
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return discretionaryLists;
    }
    
    /**
     * 
    * @Function: commonModel()
    * @Description: 查询时，一般案件信息时间格式处理
    *
    * @param: cBase 查询出来的值
    * @param: cbModel 条件参数
    * @return：cBaseModel 返回对象
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月11日 下午7:46:40 
    *
     */
    public CaseCommonBaseModel commonModel(CaseCommonBase cBase, CaseCommonBaseModel cbModel) throws Exception{
        CaseCommonBaseModel cBaseModel = new CaseCommonBaseModel();
        try {
            BeanUtils.copyProperties(cBase, cBaseModel);
            cBaseModel.setGrading(cbModel.getGrading());
            cBaseModel = commonIndexModel(cBase,cBaseModel);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cBaseModel;
    }
    
    /**
     * 
    * @Function: commonIndexModel()
    * @Description: 查询时，一般案件信息时间格式处理
    *
    * @param: cBase 查询出来的值
    * @param: cbModel 条件参数
    * @return：cbModel 返回对象
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年3月7日 下午5:22:19 
    *
     */
    public CaseCommonBaseModel commonIndexModel(CaseCommonBase cBase, CaseCommonBaseModel cbModel) throws Exception{
        try {
            //查询时，处理 02撤销立案， 03不予处罚, 04撤销原处罚决定, 98终结, 信息项,成json字符串，存入cBaseModel
            dealClosedStateModel(cBase, cbModel);
            String grading = cbModel.getGrading();
            if ("01".equals(grading)) {//立案
                //立案时间处理
                if (cBase.getFilingDate() != null) {
                    cbModel.setFilingDateStr(TeeDateUtil.format(cBase.getFilingDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setFilingDateStr("");
                }
                //申请立案信息
                if (cBase.getApprovedDate() != null) {
                    cbModel.setApprovedDateStr(TeeDateUtil.format(cBase.getApprovedDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setApprovedDateStr("");
                }
                //法制审查日期
                if (cBase.getFilingLegalReviewDate() != null) {
                    cbModel.setFilingLegalReviewDateStr(TeeDateUtil.format(cBase.getFilingLegalReviewDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setFilingLegalReviewDateStr("");
                }
                if (cBase.getCaseSources() != null && cBase.getCaseSources().size() > 0) {
                    CaseCommonCaseSource caseSource = cBase.getCaseSources().get(0);
                    CaseCommonCaseSourceModel cacseSourceModel = new CaseCommonCaseSourceModel();
                    BeanUtils.copyProperties(caseSource, cacseSourceModel);
                    if (!TeeUtility.isNullorEmpty(caseSource.getCommonCaseDate())) {
                        cacseSourceModel.setCommonCaseDateStr(TeeDateUtil.format(caseSource.getCommonCaseDate(), "yyyy-MM-dd"));
                    }else {
                        cacseSourceModel.setCommonCaseDateStr("");
                    }
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setExcludes(new String[]{"caseCommonCaseSource"});
                    jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
                    jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                    JSONObject caseSourceJson = JSONObject.fromObject(cacseSourceModel, jsonConfig);
                    cbModel.setCaseSourceJsonStr(caseSourceJson.toString());
                }
                if (cBase.getCaseCommonStaffs() != null && cBase.getCaseCommonStaffs().size() > 0) {
                    List<CaseCommonStaff> staffList = cBase.getCaseCommonStaffs();
                    String staffJsonStr = "";
                    for (CaseCommonStaff staff : staffList) {
                        staffJsonStr = staffJsonStr + staff.getIdentityId() + ",";
                    }
                    staffJsonStr = staffJsonStr.substring(0, staffJsonStr.length()-1);
                    cbModel.setPersonJsonStr(staffJsonStr);
                }
            } else if("02".equals(grading)){//调查取证
                //调查取证时间处理
                if (!TeeUtility.isNullorEmpty(cBase.getSurveyEndDate())) {
                    cbModel.setSurveyEndDateStr(TeeDateUtil.format(cBase.getSurveyEndDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setSurveyEndDateStr("");
                }
                //行政处罚事先告知书制发日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cBase.getPunishHearingMakingDate())) {
                    cbModel.setPunishHearingMakingDateStr(TeeDateUtil.format(cBase.getPunishHearingMakingDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setPunishHearingMakingDateStr("");
                }
                //行政处罚事先告知书送达日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cBase.getPunishHearingSendDate())) {
                    cbModel.setPunishHearingSendDateStr(TeeDateUtil.format(cBase.getPunishHearingSendDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setPunishHearingSendDateStr("");
                }
                //证据先行登记保存登记日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cBase.getSavePriorEvidenceDate())) {
                    cbModel.setSavePriorEvidenceDateStr(TeeDateUtil.format(cBase.getSavePriorEvidenceDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setSavePriorEvidenceDateStr("");
                }
                //证据先行登记保存处理日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cBase.getDealPriorEvidenceDate())) {
                    cbModel.setDealPriorEvidenceDateStr(TeeDateUtil.format(cBase.getDealPriorEvidenceDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setDealPriorEvidenceDateStr("");
                }
                //延期起始日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cBase.getDelayStartDateSearch())) {
                    cbModel.setDelayStartDateSearchStr(TeeDateUtil.format(cBase.getDelayStartDateSearch(), "yyyy-MM-dd"));
                }else {
                    cbModel.setDelayStartDateSearchStr("");
                }
                //延期结束日期(案件调查)
                if (!TeeUtility.isNullorEmpty(cBase.getDelayEndDateSearch())) {
                    cbModel.setDelayEndDateSearchStr(TeeDateUtil.format(cBase.getDelayEndDateSearch(), "yyyy-MM-dd"));
                }else {
                    cbModel.setDelayEndDateSearchStr("");
                }
                //听证日期处理
                if (!TeeUtility.isNullorEmpty(cBase.getHearingDate())) {
                    cbModel.setHearingDateStr(TeeDateUtil.format(cBase.getHearingDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setHearingDateStr("");
                }
                //听证举行日期处理
                if (!TeeUtility.isNullorEmpty(cBase.getHearingHoldDate())) {
                    cbModel.setHearingHoldDateStr(TeeDateUtil.format(cBase.getHearingHoldDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setHearingHoldDateStr("");
                }
                //责令改正起止日期
                if (!TeeUtility.isNullorEmpty(cBase.getCorrectStartdate())) {
                    cbModel.setCorrectStartdateStr(TeeDateUtil.format(cBase.getCorrectStartdate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setCorrectStartdateStr("");
                }
                //责令改正结束日期
                if (!TeeUtility.isNullorEmpty(cBase.getCorrectEnddate())) {
                    cbModel.setCorrectEnddateStr(TeeDateUtil.format(cBase.getCorrectEnddate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setCorrectEnddateStr("");
                }
                //告知书送达日期
                if (!TeeUtility.isNullorEmpty(cBase.getInformingbookDeliveryDate())) {
                    cbModel.setInformingbookDeliveryDateStr(TeeDateUtil.format(cBase.getInformingbookDeliveryDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setInformingbookDeliveryDateStr("");
                }
                
            } else if("03".equals(grading)){//处罚决定
                //案件处理呈批日期
                if (!TeeUtility.isNullorEmpty(cBase.getCaseHandleDate())) {
                    cbModel.setCaseHandleDateStr(TeeDateUtil.format(cBase.getCaseHandleDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setCaseHandleDateStr("");
                }
                //法制审核日期
                if (!TeeUtility.isNullorEmpty(cBase.getLegalReviewDate())) {
                    cbModel.setLegalReviewDateStr(TeeDateUtil.format(cBase.getLegalReviewDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setLegalReviewDateStr("");
                }
                //集体讨论日期
                if (!TeeUtility.isNullorEmpty(cBase.getCollectiveDiscussionDate())) {
                    cbModel.setCollectiveDiscussionDateStr(TeeDateUtil.format(cBase.getCollectiveDiscussionDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setCollectiveDiscussionDateStr("");
                }
                //处罚决定书日期
                if (!TeeUtility.isNullorEmpty(cBase.getPunishmentDate())) {
                    cbModel.setPunishmentDateStr(TeeDateUtil.format(cBase.getPunishmentDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setPunishmentDateStr("");
                }
                //处罚决定书送达日期
                if (!TeeUtility.isNullorEmpty(cBase.getPdSentDate())) {
                    cbModel.setPdSentDateStr(TeeDateUtil.format(cBase.getPdSentDate(),"yyyy-MM-dd"));
                }else {
                    cbModel.setPdSentDateStr("");
                }
                //处罚缴款书送达日期
                if (!TeeUtility.isNullorEmpty(cBase.getPpSentDate())) {
                    cbModel.setPpSentDateStr(TeeDateUtil.format(cBase.getPpSentDate(),"yyyy-MM-dd"));
                }else {
                    cbModel.setPpSentDateStr("");
                }
                //吊销许可证或营业执照起止日期
                if (!TeeUtility.isNullorEmpty(cBase.getStartdateWithhold())) {
                    cbModel.setStartdateWithholdStr(TeeDateUtil.format(cBase.getStartdateWithhold(), "yyyy-MM-dd"));
                }else {
                    cbModel.setStartdateWithholdStr("");
                }
                //吊销许可证或营业执照结束日期
                if (!TeeUtility.isNullorEmpty(cBase.getEnddateWithhold())) {
                    cbModel.setEnddateWithholdStr(TeeDateUtil.format(cBase.getEnddateWithhold(),"yyyy-MM-dd"));
                }else {
                    cbModel.setEnddateWithholdStr("");
                }
                //责令限期改正起止时间
                if (!TeeUtility.isNullorEmpty(cBase.getCorrectStartdateDecision())) {
                    cbModel.setCorrectStartdateDecisionStr(TeeDateUtil.format(cBase.getCorrectStartdateDecision(), "yyyy-MM-dd"));
                }else {
                    cbModel.setCorrectStartdateDecisionStr("");
                }
                //责令限期改正结束日期
                if (!TeeUtility.isNullorEmpty(cBase.getCorrectEnddateDecision())) {
                    cbModel.setCorrectEnddateDecisionStr(TeeDateUtil.format(cBase.getCorrectEnddateDecision(), "yyyy-MM-dd"));
                }else {
                    cbModel.setCorrectEnddateDecisionStr("");
                }
                //拘留开始时间
                if (!TeeUtility.isNullorEmpty(cBase.getStartdateDetain())) {
                    cbModel.setStartdateDetainStr(TeeDateUtil.format(cBase.getStartdateDetain(), "yyyy-MM-dd"));
                }else {
                    cbModel.setStartdateDetainStr("");
                }
                //拘留结束日期
                if (!TeeUtility.isNullorEmpty(cBase.getEnddateDetain())) {
                    cbModel.setEnddateDetainStr(TeeDateUtil.format(cBase.getEnddateDetain(), "yyyy-MM-dd"));
                }else {
                    cbModel.setEnddateDetainStr("");
                }
                //违法行为（职权）ID
                if (cBase.getCaseCommonPowers() != null && cBase.getCaseCommonPowers().size() > 0) {
                    List<CaseCommonPower> powerList = cBase.getCaseCommonPowers();
                    String powerJsonStr = "";
                    for (CaseCommonPower power : powerList) {
                        powerJsonStr = powerJsonStr + power.getPowerId() + ",";
                    }
                    if (powerJsonStr.length() > 0) {
                        powerJsonStr = powerJsonStr.substring(0, powerJsonStr.length()-1);
                    }
                    cbModel.setPowerJsonStr(powerJsonStr);
                }
                //违法依据
                if (cBase.getCaseCommonGists() != null && cBase.getCaseCommonGists().size() > 0) {
                    List<CaseCommonGist> gistList = cBase.getCaseCommonGists();
                    String gistJsonStr = "";
                    for (CaseCommonGist gist : gistList) {
                        gistJsonStr = gistJsonStr + gist.getGistId() + ",";
                    }
                    if (gistJsonStr.length() > 0) {
                        gistJsonStr = gistJsonStr.substring(0, gistJsonStr.length()-1);
                    }
                    cbModel.setGistJsonStr(gistJsonStr);
                }
                //处罚依据ID
                if (cBase.getCaseCommonPunishs() != null && cBase.getCaseCommonPunishs().size() > 0) {
                    List<CaseCommonPunish> punishList = cBase.getCaseCommonPunishs();
                    String punishJsonStr = "";
                    for (CaseCommonPunish punish : punishList) {
                        punishJsonStr = punishJsonStr + punish.getGistId() + ",";
                    }
                    if (punishJsonStr.length() > 0) {
                        punishJsonStr = punishJsonStr.substring(0, punishJsonStr.length()-1);
                    }
                    cbModel.setPunishJsonStr(punishJsonStr);
                }
                //自由裁量ID
                if (cBase.getCaseCommonDiscretionarys() != null && cBase.getCaseCommonDiscretionarys().size() > 0) {
                    List<CaseCommonDiscretionary> discretionaryList = cBase.getCaseCommonDiscretionarys();
                    String discretionaryJsonStr = "";
                    for (CaseCommonDiscretionary discretionary : discretionaryList) {
                        discretionaryJsonStr = discretionaryJsonStr + discretionary.getDiscretionaryId() + ",";
                    }
                    if (discretionaryJsonStr.length() > 0) {
                        discretionaryJsonStr = discretionaryJsonStr.substring(0, discretionaryJsonStr.length()-1);
                    }
                    cbModel.setDiscretionaryJsonStr(discretionaryJsonStr);
                }
            } else if("04".equals(grading)){//处罚执行
                //行政处罚决定执行日期
                if (!TeeUtility.isNullorEmpty(cBase.getPunishDecisionExecutDate())) {
                    cbModel.setPunishDecisionExecutDateStr(TeeDateUtil.format(cBase.getPunishDecisionExecutDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setPunishDecisionExecutDateStr("");
                }
                //整改日期
                if (!TeeUtility.isNullorEmpty(cBase.getCorrectDate())) {
                    cbModel.setCorrectDateStr(TeeDateUtil.format(cBase.getCorrectDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setCorrectDateStr("");
                }
                //延期截至时间
                if (!TeeUtility.isNullorEmpty(cBase.getPostponedToDelay())) {
                    cbModel.setPostponedToDelayStr(TeeDateUtil.format(cBase.getPostponedToDelay(), "yyyy-MM-dd"));
                }else {
                    cbModel.setPostponedToDelayStr("");
                }
                //延期申请日期
                if (!TeeUtility.isNullorEmpty(cBase.getApplyDateDelay())) {
                    cbModel.setApplyDateDelayStr(TeeDateUtil.format(cBase.getApplyDateDelay(), "yyyy-MM-dd"));
                }else {
                    cbModel.setApplyDateDelayStr("");
                }
                //延期批准日期
                if (!TeeUtility.isNullorEmpty(cBase.getApprovalDateDelay())) {
                    cbModel.setApprovalDateDelayStr(TeeDateUtil.format(cBase.getApprovalDateDelay(), "yyyy-MM-dd"));
                }else {
                    cbModel.setApprovalDateDelayStr("");
                }
                //分期截至时间
                if (!TeeUtility.isNullorEmpty(cBase.getDeadlineStage())) {
                    cbModel.setDeadlineStageStr(TeeDateUtil.format(cBase.getDeadlineStage(), "yyyy-MM-dd"));
                }else {
                    cbModel.setDeadlineStageStr("");
                }
                //分期申请日期
                if (!TeeUtility.isNullorEmpty(cBase.getApplyDateStage())) {
                    cbModel.setApplyDateStageStr(TeeDateUtil.format(cBase.getApplyDateStage(), "yyyy-MM-dd"));
                }else {
                    cbModel.setApplyDateStageStr("");
                }
                //分期批准日期
                if (!TeeUtility.isNullorEmpty(cBase.getApprovalDateStage())) {
                    cbModel.setApprovalDateStageStr(TeeDateUtil.format(cBase.getApprovalDateStage(), "yyyy-MM-dd"));
                }else {
                    cbModel.setApprovalDateStageStr("");
                }
                //当事人执行日期
                if (!TeeUtility.isNullorEmpty(cBase.getPartyActivePerforDate())) {
                    cbModel.setPartyActivePerforDateStr(TeeDateUtil.format(cBase.getPartyActivePerforDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setPartyActivePerforDateStr("");
                }
            } else if("05".equals(grading)){//结案
                if (!TeeUtility.isNullorEmpty(cBase.getClosedDate())) {
                    cbModel.setClosedDateStr(TeeDateUtil.format(cBase.getClosedDate(), "yyyy-MM-dd"));
                }else {
                    cbModel.setClosedDateStr("");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cbModel;
    }
    
    /**
     * 
    * @Function: updateOrdeleteOrSubmit()
    * @Description: 删除，提交或者退回
    *
    * @param: cbModel 参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月23日 下午6:09:21 
    *
     */
    @ResponseBody
    @RequestMapping("/updateOrdeleteOrSubmit")
    public TeeJson updateOrdeleteOrSubmit(CaseCommonBaseModel cbModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            caseCommonBaseService.updateOrdeleteOrSubmitByIds(cbModel);
            json.setRtState(true);
        } catch (Exception e) {
            json.setRtState(false);
            System.out.println("CaseCommonBaseController.submit() "+ e.getMessage());
        }
        return json;
    }
}
