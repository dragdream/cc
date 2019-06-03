package com.beidasoft.xzfy.homePage.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.utils.StringUtils;
import com.tianee.webframe.dao.TeeBaseDao;

/**
 * 案件统计相关的dao层，查询自定义的sql
 * @author Henry
 *
 */
@SuppressWarnings("rawtypes")
@Repository
public class CaseSummaryDao extends TeeBaseDao{
	//执行自定义sql，获取结果列表
	public <T> List<T> list(String sql,Class<T> clazz,Object...parms){
		Session session = this.getSession();
		Query query = session.createSQLQuery(sql);
		
		//sql绑定变量
		int idx=0;
		for(Object parm:parms){
			query.setParameter(idx ++, parm);
		}
		
		//获取sql结果
		List result = query.list();
		if(result.size() == 0){
			System.out.println("sql has no rows !");
			System.out.println(sql);
			return null;
		}
		
		//将sql result转为model对象
		List<T> data = new ArrayList<T>();
		for(Object obj:result){
			if(! obj.getClass().isArray()){
				System.out.println("obj is not array !");
				return null;//无法转为对象，则直接返回
			}
			T mod = StringUtils.arrayToObject4Stat((Object[])obj, clazz) ;
			data.add(mod);
		}
		
		return data;
	}
	
}
