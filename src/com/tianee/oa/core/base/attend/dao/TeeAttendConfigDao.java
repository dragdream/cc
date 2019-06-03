package com.tianee.oa.core.base.attend.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.attend.bean.TeeAttendConfig;
import com.tianee.oa.core.base.attend.bean.TeeAttendDuty;
import com.tianee.oa.core.base.attend.model.TeeAttendConfigModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("attendConfigDao")
public class TeeAttendConfigDao  extends TeeBaseDao<TeeAttendConfig> {
	
	public void addConfig(TeeAttendConfig config) {
		save(config);
	}
	
	
	public void updateConfig(TeeAttendConfig config) {
		update(config);
	}

	public TeeAttendConfig loadById(int id) {
		TeeAttendConfig intf = load(id);
		return intf;
	}
	
	public TeeAttendConfig getById(int id) {
		TeeAttendConfig intf = get(id);
		return intf;
	}
	
	
	public void delById(int id) {
		delete(id);
	}
	

	public void delByIds(String ids){
		
		if(!TeeUtility.isNullorEmpty(ids)){
			if(ids.endsWith(",")){
				ids= ids.substring(0, ids.length() -1 );
			}
			String hql = "delete from TeeAttendConfig where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	

	public  List<TeeAttendConfig> getConfigList() {
		String hql = "from TeeAttendConfig order by  sid asc";
		List<TeeAttendConfig> list = (List<TeeAttendConfig>) executeQuery(hql,null);
		return list;
	}	
	
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		String hql = "from TeeAttendConfig oc where 1=1 ";
		long total = count("select count(*) "+hql, null);
		
		hql+=" order by oc.sid asc";
		List rows = new ArrayList();
		List<TeeAttendConfig> list = pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		for(TeeAttendConfig config:list){
			TeeAttendConfigModel model = new TeeAttendConfigModel();
			BeanUtils.copyProperties(config, model);
			if(!TeeUtility.isNullorEmpty(config.getDutyTime1())){
				
				model.setDutyTimeDesc1(sf.format(config.getDutyTime1().getTime()));
			}
			if(!TeeUtility.isNullorEmpty(config.getDutyTime2())){
				
				model.setDutyTimeDesc2(sf.format(config.getDutyTime2().getTime()));
			}
			if(!TeeUtility.isNullorEmpty(config.getDutyTime3())){
				
				model.setDutyTimeDesc3(sf.format(config.getDutyTime3().getTime()));
			}
			if(!TeeUtility.isNullorEmpty(config.getDutyTime4())){
				
				model.setDutyTimeDesc4(sf.format(config.getDutyTime4().getTime()));
			}
			if(!TeeUtility.isNullorEmpty(config.getDutyTime5())){
				
				model.setDutyTimeDesc5(sf.format(config.getDutyTime5().getTime()));
			}
			if(!TeeUtility.isNullorEmpty(config.getDutyTime6())){
				
				model.setDutyTimeDesc6(sf.format(config.getDutyTime6().getTime()));
			}
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
}