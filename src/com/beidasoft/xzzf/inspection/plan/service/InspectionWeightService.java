package com.beidasoft.xzzf.inspection.plan.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionWeight;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionWeightDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class InspectionWeightService extends TeeBaseService{
	@Autowired
	InspectionWeightDao inspectionWeightDao;
	/**
	 * 新增或修改权重信息
	 * @param inspectionWeight
	 * @return
	 */
	public TeeJson addOrUpdate(InspectionWeight inspectionWeight){
		TeeJson json = new TeeJson();
		String inspectionId = inspectionWeight.getInspectionId();
		InspectionWeight insWeight = inspectionWeightDao.getObjByInsId(inspectionId);
		//判断表中是否存在该计划下的数据
		if (TeeUtility.isNullorEmpty(insWeight)) {
			//不存在则新增
			inspectionWeight.setId(TeeStringUtil.getString(UUID.randomUUID()));
			inspectionWeightDao.save(inspectionWeight);
			json.setRtState(true);
		} else {
			//存在则修改
			insWeight.setInspectionTotal(inspectionWeight.getInspectionTotal());
			insWeight.setWeightBlue(inspectionWeight.getWeightBlue());
			insWeight.setWeightRed(inspectionWeight.getWeightRed());
			insWeight.setWeightYellow(inspectionWeight.getWeightYellow());
			inspectionWeightDao.update(insWeight);
			json.setRtState(true);
		}
		return json;
	}
	/**
	 * 根据检查计划主表id查询相关权重
	 * @param inspectionId
	 * @return
	 */
	public InspectionWeight getObjByInsId(String inspectionId){
		return inspectionWeightDao.getObjByInsId(inspectionId);
	}
}
