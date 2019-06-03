package com.beidasoft.xzzf.punish.common.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.bean.PunishTache;
import com.beidasoft.xzzf.punish.common.model.PunishFLowModel;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.common.service.PunishTacheService;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/punishFlowCtrl")
public class PunishFlowController {
	@Autowired
	private PunishFlowService punishFlowService;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishTacheService tacheService;
	
	
	/**
	 * 获取流程信息
	 * 
	 * @param baseId 案件ID
	 * @param confFlowName 流程名
	 * @return
	 */
	@RequestMapping("/cfg")
	@ResponseBody
	public TeeJson getFlowcase(String baseId,String confFlowName, String lawLinkId) {
		TeeJson json = new TeeJson();
		
		List<PunishFLow> flowInfoList = punishFlowService.getFlowcase(baseId, confFlowName, lawLinkId);
		
		List<PunishFLowModel> modelList = new ArrayList<PunishFLowModel>();
		for(PunishFLow flows : flowInfoList){
			PunishFLowModel model = new PunishFLowModel();
			BeanUtils.copyProperties(flows, model);
			model.setCreatetimeStr(TeeDateUtil.format(flows.getTime(), "yyyy-MM-dd HH:mm"));
			modelList.add(model);
		}

		json.setRtData(modelList);
		json.setRtState(true);
		
		return json;
	}
	/**
	 * 获取指定环节
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFlowByTacheId")
	@ResponseBody
	public TeeJson getFlowByTacheId(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"), "");
		String tacheId = TeeStringUtil.getString(request.getParameter("tacheId"), "");
		
		List<PunishFLow> punishFLows = punishFlowService.getFlowByTacheId(baseId, tacheId, userId);
		
		List<PunishFLowModel> modelList = new ArrayList<PunishFLowModel>();
		for(PunishFLow flows : punishFLows){
			PunishFLowModel model = new PunishFLowModel();
			BeanUtils.copyProperties(flows, model);
			model.setCreatetimeStr(TeeDateUtil.format(flows.getTime(), "yyyy-MM-dd HH:mm"));
			modelList.add(model);
		}

		json.setRtData(modelList);
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 获取指定环节
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFlowAssign")
	@ResponseBody
	public TeeJson getFlowAssign(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		String baseId = TeeStringUtil.getString(request.getParameter("baseId"), "");
		String tacheId = TeeStringUtil.getString(request.getParameter("tacheId"), "");
		
		List<PunishFLow> punishFLows = punishFlowService.getFlowByTacheId(baseId, tacheId, userId);
		
		List<PunishFLowModel> modelList = new ArrayList<PunishFLowModel>();
		PunishFLowModel model = null;
		for(int i = 0; i < punishFLows.size(); i++){
			model = new PunishFLowModel();
			BeanUtils.copyProperties(punishFLows.get(i), model);
			model.setCreatetimeStr(TeeDateUtil.format(punishFLows.get(i).getTime(), "yyyy-MM-dd HH:mm"));
			modelList.add(model);
		}

		json.setRtData(modelList);
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 获取环节信息
	 * 
	 * @param baseId 案件ID
	 * @param punishTacheId 环节ID
	 * @return
	 */
	@RequestMapping("/flow")
	@ResponseBody
	public TeeJson getFlow(String baseId,String punishTacheId) {
		TeeJson json = new TeeJson();
		
		List<PunishTache> ss=punishFlowService.getFlow(baseId,punishTacheId);

		json.setRtData(ss);
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 保存环节信息
	 * 
	 * @param punflow
	 * @return
	 */
	@RequestMapping("/saveflow")
	@ResponseBody
	public TeeJson saveflow(PunishFLow punflow) {
		TeeJson json = new TeeJson();
		
		//存ID
		punflow.setId(UUID.randomUUID().toString());
	
		if(StringUtils.isBlank(punflow.getTacheId())){
			String id = "";
			//通过baseId 查 到 环节实体类 取其Id 赋值给 流程
			List<PunishTache> tache = tacheService.getbyindex(punflow.getBaseId(),"0");
			for(PunishTache p:tache){
				id += p.getId();
			}
			punflow.setTacheId(id);
		}
		
		//单独处理时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
		String str=sdf.format(new Date());
		punflow.setTime(TeeDateUtil.format(str, "yyyy-MM-dd HH：mm：ss"));
		
		punishFlowService.save(punflow);
		
		if (punflow.getConfFlowName().equals("案件处理呈批表")) {
			PunishFLow flow = new PunishFLow();
			BeanUtils.copyProperties(punflow, flow);
			flow.setConfFlowName("行政处罚决定书");
			flow.setPunishPrcsName("行政处罚决定书");
			flow.setId(UUID.randomUUID().toString());
			punishFlowService.save(flow);
		}
		
		json.setRtState(true);
		json.setRtData(punflow.getId());
		return json;
	}
	
	
	/**
	 * 通过 flowId  获取 流程信息
	 * 
	 * @param flowId 环节ID
	 * @return
	 */
	@RequestMapping("/getByFlowId")
	@ResponseBody
	public TeeJson getById(String flowId) {
		TeeJson json = new TeeJson();
		
		PunishFLow punflow = punishFlowService.getById(flowId);
		
		json.setRtData(punflow);
		
		return json;
	}
	
	/**
	 * 通过 runId  更新 流程信息（用于更新审批表文书名）审批表-xxx
	 * 
	 * @param runId
	 * @return
	 */
	@RequestMapping("/updateApproval")
	@ResponseBody
	public TeeJson update(String runId, String approvalMatter) {
		TeeJson json = new TeeJson();
		
		PunishFLow punflow = punishFlowService.getByRunId(runId);
		
		if(punflow.getConfFlowName().equals("审批表") && punflow.getPunishPrcsName().equals("审批表")) {
			punflow.setConfFlowName(punflow.getConfFlowName()+"--"+approvalMatter);
			punflow.setPunishPrcsName(punflow.getPunishPrcsName()+"--"+approvalMatter);
		}else {
			punflow.setConfFlowName("审批表"+"--"+approvalMatter);
			punflow.setPunishPrcsName("审批表"+"--"+approvalMatter);
		}
		
		punishFlowService.update(punflow);
		
		json.setRtData(punflow);
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 通过baseId 和 confFlowName  判断文书list长度返回 boolean 类型
	 * 
	 * @param baseId
	 * @param confFlowName
	 * @return
	 */
	@RequestMapping("/searchDocFlowNum")
	@ResponseBody
	public TeeJson searchDocFlowNum(String baseId, String confFlowName) {
		TeeJson json = new TeeJson();
		boolean res = confFlowName.contains(",");
		boolean result = true;
		if (res) {
			String nameArr[] = confFlowName.split(",");
			for (String name : nameArr) {
				List<PunishFLow> flowList = punishFlowService.getFlowcase(baseId, name, "");
				if (flowList.size() == 0) {
					result = false;
					break;
				}
			}
		} else {
			List<PunishFLow> flowList = punishFlowService.getFlowcase(baseId, confFlowName, "");
			if (flowList.size() == 0) {
				result = false;
			}
		}
		json.setRtState(result);
		return json;
	}
	
	/**
	 * 通过 runId  更新 流程信息（用于存储文书ID）
	 * 
	 * @param runId
	 * @return
	 */
	@RequestMapping("/flowUpdate")
	@ResponseBody
	public TeeJson flowUpdate(String runId, String docId) {
		TeeJson json = new TeeJson();
		
		PunishFLow punflow = punishFlowService.getByRunId(runId);
		punflow.setPunishDocId(docId);
		
		punishFlowService.update(punflow);
		
		json.setRtData(punflow);
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 
	 * 通过runId 获取流程list（适用与行政处罚决定书和案件处理呈批表）
	 * @param runId
	 * @return
	 */
	@RequestMapping("/getListByRunId")
	@ResponseBody
	public TeeJson getListByRunId(String runId) {
		TeeJson json = new TeeJson();
		List<PunishFLow> flowList = punishFlowService.getListByRunId(runId);
		
		if (flowList.size() != 0) {
			json.setRtData(flowList);
			json.setRtState(true);
		} else {
			json.setRtData(new ArrayList<>());
			json.setRtState(false);
		}
		return json;
	}
}
