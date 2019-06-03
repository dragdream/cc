package com.tianee.oa.subsys.evaluation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalScoringItemType;
import com.tianee.oa.subsys.evaluation.model.TeeEvalScoringItemTypeModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeEvalScoringItemDao")
public class TeeEvalScoringItemDao extends TeeBaseDao<TeeEvalScoringItemType>{
	/**
	 * @author nieyi
	 * @param itemType
	 */
	public void addEvalScoreItemType(TeeEvalScoringItemType itemType) {
		save(itemType);
	}
	
	/**
	 * @author nieyi
	 * @param itemType
	 */
	public void updateEvalScoreItemType(TeeEvalScoringItemType itemType) {
		update(itemType);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalScoringItemType loadById(int id) {
		TeeEvalScoringItemType intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalScoringItemType getById(int id) {
		TeeEvalScoringItemType intf = get(id);
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
			String hql = "delete from TeeEvalScoringItemType where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeEvalScoringItemType itemType";
		long total = countByList("select count(*) "+hql, null);
		
		hql += " order by itemType.sid desc ";
		List<TeeEvalScoringItemType> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		List<TeeEvalScoringItemTypeModel> models = new ArrayList<TeeEvalScoringItemTypeModel>();
		for(TeeEvalScoringItemType itemType:infos){
			TeeEvalScoringItemTypeModel m = new TeeEvalScoringItemTypeModel();
			m=parseModel(itemType);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param itemType
	 * @return
	 */
	public TeeEvalScoringItemTypeModel parseModel(TeeEvalScoringItemType itemType){
		TeeEvalScoringItemTypeModel model = new TeeEvalScoringItemTypeModel();
		if(itemType == null){
			return null;
		}
		BeanUtils.copyProperties(itemType, model);
		return model;
	}
}