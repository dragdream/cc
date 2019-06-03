package com.tianee.oa.core.base.hr.recruit.requirements.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.hr.recruit.requirements.model.TeeRecruitRequirementsModel;
import com.tianee.oa.core.base.hr.recruit.requirements.service.TeeRecruitRequirementsService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/recruitRequirementsController")
public class TeeRecruitRequirementsController extends BaseController {
	@Autowired
	private TeeRecruitRequirementsService service;

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
		TeeRecruitRequirementsModel model = new TeeRecruitRequirementsModel();

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
		TeeRecruitRequirementsModel model = new TeeRecruitRequirementsModel();
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
        TeeRecruitRequirementsModel model = new TeeRecruitRequirementsModel();
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
     * 批量 申请面试
     * @date 2015年8月10日
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/batchSendInterview")
    @ResponseBody
    public TeeJson batchSendInterview(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        json = service.batchSendInterviewInfo(request);
        return json;
    }
    

}
