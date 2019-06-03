package com.beidasoft.xzzf.task.caseAssigned.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.model.PunishBaseModel;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.task.caseAssigned.service.CaseAssignedService;
import com.beidasoft.xzzf.task.caseAssigned.service.CaseTransactService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("caseTransactControllerr")
public class CaseTransactController {
	@Autowired
	private CaseAssignedService caseAssignedService;
	
	
	@Autowired
	private CaseTransactService caseTransactService;
	
	@Autowired
	private PunishFlowService punishFlowService;
	/**
	 * 通过人员编号查看自己案件进度(案件办理界面)
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listByPage")
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel, PunishBaseModel queryModel, HttpServletRequest request) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//登录人信息
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//通过分页显示用户信息
		long total =  caseTransactService.getTotal(queryModel, loginUser);
		//遍历案件信息
		List<PunishBase> punishBase = caseTransactService.listByPageuserId(dataGridModel.getFirstResult(), dataGridModel.getRows(), queryModel, loginUser);
		List<PunishBaseModel> listPunishBaseModel = new ArrayList<>();
		
		//将案件信息从bean赋值到model以及时间类型的转换
		for(PunishBase punishBean:punishBase) {
			PunishBaseModel punishBaseModel = new PunishBaseModel();
			BeanUtils.copyProperties(punishBean, punishBaseModel);
			if(punishBean.getFilingDate() != null) {
				punishBaseModel.setFilingDateStr(TeeDateUtil.format(punishBean.getFilingDate(), "yyyy-MM-dd HH:mm"));
			}
			if(punishBean.getInspectionDate() != null) {
				punishBaseModel.setInspectionDateStr(TeeDateUtil.format(punishBean.getInspectionDate(), "yyyy-MM-dd HH:mm"));
			}
			if(punishBean.getPunishmentDate() != null) {
				punishBaseModel.setPunishmentDateStr(TeeDateUtil.format(punishBean.getPunishmentDate(), "yyyy-MM-dd HH:mm"));
			}
			
			listPunishBaseModel.add(punishBaseModel);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(listPunishBaseModel);
		return dataGridJson;
	}
	
	/**
	 * 通过人员编号查看自己案件进度(现场检查界面)
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkList")
	public TeeEasyuiDataGridJson checkList(TeeDataGridModel dataGridModel, PunishBaseModel queryModel, HttpServletRequest request) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//登录人信息
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//通过分页显示用户信息
		long total =  caseTransactService.getCheckTotal(queryModel, loginUser);
		//遍历案件信息
		List<PunishBase> punishBase = caseTransactService.listByCheckPage(dataGridModel.getFirstResult(), dataGridModel.getRows(), queryModel, loginUser);
		List<PunishBaseModel> listPunishBaseModel = new ArrayList<>();
		
		//将案件信息从bean赋值到model以及时间类型的转换
		for(PunishBase punishBean:punishBase) {
			PunishBaseModel punishBaseModel = new PunishBaseModel();
			BeanUtils.copyProperties(punishBean, punishBaseModel);
			if(punishBean.getFilingDate() != null) {
				punishBaseModel.setFilingDateStr(TeeDateUtil.format(punishBean.getFilingDate(), "yyyy-MM-dd HH:mm"));
			}
			if(punishBean.getInspectionDate() != null) {
				punishBaseModel.setInspectionDateStr(TeeDateUtil.format(punishBean.getInspectionDate(), "yyyy-MM-dd HH:mm"));
			}
			if(punishBean.getPunishmentDate() != null) {
				punishBaseModel.setPunishmentDateStr(TeeDateUtil.format(punishBean.getPunishmentDate(), "yyyy-MM-dd HH:mm"));
			}
			
			listPunishBaseModel.add(punishBaseModel);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(listPunishBaseModel);
		return dataGridJson;
	}
	
	/**
	 * 通过人员编号查看自己案件进度(听证管理界面)
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hearingInfo")
	public TeeEasyuiDataGridJson getHearingInfo(TeeDataGridModel dataGridModel, PunishBaseModel queryModel, HttpServletRequest request) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//登录人信息
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//听证场合
		queryModel.setIsHearing(1);
		
		//通过分页显示用户信息
		long total = caseTransactService.getHearingTotal(queryModel, loginUser);
		//遍历案件信息
		List<PunishBase> punishBaseList =
				caseTransactService.getHearingInfo(dataGridModel.getFirstResult(), dataGridModel.getRows(), queryModel, loginUser);
		
		List<PunishBaseModel> listPunishBaseModel = new ArrayList<>();
		
		//将案件信息从bean赋值到model以及时间类型的转换
		PunishBaseModel punishBaseModel;
		for(PunishBase punishBean : punishBaseList) {
			punishBaseModel = new PunishBaseModel();
			BeanUtils.copyProperties(punishBean, punishBaseModel);
			if(punishBean.getHearingDate() != null) {
				punishBaseModel.setHearingDateStr(TeeDateUtil.format(punishBean.getHearingDate(), "yyyy-MM-dd HH:mm"));
			}
			listPunishBaseModel.add(punishBaseModel);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(listPunishBaseModel);
		return dataGridJson;
	}
}
