package com.beidasoft.xzzf.task.caseAssigned.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.task.caseAssigned.dao.CaseAssignedDao;
import com.beidasoft.xzzf.task.caseOrder.bean.CaseOrder;
import com.beidasoft.xzzf.task.caseOrder.dao.CaseOrderDao;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;



@Service
public class CaseAssignedService extends TeeBaseService{
	@Autowired
	private CaseAssignedDao caseAssignedDao;
	
	@Autowired
	private CaseOrderDao caseOrderDao = new CaseOrderDao();
	
	/**
	 * 返回总记录结果(多表查询)
	 * @return
	 */
	public long getTotal(CaseAppointedInfo queryModel){
		String hql  = "select count(za.taskName) from "
				+ "CaseEnforcePersonInfo zp,CaseAppointedInfo za,CaseSourceInfo zcs,CaseSegment zs,CaseFlow zf "
				+ "where za.baseId = zp.baseId AND zcs.sourceId = za.taskRecId AND zs.segmentSettingBasicId = zf.flowSegmentId AND za.baseId= zs.baseId AND zf.nowFlow = zs.nowFlow AND zs.isFinish = '0' AND zp.personId = '20000000'";
		if(!TeeUtility.isNullorEmpty(queryModel.getTaskName())){
			hql+= " and za.taskName like '%"+queryModel.getTaskName()+"%'";
		}
		if(!("-1".equals(queryModel.getTaskRecId()))){
			hql+=" and za.taskRecId = "+queryModel.getTaskRecId();
		}
		return simpleDaoSupport.countByList(hql, null);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> listByPage(int firstResult,int rows,CaseAppointedInfo queryModel){
		String hql = "select  za.TASK_NAME,za.TASK_REC,ZA.TASK_SEND_TIME,zp.CASE_ID,zcs.SOURCE_NAME,zs.SEGMENT_NAME,zf.FLOW_SEGMENT_NAME,zf.CREATE_TIME from "
				+ "ZF_CASE_ENFORCE_PERSON zp,ZF_TASK_APPOINTED za,ZF_CASE_SOURCE_BASIC zcs,ZF_CASE_SEGMENT zs,ZF_CASE_FLOW zf "
				+ "where za.BASE_ID = ZP.BASE_ID AND zcs.SOURCE_ID = za.TASK_REC_ID AND zs.SEGMENT_SETTING_BASIC_ID = zf.FLOW_SEGMENT_ID AND za.BASE_ID= zs.BASE_ID AND zf.NOW_FLOW = zs.NOW_FLOW AND zs.IS_FINISH = '0' AND ZP.PERSON_ID = '20000000'";
		
		
		if(!TeeUtility.isNullorEmpty(queryModel.getTaskName())){
			hql+= " and za.TASK_NAME like '%"+queryModel.getTaskName()+"%'";
		}
		if(!("-1".equals(queryModel.getTaskRecId()))){
			hql+=" and za.TASK_REC_ID = "+queryModel.getTaskRecId();
		}
		@SuppressWarnings("unchecked")
		List<Map> dataList = simpleDaoSupport.executeNativeQuery(hql, null, firstResult, rows);
		return dataList;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getCaseSegment(int caseId) {
				String sql = "select za.taskName,za.taskRec from CaseSegment zs,CaseAppointedInfo za where za.baseId = zs.baseId AND zs.caseId ="+caseId;
				List<CaseAppointedInfo> ss = simpleDaoSupport.find(sql, null);
		 return ss;
	}



}
