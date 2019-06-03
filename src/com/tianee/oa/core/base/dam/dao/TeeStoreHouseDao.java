package com.tianee.oa.core.base.dam.dao;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.dam.bean.TeeStoreHouse;
import com.tianee.oa.core.base.dam.model.TeeStoreHouseModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeStoreHouseDao")
public class TeeStoreHouseDao extends TeeBaseDao<TeeStoreHouse>{
	/*
	 * 保存
	 */
	public void addRoom(TeeStoreHouse room){
		save(room);
	}
	
	public void updateRoom(TeeStoreHouse room) {
		update(room);	
	}
	
	
	public TeeEasyuiDataGridJson getRoomList(TeeDataGridModel dm,Map requestDatas){
		String roomCode = (String)requestDatas.get("roomCode");
		String roomName = (String)requestDatas.get("roomName");
		String deptId = (String)requestDatas.get("deptId");
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		List param = new ArrayList();
		String hql = "from TeeStoreHouse oc where 1=1 ";
		if(!TeeUtility.isNullorEmpty(roomCode)){
			hql+=" and oc.roomCode like ?";
			param.add("%"+roomCode+"%");
		}
		if(!TeeUtility.isNullorEmpty(roomName)){
			hql+=" and oc.roomName like ?";
			param.add("%"+roomName+"%");
		}
		if(!TeeUtility.isNullorEmpty(deptId)){
			hql+=" and oc.dept.uuid = ?";
			param.add(Integer.parseInt(deptId));
		}
		long total = countByList("select count(*) "+hql, param);
		hql+=" order by oc.sid asc";
		List rows = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List<TeeStoreHouse> list =pageFindByList(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), param);
		for(TeeStoreHouse room:list){
			TeeStoreHouseModel model = new TeeStoreHouseModel();
			BeanUtils.copyProperties(room, model);
			String addTimeDesc="";
			if(!TeeUtility.isNullorEmpty(room.getCreateTime())){
				addTimeDesc=sf.format(room.getCreateTime().getTime());
			}
			model.setCreateTimeStr(addTimeDesc);
			
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	public List<TeeStoreHouse> getAllRoom(){
		String hql = "from TeeStoreHouse oc where 1=1 ";
		hql+=" order by oc.sid asc";
		List<TeeStoreHouse> list=executeQuery(hql, null);
		return list;
	}
}