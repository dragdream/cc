package com.beidasoft.xzfy.caseAcceptence.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.base.exception.ValidateException;
import com.beidasoft.xzfy.caseAcceptence.bean.CaseListInfo;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseListRequest;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseAcceptRequest;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseMergeListRequest;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseMergeRequest;
import com.beidasoft.xzfy.caseAcceptence.model.request.CaseAcceptCommitRequest;
import com.beidasoft.xzfy.caseAcceptence.service.CaseAcceptService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

import common.Logger;

/**
 * 案件受理
 * @author fyj
 *
 */
@Controller
@RequestMapping("/xzfy/caseAccept")
public class CaseAcceptController extends FyBaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//日志
	public Logger log = Logger.getLogger(CaseAcceptController.class);
	
	@Autowired
	private CaseAcceptService service;
	
	
	/**
	 * 案件处理
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/caseAccept")
	@ResponseBody
	public TeeJson caseAccept(CaseAcceptRequest req) throws ValidateException{
		
		log.info("[xzfy - CaseAcceptController - caseAccept] enter controller.");
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
			
			//案件受理
			service.caseAccept(req,getRequest());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - CaseAcceptController - caseAccept] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - CaseAcceptController - caseAccept] error=" + e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - CaseAcceptController - caseAccept] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 案件受理完成
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/caseAcceptCommit")
	@ResponseBody
	public TeeJson caseAcceptCommit(CaseAcceptCommitRequest req) throws ValidateException{
		
		log.info("[xzfy - CaseAcceptController - caseAcceptCommit] enter controller.");
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
			
			//案件受理完成
			service.caseAcceptCommit(req);
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - CaseAcceptController - caseAcceptCommit] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - CaseAcceptController - caseAcceptCommit] error=" + e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - CaseAcceptController - caseAcceptCommit] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 获取并案案件列表
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/getCaseMergeList")
	@ResponseBody
	public TeeEasyuiDataGridJson getCaseMergeList(CaseMergeListRequest req) throws ValidateException{
		
		log.info("[xzfy - CaseAcceptController - getCaseMergeList] enter controller.");
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		List<CaseListInfo> list = new ArrayList<>();
		try{
			//参数校验
			req.validate();
			
			//案件合并
			list = service.getCaseMergeList(req, getRequest());
			long size = service.getCaseMergeListSize(req, getRequest());;
			//设置返回参数
			json.setTotal(size);
			json.setRows(list);
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - CaseAcceptController - getCaseMergeList] error=" + e);
		}
		catch(Exception e){
			
			log.info("[xzfy - CaseAcceptController - getCaseMergeList] error=" + e);
		}
		finally{
			
			log.info("[xzfy - CaseAcceptController - getCaseMergeList] controller end.");
		}
		return json;
	}
	
	/**
	 * 案件合并
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/caseMerge")
	@ResponseBody
	public TeeJson caseMerge(CaseMergeRequest req) throws ValidateException{
		
		log.info("[xzfy - CaseAcceptController - caseMerge] enter controller.");
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
			
			//案件合并
			service.caseMarge(req);
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - CaseAcceptController - caseMerge] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - CaseAcceptController - caseMerge] error=" + e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - CaseAcceptController - caseMerge] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 获取案件列表
	 * @param req
	 * @return
	 * @throws ValidateException
	 */
	@RequestMapping("/getCaseListByType")
	@ResponseBody
	public TeeEasyuiDataGridJson getCaseListByType(CaseListRequest req) throws ValidateException{
		
		log.info("[xzfy - CaseAcceptController - getCaseListByType] enter controller.");
		
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		
		List<CaseListInfo> list = new ArrayList<>();
		
		try{
			//参数校验
			req.validate();
			
			//获取受理案件列表
			list = service.getCaseAcceptList(req,getRequest());
			
			long size = service.getCaseAcceptListSize(req,getRequest());;
			//设置返回参数
			json.setTotal(size);
			json.setRows(list);
		}
		catch(ValidateException e){
			
			log.info("[xzfy - CaseAcceptController - getCaseListByType] error=" + e);
		}
		catch(Exception e){
			
			log.info("[xzfy - CaseAcceptController - getCaseListByType] error=" + e);
		}
		finally{
			
			log.info("[xzfy - CaseAcceptController - getCaseListByType] controller end.");
		}
		return json;
	}
	
}
