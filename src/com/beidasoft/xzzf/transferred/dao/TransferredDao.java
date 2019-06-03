package com.beidasoft.xzzf.transferred.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.transferred.bean.DocTransferred;
import com.beidasoft.xzzf.transferred.model.TransferredModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class TransferredDao extends TeeBaseDao<DocTransferred>{

	public List<DocTransferred> getByPowerList(String id,TeeDataGridModel dataGridModel) {
		String hql = "FROM DocTransferred Where id='" + id + "'";
		List<DocTransferred> check = super.pageFind(hql,dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
		return check;
	}
	/**
	 * 根据分页查找用户信息
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<DocTransferred> listByPage(int firstResult,int rows,TransferredModel transferredModel) {
		String hql ="from DocTransferred where 1=1 ";
		
		if (!TeeUtility.isNullorEmpty(transferredModel.getTransId())) {
			hql+="and transId like '%"+transferredModel.getTransId()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(transferredModel.getPartyType())){
			hql+="and partyType like '%"+transferredModel.getPartyType()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(transferredModel.getPartyName())){
			hql+="and PartyName like '%"+transferredModel.getPartyName()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(transferredModel.getTransferredTransTimeStr())){
			hql+="and transferredTransTimeStr like '%"+transferredModel.getTransferredTransTimeStr()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(transferredModel.getPerformUnit())){
			hql+="and performUnit like '%"+transferredModel.getPerformUnit()+"%'";
		}
		return super.pageFind(hql, firstResult, rows, null);
	}
	
	
	/**
	 * 重载
	 * @return
	 */
	public long getTotal(TransferredModel transferredModel){
		String hql ="select count(id) from DocTransferred where 1=1";
		if(!TeeUtility.isNullorEmpty(transferredModel.getPartyType())){
			hql+="and partyType like '%"+transferredModel.getPartyType()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(transferredModel.getPartyName())){
			hql+="and PartyName like '%"+transferredModel.getPartyName()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(transferredModel.getTransferredTransTimeStr())){
			hql+="and transferredTransTimeStr like '%"+transferredModel.getTransferredTransTimeStr()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(transferredModel.getPerformUnit())){
			hql+="and performUnit like '%"+transferredModel.getPerformUnit()+"%'";
		}
		return super.count(hql, null);
	}
	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	public long getTotal(String id) {
		String hql = "select count(id) from DocTransferred  where Id ='"
				+ id + "'";
		return super.count(hql, null);
	}
	
}
