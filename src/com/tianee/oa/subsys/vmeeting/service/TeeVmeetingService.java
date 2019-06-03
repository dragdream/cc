package com.tianee.oa.subsys.vmeeting.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.vmeeting.bean.Meeting;
import com.tianee.oa.subsys.vmeeting.model.MeetingModel;
import com.tianee.oa.subsys.vmeeting.synchronoususer.DeleteMeetin;
import com.tianee.oa.subsys.vmeeting.synchronoususer.ScheduledMeetin;
import com.tianee.oa.subsys.vmeeting.synchronoususer.UpdateMeetin;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeVmeetingService extends TeeBaseService{

	/**
	 * 
	 * @param tpm
	 * 同步用户信息
	 */
	/*public void tbyh() {
		String hql ="from TeePerson";
		@SuppressWarnings("unchecked")
		List<TeePerson> tpList=simpleDaoSupport.executeQuery(hql,null);
		//List<TeePersonModel> tpmList  = new ArrayList();
		for(TeePerson tp:tpList){
			try {
				TeePersonModel tpm=new TeePersonModel();
				BeanUtils.copyProperties(tp, tpm);
				//ReserverConf.reserConf(Integer.parseInt(tp.getDept().getGuid()),tp.getDept().getDeptName(),tp.getUuid(),"yes",3,tp.getByname(),Integer.parseInt(tp.getSex()),tp.getUserName(),tp.getPassword(),tp.getEmail(),Integer.parseInt(tp.getMobilNo()),Integer.parseInt(tp.getTelNoDept()));
				ReserverConf.reserConf(tp);
				//tpmList.add(tp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}*/

	/**
	 * 
	 * @param request
	 * @param model
	 * @param joiner
	 * 预约会议
	 */
	public TeeJson addMeetingInfo(HttpServletRequest request,
			MeetingModel model, String personIds) {
		TeeJson json = new TeeJson();
		try {
			Meeting mt = new Meeting();
			Date startTime=TeeDateUtil.parseDate(model.getStartT());
			Date endTime=TeeDateUtil.parseDate(model.getEndT());
			mt.setStartTime(startTime);
			mt.setEndTime(endTime);
			BeanUtils.copyProperties(model, mt);
			TeePerson person = (TeePerson) request.getSession().getAttribute(
					TeeConst.LOGIN_USER);
			
			//model.setUserId(String.valueOf(person.getUuid()));
			model.setUserId(Integer.toString(person.getUuid()));//Integer.toString（） 转换成String类型
			//处理申请人
			if(!TeeUtility.isNullorEmpty(model.getUserId())){//如果申请人ID不为空的话
				TeePerson regUser = new TeePerson();//实例化一个人员对象
				regUser.setUuid(Integer.parseInt(model.getUserId()));//将申请人ID作为regUser对象的主键
				mt.settPerson(regUser);//将申请人对象放入mt对象里面
			}
			
			//处理多个参会人
			if(!TeeUtility.isNullorEmpty(personIds)){//如果参会人ID字符串不为空的话
				String[] psIds = personIds.split(",");//通过逗号拆分
				for (String cyrid : psIds) {//遍历每一个ID
					TeePerson cyr = new TeePerson();//实例化一个参与人对象
					cyr.setUuid(Integer.parseInt(cyrid));//将参与人ID传入该对象的主键
					mt.getPersons().add(cyr);//给mt对象里面的persons集合加入单个参与人对象
				}
			}
			//预定会议借口
			//Map<String,String> map =ScheduledMeetin.pao(mt);
			//String kv=map.get("confid");
			
			//model.setMeetingNumber(kv);
			//mt.setMeetingNumber(model.getMeetingNumber());
		//	String key=map.get("code");
		//	if(key.equals("1")){
			//最后保存meeting对象
			simpleDaoSupport.save(mt);
			json.setRtState(true);
		//	}
			json.setRtState(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	/*	if(true){
			throw new TeeOperationException();
		}*/
		return json;
		
	}
	/**
	 * 转换成保存对象
	 * 
	 * @param model
	 * @param obj
	 * @return
	 *//*
	public Meeting parseObj(MeetingModel model, Meeting mt) {
		if (model == null) {
			return mt;
		} else {
			BeanUtils.copyProperties(model, mt);
			if (model.getUserUuid() != 0) {
				TeePerson user = new TeePerson();
				user.setUuid(model.getUserUuid());
				obj.setUser(user);
			}
			obj.setBeginTime(TeeDateUtil.format(model.getBeginTimeStr()));
			obj.setEndTime(TeeDateUtil.format(model.getEndTimeStr()));

			return mt;
		}
	}
	*/
	/**
	 * 会议列表
	 * leiqisheng
	 * @param userId 
	 * @param dm
	 * @param object
	 * @return
	 */
	public TeeEasyuiDataGridJson getMyMeetings(String uuid, TeeDataGridModel dm, MeetingModel model) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
			
			String hql = "from Meeting mt where exists (select 1 from mt.persons persons where persons.uuid="+uuid+")";//子查询；
			//判断会议名称是否为空
			if(!TeeUtility.isNullorEmpty(model.getMeetingName())){
				hql+=" and mt.meetingName like '%"+model.getMeetingName()+"%'";
			}
			//判断会议编号
			if(!TeeUtility.isNullorEmpty(model.getMeetingNumber())){
				hql+=" and mt.meetingNumber like '%"+model.getMeetingNumber()+"%'";
			}
			
			@SuppressWarnings("unchecked")
			List<Meeting> list =simpleDaoSupport.pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		
			List<MeetingModel> modelList = new ArrayList();
			int uid=0;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		    		 String dqrq=df.format(new Date());//当前系统时间
		    		 Date dqrqq=TeeDateUtil.parseDate(dqrq);
			for(Meeting meeting:list){
				MeetingModel mm = new MeetingModel();
				String s=df.format(meeting.getStartTime());
				String d=df.format(meeting.getEndTime());
				mm.setStartT(s);
				mm.setEndT(d);
				BeanUtils.copyProperties(meeting, mm);
				Date st=TeeDateUtil.parseDate(mm.getStartT());
				Date et=TeeDateUtil.parseDate(mm.getEndT());
				if(st.getTime()<dqrqq.getTime()&&et.getTime()>dqrqq.getTime()){
					mm.setTimeState(1);
				}else{
					mm.setTimeState(0);
				}
			//	System.out.println(mm.getTimeState());
			    uid =meeting.gettPerson().getUuid();
				TeePerson tp = (TeePerson) simpleDaoSupport.get(TeePerson.class, uid);
				mm.setUserName(tp.getUserName());
				modelList.add(mm);
			}
			/*for (MeetingModel meetingModel : modelList) {
				System.out.println(meetingModel.getMeetingId());
			}/**/
			long total = simpleDaoSupport.count("select count(*) "+hql, null);
			dataGridJson.setRows(modelList);
			dataGridJson.setTotal(total);
			return dataGridJson;
		}
	/**
	 * 根据id取出对应数据
	 * @param meetingId
	 * @return
	 */
	public MeetingModel getMeetingById(String meetingId) {
		Meeting mt =  (Meeting) simpleDaoSupport.get(Meeting.class, meetingId); 
		MeetingModel model = new MeetingModel();
		BeanUtils.copyProperties(mt, model);
		model.setMeetingNumber(mt.getMeetingNumber());
		model.setConfuserPwd(mt.getConfuserPwd());
		model.setMeetingName(mt.getMeetingName());
		return model;
	
	}
	/**
	 * 根据当前用户id查出对应会议数据
	 * leiqisheng
	 * @param userId
	 * @param dm
	 * @param object
	 * @return
	 */
	
	public TeeEasyuiDataGridJson getVmeetings(String userId,
			TeeDataGridModel dm, MeetingModel mm) {
	TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
	String hql="from Meeting m where m.tPerson.uuid="+userId;
	//判断会议名称是否为空
	if(!TeeUtility.isNullorEmpty(mm.getMeetingName())){
		hql+=" and m.meetingName like '%"+mm.getMeetingName()+"%'";
	}
	//判断会议编号
	if(!TeeUtility.isNullorEmpty(mm.getMeetingNumber())){
		hql+=" and m.meetingNumber like '%"+mm.getMeetingNumber()+"%'";
	}
	
	@SuppressWarnings("unchecked")
	List<Meeting> list =simpleDaoSupport.pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
	
	List<MeetingModel> modelList = new ArrayList();
	int uid=0;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
	 String dqrq=df.format(new Date());//当前系统时间
	 Date dqrqq=TeeDateUtil.parseDate(dqrq);
	for(Meeting meeting:list){
		MeetingModel model = new MeetingModel();
		String s=df.format(meeting.getStartTime());
		String d=df.format(meeting.getEndTime());
		model.setStartT(s);
		model.setEndT(d);
		BeanUtils.copyProperties(meeting, model);
		Date st=TeeDateUtil.parseDate(model.getStartT());
		Date et=TeeDateUtil.parseDate(model.getEndT());
		if(st.getTime()<dqrqq.getTime()&&et.getTime()>dqrqq.getTime()){
			model.setTimeState(1);
		}else{
			model.setTimeState(0);
		}
	    uid =meeting.gettPerson().getUuid();
		TeePerson tp = (TeePerson) simpleDaoSupport.get(TeePerson.class, uid);
		model.setUserName(tp.getUserName());
		model.setPassword(tp.getPassword());
		modelList.add(model);
	}
	long total = simpleDaoSupport.count("select count(*) "+hql, null);
	dataGridJson.setRows(modelList);
	dataGridJson.setTotal(total);
	return dataGridJson;
	}
	
	
	/**
	 * 根据id删除数据
	 * leiqisheng
	 * @param meetingId
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	public MeetingModel delMeetingInfo(String meetingId) throws IOException {
		
		  Meeting mt = (Meeting) simpleDaoSupport.get(Meeting.class, meetingId);
		  MeetingModel mtm = new MeetingModel();
			BeanUtils.copyProperties(mt, mtm);
			
		  if(mt.getMeetingId()!=null||mt.getMeetingId()==""){
			Map<String,String> map=DeleteMeetin.del(mt);
		String aa =map.get("code");
		if(aa.equals("1")){
			  simpleDaoSupport.deleteByObj(mt);
		}
		  }
		return mtm;
	}
	
	
	/**
	 * 根据id查对象(修改)
	 *leiqisheng
	 * @param meetingId
	 * @return
	 */
	public MeetingModel getMeeting(String meetingId) {
		Meeting mt = (Meeting) simpleDaoSupport.get(Meeting.class, meetingId);
		MeetingModel model = new MeetingModel();
		String s=TeeDateUtil.format(mt.getStartTime());
		String d=TeeDateUtil.format(mt.getEndTime());
		model.setStartT(s);
		model.setEndT(d);
		BeanUtils.copyProperties(mt, model);
	   List<TeePerson> list = mt.getPersons(); 
	   String ids = "";
	   String names = "";
	    for (TeePerson lists:list) {
	    	ids+=lists.getUuid()+",";
	    	names+=lists.getUserName()+",";
		}
	    model.setPersonIds(ids);
    	model.setPersonNames(names);
		return model;
	}
	
	
	/**
	 * 执行修改
	 * leiqisheng
	 * @param model
	 * @throws IOException 
	 */
			public void updateMeeting(MeetingModel model) throws IOException {
				 //先查实体类
				Meeting mt = (Meeting)simpleDaoSupport.get(Meeting.class, model.getMeetingId());
				
  				Map<String,String> map=UpdateMeetin.upd(mt);
  				String key=map.get("code");
  				if(key.equals("1")){
  				//再往实体类中set对应的属性值
                    mt.setMeetingName(model.getMeetingName());//会议名称
                    Date startTime=TeeDateUtil.parseDate(model.getStartT());
        			Date endTime=TeeDateUtil.parseDate(model.getEndT());
                    mt.setStartTime(startTime);//开始时间
                    mt.setEndTime(endTime);//结束时间
                    mt.setAttendNum(model.getAttendNum());//会议数量
                    mt.setMaxvideo(model.getMaxvideo());//视频数量
                    mt.setMaxPersonspeak(model.getMaxPersonspeak());//法人数量
                    mt.setContent(model.getContent());//会议内容
                    
                    String[] psIds = model.getPersonIds().split(",");//通过逗号拆分
                      mt.getPersons().clear();//清空
    				for (String cyrid : psIds) {//遍历每一个ID
    					TeePerson cyr = new TeePerson();//实例化一个参与人对象
    					cyr.setUuid(Integer.parseInt(cyrid));//将参与人ID传入该对象的主键
    					mt.getPersons().add(cyr);//给mt对象里面的persons集合加入单个参与人对象
    				}
  					//执行更新
  					simpleDaoSupport.update(mt);
  				}
			}



}

