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
import com.tianee.oa.util.workflow.TeeWorkflowHelper;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXSealCtrl extends TeeCtrl{

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
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		//获取签章模型
//		Map<String,String> model = jsonUtil.JsonStr2Map(formItem.getModel());
//		String validField[] = TeeStringUtil.parseStringArray(model.get("validField"));
//		String valideData = "";
//		for(int i=0;i<validField.length && !"".equals(validField[0]);i++){
//			//获取该字段id
//			int itemId = 0;
//			String name = "";
//			for(int j=0;j<form.getFormItems().size();j++){
//				if(validField[i].equals(form.getFormItems().get(j).getTitle())){
//					itemId = form.getFormItems().get(j).getItemId();
//					name = form.getFormItems().get(j).getName();
//					break;
//				}
//			}
//			//获取对应字段的数据
//			String data = datas.get(name);
//			valideData+=name+"separator"+data;
//		}
		
		//获取签章数据
		String data = flowFormData.getData();
		String sealData = datas.get("SEAL_"+formItem.getItemId());
//		String hwData = datas.get("HW_"+formItem.getItemId());
		
		if(data==null || "".equals(data)){
			return "";
		}
		
		sb.append("<div id=\"SIGN_POS_DATA_"+formItem.getItemId()+"\" style='position:absolute'>");	
		if(!"".equals(sealData) && !"null".equals(sealData) && sealData!=null){
			String sp[] =  sealData.split("\\|");
			String pos[] = sp[1].split(",");
			String size[] = sp[2].split(",");
			String left = pos[0];
			String top = pos[1];
//			sb.append("<script>sealSignDatas.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
//			sb.append("<script>sealSignPos.push(\""+left+","+top+"\");</script>");
//			sb.append("<script>stores.push(\""+formItem.getName()+"\");</script>");
			sb.append("<img onerror=\"this.style.display = 'none';\"  src=\"data:image/png;base64,"+sp[0]+"\" style=\"position:absolute;left:"+left+";top:"+top+";width:"+size[0]+";height:"+size[1]+"\"/>");
		}
		
//		if(!"".equals(hwData) && !"null".equals(hwData) && hwData!=null){
//			String sp[] =  hwData.split(";");
//			String pos[] = sp[1].split(",");
//			String left = pos[0];
//			String top = pos[1];
////			sb.append("<script>sealSignDatas.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
////			sb.append("<script>sealSignPos.push(\""+left+","+top+"\");</script>");
////			sb.append("<script>stores.push(\""+formItem.getName()+"\");</script>");
//			sb.append("<img onerror=\"this.style.display = 'none';\" src=\"data:image/gif;base64,"+sp[0]+"\" style=\"position:absolute;left:"+left+"px;top:"+top+"px\"/>");
//		}
		sb.append("</div>");
		
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
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		String model = attrs.get("model");
		
		String name = formItem.getName();
		int itemId = formItem.getItemId();
		String title = formItem.getTitle();
		String data = flowFormData.getData();
		String sealData = datas.get("SEAL_"+formItem.getItemId());
//		String hwData = datas.get("HW_"+formItem.getItemId());
		
		
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
		
		//转换json模型
		Map<String,String> jsonModel = jsonUtil.JsonStr2Map(model);
		int sealType = TeeStringUtil.getInteger(jsonModel.get("sealType"), 0);
		String validField = jsonModel.get("validField");
		
		//附加条件，如果当前为经办人的话，则没有权限动表单
		if(flowRunPrcs.getTopFlag()==0){
			writable = false;
			required = false;
		}
		
		if(hidden){
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div id=\"SIGN_POS_DATA_"+itemId+"\" style='position:absolute' >");
		//若有签章数据直接渲染
		if(!"".equals(sealData) && !"null".equals(sealData) && sealData!=null){
			String sp[] =  sealData.split("\\|");
			String pos[] = sp[1].split(",");
			String size[] = sp[2].split(",");
			String left = pos[0];
			String top = pos[1];
//			sb.append("<script>sealSignDatas.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
//			sb.append("<script>sealSignPos.push(\""+left+","+top+"\");</script>");
//			sb.append("<script>stores.push(\""+formItem.getName()+"\");</script>");
			sb.append("<img onerror=\"this.style.display = 'none';\"  src=\"data:image/png;base64,"+sp[0]+"\" style=\"position:absolute;left:0px;top:0;width:"+size[0]+";height:"+size[1]+"\"/>");
		}
		
		sb.append("</div>");
		
		
		if((sealType & 1)==1){
			sb.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"addSeal('"+name+"')\" class=\"btn btn-default\" clazz='PC' value=\"电子签章\">");
		}
		if((sealType & 2)==2){
			sb.append("&nbsp;<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"handWrite('"+name+"','0x0000FF')\" class=\"btn btn-default XsealHandWrite\" clazz='PC' value=\"电子签名\">");
		}
		
		StringBuffer fieldString = new StringBuffer();
		List<TeeFormItem> items = form.getFormItems();
		TeeFormItem tmp = null;
		String sp[] = TeeStringUtil.parseStringArray(validField);
		for(int i=0;i<sp.length;i++){
			tmp = TeeFormItem.getItemByTitle(items,sp[i]);
			if(tmp==null){
				continue;
			}
			fieldString.append(tmp.getName());
			if(i!=sp.length-1){
				fieldString.append(",");
			}
		}
		
		sb.append("<input xtype=\"xseal\" title=\""+title+"\" fieldString=\""+fieldString+"\" type=\"hidden\" name=\""+name+"\" id=\""+name+"\" value=\""+data+"\" "+(required?"required=true":"")+" "+(writable?"writable":"disabled")+" />");
		sb.append("<input type=\"hidden\" name=\"SEAL_"+formItem.getItemId()+"\" id=\"SEAL_"+formItem.getItemId()+"\" value=\""+sealData+"\" "+(required?"required=true":"")+" "+(writable?"writable":"disabled")+" />");
//		sb.append("<input type=\"hidden\" name=\"HW_"+formItem.getItemId()+"\" id=\"HW_"+formItem.getItemId()+"\" value=\""+hwData+"\" "+(required?"required=true":"")+" "+(writable?"writable":"disabled")+" />");
		
//		sb.append("<script>");
		//如果有签章值的话，进行验章
		
//			sb.append("signJson[\""+name+"\"]=\""+fieldString+"\";");
//			sb.append("signArray.push(\""+name+"\");");
		
//		sb.append("</script>");
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
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		String model = attrs.get("model");
		
		String name = formItem.getName();
		int itemId = formItem.getItemId();
		String title = formItem.getTitle();
		String data = flowFormData.getData();
		
		boolean writable = true;//可写
		boolean required = false;//必填
		boolean hidden = false;//隐藏
		
		
		//转换json模型
		Map<String,String> jsonModel = jsonUtil.JsonStr2Map(model);
		int sealType = TeeStringUtil.getInteger(jsonModel.get("sealType"), 0);
		String validField = jsonModel.get("validField");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div id=\"SIGN_POS_DATA_"+itemId+"\"></div>");
		if((sealType & 1)==1){
			sb.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"addSeal('"+name+"')\" class=\"btn btn-default\" value=\"盖章\">");
		}
		if((sealType & 2)==2){
			sb.append("&nbsp;<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"handWrite('"+name+"','0x0000FF')\" class=\"btn btn-default\" value=\"手写\">");
		}
		sb.append("<input title=\""+title+"\" type=\"hidden\" name=\""+name+"\" id=\""+name+"\" value=\""+data+"\" "+(required?"required=true":"")+" "+(writable?"writable":"disabled")+" />");
		
		sb.append("<script>");
		//如果有签章值的话，进行验章
		
			StringBuffer fieldString = new StringBuffer();
			List<TeeFormItem> items = flowRun.getForm().getFormItems();
			TeeFormItem tmp = null;
			String sp[] = TeeStringUtil.parseStringArray(validField);
			for(int i=0;i<sp.length;i++){
				tmp = TeeFormItem.getItemByTitle(items,sp[i]);
				if(tmp==null){
					continue;
				}
				fieldString.append(tmp.getName());
				if(i!=sp.length-1){
					fieldString.append(",");
				}
			}
			sb.append("signJson[\""+name+"\"]=\""+fieldString+"\";");
			sb.append("signArray.push(\""+name+"\");");
		
		sb.append("</script>");
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
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		String model = attrs.get("model");
		
		String name = formItem.getName();
		int itemId = formItem.getItemId();
		String title = formItem.getTitle();
		String data = flowFormData.getData();
		String sealData = datas.get("SEAL_"+formItem.getItemId());
//		String hwData = datas.get("HW_"+formItem.getItemId());
		
		
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
		
		//转换json模型
		Map<String,String> jsonModel = jsonUtil.JsonStr2Map(model);
		int sealType = TeeStringUtil.getInteger(jsonModel.get("sealType"), 0);
		String validField = jsonModel.get("validField");
		
		//附加条件，如果当前为经办人的话，则没有权限动表单
		if(flowRunPrcs.getTopFlag()==0){
			writable = false;
			required = false;
		}
		
		if(hidden){
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div id=\"SIGN_POS_DATA_"+itemId+"\" style='position:absolute' >");
		//若有签章数据直接渲染
		if(!"".equals(sealData) && !"null".equals(sealData) && sealData!=null){
			String sp[] =  sealData.split("\\|");
			String pos[] = sp[1].split(",");
			String size[] = sp[2].split(",");
			String left = pos[0];
			String top = pos[1];
//			sb.append("<script>sealSignDatas.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
//			sb.append("<script>sealSignPos.push(\""+left+","+top+"\");</script>");
//			sb.append("<script>stores.push(\""+formItem.getName()+"\");</script>");
			sb.append("<img onerror=\"this.style.display = 'none';\"  src=\"data:image/png;base64,"+sp[0]+"\" style=\"position:absolute;left:0px;top:0;width:"+size[0]+";height:"+size[1]+"\"/>");
		}
		
		sb.append("</div>");
		
		
		if((sealType & 1)==1){
			sb.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"addSeal('"+name+"')\" class=\"btn btn-default\" value=\"电子签章\">");
		}
		if((sealType & 2)==2){
			sb.append("&nbsp;<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"handWrite('"+name+"','0x0000FF')\" class=\"btn btn-default XsealHandWrite\" value=\"电子签名\">");
		}
		
		StringBuffer fieldString = new StringBuffer();
		List<TeeFormItem> items = form.getFormItems();
		TeeFormItem tmp = null;
		String sp[] = TeeStringUtil.parseStringArray(validField);
		for(int i=0;i<sp.length;i++){
			tmp = TeeFormItem.getItemByTitle(items,sp[i]);
			if(tmp==null){
				continue;
			}
			fieldString.append(tmp.getName());
			if(i!=sp.length-1){
				fieldString.append(",");
			}
		}
		
		sb.append("<input xtype=\"xseal\" title=\""+title+"\" fieldString=\""+fieldString+"\" type=\"hidden\" name=\""+name+"\" id=\""+name+"\" value=\""+data+"\" "+(required?"required=true":"")+" "+(writable?"writable":"disabled")+" />");
		sb.append("<input type=\"hidden\" name=\"SEAL_"+formItem.getItemId()+"\" id=\"SEAL_"+formItem.getItemId()+"\" value=\""+sealData+"\" "+(required?"required=true":"")+" "+(writable?"writable":"disabled")+" />");
//		sb.append("<input type=\"hidden\" name=\"HW_"+formItem.getItemId()+"\" id=\"HW_"+formItem.getItemId()+"\" value=\""+hwData+"\" "+(required?"required=true":"")+" "+(writable?"writable":"disabled")+" />");
		
//		sb.append("<script>");
		//如果有签章值的话，进行验章
		
//			sb.append("signJson[\""+name+"\"]=\""+fieldString+"\";");
//			sb.append("signArray.push(\""+name+"\");");
		
//		sb.append("</script>");
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
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		//获取签章模型
//		Map<String,String> model = jsonUtil.JsonStr2Map(formItem.getModel());
//		String validField[] = TeeStringUtil.parseStringArray(model.get("validField"));
//		String valideData = "";
//		for(int i=0;i<validField.length && !"".equals(validField[0]);i++){
//			//获取该字段id
//			int itemId = 0;
//			String name = "";
//			for(int j=0;j<form.getFormItems().size();j++){
//				if(validField[i].equals(form.getFormItems().get(j).getTitle())){
//					itemId = form.getFormItems().get(j).getItemId();
//					name = form.getFormItems().get(j).getName();
//					break;
//				}
//			}
//			//获取对应字段的数据
//			String data = datas.get(name);
//			valideData+=name+"separator"+data;
//		}
		
		//获取签章数据
		String data = flowFormData.getData();
		String sealData = datas.get("SEAL_"+formItem.getItemId());
//		String hwData = datas.get("HW_"+formItem.getItemId());
		
		if(data==null || "".equals(data)){
			return "";
		}
		
		sb.append("<div id=\"SIGN_POS_DATA_"+formItem.getItemId()+"\" style='position:absolute'>");	
		if(!"".equals(sealData) && !"null".equals(sealData) && sealData!=null){
			String sp[] =  sealData.split("\\|");
			String pos[] = sp[1].split(",");
			String size[] = sp[2].split(",");
			String left = pos[0];
			String top = pos[1];
//			sb.append("<script>sealSignDatas.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
//			sb.append("<script>sealSignPos.push(\""+left+","+top+"\");</script>");
//			sb.append("<script>stores.push(\""+formItem.getName()+"\");</script>");
			sb.append("<img onerror=\"this.style.display = 'none';\"  src=\"data:image/png;base64,"+sp[0]+"\" style=\"position:absolute;left:"+left+";top:"+top+";width:"+size[0]+";height:"+size[1]+"\"/>");
		}
		
//		if(!"".equals(hwData) && !"null".equals(hwData) && hwData!=null){
//			String sp[] =  hwData.split(";");
//			String pos[] = sp[1].split(",");
//			String left = pos[0];
//			String top = pos[1];
////			sb.append("<script>sealSignDatas.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
////			sb.append("<script>sealSignPos.push(\""+left+","+top+"\");</script>");
////			sb.append("<script>stores.push(\""+formItem.getName()+"\");</script>");
//			sb.append("<img onerror=\"this.style.display = 'none';\" src=\"data:image/gif;base64,"+sp[0]+"\" style=\"position:absolute;left:"+left+"px;top:"+top+"px\"/>");
//		}
		sb.append("</div>");
		
		return sb.toString();
	}
	
}
