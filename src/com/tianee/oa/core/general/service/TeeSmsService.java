package com.tianee.oa.core.general.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.dao.TeeSmsBodyDao;
import com.tianee.oa.core.general.dao.TeeSmsDao;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.page.PageUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeSmsService extends TeeBaseService {

	@Autowired
	private TeeSmsDao smsDao;
	@Autowired
	private TeeSmsBodyDao smsBodyDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;

	public TeeEasyuiDataGridJson getSendSmsList(TeeDataGridModel dm,String fromId) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<Object> values = new ArrayList<Object>();
		String hql = "from TeeSmsBody smsBody where smsBody.fromId=" + fromId + " and smsBody.delFlag=0 ";
		long total = simpleDaoSupport.count("select count(*) " + hql, null);
		List<TeeSmsBody> smsBodyList = simpleDaoSupport.pageFind(hql+"order by smsBody.sendTime desc", dm.getRows()* (dm.getPage() - 1), dm.getRows(), null);
		List<TeeSmsModel> modelList = new ArrayList<TeeSmsModel>();
		for (int i = 0; i < smsBodyList.size(); i++) {
			TeeSmsModel model = new TeeSmsModel();
			smsBodyToModel(smsBodyList.get(i), model);
			modelList.add(model);
			model.setSendTimeDesc(format.format(model.getSendTime().getTime()));
		}
		j.setTotal(total);// 设置总记录数
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	public void smsToModel(TeeSms sms, TeeSmsModel model) {
		BeanUtils.copyProperties(sms, model);
		TeePerson toUser = personDao.get(sms.getToId());
		TeePerson fromUser = personDao.get(sms.getSmsBody().getFromId());
		model.setToUser(toUser == null ? "" : toUser.getUserName());
		model.setFromUser(fromUser == null ? "" : fromUser.getUserName());
		if (sms.getRemindFlag() == 0) {
			model.setRemindFlagDesc("未阅读");
		} else if(sms.getRemindFlag() == 1) {
			model.setRemindFlagDesc("已阅读");
		}else if(sms.getRemindFlag() == 2) {
			model.setRemindFlagDesc("已弹出未阅读");
		}

		if (sms.getDeleteFlag() == 0) {
			model.setDeleteFlagDesc("未删除");
		} else if (sms.getDeleteFlag() == 1) {
			model.setDeleteFlagDesc("收信人删除");
		} else if (sms.getDeleteFlag() == 2) {
			model.setDeleteFlagDesc("发信人删除");
		}

		model.setRemindTimeDesc(TeeDateUtil.format(sms.getRemindTime()));
		model.setSmsSid(sms.getUuid());

		smsBodyToModel(sms.getSmsBody(), model);
	}

	public void smsBodyToModel(TeeSmsBody smsBody, TeeSmsModel model) {
		BeanUtils.copyProperties(smsBody, model);
		model.setSmsBodySid(smsBody.getUuid());
		TeePerson fromUser = personDao.get(smsBody.getFromId());
		model.setFromUser(fromUser == null ? "" : fromUser.getUserName());

//		String hql = "select sms.toId as TO_ID from TeeSms sms where sms.smsBody.uuid='"
//				+ smsBody.getUuid()+"'";
//		List<Map> toIds = simpleDaoSupport.getMaps(hql, null);
		String ids = "";
		String names = "";
		List<TeeSms> smsList = smsBody.getSmsList();
		TeePerson toPerson = null;
		for(int i=0;i<smsList.size();i++){
			toPerson = personDao.get(smsList.get(i).getToId());
			if(toPerson!=null){
				ids+=toPerson.getUuid()+",";
				names+=toPerson.getUserName()+",";
			}
		}
		
		if(!ids.equals("")){
			ids = ids.substring(0,ids.length()-1);
			names = names.substring(0,names.length()-1);
		}
		
//		for (int i = 0; i < toIds.size(); i++) {
//			ids += toIds.get(i).get("TO_ID");
//			if (toIds.size() - 1 != i) {
//				ids += ",";
//			}
//		}
//
//		hql = "select p.userName as USER_NAME from TeePerson p where p.uuid in ("
//				+ ids + ")";
//		List<Map> toUsers = simpleDaoSupport.getMaps(hql, null);
//		for (int i = 0; i < toUsers.size(); i++) {
//			names += toUsers.get(i).get("USER_NAME");
//			if (toUsers.size() - 1 != i) {
//				names += ",";
//			}
//		}

		model.setToUserIds(ids);
		model.setToUsers(names);
		model.setModuleNoDesc(TeeModuleConst.MODULE_SORT_TYPE.get(model.getModuleNo()));
		
		model.setSendTimeDesc(TeeDateUtil.format(smsBody.getSendTime()));
	}

	/**
	 * 获取站内短消息提醒 by lt
	 * 
	 * @param dm
	 * @param requestData
	 * @return
	 */
	public TeeEasyuiDataGridJson smsDatas(TeeDataGridModel dm, Map requestData) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int toId = TeeStringUtil.getInteger(requestData.get("toId"), 0);
		// 消息类型
		String moduleNo = TeeStringUtil.getString(requestData.get("moduleNo"));
		
		// 消息状态
		int remindFlag = TeeStringUtil.getInteger(
				requestData.get("remindFlag"), 0);

		String hql = "from TeeSms sms where sms.toId = " + toId
				+ " and sms.deleteFlag = 0 ";
		if (!"".equals(moduleNo) && !"0".equals(moduleNo)) {
			hql += " and sms.smsBody.moduleNo='" + moduleNo+"'";
		}
		if (remindFlag != -1) {
			if(remindFlag==0){
				hql += " and sms.remindFlag in (0,2)";
			}else{
				hql += " and sms.remindFlag=" + remindFlag;
			}
		}

		hql += " and sms.smsBody.sendFlag=1 ";
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeSms> smsList = smsDao.pageFind(hql+"order by sms.remindTime desc", (dm.getPage() - 1)
				* dm.getRows(), dm.getRows(), null);
		long count = smsDao.count("select count(*) " + hql, null);

		List<TeeSmsModel> modelList = new ArrayList<TeeSmsModel>();
		for (int i = 0; i < smsList.size(); i++) {
			 TeeSmsModel model = new TeeSmsModel();
			 smsToModel(smsList.get(i), model);
			 modelList.add(model);
			 
			 model.setRemindTimeDesc(format.format(model.getRemindTime().getTime()));
			 model.setSendTimeDesc(format.format(model.getSendTime().getTime()));
		}
		j.setTotal(total);// 设置总记录数
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	public TeeEasyuiDataGridJson receiveDatagrid(TeeDataGridModel dm,
			String toId) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		List<Object> values = new ArrayList<Object>();
		String hql = "from TeeSms sms where sms.toId="+toId+" and sms.deleteFlag=0 and sms.smsBody.delFlag=0 ";
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeSms> smsList = simpleDaoSupport.pageFind(hql+"order by sms.sid desc", dm
				.getRows()
				* (dm.getPage() - 1), dm.getRows(), null);
		List<TeeSmsModel> modelList = new ArrayList<TeeSmsModel>();
		for (int i = 0; i < smsList.size(); i++) {
			 TeeSmsModel model = new TeeSmsModel();
			 smsToModel(smsList.get(i), model);
			 modelList.add(model);
		}
		j.setTotal(total);// 设置总记录数
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 设置阅读标记
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void updateReadFlag(String ids) {
		ids = TeeStringUtil.formatIdsQuote(ids);
		if (!"".equals(ids)) {
			simpleDaoSupport.executeUpdate(
					"update TeeSms sms set sms.remindFlag=1 where sms.uuid in ("
							+ ids + ")", null);
		}
	}

	 /**
	 * 桌面sms列表
	 * @param sms
	 * @return
	 */
	 public List<TeeSmsModel> portletReceiveDatagrid(String toId) {
	 List<Object> values = new ArrayList<Object>();
	 String hql =
	 "select sms,sb from TeeSms sms left join sms.smsBody sb where sms.toId = '"+toId+"' and sms.deleteFlag <> 1 and sms.remindFlag = 0";
	 List<Object> objList = smsDao.getList(hql, values);
	 List<TeeSmsModel> modelList = new ArrayList<TeeSmsModel>();
	 for(int i = 0;i<objList.size();i++){
	 TeeSmsModel model = new TeeSmsModel();
	 Object[] obj = (Object[]) objList.get(i);
	 TeeSms sms = (TeeSms) obj[0];
	 TeeSmsBody smsBody = (TeeSmsBody)obj[1];
	 model.setContent(smsBody.getContent());
	 model.setSmsSid(sms.getUuid());
	 String fromUser = personDao.getPersonNameByUuid(smsBody.getFromId()+"");
	 String toUser = personDao.getPersonNameByUuid(sms.getToId()+"");
	 if(smsBody.getFromId()==0){
		 fromUser = "<Strong><font color='red'>系统信息</font></Strong>";
	 }
	 model.setFromUser(fromUser);
	 model.setToUser(toUser);
	 model.setRemindFlag(sms.getRemindFlag());
	 model.setRemindUrl(sms.getSmsBody().getRemindUrl());
	 modelList.add(model);
	 }
	
	 return modelList;
	 }

	/**
	 * 标记未读
	 * 
	 * @param sms
	 * @return
	 */
	@Transactional(readOnly = false)
	public void resetFlag(String toId, String smsIds) {
		if (!smsIds.equals(" ")) {
			smsIds = smsIds.trim();
			if (smsIds.endsWith(",")) {
				smsIds = smsIds.substring(0, smsIds.length() - 1);
			}
			// System.out.println("smsIds:"+smsIds);
			String[] smsIdStr = smsIds.split(",");
			for (String smsId : smsIdStr) {
				smsId = smsId.trim();
				// System.out.println("smsId:"+smsId);
				TeeSms sms1 = smsDao.get(Integer.parseInt(smsId));
				String bodyId = sms1.getSmsBody().getUuid() + "";
				if ("".equals(bodyId.trim())) {
					continue;
				}
				// 解决sms表 body_seq_id和to_id索引出数据不唯一的情况
				List<String> list = null;
				try {
					list = smsDao.getSmsSeqIds(toId, bodyId);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String id : list) {
					TeeSms sms = (TeeSms) smsDao.get(id);
					sms.setToId(TeeStringUtil.getInteger(toId, 0));
					sms.setRemindFlag(0);
					smsDao.update(sms);
				}
			}
		}
	}

	/**
	 * 获取信息
	 * 
	 * @param sms
	 * @return
	 */

	public long remindCheck(String personUUid) {

		long result = 0;
		try {
			result = smsDao.remindCheck(personUUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public void popup(String personUUid) {
		try {
			smsDao.popup(personUUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 全部已阅
	 * @param userId
	 */
	public void viewAll(int userId) {
		simpleDaoSupport.executeUpdate("update TeeSms sms set sms.remindFlag=1 where sms.toId="+userId, null);
	}
	
	/**
	 * 获取当前待办的消息分组
	 * @param userId
	 */
	public List<Map> getUnreadSmsGroup(int userId) {
		List<Map> tmpList = simpleDaoSupport.executeNativeQuery("select body.module_no as ID,count(body.module_no) as COUNT from sms sms,sms_body body where sms.body_id=body.UUID and sms.remind_Flag=0 and sms.to_id="+userId+" group by body.module_no ", null, 0, Integer.MAX_VALUE);
		Iterator<Map> it = tmpList.iterator();
		while(it.hasNext()){
			Map data = it.next();
			if(TeeStringUtil.getString(data.get("ID")).equals("")
					|| TeeStringUtil.getString(TeeModuleConst.MODULE_SORT_TYPE.get(""+data.get("ID"))).equals("")
					|| TeeStringUtil.getInteger(data.get("COUNT"), 0)==0){
				it.remove();
				continue;
			}
			data.put("name", TeeStringUtil.getString(TeeModuleConst.MODULE_SORT_TYPE.get(""+data.get("ID"))));
			data.put("id", data.get("ID"));
			data.put("count", data.get("COUNT"));
			data.remove("ID");
			data.remove("COUNT");
		}
		
		return tmpList;
	}
	
	/**
	 * 获取信息
	 * 
	 * @param sms
	 * @return
	 */
	public String getRemindInBox(String personUUid) {

		String data = "";
		try {
			data = smsDao.getRemindInBox(personUUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 返回详情页
	 * 
	 * @return
	 */

	public List<Map<String, String>> viewDetails(String smsIds, int toId) {
		List<Map<String, String>> map = new ArrayList<Map<String, String>>();
		try {
			map = smsDao.getViewDetails(smsIds, toId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 删除短信
	 * 
	 * @param bId
	 * @return
	 */
	@Transactional(readOnly = false)
	public void doDelSms(int bId, String deType, int personId) {
		try {
			TeeSms sms = smsDao.get(bId);
			smsDao.doDelSms(sms.getSmsBody().getUuid(), deType, personId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 收信人删除sms
	 * @param smsSid
	 */
	public void delSms(String smsSid) {
		String hql = "update TeeSms sms set sms.deleteFlag=1 where sms.uuid='"+smsSid+"'";
		simpleDaoSupport.executeUpdate(hql, null);
	}

	/**
	 * 发信人删除smsBody
	 * 
	 * @param smsBodySid
	 */
	public void delSmsBody(String smsBodySid) {
		String hql = "update TeeSmsBody smsBody set smsBody.delFlag=1 where smsBody.uuid='"+smsBodySid+"'";
		simpleDaoSupport.executeUpdate(hql, null);
		hql = "update TeeSms sms set sms.deleteFlag=2 where sms.smsBody.uuid='"+smsBodySid+"'";
		simpleDaoSupport.executeUpdate(hql, null);
	}

	/**
	 * 删除短信列表
	 * 
	 * @param bId
	 * @return
	 */
	@Transactional(readOnly = false)
	public void delSmsList(int id, int deleteFlag) {

		TeeSms sms = smsDao.get(id);
		sms.setDeleteFlag(deleteFlag);
		smsDao.update(sms);

	}

	/**
	 * quarzJob
	 * 
	 * @return
	 */
	public void exectSql(String sql) {
		try {
			smsDao.getByExectSql(sql, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * quarzJob
	 * 
	 * @return
	 */
	public int getMaxId(String sql) {
		int maxId = 0;
		try {
			maxId = smsDao.getMaxId(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxId;
	}

	/**
	 * quarzJob
	 * 
	 * @return
	 */
	public List<Object[]> getQuarzList(String sql) {
		List<Object[]> objList = new ArrayList<Object[]>();
		try {
			objList = smsDao.getQuarzList(sql, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objList;
	}

	public TeeSmsDao getSmsDao() {
		return smsDao;
	}

	public void setSmsDao(TeeSmsDao smsDao) {
		this.smsDao = smsDao;
	}

	public TeeSmsBodyDao getSmsBodyDao() {
		return smsBodyDao;
	}

	public void setSmsBodyDao(TeeSmsBodyDao smsBodyDao) {
		this.smsBodyDao = smsBodyDao;
	}

	public TeePersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

}
