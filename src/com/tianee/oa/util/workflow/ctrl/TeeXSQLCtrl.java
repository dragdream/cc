package com.tianee.oa.util.workflow.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.subsys.bisengin.service.BisViewService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;

public class TeeXSQLCtrl extends TeeCtrl{

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
		//表单控件分析
		TeeHTMLInputTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		String dfid = attrs.get("dfid");
		String style = attrs.get("style");
		String show = attrs.get("show");
		
		StringBuffer sb = new StringBuffer();
		
		List<String> list = execSql(dfid, new HashMap());
		
		if("1".equals(show)){
			sb.append("<input id=\""+formItem.getName()+"\" name=\""+formItem.getName()+"\" style=\""+style+"\"");
			if(list.size()!=0){
				sb.append(" value=\""+list.get(0)+"\"");
			}
			sb.append("/>");
		}else{
			sb.append("<select id=\""+formItem.getName()+"\" name=\""+formItem.getName()+"\" style=\""+style+"\">");
			for(String d:list){
				sb.append("<option value=\""+d+"\">"+d+"</option>");
			}
			sb.append("</select>");
		}
		
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLInputTag tag = new TeeHTMLInputTag();
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
		TeeHTMLInputTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		boolean readonly =false;
		
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
			if("1".equals(ctrl.get("readonly"))){
				readonly = true;
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
			readonly = false;
		}
		
		if(hidden){
			return "";
		}
		
		String dfid = attrs.get("dfid");
		String style = attrs.get("style");
		String show = attrs.get("show");
		
		StringBuffer sb = new StringBuffer();
		
		List<String> list = execSql(dfid, new HashMap());
		
		if("1".equals(show)){
			sb.append("<input "+(writable?"writable":"")+" "+(readonly?"readonly":"")+" id=\""+formItem.getName()+"\" name=\""+formItem.getName()+"\" style=\""+style+"\"");
			if(list.size()!=0){
				if(TeeUtility.isNullorEmpty(data)){
					sb.append(" value=\""+list.get(0)+"\"");
				}else{
					sb.append(" value=\""+data+"\"");
				}
				
			}
			sb.append("/>");
		}else{
			sb.append("<select "+(writable?"writable":"")+" "+(readonly?"readonly":"")+" id=\""+formItem.getName()+"\" name=\""+formItem.getName()+"\" style=\""+style+"\">");
			for(String d:list){
				sb.append("<option "+(data.equals(d)?"selected":"")+" value=\""+d+"\">"+d+"</option>");
			}
			sb.append("</select>");
		}
		
		return wrap(writable,sb.toString(),data,attrs.get("style"),"",formItem.getItemId(),formItem.getTitle());
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
	
	
	/**
	 * 执行SQL语句
	 * @param dfid
	 * @param params
	 * @return
	 */
	private List<String> execSql(String dfid,Map<String, String> params){
		List<String> list = new ArrayList();
		try{
			BisViewService bisViewService = (BisViewService) TeeBeanFactory.getBean("bisViewService");
			params.put("dfid", dfid);
			TeeDataGridModel dm = new TeeDataGridModel();
			dm.setPage(1);
			dm.setRows(99999);
			TeeEasyuiDataGridJson dataGridJson = bisViewService.dflist(dm, params);
			List<Map<String,Object>> ls = dataGridJson.getRows();
			Set<String> keys;
			for(Map<String,Object> l:ls){
				keys = l.keySet();
				for(String key:keys){
					list.add(TeeStringUtil.getString(l.get(key)));
					break;
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return list;
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLInputTag tag = new TeeHTMLInputTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		boolean readonly =false;
		
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
			if("1".equals(ctrl.get("readonly"))){
				readonly = true;
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
			readonly = false;
		}
		
		if(hidden){
			return "";
		}
		
		String dfid = attrs.get("dfid");
		String style = attrs.get("style");
		String show = attrs.get("show");
		
		StringBuffer sb = new StringBuffer();
		
		List<String> list = execSql(dfid, new HashMap());
		
		if("1".equals(show)){
			sb.append("<input "+(writable?"writable":"")+" "+(readonly?"readonly":"")+" id=\""+formItem.getName()+"\" name=\""+formItem.getName()+"\" style=\""+style+"\"");
			if(list.size()!=0){
				if(TeeUtility.isNullorEmpty(data)){
					sb.append(" value=\""+list.get(0)+"\"");
				}else{
					sb.append(" value=\""+data+"\"");
				}
				
			}
			sb.append("/>");
		}else{
			sb.append("<select "+(writable?"writable":"")+" "+(readonly?"readonly":"")+" id=\""+formItem.getName()+"\" name=\""+formItem.getName()+"\" style=\"\">");
			for(String d:list){
				sb.append("<option "+(data.equals(d)?"selected":"")+" value=\""+d+"\">"+d+"</option>");
			}
			sb.append("</select>");
		}
		
		return wrap(writable,sb.toString(),data,"","",formItem.getItemId(),formItem.getTitle());
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLInputTag tag = new TeeHTMLInputTag();
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
