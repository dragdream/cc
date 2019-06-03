package com.tianee.oa.core.workflow.workmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowSeniorQueryTemplate;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowSeniorQueryTemplateModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFlowSeniorQueryTemplateService extends TeeBaseService implements TeeFlowSeniorQueryTemplateServiceInterface{

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryTemplateServiceInterface#addOrUpdate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的参数
		int templateId=TeeStringUtil.getInteger(request.getParameter("templateId"),0);
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		String basicInfo=TeeStringUtil.getString(request.getParameter("basicInfo"));
		String formInfo=TeeStringUtil.getString(request.getParameter("formInfo"));
		String statisticInfo=TeeStringUtil.getString(request.getParameter("statisticInfo"));
		String templateName=TeeStringUtil.getString(request.getParameter("templateName"));
        
		TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowId);
		
		if(templateId>0){//编辑
			
			TeeFlowSeniorQueryTemplate t=(TeeFlowSeniorQueryTemplate) simpleDaoSupport.get(TeeFlowSeniorQueryTemplate.class,templateId);
			if(t!=null){
				t.setBasicInfo(basicInfo);
				t.setFormInfo(formInfo);
				t.setStatisticInfo(statisticInfo);
				simpleDaoSupport.update(t);
				json.setRtState(true);
				json.setRtMsg("修改成功！");
			}else{
				json.setRtState(false);
				json.setRtMsg("模板不存在！");
			}
		}else{//新增
			TeeFlowSeniorQueryTemplate template=new TeeFlowSeniorQueryTemplate();
			template.setBasicInfo(basicInfo);
			template.setFlowType(flowType);
			template.setFormInfo(formInfo);
			template.setStatisticInfo(statisticInfo);
			template.setTemplateName(templateName);
			template.setUser(loginUser);
			simpleDaoSupport.save(template);
			
			json.setRtState(true);
			json.setRtMsg("保存成功！");
		}

		return json;
	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryTemplateServiceInterface#renderTempalte(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson renderTempalte(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取前台页面传来的flowId
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		
		String hql=" from TeeFlowSeniorQueryTemplate t where t.user.uuid=? and t.flowType.sid=? ";
		
		List<TeeFlowSeniorQueryTemplate> list=simpleDaoSupport.executeQuery(hql, new Object[]{loginUser.getUuid(),flowId});
		List<TeeFlowSeniorQueryTemplateModel> modelList=new ArrayList<TeeFlowSeniorQueryTemplateModel>();
		TeeFlowSeniorQueryTemplateModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeFlowSeniorQueryTemplate t : list) {
				model=parseToModel(t);
				modelList.add(model);
			}
		}
		
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}



	
	/**
	 * 实体类转换成model
	 * @param t
	 * @return
	 */
	private TeeFlowSeniorQueryTemplateModel parseToModel(
			TeeFlowSeniorQueryTemplate t) {
		TeeFlowSeniorQueryTemplateModel model=new TeeFlowSeniorQueryTemplateModel();
		BeanUtils.copyProperties(t, model);
		if(t.getFlowType()!=null){
			model.setFlowId(t.getFlowType().getSid());
		}
		
		if(t.getUser()!=null){
			model.setUserId(t.getUser().getUuid());
			model.setUserName(t.getUser().getUserName());
		}
		return model;
	}



	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryTemplateServiceInterface#getInfoBySid(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int templateId=TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		TeeFlowSeniorQueryTemplate t=(TeeFlowSeniorQueryTemplate) simpleDaoSupport.get(TeeFlowSeniorQueryTemplate.class,templateId);
		
		if(t!=null){
			TeeFlowSeniorQueryTemplateModel model=parseToModel(t);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		
		return json;
	}

}
