package com.beidasoft.xzzf.lawCheck.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.lawCheck.bean.BaseLawCheck;
import com.beidasoft.xzzf.lawCheck.model.LawCheckModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class LawCheckDao extends TeeBaseDao<BaseLawCheck> {
	/**
	 * 根据分页查找用户信息
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<BaseLawCheck> listByPage(int firstResult,int rows,LawCheckModel queryModel) {
		String hql ="from BaseLawCheck where 1=1 ";
		
		if(!TeeUtility.isNullorEmpty(queryModel.getModuleName())){
			hql+="and moduleName like '%"+queryModel.getModuleName()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getModuleType())){
			hql+="and moduleType like '%"+queryModel.getModuleType()+"%'";
		}
		return super.pageFind(hql, firstResult, rows, null);

	}
	
	
//	/**
//	 * 返回总记录数
//	 * @return
//	 */
//	public long getTotal(){
//		return super.count( "select count(id) from BaseLawCheck", null);
//	}
	/**
	 * 重载
	 * @return
	 */
	public long getTotal(LawCheckModel queryModel){
		String hql ="select count(id) from BaseLawCheck where 1=1";
		if(!TeeUtility.isNullorEmpty(queryModel.getModuleName())){
			hql+="and moduleName like '%"+queryModel.getModuleName()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getModuleType())){
			hql+="and moduleType like '%"+queryModel.getModuleType()+"%'";
		}
		return super.count(hql, null);
	}
//	/**
//	 * 返回总记录数
//	 * 
//	 * @return
//	 */
//	public long getTotal(String id) {
//		String hql = "select count(id) from LawCheckItem  where moduleId ='"
//				+ id + "'";
//		return super.count(hql, null);
//	}
}
