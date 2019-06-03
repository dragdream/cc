package com.tianee.oa.core.base.meeting.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.meeting.bean.TeeMeetingEquipment;
import com.tianee.oa.core.base.meeting.model.TeeMeetingEquipmentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmContactUser;
import com.tianee.oa.subsys.crm.core.customer.model.TeeCrmContactUserModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 *
 */
@Repository("meetingEquipmentDao")
public class TeeMeetingEquipmentDao  extends TeeBaseDao<TeeMeetingEquipment> {
	/**
	 * @author syl
	 * 增加
	 * @param TeeMeetingEquipment
	 */
	public void addEquipment(TeeMeetingEquipment equipment) {
		save(equipment);
	}
	
	/**
	 * @author syl
	 * 更新
	 * @param TeeMeetingEquipment
	 */
	public void updateEquipment(TeeMeetingEquipment equipment) {
		update(equipment);
	}
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeMeetingEquipment loadById(int id) {
		TeeMeetingEquipment intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 查询
	 * @param 
	 */
	public TeeMeetingEquipment getById(int id) {
		TeeMeetingEquipment intf = get(id);
		return intf;
	}
	
	
	/**
	 * @author syl
	 * byId 删除
	 * @param 
	 */
	public void delById(int id) {
		delete(id);
	}
	
	/**
	 * 删除 by Ids
	 * 
	 * @author syl
	 * @date 2014-1-6
	 * @param ids
	 */
	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeMeetingEquipment where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	/**
	 * @author syl
	 * 查询所有记录
	 * @param 
	 */
	public  List<TeeMeetingEquipment> getAllEquipment() {
		String hql = "from TeeMeetingEquipment where 1=1";
		List<TeeMeetingEquipment> list = (List<TeeMeetingEquipment>) executeQuery(hql,null);
		return list;
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)requestDatas.get("LOGIN_USER");
		String hql = "from TeeMeetingEquipment where 1=1";
		List<TeeMeetingEquipment> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		long total = countByList("select count(*) "+hql, null);
		List<TeeMeetingEquipmentModel> models = new ArrayList<TeeMeetingEquipmentModel>();
		for(TeeMeetingEquipment equipment:infos){
			TeeMeetingEquipmentModel m = new TeeMeetingEquipmentModel();
			BeanUtils.copyProperties(equipment, m);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}	
}
