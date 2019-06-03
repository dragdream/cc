package com.beidasoft.xzfy.organ.controller;

import java.util.List;


import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzfy.base.controller.FyBaseController;
import com.beidasoft.xzfy.base.exception.ValidateException;
import com.beidasoft.xzfy.organ.bean.OrganInfo;
import com.beidasoft.xzfy.organ.model.request.OrganAddRequest;
import com.beidasoft.xzfy.organ.model.request.OrganDeleteRequest;
import com.beidasoft.xzfy.organ.model.request.OrganInfoRequest;
import com.beidasoft.xzfy.organ.model.request.OrganListRequest;
import com.beidasoft.xzfy.organ.model.request.OrganUpdateRequest;
import com.beidasoft.xzfy.organ.model.response.OrganListResponse;
import com.beidasoft.xzfy.organ.service.OrganService;
import com.beidasoft.xzfy.organ.service.OrganTreeService;
import com.beidasoft.xzfy.utils.ConstCode;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;

import common.Logger;
/**
 * 组织机构
 * @author fyj
 *
 */
@Controller
@RequestMapping("/xzfy/organ")
public class OrganController extends FyBaseController{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	public Logger log = Logger.getLogger(OrganController.class);
	
	//组织机构service
	@Autowired
	private OrganService orgService;
	
	@Autowired
	private OrganTreeService treeService;
	
	/**
	 * 查询组织机构列表信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JSONObject list(OrganListRequest req) throws ValidateException{
		
		log.info("[xzfy - OrganController - list] enter controller.");
		OrganListResponse resp = null;
		JSONObject json = null;
		try{
			//参数校验
			req.validate();
			
			//获取所有部门
			List<TeeZTreeModel> rtData = treeService.getFyTree(getRequest(),req.getTreeID());
			
			//获取列表
			List<OrganInfo> list = orgService.getOrganList(req,rtData);
			//获取总记录数
			int total = orgService.getOrganListTotal(req,rtData); 
			
			//设置返回参数
			resp = new OrganListResponse(ConstCode.SUCCESS_CODE, "请求成功");
			resp.setRows(list);
			resp.setTotal(total);
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganController - list] error=" + e);
			resp = new OrganListResponse(e.getResultCode(), e.getResultMsg());
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganController - list] error=" + e);
			resp = new OrganListResponse(ConstCode.SYSTEM_ERROR, "请求失败");
		}
		finally{
			
			json = JSONObject.fromObject(resp);
			log.info("[xzfy - OrganController - list] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 获取组织机构详情
	 * @param req
	 * @return
	 */
	@RequestMapping("/getDetial")
	@ResponseBody
	public TeeJson getDetial(OrganInfoRequest req){
		
		log.info("[xzfy - OrganController - getDetial] enter controller.");
		TeeJson json = new TeeJson();
		
		try{
			//参数校验
			req.validate();
			
			//获取详情
			OrganInfo org = orgService.getOrganInfo(req.getDeptId());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
			json.setRtData(org);
			
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganController - getDetial] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganController - getDetial] error="+e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganController - getDetial] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 新增组织机构
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/addBatch")
	@ResponseBody
	public TeeJson addBatch(OrganAddRequest req){
		
		log.info("[xzfy - OrganController - add] enter controller.");
		TeeJson json = new TeeJson();
		
		try{
		
			//参数校验
			req.validate();
			
			//新增组织机构
			orgService.addBatchInfo(req,getRequest());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganController - add] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganController - add] error="+e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganController - add] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 更新组织机构信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TeeJson update(OrganUpdateRequest req){
		
		log.info("[xzfy - OrganController - update] enter controller.");
		TeeJson json = new TeeJson();
		
		try{
			
			//参数校验
			req.validate();
			
			//修改组织机构
			orgService.updateOrganInfo(req,getRequest());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganController - update] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganController - update] error="+e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganController - update] controller end.");
		}
		return json;
	}
	
	
	/**
	 * 删除组织机构信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson delete(OrganDeleteRequest req){
		
		log.info("[xzfy - OrganController - delete] enter controller.");
		TeeJson json = new TeeJson();
		
		try{
			//参数校验
			req.validate();
			
			//删除组织机构
			orgService.deleteOrgan(req.getOrgIds());
			
			//设置返回参数
			json.setRtState(true);
			json.setRtMsg("请求成功");
		}
		catch(ValidateException e)
		{
			log.info("[xzfy - OrganController - delete] error=" + e);
			json.setRtState(false);
			json.setRtMsg("校验失败");
		}
		catch(Exception e){
			
			log.info("[xzfy - OrganController - delete] error="+e);
			json.setRtState(false);
			json.setRtMsg("请求失败");
		}
		finally{
			
			log.info("[xzfy - OrganController - delete] controller end.");
		}
		return json;
	}
	

}
