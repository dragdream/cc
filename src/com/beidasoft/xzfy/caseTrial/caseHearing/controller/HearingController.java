package com.beidasoft.xzfy.caseTrial.caseHearing.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.caseTrial.caseHearing.bean.Hearing;
import com.beidasoft.xzfy.caseTrial.caseHearing.model.HearingModel;
import com.beidasoft.xzfy.caseTrial.caseHearing.service.HearingService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

/**   
 * Description:审理-听证管理
 * @author ZCK
 * @version 0.1 2019年4月23日
 */
@Controller
@RequestMapping("/hearingController")
public class HearingController {
	@Autowired
	private HearingService hearingService;

	/**
	 * Description:调查管理保存或修改
	 * @author ZCK
	 * @version 0.1 2019年4月23日
	 * @param request
	 * @return
	 * TeeJson
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TeeJson saveOrUpdate(HttpServletRequest request) {
		TeeJson json= new TeeJson();
		try {
			//调用保存或修改方法
			Hearing hearing = hearingService.getById(request);
			if(hearing!=null) {
				hearingService.update(request,json);
			}else {
				hearingService.save(request,json);
			}
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("保存失败,请联系管理员！");
		}
	 	return json;
	}
	
	/**
	 * Description:根据案件id查询调查管理
	 * @author ZCK
	 * @version 0.1 2019年4月23日
	 * @param request
	 * @return
	 * TeeJson
	 */
	@RequestMapping("/getListByCaseId")
	@ResponseBody
	public TeeEasyuiDataGridJson getListByCaseId(TeeDataGridModel dm, HttpServletRequest request) {
		HearingModel model = new  HearingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return hearingService.getListByCaseId(dm,request,model);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson delete(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		try {
			hearingService.delete(request);
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("删除失败,请联系管理员！");
		}
		return json;
	}
	
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Hearing hearing = null;
		try {
			hearing = hearingService.getById(request);
			json.setRtState(true);
			json.setRtData(hearing);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("操作失败,请联系管理员！");
		}
		return json;
	}
	


	
}
