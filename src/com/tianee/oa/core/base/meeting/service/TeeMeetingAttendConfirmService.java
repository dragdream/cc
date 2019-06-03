package com.tianee.oa.core.base.meeting.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.meeting.bean.TeeMeetingAttendConfirm;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingAttendConfirmDao;
import com.tianee.oa.core.base.meeting.model.TeeMeetingAttendConfirmModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class TeeMeetingAttendConfirmService extends TeeBaseService {
	
	@Autowired
	private TeeMeetingAttendConfirmDao dao;
	
	@Autowired
	private TeePersonDao personDao;
	
	
	/** 
	 * 新建
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月29日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 * @throws ParseException 
	 */
	public TeeJson addObj(Map requestMap, TeePerson loginPerson, TeeMeetingAttendConfirmModel model) throws ParseException{
		TeeJson json = new TeeJson();
		TeeMeetingAttendConfirm obj = new TeeMeetingAttendConfirm();
		obj.setAttendFlag(model.getAttendFlag());
		if(!TeeUtility.isNullorEmpty(model.getConfirmTimeStr())){
			obj.setConfirmTime(TeeUtility.parseDate(model.getConfirmTimeStr()));
		}
		obj.setCreateTime(new Date());
		obj.setCreateUser(loginPerson);
		obj.setMeetingId(model.getMeetingId());
		obj.setReadFlag(model.getReadFlag());
		if(!TeeUtility.isNullorEmpty(model.getReadingTimeStr())){
			obj.setReadingTime(TeeUtility.parseDate(model.getReadingTimeStr()));
		}
		obj.setRemark(model.getRemark());
		
		dao.save(obj);
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}
	
	/**
	 * 编辑
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月29日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 * @throws ParseException 
	 */
	public TeeJson updateObj(Map requestMap, TeePerson loginPerson, TeeMeetingAttendConfirmModel model) throws ParseException{
		TeeJson json = new TeeJson();
		if(model.getSid()>0){
			TeeMeetingAttendConfirm obj = dao.get(model.getSid());
			obj.setAttendFlag(model.getAttendFlag());
			if(!TeeUtility.isNullorEmpty(model.getConfirmTimeStr())){
				obj.setConfirmTime(TeeUtility.parseDate(model.getConfirmTimeStr()));
			}
			obj.setCreateTime(new Date());
			obj.setCreateUser(loginPerson);
			obj.setMeetingId(model.getMeetingId());
			obj.setReadFlag(model.getReadFlag());
			if(!TeeUtility.isNullorEmpty(model.getReadingTimeStr())){
				obj.setReadingTime(TeeUtility.parseDate(model.getReadingTimeStr()));
			}
			obj.setRemark(model.getRemark());
			
			dao.update(obj);
		}
		
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		
		return json;
	}
	
	
	/**
	 * 更改参会或缺席状态
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月29日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 * @throws ParseException 
	 */
	public TeeJson updateAttendFlag(Map requestMap, TeePerson loginPerson, TeeMeetingAttendConfirmModel model) throws ParseException{
		TeeJson json = new TeeJson();
		int meetingId = TeeStringUtil.getInteger(requestMap.get("meetingId"), 0);
		int attendFlag = TeeStringUtil.getInteger(requestMap.get("attendFlag"), 0);
		String remark = TeeUtility.null2Empty((String)requestMap.get("remark"));
		
		
		List<TeeMeetingAttendConfirm> list = dao.getObjList(meetingId, loginPerson);
		if(list != null && list.size()>0){
			TeeMeetingAttendConfirm obj = list.get(0);
			if(obj!= null){
				obj.setAttendFlag(attendFlag);
				obj.setConfirmTime(new Date());
				if(!TeeUtility.isNullorEmpty(remark)){
					obj.setRemark(remark);
				}
				dao.update(obj);
			}
		}
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		
		return json;
	}
	
	
	/**
	 * 是否确认参会
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月29日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 * @throws ParseException 
	 */
	public TeeJson isConfirmFlag(Map requestMap, TeePerson loginPerson, TeeMeetingAttendConfirmModel model) throws ParseException{
		TeeJson json = new TeeJson();
		int meetingId = TeeStringUtil.getInteger(requestMap.get("meetingId"), 0);
		List<TeeMeetingAttendConfirm> list = dao.getConfrimObjList(meetingId, loginPerson);
		int confirmFlag = 0;
		if(list != null && list.size()>0){
			confirmFlag = 1;
		}
		Map map = new HashMap();
		map.put("confirmFlag", confirmFlag);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取会议参会情况
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月31日
	 * @param requestMap
	 * @param person
	 * @return TeeJson
	 */
	public TeeJson showMeetingAttendInfo(Map requestMap, TeePerson person)  {
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		int meetingId = TeeStringUtil.getInteger(requestMap.get("meetingId"), 0);
		
		List<TeeMeetingAttendConfirm> list = dao.getObjList(meetingId) ;
		if(list != null && list.size()>0){
			for(TeeMeetingAttendConfirm obj:list){
				Map<String,String> map = new HashMap<String, String>();
				String confirmTime = "";
				if(!TeeUtility.isNullorEmpty(obj.getConfirmTime())){
					confirmTime = TeeUtility.getDateTimeStr(obj.getConfirmTime());
				}
				map.put("userName", obj.getCreateUser().getUserName());
				map.put("deptName", obj.getCreateUser().getDept().getDeptName());
				map.put("roleName", obj.getCreateUser().getUserRole().getRoleName());
				map.put("attendFlag", String.valueOf(obj.getAttendFlag()));
				map.put("confirmTime", confirmTime);
				map.put("remark", TeeUtility.null2Empty(obj.getRemark()));
				mapList.add(map);
			}
		}
		TeeJson json = new TeeJson();
		json.setRtData(mapList);
		json.setRtState(true);
		json.setRtMsg("数据获取成功!");
		return json;
	}
	/**
	 * 获取会议签阅情况
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月31日
	 * @param requestMap
	 * @param person
	 * @return TeeJson
	 */
	public TeeJson showMeetingReadInfo(Map requestMap, TeePerson person)  {
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		int meetingId = TeeStringUtil.getInteger(requestMap.get("meetingId"), 0);
		
		List<TeeMeetingAttendConfirm> list = dao.getObjList(meetingId) ;
		if(list != null && list.size()>0){
			for(TeeMeetingAttendConfirm obj:list){
				Map<String,String> map = new HashMap<String, String>();
				String readingTime = "";
				if(!TeeUtility.isNullorEmpty(obj.getReadingTime())){
					readingTime = TeeUtility.getDateTimeStr(obj.getReadingTime());
				}
				map.put("userName", obj.getCreateUser().getUserName());
				map.put("deptName", obj.getCreateUser().getDept().getDeptName());
				map.put("roleName", obj.getCreateUser().getUserRole().getRoleName());
				map.put("readFlag", String.valueOf(obj.getReadFlag()));
				map.put("readingTime", readingTime);
				mapList.add(map);
			}
		}
		TeeJson json = new TeeJson();
		json.setRtData(mapList);
		json.setRtState(true);
		json.setRtMsg("数据获取成功!");
		return json;
	}
	
	
	/**
	 * 更改会议签阅状态
	 * @function: 
	 * @data: 2015年10月29日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 * @throws ParseException 
	 */
	public TeeJson updateReadFlag(Map requestMap, TeePerson loginPerson, TeeMeetingAttendConfirmModel model) throws ParseException{
		TeeJson json = new TeeJson();
		int meetingId = TeeStringUtil.getInteger(requestMap.get("meetingId"), 0);
		
		List<TeeMeetingAttendConfirm> list = dao.getReadObjList(meetingId, loginPerson);
		if(list != null && list.size()>0){
			TeeMeetingAttendConfirm obj = list.get(0);
			if(obj!= null){
				obj.setReadFlag(1);
				obj.setReadingTime(new Date());
				dao.update(obj);
			}
		}
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		
		return json;
	}
	
	/**
	 * 是否已签阅
	 * @function: 
	 * @author: wyw
	 * @data: 2015年10月29日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return TeeJson
	 * @throws ParseException 
	 */
	public TeeJson isReadFlag(Map requestMap, TeePerson loginPerson, TeeMeetingAttendConfirmModel model) throws ParseException{
		TeeJson json = new TeeJson();
		int meetingId = TeeStringUtil.getInteger(requestMap.get("meetingId"), 0);
		List<TeeMeetingAttendConfirm> list = dao.isReadObjList(meetingId, loginPerson);
		int readFlag = 0;
		if(list != null && list.size()>0){
			readFlag = 1;
		}
		Map map = new HashMap();
		map.put("readFlag", readFlag);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	

}
