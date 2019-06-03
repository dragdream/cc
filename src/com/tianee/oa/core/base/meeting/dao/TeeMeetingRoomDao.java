package com.tianee.oa.core.base.meeting.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.meeting.bean.TeeMeeting;
import com.tianee.oa.core.base.meeting.bean.TeeMeetingRoom;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.base.meeting.model.TeeMeetingRoomModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Repository("meetingRoomDao")
public class TeeMeetingRoomDao  extends TeeBaseDao<TeeMeetingRoom> {
	/**
	 * @author syl
	 * 增加
	 * @param TeeMeetingRoom
	 */
	public void addAttendOut(TeeMeetingRoom attendOut) {
		save(attendOut);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeMeetingRoom
	 */
	public void updateAttendOut(TeeMeetingRoom attendOut) {
		update(attendOut);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeMeetingRoom loadById(int id) {
		TeeMeetingRoom intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeMeetingRoom getById(int id) {
		TeeMeetingRoom intf = get(id);
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
		String hql = "delete from TeeMeetingRoom ";
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
			String hql = "delete from TeeMeetingRoom where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeMeetingRoom> getAllRoom(TeePerson person , TeeMeetingRoomModel model) {
		Object[] values = { };
		String hql = "from TeeMeetingRoom order by mrName";
		List<TeeMeetingRoom> list = (List<TeeMeetingRoom>) executeQuery(hql,values);
		return list;
	}	
	
	/**
	 * 分页查询
	 * @author wgg
	 * @date 2016-11-2
	 * @param firstResult
	 * @param pageSize
	 * @param dm
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	public  List<TeeMeetingRoom> getAllRoomPageFind(TeePerson person ,int firstResult,int pageSize,TeeDataGridModel dm ,TeeMeetingRoomModel model) throws ParseException { 
		String hql = "from TeeMeetingRoom order by mrName";
		List list = new ArrayList();
		return pageFindByList(hql, firstResult, pageSize, list);
	}
	
	/**
	 * @author wgg
	 * 通用列表        查询数量
	 * @param 
	 * @throws ParseException 
	 */
	public  long getCountAllRoom(TeePerson person , TeeMeetingRoomModel model) throws ParseException {
		List list = new ArrayList();
		String hql = "select count(sid) from TeeMeetingRoom order by mrName";
		long count = countByList(hql, list);
		return count;
	}
	
	/**
	 * @author syl
	 * 查询所有记录可以管理的会议室
	 * @param 
	 */
	public  List<TeeMeetingRoom> getManagerAllRoom(TeePerson person , TeeMeetingRoomModel model) {
		Object[] values = {"%," +person.getUuid() + ",%"};
		String hql = "from TeeMeetingRoom where   concat(',' , managerIds) || ','  like ?  order by mrName";
		List<TeeMeetingRoom> list = (List<TeeMeetingRoom>) executeQuery(hql,values);
		return list;
	}	
	/**
	 * @author syl
	 * 查询所有记录  ---  带申请权限
	 * @param 
	 */
	public  List<TeeMeetingRoom> selectPostMeetRoom(TeePerson person , TeeMeetingRoomModel model,int type) {
		List list1=new ArrayList();
		list1.add(person.getDept());
		list1.add( person);
		
		String hql="";
		if(type!=0){
			hql = "from TeeMeetingRoom  mr where  ( exists (select 1 from mr.postDept dept where dept =?) or exists (select 1 from mr.postUser user where user= ? )) and mr.type=?  order by mr.mrName";
			list1.add(type);
		}else{
			hql = "from TeeMeetingRoom  mr where  ( exists (select 1 from mr.postDept dept where dept =?) or exists (select 1 from mr.postUser user where user= ? ))  order by mr.mrName";
		}
		
		List<TeeMeetingRoom> list = (List<TeeMeetingRoom>) executeQuery(hql,list1.toArray());
		return list;
	}	
	
	
}
