package com.tianee.oa.subsys.topic.service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.subsys.topic.bean.TeeTopic;
import com.tianee.oa.subsys.topic.bean.TeeTopicSection;
import com.tianee.oa.subsys.topic.dao.TeeTopicDao;
import com.tianee.oa.subsys.topic.dao.TeeTopicReplyDao;
import com.tianee.oa.subsys.topic.dao.TeeTopicSectionDao;
import com.tianee.oa.subsys.topic.model.TeeTopicModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeTopicService extends TeeBaseService {
	@Autowired
	private TeeTopicDao dao;
	@Autowired
	private TeeTopicSectionDao sectionDao;
	@Autowired
	private TeeTopicReplyDao topicReplyDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeDeptDao deptDao;
	@Autowired
	private TeeAttachmentDao attachmentDao;
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeSmsManager smsManager;

	/**
	 * @function: 新建数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public TeeJson addObj(Map requestMap, TeePerson loginPerson, TeeTopicModel model) throws ParseException {
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(model.getTopicSectionId())) {
			List<TeeAttachment> listAttachments = new ArrayList<TeeAttachment>();
			if (!TeeUtility.isNullorEmpty(model.getAttacheIds())) {
				listAttachments = attachmentService.getAttachmentsByIds(model.getAttacheIds());
			}
			TeeTopic obj = new TeeTopic();
			obj.setAnonymous(model.getAnonymous());
			obj.setSubject(model.getSubject());
			obj.setContent(model.getContent());
			obj.setSection(sectionDao.get(model.getTopicSectionId()));

			obj.setFlag(1);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			obj.setCrTime(calendar);
			obj.setCrUser(loginPerson);
			obj.setLastReplyUser(loginPerson);
			obj.setLastReplyTime(calendar);
			dao.save(obj);
			for (TeeAttachment attach : listAttachments) {
				attach.setModelId(obj.getUuid() + "");
				simpleDaoSupport.update(attach);
			}
			
			TeeTopicSection section = obj.getSection();
			int newTopicSmsPriv = section.getNewTopicSmsPriv();
			int viewPrivAllFlag = section.getViewPrivAllFlag(); 
			if(newTopicSmsPriv ==1){
				Map requestData = new HashMap();
				requestData.put("content", "[" + loginPerson.getUserName() + "]" + " 新发了帖子：" + obj.getSubject());
				requestData.put("moduleNo","050");
				if(viewPrivAllFlag == 1){// 版块可见人员标记 1：全部 0：非全部
					List<TeePerson> personList = personDao.getAllUser();
					//内部短信提醒
					requestData.put("userList",personList);
					//requestData.put("sendTime","");
				}else{
					requestData.put("userSet",section.getViewPriv());
				}
				requestData.put("remindUrl","/system/subsys/topic/topicManage/topicList.jsp?uuid=" + section.getUuid());
				//手机端事务提醒
				requestData.put("remindUrl1","/system/mobile/phone/topic/topics.jsp?uuid=" + section.getUuid());
				smsManager.sendSms(requestData, loginPerson);
			}
			
		}
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * @function: 编辑数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public TeeJson updateObj(Map requestMap, TeePerson loginPerson, TeeTopicModel model) throws ParseException {
		TeeJson json = new TeeJson();
		List<TeeAttachment> listAttachments = new ArrayList<TeeAttachment>();
		if (!TeeUtility.isNullorEmpty(model.getAttacheIds())) {
			listAttachments = attachmentService.getAttachmentsByIds(model.getAttacheIds());
		}
		if (!TeeUtility.isNullorEmpty(model.getUuid())) {
			TeeTopic obj = dao.get(model.getUuid());
			if (obj != null) {
				obj.setSubject(model.getSubject());
				obj.setContent(model.getContent());
				obj.setAnonymous(model.getAnonymous());
				obj.setSection(sectionDao.get(model.getTopicSectionId()));
				obj.setFlag(1);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				obj.setCrTime(calendar);
				obj.setCrUser(loginPerson);
				dao.update(obj);
				for (TeeAttachment attach : listAttachments) {
					attach.setModelId(obj.getUuid() + "");
					simpleDaoSupport.update(attach);
				}
			}
		}
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * @function: 根据版块获取管理列表
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param dm
	 * @param requestDatas
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, Map requestDatas, TeePerson loginPerson, TeeTopicModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);

		String queryStr = " 1=1";
		String hql = "from TeeTopic where " + queryStr;
		List param = new ArrayList();
		hql += " and section.uuid=?";
		param.add(model.getTopicSectionId());

		j.setTotal(dao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by isTop desc, crTime desc";
		List<TeeTopic> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTopicModel> modelList = new ArrayList<TeeTopicModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTopicModel modeltemp = parseReturnModel(list.get(i),false);
				if(modeltemp.getAnonymous()==1){
					modeltemp.setCrUserName("匿名发布");
					modeltemp.setLastReplyUserName("匿名发布");
					
				}
				modeltemp.setContent("");
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @function: 我的帖子
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param dm
	 * @param requestDatas
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getMyTopicList(TeeDataGridModel dm, Map requestDatas, TeePerson loginPerson, TeeTopicModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
		//(自己所创建的话题 or 自己回复的话题)and(板块可见权限为全部 or 登录人为版主 or 登录人有板块可见权限)
		String hql = " from TeeTopic  obj,TeeTopicSection section where obj.section=section and (obj.crUser.uuid=? or exists (select 1 from TeeTopicReply reply where reply.topic=obj and reply.crUser.uuid=?)) "
				+ " and (section.viewPrivAllFlag=1  or section.manager like '%," + loginPerson.getUuid() + ",%' or exists (select 1 from section.viewPriv viewPriv where viewPriv.uuid=?) ) " ;
		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		param.add(loginPerson.getUuid());
		param.add(loginPerson.getUuid());
		
		
		j.setTotal(dao.countByList("select count(obj.uuid) " + hql, param));// 设置总记录数
		hql += " order by obj.crTime desc";
		List<TeeTopic> list = dao.pageFindByList("select obj " + hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTopicModel> modelList = new ArrayList<TeeTopicModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTopicModel modeltemp = parseReturnModel(list.get(i),false);
				if(modeltemp.getAnonymous()==1){
					modeltemp.setCrUserName("匿名发布");
					modeltemp.setLastReplyUserName("匿名发布");
				
				}
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @function: 最新帖子
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param dm
	 * @param requestDatas
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getLatelyTopicList(TeeDataGridModel dm, Map requestDatas, TeePerson loginPerson, TeeTopicModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		// boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
		//(板块可见权限为全部 or 登录人为版主 or 登录人有板块可见权限)
		String hql = " from TeeTopic  obj,TeeTopicSection section where obj.section=section and (section.viewPrivAllFlag=1  or section.manager like '%," + loginPerson.getUuid() + ",%' or exists (select 1 from section.viewPriv viewPriv where viewPriv.uuid=?)) " ;
		List param = new ArrayList();
		param.add(loginPerson.getUuid());

		j.setTotal(dao.countByList("select count(obj.uuid) " + hql, param));// 设置总记录数
		hql += " order by obj.crTime desc";
		List<TeeTopic> list = dao.pageFindByList("select obj "+hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTopicModel> modelList = new ArrayList<TeeTopicModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTopicModel modeltemp = parseReturnModel(list.get(i),false);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @function: 周热门帖
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param dm
	 * @param requestDatas
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getWeekTopicList(TeeDataGridModel dm, Map requestDatas, TeePerson loginPerson, TeeTopicModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		// boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
		String queryStr = " 1=1";

//		String hql = "from TeeTopic where " + queryStr;
		
		//(板块可见权限为全部 or 登录人为版主 or 登录人有板块可见权限)
				String hql = " from TeeTopic  obj,TeeTopicSection section where obj.section=section "
						+ " and (section.viewPrivAllFlag=1  or section.manager like '%," + loginPerson.getUuid() + ",%' or exists (select 1 from section.viewPriv viewPriv where viewPriv.uuid=?) ) " ;
		
		List param = new ArrayList();
		param.add(loginPerson.getUuid());

		Date date = new Date();
		Date firstDate = TeeDateUtil.getFirstDayOfWeek(date);
		Date lastDate = TeeDateUtil.getLastDayOfWeek(date);

		Calendar calendar = Calendar.getInstance();
		hql += " and obj.crTime>=?";
		calendar.setTime(firstDate);
		param.add(calendar);

		hql += " and obj.crTime<?";
		Calendar maxCalendar = Calendar.getInstance();
		maxCalendar.setTime(lastDate);
		param.add(maxCalendar);

		j.setTotal(dao.countByList("select count(obj.uuid) " + hql, param));// 设置总记录数
		hql += " order by obj.crTime desc";
		List<TeeTopic> list = dao.pageFindByList("select obj " +hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTopicModel> modelList = new ArrayList<TeeTopicModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTopicModel modeltemp = parseReturnModel(list.get(i),false);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @function: 月热门贴
	 * @author: wyw
	 * @data: 2015年1月22日
	 * @param dm
	 * @param requestDatas
	 * @param loginPerson
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getMonthTopicList(TeeDataGridModel dm, Map requestDatas, TeePerson loginPerson, TeeTopicModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		// boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
		String queryStr = " 1=1";

//		String hql = "from TeeTopic where " + queryStr;
		//(板块可见权限为全部 or 登录人为版主 or 登录人有板块可见权限)
		String hql = " from TeeTopic  obj,TeeTopicSection section where obj.section=section "
				+ " and (section.viewPrivAllFlag=1  or section.manager like '%," + loginPerson.getUuid() + ",%' or exists (select 1 from section.viewPriv viewPriv where viewPriv.uuid=?) ) " ;

		List param = new ArrayList();
		param.add(loginPerson.getUuid());

		Date date = new Date();
		Calendar calendarMin = Calendar.getInstance();
		calendarMin.set(Calendar.DAY_OF_MONTH, 1);
		calendarMin.set(Calendar.HOUR_OF_DAY, 0);
		calendarMin.set(Calendar.MINUTE, 0);
		calendarMin.set(Calendar.SECOND, 0);
		// System.out.println(TeeUtility.getDateTimeStr(calendarMin.getTime()));

		Calendar calendarMax = Calendar.getInstance();
		calendarMax.set(Calendar.DATE, calendarMax.getActualMaximum(Calendar.DATE));
		calendarMax.set(Calendar.HOUR_OF_DAY, 23);
		calendarMax.set(Calendar.MINUTE, 59);
		calendarMax.set(Calendar.SECOND, 59);
		// System.out.println(TeeUtility.getDateTimeStr(calendarMax.getTime()));

		hql += " and obj.crTime>=?";
		param.add(calendarMin);

		hql += " and obj.crTime<?";
		param.add(calendarMax);

		j.setTotal(dao.countByList("select count(obj.uuid) " + hql, param));// 设置总记录数
		hql += " order by obj.crTime desc";
		List<TeeTopic> list = dao.pageFindByList("select obj" + hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTopicModel> modelList = new ArrayList<TeeTopicModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTopicModel modeltemp = parseReturnModel(list.get(i),false);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * @function: 转换成返回对象
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param obj
	 * @return TeeCrmProductsModel
	 */
	public TeeTopicModel parseReturnModel(TeeTopic obj,boolean isEdit) {
		TeeTopicModel model = new TeeTopicModel();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		
		model.setSubject(TeeUtility.null2Empty(obj.getSubject()));

		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.topic, obj.getUuid());
		List<TeeAttachmentModel> attacheModels = new ArrayList<TeeAttachmentModel>();
		StringBuffer attacheIdBuffer = new StringBuffer();
		for (TeeAttachment attach : attaches) {
			TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, attachmentModel);
			attachmentModel.setUserId(attach.getUser().getUuid() + "");
			attachmentModel.setUserName(attach.getUser().getUserName());
			if(isEdit){
				attachmentModel.setPriv(1 + 2 + 4);// 一共五个权限好像 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			}else{
				attachmentModel.setPriv(1 + 2);
			}
			attacheModels.add(attachmentModel);
			if (attacheIdBuffer.length() > 1) {
				attacheIdBuffer.append(",");
			}
			attacheIdBuffer.append(attach.getSid());
		}
		model.setAttacheModels(attacheModels);
		model.setAttacheIds(attacheIdBuffer.toString());

		if (!TeeUtility.isNullorEmpty(obj.getCrTime())) {
			//model.setCrTimeStr(TeeUtility.getDateTimeStr(obj.getCrTime().getTime()));
			model.setCrTimeStr(sdf.format(obj.getCrTime().getTime()));
		} else {
			model.setCrTimeStr("");
		}
		if (obj.getCrUser() != null) {
			model.setCrUserId(obj.getCrUser().getUuid());
			model.setCrUserName(obj.getCrUser().getUserName());
			model.setAvatar(obj.getCrUser().getAvatar());
		} else {
			model.setCrUserName("");
		}

		if (!TeeUtility.isNullorEmpty(obj.getLastReplyTime())) {
			//model.setLastReplyTimeStr(TeeUtility.getDateTimeStr(obj.getLastReplyTime().getTime()));
			model.setLastReplyTimeStr(sdf.format(obj.getLastReplyTime().getTime()));
		} else {
			model.setLastReplyTimeStr("");
		}
		if (obj.getLastReplyUser() != null) {
			model.setLastReplyUserId(obj.getLastReplyUser().getUuid());
			model.setLastReplyUserName(obj.getLastReplyUser().getUserName());
		} else {
			model.setLastReplyUserName("");
		}
		model.setTopicSectionId(obj.getSection().getUuid());
		model.setTopicSectionName(obj.getSection().getSectionName());

		String manager = obj.getSection().getManager();
		if (!TeeUtility.isNullorEmpty(manager)) {
			if(manager.startsWith(",")){
				manager  = manager.substring(manager.indexOf(",")+1, manager.length());
			}
			if(manager.endsWith(",")){
				manager  = manager.substring(0, manager.length()-1);
			}
			
			
				model.setTopicSectionManagerId(manager);//把截取好的id,set进去
			
			StringBuffer buffer = new StringBuffer();
			List<TeePerson> personList = personDao.getPersonByUuids(manager);
			if (personList != null && personList.size() > 0) {
				for (TeePerson person : personList) {
					if (buffer.length() > 0) {
						buffer.append(",");
					}
					buffer.append(person.getUserName());
				}
			}
			model.setTopicSectionManager(buffer.toString());
		}

		return model;
	}

	/**
	 * @function: 根据sid查看详情
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getInfoById(Map requestMap, TeePerson loginPerson, TeeTopicModel model) {
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(model.getUuid())) {
			TeeTopic obj = dao.get(model.getUuid());
			if (obj != null) {
				String isEdit = TeeStringUtil.getString((String) requestMap.get("isEdit"), "");
				boolean isEditFlag = false;
				if("1".equals(isEdit)){
					isEditFlag = true;
				}
				model = parseReturnModel(obj,isEditFlag);
				if(model.getAnonymous()==1){
					model.setCrUserName("匿名发布");
					model.setLastReplyUserName("匿名发布");
					
					
				}
				
				
				
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);	
		return json;
	}

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson deleteObjById(String sids) {
		TeeJson json = new TeeJson();
		StringBuffer buffer = new StringBuffer();
		String sidsArray[] = sids.split(",");
		for (String str : sidsArray) {
			if (buffer.length() > 0) {
				buffer.append(",");
			}
			buffer.append("'" + str + "'");
		}
		dao.delByIds(buffer.toString());
		topicReplyDao.delByTopicIds(buffer.toString());
		
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * @function: 设置查看数
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson updateClickCount(Map requestMap, TeePerson loginPerson, TeeTopicModel model) {
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(model.getUuid())) {
			TeeTopic obj = dao.get(model.getUuid());
			if (obj != null) {
				obj.setClickAccount(obj.getClickAccount() + 1);
				dao.update(obj);
			}
		}
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}

	/**
	 * @function: 设置置顶
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param request
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson setTopById(Map requestMap, TeePerson loginPerson, TeeTopicModel model) {
		TeeJson json = new TeeJson();
		int isTopFlag = 0;
		if (!TeeUtility.isNullorEmpty(model.getUuid())) {
			TeeTopic obj = dao.get(model.getUuid());
			if (obj != null) {
				if (obj.getIsTop() == 0) {
					obj.setIsTop(1);
					isTopFlag =1;
				} else if (obj.getIsTop() == 1) {
					obj.setIsTop(0);
				}
				dao.update(obj);
			}
		}
		Map map = new HashMap();
		map.put("isTopFlag", isTopFlag);
		json.setRtData(map);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}
	
	/**
	 * @function: 数据迁移(新建数据)
	 * @author: lwj
	 * @data: 2018年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public void addTopic(TeeTopic obj){
			
		dao.save(obj);
	}


}
