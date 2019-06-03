package com.tianee.oa.core.phoneSms.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.phoneSms.bean.TeeSmsRecvPhone;
import com.tianee.oa.core.phoneSms.model.TeeSmsRecvPhoneModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeSmsRecvPhoneDao")
public class TeeSmsRecvPhoneDao extends TeeBaseDao<TeeSmsRecvPhone>{
	/**
	 * @author nieyi
	 * @param recvPhone
	 */
	public void addRecvPhoneInfo(TeeSmsRecvPhone recvPhone) {
		save(recvPhone);
	}
	
	/**
	 * @author nieyi
	 * @param recvPhone
	 */
	public void updateRecvPhoneInfo(TeeSmsRecvPhone recvPhone) {
		update(recvPhone);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeSmsRecvPhone loadById(int id) {
		TeeSmsRecvPhone intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeSmsRecvPhone getById(int id) {
		TeeSmsRecvPhone intf = get(id);
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
			String hql = "delete from TeeSmsRecvPhone where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List param = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String phone = (String)requestDatas.get("phone");
		String startTimeDesc = (String)requestDatas.get("startTimeDesc");
		String endTimeDesc = (String)requestDatas.get("endTimeDesc");
		String hql = "from TeeSmsRecvPhone recv where 1=1";
		if(!TeeUtility.isNullorEmpty(phone)){
			hql+=" and recv.phone like ?";
			param.add("%"+phone+"%");
		}
		if(!TeeUtility.isNullorEmpty(startTimeDesc)){
			Calendar cl = Calendar.getInstance();
			try {
				cl.setTime(sf.parse(startTimeDesc));
				hql+=" and recv.sendTime>=?";
				param.add(cl);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl = Calendar.getInstance();
			try {
				cl.setTime(sf.parse(endTimeDesc));
				hql+=" and recv.sendTime<=?";
				param.add(cl);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		List<TeeSmsRecvPhone> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = countByList("select count(*) "+hql, param);
		List<TeeSmsRecvPhoneModel> models = new ArrayList<TeeSmsRecvPhoneModel>();
		for(TeeSmsRecvPhone recvPhone:infos){
			TeeSmsRecvPhoneModel m = new TeeSmsRecvPhoneModel();
			m=parseModel(recvPhone);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param recvPhone
	 * @return
	 */
	public TeeSmsRecvPhoneModel parseModel(TeeSmsRecvPhone recvPhone){
		TeeSmsRecvPhoneModel model = new TeeSmsRecvPhoneModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(recvPhone == null){
			return null;
		}
		BeanUtils.copyProperties(recvPhone, model);
		if(!TeeUtility.isNullorEmpty(recvPhone.getSendTime())){
			String sendTimeDesc = sf.format(recvPhone.getSendTime().getTime());
			model.setSendTimeDesc(sendTimeDesc);
		}
		return model;
	}

	/**
	 * 根据条件查出所有数据
	 * @param requestDatas
	 * @return
	 */
	public List<TeeSmsRecvPhoneModel> getTotalByConditon(Map requestDatas) {
		List param = new ArrayList();
		String hql = "from TeeSmsRecvPhone recvPhone where 1=1";
		List<TeeSmsRecvPhone> infos = super.executeQueryByList(hql, param);
		List<TeeSmsRecvPhoneModel> models = new ArrayList<TeeSmsRecvPhoneModel>();
		for(TeeSmsRecvPhone recvPhone:infos){
			TeeSmsRecvPhoneModel m = new TeeSmsRecvPhoneModel();
			m=parseModel(recvPhone);	
			models.add(m);
		}
		return models;
	}
}