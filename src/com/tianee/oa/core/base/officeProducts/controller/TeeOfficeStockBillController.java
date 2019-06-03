package com.tianee.oa.core.base.officeProducts.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeStockBill;
import com.tianee.oa.core.base.officeProducts.model.TeeOfficeStockBillModel;
import com.tianee.oa.core.base.officeProducts.service.TeeOfficeStockBillService;
import com.tianee.oa.core.base.officeProducts.service.TeeOfficeStockService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/officeStockBillController")
public class TeeOfficeStockBillController {
	@Autowired
	private TeeOfficeStockBillService billService;
	@Autowired
	private TeeOfficeStockService officeStockService;
	
	@Autowired
	private TeePersonService personService;
	
	@RequestMapping("/addStockBill")
	@ResponseBody
	public TeeJson addStockBill(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		TeeOfficeStockBillModel officeStockBillModel = new TeeOfficeStockBillModel();
		TeeServletUtility.requestParamsCopyToObject(request, officeStockBillModel);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		officeStockBillModel.setRegUserId(loginUser.getUuid());
		billService.addStockBillModel(officeStockBillModel);
		
		json.setRtState(true);
		json.setRtMsg("登记成功");
		
		return json;
	}
	
	/**
	 * 作废
	 * @param request
	 * @return
	 */
	@RequestMapping("/doInvalid")
	@ResponseBody
	public TeeJson doInvalid(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int billId = TeeStringUtil.getInteger(request.getParameter("billIds"), 0);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		billService.doInvalid(loginUser, new int[]{billId});
		
		json.setRtState(true);
		json.setRtMsg("操作成功");
		
		return json;
	}
	
	/**
	 * 审批
	 * @param request
	 * @return
	 */
	@RequestMapping("/doAudit")
	@ResponseBody
	public TeeJson doAudit(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser = personService.selectByUuid(loginUser.getUuid()+"");
		int billId = TeeStringUtil.getInteger(request.getParameter("billId"), 0);
		int auditType = TeeStringUtil.getInteger(request.getParameter("auditType"), 0);
		String remark = TeeStringUtil.getString(request.getParameter("remark"));
		int operatorId = TeeStringUtil.getInteger(request.getParameter("operatorId"), 0);
		TeePerson operator = personService.selectByUuid(operatorId+"");
		
		billService.doAudit(loginUser, new int[]{billId}, auditType, operator, remark);
		
		json.setRtState(true);
		json.setRtMsg("操作成功");
		return json;
	}
	
	/**
	 * 审批列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return billService.datagrid(dm, requestDatas);
	}
	
	/**
	 * 审批列表(管理)
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagridAdmin")
	@ResponseBody
	public TeeEasyuiDataGridJson datagridAdmin(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return billService.datagridAdmin(dm, requestDatas);
	}
	
	/**
	 * 调度列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagridOperates")
	@ResponseBody
	public TeeEasyuiDataGridJson datagridOperates(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return billService.datagridOperates(dm, requestDatas);
	}
	
	/**
	 * 根据billId查询可选调度人员列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOperatorsByBillId")
	@ResponseBody
	public TeeJson getOperatorsByBillId(HttpServletRequest request){
		int billId = TeeStringUtil.getInteger(request.getParameter("billId"), 0);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtData(billService.getOperatorsByBillId(billId));
		return json;
	}
	
	/**
	 * 开始调度
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/doOperated")
	@ResponseBody
	public TeeJson doOperated(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int billId = TeeStringUtil.getInteger(request.getParameter("billId"), 0);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser = personService.selectByUuid(loginUser.getUuid()+"");
		billService.doOperated(loginUser, billId);
		json.setRtState(true);
		json.setRtMsg("操作成功");
		return json;
	}
	
	/**
	 * 确认领取
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/doObtain")
	@ResponseBody
	public TeeJson doObtain(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int billId = TeeStringUtil.getInteger(request.getParameter("billId"), 0);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser = personService.selectByUuid(loginUser.getUuid()+"");
		billService.doObtain(loginUser, billId);
		json.setRtState(true);
		json.setRtMsg("操作成功");
		return json;
	}
	
	/**
	 * 判断申请量是否超出库存量
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkOutOfStockCount")
	@ResponseBody
	public TeeJson checkOutOfStockCount(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int productId = TeeStringUtil.getInteger(request.getParameter("productId"), 0);
		int regCount = TeeStringUtil.getInteger(request.getParameter("regCount"), 0);
		json.setRtData(officeStockService.checkOutOfStockCount(productId, regCount));
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 获取某个用品申请的具体信息
	 * */
	@ResponseBody
	@RequestMapping("/findStockPublic")
	public TeeJson findStockPublic(HttpServletRequest request){
		return billService.findStockPublic(request);
	}
	
	/**
	 * 查看用品调度的具体信息
	 * */
	@ResponseBody
	@RequestMapping("/findPublicDiaoDau")
	public TeeJson findPublicDiaoDau(HttpServletRequest request){
		return billService.findPublicDiaoDau(request);
	}
}
