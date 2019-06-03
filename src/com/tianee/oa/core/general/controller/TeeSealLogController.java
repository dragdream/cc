package com.tianee.oa.core.general.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.model.TeeSealModel;
import com.tianee.oa.core.general.service.TeeSealLogService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;



@Controller
@RequestMapping("/sealLogManage")
public class TeeSealLogController  {
	@Autowired
	private TeeSealLogService sealLogService;

	/**
	 * 获取通用列表
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSealLogList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm ,HttpServletRequest request, TeeSealModel model) {
		return sealLogService.datagrid(dm,request);
	}

	public void setSealLogService(TeeSealLogService sealLogService) {
		this.sealLogService = sealLogService;
	}

	
	
}



