package com.tianee.oa.core.system.resource.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.system.resource.service.TeeResourceService;
import com.tianee.webframe.httpmodel.TeeJson;

@RequestMapping("/resourceManage")
public class TeeResourceController {
	@Autowired
	TeeResourceService resourceService;

	/**
	 * 获取资源信息
	 * @author syl
	 * @date 2013-11-18
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getResource")
	@ResponseBody
	public TeeJson getResource(HttpServletRequest request) throws Exception {
		TeeJson json =  resourceService.getResource(request);
		return json;
	}

	public void setResourceService(TeeResourceService resourceService) {
		this.resourceService = resourceService;
	}

}
