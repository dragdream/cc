package com.tianee.oa.core.xt.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.xt.service.TeeXTRunPrcsService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/teeXTRunPrcsController")
public class TeeXTRunPrcsController {

	@Autowired
	private TeeXTRunPrcsService xtRunPrcsService;
	
	
	
	/**
	 * 获取处理明细列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getHandleDetail")
	@ResponseBody
	public TeeEasyuiDataGridJson getHandleDetail(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunPrcsService.getHandleDetail(request,dm);
	}
	
	
	
	/**
	 * 获取已经处理完的数目  和  列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getHandledListAndNum")
	@ResponseBody
	public TeeJson getHandledListAndNum(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunPrcsService.getHandledListAndNum(request);
	}
	
	
	/**
	 * 获取待办列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getDaiBanList")
	@ResponseBody
	public TeeEasyuiDataGridJson getDaiBanList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunPrcsService.getDaiBanList(request,dm);
	}
	
	
	/**
	 * 获取已办列表
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getYiBanList")
	@ResponseBody
	public TeeEasyuiDataGridJson getYiBanList(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunPrcsService.getYiBanList(request,dm);
	}
	
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson delete(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunPrcsService.delete(request);
	}
	
	
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/delBatch")
	@ResponseBody
	public TeeJson delBatch(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunPrcsService.delBatch(request);
	}
	
	
	/**
	 * 任务接受
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/receive")
	@ResponseBody
	public TeeJson receive(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunPrcsService.receive(request);
	}
	
	
	/**
	 * 保存或者提交
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/saveOrSubmit")
	@ResponseBody
	public TeeJson saveOrSubmit(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunPrcsService.saveOrSubmit(request);
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException {	
		return xtRunPrcsService.getInfoBySid(request);
	}
}
