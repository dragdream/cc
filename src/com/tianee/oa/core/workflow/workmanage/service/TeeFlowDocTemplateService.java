package com.tianee.oa.core.workflow.workmanage.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowDocTemplate;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowDocTemplateModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFlowDocTemplateService extends TeeBaseService{

	@Autowired
	private TeeAttachmentService  attachmentService;
	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
	    TeeJson json=new TeeJson();
	    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
	    //流程类型
	    int flowTypeId=TeeStringUtil.getInteger(request.getParameter("flowTypeId"), 0);
	    //模板名称
	    String templateName=TeeStringUtil.getString(request.getParameter("templateName"));
	    //插件类路径
	    String pluginClassPath=TeeStringUtil.getString(request.getParameter("pluginClassPath"));
		//附件id
	    int attachId=TeeStringUtil.getInteger(request.getParameter("attachId"), 0);
	   
	    TeeAttachment att=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,attachId);
	    
	    TeeFlowType ft=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowTypeId);
	    
	    TeeFlowDocTemplate tpl=null;
	    if(sid>0){//编辑
	    	tpl=(TeeFlowDocTemplate) simpleDaoSupport.get(TeeFlowDocTemplate.class,sid);
	    	tpl.setAttach(att);
	    	tpl.setFlowType(ft);
	    	tpl.setPluginClassPath(pluginClassPath);
            tpl.setTemplateName(templateName);
	        simpleDaoSupport.update(tpl);
	    
	    }else{//新建
	    	tpl=new TeeFlowDocTemplate();
	    	tpl.setAttach(att);
	    	tpl.setFlowType(ft);
	    	tpl.setPluginClassPath(pluginClassPath);
            tpl.setTemplateName(templateName);
            simpleDaoSupport.save(tpl);
	    }
	    
	    json.setRtState(true);
	    return json;
	}

	
	
	/**
	 * 根据流程获取文书模板列表
	 * @param request
	 * @return
	 */
	public TeeJson getTemplateListByFlowType(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取所属流程类型
		int flowTypeId=TeeStringUtil.getInteger(request.getParameter("flowTypeId"), 0);
	
		String hql=" from TeeFlowDocTemplate where flowType.sid=? order by sid asc ";
		List<TeeFlowDocTemplate> list=simpleDaoSupport.executeQuery(hql, new Object[]{flowTypeId});
		
		List<TeeFlowDocTemplateModel> modelList=new ArrayList<TeeFlowDocTemplateModel>();
		
		TeeFlowDocTemplateModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeFlowDocTemplate tpl : list) {
				model=parseToModel(tpl);
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}



	
	/**
	 * 实体类转换成model
	 * @param tpl
	 * @return
	 */
	private TeeFlowDocTemplateModel parseToModel(TeeFlowDocTemplate tpl) {
		TeeFlowDocTemplateModel model=new TeeFlowDocTemplateModel();
		BeanUtils.copyProperties(tpl, model);
		
		if(tpl.getAttach()!=null){
			model.setAttachId(tpl.getAttach().getSid());
			model.setAttachName(tpl.getAttach().getAttachmentName());
			model.setAttachModel(attachmentService.getModelById(tpl.getAttach().getSid()));
		}
		
		return model;
	}



	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	public TeeJson deleteById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeFlowDocTemplate tpl=(TeeFlowDocTemplate) simpleDaoSupport.get(TeeFlowDocTemplate.class,sid);
		if(tpl!=null){
			//执行删除的操作
			simpleDaoSupport.deleteByObj(tpl);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("该模板已不存在！");
		}
		return json;
	}



	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeFlowDocTemplate tpl=(TeeFlowDocTemplate) simpleDaoSupport.get(TeeFlowDocTemplate.class,sid);
		if(tpl!=null){
			TeeFlowDocTemplateModel model=parseToModel(tpl);
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("信息获取失败！");
		}
		
		return json;
	}

}
