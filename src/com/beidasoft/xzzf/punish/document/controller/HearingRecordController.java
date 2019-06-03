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
import com.beidasoft.xzzf.punish.document.bean.DocHearingRecord;
import com.beidasoft.xzzf.punish.document.model.HearingRecordModel;
import com.beidasoft.xzzf.punish.document.service.HearingRecordService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("/hearingRecordCtrl")
public class HearingRecordController {

	@Autowired
	private HearingRecordService reocrdService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存
	 * 
	 * @param recordModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(HearingRecordModel recordModel,  HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		DocHearingRecord record = new  DocHearingRecord();
		
		BeanUtils.copyProperties(recordModel, record);
		
		//单独处理时间
		if (StringUtils.isNotBlank(recordModel.getHearingTimeStartStr())) {
			record.setHearingTimeStart(TeeDateUtil.format(recordModel.getHearingTimeStartStr(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(recordModel.getHearingTimeEndStr())) {
			String TimeStart = recordModel.getHearingTimeStartStr().substring(0,12);
			record.setHearingTimeEnd(TeeDateUtil.format(TimeStart+recordModel.getHearingTimeEndStr(), "yyyy年MM月dd日 HH时mm分"));
		}

		// 设置创建人相关信息
		if (StringUtils.isBlank(recordModel.getId())) {
			record.setCreateUserId(user.getUuid()+"");
			record.setCreateUserName(user.getUserName());
			record.setId(UUID.randomUUID().toString());
			record.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建行政处罚听证笔录");
			record.setDelFlg("0");
		} else {
			//设置修改人相关信息
			record.setUpdateUserId(user.getUserId());
			record.setUpdateUserName(user.getUserName());
			record.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改行政处罚听证笔录");
		}
		reocrdService.saveDocInfo(record,request);
		
		json.setRtData(record);
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 通过ID 获取
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		DocHearingRecord record = reocrdService.getDocInfo(id);
		HearingRecordModel recordModel = new HearingRecordModel();
		
		BeanUtils.copyProperties(record, recordModel);
		
		//单独处理时间
		if (record.getHearingTimeStart() != null) {
			recordModel.setHearingTimeStartStr(TeeDateUtil.format(record.getHearingTimeStart(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (record.getHearingTimeEnd() != null) {
			recordModel.setHearingTimeEndStr(TeeDateUtil.format(record.getHearingTimeEnd(), "HH时mm分"));
		}

		if (record.getUpdateTime() != null) {
			recordModel.setUpdateTimeStr(TeeDateUtil.format(record.getUpdateTime(), "yyyy年MM月dd日"));
		}
		
		json.setRtData(recordModel);
		json.setRtState(true);
		
		return json;
	}
}
