package com.tianee.oa.util.workflow.ctrl;

import java.util.Map;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXH5HwCtrl extends TeeCtrl{

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
			// TODO Auto-generated method stub
			StringBuffer sb = new StringBuffer();
			
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
			
			//表单控件分析
			TeeHTMLTag tag = new TeeHTMLInputTag();
			tag.analyse(formItem.getContent());
			//获取控件属性
			Map<String,String> attrs = tag.getAttributes();
			
			String float_ = attrs.get("float");
			
			//获取签章数据
			String data = flowFormData.getData();
			data = data==null?"":data;
			data = data.replace("\n", "");
			
			sb.append("<div id=\"H5_HW_POS_DATA_"+formItem.getItemId()+"\"  style='"+(("0".equals(float_))?"":"position:absolute")+"' ><img src=\""+data+"\" style=\"width:"+attrs.get("w")+"px;height:"+attrs.get("h")+"px;"+(("0".equals(float_))?"max-width:100%":"")+"\" onerror=\"this.style.display='none'\"/></div>");
//			sb.append("<script>h5HwArray.push(\""+formItem.getName()+"\");</script>");
//			sb.append("<script>h5HwStores.push(\""+data+"\");</script>");
//			sb.append("<a id=\""+formItem.getName()+"\" w='"+attrs.get("w")+"' h='"+attrs.get("h")+"'></a>");
			
			return sb.toString();
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
		
		
		String name = formItem.getName();
		int itemId = formItem.getItemId();
		String title = formItem.getTitle();
		String data = flowFormData.getData();
		data = data==null?"":data;
		data = data.replace("\r\n", "").replace("\n", "");
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//隐藏
		
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
		
		//如果隐藏，则不显示
		if(hidden){
			return "";
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
		
		StringBuffer sb = new StringBuffer();
		//获取签章数据
		sb.append("<div id=\"H5_HW_POS_DATA_"+formItem.getItemId()+"\" style='position:absolute;'></div>");
		sb.append("<script>h5HwArray.push(\""+formItem.getName()+"\");</script>");
		sb.append("<script>h5HwStores.push(\""+data+"\");</script>");
		sb.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"addH5HandSeal('"+name+"')\" class=\"btn btn-default\" value=\"手写\" />");
		sb.append("<input title=\""+title+"\" h=\""+attrs.get("h")+"\" w=\""+attrs.get("w")+"\" weight=\""+attrs.get("weight")+"\" color=\""+attrs.get("color")+"\" float=\""+attrs.get("float")+"\" type=\"hidden\"  name=\""+name+"\" id=\""+name+"\" value=\""+data+"\" "+(required?"required='true'":"required='false'")+" "+(writable?"writable":"")+" />");
		
		return sb.toString();
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
		attrs.put("style","");
		
		String name = formItem.getName();
		int itemId = formItem.getItemId();
		String title = formItem.getTitle();
		String data = flowFormData.getData();
		data = data==null?"":data;
		data = data.replace("\r\n", "").replace("\n", "");
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//隐藏
		
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
		
		//如果隐藏，则不显示
		if(hidden){
			return "";
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
		
		StringBuffer sb = new StringBuffer();
		//获取签章数据
		sb.append("<div id=\"H5_HW_POS_DATA_"+formItem.getItemId()+"\" style='position:absolute;'></div>");
		sb.append("<script>h5HwArray.push(\""+formItem.getName()+"\");</script>");
		sb.append("<script>h5HwStores.push(\""+data+"\");</script>");
		sb.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"addH5HandSeal('"+name+"')\" class=\"btn btn-default\" value=\"手写\" />");
		sb.append("<input title=\""+title+"\" h=\""+attrs.get("h")+"\" w=\""+attrs.get("w")+"\" weight=\""+attrs.get("weight")+"\" color=\""+attrs.get("color")+"\" float=\""+attrs.get("float")+"\" type=\"hidden\"  name=\""+name+"\" id=\""+name+"\" value=\""+data+"\" "+(required?"required='true'":"required='false'")+" "+(writable?"writable":"")+" />");
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
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
		
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		String float_ = attrs.get("float");
		
		//获取签章数据
		String data = flowFormData.getData();
		data = data==null?"":data;
		data = data.replace("\n", "");
		//style='"+(("0".equals(float_))?"":"position:absolute")+"' 
		sb.append("<div id=\"H5_HW_POS_DATA_"+formItem.getItemId()+"\"  ><img src=\""+data+"\" style=\"width:"+attrs.get("w")+"px;height:"+attrs.get("h")+"px;"+(("0".equals(float_))?"max-width:100%":"")+"\" onerror=\"this.style.display='none'\"/></div>");
//		sb.append("<script>h5HwArray.push(\""+formItem.getName()+"\");</script>");
//		sb.append("<script>h5HwStores.push(\""+data+"\");</script>");
//		sb.append("<a id=\""+formItem.getName()+"\" w='"+attrs.get("w")+"' h='"+attrs.get("h")+"'></a>");
		
		return sb.toString();
	}

}
