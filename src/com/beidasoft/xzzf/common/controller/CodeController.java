package com.beidasoft.xzzf.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.common.bean.CodeDetail;
import com.beidasoft.xzzf.common.service.CodeService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("codeCtrl")
public class CodeController {
	
	@Autowired
	private CodeService codeService;
	
	@ResponseBody
	@RequestMapping("getCodeByMainKey")
	public TeeJson getCodeByMainKey(String mainKey, HttpServletRequest request) {
		TeeJson teeJson = new TeeJson();
		
		List<CodeDetail> codeDetails = codeService.getCodeDetails(mainKey);
		teeJson.setRtData(codeDetails);
		teeJson.setRtState(true);
		
		return teeJson;
	}
	
	@ResponseBody
	@RequestMapping("getCodeDetailValue")
	public TeeJson getCodeDetailValue(String mainKey, String detailKey, HttpServletRequest request) {
		TeeJson teeJson = new TeeJson();
		
		List<CodeDetail> codeDetails = codeService.getCodeByDetailKey(mainKey, detailKey);
		teeJson.setRtData(codeDetails);
		teeJson.setRtState(true);
		
		return teeJson;
	}
	
	

}
