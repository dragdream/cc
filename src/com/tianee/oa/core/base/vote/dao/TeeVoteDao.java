package com.tianee.oa.core.base.vote.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.vote.bean.TeeVote;
import com.tianee.oa.core.base.vote.model.TeeVoteModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author syl
 *
 */
@Repository("voteDao")
public class TeeVoteDao extends TeeBaseDao<TeeVote>{

	/**
	 * @author syl
	 * 增加
	 * @param TeeVote
	 */
	public void add(TeeVote obj) {
		save(obj);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeVote
	 */
	public void updateObj(TeeVote obj) {
		update(obj);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeVote loadById(int id) {
		TeeVote intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeVote getById(int id) {
		TeeVote intf = get(id);
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
	 * 删除 所有会议室
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delAll(){
		String hql = "delete from TeeVote ";
		deleteOrUpdateByQuery(hql, null);
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
			String hql = "delete from TeeVote where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl   
	 * 查询所有记录 
	 * @param 
	 */
	public  List<TeeVote> getAllVote(TeePerson person , TeeVoteModel model) {
		Object[] values = { };
		String hql = "from TeeVote where parentVote is null  order by createDate";
		List<TeeVote> list = (List<TeeVote>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * 投票管理    ----  获取记录数
	 * @author syl
	 * @date 2014-2-21
	 * @param person  系统当前用户
	 * @param model  模型
	 * @param isSuperAdmin  是否超级管理员
	 * @return
	 * @throws ParseException 
	 */
	public  long getVoteManagerCount(TeePerson person ,Map requestDatas, TeeVoteModel model ,boolean isSuperAdmin ,TeeManagerPostPersonDataPrivModel dataPrivModel) throws ParseException {
		String subject = (String) requestDatas.get("subject");
		String publish = (String) requestDatas.get("publish");
		
		String startDateStr = (String) requestDatas.get("startDateStr");
		String endDateStr = (String) requestDatas.get("endDateStr");
		
		String hql = "select count(sid) from TeeVote where parentVote is null  ";
		
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(subject)) {
			hql += " and subject like ?";
			param.add("%" + subject + "%");
		}
		if (!TeeUtility.isNullorEmpty(publish)) {
			hql += " and publish = ?";
			param.add(publish);
		}
		if (!TeeUtility.isNullorEmpty(startDateStr)) {
			hql += " and createDate >= ?";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startDateStr + " 00:00:00"));
			param.add(calendar);
		}
		if (!TeeUtility.isNullorEmpty(endDateStr)) {
			hql += " and createDate <= ?";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endDateStr + " 23:59:59"));
			param.add(calendar);
		}
		
		if(dataPrivModel.getPrivType().equals("0")){//空
			hql = hql + " and createUser.uuid = " + person.getUuid();//加上自己创建的
		}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
			// hql = "from TeeNews n where  1 = 1";
		}else{
			List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
			pIdList.add(person.getUuid());
			if(pIdList.size() > 0){
				String personIdsSql =  TeeDbUtility.IN("createUser.uuid", pIdList);
				hql = hql + " and " + personIdsSql;
			}else{
				return 0L;
			}
		}
		long count = countByList(hql, param);
		return count;
	}
	
	/**
	 * 投票管理    ----  获取记录数
	 * @author syl
	 * @date 2014-2-21
	 * @param person  系统当前用户
	 * @param model  模型
	 * @param isSuperAdmin  是否超级管理员
	 * @return
	 * @throws ParseException 
	 */
	public  long getVoteManagerCount(TeePerson person ,Map requestDatas, TeeVoteModel model ,boolean isSuperAdmin ) throws ParseException {
		String subject = (String) requestDatas.get("subject");
		String publish = (String) requestDatas.get("publish");
		
		String startDateStr = (String) requestDatas.get("startDateStr");
		String endDateStr = (String) requestDatas.get("endDateStr");
//		Object[] values = { person.getUuid()};
//		String hql = "select count(sid) from TeeVote where parentVote is null  and createUser.uuid = ? ";
		String hql = "select count(*) from TeeVote vote where vote.parentVote is null and vote.publish <> 0 and (exists (select 1 from vote.postUser p where p.uuid = ?) or exists(select 1 from vote.postUserRole r where r.uuid = ?) or exists (select 1 from vote.postDept d where d.uuid = ?)) ";
		List param = new ArrayList();
		param.add(person.getUuid());
		param.add(person.getUserRole().getUuid());
		param.add(person.getDept().getUuid());
		
		if (!TeeUtility.isNullorEmpty(subject)) {
			hql += " and subject like ?";
			param.add("%" + subject + "%");
		}
		if (!TeeUtility.isNullorEmpty(publish)) {
			hql += " and publish = ?";
			param.add(publish);
		}
		if (!TeeUtility.isNullorEmpty(startDateStr)) {
			hql += " and createDate >= ?";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startDateStr + " 00:00:00"));
			param.add(calendar);
		}
		if (!TeeUtility.isNullorEmpty(endDateStr)) {
			hql += " and createDate <= ?";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endDateStr + " 23:59:59"));
			param.add(calendar);
		}
		
		
		/*
		if(isSuperAdmin){
			values = null;
			hql = "select count(sid) from TeeVote where parentVote is null ";
		}*/
		long count = countByList(hql, param);
		return count;
	}

	/**
	 * 投票管理    ----  获取记录数
	 * @author syl
	 * @date 2014-2-21
	 * @param person  系统当前用户
	 * @param model  模型
	 * @param isSuperAdmin  是否超级管理员
	 * @return
	 */
	public   List<TeeVote>  getVoteManager(TeePerson  person ,Map requestDatas, boolean isSuperAdmin , int firstResult,int pageSize,TeeDataGridModel dm ,TeeVoteModel model ,TeeManagerPostPersonDataPrivModel dataPrivModel) throws ParseException { 
		String subject = (String) requestDatas.get("subject");
		String publish = (String) requestDatas.get("publish");
		
		String startDateStr = (String) requestDatas.get("startDateStr");
		String endDateStr = (String) requestDatas.get("endDateStr");
		
		String hql = "from TeeVote where parentVote is null ";
		List param = new ArrayList();
		if (!TeeUtility.isNullorEmpty(subject)) {
			hql += " and subject like ?";
			param.add("%" + subject + "%");
		}
		if (!TeeUtility.isNullorEmpty(publish)) {
			hql += " and publish = ?";
			param.add(publish);
		}
		if (!TeeUtility.isNullorEmpty(startDateStr)) {
			hql += " and createDate >= ?";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startDateStr + " 00:00:00"));
			param.add(calendar);
		}
		if (!TeeUtility.isNullorEmpty(endDateStr)) {
			hql += " and createDate <= ?";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endDateStr + " 23:59:59"));
			param.add(calendar);
		}
		
		List<TeeVote> list = new ArrayList<TeeVote>();
		if(dataPrivModel.getPrivType().equals("0")){//空
			hql = hql + " and createUser.uuid = " + person.getUuid();//加上自己创建的
		}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
			// hql = "from TeeNews n where  1 = 1";
		}else{
			List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
			pIdList.add(person.getUuid());
			if(pIdList.size() > 0){
				String personIdsSql =  TeeDbUtility.IN("createUser.uuid", pIdList);
				hql = hql + " and " + personIdsSql;
			}else{
				return  list;
			}
		}
		if(TeeUtility.isNullorEmpty(dm.getSort()) ){
			dm.setSort("createDate");
			dm.setOrder("desc");
		}
		if(dm.getSort().equals("beginDateStr")){
			dm.setSort("beginDate");
		}
		if(dm.getSort().equals("endDateStr")){
			dm.setSort("endDate");
		}
		hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
		return pageFindByList(hql, firstResult, pageSize, param);
	}
	

	
	/**
	 * @author syl   
	 * 查询个人创建所有记录  
	 * @param 
	 */
	public  List<TeeVote> getPersonalVote(TeePerson person , TeeVoteModel model ,boolean isSuperAdmin) {
		Object[] values = {person };
		String hql = "from TeeVote where parentVote is null  and createUser = ? order by createDate";
		if(isSuperAdmin){
			values = null;
			hql = "from TeeVote where parentVote is null  order by createDate";
		}
		List<TeeVote> list = (List<TeeVote>) executeQuery(hql,values);
		return list;
	}
	
	/**
	 * @author syl
	 * 查询记录  ---  带发布范围权限  部门、人员、角色
	 * @param 
	 */
	public  List<TeeVote> selectPostVote(TeePerson person , TeeVoteModel model) {
		Object[] values = { person.getDept() , person , person.getUserRole()};
		String hql = "from TeeVote  v where v.parentVote is null  and  " +
				" ( exists (select 1 from v.postDept dept where dept =?) " +
				" or exists (select 1 from v.postUser user where user= ? )" +
				" or exists (select 1 from v.postUserRole userRole where userRole= ? )" +
				") " +
				"" +
				"order by v.createDate";
		List<TeeVote> list = (List<TeeVote>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * @author CXT
	 * 新增/更新
	 * @param TeeVote
	 */
	public void saveObj(TeeVote obj) {
		Session session = this.getSession();
		session.merge(obj);
	}
	
	/**
	 * @author CXT   
	 * 根据符id查找vote
	 * @param 
	 */
	public  List<TeeVote> getVoteListBySid(int id) {
		Object[] values = { };
		String hql = "select vote from TeeVote vote where parentVote.sid ="+id+" order by vote.voteNo asc";
		List<TeeVote> list = (List<TeeVote>) executeQuery(hql,values);
		return list;
	}	
		
	
	/**
	 * 投票记录
	 * @author CXT
	 * @date 2014-3-26
	 * @param person  系统当前用户
	 * @param model  模型
	 * @param isSuperAdmin  是否超级管理员
	 * @return
	 */
	public   List<TeeVote>  getVotes(TeePerson  person ,Map requestDatas, boolean isSuperAdmin , int firstResult,int pageSize,TeeDataGridModel dm ,TeeVoteModel model) throws ParseException { 
		String subject = (String) requestDatas.get("subject");
		String publish = (String) requestDatas.get("publish");
		
		String startDateStr = (String) requestDatas.get("startDateStr");
		String endDateStr = (String) requestDatas.get("endDateStr");
		
		
//		Object[] values = {person.getUuid(),person.getUserRole().getUuid(),person.getDept().getUuid()};
		String hql = "select vote from TeeVote vote where vote.parentVote is null and vote.publish <> 0 and (exists (select 1 from vote.postUser p where p.uuid = ?) or exists(select 1 from vote.postUserRole r where r.uuid = ?) or exists (select 1 from vote.postDept d where d.uuid = ?)) ";
		
		List param = new ArrayList();
		param.add(person.getUuid());
		param.add(person.getUserRole().getUuid());
		param.add(person.getDept().getUuid());
		
		if (!TeeUtility.isNullorEmpty(subject)) {
			hql += " and subject like ?";
			param.add("%" + subject + "%");
		}
		if (!TeeUtility.isNullorEmpty(publish)) {
			hql += " and publish = ?";
			param.add(publish);
		}
		if (!TeeUtility.isNullorEmpty(startDateStr)) {
			hql += " and createDate >= ?";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", startDateStr + " 00:00:00"));
			param.add(calendar);
		}
		if (!TeeUtility.isNullorEmpty(endDateStr)) {
			hql += " and createDate <= ?";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(TeeDateUtil.parseDate("yyyy-MM-dd HH:mm:ss", endDateStr + " 23:59:59"));
			param.add(calendar);
		}
		
		
		
		
		if(TeeUtility.isNullorEmpty(dm.getSort()) ){
			dm.setSort("createDate");
			dm.setOrder("desc");
		}
		if(dm.getSort().equals("beginDateStr")){
			dm.setSort("vote.beginDate");
		}
		if(dm.getSort().equals("endDateStr")){
			dm.setSort("vote.endDate");
		}
		hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
		return pageFindByList(hql, firstResult, pageSize, param);
	}
	
	
	
	/**
	 * 判断是否有该投票权限
	 * @function: 
	 * @author: wyw
	 * @data: 2015年6月26日
	 * @param person
	 * @param voteId
	 * @return
	 * @throws ParseException List<TeeVote>
	 */
	public  List<TeeVote>  getVotesPriv(TeePerson  person ,int voteId) throws ParseException { 
		Object[] values = {voteId,person.getUuid(),person.getUserRole().getUuid(),person.getDept().getUuid()};
		String hql = "select vote from TeeVote vote where vote.sid=? and vote.parentVote is null and vote.publish <> 0 and (exists (select 1 from vote.postUser p where p.uuid = ?) or exists(select 1 from vote.postUserRole r where r.uuid = ?) or exists (select 1 from vote.postDept d where d.uuid = ?))";
		return executeQuery(hql, values);
	}
	
	
	/**
	 * 获取非匿名数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年6月27日
	 * @param id
	 * @return List<TeeVote>
	 */
	public  List<TeeVote> getVoteDateListBySid(int id) {
		Object[] values = { };
		String hql = "select vote from TeeVote vote where parentVote.sid ="+id+" order by vote.voteNo asc";
		List<TeeVote> list = (List<TeeVote>) executeQuery(hql,values);
		return list;
	}	
		
	
	
}
