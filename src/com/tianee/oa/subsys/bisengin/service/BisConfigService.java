package com.tianee.oa.subsys.bisengin.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class BisConfigService extends TeeBaseService{
	
	public boolean isOpenBisEngine(int type){
		Map data = simpleDaoSupport.getMap("select status as status from BisConfig where type="+type, null);
		if(data==null){
			return false;
		}else{
			if("1".equals(TeeStringUtil.getString(data.get("status")))){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public void setStatus(int type,int status){
		simpleDaoSupport.executeUpdate("update BisConfig set status="+status+" where type="+type, null);
	}
	
	public TeeEasyuiDataGridJson datagrid(){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from BisConfig b ";
		List list = simpleDaoSupport.find(hql+"order by b.sid asc", null);
		long total = simpleDaoSupport.count("select count(b.sid) "+hql, null);
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
}
