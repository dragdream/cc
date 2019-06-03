package com.beidasoft.xzzf.inspection.plan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionCondition;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class InspectionConditionDao extends TeeBaseDao<InspectionCondition>{
	/**
	 * 根据检查计划主表id查询
	 * @return
	 */
	public List<InspectionCondition> getListByInsId(String inspectionId){
		return super.find("from InspectionCondition where inspectionId = '"+inspectionId+"'", null);
	}
}
