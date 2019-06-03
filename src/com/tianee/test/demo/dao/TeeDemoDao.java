package com.tianee.test.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.test.demo.bean.TeeDemo;
import com.tianee.test.demo.model.TeeDemoModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class TeeDemoDao extends TeeBaseDao<TeeDemo>{
	
	/**
	 * 分页并按条件查询
	 * @param demoModel 传入的dto模型
	 * @param gridModel easyui列表传来的分页对象
	 * @return
	 */
	public List<TeeDemo> query(TeeDemoModel demoModel,TeeDataGridModel gridModel){
		String hql = "from TeeDemo where 1=1 ";
		
		//实例化参数集合
		List params = new ArrayList();
		
		//如果用户名不等于空或者空字符串，则加入检索条件中
		if(!TeeUtility.isNullorEmpty(demoModel.getUserName())){
			hql += " and userName = ? ";
			params.add(demoModel.getUserName());
		}
		
		//如果年龄不等于0时，则加入检索条件中
		if(demoModel.getAge()!=0){
			hql += " and age = ? ";
			params.add(demoModel.getAge());
		}
		
		/**
		 * firstResult：第一条记录的索引
		 * pageSize：分页大小
		 */
		List<TeeDemo> demoList = pageFind(hql+" order by sid asc", 
				gridModel.getFirstResult(),
				gridModel.getRows(), 
				params.toArray());
		
		return demoList;
	}
	
	/**
	 * 按照检索条件查询记录总数
	 * @param demoModel 传入的dto模型
	 * @return
	 */
	public long queryCount(TeeDemoModel demoModel){
		String hql = "select count(sid) from TeeDemo where 1=1 ";
		
		//实例化参数集合
		List params = new ArrayList();
		
		//如果用户名不等于空或者空字符串，则加入检索条件中
		if(!TeeUtility.isNullorEmpty(demoModel.getUserName())){
			hql += " and userName = ? ";
			params.add(demoModel.getUserName());
		}
		
		//如果年龄不等于0时，则加入检索条件中
		if(demoModel.getAge()!=0){
			hql += " and age = ? ";
			params.add(demoModel.getAge());
		}
		
		long count = count(hql, params.toArray());
		
		return count;
	}
}
