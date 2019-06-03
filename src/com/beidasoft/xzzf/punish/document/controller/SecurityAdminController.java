package com.beidasoft.xzzf.punish.document.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocSecurityAdmin;
import com.beidasoft.xzzf.punish.document.model.CasedealModel;
import com.beidasoft.xzzf.punish.document.service.SecurityAdminService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/securityAdminCtrl")
public class SecurityAdminController {

	@Autowired
	private SecurityAdminService securityAdminService;
	
	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存行政处罚决定书
	 * @param casedealModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson saveDocInfo(CasedealModel casedealModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		DocSecurityAdmin docSecurityAdmin = new DocSecurityAdmin();
		
		BeanUtils.copyProperties(casedealModel, docSecurityAdmin);
		
		
		
		securityAdminService.saveDocInfo(docSecurityAdmin);
		json.setRtData(docSecurityAdmin);
		json.setRtState(true);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson getDocInfo(String pId, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		DocSecurityAdmin docSecurityAdmin = securityAdminService.getDocInfo(pId);
		
		CasedealModel casedealModel = new CasedealModel();
		
		BeanUtils.copyProperties(docSecurityAdmin, casedealModel);
		
		
		json.setRtData(casedealModel);
		json.setRtState(true);
		return json;
		
	}
}
