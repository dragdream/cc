package com.beidasoft.xzzf.inspection.plan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionRegion;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class InspectionRegionDao extends TeeBaseDao<InspectionRegion>{
	/**
	 * 根据检查计划主表id查询
	 * @return
	 */
	public List<InspectionRegion> getListByInsId(String inspectionId){
		return super.find("from InspectionRegion where inspectionId = '"+inspectionId+"'", null);
	}
}
