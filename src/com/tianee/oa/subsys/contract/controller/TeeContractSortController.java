package com.tianee.oa.subsys.contract.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.contract.model.TeeContractSortModel;
import com.tianee.oa.subsys.contract.service.TeeContractSortService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/contractSort")
public class TeeContractSortController {
	
	@Autowired
	private TeeContractSortService contractSortService;
	
	@ResponseBody
	@RequestMapping("/add")
	public TeeJson add(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		TeeContractSortModel contractSortModel = 
				(TeeContractSortModel) TeeServletUtility.request2Object(request, TeeContractSortModel.class);
		contractSortService.add(contractSortModel);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		TeeContractSortModel contractSortModel = 
				(TeeContractSortModel) TeeServletUtility.request2Object(request, TeeContractSortModel.class);
		
		contractSortService.update(contractSortModel);
		json.setRtMsg("更新成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		contractSortService.delete(sid);
		json.setRtMsg("删除成功");
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(contractSortService.get(sid));
		return json;
	}
	
	/**
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		return contractSortService.datagrid(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/getSortByCatId")
	public TeeJson getSortByCatId(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int catId = TeeStringUtil.getInteger(request.getParameter("catId"), 0);
		json.setRtData(contractSortService.getSortByCatId(catId));
		return json;
	}
}
