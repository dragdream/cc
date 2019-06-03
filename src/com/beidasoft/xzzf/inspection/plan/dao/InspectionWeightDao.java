package com.beidasoft.xzzf.inspection.plan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionWeight;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class InspectionWeightDao extends TeeBaseDao<InspectionWeight>{
	/**
	 * 根据检查计划主表id
	 * @param inspectionId
	 * @return
	 */
	public InspectionWeight getObjByInsId(String inspectionId){
		List<InspectionWeight> list = super.find("from InspectionWeight where inspectionId = '"+inspectionId+"'", null);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
