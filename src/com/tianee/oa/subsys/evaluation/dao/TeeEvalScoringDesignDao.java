package com.tianee.oa.subsys.evaluation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalScoringDesign;
import com.tianee.oa.subsys.evaluation.model.TeeEvalScoringDesignModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeEvalScoringDesignDao")
public class TeeEvalScoringDesignDao extends TeeBaseDao<TeeEvalScoringDesign>{
	/**
	 * @author nieyi
	 * @param scoringDesign
	 */
	public void addScoringDesign(TeeEvalScoringDesign scoringDesign) {
		save(scoringDesign);
	}
	
	/**
	 * @author nieyi
	 * @param scoringDesign
	 */
	public void updateScoringDesign(TeeEvalScoringDesign scoringDesign) {
		update(scoringDesign);
	}
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalScoringDesign loadById(int id) {
		TeeEvalScoringDesign intf = load(id);
		return intf;
	}
	
	
	/**
	 * @author nieyi
	 * @param id
	 * @return
	 */
	public TeeEvalScoringDesign getById(int id) {
		TeeEvalScoringDesign intf = get(id);
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
			String hql = "delete from TeeEvalScoringDesign where sid in (" + ids + ")";
			deleteOrUpdateByQuery(hql, null);
		}
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int evalTemplateId = TeeStringUtil.getInteger(requestDatas.get("evalTemplateId"), 0);
		
		String hql = "from TeeEvalScoringDesign scoringDesign where 1=1 ";
		//if(evalTemplateId>0){
			hql+=" and scoringDesign.evalTemplate.sid="+evalTemplateId;
		//}
		long total = countByList("select count(*) "+hql, null);
		
		hql += " order by scoringDesign.sid desc ";
		List<TeeEvalScoringDesign> infos = super.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), null);
		List<TeeEvalScoringDesignModel> models = new ArrayList<TeeEvalScoringDesignModel>();
		for(TeeEvalScoringDesign scoringDesign:infos){
			TeeEvalScoringDesignModel m = new TeeEvalScoringDesignModel();
			m=parseModel(scoringDesign);	
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	/**
	 * 对象转换
	 * @author nieyi
	 * @param scoringDesign
	 * @return
	 */
	public TeeEvalScoringDesignModel parseModel(TeeEvalScoringDesign scoringDesign){
		TeeEvalScoringDesignModel model = new TeeEvalScoringDesignModel();
		if(scoringDesign == null){
			return null;
		}
		BeanUtils.copyProperties(scoringDesign, model);
		if(null!=scoringDesign.getEvalTemplate()){
			model.setEvalTemplateId(scoringDesign.getEvalTemplate().getSid());
			model.setEvalTemplateSubject(scoringDesign.getEvalTemplate().getSubject());
		}
		return model;
	}
}