package com.tianee.oa.util.workflow.ctrl;

import java.util.Map;

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

public class TeeXPositionCtrl extends TeeCtrl{

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
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		StringBuffer html = new StringBuffer();
			html.append("<img src=\"/common/ckeditor/plugins/xposition/imgs/position.png\"  style=\"width:20px;height:20px\"   positiontype=\""+attrs.get("positiontype")+"\"  onclick=\"selectPosition('DATA_"+formItem.getItemId()+"','EXTRA_"+formItem.getItemId()+"','"+attrs.get("positiontype")+"','"+attrs.get("radius")+"')\" />");
			html.append("<span style=\"cursor:pointer\" id=\"POS_DATA_"+formItem.getItemId()+"\" onclick=\"previewPosition('"+"POS_DATA_"+formItem.getItemId()+"')\"></span>");
			html.append("<input  type=\"hidden\" name=\""+"DATA_"+formItem.getItemId()+"\" id=\""+"DATA_"+formItem.getItemId()+"\"  title=\""+attrs.get("title")+"\"   />");
			html.append("<input  type=\"hidden\" name=\""+"EXTRA_"+formItem.getItemId()+"\" id=\""+"EXTRA_"+formItem.getItemId()+"\"  />");
		
		
		return html.toString();
		
		
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
		
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		String extraData=datas.get("EXTRA_"+formItem.getItemId());
		boolean hidden = false;//可见
		if(ctrl!=null){
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		if(hidden){
			return "";
		}
		
		StringBuffer html=new StringBuffer();
		html.append("<span style=\"cursor:pointer;\" id=\"POS_DATA_"+formItem.getItemId()+"\"    onclick=\"previewPosition('"+"POS_DATA_"+formItem.getItemId()+"')\">"+data+"</span>");
		html.append("<input value='"+data+"' type=\"hidden\" name=\""+"DATA_"+formItem.getItemId()+"\" id=\""+"DATA_"+formItem.getItemId()+"\"  title=\""+attrs.get("title")+"\"   />");
		html.append("<input value='"+extraData+"' type=\"hidden\" name=\""+"EXTRA_"+formItem.getItemId()+"\" id=\""+"EXTRA_"+formItem.getItemId()+"\"  />");
		return html.toString();
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		String extraData=datas.get("EXTRA_"+formItem.getItemId());
		
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
		
		
		StringBuffer html = new StringBuffer();
		if(writable){
			html.append("<img src=\"/common/ckeditor/plugins/xposition/imgs/position.png\"  style=\"width:20px;height:20px\" positiontype=\""+attrs.get("positiontype")+"\"  onclick=\"selectPosition('DATA_"+formItem.getItemId()+"','EXTRA_"+formItem.getItemId()+"','"+attrs.get("positiontype")+"','"+attrs.get("radius")+"')\" />");
			html.append("<span style=\"cursor:pointer;\" id=\"POS_DATA_"+formItem.getItemId()+"\"    onclick=\"previewPosition('"+"POS_DATA_"+formItem.getItemId()+"')\">"+data+"</span>");
			html.append("<input value='"+data+"' type=\"hidden\" name=\""+"DATA_"+formItem.getItemId()+"\" id=\""+"DATA_"+formItem.getItemId()+"\"  title=\""+attrs.get("title")+"\"  "+(required?"required=\"required\"":"")+"  />");
			html.append("<input value='"+extraData+"' type=\"hidden\" name=\""+"EXTRA_"+formItem.getItemId()+"\" id=\""+"EXTRA_"+formItem.getItemId()+"\"  />");
		}
		
		return html.toString();
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
		String extraData=datas.get("EXTRA_"+formItem.getItemId());
		
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
		
		
		StringBuffer html = new StringBuffer();
		if(writable){
			html.append("<img src=\"/common/ckeditor/plugins/xposition/imgs/position.png\"  style=\"width:20px;height:20px\" positiontype=\""+attrs.get("positiontype")+"\"  onclick=\"selectPosition('DATA_"+formItem.getItemId()+"','EXTRA_"+formItem.getItemId()+"','"+attrs.get("positiontype")+"','"+attrs.get("radius")+"')\" />");
			html.append("<span style=\"cursor:pointer;\" id=\"POS_DATA_"+formItem.getItemId()+"\"    onclick=\"previewPosition('"+"POS_DATA_"+formItem.getItemId()+"')\">"+data+"</span>");
			html.append("<input value='"+data+"' type=\"hidden\" name=\""+"DATA_"+formItem.getItemId()+"\" id=\""+"DATA_"+formItem.getItemId()+"\"  title=\""+attrs.get("title")+"\"  "+(required?"required=\"required\"":"")+"  />");
			html.append("<input value='"+extraData+"' type=\"hidden\" name=\""+"EXTRA_"+formItem.getItemId()+"\" id=\""+"EXTRA_"+formItem.getItemId()+"\"  />");
		}
		
		return html.toString();
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
		
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		String extraData=datas.get("EXTRA_"+formItem.getItemId());
		boolean hidden = false;//可见
		if(ctrl!=null){
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		if(hidden){
			return "";
		}
		
		StringBuffer html=new StringBuffer();
		html.append("<span style=\"cursor:pointer;\" id=\"POS_DATA_"+formItem.getItemId()+"\"    onclick=\"previewPosition('"+"POS_DATA_"+formItem.getItemId()+"')\">"+data+"</span>");
		html.append("<input value='"+data+"' type=\"hidden\" name=\""+"DATA_"+formItem.getItemId()+"\" id=\""+"DATA_"+formItem.getItemId()+"\"  title=\""+attrs.get("title")+"\"   />");
		html.append("<input value='"+extraData+"' type=\"hidden\" name=\""+"EXTRA_"+formItem.getItemId()+"\" id=\""+"EXTRA_"+formItem.getItemId()+"\"  />");
		return html.toString();
	}

}
