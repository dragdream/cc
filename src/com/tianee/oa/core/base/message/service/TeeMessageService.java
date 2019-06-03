package com.tianee.oa.core.base.message.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.message.bean.TeeMessage;
import com.tianee.oa.core.base.message.bean.TeeMessageBody;
import com.tianee.oa.core.base.message.dao.TeeMessageDao;
import com.tianee.oa.core.base.message.model.TeeMessageModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeMessageService extends TeeBaseService {
	@Autowired
	private TeeMessageDao messageDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	public void saveMessage(TeeMessage message){
		messageDao.save(message);
	}
	
	public void saveMessageBody(TeeMessageBody messageBody){
		simpleDaoSupport.save(messageBody);
	}
	
	public List<TeeMessageModel> getOfflineMessages(String userId){
		List<TeeMessage> list = messageDao.getOfflineMessages(userId);
		List<TeeMessageModel> models = new ArrayList();
		for(TeeMessage message:list){
			TeeMessageModel m = new TeeMessageModel();
			BeanUtils.copyProperties(message, m);
			m.setContent(message.getBody().getContent());
			m.setToId(message.getToId());
			m.setFromId(message.getBody().getFromId());
			//m.setFromIdName(personDao.getPersonByUserId(message.getFromId()).getUserName());
			m.setSendTimeDesc(TeeDateUtil.format(message.getBody().getSendTime()));
			models.add(m);
		}
		return models;
	}
	
	
	public void readAllByUserId(String userId,String from){
		simpleDaoSupport.executeUpdate("update TeeMessage msg set msg.remindFlag=1 where msg.toId=? and exists (select 1 from TeeMessageBody msgBody where msgBody.uuid=msg.body.uuid and msgBody.fromId=?)", new Object[]{userId,from});
	}
	
	/**
	 * 新增
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public int addMessage(TeeMessageModel model , String toIds ,TeePerson person) {
		if(!toIds.equals("")){
			TeeMessageBody messageBody = new TeeMessageBody();
			messageBody.setFromId(person.getUserId());
			messageBody.setMessageType(3);
			messageBody.setContent(model.getContent());
			messageBody.setSendTime(Calendar.getInstance());
			simpleDaoSupport.save(messageBody);
			
			String[] toIdArray = toIds.split(",");
			for (int i = 0; i < toIdArray.length; i++) {
				TeeMessage message = new TeeMessage();
				message.setBody(messageBody);
				message.setToId(personDao.selectPersonById(Integer.parseInt(toIdArray[i])).getUserId());
				message.setRemindFlag(0);
				messageDao.addMessage(message);
			}
		}
		
		return 0;
	}

	/**
	 * byId 查询
	 * @param sid
	 * @return
	 */
	public TeeMessage selectById(String sid) {
		TeeMessage message = messageDao.selectById(Integer.parseInt(sid));
		return message;
	}
	
	
	/**
	 * by  发信人 查询
	 * @param sid
	 * @return
	 */
	public List<TeeMessage> selectByFromId(String fromId) {
		List<TeeMessage> list = messageDao.selectByFromId(fromId);
		return list;
	}
	
	
	
	/**
	 * 通用列表
	 * @param dm
	 * @return
	 */
	//@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();	
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
	
		j.setTotal(messageDao.getComfireNoCount(loginPerson.getUserId()));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
	
		List<TeeMessage> list = messageDao.getComfireNoList(firstIndex , dm.getRows(),dm , loginPerson.getUserId());// 查询
		List<TeeMessageModel> modelList = new ArrayList<TeeMessageModel>();
		
		String messageIds = "";//处理收信人已读记录
		if (list != null && list.size() > 0) {
			for (TeeMessage message : list) {
				TeeMessageModel um = new TeeMessageModel();
				BeanUtils.copyProperties(message, um);
				String formId = message.getBody().getFromId();
				TeePerson person = personDao.getPersonByUserId(formId);
				if(person != null ){
					um.setFromIdName(person.getUserName());
				}
				Calendar ca = message.getBody().getSendTime();
				if(ca != null ){
					String sendTimeDesc = TeeDateUtil.format(ca.getTime());
					um.setSendTimeDesc(sendTimeDesc);
				}
				
				//role.setSysMenus(null);
				messageIds = messageIds + message.getUuid() + ",";
				modelList.add(um);
			}
		}
		j.setRows(modelList);// 设置返回的行
		messageDao.updateRemindFlag(messageIds, 2);
		return j;
	}
	

	/**
	 * 与人员对话通用列表
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getMessageListByPersonId(TeeDataGridModel dm,HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();	
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		int personId = TeeStringUtil.getInteger(request.getParameter("personId"),0);//对话人员Id
		String userId = TeeStringUtil.getString(request.getParameter("userId"));//对话人员userId
		j.setTotal(messageDao.getPersonDialoguCount(loginPerson.getUserId() ,userId));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
	
		List<TeeMessage> list = messageDao.getPersonDialoguList(firstIndex , dm.getRows(),dm , loginPerson.getUserId(),userId);// 查询
		List<TeeMessageModel> modelList = new ArrayList<TeeMessageModel>();
		if (list != null && list.size() > 0) {
			for (TeeMessage message : list) {
				TeeMessageModel um = new TeeMessageModel();
				BeanUtils.copyProperties(message, um);
				String formId = message.getBody().getFromId();
				TeePerson person = personDao.getPersonByUserId(formId);
				if(person != null ){
					um.setFromIdName(person.getUserName());
				}
				um.setFromId(formId);
				um.setToIdName(person.getUserName());
				um.setContent(message.getBody().getContent());
				Calendar ca = message.getBody().getSendTime();
				if(ca != null ){
					String sendTimeDesc = TeeDateUtil.format(ca.getTime());
					um.setSendTimeDesc(sendTimeDesc);
				}
				
				//role.setSysMenus(null);
				
				modelList.add(um);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	

	/**
	 * 与人员对话通用列表
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getQueryMessage(TeeDataGridModel dm ,TeeMessageModel model,HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();	
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
	
	
		j.setTotal(messageDao.getQueryCount(request));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
	
		List<TeeMessage> list = messageDao.getQuery(firstIndex , dm.getRows(),dm , request);// 查询
		List<TeeMessageModel> modelList = new ArrayList<TeeMessageModel>();
		if (list != null && list.size() > 0) {
			for (TeeMessage message : list) {
				TeeMessageModel um = new TeeMessageModel();
				BeanUtils.copyProperties(message, um);
				String formId = message.getBody().getFromId();
				TeePerson person = personDao.getPersonByUserId(formId);
				if(person != null ){
					um.setFromIdName(person.getUserName());
				}
				um.setToIdName(person.getUserName());
				Calendar ca = message.getBody().getSendTime();
				if(ca != null ){
					String sendTimeDesc = TeeDateUtil.format(ca.getTime());
					um.setSendTimeDesc(sendTimeDesc);
				}
				
				//role.setSysMenus(null);
				
				modelList.add(um);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	
	
	/**
	 * 更新发件人  / 收件人 删除 
	 * @param sid
	 * @param person
	 * @return
	 */
	public long updateDeleteFlag(String  ids , TeePerson person) {
		long fromCount =  messageDao.updateFromDeleteFlag(ids, person.getUserId());	
		long toCount =  messageDao.updateToDeleteFlag(ids, person.getUserId());
		return fromCount + toCount;	
	}
	
	
	/**
	 * by  ids 
	 * @param sid
	 * @return
	 */
	public long delBySids(String  ids) {
		return  messageDao.deleteByIds(ids);
	}
	
	public void setMessageDao(TeeMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}
	
	
	
	
}




	