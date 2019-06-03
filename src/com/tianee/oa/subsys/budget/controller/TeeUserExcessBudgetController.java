package com.tianee.oa.subsys.budget.controller;

import java.util.Map;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import com.tianee.oa.subsys.budget.model.TeeUserExcessBudgetModel;
import com.tianee.oa.subsys.budget.service.TeeUserExcessBudgetService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;


@Controller
@RequestMapping("/userExcessBudgetController")
public class TeeUserExcessBudgetController {
	@Autowired
	private TeeUserExcessBudgetService userExcessService;
	/**
	 * 添加超额度表
	 * @param request
	 * @return
	 */
	@RequestMapping("/adduserExcess")
	@ResponseBody
	public TeeJson adduserExcess(HttpServletRequest request)  {
		TeeJson json = new TeeJson();
		TeeUserExcessBudgetModel  model = new TeeUserExcessBudgetModel();
		String userId=request.getParameter("userId");
		TeeServletUtility.requestParamsCopyToObject(request, model);
			 userExcessService.addObj(request,model,userId);
			 json.setRtState(true);
		return json;
	}
}