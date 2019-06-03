package com.tianee.oa.util.workflow.ctrl;

import java.util.Map;

import com.tianee.oa.core.customnumber.service.TeeCusNumberService;
import com.tianee.oa.core.customnumber.util.TeeCustomNumberModelKeys;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

/**
 * 表单自动编号控件
 * @author LiTeng
 *
 */
public class TeeXAutoNumberCtrl extends TeeCtrl{

	@Override
	public String getCtrlColumnTypeName(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCtrlHtml4Design(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return formItem.getContent();
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
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
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
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
		int numberId = TeeStringUtil.getInteger(attrs.get("numberid"), 0);
		TeeFlowRun flowRun = flowRunPrcs.getFlowRun();
		
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
		
		
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		
		if(!writable){
			attrs.put("disabled", "disabled");
		}
		
		if(writable){//可写
			//自动赋值
			if(!auto && "".equals(data)){
                attrs.put("value", data);
				
				String html="<input id=\"DATA_"+formItem.getItemId()+"\" title=\"自动编号控件\" writable="+writable+" xtype=\"xautonumber\" name=\"DATA_"+formItem.getItemId()+"\" value=\""+TeeStringUtil.getString(attrs.get("value"))+"\"  numberid="+numberId+" required="+required+"/>";
			    html+="<input type=\"button\" value=\"生成编号\" class=\"btn-win-white\" onclick=\"generateAutoNum("+formItem.getItemId()+","+numberId+",'workflowRun',"+String.valueOf(flowRun.getRunId())+")\" />";
			    
			    return html;
			}else{  
			    //如果自动编号id!=0，则自动进行编号递增
				 if(numberId!=0){
					TeeCusNumberService cusNumberService = getContext().getCusNumberService();
					data = cusNumberService.generateCustomNumber(numberId, TeeCustomNumberModelKeys.workflowRun, String.valueOf(flowRun.getRunId()));
				 }
				 attrs.put("value", data);
				 return wrap(writable,tag.toString(),TeeStringUtil.getString(attrs.get("value")),attrs.get("style"),"",formItem.getItemId(),title);
			}
			
		}else{//不可写
			attrs.put("value", data);
			return wrap(writable,tag.toString(),TeeStringUtil.getString(attrs.get("value")),attrs.get("style"),"",formItem.getItemId(),title);
		}
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun, TeeFormItem formItem,
			TeeFlowFormData flowFormData, Map<String, String> datas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
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
		int numberId = TeeStringUtil.getInteger(attrs.get("numberid"), 0);
		TeeFlowRun flowRun = flowRunPrcs.getFlowRun();
		
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
		
		
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		
		if(!writable){
			attrs.put("disabled", "disabled");
		}
		
		if(writable){//可写
			//自动赋值
			if(!auto && "".equals(data)){
                attrs.put("value", data);
				
				String html="<input id=\"DATA_"+formItem.getItemId()+"\" title=\"自动编号控件\" writable="+writable+" xtype=\"xautonumber\" name=\"DATA_"+formItem.getItemId()+"\" value=\""+TeeStringUtil.getString(attrs.get("value"))+"\"  numberid="+numberId+" required="+required+"/>";
			    html+="<input type=\"button\" value=\"生成编号\" class=\"btn-win-white\" onclick=\"generateAutoNum("+formItem.getItemId()+","+numberId+",'workflowRun',"+String.valueOf(flowRun.getRunId())+")\" />";
			    
			    return html;
			}else{  
			    //如果自动编号id!=0，则自动进行编号递增
				 if(numberId!=0){
					TeeCusNumberService cusNumberService = getContext().getCusNumberService();
					data = cusNumberService.generateCustomNumber(numberId, TeeCustomNumberModelKeys.workflowRun, String.valueOf(flowRun.getRunId()));
				 }
				 attrs.put("value", data);
				 return wrap(writable,tag.toString(),TeeStringUtil.getString(attrs.get("value")),"","",formItem.getItemId(),title);
			}
			
		}else{//不可写
			attrs.put("value", data);
			return wrap(writable,tag.toString(),TeeStringUtil.getString(attrs.get("value")),"","",formItem.getItemId(),title);
		}
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
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
