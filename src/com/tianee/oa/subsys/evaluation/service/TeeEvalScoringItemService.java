package com.tianee.oa.subsys.evaluation.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.evaluation.bean.TeeEvalScoringItemType;
import com.tianee.oa.subsys.evaluation.dao.TeeEvalScoringItemDao;
import com.tianee.oa.subsys.evaluation.model.TeeEvalScoringItemTypeModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class TeeEvalScoringItemService extends TeeBaseService{
	@Autowired
	private TeeEvalScoringItemDao itemTypeDao;
	
	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeEvalScoringItemTypeModel model) {
		TeeJson json = new TeeJson();
		TeeEvalScoringItemType itemType = new TeeEvalScoringItemType();
		if(model.getSid() > 0){
		    itemType  = itemTypeDao.getById(model.getSid());
			if(itemType != null){
				BeanUtils.copyProperties(model, itemType);
				itemTypeDao.updateEvalScoreItemType(itemType);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
			
		}else{
			BeanUtils.copyProperties(model, itemType);
			itemTypeDao.addEvalScoreItemType(itemType);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
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
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request, String sids) {
		TeeJson json = new TeeJson();
		itemTypeDao.delByIds(sids);
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
	public TeeJson getById(HttpServletRequest request, TeeEvalScoringItemTypeModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeEvalScoringItemType itemType = itemTypeDao.getById(model.getSid());
			if(itemType !=null){
				model = parseModel(itemType);
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
		return itemTypeDao.datagird(dm, requestDatas);
	}

	
}