package com.beidasoft.xzzf.punish.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.PunishRecord;
import com.beidasoft.xzzf.punish.common.model.PunishRecordModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class PunishRecordDao extends TeeBaseDao<PunishRecord> {
	/**
	 * 根据Id与环节名查询
	 * 
	 * @param PunishRecordModel
	 * @param dataGridModel
	 * @return
	 */
	public List<PunishRecord> getPunishRecordsById(PunishRecordModel punishRecordModel, 
			TeeDataGridModel dataGridModel) {
		
		String hql = "from PunishRecord where 1=1 ";
		if (!TeeUtility.isNullorEmpty(punishRecordModel.getOpeartionPersonName())) {
			hql += " and opeartionPersonName like '%" + punishRecordModel.getOpeartionPersonName() + "%'";
		}
		if (!TeeUtility.isNullorEmpty(punishRecordModel.getBaseId())) {
			hql += " and baseId = '" + punishRecordModel.getBaseId() + "'";
		}
		if (!TeeUtility.isNullorEmpty(punishRecordModel.getConfTacheName())) {
			hql += " and confTacheName = '" + punishRecordModel.getConfTacheName() + "'";
		}
		
		List<PunishRecord> punishRecords = super.pageFind(hql, 
				dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
		
		return punishRecords;
	}
	
	/**
	 * 根据Id与环节名查询符合的总记录数
	 * @param PunishRecordModel
	 * @return
	 */
	public long getTotalById(PunishRecordModel punishRecordModel) {
		String hql = "select count(punishRecordId) from PunishRecord Where 1=1 ";
		if (!TeeUtility.isNullorEmpty(punishRecordModel.getOpeartionPersonName())) {
			hql += " and opeartionPersonName like '%" + punishRecordModel.getOpeartionPersonName() + "%'";
		}
		if (!TeeUtility.isNullorEmpty(punishRecordModel.getBaseId())) {
			hql += " and baseId = '" + punishRecordModel.getBaseId() + "'";
		}
		if (!TeeUtility.isNullorEmpty(punishRecordModel.getConfTacheName())) {
			hql += " and confTacheName = '" + punishRecordModel.getConfTacheName() + "'";
		}
		return super.count(hql, null);
	}
}
