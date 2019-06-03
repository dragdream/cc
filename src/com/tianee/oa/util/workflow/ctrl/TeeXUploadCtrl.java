package com.tianee.oa.util.workflow.ctrl;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXUploadCtrl extends TeeCtrl{

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getCtrlColumnTypeName(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
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
		
		StringBuffer attaches = new StringBuffer("");
		//获取附件
		List<TeeAttachmentModel> attachList = 
				this.getContext().getAttachService().getAttacheModels(TeeAttachmentModelKeys.workFlowUploadCtrl, String.valueOf(formItem.getItemId()+"_"+flowRun.getRunId()));
		
		for(int i=0;i<attachList.size();i++){
			TeeAttachmentModel model = attachList.get(i);
			attaches.append("<p style='margin-bottom:5px'><a class='upload_ctrl_item' attach_id='"+model.getSid()+"' attach_name='"+model.getAttachmentName()+"' file_name='"+model.getFileName()+"' ext='"+model.getExt()+"' style='cursor:pointer' onclick=\"PrintShowFile(this)\" target='_blank'>"+model.getFileName()+"</a></p>");
		}
		
		return attaches.toString();
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
		
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRunPrcs.getFlowRun(),flowRunPrcs, formItem);
		//获取附件控制模型
		int priv=0;
		if(flowRunPrcs.getFlowPrcs()!=null){
			String attachCtrlModel=flowRunPrcs.getFlowPrcs().getAttachCtrlModel();
			List<Map<String, String>> mapList=TeeJsonUtil.JsonStr2MapList(attachCtrlModel);
			for (Map<String, String> map : mapList) {
				if(formItem.getItemId()==TeeStringUtil.getInteger(map.get("itemId"), 0)){
					priv=TeeStringUtil.getInteger(map.get("priv"), 0);
					break;
				}
			}
			
		}else{
			String attachCtrlModel=flowRunPrcs.getAttachCtrlModel();
			List<Map<String, String>> mapList=TeeJsonUtil.JsonStr2MapList(attachCtrlModel);
			for (Map<String, String> map : mapList) {
				if(formItem.getItemId()==TeeStringUtil.getInteger(map.get("itemId"), 0)){
					priv=TeeStringUtil.getInteger(map.get("priv"), 0);
					break;
				}
			}
		}
		
		
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
			priv=1+2+4+8+16;
		}
		
		//附加条件，如果当前为经办人的话，则没有权限动表单
		if(flowRunPrcs.getTopFlag()==0){
			writable = false;
			required = false;
		}
		
		if(hidden){
			return "";
		}
		
		
		StringBuffer attaches = new StringBuffer("");
		//获取附件
		List<TeeAttachmentModel> attachList = 
				this.getContext().getAttachService().getAttacheModels(TeeAttachmentModelKeys.workFlowUploadCtrl, String.valueOf(formItem.getItemId()+"_"+flowRunPrcs.getFlowRun().getRunId()));
		
		for(int i=0;i<attachList.size();i++){
			TeeAttachmentModel model = attachList.get(i);
			attaches.append(model.getSid()+"^"+model.getFileName()+"^"+model.getSizeDesc()+"^"+model.getExt());
			if(i!=attachList.size()-1){
				attaches.append("|");
			}
		}
		
		return "<div id=\"xuploadDiv"+formItem.getItemId()+"\"></div><input type=\"hidden\" "+(writable?"writable='writable'":"")+" xtype=\"xupload\" id=\""+name+"\" name=\""+name+"\" value=\""+attaches.toString()+"\"  priv="+priv+" "+(writable?"writable":"")+" title=\""+title+"\">";
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun, TeeFormItem formItem,
			TeeFlowFormData flowFormData, Map<String, String> datas) {
		// TODO Auto-generated method stub
		return null;
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
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRunPrcs.getFlowRun(),flowRunPrcs, formItem);
		//获取附件控制模型
		int priv=0;
		if(flowRunPrcs.getFlowPrcs()!=null){
			String attachCtrlModel=flowRunPrcs.getFlowPrcs().getAttachCtrlModel();
			List<Map<String, String>> mapList=TeeJsonUtil.JsonStr2MapList(attachCtrlModel);
			for (Map<String, String> map : mapList) {
				if(formItem.getItemId()==TeeStringUtil.getInteger(map.get("itemId"), 0)){
					priv=TeeStringUtil.getInteger(map.get("priv"), 0);
					break;
				}
			}
			
		}else{
			String attachCtrlModel=flowRunPrcs.getAttachCtrlModel();
			List<Map<String, String>> mapList=TeeJsonUtil.JsonStr2MapList(attachCtrlModel);
			for (Map<String, String> map : mapList) {
				if(formItem.getItemId()==TeeStringUtil.getInteger(map.get("itemId"), 0)){
					priv=TeeStringUtil.getInteger(map.get("priv"), 0);
					break;
				}
			}
		}
		
		
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
			priv=1+2+4+8+16;
		}
		
		//附加条件，如果当前为经办人的话，则没有权限动表单
		if(flowRunPrcs.getTopFlag()==0){
			writable = false;
			required = false;
		}
		
		if(hidden){
			return "";
		}
		
		
		StringBuffer attaches = new StringBuffer("");
		//获取附件
		List<TeeAttachmentModel> attachList = 
				this.getContext().getAttachService().getAttacheModels(TeeAttachmentModelKeys.workFlowUploadCtrl, String.valueOf(formItem.getItemId()+"_"+flowRunPrcs.getFlowRun().getRunId()));
		
		for(int i=0;i<attachList.size();i++){
			TeeAttachmentModel model = attachList.get(i);
			attaches.append(model.getSid()+"^"+model.getFileName()+"^"+model.getSizeDesc()+"^"+model.getExt());
			if(i!=attachList.size()-1){
				attaches.append("|");
			}
		}
		
		return "<div id=\"xuploadDiv"+formItem.getItemId()+"\"></div><input type=\"hidden\" "+(writable?"writable='writable'":"")+" xtype=\"xupload\" id=\""+name+"\" name=\""+name+"\" value=\""+attaches.toString()+"\"  priv="+priv+" >";
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
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
		
		StringBuffer attaches = new StringBuffer("");
		//获取附件
		List<TeeAttachmentModel> attachList = 
				this.getContext().getAttachService().getAttacheModels(TeeAttachmentModelKeys.workFlowUploadCtrl, String.valueOf(formItem.getItemId()+"_"+flowRun.getRunId()));
		
		for(int i=0;i<attachList.size();i++){
			TeeAttachmentModel model = attachList.get(i);
			attaches.append("<p style='margin-bottom:5px'><a class='upload_ctrl_item' attach_id='"+model.getSid()+"' attach_name='"+model.getAttachmentName()+"' file_name='"+model.getFileName()+"' ext='"+model.getExt()+"' style='cursor:pointer' onclick=\"PrintShowFile(this)\" target='_blank'>"+model.getFileName()+"</a></p>");
		}
		
		return attaches.toString();
	}

}
