package com.tianee.oa.subsys.salManage.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmCustomerInfoModel;
import com.tianee.oa.subsys.salManage.bean.TeeSalDataPerson;
import com.tianee.oa.subsys.salManage.model.TeeSalDataPersonModel;
import com.tianee.oa.subsys.salManage.service.TeeSalDataPersonService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 工资数据
 * @author think
 *
 */
@Controller
@RequestMapping("/salDataPersonManage")
public class TeeSalDataPersonController  extends BaseController{
	
	@Autowired
	private TeeSalDataPersonService dataPersonService;
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/generate")
	@ResponseBody
	public TeeJson generate(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		dataPersonService.generate(sid);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeSalDataPersonModel model) {
		TeeJson json = new TeeJson();
		json = dataPersonService.getById(request , model);
		return json;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(HttpServletRequest request , TeeSalDataPersonModel model) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		TeeJson json = new TeeJson();
		json = dataPersonService.update(request , model);
		return json;
	}
	
	
	
		
}
