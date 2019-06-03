package com.beidasoft.xzzf.punish.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.bean.PunishTache;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.dao.PunishTacheDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class PunishFlowService extends TeeBaseService {

	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private PunishTacheDao tachedao; 
	// 保存
	public void save(PunishFLow o) {
		punishFlowDao.save(o);
	}
    //删除
	public void del(int id) {
		punishFlowDao.delete(id);
	}
    //更新
	public void update(PunishFLow o) {
		punishFlowDao.update(o);
	}
    //通过runId查询
	public PunishFLow getByRunId(String runId) {
		return punishFlowDao.getByRunId(runId);
	}
	//通过UUID查询
	public PunishFLow getById(String id) {
		return punishFlowDao.get(id);
	}
    //查询全部
	public List<PunishFLow> getPunishFLowInfo() {
		return null;
	}

	//流程信息
	public List<PunishFLow> getFlowcase(String baseId,String confFlowName, String lawLinkId) {
		return punishFlowDao.getFlowcase(baseId, confFlowName, lawLinkId);
	}
	//环节信息
	public List<PunishTache> getFlow(String baseId,String punishTacheId) {
		return tachedao.getFlow(baseId,punishTacheId);
	}
	
	//根据baseId, flowId流程信息
	public List<PunishFLow> getFlowcase(String baseId,int flowId) {
		return punishFlowDao.getFlowcase(baseId, flowId);
	}
	
	public List<PunishFLow> getFlowByTacheId(String baseId, String tacheId, int userId) {
		return punishFlowDao.getFlowByTacheId(baseId, tacheId, userId);
	}
	
	public List<PunishFLow> getFlowAssign(String baseId, String tacheId, int userId) {
		return punishFlowDao.getFlowAssign(baseId, tacheId, userId);
	}
	
	public List<PunishFLow> getByBaseId(String baseId) {
		return punishFlowDao.getByBaseId(baseId);
	}
	
	/**
	 * 通过runId 和 文书名查流程信息
	 * 
	 * @param runId
	 * @param flowName
	 * @return
	 */
	public PunishFLow getFlowByRunIdOrFlowName(String runId, String flowName) {
		return punishFlowDao.getFlowByRunIdOrFlowName(runId, flowName);
	}
	
	
	public List<PunishFLow> getListByRunId(String runId) {
		return punishFlowDao.getListByRunId(runId);
	}
}
