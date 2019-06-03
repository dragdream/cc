package com.tianee.oa.subsys.evaluation.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalScoreLevel;
import com.tianee.oa.subsys.evaluation.bean.TeeEvalTemplate;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalScoreLevelDao;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalTemplateDao;
import com.tianee.oa.subsys.evaluation.model.TeeEvalScoreLevelModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class TeeEvalScoreLevelService extends TeeBaseService{
	@Autowired
	private TeeEvalScoreLevelDao scoreLevelDao;
	
	@Autowired
	private TeeEvalTemplateDao templateDao;
	
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeEvalScoreLevelModel model) {
		TeeJson json = new TeeJson();
		TeeEvalScoreLevel scoreLevel = new TeeEvalScoreLevel();
		if(model.getSid() > 0){
		    scoreLevel  = scoreLevelDao.getById(model.getSid());
			if(scoreLevel != null){
				BeanUtils.copyProperties(model, scoreLevel);
				scoreLevelDao.updateScoreLevel(scoreLevel);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
			
		}else{
			BeanUtils.copyProperties(model, scoreLevel);
			if(model.getEvalTemplateId()>0){
				TeeEvalTemplate evalTemplate = templateDao.get(model.getEvalTemplateId());
				scoreLevel.setEvalTemplate(evalTemplate);
			}
			scoreLevelDao.addScoreLevel(scoreLevel);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
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
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request, String sids) {
		TeeJson json = new TeeJson();
		scoreLevelDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeEvalScoreLevelModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeEvalScoreLevel scoreLevel = scoreLevelDao.getById(model.getSid());
			if(scoreLevel !=null){
				model = parseModel(scoreLevel);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关客户记录！");
		return json;
	}
	

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		return scoreLevelDao.datagird(dm, requestDatas);
	}

	
}