package com.tianee.oa.core.workflow.flowmanage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeListCtrlExtend;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowFormItemModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/listCtrlExtend")
public class TeeListCtrlExtendController {
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	
	/**
	 * 保存列表扩展信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody()
	public TeeJson save(HttpServletRequest request){
		TeeJson json = new TeeJson();
		
		int flowPrcsId = TeeStringUtil.getInteger(request.getParameter("flowPrcsId"), 0);
		int formItemId = TeeStringUtil.getInteger(request.getParameter("formItemId"), 0);
		String columnCtrlModel = TeeStringUtil.getString(request.getParameter("columnCtrlModel"));
		
		TeeListCtrlExtend lce = new TeeListCtrlExtend();
		lce.setColumnCtrlModel(columnCtrlModel);
		lce.setFlowPrcsId(flowPrcsId);
		
		TeeFormItem item = flowFormService.getFormItemById(formItemId);
		lce.setFormItemId(item.getItemId());
		
		flowFormService.saveOrUpdateListCtrlExtend(lce);
		
		json.setRtState(true);
		json.setRtMsg("保存列表扩展信息成功");
		return json;
	}
	
	/**
	 * 获取列表扩展信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListCtrlExtend")
	@ResponseBody()
	public TeeJson getListCtrlExtend(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int flowPrcsId = TeeStringUtil.getInteger(request.getParameter("flowPrcsId"), 0);
		int formItemId = TeeStringUtil.getInteger(request.getParameter("formItemId"), 0);
		
		TeeFormItem item = flowFormService.getFormItemById(formItemId);
		
		TeeListCtrlExtend lce = flowFormService.getListCtrlExtend(flowPrcsId, item.getItemId());
		
		json.setRtData(lce);
		json.setRtState(true);
		json.setRtMsg("保存列表扩展信息成功");
		return json;
	}
	/**
	 * 获取列表字段模型数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getListFieldInfo")
	@ResponseBody()
	public TeeJson getListFieldInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int formItemId = TeeStringUtil.getInteger(request.getParameter("formItemId"), 0);
		
		TeeFormItem formItem = flowFormService.getFormItemById(formItemId);
		TeeFlowFormItemModel model = new TeeFlowFormItemModel();
		BeanUtils.copyProperties(formItem, model);
		
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}

	public void setFlowFormService(TeeFlowFormServiceInterface flowFormService) {
		this.flowFormService = flowFormService;
	}

	
}
