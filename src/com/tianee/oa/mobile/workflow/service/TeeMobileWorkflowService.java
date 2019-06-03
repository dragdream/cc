package com.tianee.oa.mobile.workflow.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeSimpleDataLoaderInterface;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFeedBack;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunAipTemplateModel;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunAipTemplateServiceInterface;
import com.tianee.oa.core.workflow.flowrun.service.TeeWfPluginFactory;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItemGroup;
import com.tianee.oa.core.workflow.plugins.TeeWfPlugin;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrintTemplateModel;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface;
import com.tianee.oa.mobile.workflow.comparator.TeeFormItemComparator;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;
import com.tianee.oa.util.workflow.ctrl.TeeCtrl;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeMobileWorkflowService extends TeeBaseService implements TeeMobileWorkflowServiceInterface{
	
	@Autowired
	private TeeWorkflowServiceInterface workflowService;
	
	@Autowired
	private TeeWorkflowHelperInterface workflowHelper;
	
	@Autowired
	private TeeWorkFlowServiceContextInterface flowServiceContext;
	
	@Autowired
	private TeeSimpleDataLoaderInterface dataLoader;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	
	@Autowired
	private TeeFlowRunAipTemplateServiceInterface flowRunAipTemplateService;
	
	@Autowired
	private  TeeFlowPrintTemplateServiceInterface  flowPrintTemplateService;
	
	@Autowired
	private TeePersonDao personDao;
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.workflow.service.TeeMobileWorkflowServiceInterface#getMyWorkList(com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.webframe.httpModel.TeeDataGridModel, java.util.Map)
	 */
	@Override
	@Transactional(readOnly=true)
	public TeeEasyuiDataGridJson getMyWorkList(TeePerson loginUser,TeeDataGridModel dm,Map<String,String> requestData){
		TeeEasyuiDataGridJson dataGridJson = workflowService.getReceivedWorks(requestData, loginUser, dm);
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.workflow.service.TeeMobileWorkflowServiceInterface#getMyWorkHandledList(com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.webframe.httpModel.TeeDataGridModel, java.util.Map)
	 */
	@Override
	@Transactional(readOnly=true)
	public TeeEasyuiDataGridJson getMyWorkHandledList(TeePerson loginUser,TeeDataGridModel dm,Map<String,String> requestData){
		TeeEasyuiDataGridJson dataGridJson = workflowService.getHandledWorks(requestData, loginUser, dm);
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.workflow.service.TeeMobileWorkflowServiceInterface#getFormHanderData(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getFormHanderData(Map requestData){
		//去除style的正則表达式
		String regEx = " style=\"(.*?)\"";
		
		Map handlerData = new HashMap();
		int frpSid = TeeStringUtil.getInteger(requestData.get("frpSid"), 0);
		
		TeePerson loginPerson = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		dataLoader.getHandlerData(requestData, loginPerson);
		
		TeeFlowRunPrcs frp = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		frp.setFlag(2);
		TeeFlowRun flowRun = frp.getFlowRun();
		TeeFlowType flowType = flowRun.getFlowType();
		TeeForm form = flowRun.getForm();
		TeeFlowProcess fp = frp.getFlowPrcs();
		Map formDatas = workflowHelper.getFlowRunData(flowRun.getRunId(), flowType.getSid());
		
		
		//流程信息控制模型
		if(fp!=null){
			handlerData.put("workFlowCtrModel", fp.getWorkFlowCtrlModel());	
		}
		//获取流程基础信息
		handlerData.put("runName", flowRun.getRunName());
		handlerData.put("runId", flowRun.getRunId());
		handlerData.put("beginUser", flowRun.getBeginPerson().getUserName());
		handlerData.put("beginTime", TeeDateUtil.format(flowRun.getBeginTime()));
		handlerData.put("prcsDesc", "(第"+frp.getPrcsId()+"步)&nbsp;&nbsp;"+(fp==null?"":fp.getPrcsName()));
		handlerData.put("feedback", fp==null?1:fp.getFeedback());
		handlerData.put("formValidModel", fp==null?"[]":fp.getFormValidModel());
		handlerData.put("isOpen", fp==null?0:fp.getIsOpenMobileCtr());
		handlerData.put("delegate", flowType.getDelegate());
		handlerData.put("attachPriv",fp==null?0:fp.getAttachPriv());
		handlerData.put("attachPrivLock",flowType.getAttachPriv());
		handlerData.put("topFlag", frp.getTopFlag());
		
		if(fp!=null){
			handlerData.put("officePriv", fp.getOfficePriv());
			handlerData.put("attachOtherPriv", fp.getAttachOtherPriv());
		}
		String formStr="";
		List<TeeFormItemGroup> find = simpleDaoSupport.find("from TeeFormItemGroup where form.sid=? order by order asc", new Object[]{form.getSid()});
		String itemIds2="0";
		if(find!=null && find.size()>0){
			for(TeeFormItemGroup g:find){
				itemIds2+=","+g.getItemIds();
			}
		}
		TeeFormItemGroup g=new TeeFormItemGroup();
		find.add(g);
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		
			for(TeeFormItemGroup group:find){
				String items=group.getItemIds();
				Map<String,Object> map2=new HashMap<String,Object>();
				//获取表单渲染数据
				//List<TeeFormItem> formItems = form.getFormItems();
				StringBuffer formString = new StringBuffer();
				String hql="";
				if(group.getSid()==0){
					map2.put("groupId", 0);
					map2.put("groupName", "组容器");
					//formStr+="{\"groupId\":\"0\",\"groupName\":\"组容器\",";
					hql="from TeeFormItem where form.sid=? and itemId not in ("+itemIds2+")";
				}else{
					map2.put("groupId",group.getSid());
					map2.put("groupName", group.getGroupName());
					//formStr+="{\"groupId\":\""+group.getSid()+"\",\"groupName\":\""+group.getGroupName()+"\",";
					hql="from TeeFormItem where form.sid=? and itemId in ("+items+")";
				}
				List<TeeFormItem> formItems = simpleDaoSupport.find(hql, form.getSid());
				Collections.sort(formItems, new TeeFormItemComparator());
				TeeCtrl ctrl = null;
				TeeFlowFormData flowFromData = new TeeFlowFormData();
				
				
				
				if(fp!=null){//固定流程
					//判断移动端控制模型是否开启
					int isOpenMobileCtr=fp.getIsOpenMobileCtr();
					
					if(isOpenMobileCtr==1){//开启
						//获取步骤手机字段显示控制模型
						String mobileFieldCtrModel=fp.getMobileFieldCtrlModel();	
						List<Map<String,String>>list=TeeJsonUtil.JsonStr2MapList(mobileFieldCtrModel);
						List<Map<String,String>> appendStrList=new ArrayList<Map<String,String>>();
						List<TeeFormItem> sortList=new ArrayList<TeeFormItem>();
						Map<String,String> map=null;
						for(int i=0;i<list.size();i++){
							for(int j=0;j<formItems.size();j++){
								if(list.get(i).get("itemId").equals(formItems.get(j).getItemId()+"")&&list.get(i).get("isShow").equals("true")){
									ctrl = TeeCtrl.getInstanceOf(formItems.get(j).getXtype());
									flowFromData.setData((String)formDatas.get(formItems.get(j).getName()));
									flowFromData.setItemId(formItems.get(j).getItemId());
									flowFromData.setRunId(flowRun.getRunId());
									ctrl.setContext(flowServiceContext);
//							if(formItems.get(j).getXtype().equals("xtextarea") && formItems.get(j).getContent().indexOf("rich=\"1\"")!=-1){
//								formString.append("<p class='item'><b>"+formItems.get(j).getTitle()+"：</b><br/>"+flowFromData.getData()+"</p>");
//							}else{
									//formString.append("<p class='item'><b>"+formItems.get(j).getTitle()+"：</b>"+ctrl.getCtrlHtml4Process(formItems.get(j),flowFromData , flowType, form, frp, formDatas)+"</p>");
//							}
									map=new HashMap<String, String>();	
									//单行  多行   下拉   选择器    宏控件    去除style
									if(("xinput").equals(formItems.get(j).getXtype())||("xtextarea").equals(formItems.get(j).getXtype())||("xselect").equals(formItems.get(j).getXtype())||("xfetch").equals(formItems.get(j).getXtype())||("xmacro").equals(formItems.get(j).getXtype())){
										Pattern p = Pattern.compile(regEx);
										Matcher m = p.matcher(ctrl.getCtrlHtml4MobileProcess(formItems.get(j),flowFromData , flowType, form, frp, formDatas));
										String okContent = null;
										if (m.find()) {
											okContent = m.replaceAll("");
										}else{
											okContent=ctrl.getCtrlHtml4MobileProcess(formItems.get(j),flowFromData , flowType, form, frp, formDatas);
										}
										map.put("appendStr","<div class=\"table-2\"><div class=\"table-left\">"+formItems.get(j).getTitle()+"：</div><div class=\"table-right lang-1\">"+okContent+"</div></div>" );
									}else{
										if("xfeedback".equals(formItems.get(j).getXtype())){
											map.put("appendStr","<div class=\"table-2\"><div>"+formItems.get(j).getTitle()+"：</div><div>"+ctrl.getCtrlHtml4MobileProcess(formItems.get(j),flowFromData , flowType, form, frp, formDatas)+"</div></div>" );
										}else{
											map.put("appendStr","<div class=\"table-2\"><div class=\"table-left\">"+formItems.get(j).getTitle()+"：</div><div class=\"table-right lang-1\">"+ctrl.getCtrlHtml4MobileProcess(formItems.get(j),flowFromData , flowType, form, frp, formDatas)+"</div></div>" );
										}
									}
									
									map.put("sortNo", formItems.get(j).getSortNo()+"");
									map.put("itemSid", formItems.get(j).getSid()+"");
									appendStrList.add(map);
									sortList.add(formItems.get(j));
								}
							}	
						}
						Collections.sort(sortList, new TeeFormItemComparator());
						
						
						for(int i=0;i<sortList.size();i++){
							for(int j=0;j<appendStrList.size();j++){
								if(sortList.get(i).getSid()==TeeStringUtil.getInteger(appendStrList.get(j).get("itemSid"), 0)){
									formString.append(appendStrList.get(j).get("appendStr"));
								}else{
									continue;
								}
							}	
						}
						
					}else{//未开启
						
						for(TeeFormItem formItem:formItems){
							ctrl = TeeCtrl.getInstanceOf(formItem.getXtype());
							//System.out.println(formItem.getXtype());
							flowFromData.setData((String)formDatas.get(formItem.getName()));
							flowFromData.setItemId(formItem.getItemId());
							flowFromData.setRunId(flowRun.getRunId());
							ctrl.setContext(flowServiceContext);
//					if(formItem.getXtype().equals("xtextarea") && formItem.getContent().indexOf("rich=\"1\"")!=-1){
//						formString.append("<p class='item'><b>"+formItem.getTitle()+"：</b><br/>"+flowFromData.getData()+"</p>");
//					}else{
							//单行  多行   下拉   选择器    宏控件    去除style
							if(("xinput").equals(formItem.getXtype())||("xtextarea").equals(formItem.getXtype())||("xselect").equals(formItem.getXtype())||("xfetch").equals(formItem.getXtype())||("xmacro").equals(formItem.getXtype())){
								Pattern p = Pattern.compile(regEx);
								Matcher m = p.matcher(ctrl.getCtrlHtml4MobileProcess(formItem,flowFromData , flowType, form, frp, formDatas));
								String okContent = null;
								if (m.find()) {
									okContent = m.replaceAll("");
								}else{
									okContent=ctrl.getCtrlHtml4MobileProcess(formItem,flowFromData , flowType, form, frp, formDatas);
								}
								
								formString.append("<div class=\"table-2\"><div class=\"table-left\">"+formItem.getTitle()+"：</div><div class=\"table-right lang-1\">"+okContent+"</div></div>");
							}else{
								if("xfeedback".equals(formItem.getXtype())){
									formString.append("<div class=\"table-2\"><div>"+formItem.getTitle()+"：</div><div>"+ctrl.getCtrlHtml4MobileProcess(formItem,flowFromData , flowType, form, frp, formDatas)+"</div></div>");
								}else{
									formString.append("<div class=\"table-2\"><div class=\"table-left\">"+formItem.getTitle()+"：</div><div class=\"table-right lang-1\">"+ctrl.getCtrlHtml4MobileProcess(formItem,flowFromData , flowType, form, frp, formDatas)+"</div></div>");
								}
							}
							
							
//					}
						}
					}
					
					
					
					
					
					
					
					
				}else{//自由流程
					
					for(TeeFormItem formItem:formItems){
						ctrl = TeeCtrl.getInstanceOf(formItem.getXtype());
						flowFromData.setData((String)formDatas.get(formItem.getName()));
						flowFromData.setItemId(formItem.getItemId());
						flowFromData.setRunId(flowRun.getRunId());
						ctrl.setContext(flowServiceContext);
//				if(formItem.getXtype().equals("xtextarea") && formItem.getContent().indexOf("rich=\"1\"")!=-1){
//					formString.append("<p class='item'><b>"+formItem.getTitle()+"：</b><br/>"+flowFromData.getData()+"</p>");
//				}else{
						
						//单行  多行   下拉   选择器    宏控件    去除style
						if(("xinput").equals(formItem.getXtype())||("xtextarea").equals(formItem.getXtype())||("xselect").equals(formItem.getXtype())||("xfetch").equals(formItem.getXtype())||("xmacro").equals(formItem.getXtype())){
							Pattern p = Pattern.compile(regEx);
							Matcher m = p.matcher(ctrl.getCtrlHtml4MobileProcess(formItem,flowFromData , flowType, form, frp, formDatas));
							String okContent = null;
							if (m.find()) {
								okContent = m.replaceAll("");
							}else{
								okContent=ctrl.getCtrlHtml4MobileProcess(formItem,flowFromData , flowType, form, frp, formDatas);
							}
							
							formString.append("<div class=\"table-2\"><div class=\"table-left\">"+formItem.getTitle()+"：</div><div class=\"table-right lang-1\">"+okContent+"</div></div>");
						}else{
							if("xfeedback".equals(formItem.getXtype())){
								formString.append("<div class=\"table-2\"><div>"+formItem.getTitle()+"：</div><div>"+ctrl.getCtrlHtml4MobileProcess(formItem,flowFromData , flowType, form, frp, formDatas)+"</div></div>");
							}else{
								formString.append("<div class=\"table-2\"><div class=\"table-left\">"+formItem.getTitle()+"：</div><div class=\"table-right lang-1\">"+ctrl.getCtrlHtml4MobileProcess(formItem,flowFromData , flowType, form, frp, formDatas)+"</div></div>");
							}
						}
//				}
					}
					
					
				}
				
				//formStr+="\"items\":\""+formString.toString()+"\"},";		
				if(formString.length()>0){
					map2.put("items", formString.toString());
					listMap.add(map2);
				}
			}
			
		

		//System.out.println( formString.toString());
		handlerData.put("form", listMap);
		handlerData.put("script", form.getScript());
		
		//获取流程正文
		TeeFlowRunDoc flowRunDoc = (TeeFlowRunDoc) simpleDaoSupport.unique("from TeeFlowRunDoc doc where doc.flowRun.runId=?", new Object[]{flowRun.getRunId()});
		if(flowRunDoc!=null){
			if(flowRunDoc.getDocAttach()!=null){
				handlerData.put("docId", flowRunDoc.getDocAttach().getSid());
				handlerData.put("docFileName", flowRunDoc.getDocAttach().getFileName());
				handlerData.put("docAttachName", flowRunDoc.getDocAttach().getAttachmentName());
			}
		}
		
		//获取版式正文
		List<TeeAttachment> attachs = attachmentDao.getAttaches(TeeAttachmentModelKeys.workFlowDocAip, String.valueOf(flowRun.getRunId()));
		TeeAttachmentModel doc = new TeeAttachmentModel();
		if(attachs!=null && attachs.size()!=0){
			handlerData.put("aipDocId", attachs.get(0).getSid());
		}
		
		//创建插件工厂
		TeeWfPluginFactory pluginFactory = new TeeWfPluginFactory(frp, formDatas);
		TeeWfPlugin wfPlugin = null;
		try {
			wfPlugin = pluginFactory.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TeeOperationException(e);
		}
		if(wfPlugin!=null && wfPlugin.afterRendered()!=null){
			handlerData.put("afterRenderedScript", wfPlugin.afterRendered());
		}
		
		//判断该流程类型是否有相关联的aip签批模板
		int flowId=TeeStringUtil.getInteger(requestData.get("flowId"), 0);
		List<TeeFlowPrintTemplateModel> modelList = flowPrintTemplateService.selectModulByFlowType(flowId+"");
		if(modelList!=null&&modelList.size()>0){
			handlerData.put("isHasAipTemplate", 1);
		}else{
			handlerData.put("isHasAipTemplate", 0);
		}
		
		return handlerData;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.workflow.service.TeeMobileWorkflowServiceInterface#getFormHanderData4Print(java.util.Map)
	 */
	@Override
	public Map getFormHanderData4Print(Map requestData){
		//去除style正则
		String regEx = " style=\"(.*?)\"";
		
		Map handlerData = new HashMap();
		
		int runId = TeeStringUtil.getInteger(requestData.get("runId"), 0);
		
		TeePerson loginPerson = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		//判断是否存在该步骤工作
		
		TeeFlowRun flowRun = flowServiceContext.getFlowRunService().get(runId);
		if(flowRun==null){
			throw new TeeOperationException("该流程不存在或已删除");
		}
		
		TeeFlowType flowType = flowRun.getFlowType();
		TeeForm form = flowRun.getForm();
		Map formDatas = workflowHelper.getFlowRunData(flowRun.getRunId(), flowType.getSid());
		
		int level=flowRun.getLevel();
		String levelDesc="";
		if(level==1){
			levelDesc="普通";
		}else if(level==2){
			levelDesc="紧急";
		}else if(level==3){
			levelDesc="加急";
		}
		handlerData.put("level",levelDesc );
		handlerData.put("runName", flowRun.getRunName());
		handlerData.put("runId", flowRun.getRunId());
		handlerData.put("beginUser", flowRun.getBeginPerson().getUserName());
		handlerData.put("beginTime", TeeDateUtil.format(flowRun.getBeginTime()));
		
		//获取表单渲染数据
		String formStr="";
		List<TeeFormItemGroup> find = simpleDaoSupport.find("from TeeFormItemGroup where form.sid=? order by order asc", new Object[]{form.getSid()});
		String itemIds2="0";
		if(find!=null && find.size()>0){
			for(TeeFormItemGroup g:find){
				itemIds2+=","+g.getItemIds();
			}
		}
		TeeFormItemGroup g=new TeeFormItemGroup();
		find.add(g);
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
			for(TeeFormItemGroup group:find){
				String items=group.getItemIds();
				Map<String,Object> map2=new HashMap<String,Object>();
				//获取表单渲染数据
				//List<TeeFormItem> formItems = form.getFormItems();
				StringBuffer formString = new StringBuffer();
				String hql="";
				if(group.getSid()==0){
					map2.put("groupId", 0);
					map2.put("groupName", "组容器");
					//formStr+="{\"groupId\":\"0\",\"groupName\":\"组容器\",";
					hql="from TeeFormItem where form.sid=? and itemId not in ("+itemIds2+")";
				}else{
					map2.put("groupId",group.getSid());
					map2.put("groupName", group.getGroupName());
					//formStr+="{\"groupId\":\""+group.getSid()+"\",\"groupName\":\""+group.getGroupName()+"\",";
					hql="from TeeFormItem where form.sid=? and itemId in ("+items+")";
				}
				List<TeeFormItem> formItems = simpleDaoSupport.find(hql, form.getSid());
				Collections.sort(formItems, new TeeFormItemComparator());
				
				TeeCtrl ctrl = null;
				TeeFlowFormData flowFromData = new TeeFlowFormData();
				//List<TeeFormItem> formItems = form.getFormItems();
				for(TeeFormItem formItem:formItems){
					if(!("xseal").equals(formItem.getXtype())){
						ctrl = TeeCtrl.getInstanceOf(formItem.getXtype());
						flowFromData.setData(TeeStringUtil.getString(formDatas.get(formItem.getName())));
						flowFromData.setItemId(formItem.getItemId());
						flowFromData.setRunId(flowRun.getRunId());
						ctrl.setContext(flowServiceContext);
						if(formItem.getXtype().equals("xtextarea") && formItem.getContent().indexOf("rich=\"1\"")!=-1){
							formString.append("<div class=\"table-2\"><div class=\"table-left\">"+formItem.getTitle()+"：</div><div class=\"table-right lang-1\">"+flowFromData.getData()+"</div></div>");
						}else{
							if(("xinput").equals(formItem.getXtype())||("xtextarea").equals(formItem.getXtype())||("xselect").equals(formItem.getXtype())||("xfetch").equals(formItem.getXtype())||("xmacro").equals(formItem.getXtype())){
								Pattern p = Pattern.compile(regEx);
								Matcher m = p.matcher(ctrl.getCtrlHtml4MobilePrint(formItem,flowFromData , flowType,form,flowRun, null, formDatas));
								String okContent = null;
								if (m.find()) {
									okContent = m.replaceAll("");
								}else{
									okContent=ctrl.getCtrlHtml4MobilePrint(formItem,flowFromData , flowType,form,flowRun, null, formDatas);
								}
								
								formString.append("<div class=\"table-2\"><div class=\"table-left\">"+formItem.getTitle()+"：</div><div class=\"table-right lang-1\">"+okContent+"</div></div>");
							}else{
								if("xfeedback".equals(formItem.getXtype())){
									formString.append("<div class=\"table-2\"><div>"+formItem.getTitle()+"：</div><div>"+ctrl.getCtrlHtml4MobilePrint(formItem,flowFromData , flowType,form,flowRun, null, formDatas)+"</div></div>");
								}else{
									formString.append("<div class=\"table-2\"><div class=\"table-left\">"+formItem.getTitle()+"：</div><div class=\"table-right lang-1\">"+ctrl.getCtrlHtml4MobilePrint(formItem,flowFromData , flowType,form,flowRun, null, formDatas)+"</div></div>");
								}
							}
							
						}	
					}
				}	
				if(formString.length()>0){
					map2.put("items", formString.toString());
					listMap.add(map2);
				}
			}

		

		handlerData.put("form", listMap);
		handlerData.put("script", form.getScript());
		
		//获取流程正文
		TeeFlowRunDoc flowRunDoc = (TeeFlowRunDoc) simpleDaoSupport.unique("from TeeFlowRunDoc doc where doc.flowRun.runId=?", new Object[]{flowRun.getRunId()});
		if(flowRunDoc!=null){
			if(flowRunDoc.getDocAttach()!=null){
				handlerData.put("docId", flowRunDoc.getDocAttach().getSid());
				handlerData.put("docFileName", flowRunDoc.getDocAttach().getFileName());
				handlerData.put("docAttachName", flowRunDoc.getDocAttach().getAttachmentName());
			}
		}
		
		//获取版式正文
		List<TeeAttachment> attachs = attachmentDao.getAttaches(TeeAttachmentModelKeys.workFlowDocAip, String.valueOf(flowRun.getRunId()));
		TeeAttachmentModel doc = new TeeAttachmentModel();
		if(attachs!=null && attachs.size()!=0){
			handlerData.put("aipDocId", attachs.get(0).getSid());
		}
		
		
		
		//判断当前流程有没有关联的AIP文件的存在
		List<TeeFlowRunAipTemplateModel> modelList=(List<TeeFlowRunAipTemplateModel>) flowRunAipTemplateService.getListByRunId(runId).getRtData();
		if(modelList!=null&&modelList.size()>0){
			handlerData.put("hasAipFile", 1);
		}else{
			handlerData.put("hasAipFile", 0);
		}
		return handlerData;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.workflow.service.TeeMobileWorkflowServiceInterface#addFeedBack(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public void addFeedBack(Map requestData,TeePerson loginUser){
		String content = TeeStringUtil.getString(requestData.get("content"));
		String voiceId = TeeStringUtil.getString(requestData.get("voiceId"));
		int frpSid = TeeStringUtil.getInteger(requestData.get("frpSid"), 0);
		TeeFlowRunPrcs frp = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		
		loginUser = personDao.get(loginUser.getUuid());
		
		TeeFeedBack fb = new TeeFeedBack();
		fb.setContent(content);
		fb.setEditTime(Calendar.getInstance());
		fb.setFeedFlag(0);
		fb.setPrcsId(frp.getPrcsId());
		fb.setFlowRun(frp.getFlowRun());
		fb.setFlowPrcs(frp.getFlowPrcs()==null?null:frp.getFlowPrcs());
		fb.setUserPerson(loginUser);
		fb.setVoiceId(voiceId);
		fb.setUserName(loginUser.getUserName());
		if(loginUser.getDept()!=null){
			fb.setDeptName(loginUser.getDept().getDeptName());
			fb.setDeptFullPath(loginUser.getDept().getDeptFullName());
		}
		if(loginUser.getUserRole()!=null){
			fb.setRoleName(loginUser.getUserRole().getRoleName());
		}
		
		simpleDaoSupport.save(fb);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.workflow.service.TeeMobileWorkflowServiceInterface#formListView(java.util.Map)
	 */
	@Override
	public String formListView(Map requestData){
		int frpSid = TeeStringUtil.getInteger(requestData.get("frpSid"), 0);
		int itemId = TeeStringUtil.getInteger(requestData.get("itemId"), 0);
		
		TeeFlowRunPrcs frp = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		TeeFlowRun flowRun = frp.getFlowRun();
		TeeFlowType flowType = flowRun.getFlowType();
		TeeForm form = flowRun.getForm();
		TeeFlowProcess fp = frp.getFlowPrcs();
		Map formDatas = workflowHelper.getFlowRunData(flowRun.getRunId(), flowType.getSid());
		
		String html = null;
		List<TeeFormItem> formItems = form.getFormItems();
		TeeCtrl ctrl = null;
		TeeFlowFormData flowFromData = new TeeFlowFormData();
		for(TeeFormItem formItem:formItems){
			if(formItem.getItemId()!=itemId){
				continue;
			}
			ctrl = TeeCtrl.getInstanceOf(formItem.getXtype());
			flowFromData.setData((String)formDatas.get(formItem.getName()));
			flowFromData.setItemId(formItem.getItemId());
			flowFromData.setRunId(flowRun.getRunId());
			ctrl.setContext(flowServiceContext);
			html = ctrl.getCtrlHtml4Print(formItem, flowFromData, flowType, form, flowRun, frp, formDatas);
		}
		
		return html;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.workflow.service.TeeMobileWorkflowServiceInterface#getFlowSortByPriv(com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeEasyuiDataGridJson getFlowSortByPriv(TeePerson loginUser){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<Map> datas = workflowService.getCreatablePrivFlowListModelsByPerson(loginUser);
		
		Iterator<Map> it = datas.iterator();
		Map d = null;
		while(it.hasNext()){
			d = it.next();
			d.put("flowCount", ((List)d.get("flowTypes")).size());
			if(((List)d.get("flowTypes")).size()==0){
				it.remove();
			}
		}
		
		for(Map data:datas){
			data.remove("flowTypes");
		}
		
		dataGridJson.setRows(datas);
		dataGridJson.setTotal(TeeStringUtil.getLong(datas.size(), 0));
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.mobile.workflow.service.TeeMobileWorkflowServiceInterface#getFlowTypeBySortAndPriv(com.tianee.oa.core.org.bean.TeePerson, int)
	 */
	@Override
	public TeeEasyuiDataGridJson getFlowTypeBySortAndPriv(TeePerson loginUser,int sortId){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<Map> datas = workflowService.getCreatablePrivFlowListModelsByPerson(loginUser);
		List<Map> ftDatas = new ArrayList();
		for(Map data:datas){
			if(data.get("sortId").toString().equals(String.valueOf(sortId))){
				ftDatas = (List<Map>) data.get("flowTypes");
				for(Map d:ftDatas){
					d.remove("formId");
					d.remove("type");
					d.remove("comment");
				}
				break;
			}
		}
		dataGridJson.setRows(ftDatas);
		dataGridJson.setTotal(TeeStringUtil.getLong(ftDatas.size(), 0));
		return dataGridJson;
	}
	
}
