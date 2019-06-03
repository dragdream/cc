package com.beidasoft.xzzf.punish.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.document.service.ArticlesMainService;
import com.beidasoft.xzzf.punish.document.service.SealseizureService;
import com.beidasoft.xzzf.punish.manage.bean.PunishSealseizure;
import com.beidasoft.xzzf.punish.manage.model.PunishSealseizureModel;
import com.beidasoft.xzzf.punish.manage.service.PunishSealseizureService;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/punishSealseizureController")
public class PunishSealseizureController {
	
	@Autowired
	private TeeBaseUpload baseUpload;
	@Autowired
	private PunishSealseizureService punishSealseizureService;
	@Autowired
	private SealseizureService sealseizureService;
	@Autowired
	private ArticlesMainService articlesMainService;
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@RequestMapping("/table")
	@ResponseBody
	public TeeEasyuiDataGridJson table(HttpServletRequest request, TeeDataGridModel dm, String baseId) {
		return punishSealseizureService.getSealseizureOfPage(dm, request,baseId);
	}
	
	/**
	 * 查封扣押办结+保存管理数据
	 */
	@RequestMapping("/punishTurnEnd")
	@ResponseBody
	public TeeJson save(PunishSealseizureModel model, HttpServletRequest request) {
		return punishSealseizureService.punishTurnEnd(model, request);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(PunishSealseizureModel model){
		return punishSealseizureService.updatePunishSealseizure(model);
	}
	
	/**
	 * 根据id获取
	 */
	@RequestMapping("/get")
	@ResponseBody
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();
		Map map = new HashMap();
		PunishSealseizure punishSealseizure= punishSealseizureService.loadById(id);
		PunishSealseizureModel model = new PunishSealseizureModel();
		BeanUtils.copyProperties(punishSealseizure, model);
		if(!TeeUtility.isNullorEmpty(punishSealseizure.getProcessDecisionDateStart())){
			model.setProcessDecisionDateStartStr(TeeDateUtil.format(punishSealseizure.getProcessDecisionDateStart(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(punishSealseizure.getProcessDecisionDateEnd())){
			model.setProcessDecisionDateEndStr(TeeDateUtil.format(punishSealseizure.getProcessDecisionDateEnd(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(punishSealseizure.getCheckupDateStart())){
			model.setCheckupDateStartStr(TeeDateUtil.format(punishSealseizure.getCheckupDateStart(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(punishSealseizure.getCheckupDateEnd())){
			model.setCheckupDateEndStr(TeeDateUtil.format(punishSealseizure.getCheckupDateEnd(), "yyyy-MM-dd"));
		}
		if(!TeeUtility.isNullorEmpty(punishSealseizure.getDelayDate())){
			model.setDelayDateStr(TeeDateUtil.format(punishSealseizure.getDelayDate(), "yyyy-MM-dd"));
		}
		String attachName = "";
		if((!TeeUtility.isNullorEmpty(punishSealseizure.getAttachId()))){
			if(punishSealseizure.getAttachId()!=0){
				attachName = attachmentService.getFileNameById(punishSealseizure.getAttachId());
			}
		}
		map.put("model", model);
		map.put("attachName", attachName);
		json.setRtData(map);
		return json;
	}
	
	
}
