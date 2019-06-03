package com.tianee.oa.util.workflow.ctrl;

import java.util.Map;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXDataSelCtrl extends TeeCtrl {

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
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = "";
		
		boolean writable = true;//可写
		boolean required = true;//必填
		
		attrs.put("dftarget", formItem.getName());
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		attrs.put("onclick", "selectDataSource('"+attrs.get("dfid")+"','"+formItem.getName()+"')");
		if(!writable){
			attrs.put("disabled", "disabled");
		}
		attrs.remove("name");
		attrs.remove("id");
		attrs.remove("title");
		
		if(!"".equals(data)){
			
		}
		
		return wrap(writable,tag.toString()+"<input value=\""+data+"\" type=\"hidden\" name=\""+formItem.getName()+"\" id=\""+formItem.getName()+"\" title=\""+formItem.getTitle()+"\"/>","",attrs.get("style"),"",formItem.getItemId(),formItem.getTitle());
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
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
		}
		
		//如果为自由流程并且是第一步发起时，将所有字段设置为可写
		if(flowType.getType()==2 && flowRunPrcs.getPrcsId()==1){
			writable = true;
		}
		
		//附加条件，如果当前为经办人的话，则没有权限动表单
		if(flowRunPrcs.getTopFlag()==0){
			writable = false;
			required = false;
		}
		
		if(hidden){
			return "";
		}
		
		attrs.put("dftarget", formItem.getName());
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		attrs.put("onclick", "selectDataSource('"+attrs.get("dfid")+"','"+formItem.getName()+"','"+formItem.getTitle()+"')");
		if(!writable){
			attrs.put("disabled", "disabled");
		}
		attrs.remove("name");
		attrs.remove("id");
		attrs.remove("title");
		
		if(!"".equals(data)){
			
		}
		
		return wrap(writable,tag.toString()+"<input value=\""+data+"\" type=\"hidden\" name=\""+formItem.getName()+"\" id=\""+formItem.getName()+"\" title=\""+formItem.getTitle()+"\"/>","",attrs.get("style"),"",formItem.getItemId(),title);
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun, TeeFormItem formItem,
			TeeFlowFormData flowFormData, Map<String, String> datas) {
		// TODO Auto-generated method stub
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
		
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		attrs.put("onclick", "selectDataSource('"+attrs.get("dfid")+"','"+formItem.getName()+"')");
		if(!writable){
			attrs.put("disabled", "disabled");
		}
		attrs.remove("name");
		attrs.remove("id");
		attrs.remove("title");
		
		if(!"".equals(data)){
			
		}
		
		return tag.toString()+"<input value=\""+data+"\" type=\"hidden\" name=\""+formItem.getName()+"\" id=\""+formItem.getName()+"\" title=\""+formItem.getTitle()+"\"/>";
	
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
	// TODO Auto-generated method stub
		
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
		}
		
		//如果为自由流程并且是第一步发起时，将所有字段设置为可写
		if(flowType.getType()==2 && flowRunPrcs.getPrcsId()==1){
			writable = true;
		}
		
		//附加条件，如果当前为经办人的话，则没有权限动表单
		if(flowRunPrcs.getTopFlag()==0){
			writable = false;
			required = false;
		}
		
		if(hidden){
			return "";
		}
		
		attrs.put("dftarget", formItem.getName());
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		attrs.put("onclick", "selectDataSource('"+attrs.get("dfid")+"','"+formItem.getName()+"','"+formItem.getTitle()+"')");
		if(!writable){
			attrs.put("disabled", "disabled");
		}
		attrs.remove("name");
		attrs.remove("id");
		attrs.remove("title");
		
		if(!"".equals(data)){
			
		}
		
		return wrap(writable,tag.toString()+"<input value=\""+data+"\" type=\"hidden\" name=\""+formItem.getName()+"\" id=\""+formItem.getName()+"\" title=\""+formItem.getTitle()+"\"/>","","","",formItem.getItemId(),title);
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		return "";
	}

}
