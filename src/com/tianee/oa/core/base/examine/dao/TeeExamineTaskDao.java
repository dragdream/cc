package com.tianee.oa.core.base.examine.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.examine.bean.TeeExamineTask;
import com.tianee.oa.core.base.examine.model.TeeExamineTaskModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


@Repository("TeeExamineTaskDao")
public class TeeExamineTaskDao  extends TeeBaseDao<TeeExamineTask>{
	/**
	 * @author syl
	 * 增加
	 * @param TeeExamineTask
	 */
	public void add(TeeExamineTask obj) {
		save(obj);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeExamineTask
	 */
	public void updateObj(TeeExamineTask obj) {
		update(obj);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineTask loadById(int id) {
		TeeExamineTask intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeExamineTask getById(int id) {
		TeeExamineTask intf = get(id);
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
	 * 删除 所有记录
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delAll(){
		String hql = "delete from TeeExamineTask ";
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
			String hql = "delete from TeeExamineTask where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	
	/**
	 * 查询所有记录  ---  带申请权限
	 * @author syl
	 * @date 2014-5-24
	 * @param person  人员对象
	 * @param model  模型
	 * @param type 是否获取数据   true:获取数据  false:获取记录数
	 * @return
	 */
	public  Map selectPostExamineTask(TeePerson person , TeeExamineTaskModel model , boolean type,int firstResult,int pageSize,TeeDataGridModel dm) {
		List<TeeExamineTask> list = new ArrayList<TeeExamineTask>();
		Map map  = new HashMap();
		if(TeePersonService.checkIsSuperAdmin(person, "")){//是否超级管理员
			String hql = "from TeeExamineTask ";
			
			if(type){
				if(TeeUtility.isNullorEmpty(dm.getSort())){
					dm.setSort("sid");
				}
				hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
				list = (List<TeeExamineTask>) pageFind(hql, firstResult, pageSize, null);
				map.put("data", list);
			}else{
				
				hql = " select count(sid) "  + hql;
				long count = count(hql, null);
				map.put("count", count);
			}
			
		}else{
			//获取人员部门、角色、人员 sid
			//int temp[] = TeeUtility.parsePersonDeptId_UserId_UserRoleId(person);
			Object[] values = { person.getUuid()};
			String hql = "from TeeExamineTask  where creater.uuid = ? ";
			if(type){
				if(TeeUtility.isNullorEmpty(dm.getSort())){
					dm.setSort("sid");
				}
				hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
				list = (List<TeeExamineTask>) pageFind(hql, firstResult, pageSize, values);
				map.put("data", list);
			}else{
				hql = " select count(sid) "  + hql;
				long count = count(hql, values);
				map.put("count", count);
			}
		}
		return map;
	}	
	
	
	
	/**
	 * 查询所有记录  ---  查询
	 * @author syl
	 * @date 2014-5-24
	 * @param person  人员对象
	 * @param model  模型
	 * @param type 是否获取数据   true:获取数据  false:获取记录数
	 * @return
	 * @throws ParseException 
	 */
	public  Map selectQueryExamineTask(TeePerson person , TeeExamineTaskModel model , boolean type,int firstResult,int pageSize,TeeDataGridModel dm) throws ParseException {
		List<TeeExamineTask> list = new ArrayList<TeeExamineTask>();
		Map map  = new HashMap();
		String hql = "from TeeExamineTask  task where 1=1  ";
		
		List query = new ArrayList();
		if(!TeeUtility.isNullorEmpty(model.getTaskTitle())){//标题
			query.add("%" + model.getTaskTitle() + "%");
			hql = hql + " and task.taskTitle like ?";
		}
		if(!TeeUtility.isNullorEmpty(model.getTaskDesc())){//描述
			query.add("%" + model.getTaskDesc() + "%");
			hql = hql + " and task.taskDesc like ?";
		}
		if(!TeeUtility.isNullorEmpty(model.getGroupId())){//指标集
			query.add(Integer.parseInt(model.getGroupId()));
			hql = hql + " and task.group.sid = ?";
		}
		String rankmanIds = model.getRankmanIds();
		if(!TeeUtility.isNullorEmpty(rankmanIds)){//考核人
			if(rankmanIds.endsWith(",")){
				rankmanIds = rankmanIds.substring(0, rankmanIds.length() - 1);
			}
			hql = hql + " and (exists (select 1 from task.rankman temp where  temp.uuid in (" + rankmanIds + ") ))";
		}
		
		String participantIds = model.getParticipantIds();
		if(!TeeUtility.isNullorEmpty(participantIds)){//被考核人
			if(participantIds.endsWith(",")){
				participantIds = participantIds.substring(0, participantIds.length() - 1);
			}
			hql = hql + " and (exists (select 1 from task.participant temp where  temp.uuid in (" + participantIds + ") ))";
		}
		
		if(!TeeUtility.isNullorEmpty(model.getTaskBeginStr())){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getTaskBeginStr());
			query.add(date);
			hql = hql + " and task.taskBegin >= ?";
		}
		
		if(!TeeUtility.isNullorEmpty(model.getTaskBeginStr2())){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getTaskBeginStr2());
			query.add(date);
			hql = hql + " and task.taskBegin <= ?";
		}
		
		if(!TeeUtility.isNullorEmpty(model.getTaskEndStr())){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getTaskEndStr());
			query.add(date);
			hql = hql + " and task.taskEnd >=?";
		}
		
		if(!TeeUtility.isNullorEmpty(model.getTaskEndStr2())){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getTaskEndStr2());
			query.add(date);
			hql = hql + " and task.taskEnd <=?";
		}
		
		if(TeePersonService.checkIsSuperAdmin(person, "")){//是否超级管理员
			if(type){
				if(TeeUtility.isNullorEmpty(dm.getSort())){
					dm.setSort("sid");
				}
				hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
				list = (List<TeeExamineTask>) pageFindByList(hql, firstResult, pageSize, query);
				map.put("data", list);
			}else{
				
				hql = " select count(sid) "  + hql;
				long count = countByList(hql, query);
				map.put("count", count);
			}
		}else{
			//获取人员部门、角色、人员 sid
			//int temp[] = TeeUtility.parsePersonDeptId_UserId_UserRoleId(person);
			query.add(person.getUuid());
		    hql = hql + " and task.creater.uuid = ? ";
			if(type){
				if(TeeUtility.isNullorEmpty(dm.getSort())){
					dm.setSort("sid");
				}
				hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
				list = (List<TeeExamineTask>) pageFindByList(hql, firstResult, pageSize, query);
				map.put("data", list);
			}else{
				hql = " select count(sid) "  + hql;
				long count = countByList(hql, query);
				map.put("count", count);
			}
		}
		return map;
	}
	
	/**
	 * 根据明细集 查询 所有记录 
	 * @author syl
	 * @date 2014-5-24
	 * @param person
	 * @return
	 */
	public List<TeeExamineTask>  getAllByGroupId(int groupId) {
		Object[] values = { groupId};
		String hql = "from TeeExamineTask  where  group.sid = ?";
		List<TeeExamineTask> list =  executeQuery(hql, values);
		return list;
	}	
	
	
	/**
	 * 获取自评、 且自己为被考核人的任务   
	 * @author syl
	 * @date 2014-5-24
	 * @param person
	 * @return
	 */
	public List<TeeExamineTask>  getCurrUserSelfTask(TeePerson  person , String selfType) {
		Object[] values = { person.getUuid() , person.getUuid() };
		String hql = "from TeeExamineTask taskObj where  taskObj.isSelfAssessment = '1' and (exists (select 1 from taskObj.participant user where user.uuid= ? )) " ;
		Object[] values2 = { person.getUuid()  };
		List<TeeExamineTask> list  = new ArrayList<TeeExamineTask>();
		if(selfType.equals("1")){//----未自评，去掉终止的
			hql = hql + " and   taskObj.taskBegin <= ? and (taskObj.taskEnd >= ?  or taskObj.taskEnd is null) and (not exists (select 1 from taskObj.selfData selfData where  selfData.participant.uuid= ? ))";
			Calendar calendar = TeeUtility.getMinTimeOfDayCalendar(null);
			Object[] values3 = {person.getUuid(), calendar.getTime(), calendar.getTime(), person.getUuid() };
			list =  executeQuery(hql, values3);
		}else if(selfType.equals("2")){//----已自评,
			hql =  hql + " and  (exists (select 1 from taskObj.selfData selfData where  selfData.participant.uuid= ? ))";
			list =  executeQuery(hql, values);
		}else{
			//未自评，去掉终止的 + ---已自评,
			hql = hql + " and  (( taskObj.taskBegin <= ? and (taskObj.taskEnd >= ?  or taskObj.taskEnd is null) and (not exists (select 1 from taskObj.selfData selfData where  selfData.participant.uuid= ? )))"
					+" or  (exists (select 1 from taskObj.selfData selfData where  selfData.participant.uuid= ? ) )"
					+")";
			Calendar calendar = TeeUtility.getMinTimeOfDayCalendar(null);
			Object[] values3 = {person.getUuid(), calendar.getTime(), calendar.getTime(), person.getUuid(),person.getUuid() };
	
			list =  executeQuery(hql, values3);
		}
		return list;
	}	
	
	

	/**
	 * 获取有权限考核的信息
	 * @author syl
	 * @date 2014-5-24
	 * @param person
	 * @return
	 */
	public Map getPostExamineTask(TeePerson  person , boolean type,int firstResult,int pageSize,TeeDataGridModel dm ) {
		Calendar calendar = TeeUtility.getMinTimeOfDayCalendar(null);
		
		Object[] values = { calendar.getTime(), calendar.getTime() ,person.getUuid() };
		String hql = "from TeeExamineTask taskObj where taskObj.taskBegin <= ? and (taskObj.taskEnd >= ?  or taskObj.taskEnd is null) and  (exists (select 1 from taskObj.rankman user where user.uuid= ? )) ";
		Map map = new HashMap();
		if(type){
			if(TeeUtility.isNullorEmpty(dm.getSort())){
				dm.setSort("taskObj.taskBegin");
			}
			List<TeeExamineTask> list = pageFind(hql, firstResult, pageSize, values);
			map.put("data", list);
		}else{
			hql = " select count(sid) " + hql;
			long count = count(hql, values);
			map.put("count", count);
		}
		return map;
	}	
	
	
}
