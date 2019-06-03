package com.beidasoft.xzzf.evi.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.evi.bean.CaseBase;
import com.beidasoft.xzzf.evi.model.CaseBaseModel;
import com.beidasoft.xzzf.evi.service.BaseCaseService;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/BaseCaseController")
public class BaseCaseController {
	@Autowired
	private BaseCaseService  caseService;
	@Autowired
	private  TeeAttachmentService attachmentService;
	
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(String baseId){
		TeeJson json=new TeeJson();
		CaseBase base=caseService.getById(baseId);
		CaseBaseModel baseModel= new CaseBaseModel();
		BeanUtils.copyProperties(base, baseModel);
		json.setRtData(baseModel);
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
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,
			CaseBaseModel queryModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		// 通过分页获取用户信息数据的list集合
		long total = caseService.getTotal(queryModel);
		List<CaseBase> bases = caseService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(bases);
		return dataGridJson;
	}
}
