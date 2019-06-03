package com.tianee.oa.subsys.evaluation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalTemplate;
import com.tianee.oa.subsys.evaluation.model.TeeEvalTemplateModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeEvalTemplateDao")
public class TeeEvalTemplateDao extends TeeBaseDao<TeeEvalTemplate>{
	/**
	 * @author nieyi
	 * @param template
	 */
	public void addEvalTemplate(TeeEvalTemplate template) {
		save(template);
	}
	
	/**
	 * @author nieyi
	 * @param template
	 */
	public void updateEvalTemplate(TeeEvalTemplate template) {
		update(template);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalTemplate loadById(int id) {
		TeeEvalTemplate intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalTemplate getById(int id) {
		TeeEvalTemplate intf = get(id);
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
			String hql = "delete from TeeEvalTemplate where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeEvalTemplate template";
		long total = countByList("select count(*) "+hql, null);
		
		hql += " order by template.sid desc ";
		List<TeeEvalTemplate> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		List<TeeEvalTemplateModel> models = new ArrayList<TeeEvalTemplateModel>();
		for(TeeEvalTemplate template:infos){
			TeeEvalTemplateModel m = new TeeEvalTemplateModel();
			m=parseModel(template);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param template
	 * @return
	 */
	public TeeEvalTemplateModel parseModel(TeeEvalTemplate template){
		TeeEvalTemplateModel model = new TeeEvalTemplateModel();
		if(template == null){
			return null;
		}
		BeanUtils.copyProperties(template, model);
		return model;
	}
}