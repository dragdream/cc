package com.beidasoft.xzzf.punish.document.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocAdministrativePenality;
import com.beidasoft.xzzf.punish.document.bean.DocNotificationcorrection;
import com.beidasoft.xzzf.punish.document.model.AdministrativePenalityModel;
import com.beidasoft.xzzf.punish.document.model.NotificationcorrectionModel;
import com.beidasoft.xzzf.punish.document.service.AdministrativePenalityService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/administrativePenalityCtrl")
public class AdministrativePenalityController {
	@Autowired
	private AdministrativePenalityService adminService;
	@Autowired
	private TeeWenShuService wenShuService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private PunishFlowService flowService;
	/*
	 * 行政处罚事先告知书
	 */
	/**
	 * @param administrativePenalityModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(
			AdministrativePenalityModel administrativePenalityModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocAdministrativePenality administrativePenality = new DocAdministrativePenality();
		// 属性值传递
		BeanUtils.copyProperties(administrativePenalityModel,
				administrativePenality);
		// 单独处理时间类型转换
		// 违法时间
		if (StringUtils.isNotBlank(administrativePenalityModel
				.getCauseTimeStr())) {
			administrativePenality.setCauseTime(TeeDateUtil.format(
					administrativePenalityModel.getCauseTimeStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(administrativePenalityModel
				.getCauseTimeEndStr())) {
			administrativePenality.setCauseTimeEnd(TeeDateUtil.format(
					administrativePenalityModel.getCauseTimeEndStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		// 行政机关落款印章时间
		if (StringUtils.isNotBlank(administrativePenalityModel
				.getLawUnitDateStr())) {
			administrativePenality.setLawUnitDate(TeeDateUtil.format(
					administrativePenalityModel.getLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		// 当事人签名或盖章时间
		if (StringUtils.isNotBlank(administrativePenalityModel
				.getSiteLeaderDateStr())) {
			administrativePenality.setSiteLeaderDate(TeeDateUtil.format(
					administrativePenalityModel.getSiteLeaderDateStr(),
					"yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(administrativePenalityModel.getId())) {
			administrativePenality.setId(UUID.randomUUID().toString());
			administrativePenality.setCreateUserId(user.getUserId());
			administrativePenality.setCreateUserName(user.getUserName());
			administrativePenality.setCreateTime(Calendar.getInstance()
					.getTime());
			administrativePenality.setDelFlg("0");
			//添加文书操作日志
			commonService.writeLog(request, "新建行政处罚事先告知书");
		} else {
			// 设置创建人相关信息
			administrativePenality.setUpdateUserId(user.getUserId());
			administrativePenality.setUpdateUserName(user.getUserName());
			administrativePenality.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改行政处罚事先告知书");
		}
		
		// 保存 行政处罚事先告知书
		adminService.save(administrativePenality , request);
		json.setRtData(administrativePenality);
		json.setRtState(true);
		return json;
	}

	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocAdministrativePenality administrativePenality = adminService
				.getById(id);
		AdministrativePenalityModel administrativePenalityModel = new AdministrativePenalityModel();
		BeanUtils.copyProperties(administrativePenality,
				administrativePenalityModel);
		// 单独处理时间类型转换
		if (administrativePenality.getCauseTime() != null) {
			administrativePenalityModel.setCauseTimeStr(TeeDateUtil.format(
					administrativePenality.getCauseTime(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (administrativePenality.getCauseTimeEnd() != null) {
			administrativePenalityModel.setCauseTimeEndStr(TeeDateUtil.format(
					administrativePenality.getCauseTimeEnd(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (administrativePenality.getLawUnitDate() != null) {
			administrativePenalityModel.setLawUnitDateStr(TeeDateUtil.format(
					administrativePenality.getLawUnitDate(), "yyyy年MM月dd日"));
		}
		if (administrativePenality.getSiteLeaderDate() != null) {
			administrativePenalityModel.setSiteLeaderDateStr(TeeDateUtil
					.format(administrativePenality.getSiteLeaderDate(),
							"yyyy年MM月dd日"));
		}
		// 返回 行政处罚事先告知书 json 对象
		json.setRtData(administrativePenalityModel);
		json.setRtState(true);
		return json;
	}

	/**
	 * @param administrativePenalityModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(
			AdministrativePenalityModel administrativePenalityModel,
			HttpServletRequest request) {
		// 实例化实体类对象
		DocAdministrativePenality administrativePenality = new DocAdministrativePenality();
		TeeJson json = new TeeJson();
		// 创建人相关信息
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 属性值传递
		BeanUtils.copyProperties(administrativePenalityModel,
				administrativePenality);
		// 单独处理时间类型转换
		// 违法时间
		if (StringUtils.isNotBlank(administrativePenalityModel
				.getCauseTimeStr())) {
			administrativePenality.setCauseTime(TeeDateUtil.format(
					administrativePenalityModel.getCauseTimeStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(administrativePenalityModel
				.getCauseTimeEndStr())) {
			administrativePenality.setCauseTimeEnd(TeeDateUtil.format(
					administrativePenalityModel.getCauseTimeEndStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		// 行政机关落款印章时间
		if (StringUtils.isNotBlank(administrativePenalityModel
				.getLawUnitDateStr())) {
			administrativePenality.setLawUnitDate(TeeDateUtil.format(
					administrativePenalityModel.getLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		// 当事人签名或盖章时间
		if (StringUtils.isNotBlank(administrativePenalityModel
				.getSiteLeaderDateStr())) {
			administrativePenality.setSiteLeaderDate(TeeDateUtil.format(
					administrativePenalityModel.getSiteLeaderDateStr(),
					"yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		administrativePenality.setUpdateUserId(user.getUserId());
		administrativePenality.setUpdateUserName(user.getUserName());
		administrativePenality.setUpdateTime(Calendar.getInstance().getTime());
		// 保存 抽样取证凭证表
		adminService.update(administrativePenality);
		json.setRtData(administrativePenality);
		json.setRtState(true);
		return json;
	}

	/**
	 * 预览文书
	 * 
	 * @param samplingevidencesModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(
			AdministrativePenalityModel administrativePenalityModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = administrativePenalityModel
				.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}

	/**
	 * 行政处罚事先告知书取责令改正的信息
	 * @param baseId
	 * @param lawLinkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromNoti")
	public TeeJson getDocFromNoti(String baseId) {
		TeeJson json = new TeeJson();
		List<PunishFLow> list = flowService.getFlowcase(baseId, "责令改正", "");//通过baseId 和 流程名  查流程 信息（立案审批一般只有一个）
		PunishFLow fir = list.get(0);
		String lawLinkId = fir.getTacheId();
		NotificationcorrectionModel model = new NotificationcorrectionModel();
		List<DocNotificationcorrection> notilist=  (List<DocNotificationcorrection>)commonService.getDocByBaseId("DocNotificationcorrection",baseId,lawLinkId);
		List<NotificationcorrectionModel> modelList = new ArrayList<NotificationcorrectionModel>();
		for (DocNotificationcorrection docNotificationcorrection : notilist) {
			BeanUtils.copyProperties(docNotificationcorrection, model);
			model.setCaseTimeStr(TeeDateUtil.format(docNotificationcorrection.getCaseTime(),"yyyy年MM月dd日HH时mm分"));
			model.setCaseTimeEndStr(TeeDateUtil.format(docNotificationcorrection.getCaseTimeEnd(),"yyyy年MM月dd日HH时mm分"));
			modelList.add(model);
		}
		json.setRtData(modelList);
		return json;
	}
}
