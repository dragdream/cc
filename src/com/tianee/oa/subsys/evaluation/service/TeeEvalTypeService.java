package com.tianee.oa.subsys.evaluation.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalType;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalTypeDao;
import com.tianee.oa.subsys.evaluation.model.TeeEvalTypeModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class TeeEvalTypeService extends TeeBaseService{
	@Autowired
	private TeeEvalTypeDao evalTypeDao;
	
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeEvalTypeModel model) {
		TeeJson json = new TeeJson();
		TeeEvalType evalType = new TeeEvalType();
		if(model.getSid() > 0){
		    evalType  = evalTypeDao.getById(model.getSid());
			if(evalType != null){
				BeanUtils.copyProperties(model, evalType);
				evalTypeDao.updateEvalType(evalType);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
			
		}else{
			BeanUtils.copyProperties(model, evalType);
			evalTypeDao.addEvalType(evalType);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
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
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request, String sids) {
		TeeJson json = new TeeJson();
		evalTypeDao.delByIds(sids);
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
	public TeeJson getById(HttpServletRequest request, TeeEvalTypeModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeEvalType evalType = evalTypeDao.getById(model.getSid());
			if(evalType !=null){
				model = parseModel(evalType);
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
		return evalTypeDao.datagird(dm, requestDatas);
	}

	
}