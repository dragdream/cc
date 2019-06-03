package com.beidasoft.xzzf.law.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.law.bean.BaseLaw;
import com.beidasoft.xzzf.law.bean.BaseLawDetail;
import com.beidasoft.xzzf.law.model.BaseLawModel;
import com.beidasoft.xzzf.law.service.BaseLawService;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("/BaseLawController")
public class BaseLawController {
	@Autowired
	private BaseLawService lawService;
	@Autowired
	private TeeAttachmentService attachmentService;

	/**
	 * 获取id为xxx的信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();
		BaseLaw law = lawService.getById(id);
		BaseLawModel lawMadel = new BaseLawModel();
		BeanUtils.copyProperties(law, lawMadel);
		
		if (law.getAnnulTime() != null) {
			lawMadel.setAnnulTimeStr(TeeDateUtil.format(law.getAnnulTime(), "yyyy年MM月dd日"));
		}
		if (law.getImplementation() != null) {
			lawMadel.setImplementationStr(TeeDateUtil.format(law.getImplementation(), "yyyy年MM月dd日"));
		}
		if (law.getPromulgation() != null) {
			lawMadel.setPromulgationStr(TeeDateUtil.format(law.getPromulgation(), "yyyy年MM月dd日"));
		}
		
		json.setRtData(lawMadel);
		return json;
	}
    
	
	/**
	 * 获取getDetailDataGrid List
	 * @param dataGridModel
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDetailDataGrid")
	public TeeEasyuiDataGridJson getDetailDataGrid(
			TeeDataGridModel dataGridModel, BaseLawModel lawModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<BaseLawDetail> DetailList = lawService.getLawListById(
				lawModel.getId(), dataGridModel);
		long total = lawService.getTotal(lawModel.getId()); // 根据传来的条件
		dataGridJson.setRows(DetailList);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}

	

	/**
	 * 查询分页
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByPage")
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,
			BaseLawModel queryModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		// 通过分页获取用户信息数据的list集合
		long total = lawService.getTotal(queryModel);
		List<BaseLaw> laws = lawService.listByPage(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				queryModel);
		List<BaseLawModel> modelList = new ArrayList<BaseLawModel>();
		for (BaseLaw law : laws) {
			BaseLawModel lawModel = new BaseLawModel();
			BeanUtils.copyProperties(law, lawModel);
			modelList.add(lawModel);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}

}
