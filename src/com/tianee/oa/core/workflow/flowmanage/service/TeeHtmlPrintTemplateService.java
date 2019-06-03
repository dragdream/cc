package com.tianee.oa.core.workflow.flowmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeHtmlPrintTemplate;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.util.workflow.ctrl.TeeCtrl;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;
@Service
public class TeeHtmlPrintTemplateService extends TeeBaseService implements TeeHtmlPrintTemplateServiceInterface{

	@Autowired
	private TeeWorkFlowServiceContextInterface context;
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeHtmlPrintTemplateServiceInterface#addOrUpdate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int flowTypeId=TeeStringUtil.getInteger(request.getParameter("flowTypeId"), 0);
		String templateName=TeeStringUtil.getString(request.getParameter("templateName"));
		if(sid>0){//编辑
			TeeHtmlPrintTemplate tpl=(TeeHtmlPrintTemplate) simpleDaoSupport.get(TeeHtmlPrintTemplate.class,sid);
			if(tpl!=null){
				tpl.setTemplateName(templateName);
				simpleDaoSupport.update(tpl);
				
				json.setRtState(true);
				json.setRtMsg("模板编辑成功！");	
			}else{
				json.setRtState(false);
				json.setRtMsg("该模板已不存在！");	
			}	
		}else{//新建
			TeeHtmlPrintTemplate tpl=new TeeHtmlPrintTemplate();
			tpl.setFlowTypeId(flowTypeId);
			tpl.setTemplateName(templateName);
			simpleDaoSupport.save(tpl);
			
			json.setRtState(true);
			json.setRtMsg("模板新建成功！");	
		}
		return json;
	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeHtmlPrintTemplateServiceInterface#list(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson list(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台传来的flowTypeId
		int flowTypeId=TeeStringUtil.getInteger(request.getParameter("flowTypeId"),0);
		String hql=" from TeeHtmlPrintTemplate tpl where tpl.flowTypeId=? ";
		List<TeeHtmlPrintTemplate> list=simpleDaoSupport.executeQuery(hql, new Object[]{flowTypeId});
		json.setRtState(true);
		json.setRtData(list);
		json.setRtMsg("数据获取成功！");
		return json;
	}



	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeHtmlPrintTemplateServiceInterface#getInfoBySid(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeHtmlPrintTemplate  tpl=(TeeHtmlPrintTemplate) simpleDaoSupport.get(TeeHtmlPrintTemplate.class,sid);
		if(tpl!=null){
			json.setRtState(true);
			json.setRtData(tpl);
			json.setRtMsg("数据获取成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeHtmlPrintTemplateServiceInterface#deleteBySid(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson deleteBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台传来的sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeHtmlPrintTemplate  tpl=(TeeHtmlPrintTemplate) simpleDaoSupport.get(TeeHtmlPrintTemplate.class,sid);
		if(tpl!=null){
			simpleDaoSupport.deleteByObj(tpl);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		
		
		return json;
	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeHtmlPrintTemplateServiceInterface#getBasicFormItemsByFlowType(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getBasicFormItemsByFlowType(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int flowTypeId=TeeStringUtil.getInteger(request.getParameter("flowTypeId"),0);
		TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowTypeId);
		if(flowType!=null){
			TeeForm form=flowType.getForm();
			if(form!=null){
				List<TeeFormItem> itemList=simpleDaoSupport.executeQuery(" from TeeFormItem t where t.form.sid=? ", new Object[]{form.getSid()});
			    List<Map> mapList=new ArrayList<Map>();
			    Map map=null;
			    if(itemList!=null&&itemList.size()>0){
			    	for (TeeFormItem item : itemList) {
						map=new HashMap();
						map.put("sid",item.getSid());
						map.put("content", item.getContent());
						map.put("title",item.getTitle());
						map.put("name",item.getName());
						mapList.add(map);
					}
			    }
			    json.setRtState(true);
				json.setRtData(mapList);
			}else{
				json.setRtState(false);
				json.setRtMsg("该流程类型所绑定的表单不存在！");
			}
			
		}else{
			json.setRtState(false);
			
		}
		return json;
	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeHtmlPrintTemplateServiceInterface#updateTplContent(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson updateTplContent(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String tplContent=TeeStringUtil.getString(request.getParameter("tplContent"));
		TeeHtmlPrintTemplate tpl=(TeeHtmlPrintTemplate) simpleDaoSupport.get(TeeHtmlPrintTemplate.class,sid);
		if(tpl!=null){
			tpl.setTplContent(tplContent);
			simpleDaoSupport.update(tpl);
			
			json.setRtState(true);
			json.setRtMsg("保存成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("该模板已不存在！");
		}
		
		return  json;
	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeHtmlPrintTemplateServiceInterface#listByRunId(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson listByRunId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台传来的runId
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
		TeeFlowRun run=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		if(run!=null && run.getFlowType()!=null){
			
			String hql=" from TeeHtmlPrintTemplate tpl where tpl.flowTypeId=? ";
			List<TeeHtmlPrintTemplate> list=simpleDaoSupport.executeQuery(hql, new Object[]{run.getFlowType().getSid()});
			json.setRtState(true);
			json.setRtData(list);
			json.setRtMsg("数据获取成功！");
			
		}else{
			json.setRtState(false);
		}

		return json;
	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeHtmlPrintTemplateServiceInterface#printExplore(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String printExplore(HttpServletRequest request) {
		//获取sid  和  runId
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		
		final TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class, runId);
		final TeeForm form = flowRun.getForm();
		final List<TeeFormItem> items = form.getFormItems();
		
		TeeHtmlPrintTemplate tpl=(TeeHtmlPrintTemplate) simpleDaoSupport.get(TeeHtmlPrintTemplate.class,sid);
		if(tpl!=null){
		    int flowTypeId=tpl.getFlowTypeId();
		    
		    final Map<String,String>  data = context.getTeeWorkflowHelper().getFlowRunData(runId);
			
			
			String tplContent=tpl.getTplContent();
			//正则解析器
			TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
			analyser.setText(tplContent);//将需要匹配的原字符串传入
			

			String newStr = analyser.replace(new String[] {new TeeHTMLImgTag().getRegExp()},
					new TeeExpFetcher() {
							@Override
							public String parse(String pattern) {
								//pattern是满足正则的字符串截取内容
								 TeeHTMLImgTag  img =new TeeHTMLImgTag();
								 img.analyse(pattern);
								 Map m=img.getAttributes();
								 String id=TeeStringUtil.getString(m.get("id")) ;
								 String clazz=TeeStringUtil.getString(m.get("class"));
								 if(("DATAFIELD").equals(clazz)){
									 for(TeeFormItem formItem:items){
										 if(formItem.getName().equals(id)){
											TeeCtrl ctrl = TeeCtrl.getInstanceOf(formItem.getXtype());
											ctrl.setContext(context);
											TeeFlowFormData flowFormData = new TeeFlowFormData();
											flowFormData.setData(TeeStringUtil.getString(data.get(id)));
											flowFormData.setItemId(formItem.getItemId());
											flowFormData.setRunId(flowRun.getRunId());
											return ctrl.getCtrlHtml4Print(formItem, flowFormData,flowRun.getFlowType(), form, flowRun, null, data);
										 }
									 }
									 
									 return "";
								 }else{
									 return pattern;
								 }
							}
	
						});
			return newStr;
			
		}else{
			return "";
		}
	}

	
	
	
	
}
