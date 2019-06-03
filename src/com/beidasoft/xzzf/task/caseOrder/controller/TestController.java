package com.beidasoft.xzzf.task.caseOrder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.task.caseInterface.httpmodel.CaseJson;
import com.beidasoft.xzzf.task.caseInterface.service.CaseInfoProgressService;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;

@Controller
@RequestMapping("testController")
public class TestController {

	@Autowired
	private CaseInfoProgressService caseInfo;
	
	@ResponseBody
	@RequestMapping("save")
	public void save(){
		CaseAppointedInfo casein = new CaseAppointedInfo();
		casein.setTaskType(0);
		casein.setTaskName("立案");
		 CaseJson ss = caseInfo.NewCaseApprove(casein);
		System.out.println(ss);
	}
}
