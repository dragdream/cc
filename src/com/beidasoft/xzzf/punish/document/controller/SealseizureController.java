package com.beidasoft.xzzf.punish.document.controller;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocSealseizure;
import com.beidasoft.xzzf.punish.document.model.SealseizureModel;
import com.beidasoft.xzzf.punish.document.service.SealseizureService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/sealseizureCtrl")
public class SealseizureController {

	@Autowired
	private SealseizureService sealseizureService;

	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;

	/**
	 * 保存查封扣押决定书
	 * 
	 * @param SealseizureModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(SealseizureModel sealseizureModel,
			HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocSealseizure sealseizure = new DocSealseizure();
		// 属性值传递
		BeanUtils.copyProperties(sealseizureModel, sealseizure);
		
//		// RUNID
//		String runId = request.getParameter("runId");
		
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(sealseizureModel
				.getProcessDecisionDateStartStr())) {
			sealseizure.setProcessDecisionDateStart(TeeDateUtil.format(
					sealseizureModel.getProcessDecisionDateStartStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(sealseizureModel
				.getProcessDecisionDateEndStr())) {
			sealseizure.setProcessDecisionDateEnd(TeeDateUtil.format(
					sealseizureModel.getProcessDecisionDateEndStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(sealseizureModel.getAdministrationDateStr())) {
			sealseizure
					.setAdministrationDate(TeeDateUtil.format(
							sealseizureModel.getAdministrationDateStr(),
							"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(sealseizureModel.getDeliveryTimeStr())) {
			sealseizure
					.setDeliveryTime(TeeDateUtil.format(
							sealseizureModel.getDeliveryTimeStr(),
							"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(sealseizureModel.getReceiverDateStr())) {
			sealseizure
					.setReceiverDate(TeeDateUtil.format(
							sealseizureModel.getReceiverDateStr(),
							"yyyy年MM月dd日 HH时mm分"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(sealseizureModel.getId())) {
			sealseizure.setId(UUID.randomUUID().toString());
			sealseizure.setCreateUserId(user.getUserId());
			sealseizure.setCreateUserName(user.getUserName());
			sealseizure.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建查封扣押决定书");
			// 防止出现空
			sealseizure.setDelFlg("0");
		} else {
			// 设置创建人相关信息
			sealseizure.setUpdateUserId(user.getUserId());
			sealseizure.setUpdateUserName(user.getUserName());
			sealseizure.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改查封扣押决定书");
		}
		
		// 保存 查封扣押决定书
		sealseizureService.save(sealseizure,request);
		json.setRtData(sealseizure);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取查封扣押决定书
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {

		TeeJson json = new TeeJson();
		DocSealseizure sealseizure = sealseizureService.getById(id);
		SealseizureModel sealseizureModel = new SealseizureModel();
		BeanUtils.copyProperties(sealseizure, sealseizureModel);

		// 单独处理时间类型转换

		if (sealseizure.getProcessDecisionDateStart() != null) {
			sealseizureModel.setProcessDecisionDateStartStr(TeeDateUtil.format(
					sealseizure.getProcessDecisionDateStart(), "yyyy年MM月dd日"));
		}
		if (sealseizure.getProcessDecisionDateEnd() != null) {
			sealseizureModel.setProcessDecisionDateEndStr(TeeDateUtil.format(
					sealseizure.getProcessDecisionDateEnd(), "yyyy年MM月dd日"));
		}
		if (sealseizure.getAdministrationDate() != null) {
			sealseizureModel.setAdministrationDateStr(TeeDateUtil.format(
					sealseizure.getAdministrationDate(), "yyyy年MM月dd日"));
		}
		if (sealseizure.getDeliveryTime() != null) {
			sealseizureModel.setDeliveryTimeStr(TeeDateUtil.format(
					sealseizure.getDeliveryTime(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (sealseizure.getReceiverDate() != null) {
			sealseizureModel.setReceiverDateStr(TeeDateUtil.format(
					sealseizure.getReceiverDate(), "yyyy年MM月dd日 HH时mm分"));
		}

		// 返回 查封扣押决定书 json 对象
		json.setRtData(sealseizureModel);
		json.setRtState(true);
		return json;
	}

	/**
	 * 预览文书
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/viewDocInfo")
	@ResponseBody
	public TeeJson viewDocInfo(SealseizureModel sealseizureModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = sealseizureModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getById")
	public TeeJson getById(String id) {
		TeeJson json = new TeeJson();
		DocSealseizure sealseizure = sealseizureService.getById(id);
		json.setRtData(sealseizure);
		json.setRtState(true);
		return json;
	}
}
