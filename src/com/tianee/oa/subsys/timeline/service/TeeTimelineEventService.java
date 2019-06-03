package com.tianee.oa.subsys.timeline.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.timeline.bean.TeeTimeline;
import com.tianee.oa.subsys.timeline.bean.TeeTimelineEvent;
import com.tianee.oa.subsys.timeline.dao.TeeTimelineDao;
import com.tianee.oa.subsys.timeline.dao.TeeTimelineEventDao;
import com.tianee.oa.subsys.timeline.model.TeeTimelineEventModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class TeeTimelineEventService extends TeeBaseService{
	@Autowired
	private TeeTimelineEventDao timelineEventDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	
	@Autowired
	private TeeTimelineDao timelineDao;
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeTimelineEventModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeTimelineEvent timelineEvent = new TeeTimelineEvent();
		SimpleDateFormat sf =new  SimpleDateFormat("yyyy-MM-dd");
		Calendar scl = Calendar.getInstance();
		Calendar ecl = Calendar.getInstance();
		if(!TeeUtility.isNullorEmpty(model.getStartTimeDesc())){
			try {
				scl.setTime(sf.parse(model.getStartTimeDesc()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(model.getEndTimeDesc())){
			try {
				ecl.setTime(sf.parse(model.getEndTimeDesc()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(model.getUuid()) && !"0".equals(model.getUuid())){
		    timelineEvent  = timelineEventDao.getById(model.getUuid());
			if(timelineEvent != null){
				BeanUtils.copyProperties(model, timelineEvent);
				timelineEvent.setUpdateUser(person);
				if(!TeeUtility.isNullorEmpty(model.getStartTimeDesc())){
					timelineEvent.setStartTime(scl);
				}
				if(!TeeUtility.isNullorEmpty(model.getEndTimeDesc())){
					timelineEvent.setEndTime(ecl);
				}
				if(!TeeUtility.isNullorEmpty(model.getTimelineUuid())){
					TeeTimeline timeline = timelineDao.get(model.getTimelineUuid());
					timelineEvent.setTimeline(timeline);
				}
				timelineEventDao.updateTimelineEvent(timelineEvent);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关大事件信息！");
				return json;
			}
		}else{
			Calendar cl = Calendar.getInstance();
			BeanUtils.copyProperties(model, timelineEvent);
			timelineEvent.setCrTime(cl);
			timelineEvent.setCrUser(person);
			if(!TeeUtility.isNullorEmpty(model.getStartTimeDesc())){
				timelineEvent.setStartTime(scl);
			}
			if(!TeeUtility.isNullorEmpty(model.getEndTimeDesc())){
				timelineEvent.setEndTime(ecl);
			}
			if(!TeeUtility.isNullorEmpty(model.getTimelineUuid())){
				TeeTimeline timeline = timelineDao.get(model.getTimelineUuid());
				timelineEvent.setTimeline(timeline);
			}
			timelineEventDao.addTimelineEvent(timelineEvent);
		}
		
		//附件处理
		String dbAttachSid = TeeStringUtil.getString(request.getParameter("dbAttachSid"), "0");
		String attachmentSidStr = TeeStringUtil.getString(request.getParameter("attachmentSidStr") ,"0");
		List<TeeAttachment> attachAll = new ArrayList<TeeAttachment>();
		List<TeeAttachment> dbAttachSidList = attachmentDao.getAttachmentsByIds(dbAttachSid);
		List<TeeAttachment> attachments = attachmentDao.getAttachmentsByIds(attachmentSidStr);
		attachAll.addAll(dbAttachSidList);
		attachAll.addAll(attachments);
		for(TeeAttachment attach:attachAll){
			attach.setModelId(timelineEvent.getUuid());
			simpleDaoSupport.update(attach);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 对象转换
	 * @author nieyi
	 * @param timelineEvent
	 * @return
	 */
	public TeeTimelineEventModel parseModel(TeeTimelineEvent timelineEvent){
		TeeTimelineEventModel model = new TeeTimelineEventModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if(timelineEvent == null){
			return null;
		}
		BeanUtils.copyProperties(timelineEvent, model);
		if(!TeeUtility.isNullorEmpty(timelineEvent.getStartTime())){
			model.setStartTimeDesc(sf.format(timelineEvent.getStartTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timelineEvent.getEndTime())){
			model.setEndTimeDesc(sf.format(timelineEvent.getEndTime().getTime()));
		}
		//处理附件,获取附件
		List<TeeAttachment> attaches = attachmentDao.getAttaches("timelineEvent", String.valueOf(model.getUuid()));
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:attaches){
			TeeAttachmentModel m = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, m);
			m.setUserId(attach.getUser().getUuid()+"");
			m.setUserName(attach.getUser().getUserName());
			m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(m);
		}
		model.setAttacheModels(attachmodels);
		return model;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request,String sids) {
		TeeJson json = new TeeJson();
		timelineEventDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeTimelineEventModel model) {
		TeeJson json = new TeeJson();
		if(!TeeUtility.isNullorEmpty(model.getUuid())){
			TeeTimelineEvent timelineEvent = timelineEventDao.getById(model.getUuid());
			if(timelineEvent !=null){
				model = parseModel(timelineEvent);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关主线记录！");
		return json;
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		return timelineEventDao.datagird(dm, requestDatas);
	}

	public TeeJson getEvent(Map requestDatas) {
		TeeJson json = new TeeJson();
		List<TeeTimelineEventModel> list = timelineEventDao.getEvent(requestDatas);
		if(null!=list){
			json.setRtData(list);
			json.setRtState(true);
			json.setRtMsg("查询成功!");
			return json;
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关主线记录！");
		return json;
	}
}