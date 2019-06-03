package com.tianee.oa.core.general.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeePortalTemplate;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Repository("TeePortalTemplateDao")
public class TeePortalTemplateDao extends TeeBaseDao<TeePortalTemplate>{
	/*
	 * 保存
	 */
	public TeeEasyuiDataGridJson getTemplateList(TeeDataGridModel dm,Map requestDatas,int type){
		TeePerson person = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		List param = new ArrayList();
		String hql = "from TeePortalTemplate tpl where 1=1 ";
		if(type==1){
			hql+=" and (tpl.userPriv like '%"+person.getUuid()+".u%' or tpl.deptPriv like '%"+person.getDept().getUuid()+".d%' or tpl.rolePriv like '%"+person.getUserRole().getUuid()+".r%')";
		}
		long total = countByList("select count(*) "+hql, param);
		
		if(dm.getSort()!=null){
			hql+=" order by "+dm.getSort()+" "+dm.getOrder();
		}else{
			hql+=" order by tpl.sid asc";
		}
		List<TeePortalTemplate> list =pageFindByList(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), param);
		datagird.setRows(list);
		datagird.setTotal(total);
		return datagird;
	}

	/**
	 * 
	 * @param person
	 * @return 
	 */
	public List<TeePortalTemplate> getTemplateList(TeePerson person,int deptId) {
		List<TeePortalTemplate> list = new ArrayList<TeePortalTemplate>();
		List param = new ArrayList();
		String hql = "from TeePortalTemplate tpl where 1=1 ";
		if(deptId>0){
			hql+=" and (tpl.userPriv like '%"+person.getUuid()+".u%' or tpl.deptPriv like '%"+deptId+".d%' or tpl.rolePriv like '%"+person.getUserRole().getUuid()+".r%')";
		}else{
			if(person.getDept()!=null){
				hql+=" and (tpl.userPriv like '%"+person.getUuid()+".u%' or tpl.deptPriv like '%"+person.getDept().getUuid()+".d%' or tpl.rolePriv like '%"+person.getUserRole().getUuid()+".r%')";
			}else{
				hql+=" and (tpl.userPriv like '%"+person.getUuid()+".u%' or tpl.deptPriv like '%"+"0"+".d%' or tpl.rolePriv like '%"+person.getUserRole().getUuid()+".r%')";
			}
			
			
		}
		hql+=" order by tpl.sid asc";
		list = find(hql, null);
		return list;
	}
	
}