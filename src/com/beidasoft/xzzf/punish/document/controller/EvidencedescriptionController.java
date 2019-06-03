package com.beidasoft.xzzf.punish.document.controller;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;

@Controller
@RequestMapping("/evidencedescriptionCtrl")
public class EvidencedescriptionController {

	@Autowired
	private EvidencedescriptionService evidencedescriptionService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	@Autowired
	
	/**
	 * 取证情况及证据说明保存
	 * 
	 * @param docevidencedescriptionModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(EvidencedescriptionModel docevidencedescriptionModel, HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		// 实例化实体类对象
		DocEvidencedescription docevidencedescription = new DocEvidencedescription();
		
		// 属性值传递
		BeanUtils.copyProperties(docevidencedescriptionModel, docevidencedescription);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(docevidencedescriptionModel.getEvidenceStartTimeStr())) {
			docevidencedescription.setEvidenceStartTime(TeeDateUtil.format(docevidencedescriptionModel.getEvidenceStartTimeStr(),
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(docevidencedescriptionModel.getEvidenceEndTimeStr())) {
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(docevidencedescriptionModel.getEnforcementStampDataStr())) {
			docevidencedescription.setEnforcementStampData(TeeDateUtil.format(docevidencedescriptionModel.getEnforcementStampDataStr(), 
					 "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(docevidencedescriptionModel.getClientStampDataStr())) {
			docevidencedescription.setClientStampData(TeeDateUtil.format(docevidencedescriptionModel.getClientStampDataStr(), 
					"yyyy年MM月dd日"));
		}

		if (StringUtils.isBlank(docevidencedescriptionModel.getId())) {
			docevidencedescription.setCreateUserId(user.getUserId());
		//设置创建人相关信息
		docevidencedescription.setUpdateUserId(user.getUserId());
		docevidencedescription.setUpdateUserName(user.getUserName());
		docevidencedescription.setUpdateTime(Calendar.getInstance().getTime());
		docevidencedescription.setDelFlg("0");
		// 保存 取证情况及证据说明
		evidencedescriptionService.save(docevidencedescription , request);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 获取单个取证情况及证据说明（通过主键ID）
	 * @param inspectionRecord
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson get(String id, HttpServletRequest request) {

		TeeJson json = new TeeJson();
		
		DocEvidencedescription docEvidencedescription = evidencedescriptionService.getById(id);
		
		EvidencedescriptionModel docevidencedescriptionModel = new EvidencedescriptionModel();

		BeanUtils.copyProperties(docEvidencedescription, docevidencedescriptionModel);

		// 单独处理时间类型转换
		if (docEvidencedescription.getEvidenceStartTime() != null) {
			docevidencedescriptionModel.setEvidenceStartTimeStr(TeeDateUtil.format(docEvidencedescription.getEvidenceStartTime(), 
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (docEvidencedescription.getEvidenceEndTime() != null) {
			docevidencedescriptionModel.setEvidenceEndTimeStr(TeeDateUtil.format(docEvidencedescription.getEvidenceEndTime(), 
					"HH时mm分"));
		}
		if (docEvidencedescription.getEnforcementStampData() != null) {
			docevidencedescriptionModel.setEnforcementStampDataStr(TeeDateUtil.format(docEvidencedescription.getEnforcementStampData(), 
					"yyyy年MM月dd日"));
		}
		if (docEvidencedescription.getClientStampData() != null) {
			docevidencedescriptionModel.setClientStampDataStr(TeeDateUtil.format(docEvidencedescription.getClientStampData(), 
					"yyyy年MM月dd日"));
		}

		// 返回 取证情况及证据说明记录表 json 对象
		json.setRtData(docevidencedescriptionModel);
		json.setRtState(true);

		return json;
	}
	
	
	/**
	 * 更新取证情况及证据说明
	 * @param inspectionRecordModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(EvidencedescriptionModel docevidencedescriptionModel, HttpServletRequest request) {

		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		DocEvidencedescription docEvidencedescription = evidencedescriptionService.getById(docevidencedescriptionModel.getId());

		// 属性值传递
		BeanUtils.copyProperties(docevidencedescriptionModel, docEvidencedescription);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(docevidencedescriptionModel.getEvidenceStartTimeStr())) {
			docEvidencedescription.setEvidenceStartTime(TeeDateUtil.format(docevidencedescriptionModel.getEvidenceStartTimeStr(),
					"yyyy年MM月dd日 HH时mm分ss秒"));
		}
		if (StringUtils.isNotBlank(docevidencedescriptionModel.getEvidenceEndTimeStr())) {
			docEvidencedescription.setEvidenceEndTime(TeeDateUtil.format(docevidencedescriptionModel.getEvidenceEndTimeStr(),
					"yyyy年MM月dd日 HH时mm分ss秒"));
		}
		if (StringUtils.isNotBlank(docevidencedescriptionModel.getEnforcementStampDataStr())) {
			docEvidencedescription.setEnforcementStampData(TeeDateUtil.format(docevidencedescriptionModel.getEnforcementStampDataStr(), 
					"yyyy年MM月dd日 HH时mm分ss秒"));
		}
		if (StringUtils.isNotBlank(docevidencedescriptionModel.getClientStampDataStr())) {
			docEvidencedescription.setClientStampData(TeeDateUtil.format(docevidencedescriptionModel.getClientStampDataStr(), 
					"yyyy年MM月dd日 HH时mm分ss秒"));
		}
		
		//设置创建人相关信息
		docEvidencedescription.setUpdateUserId(user.getUserId());
		docEvidencedescription.setUpdateUserName(user.getUserName());
		docEvidencedescription.setUpdateTime(Calendar.getInstance().getTime());

		// 更新 取证情况及证据说明
		evidencedescriptionService.update(docEvidencedescription);

		json.setRtData(docEvidencedescription);
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
	public TeeJson insptnrcdpsnDoc(EvidencedescriptionModel evidencedescriptionModel, HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString(request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = evidencedescriptionModel.getDocInfo(caseCode);
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);
		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
}