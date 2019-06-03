package com.tianee.oa.core.base.hr.training.plan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.hr.training.plan.model.TeeTrainingPlanModel;
import com.tianee.oa.core.base.hr.training.plan.service.TeeTrainingPlanService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/trainingPlanController")
public class TeeTrainingPlanController extends BaseController {
	@Autowired
	private TeeTrainingPlanService service;
	

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
		TeeTrainingPlanModel model = new TeeTrainingPlanModel();

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
	@RequestMapping("/getRecruitList")
	@ResponseBody
	public TeeEasyuiDataGridJson getRecruitList(TeeDataGridModel dm,
			HttpServletRequest request) throws ParseException,
			java.text.ParseException {
		TeeTrainingPlanModel model = new TeeTrainingPlanModel();
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
        TeeTrainingPlanModel model = new TeeTrainingPlanModel();
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
     * 获取各种状态总数
     * @date 2014-3-9
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/getStatusCount")
    @ResponseBody
    public TeeJson getStatusCount(HttpServletRequest request ) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        //将request中的对应字段映值射到目标对象的属性中
        TeeTrainingPlanModel model = new TeeTrainingPlanModel();
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = service.getStatusCountService(person , model);
        return json;
    }
    
    /**
	 * 审批查询
	 * 
	 * @date 2014-3-18
	 * @author
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getPlanApprovalList")
	@ResponseBody
	public TeeEasyuiDataGridJson getPlanApprovalList(TeeDataGridModel dm,
			HttpServletRequest request) throws ParseException,
			java.text.ParseException {
		TeeTrainingPlanModel model = new TeeTrainingPlanModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.getPlanApprovalListService(dm, request, model);
	}
	
	
	/**
	 * 设置状态
	 * 
	 * @date 2014-3-18
	 * @author
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/setPlanStatus")
	@ResponseBody
	public TeeJson setPlanStatus(TeeDataGridModel dm,
			HttpServletRequest request) throws ParseException,
			java.text.ParseException {
		TeeTrainingPlanModel model = new TeeTrainingPlanModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return service.setPlanStatusService(request, model);
	}
	
	
	
	/**
	 * 获取所有的培训计划
	 * @author syl
	 * @date 2014-6-21
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllPlan")
	@ResponseBody
	public TeeJson getAllPlan(HttpServletRequest request){
		TeeJson json  = service.getAllPlan();
		return json;
	}
	
    
    

}
