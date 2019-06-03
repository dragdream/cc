package com.beidasoft.zfjd.adminCoercion.controller;

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

import com.beidasoft.zfjd.adminCoercion.bean.CoercionCaseBasic;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionCaseGist;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionCasePower;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionCourtPerform;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionMeasure;
import com.beidasoft.zfjd.adminCoercion.bean.CoercionPerform;
import com.beidasoft.zfjd.adminCoercion.model.CoercionCaseBasicModel;
import com.beidasoft.zfjd.adminCoercion.model.CoercionCourtPerformModel;
import com.beidasoft.zfjd.adminCoercion.model.CoercionMeasureModel;
import com.beidasoft.zfjd.adminCoercion.model.CoercionPerformModel;
import com.beidasoft.zfjd.adminCoercion.service.CoercionCaseBasicService;
import com.beidasoft.zfjd.adminCoercion.service.CoercionCourtPerformService;
import com.beidasoft.zfjd.adminCoercion.service.CoercionMeasureService;
import com.beidasoft.zfjd.adminCoercion.service.CoercionPerformService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeePerson;
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
 * 强制案件基础表CONTROLLER类
 */
@Controller
@RequestMapping("/coercionCaseCtrl")
public class CoercionCaseBasicController {

    @Autowired
    private CoercionCaseBasicService coercionCaseBasicService;

    @Autowired
    private CoercionMeasureService coercionMeasureService;

    @Autowired
    private CoercionPerformService coercionPerformService;

    @Autowired
    private CoercionCourtPerformService coercionCourtPerformService;

    /**
     * 保存强制案件基础表数据
     *
     * @param model
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(CoercionCaseBasicModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        CoercionCaseBasic beanInfo = new CoercionCaseBasic();

        // model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);

        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }

    /**
     * 通过id获取强制措施数据
     *
     * @param id
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping("/getMeasureInfo.action")
    public TeeJson getMeasureInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        CoercionMeasureModel model = new CoercionMeasureModel();
        if (id != null && !"".equals(id)) {
            CoercionMeasure beanInfo = coercionMeasureService.getById(id);
            // 实体类-->model传值
            BeanUtils.copyProperties(beanInfo, model);

            // 职权转换
            if (beanInfo.getPowers() != null && beanInfo.getPowers().size() > 0) {
                List<CoercionCasePower> powerList = beanInfo.getPowers();
                String powerJsonStr = "";
                for (CoercionCasePower power : powerList) {
                    powerJsonStr = powerJsonStr + power.getPowerId() + ",";
                }
                powerJsonStr = powerJsonStr.substring(0, powerJsonStr.length() - 1);
                model.setPowerJsonStr(powerJsonStr);
            }
            // 依据转换
            if (beanInfo.getGists() != null && beanInfo.getGists().size() > 0) {
                List<CoercionCaseGist> gistList = beanInfo.getGists();
                String gistJsonStr = "";
                for (CoercionCaseGist gist : gistList) {
                    gistJsonStr = gistJsonStr + gist.getGistId() + ",";
                }
                gistJsonStr = gistJsonStr.substring(0, gistJsonStr.length() - 1);
                model.setGistJsonStr(gistJsonStr);
            }

            // 强制措施种类字典表
            List<Map<String, Object>> codeList = TeeSysCodeManager
                    .getChildSysCodeListByParentCodeNo("COERCION_MEASURE_TYPE");

            for (Map<String, Object> code : codeList) {
                if (model.getMeasureType().equals(code.get("codeNo").toString())) {
                    model.setMeasureTypeStr(code.get("codeName").toString());
                    break;
                }
            }
        }
        json.setRtData(model);
        json.setRtState(true);
        return json;
    }

    /**
     * 通过id获取强制执行数据
     *
     * @param id
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping("/getPerofrmInfo.action")
    public TeeJson getPerofrmInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        CoercionMeasureModel model = new CoercionMeasureModel();
        if (id != null && !"".equals(id)) {
            CoercionMeasure beanInfo = coercionMeasureService.getById(id);
            // 实体类-->model传值
            BeanUtils.copyProperties(beanInfo, model);

            // 职权转换
            if (beanInfo.getPowers() != null && beanInfo.getPowers().size() > 0) {
                List<CoercionCasePower> powerList = beanInfo.getPowers();
                String powerJsonStr = "";
                for (CoercionCasePower power : powerList) {
                    powerJsonStr = powerJsonStr + power.getPowerId() + ",";
                }
                powerJsonStr = powerJsonStr.substring(0, powerJsonStr.length() - 1);
                model.setPowerJsonStr(powerJsonStr);
            }
            // 依据转换
            if (beanInfo.getGists() != null && beanInfo.getGists().size() > 0) {
                List<CoercionCaseGist> gistList = beanInfo.getGists();
                String gistJsonStr = "";
                for (CoercionCaseGist gist : gistList) {
                    gistJsonStr = gistJsonStr + gist.getGistId() + ",";
                }
                gistJsonStr = gistJsonStr.substring(0, gistJsonStr.length() - 1);
                model.setGistJsonStr(gistJsonStr);
            }

            // 强制措施种类字典表
            List<Map<String, Object>> codeList = TeeSysCodeManager
                    .getChildSysCodeListByParentCodeNo("COERCION_MEASURE_TYPE");

            for (Map<String, Object> code : codeList) {
                if (model.getMeasureType().equals(code.get("codeNo").toString())) {
                    model.setMeasureTypeStr(code.get("codeName").toString());
                    break;
                }
            }
        }
        json.setRtData(model);
        json.setRtState(true);
        return json;
    }

    @ResponseBody
    @RequestMapping("/caseListByPage.action")
    public TeeEasyuiDataGridJson caseListByPage(TeeDataGridModel dm, String subjectId, HttpServletRequest request)
            throws Exception {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        try {
            List<CoercionCaseBasicModel> coercionCaseBasicModels = new ArrayList<CoercionCaseBasicModel>();
            // 是否指明案件所属id
            if (null == subjectId || "".equals(subjectId)) {
                gridJson.setRows(coercionCaseBasicModels);
                gridJson.setTotal(0L);
                return gridJson;
            }

            // 获取主体案件列表
            CoercionCaseBasicModel coercionCaseBasicModel = new CoercionCaseBasicModel();
            coercionCaseBasicModel.setSubjectId(subjectId);
            List<CoercionCaseBasic> cases = coercionCaseBasicService.listByPage(dm, coercionCaseBasicModel);
            List<CoercionCaseBasicModel> caseModels = new ArrayList<CoercionCaseBasicModel>();
            Long total = coercionCaseBasicService.listCount(coercionCaseBasicModel);

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
	 * 获取采取的强制措施信息列表
	 *
	 * @param coercionCaseBasicModel
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/measureListByPage.action")
    public TeeEasyuiDataGridJson measureListByPage(TeeDataGridModel dm, CoercionCaseBasicModel caseBasicModel,
            HttpServletRequest request) throws Exception {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        try {
            List<CoercionMeasureModel> measuresModels = new ArrayList<CoercionMeasureModel>();
            // 是否指明案件来源
            if (null == caseBasicModel.getCaseSourceType()) {
                gridJson.setRows(measuresModels);
                gridJson.setTotal(0L);
                return gridJson;
            } else {
                String srcType = TeeStringUtil.getString(request.getParameter("caseSourceType"), "");
                if (srcType.equals("100") || srcType.equals("200")) {
                    // 案件来源为其他来源，则获取强制案件基础表id
                    List<CoercionCaseBasic> caseBasics = coercionCaseBasicService
                            .getCoercionBaseByOtherSrc(caseBasicModel);
                    if (caseBasics.size() > 0) {
                        caseBasicModel.setId(caseBasics.get(0).getId());
                    } else {// 该案件还未有强制措施信息
                        gridJson.setRows(measuresModels);
                        gridJson.setTotal(0L);
                        return gridJson;
                    }
                } else if (srcType.equals("300")) {
                    // 案件来源为直接强制的，可直接获取
                    caseBasicModel.setId(caseBasicModel.getId());
                }
            }

            // 获取该案件采取的强制措施列表
            CoercionMeasureModel coercionMeasureModel = new CoercionMeasureModel();
            coercionMeasureModel.setCoercionCaseId(caseBasicModel.getId());
            List<CoercionMeasure> measures = coercionMeasureService.listByPage(dm, coercionMeasureModel);

            Long total = coercionMeasureService.listCount(coercionMeasureModel);

            // 强制措施种类字典表
            List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("COERCION_MEASURE_TYPE");

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
	 * 获取行政机关强制执行信息列表
	 *
	 * @param coercionCaseBasicModel
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/performListByPage.action")
    public TeeEasyuiDataGridJson performListByPage(TeeDataGridModel dm, CoercionCaseBasicModel caseBasicModel,
            HttpServletRequest request) throws Exception {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        try {
            List<CoercionPerformModel> performModels = new ArrayList<CoercionPerformModel>();
            // 是否指明案件来源
            if (null == caseBasicModel.getCaseSourceType()) {
                gridJson.setRows(performModels);
                gridJson.setTotal(0L);
                return gridJson;
            } else {
                String srcType = TeeStringUtil.getString(request.getParameter("caseSourceType"), "");
                if (srcType.equals("100") || srcType.equals("200")) {
                    // 案件来源为其他来源，则获取强制案件基础表id
                    List<CoercionCaseBasic> caseBasics = coercionCaseBasicService
                            .getCoercionBaseByOtherSrc(caseBasicModel);
                    if (caseBasics.size() > 0) {
                        caseBasicModel.setId(caseBasics.get(0).getId());
                    } else {// 该案件还未有强制措施信息
                        gridJson.setRows(performModels);
                        gridJson.setTotal(0L);
                        return gridJson;
                    }
                } else if (srcType.equals("300")) {
                    // 案件来源为直接强制的，可直接获取
                    caseBasicModel.setId(caseBasicModel.getId());
                }
            }

            // 获取该案件采取的强制措施列表
            CoercionPerformModel coercionPerformModel = new CoercionPerformModel();
            coercionPerformModel.setCoercionCaseId(caseBasicModel.getId());
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
	 * 强制案件信息直接填录-基础编辑页面
	 *
	 * @param caseBasicModel
	 * @param request
	 * @return
	 */
    @RequestMapping("/directEditInput.action")
    public void directEditInput(CoercionCaseBasicModel caseBasicModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            boolean paramsCheck = true;
            //数据校验，确认请求来源
            if (null == caseBasicModel.getCaseSourceType() || "".equals(caseBasicModel.getCaseSourceType())) {
                paramsCheck = false;
            }
            if (paramsCheck) {
                Integer srcType = caseBasicModel.getCaseSourceType();
                if (srcType.equals(300)) {
                    // 案件来源为直接强制的，可直接打开页面
                	CoercionCaseBasic caseInfo = null;
                	if(caseBasicModel.getId() != null && !"".equals(caseBasicModel.getId())){
                		//id不为空，尝试获取已有数据
                		caseInfo = coercionCaseBasicService.getById(caseBasicModel.getId());
                	}
                    caseBasicModel.setId(caseBasicModel.getId());
                    request.setAttribute("basicInfo", caseInfo);
                    request.getRequestDispatcher("/supervise/adminCoercion/coercion_direct_input.jsp").forward(request,
                            response);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
	/**
	 * 强制措施信息编辑-列表展示页面
	 *
	 * @param caseBasicModel
	 * @param request
	 * @return
	 */
    @RequestMapping("/measuresInput")
    public void measuresInput(CoercionCaseBasicModel caseBasicModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            boolean paramsCheck = true;
            if (caseBasicModel.getId() == null || "".equals(caseBasicModel.getId())) {
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
            }
            if (paramsCheck) {
                // 案件来源为其他来源，则获取强制案件基础表id
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
                    caseBasicModel.setId(caseBasicModel.getId());
                }

                request.setAttribute("basicInfo", caseBasicModel);
                request.getRequestDispatcher("/supervise/adminCoercion/measure_base_input.jsp").forward(request,
                        response);
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

	/**
	 * 强制执行信息编辑-列表展示页面
	 *
	 * @param caseBasicModel
	 * @param request
	 * @return
	 */
    @RequestMapping("/performsInput")
    public void performsInput(CoercionCaseBasicModel caseBasicModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            boolean paramsCheck = true;
            if (caseBasicModel.getId() == null || "".equals(caseBasicModel.getId())) {
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
            }
            if (paramsCheck) {
                // 案件来源为其他来源，则获取强制案件基础表id
                // 是否指明案件来源
                if (null == caseBasicModel.getCaseSourceType()) {
                    // 无数据来源说明
                } else {
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
                        caseBasicModel.setId(caseBasicModel.getId());
                    }
                }
                // 尝试获取法院强制执行数据
                if (caseBasicModel.getId() != null && !"".equals(caseBasicModel.getId())) {
                    List<CoercionCourtPerform> courtPerforms = coercionCourtPerformService
                            .getByCoercionCaseId(caseBasicModel.getId());
                    if (courtPerforms != null && courtPerforms.size() > 0) {
                        CoercionCourtPerformModel rtModel = new CoercionCourtPerformModel();
                        CoercionCourtPerform temp = courtPerforms.get(0);
                        BeanUtils.copyProperties(temp, rtModel);
                        if (temp.getPunishDateBefore() != null) {
                            rtModel.setPunishDateBeforeStr(
                                    TeeDateUtil.format(temp.getPunishDateBefore(), "yyyy-MM-dd"));
                        } else {
                            rtModel.setPunishDateBeforeStr("");
                        }
                        if (temp.getPressSendDate() != null) {
                            rtModel.setPressSendDateStr(TeeDateUtil.format(temp.getPressSendDate(), "yyyy-MM-dd"));
                        } else {
                            rtModel.setPressSendDateStr("");
                        }
                        if (temp.getSecondPressDate() != null) {
                            rtModel.setSecondPressDateStr(TeeDateUtil.format(temp.getSecondPressDate(), "yyyy-MM-dd"));
                        } else {
                            rtModel.setSecondPressDateStr("");
                        }
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
                        request.setAttribute("performInfo", rtModel);
                    }
                }
                request.setAttribute("basicInfo", caseBasicModel);
                request.getRequestDispatcher("/supervise/adminCoercion/perform_base_input.jsp").forward(request,
                        response);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

	/**
	 * 强制措施信息编辑-编辑页面
	 *
	 * @param coercionMeasureModel
	 * @param request
	 * @return
	 */
    @RequestMapping("/measuresEditInput")
    public void measuresEditInput(CoercionMeasureModel coercionMeasureModel, HttpServletRequest request,
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
            }
            request.setAttribute("baseInfo", coercionMeasureModel);
            request.setAttribute("measureInfo", model);
            request.getRequestDispatcher("/supervise/adminCoercion/measure_edit_input.jsp").forward(request, response);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

	/**
	 * 强制执行信息编辑-编辑页面
	 *
	 * @param coercionMeasureModel
	 * @param request
	 * @return
	 */
    @RequestMapping("/performsEditInput")
    public void performsEditInput(CoercionPerformModel coercionPerformModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            // 根据id查询强制措施信息
            CoercionPerformModel model = new CoercionPerformModel();
            if (coercionPerformModel.getId() != null && !"".equals(coercionPerformModel.getId())) {
                CoercionPerform beanInfo = coercionPerformService.getById(coercionPerformModel.getId());
                if (beanInfo != null) {
                    if (beanInfo.getId() != null && !"".equals(beanInfo.getId())) {

                        // 实体类-->model传值
                        BeanUtils.copyProperties(beanInfo, model);
                        // 催告
                        model.setPunishDateBeforeStr(TeeDateUtil.format(beanInfo.getPunishDateBefore(), "yyyy-MM-dd"));
                        model.setPressSendDateStr(TeeDateUtil.format(beanInfo.getPressSendDate(), "yyyy-MM-dd"));
                        model.setOriginalDateStr(TeeDateUtil.format(beanInfo.getOriginalDate(), "yyyy-MM-dd"));
                        model.setSecondPressDateStr(TeeDateUtil.format(beanInfo.getSecondPressDate(), "yyyy-MM-dd"));
                        // 申请
                        model.setApplyDateStr(TeeDateUtil.format(beanInfo.getApplyDate(), "yyyy-MM-dd"));
                        model.setApproveDateStr(TeeDateUtil.format(beanInfo.getApproveDate(), "yyyy-MM-dd"));
                        // 事项处理
                        model.setPerformEnforceDateStr(
                                TeeDateUtil.format(beanInfo.getPerformEnforceDate(), "yyyy-MM-dd"));
                        model.setDecideSendDateStr(TeeDateUtil.format(beanInfo.getDecideSendDate(), "yyyy-MM-dd"));
                        model.setTransNoticeDateStr(TeeDateUtil.format(beanInfo.getTransNoticeDate(), "yyyy-MM-dd"));
                        model.setAuctionDateStr(TeeDateUtil.format(beanInfo.getAuctionDate(), "yyyy-MM-dd"));
                        model.setReplaceEnforceDateStr(
                                TeeDateUtil.format(beanInfo.getReplaceEnforceDate(), "yyyy-MM-dd"));
                        model.setAgreeSignDateStr(TeeDateUtil.format(beanInfo.getAgreeSignDate(), "yyyy-MM-dd"));
                        model.setEnforceReturnDateStr(
                                TeeDateUtil.format(beanInfo.getEnforceReturnDate(), "yyyy-MM-dd"));
                        model.setEndEnforceSendDateStr(
                                TeeDateUtil.format(beanInfo.getEndEnforceSendDate(), "yyyy-MM-dd"));
                        model.setBreakEnforceDateStr(TeeDateUtil.format(beanInfo.getBreakEnforceDate(), "yyyy-MM-dd"));
                        model.setRelwaseEnforceDateStr(
                                TeeDateUtil.format(beanInfo.getRelwaseEnforceDate(), "yyyy-MM-dd"));
                        // 强制执行方式字典表
                        List<Map<String, Object>> codeList = TeeSysCodeManager
                                .getChildSysCodeListByParentCodeNo("COERCION_PERFORM_TYPE");

                        for (Map<String, Object> code : codeList) {
                            if (model.getPerformType().equals(code.get("codeNo").toString())) {
                                model.setPerformTypeStr(code.get("codeName").toString());
                                break;
                            }
                        }
                    }
                }
            }
            request.setAttribute("baseInfo", coercionPerformModel);
            request.setAttribute("performInfo", model);
            request.getRequestDispatcher("/supervise/adminCoercion/perform_self_edit_input.jsp").forward(request,
                    response);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

	/**
	 * 强制措施信息保存-强制措施类型
	 *
	 * @param coercionMeasureModel
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/saveMeasureType.action")
    public TeeJson saveMeasureType(CoercionMeasureModel coercionMeasureModel, HttpServletRequest request)
            throws Exception {
        TeeJson result = new TeeJson();
        JSONObject jsonObject = new JSONObject();
        try {
            // 是否需要新增主表判断
            if (null == coercionMeasureModel.getCoercionCaseId()
                    || "".equals(coercionMeasureModel.getCoercionCaseId())) {
                // coercionCaseId为空，认为该数据为新增数据
                // 因此在保存是应先新增主表数据
                CoercionCaseBasic coercionCaseBasic = new CoercionCaseBasic();
                coercionCaseBasic.setId(UUID.randomUUID().toString());
                coercionCaseBasic.setCaseSourceType(coercionMeasureModel.getCaseSourceType());
                coercionCaseBasic.setCaseSourceId(coercionMeasureModel.getCaseSourceId());
                coercionCaseBasic.setSubjectId(coercionMeasureModel.getSubjectId());
                coercionCaseBasic.setDepartmentId(coercionMeasureModel.getDepartmentId());
                coercionCaseBasic.setIsDelete(0);
                coercionCaseBasic.setIsSubmit(0);
                coercionCaseBasic.setCreateDate(new Date());
                coercionCaseBasic.setCreateType(2);
                coercionCaseBasicService.save(coercionCaseBasic);
                // 保存后获取主表id
                coercionMeasureModel.setCoercionCaseId(coercionCaseBasic.getId());
                jsonObject.put("coercionCaseId", coercionCaseBasic.getId());
            } else {
                jsonObject.put("coercionCaseId", coercionMeasureModel.getCoercionCaseId());
            }
            // 强制措施表信息保存
            CoercionMeasure coercionMeasure = new CoercionMeasure();
            if (null == coercionMeasureModel.getId() || "".equals(coercionMeasureModel.getId())) {
                // id为空，认为该数据为新增数据
                coercionMeasure.setId(UUID.randomUUID().toString());
                coercionMeasure.setCoercionCaseId(coercionMeasureModel.getCoercionCaseId());
                coercionMeasure.setMeasureType(coercionMeasureModel.getMeasureType());
                coercionMeasure.setCreateDate(new Date());
                coercionMeasure.setEnforceStep(1);
                coercionMeasureService.save(coercionMeasure);
                jsonObject.put("id", coercionMeasure.getId());
            } else {
                coercionMeasure.setId(coercionMeasureModel.getId());
                coercionMeasure.setMeasureType(coercionMeasureModel.getMeasureType());
                coercionMeasure.setUpdateDate(new Date());
                coercionMeasure.setEnforceStep(1);
                coercionMeasureService.udpateMeasureInfo(coercionMeasure);
                jsonObject.put("id", coercionMeasure.getId());
            }
            jsonObject.put("flag", 1);
            result.setRtData(jsonObject);
        } catch (Exception e) {
            // TODO: handle exception
            result.setRtData(-1);
            System.out.println(e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/saveMeasureApply.action")
    public TeeJson saveMeasureApply(CoercionMeasureModel coercionMeasureModel, HttpServletRequest request)
            throws Exception {
        TeeJson result = new TeeJson();
        try {
            // 是否需要新增主表判断
            if (null == coercionMeasureModel.getCoercionCaseId()
                    || "".equals(coercionMeasureModel.getCoercionCaseId())) {
                // coercionCaseId为空，认为该数据是错误数据
                // 因此在直接返回
            }
            // 强制措施表信息保存

            if (null != coercionMeasureModel.getId() || !"".equals(coercionMeasureModel.getId())) {
                // 获取原数据,甚至流程状态字段
                CoercionMeasure measureOld = coercionMeasureService.getById(coercionMeasureModel.getId());
                if (measureOld.getEnforceStep() >= 2) {
                    coercionMeasureModel.setEnforceStep(measureOld.getEnforceStep());
                } else {
                    coercionMeasureModel.setEnforceStep(2);
                }
                coercionMeasureService.updateMeasureApplyInfo(coercionMeasureModel);
                CoercionMeasure measure = new CoercionMeasure();
                measure.setId(coercionMeasureModel.getId());
                // 强制措施职权信息
                List<CoercionCasePower> powerList = buildPowerLists(coercionMeasureModel.getCoercionCaseId(), 1,
                        measure, null, coercionMeasureModel.getPowerJsonStr());
                coercionCaseBasicService.saveCasePower(coercionMeasureModel, null, powerList);
                // 强制措施依据信息
                List<CoercionCaseGist> gistList = buildGistLists(coercionMeasureModel.getCoercionCaseId(), 1, measure,
                        null, coercionMeasureModel.getGistJsonStr());
                coercionCaseBasicService.saveCaseGist(coercionMeasureModel, null, gistList);
            }

            result.setRtData(1);
        } catch (Exception e) {
            // TODO: handle exception
            result.setRtData(-1);
            System.out.println(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveMeasureResult.action")
    public TeeJson saveMeasureResult(CoercionMeasureModel coercionMeasureModel, HttpServletRequest request)
            throws Exception {
        TeeJson result = new TeeJson();
        try {
            // 是否需要新增主表判断
            if (null == coercionMeasureModel.getCoercionCaseId()
                    || "".equals(coercionMeasureModel.getCoercionCaseId())) {
                // coercionCaseId为空，认为该数据是错误数据
                // 因此在直接返回
            }
            // 强制措施表信息保存

            if (null != coercionMeasureModel.getId() || !"".equals(coercionMeasureModel.getId())) {
                // 获取原数据,甚至流程状态字段
                CoercionMeasure measureOld = coercionMeasureService.getById(coercionMeasureModel.getId());
                if (measureOld.getEnforceStep() >= 3) {
                    coercionMeasureModel.setEnforceStep(measureOld.getEnforceStep());
                } else {
                    coercionMeasureModel.setEnforceStep(3);
                }
                coercionMeasureService.updateMeasureResultInfo(coercionMeasureModel);
                CoercionMeasure measure = new CoercionMeasure();
                measure.setId(coercionMeasureModel.getId());
            }
            result.setRtData(1);
        } catch (Exception e) {
            // TODO: handle exception
            result.setRtData(-1);
            System.out.println(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/savePerformType.action")
    public TeeJson savePerformType(CoercionPerformModel coercionPerformModel, HttpServletRequest request)
            throws Exception {
        TeeJson result = new TeeJson();
        try {
            // 是否需要新增主表判断
            if (null == coercionPerformModel.getCoercionCaseId()
                    || "".equals(coercionPerformModel.getCoercionCaseId())) {
                // coercionCaseId为空，认为该数据为新增数据
                // 因此在保存是应先新增主表数据
                CoercionCaseBasic coercionCaseBasic = new CoercionCaseBasic();
                coercionCaseBasic.setId(UUID.randomUUID().toString());
                coercionCaseBasic.setCaseSourceType(coercionPerformModel.getCaseSourceType());
                coercionCaseBasic.setCaseSourceId(coercionPerformModel.getCaseSourceId());
                coercionCaseBasic.setSubjectId(coercionPerformModel.getSubjectId());
                coercionCaseBasic.setDepartmentId(coercionPerformModel.getDepartmentId());
                coercionCaseBasic.setCreateDate(new Date());
                coercionCaseBasic.setIsDelete(0);
                coercionCaseBasic.setIsSubmit(0);
                coercionCaseBasic.setCreateType(2);
                coercionCaseBasicService.save(coercionCaseBasic);
                // 保存后获取主表id
                coercionPerformModel.setCoercionCaseId(coercionCaseBasic.getId());
            }
            // 强制执行表信息保存
            CoercionPerform coercionPerform = new CoercionPerform();
            if (null == coercionPerformModel.getId() || "".equals(coercionPerformModel.getId())) {
                // id为空，认为该数据为新增数据
                coercionPerform.setId(UUID.randomUUID().toString());
                coercionPerform.setCoercionCaseId(coercionPerformModel.getCoercionCaseId());
                coercionPerform.setPerformType(coercionPerformModel.getPerformType());
                coercionPerform.setCreateDate(new Date());
                coercionPerform.setEnforceStep(1);
                coercionPerformService.save(coercionPerform);
            } else {
                coercionPerform.setId(coercionPerformModel.getId());
                coercionPerform.setPerformType(coercionPerformModel.getPerformType());
                coercionPerform.setUpdateDate(new Date());
                coercionPerformService.updatePerformTypeInfo(coercionPerform);
            }
            coercionPerformModel.setId(coercionPerform.getId());
            result.setRtState(true);
            result.setRtData(coercionPerformModel);
        } catch (Exception e) {
            // TODO: handle exception
            result.setRtData(false);
            System.out.println(e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping("/savePerformPress.action")
    public TeeJson savePerformPress(CoercionPerformModel coercionPerformModel, HttpServletRequest request)
            throws Exception {
        TeeJson result = new TeeJson();
        try {
            if (null == coercionPerformModel.getCoercionCaseId()
                    || "".equals(coercionPerformModel.getCoercionCaseId())) {
                // coercionCaseId为空，认为该数据是错误数据
                // 因此在直接返回
            }
            // 强制措施表信息保存

            if (null != coercionPerformModel.getId() || !"".equals(coercionPerformModel.getId())) {
                // 获取原数据,甚至流程状态字段
                CoercionPerform performOld = coercionPerformService.getById(coercionPerformModel.getId());
                if (performOld.getEnforceStep() >= 2) {
                    coercionPerformModel.setEnforceStep(performOld.getEnforceStep());
                } else {
                    coercionPerformModel.setEnforceStep(2);
                }
                coercionPerformService.updatePerformPress(coercionPerformModel);
            }

            result.setRtData(1);
        } catch (Exception e) {
            // TODO: handle exception
            result.setRtData(-1);
            System.out.println(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/savePerformApply.action")
    public TeeJson savePerformApply(CoercionPerformModel coercionPerformModel, HttpServletRequest request)
            throws Exception {
        TeeJson result = new TeeJson();
        try {
            if (null == coercionPerformModel.getCoercionCaseId()
                    || "".equals(coercionPerformModel.getCoercionCaseId())) {
                // coercionCaseId为空，认为该数据是错误数据
                // 因此在直接返回
            }
            // 强制措施表信息保存

            if (null != coercionPerformModel.getId() || !"".equals(coercionPerformModel.getId())) {
                // 获取原数据,甚至流程状态字段
                CoercionPerform performOld = coercionPerformService.getById(coercionPerformModel.getId());
                if (performOld.getEnforceStep() >= 3) {
                    coercionPerformModel.setEnforceStep(performOld.getEnforceStep());
                } else {
                    coercionPerformModel.setEnforceStep(3);
                }
                coercionPerformService.updatePerformApply(coercionPerformModel);
                CoercionPerform perform = new CoercionPerform();
                perform.setId(coercionPerformModel.getId());
                List<CoercionCasePower> powerList = buildPowerLists(coercionPerformModel.getCoercionCaseId(), 2, null,
                        perform, coercionPerformModel.getPowerJsonStr());
                List<CoercionCaseGist> gistLsit = buildGistLists(coercionPerformModel.getCoercionCaseId(), 2, null,
                        perform, coercionPerformModel.getGistJsonStr());
                coercionCaseBasicService.saveCasePower(null, coercionPerformModel, powerList);
                coercionCaseBasicService.saveCaseGist(null, coercionPerformModel, gistLsit);
            }

            result.setRtData(1);
        } catch (Exception e) {
            // TODO: handle exception
            result.setRtData(-1);
            System.out.println(e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/savePerformEnforce.action")
    public TeeJson savePerformEnforce(CoercionPerformModel coercionPerformModel, HttpServletRequest request)
            throws Exception {
        TeeJson result = new TeeJson();
        try {
            if (null == coercionPerformModel.getCoercionCaseId()
                    || "".equals(coercionPerformModel.getCoercionCaseId())) {
                // coercionCaseId为空，认为该数据是错误数据
                // 因此在直接返回
            }
            // 强制措施表信息保存

            if (null != coercionPerformModel.getId() || !"".equals(coercionPerformModel.getId())) {
                // 获取原数据,甚至流程状态字段
                CoercionPerform performOld = coercionPerformService.getById(coercionPerformModel.getId());
                if (performOld.getEnforceStep() >= 4) {
                    coercionPerformModel.setEnforceStep(performOld.getEnforceStep());
                } else {
                    coercionPerformModel.setEnforceStep(4);
                }
                coercionPerformService.updatePerformEnforce(coercionPerformModel);
            }

            result.setRtData(1);
        } catch (Exception e) {
            // TODO: handle exception
            result.setRtData(-1);
            System.out.println(e.getMessage());
        }
        return result;
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
     * @Editor: hoax
     * @date: 2019年1月18日 下午3:54:21
     *
     */
    @SuppressWarnings("unchecked")
    public List<CoercionCasePower> buildPowerLists(String caseId, Integer courcionType, CoercionMeasure measure,
            CoercionPerform perform, String powerInfoStr) throws Exception {
        List<CoercionCasePower> powerInfoList = new ArrayList<CoercionCasePower>();
        try {
            // 违法行为json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(powerInfoStr)) {
                JSONArray powerArray = JSONArray.fromObject(powerInfoStr);
                powerInfoList = (List<CoercionCasePower>) JSONArray.toCollection(powerArray, CoercionCasePower.class);
                for (CoercionCasePower powerInfo : powerInfoList) {
                    powerInfo.setId(UUID.randomUUID().toString());
                    powerInfo.setCoercionCaseId(caseId);
                    powerInfo.setCoercionType(courcionType);
                    powerInfo.setCoercionMeasurePower(measure);
                    powerInfo.setCoercionPerformPower(perform);
                }
            }
        } catch (Exception e) {

        }
        return powerInfoList;
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
     * @Editor: hoax
     * @date: 2019年1月18日 下午3:54:21
     *
     */
    @SuppressWarnings("unchecked")
    public List<CoercionCaseGist> buildGistLists(String caseId, Integer courcionType, CoercionMeasure measure,
            CoercionPerform perform, String gistInfoStr) throws Exception {
        List<CoercionCaseGist> gistLists = new ArrayList<CoercionCaseGist>();
        try {
            // 违法依据json串转List<..Model>对象
            if (!TeeUtility.isNullorEmpty(gistInfoStr)) {
                JSONArray gistArray = JSONArray.fromObject(gistInfoStr);
                gistLists = (List<CoercionCaseGist>) JSONArray.toCollection(gistArray, CoercionCaseGist.class);
                for (CoercionCaseGist gistIfo : gistLists) {
                    // 将 gistModel 赋值 gist
                    gistIfo.setId(UUID.randomUUID().toString());
                    gistIfo.setCoercionCaseId(caseId);
                    gistIfo.setCoercionType(courcionType);
                    gistIfo.setCoercionMeasureGist(measure);
                    gistIfo.setCoercionPerformGist(perform);
                    gistIfo.setCreateDate(new Date());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return gistLists;
    }

    @ResponseBody
    @RequestMapping("/saveCourtPerforminfo.action")
    public TeeJson saveCourtPerforminfo(CoercionCourtPerformModel coercionCourtPerformModel, HttpServletRequest request)
            throws Exception {
        TeeJson result = new TeeJson();
        JSONObject jsonObject = new JSONObject();
        try {
            // 是否需要新增主表判断
            if (null == coercionCourtPerformModel.getCoercionCaseId()
                    || "".equals(coercionCourtPerformModel.getCoercionCaseId())) {
                // coercionCaseId为空，认为该数据为新增数据
                // 因此在保存是应先新增主表数据
                CoercionCaseBasic coercionCaseBasic = new CoercionCaseBasic();
                coercionCaseBasic.setId(UUID.randomUUID().toString());
                coercionCaseBasic.setCaseSourceType(coercionCourtPerformModel.getCaseSourceType());
                coercionCaseBasic.setCaseSourceId(coercionCourtPerformModel.getCaseSourceId());
                coercionCaseBasic.setSubjectId(coercionCourtPerformModel.getSubjectId());
                coercionCaseBasic.setDepartmentId(coercionCourtPerformModel.getDepartmentId());
                coercionCaseBasic.setIsDelete(0);
                coercionCaseBasic.setIsSubmit(0);
                coercionCaseBasic.setCreateDate(new Date());
                coercionCaseBasic.setCreateType(2);
                coercionCaseBasicService.save(coercionCaseBasic);
                // 保存后获取主表id
                coercionCourtPerformModel.setCoercionCaseId(coercionCaseBasic.getId());
                jsonObject.put("coercionCaseId", coercionCaseBasic.getId());
            } else {
                jsonObject.put("coercionCaseId", coercionCourtPerformModel.getCoercionCaseId());
            }
            // 强制措施表信息保存
            CoercionCourtPerform coercionCourtPerform = null;
            if (null == coercionCourtPerformModel.getId() || "".equals(coercionCourtPerformModel.getId())) {
                // id为空，认为该数据为新增数据
                coercionCourtPerform = new CoercionCourtPerform();
                coercionCourtPerform.setId(UUID.randomUUID().toString());
                coercionCourtPerform.setCoercionCaseId(coercionCourtPerformModel.getCoercionCaseId());
                coercionCourtPerform.setCreateDate(new Date());
            } else {
                // id不为空，认为该数据为更新数据
                coercionCourtPerform = coercionCourtPerformService.getById(coercionCourtPerformModel.getId());
                coercionCourtPerform.setUpdateDate(new Date());
                coercionCourtPerformService.save(coercionCourtPerform);
                jsonObject.put("id", coercionCourtPerform.getId());
            }
            // 业务数据处理
            // *催告
            coercionCourtPerform.setPunishCodeBefore(coercionCourtPerformModel.getPunishCodeBefore());
            String punishDateStr = coercionCourtPerformModel.getPunishDateBeforeStr();
            if (punishDateStr != null && !"".equals(punishDateStr)) {
                coercionCourtPerform.setPunishDateBefore(TeeDateUtil.format(punishDateStr, "yyyy-MM-dd"));
            } else {
                coercionCourtPerform.setPunishDateBefore(null);
            }
            String pressSendDateStr = coercionCourtPerformModel.getPressSendDateStr();
            if (pressSendDateStr != null && !"".equals(pressSendDateStr)) {
                coercionCourtPerform.setPressSendDate(TeeDateUtil.format(pressSendDateStr, "yyyy-MM-dd"));
            } else {
                coercionCourtPerform.setPressSendDate(null);
            }
            coercionCourtPerform.setPressSendType(coercionCourtPerformModel.getPressSendType());
            coercionCourtPerform.setIsSecondPress(coercionCourtPerformModel.getIsSecondPress());
            String secondPressDateStr = coercionCourtPerformModel.getSecondPressDateStr();
            if (secondPressDateStr != null && !"".equals(secondPressDateStr)) {
                coercionCourtPerform.setSecondPressDate(TeeDateUtil.format(secondPressDateStr, "yyyy-MM-dd"));
            } else {
                coercionCourtPerform.setSecondPressDate(null);
            }
            coercionCourtPerform.setSecondPressType(coercionCourtPerformModel.getSecondPressType());
            // *申请与批准
            String applyDateStr = coercionCourtPerformModel.getApplyDateStr();
            if (applyDateStr != null && !"".equals(applyDateStr)) {
                coercionCourtPerform.setApplyDate(TeeDateUtil.format(applyDateStr, "yyyy-MM-dd"));
            } else {
                coercionCourtPerform.setApplyDate(null);
            }
            String approveDateStr = coercionCourtPerformModel.getApproveDateStr();
            if (approveDateStr != null && !"".equals(approveDateStr)) {
                coercionCourtPerform.setApproveDate(TeeDateUtil.format(approveDateStr, "yyyy-MM-dd"));
            } else {
                coercionCourtPerform.setApproveDate(null);
            }
            coercionCourtPerform.setPerformType(coercionCourtPerformModel.getPerformType());
            coercionCourtPerform.setEnforceElementCondition(coercionCourtPerformModel.getEnforceElementCondition());
            // 执行保存
            coercionCourtPerformService.save(coercionCourtPerform);
            result.setRtState(true);
            jsonObject.put("id", coercionCourtPerform.getId());
            jsonObject.put("flag", 1);
            result.setRtData(jsonObject);
        } catch (Exception e) {
            // TODO: handle exception
            result.setRtData(-1);
            System.out.println(e.getMessage());
        }

        return result;
    }

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
            Long total = coercionCaseBasicService.listCount(coercionCaseBasicModel);

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
}
