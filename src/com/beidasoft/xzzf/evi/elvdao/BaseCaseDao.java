package com.beidasoft.xzzf.evi.elvdao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.evi.bean.CaseBase;
import com.beidasoft.xzzf.evi.model.CaseBaseModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class BaseCaseDao extends TeeBaseDao<CaseBase>{
	/**
	 * 根据分页查找用户信息
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<CaseBase> listByPage(int firstResult,int rows,CaseBaseModel queryModel) {
		String hql ="from CaseBase where 1=1 ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getBaseCode())){
			hql+=" and baseCode like '%"+queryModel.getBaseCode()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getSourceType())){
			hql+=" and sourceType like '%"+queryModel.getSourceType()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getMajorPersonName())){
			hql+=" and majorPersonName like '%"+queryModel.getMajorPersonName()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getMinorPersonName())){
			hql+=" and minorPersonName like '%"+queryModel.getMinorPersonName()+"%'";
		}
		return super.pageFind(hql, firstResult, rows, null);
	}
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(){
		return super.count( "select count(baseId) from CaseBase", null);
	}
	/**
	 * 重载
	 * @return
	 */
	public long getTotal(CaseBaseModel queryModel){
		String hql ="select count(baseId) from CaseBase where 1=1";
		if(!TeeUtility.isNullorEmpty(queryModel.getBaseCode())){
			hql+=" and baseCode like '%"+queryModel.getBaseCode()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getSourceType())){
			hql+=" and sourceType like '%"+queryModel.getSourceType()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getMajorPersonName())){
			hql+=" and majorPersonName like '%"+queryModel.getMajorPersonName()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getMinorPersonName())){
			hql+=" and minorPersonName like '%"+queryModel.getMinorPersonName()+"%'";
		}
		return super.count(hql, null);
	}
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(String id) {
		String hql = "select count(id) from EleEvidenceBase  where baseId ='"
				+ id + "'";
		return super.count(hql, null);
	}
}
