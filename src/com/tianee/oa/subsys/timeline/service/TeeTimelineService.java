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
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.subsys.timeline.bean.TeeTimeline;
import com.tianee.oa.subsys.timeline.dao.TeeTimelineDao;
import com.tianee.oa.subsys.timeline.model.TeeTimelineModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Service
public class TeeTimelineService extends TeeBaseService{
	@Autowired
	private TeeTimelineDao timelineDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeUserRoleDao roleDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;


	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeTimelineModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeTimeline timeline = new TeeTimeline();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		int[] viewUserIds = TeeStringUtil.parseIntegerArray(model.getViewUserIds());
		int[] viewDeptIds = TeeStringUtil.parseIntegerArray(model.getViewDeptIds());
		int[] viewRoleIds = TeeStringUtil.parseIntegerArray(model.getViewRoleIds());
		int[] postUserIds = TeeStringUtil.parseIntegerArray(model.getPostUserIds());
		int[] postDeptIds = TeeStringUtil.parseIntegerArray(model.getPostDeptIds());
		int[] postRoleIds = TeeStringUtil.parseIntegerArray(model.getPostRoleIds());
		String startTimeDesc = model.getStartTimeDesc();
		String endTimeDesc = model.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(model.getUuid()) &&! model.getUuid().equals("0")){
		    timeline  = timelineDao.getById(model.getUuid());
			if(timeline != null){
				Calendar cl = Calendar.getInstance();
				BeanUtils.copyProperties(model, timeline);
				timeline.setUpdateUser(person);
				timeline.setUpdateTime(cl);
				timeline.getViewUser().clear();
				for(int uuid:viewUserIds){
					if(uuid==0){
						continue;
					}
					timeline.getViewUser().add((TeePerson)personDao.get(uuid));
				}
				timeline.getViewDept().clear();
				for(int uuid:viewDeptIds){
					if(uuid==0){
						continue;
					}
					timeline.getViewDept().add((TeeDepartment)deptDao.get(uuid));
				}
				timeline.getViewRole().clear();
				for(int uuid:viewRoleIds){
					if(uuid==0){
						continue;
					}
					timeline.getViewRole().add((TeeUserRole)roleDao.get(uuid));
				}
				timeline.getPostUser().clear();
				for(int uuid:postUserIds){
					if(uuid==0){
						continue;
					}
					timeline.getPostUser().add((TeePerson)personDao.get(uuid));
				}
				timeline.getPostDept().clear();
				for(int uuid:postDeptIds){
					if(uuid==0){
						continue;
					}
					timeline.getPostDept().add((TeeDepartment)deptDao.get(uuid));
				}
				timeline.getPostRole().clear();
				for(int uuid:postRoleIds){
					if(uuid==0){
						continue;
					}
					timeline.getPostRole().add((TeeUserRole)roleDao.get(uuid));
				}
				if(!TeeUtility.isNullorEmpty(startTimeDesc)){
					Calendar sl = Calendar.getInstance();
					try {
						sl.setTime(sf.parse(startTimeDesc));
						timeline.setStartTime(sl);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(!TeeUtility.isNullorEmpty(endTimeDesc)){
					Calendar el = Calendar.getInstance();
					try {
						el.setTime(sf.parse(endTimeDesc));
						timeline.setEndTime(el);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				timelineDao.updateTimeline(timeline);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关大事件信息！");
				return json;
			}
		}else{
			Calendar cl = Calendar.getInstance();
			BeanUtils.copyProperties(model, timeline);
			timeline.setCrTime(cl);
			timeline.setCrUser(person);
			for(int uuid:viewUserIds){
				if(uuid==0){
					continue;
				}
				timeline.getViewUser().add((TeePerson)personDao.get(uuid));
			}
			for(int uuid:viewDeptIds){
				if(uuid==0){
					continue;
				}
				timeline.getViewDept().add((TeeDepartment)deptDao.get(uuid));
			}
			for(int uuid:viewRoleIds){
				if(uuid==0){
					continue;
				}
				timeline.getViewRole().add((TeeUserRole)roleDao.get(uuid));
			}
			for(int uuid:postUserIds){
				if(uuid==0){
					continue;
				}
				timeline.getPostUser().add((TeePerson)personDao.get(uuid));
			}
			for(int uuid:postDeptIds){
				if(uuid==0){
					continue;
				}
				timeline.getPostDept().add((TeeDepartment)deptDao.get(uuid));
			}
			for(int uuid:postRoleIds){
				if(uuid==0){
					continue;
				}
				timeline.getPostRole().add((TeeUserRole)roleDao.get(uuid));
			}
			if(!TeeUtility.isNullorEmpty(startTimeDesc)){
				Calendar sl = Calendar.getInstance();
				try {
					sl.setTime(sf.parse(startTimeDesc));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				timeline.setStartTime(sl);
			}
			if(!TeeUtility.isNullorEmpty(endTimeDesc)){
				Calendar el = Calendar.getInstance();
				try {
					el.setTime(sf.parse(endTimeDesc));
					timeline.setEndTime(el);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			timelineDao.addTimeline(timeline);
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
			attach.setModelId(timeline.getUuid());
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
	 * @param timeline
	 * @return
	 */
	public TeeTimelineModel parseModel(TeeTimeline timeline){
		TeeTimelineModel model = new TeeTimelineModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if(timeline == null){
			return null;
		}
		BeanUtils.copyProperties(timeline, model);
		if(!TeeUtility.isNullorEmpty(timeline.getViewUser())){
			String viewUserIds="";
			String viewUserNames="";
			for(TeePerson person:timeline.getViewUser()){
				viewUserIds += person.getUuid()+",";
				viewUserNames+=person.getUserName()+",";
			}
			model.setViewUserIds(viewUserIds);
			model.setViewUserNames(viewUserNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getViewDept())){
			String viewDeptIds="";
			String viewDeptNames="";
			for(TeeDepartment dept:timeline.getViewDept()){
				viewDeptIds += dept.getUuid()+",";
				viewDeptNames+=dept.getDeptName()+",";
			}
			model.setViewDeptIds(viewDeptIds);
			model.setViewDeptNames(viewDeptNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getViewRole())){
			String viewRoleIds="";
			String viewRoleNames="";
			for(TeeUserRole role:timeline.getViewRole()){
				viewRoleIds += role.getUuid()+",";
				viewRoleNames+=role.getRoleName()+",";
			}
			model.setViewRoleIds(viewRoleIds);
			model.setViewRoleNames(viewRoleNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getPostUser())){
			String postUserIds="";
			String postUserNames="";
			for(TeePerson person:timeline.getPostUser()){
				postUserIds += person.getUuid()+",";
				postUserNames+=person.getUserName()+",";
			}
			model.setPostUserIds(postUserIds);
			model.setPostUserNames(postUserNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getPostDept())){
			String postDeptIds="";
			String postDeptNames="";
			for(TeeDepartment dept:timeline.getPostDept()){
				postDeptIds += dept.getUuid()+",";
				postDeptNames+=dept.getDeptName()+",";
			}
			model.setPostDeptIds(postDeptIds);
			model.setPostDeptNames(postDeptNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getPostRole())){
			String postRoleIds="";
			String postRoleNames="";
			for(TeeUserRole role:timeline.getPostRole()){
				postRoleIds += role.getUuid()+",";
				postRoleNames +=role.getRoleName()+",";
			}
			model.setPostRoleIds(postRoleIds);
			model.setPostRoleNames(postRoleNames);
		}
		if(!TeeUtility.isNullorEmpty(timeline.getStartTime())){
			model.setStartTimeDesc(sf.format(timeline.getStartTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timeline.getEndTime())){
			model.setEndTimeDesc(sf.format(timeline.getEndTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timeline.getCrTime())){
			model.setCrTimeDesc(sf.format(timeline.getCrTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timeline.getUpdateTime())){
			model.setUpdateTimeDesc(sf.format(timeline.getUpdateTime().getTime()));
		}
		if(!TeeUtility.isNullorEmpty(timeline.getCrUser())){
			model.setCrUserId(timeline.getCrUser().getUuid());
			model.setCrUserName(timeline.getCrUser().getUserName());
		}
		if(!TeeUtility.isNullorEmpty(timeline.getUpdateUser())){
			model.setUpdateUserId(timeline.getUpdateUser().getUuid());
			model.setUpdateUserName(timeline.getUpdateUser().getUserName());
		}
		if(!TeeUtility.isNullorEmpty(model.getType())){
			String typeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("TIMELINE_TYPE",model.getType());
			model.setTypeDesc(typeDesc);
		}
		
		//处理附件,获取附件
		List<TeeAttachment> attaches = attachmentDao.getAttaches("timeline", String.valueOf(model.getUuid()));
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
		timelineDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功！");
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeTimelineModel model) {
		TeeJson json = new TeeJson();
		if(!TeeUtility.isNullorEmpty(model.getUuid())){
			TeeTimeline timeline = timelineDao.getById(model.getUuid());
			if(timeline !=null){
				model = parseModel(timeline);
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
		return timelineDao.datagird(dm, requestDatas);
	}

	public List<TeeTimelineModel> getTotalByConditon(Map requestDatas) {
		return timelineDao.getTotalByConditon(requestDatas);
	}
	
	/**
	 * 判断当前登录人是否有访问权限
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getVisitPriv(HttpServletRequest request, TeeTimelineModel model) {
		TeeJson json = new TeeJson();
		TeePerson person =(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		boolean visitPriv=false;
		if(!TeeUtility.isNullorEmpty(model.getUuid())){
			TeeTimeline timeline = timelineDao.getById(model.getUuid());
			if(timeline !=null){
				for(TeePerson user:timeline.getViewUser()){
					if(user.getUuid()==person.getUuid()){
						visitPriv = true;
					}
				}
				for(TeeDepartment dept:timeline.getViewDept()){
					if(dept.getUuid()==person.getDept().getUuid()){
						visitPriv = true;
					}
				}
				for(TeeUserRole role:timeline.getViewRole()){
					if(role.getUuid()==person.getUserRole().getUuid()){
						visitPriv = true;
					}
				}
				json.setRtData(visitPriv);
				json.setRtState(true);
				json.setRtMsg("访问权限判断成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("没有相关大事记！");
		return json;
	}
	
	/**
	 * 判断当前登陆人是否有管理权限
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getManagePriv(HttpServletRequest request, TeeTimelineModel model) {
		TeeJson json = new TeeJson();
		TeePerson person =(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		boolean managePriv=false;
		if(!TeeUtility.isNullorEmpty(model.getUuid())){
			TeeTimeline timeline = timelineDao.getById(model.getUuid());
			if(timeline !=null){
				for(TeePerson user:timeline.getPostUser()){
					if(user.getUuid()==person.getUuid()){
						managePriv = true;
					}
				}
				for(TeeDepartment dept:timeline.getPostDept()){
					if(dept.getUuid()==person.getDept().getUuid()){
						managePriv = true;
					}
				}
				for(TeeUserRole role:timeline.getPostRole()){
					if(role.getUuid()==person.getUserRole().getUuid()){
						managePriv = true;
					}
				}
				
				if(timeline.getCrUser().getUuid()==person.getUuid() || timeline.getUpdateUser().getUuid()==person.getUuid()){
					managePriv = true;
				}
				json.setRtData(managePriv);
				json.setRtState(true);
				json.setRtMsg("管理权限判断成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("没有相关大事记！");
		return json;
	}
}