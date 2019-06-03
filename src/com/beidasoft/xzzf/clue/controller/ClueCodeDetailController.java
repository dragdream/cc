package com.beidasoft.xzzf.clue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.clue.bean.ClueCodeDetail;
import com.beidasoft.xzzf.clue.service.ClueCodeDetailService;

@Controller
@RequestMapping("codeDetailController")
public class ClueCodeDetailController {
	
	@Autowired
	private ClueCodeDetailService codeDetailService;
	
	
	@ResponseBody
	@RequestMapping("formList")
	public List<ClueCodeDetail> FormList(){
		List<ClueCodeDetail> formList = codeDetailService.findFormList();
		return formList;
	}
	
	@ResponseBody
	@RequestMapping("formMap")
	public Map<String,Object> FormMap(){
		Map<String, Object> formmap = new HashMap<String, Object>();
		List<ClueCodeDetail> formList = codeDetailService.findFormList();
		for(ClueCodeDetail c:formList){
			formmap.put(c.getDetailKey(), c.getDetailValue());
		}
		return formmap;
	}
	
	@ResponseBody
	@RequestMapping("sourceList")
	public List<ClueCodeDetail> SourceList(String reportForm){
		List<ClueCodeDetail> sourceList = codeDetailService.findSourceList(reportForm);
		return sourceList;
	}
	
	@ResponseBody
	@RequestMapping("sourceMap")
	public Map<String,Object> sourceMap(String reportForm){
		Map<String, Object> sourcemmap = new HashMap<String, Object>();
		List<ClueCodeDetail> sourceList = codeDetailService.findSourceList(reportForm);
		for(ClueCodeDetail c:sourceList){
			sourcemmap.put(c.getDetailKey(), c.getDetailValue());
		}
		return sourcemmap;
	}
	
	@ResponseBody
	@RequestMapping("typeList")
	public List<ClueCodeDetail> findTypeList(){
		List<ClueCodeDetail> typeList = codeDetailService.findTypeList();
		return typeList;
	}
	
	@ResponseBody
	@RequestMapping("departList")
	public List<ClueCodeDetail> findDepartList() {
		List<ClueCodeDetail> typeList = codeDetailService.findDepartList();
		return typeList;
	}
}
