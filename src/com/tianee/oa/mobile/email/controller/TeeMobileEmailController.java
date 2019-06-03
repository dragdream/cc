package com.tianee.oa.mobile.email.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.email.model.TeeEmailBodyModel;
import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.email.service.TeeMobileEmailService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**
 * 手机端  -- 邮件
 * @author syl
 */
@Controller
@RequestMapping("/mobileEmailController")
public class TeeMobileEmailController {
	
	@Autowired
	TeeMobileEmailService mobileEmailService;

	/**
	 * 获取收件箱
	 * @author syl
	 * @throws ParseException 
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public TeeEasyuiDataGridJson getList(TeeDataGridModel dm,HttpServletRequest request) throws ParseException {
		TeeEmailModel model = new TeeEmailModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);	
		TeeEasyuiDataGridJson json =  mobileEmailService.getEmailListService(dm, request, model);
		return json;
	}
	
	/**
	 * 获取发送
	 * @author syl
	 * @throws ParseException 
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getSendEmailList")
	@ResponseBody
	public TeeEasyuiDataGridJson getSendEmailList(TeeDataGridModel dm,HttpServletRequest request) throws ParseException {
		TeeEmailBodyModel model = new TeeEmailBodyModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);	
		TeeEasyuiDataGridJson json =  mobileEmailService.getSendEmailList(dm, request, model);
		return json;
	}
	
	/**
	 * 根据Id 获取 邮件对象
	 * @author syl
	 * @date 2014-4-15
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json =  mobileEmailService.getById(request);
		return json;
	}
	/**
	 * 根据Id 获取 邮件对象
	 * @author syl
	 * @date 2014-4-15
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEmailDetailByMailBodyId")
	@ResponseBody
	public TeeJson getEmailDetailByMailBodyId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json =  mobileEmailService.getEmailDetailByMailBodyId(request);
		return json;
	}
	
	/**
	 * 新建并更新
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		TeeJson json = mobileEmailService.addOrUpdate(request ,response);
		return json;
	}
	
	/**
	 * 获取最近联系人
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRecentContacters")
	@ResponseBody
	public TeeJson getRecentContacters(HttpServletRequest request,HttpServletResponse response){
		TeePerson loginPerson = 
				(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = mobileEmailService.getRecentContacters(loginPerson);
		return json;
	}
	
}
