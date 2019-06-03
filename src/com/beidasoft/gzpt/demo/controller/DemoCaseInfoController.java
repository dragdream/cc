package com.beidasoft.gzpt.demo.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.gzpt.demo.service.DemoCaseInfoService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Controller
@RequestMapping("/DemoCaseInfoController")
public class DemoCaseInfoController {
	@Autowired
	DemoCaseInfoService demoCaseInfoService;

	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request) throws ParseException {
		return demoCaseInfoService.datagrid(dm, null);
	}
}
