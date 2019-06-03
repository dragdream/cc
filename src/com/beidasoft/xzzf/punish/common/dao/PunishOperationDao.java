package com.beidasoft.xzzf.punish.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.PunishOperation;
import com.beidasoft.xzzf.punish.common.model.PunishOperationModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class PunishOperationDao extends TeeBaseDao<PunishOperation> {

	/**
	 * 分页查询日志表
	 * @param punishOperationModel
	 * @param dataGridModel
	 * @return
	 */
	public List<PunishOperation> getPunishOperations(PunishOperationModel punishOperationModel, 
			TeeDataGridModel dataGridModel) {
		
		String hql = "from PunishOperation where 1=1 ";
		if (!TeeUtility.isNullorEmpty(punishOperationModel.getOpeartionPersonName())) {
			hql += " and opeartionPersonName like '%" + punishOperationModel.getOpeartionPersonName() + "%'";
		}
		if (!TeeUtility.isNullorEmpty(punishOperationModel.getConfTacheName())) {
			hql += " and confTacheName like '%" + punishOperationModel.getConfTacheName() + "%'";
		}
		
		List<PunishOperation> punishOperations = super.pageFind(hql, 
				dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
		
		return punishOperations;
	}
	
	
	
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal() {
		return super.count("select count(punishOperationId) from PunishOperation", null);
	}
	
	/**
	 * 返回条件符合的总记录数
	 * @param punishOperationModel
	 * @return
	 */
	public long getTotal(PunishOperationModel punishOperationModel) {
		String hql = "select count(punishOperationId) from PunishOperation Where 1=1 ";
		if (!TeeUtility.isNullorEmpty(punishOperationModel.getOpeartionPersonName())) {
			hql += " and opeartionPersonName like '%" + punishOperationModel.getOpeartionPersonName() + "%'";
		}
		if (!TeeUtility.isNullorEmpty(punishOperationModel.getConfTacheName())) {
			hql += " and confTacheName like '%" + punishOperationModel.getConfTacheName() + "%'";
		}
		return super.count(hql, null);
	}
	
	
}
