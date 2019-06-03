package com.tianee.oa.subsys.report.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.subsys.report.bean.TeeConditionItem;
import com.tianee.oa.subsys.report.bean.TeeReportCondition;
import com.tianee.oa.subsys.report.bean.TeeReportTemplate;
import com.tianee.oa.subsys.report.bean.TeeTemplateItem;
import com.tianee.oa.subsys.report.model.TeeConditionItemModel;
import com.tianee.oa.subsys.report.model.TeeReportConditionModel;
import com.tianee.oa.subsys.report.model.TeeReportTemplateModel;
import com.tianee.oa.subsys.report.model.TeeTemplateItemModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeReportService extends TeeBaseService{
	
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	/**
	 * 根据流程ID获取条件列表
	 * @param flowId
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagridCondition(int flowId,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeReportCondition where flowType.sid="+flowId+" ";
		List<TeeReportCondition> conditions = simpleDaoSupport.pageFind(hql+"order by sid asc", 
				dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		
		List<TeeReportConditionModel> conditionModels = new ArrayList();
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		for(TeeReportCondition condition:conditions){
			TeeReportConditionModel m = new TeeReportConditionModel();
			ConditionEntity2Model(condition, m);
			conditionModels.add(m);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(conditionModels);
		return dataGridJson;
	}
	
	/**
	 * 保存condition
	 * @param conditionModel
	 * @param itemModels
	 */
	public void saveCondition(TeeReportConditionModel conditionModel,List<TeeConditionItemModel> itemModels){
		TeeReportCondition reportCondition = new TeeReportCondition();
		ConditionModel2Entity(conditionModel, reportCondition);
		simpleDaoSupport.save(reportCondition);
		
		for(TeeConditionItemModel itemModel:itemModels){
			TeeConditionItem item = new TeeConditionItem();
			ConditionItemModel2Entity(itemModel, item);
			item.setCondition(reportCondition);
			simpleDaoSupport.save(item);
		}
		conditionModel.setSid(reportCondition.getSid());
	}
	
	/**
	 * 删除condition
	 * @param conditionModel
	 * @param itemModels
	 */
	public void delCondition(int conditionId){
		long count = simpleDaoSupport.count("select count(*) from TeeReportTemplate where condition.sid="+conditionId, null);
		if(count!=0){
			throw new TeeOperationException("该条件已经被某模板使用，无法删除。");
		}
		simpleDaoSupport.delete(TeeReportCondition.class, conditionId);
	}
	
	/**
	 * 更新condition
	 * @param conditionModel
	 * @param itemModels
	 */
	public void updateCondition(TeeReportConditionModel conditionModel,List<TeeConditionItemModel> itemModels){
		TeeReportCondition reportCondition = new TeeReportCondition();
		ConditionModel2Entity(conditionModel, reportCondition);
		simpleDaoSupport.update(reportCondition);
		
		//删除所有条件项目
		simpleDaoSupport.executeUpdate("delete from TeeConditionItem where condition.sid="+conditionModel.getSid(), null);
		
		for(TeeConditionItemModel itemModel:itemModels){
			TeeConditionItem item = new TeeConditionItem();
			ConditionItemModel2Entity(itemModel, item);
			item.setCondition(reportCondition);
			simpleDaoSupport.save(item);
		}
		conditionModel.setSid(reportCondition.getSid());
		
	}
	
	/**
	 * 获取条件模型
	 * @param conditionId
	 * @return
	 */
	public TeeReportConditionModel getCondition(int conditionId){
		TeeReportCondition reportCondition = 
				(TeeReportCondition) simpleDaoSupport.get(TeeReportCondition.class, conditionId);
		
		TeeReportConditionModel m = new TeeReportConditionModel();
		ConditionEntity2Model(reportCondition, m);
		
		if(!"0".equals(m.getBeginUser()) && !TeeUtility.isNullorEmpty(m.getBeginUser())){
			int sp[] = TeeStringUtil.parseIntegerArray(m.getBeginUser());
			String sb = "";
			TeePerson p = null;
			for(int i=0;i<sp.length;i++){
				p = (TeePerson) simpleDaoSupport.get(TeePerson.class, sp[i]);
				if(p!=null){
					sb+=p.getUserName();
				}
				if(i!=sb.length()-1 && p!=null){
					sb+=",";
				}
			}
			m.setBeginUserNames(sb);
		}else{
			m.setBeginUser("");
		}
		
		if(!"0".equals(m.getBeginDept()) && !TeeUtility.isNullorEmpty(m.getBeginDept())){
			int sp[] = TeeStringUtil.parseIntegerArray(m.getBeginDept());
			String sb = "";
			TeeDepartment p = null;
			for(int i=0;i<sp.length;i++){
				p = (TeeDepartment) simpleDaoSupport.get(TeeDepartment.class, sp[i]);
				if(p!=null){
					sb+=p.getDeptName();
				}
				if(i!=sb.length()-1 && p!=null){
					sb+=",";
				}
			}
			m.setBeginDeptNames(sb);
		}else{
			m.setBeginDept("");
		}
		
		if(!"0".equals(m.getBeginRole()) && !TeeUtility.isNullorEmpty(m.getBeginRole())){
			int sp[] = TeeStringUtil.parseIntegerArray(m.getBeginRole());
			String sb = "";
			TeeUserRole p = null;
			for(int i=0;i<sp.length;i++){
				p = (TeeUserRole) simpleDaoSupport.get(TeeUserRole.class, sp[i]);
				if(p!=null){
					sb+=p.getRoleName();
				}
				if(i!=sb.length()-1 && p!=null){
					sb+=",";
				}
			}
			m.setBeginRoleNames(sb);
		}else{
			m.setBeginRole("");
		}
		
		
		return m;
	}
	
	/**
	 * 获取条件模型项
	 * @param conditionId
	 * @return
	 */
	public List<TeeConditionItemModel> getConditionItems(int conditionId){
		List<TeeConditionItem> conditionItems = simpleDaoSupport.find("from TeeConditionItem where condition.sid=? ", new Object[]{conditionId});
		List<TeeConditionItemModel> list = new ArrayList<TeeConditionItemModel>();
		for(TeeConditionItem item:conditionItems){
			TeeConditionItemModel m = new TeeConditionItemModel();
			ConditionItemEntity2Model(item, m);
			list.add(m);
		}
		return list;
	}
	
	/**
	 * 根据流程ID获取模版列表
	 * @param flowId
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagridTemplate(int flowId,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeReportTemplate where flowType.sid="+flowId+" ";
		List<TeeReportTemplate> templates = simpleDaoSupport.pageFind(hql+"order by sid asc", 
				dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		
		List<TeeReportTemplateModel> conditionModels = new ArrayList();
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		for(TeeReportTemplate template:templates){
			TeeReportTemplateModel m = new TeeReportTemplateModel();
			TemplateEntity2Model(template, m);
			conditionModels.add(m);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(conditionModels);
		return dataGridJson;
	}
	
	/**
	 * 保存模版
	 * @param reportTemplateModel
	 * @param itemModels
	 */
	public void saveTemplate(TeeReportTemplateModel reportTemplateModel,List<TeeTemplateItemModel> itemModels){
		TeeReportTemplate reportTemplate = new TeeReportTemplate();
		TemplateModel2Entity(reportTemplateModel, reportTemplate);
		simpleDaoSupport.save(reportTemplate);
		
		for(TeeTemplateItemModel templateItemModel:itemModels){
			TeeTemplateItem templateItem = new TeeTemplateItem();
			TemplateItemModel2Entity(templateItemModel, templateItem);
			templateItem.setTemplate(reportTemplate);
			simpleDaoSupport.save(templateItem);
		}
		
		reportTemplateModel.setSid(reportTemplate.getSid());
	}
	
	/**
	 * 更新模版
	 * @param reportTemplateModel
	 * @param itemModels
	 */
	public void updateTemplate(TeeReportTemplateModel reportTemplateModel,List<TeeTemplateItemModel> itemModels){
		TeeReportTemplate reportTemplate = new TeeReportTemplate();
		TemplateModel2Entity(reportTemplateModel, reportTemplate);
		simpleDaoSupport.update(reportTemplate);
		
		//删除所有条件项目
		simpleDaoSupport.executeUpdate("delete from TeeTemplateItem where template.sid="+reportTemplateModel.getSid(), null);
		
		for(TeeTemplateItemModel templateItemModel:itemModels){
			TeeTemplateItem templateItem = new TeeTemplateItem();
			TemplateItemModel2Entity(templateItemModel, templateItem);
			templateItem.setTemplate(reportTemplate);
			simpleDaoSupport.save(templateItem);
		}
		
		reportTemplateModel.setSid(reportTemplate.getSid());
	}
	
	/**
	 * 获取模版
	 * @param templateId
	 * @return
	 */
	public TeeReportTemplateModel getTemplate(int templateId){
		TeeReportTemplate reportTemplate = 
				(TeeReportTemplate) simpleDaoSupport.get(TeeReportTemplate.class, templateId);
		TeeReportTemplateModel model = new TeeReportTemplateModel();
		TemplateEntity2Model(reportTemplate, model);
		return model;
	}
	
	/**
	 * 获取模版
	 * @param templateId
	 * @return
	 */
	public void delTemplate(int templateId){
		simpleDaoSupport.delete(TeeReportTemplate.class, templateId);
	}
	
	/**
	 * 获取模版项
	 * @param templateId
	 * @return
	 */
	public List<TeeTemplateItemModel> getTemplateItems(int templateId){
		List<TeeTemplateItemModel> modelList = new ArrayList();
		List<TeeTemplateItem> templateItemList = simpleDaoSupport.find("from TeeTemplateItem where template.sid="+templateId, null);
		for(TeeTemplateItem templateItem:templateItemList){
			TeeTemplateItemModel m = new TeeTemplateItemModel();
			TemplateItemEntity2Model(templateItem, m);
			modelList.add(m);
		}
		return modelList;
	}
	
	public void ConditionModel2Entity(TeeReportConditionModel conditionModel,TeeReportCondition condition){
		BeanUtils.copyProperties(conditionModel, condition);
		condition.setFlowType((TeeFlowType)simpleDaoSupport.get(TeeFlowType.class, conditionModel.getFlowId()));
		if(conditionModel.getTime1Desc()!=null && !"".equals(conditionModel.getTime1Desc())){
			condition.setTime1(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", conditionModel.getTime1Desc()+" 00:00:00"));
		}
		
		if(conditionModel.getTime2Desc()!=null && !"".equals(conditionModel.getTime2Desc())){
			condition.setTime2(TeeDateUtil.parseCalendar("yyyy-MM-dd hh:mm:ss", conditionModel.getTime2Desc()+" 23:59:59"));
		}
	}
	
	public void ConditionEntity2Model(TeeReportCondition condition,TeeReportConditionModel conditionModel){
		BeanUtils.copyProperties(condition, conditionModel);
		if(condition.getTime1()!=null){
			conditionModel.setTime1Desc(TeeDateUtil.format(condition.getTime1().getTime(),"yyyy-MM-dd"));
		}
		if(condition.getTime2()!=null){
			conditionModel.setTime2Desc(TeeDateUtil.format(condition.getTime2().getTime(),"yyyy-MM-dd"));
		}
		conditionModel.setFlowId(condition.getFlowType().getSid());
	}
	
	public void ConditionItemModel2Entity(TeeConditionItemModel conditionItemModel,TeeConditionItem conditionItem){
		BeanUtils.copyProperties(conditionItemModel, conditionItem);
	}
	
	public void ConditionItemEntity2Model(TeeConditionItem conditionItem,TeeConditionItemModel conditionItemModel){
		BeanUtils.copyProperties(conditionItem, conditionItemModel);
		conditionItemModel.setConditionId(conditionItem.getCondition().getSid());
	}
	
	public void TemplateModel2Entity(TeeReportTemplateModel reportTemplateModel,TeeReportTemplate reportTemplate){
		BeanUtils.copyProperties(reportTemplateModel, reportTemplate);
		reportTemplate.setFlowType((TeeFlowType)simpleDaoSupport.get(TeeFlowType.class, reportTemplateModel.getFlowId()));
		if(reportTemplateModel.getConditionId()!=0){
			reportTemplate.setCondition((TeeReportCondition)simpleDaoSupport.get(TeeReportCondition.class, reportTemplateModel.getConditionId()));
		}
		
		//关联人员
		int ids [] = TeeStringUtil.parseIntegerArray(reportTemplateModel.getUserPrivIds());
		for(int uuid:ids){
			TeePerson p = new TeePerson();
			p.setUuid(uuid);
			reportTemplate.getUserPriv().add(p);
		}
		
		//关联部门
		String deptPrivIds = reportTemplateModel.getDeptPrivIds();
		ids = TeeStringUtil.parseIntegerArray(deptPrivIds);
		for(int uuid:ids){
			TeeDepartment d = new TeeDepartment();
			d.setUuid(uuid);
			reportTemplate.getDeptPriv().add(d);
		}
	}
	
	public void TemplateEntity2Model(TeeReportTemplate reportTemplate,TeeReportTemplateModel reportTemplateModel){
		BeanUtils.copyProperties(reportTemplate, reportTemplateModel);
		if(reportTemplate.getCondition()!=null){
			reportTemplateModel.setConditionId(reportTemplate.getCondition().getSid());
			reportTemplateModel.setConditionName(reportTemplate.getCondition().getConditionName());
		}
		reportTemplateModel.setFlowId(reportTemplate.getFlowType().getSid());
		
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		Set<TeePerson> userPriv = reportTemplate.getUserPriv();
		for(TeePerson p : userPriv){
			ids.append(p.getUuid()+",");
			names.append(p.getUserName()+",");
		}
		
		if(ids.length()!=0 && ids.charAt(ids.length()-1)==','){
			ids.deleteCharAt(ids.length()-1);
			names.deleteCharAt(names.length()-1);
		}
		reportTemplateModel.setUserPrivIds(ids.toString());
		reportTemplateModel.setUserPrivNames(names.toString());
		
		ids.delete(0, ids.length());
		names.delete(0,names.length());
		
		Set<TeeDepartment> deptPriv = reportTemplate.getDeptPriv();
		for(TeeDepartment d : deptPriv){
			ids.append(d.getUuid()+",");
			names.append(d.getDeptName()+",");
		}
		if(ids.length()!=0 && ids.charAt(ids.length()-1)==','){
			ids.deleteCharAt(ids.length()-1);
			names.deleteCharAt(names.length()-1);
		}
		reportTemplateModel.setDeptPrivIds(ids.toString());
		reportTemplateModel.setDeptPrivNames(names.toString());
		
	}
	
	public void TemplateItemEntity2Model(TeeTemplateItem templateItem,TeeTemplateItemModel templateItemModel){
		BeanUtils.copyProperties(templateItem, templateItemModel);
		templateItemModel.setTemplateId(templateItem.getTemplate().getSid());
	}
	
	public void TemplateItemModel2Entity(TeeTemplateItemModel templateItemModel,TeeTemplateItem templateItem){
		BeanUtils.copyProperties(templateItemModel, templateItem);
		templateItem.setTemplate((TeeReportTemplate)simpleDaoSupport.get(TeeReportTemplate.class, templateItemModel.getTemplateId()));
	}
	
	public TeeEasyuiDataGridJson viewableReports(Map requestData,TeeDataGridModel dm,TeePerson loginUser){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String tplName = TeeStringUtil.getString(requestData.get("tplName"));
		
		
		String hql = "from TeeReportTemplate rt where rt.tplName like ? and (exists (select 1 from rt.userPriv userPriv where userPriv.uuid="+loginUser.getUuid()+") "
				+ " or exists (select 1 from rt.deptPriv deptPriv where deptPriv.uuid="+loginUser.getDept().getUuid()+")) ";
		
		List<TeeReportTemplate> templates = simpleDaoSupport.pageFind(hql+"order by sid asc", 
				dm.getRows()*(dm.getPage()-1), dm.getRows(), new Object[]{"%"+tplName+"%"});
		
		List<TeeReportTemplateModel> conditionModels = new ArrayList();
		long total = simpleDaoSupport.count("select count(*) "+hql, new Object[]{"%"+tplName+"%"});
		for(TeeReportTemplate template:templates){
			TeeReportTemplateModel m = new TeeReportTemplateModel();
			TemplateEntity2Model(template, m);
			conditionModels.add(m);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(conditionModels);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson reportDatas(Map requestData,TeeDataGridModel dm,TeePerson loginUser){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		int templateId = TeeStringUtil.getInteger(requestData.get("templateId"), 0);
		TeeReportTemplate template = 
				(TeeReportTemplate) simpleDaoSupport.get(TeeReportTemplate.class, templateId);
		String dialect = TeeSysProps.getString("dialect");
		TeeFlowType ft = template.getFlowType();
		String joinTable = "tee_f_r_d_"+ft.getSid();//连接表名
		
		StringBuffer sql = new StringBuffer();
		StringBuffer select = new StringBuffer();
		StringBuffer groupSql = new StringBuffer();
		select.append("select fr.BEGIN_TIME as BEGIN_TIME,"
				+ "fr.RUN_NAME as RUN_NAME,"
				+ "fr.END_TIME as END_TIME,"
				+ "fr.RUN_ID as RUN_ID,"
				+ "fr.BEGIN_PERSON as BEGIN_PERSON,"
				+ "fr.DEL_FLAG as DEL_FLAG,"
				+ "p.USER_NAME as USER_NAME");
		
		//获取流程表单信息
		TeeForm form = ft.getForm();
		form = flowFormService.getLatestVersion(form);
		String dataSql = "";
		List<TeeFormItem> formItems = form.getFormItems();
		for(TeeFormItem item:formItems){
			dataSql+=","+TeeDbUtility.TO_CHAR(dialect, "data."+item.getName())+" as "+item.getName();
		}
		select.append(dataSql);
		
		//获取显示列
		List<TeeTemplateItem> items = simpleDaoSupport.find("from TeeTemplateItem where template.sid="+templateId+" order by sid asc", null);
		
		//拼接select语句
//		for(TeeTemplateItem item:items){
//			if(!item.getItem().startsWith("RUN_")){//非流程内部数据
//				select.append(",data."+item.getItem());
//			}
//		}
		
		sql.append(" from FLOW_RUN fr,"+joinTable+" data,DEPARTMENT dept,PERSON p,USER_ROLE r where fr.RUN_ID=data.RUN_ID and fr.BEGIN_PERSON=p.UUID and p.DEPT_ID=dept.UUID and p.USER_ROLE=r.UUID and fr.DEL_FLAG=0 ");
		
		//获取查询条件
		TeeReportCondition condition = template.getCondition();
		List params = new ArrayList();
		if(condition!=null){
			//拼接流程状态
			if(condition.getFlowFlag()==1){//已完成
				sql.append(" and fr.END_TIME is not null and fr.DEL_FLAG=0");
			}else if(condition.getFlowFlag()==2){//办理中
				sql.append(" and fr.END_TIME is null and fr.DEL_FLAG=0");
			}else if(condition.getFlowFlag()==3){//已完成与办理中
				sql.append(" and fr.DEL_FLAG=0");
			}else if(condition.getFlowFlag()==4){//所有流程
				//条件为空
			}
			
			//拼接发起人
			if(!"0".equals(condition.getBeginUser()) && !TeeUtility.isNullorEmpty(condition.getBeginUser())){
				sql.append(" and fr.BEGIN_PERSON in ("+condition.getBeginUser()+")");
			}
			
			//拼接发起人部门
			if(!"0".equals(condition.getBeginDept()) && !TeeUtility.isNullorEmpty(condition.getBeginDept())){
				sql.append(" and dept.UUID in ("+condition.getBeginDept()+")");
			}
			
			//拼接发起人角色
			if(!"0".equals(condition.getBeginRole()) && !TeeUtility.isNullorEmpty(condition.getBeginRole())){
				sql.append(" and r.UUID in ("+condition.getBeginRole()+")");
			}
			
			//拼接时间范围
			if(condition.getTimeRange()==1){//以发起时间为准
				if(condition.getTime1()!=null){
					sql.append(" and fr.BEGIN_TIME>=?");
					params.add(condition.getTime1());
				}
				if(condition.getTime2()!=null){
					sql.append(" and fr.BEGIN_TIME<=?");
					params.add(condition.getTime2());
				}
			}else if(condition.getTimeRange()==2){//以结束时间为准
				if(condition.getTime1()!=null){
					sql.append(" and fr.END_TIME>=?");
					params.add(condition.getTime1());
				}
				if(condition.getTime2()!=null){
					sql.append(" and fr.END_TIME<=?");
					params.add(condition.getTime2());
				}
			}else if(condition.getTimeRange()==3){//以发起时间和结束时间为准
				if(condition.getTime1()!=null){
					sql.append(" and fr.BEGIN_TIME>=?");
					params.add(condition.getTime1());
				}
				if(condition.getTime2()!=null){
					sql.append(" and fr.END_TIME<=?");
					params.add(condition.getTime2());
				}
			}
			
			//拼接条件项
			List<TeeConditionItem> conditionItems = simpleDaoSupport.find("from TeeConditionItem ci where ci.condition.sid="+condition.getSid(), null);
			for(TeeConditionItem item:conditionItems){
				if("包含".equals(item.getOper())){
					sql.append(" and "+TeeDbUtility.TO_CHAR(dialect,"data.DATA_"+item.getItemId())+" like '%"+item.getVal()+"%'");
				}else if("等于".equals(item.getOper())){
					sql.append(" and "+TeeDbUtility.TO_CHAR(dialect,"data.DATA_"+item.getItemId())+" like '"+item.getVal()+"'");
				}else if("大于".equals(item.getOper())){
					sql.append(" and "+TeeDbUtility.TO_CHAR(dialect,"data.DATA_"+item.getItemId())+" > '"+item.getVal()+"'");
				}else if("小于".equals(item.getOper())){
					sql.append(" and "+TeeDbUtility.TO_CHAR(dialect,"data.DATA_"+item.getItemId())+" < '"+item.getVal()+"'");
				}else if("大于等于".equals(item.getOper())){
					sql.append(" and "+TeeDbUtility.TO_CHAR(dialect,"data.DATA_"+item.getItemId())+" >= '"+item.getVal()+"'");
				}else if("小于等于".equals(item.getOper())){
					sql.append(" and "+TeeDbUtility.TO_CHAR(dialect,"data.DATA_"+item.getItemId())+" <= '"+item.getVal()+"'");
				}else if("开始为".equals(item.getOper())){
					sql.append(" and "+TeeDbUtility.TO_CHAR(dialect,"data.DATA_"+item.getItemId())+" like '"+item.getVal()+"%'");
				}else if("结束为".equals(item.getOper())){
					sql.append(" and "+TeeDbUtility.TO_CHAR(dialect,"data.DATA_"+item.getItemId())+" like '%"+item.getVal()+"'");
				}else if("不包含".equals(item.getOper())){
					sql.append(" and "+TeeDbUtility.TO_CHAR(dialect,"data.DATA_"+item.getItemId())+" not like '%"+item.getVal()+"%'");
				}
			}
		}
		
		//拼接高级查询数据
		String sItem1 = (String) requestData.get("sItem1");
		String sCdt1 = (String) requestData.get("sCdt1");
		String sTxt1 = (String) requestData.get("sTxt1");
		
		String sItem2 = (String) requestData.get("sItem2");
		String sCdt2 = (String) requestData.get("sCdt2");
		String sTxt2 = (String) requestData.get("sTxt2");
		
		String sItem3 = (String) requestData.get("sItem3");
		String sCdt3 = (String) requestData.get("sCdt3");
		String sTxt3 = (String) requestData.get("sTxt3");
		
		String sItem4 = (String) requestData.get("sItem4");
		String sCdt4 = (String) requestData.get("sCdt4");
		String sTxt4 = (String) requestData.get("sTxt4");
		
		String sItem5 = (String) requestData.get("sItem5");
		String sCdt5 = (String) requestData.get("sCdt5");
		String sTxt5 = (String) requestData.get("sTxt5");
		
		
		
		sql.append(getSqlBySeniorSearch(sItem1,sCdt1,sTxt1));
		sql.append(getSqlBySeniorSearch(sItem2,sCdt2,sTxt2));
		sql.append(getSqlBySeniorSearch(sItem3,sCdt3,sTxt3));
		sql.append(getSqlBySeniorSearch(sItem4,sCdt4,sTxt4));
		sql.append(getSqlBySeniorSearch(sItem5,sCdt5,sTxt5));
		
		
		String orderBy = "";
		if(!"".equals(template.getSortBy())){
			if("RUN_ID".equals(template.getSortBy())){
				orderBy = " order by fr.RUN_ID "+TeeStringUtil.getString(template.getSortByOrder());
			}else if("RUN_NAME".equals(TeeStringUtil.getString(template.getSortBy()))){
				orderBy = " order by fr.RUN_NAME "+TeeStringUtil.getString(template.getSortByOrder());
			}else if("RUN_STATUS".equals(TeeStringUtil.getString(template.getSortBy()))){
				orderBy = " order by fr.END_TIME "+TeeStringUtil.getString(template.getSortByOrder());
			}else if("RUN_USER".equals(TeeStringUtil.getString(template.getSortBy()))){
				orderBy = " order by fr.BEGIN_PERSON "+TeeStringUtil.getString(template.getSortByOrder());
			}else if("RUN_START".equals(TeeStringUtil.getString(template.getSortBy()))){
				orderBy = " order by fr.BEGIN_TIME "+TeeStringUtil.getString(template.getSortByOrder());
			}else if("RUN_END".equals(TeeStringUtil.getString(template.getSortBy()))){
				orderBy = " order by fr.END_TIME "+TeeStringUtil.getString(template.getSortByOrder());
			}else{
				orderBy = " order by "+TeeStringUtil.getString(template.getSortBy())+" "+TeeStringUtil.getString(template.getSortByOrder());
			}
		}
		
		List<Map> datas = null;
		StringBuffer groupCount = new StringBuffer();
		long total = 0;
		
		
		List<Map> tmpDatas = null;
		template.setGroupBy(TeeStringUtil.getString(template.getGroupBy()));
		template.setSortBy(TeeStringUtil.getString(template.getSortBy()));
		template.setSortByOrder(TeeStringUtil.getString(template.getSortByOrder()));
		template.setGroupByOrder(TeeStringUtil.getString(template.getGroupByOrder()));
		//存在分组，则以分组对分页对象
		if(!"".equals(template.getGroupBy())){
			datas = new ArrayList();
			if("RUN_ID".equals(template.getGroupBy())){
				groupSql.append("SELECT fr.RUN_ID "+sql.toString()+" group by fr.RUN_ID order by fr.RUN_ID "+TeeStringUtil.getString(template.getGroupByOrder()));
				List<Map> tmps = simpleDaoSupport.executeNativeQuery(groupSql.toString(), null, dm.getRows()*(dm.getPage()-1), dm.getRows());
				for(Map tmp:tmps){
					tmpDatas = simpleDaoSupport.executeNativeQuery(select.toString()+sql.toString()+" and fr.RUN_ID="+tmp.get("RUN_ID")+" "+orderBy, params.toArray(), 0, Integer.MAX_VALUE);
					filterReportDatas(template.getGroupBy(),tmpDatas, items);
					datas.addAll(tmpDatas);
				}
				groupCount.append("SELECT count(distinct(fr.RUN_ID)) as c "+sql.toString()+"");
			}else if("RUN_NAME".equals(template.getGroupBy())){
				groupSql.append("SELECT fr.RUN_NAME "+sql.toString()+" group by fr.RUN_NAME order by fr.RUN_NAME "+TeeStringUtil.getString(template.getGroupByOrder()));
				List<Map> tmps = simpleDaoSupport.executeNativeQuery(groupSql.toString(), null, dm.getRows()*(dm.getPage()-1), dm.getRows());
				for(Map tmp:tmps){
					tmpDatas=simpleDaoSupport.executeNativeQuery(select.toString()+sql.toString()+" and fr.RUN_NAME like '"+tmp.get("RUN_NAME")+"'"+" "+orderBy, params.toArray(), 0, Integer.MAX_VALUE);
					filterReportDatas(template.getGroupBy(),tmpDatas, items);
					datas.addAll(tmpDatas);
				}
				groupCount.append("SELECT count(distinct(fr.RUN_NAME)) as c "+sql.toString()+"");
			}else if("RUN_STATUS".equals(template.getGroupBy())){
				groupCount.append("SELECT 2 as c from SYSTMP");
				tmpDatas=simpleDaoSupport.executeNativeQuery(select.toString()+sql.toString()+""+" "+orderBy, params.toArray(), 0, Integer.MAX_VALUE);
				filterReportDatas("RUN_STATUS",tmpDatas, items);
				datas.addAll(tmpDatas);
			}else if("RUN_USER".equals(template.getGroupBy())){
				groupSql.append("SELECT fr.BEGIN_PERSON "+sql.toString()+" group by fr.BEGIN_PERSON order by fr.BEGIN_PERSON "+TeeStringUtil.getString(template.getGroupByOrder()));
				List<Map> tmps = simpleDaoSupport.executeNativeQuery(groupSql.toString(), null, dm.getRows()*(dm.getPage()-1), dm.getRows());
				for(Map tmp:tmps){
					tmpDatas=simpleDaoSupport.executeNativeQuery(select.toString()+sql.toString()+" and fr.BEGIN_PERSON = "+tmp.get("BEGIN_PERSON")+""+" "+orderBy, params.toArray(), 0, Integer.MAX_VALUE);
					filterReportDatas(template.getGroupBy(),tmpDatas, items);
					datas.addAll(tmpDatas);
				}
				groupCount.append("SELECT count(distinct(fr.BEGIN_PERSON)) as c "+sql.toString());
			}else if("RUN_START".equals(template.getGroupBy())){
				groupSql.append("SELECT "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.BEGIN_TIME")+" as TIME_STR "+sql.toString()+" group by "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.BEGIN_TIME")+" order by "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.BEGIN_TIME")+" "+TeeStringUtil.getString(template.getGroupByOrder()));
				List<Map> tmps = simpleDaoSupport.executeNativeQuery(groupSql.toString(), null, dm.getRows()*(dm.getPage()-1), dm.getRows());
				for(Map tmp:tmps){
					tmpDatas=simpleDaoSupport.executeNativeQuery(select.toString()+sql.toString()+" and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.BEGIN_TIME")+" like '"+tmp.get("TIME_STR")+"'"+" "+orderBy, params.toArray(), 0, Integer.MAX_VALUE);
					filterReportDatas(template.getGroupBy(),tmpDatas, items);
					datas.addAll(tmpDatas);
				}
				groupCount.append("SELECT count(distinct("+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.BEGIN_TIME")+")) as c "+sql.toString());
			}else if("RUN_END".equals(template.getGroupBy())){
				groupSql.append("SELECT "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.END_TIME")+" as TIME_STR "+sql.toString()+" group by "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.END_TIME")+" order by "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.END_TIME")+" "+TeeStringUtil.getString(template.getGroupByOrder()));
				List<Map> tmps = simpleDaoSupport.executeNativeQuery(groupSql.toString(), null, dm.getRows()*(dm.getPage()-1), dm.getRows());
				for(Map tmp:tmps){
					tmpDatas=simpleDaoSupport.executeNativeQuery(select.toString()+sql.toString()+" and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.END_TIME")+" like '"+tmp.get("TIME_STR")+"'"+" "+orderBy, params.toArray(), 0, Integer.MAX_VALUE);
					filterReportDatas(template.getGroupBy(),tmpDatas, items);
					datas.addAll(tmpDatas);
				}
				groupCount.append("SELECT count(distinct("+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,"fr.END_TIME")+")) as c "+sql.toString());
			}else{
				groupSql.append("SELECT "+TeeDbUtility.TO_CHAR(dialect, "data."+template.getGroupBy())+" as DATA "+sql.toString()+" group by "+TeeDbUtility.TO_CHAR(dialect, "data."+template.getGroupBy())+" order by "+TeeDbUtility.TO_CHAR(dialect, "data."+template.getGroupBy())+" "+TeeStringUtil.getString(template.getGroupByOrder()));
				List<Map> tmps = simpleDaoSupport.executeNativeQuery(groupSql.toString(), null, dm.getRows()*(dm.getPage()-1), dm.getRows());
				for(Map tmp:tmps){
					tmpDatas=simpleDaoSupport.executeNativeQuery(select.toString()+sql.toString()+" and "+TeeDbUtility.TO_CHAR(dialect, "data."+template.getGroupBy())+" like "+TeeDbUtility.TO_CHAR(dialect, "'"+tmp.get("DATA")+"'")+" "+orderBy, params.toArray(), 0, Integer.MAX_VALUE);
					filterReportDatas(template.getGroupBy(),tmpDatas, items);
					datas.addAll(tmpDatas);
				}
				groupCount.append("SELECT count(distinct("+TeeDbUtility.TO_CHAR(dialect, "data."+template.getGroupBy()+"")+")) as c "+sql.toString());
			}
			Map<String,String> d = simpleDaoSupport.executeNativeUnique(groupCount.toString(), null);
			total = TeeStringUtil.getLong(d.get("c"), 0);
		}else{//不存在分组，则以平面查询为分组对象
			datas = simpleDaoSupport.executeNativeQuery(select.toString()+sql.toString()+orderBy, params.toArray(), dm.getRows()*(dm.getPage()-1), dm.getRows());
			filterReportDatas(null,datas, items);
			Map<String,String> d = simpleDaoSupport.executeNativeUnique("SELECT count(1) as c "+sql.toString(), params.toArray());
			total = TeeStringUtil.getLong(d.get("c"), 0);
		}
		
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(datas);
		
		return dataGridJson;
	}
	
	private void filterReportDatas(String groupName,List<Map> datas,List<TeeTemplateItem> items){
		//过滤数据
		Timestamp beginTime = null;
		Timestamp endTime = null;
		if(datas==null){
			return;
		}
		for(Map data:datas){
			if(data.get("END_TIME")==null){
				data.put("RUN_STATUS","办理中");
			}else{
				data.put("RUN_STATUS","已完成");
			}
			
			data.put("RUN_USER",data.get("USER_NAME"));
			beginTime = (Timestamp)data.get("BEGIN_TIME");
			endTime = (Timestamp)data.get("END_TIME");
			if(beginTime!=null){
				data.put("RUN_START",TeeDateUtil.format(beginTime, "yyyy-MM-dd"));
			}
			if(endTime!=null){
				data.put("RUN_END",TeeDateUtil.format(endTime, "yyyy-MM-dd"));
			}
		}
		
		
		Map<String,String> colModel = null;
		double average = 0;
		double sum = 0;
		double max = Integer.MIN_VALUE;
		double min = Integer.MAX_VALUE;
		double tmp = 0;
		double count = 0;
		
		Map extraData = new HashMap();
		if(groupName!=null && datas.size()!=0){//额外信息添加分组
			extraData.put(groupName, datas.get(0).get(groupName));
		}

		int decimalInt = 0;
		BigDecimal decimal = null;
		
		boolean isMergeGroup = false;
		
		for(TeeTemplateItem item:items){
			colModel = TeeJsonUtil.JsonStr2Map(item.getColModel());
			if(item.getTemplate().getMergeGroup()==1){
				isMergeGroup = true;
			}
			for(Map data:datas){
				count++;
				if(!"0".equals(colModel.get("cal"))){
					tmp = TeeStringUtil.getDouble(data.get(item.getItem()), 0);
					sum += tmp;//加总和
					if(max<tmp){
						max = tmp;
					}
					if(min>tmp){
						min = tmp;
					}
				}
				String itemData = data.get(item.getItem())+"";
				if("null".equals(itemData)){
					itemData = "";
				}
				
				//加前缀
				if("1".equals(colModel.get("fix"))){//前缀
					data.put(item.getItem(), colModel.get("fixName")+itemData);
				}else if("2".equals(colModel.get("fix"))){//后缀
					data.put(item.getItem(), itemData+colModel.get("fixName"));
				}
			}
			
			decimalInt = TeeStringUtil.getInteger(colModel.get("decimal"), 0);
			if("1".equals(colModel.get("cal"))){//平均
				
				decimal = new BigDecimal(datas.size()==0?0:sum/datas.size());
				extraData.put(item.getItem(), decimal.setScale(decimalInt,BigDecimal.ROUND_HALF_UP).toString());
				
			}else if("2".equals(colModel.get("cal"))){//总和
				
				decimal = new BigDecimal(sum);
				extraData.put(item.getItem(), decimal.setScale(decimalInt,BigDecimal.ROUND_HALF_UP).toString());
				
			}else if("3".equals(colModel.get("cal"))){//最大值
				
				decimal = new BigDecimal(max);
				extraData.put(item.getItem(), decimal.setScale(decimalInt,BigDecimal.ROUND_HALF_UP).toString());
				
			}else if("4".equals(colModel.get("cal"))){//最小值
				
				decimal = new BigDecimal(min);
				extraData.put(item.getItem(), decimal.setScale(decimalInt,BigDecimal.ROUND_HALF_UP).toString());
				
			}else if("5".equals(colModel.get("cal"))){//数量
				extraData.put(item.getItem(), count);
				
			}
			
			average = 0;
			sum = 0;
			max = 0;
			min = 0;
			count = 0;
			
			if("1".equals(colModel.get("fix"))){//前缀
				extraData.put(item.getItem(), colModel.get("fixName")+extraData.get(item.getItem()));
			}else if("2".equals(colModel.get("fix"))){//后缀
				extraData.put(item.getItem(), extraData.get(item.getItem())+colModel.get("fixName"));
			}
			
		}
		
		if(isMergeGroup){//如果分组合并，则移除所有元素
			datas.clear();
		}
		if(extraData.size()!=0){//有列计算的话，加入进去
			extraData.put("GROUPFLAG", 1);
			datas.add(extraData);
		}
	}
	
	private String getSqlBySeniorSearch(String sItem,String sCdt,String sTxt){
		String dialect = TeeSysProps.getString("dialect");
		if(sItem!=null && !"".equals(sItem)){
			boolean isInt = false;
			boolean isDate = false;
			if("RUN_ID".equals(sItem)){
				sItem = "fr.RUN_ID";
				isInt = true;
			}else if("RUN_NAME".equals(sItem)){
				sItem = "fr.RUN_NAME";
			}else if("RUN_STATUS".equals(sItem)){
				
			}else if("RUN_USER".equals(sItem)){
				sItem = "p.USER_NAME";
			}else if("RUN_START".equals(sItem)){
				sItem = "fr.BEGIN_TIME";
				isDate = true;
			}else if("RUN_END".equals(sItem)){
				sItem = "fr.END_TIME";
				isDate = true;
			}else{
				sItem = "data."+sItem;
			}
			
			if("包含".equals(sCdt)){
				if(isInt){
					return " and "+sItem+" = "+sTxt+"";
				}else if(isDate){
					return " and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,sItem)+" like '%"+sTxt+"%'";
				}else{
					return " and "+TeeDbUtility.TO_CHAR(dialect,sItem)+" like '%"+sTxt+"%'";
				}
			}else if("等于".equals(sCdt)){
				if(isInt){
					return " and "+sItem+" = "+sTxt+"";
				}else if(isDate){
					return " and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,sItem)+" = '"+sTxt+"'";
				}else{
					return " and "+TeeDbUtility.TO_CHAR(dialect,sItem)+" = '"+sTxt+"'";
				}
			}else if("大于".equals(sCdt)){
				if(isInt){
					return " and "+sItem+" > "+sTxt+"";
				}else if(isDate){
					return " and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,sItem)+" > '"+sTxt+"'";
				}else{
					return " and "+TeeDbUtility.TO_CHAR(dialect,sItem)+" > '"+sTxt+"'";
				}
			}else if("小于".equals(sCdt)){
				if(isInt){
					return " and "+sItem+" < "+sTxt+"";
				}else if(isDate){
					return " and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,sItem)+" < '"+sTxt+"'";
				}else{
					return " and "+TeeDbUtility.TO_CHAR(dialect,sItem)+" < '"+sTxt+"'";
				}
			}else if("大于等于".equals(sCdt)){
				if(isInt){
					return " and "+sItem+" >= "+sTxt+"";
				}else if(isDate){
					return " and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,sItem)+" >= '"+sTxt+"'";
				}else{
					return " and "+TeeDbUtility.TO_CHAR(dialect,sItem)+" >= '"+sTxt+"'";
				}
			}else if("小于等于".equals(sCdt)){
				if(isInt){
					return " and "+sItem+" <= "+sTxt+"";
				}else if(isDate){
					return " and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,sItem)+" <= '"+sTxt+"'";
				}else{
					return " and "+TeeDbUtility.TO_CHAR(dialect,sItem)+" <= '"+sTxt+"'";
				}
			}else if("开始为".equals(sCdt)){
				if(isInt){
					return " and "+sItem+" = "+sTxt+"";
				}else if(isDate){
					return " and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,sItem)+" like '"+sTxt+"%'";
				}else{
					return " and "+TeeDbUtility.TO_CHAR(dialect,sItem)+" like '"+sTxt+"%'";
				}
			}else if("结束为".equals(sCdt)){
				if(isInt){
					return " and "+sItem+" = "+sTxt+"";
				}else if(isDate){
					return " and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,sItem)+" like '%"+sTxt+"'";
				}else{
					return " and "+TeeDbUtility.TO_CHAR(dialect,sItem)+" like '%"+sTxt+"'";
				}
			}else if("不包含".equals(sCdt)){
				if(isInt){
					return " and "+sItem+" != "+sTxt+"";
				}else if(isDate){
					return " and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect,sItem)+" not like '%"+sTxt+"%'";
				}else{
					return " and "+TeeDbUtility.TO_CHAR(dialect,sItem)+" not like '%"+sTxt+"%'";
				}
			}
		}
		return "";
	}
}
