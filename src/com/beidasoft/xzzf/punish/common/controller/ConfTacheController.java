package com.beidasoft.xzzf.punish.common.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.beidasoft.xzzf.punish.common.model.ConfTacheModel;
import com.beidasoft.xzzf.punish.common.service.ConfTacheService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/confTacheCtrl")
public class ConfTacheController {
	
	@Autowired
	private ConfTacheService confTacheService;
	
	/**
	 * 获取环节信息列表
	 * 
	 * @param request
	 * @param dm
	 * @return
	 */
	@RequestMapping("/tacheList")
	@ResponseBody
	public TeeEasyuiDataGridJson getTacheList(HttpServletRequest request, TeeDataGridModel dm) {
		return confTacheService.getTacheInfoList(dm, request);
	}
	
	/**
	 * 获取环节信息
	 * 
	 * @param confFlowId
	 * @return
	 */
	@RequestMapping("/confTacheInfo")
	@ResponseBody
	public TeeJson getConfTacheInfo(ConfTacheModel confTacheMdl, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		// 指定环节ID
		ConfTache confTache = new ConfTache();
		BeanUtils.copyProperties(confTacheMdl, confTache);
		List<ConfTacheModel> tacheList = confTacheService.getConfTacheInfo(confTache);
		if (tacheList.size() == 0) {
			json.setRtState(false);
		} else {
			json.setRtData(tacheList);
			json.setRtState(true);
		}
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
		confTacheService.delete(id);
		json.setRtState(true);
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
	public TeeJson saveTacheInfo(ConfTacheModel confTacheMdl, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		ConfTache confTache = new ConfTache();
		BeanUtils.copyProperties(confTacheMdl, confTache);
		if (StringUtils.isBlank(confTacheMdl.getConfTacheCode())) {
			confTache.setConfTacheCode(UUID.randomUUID().toString());
		}
		confTacheService.edit(confTache);
		json.setRtState(true);
		return json;
	}
}
