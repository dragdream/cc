package com.beidasoft.xzfy.caseQuery.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.base.exception.ValidateException;
import com.beidasoft.xzfy.caseQuery.bean.CaseListInfo;
import com.beidasoft.xzfy.caseQuery.model.request.CaseListRequest;
import com.beidasoft.xzfy.caseQuery.service.CaseListService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import common.Logger;
@Controller
@RequestMapping("/xzfy/caseQuery")
public class CaseListController extends FyBaseController{
	
	private static final long serialVersionUID = 1L;

	//日志
	public Logger log = Logger.getLogger(CaseListController.class);
	
	@Autowired
	private CaseListService service;
	/**
	 * 获取案件列表
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/getCaseListByType")
	@ResponseBody
	public TeeEasyuiDataGridJson getCaseListByType(CaseListRequest req) throws ValidateException{
		
		log.info("[xzfy - CaseListController - getCaseListByType] enter controller.");
		
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		
		List<CaseListInfo> list = new ArrayList<>();
		
		try{
			//参数校验
			req.validate();
			
			//获取受理案件列表
			list = service.getCaseAcceptList(req,getRequest());
			
			//设置返回参数
			json.setTotal(new Long(list.size()));
			json.setRows(list);
		}
		catch(ValidateException e){
			
			log.info("[xzfy - CaseListController - getCaseListByType] error=" + e);
		}
		catch(Exception e){
			
			log.info("[xzfy - CaseListController - getCaseListByType] error=" + e);
		}
		finally{
			
			log.info("[xzfy - CaseListController - getCaseListByType] controller end.");
		}
		return json;
	}
}
