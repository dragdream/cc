package com.tianee.oa.core.phoneSms.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.phoneSms.model.TeeSmsSendPhoneModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeSmsSendPhoneDao")
public class TeeSmsSendPhoneDao extends TeeBaseDao<TeeSmsSendPhone>{
	/**
	 * @author nieyi
	 * @param sendPhone
	 */
	public void addSendPhoneInfo(TeeSmsSendPhone sendPhone) {
		save(sendPhone);
	}
	
	/**
	 * @author nieyi
	 * @param sendPhone
	 */
	public void updateSendPhoneInfo(TeeSmsSendPhone sendPhone) {
		update(sendPhone);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeSmsSendPhone loadById(int id) {
		TeeSmsSendPhone intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeSmsSendPhone getById(int id) {
		TeeSmsSendPhone intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * @author nieyi
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeSmsSendPhone where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List param = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		String fromId = (String)requestDatas.get("fromId");
		String phone = (String)requestDatas.get("phone");
		String startTimeDesc = (String)requestDatas.get("startTimeDesc");
		String endTimeDesc = (String)requestDatas.get("endTimeDesc");
		TeePerson person = (TeePerson)requestDatas.get("LOGIN_USER");
		String hql = "from TeeSmsSendPhone send where 1=1";
//		if(!TeeUtility.isNullorEmpty(fromId)){
//			hql+=" and send.fromId like ?";
//			param.add("%"+fromId+"%");
//		}
		if(!TeeUtility.isNullorEmpty(phone)){
			hql+=" and send.phone like ?";
			param.add("%"+phone+"%");
		}
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl = Calendar.getInstance();
			try {
				cl.setTime(sf.parse(startTimeDesc));
				hql+=" and send.sendTime>=?";
				param.add(cl);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl = Calendar.getInstance();
			try {
				cl.setTime(sf.parse(endTimeDesc));
				hql+=" and send.sendTime<=?";
				param.add(cl);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(person)){
			hql+=" and send.fromId=?";
			param.add(person.getUuid());
		}
		
		List<TeeSmsSendPhone> infos = super.pageFindByList(hql+" order by sid desc", (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = countByList("select count(*) "+hql, param);
//		List<TeeSmsSendPhoneModel> models = new ArrayList<TeeSmsSendPhoneModel>();
//		for(TeeSmsSendPhone sendPhone:infos){
//			TeeSmsSendPhoneModel m = new TeeSmsSendPhoneModel();
//			m=parseModel(sendPhone);
//			models.add(m);
//		}
		dataGridJson.setRows(infos);
		dataGridJson.setTotal(total);
		return dataGridJson;
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
	 * 根据条件查出所有数据
	 * @param requestDatas
	 * @return
	 */
	public List<TeeSmsSendPhoneModel> getTotalByConditon(Map requestDatas) {
		List param = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fromId = (String)requestDatas.get("fromId");
		String phone = (String)requestDatas.get("phone");
		String startTimeDesc = (String)requestDatas.get("startTimeDesc");
		String endTimeDesc = (String)requestDatas.get("endTimeDesc");
		String hql = "from TeeSmsSendPhone send where 1=1";
		if(!TeeUtility.isNullorEmpty(fromId)){
			hql+=" and send.fromId like ?";
			param.add("%"+fromId+"%");
		}
		if(!TeeUtility.isNullorEmpty(phone)){
			hql+=" and send.phone like ?";
			param.add("%"+phone+"%");
		}
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl = Calendar.getInstance();
			try {
				cl.setTime(sf.parse(startTimeDesc));
				hql+=" and send.sendTime>=?";
				param.add(cl);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl = Calendar.getInstance();
			try {
				cl.setTime(sf.parse(endTimeDesc));
				hql+=" and send.sendTime>=?";
				param.add(cl);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<TeeSmsSendPhone> infos = super.executeQueryByList(hql, param);
		List<TeeSmsSendPhoneModel> models = new ArrayList<TeeSmsSendPhoneModel>();
		for(TeeSmsSendPhone sendPhone:infos){
			TeeSmsSendPhoneModel m = new TeeSmsSendPhoneModel();
			m=parseModel(sendPhone);	
			models.add(m);
		}
		return models;
	}
}