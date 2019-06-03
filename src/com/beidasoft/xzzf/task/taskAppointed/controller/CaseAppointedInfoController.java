package com.beidasoft.xzzf.task.taskAppointed.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.common.service.PunishTacheService;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.beidasoft.xzzf.task.taskAppointed.model.CaseAppointedInfoModel;
import com.beidasoft.xzzf.task.taskAppointed.service.CaseAppointedInfoService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;


@Controller
@RequestMapping("caseAppointedInfoController")
public class CaseAppointedInfoController {
	@Autowired
	private CaseAppointedInfoService caseAppotionedService;
	
	@Autowired
	private PunishBaseDao baseDao;

	@Autowired
	private TeePersonService teePersonService;
	
	@Autowired
	private PunishBaseService punishBaseService;
	
	@Autowired
	private PunishFlowService punishFlowService;
	
	@Autowired
	private PunishTacheService punishTacheService;
	
	/**
	 * 获取所有待受理案件
	 * @param dataGridModel
	 * 
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listByPage")
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel, CaseAppointedInfoModel queryModel, HttpServletRequest request) {
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		//登录用户信息
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//通过分页显示案件信息
		long total =  caseAppotionedService.getTotal(queryModel,loginUser);
		List<CaseAppointedInfoModel> modelList = new ArrayList<>();
		List<CaseAppointedInfo> caseAppointed = caseAppotionedService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(), queryModel, loginUser);
		for (CaseAppointedInfo caseAppointedInfo : caseAppointed) {
			CaseAppointedInfoModel caseAppoiontedInfoModel = new CaseAppointedInfoModel();
			BeanUtils.copyProperties(caseAppointedInfo, caseAppoiontedInfoModel);
			if(caseAppointedInfo.getTaskSendTime() != null) {
				caseAppoiontedInfoModel.setTaskSendTimeStr(TeeDateUtil.format(caseAppointedInfo.getTaskSendTime(), "yyyy-MM-dd"));
			}
			if(caseAppointedInfo.getCreateTime() != null) {
				caseAppoiontedInfoModel.setCreateTimeStr(TeeDateUtil.format(caseAppointedInfo.getCreateTime().getTime(),"yyyy-MM-dd HH:mm"));
			}
			if(caseAppointedInfo.getDisposeTime() != null) {
				caseAppoiontedInfoModel.setDisposeTimeStr(TeeDateUtil.format(caseAppointedInfo.getDisposeTime().getTime(),"yyyy-MM-dd HH:mm"));
			}
			
			modelList.add(caseAppoiontedInfoModel);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
	/**
	 * 通过taskRecId获取案件指派页面初始化信息（案件信息、）
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getByTaskRecId")
	public TeeJson getByTaskRecId(Model model,String taskRecId, HttpServletRequest request){
		TeeJson json = new TeeJson();
		//获取案件信息
		CaseAppointedInfo caseAppointedInfo = caseAppotionedService.getByTaskRecId(taskRecId);
		CaseAppointedInfoModel caseAppoiontedInfoModel = new CaseAppointedInfoModel();
		if (caseAppointedInfo != null) {
			
			BeanUtils.copyProperties(caseAppointedInfo, caseAppoiontedInfoModel);
			if(caseAppointedInfo.getTaskSendTime() != null) {
				caseAppoiontedInfoModel.setTaskSendTimeStr(TeeDateUtil.format(caseAppointedInfo.getTaskSendTime(), "yyyy-MM-dd"));
			}
			if(caseAppointedInfo.getCreateTime() != null) {
				caseAppoiontedInfoModel.setCreateTimeStr(TeeDateUtil.format(caseAppointedInfo.getCreateTime().getTime(),"yyyy-MM-dd HH:mm"));
			}
			if(caseAppointedInfo.getDisposeTime() != null) {
				caseAppoiontedInfoModel.setDisposeTimeStr(TeeDateUtil.format(caseAppointedInfo.getDisposeTime().getTime(),"yyyy-MM-dd HH:mm"));
			}

			json.setRtState(true);
			json.setRtData(caseAppoiontedInfoModel);
		} else {
			json.setRtState(false);
			json.setRtData(null);
		}
		return json;
	}
	/**
	 * 案件指派（添加案件执法人员、案件基础信息）
	 * @param caseAppointedInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(CaseAppointedInfoModel caseAppointedInfoModel, HttpServletRequest request){
		//判断案件指派时候的类型（如果saveType的值是9,表示案件已经指派但是还可继续指派；如果saveType的值是10表示案件已经指派完毕）
		return caseAppotionedService.saveAppointed(caseAppointedInfoModel, request);
	}
	
	/**
	 * 案件办结（将案件表dealType属性改为10，不能再次进行案件指派功能）
	 * @param caseAppointedInfoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appointBreak")
	public TeeJson appointBreak(CaseAppointedInfoModel caseAppointedInfoModel, HttpServletRequest request){
		return caseAppotionedService.appointBreak(request.getParameter("taskRecId"));
	}
	
	/**
	 * 案件不予立案
	 * @param CaseAppointedInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public TeeJson update(CaseAppointedInfoModel caseAppointedInfoModel){
		CaseAppointedInfo userInfo = caseAppotionedService.getByTaskRecId(caseAppointedInfoModel.getTaskRecId());
		
		userInfo.setExtraComments(caseAppointedInfoModel.getExtraComment());
		userInfo.setDealType(11);
		userInfo.setDisposeTime(Calendar.getInstance());
		return caseAppotionedService.update(userInfo);
	}
	
	
	/**
	 * 申请立案
	 * 
	 * @param baseId punishBase 表主键UUID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/applyRegister")
	public TeeJson applyRegister(String baseId) {
		TeeJson json = new TeeJson();
		
		json = caseAppotionedService.applyRegister(baseId);
		
		return json;
	}
	
	/**
	 * 检查合格
	 * 
	 * @param baseId punishBase 表主键UUID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkUnError")
	public TeeJson checkUnError(String baseId) {
		TeeJson json = new TeeJson();
		
		json = caseAppotionedService.checkUnError(baseId, 1);
		
		return json;
	}
	
	/**
	 * 企业不存在或其他原因
	 * 
	 * @param baseId punishBase 表主键UUID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkLostParty")
	public TeeJson checkLostParty(String baseId) {
		TeeJson json = new TeeJson();
		
		json = caseAppotionedService.checkUnError(baseId, 2);
		
		return json;
	}
	
}
