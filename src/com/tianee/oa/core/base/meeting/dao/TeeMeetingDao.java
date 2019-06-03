package com.tianee.oa.core.base.meeting.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.meeting.bean.TeeMeeting;
import com.tianee.oa.core.base.meeting.bean.TeeMeetingTopic;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.base.meeting.model.TeeMeetingTopicModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Repository("meetingDao")
public class TeeMeetingDao  extends TeeBaseDao<TeeMeeting> {
	/**
	 * @author syl
	 * 增加
	 * @param TeeMeeting
	 */
	public void addAttendOut(TeeMeeting attendOut) {
		save(attendOut);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeMeeting
	 */
	public void updateAttendOut(TeeMeeting attendOut) {
		update(attendOut);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeMeeting loadById(int id) {
		TeeMeeting intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeMeeting getById(int id) {
		TeeMeeting intf = get(id);
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
			String hql = "delete from TeeMeeting where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/*
	 * 更改会议状态 ----主要用于审批
	 */
	public void updateStatus(TeePerson person , TeeMeetingModel model) {
		Object[] values = {model.getStatus() , model.getSid() };
		String hql = "update  TeeMeeting  set status = ? where sid =?";
		/*if(model.getStatus() == 3){//不批准
			hql = "update  TeeMeeting  set status = ? ,  where sid =?";
		}*/
		deleteOrUpdateByQuery(hql,values);
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeMeeting> selectPersonalMeeting(TeePerson person , TeeMeetingModel model) {
		Object[] values = {person };
		String hql = "from TeeMeeting where user = ? and status = ?  order by  createTime desc";
		List<TeeMeeting> list = (List<TeeMeeting>) executeQuery(hql,values);
		return list;
	}	
	
	
	/**
	 * @author syl
	 * 查询个人会议记录
	 * @param 
	 */
	public  List<TeeMeeting> getPersonalMeetByStatus(TeePerson person , TeeMeetingModel model) {
		Object[] values = {person  ,  model.getStatus()};
		String hql = "from TeeMeeting where user = ? and status = ?  order by  createTime desc";
		List<TeeMeeting> list = (List<TeeMeeting>) executeQuery(hql,values);
		return list;
	}
	
	/**
	 * @author wgg
	 * 通用列表        查询数量
	 * @param roomType=会议室类型  1=会议室   2=询问室内
	 * @throws ParseException 
	 */
	public  long getQueryCountByStatus(TeePerson person , TeeMeetingModel model,int roomType) throws ParseException {
		List list = new ArrayList();
		String hql = "select count(m.sid) from TeeMeeting m   left join m.meetRoom as mr   where  1 = 1  ";
		if(roomType==1){//会议室  或者 外部会议
			hql+=" and (mr is null or mr.type=?)  ";
		}else{//询问室内
			hql+=" and mr.type=?   ";
		}
		list.add(roomType);
		if(!TeeUtility.isNullorEmpty(person)){//会议室
			hql = hql + " and m.user = ?";
			list.add(person);
		}
		
		if(model.getStatus() >= 0){//状态
			hql = hql + " and m.status = ?";
			list.add(model.getStatus());
		}
		long count = countByList(hql, list);
		return count;
	}	
	/**
	 * 分页查询
	 * @author wgg
	 * @date 2016-11-1
	 * @param firstResult
	 * @param pageSize
	 * @param dm
	 * @param model
	 * @return  roomType会议室类型   1=会议室  2=询问室
	 * @throws ParseException 
	 */
	public  List<TeeMeeting> getMeetPageFindByStatus(TeePerson person ,int firstResult,int pageSize,TeeDataGridModel dm ,TeeMeetingModel model,int roomType) throws ParseException { 
		String hql = " select(m) from TeeMeeting m left join m.meetRoom as mr   where  1 = 1  ";
		List list = new ArrayList();
		
		if(roomType==1){//会议室  或者  外部会议
			hql+=" and (mr is null  or mr.type=?) ";
		}else{
			hql+=" and mr.type=? ";
		}
		
		list.add(roomType);
		if(!TeeUtility.isNullorEmpty(person)){//会议室
			hql = hql + " and m.user = ?";
			list.add(person);
		}
		if(model.getStatus() >= 0){//状态
			hql = hql + " and m.status = ?";
			list.add(model.getStatus());
		}
		hql = hql + " order by  m.createTime desc";
		return pageFindByList(hql, firstResult, pageSize, list);
	}
	
	/**
	 * @author syl
	 * 查询会议审批记录
	 * @param 
	 */
	public  List<TeeMeeting> getLeaderMeetByStatus(TeePerson person , TeeMeetingModel model) {
		Object[] values = {person  ,  model.getStatus()};
		String hql = "from TeeMeeting where manager = ? and status = ?  order by  createTime desc";
		List<TeeMeeting> list = (List<TeeMeeting>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * @author wgg
	 * 通用列表        查询数量
	 * @param 
	 * @throws ParseException 
	 */
	public  long getLeaderQueryCountByStatus(TeePerson person , TeeMeetingModel model) throws ParseException {
		List list = new ArrayList();
		String hql = "select count(sid) from TeeMeeting  where  1 = 1  ";
		
//		if(!TeeUtility.isNullorEmpty(person)){//会议室
//			hql = hql + " and manager = ?";
//			list.add(person);
//		}
		
		if(model.getStatus() >= 0){//状态
			hql = hql + " and status = ?";
			list.add(model.getStatus());
		}
		long count = countByList(hql, list);
		return count;
	}	
	/**
	 * 分页查询
	 * @author wgg
	 * @date 2016-11-1
	 * @param firstResult
	 * @param pageSize
	 * @param dm
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	public  List<TeeMeeting> getLeaderMeetPageFindByStatus(TeePerson person ,int firstResult,int pageSize,TeeDataGridModel dm ,TeeMeetingModel model) throws ParseException { 
		String hql = "from TeeMeeting  where  1 = 1  ";
		List list = new ArrayList();
//		if(!TeeUtility.isNullorEmpty(person)){//会议室
//			hql = hql + " and manager = ?";
//			list.add(person);
//		}
		if(model.getStatus() >= 0){//状态
			hql = hql + " and status = ?";
			list.add(model.getStatus());
		}
		hql = hql + " order by  createTime desc";
		return pageFindByList(hql, firstResult, pageSize, list);
	}
	
	
	/**
	 * @author syl
	 * 查询会议审批记录数量  根据状态
	 * @param 
	 */
	public  long getLeaderApproveCount(TeePerson person , TeeMeetingModel model) {
		Object[] values = {person.getUuid()  ,  model.getStatus()};
		String hql = "select count(sid) from TeeMeeting  where manager.uuid = ? and status = ?  ";
		long count = count(hql, values);
		return count;
	}	
	
	

	
	/**
	 * @author syl
	 * 根据时间查询个人记录  by Time  
	 * @param 
	 */
	public  List<TeeMeeting> selectPersonalByTime(TeePerson person , Date startTime , Date endTime,TeeMeetingModel model) {
		int meetRoomId = TeeStringUtil.getInteger(model.getMeetRoomId(),0);
		//Object[] values = {person  , endTime.getTime() , startTime.getTime() ,meetRoomId};
		Object[] values = { endTime.getTime() , startTime.getTime()};
		String hql = "from TeeMeeting where";
		hql = hql + "  startTime < ?";
		hql = hql + " and endTime >= ?  ";
		if(meetRoomId>0){
			hql = hql + " and meetRoom.sid ="+meetRoomId;
		}else{
			hql = hql + " and meetRoom.sid = null";
		}
		hql = hql + " order by createTime";
		List<TeeMeeting> list = (List<TeeMeeting>) executeQuery(hql,values);
		return list;
	}	
	
	
	
	/**
	 * @author syl
	 * 获取所有会议  by Time  
	 * @param 
	 */
	public  List<TeeMeeting> selectAllByTime(TeePerson person , Date startTime , Date endTime,TeeMeetingModel model) {
		int meetRoomId = TeeStringUtil.getInteger(model.getMeetRoomId(),0);
		//Object[] values = {person  , endTime.getTime() , startTime.getTime() ,meetRoomId};
		Object[] values = { endTime.getTime() , startTime.getTime() };
		String hql = "from TeeMeeting where";
		hql = hql + "  startTime <= ?";
		hql = hql + " and endTime >= ?  ";
		hql = hql + " order by createTime";
		List<TeeMeeting> list = (List<TeeMeeting>) executeQuery(hql,values);
		return list;
	}	

	/**
	 * 检查会议申请  时间是否重叠
	 * @author syl
	 * @date 2014-4-27
	 * @param meeting
	 * @return
	 */
	public boolean checkMeetingTimeIsRepeat(TeeMeeting meeting){
		Object[] values = { meeting.getStartTime() ,  meeting.getEndTime(),meeting.getStartTime() ,meeting.getStartTime(),meeting.getEndTime(),meeting.getEndTime(),meeting.getStartTime(),meeting.getEndTime(),meeting.getMeetRoom().getSid()};
		String hql = "select count(sid) from TeeMeeting where";
		hql = hql + " ( (startTime < ? and endTime > ?) or  ";
		hql = hql + "  (startTime <? and endTime>? ) or ";
		hql = hql + "  (startTime <? and endTime>? ) or ";
		hql = hql + "  (startTime >? and endTime<? ) ) ";
		hql = hql + " and (status = 1 or  status = 2 or status = 4)";//已批准、进行中、已结束
		hql = hql +  " and meetRoom.sid = ? ";
		if(meeting.getSid() > 0){
			hql = hql +  " and sid != " + meeting.getSid();
		}
		long count = count(hql,values);
		if(count <=0){
			return false;
		}
		return true;
	}
	/**
	 * @author syl
	 * 自动 查询  审批记录  -- “已批准” 和 “进行中” 
	 * @param 
	 */
	public  List<TeeMeeting> getAutoLeaderMeetByStatus(TeePerson person , TeeMeetingModel model) {
		Object[] values = {person  };
		String hql = "from TeeMeeting where manager = ? and (status = 1 or  status = 2)  order by  createTime desc";
		List<TeeMeeting> list = (List<TeeMeeting>) executeQuery(hql,values);
		return list;
	}	
	/**
	 *  自动 -- 更改会议状态  用于 “已审批” 改成 “进行中”  ； “进行中” 改成“结束”
	 * @author syl
	 * @date 2014-2-15
	 * @param person
	 * @param model
	 */
	public void autoUpdateStatus(TeePerson person , TeeMeetingModel model) {
		Object[] values = {model.getStatus() , model.getSid() };
		String hql = "update  TeeMeeting  set status = ? where sid = ?";
		deleteOrUpdateByQuery(hql,values);
	}
	
	/**
	 * @author syl
	 * 通用列表        查询数量
	 * @param 
	 * @throws ParseException 
	 */
	public  long getQueryCount(TeePerson person , TeeMeetingModel model) throws ParseException {
		List list = new ArrayList();
		String hql = "select count(sid) from TeeMeeting  where  1 = 1  ";
		if(!TeeUtility.isNullorEmpty(model.getMeetName())){//会议名称
			hql = hql + " and meetName like  ?";
			list.add("%" + model.getMeetName() + "%");
		}
		
		if(!TeeUtility.isNullorEmpty(model.getUserId())){//申请人
			hql = hql + " and user.uuid = ?";
			list.add(TeeStringUtil.getInteger(model.getUserId(),0));
		}
		
		if(!TeeUtility.isNullorEmpty(model.getStartTimeStr())){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getStartTimeStr());
			hql = hql + " and startTime >= ?";
			list.add(date.getTime());
		}
		
		if(!TeeUtility.isNullorEmpty(model.getEndTimeStr())){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getEndTimeStr());
			hql = hql + " and startTime <= ? ";
			list.add(date.getTime());
		}
		
		if(!TeeUtility.isNullorEmpty(model.getMeetRoomId())){//会议室
			hql = hql + " and meetRoom.sid = ?";
			list.add(TeeStringUtil.getInteger(model.getMeetRoomId(),0));
		}
		
		if(model.getStatus() > 0){//状态
			hql = hql + " and status = ?";
			list.add(model.getStatus()  - 1);
		}
		long count = countByList(hql, list);
		return count;
	}	
	/**
	 * 分页查询
	 * @author syl
	 * @date 2014-2-16
	 * @param firstResult
	 * @param pageSize
	 * @param dm
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	public  List<TeeMeeting> getMeetPageFind(int firstResult,int pageSize,TeeDataGridModel dm ,TeeMeetingModel model) throws ParseException { 
		String hql = "from TeeMeeting  where  1 = 1  ";
		List list = new ArrayList();
		if(!TeeUtility.isNullorEmpty(model.getMeetName())){//会议名称
			hql = hql + " and meetName like  ?";
			list.add("%" + model.getMeetName() + "%");
		}
		
		if(!TeeUtility.isNullorEmpty(model.getUserId())){//申请人
			hql = hql + " and user.uuid = ?";
			list.add(TeeStringUtil.getInteger(model.getUserId(),0));
		}
		
		if(!TeeUtility.isNullorEmpty(model.getStartTimeStr())){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getStartTimeStr());
			hql = hql + " and startTime >= ?";
			list.add(date.getTime());
		}
		
		if(!TeeUtility.isNullorEmpty(model.getEndTimeStr())){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getEndTimeStr());
			hql = hql + " and startTime <= ? ";
			list.add(date.getTime());
		}
		
		if(!TeeUtility.isNullorEmpty(model.getMeetRoomId())){//会议室
			hql = hql + " and meetRoom.sid = ?";
			list.add(TeeStringUtil.getInteger(model.getMeetRoomId(),0));
		}
		
		if(model.getStatus() > 0){//状态
			hql = hql + " and status = ?";
			list.add(model.getStatus()  - 1);
		}
		if(TeeUtility.isNullorEmpty(dm.getSort())){
			dm.setSort("startTime");
			dm.setOrder("desc");
		}
		hql = hql + " order by status," + dm.getSort() + " " + dm.getOrder();
		return pageFindByList(hql, firstResult, pageSize, list);
	}

	
	/**
	 * 获取我的会议总数
	 * @param requestDatas
	 * @return
	 * @throws Exception
	 */
	public Long getMyMeetingTotal(Map requestDatas) throws Exception {
		String meetName = TeeStringUtil.getString(requestDatas.get("meetName"),"");
		String startTimeStr = TeeStringUtil.getString(requestDatas.get("startTimeStr"),"");
		String endTimeStr = TeeStringUtil.getString(requestDatas.get("endTimeStr"),"");
		int approveId = TeeStringUtil.getInteger(requestDatas.get("userId"), 0);
		int roomId = TeeStringUtil.getInteger(requestDatas.get("roomId"), 0);
//		int status = TeeStringUtil.getInteger(requestDatas.get("status"), 0);
		TeePerson loginUser = (TeePerson)requestDatas.get("LOGIN_USER");
		String hql = "from TeeMeeting meeting where  1 = 1  ";
		List list = new ArrayList();
		if(!TeeUtility.isNullorEmpty(meetName)){//会议名称
			hql = hql + " and meetName like  ?";
			list.add("%" + meetName + "%");
		}
		
		if(approveId>0){//申请人
			hql = hql + " and user.uuid = ?";
			list.add(approveId);
		}
		
		if(!TeeUtility.isNullorEmpty(startTimeStr)){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd",startTimeStr);
			hql = hql + " and startTime >= ?";
			list.add(date.getTime());
		}
		
		if(!TeeUtility.isNullorEmpty(endTimeStr)){//结束时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", endTimeStr);
			hql = hql + " and startTime <= ? ";
			list.add(date.getTime());
		}
		
		if(roomId>0){//会议室
			hql = hql + " and meetRoom.sid = ?";
			list.add(roomId);
		}
		
//		if(status > 0){//状态
			hql = hql + " and status in (1,2,4)";
//			list.add(status  - 1);
//		}
		hql+=" and (user.uuid = "+loginUser.getUuid()+" or recorder.uuid = "+loginUser.getUuid()+" or exists (select 1 from meeting.attendeeList attendee where attendee.uuid = "+loginUser.getUuid()+"))";
		long count = countByList("select count(*) "+hql, list);
		return count;
		
	}

	/**
	 * 取得我的会议list
	 * @param firstIndex
	 * @param pageSize
	 * @param dm
	 * @param requestDatas
	 * @return
	 * @throws Exception
	 */
	public List<TeeMeeting> getMyMeeting(int firstIndex, int pageSize,TeeDataGridModel dm, Map requestDatas) throws Exception {
		String meetName = TeeStringUtil.getString(requestDatas.get("meetName"),"");
		String startTimeStr = TeeStringUtil.getString(requestDatas.get("startTimeStr"),"");
		String endTimeStr = TeeStringUtil.getString(requestDatas.get("endTimeStr"),"");
		int approveId = TeeStringUtil.getInteger(requestDatas.get("userId"), 0);
		int roomId = TeeStringUtil.getInteger(requestDatas.get("roomId"), 0);
//		int status = TeeStringUtil.getInteger(requestDatas.get("status"), 0);
		TeePerson loginUser = (TeePerson)requestDatas.get("LOGIN_USER");
		String hql = "from TeeMeeting meeting where  1 = 1  ";
		List list = new ArrayList();
		if(!TeeUtility.isNullorEmpty(meetName)){//会议名称
			hql = hql + " and meetName like  ?";
			list.add("%" + meetName + "%");
		}
		
		if(approveId>0){//申请人
			hql = hql + " and user.uuid = ?";
			list.add(approveId);
		}
		
		if(!TeeUtility.isNullorEmpty(startTimeStr)){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd",startTimeStr);
			hql = hql + " and startTime >= ?";
			list.add(date.getTime());
		}
		
		if(!TeeUtility.isNullorEmpty(endTimeStr)){//结束时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", endTimeStr);
			hql = hql + " and startTime <= ? ";
			list.add(date.getTime());
		}
		
		if(roomId>0){//会议室
			hql = hql + " and meetRoom.sid = ?";
			list.add(roomId);
		}
		
//		if(status > 0){//状态
			hql = hql + " and status in (1,2,4)";
//			list.add(status  - 1);
//		}
		if(TeeUtility.isNullorEmpty(dm.getSort())){
			dm.setSort("startTime");
			dm.setOrder("desc");
		}
		hql+=" and (user.uuid = "+loginUser.getUuid()+" or recorder.uuid = "+loginUser.getUuid()+" or exists (select 1 from meeting.attendeeList attendee where attendee.uuid = "+loginUser.getUuid()+"))";
		
		hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
		return pageFindByList(hql, firstIndex, pageSize, list);
	}
	
	
	/**
	 * 获取我的会议总数
	 * @param requestDatas
	 * @return
	 * @throws Exception
	 */
	public Long getSummaryTotal(Map requestDatas) throws Exception {
		String meetName = TeeStringUtil.getString(requestDatas.get("meetName"),"");
		String startTimeStr = TeeStringUtil.getString(requestDatas.get("startTimeStr"),"");
		String endTimeStr = TeeStringUtil.getString(requestDatas.get("endTimeStr"),"");
		int approveId = TeeStringUtil.getInteger(requestDatas.get("userId"), 0);
		int roomId = TeeStringUtil.getInteger(requestDatas.get("roomId"), 0);
		int status = TeeStringUtil.getInteger(requestDatas.get("status"), 0);
		TeePerson loginUser = (TeePerson)requestDatas.get("LOGIN_USER");
		String hql = "from TeeMeeting meeting where  1 = 1  ";
		List list = new ArrayList();
		if(!TeeUtility.isNullorEmpty(meetName)){//会议名称
			hql = hql + " and meetName like  ?";
			list.add("%" + meetName + "%");
		}
		
		if(approveId>0){//申请人
			hql = hql + " and user.uuid = ?";
			list.add(approveId);
		}
		
		if(!TeeUtility.isNullorEmpty(startTimeStr)){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd",startTimeStr);
			hql = hql + " and startTime >= ?";
			list.add(date.getTime());
		}
		
		if(!TeeUtility.isNullorEmpty(endTimeStr)){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", endTimeStr);
			hql = hql + " and startTime <= ? ";
			list.add(date.getTime());
		}
		
		if(roomId>0){//会议室
			hql = hql + " and meetRoom.sid = ?";
			list.add(roomId);
		}
		
		if(status > 0){//状态
			hql = hql + " and status = ?";
			list.add(status  - 1);
		}
		hql+=" and (recorder.uuid = "+loginUser.getUuid()+" or exists (select 1 from meeting.readPeoples readPerson where readPerson.uuid = "+loginUser.getUuid()+"))";
		long count = countByList("select count(*) "+hql, list);
		return count;
		
	}

	/**
	 * 取得我的会议list
	 * @param firstIndex
	 * @param pageSize
	 * @param dm
	 * @param requestDatas
	 * @return
	 * @throws Exception
	 */
	public List<TeeMeeting> getSummary(int firstIndex, int pageSize,TeeDataGridModel dm, Map requestDatas) throws Exception {
		String meetName = TeeStringUtil.getString(requestDatas.get("meetName"),"");
		String startTimeStr = TeeStringUtil.getString(requestDatas.get("startTimeStr"),"");
		String endTimeStr = TeeStringUtil.getString(requestDatas.get("endTimeStr"),"");
		int approveId = TeeStringUtil.getInteger(requestDatas.get("userId"), 0);
		int roomId = TeeStringUtil.getInteger(requestDatas.get("roomId"), 0);
		int status = TeeStringUtil.getInteger(requestDatas.get("status"), 0);
		TeePerson loginUser = (TeePerson)requestDatas.get("LOGIN_USER");
		String hql = "from TeeMeeting  meeting where  1 = 1  ";
		List list = new ArrayList();
		if(!TeeUtility.isNullorEmpty(meetName)){//会议名称
			hql = hql + " and meetName like  ?";
			list.add("%" + meetName + "%");
		}
		
		if(approveId>0){//申请人
			hql = hql + " and user.uuid = ?";
			list.add(approveId);
		}
		
		if(!TeeUtility.isNullorEmpty(startTimeStr)){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd",startTimeStr);
			hql = hql + " and startTime >= ?";
			list.add(date.getTime());
		}
		
		if(!TeeUtility.isNullorEmpty(endTimeStr)){//开始时间
			Date date = TeeUtility.parseDate("yyyy-MM-dd", endTimeStr);
			hql = hql + " and startTime <= ? ";
			list.add(date.getTime());
		}
		
		if(roomId>0){//会议室
			hql = hql + " and meetRoom.sid = ?";
			list.add(roomId);
		}
		
		if(status > 0){//状态
			hql = hql + " and status = ?";
			list.add(status  - 1);
		}
		if(TeeUtility.isNullorEmpty(dm.getSort())){
			dm.setSort("startTime");
			dm.setOrder("desc");
		}
		hql+=" and (recorder.uuid = "+loginUser.getUuid()+" or exists (select 1 from meeting.readPeoples readPerson where readPerson.uuid = "+loginUser.getUuid()+"))";
		
		hql = hql + " order by " + dm.getSort() + " " + dm.getOrder();
		return pageFindByList(hql, firstIndex, pageSize, list);
	}
	
	
	public  List<TeeMeeting> getMeetingByRoomId(int roomId) {
		String hql = "from TeeMeeting where meetRoom.sid="+roomId+" order by  createTime desc";
		List<TeeMeeting> list = (List<TeeMeeting>) executeQuery(hql,null);
		return list;
	}

	 
	/**
	 * 根据会议室 的id获取该会议室下未完成的会议列表
	 * @param sid
	 * @return
	 */
	public List<TeeMeeting> getUnfinishedMeeting(int sid) {
		String hql="from TeeMeeting m where m.status in (0,1,2) and m.meetRoom.sid=?";
		List<TeeMeeting> list=executeQuery(hql, new Object[]{sid});
		return list;
	}	
}
