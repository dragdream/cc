package com.tianee.oa.util.workflow.ctrl;

import java.util.Map;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXMobileHandSealCtrl extends TeeCtrl{

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		formItem.setColumnType(TeeColumnType.VARCHAR);
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
		return formItem.getContent();
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,TeeFlowRun flowRun,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
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
		
		
		
		//获取签章数据
		String data = flowFormData.getData();
		data = data==null?"":data;
		data = data.replace("\n", "");
		
		String float_ = attrs.get("float");
		
		
		sb.append("<span id=\"MOBILE_HW_POS_DATA_"+formItem.getItemId()+"\" style='"+("0".equals(float_)?"":"position:absolute")+"'><img onerror=\"this.style.display = 'none';\"  src=\""+data+"\" /></span>");
//		sb.append("<script>mobileHwArray.push(\""+formItem.getName()+"\");</script>");
//		sb.append("<script>mobileHwStores.push(\""+data+"\");</script>");
		
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
		
		
		String float_ = attrs.get("float");
		
		StringBuffer sb = new StringBuffer();
		//获取签章数据
		sb.append("<script>mobileHwArray.push(\""+formItem.getName()+"\");</script>");
		sb.append("<script>mobileHwStores.push(\""+data+"\");</script>");
		sb.append("<span id=\"MOBILE_HW_POS_DATA_"+formItem.getItemId()+"\" style='position:absolute'></span>");
		sb.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"addMobileHandSeal('"+name+"')\" class=\"btn btn-default\" clazz='MOBILE' value=\"手写\" />");
		sb.append("<input title=\""+title+"\" h=\""+attrs.get("h")+"\" w=\""+attrs.get("w")+"\" type=\"hidden\" float=\""+float_+"\"  name=\""+name+"\" id=\""+name+"\" value=\""+data+"\" "+(required?"required":"")+" "+(writable?"writable":"")+" />");
		
		return sb.toString();
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun, TeeFormItem formItem,
			TeeFlowFormData flowFormData, Map<String, String> datas) {
		// TODO Auto-generated method stub
//		//表单控件分析
//		TeeHTMLTag tag = new TeeHTMLInputTag();
//		tag.analyse(formItem.getContent());
//		//获取控件属性
//		Map<String,String> attrs = tag.getAttributes();
//		
//		TeeJsonUtil jsonUtil = new TeeJsonUtil();
//		String model = attrs.get("model");
//		
//		String name = formItem.getName();
//		int itemId = formItem.getItemId();
//		String title = formItem.getTitle();
//		String data = flowFormData.getData();
//		
//		boolean writable = true;//可写
//		boolean required = false;//必填
//		boolean hidden = false;//隐藏
//		
//		
//		//转换json模型
//		Map<String,String> jsonModel = jsonUtil.JsonStr2Map(model);
//		int sealType = TeeStringUtil.getInteger(jsonModel.get("sealType"), 0);
//		String validField = jsonModel.get("validField");
//		
		StringBuffer sb = new StringBuffer();
//		sb.append("<div id=\"SIGN_POS_DATA_"+itemId+"\"></div>");
//		if((sealType & 1)==1){
//			sb.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"addSeal('"+name+"')\" class=\"btn btn-default\" value=\"盖章\">");
//		}
//		if((sealType & 2)==2){
//			sb.append("&nbsp;<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"handWrite('"+name+"','0x0000FF')\" class=\"btn btn-default\" value=\"手写\">");
//		}
//		sb.append("<input title=\""+title+"\" type=\"hidden\" name=\""+name+"\" id=\""+name+"\" value=\""+data+"\" "+(required?"required=true":"")+" "+(writable?"writable":"disabled")+" />");
//		
//		sb.append("<script>");
//		//如果有签章值的话，进行验章
//		
//			StringBuffer fieldString = new StringBuffer();
//			List<TeeFormItem> items = flowRun.getForm().getFormItems();
//			TeeFormItem tmp = null;
//			String sp[] = TeeStringUtil.parseStringArray(validField);
//			for(int i=0;i<sp.length;i++){
//				tmp = TeeFormItem.getItemByTitle(items,sp[i]);
//				if(tmp==null){
//					continue;
//				}
//				fieldString.append(tmp.getName());
//				if(i!=sp.length-1){
//					fieldString.append(",");
//				}
//			}
//			sb.append("signJson[\""+name+"\"]=\""+fieldString+"\";");
//			sb.append("signArray.push(\""+name+"\");");
//		
//		sb.append("</script>");
		return sb.toString();
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
		
		
		String float_ = attrs.get("float");
		
		StringBuffer sb = new StringBuffer();
		//获取签章数据
		sb.append("<script>mobileHwArray.push(\""+formItem.getName()+"\");</script>");
		sb.append("<script>mobileHwStores.push(\""+data+"\");</script>");
		sb.append("<span id=\"MOBILE_HW_POS_DATA_"+formItem.getItemId()+"\" style='position:absolute'></span>");
		sb.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"addMobileHandSeal('"+name+"')\" class=\"btn btn-default\" clazz='MOBILE' value=\"手写\" />");
		sb.append("<input title=\""+title+"\" h=\""+attrs.get("h")+"\" w=\""+attrs.get("w")+"\" type=\"hidden\" float=\""+float_+"\"  name=\""+name+"\" id=\""+name+"\" value=\""+data+"\" "+(required?"required":"")+" "+(writable?"writable":"")+" />");
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
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
		
		
		
		//获取签章数据
		String data = flowFormData.getData();
		data = data==null?"":data;
		data = data.replace("\n", "");
		
		String float_ = attrs.get("float");
		
		
		sb.append("<span id=\"MOBILE_HW_POS_DATA_"+formItem.getItemId()+"\" style='"+("0".equals(float_)?"":"position:absolute")+"'><img onerror=\"this.style.display = 'none';\"  src=\""+data+"\" /></span>");
//		sb.append("<script>mobileHwArray.push(\""+formItem.getName()+"\");</script>");
//		sb.append("<script>mobileHwStores.push(\""+data+"\");</script>");
		
		return sb.toString();
	}
	
}
