package com.tianee.oa.util.workflow.ctrl;

import java.util.Map;

import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;

public class TeeXCalculateCtrl extends TeeCtrl{

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		formItem.setColumnType(TeeColumnType.DECIMAL);
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
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取模型
		String model = formItem.getModel();
		String name = formItem.getName();
		String title = formItem.getTitle();
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
		String formula = modelObj.get("formula");//公式
			
		
		return "<input type=\"text\" writable " +
				"value=\"\" " +
				"readonly " +
				"formula=\""+formula+"\" " +
				"model=\""+model+"\" " +
				"name=\""+name+"\" " +
				"title=\""+title+"\""+
				"id=\""+name+"\" style=\""+tag.getAttributes().get("style")+"\"/>";
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,TeeFlowRun flowRun,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
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
		String data = flowFormData.getData();
		
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
		
		//获取模型
		String model = formItem.getModel();
		
		Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
		String precision = modelObj.get("precision");//精度（小数点后x位）
		String valuetype = modelObj.get("valuetype");//值类型1：整型2：小数
		String format = modelObj.get("format");//数据格式1：四舍五入2：结果加1
		String formula = modelObj.get("formula");//公式
			
		
		return "<input type=\"text\" " +
				"value=\""+(data==null?"":data)+"\" " +
				"readonly " +
				"formula=\""+formula+"\" " +
				"model=\""+model+"\" " +
				"name=\""+name+"\" " +
				"title=\""+title+"\""+
				"id=\""+name+"\" " +
				""+(writable?"writable":"class=\"readonly\"")+" style=\""+attrs.get("style")+"\"/>";
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		String name = formItem.getName();
		datas.put(name, "");
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
		String data = flowFormData.getData();
		
		boolean writable = true;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		
		//获取模型
		String model = formItem.getModel();
		
		Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
		String precision = modelObj.get("precision");//精度（小数点后x位）
		String valuetype = modelObj.get("valuetype");//值类型1：整型2：小数
		String format = modelObj.get("format");//数据格式1：四舍五入2：结果加1
		String formula = modelObj.get("formula");//公式
			
		
		return "<input type=\"text\" " +
				"value=\""+(data==null?"":data)+"\" " +
				"readonly " +
				"formula=\""+formula+"\" " +
				"model=\""+model+"\" " +
				"name=\""+name+"\" " +
				"title=\""+title+"\""+
				"id=\""+name+"\" " +
				""+(writable?"writable":"class=\"readonly\"")+"/>";
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
		String data = flowFormData.getData();
		
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
		
		//获取模型
		String model = formItem.getModel();
		
		Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
		String precision = modelObj.get("precision");//精度（小数点后x位）
		String valuetype = modelObj.get("valuetype");//值类型1：整型2：小数
		String format = modelObj.get("format");//数据格式1：四舍五入2：结果加1
		String formula = modelObj.get("formula");//公式
			
		
		return "<input type=\"text\" " +
				"value=\""+(data==null?"":data)+"\" " +
				"readonly " +
				"formula=\""+formula+"\" " +
				"model=\""+model+"\" " +
				"name=\""+name+"\" " +
				"title=\""+title+"\""+
				"id=\""+name+"\" " +
				""+(writable?"writable":"class=\"readonly\"")+" />";
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
