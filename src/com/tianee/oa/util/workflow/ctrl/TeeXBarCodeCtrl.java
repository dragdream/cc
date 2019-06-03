package com.tianee.oa.util.workflow.ctrl;

import java.util.List;
import java.util.Map;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.util.str.TeeBarCodeUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXBarCodeCtrl extends TeeCtrl{

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
		TeeHTMLTag tag = new TeeHTMLImgTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		return "<img src=\"/common/ckeditor/plugins/xbarcode/imgs/code.png\" style=\"vertical-align:middle\" />";
		
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLImgTag();
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
		
		String savefield=attrs.get("savefield");
		String format=attrs.get("format");
		String barheight=attrs.get("barheight");
		String dimension=attrs.get("dimension");
		String isShowText=attrs.get("isshowtext");
		
		List<TeeFormItem> formItems=form.getFormItems();
		String strInfo="";
		if(!TeeUtility.isNullorEmpty(savefield)){
			for(int j=0;j<formItems.size();j++){
				if((formItems.get(j).getTitle()).equals(savefield)){
					strInfo+=datas.get(formItems.get(j).getName());
				    break;
				}
			}
			
		}
		String baseStr="";
		try {
			if(!TeeUtility.isNullorEmpty(strInfo)){
				baseStr = genarateCode(format,strInfo,dimension,barheight,isShowText);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!TeeUtility.isNullorEmpty(baseStr)){
			return "<img style=\"vertical-align:middle\"  src=\"data:image/jpeg;base64,"+baseStr+"   \"/>";
		}else{
			return "";
		}
		
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLImgTag();
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
		
		
		//StringBuffer html = new StringBuffer();
		String savefield=attrs.get("savefield");
		String format=attrs.get("format");
		String barheight=attrs.get("barheight");
		String dimension=attrs.get("dimension");
		String isShowText=attrs.get("isshowtext");
		
		List<TeeFormItem> formItems=form.getFormItems();
		String strInfo="";
		if(!TeeUtility.isNullorEmpty(savefield)){
			for(int j=0;j<formItems.size();j++){
				if((formItems.get(j).getTitle()).equals(savefield)){
					strInfo+=datas.get(formItems.get(j).getName());
				    break;
				}
			}
			
		}
		String baseStr="";
		try {
			if(!TeeUtility.isNullorEmpty(strInfo)){
				baseStr = genarateCode(format,strInfo,dimension,barheight,isShowText);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!TeeUtility.isNullorEmpty(baseStr)){
			return "<img  style=\"vertical-align:middle\" src=\"data:image/jpeg;base64,"+baseStr+"   \"/>";
		}else{
			return "";
		}
		
		
	}

	/**
	 * 生成条形码
	 * @param format
	 * @param strInfo
	 * @param dimension
	 * @param barheight
	 * @param isShowText
	 * @return
	 */
	private String genarateCode(String format, String strInfo,
			String dimension, String barheight, String isShowText) {
		boolean isShow=false;
		
		if(TeeStringUtil.getInteger(isShowText,0)==1){
			isShow=true;
		}else{
			isShow=false;
		}
		
		double height=TeeStringUtil.getDouble(barheight,0)*10;
		return TeeBarCodeUtil.generateBarCode128(format, strInfo, dimension, height+"", isShow);
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
				TeeHTMLTag tag = new TeeHTMLImgTag();
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
				
				
				//StringBuffer html = new StringBuffer();
				String savefield=attrs.get("savefield");
				String format=attrs.get("format");
				String barheight=attrs.get("barheight");
				String dimension=attrs.get("dimension");
				String isShowText=attrs.get("isshowtext");
				
				List<TeeFormItem> formItems=form.getFormItems();
				String strInfo="";
				if(!TeeUtility.isNullorEmpty(savefield)){
					for(int j=0;j<formItems.size();j++){
						if((formItems.get(j).getTitle()).equals(savefield)){
							strInfo+=datas.get(formItems.get(j).getName());
						    break;
						}
					}
					
				}
				String baseStr="";
				try {
					if(!TeeUtility.isNullorEmpty(strInfo)){
						baseStr = genarateCode(format,strInfo,dimension,barheight,isShowText);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!TeeUtility.isNullorEmpty(baseStr)){
					return "<img src=\"data:image/jpeg;base64,"+baseStr+"   \"/>";
				}else{
					return "";
				}
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLImgTag();
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
		
		String savefield=attrs.get("savefield");
		String format=attrs.get("format");
		String barheight=attrs.get("barheight");
		String dimension=attrs.get("dimension");
		String isShowText=attrs.get("isshowtext");
		
		List<TeeFormItem> formItems=form.getFormItems();
		String strInfo="";
		if(!TeeUtility.isNullorEmpty(savefield)){
			for(int j=0;j<formItems.size();j++){
				if((formItems.get(j).getTitle()).equals(savefield)){
					strInfo+=datas.get(formItems.get(j).getName());
				    break;
				}
			}
			
		}
		String baseStr="";
		try {
			if(!TeeUtility.isNullorEmpty(strInfo)){
				baseStr = genarateCode(format,strInfo,dimension,barheight,isShowText);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!TeeUtility.isNullorEmpty(baseStr)){
			return "<img style=\"vertical-align:middle\"  src=\"data:image/jpeg;base64,"+baseStr+"   \"/>";
		}else{
			return "";
		}
	}
	
	

}
