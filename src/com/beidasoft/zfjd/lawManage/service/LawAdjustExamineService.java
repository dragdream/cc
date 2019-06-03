package com.beidasoft.zfjd.lawManage.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.law.bean.TblLawInfo;
import com.beidasoft.zfjd.law.dao.TblLawDao;
import com.beidasoft.zfjd.lawManage.bean.LawAdjustReport;
import com.beidasoft.zfjd.lawManage.dao.LawAdjustReportDao;
import com.beidasoft.zfjd.lawManage.model.LawAdjustReportModel;
import com.tianee.webframe.service.TeeBaseService;


/**
 * 法律法规调整SERVICE类
 */
@Service
public class LawAdjustExamineService extends TeeBaseService {

	@Autowired
	private LawAdjustReportDao lawAdjustReportDao;
	
	@Autowired
	private TblLawDao tblLawDao;
	
	/**
	 * 根据id获取数据
	 *
	 * @param String 
	 * @return LawAdjustReport
	 */
	public LawAdjustReport getById(String id) {
		return lawAdjustReportDao.get(id);
	}

	/**
	 * 保存法律法规调整数据
	 *
	 * @param beanInfo
	 */
	public List<LawAdjustReport> examineListByPage(int firstResult, int rows, LawAdjustReportModel lawAdjustReportModel,
			OrgCtrlInfoModel orgCtrlInfoModel) {
		return lawAdjustReportDao.examineListByPage(firstResult, rows, lawAdjustReportModel, orgCtrlInfoModel);

	}

	/**
	 * 保存法律法规调整数据
	 *
	 * @param beanInfo
	 */
	public long getExamineTotal(LawAdjustReportModel lawAdjustReportModel, OrgCtrlInfoModel orgCtrlInfoModel) {
		return lawAdjustReportDao.getExamineTotal(lawAdjustReportModel, orgCtrlInfoModel);
	}
	
	/**
	 * 更新审核标记
	 *
	 * @param lawAdjustReportModel
	 */
	public void updateExamineById(LawAdjustReportModel lawAdjustReportModel){
		lawAdjustReportDao.updateExamineById(lawAdjustReportModel);
	}
	
	/**
	 * 更新审核标记
	 *
	 * @param lawAdjustReportModel
	 */
	public void updateRealDataByCtrlType(LawAdjustReportModel lawAdjustReportModel){
		//获取申请调整数据
		LawAdjustReport reportLawInfo = lawAdjustReportDao.get(lawAdjustReportModel.getId());
		if("01".equals(reportLawInfo.getControlType())){
			//新法
			TblLawInfo newLaw = new TblLawInfo();
			newLaw.setId(reportLawInfo.getId());
			newLaw.setName(reportLawInfo.getName());
			newLaw.setTimeliness(reportLawInfo.getTimeliness());
			newLaw.setSubmitlawLevel(reportLawInfo.getSubmitlawLevel());
			newLaw.setWord(reportLawInfo.getWord());
			newLaw.setOrgan(reportLawInfo.getOrgan());
			newLaw.setPromulgation(reportLawInfo.getPromulgation());
			newLaw.setImplementation(reportLawInfo.getImplementation());
			newLaw.setRemark(reportLawInfo.getRemark());
			newLaw.setExamine(1);
			newLaw.setIsDelete(0);
			newLaw.setControlType(reportLawInfo.getControlType());
			newLaw.setCreateDate(new Date());
			tblLawDao.save(newLaw);
		}else if("02".equals(reportLawInfo.getControlType())){
			//修订
			//保存新的修订法
			TblLawInfo newLaw = new TblLawInfo();
			newLaw.setId(reportLawInfo.getId());
			newLaw.setName(reportLawInfo.getName());
			newLaw.setTimeliness(reportLawInfo.getTimeliness());
			newLaw.setSubmitlawLevel(reportLawInfo.getSubmitlawLevel());
			newLaw.setWord(reportLawInfo.getWord());
			newLaw.setOrgan(reportLawInfo.getOrgan());
			newLaw.setPromulgation(reportLawInfo.getPromulgation());
			newLaw.setImplementation(reportLawInfo.getImplementation());
			newLaw.setRemark(reportLawInfo.getRemark());
			newLaw.setExamine(1);
			newLaw.setIsDelete(0);
			newLaw.setControlType(reportLawInfo.getControlType());
			newLaw.setCreateDate(new Date());
			tblLawDao.save(newLaw);
			//停用原法律
			Map<String, Object> updateItems = new HashMap<>();
			updateItems.put("isDelete", 1);
			updateItems.put("timeliness", "02");
			tblLawDao.update(updateItems, reportLawInfo.getUpdateLawId());
		}else if("03".equals(reportLawInfo.getControlType())){
			//废止
			Map<String, Object> updateItems = new HashMap<>();
			updateItems.put("isDelete", 1);
			updateItems.put("timeliness", "02");
			tblLawDao.update(updateItems, reportLawInfo.getUpdateLawId());
		}
		//更新审核通过标记
		lawAdjustReportDao.updateExamineById(lawAdjustReportModel);
	}
	
}
