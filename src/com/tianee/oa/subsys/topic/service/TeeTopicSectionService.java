package com.tianee.oa.subsys.topic.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.topic.bean.TeeTopic;
import com.tianee.oa.subsys.topic.bean.TeeTopicSection;
import com.tianee.oa.subsys.topic.dao.TeeTopicDao;
import com.tianee.oa.subsys.topic.dao.TeeTopicSectionDao;
import com.tianee.oa.subsys.topic.model.TeeTopicSectionModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeTopicSectionService extends TeeBaseService {
	@Autowired
	private TeeTopicSectionDao dao;
	@Autowired
	private TeeTopicDao topicDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeDeptDao deptDao;

	/**
	 * @function: 新建数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public TeeJson addObj(Map requestMap, TeePerson loginPerson, TeeTopicSectionModel model) throws ParseException {
		TeeJson json = new TeeJson();
		TeeTopicSection obj = new TeeTopicSection();
		obj.setAnonymous(model.getAnonymous());
		obj.setSectionName(model.getSectionName());
		
		if(!TeeUtility.isNullorEmpty(model.getManager())){
			if(model.getManager().endsWith(",")){
				obj.setManager("," + model.getManager());
			}else{
				obj.setManager("," + model.getManager() + ",");
			}
			
		}else{
			obj.setManager("");
		}
		obj.setCrPriv(model.getCrPriv());
		int viewPrivAllFlag = model.getViewPrivAllFlag();
		obj.setViewPrivAllFlag(viewPrivAllFlag);
		if (viewPrivAllFlag != 1) {// 版块可见人员标记 1：全部 0：非全部
			String viewPrivId = TeeUtility.null2Empty(model.getViewPrivId());
			List<TeePerson> personList = personDao.getPersonByUuids(viewPrivId);
			Set<TeePerson> viewPrivSet = new HashSet();
			if (personList != null && personList.size() > 0) {
				for (TeePerson person : personList) {
					viewPrivSet.add(person);
				}
			}
			obj.setViewPriv(viewPrivSet);
		}

		obj.setNewTopicSmsPriv(model.getNewTopicSmsPriv());
		obj.setReplySmsPriv(model.getReplySmsPriv());
		obj.setOrderNo(model.getOrderNo());
		obj.setRemark(model.getRemark());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		obj.setCreateTime(calendar);
		obj.setCreateUser(loginPerson);
		dao.save(obj);
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
	public TeeJson updateObj(Map requestMap, TeePerson loginPerson, TeeTopicSectionModel model) throws ParseException {
		TeeJson json = new TeeJson();

		if (!TeeUtility.isNullorEmpty(model.getUuid())) {
			TeeTopicSection obj = dao.get(model.getUuid());
			if (obj != null) {
				obj.setSectionName(model.getSectionName());
				obj.setAnonymous(model.getAnonymous());
				if(!TeeUtility.isNullorEmpty(model.getManager())){
					if(model.getManager().endsWith(",")){
						obj.setManager("," + model.getManager());
					}else{
						obj.setManager("," + model.getManager() + ",");
					}
					
				}else{
					obj.setManager("");
				}
				
				obj.setCrPriv(model.getCrPriv());
				int viewPrivAllFlag = model.getViewPrivAllFlag();
				obj.setViewPrivAllFlag(viewPrivAllFlag);
				if (viewPrivAllFlag != 1) {// 版块可见人员标记 1：全部 0：非全部
					String viewPrivId = TeeUtility.null2Empty(model.getViewPrivId());
					List<TeePerson> personList = personDao.getPersonByUuids(viewPrivId);
					Set<TeePerson> viewPrivSet = new HashSet();
					if (personList != null && personList.size() > 0) {
						for (TeePerson person : personList) {
							viewPrivSet.add(person);
						}
					}
					obj.setViewPriv(viewPrivSet);
				}
				obj.setNewTopicSmsPriv(model.getNewTopicSmsPriv());
				obj.setReplySmsPriv(model.getReplySmsPriv());
				obj.setOrderNo(model.getOrderNo());
				obj.setRemark(model.getRemark());
				dao.update(obj);
			}
		}
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * @function: 管理列表(普通用户板块浏览)
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
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, Map requestDatas, TeePerson loginPerson, TeeTopicSectionModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);

		//版块可见人员标记为1-全部；或可见权限为当前系统登录人；或者是版主
		String hql = " from TeeTopicSection obj  " ;
		List param = new ArrayList();
		hql += " where obj.viewPrivAllFlag=1 or  obj.manager like '%," + loginPerson.getUuid() + ",%' or exists (select 1 from obj.viewPriv viewPriv where viewPriv.uuid=?) ";
		param.add(loginPerson.getUuid());
		
		hql += "order by obj.orderNo asc";

		
		j.setTotal(dao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by orderNo asc,createTime desc";
		List<TeeTopicSection> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTopicSectionModel> modelList = new ArrayList<TeeTopicSectionModel>();
		
		List<Map> topicList = topicDao.getTopicReplyCount();
		Map topicMap = new HashMap();
		if(topicList != null && topicList.size()>0){
			for(Map map:topicList){
				topicMap.put(map.get("SECTIONID"), TeeStringUtil.getLong(map.get("TOPICREPLYCOUNT"), 0));
			}
		}
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTopicSectionModel modeltemp = parseReturnModel(list.get(i),loginPerson);
				modeltemp.setTopicReplyCount((Long)topicMap.get(modeltemp.getUuid()));
				modeltemp.setManagerName(modeltemp.getManagerName().replace("，", ""));
				
				List<TeeTopic> topics = topicDao.getTopicListById(modeltemp.getUuid());
				if(topics != null && topics.size()>0){
					TeeTopic topic = topics.get(0);
					if(topic != null){
						modeltemp.setLastTopicId(topic.getUuid());
						modeltemp.setLastTopicSubject(topic.getSubject());
						modeltemp.setLastReplyUserName(topic.getLastReplyUser().getUserName());
						//modeltemp.setLastReplyTimeStr(TeeUtility.getDateTimeStr(topic.getLastReplyTime().getTime()));
						modeltemp.setLastReplyTimeStr(sdf.format(topic.getLastReplyTime().getTime()));
					}
				}else{
					modeltemp.setLastTopicSubject("");
					modeltemp.setLastReplyUserName("");
					modeltemp.setLastReplyTimeStr("");
				}
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	

	/**
	 * @function: 管理列表(管理员-板块浏览)
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
	public TeeEasyuiDataGridJson getTopicSectionList(TeeDataGridModel dm, Map requestDatas, TeePerson loginPerson, TeeTopicSectionModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		 boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);

		String queryStr = " 1=1";
		String hql = " from TeeTopicSection obj  " ;
		List param = new ArrayList();
		if(!isAdmin){
			//hql += " where obj.viewPrivAllFlag=1 or exists (select 1 from obj.viewPriv viewPriv where viewPriv.uuid=?) ";
			//param.add(loginPerson.getUuid());
		}
		
		hql += "order by obj.orderNo asc";

		
		j.setTotal(dao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by orderNo asc,createTime desc";
		List<TeeTopicSection> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTopicSectionModel> modelList = new ArrayList<TeeTopicSectionModel>();
		
		List<Map> topicList = topicDao.getTopicReplyCount();
		Map topicMap = new HashMap();
		if(topicList != null && topicList.size()>0){
			for(Map map:topicList){
				topicMap.put(map.get("SECTIONID"), TeeStringUtil.getLong(map.get("TOPICREPLYCOUNT"), 0));
			}
		}
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTopicSectionModel modeltemp = parseReturnModel(list.get(i),loginPerson);
				modeltemp.setTopicReplyCount((Long)topicMap.get(modeltemp.getUuid()));
				modeltemp.setManagerName(modeltemp.getManagerName().replace("，", ""));
				
				List<TeeTopic> topics = topicDao.getTopicListById(modeltemp.getUuid());
				if(topics != null && topics.size()>0){
					TeeTopic topic = topics.get(0);
					if(topic != null){
						modeltemp.setLastTopicId(topic.getUuid());
						modeltemp.setLastTopicSubject(topic.getSubject());
						modeltemp.setLastReplyUserName(topic.getLastReplyUser().getUserName());
						modeltemp.setLastReplyTimeStr(TeeUtility.getDateTimeStr(topic.getLastReplyTime().getTime()));
					}
				}else{
					modeltemp.setLastTopicSubject("");
					modeltemp.setLastReplyUserName("");
					modeltemp.setLastReplyTimeStr("");
				}
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
	public TeeTopicSectionModel parseReturnModel(TeeTopicSection obj,TeePerson loginPerson) {
		TeeTopicSectionModel model = new TeeTopicSectionModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		model.setManager(obj.getManager());
		String manager = obj.getManager();
		if (!TeeUtility.isNullorEmpty(manager)) {
			if(manager.startsWith(",")){
				manager  = manager.substring(manager.indexOf(",")+1, manager.length());
			}
			if(manager.endsWith(",")){
				manager  = manager.substring(0, manager.length()-1);
			}
			
			StringBuffer buffer = new StringBuffer();
			//版主
			List<TeePerson> personList = personDao.getPersonByUuids(manager);
			if (personList != null && personList.size() > 0) {
				for (TeePerson person : personList) {
					if (buffer.length() > 0) {
						buffer.append(",");
					}
					if(person.getUuid() == loginPerson.getUuid()){
						model.setManagerPrivFlag(true);
					}
					buffer.append(person.getUserName());
				}
			}
			model.setManager(manager);
			model.setManagerName(buffer.toString());
		}

		//新建帖子
		String crPriv = obj.getCrPriv();
		model.setCrPriv(crPriv);
		if (!"ALL".equalsIgnoreCase(crPriv)) {
			StringBuffer buffer = new StringBuffer();
			List<TeePerson> personList = personDao.getPersonByUuids(crPriv);
			
			if (personList != null && personList.size() > 0) {
				for (TeePerson person : personList) {
					if (buffer.length() > 0) {
						buffer.append(",");
					}
					if(person.getUuid() == loginPerson.getUuid()){
						model.setCrPrivFlag(true);
					}
					buffer.append(person.getUserName());
				}
			}
			model.setCrPrivName(buffer.toString());
			model.setCrPrivAllFlag(0);
		} else {
			model.setCrPrivName("全体人员");
			model.setCrPrivAllFlag(1);
			model.setCrPrivFlag(true);
		}

		//浏览权限
		int viewPrivAllFlag = obj.getViewPrivAllFlag();
		obj.setViewPrivAllFlag(viewPrivAllFlag);
		if (viewPrivAllFlag != 1) {// 版块可见人员标记 1：全部 0：非全部
			Set<TeePerson> personSet = obj.getViewPriv();
			if (personSet != null && personSet.size() > 0) {
				StringBuffer buffer = new StringBuffer();
				StringBuffer viewPrivBuffer = new StringBuffer();
				for (TeePerson person : personSet) {
					if (viewPrivBuffer.length() > 0) {
						viewPrivBuffer.append(",");
					}
					viewPrivBuffer.append(person.getUuid());
					if (buffer.length() > 0) {
						buffer.append(",");
					}
					if(person.getUuid() == loginPerson.getUuid()){
						model.setViewPrivFlag(true);
					}
					buffer.append(person.getUserName());
				}
				model.setViewPrivId(viewPrivBuffer.toString());
				model.setViewPrivName(buffer.toString());
			}
		}else{
			model.setViewPrivId("");
			model.setViewPrivName("全体人员");
			model.setViewPrivFlag(true);
		}

		if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
			model.setCreateTimeStr(TeeUtility.getDateTimeStr(obj.getCreateTime().getTime()));
		}
		if (obj.getCreateUser() != null) {
			model.setCreateUserId(obj.getCreateUser().getUuid());
			model.setCreateUserName(obj.getCreateUser().getUserName());
		}
		
		model.setTopicCount(topicDao.getTopicListById(obj.getUuid()).size());
		

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
	public TeeJson getInfoById(Map requestMap, TeePerson loginPerson, TeeTopicSectionModel model) {
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(model.getUuid())) {
			TeeTopicSection obj = dao.get(model.getUuid());
			if (obj != null) {
				model = parseReturnModel(obj,loginPerson);
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
		String sidsArray [] = sids.split(",");
		for(String str:sidsArray){
			if(buffer.length()>0){
				buffer.append(",");
			}
			buffer.append("'" + str + "'");
		}
		dao.delByIds(buffer.toString());
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	
	/**
	 * 获取论坛版块列表
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月7日
	 * @param requestMap
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getTopicSectionList(Map requestMap, TeeTopicSectionModel model){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) requestMap.get(TeeConst.LOGIN_USER);
		
		//根据版块可见人员权限获取版块
		List<TeeTopicSection> topicSections = dao.getTopicSectionListByViewPriv(person);
		List<Map> topicList = topicDao.getTopicListCounter();
		Map topicMap = new HashMap();
		if(topicList != null && topicList.size()>0){
			for(Map map:topicList){
				topicMap.put(map.get("sectionId"), map.get("topicCount"));
			}
		}
		
		List<Map> returnList = new ArrayList<Map>();
		if(topicSections != null && topicSections.size()>0){
			for(TeeTopicSection topicSection:topicSections){
				Map returnMap = new HashMap();
				TeeTopicSectionModel sectionModel = parseReturnModel(topicSection,person);
				returnMap.put("uuid", topicSection.getUuid());
				returnMap.put("sectionName", topicSection.getSectionName());
				returnMap.put("remark", topicSection.getRemark());
				returnMap.put("topicCount", topicMap.get(topicSection.getUuid()));
				returnMap.put("topicReplyCount", "5");
				returnMap.put("lastTopicName", "4");
				returnMap.put("lastTopicAuthor", "3");
				returnMap.put("lastTopicTime", "2015-01-07 23:50 ");
				returnMap.put("manager", sectionModel.getManagerName().replace(",", "<br>"));
				returnList.add(returnMap);
			}
		}
		json.setRtData(returnList);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}
	
	
	

}
