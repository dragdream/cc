package com.tianee.oa.util.workflow.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXFetchCtrl extends TeeCtrl{

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		String model = formItem.getModel();
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		if(model!=null){
			Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
			String type = modelObj.get("type");
			if("1".equals(type) || "2".equals(type)){//时间型
				formItem.setColumnType(TeeColumnType.TIMESTAMP);
			}else{//大字段
				formItem.setColumnType(TeeColumnType.TEXT);
			}
		}else{
			formItem.setColumnType(TeeColumnType.TEXT);
		}
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
		String model = formItem.getModel();
		TeeJsonUtil json = new TeeJsonUtil();
		
		
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
				
		Map<String,String> params = json.JsonStr2Map(model);
		StringBuffer sb = new StringBuffer();
		
		String type = params.get("type");
		String format = params.get("format");
		
		//部门范围  默认是指定部门范围
		int bmfw=TeeStringUtil.getInteger(params.get("bmfw"), 3);
		
		String deptIds=params.get("deptIds");
		String deptNames=params.get("deptNames");
		String def = params.get("def");
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = "";
		int itemId = formItem.getItemId();
		
		boolean writable = true;//可写
		boolean required = true;//必填
		boolean hidden = false;//可见
		boolean readonly = true;//只读
		
		
		SimpleDateFormat sdf = null;
		
		if("1".equals(type) || "2".equals(type)){//日期或选择器
			sdf = new SimpleDateFormat(format);
			sb.append("<input type=\"text\" " +
					"name=\""+name+"\"" +
					"id=\""+name+"\"" +
					"title=\""+title+"\" style=\""+attrs.get("style")+"\" "+
					"class=\"Wdate "+(writable?"":"readonly")+"\" " +
					"onFocus=\"WdatePicker({isShowClear:false,readOnly:true,dateFmt:'"+format+"'})\" "+(required?"required=true":"")+" ");
			
			//如果不可写的话
			if(!writable){
				sb.append(" disabled ");
			}
			
			//初始化数据
			if(writable && data==null){
				sb.append(("1".equals(def)?" value=\""+sdf.format(new Date())+"\"":"")+"/>");
			}else{
				sb.append(" value=\""+(data==null?"":data)+"\"/>");
			}
			
			
		}else if("3".equals(type) || "4".equals(type)||
				"5".equals(type) || "6".equals(type) ||
				"7".equals(type) || "8".equals(type)){//对象选择器
			
			String extraPrefix = TeeWorkflowHelper.getFormDataFieldExtraPrefix();
			String extraData = "";
			
			String onclick = null;
			String clearData = null;
			boolean isTextarea = false;
			String style=null;
			if("3".equals(type)){//部门
				onclick = "selectSingleDept(['"+extraPrefix+itemId+"','"+name+"'])";
				style="style=\"background: #fff url(/common/images/workflow/group_select.png) no-repeat right;"+attrs.get("style")+"\"";
			}else if("4".equals(type)){//部门多选
				onclick = "selectDept(['"+extraPrefix+itemId+"','"+name+"'])";
				clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
				style="style=\"background: #fff url(/common/images/workflow/group_select.png) no-repeat right;"+attrs.get("style")+"\"";
				isTextarea = true;
			}else if("5".equals(type)){//角色
				onclick = "selectSingleRole(['"+extraPrefix+itemId+"','"+name+"'])";
				style="style=\"background: #fff url(/common/images/workflow/role_select.png) no-repeat right;"+attrs.get("style")+"\"";
			}else if("6".equals(type)){//角色多选
				onclick = "selectRole(['"+extraPrefix+itemId+"','"+name+"'])";
				clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
				isTextarea = true;
				style="style=\"background: #fff url(/common/images/workflow/role_select.png) no-repeat right;"+attrs.get("style")+"\"";
			}else if("7".equals(type)){//人员
				
				if(bmfw==1){//流程发起人所属部门
					onclick = "selectSingleUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+loginUser.getDept().getUuid()+"')";
					style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
				}else if(bmfw==2){//当前登录人所属部门
					onclick = "selectSingleUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+loginUser.getDept().getUuid()+"')";
					style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
				}else if(bmfw==3){//指定部门范围
					if(!TeeUtility.isNullorEmpty(deptIds)){
						onclick = "selectSingleUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+deptIds+"')";
						style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
					}else{
						onclick = "selectSingleUser(['"+extraPrefix+itemId+"','"+name+"'])";
						style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
					}
				}
				
				
				
			}else if("8".equals(type)){//人员多选
				if(bmfw==1){//流程发起人所属部门
					onclick = "selectUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+loginUser.getDept().getUuid()+"')";
					clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
					isTextarea = true;
					style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
				}else if(bmfw==2){//当前登录人所属部门
					onclick = "selectUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+loginUser.getDept().getUuid()+"')";
					clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
					isTextarea = true;
					style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
				}else if(bmfw==3){//指定部门范围
					if(!TeeUtility.isNullorEmpty(deptIds)){
						onclick = "selectUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+deptIds+"')";
						clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
						isTextarea = true;
						style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
					}else{
						onclick = "selectUser(['"+extraPrefix+itemId+"','"+name+"'])";
						clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
						isTextarea = true;
						style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
					}
				}
				
				
				
				
			}
			
			if(!writable){
				onclick="";
			}
			if(isTextarea){//大文本框
				sb.append("<textarea "+(writable?"writable":"class=\"readonly\"")+" " +
							"type=\"text\" "+(readonly?"readonly":"")+" " +
							"name=\""+name+"\" id=\""+name+"\" " +style+" "+
							"title=\""+title+"\" " +
//							"value=\""+(data==null?"":data)+"\" " +
							""+(required?"required=true":"")+" " +
							" colspan=\"\" " +
							" rows=\"\" " +
							" onclick=\""+(onclick)+"\" style=\""+attrs.get("style")+"\">"+(data==null?"":data)+"</textarea>" +
							//"&nbsp;&nbsp;<a href='javascript:void(0);' class='orgAdd' onclick=\""+(onclick)+"\">选择</a> &nbsp;&nbsp;" +
							//"<a href='javascript:void(0);' class='orgClear' onclick=\""+(clearData)+"\">清空</a> &nbsp;&nbsp;" +
						"<input "+(!writable?"disabled":"")+" type=\"hidden\" id=\""+extraPrefix+itemId+"\" name=\""+extraPrefix+itemId+"\" value=\""+(extraData==null?"":extraData)+"\"/>");
			}else{
				sb.append("<input "+(writable?"writable":"class=\"readonly\"")+" " +
						"type=\"text\"  readonly " +
						"name=\""+name+"\" id=\""+name+"\" " + style+" "+
								"title=\""+title+"\" " +
										"value=\""+(data==null?"":data)+"\" " +
												""+(required?"required=true":"")+" " +
														"onclick=\""+(onclick)+"\" style=\""+attrs.get("style")+"\"/>" +
						"<input "+(!writable?"disabled":"")+" type=\"hidden\" id=\""+extraPrefix+itemId+"\" name=\""+extraPrefix+itemId+"\" value=\""+(extraData==null?"":extraData)+"\"/>");
			}
			
			
			
		}
		
		return wrap(writable,sb.toString(),TeeStringUtil.getString(data),attrs.get("style"),"",formItem.getItemId(),title);
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,TeeFlowRun flowRun,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		String model = formItem.getModel();
		TeeJsonUtil json = new TeeJsonUtil();
		
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
				
		Map<String,String> params = json.JsonStr2Map(model);
		StringBuffer sb = new StringBuffer();
		
		String type = params.get("type");
		String format = params.get("format");
		String def = params.get("def");
		String deptIds=params.get("deptIds");//部门范围id字符串
		String deptNames=params.get("deptNames");//部门范围名称字符串
		
		
		//流程发起人部门id
		int beginUserDeptId=flowRunPrcs.getFlowRun().getBeginPerson().getDept().getUuid();
		int prcsUserDeptId=flowRunPrcs.getPrcsUser().getDept().getUuid();
		
		//部门范围    默认是指定部门范围
		int bmfw=TeeStringUtil.getInteger(params.get("bmfw"), 3);
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData();
		int itemId = formItem.getItemId();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean readonly = false;//只读
		
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
			readonly = false;
		}
		
		if(hidden){
			return "";
		}
		
		SimpleDateFormat sdf = null;
		
		if("1".equals(type) || "2".equals(type)){//日期或选择器
			sdf = new SimpleDateFormat(format);
			sb.append("<input type=\"text\" " +
					"name=\""+name+"\"" +
					"id=\""+name+"\"" +
					"title=\""+title+"\"  style=\""+attrs.get("style")+"\" "+
					"class=\"Wdate "+(writable?"":"readonly")+"\" " +
					"onClick=\"WdatePicker({isShowClear:false,readOnly:true,dateFmt:'"+format+"'})\" "+(required?"required=true":"")+" ");
			
			//如果不可写的话
			if(!writable){
				sb.append(" disabled ");
			}
			
			//初始化数据
			if(writable && data==null){
				sb.append(("1".equals(def)?" value=\""+sdf.format(new Date())+"\"":"")+"/>");
			}else{
				sb.append(" value=\""+(data==null?"":data)+"\"/>");
			}
			
			
		}else if("3".equals(type) || "4".equals(type)||
				"5".equals(type) || "6".equals(type) ||
				"7".equals(type) || "8".equals(type)){//对象选择器
			
			String extraPrefix = TeeWorkflowHelper.getFormDataFieldExtraPrefix();
			String extraData = datas.get(extraPrefix+itemId);
			
			String onclick = null;
			String clearData = null;
			boolean isTextarea = false;
			String style=null;
			if("3".equals(type)){//部门
				
				style="style=\"background: #fff url(/common/images/workflow/group_select.png) no-repeat right;"+attrs.get("style")+"\"";
				onclick = "selectSingleDept(['"+extraPrefix+itemId+"','"+name+"'])";
			}else if("4".equals(type)){//部门多选
				onclick = "selectDept(['"+extraPrefix+itemId+"','"+name+"'])";
				clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
				isTextarea = true;
				style="style=\"background: #fff url(/common/images/workflow/group_select.png) no-repeat right;"+attrs.get("style")+"\"";
			}else if("5".equals(type)){//角色
				style="style=\"background: #fff url(/common/images/workflow/role_select.png) no-repeat right;"+attrs.get("style")+"\"";
				onclick = "selectSingleRole(['"+extraPrefix+itemId+"','"+name+"'])";
			}else if("6".equals(type)){//角色多选
				style="style=\"background: #fff url(/common/images/workflow/role_select.png) no-repeat right;"+attrs.get("style")+"\"";
				onclick = "selectRole(['"+extraPrefix+itemId+"','"+name+"'])";
				clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
				isTextarea = true;
			}else if("7".equals(type)){//人员
				
				if(bmfw==1){//流程发起人所属部门   
					style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
					onclick = "selectSingleUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+beginUserDeptId+"')";
				}else if(bmfw==2){//当前办理人所属部门
					style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
					onclick = "selectSingleUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+prcsUserDeptId+"')";
				}else if(bmfw==3){//指定部门范围
					if(!TeeUtility.isNullorEmpty(deptIds)){
						style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
						onclick = "selectSingleUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+deptIds+"')";
					}else{
						style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
						onclick = "selectSingleUser(['"+extraPrefix+itemId+"','"+name+"'])";
					}
				}
				
				
				
				
			}else if("8".equals(type)){//人员多选
				
				if(bmfw==1){//流程发起人所属部门   
					onclick = "selectUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+beginUserDeptId+"')";
					clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
					isTextarea = true;
					style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
				}else if(bmfw==2){//当前办理人所属部门
					onclick = "selectUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+prcsUserDeptId+"')";
					clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
					isTextarea = true;
					style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
				}else if(bmfw==3){//指定部门范围
					if(!TeeUtility.isNullorEmpty(deptIds)){
						onclick = "selectUserByDept(['"+extraPrefix+itemId+"','"+name+"'],'"+deptIds+"')";
						clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
						isTextarea = true;
						style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
					}else{
						onclick = "selectUser(['"+extraPrefix+itemId+"','"+name+"'])";
						clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
						isTextarea = true;
						style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;"+attrs.get("style")+"\"";
					}
				}
				
				
				
				
			}
			
			if(!writable){
				onclick="";
			}
			if(isTextarea){//大文本框
				sb.append("<textarea "+(writable?"writable":"class=\"readonly\"")+" " +
							"type=\"text\" "+(readonly?"readonly":"")+" " +
							"name=\""+name+"\" id=\""+name+"\" " +
							"title=\""+title+"\" " +
//							"value=\""+(data==null?"":data)+"\" " +
							""+(required?"required=true":"")+" " + style+" "+
							" colspan=\"\" " +
							" rows=\"\" " +
							" onclick=\""+(onclick)+"\" style=\""+attrs.get("style")+"\">"+(data==null?"":data)+"</textarea>" +
							//"&nbsp;&nbsp;<a href='javascript:void(0);' class='orgAdd' onclick=\""+(onclick)+"\">选择</a> &nbsp;&nbsp;" +
							//"<a href='javascript:void(0);' class='orgClear' onclick=\""+(clearData)+"\">清空</a> &nbsp;&nbsp;" +
						"<input "+(!writable?"disabled":"")+" type=\"hidden\" id=\""+extraPrefix+itemId+"\" name=\""+extraPrefix+itemId+"\" value=\""+(extraData==null?"":extraData)+"\"/>");
			}else{
				sb.append("<input "+(writable?"writable":"class=\"readonly\"")+" " +
						"type=\"text\"  readonly " +
						"name=\""+name+"\" id=\""+name+"\" " + style+" "+
								"title=\""+title+"\" " +
										"value=\""+(data==null?"":data)+"\" " +
												""+(required?"required=true":"")+" " +
														"onclick=\""+(onclick)+"\" style=\""+attrs.get("style")+"\"/>" +
						"<input "+(!writable?"disabled":"")+" type=\"hidden\" id=\""+extraPrefix+itemId+"\" name=\""+extraPrefix+itemId+"\" value=\""+(extraData==null?"":extraData)+"\"/>");
			}
			
			
			
		}
		
		return wrap(writable,sb.toString(),TeeStringUtil.getString(data),attrs.get("style"),"",formItem.getItemId(),title);
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun, TeeFormItem formItem,
			TeeFlowFormData flowFormData, Map<String, String> datas) {
		// TODO Auto-generated method stub
		String model = formItem.getModel();
		TeeJsonUtil json = new TeeJsonUtil();
		
		Map<String,String> params = json.JsonStr2Map(model);
		StringBuffer sb = new StringBuffer();
		
		String type = params.get("type");
		String format = params.get("format");
		String def = params.get("def");
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData();
		int itemId = formItem.getItemId();
		
		boolean writable = true;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean readonly = false;//只读
		
		SimpleDateFormat sdf = null;
		
		if("1".equals(type) || "2".equals(type)){//日期或选择器
			sdf = new SimpleDateFormat(format);
			sb.append("<input type=\"text\" " +
					"name=\""+name+"\"" +
					"id=\""+name+"\"" +
					"title=\""+title+"\" " +
					"class=\"Wdate "+(writable?"":"readonly")+"\" " +
					"onFocus=\"WdatePicker({isShowClear:false,readOnly:true,dateFmt:'"+format+"'})\" "+(required?"required=true":"")+" ");
			
			//如果不可写的话
			if(!writable){
				sb.append(" disabled ");
			}
			
			//初始化数据
			if(writable && data==null){
				sb.append(("1".equals(def)?" value=\""+sdf.format(new Date())+"\"":"")+"/>");
			}else{
				sb.append(" value=\""+(data==null?"":data)+"\"/>");
			}
			
			
		}else if("3".equals(type) || "4".equals(type)||
				"5".equals(type) || "6".equals(type) ||
				"7".equals(type) || "8".equals(type)){//对象选择器
			
			String extraPrefix = TeeWorkflowHelper.getFormDataFieldExtraPrefix();
			String extraData = datas.get(extraPrefix+itemId);
			String style=null;
			String onclick = null;
			if("3".equals(type)){//部门
				onclick = "selectSingleDept(['"+extraPrefix+itemId+"','"+name+"'])";
				style="style=\"background: #fff url(/common/images/workflow/group_select.png) no-repeat right;\"";
			}else if("4".equals(type)){//部门多选
				onclick = "selectDept(['"+extraPrefix+itemId+"','"+name+"'])";
				style="style=\"background: #fff url(/common/images/workflow/group_select.png) no-repeat right;\"";
			}else if("5".equals(type)){//角色
				onclick = "selectSingleRole(['"+extraPrefix+itemId+"','"+name+"'])";
				style="style=\"background: #fff url(/common/images/workflow/role_select.png) no-repeat right;\"";
			}else if("6".equals(type)){//角色多选
				onclick = "selectRole(['"+extraPrefix+itemId+"','"+name+"'])";
				style="style=\"background: #fff url(/common/images/workflow/role_select.png) no-repeat right;\"";
			}else if("7".equals(type)){//人员
				style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;\"";
				onclick = "selectSingleUser(['"+extraPrefix+itemId+"','"+name+"'])";
			}else if("8".equals(type)){//人员多选
				style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;\"";
				onclick = "selectUser(['"+extraPrefix+itemId+"','"+name+"'])";
			}
			
			if(!writable){
				onclick="";
			}
			
			sb.append("<input "+(writable?"writable":"class=\"readonly\"")+" " +
					"type=\"text\"  readonly " +
					"name=\""+name+"\" id=\""+name+"\" " +style+" "+
							"title=\""+title+"\" " +
									"value=\""+(data==null?"":data)+"\" " +
											""+(required?"required=true":"")+" " +
													"onclick=\""+(onclick)+"\"/>" +
					"<input "+(!writable?"disabled":"")+" type=\"hidden\" id=\""+extraPrefix+itemId+"\" name=\""+extraPrefix+itemId+"\" value=\""+(extraData==null?"":extraData)+"\"/>");
			
		}
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		String model = formItem.getModel();
		TeeJsonUtil json = new TeeJsonUtil();
		
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		Map<String,String> params = json.JsonStr2Map(model);
		StringBuffer sb = new StringBuffer();
		
		String type = params.get("type");
		String format = params.get("format");
		String def = params.get("def");
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData();
		int itemId = formItem.getItemId();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean readonly = false;//只读
		
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
			readonly = false;
		}
		
		if(hidden){
			return "";
		}
		
		SimpleDateFormat sdf = null;
		
		if("1".equals(type) || "2".equals(type)){//日期或选择器
			sdf = new SimpleDateFormat(format);
			sb.append("<input type=\"text\" " +
					"name=\""+name+"\"" +
					"id=\""+name+"\"" +
					"title=\""+title+"\"  style=\"\" "+
					"class=\"Wdate "+(writable?"":"readonly")+"\" " +
					"onClick=\"WdatePicker({isShowClear:false,readOnly:true,dateFmt:'"+format+"'})\" "+(required?"required=true":"")+" ");
			
			//如果不可写的话
			if(!writable){
				sb.append(" disabled ");
			}
			
			//初始化数据
			if(writable && data==null){
				sb.append(("1".equals(def)?" value=\""+sdf.format(new Date())+"\"":"")+"/>");
			}else{
				sb.append(" value=\""+(data==null?"":data)+"\"/>");
			}
			
			
		}else if("3".equals(type) || "4".equals(type)||
				"5".equals(type) || "6".equals(type) ||
				"7".equals(type) || "8".equals(type)){//对象选择器
			
			String extraPrefix = TeeWorkflowHelper.getFormDataFieldExtraPrefix();
			String extraData = datas.get(extraPrefix+itemId);
			
			String onclick = null;
			String clearData = null;
			boolean isTextarea = false;
			String style=null;
			if("3".equals(type)){//部门
				
				style="style=\"background: #fff url(/common/images/workflow/group_select.png) no-repeat right;\"";
				onclick = "selectSingleDept(['"+extraPrefix+itemId+"','"+name+"'])";
			}else if("4".equals(type)){//部门多选
				onclick = "selectDept(['"+extraPrefix+itemId+"','"+name+"'])";
				clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
				isTextarea = true;
				style="style=\"background: #fff url(/common/images/workflow/group_select.png) no-repeat right;\"";
			}else if("5".equals(type)){//角色
				style="style=\"background: #fff url(/common/images/workflow/role_select.png) no-repeat right;\"";
				onclick = "selectSingleRole(['"+extraPrefix+itemId+"','"+name+"'])";
			}else if("6".equals(type)){//角色多选
				style="style=\"background: #fff url(/common/images/workflow/role_select.png) no-repeat right;\"";
				onclick = "selectRole(['"+extraPrefix+itemId+"','"+name+"'])";
				clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
				isTextarea = true;
			}else if("7".equals(type)){//人员
				style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;\"";
				onclick = "selectSingleUser(['"+extraPrefix+itemId+"','"+name+"'])";
			}else if("8".equals(type)){//人员多选
				onclick = "selectUser(['"+extraPrefix+itemId+"','"+name+"'])";
				clearData = "clearData('"+extraPrefix+itemId+"','"+name+"')";
				isTextarea = true;
				style="style=\"background: #fff url(/common/images/workflow/user_select.png) no-repeat right;\"";
			}
			
			if(!writable){
				onclick="";
			}
			if(isTextarea){//大文本框
				sb.append("<textarea "+(writable?"writable":"class=\"readonly\"")+" " +
							"type=\"text\" "+(readonly?"readonly":"")+" " +
							"name=\""+name+"\" id=\""+name+"\" " +
							"title=\""+title+"\" " +
//							"value=\""+(data==null?"":data)+"\" " +
							""+(required?"required=true":"")+" " + style+" "+
							" colspan=\"\" " +
							" rows=\"\" " +
							" onclick=\""+(onclick)+"\" style=\"\">"+(data==null?"":data)+"</textarea>" +
							//"&nbsp;&nbsp;<a href='javascript:void(0);' class='orgAdd' onclick=\""+(onclick)+"\">选择</a> &nbsp;&nbsp;" +
							//"<a href='javascript:void(0);' class='orgClear' onclick=\""+(clearData)+"\">清空</a> &nbsp;&nbsp;" +
						"<input "+(!writable?"disabled":"")+" type=\"hidden\" id=\""+extraPrefix+itemId+"\" name=\""+extraPrefix+itemId+"\" value=\""+(extraData==null?"":extraData)+"\"/>");
			}else{
				sb.append("<input "+(writable?"writable":"class=\"readonly\"")+" " +
						"type=\"text\"  readonly " +
						"name=\""+name+"\" id=\""+name+"\" " + style+" "+
								"title=\""+title+"\" " +
										"value=\""+(data==null?"":data)+"\" " +
												""+(required?"required=true":"")+" " +
														"onclick=\""+(onclick)+"\" style=\"\"/>" +
						"<input "+(!writable?"disabled":"")+" type=\"hidden\" id=\""+extraPrefix+itemId+"\" name=\""+extraPrefix+itemId+"\" value=\""+(extraData==null?"":extraData)+"\"/>");
			}
			
			
			
		}
		
		return wrap(writable,sb.toString(),TeeStringUtil.getString(data),"","",formItem.getItemId(),title);
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
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
