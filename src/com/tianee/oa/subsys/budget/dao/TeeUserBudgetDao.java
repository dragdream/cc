package com.tianee.oa.subsys.budget.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.budget.bean.TeeUserBudget;
import com.tianee.oa.subsys.budget.bean.TeeUserExcessBudget;
import com.tianee.oa.subsys.budget.model.TeeUserBudgetModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("userBudget")
public class TeeUserBudgetDao extends TeeBaseDao<TeeUserBudget> {
	@Autowired
	private TeeUserExcessBudgetDao userExcessDao;

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
			String hql = "delete from TeeUserBudget where uuid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	
	/**
	 * @function: 根据人员id和年份获取该年预算详情
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param model
	 * @return List<TeeUserBudget>
	 */
	public List<TeeUserBudget> getObjList(TeeUserBudgetModel model){
		Object[] values= {model.getUserId(),model.getYear()};
		String hql = " from TeeUserBudget where user.uuid=? and year=?";
		List<TeeUserBudget> list = executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 根据ids 获取数据
	 * @function: 
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param ids
	 * @return List<TeeUserBudget>
	 */
	public List<TeeUserBudget> getObjListByIds(String ids){
		if(TeeUtility.isNullorEmpty(ids)){
			ids = "0";
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		Object[] values= {ids};
		String hql = " from TeeUserBudget where uuid in(?)";
		List<TeeUserBudget> list = executeQuery(hql, values);
		return list;
	}
	
	/**
	 * @function: 根据人员id和年份删除数据
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param model void
	 */
	public void deleteObjByDeptIdAndYear(TeeUserBudgetModel model){
		Object[] values= {model.getUserId(),model.getYear()};
		String hql = " delete from TeeUserBudget where user.uuid=? and year=?";
		deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * @function: 校验是否已存在
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param model
	 * @return List<TeeUserBudget>
	 */
	public List<TeeUserBudget> checkExist(TeeUserBudgetModel model){
		Object[] values= {model.getUserId(),model.getYear()};
		//System.out.println(values[0]+"  "+values[1]);
		String hql = "  from TeeUserBudget where user.uuid=? and year=?";
		List<TeeUserBudget> list = executeQuery(hql, values);
		return list;
	}
	
	

	/**
	 * @function: 获取个人当月预算申请金额
	 * @author: wyw
	 * @data: 2014年9月7日
	 * @param deptId
	 * @param year
	 * @return double
	 */
	public double getUserBudgetCost(int userId,int year,int month){
		double amount = 0.0;
		double eamount =0.0;
		double aamount=0.0;
		Object[] values= {userId,String.valueOf(year),String.valueOf((month<10)?("0"+month):(month))};
		String hql = " select amount from TeeUserBudget where user.uuid=? and year=? and month=?";
		List list = executeQuery(hql, values);
		//String hhql="select excess_amount from TeeUserExcessBudget teb  where teb.user.uuid=? and year=? and month=?";
		//List llist =executQuery(hhql,values);
		
		List llist=userExcessDao.getue(userId, year, month);
		if(llist!=null&&list.size()!=0){
			for(int i = 0; i < llist.size(); i++){
				eamount+=TeeStringUtil.getDouble(llist.get(i),0);
				
			}
			//System.out.println(eamount);
		}
		if(list!=null && list.size()!=0){
			aamount=TeeStringUtil.getDouble(list.get(0), 0);
			//System.out.println(aamount);
			//System.out.println(aamount+eamount);
			return aamount+eamount;
		}
		return amount;
	}
	
	
	/**
	 * 预算申请金额
	 * @function: 
	 * @author: wyw
	 * @data: 2014年9月6日
	 * @param deptId
	 * @param year
	 * @return double
	 */
	public double getRegBudgetCost(int userId,int year,int month){
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
		Object[] values= {userId,calendarMin.getTime(),calendarMax.getTime()};
		String hql = " select sum(amount) from TeeBudgetReg where regType=1 and opUser.uuid=? and crTime>=? and crTime<?";
		List list = executeQuery(hql, values);
		if( list != null && list.size()>0){
			amount = TeeStringUtil.getDouble(list.get(0), 0);
		}
		
		return amount;
	}
	
	
	
	
}
