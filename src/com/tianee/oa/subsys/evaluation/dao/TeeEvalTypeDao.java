package com.tianee.oa.subsys.evaluation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalType;
import com.tianee.oa.subsys.evaluation.model.TeeEvalTypeModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeEvalTypeDao")
public class TeeEvalTypeDao extends TeeBaseDao<TeeEvalType>{
	/**
	 * @author nieyi
	 * @param evalType
	 */
	public void addEvalType(TeeEvalType evalType) {
		save(evalType);
	}
	
	/**
	 * @author nieyi
	 * @param evalType
	 */
	public void updateEvalType(TeeEvalType evalType) {
		update(evalType);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalType loadById(int id) {
		TeeEvalType intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalType getById(int id) {
		TeeEvalType intf = get(id);
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
			String hql = "delete from TeeEvalType where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeEvalType evalType";
		long total = countByList("select count(*) "+hql, null);
		
		hql += " order by evalType.sid desc ";
		List<TeeEvalType> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		List<TeeEvalTypeModel> models = new ArrayList<TeeEvalTypeModel>();
		for(TeeEvalType evalType:infos){
			TeeEvalTypeModel m = new TeeEvalTypeModel();
			m=parseModel(evalType);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param evalType
	 * @return
	 */
	public TeeEvalTypeModel parseModel(TeeEvalType evalType){
		TeeEvalTypeModel model = new TeeEvalTypeModel();
		if(evalType == null){
			return null;
		}
		BeanUtils.copyProperties(evalType, model);
		return model;
	}
}