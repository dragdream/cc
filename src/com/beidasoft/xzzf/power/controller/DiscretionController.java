package com.beidasoft.xzzf.power.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.power.bean.BaseDiscretion;
import com.beidasoft.xzzf.power.model.DiscretionModel;
import com.beidasoft.xzzf.power.service.DiscretionService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/DiscretionController")
public class DiscretionController {
	@Autowired
	private DiscretionService cretionService;

	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(DiscretionModel cretionModel) {
		TeeJson json = new TeeJson();
		// 创建实例化实体类对象
		BaseDiscretion discretion = new BaseDiscretion();
		// 复制model中的字段到 实体类
		BeanUtils.copyProperties(cretionModel, discretion);
		// 自动生成主键
		discretion.setId(UUID.randomUUID().toString());
		cretionService.save(discretion);
		json.setRtState(true);
		return json;
	}

	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(DiscretionModel cretionModel) {

		TeeJson json = new TeeJson();

		BaseDiscretion discretion = cretionService.getById(cretionModel.getId());
		BeanUtils.copyProperties(cretionModel, discretion);

		cretionService.update(discretion);
		json.setRtState(true);
		return json;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(String id) {
		TeeJson json = new TeeJson();
		cretionService.deleteById(id);
		json.setRtState(true);
		return json;
	}

	/**
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();
		BaseDiscretion discretion = cretionService.getById(id);
		DiscretionModel cretionModel = new DiscretionModel();
		BeanUtils.copyProperties(discretion, cretionModel);
		json.setRtData(cretionModel);
		return json;
	}

	@ResponseBody
	@RequestMapping("/findAllUsers")
	public TeeJson findAllUsers() {
		return null;
	}

	@ResponseBody
	@RequestMapping("/listByPage")
	// 用户信息
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,
			DiscretionModel queryModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		// 通过分页获取用户信息数据的list集合
		long total = cretionService.getTotal(queryModel);
		List<BaseDiscretion> discretions = cretionService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(discretions);

		return dataGridJson;
	}
}
