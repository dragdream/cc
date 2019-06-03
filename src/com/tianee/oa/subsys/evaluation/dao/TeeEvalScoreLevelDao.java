package com.tianee.oa.subsys.evaluation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalScoreLevel;
import com.tianee.oa.subsys.evaluation.model.TeeEvalScoreLevelModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeEvalScoreLevelDao")
public class TeeEvalScoreLevelDao extends TeeBaseDao<TeeEvalScoreLevel>{
	/**
	 * @author nieyi
	 * @param scoreLevel
	 */
	public void addScoreLevel(TeeEvalScoreLevel scoreLevel) {
		save(scoreLevel);
	}
	
	/**
	 * @author nieyi
	 * @param scoreLevel
	 */
	public void updateScoreLevel(TeeEvalScoreLevel scoreLevel) {
		update(scoreLevel);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalScoreLevel loadById(int id) {
		TeeEvalScoreLevel intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalScoreLevel getById(int id) {
		TeeEvalScoreLevel intf = get(id);
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
			String hql = "delete from TeeEvalScoreLevel where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int evalTemplateId = TeeStringUtil.getInteger(requestDatas.get("evalTemplateId"), 0);
		String hql = "from TeeEvalScoreLevel scoreLevel where 1=1 ";
		//if(evalTemplateId>0){
			hql+=" and scoreLevel.evalTemplate.sid="+evalTemplateId;
		//}
		long total = countByList("select count(*) "+hql, null);
		hql += " order by scoreLevel.sid asc ";
		List<TeeEvalScoreLevel> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		List<TeeEvalScoreLevelModel> models = new ArrayList<TeeEvalScoreLevelModel>();
		for(TeeEvalScoreLevel scoreLevel:infos){
			TeeEvalScoreLevelModel m = new TeeEvalScoreLevelModel();
			m=parseModel(scoreLevel);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param scoreLevel
	 * @return
	 */
	public TeeEvalScoreLevelModel parseModel(TeeEvalScoreLevel scoreLevel){
		TeeEvalScoreLevelModel model = new TeeEvalScoreLevelModel();
		if(scoreLevel == null){
			return null;
		}
		BeanUtils.copyProperties(scoreLevel, model);
		if(null!=scoreLevel.getEvalTemplate()){
			model.setEvalTemplateId(scoreLevel.getEvalTemplate().getSid());
			model.setEvalTemplateSubject(scoreLevel.getEvalTemplate().getSubject());
		}
		return model;
	}
}