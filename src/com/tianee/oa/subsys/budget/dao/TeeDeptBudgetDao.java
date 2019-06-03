package com.tianee.oa.subsys.budget.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.budget.bean.TeeBudgetReg;
import com.tianee.oa.subsys.budget.bean.TeeDeptBudget;
import com.tianee.oa.subsys.budget.model.TeeDeptBudgetModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("deptBudget")
public class TeeDeptBudgetDao extends TeeBaseDao<TeeDeptBudget> {
	
	@Autowired
	private TeeDeptExcessBudgetDao tdebdao;

	/**
	 * @function: 删除数据
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param ids
	 *            void
	 */
	public void delByIds(String ids) {

		if (!TeeUtility.isNullorEmpty(ids)) {
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}
			String hql = "delete from TeeDeptBudget where uuid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	
	/**
	 * @function: 根据部门id和年份获取该年预算详情
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param model
	 * @return List<TeeDeptBudget>
	 */
	public List<TeeDeptBudget> getObjList(TeeDeptBudgetModel model){
		Object[] values= {model.getDeptId(),model.getYear()};
		String hql = " from TeeDeptBudget where dept.uuid=? and year=?";
		List<TeeDeptBudget> list = executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 根据ids 获取数据
	 * @function: 
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param ids
	 * @return List<TeeDeptBudget>
	 */
	public List<TeeDeptBudget> getObjListByIds(String ids){
		if(TeeUtility.isNullorEmpty(ids)){
			ids = "0";
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		Object[] values= {ids};
		String hql = " from TeeDeptBudget where uuid in(?)";
		List<TeeDeptBudget> list = executeQuery(hql, values);
		return list;
	}
	
	/**
	 * @function: 根据部门id和年份删除数据
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param model void
	 */
	public void deleteObjByDeptIdAndYear(TeeDeptBudgetModel model){
		Object[] values= {model.getDeptId(),model.getYear()};
		String hql = " delete from TeeDeptBudget where dept.uuid=? and year=?";
		deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * @function: 校验是否已存在
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param model
	 * @return List<TeeDeptBudget>
	 */
	public List<TeeDeptBudget> checkExist(TeeDeptBudgetModel model){
		Object[] values= {model.getDeptId(),model.getYear()};
		String hql = "  from TeeDeptBudget where dept.uuid=? and year=?";
		List<TeeDeptBudget> list = executeQuery(hql, values);
		return list;
	}
	
	
	/**
	 * @function: 根据年份、月份获取部门预算金额
	 * @author: wyw
	 * @data: 2014年9月7日
	 * @param deptId
	 * @param year
	 * @return double
	 */
	public double getDeptBudgetCost(int deptId,int year,int month){
		double amount = 0.0;
		double eamount =0.0;
		double aamount=0.0;
		Object[] values= {deptId,String.valueOf(year),String.valueOf((month<10)?("0"+month):(month))};
		String hql = " select amount from TeeDeptBudget where dept.uuid=? and year=? and month=?";
		List list = executeQuery(hql, values);
		List llist=tdebdao.getde(deptId, year, month);
		if(llist!=null&&list.size()!=0){
			for(int i = 0; i < llist.size(); i++){
				eamount+=TeeStringUtil.getDouble(llist.get(i),0);
				
			}
			//System.out.println(eamount);
		}
		if(list!=null && list.size()!=0){
			aamount=TeeStringUtil.getDouble(list.get(0), 0);
			//System.out.println(aamount);
		//	System.out.println(aamount+eamount);
			return aamount+eamount;
		}
		return amount;
	}
	
	
	/**
	 * 获取当年、当月预算已申请的金额
	 * @function: 
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param deptId
	 * @param year
	 * @return double
	 */
	public double getRegBudgetCost(int deptId,int year,int month){
		double amount = 0.0;
		Calendar calendarMin= Calendar.getInstance();
		calendarMin.set(Calendar.YEAR, year);
		calendarMin.set(Calendar.MONTH, month-1);
		calendarMin.set(Calendar.DATE, 1);
		calendarMin.set(Calendar.HOUR_OF_DAY, 0);
		calendarMin.set(Calendar.MINUTE, 0);
		calendarMin.set(Calendar.SECOND, 0);
		
		Calendar calendarMax= Calendar.getInstance();
		calendarMax.set(Calendar.YEAR, year);
		calendarMax.set(Calendar.MONTH, month-1);
		calendarMax.set(Calendar.DATE, calendarMax.getActualMaximum(Calendar.DATE));
		calendarMax.set(Calendar.HOUR_OF_DAY, 23);
		calendarMax.set(Calendar.MINUTE, 59);
		calendarMax.set(Calendar.SECOND, 59);
		/*
		System.out.println(TeeUtility.getDateTimeStr(calendarMin.getTime()));
		System.out.println(TeeUtility.getDateTimeStr(calendarMax.getTime()));
		*/
		Object[] values= {deptId,calendarMin.getTime(),calendarMax.getTime()};
		String hql = " select sum(amount) from TeeBudgetReg where regType=2 and opDept.uuid=? and crTime>=? and crTime<?";
		List list = executeQuery(hql, values);
		if( list != null && list.size()>0){
			amount = TeeStringUtil.getDouble(list.get(0), 0);
		}
		return amount;
	}
	
	
	
	
	
}
