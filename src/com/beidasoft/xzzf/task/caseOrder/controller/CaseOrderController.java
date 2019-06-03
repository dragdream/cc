package com.beidasoft.xzzf.task.caseOrder.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.task.caseOrder.model.CaseOrderModel;
import com.beidasoft.xzzf.task.caseOrder.model.CaseOrderModell;
import com.beidasoft.xzzf.task.caseOrder.service.CaseOrderService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Controller
@RequestMapping("caseOrderController")
public class CaseOrderController {
	@Autowired
	private CaseOrderService caseOrderService ;
	
	@ResponseBody
	@RequestMapping("listByPage")
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,CaseOrderModel queryModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//通过分页显示用户信息
//		long total =  caseOrderService.getTotal(queryModel);
		List<CaseOrderModel> modelList = new ArrayList<>();
//		List<CaseOrder> caseOrder = caseOrderService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel);
//		for(CaseOrder law:caseOrder){
//			CaseOrderModel infoModel = new CaseOrderModel();
//			BeanUtils.copyProperties(law, infoModel);
//			infoModel.setDateDeadlineStr(TeeDateUtil.format(law.getDateDeadline(),"yyyy年MM月dd日"));
//			modelList.add(infoModel);
//		}
//		dataGridJson.setTotal(total);
		CaseOrderModel caseOrderModel = new CaseOrderModel();
		caseOrderModel.setCaseOrderId("123242");
		caseOrderModel.setCaseOrderName("行政处罚");
		caseOrderModel.setCaseOrderSource(1);
		caseOrderModel.setCurrentLink(1);
		caseOrderModel.setDateDeadlineStr("2018-09-09");
		caseOrderModel.setHandleStage(1);
		modelList.add(caseOrderModel);
		long total = modelList.size();
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
	
	@ResponseBody
	@RequestMapping("listPage")
	public TeeEasyuiDataGridJson listPage(TeeDataGridModel dataGridModel,CaseOrderModell queryModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//通过分页显示用户信息
//		long total =  caseOrderService.getTotal(queryModel);
		List<CaseOrderModell> modelList = new ArrayList<>();
//		List<CaseOrder> caseOrder = caseOrderService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel);
//		for(CaseOrder law:caseOrder){
//			CaseOrderModel infoModel = new CaseOrderModel();
//			BeanUtils.copyProperties(law, infoModel);
//			infoModel.setDateDeadlineStr(TeeDateUtil.format(law.getDateDeadline(),"yyyy年MM月dd日"));
//			modelList.add(infoModel);
//		}
//		dataGridJson.setTotal(total);
		CaseOrderModell caseOrderModel = new CaseOrderModell();
		caseOrderModel.setCaseOrderId("123242");
		caseOrderModel.setCaseOrderName("行政处罚");
		caseOrderModel.setCaseOrderSource(1);
		caseOrderModel.setName("李四");;
		caseOrderModel.setDateDeadlineStr("2018-09-09");
		modelList.add(caseOrderModel);
		long total = modelList.size();
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
}
