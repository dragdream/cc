package com.tianee.oa.core.partthree.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.partthree.bean.TeePartThreeRule;
import com.tianee.oa.core.partthree.model.TeePartThreeRuleModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeePartThreeRuleService extends TeeBaseService{

	
	/**
	 * 获取所有的规则列表
	 * @param request
	 * @return
	 */
	public TeeJson getAllRules(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String hql=" from TeePartThreeRule order by sid asc ";
		List<TeePartThreeRule> list=simpleDaoSupport.executeQuery(hql, null);
		List<TeePartThreeRuleModel> modelList=new ArrayList<TeePartThreeRuleModel>();
		if(list!=null&&list.size()>0){
			TeePartThreeRuleModel model=null;
			for (TeePartThreeRule teePartThreeRule : list) {
				model=parseToModel(teePartThreeRule);
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

	
	/**
	 * 转换成model
	 * @param teePartThreeRule
	 * @return
	 */
	private TeePartThreeRuleModel parseToModel(TeePartThreeRule teePartThreeRule) {
		TeePartThreeRuleModel model=new TeePartThreeRuleModel();
		BeanUtils.copyProperties(teePartThreeRule, model);
		if(teePartThreeRule.getOperPriv()==1){//系统管理员
			model.setOperPrivDesc("系统管理员");
		}else if(teePartThreeRule.getOperPriv()==2){//系统安全员
			model.setOperPrivDesc("系统安全员");
		}else{//安全审计员
			model.setOperPrivDesc("安全审计员");
		}
		return model;
	}


	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeePartThreeRule rule=(TeePartThreeRule) simpleDaoSupport.get(TeePartThreeRule.class,sid);
		if(rule!=null){
			TeePartThreeRuleModel model=parseToModel(rule);
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}


	
	/**
	 * 修改规则
	 * @param request
	 * @return
	 */
	public TeeJson updateRule(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		int operPriv=TeeStringUtil.getInteger(request.getParameter("operPriv"),0);
		int isOpen=TeeStringUtil.getInteger(request.getParameter("isOpen"),0);
		
		TeePartThreeRule rule=(TeePartThreeRule) simpleDaoSupport.get(TeePartThreeRule.class,sid);
		if(rule!=null){
			rule.setIsOpen(isOpen);
			rule.setOperPriv(operPriv);
			simpleDaoSupport.update(rule);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}

	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public TeePartThreeRule getRuleByRuleCode(String ruleCode) {
		String hql=" from TeePartThreeRule  where ruleCode=? ";
		TeePartThreeRule rule=(TeePartThreeRule) simpleDaoSupport.unique(hql,new Object[]{ruleCode});
		return rule;
	}
}
