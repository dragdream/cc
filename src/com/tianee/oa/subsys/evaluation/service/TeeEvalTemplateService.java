package com.tianee.oa.subsys.evaluation.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalTemplate;
import com.tianee.oa.subsys.evaluation.bean.TeeEvalTemplateItem;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalTemplateDao;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalTemplateItemDao;
import com.tianee.oa.subsys.evaluation.model.TeeEvalTemplateModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class TeeEvalTemplateService extends TeeBaseService{
	@Autowired
	private TeeEvalTemplateDao templateDao;
	
	@Autowired
	private TeeEvalTemplateItemDao templateItemDao;
	
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeEvalTemplateModel model) {
		TeeJson json = new TeeJson();
		TeeEvalTemplate template = new TeeEvalTemplate();
		if(model.getSid() > 0){
		    template  = templateDao.getById(model.getSid());
			if(template != null){
				BeanUtils.copyProperties(model, template);
				templateDao.updateEvalTemplate(template);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
			
		}else{
			BeanUtils.copyProperties(model, template);
			templateDao.addEvalTemplate(template);
	/*		*//**
			 * 这里需要新建模板项第一项
			 *//*
			TeeEvalTemplateItem templateItem = new TeeEvalTemplateItem();
			templateItem.setEvalTemplate(template);
			templateItem.setName("考核项目");
			templateItemDao.save(templateItem);*/
		}
		json.setRtState(true);
		json.setRtData(template);
		json.setRtMsg("保存成功！");
		return json;
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
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request, String sids) {
		TeeJson json = new TeeJson();
		templateDao.delByIds(sids);
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
	public TeeJson getById(HttpServletRequest request, TeeEvalTemplateModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeEvalTemplate template = templateDao.getById(model.getSid());
			if(template !=null){
				model = parseModel(template);
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
		return templateDao.datagird(dm, requestDatas);
	}

	
}