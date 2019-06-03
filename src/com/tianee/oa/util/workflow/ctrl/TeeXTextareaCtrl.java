package com.tianee.oa.util.workflow.ctrl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTextareaTag;

public class TeeXTextareaCtrl extends TeeCtrl{

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		formItem.setColumnType(TeeColumnType.TEXT);
		return formItem.getColumnType();
	}

	@Override
	public String getCtrlColumnTypeName(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return TeeColumnType.getColumnType(getCtrlColumnType(formItem));
	}

	@Override
	public String getCtrlHtml4Design(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		TeeHTMLTextareaTag tag = new TeeHTMLTextareaTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		String richScript = null;
		if("1".equals(attrs.get("rich"))){//富文本格式
			richScript = "<script>"
					+ "window."+formItem.getName()+"_CK = UE.getEditor('"+formItem.getName()+"',{width : '100%',height:$('#"+formItem.getName()+"').height()});"
					+ "$('#"+formItem.getName()+"').attr('ck','1');"
					+ "</script>";
		}else{//普通文本格式
			richScript = "";
		}
		
		return wrap(true,tag.toString()+richScript,TeeStringUtil.getString(""),attrs.get("style"),"",formItem.getItemId(),formItem.getTitle());
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,TeeFlowRun flowRun,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRun,flowRunPrcs, formItem);
		TeeHTMLTextareaTag tag = new TeeHTMLTextareaTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		boolean hidden = false;//可见
		if(ctrl!=null){
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		if(hidden){
			return "";
		}
		
		if(flowFormData.getData()==null){
			flowFormData.setData("");
		}
		
		String data = TeeStringUtil.getString(flowFormData.getData());
		if("1".equals(attrs.get("rich"))){
			return "<span style=\""+attrs.get("style")+"\">"+data+"</span>";
		}else{
			return "<span style=\""+attrs.get("style")+"\">"+data.replace("\n", "<br/>").replace(" ", "&nbsp;")+"</span>"+"<input class=\"FORM_PRINT\" type=\"hidden\" value=\""+data+"\" title=\""+formItem.getTitle()+"\" />";
		}
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs,Map<String,String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTextareaTag tag = new TeeHTMLTextareaTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType,flowRunPrcs.getFlowRun(), flowRunPrcs, formItem);
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
		
		//自动赋值
		if(auto && "".equals(data)){
			
		}else{
			tag.setContent(data);
		}
		
		if(!writable){
			tag.getAttributes().put("disabled", "disabled");
		}else{
			attrs.put("ondblclick", "quickFillData(this)");
			attrs.put("ontouchstart", "quickFillData(this)");
			tag.getAttributes().put("writable", writable+"");
		}
		tag.getAttributes().put("required", String.valueOf(required));
		
		String richScript = null;
		if("1".equals(attrs.get("rich"))){//富文本格式
			richScript = "<script>"
					+ "window."+formItem.getName()+"_CK = UE.getEditor('"+formItem.getName()+"',{width : '100%',height:$('#"+formItem.getName()+"').height()});"
					+ "$('#"+formItem.getName()+"').attr('ck','1');"
					+ "</script>";
		}else{//普通文本格式
			richScript = "";
//			data = TeeStringUtil.getString(data).replace("\n", "<br/>");
		}
		
		return wrap(writable,tag.toString()+richScript,data.replace("\n", "<br/>").replace(" ", "&nbsp;"),attrs.get("style"),"",formItem.getItemId(),formItem.getTitle());
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
		TeeHTMLTextareaTag tag = new TeeHTMLTextareaTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		boolean writable = true;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		//自动赋值
		if(auto && "".equals(data)){
			
		}else{
			tag.setContent(data);
		}
		
		if(!writable){
			tag.getAttributes().put("disabled", "disabled");
		}
		tag.getAttributes().put("required", String.valueOf(required));
		
		String richScript = null;
		if("1".equals(attrs.get("rich"))){//富文本格式
			richScript = "<script>"
					+ formItem.getName()+"_CK = CKEDITOR.replace('"+formItem.getName()+"',{width : 'auto',height:$('#"+formItem.getName()+"').height()});"
					+ "$('#"+formItem.getName()+"').attr('ck','1');"
					+ "</script>";
		}else{//普通文本格式
			richScript = "";
		}
		
		return tag.toString()+richScript;
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTextareaTag tag = new TeeHTMLTextareaTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType,flowRunPrcs.getFlowRun(), flowRunPrcs, formItem);
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
		
		//自动赋值
		if(auto && "".equals(data)){
			
		}else{
			tag.setContent(data);
		}
		
		if(!writable){
			tag.getAttributes().put("disabled", "disabled");
		}else{
			attrs.put("ondblclick", "quickFillData(this)");
			attrs.put("ontouchstart", "quickFillData(this)");
			tag.getAttributes().put("writable", writable+"");
		}
		tag.getAttributes().put("required", String.valueOf(required));
		
		String richScript = null;
		if("1".equals(attrs.get("rich"))){//富文本格式
			richScript = "<script>"
					+ "window."+formItem.getName()+"_CK = UE.getEditor('"+formItem.getName()+"',{width : '100%',height:$('#"+formItem.getName()+"').height()});"
					+ "$('#"+formItem.getName()+"').attr('ck','1');"
					+ "</script>";
		}else{//普通文本格式
			richScript = "";
//			data = TeeStringUtil.getString(data).replace("\n", "<br/>");
		}
		return wrap(writable,tag.toString()+richScript,data.replace("\n", "<br/>").replace(" ", "&nbsp;"),"","",formItem.getItemId(),formItem.getTitle());
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
	// TODO Auto-generated method stub
		
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRun,flowRunPrcs, formItem);
		TeeHTMLTextareaTag tag = new TeeHTMLTextareaTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		boolean hidden = false;//可见
		if(ctrl!=null){
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		if(hidden){
			return "";
		}
		
		if(flowFormData.getData()==null){
			flowFormData.setData("");
		}
		
		String data = TeeStringUtil.getString(flowFormData.getData());
		if("1".equals(attrs.get("rich"))){
			return "<span style=\""+attrs.get("style")+"\">"+data+"</span>";
		}else{
			return "<span style=\""+attrs.get("style")+"\">"+data.replace("\n", "<br/>").replace(" ", "&nbsp;")+"</span>"+"<input class=\"FORM_PRINT\" type=\"hidden\" value=\""+data+"\" title=\""+formItem.getTitle()+"\" />";
		}
	}

	
}
