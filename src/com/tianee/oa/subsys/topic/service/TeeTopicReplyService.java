package com.tianee.oa.subsys.topic.service;

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
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.topic.bean.TeeTopic;
import com.tianee.oa.subsys.topic.bean.TeeTopicReply;
import com.tianee.oa.subsys.topic.bean.TeeTopicSection;
import com.tianee.oa.subsys.topic.dao.TeeTopicDao;
import com.tianee.oa.subsys.topic.dao.TeeTopicReplyDao;
import com.tianee.oa.subsys.topic.dao.TeeTopicSectionDao;
import com.tianee.oa.subsys.topic.model.TeeTopicReplyModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeTopicReplyService extends TeeBaseService {
	@Autowired
	private TeeTopicReplyDao dao;
	@Autowired
	private TeeTopicDao topicDao;
	@Autowired
	private TeeTopicSectionDao sectionDao;
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
	public TeeJson addObj(Map requestMap, TeePerson loginPerson, TeeTopicReplyModel model) throws ParseException {
		TeeJson json = new TeeJson();
		if(!TeeUtility.isNullorEmpty(model.getTopicId())){
			TeeTopic topic = topicDao.get(model.getTopicId());
			if(topic!= null){
				List<TeeAttachment> listAttachments = new ArrayList<TeeAttachment>();
				if (!TeeUtility.isNullorEmpty(model.getAttacheIds())) {
					listAttachments = attachmentService.getAttachmentsByIds(model.getAttacheIds());
				}
				TeeTopicReply obj = new TeeTopicReply();
				obj.setAnonymous(model.getAnonymous());
				obj.setContent(model.getContent());
				obj.setTopic(topic);
				obj.setFlag(1);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				obj.setCrTime(calendar);
				obj.setCrUser(loginPerson);
				obj.setSection(topic.getSection());
				dao.save(obj);
				topic.setReplyAmount(dao.getTopicReplyListById(loginPerson, model).size());
				topic.setLastReplyTime(calendar);
				topic.setLastReplyUser(loginPerson);
				topicDao.update(topic);
				for (TeeAttachment attach : listAttachments) {
					attach.setModelId(obj.getUuid() + "");
					simpleDaoSupport.update(attach);
				}
				
				//内部短信提醒
				TeeTopicSection section = obj.getSection();
				int replySmsPriv = section.getReplySmsPriv(); 
				if(replySmsPriv ==1){
					Map requestData = new HashMap();
					requestData.put("content", "[" + loginPerson.getUserName() + "]" + " 发送了跟帖：" + topic.getSubject());
					requestData.put("moduleNo","050");
					requestData.put("userListIds",topic.getCrUser().getUuid() + "");
					//requestData.put("sendTime","");
					requestData.put("remindUrl","/system/subsys/topic/topicManage/topicDetail.jsp?topicSectionId=" + section.getUuid() + "&uuid=" + topic.getUuid());
					//手机端事务提醒
					requestData.put("remindUrl1","/system/mobile/phone/topic/detail.jsp?topicSectionId=" + section.getUuid() + "&uuid=" + topic.getUuid());
					smsManager.sendSms(requestData, loginPerson);
				}
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
	public TeeJson updateObj(Map requestMap, TeePerson loginPerson, TeeTopicReplyModel model) throws ParseException {
		TeeJson json = new TeeJson();
		List<TeeAttachment> listAttachments = new ArrayList<TeeAttachment>();
		if (!TeeUtility.isNullorEmpty(model.getAttacheIds())) {
			listAttachments = attachmentService.getAttachmentsByIds(model.getAttacheIds());
		}
		if (!TeeUtility.isNullorEmpty(model.getUuid())) {
			TeeTopicReply obj = dao.get(model.getUuid());
			if (obj != null) {
				obj.setContent(model.getContent());
				obj.setTopic(topicDao.get(model.getTopicId()));
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
	 * @function: 我的帖子管理列表
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
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, Map requestDatas, TeePerson loginPerson, TeeTopicReplyModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		 boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
		String queryStr = " 1=1";
		if(!isAdmin){
			queryStr += " and r.USER_ID= " + loginPerson.getUuid();
		}
		String sql = "select t.uuid as uuid,"
				+ "t.SUBJECT_ as subject,"
				+ "t.CR_USER as crUser,"
				+ "t.CR_TIME as crTime,"
				+ "t.LAST_REPLY_USER_ID as lastReplyUser,"
				+ "t.LAST_REPLY_TIME as lastReplyTime,"
				+ "t.REPLY_AMOUNT as replyAmount,"
				+ "t.CLICK_ACCOUNT as clickAccount "
				+ " from topic_reply as r  right join  topic as t on  r.TOPIC_ID=t.uuid and"  + queryStr ;
		sql +=" order by t.CR_TIME desc";
		
		List<Map> dataList = simpleDaoSupport.executeNativeQuery(sql, null, dm.getFirstResult(), dm.getRows());
		
		List<Map> returnList = new ArrayList<Map>();
		if(dataList != null && dataList.size()>0){
			for(Map map:dataList){
				Map returnMap = new HashMap();
				int crUserId = TeeStringUtil.getInteger((Integer) map.get("crUser"),0);
				TeePerson crUser = personDao.get(crUserId);
				int lastReplyUserId = TeeStringUtil.getInteger((Integer) map.get("lastReplyUser"),0);
				TeePerson lastReplyUser = personDao.get(lastReplyUserId);
				
				Date crTime = (Date) map.get("crTime");
				Date lastReplyTime = (Date) map.get("lastReplyTime");
				if (!TeeUtility.isNullorEmpty(crTime)) {
					map.put("crTime", TeeUtility.getDateTimeStr(crTime));
				}else{
					map.put("crTime", "");
				}
				
				if (!TeeUtility.isNullorEmpty(lastReplyTime)) {
					map.put("lastReplyTime", TeeUtility.getDateTimeStr(lastReplyTime));
				}else{
					map.put("lastReplyTime", "");
				}
				
				if(!TeeUtility.isNullorEmpty(crUser)){
					map.put("crUser", crUser.getUserName());
				}else{
					map.put("crUser", "");
				}
				if(!TeeUtility.isNullorEmpty(lastReplyUser)){
					map.put("lastReplyUser", lastReplyUser.getUserName());
				}else{
					map.put("lastReplyUser", "");
				}
				
				returnList.add(map);
			}
		}
		Map obj = simpleDaoSupport.executeNativeUnique("select count(1) as count from ("+sql+") tmp", null);
		j.setTotal(TeeStringUtil.getLong(obj.get("count"), 0));
		j.setRows(returnList);// 设置返回的行
		return j;
	}
	
	
	/**
	 * @function: 最新帖子管理列表
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
	public TeeEasyuiDataGridJson getLatelyTopicList(TeeDataGridModel dm, Map requestDatas, TeePerson loginPerson, TeeTopicReplyModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

//		 boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
		String queryStr = " 1=1";
		String sql = "select t.uuid as uuid,"
				+ "t.SUBJECT_ as subject,"
				+ "t.CR_USER as crUser,"
				+ "t.CR_TIME as crTime,"
				+ "t.LAST_REPLY_USER_ID as lastReplyUser,"
				+ "t.LAST_REPLY_TIME as lastReplyTime,"
				+ "t.REPLY_AMOUNT as replyAmount,"
				+ "t.CLICK_ACCOUNT as clickAccount "
				+ " from topic_reply as r  right join  topic as t on  r.TOPIC_ID=t.uuid and"  + queryStr ;
		sql +=" order by t.CR_TIME desc";
		
		List<Map> dataList = simpleDaoSupport.executeNativeQuery(sql, null, dm.getFirstResult(), dm.getRows());
		
		List<Map> returnList = new ArrayList<Map>();
		if(dataList != null && dataList.size()>0){
			for(Map map:dataList){
				Map returnMap = new HashMap();
				int crUserId = TeeStringUtil.getInteger((Integer) map.get("crUser"),0);
				TeePerson crUser = personDao.get(crUserId);
				int lastReplyUserId = TeeStringUtil.getInteger((Integer) map.get("lastReplyUser"),0);
				TeePerson lastReplyUser = personDao.get(lastReplyUserId);
				
				Date crTime = (Date) map.get("crTime");
				Date lastReplyTime = (Date) map.get("lastReplyTime");
				if (!TeeUtility.isNullorEmpty(crTime)) {
					map.put("crTime", TeeUtility.getDateTimeStr(crTime));
				}else{
					map.put("crTime", "");
				}
				
				if (!TeeUtility.isNullorEmpty(lastReplyTime)) {
					map.put("lastReplyTime", TeeUtility.getDateTimeStr(lastReplyTime));
				}else{
					map.put("lastReplyTime", "");
				}
				
				if(!TeeUtility.isNullorEmpty(crUser)){
					map.put("crUser", crUser.getUserName());
				}else{
					map.put("crUser", "");
				}
				if(!TeeUtility.isNullorEmpty(lastReplyUser)){
					map.put("lastReplyUser", lastReplyUser.getUserName());
				}else{
					map.put("lastReplyUser", "");
				}
				
				returnList.add(map);
			}
		}
		Map obj = simpleDaoSupport.executeNativeUnique("select count(1) as count from ("+sql+") tmp", null);
		j.setTotal(TeeStringUtil.getLong(obj.get("count"), 0));
		j.setRows(returnList);// 设置返回的行
		return j;
	}
	
	

	/**
	 * @function: 转换成返回对象
	 * @author: wyw
	 * @data: 2014年8月29日
	 * @param obj
	 * @return TeeCrmProductsModel
	 */
	public TeeTopicReplyModel parseReturnModel(TeeTopicReply obj,boolean isEdit) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeTopicReplyModel model = new TeeTopicReplyModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		model.setAvatar(obj.getCrUser().getAvatar());

		List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.topicReply, obj.getUuid());
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
		}else{
			model.setCrTimeStr("");
		}
		if (obj.getCrUser() != null) {
			model.setCrUserId(obj.getCrUser().getUuid());
			model.setCrUserName(obj.getCrUser().getUserName());
		}else{
			model.setCrUserName("");
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
	public TeeJson getInfoById(Map requestMap, TeePerson loginPerson, TeeTopicReplyModel model) {
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(model.getUuid())) {
			TeeTopicReply obj = dao.get(model.getUuid());
			if (obj != null) {
				String isEdit = TeeStringUtil.getString((String) requestMap.get("isEdit"), "");
				boolean isEditFlag = false;
				if("1".equals(isEdit)){
					isEditFlag = true;
				}
				model = parseReturnModel(obj,isEditFlag);
				
				if(model.getAnonymous()==1){
					model.setCrUserName("匿名发布");
					
					
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
	public TeeJson deleteObjById(Map requestMap, TeePerson loginPerson, TeeTopicReplyModel model) {
		String sids = (String) requestMap.get("sids");
		if(!TeeUtility.isNullorEmpty(model.getTopicId())){
			TeeTopic topic = topicDao.get(model.getTopicId());
			if(topic!= null){
				StringBuffer buffer = new StringBuffer();
				String sidsArray[] = sids.split(",");
				for (String str : sidsArray) {
					if (buffer.length() > 0) {
						buffer.append(",");
					}
					buffer.append("'" + str + "'");
				}
				dao.delByIds(buffer.toString());
				
				topic.setReplyAmount(dao.getTopicReplyListById(loginPerson, model).size());
//				topic.setLastReplyTime(calendar);
//				topic.setLastReplyUser(loginPerson);
				topicDao.update(topic);
				
			}
		}
		
		
		
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * 根据话题获取回复内容数据
	 * @function: 
	 * @author: wyw
	 * @data: 2015年1月7日
	 * @param requestMap
	 * @param model
	 * @return TeeJson
	 */
	public TeeJson getTopicReplyList(Map requestMap, TeeTopicReplyModel model){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) requestMap.get(TeeConst.LOGIN_USER);
		
		//根据话题获取回复内容数据
		List<TeeTopicReply> topicReplies = dao.getTopicReplyListById(person,model);
		List<Map> returnList = new ArrayList<Map>();
		
		if(topicReplies != null && topicReplies.size()>0){
			int counter = 0;
			for(TeeTopicReply topicReply:topicReplies){
				Map returnMap = new HashMap();
				TeeTopicReplyModel modelTemp = parseReturnModel(topicReply,false);
				returnMap.put("counter", ++counter);
				returnMap.put("uuid", modelTemp.getUuid());
				returnMap.put("content", modelTemp.getContent());
				returnMap.put("crUserId", modelTemp.getCrUserId());
				if(modelTemp.getAnonymous()==1){
					returnMap.put("crUserName", "匿名发布");
					
				}else{
					returnMap.put("crUserName", modelTemp.getCrUserName());
				}
				
				returnMap.put("avatar", topicReply.getCrUser().getAvatar());
				returnMap.put("crTimeStr", modelTemp.getCrTimeStr());
				returnMap.put("attacheIds", modelTemp.getAttacheIds());
				returnMap.put("attacheModels", modelTemp.getAttacheModels());
				returnList.add(returnMap);
			}
		}
		json.setRtData(returnList);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}
	
	/**
	 * 获取回复内容
	 * @function: 
	 * @author: wyw
	 * @data: 2015年8月19日
	 * @param dm
	 * @param requestMap
	 * @param model
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getTopicReplyPage(TeeDataGridModel dm, Map requestMap, TeeTopicReplyModel model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();

		String hql = " from TeeTopicReply obj where obj.topic.uuid=? ";
		List param = new ArrayList();
		param.add(model.getTopicId());

		// 设置总记录数
		j.setTotal(dao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by obj.crTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeTopicReply> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeTopicReplyModel> modelList = new ArrayList<TeeTopicReplyModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTopicReplyModel modeltemp = parseReturnModel(list.get(i), false);
				if(modeltemp.getAnonymous()==1){
					modeltemp.setCrUserName("匿名发布");
				}
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	
	
	/**
	 * @function:数据迁移（新建数据）
	 * @author: lwj
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public void addTopicReply(TeeTopicReply topicReply) {
		dao.save(topicReply);
	}

	

}
