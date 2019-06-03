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

public class TeeXRadioCtrl extends TeeCtrl{

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
		StringBuffer sb = new StringBuffer();
		String model = formItem.getModel();
		
		TeeJsonUtil json = new TeeJsonUtil();
		//转换数据模型
		List<Map<String,String>> list = json.JsonStr2MapList(model);
		
		String data;
		String _default;
		for(Map<String,String> m:list){
			data = m.get("data");
			_default = m.get("default");
			sb.append("<input " +
					"type=\"radio\" " +
					"value=\""+data+"\" " +
					"name=\""+formItem.getName()+"\" " +
					""+("false".equals(_default)?"":"checked")+" />"+data+"&nbsp;");
		}
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,TeeFlowRun flowRun,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
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
		
		return TeeStringUtil.getString(flowFormData.getData())+"<input class=\"FORM_PRINT\" type=\"hidden\" value=\""+flowFormData.getData()+"\" title=\""+formItem.getTitle()+"\" />";
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs,Map<String,String> _datas) {
		
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
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
		
		//用于控件渲染字符串对象
		StringBuffer sb = new StringBuffer();
		
		//获取控件模型
		String model = formItem.getModel();
		List<Map<String,String>> mList = jsonUtil.JsonStr2MapList(model);
		
		for(Map<String,String> datas:mList){
			sb.append("<input "+(writable?"":"disabled")+" type=\"radio\" name=\""+formItem.getName()+"\" ");
			sb.append(" value=\""+datas.get("data")+"\" ");
			if("".equals(data)){
				if(datas.get("default").equals("true")){
					sb.append(" checked");
				}
			}else{
				if(datas.get("data").equals(data)){
					sb.append(" checked");
				}
			}
			sb.append("/>"+datas.get("data"));
		}
		
		return wrap(writable,sb.toString(),TeeStringUtil.getString(data),attrs.get("style"),"",formItem.getItemId(),formItem.getTitle());
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
		
		String data = flowFormData.getData();
		
		boolean writable = true;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		
		//用于控件渲染字符串对象
		StringBuffer sb = new StringBuffer();
		
		//获取控件模型
		String model = formItem.getModel();
		List<Map<String,String>> mList = jsonUtil.JsonStr2MapList(model);
		
		for(Map<String,String> datas0:mList){
			sb.append("<input "+(writable?"":"disabled")+" type=\"radio\" name=\""+formItem.getName()+"\" ");
			sb.append(" value=\""+datas0.get("data")+"\" ");
			if("".equals(data)){
				if(datas0.get("default").equals("true")){
					sb.append(" checked");
				}
			}else{
				if(datas0.get("data").equals(data)){
					sb.append(" checked");
				}
			}
			sb.append("/>"+datas0.get("data"));
		}
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> _datas) {

		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
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
		
		//用于控件渲染字符串对象
		StringBuffer sb = new StringBuffer();
		
		//获取控件模型
		String model = formItem.getModel();
		List<Map<String,String>> mList = jsonUtil.JsonStr2MapList(model);
		
		for(Map<String,String> datas:mList){
			sb.append("<input "+(writable?"":"disabled")+" type=\"radio\" name=\""+formItem.getName()+"\" ");
			sb.append(" value=\""+datas.get("data")+"\" ");
			if("".equals(data)){
				if(datas.get("default").equals("true")){
					sb.append(" checked");
				}
			}else{
				if(datas.get("data").equals(data)){
					sb.append(" checked");
				}
			}
			sb.append("/>"+datas.get("data"));
		}
		
		return wrap(writable,sb.toString(),TeeStringUtil.getString(data),"","",formItem.getItemId(),formItem.getTitle());
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
		
		return TeeStringUtil.getString(flowFormData.getData())+"<input class=\"FORM_PRINT\" type=\"hidden\" value=\""+flowFormData.getData()+"\" title=\""+formItem.getTitle()+"\" />";
	}

}