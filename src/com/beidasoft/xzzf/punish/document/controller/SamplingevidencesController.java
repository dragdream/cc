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
import com.beidasoft.xzzf.punish.document.bean.DocPutonrecord;
import com.beidasoft.xzzf.punish.document.bean.DocSamplingevidences;
import com.beidasoft.xzzf.punish.document.model.PutonrecordModel;
import com.beidasoft.xzzf.punish.document.model.SamplingevidencesModel;
import com.beidasoft.xzzf.punish.document.service.SamplingevidencesService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/samplingevidencesCtrl")
public class SamplingevidencesController {

	@Autowired
	private SamplingevidencesService samplingevidencesService;
	@Autowired
	private TeeWenShuService wenShuService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private PunishFlowService flowService;

	/**
	 * 保存抽样取证凭证表
	 * 
	 * @param samplingevidencesModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(SamplingevidencesModel samplingevidencesModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocSamplingevidences samplingevidences = new DocSamplingevidences();
//		else {
//			samplingevidences = samplingevidencesService.getById(samplingevidencesModel.getId());
//		}
		// 属性值传递
		BeanUtils.copyProperties(samplingevidencesModel, samplingevidences);
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(samplingevidencesModel.getStampDateStr())) {
			samplingevidences.setStampDate(TeeDateUtil.format(
					samplingevidencesModel.getStampDateStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(samplingevidencesModel.getSendDateStr())) {
			samplingevidences.setSendDate(TeeDateUtil.format(
					samplingevidencesModel.getSendDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(samplingevidencesModel.getReceiptDateStr())) {
			samplingevidences.setReceiptDate(TeeDateUtil.format(
					samplingevidencesModel.getReceiptDateStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(samplingevidencesModel.getId())) {
			samplingevidences.setId(UUID.randomUUID().toString());
			samplingevidences.setCreateUserId(user.getUserId());
			samplingevidences.setCreateUserName(user.getUserName());
			samplingevidences.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建抽样取证凭证");
		} else {
			// 设置创建人相关信息
			samplingevidences.setUpdateUserId(user.getUserId());
			samplingevidences.setUpdateUserName(user.getUserName());
			samplingevidences.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改抽样取证凭证");
		}
		
		samplingevidences.setDelFlg("0");
		// 保存 抽样取证凭证表
		samplingevidencesService.save(samplingevidences,request);
		json.setRtData(samplingevidences);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取 抽样取证凭证（通过主键ID）
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocSamplingevidences samplingevidences = samplingevidencesService
				.getById(id);
		SamplingevidencesModel samplingevidencesModel = new SamplingevidencesModel();
		BeanUtils.copyProperties(samplingevidences, samplingevidencesModel);
		// 单独处理时间类型转换
		if (samplingevidences.getStampDate() != null) {
			samplingevidencesModel.setStampDateStr(TeeDateUtil.format(
					samplingevidences.getStampDate(), "yyyy年MM月dd日"));
		}
		if (samplingevidences.getSendDate() != null) {
			samplingevidencesModel.setSendDateStr(TeeDateUtil.format(
					samplingevidences.getSendDate(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (samplingevidences.getReceiptDate() != null) {
			samplingevidencesModel.setReceiptDateStr(TeeDateUtil.format(
					samplingevidences.getReceiptDate(), "yyyy年MM月dd日 HH时mm分"));
		}
		// 返回 抽样取证凭证表 json 对象
		json.setRtData(samplingevidencesModel);
		json.setRtState(true);
		return json;
	}

	/**
	 * 更新抽样取证凭证表
	 * 
	 * @param samplingevidencesModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(SamplingevidencesModel samplingevidencesModel,
			HttpServletRequest request) {
		// 实例化实体类对象
		DocSamplingevidences samplingevidences = new DocSamplingevidences();
		TeeJson json = new TeeJson();
		// 创建人相关信息
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 属性值传递
		BeanUtils.copyProperties(samplingevidencesModel, samplingevidences);
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(samplingevidencesModel.getStampDateStr())) {
			samplingevidences.setStampDate(TeeDateUtil.format(
					samplingevidencesModel.getStampDateStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(samplingevidencesModel.getSendDateStr())) {
			samplingevidences.setSendDate(TeeDateUtil.format(
					samplingevidencesModel.getSendDateStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(samplingevidencesModel.getReceiptDateStr())) {
			samplingevidences.setReceiptDate(TeeDateUtil.format(
					samplingevidencesModel.getReceiptDateStr(), "yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		samplingevidences.setUpdateUserId(user.getUserId());
		samplingevidences.setUpdateUserName(user.getUserName());
		samplingevidences.setUpdateTime(Calendar.getInstance().getTime());
		// 保存 抽样取证凭证表
		samplingevidencesService.update(samplingevidences);
		json.setRtData(samplingevidences);
		json.setRtState(true);
		return json;
	}

	/**
	 * 通过baseId和lawLinkId 获取物品清单信息
	 * 
	 * @param samplingevidencesModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSampEdnsInfo")
	public TeeJson get(SamplingevidencesModel samplingevidencesModel,HttpServletRequest request) {
		TeeJson json = new TeeJson();
		List<DocSamplingevidences> listSamplingevidences = samplingevidencesService
				.getByBaseId(samplingevidencesModel);
		json.setRtData(listSamplingevidences);
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
	public TeeJson viewDocInfo(SamplingevidencesModel samplingevidencesModel,
			HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(
				request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(
				request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = samplingevidencesModel
				.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId,
				content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	/**
	 * 抽样取证凭证取立案审批表的信息
	 * @param baseId
	 * @param lawLinkId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromPuton")
	public TeeJson getDocFromPuton(String baseId) {
		TeeJson json = new TeeJson();
		List<PunishFLow> list = flowService.getFlowcase(baseId, "立案审批", "");//通过baseId 和 流程名  查流程 信息（立案审批一般只有一个）
		PunishFLow fir = list.get(0);
		String lawLinkId = fir.getTacheId();
		PutonrecordModel model = new PutonrecordModel();
		List<DocPutonrecord> putoList = (List<DocPutonrecord>) commonService .getDocByBaseId("DocPutonrecord", baseId, lawLinkId);
		List<PutonrecordModel> modelList = new ArrayList<PutonrecordModel>();
		for (DocPutonrecord docPutonrecord : putoList) {
			BeanUtils.copyProperties(docPutonrecord, model);	
			modelList.add(model);
		}
		json.setRtData(modelList);
		return json;
  }
	/**
	 * 抽样取证凭证取现场勘察的信息
	 * @param baseId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/getDocFromInsp")
	public TeeJson getDocFromInsp(String baseId, String lawLinkId) {
		TeeJson json = new TeeJson();
		List<DocInspectionRecord> inspList = (List<DocInspectionRecord>) commonService.getDocByBaseId("DocInspectionRecord", baseId, lawLinkId);
		json.setRtData(inspList);
		return json;
  }
}
