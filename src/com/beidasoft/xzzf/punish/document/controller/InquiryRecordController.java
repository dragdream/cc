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
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocInquiryRecord;
import com.beidasoft.xzzf.punish.document.model.InquiryRecordModel;
import com.beidasoft.xzzf.punish.document.service.InquiryRecordService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/inquiryRecordCtrl")
public class InquiryRecordController {
	
	@Autowired
	private InquiryRecordService recordService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private PunishFlowService punishFlowService;
	
	@Autowired
	private CommonService commonService;
	/**
	 * 保存调查询问笔录表数据
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo (InquiryRecordModel model, HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		DocInquiryRecord record = new DocInquiryRecord();

		//model-->实体类传值
		BeanUtils.copyProperties(model, record);
		
		//单独处理时间
		if (StringUtils.isNotBlank(model.getAskTimeStartStr())) {
			record.setAskTimeStart(TeeDateUtil.format(model.getAskTimeStartStr(),"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(model.getAskTimeEndStr())) {
			String date = model.getAskTimeStartStr().substring(0,12);
			String endDateStr = date + model.getAskTimeEndStr();
			record.setAskTimeEnd(TeeDateUtil.format(endDateStr,"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(model.getAskedBirthdayStr())) {
			record.setAskedBirthday(TeeDateUtil.format(model.getAskedBirthdayStr(),"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(model.getAskSignatureDateStr())) {
			record.setAskSignatureDate(TeeDateUtil.format(model.getAskSignatureDateStr(),"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(model.getAskedSignatureDateStr())) {
			record.setAskedSignatureDate(TeeDateUtil.format(model.getAskedSignatureDateStr(),"yyyy年MM月dd日"));
		}
		
		//创建人相关信息
		if (StringUtils.isBlank(model.getId())) {
			record.setId(UUID.randomUUID().toString());
			record.setCreateUserId(user.getUuid()+"");
			record.setCreateUserName(user.getUserName());
			record.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建调查询问笔录");
		}
		//添加文书操作日志
		commonService.writeLog(request, "修改调查询问笔录");
		//保存调查询问笔录
		recordService.save(record,request);
		
		json.setRtData(record);
		json.setRtState(true);
		return json;
	}

	/**
	 * 通过UUID 获取单条调查询问笔录信息
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id) {
		TeeJson json = new TeeJson();
		InquiryRecordModel recordModel = new InquiryRecordModel();
		
		DocInquiryRecord record = recordService.getById(id);
		//实体类-->model传值
		BeanUtils.copyProperties(record, recordModel);
		
		//单独处理时间
		if (record.getAskTimeStart() != null) {
			recordModel.setAskTimeStartStr(TeeDateUtil.format(record.getAskTimeStart(),"yyyy年MM月dd日 HH时mm分"));
		}
		
		if (record.getAskTimeEnd() != null) {
			recordModel.setAskTimeEndStr(TeeDateUtil.format(record.getAskTimeEnd(),"HH时mm分"));
		}
		if (record.getAskedBirthday() != null) {
			recordModel.setAskedBirthdayStr(TeeDateUtil.format(record.getAskedBirthday(),"yyyy年MM月dd日"));
		}
		if (record.getAskSignatureDate() != null) {
			recordModel.setAskSignatureDateStr(TeeDateUtil.format(record.getAskSignatureDate(),"yyyy年MM月dd日"));
		}
		if (record.getAskedSignatureDate() != null) {
			recordModel.setAskedSignatureDateStr(TeeDateUtil.format(record.getAskedSignatureDate(),"yyyy年MM月dd日"));
		}

		if (record.getUpdateTime() != null) {
			recordModel.setUpdateTimeStr(TeeDateUtil.format(record.getUpdateTime(),"yyyy年MM月dd日"));
		}
		
		json.setRtData(recordModel);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 调查询问笔录文书预览
	 * 
	 * @param recordModel
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/viewDocInfo")
	public TeeJson viewDocInfo(InquiryRecordModel recordModel,HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		// 案件编号
		String caseCode = TeeStringUtil.getString( request.getParameter("caseCode"), "");
		// 文书编号
		int templateId = TeeStringUtil.getInteger( request.getParameter("templateId"), 0);
		// 获取文书内容
		Map<String, String> content = recordModel.getDocInfo(caseCode);
		
		TeeAttachment pdfAttach = wenShuService.initDocTemplate(templateId, content);

		// 文书模板调用ID
		json.setRtData(pdfAttach.getSid());
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 根据baseId获取PunishFLow表数据
	 * @param baseId
	 * @param request
	 * @return TeeJson
	 */
	@ResponseBody
	@RequestMapping("/getPunishFLow")
	public TeeJson getPunishFLow(String runId, HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtData(punishFlowService.getByRunId(runId));
		json.setRtState(true);
		return json;
	}
}
