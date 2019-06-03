package com.beidasoft.xzzf.punish.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.bean.PunishRecord;
import com.beidasoft.xzzf.punish.common.model.PunishRecordModel;
import com.beidasoft.xzzf.punish.common.service.PunishRecordService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/punishRecordCtrl")
public class PunishRecordController {

	@Autowired
	private PunishRecordService punishRecordService;
	
	/**
	 * 分页查询日志记录
	 * @param punishRecordModel
	 * @param dataGridModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getPunishRecordsById")
	public TeeEasyuiDataGridJson getPunishRecordsById(PunishRecordModel punishRecordModel, 
			TeeDataGridModel dataGridModel,  HttpServletRequest request) {
		
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		punishRecordModel.setBaseId(TeeStringUtil.getString(request.getParameter("baseId"), ""));
		punishRecordModel.setConfTacheName("案件借阅");
		//获得符合条件的总数
		long total = punishRecordService.getTotalById(punishRecordModel);
		
		List<PunishRecordModel> modelList = new ArrayList<>();
		//查询返回日志集合
		List<PunishRecord> punishRecordsList = punishRecordService.getPunishRecordsById(punishRecordModel, dataGridModel);
		
		for (PunishRecord punishRecord : punishRecordsList) {
			PunishRecordModel punishRecordModelRow = new PunishRecordModel();
			//循环copy
			BeanUtils.copyProperties(punishRecord, punishRecordModelRow);
			if (punishRecord.getOpeartionTime() != null) {
				punishRecordModelRow.setOpeartionTimeStr(TeeDateUtil.format(punishRecord.getOpeartionTime(),
						"yyyy年MM月dd日 HH:mm:ss"));
			}
			
			modelList.add(punishRecordModelRow);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
}
