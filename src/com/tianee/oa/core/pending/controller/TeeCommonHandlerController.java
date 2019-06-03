package com.tianee.oa.core.pending.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.pending.bean.TeeCommonHandler;
import com.tianee.oa.core.pending.model.TeeCommonHandlerModel;
import com.tianee.oa.core.pending.service.TeeCommonHandlerService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("TeeCommonHandlerController")
public class TeeCommonHandlerController {
	@Autowired
	private TeeCommonHandlerService CHandlerService;
	
	/*
	 * 添加
	 */
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(TeeCommonHandlerModel cHandlerModel){
		TeeJson json =new TeeJson();
		TeeCommonHandler chandler=new TeeCommonHandler();
		
		TeePerson person=new TeePerson();
		person.setUuid(cHandlerModel.getRecUserIds());
		chandler.setRecUserId(person);
		
		TeePerson persons=new TeePerson();
		persons.setUuid(cHandlerModel.getSendUserIds());
		chandler.setSendUserId(persons);
		
		Calendar createTime= Calendar.getInstance();
		chandler.setCreateTime(createTime);
		/*chandler.setPendingTitle(cHandlerModel.getPendingTitle());
		chandler.setPendingContent(cHandlerModel.getPendingContent());
		chandler.setUrl(cHandlerModel.getUrl());
		chandler.setState(cHandlerModel.getState());
		chandler.setModel(cHandlerModel.getModel());*/
		BeanUtils.copyProperties(cHandlerModel, chandler);
		CHandlerService.save(chandler);
		json.setRtState(true);
		return json;
		
	}
	
	/*
	 * 查询
	 */
	@ResponseBody
	@RequestMapping("get")
	public TeeJson update(String uuid){
		TeeJson json =new TeeJson();
		TeeCommonHandlerModel cHandlerModel=new TeeCommonHandlerModel();
		
		TeeCommonHandler chandlers=CHandlerService.get(uuid);
		cHandlerModel.setRecUserName(chandlers.getRecUserId().getUserName());
		cHandlerModel.setRecUserIds(chandlers.getRecUserId().getUuid());
		
		String createTime =TeeDateUtil.format(chandlers.getCreateTime());
		cHandlerModel.setCreateTime(createTime);
		
		cHandlerModel.setSendUserName(chandlers.getSendUserId().getUserName());
		cHandlerModel.setSendUserIds(chandlers.getSendUserId().getUuid());
		BeanUtils.copyProperties(chandlers,cHandlerModel);
		//System.out.println(cHandlerModel);
		json.setRtData(cHandlerModel);
		return json;
		
	}
	
	/*
	 * 更改
	 */
	@ResponseBody
	@RequestMapping("update")
	public TeeJson get(TeeCommonHandlerModel cHandlerModel){
		
		TeeJson json =new TeeJson();
		TeeCommonHandler chandler=CHandlerService.get(cHandlerModel.getUuid());
		
		TeePerson person=new TeePerson();
		person.setUuid(cHandlerModel.getRecUserIds());
		chandler.setRecUserId(person);
		
		TeePerson persons=new TeePerson();
		persons.setUuid(cHandlerModel.getSendUserIds());
		chandler.setSendUserId(persons);
		
		BeanUtils.copyProperties(cHandlerModel, chandler);
		CHandlerService.update(chandler);
		json.setRtState(true);
		return json;
		
	}
	
	/*
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson delete(String uuid){
		TeeJson json =new TeeJson();
		TeePerson person=new TeePerson();
		CHandlerService.delete(uuid);
		json.setRtState(true);
		return json;
		
	}
	
	
	/*
	 * 显示列表 
	 */
	@ResponseBody
	@RequestMapping("list")
	public TeeEasyuiDataGridJson list1(TeeDataGridModel gridModel,TeeCommonHandlerModel chandlandlerModel){
		TeeEasyuiDataGridJson gridJson=new TeeEasyuiDataGridJson();
		
		List<TeeCommonHandlerModel> teeCommonHandlerModel=new ArrayList<TeeCommonHandlerModel>();
		long total=CHandlerService.getTotal(chandlandlerModel);
		List<TeeCommonHandler> chandler=CHandlerService.lists(gridModel.getFirstResult(), gridModel.getRows(),chandlandlerModel);
		for(TeeCommonHandler  chandlers: chandler ){
			
			TeeCommonHandlerModel chandlerModel=new TeeCommonHandlerModel();
			//BeanUtils.copyProperties(chandlerModel,chandlers);
			chandlerModel.setPendingTitle(chandlers.getPendingTitle());
			chandlerModel.setPendingContent(chandlers.getPendingContent());
			chandlerModel.setUrl(chandlers.getUrl());
			chandlerModel.setYiUrl(chandlers.getYiUrl());
			String createTime =TeeDateUtil.format(chandlers.getCreateTime());
			chandlerModel.setCreateTime(createTime);
			chandlerModel.setUuid(chandlers.getUuid());
			chandlerModel.setRecUserName(chandlers.getRecUserId().getUserName());
			chandlerModel.setSendUserName(chandlers.getSendUserId().getUserName());
			chandlerModel.setState(chandlers.getState());
			if(chandlers.getModel().equals("HR")){
				chandlerModel.setModel("人事系统");
			}else if( chandlers.getModel().equals("CW")){
				chandlerModel.setModel("财务系统");
			}else if(chandlers.getModel().equals("ZC")){
				chandlerModel.setModel("资产系统");
			}
			
			teeCommonHandlerModel.add(chandlerModel);
		}
		
		gridJson.setTotal(total);
		gridJson.setRows(teeCommonHandlerModel);
		return gridJson;
		
	}
}
