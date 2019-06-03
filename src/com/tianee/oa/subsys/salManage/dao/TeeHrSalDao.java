package com.tianee.oa.subsys.salManage.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.salManage.bean.TeeHrSalData;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;



/**
 * 
 * @author CXT
 *
 */
@Repository("hrSalDataDao")
public class TeeHrSalDao extends TeeBaseDao<TeeHrSalData>{

	public void add(TeeHrSalData book){
		save(book);
	}
	
	/**
	 * 获取人员参数设置 
	 * @param personId
	 * @return
	 */
	public TeeHrSalData getSalData(String personId){
		TeeHrSalData salData = new TeeHrSalData();
		Object[] values = {Integer.parseInt(personId)};
		String hql = "from TeeHrSalData salData where salData.user.uuid = ?"; 
		List<TeeHrSalData> list = executeQuery(hql, values);
		if(list.size()>0){
			salData = list.get(0);
		}
		return salData;
	}
	
	/**
	 * 获取人员参数设置 
	 * @param personId
	 * @return
	 */
	public List<TeeHrSalData> getSalDataList(String personIds){
		List<TeeHrSalData> salDataList = new ArrayList<TeeHrSalData>();
		if(TeeUtility.isNullorEmpty(personIds)){
			return salDataList;
		}
		if(personIds.endsWith(",")){
			personIds = personIds.substring(0, personIds.length() - 1);
		}
		Object[] values = {};
		String hql = "from TeeHrSalData salData where salData.user.uuid in (" +  personIds + ")"; 
		salDataList = executeQuery(hql, values);
		return salDataList;
	}
	
}
