package com.beidasoft.xzzf.punish.document.controller;

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
import com.beidasoft.xzzf.punish.document.bean.DocPublishNotice;
import com.beidasoft.xzzf.punish.document.model.PublishNoticeModel;
import com.beidasoft.xzzf.punish.document.service.PublishNoticeService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
@Controller
@RequestMapping("/publishNoticeCtrl")
public class PublishNoticeController {

	@Autowired
	private PublishNoticeService publishNoticeServicep;
	@Autowired
	private CommonService commonService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private TeeWenShuService wenShuService;

	/**
	 * 行政处罚听证通知书
	 * @param publishNoticeModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("saveDocInfo")
	public TeeJson saveDocInfo(PublishNoticeModel publishNoticeModel,HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		
		TeePerson user = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

		// 实例化实体类对象
		DocPublishNotice docPublishNotice = new DocPublishNotice();

		// 属性值传递
		BeanUtils.copyProperties(publishNoticeModel, docPublishNotice);

		// 单独处理时间类型转换
		if (!StringUtils.isBlank(publishNoticeModel.getDocDateStr())) {
			docPublishNotice.setDocDate(TeeDateUtil.format(publishNoticeModel.getDocDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (!StringUtils.isBlank(publishNoticeModel.getLawUnitDateStr())) {
			docPublishNotice.setLawUnitDate(TeeDateUtil.format(publishNoticeModel.getLawUnitDateStr(),
					"yyyy年MM月dd日"));
		}
		if (!StringUtils.isBlank(publishNoticeModel.getDeliverDateStr())) {
			docPublishNotice.setDeliverDate(TeeDateUtil.format(publishNoticeModel.getDeliverDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (!StringUtils.isBlank(publishNoticeModel.getReceiverDateStr())) {
			docPublishNotice.setReceiverDate(TeeDateUtil.format(publishNoticeModel.getReceiverDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}

		// 设置创建人相关信息
		if (StringUtils.isBlank(publishNoticeModel.getId())) {
			docPublishNotice.setId(UUID.randomUUID().toString());
			docPublishNotice.setCreateUserId(user.getUserId());
			docPublishNotice.setCreateUserName(user.getUserName());
			docPublishNotice.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建行政处罚听证通知书");
		} else {
			//设置更新人相关信息
			docPublishNotice.setUpdateUserId(user.getUserId());
			docPublishNotice.setUpdateUserName(user.getUserName());
			docPublishNotice.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改行政处罚听证通知书");
		}
		
		// 行政处罚听证通知书
		publishNoticeServicep.saveDocInfo(docPublishNotice , request);

		json.setRtData(docPublishNotice);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 行政处罚听证通知书（通过主键ID）
	 * @param id
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {

		TeeJson json = new TeeJson();
		
		DocPublishNotice docPublishNotice = publishNoticeServicep.getById(id);
		PublishNoticeModel publishNoticeModel = new PublishNoticeModel();

		BeanUtils.copyProperties(docPublishNotice, publishNoticeModel);

		// 单独处理时间类型转换
		if (docPublishNotice.getDocDate() != null) {
			publishNoticeModel.setDocDateStr(TeeDateUtil.format(docPublishNotice.getDocDate(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (docPublishNotice.getLawUnitDate() != null) {
			publishNoticeModel.setLawUnitDateStr(TeeDateUtil.format(docPublishNotice.getLawUnitDate(),
					"yyyy年MM月dd日"));
		}
		if (docPublishNotice.getDeliverDate() != null) {
			publishNoticeModel.setDeliverDateStr(TeeDateUtil.format(docPublishNotice.getDeliverDate(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (docPublishNotice.getReceiverDate() != null) {
			publishNoticeModel.setReceiverDateStr(TeeDateUtil.format(docPublishNotice.getReceiverDate(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		// 返回 行政处罚听证通知书 json 对象
		json.setRtData(publishNoticeModel);
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
	public TeeJson viewDocInfo(PublishNoticeModel publishNoticeModel, HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = publishNoticeModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	
	}
	/**
	 * 行政处罚听证通知书取行政处罚事先告知书的信息
	 * @param baseId
	 * @param lawLinkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromAdmin")
	public TeeJson getDocFromAdmin(String baseId) {
		TeeJson json = new TeeJson();
		List<PunishFLow> list = flowService.getFlowcase(baseId, "行政处罚事先告知书", "");//通过baseId 和 流程名  查流程 信息
		PunishFLow fir = list.get(0);
		String lawLinkId = fir.getTacheId();
		List<DocAdministrativePenality> adminlist = (List<DocAdministrativePenality>)commonService.getDocByBaseId("DocAdministrativePenality",baseId,lawLinkId);
		json.setRtData(adminlist);
		return json;
	}
	
}
