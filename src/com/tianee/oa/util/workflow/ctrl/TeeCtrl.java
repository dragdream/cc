package com.tianee.oa.util.workflow.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

public abstract class TeeCtrl {
	
	TeeWorkFlowServiceContextInterface context;
	TeePerson loginUser;
	
	/**
	 * 获取该控件的数据列类型名称
	 * @param formItem
	 * @return
	 */
	public abstract String getCtrlColumnTypeName(TeeFormItem formItem);
	
	/**
	 * 获取控件名称数据列类型
	 * @param formItem
	 * @return
	 */
	public abstract int getCtrlColumnType(TeeFormItem formItem);
	
	/**
	 * 获取表单设计预览控件HTML
	 * @param formItem
	 * @return
	 */
	public abstract String getCtrlHtml4Design(TeeFormItem formItem);
	
	/**
	 * 获取表单打印的控件HTML样式
	 * @param formItem
	 * @param flowFormData
	 * @param flowType
	 * @param form
	 * @param flowRunPrcs
	 * @param datas TODO
	 * @return
	 */
	public abstract String getCtrlHtml4Print(TeeFormItem formItem,TeeFlowFormData flowFormData,TeeFlowType flowType,TeeForm form,TeeFlowRun flowRun,TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas);
	
	/**
	 * 获取表单打印的控件HTML样式
	 * @param formItem
	 * @param flowFormData
	 * @param flowType
	 * @param form
	 * @param flowRunPrcs
	 * @param datas TODO
	 * @return
	 */
	public abstract String getCtrlHtml4MobilePrint(TeeFormItem formItem,TeeFlowFormData flowFormData,TeeFlowType flowType,TeeForm form,TeeFlowRun flowRun,TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas);
	
	/**
	 * 获取表单流程过程中的HTML
	 * @param formItem
	 * @param flowFormData
	 * @param flowType
	 * @param form
	 * @param flowRunPrcs
	 * @return
	 */
	public abstract String getCtrlHtml4Process(TeeFormItem formItem,TeeFlowFormData flowFormData,TeeFlowType flowType,TeeForm form,TeeFlowRunPrcs flowRunPrcs,Map<String,String> datas);
	
	/**
	 * 获取表单流程过程中的HTML
	 * @param formItem
	 * @param flowFormData
	 * @param flowType
	 * @param form
	 * @param flowRunPrcs
	 * @return
	 */
	public abstract String getCtrlHtml4MobileProcess(TeeFormItem formItem,TeeFlowFormData flowFormData,TeeFlowType flowType,TeeForm form,TeeFlowRunPrcs flowRunPrcs,Map<String,String> datas);
	
	
	
	/**
	 * 获取表单编辑过程中的HTML
	 * @param flowRun
	 * @return
	 */
	public abstract String getCtrlHtml4Edit(TeeFlowRun flowRun,TeeFormItem formItem,TeeFlowFormData flowFormData,Map<String,String> datas);
	
	/**
	 * 初始化控件字段数据
	 * @param formItem
	 * @param datas
	 */
	public abstract void initFieldData(TeeFormItem formItem,Map<String,String> datas);
	
	/**
	 * 获取控制模型
	 * @param ft
	 * @param flowRunPrcs
	 * @param formItem
	 * @return
	 */
	public Map<String,String> getCtrlModel(TeeFlowType ft,TeeFlowRun flowRun,TeeFlowRunPrcs flowRunPrcs,TeeFormItem formItem){
		String fieldCtrlModel = null;//转交字段控制
		int type = ft.getType();
		List<Map<String,String>> ctrlList = null;
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		//获取字段控制模型
		if(flowRunPrcs!=null){
			if(type==1){//固定流
				fieldCtrlModel = flowRunPrcs.getFlowPrcs().getFieldCtrlModel();
			}else{//自由流
				fieldCtrlModel = flowRunPrcs.getFreeCtrlModel();
			}
			ctrlList = jsonUtil.JsonStr2MapList(fieldCtrlModel);
		}else{
			List<List<Map<String,String>>> ctrls = new ArrayList<List<Map<String,String>>>();
			
			//判断当前流程是否完毕，如果已结束，则字段不受控制
			if(flowRun.getEndTime()!=null){
				return null;
			}
			
			//循环遍历这些节点的ctrlModel，将做一个交集运算，但凡存在一个模型字段有隐藏域的话
			//则不允许显示该控件值。
			if(type==1){//固定流
				//获取所有步骤节点
				List<TeeFlowProcess> flowProcessList = ft.getProcessList();
				for(TeeFlowProcess fp:flowProcessList){
					String tmpCtrlStrModel = fp.getFieldCtrlModel();
					if(tmpCtrlStrModel!=null && !"".equals(tmpCtrlStrModel)){
						ctrls.add(jsonUtil.JsonStr2MapList(tmpCtrlStrModel));
					}
				}
			}else{//自由流
				List<String> list = context.getFlowRunPrcsService().getFreePrcsCtrlModelList(flowRun.getRunId());
				for(String model:list){
					if(!"".equals(model)){
						ctrls.add(jsonUtil.JsonStr2MapList(model));
					}
				}
			}
			
			//初始化
			ctrlList = new ArrayList<Map<String,String>>();
			
			for(List<Map<String,String>> tList :ctrls){
				for(Map<String,String> map:tList){
					//从ctrlList中获取与其对应的属性定义
					boolean exist = false;
					for(Map<String,String> target:ctrlList){
						//如果存在定义的话，则取hidden属性为1的
						if(map.get("itemId")!=null && map.get("itemId").equals(target.get("itemId"))){
							exist = true;
							if("1".equals(map.get("hidden"))){
								target.put("hidden", "1");
							}
							break;
						}
					}
					if(!exist){
						ctrlList.add(map);
					}
				}
			}
			
		}
		
		
		//获取当前字段控制模型
		for(Map<String,String> m:ctrlList){
			if(m.get("itemId").equals(formItem.getItemId()+"")){
				return m;
			}
		}
		return null;
	}
	
	public static  TeeCtrl getInstanceOf(String xtype){
		TeeCtrl ctrl = null;
		
		if("xinput".equals(xtype)){
			ctrl = new TeeXInputCtrl();
		}else if("xradio".equals(xtype)){
			ctrl = new TeeXRadioCtrl();
		}else if("xcalculate".equals(xtype)){
			ctrl = new TeeXCalculateCtrl();
		}else if("xcheckbox".equals(xtype)){
			ctrl = new TeeXCheckboxCtrl();
		}else if("xfetch".equals(xtype)){
			ctrl = new TeeXFetchCtrl();
		}else if("xselect".equals(xtype)){
			ctrl = new TeeXSelectCtrl();
		}else if("xtextarea".equals(xtype)){
			ctrl = new TeeXTextareaCtrl();
		}else if("xlist".equals(xtype)){
			ctrl = new TeeXListCtrl();
		}else if("xmacro".equals(xtype)){
			ctrl = new TeeMacroCtrl();
		}else if("xmacrotag".equals(xtype)){
			ctrl = new TeeMacroTagCtrl();
		}else if("xseal".equals(xtype)){
			ctrl = new TeeXSealCtrl();
		}else if("ximg".equals(xtype)){
			ctrl = new TeeXImgCtrl();
		}else if("xupload".equals(xtype)){
			ctrl = new TeeXUploadCtrl();
		}else if("xdocnum".equals(xtype)){
			ctrl = new TeeXDocNumCtrl();
		}else if("xfeedback".equals(xtype)){
			ctrl = new TeeXFeedbackCtrl();
		}else if("xdatasel".equals(xtype)){
			ctrl = new TeeXDataSelCtrl();
		}else if("xmobileseal".equals(xtype)){
			ctrl = new TeeXMobileSealCtrl();
		}else if("xmobilehandseal".equals(xtype)){
			ctrl = new TeeXMobileHandSealCtrl();
		}else if("xvoice".equals(xtype)){
			ctrl = new TeeXVoiceCtrl();
		}else if("xh5hw".equals(xtype)){
			ctrl = new TeeXH5HwCtrl();
		}else if("xsql".equals(xtype)){
			ctrl = new TeeXSQLCtrl();
		}else if("xautonumber".equals(xtype)){
			ctrl = new TeeXAutoNumberCtrl();
		}else if("xaddress".equals(xtype)){
			ctrl = new TeeXAddressCtrl();
		}else if("xposition".equals(xtype)){
			ctrl = new TeeXPositionCtrl();
		}else if("xqrcode".equals(xtype)){
			ctrl = new TeeXQrcodeCtrl();
		}else if("xflowdatasel".equals(xtype)){
			ctrl = new TeeXFlowDataSelCtrl();
		}else if("xbarcode".equals(xtype)){
			ctrl = new TeeXBarCodeCtrl();
		}
		
		return ctrl;
	}
	
	public static String getXTypeDesc(String xtype){
		String desc = null;
		if("xinput".equals(xtype)){
			desc = "单行输入框";
		}else if("xtextarea".equals(xtype)){
			desc = "多行文本框";
		}else if("xselect".equals(xtype)){
			desc = "下拉菜单";
		}else if("xradio".equals(xtype)){
			desc = "单选控件";
		}else if("xcheckbox".equals(xtype)){
			desc = "多选控件";
		}else if("xcalculate".equals(xtype)){
			desc = "计算控件";
		}else if("xfetch".equals(xtype)){
			desc = "选择器";
		}else if("xlist".equals(xtype)){
			desc = "列表控件";
		}else if("xmacro".equals(xtype)){
			desc = "宏控件";
		}else if("xmacrotag".equals(xtype)){
			desc = "宏标记";
		}else if("xseal".equals(xtype)){
			desc = "签章控件";
		}else if("ximg".equals(xtype)){
			desc = "图片上传控件";
		}else if("xupload".equals(xtype)){
			desc = "附件上传控件";
		}else if("xdocnum".equals(xtype)){
			desc = "文号控件";
		}else if("xfeedback".equals(xtype)){
			desc = "会签控件";
		}else if("xdatasel".equals(xtype)){
			desc = "数据选择控件";
		}else if("xmobileseal".equals(xtype)){
			desc = "移动签章";
		}else if("xmobilehandseal".equals(xtype)){
			desc = "移动手写";
		}else if("xvoice".equals(xtype)){
			desc = "语音控件";
		}else if("xh5hw".equals(xtype)){
			desc = "H5手写签批";
		}else if("xsql".equals(xtype)){
			desc = "SQL控件";
		}else if("xautonumber".equals(xtype)){
			desc = "自动防编号控件";
		}else if("xaddress".equals(xtype)){
			desc = "区域联动控件";
		}else if("xposition".equals(xtype)){
			desc = "定位控件";
		}else if("xqrcode".equals(xtype)){
			desc = "二维码控件";
		}else if("xflowdatasel".equals(xtype)){
			desc = "流程数据选择控件";
		}else if("xbarcode".equals(xtype)){
			desc = "条形码控件";
		}
		return desc;
	}
	
	/**
	 * 包装
	 * @param writtable
	 * @param originalHtml
	 * @param realValue
	 * @return
	 */
	public static String wrap(boolean writtable,String originalHtml,String realValue,String style,String extValue,int itemId,String title){
		if(!writtable){//如果不可写，则进行包装
			String span = "<span style='display:none'>";
			span += "<textarea id=\"DATA_"+itemId+"\" title=\""+title+"\" >"+realValue+"</textarea><input id=\"EXTRA_"+itemId+"\" value=\""+extValue+"\" /></span>";
			span+="<span style=\""+style+"\">"+TeeStringUtil.getString(realValue).replace("\n", "<br/>")+"</span>";
			return span;
		}
		return originalHtml;
	}
	

	public void setContext(TeeWorkFlowServiceContextInterface context) {
		this.context = context;
	}

	public TeeWorkFlowServiceContextInterface getContext() {
		return context;
	}

	public TeePerson getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(TeePerson loginUser) {
		this.loginUser = loginUser;
	}
}
