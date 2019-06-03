package com.beidasoft.xzzf.inspection.code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.inspection.code.bean.BaseCodeDetail;
import com.beidasoft.xzzf.inspection.code.model.BaseCodeDetailModel;
import com.beidasoft.xzzf.inspection.code.service.BaseCodeDetailService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/baseCodeDetailCtrl")
public class BaseCodeDetailController {
	
	@Autowired
	private BaseCodeDetailService baseCodeDetailService;
	
	/**
	 * 查询代码表（根据MainKey）
	 * @param baseCodeDetailModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBaseCodeDetails")
	public TeeJson getBaseCodeDetails(BaseCodeDetailModel baseCodeDetailModel) {
		TeeJson json = new TeeJson();
		List<BaseCodeDetail> baseCodeList = baseCodeDetailService.getBaseCodeDetails(baseCodeDetailModel.getMainKey());
		json.setRtData(baseCodeList);
		json.setRtState(true);
		return json;
	}

}
