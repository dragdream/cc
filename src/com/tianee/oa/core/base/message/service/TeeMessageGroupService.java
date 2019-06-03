package com.tianee.oa.core.base.message.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.message.bean.TeeMessageGroup;
import com.tianee.oa.core.base.message.dao.TeeMessageGroupDao;
import com.tianee.oa.core.base.message.model.TeeMessageGroupModel;
import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMessageGroupService extends TeeBaseService  {
	@Autowired
	private TeeMessageGroupDao messageGroupDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private	TeeSmsSender smsSender;
	

	/**
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson addOrUpdateGroup(TeeMessageGroupModel model ,TeePerson person) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeMessageGroup group  = messageGroupDao.selectById(model.getSid());
			if(group != null ){
				BeanUtils.copyProperties(model, group);
				
				//获取人员userId
				StringBuffer sb = new StringBuffer();
				List<TeePerson> persons = personDao.getPersonByUuids(model.getToId());
				for(int i=0;i<persons.size();i++){
					sb.append(persons.get(i).getUserId());
					if(i!=persons.size()-1){
						sb.append(",");
					}
				}
				group.setToUserId(sb.toString());
				
				messageGroupDao.updateMessageGroup(group);
				
				//发送消息
				Map requestData = new HashMap();
				requestData.put("content", "讨论组["+model.getGroupName()+"]相关信息已变动");
				requestData.put("userListIds",model.getToId());
				requestData.put("moduleNo", "054");
				smsSender.sendSms(requestData, null);
				
			}else{
				json.setRtState(false);
				json.setRtMsg("未找到相关群组");
				return json;
			}
		}else{
			TeeMessageGroup group = new TeeMessageGroup();
			BeanUtils.copyProperties(model, group);
			
			//获取人员userId
			StringBuffer sb = new StringBuffer();
			List<TeePerson> persons = personDao.getPersonByUuids(model.getToId());
			for(int i=0;i<persons.size();i++){
				sb.append(persons.get(i).getUserId());
				if(i!=persons.size()-1){
					sb.append(",");
				}
			}
			group.setToUserId(sb.toString());
			
			group.setGroupCreator(person.getUuid());
			messageGroupDao.addMessageGroup(group);
			
			//发送消息
			Map requestData = new HashMap();
			requestData.put("content", "您已被"+person.getUserName()+"加入讨论群组："+model.getGroupName());
			requestData.put("userListIds",model.getToId());
			requestData.put("moduleNo", "054");
			smsSender.sendSms(requestData, null);
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 根据Id查询
	 * @param id
	 * @return
	 */
	public TeeJson selectById(String id) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(id, 0);
		TeeMessageGroup group  = messageGroupDao.selectById(sid);
		TeeMessageGroupModel model =  new TeeMessageGroupModel();
		if(group != null  && sid > 0){
			BeanUtils.copyProperties(group, model);
			if(!TeeUtility.isNullorEmpty(model.getToId())){
				String[] personInfo = personDao.getPersonNameAndUuidByUuids(model.getToId());
				model.setToId(personInfo[0]);
				model.setToName(personInfo[1]);
				model.setToUserId(personInfo[2]);
			}
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("未找到相关群组");
		}
		json.setRtData(model);
		return json;
	}
	
	
	/**
	 * 删除   根据Id
	 * @param id
	 * @return
	 */
	public TeeJson delById(String id) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(id, 0);
		messageGroupDao.delById(sid);
		json.setRtState(true);
		json.setRtData("");
		return json;
	}
	
	
	/**
	 * 更改状态 by Id
	 * @param  id  
	 * @param flag 状态 0 正常 1-停用
	 */
	public TeeJson updateFlagById(String id , String groupflag ) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(id, 0);
		int flag = TeeStringUtil.getInteger(groupflag, 0);
		messageGroupDao.updateFlag(sid, flag);
		json.setRtState(true);
		json.setRtMsg("激活成功！");	
		if(flag == 1){
			json.setRtMsg("取消激活成功！");	
		}
		json.setRtData("");
		return json;
	}
	

	/**
	 * 根据系统当前登录人获取  所有群组
	 * @param loginPerson 系统当前登录人
	 * @return
	 */
	public TeeEasyuiDataGridJson selectListByLoginPerson(TeeDataGridModel dm,TeePerson loginPerson) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(loginPerson, "");
		
		List<TeeMessageGroupModel> modelList = new ArrayList<TeeMessageGroupModel>();
		String hql="";
		if(isSuperAdmin){
			hql+="from TeeMessageGroup";
		}else{
			hql+="from TeeMessageGroup where groupCreator = " + loginPerson.getUuid();
		}
		long total = simpleDaoSupport.count("select count(*) " + hql, null);
		List<TeeMessageGroup> list = messageGroupDao.pageFind(hql, (dm.getPage() - 1)
				* dm.getRows(), dm.getRows(), null);
		long count = messageGroupDao.count("select count(*) " + hql, null);

		for (int i = 0; i < list.size(); i++) {
			TeeMessageGroupModel model = new TeeMessageGroupModel();
			BeanUtils.copyProperties(list.get(i), model);
			model.setToName("");
			if(!TeeUtility.isNullorEmpty(model.getToId())){
				String[] personInfo = personDao.getPersonNameAndUuidByUuids(model.getToId());
				model.setToId(personInfo[0]);
				model.setToName(personInfo[1]);
			}
			TeePerson person = personDao.get(model.getGroupCreator());
			model.setGroupCreatorName("");
			if(person != null ){
				model.setGroupCreator(person.getUuid());
				model.setGroupCreatorName(person.getUserName());
			}
			modelList.add(model);
		}
		json.setTotal(total);// 设置总记录数
		json.setRows(modelList);// 设置返回的行
		return json;
	}

	public TeeMessageGroupDao getMessageGroupDao() {
		return messageGroupDao;
	}

	
	/**
	 * 根据系统当前登录人获取  所有群组
	 * @param loginPerson 系统当前登录人
	 * @return
	 */
	@Transactional(readOnly=true)
	public TeeJson getMyGroup(TeePerson loginPerson) {
		TeeJson json = new TeeJson();
		List<TeeMessageGroup> list = new ArrayList<TeeMessageGroup>();
		
		List<TeeMessageGroupModel> modelList = new ArrayList<TeeMessageGroupModel>();
		list = messageGroupDao.getAllGroup();
		
		Iterator<TeeMessageGroup> it = list.iterator();
		String ids = null;
		TeeMessageGroup g = null;
		while(it.hasNext()){
			g = it.next();
			if(g.getGroupFlag()==1){
				continue;
			}
			ids = g.getToId();
			if(!TeeStringUtil.existString(TeeStringUtil.parseStringArray(ids), String.valueOf(loginPerson.getUuid()))){
				it.remove();
			}
			
			TeeMessageGroupModel model = new TeeMessageGroupModel();
			BeanUtils.copyProperties(g, model);
			model.setToName("");
//			if(!TeeUtility.isNullorEmpty(model.getToId())){
//				String[] personInfo = personDao.getPersonNameAndUuidByUuids(model.getToId());
//				model.setToId(personInfo[0]);
//				model.setToName(personInfo[1]);
//			}
			TeePerson person = personDao.get(model.getGroupCreator());
			model.setGroupCreatorName("");
			if(person != null ){
				model.setGroupCreator(person.getUuid());
				model.setGroupCreatorName(person.getUserName());
			}
			modelList.add(model);
		}
		
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}
	


	 
	public void setMessageGroupDao(TeeMessageGroupDao messageGroupDao) {
		this.messageGroupDao = messageGroupDao;
	}


	public TeePersonDao getPersonDao() {
		return personDao;
	}


	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}
	
	
}



	
	