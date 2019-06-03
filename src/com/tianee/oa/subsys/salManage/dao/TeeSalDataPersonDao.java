package com.tianee.oa.subsys.salManage.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomerInfo;
import com.tianee.oa.subsys.salManage.bean.TeeHrSalData;
import com.tianee.oa.subsys.salManage.bean.TeeSalDataPerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author CXT
 *
 */
@Repository("salDataPersonDao")
public class TeeSalDataPersonDao extends TeeBaseDao<TeeSalDataPerson>{

	public void add(TeeSalDataPerson book){
		save(book);
	}
	
	/**
	 * 根据人员 Id 获取工资
	 * @param personId
	 * @return
	 */
	public List<TeeSalDataPerson> getSalData(int personId){
		Object[] values = {personId};
		String hql = "from TeeSalDataPerson salData where salData.user.uuid = ?"; 
		List<TeeSalDataPerson> list = executeQuery(hql, values);
		return list;
	}
	/**
	 * 根据 年份  + 月份 获取工资
	 * @param year
	 * @param month 
	 * @return
	 */
	public List<TeeSalDataPerson> getSalData( int year , int month){
		Object[] values = { year , month};
		String hql = "from TeeSalDataPerson salData where  salYear = ? and salMonth = ?"; 
		List<TeeSalDataPerson> list = executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 根据人员 Id + 年份  + 月份 获取工资
	 * @param personId
	 * @param year
	 * @param month 
	 * @return
	 */
	public List<TeeSalDataPerson> getSalData(int personId , int year , int month){
		Object[] values = {personId , year , month};
		String hql = "from TeeSalDataPerson salData where salData.user.uuid = ? and salYear = ? and salMonth = ?"; 
		List<TeeSalDataPerson> list = executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 判断用户 根据年份 + 月份 是否已经存在
	 * @param personId
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean checkUserSalExist(int personId , int year , int month){
		Object[] values = {personId , year , month};
		String hql = "from TeeSalDataPerson salData where salData.user.uuid = ? and salYear = ? and salMonth = ?"; 
		List<TeeSalDataPerson> list = executeQuery(hql, values);
		if(list.size() > 0){
			return true;
		}
		return false;
	}
	
	
	public void updateSalData(TeeSalDataPerson book){
		Session session = this.getSession();
		session.merge(book);
	}
	
	/**
	 * @author nieyi
	 * @return
	 */
	public List<TeeSalDataPerson> getAllSalaryData(String userIds, int salYear, int salMonth,String userName ,int accountId){
		List<TeeSalDataPerson> salDataList = new ArrayList<TeeSalDataPerson>();
		List param = new ArrayList();
		String hql = "from TeeSalDataPerson salData where accountId=" + accountId; 
		if(!TeeUtility.isNullorEmpty(userIds)){
			hql+=" and salData.user.uuid in ("+userIds+")";
		}else{
			return null;
		}
		if(salYear>0){
			hql+=" and salData.salYear=?";
			param.add(salYear);
		}
		if(salMonth>0){
			hql+=" and salData.salMonth=?";
			param.add(salMonth);
		}
		if(!TeeUtility.isNullorEmpty(userName)){
			hql+=" and salData.user.userName like ?";
			param.add("%"+userName+"%");
		}
		salDataList = executeQueryByList(hql, param);
		return salDataList;
	}
	/**
	 * 根据条件查询个人工资情况
	 * @param requestDatas
	 * @return
	 */
	public List<TeeSalDataPerson> querySalaryByCondition(Map requestDatas) {
		List<TeeSalDataPerson> salDataList = new ArrayList<TeeSalDataPerson>();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		int startSalYear = TeeStringUtil.getInteger(requestDatas.get("startSalYear"), 0);
		int startSalMonth = TeeStringUtil.getInteger(requestDatas.get("startSalMonth"), 0);
		int endSalYear = TeeStringUtil.getInteger(requestDatas.get("endSalYear"), 0);
		int endSalMonth = TeeStringUtil.getInteger(requestDatas.get("endSalMonth"), 0);
		int  accountId  =  TeeStringUtil.getInteger(requestDatas.get("accountId"), 0);
		List param = new ArrayList();
		String hql = "from TeeSalDataPerson salData where accountId = " + accountId; 
		if(!TeeUtility.isNullorEmpty(person) && person.getUuid()>0){
			hql+=" and salData.user.uuid=?";
			param.add(person.getUuid());
		}
		if(startSalYear>0){
			hql+=" and salData.salYear=?";
			param.add(startSalYear);
		}
//		if(startSalMonth>0){
//			hql+=" and salData.salMonth>=?";
//			param.add(startSalMonth);
//		}
//		if(endSalYear>0){
//			hql+=" and salData.salYear<=?";
//			param.add(endSalYear);
//		}
//		if(endSalMonth>0){
//			hql+=" and salData.salMonth<=?";
//			param.add(endSalMonth);
//		}
		salDataList = executeQueryByList(hql, param);
		return salDataList;
	}
	/**
	 * 根据sid 或去工资信息
	 * @param sid
	 * @return
	 */
	public TeeSalDataPerson getById(int sid) {
		TeeSalDataPerson salData = get(sid);
		return salData;
	}
}
