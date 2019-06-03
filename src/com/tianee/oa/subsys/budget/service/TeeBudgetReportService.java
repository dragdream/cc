package com.tianee.oa.subsys.budget.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeBudgetReportService extends TeeBaseService {

	public List<Map> getPersonalBudget(int year) {
		try{
			String queryStr = " 1=1";
			
			if (!TeeUtility.isNullorEmpty(year)) {
				queryStr += " and bu2.year = " + year;
			}
			
			String sql = "select sum(bu2.amount) as AMOUNT,"
					+ "bu2.user_id as USERID,"
					+ "(select p.user_name from person p where p.uuid=bu2.user_id) as USERNAME,"
					+ "bu2.year as YEAR "
					+ "from budget_user bu2 "
					+ " group by bu2.user_Id,bu2.year having " + queryStr ;
			
			String startDeteDesc = year+"-01-01 00:00:00";
			String endDateDesc = year+"-12-31 23:59:59";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Map> dataList = simpleDaoSupport.executeNativeQuery(sql, null, 0, Integer.MAX_VALUE);
			if(null!=dataList && dataList.size()>0){
				for(int i=0;i<dataList.size();i++){
					String sql2 = "select sum(reg.amount) as COSTAMOUNT from budget_reg reg where reg.oper_user_id = ?";
					sql2+=" and reg.cr_time>=? and reg.cr_time<=?";
					Map<String,Double> map = simpleDaoSupport.executeNativeUnique(sql2, new Object[]{dataList.get(i).get("USERID"),sdf.parse(startDeteDesc),sdf.parse(endDateDesc)});
					if(!TeeUtility.isNullorEmpty(map.get("COSTAMOUNT"))){
						dataList.get(i).put("COSTAMOUNT", map.get("COSTAMOUNT"));
					}else{
						dataList.get(i).put("COSTAMOUNT", 0.0);
					}
				}
			}
			return dataList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Map> getDeptBudget(int year) {
		try{
			String queryStr = " 1=1";
			if (!TeeUtility.isNullorEmpty(year)) {
				queryStr += " and bu2.year = " + year;
			}
			String sql = "select sum(bu2.amount) as AMOUNT,"
					+ "bu2.dept_id as DEPTID,"
					+ "(select dept.dept_Name from department dept where dept.uuid=bu2.dept_id) as DEPTNAME,"
					+ "bu2.year as YEAR "
					+ "from budget_dept bu2 "
					+ " group by bu2.dept_id,bu2.year having " + queryStr ;
			
			String startDeteDesc = year+"-01-01 00:00:00";
			String endDateDesc = year+"-12-31 23:59:59";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Map> dataList = simpleDaoSupport.executeNativeQuery(sql, null, 0, Integer.MAX_VALUE);
			if(null!=dataList && dataList.size()>0){
				for(int i=0;i<dataList.size();i++){
					String sql2 = "select sum(reg.amount) as COSTAMOUNT from budget_reg reg where reg.op_dept_id = ?";
					sql2+=" and reg.cr_time>=? and reg.cr_time<=?";
					Map<String,Double> map = simpleDaoSupport.executeNativeUnique(sql2, new Object[]{dataList.get(i).get("DEPTID"),sdf.parse(startDeteDesc),sdf.parse(endDateDesc)});
					if(!TeeUtility.isNullorEmpty(map.get("COSTAMOUNT"))){
						dataList.get(i).put("COSTAMOUNT", map.get("COSTAMOUNT"));
					}else{
						dataList.get(i).put("COSTAMOUNT", 0.0);
					}
				}
			}
			return dataList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
