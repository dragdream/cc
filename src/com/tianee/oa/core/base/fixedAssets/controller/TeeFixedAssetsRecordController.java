package com.tianee.oa.core.base.fixedAssets.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsInfoModel;
import com.tianee.oa.core.base.fixedAssets.model.TeeFixedAssetsRecordModel;
import com.tianee.oa.core.base.fixedAssets.service.TeeFixedAssetsInfoService;
import com.tianee.oa.core.base.fixedAssets.service.TeeFixedAssetsRecordService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("teeFixedAssetsRecordController")
public class TeeFixedAssetsRecordController {
	
	@Autowired
	private TeeFixedAssetsRecordService assetsRecordService;
	
	@Autowired
	private TeeFixedAssetsInfoService assetsInfoService;

	
	/*
	 * 新增或者更新
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addAssetsInfo(HttpServletRequest request ) throws Exception{
		TeeFixedAssetsRecordModel model = new TeeFixedAssetsRecordModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		TeeJson json = assetsRecordService.addOrUpdate(request, model);
		return json;
	}
	
	/**
	 * byId 
	 * @author syl
	 * @date 2014-6-7
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request ) throws Exception{
		TeeJson json = assetsRecordService.getById(request);
		return json;
	}
	
	
	/**
	 * 根据固定资产Id  获取领用和 返库清单
	 * @author syl
	 * @date 2014-6-7
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectByAssetsId")
	@ResponseBody
	public TeeJson selectByAssetsId(HttpServletRequest request ) throws Exception{
		TeeFixedAssetsRecordModel model = new TeeFixedAssetsRecordModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		TeeJson json = assetsRecordService.selectByAssetsId(request , model);
		return json;
	}
	
	/**
	 * 获取固定资产折旧记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectDeprecRecordsByAssetsId")
	@ResponseBody
	public TeeJson selectDeprecRecordsByAssetsId(HttpServletRequest request ) throws Exception{
		int assetId = TeeStringUtil.getInteger(request.getParameter("assetId"), 0);
		TeeJson json = new TeeJson();
		json.setRtData(assetsRecordService.selectDeprecRecordsByAssetsId(assetId));
		return json;
	}
	
	
	/**
	 * 获取固定资产 ---  在库的
	 * @author syl
	 * @date 2014-6-8
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAssetsDatagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson getAssetsDatagrid(TeeDataGridModel dm,HttpServletRequest request  ) {
		TeeFixedAssetsInfoModel model = new TeeFixedAssetsInfoModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return assetsInfoService.getAssetsDatagrid(request , dm, model);
	}
	
	/**
	 * 获取明细记录
	 * @author syl
	 * @date 2014-6-8
	 * @param dm
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRecordDatagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson getRecordDatagrid(TeeDataGridModel dm,HttpServletRequest request  ) {
		TeeFixedAssetsInfoModel model = new TeeFixedAssetsInfoModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return assetsRecordService.assetsRecordService(request , dm, model);
	}
	
	/**
	 * 维修确认
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/repairConfirm")
	@ResponseBody
	public TeeJson repairConfirm(HttpServletRequest request ) throws Exception{
		int recordId = TeeStringUtil.getInteger(request.getParameter("recordId"), 0);
		TeeJson json = new TeeJson();
		assetsRecordService.repairConfirm(recordId);
		return json;
	}
	
}
