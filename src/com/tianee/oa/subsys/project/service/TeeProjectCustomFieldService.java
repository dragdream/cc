package com.tianee.oa.subsys.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.project.bean.TeeProjectCustomField;
import com.tianee.oa.subsys.project.bean.TeeProjectType;
import com.tianee.oa.subsys.project.model.TeeProjectCustomFieldModel;
import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeProjectCustomFieldService extends TeeBaseService{

	/**
	 * 新增/编辑自定义字段
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeProjectCustomFieldModel model) {
		TeeJson json=new TeeJson();
		int sid = model.getSid();
		if(model.getSid()>0){//编辑
			TeeProjectCustomField field=parseToField(model);
		    simpleDaoSupport.update(field);
		    json.setRtState(true);
		    json.setRtMsg("编辑成功！");
		    
		}else{//新增
			TeeProjectCustomField field=parseToField(model);
			simpleDaoSupport.save(field);
			String columnName="FIELD_"+field.getSid();	
			simpleDaoSupport.executeNativeUpdate(" alter table project add column  "+columnName+"  "+TeeColumnType.getColumnType(TeeColumnType.VARCHAR)+" ", null);
		    json.setRtState(true);
		    json.setRtMsg("添加成功！");
		}
		return json;
	}

	
	
	/**
	 * 将model转换成实体类
	 * @param model
	 * @return
	 */
	private TeeProjectCustomField parseToField(TeeProjectCustomFieldModel model) {
		TeeProjectCustomField field=null;
		TeeProjectType type=null;
		TeeProjectType typeTask=null;

		if(model.getSid()>0){//编辑
	         field=(TeeProjectCustomField) simpleDaoSupport.get(TeeProjectCustomField.class,model.getSid());
	         field.setFieldName(model.getFieldName());
	         field.setFieldType(model.getFieldType());
	         field.setIsQuery(model.getIsQuery());
	         field.setIsShow(model.getIsShow());
	         field.setOrderNum(model.getOrderNum()); 
	         field.setSid(model.getSid());
	         String fieldCtrModel="";
			    if(("下拉列表").equals(model.getFieldType())){
			    	if(("系统编码").equals(model.getCodeType())){
			    		fieldCtrModel="{codeType:'"+model.getCodeType()+"',value:'"+model.getSysCode()+"'}";
			    	}else if(("自定义选项").equals(model.getCodeType())){
			    		fieldCtrModel="{codeType:'"+model.getCodeType()+"',value:['"+model.getOptionName()+"','"+model.getOptionValue()+"']}";
			    	}
			    	
			    }    
			    if(model.getProjectTypeId()>0){
			    	type=(TeeProjectType) simpleDaoSupport.get(TeeProjectType.class,model.getProjectTypeId());
			    }
			    if(model.getProjectTask()>0){
			    	typeTask=(TeeProjectType) simpleDaoSupport.get(TeeProjectType.class,model.getProjectTask());
			    }
			    field.setFieldCtrModel(fieldCtrModel);
			    field.setProjectType(type);
			    field.setProjectTask(typeTask);
		}else{//新增
			field=new TeeProjectCustomField();
		    //BeanUtils.copyProperties(model, field);	
			field.setFieldName(model.getFieldName());
	         field.setFieldType(model.getFieldType());
	         field.setIsQuery(model.getIsQuery());
	         field.setIsShow(model.getIsShow());
	         field.setOrderNum(model.getOrderNum());
		    String fieldCtrModel="";
		    if(("下拉列表").equals(model.getFieldType())){
		    	if(("系统编码").equals(model.getCodeType())){
		    		fieldCtrModel="{codeType:'"+model.getCodeType()+"',value:'"+model.getSysCode()+"'}";
		    	}else if(("自定义选项").equals(model.getCodeType())){
		    		fieldCtrModel="{codeType:'"+model.getCodeType()+"',value:['"+model.getOptionName()+"','"+model.getOptionValue()+"']}";
		    	}
		    	
		    }
		    
		    if(model.getProjectTypeId()>0){
		    	type=(TeeProjectType) simpleDaoSupport.get(TeeProjectType.class,model.getProjectTypeId());
		    }
		    if(model.getProjectTask()>0){
		    	typeTask=(TeeProjectType) simpleDaoSupport.get(TeeProjectType.class,model.getProjectTask());
		    }
		    field.setFieldCtrModel(fieldCtrModel);
		    field.setProjectType(type);
		    field.setProjectTask(typeTask);
		    
		}
		return field;
	}



	public TeeJson getFieldList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<TeeProjectCustomField> list=simpleDaoSupport.executeQuery("from TeeProjectCustomField order by orderNum asc ", null);
		List<TeeProjectCustomFieldModel> modelList=new ArrayList<TeeProjectCustomFieldModel>();
		TeeProjectCustomFieldModel model=null;
		for (TeeProjectCustomField teeProjectCustomField : list) {
			model=new TeeProjectCustomFieldModel();
			//BeanUtils.copyProperties(teeProjectCustomField, model);
			teeProjectCustomField.getFieldCtrModel();
			model.setFieldName(teeProjectCustomField.getFieldName());
			model.setFieldType(teeProjectCustomField.getFieldType());
			model.setIsQuery(teeProjectCustomField.getIsQuery());
			model.setIsShow(teeProjectCustomField.getIsShow());
			model.setOrderNum(teeProjectCustomField.getOrderNum());
			model.setSid(teeProjectCustomField.getSid());
			
			if(teeProjectCustomField.getProjectType()!=null){
				model.setProjectTypeId(teeProjectCustomField.getProjectType().getSid());
				model.setProjectTypeName(teeProjectCustomField.getProjectType().getTypeName());
			}else{
				model.setProjectTypeName("");
			}
			if(teeProjectCustomField.getProjectTask()!=null){
				model.setProjectTask(teeProjectCustomField.getProjectTask().getSid());
				model.setProjectTaskName(teeProjectCustomField.getProjectTask().getTypeName());
			}
			modelList.add(model);
		}
		json.setRtState(true);
		json.setRtData(modelList);
		json.setRtMsg("数据获取成功！");
		return json;
	}



	//根据主键删除
	public TeeJson deleteBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
		   	TeeProjectCustomField field=(TeeProjectCustomField) simpleDaoSupport.get(TeeProjectCustomField.class,sid);
		    simpleDaoSupport.deleteByObj(field);
		    //从项目表中删除列
		    String  columnName="field_"+sid;
		    simpleDaoSupport.executeNativeUpdate(" alter  table project  drop "+columnName,null);
		    json.setRtState(true);
		    json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("该自定义字段 不存在！");
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
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectCustomField field=(TeeProjectCustomField) simpleDaoSupport.get(TeeProjectCustomField.class,sid);
			TeeProjectCustomFieldModel model=parseToModel(field);
			json.setRtData(model);
			json.setRtState(true);
			json.setRtMsg("查询成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}



	/**
	 * 将Field转换成Model
	 * @param field
	 * @return
	 */
	private TeeProjectCustomFieldModel parseToModel(TeeProjectCustomField field) {
		TeeProjectCustomFieldModel  model=new TeeProjectCustomFieldModel();
		//BeanUtils.copyProperties(field, model);
		model.setFieldName(field.getFieldName());
		model.setFieldType(field.getFieldType());
		model.setIsQuery(field.getIsQuery());
		model.setIsShow(field.getIsShow());
		model.setOrderNum(field.getOrderNum());
		if(field.getProjectType()!=null){
			model.setProjectTypeId(field.getProjectType().getSid());
			model.setProjectTypeName(field.getProjectType().getTypeName());
		}
		if(field.getProjectTask()!=null){
			model.setProjectTask(field.getProjectTask().getSid());
			model.setProjectTaskName(field.getProjectTask().getTypeName());
		}
		String fieldCtrModel=field.getFieldCtrModel();
		if(!("").equals(fieldCtrModel)){	
			Map<String, String> map=TeeJsonUtil.JsonStr2Map(fieldCtrModel);
		    model.setCodeType(TeeStringUtil.getString(map.get("codeType")));
		    if(("系统编码").equals(TeeStringUtil.getString(map.get("codeType")))){
		    	model.setSysCode(TeeStringUtil.getString(map.get("value")));
		    }else if(("自定义选项").equals(TeeStringUtil.getString(map.get("codeType")))){
		    	String options=TeeStringUtil.getString(map.get("value"));
		    	JSONArray jsonArray = JSONArray.fromObject(options);    	
		    	model.setOptionName(TeeStringUtil.getString(jsonArray.get(0)));
		    	model.setOptionValue(TeeStringUtil.getString(jsonArray.get(1)));
		    }	    
		}	
		return model;
	}



	/**
	 * 根据项目类型主键获取自定义字段的集合
	 * @param request
	 * @return
	 */
	public TeeJson getListByProjectType(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int projectTypeId=TeeStringUtil.getInteger(request.getParameter("projectTypeId"), 0);
	    List<TeeProjectCustomField> list=null;
		if(projectTypeId>0){
			list=simpleDaoSupport.executeQuery(" from TeeProjectCustomField f where f.projectType.sid=? or f.projectTask.sid=? order by f.orderNum", new Object[]{projectTypeId,0});
		    json.setRtState(true);
			json.setRtData(list);
		}else{
			json.setRtState(false);		
		}
		return json;
	}



	/**
	 * 根据项目类型主键      获取自定义字段中可以在列表显示的字段集合
	 * @param request
	 * @return
	 */
	public TeeJson getShowFieldListByProjectTypeId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的项目类型的主键
		int projectTypeId=TeeStringUtil.getInteger(request.getParameter("projectTypeId"), 0);
		List<TeeProjectCustomField> list=simpleDaoSupport.executeQuery(" from TeeProjectCustomField where  projectType.sid=? and isShow=1 ", new Object[]{projectTypeId});
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}



	
	/**
	 * 根据项目类型主键      获取自定义字段中可以查询的字段集合
	 * @param request
	 * @return
	 */
	public TeeJson getQueryFieldListByProjectTypeId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的项目类型的主键
		int projectTypeId=TeeStringUtil.getInteger(request.getParameter("projectTypeId"), 0);
		List<TeeProjectCustomField> list=simpleDaoSupport.executeQuery(" from TeeProjectCustomField where  projectType.sid=? and isQuery=1 ", new Object[]{projectTypeId});
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}

}
