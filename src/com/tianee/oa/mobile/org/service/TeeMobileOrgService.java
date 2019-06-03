package com.tianee.oa.mobile.org.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;

@Service
public class TeeMobileOrgService extends TeeBaseService{
	
	
	public TeeEasyuiDataGridJson queryPersons(String keyWords,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<TeePerson> list = simpleDaoSupport.pageFind("from TeePerson where userName like '%"+TeeDbUtility.formatString(keyWords)+"%'", dm.getFirstResult(), dm.getRows(), null);
		List<Map> mapList = new ArrayList();
		Map map = null;
		for(TeePerson person:list){
			map = new HashMap();
			map.put("uuid", person.getUuid());
			map.put("userId", person.getUserId());
			map.put("userName", person.getUserName());
			if(person.getDept()!=null){
				map.put("deptName", person.getDept().getDeptName());
			}else{
				map.put("deptName", "");
			}
			if(person.getUserRole()!=null){
				map.put("roleName", person.getUserRole().getRoleName());
			}else{
				map.put("roleName", "");
			}
			
			mapList.add(map);
		}
		
		dataGridJson.setRows(mapList);
		dataGridJson.setTotal(simpleDaoSupport.count("select count(uuid) from TeePerson where userName like '%"+TeeDbUtility.formatString(keyWords)+"%'", null));
		
		return dataGridJson;
	}
}
