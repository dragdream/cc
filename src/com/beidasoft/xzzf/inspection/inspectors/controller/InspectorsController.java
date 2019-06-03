package com.beidasoft.xzzf.inspection.inspectors.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.inspection.code.service.BaseCodeDetailService;
import com.beidasoft.xzzf.inspection.inspectors.bean.Inspectors;
import com.beidasoft.xzzf.inspection.inspectors.model.InspectorsModel;
import com.beidasoft.xzzf.inspection.inspectors.service.InspectorsService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/inspectorsCtrl")
public class InspectorsController {
	
	@Autowired
	private InspectorsService inspectorsService;
	
	@Autowired
	private BaseCodeDetailService codeDetailService;
	
	/**
	 * 条件分页查询检查人员
	 * @param inspectorsModel
	 * @param dataGridModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInspectors")
	public TeeEasyuiDataGridJson getInspectors(InspectorsModel inspectorsModel, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		//通过分页获取检查人员信息数据的List集合
		long total = inspectorsService.getTotal(inspectorsModel);
		
		List<InspectorsModel> modelList = new ArrayList<>();
		List<Inspectors> inspectorsList = inspectorsService.getInspectors(inspectorsModel, dataGridModel);
		//获取代码表map
		Map<String, String> map = codeDetailService.getCodeMap("ZF_INSPECTION_ORG_TYPE");
		for (Inspectors inspectors : inspectorsList) {
			InspectorsModel inspectorsModelRow = new InspectorsModel();
			
			BeanUtils.copyProperties(inspectors, inspectorsModelRow);
			//通过key获取map中的value
			inspectorsModelRow.setDepartmentInspectType(map.get(inspectorsModelRow.getDepartmentInspectType()));
			
			modelList.add(inspectorsModelRow);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	/**
	 * 保存检查人员
	 * @param inspectorsModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(InspectorsModel inspectorsModel, HttpServletRequest request) {
		
		//将获取的字符串分割成数组
		String[] staffIdStr = TeeStringUtil.getString(request.getParameter("staffIdStr"), "").split(",");
		String[] staffNameStr = TeeStringUtil.getString(request.getParameter("staffNameStr"), "").split(",");
		
		List<Inspectors> inspectorsList = new ArrayList<Inspectors>();
		//获取代码表map
		Map<String, String> map = codeDetailService.getCodeMap("ZF_CLUE_DEPT");
		for(int i = 0; i < staffIdStr.length; i++) {
			Inspectors inspectors = new Inspectors();
			BeanUtils.copyProperties(inspectorsModel, inspectors);
			//单独处理以下数据
			inspectors.setStaffId(TeeStringUtil.getInteger(staffIdStr[i], 0));
			inspectors.setStaffName(staffNameStr[i]);
			inspectors.setDepartmentId(TeeStringUtil.getInteger(inspectorsModel.getDepartmentName(), 0));
			inspectors.setDepartmentName(map.get(inspectorsModel.getDepartmentName()));
			
			inspectorsList.add(inspectors);
		}
		
		return inspectorsService.saveInspectors(inspectorsList);
	}
	
	/**
	 * 修改检查人员
	 * @param inspectorsModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(InspectorsModel inspectorsModel) {
		TeeJson json = new TeeJson();
		
		Inspectors inspectors = inspectorsService.getById(inspectorsModel.getStaffId());
		
		BeanUtils.copyProperties(inspectorsModel, inspectors);
		
		inspectorsService.update(inspectors);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据id查询检查人员
	 * @param staffId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(int staffId) {
		TeeJson json = new TeeJson();
		
		Inspectors inspectors = inspectorsService.getById(staffId);
		InspectorsModel inspectorsModel = new InspectorsModel();
		
		BeanUtils.copyProperties(inspectors, inspectorsModel);
		
		json.setRtData(inspectorsModel);
		return json;
	}
	
	/**
	 * 删除检查人员
	 * @param staffId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(int staffId) {
		TeeJson json = new TeeJson();
		inspectorsService.deleteById(staffId);
		
		json.setRtState(true);
		return json;
				
	}
}
