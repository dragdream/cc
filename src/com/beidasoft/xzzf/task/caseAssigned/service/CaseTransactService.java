package com.beidasoft.xzzf.task.caseAssigned.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.model.PunishBaseModel;
import com.beidasoft.xzzf.task.caseAssigned.dao.CaseAssignedDao;
import com.beidasoft.xzzf.task.caseAssigned.dao.CaseTransactDao;
import com.beidasoft.xzzf.task.caseOrder.bean.CaseOrder;
import com.beidasoft.xzzf.task.caseOrder.dao.CaseOrderDao;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.service.TeeBaseService;



@Service
public class CaseTransactService extends TeeBaseService{
	@Autowired
	private CaseAssignedDao caseAssignedDao;
	
	@Autowired
	private CaseOrderDao caseOrderDao;
	
	@Autowired
	private CaseTransactDao caseTransactDao;
	
	/**
	 * 案件办理返回总记录结果(多表查询)
	 * @return
	 */
	public long getTotal(PunishBaseModel queryModel, TeePerson loginUser) {
		return caseTransactDao.getTotal(queryModel, loginUser);
	}
	
	/**
	 * 根据userId查看自己的代办案件（案件办理）
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @param userId
	 * @return
	 */
	public List<PunishBase> listByPageuserId(int firstResult,int rows,PunishBaseModel queryModel, TeePerson loginUser){
		return caseTransactDao.listByPageuserId(firstResult, rows, queryModel, loginUser);
	}
	
	/**
	 * 现场检查返回总记录结果(多表查询)
	 * @return
	 */
	public long getCheckTotal(PunishBaseModel queryModel, TeePerson loginUser) {
		return caseTransactDao.getCheckTotal(queryModel, loginUser);
	}
	
	/**
	 * 根据userId查看自己的代办案件（现场检查）
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @param userId
	 * @return
	 */
	public List<PunishBase> listByCheckPage(int firstResult,int rows,PunishBaseModel queryModel, TeePerson loginUser){
		return caseTransactDao.listByCheckPage(firstResult, rows, queryModel, loginUser);
	}
	
	/**
	 * 现场检查返回总记录结果(听证)
	 * @return
	 */
	public long getHearingTotal(PunishBaseModel queryModel, TeePerson loginUser) {
		return caseTransactDao.getHearingInfoTotal(queryModel, loginUser);
	}
	
	/**
	 * 根据userId查看自己的代办案件（听证）
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @param userId
	 * @return
	 */
	public List<PunishBase> getHearingInfo(int firstResult,int rows,PunishBaseModel queryModel, TeePerson loginUser){
		return caseTransactDao.getHearingInfo(firstResult, rows, queryModel, loginUser);
	}
	
	/**
	 * 修改用户信息
	 * @param userInfo
	 */
	public void update(CaseOrder caseOrder){
		caseOrderDao.update(caseOrder);
	}
	/**
	 * 通过id删除用户
	 * @param sid
	 */
	public void deleteById(int id){
		caseOrderDao.delete(id);
	}
	/**
	 * 通过id查找用户
	 * @param sid
	 * @return
	 */
	public CaseOrder getById(int id){
		return caseOrderDao.get(id);
	}

	public Object getCaseSegment(int caseId) {
		String sql = "select za.taskName,za.taskRec from CaseSegment zs,CaseAppointedInfo za where za.baseId = zs.baseId AND zs.caseId =" + caseId;
		List<CaseAppointedInfo> infoList = simpleDaoSupport.find(sql, null);
		return infoList;
	}
}
