package com.tianee.oa.core.base.attend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeAttendLeaderRule;
import com.tianee.oa.core.base.attend.model.TeeAttendLeaderRuleModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Repository("attendLeaderRuleDao")
public class TeeAttendLeaderRuleDao  extends TeeBaseDao<TeeAttendLeaderRule> {
	/**
	 * @author syl
	 * 增加
	 * @param TeeAttendOut
	 */
	public void addAttendLeader(TeeAttendLeaderRule attend) {
		save(attend);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeAttendOut
	 */
	public void updateAttend(TeeAttendLeaderRule attend) {
		update(attend);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeAttendLeaderRule loadById(int id) {
		TeeAttendLeaderRule intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeAttendLeaderRule getById(int id) {
		TeeAttendLeaderRule intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 删除
	 * @param 
	 */
	public void delById(int id) {
		delete(id);
	}
	
	/**
	 * 删除 by Ids
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeAttendLeaderRule where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeAttendLeaderRule> selectRule(TeePerson person , TeeAttendLeaderRuleModel model) {
		Object[] values = null;
		String hql = "from TeeAttendLeaderRule";
		List<TeeAttendLeaderRule> list = (List<TeeAttendLeaderRule>) executeQuery(hql,values);
		return list;
	}	
	
	
	/**
	 * @author syl
	 * 当前登录人 获取符合雕件的审批规则
	 * @param 
	 */
	public  List<TeeAttendLeaderRule> selectLeaderRule(TeePerson person , TeeAttendLeaderRuleModel model) {
		Object[] values = { person.getDept(), person};
		String hql = "from TeeAttendLeaderRule rule where  (exists (select 1 from rule.depts depts where depts=?)) or  (exists (select 1 from rule.users users where users=?))";
		List<TeeAttendLeaderRule> list = (List<TeeAttendLeaderRule>) executeQuery(hql,values);
		return list;
	}	
	
}