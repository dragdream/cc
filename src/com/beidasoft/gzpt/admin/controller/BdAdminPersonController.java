package com.beidasoft.gzpt.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/adminPerson")
public class BdAdminPersonController {

	@Autowired
	private TeePersonService personService;
	
	
	/**
	 * 按条件查询----
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryPerson")
	@ResponseBody
	public TeeJson queryPerson(HttpServletRequest request, TeePersonModel model) throws Exception {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			model.setIsAdmin("1"); //只取分管理员用户
			json = personService.queryPerson(model, json,loginPerson);
			json.setRtState(true);
			json.setRtMsg("查询成功");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 获取人员通用个列表
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getPersonList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest response) {
		return personService.datagrid(dm,response);
	}

	
}
