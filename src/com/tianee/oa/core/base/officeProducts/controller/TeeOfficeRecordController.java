package com.tianee.oa.core.base.officeProducts.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.officeProducts.model.TeeOfficeProductModel;
import com.tianee.oa.core.base.officeProducts.service.TeeOfficeRecordService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/officeRecordController")
public class TeeOfficeRecordController {
	
	@Autowired
	TeeOfficeRecordService officeRecordService;
	
	@RequestMapping("/delRecord")
	@ResponseBody
	public TeeJson delRecord(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 入库记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeRecordService.datagrid(dm, requestDatas);
	}
	
	/**
	 * 报废记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid2")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid2(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeRecordService.datagrid2(dm, requestDatas);
	}
	
	/**
	 * 领用记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid3")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid3(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeRecordService.datagrid3(dm, requestDatas);
	}
	
	/**
	 * 借用记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid4")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid4(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeRecordService.datagrid4(dm, requestDatas);
	}
	
	/**
	 * 归还记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid5")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid5(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeRecordService.datagrid5(dm, requestDatas);
	}
	
	/**
	 * 维护记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid6")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid6(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeRecordService.datagrid6(dm, requestDatas);
	}
	
	/**
	 * 库存记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid7")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid7(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeRecordService.datagrid7(dm, requestDatas);
	}
	
	/**
	 * 删除记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/datagrid8")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid8(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeRecordService.datagrid8(dm, requestDatas);
	}
	
	/**
	 * 删除记录列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOfficeRecords")
	@ResponseBody
	public TeeJson getOfficeRecords(HttpServletRequest request){
		String proCode = TeeStringUtil.getString(request.getParameter("proCode"),"");
		String recordType = TeeStringUtil.getString(request.getParameter("recordType"),"");
		return officeRecordService.getOfficeRecords(proCode,recordType);
	}
}
