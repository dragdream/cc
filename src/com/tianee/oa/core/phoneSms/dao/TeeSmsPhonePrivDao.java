package com.tianee.oa.core.phoneSms.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.phoneSms.bean.TeeSmsPhonePriv;
import com.tianee.oa.core.phoneSms.model.TeeSmsPhonePrivModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeSmsPhonePrivDao")
public class TeeSmsPhonePrivDao extends TeeBaseDao<TeeSmsPhonePriv>{
	/**
	 * @author nieyi
	 * @param priv
	 */
	public void addPhonePrivInfo(TeeSmsPhonePriv priv) {
		save(priv);
	}
	
	/**
	 * @author nieyi
	 * @param priv
	 */
	public void updatePhonePrivInfo(TeeSmsPhonePriv priv) {
		update(priv);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeSmsPhonePriv loadById(int id) {
		TeeSmsPhonePriv intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeSmsPhonePriv getById(int id) {
		TeeSmsPhonePriv intf = get(id);
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
			String hql = "delete from TeeSmsPhonePriv where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeSmsPhonePriv priv where 1=1";
		List<TeeSmsPhonePriv> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = countByList("select count(*) "+hql, null);
		List<TeeSmsPhonePrivModel> models = new ArrayList<TeeSmsPhonePrivModel>();
		for(TeeSmsPhonePriv priv:infos){
			TeeSmsPhonePrivModel m = new TeeSmsPhonePrivModel();
			m=parseModel(priv);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param priv
	 * @return
	 */
	public TeeSmsPhonePrivModel parseModel(TeeSmsPhonePriv priv){
		TeeSmsPhonePrivModel model = new TeeSmsPhonePrivModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(priv == null){
			return null;
		}
		BeanUtils.copyProperties(priv, model);
		return model;
	}

	/**
	 * 根据条件查出所有数据
	 * @param requestDatas
	 * @return
	 */
	public List<TeeSmsPhonePrivModel> getTotalByConditon(Map requestDatas) {
		List param = new ArrayList();
		String hql = "from TeeSmsPhonePriv priv where 1=1";
		
		List<TeeSmsPhonePriv> infos = super.executeQueryByList(hql, param);
		List<TeeSmsPhonePrivModel> models = new ArrayList<TeeSmsPhonePrivModel>();
		for(TeeSmsPhonePriv priv:infos){
			TeeSmsPhonePrivModel m = new TeeSmsPhonePrivModel();
			m=parseModel(priv);	
			models.add(m);
		}
		return models;
	}

	public TeeSmsPhonePriv getPhonePriv() {
		List param = new ArrayList();
		String hql = "from TeeSmsPhonePriv priv where 1=1";
		List<TeeSmsPhonePriv> infos = super.executeQueryByList(hql, param);
		if(infos.size()>0){
			return infos.get(0);
		}else{
			return null;
		}
	}
}