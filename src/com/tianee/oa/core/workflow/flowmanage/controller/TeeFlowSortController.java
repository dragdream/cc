package com.tianee.oa.core.workflow.flowmanage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowSortServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowSortModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping(value="/flowSort")
public class TeeFlowSortController {
	@Autowired
	private TeeFlowSortServiceInterface flowSortService;
	
	/**
	 * EASYUI-datagrid前端获取器
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request,HttpServletResponse response) {
		return flowSortService.datagrid(dm);
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public TeeJson save(TeeFlowSort flowSort){
		TeeJson json = new TeeJson();
		
		flowSortService.save(flowSort);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("保存流程分类成功");
		
		return json;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public TeeJson update(int flowSortId,String sortName){
		TeeJson json = new TeeJson();
		
		TeeFlowSort flowSort = flowSortService.get(flowSortId);
		flowSort.setSortName(sortName);
		
		flowSortService.update(flowSort);
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("更新流程分类成功");
		
		return json;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public TeeJson delete(TeeFlowSort flowSort){
		TeeJson json = new TeeJson();
		
//		flowSortService.delete(flowSort);
//		json.setRtState(TeeConst.RETURN_OK);
//		json.setRtMsg("删除流程分类成功");
		
		return json;
	}
	
	/**
	 * 获取模型实体
	 * @param flowSortId
	 * @return
	 */
	@RequestMapping(value="/get")
	@ResponseBody
	public TeeJson get(int flowSortId){
		TeeJson json = new TeeJson();
		
		TeeFlowSortModel m = flowSortService.getModel(flowSortId);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(m);
		
		return json;
	}
	
	/**
	 * 获取模型列表
	 * @return
	 */
	@RequestMapping(value="/getList")
	@ResponseBody
	public TeeJson getList(){
		TeeJson json = new TeeJson();
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(flowSortService.getModelList());
		
		return json;
	}
	
	@RequestMapping(value="/doSortOrder")
	@ResponseBody
	public TeeJson doSortOrder(int type,int flowSortId){
		TeeJson json = new TeeJson();
		
		flowSortService.doSortOrder(type, flowSortId);
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("已排序");
		
		return json;
	}
	
	@RequestMapping(value="/deleteBatch")
	@ResponseBody
	public TeeJson deleteBatch(String sortIds){
		TeeJson json = new TeeJson();
		
		int sortIdArray[] = TeeStringUtil.parseIntegerArray(sortIds);
		
		
		for(int id:sortIdArray){
			flowSortService.delete(id);
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("已排序");
		
		return json;
	}
	
	/**
	 * 获取表单分类信息
	 * @param type
	 * @param flowSortId
	 * @return
	 */
	@RequestMapping(value="/getFlowSortTree4Form")
	@ResponseBody
	public TeeJson getFlowSortTree4Form(Integer id){
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		
		TeeZTreeModel root = new TeeZTreeModel();
		root.setId("-1");
		root.setName("默认分类");
		root.setParent(false);
		list.add(root);
		
		if(id==null){//获取第一层分类节点
			List<TeeFlowSort> flowSortList = flowSortService.list();
			for(TeeFlowSort flowSort:flowSortList){
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId(String.valueOf(flowSort.getSid()));
				m.setName(flowSort.getSortName());
				m.setParent(false);
				list.add(m);
			}
		}else{//根据分类节点获取表单
			
		}
		
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(list);
		
		return json;
	}

	public void setFlowSortService(TeeFlowSortServiceInterface flowSortService) {
		this.flowSortService = flowSortService;
	}

	
}
