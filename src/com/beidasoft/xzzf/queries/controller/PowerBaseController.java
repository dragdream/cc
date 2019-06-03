package com.beidasoft.xzzf.queries.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.queries.bean.PowerBase;
import com.beidasoft.xzzf.queries.bean.PowerBaseGist;
import com.beidasoft.xzzf.queries.model.PowerBaseModel;
import com.beidasoft.xzzf.queries.service.PowerBaseService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/PowerBaseController")
public class PowerBaseController {

	@Autowired
	private PowerBaseService powerService;
	
	@ResponseBody
	@RequestMapping("listByPage")
	public TeeEasyuiDataGridJson test(TeeDataGridModel dataGridModel,
			PowerBaseModel queryModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		// 通过分页获取用户信息数据的list集合
		long total = powerService.getTotal(queryModel);
		//List<PowerBase> powers = powerService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel);
		List<Map> ss = powerService.test(dataGridModel.getFirstResult(), dataGridModel.getRows(), queryModel);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(ss);
		return dataGridJson;
	}
	
	/**
	 * 获取id为xxx的信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();
		PowerBase power = powerService.getById(id);
		PowerBaseModel powerModel = new PowerBaseModel();
		BeanUtils.copyProperties(power, powerModel); // 实体类属性值传给model
		json.setRtData(powerModel);
		json.setRtState(true);
		return json;

	}
}
