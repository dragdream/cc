package com.beidasoft.xzzf.discretionaryPower.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.discretionaryPower.bean.discretionaryPower;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class discretionaryPowerDao extends TeeBaseDao<discretionaryPower> {
	@Autowired
	private TeeDeptDao dao;
	
	/**
	 * 分页查询总数
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<discretionaryPower> listByPage(int firstResult,int rows){
	    return	super.pageFindByList("from discretionaryPower", firstResult, rows, null);
	}
	
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(){
	    return	super.count("select count(id) from discretionaryPower ", null);
	}
	
	
	//模糊查询 和上面一样只不过加了几个条件
	
	
	/**
	 * 模糊查询分页数
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @return
	 */
	public List<discretionaryPower> listByPage(int firstResult,int rows,discretionaryPower power){
		
		String hql="from discretionaryPower  where 1=1 ";
		
		if(!TeeUtility.isNullorEmpty(power.getPower())){
			hql+=" and POWER like'%"+power.getPower()+"%' ";
		}
		
		if(!TeeUtility.isNullorEmpty(power.getBreaklow())){
			hql+=" and BREAKLOW like '%"+power.getBreaklow()+"%' ";
		}
		System.out.println(hql);
	    return	super.pageFind(hql, firstResult, rows, null);
	}
	
	/**
	 * 模糊查询总数
	 * @param firstResult
	 * @param rows
	 * @param queryModel
	 * @return
	 */
	public long getTotal(discretionaryPower power){
		String hql="select count(id) from discretionaryPower  where 1=1  ";
		
		if(!TeeUtility.isNullorEmpty(power.getPower())){
			hql+=" and POWER like'%"+power.getPower()+"%'";
		}
		
		if(!TeeUtility.isNullorEmpty(power.getBreaklow())){
			hql+=" and BREAKLOW like '%"+power.getBreaklow()+"%' ";
		}
		System.out.println(hql);
	    return	super.count(hql, null);
	}
}
