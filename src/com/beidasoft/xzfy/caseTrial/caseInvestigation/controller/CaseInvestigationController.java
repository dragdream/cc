package com.beidasoft.xzfy.caseTrial.caseInvestigation.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.caseTrial.caseInvestigation.bean.CaseInvestigation;
import com.beidasoft.xzfy.caseTrial.caseInvestigation.service.CaseInvestigationService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

/**   
 * Description:审理-调查管理
 * @author ZCK
 * @version 0.1 2019年4月23日
 */
@Controller
@RequestMapping("/caseInvestigationController")
public class CaseInvestigationController {
	@Autowired
	private CaseInvestigationService caseInvestigationService;
	/**
	 * Description:调查管理保存
	 * @author ZCK
	 * @version 0.1 2019年4月23日
	 * @param request
	 * @return
	 * TeeJson
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TeeJson saveOrUpdate(HttpServletRequest request) {
		TeeJson json= new TeeJson();
		try {
			CaseInvestigation caseInvestigation = caseInvestigationService.getById(request);
			//调用保存或修改方法
			if(caseInvestigation!=null) {
				caseInvestigationService.update(request,json);
			}else {
				caseInvestigationService.save(request,json);
			}
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("保存失败,请联系管理员!");
		}
	 	return json;
	}
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(HttpServletRequest request) {
		TeeJson json= new TeeJson();
		try {
			//调用保存或修改方法
			caseInvestigationService.update(request,json);
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("修改失败,请联系管理员!");
		}
		return json;
	}
	
	/**
	 * Description:根据案件id查询调查管理
	 * @author ZCK
	 * @version 0.1 2019年4月23日
	 * @param request
	 * @return
	 * TeeJson
	 */
	@RequestMapping("/getListByCaseId")
	@ResponseBody
	public TeeEasyuiDataGridJson getListByCaseId(TeeDataGridModel dm, HttpServletRequest request) {
		return caseInvestigationService.getListByCaseId(dm,request);
		//return caseInvestigationService.getListByCaseIdRtJson(request);
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public TeeJson del(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		try {
			caseInvestigationService.delete(request);
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("操作失败,请联系管理员!");
		}
		return json;
	}
	
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		CaseInvestigation caseInvestigation = null;
		try {
			caseInvestigation = caseInvestigationService.getById(request);
			json.setRtState(true);
			json.setRtData(caseInvestigation);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("操作失败,请联系管理员!");
		}
		return json;
	}
	
}
