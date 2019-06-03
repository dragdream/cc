package com.beidasoft.xzzf.punish.common.dao;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.punish.common.bean.ConfFlow;
import com.beidasoft.xzzf.punish.common.model.ConfFlowModel;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class ConfFlowDao  extends TeeBaseDao<ConfFlow> {
	
	/**
	 * 获取环节流程信息
	 * 
	 * @param tacheCode
	 * @return
	 */
	public List<ConfFlow> getConfFlowInfo(ConfFlowModel confFlowModel) {
		String hql = "from ConfFlow where 1=1 ";
		if (StringUtils.isNotBlank(confFlowModel.getId())) {
			hql += " and id = '" + confFlowModel.getId() + "'";
		}
		if (confFlowModel.getConfFlowId() != 0) {
			hql += " and confFlowId = " + confFlowModel.getConfFlowId();
		}
		if (StringUtils.isNotBlank(confFlowModel.getConfFlowName())) {
			hql += " and confFlowName like '%" + confFlowModel.getConfFlowName() + "%'";
		}
		if (StringUtils.isNotBlank(confFlowModel.getConfTacheCode())) {
			hql += " and confTacheCode = '" + confFlowModel.getConfTacheCode() + "'";
		}
		if (StringUtils.isNotBlank(confFlowModel.getConfTacheName())) {
			hql += " and confTacheName like '%" + confFlowModel.getConfTacheName() + "%'";
		}
		if (confFlowModel.getIsStop() != -1) {
			hql += " and isStop = " + confFlowModel.getIsStop();
		}
		hql += " and isDelete = " + confFlowModel.getIsDelete();
		if (StringUtils.isNotBlank(confFlowModel.getCreateTimeStr())) {
			hql += " and createTime = '" + confFlowModel.getCreateTimeStr() + "'";
		}
		if (StringUtils.isNotBlank(confFlowModel.getConfDocId())) {
			hql += " and confDocId = '" + confFlowModel.getConfDocId() + "'";
		}
		hql += " order by confFlowIndex ";
		List<ConfFlow> tacheFlowList = executeQuery(hql, null);
		return tacheFlowList;
	}
	
}