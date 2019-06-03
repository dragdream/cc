package com.beidasoft.xzzf.queries.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.queries.bean.LawBase;
import com.beidasoft.xzzf.queries.model.LawBaseModel;
import com.beidasoft.xzzf.queries.service.LawBaseService;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/lawController")
public class LawController {
	
	@Autowired
	private LawBaseService lawBaseService;
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
		LawBase law = lawBaseService.getById(id);
		LawBaseModel lawMadel = new LawBaseModel();
		BeanUtils.copyProperties(law, lawMadel);
		json.setRtData(lawMadel);
		return json;
	}
	/**
	 * 查询分页
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByPage")
	public TeeEasyuiDataGridJson test(TeeDataGridModel dataGridModel,
			LawBaseModel queryModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		// 通过分页获取用户信息数据的list集合
		long total = lawBaseService.getTotal(queryModel);
//		List<LawBase> laws = lawBaseService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel);
		List<Map> ss = lawBaseService.test(dataGridModel.getFirstResult(), dataGridModel.getRows(), queryModel);
		//System.out.println(ss.size());
		dataGridJson.setTotal(total);
		dataGridJson.setRows(ss);
		
		return dataGridJson;
	}
}
