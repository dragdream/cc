package com.tianee.oa.core.workflow.formmanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormSort;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.core.workflow.formmanage.service.TeeFormSortServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFormSortModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping(value="/formSort")
public class TeeFormSortController {
	@Autowired
	private TeeFormSortServiceInterface formSortService;
	
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	
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
		return formSortService.datagrid(dm);
	}
	
	/**
	 * 
	 * 获取表单分类列表
	 * @return
	 */
	@RequestMapping(value="/getSortList")
	@ResponseBody
	public TeeJson getSortList() {
		TeeJson json = new TeeJson();
		List<TeeFormSortModel> list = new ArrayList<TeeFormSortModel>();
		List<TeeFormSort> sortList = formSortService.list();//获取所有表单分类
		for(TeeFormSort formSort:sortList){//将表单分类转化为表单分类模型
			TeeFormSortModel m = new TeeFormSortModel();
			BeanUtils.copyProperties(formSort, m);
			list.add(m);
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(list);
		
		return json;
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public TeeJson save(TeeFormSort formSort){
		TeeJson json = new TeeJson();
		
		formSortService.save(formSort);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("保存表单分类成功");
		
		return json;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public TeeJson update(int formSortId,String sortName){
		TeeJson json = new TeeJson();
		
		TeeFormSort formSort = formSortService.get(formSortId);
		formSort.setSortName(sortName);
		
		formSortService.update(formSort);
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("更新表单分类成功");
		
		return json;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public TeeJson delete(TeeFormSort formSort){
		TeeJson json = new TeeJson();
		
		formSortService.delete(formSort);
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtMsg("删除表单分类成功");
		
		return json;
	}
	
	@RequestMapping(value="/deleteBatch")
	@ResponseBody
	public TeeJson deleteBatch(String sortIds){
		TeeJson json = new TeeJson();
		
		int sortIdArray[] = TeeStringUtil.parseIntegerArray(sortIds);
		
		
		for(int id:sortIdArray){
			formSortService.delete(id);
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		
		return json;
	}
	
	@RequestMapping(value="/get")
	@ResponseBody
	public TeeJson get(int formSortId){
		TeeJson json = new TeeJson();
		
		TeeFormSortModel m = formSortService.getModel(formSortId);
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(m);
		
		return json;
	}
	
	@RequestMapping(value="/doSortOrder")
	@ResponseBody
	public TeeJson doSortOrder(int type,int formSortId){
		TeeJson json = new TeeJson();
		
		formSortService.doSortOrder(type, formSortId);
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
	@RequestMapping(value="/getFormSortTree")
	@ResponseBody
	public TeeJson getFormSortTree(Integer id){
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		
		if(id==null){//获取第一层分类节点
			List<TeeFormSort> formSortList = formSortService.list();
			for(TeeFormSort formSort:formSortList){
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId(String.valueOf(formSort.getSid()));
				m.setName(formSort.getSortName());
				m.setParent(true);
				list.add(m);
			}
		}else{//根据分类节点获取表单
			
		}
		
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(list);
		
		return json;
	}
	
	/**
	 * 获取表单分类模型集合
	 * @return
	 */
	@RequestMapping(value="/getSelectCtrlDataSource")
	@ResponseBody
	public TeeJson getSelectCtrlDataSource(){
		TeeJson json = new TeeJson();
		
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		List<TeeFormSort> formSortList = formSortService.list();
		
		TeeZTreeModel m = new TeeZTreeModel();
		m.setDisabled(true);
		m.setName("默认分类");
		m.setTitle("默认分类");
		m.setpId(null);
		m.setId("fs"+0);
		m.setIconSkin("wfNode1");
		list.add(m);
		
		//创建分类集合
		for(TeeFormSort formSort:formSortList){
			m = new TeeZTreeModel();
			m.setDisabled(true);
			m.setName(formSort.getSortName());
			m.setTitle(formSort.getSortName());
			m.setpId(null);
			m.setId("fs"+formSort.getSid());
			m.setIconSkin("wfNode1");
//			Map map = new HashMap();
//			map.put("groupName", formSort.getSortName());
//			List<Map> list1 = new ArrayList<Map>();
//			
//			map.put("items", list1);
			list.add(m);
		}
		
		//获取所有分类下表单集合
		List<TeeForm> formList = flowFormService.list();
		for(TeeForm form:formList){
			m = new TeeZTreeModel();
			m.setName(form.getFormName());
			m.setTitle(form.getFormName());
			m.setId(String.valueOf(form.getSid()));
			m.setpId(form.getFormSort()==null?"fs0":"fs"+form.getFormSort().getSid());
			m.setIconSkin("wfNode2");
			list.add(m);
		}
		
		json.setRtState(TeeConst.RETURN_OK);
		json.setRtData(list);
		
		return json;
	}

	public void setFormSortService(TeeFormSortServiceInterface formSortService) {
		this.formSortService = formSortService;
	}

	public void setFlowFormService(TeeFlowFormServiceInterface flowFormService) {
		this.flowFormService = flowFormService;
	}

	
	
}
