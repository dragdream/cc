package com.tianee.oa.core.base.hr.recruit.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrFilterModel;
import com.tianee.oa.core.base.hr.recruit.service.TeeHrFilterService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
@Controller
@RequestMapping("/hrFilterController")
public class TeeHrFilerController extends BaseController {
	@Autowired 
	private TeeHrFilterService filterService;

	/**
	 * 新增或者更新
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeHrFilterModel model)
			throws ParseException, IOException {
		TeeJson json = new TeeJson();
		json = filterService.addOrUpdate(request, model);
		return json;
	}

	/**
	 * 筛选
	 * @date 2014-3-18
	 * @author
	 * @param dm
	 * @param request
	 * @return  
	 */
	@RequestMapping("/getHrFilterList")
	@ResponseBody
	public TeeEasyuiDataGridJson getHrFilterList(TeeDataGridModel dm,
			HttpServletRequest request , TeeHrFilterModel model){
		return filterService.getHrFilterList(dm, request, model);
	}
	
	/**
	 * 查询
	 * @date 2014-3-18
	 * @author
	 * @param dm
	 * @param request
	 * @return  
	 */
	@RequestMapping("/queryHrFilterList")
	@ResponseBody
	public TeeEasyuiDataGridJson queryHrFilterList(TeeDataGridModel dm,
			HttpServletRequest request , TeeHrFilterModel model){
		return filterService.queryHrFilterList(dm, request, model);
	}
	
	
	/**
	 * 查询详情
	 * @author syl
	 * @date 2014-6-27
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeHrFilterModel model){
		return filterService.getByIdInfo( model);
	}
	
	  /**
     * 删除
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/deleteObjById")
    @ResponseBody
    public TeeJson deleteObjById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String sidStr = request.getParameter("sids");
        json = filterService.deleteObjById(sidStr);
        return json;
    }
}
