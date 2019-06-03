package com.beidasoft.xzzf.punish.classicCase.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.classicCase.bean.ClassicCase;
import com.beidasoft.xzzf.punish.classicCase.model.ClassicCaseModel;
import com.beidasoft.xzzf.punish.classicCase.service.ClassicCaseCollectionService;
import com.beidasoft.xzzf.punish.classicCase.service.ClassicCaseService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/classicCaseController")
public class ClassicCaseController {
	
	@Autowired
	private ClassicCaseService classicCaseService;
	@Autowired
	private ClassicCaseCollectionService classicCaseCollectionService;
	
	/**
	 * 列表
	 * @param dm
	 * @param request
	 * @param classicCase
	 * @return
	 */
	@RequestMapping("/table")
	@ResponseBody
	public TeeEasyuiDataGridJson table(TeeDataGridModel dm, HttpServletRequest request, ClassicCase classicCase) {
		return classicCaseService.pageList(dm, classicCase);
	}
	
	/**
	 * 增加
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TeeJson save(ClassicCaseModel model, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		//1为关联基础案件信息，2为上传附件方式
		if("1".equals(model.getOrigin())){
			json = classicCaseService.saveByBaseCase(model);
		}else{
			json = classicCaseService.saveByUpload(model);
		}
		return json;
	}
	
	
	
	/**
	 * 根据案件id判断是否收藏
	 * @param id
	 * @return TeeJson
	 */
	@RequestMapping("/isCollect")
	@ResponseBody
	public TeeJson get(String id, HttpServletRequest request) {
		return classicCaseCollectionService.isCollect(id,request);
	}
	
	/**
	 * 批量修改状态
	 * @param ids
	 * @param num
	 * @param param
	 * @return TeeJson
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public TeeJson updateStatus(String ids, String num, String param, HttpServletRequest request) {
		return classicCaseService.delete(ids, num, param);
	}
	
	/**
	 * 根据案件id获取
	 * @param id
	 * @return TeeJson
	 */
	@RequestMapping("/getCaseById")
	@ResponseBody
	public TeeJson getCaseById(String id, HttpServletRequest request) {
		return classicCaseService.loadById(id);
	}
	
	/**
	 * 保存收藏案例
	 * @param id
	 * @return TeeJson
	 */
	@RequestMapping("/collect")
	@ResponseBody
	public TeeJson collect(String id, HttpServletRequest request) {
		return classicCaseCollectionService.saveClassicCaseCollection(id, request);
	}
	
	/**
	 * 收藏列表
	 * @param dm
	 * @param request
	 * @param classicCase
	 * @return
	 */
	@RequestMapping("/collectionTable")
	@ResponseBody
	public TeeEasyuiDataGridJson collectionTable(TeeDataGridModel dm, HttpServletRequest request, ClassicCase classicCase) {
		return classicCaseCollectionService.pageList(dm, classicCase, request);
	}
	
	/**
	 * 根据案件id删除收藏
	 * @param id
	 * @return TeeJson
	 */
	@RequestMapping("/delCollectionById")
	@ResponseBody
	public TeeJson delCollectionById(String id, HttpServletRequest request) {
		return classicCaseCollectionService.deleteClassicCaseCollection(id,request);
	}
	
}
