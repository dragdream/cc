package com.tianee.oa.util.workflow.ctrl;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXInputCtrl extends TeeCtrl{

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		String model = formItem.getModel();
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		if(model!=null){
			Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
			String dataType = modelObj.get("datatype");
			if("1".equals(dataType)){//字符型255
				formItem.setColumnType(TeeColumnType.VARCHAR);
			}else if("2".equals(dataType) || "3".equals(dataType)){//整型或者浮点型
				formItem.setColumnType(TeeColumnType.DECIMAL);
			}
		}else{
			formItem.setColumnType(TeeColumnType.VARCHAR);
		}
		return formItem.getColumnType();
	}

	@Override
	public String getCtrlColumnTypeName(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return TeeColumnType.getColumnType(getCtrlColumnType(formItem));
	}

	@Override
	public String getCtrlHtml4Design(TeeFormItem formItem) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		return "<input " +
				"title=\""+attrs.get("title")+"\" " +
				"id=\""+attrs.get("id")+"\" " +
				"name=\""+attrs.get("name")+"\" " +
				"style=\""+attrs.get("style")+"\" " +
				"type=\"text\" " +
				"value=\""+(attrs.get("value")==null?"":attrs.get("value"))+"\" />";
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,TeeFlowRun flowRun,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRun,flowRunPrcs, formItem);
		
		boolean hidden = false;//可见
		if(ctrl!=null){
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		if(hidden){
			return "";
		}
		
		return "<span style=\""+attrs.get("style")+"\">"+TeeStringUtil.getString(flowFormData.getData())+"</span>"+"<input class=\"FORM_PRINT\" type=\"hidden\" value=\""+flowFormData.getData()+"\" title=\""+formItem.getTitle()+"\" />";
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs,Map<String,String> datas) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRunPrcs.getFlowRun(),flowRunPrcs, formItem);
		if(ctrl!=null){
			if("1".equals(ctrl.get("writable"))){
				writable = true;
			}
			if("1".equals(ctrl.get("required"))){
				required = true;		
			}
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
			if("1".equals(ctrl.get("auto"))){
				auto = true;
			}
		}
		
		//如果为自由流程并且是第一步发起时，将所有字段设置为可写
		if(flowType.getType()==2 && flowRunPrcs.getPrcsId()==1){
			writable = true;
		}
		
		//附加条件，如果当前为经办人的话，则没有权限动表单
		if(flowRunPrcs.getTopFlag()==0){
			writable = false;
			required = false;
			auto = false;
		}
		
		if(hidden){
			return "";
		}
		
		//控件blur事件
		StringBuffer onblur = new StringBuffer();
		//控件focus事件
		StringBuffer onfocus = new StringBuffer();
//		if(required){//必填项
//			onblur.append("blurRequired(this);");
//		}
		
		//获取模型
		String model = formItem.getModel();
		if(model!=null){
			Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
			String dataType = modelObj.get("datatype");
			String format = modelObj.get("format");
			if(!"1".equals(dataType)){//格式化数字
				onblur.append("blurFormatNumber(this,'"+format+"');");
				onfocus.append("focusFormatNumber(this)");
			}
		}
		
		//自动赋值
		if(auto && "".equals(data)){
			
		}else{
			attrs.put("value", data);
		}
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		
		if(!writable){
			attrs.put("disabled", "disabled");
		}else{
			attrs.put("onblur", onblur.toString());
			attrs.put("onfocus", onfocus.toString());
			attrs.put("ondblclick", "quickFillData(this)");
			attrs.put("ontouchstart", "quickFillData(this)");
		}
		return wrap(writable,tag.toString(),TeeStringUtil.getString(attrs.get("value")),attrs.get("style"),"",formItem.getItemId(),title);
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun, TeeFormItem formItem,
			TeeFlowFormData flowFormData, Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		boolean writable = true;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		
		//控件blur事件
		StringBuffer onblur = new StringBuffer();
		//控件focus事件
		StringBuffer onfocus = new StringBuffer();
//				if(required){//必填项
//					onblur.append("blurRequired(this);");
//				}
		
		//获取模型
		String model = formItem.getModel();
		if(model!=null){
			Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
			String dataType = modelObj.get("datatype");
			String format = modelObj.get("format");
			if(!"1".equals(dataType)){//格式化数字
				onblur.append("blurFormatNumber(this,'"+format+"');");
				onfocus.append("focusFormatNumber(this)");
			}
		}
		
		//自动赋值
		if(auto && "".equals(data)){
			
		}else{
			attrs.put("value", data);
		}
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		
		if(!writable){
			attrs.put("disabled", "disabled");
		}else{
			attrs.put("onblur", onblur.toString());
			attrs.put("onfocus", onfocus.toString());
		}
		return tag.toString();
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		//表单控件分析
				TeeHTMLTag tag = new TeeHTMLInputTag();
				tag.getAttributes().put("style", "");
				tag.analyse(formItem.getContent());
				//获取控件属性
				Map<String,String> attrs = tag.getAttributes();
				attrs.put("style", "");
				TeeJsonUtil jsonUtil = new TeeJsonUtil();
				
				String name = formItem.getName();
				String title = formItem.getTitle();
				String data = flowFormData.getData()==null?"":flowFormData.getData();
				
				boolean writable = false;//可写
				boolean required = false;//必填
				boolean hidden = false;//可见
				boolean auto = false;//自动赋值
				
				//获取控制模型
				Map<String,String> ctrl = getCtrlModel(flowType, flowRunPrcs.getFlowRun(),flowRunPrcs, formItem);
				if(ctrl!=null){
					if("1".equals(ctrl.get("writable"))){
						writable = true;
					}
					if("1".equals(ctrl.get("required"))){
						required = true;		
					}
					if("1".equals(ctrl.get("hidden"))){
						hidden = true;
					}
					if("1".equals(ctrl.get("auto"))){
						auto = true;
					}
				}
				
				//如果为自由流程并且是第一步发起时，将所有字段设置为可写
				if(flowType.getType()==2 && flowRunPrcs.getPrcsId()==1){
					writable = true;
				}
				
				//附加条件，如果当前为经办人的话，则没有权限动表单
				if(flowRunPrcs.getTopFlag()==0){
					writable = false;
					required = false;
					auto = false;
				}
				
				if(hidden){
					return "";
				}
				
				//控件blur事件
				StringBuffer onblur = new StringBuffer();
				//控件focus事件
				StringBuffer onfocus = new StringBuffer();
//				if(required){//必填项
//					onblur.append("blurRequired(this);");
//				}
				
				//获取模型
				String model = formItem.getModel();
				if(model!=null){
					Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
					String dataType = modelObj.get("datatype");
					String format = modelObj.get("format");
					if(!"1".equals(dataType)){//格式化数字
						onblur.append("blurFormatNumber(this,'"+format+"');");
						onfocus.append("focusFormatNumber(this)");
					}
				}
				
				//自动赋值
				if(auto && "".equals(data)){
					
				}else{
					attrs.put("value", data);
				}
				attrs.put("writable", String.valueOf(writable));
				attrs.put("required", String.valueOf(required));
				
				if(!writable){
					attrs.put("disabled", "disabled");
				}else{
					attrs.put("onblur", onblur.toString());
					attrs.put("onfocus", onfocus.toString());
					attrs.put("ondblclick", "quickFillData(this)");
					attrs.put("ontouchstart", "quickFillData(this)");
				}
				return wrap(writable,tag.toString(),TeeStringUtil.getString(attrs.get("value")),"","",formItem.getItemId(),title);
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRun,flowRunPrcs, formItem);
		
		boolean hidden = false;//可见
		if(ctrl!=null){
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		if(hidden){
			return "";
		}
		
		return "<span style=\""+attrs.get("style")+"\">"+TeeStringUtil.getString(flowFormData.getData())+"</span>"+"<input class=\"FORM_PRINT\" type=\"hidden\" value=\""+flowFormData.getData()+"\" title=\""+formItem.getTitle()+"\" />";
	}
	
}
