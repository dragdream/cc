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
import com.beidasoft.xzzf.punish.document.bean.DocInspectionRecord;
import com.beidasoft.xzzf.punish.document.bean.DocNotificationcorrection;
import com.beidasoft.xzzf.punish.document.model.InspectionRecordModel;
import com.beidasoft.xzzf.punish.document.model.NotificationcorrectionModel;
import com.beidasoft.xzzf.punish.document.service.NotificationcorrectionService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/notificationcorrectionCtrl")
public class NotificationcorrectionController {
	@Autowired
	private NotificationcorrectionService notificationcorrectionService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private TeeWenShuService wenShuService;
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(
			NotificationcorrectionModel notificationcorrectionModel,
			HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocNotificationcorrection notificationcorrection = new DocNotificationcorrection();
		// 属性值传递
		BeanUtils.copyProperties(notificationcorrectionModel,
				notificationcorrection);
		// 单独处理时间类型转换
		if (StringUtils
				.isNotBlank(notificationcorrectionModel.getCaseTimeStr())) {
			notificationcorrection
					.setCaseTime(TeeDateUtil.format(
							notificationcorrectionModel.getCaseTimeStr(),
							"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(notificationcorrectionModel
				.getCaseTimeEndStr())) {
			notificationcorrection.setCaseTimeEnd(TeeDateUtil.format(
					notificationcorrectionModel.getCaseTimeEndStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(notificationcorrectionModel.getBeforeStr())) {
			notificationcorrection.setBefore(TeeDateUtil.format(
					notificationcorrectionModel.getBeforeStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(notificationcorrectionModel
				.getDeliverDateStr())) {
			notificationcorrection.setDeliverDate(TeeDateUtil.format(
					notificationcorrectionModel.getDeliverDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils
				.isNotBlank(notificationcorrectionModel.getSignTimeStr())) {
			notificationcorrection.setSignTime(TeeDateUtil.format(
					notificationcorrectionModel.getSignTimeStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils
				.isNotBlank(notificationcorrectionModel.getSealTimeStr())) {
			notificationcorrection
					.setSealTime(TeeDateUtil.format(
							notificationcorrectionModel.getSealTimeStr(),
							"yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(notificationcorrectionModel.getId())) {
			notificationcorrection.setId(UUID.randomUUID().toString());
			notificationcorrection.setCreateUserId(user.getUserId());
			notificationcorrection.setCreateUserName(user.getUserName());
			notificationcorrection.setCreateTime(Calendar.getInstance()
					.getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建责令改正通知书");
			// 防止出现空
			notificationcorrection.setDelFlg("0");
		} else {
			// 设置创建人相关信息
			notificationcorrection.setUpdateUserId(user.getUserId());
			notificationcorrection.setUpdateUserName(user.getUserName());
			notificationcorrection.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改责令改正通知书");
		}
		
		// 保存 责令改正通知书
		notificationcorrectionService.save(notificationcorrection , request);
		json.setRtData(notificationcorrection);
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
		DocNotificationcorrection notificationcorrection = notificationcorrectionService
				.getById(id);
		NotificationcorrectionModel notificationcorrectionModel = new NotificationcorrectionModel();

		BeanUtils.copyProperties(notificationcorrection,
				notificationcorrectionModel);
		// 单独处理时间类型转换
		if (notificationcorrection.getCaseTime() != null) {
			notificationcorrectionModel.setCaseTimeStr(TeeDateUtil.format(
					notificationcorrection.getCaseTime(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (notificationcorrection.getCaseTimeEnd() != null) {
			notificationcorrectionModel.setCaseTimeEndStr(TeeDateUtil.format(
					notificationcorrection.getCaseTimeEnd(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (notificationcorrection.getBefore() != null) {
			notificationcorrectionModel.setBeforeStr(TeeDateUtil.format(
					notificationcorrection.getBefore(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (notificationcorrection.getDeliverDate() != null) {
			notificationcorrectionModel.setDeliverDateStr(TeeDateUtil.format(
					notificationcorrection.getDeliverDate(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (notificationcorrection.getSignTime() != null) {
			notificationcorrectionModel.setSignTimeStr(TeeDateUtil.format(
					notificationcorrection.getSignTime(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (notificationcorrection.getSealTime() != null) {
			notificationcorrectionModel.setSealTimeStr(TeeDateUtil.format(
					notificationcorrection.getSealTime(), "yyyy年MM月dd日"));
		}
		// 返回 责令改正通知书 json 对象
		json.setRtData(notificationcorrectionModel);
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
	public TeeJson viewDocInfo(
			NotificationcorrectionModel notificationcorrectionModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = notificationcorrectionModel
				.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	/**
	 * 行政处罚决定书（当场处罚）取现场勘验笔录的信息
	 * @param baseId
	 * @param lawLinkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromInsep")
	public TeeJson getDocFromInsep(String baseId) {
		TeeJson json = new TeeJson();
		List<PunishFLow> list = flowService.getFlowcase(baseId, "现场检查", "");
		PunishFLow fir = list.get(0);
		String lawLinkId = fir.getTacheId();
		InspectionRecordModel model = new InspectionRecordModel();
		List<DocInspectionRecord> Inseplist=  (List<DocInspectionRecord>)commonService.getDocByBaseId("DocInspectionRecord",baseId,lawLinkId);
		List<InspectionRecordModel> modelList = new ArrayList<InspectionRecordModel>();
		for (DocInspectionRecord docInspectionRecord : Inseplist) {
			BeanUtils.copyProperties(docInspectionRecord, model);
			model.setInspectionTimeStartStr(TeeDateUtil.format(docInspectionRecord.getInspectionTimeStart(),"yyyy年MM月dd日HH时mm分"));
			model.setInspectionTimeEndStr(TeeDateUtil.format(docInspectionRecord.getInspectionTimeEnd(),"yyyy年MM月dd日HH时mm分"));
			modelList.add(model);
		}
		json.setRtData(modelList);
		return json;
	}
}
