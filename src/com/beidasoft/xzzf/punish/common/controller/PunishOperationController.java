package com.beidasoft.xzzf.punish.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishOperation;
import com.beidasoft.xzzf.punish.common.model.PunishOperationModel;
import com.beidasoft.xzzf.punish.common.service.PunishOperationService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/punishOperationCtrl")
public class PunishOperationController {
	
	@Autowired
	private PunishOperationService punishOperationService;
	
	
	/**
	 * 保存案件操作记录表(日志)
	 * @param punishOperationModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/savePunishOperation")
	public TeeJson savePunishOperation(PunishOperationModel punishOperationModel, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		//添加操作说明
		punishOperationModel.setOperationContent("查看案件操作记录表！");
		punishOperationModel.setBaseId(TeeStringUtil.getString(request.getParameter("baseId"), ""));
		
		punishOperationService.save(punishOperationModel,request);
		
		json.setRtData(punishOperationModel);
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 分页查询日志记录
	 * @param punishOperationModel
	 * @param dataGridModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPunishOperations")
	public TeeEasyuiDataGridJson getPunishOperations(PunishOperationModel punishOperationModel, 
			TeeDataGridModel dataGridModel) {
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		//获得符合条件的总数
		long total = punishOperationService.getTotal(punishOperationModel);
		
		List<PunishOperationModel> modelList = new ArrayList<>();
		//查询返回日志集合
		List<PunishOperation> punishOperationsList = punishOperationService.getPunishOperations(punishOperationModel, dataGridModel);
		
		for (PunishOperation punishOperation : punishOperationsList) {
			PunishOperationModel punishOperationModelRow = new PunishOperationModel();
			//循环copy
			BeanUtils.copyProperties(punishOperation, punishOperationModelRow);
			if (punishOperation.getOpeartionTime() != null) {
				punishOperationModelRow.setOpeartionTimeStr(TeeDateUtil.format(punishOperation.getOpeartionTime(),
						"yyyy年MM月dd日 HH:mm:ss"));
			}
			
			modelList.add(punishOperationModelRow);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	
}
