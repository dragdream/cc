package com.tianee.oa.core.base.hr.recruit.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrRecruitmentModel;
import com.tianee.oa.core.base.hr.recruit.service.TeeHrRecruitmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;

@Controller
@RequestMapping("/recruitmentController")
public class TeeHrRecruitmentController extends BaseController {
	@Autowired
	private TeeHrRecruitmentService service;

	/**
	 * 新增或者更新
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request)
			throws ParseException {
		TeeJson json = new TeeJson();
		TeeHrRecruitmentModel model = new TeeHrRecruitmentModel();

		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = service.addOrUpdateService(request, model);
		return json;
	}

	/**
	 * 招聘信息查询
	 * 
	 * @date 2014-3-18
	 * @author
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getRecruitList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getRecruitList(TeeDataGridModel dm,
			HttpServletRequest request) throws ParseException,
			java.text.ParseException {
		TeeHrRecruitmentModel model = new TeeHrRecruitmentModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getRecruitList(dm, request, model);
	}
	
	
	 /**
     * 根据sid查看详情
     * @date 2014-3-8
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/getInfoById")
    @ResponseBody
    public TeeJson getInfoById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeeHrRecruitmentModel model = new TeeHrRecruitmentModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = service.getInfoByIdService(request, model);
        return json;
    }
	
	
    /**
     * 删除
     * @date 2014年5月27日
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/deleteObjById")
    @ResponseBody
    public TeeJson deleteObjById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String sidStr = request.getParameter("sids");
        json = service.deleteObjByIdService(sidStr);
        return json;
    }

    
    
	/**
	 * 导出excel表格
	 * @param request
	 */
	@RequestMapping("/exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		service.exportExcel(request,response);
	}
}
