package com.tianee.oa.subsys.informationReport.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplateType;
import com.tianee.oa.subsys.informationReport.model.TeeTaskTemplateTypeModel;
import com.tianee.oa.subsys.informationReport.service.TeeTaskTemplateTypeService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/TeeTaskTemplateTypeController")
public class TeeTaskTemplateTypeController {
	@Autowired	
	private TeeTaskTemplateTypeService typeService;
	
	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(TeeTaskTemplateTypeModel typeModel){
		TeeJson json =new TeeJson();
		TeeTaskTemplateType type=new TeeTaskTemplateType();
		BeanUtils.copyProperties(typeModel, type);
		
		typeService.save(type);
		json.setRtState(true);
		
		return json;
		
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(TeeTaskTemplateTypeModel typeModel){
		TeeJson json =new TeeJson();
		TeeTaskTemplateType type=new TeeTaskTemplateType();
		BeanUtils.copyProperties(typeModel, type);
		
		typeService.update(type);
		json.setRtState(true);
		
		return json;
		
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(int sid){
		TeeJson json =new TeeJson();
		
		typeService.delete(sid);
		json.setRtState(true);
		return json;
		
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(int sid){
		TeeJson json =new TeeJson();
		
		TeeTaskTemplateType type=typeService.get(sid);
		TeeTaskTemplateTypeModel typeModel=new TeeTaskTemplateTypeModel();
		BeanUtils.copyProperties(type, typeModel);
		json.setRtData(typeModel);
		return json;
		
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public List<TeeTaskTemplateTypeModel> taskTemplateTypeList(){
		TeeJson json =new TeeJson();
		List<TeeTaskTemplateTypeModel> typeModels=new ArrayList<TeeTaskTemplateTypeModel>();
		
		List<TeeTaskTemplateType> types=typeService.taskTemplateTypeList();
		for(TeeTaskTemplateType type:types){
			TeeTaskTemplateTypeModel typeModel=new TeeTaskTemplateTypeModel();
			BeanUtils.copyProperties(type, typeModel);
			typeModels.add(typeModel);
		}
		
		
		return typeModels;
		
	}
	
	//检索
	@ResponseBody
	@RequestMapping("/list1")
	public TeeEasyuiDataGridJson list1(TeeDataGridModel gridModel,TeeTaskTemplateTypeModel typemodel){
		TeeEasyuiDataGridJson gridJson=new TeeEasyuiDataGridJson();
		
		List<TeeTaskTemplateTypeModel> teeTypeModel=new ArrayList<TeeTaskTemplateTypeModel>();
		long total=typeService.getTotal(typemodel);
		List<TeeTaskTemplateType> types=typeService.list(gridModel.getFirstResult(), gridModel.getRows(),typemodel);
		for(TeeTaskTemplateType  type: types ){
			TeeTaskTemplateTypeModel typeInfoModel=new TeeTaskTemplateTypeModel();
			BeanUtils.copyProperties(type,typeInfoModel);
			teeTypeModel.add(typeInfoModel);
		}
		
		gridJson.setTotal(total);
		gridJson.setRows(teeTypeModel);
		return gridJson;
		
	}
}
