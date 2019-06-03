package com.beidasoft.xzzf.inspection.plan.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionCondition;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionConditionDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class InspectionConditionService extends TeeBaseService{
	@Autowired
	InspectionConditionDao inspectionConditionDao;
	
	/**
	 * 保存筛选条件列表
	 * @param inspectionId
	 * @param conditionJson
	 * @return
	 */
	public TeeJson saveConditionList(String inspectionId,String conditionJson){
		TeeJson json = new TeeJson();
		List<Map<String, String>> mapList = TeeJsonUtil.JsonStr2MapList(conditionJson);
		List<InspectionCondition> conditionList = inspectionConditionDao.getListByInsId(inspectionId);
		//判断筛选条件是否存在
		if (!conditionList.isEmpty()) {
			//如果已经存在条件，则删除后重新保存
			for (InspectionCondition inspectionCondition : conditionList) {
				inspectionConditionDao.deleteByObj(inspectionCondition);
			}
		}
		for (Map<String, String> map : mapList) {
			InspectionCondition condition = new InspectionCondition();
			condition.setId(TeeStringUtil.getString(UUID.randomUUID()));
			condition.setConditionType(map.get("type"));
			condition.setConditionProportion(TeeStringUtil.getInteger(map.get("proportion"), 0));
			condition.setConditionCredit(TeeStringUtil.getInteger(map.get("color"), 0));
			condition.setConditionRegion(TeeStringUtil.getInteger(map.get("region"), 0));
			condition.setInspectionId(inspectionId);
			inspectionConditionDao.save(condition);
			json.setRtState(true);
		}
		return json;
	}
	/**
	 * 根据检查计划主表id查询
	 * @return
	 */
	public List<InspectionCondition> getListByInsId(String inspectionId){
		return inspectionConditionDao.getListByInsId(inspectionId);
	}
	
}
