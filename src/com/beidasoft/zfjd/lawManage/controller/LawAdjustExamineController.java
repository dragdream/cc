package com.beidasoft.zfjd.lawManage.controller;

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

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.law.bean.TblLawInfo;
import com.beidasoft.zfjd.law.service.TblLawService;
import com.beidasoft.zfjd.lawManage.bean.LawAdjustReport;
import com.beidasoft.zfjd.lawManage.model.LawAdjustReportModel;
import com.beidasoft.zfjd.lawManage.service.LawAdjustExamineService;
import com.beidasoft.zfjd.lawManage.service.LawAdjustReportService;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("lawAdjustExamineCtrl")
public class LawAdjustExamineController {

	@Autowired
	private LawAdjustReportService lawAdjustReportService;
	@Autowired
	private LawAdjustExamineService lawAdjustExamineService;
	
	@Autowired
	private CommonService commonservice;
	
	@Autowired
	private TblLawService tblLawService;
	
	@Autowired
	private TeeAttachmentService teeAttachmentService;
	
	/**
	 * 分页获取已提交的数据
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
    @RequestMapping("examineListByPage")
    public TeeEasyuiDataGridJson examineListByPage(TeeDataGridModel dataGridModel,LawAdjustReportModel lawAdjustReportModel,
    		HttpServletRequest request){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//获取用户信息数据控制
		OrgCtrlInfoModel orgCtrlInfoModel = commonservice.getOrgCtrlInfo(request);
		List<LawAdjustReportModel> lawModelList = new ArrayList<LawAdjustReportModel>();
		List<LawAdjustReport> lawInfos = lawAdjustExamineService.examineListByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),
				lawAdjustReportModel, orgCtrlInfoModel);
		long total = lawAdjustExamineService.getExamineTotal(lawAdjustReportModel, orgCtrlInfoModel);
		
		for (LawAdjustReport lawInfo : lawInfos) {
			LawAdjustReportModel infoModel = new LawAdjustReportModel();
			BeanUtils.copyProperties(lawInfo, infoModel);
			if (lawInfo.getSubmitDate() != null) {
				infoModel.setSubmitDateStr(TeeDateUtil.format(lawInfo.getSubmitDate(), "yyyy-MM-dd"));
            } else {
            	infoModel.setSubmitDateStr("");
            }
//			//infoModel.setExamineStr(Integer.toString(userInfo.getExamine()));
			lawModelList.add(infoModel);
//
		}

		dataGridJson.setTotal(total);
		dataGridJson.setRows(lawModelList);
		
		return dataGridJson;

	}
	
	/**
	 * 打开法律法规调整申请页面
	 * @param lawModel
	 * @return
	 */
    @RequestMapping("openLawExamineInput")
    public void openLawExamineInput(LawAdjustReportModel lawAdjustReportModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
        	String dataId = lawAdjustReportModel.getId();
        	LawAdjustReportModel lawAdjsutModel = new LawAdjustReportModel();
            if (dataId !=null && !"".equals(dataId)) {
                // 案件来源为其他来源，则获取强制案件基础表id
                LawAdjustReport lawAdjust = lawAdjustReportService.getById(dataId);
                BeanUtils.copyProperties(lawAdjust, lawAdjsutModel);
                if(lawAdjsutModel.getUpdateLawId() != null){
                	TblLawInfo historyLaw = tblLawService.getById(lawAdjsutModel.getUpdateLawId());
                	lawAdjsutModel.setUpdateLawName(historyLaw.getName());
                }
                //法律法规类别
                List<Map<String, Object>> controlTypeList = TeeSysCodeManager
                        .getChildSysCodeListByParentCodeNo("LAW_CONTROL_TYPE");
                if(controlTypeList != null){
                	for (Map<String, Object> code : controlTypeList) {
                		if (lawAdjsutModel.getControlType().equals(code.get("codeNo").toString())) {
                			lawAdjsutModel.setControlTypeStr(code.get("codeName").toString());
                			break;
                		}
                	}
                }
                //法律法规类别
                List<Map<String, Object>> lawTypeList = TeeSysCodeManager
                        .getChildSysCodeListByParentCodeNo("LAW_TYPE");
                for (Map<String, Object> code : lawTypeList) {
                    if (lawAdjsutModel.getSubmitlawLevel().equals(code.get("codeNo").toString())) {
                    	lawAdjsutModel.setSubmitlawLevelStr(code.get("codeName").toString());
                        break;
                    }
                }
                //时效性
                List<Map<String, Object>> lawTimelinessList = TeeSysCodeManager
                        .getChildSysCodeListByParentCodeNo("LAW_TIMELINESS");
                for (Map<String, Object> code : lawTimelinessList) {
                    if (lawAdjsutModel.getTimeliness().equals(code.get("codeNo").toString())) {
                    	lawAdjsutModel.setTimelinessStr(code.get("codeName").toString());
                        break;
                    }
                }
                //
                if(lawAdjust.getImplementation() != null){
                	lawAdjsutModel.setImplementationStr(TeeDateUtil.format(lawAdjust.getImplementation(), "yyyy-MM-dd"));
                }
                
                //
                if(lawAdjust.getPromulgation() != null){
                	lawAdjsutModel.setPromulgationStr(TeeDateUtil.format(lawAdjust.getPromulgation(), "yyyy-MM-dd"));
                }
            }
            request.setAttribute("lawAdjust", lawAdjsutModel);
            request.getRequestDispatcher("/supervise/lawManage/lawExamine/lawExamine_input.jsp").forward(request,
                    response);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    
	/**
	 * 保存法律法规调整信息
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("examinePass")
	public TeeJson examinePass(LawAdjustReportModel lawAdjustReportModel){
		TeeJson json = new TeeJson();
		LawAdjustReport lawReportInfo = lawAdjustReportService.getById(lawAdjustReportModel.getId());
		if("03".equals(lawReportInfo .getControlType())){
			lawAdjustReportModel.setExamine(9);
		}else{
			lawAdjustReportModel.setExamine(3);
		}
		//更新审核标记
		lawAdjustExamineService.updateExamineById(lawAdjustReportModel);
		//根据调整类型数据真实入库
		lawAdjustExamineService.updateRealDataByCtrlType(lawAdjustReportModel);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 保存法律法规调整信息
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("examineNotPass")
	public TeeJson examineNotPass(LawAdjustReportModel lawAdjustReportModel){
		TeeJson json = new TeeJson();
		lawAdjustReportModel.setExamine(1);
		lawAdjustExamineService.updateExamineById(lawAdjustReportModel);
		json.setRtState(true);
		return json;
	}
}
