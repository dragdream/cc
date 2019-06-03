package com.beidasoft.xzzf.punish.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.ConfFlow;
import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.beidasoft.xzzf.punish.common.model.ConfFlowModel;
import com.beidasoft.xzzf.punish.common.model.ConfTacheModel;
import com.beidasoft.xzzf.punish.common.service.ConfFlowService;
import com.beidasoft.xzzf.punish.common.service.ConfTacheService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/confFlowCtrl")
public class ConfFlowController {
	
	private static List<Object> rtnInfo = new ArrayList<Object>();
	
	@Autowired
	private ConfFlowService confFlowService;
	
	@Autowired
	private ConfTacheService confTacheService;
	
	/**
	 * 获取环节信息列表
	 * 
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping("/flowList")
	@ResponseBody
	public TeeEasyuiDataGridJson getTacheList(HttpServletRequest request, TeeDataGridModel dm) {
		return confFlowService.getFlowInfoList(dm, request);
	}
	
	/**
	 * 获取环节流程信息
	 * 
	 * @param confFlowId
	 * @return
	 */
	@RequestMapping("/confFlowInfo")
	@ResponseBody
	public TeeJson getConfFlowInfo(ConfFlowModel confFlowModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		// 指定环节ID
		List<ConfFlowModel> confFlowList = confFlowService.getConfFlowInfo(confFlowModel);
		json.setRtState(true);
		json.setRtData(confFlowList);
		return json;
	}
	
	/**
	 * 删除环节信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TeeJson deleteTacheInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String id = request.getParameter("id");
		confFlowService.delete(id);
		json.setRtState(true);
		// 重置环节信息
		rtnInfo.clear();
		return json;
	}
	
	/**
	 * 编辑环节信息
	 * 
	 * @param confTacheMdl
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public TeeJson saveTacheInfo(ConfFlowModel confFlowMdl, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		ConfFlow confFlow = new ConfFlow();
		BeanUtils.copyProperties(confFlowMdl, confFlow);
		if (StringUtils.isBlank(confFlowMdl.getId())) {
			confFlow.setId(UUID.randomUUID().toString());
			// 创建时间
			confFlow.setCreateTime(new Date());
		}
		confFlowService.edit(confFlow);
		json.setRtState(true);
		// 重置环节信息
		rtnInfo.clear();
		return json;
	}
	
	/**
	 * 获取环节流程信息（暂时保留sence.jsp使用）
	 * 
	 * @param confFlowId
	 * @return
	 */
	@RequestMapping("/detailInfo")
	@ResponseBody
	public TeeJson getConfFlowInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		// 指定环节ID
		String tacheCode = request.getParameter("TacheCode");
		ConfFlowModel confFlowModel = new ConfFlowModel();
		
		confFlowModel.setConfTacheCode(tacheCode);
		List<ConfFlowModel> confFlowList = confFlowService .getConfFlowInfo(confFlowModel);

		json.setRtData(confFlowList);
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 获取环节，文书流程信息
	 * 
	 * @return
	 */
	@RequestMapping("/initTFInfo")
	@ResponseBody
	public TeeJson getTacheFlowInfo() {
		TeeJson json = new TeeJson();
		if (rtnInfo.isEmpty()) {
			Map<String, Object> confInfo = new HashMap<String, Object>();
			List<ConfFlowModel> flowList = null;
			ConfFlowModel tmpMdl = null;
			List<ConfTacheModel> tacheList = confTacheService.getConfTacheInfo(new ConfTache());
			// 根据环节信息获取对应流程信息
			for (ConfTacheModel mdl : tacheList) {
				confInfo = new HashMap<String, Object>();
				tmpMdl = new ConfFlowModel();
				tmpMdl.setConfTacheCode(mdl.getConfTacheCode());
				flowList = confFlowService.getConfFlowInfo(tmpMdl);
				
				//环节ID
				confInfo.put("tacheId", mdl.getConfTacheCode());
				//环节名称
				confInfo.put("tacheName", mdl.getConfTacheName());
				//文书流程信息
				confInfo.put("flowList", flowList);
				//设定返回信息
				rtnInfo.add(confInfo);
			}
		}
		
		json.setRtData(rtnInfo);
		json.setRtState(true);
		return json;
	}
}
