package com.tianee.oa.subsys.officialSeal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.officialSeal.service.TeeOfficialSealService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/teeOfficialSealController")
public class TeeOfficialSealController {

	@Autowired
	private  TeeOfficialSealService officialService;
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrUpdate.action")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) {
		return officialService.addOrUpdate(request);
	}
	
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delById.action")
	@ResponseBody
	public TeeJson delById(HttpServletRequest request) {
		return officialService.delById(request);
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInfoBySid.action")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request) {
		return officialService.getInfoBySid(request);
	}
	
	
	
	/**
	 * 根据关键字  获取公章的信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSealByKeyWord.action")
	@ResponseBody
	public TeeJson getSealByKeyWord(HttpServletRequest request) {
		//获取前台页面传来的keyWord
		String keyWord=TeeStringUtil.getString(request.getParameter("keyWord"));
		TeeJson json=new TeeJson();
		json.setRtData(officialService.getSealByKeyWord(keyWord));
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 获取公章列表  分页
	 * @param dm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAllList")
	@ResponseBody
	public TeeEasyuiDataGridJson getAllList(TeeDataGridModel dm, HttpServletRequest request) throws Exception {
		return officialService.getAllList(dm, request);
	}
	
	
}
