package com.tianee.oa.subsys.salManage.dao;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.tianee.oa.subsys.salManage.bean.TeeSalAccount;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("teeSalAccountDao")
public class TeeSalAccountDao extends TeeBaseDao<TeeSalAccount>{	
	/**
	 * 删除
	 * @param personId
	 * @return
	 */
	public void delById(int accountId){
		String hql = "delete from TeeSalAccount where sid = ?";
		deleteOrUpdateByQuery(hql, new Object[] {accountId});
	}
	
	/**
	 * 获取所有账套记录
	 * @param personId
	 * @return
	 */
	public List<TeeSalAccount> getAllList(){
		Object[] values = {};
		String hql = "from TeeSalAccount order by accountSort" ; 
		List<TeeSalAccount> salDataList  = executeQuery(hql, values);
		return salDataList;
	}
	
	/**
	 * 获取人员账套类型
	 * @param userId
	 * @return
	 */
	public List<TeeSalAccount> getSalAccountByPersonId(int userId){
		Object[] values = {userId};
		String hql = "from TeeSalAccount sa where   (exists (select 1 from sa.accountPerson ap where ap.user.uuid=?)) order by accountSort" ; 
		List<TeeSalAccount> salDataList  = executeQuery(hql, values);
		return salDataList;
	}
}
