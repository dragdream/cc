package com.beidasoft.xzzf.punish.document.controller;

import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocFinalReport;
import com.beidasoft.xzzf.punish.document.model.FinalReportModel;
import com.beidasoft.xzzf.punish.document.service.FinalReportService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;


@Controller
@RequestMapping("/finalReportCtrl")
public class FinalReportController {
	@Autowired
	private FinalReportService finalReportService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存结案报告信息
	 * @param finalReportModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(FinalReportModel finalReportModel, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		// 实例化实体类对象
		DocFinalReport docFinalReport = new DocFinalReport();
		
		// 属性值传递
		BeanUtils.copyProperties(finalReportModel, docFinalReport);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(finalReportModel.getFileTimeStr())) {
			docFinalReport.setFileTime(TeeDateUtil.format(finalReportModel.getFileTimeStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(finalReportModel.getPublishDateStr())) {
			docFinalReport.setPublishDate(TeeDateUtil.format(finalReportModel.getPublishDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(finalReportModel.getMinorPersonDateStr())) {
			docFinalReport.setMinorPersonDate(TeeDateUtil.format(finalReportModel.getMinorPersonDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(finalReportModel.getLawUnitDateStr())) {
			docFinalReport.setLawUnitDate(TeeDateUtil.format(finalReportModel.getLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(finalReportModel.getMajorEmpLeaderDateStr())) {
			docFinalReport.setMajorEmpLeaderDate(TeeDateUtil.format(finalReportModel.getMajorEmpLeaderDateStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(finalReportModel.getMajorLeaderDateStr())) {
			docFinalReport.setMajorLeaderDate(TeeDateUtil.format(finalReportModel.getMajorLeaderDateStr(),
					"yyyy年MM月dd日"));
		}
		
		// 设置创建人相关信息
		if (StringUtils.isBlank(finalReportModel.getId())) {
			docFinalReport.setId(UUID.randomUUID().toString());
			docFinalReport.setCreateUserName(user.getUserName());
			docFinalReport.setCreateUserId(user.getUuid()+"");
			docFinalReport.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建结案报告");
			docFinalReport.setDelFlg("0");
		} else {
			//设置修改人相关信息
			docFinalReport.setUpdateUserId(user.getUserId());
			docFinalReport.setUpdateUserName(user.getUserName());
			docFinalReport.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改结案报告");
		}
		// 保存 结案报告记录表
		finalReportService.saveDocInfo(docFinalReport , request);
		
		json.setRtData(docFinalReport);
		json.setRtState(true);

		return json;
	}
	/**
	 * 查询结案报告信息（根据ID）
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocFinalReport docFinalReport = finalReportService.getDocInfo(id);
		FinalReportModel finalReportModel = new FinalReportModel();
		BeanUtils.copyProperties(docFinalReport, finalReportModel);
		// 单独处理时间类型转换
		if (docFinalReport.getFileTime() != null) {
			finalReportModel.setFileTimeStr(TeeDateUtil.format(docFinalReport.getFileTime(),
					"yyyy年MM月dd日"));
		}
		if (docFinalReport.getPublishDate() != null) {
			finalReportModel.setPublishDateStr(TeeDateUtil.format(docFinalReport.getPublishDate(),
					"yyyy年MM月dd日"));
		}
		if (docFinalReport.getMinorPersonDate() != null) {
			finalReportModel.setMinorPersonDateStr(TeeDateUtil.format(docFinalReport.getMinorPersonDate(),
					"yyyy年MM月dd日"));
		}
		if (docFinalReport.getMajorEmpLeaderDate() != null) {
			finalReportModel.setMajorEmpLeaderDateStr(TeeDateUtil.format(docFinalReport.getMajorEmpLeaderDate(),
					"yyyy年MM月dd日"));
		}
		if (docFinalReport.getLawUnitDate() != null) {
			finalReportModel.setLawUnitDateStr(TeeDateUtil.format(docFinalReport.getLawUnitDate(),
					"yyyy年MM月dd日"));
		}
		if (docFinalReport.getMajorLeaderDate() != null) {
			finalReportModel.setMajorLeaderDateStr(TeeDateUtil.format(docFinalReport.getMajorLeaderDate(),
					"yyyy年MM月dd日"));
		}
		// 返回 结案报告记录表 json 对象
		json.setRtData(finalReportModel);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 根据baseId和文书id查询行政处罚决定书预览地址
	 * @param baseId
	 * @param securityAdminId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSecurityAdminPath")
	public TeeJson getSecurityAdminPath(String baseId, String securityAdminId, HttpServletRequest request){
		return finalReportService.getSecurityAdminPath(baseId, securityAdminId);
	}
}
