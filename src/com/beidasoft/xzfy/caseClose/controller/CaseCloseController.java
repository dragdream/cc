package com.beidasoft.xzfy.caseClose.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.base.exception.ValidateException;
import com.beidasoft.xzfy.caseAcceptence.service.CaseAcceptService;
import com.beidasoft.xzfy.caseClose.model.request.CaseCloseCommitRequest;
import com.beidasoft.xzfy.caseClose.model.request.CaseCloseSaveRequest;
import com.beidasoft.xzfy.caseClose.service.CaseCloseService;
import com.tianee.webframe.httpmodel.TeeJson;

import common.Logger;
/**
 * 案件结案
 * @author fyj
 *
 */
@Controller
@RequestMapping("/xzfy/caseClose")
public class CaseCloseController extends FyBaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//日志
	public Logger log = Logger.getLogger(CaseCloseController.class); 
	
	@Autowired
	private CaseCloseService caseCloseService;
	
	//案件受理service
	@Autowired
	private CaseAcceptService service;
	
	/**
	 * 案件资料保存
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/caseMaterialSave")
	@ResponseBody
	public TeeJson caseMaterialSave(CaseCloseSaveRequest req) throws ValidateException{
		
		log.info("[xzfy - CaseCloseController - caseMaterialSave] enter controller.");
		TeeJson json = new TeeJson();
		try{
			//参数校验
			req.validate();
			
			//校验案件是否存在
			Boolean b = service.isExitCase(req.getCaseId());
			if(!b){
				json.setRtState(false);
				json.setRtMsg("案件不存在");
			}
			//案件资料保存
			caseCloseService.caseMaterialSave(req);
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - CaseCloseController - caseMaterialSave] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - CaseCloseController - caseMaterialSave] error=" + e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - CaseCloseController - caseMaterialSave] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 案件结案完成
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/caseCloseCommit")
	@ResponseBody
	public TeeJson caseCloseCommit(CaseCloseCommitRequest req) throws ValidateException{
		
		log.info("[xzfy - CaseCloseController - caseCloseCommit] enter controller.");
		TeeJson json = new TeeJson();
		try{
			//参数校验
			req.validate();
			
			//案件完成
			caseCloseService.caseCloseCommit(req.getCaseIds(), getRequest());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - CaseCloseController - caseCloseCommit] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - CaseCloseController - caseCloseCommit] error=" + e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - CaseCloseController - caseCloseCommit] controller end.");
		}
		return json;
	}
	
	
}
