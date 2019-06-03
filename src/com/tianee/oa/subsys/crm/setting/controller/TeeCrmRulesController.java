package com.tianee.oa.subsys.crm.setting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.attend.bean.TeeAttendLeaderRule;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaderRuleModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.setting.service.TeeCrmRulesService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/TeeCrmRulesController")
public class TeeCrmRulesController extends BaseController {
	@Autowired
	private TeeCrmRulesService rulesService;
	
	/**
	 * 设置Crm审批规则
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveCrmRulePerson")
	public TeeJson saveCrmRulePerson(HttpServletRequest request) throws Exception {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		TeeJson json = new TeeJson();
		rulesService.saveCrmRulePerson(requestDatas);
		json.setRtMsg("参数设置成功！");
		json.setRtState(true);
		return json;
		
	}
	

}
