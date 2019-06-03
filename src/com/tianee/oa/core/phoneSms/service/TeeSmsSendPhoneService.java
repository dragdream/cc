package com.tianee.oa.core.phoneSms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.phoneSms.dao.TeeSmsSendPhoneDao;
import com.tianee.oa.core.phoneSms.model.TeeSmsSendPhoneModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeSmsSendPhoneService extends TeeBaseService{
	@Autowired
	private TeeSmsSendPhoneDao sendPhoneDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeSmsSendPhoneModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeSmsSendPhone sendPhone = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cl = Calendar.getInstance();
		try {
			cl.setTime(sf.parse(model.getSendTimeDesc()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(model.getSid() > 0){
//		    sendPhone  = sendPhoneDao.getById(model.getSid());
//			if(sendPhone != null){
//				BeanUtils.copyProperties(model, sendPhone);
//				sendPhone.setFromId(person.getUuid());
//				if(!TeeUtility.isNullorEmpty(model.getSendTimeDesc())){
//					try {
//						cl.setTime(sf.parse(model.getSendTimeDesc()));
//						sendPhone.setSendTime(cl);
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//				}
//				sendPhone.setSendFlag('0');
//				sendPhoneDao.updateSendPhoneInfo(sendPhone);
//			}else{
//				json.setRtState(false);
//				json.setRtMsg("未查到到相关手机信息！");
//				return json;
//			}
//		}else{
//			BeanUtils.copyProperties(model, sendPhone);
//			sendPhone.setFromId(person.getUuid());
//			sendPhone.setSendTime(cl);
//			sendPhone.setSendFlag('0');
//			sendPhoneDao.addSendPhoneInfo(sendPhone);
//		}
		
		//处理收信人[内部]
		if(!"".equals(model.getToId())){
			String toIds [] = model.getToId().split(",");
			TeePerson recUser = null;
			for(int i=0;i<toIds.length;i++){
				recUser = personDao.get(Integer.parseInt(toIds[i]));
				if(!TeeUtility.isNullorEmpty(recUser.getMobilNo())){
					sendPhone = new TeeSmsSendPhone();
					sendPhone.setContent(model.getContent());
					sendPhone.setFromName(person.getUserName());
					sendPhone.setFromId(person.getUuid());
					sendPhone.setPhone(recUser.getMobilNo());
					sendPhone.setToId(recUser.getUuid());
					sendPhone.setToName(recUser.getUserName());
					sendPhone.setSendTime(cl);
					sendPhone.setSendFlag(0);
					sendPhoneDao.addSendPhoneInfo(sendPhone);
				}
			}
		}
		
		//处理收信人[外部]
		if(!"".equals(model.getPhone())){
			String phone = model.getPhone().replace("，", ",");
			String sp [] = phone.split(",");
			for(int i=0;i<sp.length;i++){
				if(!TeeUtility.isNullorEmpty(sp[i])){
					sendPhone = new TeeSmsSendPhone();
					sendPhone.setContent(model.getContent());
					sendPhone.setFromName(person.getUserName());
					sendPhone.setFromId(person.getUuid());
					sendPhone.setPhone(sp[i]);
					sendPhone.setSendTime(cl);
					sendPhone.setSendFlag(0);
					sendPhoneDao.addSendPhoneInfo(sendPhone);
				}
			}
		}
		
		
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 对象转换
	 * @author nieyi
	 * @param sendPhone
	 * @return
	 */
	public TeeSmsSendPhoneModel parseModel(TeeSmsSendPhone sendPhone){
		TeeSmsSendPhoneModel model = new TeeSmsSendPhoneModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(sendPhone == null){
			return null;
		}
		BeanUtils.copyProperties(sendPhone, model);
		if(!TeeUtility.isNullorEmpty(sendPhone.getSendTime())){
			String sendTimeDesc = sf.format(sendPhone.getSendTime().getTime());
			model.setSendTimeDesc(sendTimeDesc);
		}
		if(!TeeUtility.isNullorEmpty(sendPhone.getToId())){
			TeePerson toUser = (TeePerson)personDao.get(sendPhone.getToId());
			model.setToUserName(toUser.getUserName());
		}
		/**
		 * 0-未发送
			1-发送成功
			2-发送超时，请人工确认
			3-发送中...
		 */
		if(!TeeUtility.isNullorEmpty(sendPhone.getSendFlag())){
			int flag = sendPhone.getSendFlag();
			if(flag==0){
				model.setSendFlagDesc("未发送");
			}else if(flag==1){
				model.setSendFlagDesc("发送成功");
			}else if(flag==2){
				model.setSendFlagDesc("发送超时，请人工确认");
			}else if(flag==3){
				model.setSendFlagDesc("发送中...");
			}
		}
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
		json.setRtState(true);
		sendPhoneDao.delByIds(sids);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeSmsSendPhoneModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeSmsSendPhone sendPhone = sendPhoneDao.getById(model.getSid());
			if(sendPhone !=null){
				model = parseModel(sendPhone);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关记录！");
		return json;
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		return sendPhoneDao.datagird(dm, requestDatas);
	}


	public List<TeeSmsSendPhoneModel> getTotalByConditon(Map requestDatas) {
		return sendPhoneDao.getTotalByConditon(requestDatas);
	}

	/**
	 * 手机短信发送
	 * @param request
	 * @return
	 */
	public TeeJson sendPhoneSms(Map request) {
		TeeJson json = new TeeJson();
		String toUserIds=TeeStringUtil.getString(request.get("hasRemindPriv"), "");
		List<TeePerson> hasRemindPrivPersonList = (List<TeePerson>) request.get("hasRemindPrivPersonList");
		String smsContent=TeeStringUtil.getString(request.get("smsContent"),"");
		String sendTime=TeeStringUtil.getString(request.get("sendTime"),"");
		TeePerson person = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		if(toUserIds.endsWith(",")){
			toUserIds=toUserIds.substring(0,toUserIds.length()-1);
		}
		if(toUserIds.length() > 0){
			String[] userIds = toUserIds.split(",");
			TeePerson user = null;
			String userPhone = null;
			for(int i=0;i<userIds.length;i++){
				user=(TeePerson)personDao.load(Integer.parseInt(userIds[i]));
				userPhone=user.getMobilNo();
				if(!TeeUtility.isNullorEmpty(userPhone)){
					//保存手机发送记录（本地）
					TeeSmsSendPhone sendPhone = new TeeSmsSendPhone();
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar cl = Calendar.getInstance();
					try {
						if(!TeeUtility.isNullorEmpty(sendTime)){
							cl.setTime(sf.parse(sendTime+" 00:00:00"));
						}
						sendPhone.setFromId(person.getUuid());
						sendPhone.setFromName(person.getUserName());
						sendPhone.setSendTime(cl);
						sendPhone.setSendFlag(0);
						sendPhone.setContent(smsContent);
						sendPhone.setToId(user.getUuid());
						sendPhone.setToName(user.getUserName());
						sendPhone.setPhone(userPhone);
						sendPhoneDao.addSendPhoneInfo(sendPhone);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			json.setRtState(true);
			json.setRtMsg("手机短信发送成功!");
			return json;
		}else if(hasRemindPrivPersonList!=null){
			String[] userIds = toUserIds.split(",");
			TeePerson user = null;
			String userPhone = null;
			for(int i=0;i<hasRemindPrivPersonList.size();i++){
				user=(TeePerson)hasRemindPrivPersonList.get(i);
				userPhone=user.getMobilNo();
				if(!TeeUtility.isNullorEmpty(userPhone)){
					//保存手机发送记录（本地）
					TeeSmsSendPhone sendPhone = new TeeSmsSendPhone();
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Calendar cl = Calendar.getInstance();
					try {
						if(!TeeUtility.isNullorEmpty(sendTime)){
							cl.setTime(sf.parse(sendTime+" 00:00:00"));
						}
						sendPhone.setFromId(person.getUuid());
						sendPhone.setFromName(person.getUserName());
						sendPhone.setSendTime(cl);
						sendPhone.setSendFlag(0);
						sendPhone.setContent(smsContent);
						sendPhone.setToId(user.getUuid());
						sendPhone.setToName(user.getUserName());
						sendPhone.setPhone(userPhone);
						sendPhoneDao.addSendPhoneInfo(sendPhone);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			json.setRtState(true);
			json.setRtMsg("手机短信发送成功!");
			return json;
		}
		json.setRtState(false);
		json.setRtMsg("短信发送失败！");
		return json;
	}
	
}