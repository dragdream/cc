package com.tianee.oa.core.workflow.formmanage.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeListCtrlExtend;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItemGroup;
import com.tianee.oa.core.workflow.formmanage.dao.TeeFlowFormDao;
import com.tianee.oa.core.workflow.formmanage.dao.TeeFlowFormItemDao;
import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.oa.util.workflow.TeeFormParser;
import com.tianee.oa.util.workflow.TeeWorkflowHelper;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;
import com.tianee.oa.util.workflow.ctrl.TeeCtrl;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowFormItemModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowFormModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;

@Service
public class TeeFlowFormService extends TeeBaseService implements TeeFlowFormServiceInterface{
	@Autowired
	private TeeFlowFormDao flowFormDao;
	
	@Autowired
	private TeeFlowFormItemDao flowFormItemDao;
	
	@Autowired
	private TeeFlowTypeDao flowTypeDao;
	
	@Autowired
	private TeeWorkflowHelperInterface workflowHelper;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#datagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel, int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Transactional(readOnly=true)
	public List<TeeFlowFormModel> datagrid(TeeDataGridModel m,int sortId,TeePerson loginUser){
		List<TeeForm> formList = this.getFlowFormBySort(loginUser,sortId,(m.getPage()-1)*m.getRows(),m.getRows());
		List<TeeFlowFormModel> list = new ArrayList<TeeFlowFormModel>();
		List<TeeFlowType> fts = null;
		String sb = "";
		for(TeeForm form:formList){
			sb="";
			TeeFlowFormModel model = new TeeFlowFormModel();
			BeanUtils.copyProperties(form, model,new String[]{"printModel","printModelShort"});
			fts = getBundledFlowTypes(form.getSid());
			
			for(TeeFlowType ft:fts){
				sb+=ft.getFlowName()+",";
			}
			model.setBundledFlowType(sb);
			list.add(model);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#deleteFormService(int)
	 */
	@TeeLoggingAnt(template="删除表单[{#.formName}]",type="006G")
	public TeeForm deleteFormService(int formId){
		TeeForm form = flowFormDao.get(formId);
		flowFormDao.deleteByObj(form);
		return form;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getFlowFormCountBySort(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	public long getFlowFormCountBySort(int sortId,TeePerson loginUser){
		//判断当前登录的用户是否是超级管理员
		boolean isAdmin=TeePersonService.checkIsSuperAdmin(loginUser, loginUser.getUserId());
		
		return flowFormDao.getFlowFormCountBySort(sortId,isAdmin,loginUser);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#listHistoryVersion(int)
	 */
	@Transactional(readOnly=true)
	public List<TeeForm> listHistoryVersion(int formId){
		TeeForm form = getForm(formId);
		if(form==null){
			return null;
		}
		return flowFormDao.listHistoryVersion(form);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#saveService(com.tianee.oa.core.workflow.formmanage.bean.TeeForm)
	 */
	@TeeLoggingAnt(template="创建表单[{$1.formName}]",type="006E")
	public void saveService(TeeForm form){
		form.setVersionNo(1);
		form.setVersionTime(Calendar.getInstance());
		flowFormDao.save(form);
		form.setFormGroup(form.getSid());
		flowFormDao.update(form);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#updateService(com.tianee.oa.core.workflow.formmanage.bean.TeeForm)
	 */
	@TeeLoggingAnt(template="更新表单[{$1.formName}]",type="006F")
	public void updateService(TeeForm form){
		flowFormDao.update(form);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getForm(int)
	 */
	public TeeForm getForm(int formId){
		return flowFormDao.load(formId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getFormModel(int)
	 */
	@Transactional(readOnly=true)
	public TeeFlowFormModel getFormModel(int formId){
		TeeFlowFormModel m = new TeeFlowFormModel();
		TeeForm form = flowFormDao.load(formId);
		BeanUtils.copyProperties(form, m);
		return m;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getFormModelList(int)
	 */
	@Transactional(readOnly=true)
	public List<TeeFlowFormModel> getFormModelList(int sortId){
		List<TeeFlowFormModel> list = new ArrayList<TeeFlowFormModel>();
		List<TeeForm> formList = flowFormDao.getFlowFormBySort(sortId);
		for(TeeForm form:formList){
			TeeFlowFormModel m = new TeeFlowFormModel();
			BeanUtils.copyProperties(form, m);
			list.add(m);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#saveFormItem(com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem)
	 */
	public void saveFormItem(TeeFormItem formItem){
		flowFormItemDao.save(formItem);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getLatestFormItemsByOriginForm(com.tianee.oa.core.workflow.formmanage.bean.TeeForm)
	 */
	public List<TeeFormItem> getLatestFormItemsByOriginForm(TeeForm form){
		TeeForm latestForm = flowFormDao.getLatestVersion(form);
		if(latestForm==null){
			return new ArrayList<TeeFormItem>();
		}
		return flowFormItemDao.listSortedFormItems(latestForm.getFormItems());
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#updateItemsSortNo(java.util.List)
	 */
	public void updateItemsSortNo(List<Map<String,String>> fieldMapList){
		Map updateItem = new HashMap();
		for(Map<String,String> fieldMap:fieldMapList){
			updateItem.put("sortNo", Integer.parseInt(fieldMap.get("sort")));
			flowFormItemDao.update(updateItem, Integer.parseInt(fieldMap.get("id")));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getLatestVersion(com.tianee.oa.core.workflow.formmanage.bean.TeeForm)
	 */
	public TeeForm getLatestVersion(TeeForm form){
		return flowFormDao.getLatestVersion(form);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getLatestVersion(int)
	 */
	public TeeForm getLatestVersion(int formId){
		return flowFormDao.getLatestVersion(flowFormDao.get(formId));
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#listFormItems(com.tianee.oa.core.workflow.formmanage.bean.TeeForm)
	 */
	public List<TeeFormItem> listFormItems(TeeForm form){
		if(form==null){
			return null;
		}
		return flowFormItemDao.listFormItems(form);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#listFormItems(int)
	 */
	public List<TeeFormItem> listFormItems(int formId){
		if(formId==0){
			return null;
		}
		return flowFormItemDao.listFormItems(formId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#printExplore(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Transactional(readOnly=true)
	public String printExplore(int formId,TeePerson loginUser){
		TeeForm form = getForm(formId);
		final TeePerson loginUser0 = (TeePerson) simpleDaoSupport.get(TeePerson.class, loginUser.getUuid());
		final List<TeeFormItem> items = flowFormItemDao.listFormItems(form);
		String printModelShort = form.getPrintModelShort();
		TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
		analyser.setText(printModelShort);
		printModelShort = analyser.replace(new String[]{"\\{DATA_[0-9]+\\}"}, new TeeExpFetcher(){

			@Override
			public String parse(String pattern) {
				// TODO Auto-generated method stub
				String name = pattern.replaceAll("[\\{\\}]{1}", "");
				TeeFormItem curItem = null;
				for(TeeFormItem formItem:items){
					if(formItem.getName().equals(name)){
						curItem = formItem;
						break;
					}
				}
				
				if(curItem==null){
					return pattern;
				}
				
				String xtype = curItem.getXtype();
				TeeCtrl ctrl = TeeCtrl.getInstanceOf(xtype);
				
				if(ctrl==null){
					return "";
				}else{
					ctrl.setLoginUser(loginUser0);
					return ctrl.getCtrlHtml4Design(curItem);
				}
			}
			
		});
		
		return printModelShort;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#saveAndUpdateFormService(java.lang.String, int)
	 */
	@TeeLoggingAnt(template="修改表单内容 [{#.formName}]",type="006I")
	public TeeForm saveAndUpdateFormService(String content,int formId){
		//获取方言
		String dialect=TeeSysProps.getDialect();
		//实例化表单转换器工具
		TeeFormParser formParser = new TeeFormParser();
		//抽取表单内容，获取各个控件
		List<TeeFormItem> formItems = formParser.getFormItemsFromHtml(content);
		String shortPrintModel = formParser.getShortModelFromHtml(content);
		
		int maxItem = 0;
		
		//获取表单
		TeeForm form = getForm(formId);
		if(form==null){
			throw new TeeOperationException("不存在formId为["+formId+"]的表单实体");
		}
		
		//获取最大表单项ID
		for(TeeFormItem fi:formItems){
			if(maxItem<fi.getItemId()){
				maxItem = fi.getItemId();
			}
		}
		//设置表单的最大序列项
		if(form.getItemMax()<=maxItem){
			form.setItemMax(maxItem);
		}
		//设置打印模板
		form.setPrintModel(content);
		//设置快速打印模板
		form.setPrintModelShort(shortPrintModel);
		
		
		//原表单字段
//		List<TeeFormItem> originFormItems = listFormItems(form);
//		if(originFormItems.size()==0){//原表单字段不存在，保存实体
//			for(TeeFormItem fi:formItems){
//				fi.setForm(form);
//				saveFormItem(fi);
//			}
//		}else{//原表单字段存在，进行替换
//			form.getFormItems().clear();
//			for(TeeFormItem fi:formItems){
//				fi.setForm(form);
//				saveFormItem(fi);
//			}
//			
//			//逐一进行实体表更改列类型
//			TeeForm rootForm = flowFormDao.getFirstVersion(form);
//			List<TeeFlowType> flowTypes = flowTypeDao.getFlowTypesByForm(rootForm);
//			for(TeeFlowType ft:flowTypes){
//				int flowId = ft.getSid();
//				String tableName = workflowHelper.getFormDataTableName(flowId);
//				workflowHelper.updateColumns(formItems, tableName);
//			}
//			
//		}
		
		form.getFormItems().clear();
		for(TeeFormItem fi:formItems){
			fi.setForm(form);
			saveFormItem(fi);
		}
		
		//逐一进行实体表更改列类型
		TeeForm rootForm = flowFormDao.getFirstVersion(form);
		List<TeeFlowType> flowTypes = flowTypeDao.getFlowTypesByForm(rootForm);
		for(TeeFlowType ft:flowTypes){
			//创建实体表
			workflowHelper.createFlowRunDataTable(ft);
			int flowId = ft.getSid();
			String tableName = TeeWorkflowHelper.getFormDataTableName(flowId);
			workflowHelper.updateColumns(formItems, tableName);
		}
		
		
//	    //列表控件   直接创建或者更改相关的中间表
//		for(TeeFormItem fi:formItems){
//			//判断当前控件是不是列表控件
//			if(("xlist").equals(fi.getXtype())){
//				TeeHTMLImgTag htmlImgTag = new TeeHTMLImgTag();
//				htmlImgTag.analyse(fi.getContent());
//				String tableName=TeeStringUtil.getString(htmlImgTag.getAttributes().get("tablename"));
//				List<Map<String, String>> mapList=TeeJsonUtil.JsonStr2MapList(fi.getModel());
//				String columnType=TeeColumnType.getColumnType(TeeColumnType.VARCHAR);
//				try{
//					simpleDaoSupport.executeNativeQuery("select 1 from "+ tableName, null, 0, 0);
//					//存在 则更新
//					if("mysql".equals(dialect)){//mysql
//						if(mapList!=null&&mapList.size()>0){
//					    	for (Map<String, String> map : mapList) {
//					    		try{
//					    			simpleDaoSupport.executeNativeUpdate(" alter table "+tableName+" add column "+map.get("fieldName").toUpperCase()+"  "+columnType, null);	
//					    		}catch(Exception e){
////					    			e.printStackTrace();
//					    		}
//								
//							}
//					    }
//					}else if("oracle".equals(dialect)){//oracle
//						if(mapList!=null&&mapList.size()>0){
//					    	for (Map<String, String> map : mapList) {
//					    		try{
//					    			simpleDaoSupport.executeNativeUpdate(" alter table "+tableName+" add  "+map.get("fieldName").toUpperCase()+"  "+columnType, null);
//					    		}catch(Exception e){
//					    			
//					    		}
//								
//							}
//					    }
//					}else if("sqlserver".equals(dialect)){//sqlserver
//						if(mapList!=null&&mapList.size()>0){
//					    	for (Map<String, String> map : mapList) {
//					    		try{
//					    			simpleDaoSupport.executeNativeUpdate(" alter table "+tableName+" add  "+map.get("fieldName").toUpperCase()+"  "+columnType, null);
//					    		}catch(Exception e){
//					    			
//					    		}	
//							}
//					    }
//					}
//				}catch(Exception e){
//					e.printStackTrace();
//					//不存在 则新建
//					if("mysql".equals(dialect)){//mysql
//						String sql=" CREATE TABLE "+tableName.toUpperCase()+" (SID INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,RUN_ID INT,FLOW_ID INT";
//					    if(mapList!=null&&mapList.size()>0){
//					    	for (Map<String, String> map : mapList) {
//								sql+=","+map.get("fieldName").toUpperCase()+" "+columnType;
//							}
//					    }
//					    sql+=")";
//					    simpleDaoSupport.executeNativeUpdate(sql, null);
//					}else if("oracle".equals(dialect)){//oracle
//						String seqStr="CREATE SEQUENCE "+tableName+"_SEQ" +" INCREMENT BY 1  START WITH 1 NOMAXVALUE  NOCYCLE  NOCACHE ";
//						simpleDaoSupport.executeNativeUpdate(seqStr, null);
//						String sql=" CREATE TABLE "+tableName.toUpperCase()+" (SID NUMBER(10) NOT NULL PRIMARY KEY,RUN_ID NUMBER(10),FLOW_ID NUMBER(10)";
//					    if(mapList!=null&&mapList.size()>0){
//					    	for (Map<String, String> map : mapList) {
//								sql+=","+map.get("fieldName").toUpperCase()+" "+columnType;
//							}
//					    }
//					    sql+=")";
//					    simpleDaoSupport.executeNativeUpdate(sql, null);
//					}else if("sqlserver".equals(dialect)){//sqlserver
//						String sql=" CREATE TABLE "+tableName.toUpperCase()+" (SID INT PRIMARY KEY IDENTITY(1,1),RUN_ID INT,FLOW_ID INT";
//					    if(mapList!=null&&mapList.size()>0){
//					    	for (Map<String, String> map : mapList) {
//								sql+=","+map.get("fieldName").toUpperCase()+" "+columnType;
//							}
//					    }
//					    sql+=")";
//					    simpleDaoSupport.executeNativeUpdate(sql, null);
//					}
//				}
				
//			}
//		}
		return form;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getFlowFormBySort1(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	public List<TeeForm> getFlowFormBySort1(int sortId,TeePerson loginUser){
		//判断当前登录的用户 是不是系统管理员
		boolean isAdmin=TeePersonService.checkIsSuperAdmin(loginUser, loginUser.getUserId());
		return flowFormDao.getFlowFormBySort1(sortId,loginUser,isAdmin);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getFlowFormBySort(int)
	 */
	public List<TeeForm> getFlowFormBySort(int sortId){
	
		return flowFormDao.getFlowFormBySort(sortId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getFlowFormBySort(com.tianee.oa.core.org.bean.TeePerson, int, int, int)
	 */
	public List<TeeForm> getFlowFormBySort(TeePerson loginUser,int sortId,int firstResult,int pageSize){
		//是否是超级管理员
		boolean isAdmin=TeePersonService.checkIsSuperAdmin(loginUser, loginUser.getUserId());
		return flowFormDao.getFlowFormBySort(loginUser,isAdmin,sortId,firstResult,pageSize);
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#setFlowFormDao(com.tianee.oa.core.workflow.formmanage.dao.TeeFlowFormDao)
	 */
	public void setFlowFormDao(TeeFlowFormDao flowFormDao) {
		this.flowFormDao = flowFormDao;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#setFlowFormItemDao(com.tianee.oa.core.workflow.formmanage.dao.TeeFlowFormItemDao)
	 */
	public void setFlowFormItemDao(TeeFlowFormItemDao flowFormItemDao) {
		this.flowFormItemDao = flowFormItemDao;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#setFlowTypeDao(com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao)
	 */
	public void setFlowTypeDao(TeeFlowTypeDao flowTypeDao) {
		this.flowTypeDao = flowTypeDao;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getVersionForm(int, int)
	 */
	public TeeForm getVersionForm(int formGroup, int versionNo) {
		// TODO Auto-generated method stub
		return flowFormDao.getVersionForm(formGroup, versionNo);
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#saveOrUpdateListCtrlExtend(com.tianee.oa.core.workflow.flowmanage.bean.TeeListCtrlExtend)
	 */
	public void saveOrUpdateListCtrlExtend(TeeListCtrlExtend lce) {
		//先检查是否存在该实体
		List<TeeListCtrlExtend> list = simpleDaoSupport.find("from TeeListCtrlExtend lce where lce.flowPrcsId=? and lce.formItemId=?", lce.getFlowPrcsId(),lce.getFormItemId());
		if(list.size()==0){
			simpleDaoSupport.save(lce);
		}else{
			TeeListCtrlExtend origin = list.get(0);
			origin.setColumnCtrlModel(lce.getColumnCtrlModel());
			simpleDaoSupport.update(origin);
		}
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#setSimpleDaoSupport(com.tianee.webframe.dao.TeeSimpleDaoSupport)
	 */
	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport) {
		this.simpleDaoSupport = simpleDaoSupport;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getListCtrlExtend(int, int)
	 */
	public TeeListCtrlExtend getListCtrlExtend(int flowPrcsId, int formItemId) {
		//先检查是否存在该实体
		List<TeeListCtrlExtend> list = simpleDaoSupport.find("from TeeListCtrlExtend lce where lce.flowPrcsId=? and lce.formItemId=?", flowPrcsId,formItemId);
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#createVersionFormService(int)
	 */
	@TeeLoggingAnt(template="创建表单版本 [{#.formName}]",type="006H")
	public TeeForm createVersionFormService(int formId){
		TeeForm form = flowFormDao.get(formId);
		TeeForm lastestForm = flowFormDao.getLatestVersion(form);
		TeeForm versionForm = new TeeForm();
		
		versionForm.setCss(form.getCss());
		versionForm.setFormGroup(form.getFormGroup());
		versionForm.setSid(0);
		versionForm.setFormName(form.getFormName());
		versionForm.setFormSort(form.getFormSort());
		versionForm.setItemMax(lastestForm.getItemMax());
		versionForm.setPrintModel(form.getPrintModel());
		versionForm.setPrintModelShort(form.getPrintModelShort());
		versionForm.setScript(form.getScript());
		versionForm.setVersionNo(lastestForm.getVersionNo()+1);
		versionForm.setVersionTime(Calendar.getInstance());
		
		
		flowFormDao.save(versionForm);
		TeeFormItem it = null;
		
		List<TeeFormItem> items = form.getFormItems();
		for(TeeFormItem item:items){
			it = new TeeFormItem();
			BeanUtils.copyProperties(item, it);
			it.setSid(0);
			it.setForm(versionForm);
			flowFormItemDao.save(it);
		}
		
		return versionForm;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#listVersions2Json(int)
	 */
	public TeeJson listVersions2Json(int formId){
		TeeJson json = new TeeJson();
		List<TeeForm> formList = listHistoryVersion(formId);
		List<Map> list = new ArrayList<Map>();
		for(TeeForm f:formList){
			Map datas = new HashMap();
			datas.put("versionTime", TeeDateUtil.format(f.getVersionTime().getTime()));
			datas.put("versionNo", f.getVersionNo());
			datas.put("itemCount", f.getFormItems().size());
			datas.put("formId", f.getSid());
			list.add(datas);
		}
		
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#importForm(int, java.io.File)
	 */
	public void importForm(int formId,File file) throws IOException{
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
		int available = input.available();
		byte b[] = new byte[available];
		input.read(b, 0, available);
		String html = new String(b,"UTF-8");
		
		saveAndUpdateFormService(html, formId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#checkExistFlow(int)
	 */
	public boolean checkExistFlow(int formId){
		long count = simpleDaoSupport.count("select count(*) from TeeFlowType ft where ft.form.sid="+formId, null);
		return count!=0;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getFormItemById(int)
	 */
	public TeeFormItem getFormItemById(int sid) {
		// TODO Auto-generated method stub
		return flowFormItemDao.get(sid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#list()
	 */
	public List<TeeForm> list(){
		return flowFormDao.list();
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getCountOfBundledFlowType(int)
	 */
	public long getCountOfBundledFlowType(int formId){
		return flowFormDao.getCountOfBundledFlowType(formId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface#getBundledFlowTypes(int)
	 */
	public List<TeeFlowType> getBundledFlowTypes(int formId){
		return flowFormDao.getBundledFlowTypes(formId);
	}

	
	/**
	 * 流程数据选择控件  映射字段
	 * @param flowId
	 * @param mappingStr
	 * @return
	 */
	public List<TeeFormItem> getMappingFormItemsByFlowType(int flowId,
			String mappingStr) {
		TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowId);
		TeeForm form=flowType.getForm();
		List<TeeFormItem> formItems=form.getFormItems();
		Set<TeeFormItem> set=new HashSet<TeeFormItem>();
		List<Map<String,String>> mapList=TeeJsonUtil.JsonStr2MapList(mappingStr);
		if(mapList!=null&&mapList.size()>0){
			if(formItems!=null&&formItems.size()>0){
				for (Map<String,String> map:mapList) {
					for (TeeFormItem formItem : formItems) {
						if(map.get("title1").equals(formItem.getTitle())){
							set.add(formItem);
						}
					}
				}
			}
		}
		
		List<TeeFormItem> list=new ArrayList<TeeFormItem>(set);
		return list;
	}

	@Override
	public TeeJson getFormItemsByFormGroup(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);
		//		
//		TeeForm form = getForm(formId);
//		//表单中的所有控件
//		List<TeeFormItem> items = getLatestFormItemsByOriginForm(form);
//		//控件分组
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
//		List<TeeFormItemGroup> listGroup=new ArrayList<TeeFormItemGroup>();
//		//获取表单控件所有分组
//		for(TeeFormItem item:items){
//			TeeFormItemGroup igroup = item.getItemGroup();
//		    if(!listGroup.contains(igroup)){
//		    	listGroup.add(igroup);
//		    }
//		}
		List<TeeFormItemGroup> listGroup = simpleDaoSupport.find("from TeeFormItemGroup where form.sid=? order by order asc", new Object[]{formId});
		String itemIds2="0";
		if(listGroup!=null && listGroup.size()>0){
			for(TeeFormItemGroup g:listGroup){
				itemIds2+=","+g.getItemIds();
			}
		}
		TeeFormItemGroup item2=new TeeFormItemGroup();
		listGroup.add(item2);
		for(TeeFormItemGroup g:listGroup){
			//{"type":"group","name":"组1","fields":
			//[{"name":"\u586b\u8868\u65e5\u671f","type":"macro","id":"DATA_243"}
			String hql="";
			Map<String,Object> map=new HashMap<String,Object>();
			//map.put("formId", formId);
			map.put("type", "group");
			if(g.getSid()==0){
				map.put("groupId", 0);
				map.put("name", "组容器");
				hql="from TeeFormItem where form.sid="+formId+" and itemId not in ("+itemIds2+")";
			}else{
				map.put("groupId", g.getSid());
				map.put("name", g.getGroupName());
				String itemIds = g.getItemIds();
				hql="from TeeFormItem where itemId in ("+itemIds+") and form.sid="+formId;
			}
			List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
			List<TeeFormItem> find = flowFormItemDao.find(hql+" order by sortNo asc", null);
			if(find!=null && find.size()>0){
				for(TeeFormItem item:find){
					Map<String,Object> m=new HashMap<String,Object>();
					m.put("itemId", item.getItemId());
					m.put("name", item.getTitle());
					m.put("type", item.getXtype());
					m.put("id", item.getName());
					listItem.add(m);
				}
			}
			if(listItem.size()>0){
				map.put("fields", listItem);
				listMap.add(map);
			}
		}
		json.setRtData(listMap);
		json.setRtState(true);
		return json;
	}

	/**
	 * 给控件分类
	 * */
	@Override
	public TeeJson updateFormItemsByFormGroup(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//组控件
		String strArr=TeeStringUtil.getString(request.getParameter("strArr"));
		//System.out.println(strArr);
		//strArr = strArr.replace("\\", "");
		JSONArray jsonArray = JSONArray.fromObject(strArr);
		List<Map<String,Object>> mapListJson = (List)jsonArray;
		int formId=0;
		if(mapListJson!=null && mapListJson.size()>0){
			String groupIds="";
			for(int i=0;i<mapListJson.size();i++){
				int order=i+1;
				Map<String, Object> map = mapListJson.get(i);
				int groupId=TeeStringUtil.getInteger(map.get("groupId"), 0);//组id
				String groupName=TeeStringUtil.getString(map.get("groupName"));//组名称
				formId=TeeStringUtil.getInteger(map.get("formId"),0);//组名称
				String itemIds=TeeStringUtil.getString(map.get("items"));
				if(groupId>0){//修改组
					TeeFormItemGroup group = (TeeFormItemGroup)simpleDaoSupport.get(TeeFormItemGroup.class,groupId);
					group.setGroupName(groupName);
					group.setOrder(order);
//					TeeForm form = getForm(formId);
//					group.setForm(form);
					group.setItemIds(itemIds);
					simpleDaoSupport.update(group);
					//simpleDaoSupport.deleteOrUpdateByQuery("update TeeFormItemGroup set groupName=?,order=?,itemIds=? where sid=?", new Object[]{groupName,order,itemIds,groupId});
				}else{//新建组
					TeeFormItemGroup group=new TeeFormItemGroup();
					group.setGroupName(groupName);
					group.setOrder(order);
					TeeForm form = getForm(formId);
					group.setForm(form);
					group.setItemIds(itemIds);
					Serializable save=simpleDaoSupport.save(group);
					groupId=TeeStringUtil.getInteger(save, 0);
				}
				groupIds+=groupId+",";
				//控件字符串
				String items=TeeStringUtil.getString(map.get("items"));
				if(items!=null && !"".equals(items)){
					String[] split = items.split(",");
					for(int j=0;j<split.length;j++){
						int sortNo=j+1;
						//控件id
						int itemId=TeeStringUtil.getInteger(split[j], 0);
						//给控件分组
						simpleDaoSupport.deleteOrUpdateByQuery("update TeeFormItem set sortNo=? where itemId=? and form.sid=?", new Object[]{sortNo,itemId,formId});
					}
					
				}
				
			}
			if(!"".equals(groupIds)){
				groupIds=groupIds.substring(0, groupIds.length()-1);
			}
			simpleDaoSupport.deleteOrUpdateByQuery("delete from TeeFormItemGroup where form.sid=? and sid not in("+groupIds+")", new Object[]{formId});
			//simpleDaoSupport.deleteOrUpdateByQuery("delete from TeeFormItemGroup where form.sid=? and itemIds=null", new Object[]{formId});
			json.setRtState(true);
			json.setRtMsg("保存成功");
		}
		return json;
	}
}
