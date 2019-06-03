package com.beidasoft.zfjd.adminCoercion.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.adminCoercion.bean.CoercionCaseBasic;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionMeasure;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionPerform;
import com.beidasoft.zfjd.adminCoercion.model.CoercionCaseBasicModel;
import com.beidasoft.zfjd.adminCoercion.model.CoercionMeasureModel;
import com.beidasoft.zfjd.adminCoercion.model.CoercionPerformModel;
import com.beidasoft.zfjd.adminCoercion.service.CoercionCaseBasicService;
import com.beidasoft.zfjd.adminCoercion.service.CoercionCourtPerformService;
import com.beidasoft.zfjd.adminCoercion.service.CoercionMeasureService;
import com.beidasoft.zfjd.adminCoercion.service.CoercionPerformService;
import com.beidasoft.zfjd.caseManager.commonCase.bean.CaseCommonBase;
import com.beidasoft.zfjd.caseManager.commonCase.model.CaseCommonBaseModel;
import com.beidasoft.zfjd.caseManager.commonCase.service.CaseCommonBaseService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.date.TeeDateUtil;

/**
 * 强制案件基础表CONTROLLER类
 */
@Controller
@RequestMapping("/coercionCaseSearchCtrl")
public class CoercionCaseSearchController {

    @Autowired
    private CoercionCaseBasicService coercionCaseBasicService;

    @Autowired
    private CoercionMeasureService coercionMeasureService;

    @Autowired
    private CoercionPerformService coercionPerformService;

    @Autowired
    private CaseCommonBaseService caseCommonBaseService;

    /**
     * 获取强制案件列表
     *
     * @param coercionCaseBasicModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/coercionSearchListByPage.action")
    public TeeEasyuiDataGridJson coercionSearchListByPage(TeeDataGridModel dm,
            CoercionCaseBasicModel coercionCaseBasicModel, HttpServletRequest request) throws Exception {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        try {
            List<CoercionCaseBasicModel> coercionCaseBasicModels = new ArrayList<CoercionCaseBasicModel>();

            // 获取主体案件列表
            List<CoercionCaseBasic> cases = coercionCaseBasicService.coercionSearchListByPage(dm,
                    coercionCaseBasicModel);
            List<CoercionCaseBasicModel> caseModels = new ArrayList<CoercionCaseBasicModel>();
            Long total = coercionCaseBasicService.coercionSearchListCount(coercionCaseBasicModel);

            // 返回数据model处理
            CoercionCaseBasicModel rtModel = null;
            for (int i = 0; i < cases.size(); i++) {
                rtModel = new CoercionCaseBasicModel();
                CoercionCaseBasic temp = cases.get(i);

                BeanUtils.copyProperties(temp, rtModel);
                rtModel.setCreateDateStr(TeeDateUtil.format(temp.getCreateDate(), "yyyy-MM-dd"));
                // 案件来源
                if (temp.getCaseSourceType() != null) {
                    if (100 == temp.getCaseSourceType()) {
                        rtModel.setCaseSourceTypeStr("行政检查");
                    }
                    if (200 == temp.getCaseSourceType()) {
                        rtModel.setCaseSourceTypeStr("行政处罚");
                    }
                }
                // 处罚数据获取与拼接
                if (temp.getCaseSourceType() != null && temp.getCaseSourceType().equals(200)) {
                    CaseCommonBaseModel caseCommonBaseModel = new CaseCommonBaseModel();
                    caseCommonBaseModel.setId(temp.getCaseSourceId());
                    CaseCommonBase caseInfo = caseCommonBaseService.findCaseCommonBaseById(caseCommonBaseModel);
                    if (caseInfo != null) {
                        rtModel.setName(caseInfo.getName());
                    }
                }
                caseModels.add(rtModel);
            }
            gridJson.setRows(caseModels);
            gridJson.setTotal(total);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return gridJson;
    }

    /**
     * 打开强制行为信息查看页面
     *
     * @param coercionCaseBasicModel
     * @param request
     * @return
     */
    @RequestMapping("/seeCoercionCaseInfoPage")
    public void seeCoercionCaseInfoPage(CoercionCaseBasicModel caseBasicModel, String pageType,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            boolean paramsCheck = true;
            //判断是否传入强制案件基础表id
            if (caseBasicModel.getId() == null || "".equals(caseBasicModel.getId())) {
                //无强制案件基础表id。判断是否通过其他来源访问获取强制信息
                if (null == caseBasicModel.getCaseSourceId() || "".equals(caseBasicModel.getCaseSourceId())) {
                    paramsCheck = false;
                }
                if (null == caseBasicModel.getSubjectId() || "".equals(caseBasicModel.getSubjectId())) {
                    paramsCheck = false;
                }
                if (null == caseBasicModel.getCaseSourceType() || "".equals(caseBasicModel.getCaseSourceType())) {
                    paramsCheck = false;
                }
                if (null == caseBasicModel.getDepartmentId() || "".equals(caseBasicModel.getDepartmentId())) {
                    paramsCheck = false;
                }
                //参数校验完毕，允许进行查询，获取关联强制信息的基础表id
                if (paramsCheck) {
                    Integer srcType = caseBasicModel.getCaseSourceType();
                    if (srcType.equals(100) || srcType.equals(200)) {
                        // 案件来源为其他来源，则获取强制案件基础表id
                        List<CoercionCaseBasic> caseBasics = coercionCaseBasicService
                                .getCoercionBaseByOtherSrc(caseBasicModel);
                        if (caseBasics.size() > 0) {
                            caseBasicModel.setId(caseBasics.get(0).getId());
                        }
                    } else if (srcType.equals(300)) {
                        // 案件来源为直接强制的，可直接获取
                        //caseBasicModel.setId(caseBasicModel.getId());
                    }
                }
            }
            if(caseBasicModel.getId() != null && !"".equals(caseBasicModel.getId())){
                // 根据id查询强制基本信息
                CoercionCaseBasic baseCase = coercionCaseBasicService.getById(caseBasicModel.getId());
                // 处罚数据获取与拼接
                if (baseCase.getCaseSourceType() != null && baseCase.getCaseSourceType().equals(200)) {
                    CaseCommonBaseModel caseCommonBaseModel = new CaseCommonBaseModel();
                    caseCommonBaseModel.setId(baseCase.getCaseSourceId());
                    CaseCommonBase caseInfo = caseCommonBaseService.findCaseCommonBaseById(caseCommonBaseModel);
                    if (caseInfo != null) {
                        caseBasicModel.setName(caseInfo.getName());
                    }
                }
                request.setAttribute("baseInfo", caseBasicModel);
                request.setAttribute("pageType", pageType);
                request.getRequestDispatcher("/supervise/adminCoercion/coercionSearch/coercionCaseInfo-base.jsp")
                        .forward(request, response);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    /**
     * 获取某案件的强制措施列表
     *
     * @param coercionCaseBasicModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/measureListByPage.action")
    public TeeEasyuiDataGridJson measureListByPage(TeeDataGridModel dm, CoercionMeasureModel coercionMeasureModel,
            HttpServletRequest request) throws Exception {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        try {
            List<CoercionMeasureModel> measuresModels = new ArrayList<CoercionMeasureModel>();

            // 获取该案件采取的强制措施列表

            List<CoercionMeasure> measures = coercionMeasureService.listByPage(dm, coercionMeasureModel);
            Long total = coercionMeasureService.listCount(coercionMeasureModel);

            // 强制措施种类字典表
            List<Map<String, Object>> codeList = TeeSysCodeManager
                    .getChildSysCodeListByParentCodeNo("COERCION_MEASURE_TYPE");

            // 返回数据model处理
            CoercionMeasureModel rtModel = null;
            for (int i = 0; i < measures.size(); i++) {
                rtModel = new CoercionMeasureModel();
                CoercionMeasure temp = measures.get(i);

                BeanUtils.copyProperties(temp, rtModel);
                rtModel.setCreateDateStr(TeeDateUtil.format(temp.getCreateDate(), "yyyy-MM-dd"));
                if (temp.getApplyDate() != null) {
                    rtModel.setApplyDateStr(TeeDateUtil.format(temp.getApplyDate(), "yyyy-MM-dd"));
                } else {
                    rtModel.setApplyDateStr("");
                }
                if (temp.getApproveDate() != null) {
                    rtModel.setApproveDateStr(TeeDateUtil.format(temp.getApproveDate(), "yyyy-MM-dd"));
                } else {
                    rtModel.setApproveDateStr("");
                }

                for (Map<String, Object> code : codeList) {
                    if (rtModel.getMeasureType().equals(code.get("codeNo").toString())) {
                        rtModel.setMeasureTypeStr(code.get("codeName").toString());
                        break;
                    }
                }
                measuresModels.add(rtModel);
            }

            gridJson.setRows(measuresModels);
            gridJson.setTotal(total);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return gridJson;
    }

    /**
     * 跳转强制措施详细信息页面
     *
     * @param coercionCaseBasicModel
     * @param request
     * @return
     */
    @RequestMapping("/measuresSeeInfoInput")
    public void measuresSeeInfoInput(CoercionMeasureModel coercionMeasureModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            CoercionMeasureModel model = new CoercionMeasureModel();
            if (coercionMeasureModel.getId() != null && !"".equals(coercionMeasureModel.getId())) {
                // 根据id查询强制措施信息
                CoercionMeasure beanInfo = coercionMeasureService.getById(coercionMeasureModel.getId());
                // 实体类-->model传值
                BeanUtils.copyProperties(beanInfo, model);

                model.setCreateDateStr(TeeDateUtil.format(beanInfo.getCreateDate(), "yyyy-MM-dd"));
                model.setApplyDateStr(TeeDateUtil.format(beanInfo.getApplyDate(), "yyyy-MM-dd"));
                model.setApproveDateStr(TeeDateUtil.format(beanInfo.getApproveDate(), "yyyy-MM-dd"));

                // 强制措施种类字典表
                List<Map<String, Object>> codeList = TeeSysCodeManager
                        .getChildSysCodeListByParentCodeNo("COERCION_MEASURE_TYPE");

                for (Map<String, Object> code : codeList) {
                    if (model.getMeasureType().equals(code.get("codeNo").toString())) {
                        model.setMeasureTypeStr(code.get("codeName").toString());
                        break;
                    }
                }
                // 强制措施种类字典表
                if (model.getCdSendType() != null) {
                    List<Map<String, Object>> sendWay = TeeSysCodeManager
                            .getChildSysCodeListByParentCodeNo("COMMON_SENT_WAY");
                    for (Map<String, Object> code : sendWay) {
                        if (model.getCdSendType().equals(code.get("codeNo").toString())) {
                            model.setCdSendTypeStr(code.get("codeName").toString());
                            break;
                        }
                    }
                }
            }
            request.setAttribute("baseInfo", coercionMeasureModel);
            request.setAttribute("measureInfo", model);
            request.getRequestDispatcher("/supervise/adminCoercion/coercionSearch/measure_seeDetail_input.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    /**
     * 获取行政机关强制执行信息列表
     *
     * @param coercionCaseBasicModel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/performListByPage.action")
    public TeeEasyuiDataGridJson performListByPage(TeeDataGridModel dm, CoercionPerformModel coercionPerformModel,
            HttpServletRequest request) throws Exception {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        try {
            List<CoercionPerformModel> performModels = new ArrayList<CoercionPerformModel>();

            // 获取该案件采取的强制措施列表
            List<CoercionPerform> performs = coercionPerformService.listByPage(dm, coercionPerformModel);
            Long total = coercionPerformService.listCount(coercionPerformModel);

            // 强制措施种类字典表
            List<Map<String, Object>> codeList = TeeSysCodeManager
                    .getChildSysCodeListByParentCodeNo("COERCION_PERFORM_TYPE");

            // 返回数据model处理
            CoercionPerformModel rtModel = null;
            for (int i = 0; i < performs.size(); i++) {
                rtModel = new CoercionPerformModel();
                CoercionPerform temp = performs.get(i);

                BeanUtils.copyProperties(temp, rtModel);
                rtModel.setCreateDateStr(TeeDateUtil.format(temp.getCreateDate(), "yyyy-MM-dd"));
                rtModel.setApplyDateStr(TeeDateUtil.format(temp.getApplyDate(), "yyyy-MM-dd"));
                rtModel.setApproveDateStr(TeeDateUtil.format(temp.getApproveDate(), "yyyy-MM-dd"));

                for (Map<String, Object> code : codeList) {
                    if (rtModel.getPerformType().equals(code.get("codeNo").toString())) {
                        rtModel.setPerformTypeStr(code.get("codeName").toString());
                        break;
                    }
                }
                performModels.add(rtModel);
            }

            gridJson.setRows(performModels);
            gridJson.setTotal(total);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        return gridJson;
    }

    /**
     * 跳转行政机关0强制执行详细信息页面
     *
     * @param coercionCaseBasicModel
     * @param request
     * @return
     */
    @RequestMapping("/performsSeeInfoInput")
    public void performsSeeInfoInput(CoercionPerformModel coercionPerformModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            CoercionPerformModel model = new CoercionPerformModel();
            if (coercionPerformModel.getId() != null && !"".equals(coercionPerformModel.getId())) {
                // 根据id查询强制措施信息
                CoercionPerform beanInfo = coercionPerformService.getById(coercionPerformModel.getId());
                // 实体类-->model传值
                BeanUtils.copyProperties(beanInfo, model);

                model.setCreateDateStr(TeeDateUtil.format(beanInfo.getCreateDate(), "yyyy-MM-dd"));
                model.setApplyDateStr(TeeDateUtil.format(beanInfo.getApplyDate(), "yyyy-MM-dd"));
                model.setApproveDateStr(TeeDateUtil.format(beanInfo.getApproveDate(), "yyyy-MM-dd"));

                // 强制措施种类字典表
                List<Map<String, Object>> codeList = TeeSysCodeManager
                        .getChildSysCodeListByParentCodeNo("COERCION_PERFORM_TYPE");

                for (Map<String, Object> code : codeList) {
                    if (model.getPerformType().equals(code.get("codeNo").toString())) {
                        model.setPerformTypeStr(code.get("codeName").toString());
                        break;
                    }
                }
                // 强制措施种类字典表
                List<Map<String, Object>> sendWay = TeeSysCodeManager
                        .getChildSysCodeListByParentCodeNo("COMMON_SENT_WAY");
                if (model.getPressSendType() != null) {
                    for (Map<String, Object> code : sendWay) {
                        if (model.getPressSendType().equals(code.get("codeNo").toString())) {
                            model.setPressSendType(code.get("codeName").toString());
                            break;
                        }
                    }
                }
            }
            request.setAttribute("baseInfo", coercionPerformModel);
            request.setAttribute("performInfo", model);
            request.getRequestDispatcher("/supervise/adminCoercion/coercionSearch/perform_seeDetail_input.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

}
