package com.tianee.oa.subsys.budget.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.budget.model.TeeDeptExcessBudgetModel;
import com.tianee.oa.subsys.budget.model.TeeUserExcessBudgetModel;
import com.tianee.oa.subsys.budget.service.TeeDeptExcessBudgetService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/deptExcessBudgetController")
public class TeeDeptExcessBudgetController {
	@Autowired
	private TeeDeptExcessBudgetService deptExcessService;
	
	@RequestMapping("/addDeptExcess")
	@ResponseBody
	public TeeJson adduserExcess(HttpServletRequest request)  {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeDeptExcessBudgetModel  model = new TeeDeptExcessBudgetModel();
		String deptId=request.getParameter("deptId");
		TeeServletUtility.requestParamsCopyToObject(request, model);
		deptExcessService.addObj(request,model,deptId,person);
			 json.setRtState(true);
		return json;
	}
}
