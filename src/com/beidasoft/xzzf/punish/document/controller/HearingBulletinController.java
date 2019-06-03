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
import com.beidasoft.xzzf.punish.document.bean.DocHearingBulletin;
import com.beidasoft.xzzf.punish.document.model.HearingBulletinModel;
import com.beidasoft.xzzf.punish.document.service.HearingBulletinService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("/hearingBulletinCtrl")
public class HearingBulletinController {

	@Autowired
	private HearingBulletinService hearingBulletinService;
	
	@Autowired
	private CommonService commonService;
	
	
	/**
	 * 听证公告保存
	 * 
	 * @param hearingBullModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(HearingBulletinModel hearingBullModel,  HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeDepartment department = user.getDept();
		DocHearingBulletin hearingBull = new DocHearingBulletin();
		// model--> bean  传值
		BeanUtils.copyProperties(hearingBullModel, hearingBull);
		
		//单独处理时间
		if (StringUtils.isNotBlank(hearingBullModel.getHearingTimeStr())) {
			hearingBull.setHearingTime(TeeDateUtil.format(hearingBullModel.getHearingTimeStr(),"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(hearingBullModel.getLawUnitDateStr())) {
			hearingBull.setLawUnitDate(TeeDateUtil.format(hearingBullModel.getLawUnitDateStr(),"yyyy年MM月dd日"));
		}

		
		// 设置创建人相关信息
		if (StringUtils.isBlank(hearingBullModel.getId())) {
			hearingBull.setLawUnitId(department.getUuid()+"");
			hearingBull.setLawUnitName(department.getDeptName());
			hearingBull.setCreateUserId(user.getUserId());
			hearingBull.setCreateUserName(user.getUserName());
			hearingBull.setId(UUID.randomUUID().toString());
			hearingBull.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建听证公告");
			hearingBull.setDelFlg("0");
		} else {
			//设置修改人相关信息
			hearingBull.setUpdateUserId(user.getUserId());
			hearingBull.setUpdateUserName(user.getUserName());
			hearingBull.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改听证公告");
		}
		hearingBulletinService.save(hearingBull , request);

		json.setRtData(hearingBull);
		json.setRtState(true);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		DocHearingBulletin hearingBull = hearingBulletinService.get(id);
		
		HearingBulletinModel hearingBullModel = new HearingBulletinModel();
		
		BeanUtils.copyProperties(hearingBull, hearingBullModel);
		
		//单独处理时间
		if (hearingBull.getHearingTime() != null) {
			hearingBullModel.setHearingTimeStr(TeeDateUtil.format(hearingBull.getHearingTime(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (hearingBull.getLawUnitDate() != null) {
			hearingBullModel.setLawUnitDateStr(TeeDateUtil.format(hearingBull.getLawUnitDate(),  "yyyy年MM月dd日"));
		}

		if (hearingBull.getUpdateTime() != null) {
			hearingBullModel.setUpdateTimeStr(TeeDateUtil.format(hearingBull.getUpdateTime(),  "yyyy年MM月dd日"));
		}
		
		json.setRtData(hearingBullModel);
		json.setRtState(true);
		return json;
	}
}
