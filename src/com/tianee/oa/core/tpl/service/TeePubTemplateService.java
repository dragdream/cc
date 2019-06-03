package com.tianee.oa.core.tpl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.tpl.bean.TeePubTemplate;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeePubTemplateService extends TeeBaseService{
	
	/**
	 * 添加模版
	 * @param pubTemplate
	 */
	public void addTemplate(TeePubTemplate pubTemplate){
		simpleDaoSupport.save(pubTemplate);
	}
	
	/**
	 * 更新模版
	 * @param pubTemplate
	 */
	public void updateTemplate(TeePubTemplate pubTemplate){
		simpleDaoSupport.update(pubTemplate);
	}
	
	/**
	 * 删除模版
	 * @param sid
	 * @return
	 */
	public TeePubTemplate deleteTemplate(int sid){
		TeePubTemplate pubTemplate = getTemplate(sid);
		simpleDaoSupport.deleteByObj(pubTemplate);
		return pubTemplate;
	}
	
	public TeePubTemplate getTemplate(int sid){
		TeePubTemplate pubTemplate = (TeePubTemplate) simpleDaoSupport.get(TeePubTemplate.class, sid);
		return pubTemplate;
	}
	
	public List<TeePubTemplate> listTemplateSimple(){
		String hql = "select pt.sid as SID,pt.tplName as tplName,pt.tplDesc as tplDesc from TeePubTemplate pt order by pt.sortNo asc";
		List<Map> list = simpleDaoSupport.getMaps(hql, null);
		List<TeePubTemplate> tmps = new ArrayList<TeePubTemplate>();
		for(Map data:list){
			TeePubTemplate pub = new TeePubTemplate();
			pub.setSid(TeeStringUtil.getInteger(data.get("SID"), 0));
			pub.setTplName(TeeStringUtil.getString(data.get("tplName")));
			pub.setTplDesc(TeeStringUtil.getString(data.get("tplDesc")));
			tmps.add(pub);
		}
		return tmps;
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestData){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeePubTemplate pt ";
		List<TeePubTemplate> list = simpleDaoSupport.pageFind(hql+"order by pt.sortNo asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
}
