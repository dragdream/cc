package com.tianee.oa.subsys.evaluation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalTemplateItem;
import com.tianee.oa.subsys.evaluation.model.TeeEvalTemplateItemModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeEvalTemplateItemDao")
public class TeeEvalTemplateItemDao extends TeeBaseDao<TeeEvalTemplateItem>{
	/**
	 * @author nieyi
	 * @param templateItem
	 */
	public void addTemplateItem(TeeEvalTemplateItem templateItem) {
		save(templateItem);
	}
	
	/**
	 * @author nieyi
	 * @param templateItem
	 */
	public void updateTemplateItem(TeeEvalTemplateItem templateItem) {
		update(templateItem);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalTemplateItem loadById(int id) {
		TeeEvalTemplateItem intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalTemplateItem getById(int id) {
		TeeEvalTemplateItem intf = get(id);
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
			String hql = "delete from TeeEvalTemplateItem where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int evalTemplateId = TeeStringUtil.getInteger(requestDatas.get("evalTemplateId"), 0);
		String hql = "from TeeEvalTemplateItem templateItem where 1=1";
		if(evalTemplateId>0){
			hql+=" and templateItem.evalTemplate.sid="+evalTemplateId;
		}
		long total = countByList("select count(*) "+hql, null);
		
		hql += " order by templateItem.sid desc ";
		List<TeeEvalTemplateItem> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		List<TeeEvalTemplateItemModel> models = new ArrayList<TeeEvalTemplateItemModel>();
		for(TeeEvalTemplateItem templateItem:infos){
			TeeEvalTemplateItemModel m = new TeeEvalTemplateItemModel();
			m=parseModel(templateItem);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param templateItem
	 * @return
	 */
	public TeeEvalTemplateItemModel parseModel(TeeEvalTemplateItem templateItem){
		TeeEvalTemplateItemModel model = new TeeEvalTemplateItemModel();
		if(templateItem == null){
			return null;
		}
		BeanUtils.copyProperties(templateItem, model);
		if(null!=templateItem.getEvalTemplate()){
			model.setEvalTemplateId(templateItem.getEvalTemplate().getSid());
			model.setEvalTemplateSubject(templateItem.getEvalTemplate().getSubject());
		}
		return model;
	}

	public List<TeeEvalTemplateItem> getTemplateItemRoot(int evalTemplateId) {
		String hql=" from TeeEvalTemplateItem item where item.evalTemplate.sid="+evalTemplateId +" and item.level is null";
		List<TeeEvalTemplateItem> itemList = executeQuery(hql, null);
		return itemList;
	}

	public List<TeeEvalTemplateItem> getItemListByParent(String level,int sid,int evalTemplateId) {
		String hql = "from TeeEvalTemplateItem item where item.evalTemplate.sid="+evalTemplateId +" and( item.level like'/" + level + "%'  or item.sid="+sid+") order by item.sid asc";
		return executeQuery(hql, null);
	}

	public List<TeeEvalTemplateItemModel> getEvalTemplateItem(String level) {
		String hql=" from TeeEvalTemplateItem item where item.level like '%/"+level+"/'";
		List<TeeEvalTemplateItem> itemList = executeQuery(hql, null);
		List<TeeEvalTemplateItemModel> models = new ArrayList<TeeEvalTemplateItemModel>();
		for(TeeEvalTemplateItem templateItem:itemList){
			TeeEvalTemplateItemModel m = new TeeEvalTemplateItemModel();
			m=parseModel(templateItem);	
			models.add(m);
		}
		return models;
	}
}