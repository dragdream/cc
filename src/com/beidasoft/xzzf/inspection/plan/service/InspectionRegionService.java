package com.beidasoft.xzzf.inspection.plan.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionCondition;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionRegion;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionRegionDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class InspectionRegionService extends TeeBaseService{
	@Autowired
	InspectionRegionDao inspectionRegionDao;
	/**
	 * 根据检查计划主表id查询
	 * @return
	 */
	public List<InspectionRegion> getListByInsId(String inspectionId){
		return inspectionRegionDao.getListByInsId(inspectionId);
	}
	/**
	 * 保存筛选条件列表
	 * @param inspectionId
	 * @param conditionJson
	 * @return
	 */
	public TeeJson saveRegionList(String inspectionId,String regionJson){
		TeeJson json = new TeeJson();
		List<Map<String, String>> mapList = TeeJsonUtil.JsonStr2MapList(regionJson);
		List<InspectionRegion> regionList = inspectionRegionDao.getListByInsId(inspectionId);
		//判断地区权重是否存在
		if (!regionList.isEmpty()) {
			//如果已经存在条件，则删除后重新保存
			for (InspectionRegion inspectionRegion : regionList) {
				inspectionRegionDao.deleteByObj(inspectionRegion);
			}
		}
		for (Map<String, String> map : mapList) {
			InspectionRegion region = new InspectionRegion();
			region.setId(TeeStringUtil.getString(UUID.randomUUID()));
			region.setInspectionId(inspectionId);
			region.setRegionCode(TeeStringUtil.getInteger(map.get("area"), 0));
			region.setRegionWeight(TeeStringUtil.getInteger(map.get("areaProportion"),0));
			inspectionRegionDao.save(region);
			json.setRtState(true);
		}
		return json;
	}
}
