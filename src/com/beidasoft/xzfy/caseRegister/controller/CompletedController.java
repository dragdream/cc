package com.beidasoft.xzfy.caseRegister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bjca.org.apache.log4j.Logger;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.caseRegister.model.registrationCompleted.request.CaseRegisteredReq;
import com.beidasoft.xzfy.caseRegister.model.registrationCompleted.response.CaseRegisteredResp;
import com.beidasoft.xzfy.caseRegister.service.CompletedService;
import com.beidasoft.xzfy.organPerson.controller.OrganPersonController;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 登记完成
 * @author chumc
 *
 */
@Controller
@RequestMapping("/registrationCompleted")
public class CompletedController extends FyBaseController {

	private static final long serialVersionUID = 1L;
	
	public Logger log = Logger.getLogger(OrganPersonController.class);
	
	private CompletedService completedService;

	/**
	 * 案件登记完成
	 * @param caseRegisteredReq
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/caseRegistered")
	public TeeJson caseRegistered(@RequestBody CaseRegisteredReq caseRegisteredReq){
		log.info("[xzfy - CompletedController - caseRegistered] enter controller.");
		TeeJson returnData = new TeeJson();
		CaseRegisteredResp resp = new CaseRegisteredResp();
		try {
			caseRegisteredReq.validate();
			resp = completedService.caseRegistered(caseRegisteredReq);
			returnData.setRtData(resp);
			returnData.setRtState(true);
			returnData.setRtMsg("案件登记完成");
		} catch (Exception e) {
			log.info("[xzfy - registrationCompleted - caseRegistered] error="
					+ e);
			returnData.setRtData(e);
			returnData.setRtState(true);
			returnData.setRtMsg("案件登记失败");
		}
		return returnData;
	}
}
