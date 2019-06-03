package com.tianee.oa.subsys.evaluation.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalScoringDesign;
import com.tianee.oa.subsys.evaluation.bean.TeeEvalTemplate;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalScoringDesignDao;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalTemplateDao;
import com.tianee.oa.subsys.evaluation.model.TeeEvalScoringDesignModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class TeeEvalScoringDesignService extends TeeBaseService{
	@Autowired
	private TeeEvalScoringDesignDao scoringDesignDao;
	
	@Autowired
	private TeeEvalTemplateDao templateDao;
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeEvalScoringDesignModel model) {
		TeeJson json = new TeeJson();
		TeeEvalScoringDesign scoringDesign = new TeeEvalScoringDesign();
		if(model.getSid() > 0){
		    scoringDesign  = scoringDesignDao.getById(model.getSid());
			if(scoringDesign != null){
				BeanUtils.copyProperties(model, scoringDesign);
				scoringDesignDao.updateScoringDesign(scoringDesign);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
			
		}else{
			BeanUtils.copyProperties(model, scoringDesign);
			if(model.getEvalTemplateId()>0){
				TeeEvalTemplate evalTemplate = templateDao.get(model.getEvalTemplateId());
				scoringDesign.setEvalTemplate(evalTemplate);
			}
			scoringDesignDao.addScoringDesign(scoringDesign);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
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
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request, String sids) {
		TeeJson json = new TeeJson();
		scoringDesignDao.delByIds(sids);
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
	public TeeJson getById(HttpServletRequest request, TeeEvalScoringDesignModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeEvalScoringDesign scoringDesign = scoringDesignDao.getById(model.getSid());
			if(scoringDesign !=null){
				model = parseModel(scoringDesign);
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
		return scoringDesignDao.datagird(dm, requestDatas);
	}

	
}