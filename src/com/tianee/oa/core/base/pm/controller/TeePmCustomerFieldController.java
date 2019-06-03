package com.tianee.oa.core.base.pm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.pm.model.TeePmCustomerFieldModel;
import com.tianee.oa.core.base.pm.service.TeePmCustomerFieldService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/pmCustomerFieldController")
public class TeePmCustomerFieldController extends BaseController {
	@Autowired
	private TeePmCustomerFieldService pmCustomerFieldService;
	
	/**
	 * 保存或更新
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePmCustomerFieldModel model = new TeePmCustomerFieldModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
	    pmCustomerFieldService.addOrUpdate(model);
		json.setRtMsg("添加成功！");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 查询字段列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request){
		return pmCustomerFieldService.datagrid(dm,null);
		
	}
	
	/**
	 * 删除选中字段
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteField")
	public TeeJson deleteField(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		pmCustomerFieldService.deleteFieldById(sid);
		json.setRtMsg("删除成功！");
		json.setRtState(true);
		return json;
		
	}
	
	/**
	 * 获取自定义字段
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(pmCustomerFieldService.getInfoBySid(sid));
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("updateField")
	public TeeJson updateField(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePmCustomerFieldModel model = new TeePmCustomerFieldModel();
		int sid= TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		pmCustomerFieldService.updateField(model,sid);
		
		json.setRtMsg("修改成功");
		json.setRtState(true);
		return json;
	}

}
