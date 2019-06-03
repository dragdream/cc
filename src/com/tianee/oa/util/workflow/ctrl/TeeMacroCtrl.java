package com.tianee.oa.util.workflow.ctrl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

public class TeeMacroCtrl extends TeeCtrl{

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCtrlColumnTypeName(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCtrlHtml4Design(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String model = formItem.getModel();
		String defaultValue = "";
		StringBuffer opts = new StringBuffer();
		String tmp = "";
		
		//标记是否为人员、部门、角色控件，如果为这些控件的话，则标记为true，强制在当前步骤刷新
		boolean foreceRefresh = false;
		
		if(model!=null){
			Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
			String type = modelObj.get("type");
			String format = modelObj.get("format");
			
			if("1".equals(type) || "2".equals(type) || "3".equals(type)){
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				defaultValue = sdf.format(new Date());
				tag.getAttributes().put("class", "Wdate");
				tag.getAttributes().put("onfocus", "WdatePicker({isShowClear:true,readOnly:true,dateFmt:'"+format+"'})");
			}else if("5".equals(type)){//流程名称
				defaultValue = "{流程名称}";
			}else if("6".equals(type)){//流程编号
				defaultValue = "{流程编号}";
			}else if("7".equals(type)){//流程发起人帐号
				defaultValue = loginUser.getUserId();
			}else if("8".equals(type)){//流程发起人姓名
				defaultValue = loginUser.getUserName();
			}else if("9".equals(type)){//流程发起人部门
				defaultValue = loginUser.getDept().getDeptName();
			}else if("10".equals(type)){//流程发起人角色
				defaultValue = loginUser.getUserRole().getRoleName();
			}else if("11".equals(type)){//当前用户帐号
				defaultValue = loginUser.getUserId();
				foreceRefresh = true;
			}else if("12".equals(type)){//当前用户姓名
				defaultValue =  loginUser.getUserName();
				foreceRefresh = true;
			}else if("13".equals(type)){//当前用户部门
				defaultValue = loginUser.getDept().getDeptName();
				foreceRefresh = true;
			}else if("14".equals(type)){//当前用户角色
				defaultValue = loginUser.getUserRole().getRoleName();
				foreceRefresh = true;
			}else if("15".equals(type)){//当前用户IP
				defaultValue = TeeRequestInfoContext.getRequestInfo().getIpAddress();
				foreceRefresh = true;
			}else if("16".equals(type)){//流程发起人辅助部门选择
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\"  title=\""+title+"\">");
				List<TeeDepartment> deptOther = loginUser.getDeptIdOther();
				TeeDepartment curDept = loginUser.getDept();
				opts.append("<option selected value=\""+curDept.getDeptName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptName()+"</option>");
				for(TeeDepartment dept:deptOther){
					tmp = dept.getUuid()+"";
					opts.append("<option value=\""+dept.getDeptName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
				}
				opts.append("</select>");
			}else if("17".equals(type)){//流程发起人辅助角色选择
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\"  title=\""+title+"\">");
				List<TeeUserRole> roleOther = loginUser.getUserRoleOther();
				TeeUserRole curRole = loginUser.getUserRole();
				opts.append("<option selected value=\""+curRole.getRoleName()+"\" extValue=\""+curRole.getUuid()+"\">"+curRole.getRoleName()+"</option>");
				for(TeeUserRole role:roleOther){
					opts.append("<option value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
				}
				
				opts.append("</select>");
			}else if("18".equals(type)){//当前用户辅助部门选择
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\"  title=\""+title+"\">");
				List<TeeDepartment> deptOther = loginUser.getDeptIdOther();
				TeeDepartment curDept = loginUser.getDept();
				opts.append("<option selected value=\""+curDept.getDeptName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptName()+"</option>");
				for(TeeDepartment dept:deptOther){
					opts.append("<option value=\""+dept.getDeptName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
				}
				
				opts.append("</select>");
			}else if("19".equals(type)){//当前用户辅助角色选择
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\"  title=\""+title+"\">");
				List<TeeUserRole> roleOther = loginUser.getUserRoleOther();
				TeeUserRole curRole = loginUser.getUserRole();
				opts.append("<option selected value=\""+curRole.getRoleName()+"\" extValue=\""+curRole.getUuid()+"\">"+curRole.getRoleName()+"</option>");
				for(TeeUserRole role:roleOther){
					opts.append("<option value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
				}
				
				opts.append("</select>");
			}else if("21".equals(type)){//当前工作名称
				defaultValue = "[工作名称]";
			}
//			<option value="22">当前用户部门（层级）</option>
//			<option value="23">当前用户辅助部门（层级）</option>
//			<option value="24">流程发起人部门（层级）</option>
//			<option value="25">流程发起人辅助部门（层级）</option>
			else if("22".equals(type)){//当前用户部门（层级）
				defaultValue = loginUser.getDept().getDeptFullName();
				foreceRefresh = true;
			}else if("23".equals(type)){//当前用户辅助部门（层级）
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\"  title=\""+title+"\">");
				List<TeeDepartment> deptOther = loginUser.getDeptIdOther();
				TeeDepartment curDept = loginUser.getDept();
				opts.append("<option selected value=\""+curDept.getDeptFullName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptFullName()+"</option>");
				for(TeeDepartment dept:deptOther){
					opts.append("<option value=\""+dept.getDeptFullName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
				}
				
				opts.append("</select>");
			}else if("24".equals(type)){//流程发起人部门（层级）
				defaultValue = loginUser.getDept().getDeptFullName();
			}else if("25".equals(type)){//流程发起人辅助部门（层级）
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\"  title=\""+title+"\">");
				List<TeeDepartment> deptOther = loginUser.getDeptIdOther();
				TeeDepartment curDept = loginUser.getDept();
				opts.append("<option selected value=\""+curDept.getDeptFullName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptFullName()+"</option>");
				for(TeeDepartment dept:deptOther){
					tmp = dept.getUuid()+"";
					opts.append("<option value=\""+dept.getDeptFullName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
				}
				opts.append("</select>");
			}else if("26".equals(type)){//当前用户姓名+当前日期
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				defaultValue = loginUser.getUserName()+" "+sdf.format(new Date());
				foreceRefresh = true;
			}else if("27".equals(type)){
				foreceRefresh = true;
				TeeAttachment attach = loginUser.getAttach();
				if(attach!=null && attach.getSid()>0){
					InputStream in = null;
					byte[] data1 = null;
					//读取图片字节数组
					try 
					{
						in = new FileInputStream(attach.getFilePath());        
						data1 = new byte[in.available()];
						in.read(data1);
						in.close();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
					//对字节数组Base64编码  
					BASE64Encoder encoder1 = new BASE64Encoder();  
					String encode = encoder1.encode(data1);//返回Base64编码过的字节数组字符串  
					encode="data:image/png;base64,"+encode.replaceAll("\r|\n", "");
					defaultValue=encode;
					opts.append("<img src='"+encode+"'/>");
				}
			}
		}
		
		tag.getAttributes().put("value", defaultValue);
		
		if(opts.length()!=0){
			return opts.toString();
		}
		
		return tag.toString();
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
		String extValue = TeeStringUtil.getString(datas.get("EXTRA_"+formItem.getItemId()));
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		String data = TeeStringUtil.getString(flowFormData.getData());
		String model = attrs.get("model");
		String toString="<span style=\""+attrs.get("style")+"\">"+data+"</span>"+"<input class=\"FORM_PRINT\" type=\"hidden\" value=\""+data+"\" title=\""+formItem.getTitle()+"\" />";
		if(model!=null){
			Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
			String type = modelObj.get("type");
			if("27".equals(type)){
				toString="<span style=\"display:none;\">"+toString+"</span><img src=\""+extValue+"\"/>";
			}
		}
		   
		
		return toString;
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
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
		
//		//如果当前为回退的步骤，则宏控件是不生效的
//		if(flowRunPrcs.getBackFlag()==1){
//			auto = false;
//		}
		
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
		
		//获取模型
		/**
		 * <option value="1">当前年份</option>
		<option value="2">当前日期</option>
		<option value="3">当前时间</option>
		<option value="4">当前季度</option>
		<option value="20">当前星期</option>
		<option value="5">流程名称</option>
		<option value="6">流程编号</option>
		<option value="7">流程发起人帐号</option>
		<option value="8">流程发起人姓名</option>
		<option value="9">流程发起人部门</option>
		<option value="10">流程发起人角色</option>
		<option value="11">当前用户帐号</option>
		<option value="12">当前用户姓名</option>
		<option value="13">当前用户部门</option>
		<option value="14">当前用户角色</option>
		<option value="15">当前用户IP</option>
		<option value="16">流程发起人辅助部门选择</option>
		<option value="17">流程发起人辅助角色选择</option>
		<option value="18">当前用户辅助部门选择</option>
		<option value="19">当前用户辅助角色选择</option>
		 */
		String model = formItem.getModel();
		String defaultValue = "";
		String extValue = TeeStringUtil.getString(datas.get("EXTRA_"+formItem.getItemId()));
		String defaultExtValue = "";
		StringBuffer opts = new StringBuffer();
		String tmp = "";
		
		//标记是否为人员、部门、角色控件，如果为这些控件的话，则标记为true，强制在当前步骤刷新
		boolean foreceRefresh = false;
		String type = null;
		if(model!=null){
			Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
			type = modelObj.get("type");
			//System.out.println("type:"+type);
			String format = modelObj.get("format");
			
			if("1".equals(type) || "2".equals(type) || "3".equals(type)){
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				defaultValue = sdf.format(new Date());
				tag.getAttributes().put("class", "Wdate");
				tag.getAttributes().put("onfocus", "WdatePicker({isShowClear:true,readOnly:true,dateFmt:'"+format+"'})");
			}else if("5".equals(type)){//流程名称
				defaultValue = flowRunPrcs.getFlowRun().getRunName();
			}else if("6".equals(type)){//流程编号
				defaultValue = ""+flowRunPrcs.getFlowRun().getFlowType().getNumbering();
			}else if("7".equals(type)){//流程发起人帐号
				defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUserId();
				defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUuid();
			}else if("8".equals(type)){//流程发起人姓名
				defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUserName();
				defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUuid();
			}else if("9".equals(type)){//流程发起人部门
				defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getDept().getDeptName();
				defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getDept().getUuid();
			}else if("10".equals(type)){//流程发起人角色
				defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUserRole().getRoleName();
				defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUserRole().getUuid();
			}else if("11".equals(type)){//当前用户帐号
				defaultValue = ""+flowRunPrcs.getPrcsUser().getUserId();
				defaultExtValue = ""+flowRunPrcs.getPrcsUser().getUuid();
				foreceRefresh = true;
			}else if("12".equals(type)){//当前用户姓名
				defaultValue = ""+flowRunPrcs.getPrcsUser().getUserName();
				defaultExtValue = ""+flowRunPrcs.getPrcsUser().getUuid();
				foreceRefresh = true;
			}else if("13".equals(type)){//当前用户部门
				defaultValue = ""+flowRunPrcs.getPrcsUser().getDept().getDeptName();
				defaultExtValue = ""+flowRunPrcs.getPrcsUser().getDept().getUuid();
				foreceRefresh = true;
			}else if("14".equals(type)){//当前用户角色
				defaultValue = ""+flowRunPrcs.getPrcsUser().getUserRole().getRoleName();
				defaultExtValue = ""+flowRunPrcs.getPrcsUser().getUserRole().getUuid();
				foreceRefresh = true;
			}else if("15".equals(type)){//当前用户IP
				defaultValue = TeeRequestInfoContext.getRequestInfo().getIpAddress();
				foreceRefresh = true;
			}else if("16".equals(type)){//流程发起人辅助部门选择
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
				List<TeeDepartment> deptOther = flowRunPrcs.getFlowRun().getBeginPerson().getDeptIdOther();
				TeeDepartment curDept = flowRunPrcs.getFlowRun().getBeginPerson().getDept();
				opts.append("<option selected value=\""+curDept.getDeptName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptName()+"</option>");
				if("".equals(data)){
					tmp = curDept.getUuid()+"";
				}else if(String.valueOf(curDept.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
					tmp = curDept.getUuid()+"";
				}
				if(writable){
					for(TeeDepartment dept:deptOther){
						if(data.equals(dept.getDeptName())){
							tmp = dept.getUuid()+"";
							opts.append("<option selected value=\""+dept.getDeptName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
						}else{
							opts.append("<option value=\""+dept.getDeptName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
						}
					}
				}
				opts.append("</select>");
			}else if("17".equals(type)){//流程发起人辅助角色选择
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
				List<TeeUserRole> roleOther = flowRunPrcs.getFlowRun().getBeginPerson().getUserRoleOther();
				TeeUserRole curRole = flowRunPrcs.getFlowRun().getBeginPerson().getUserRole();
				opts.append("<option selected value=\""+curRole.getRoleName()+"\" extValue=\""+curRole.getUuid()+"\">"+curRole.getRoleName()+"</option>");
				if("".equals(data)){
					tmp = curRole.getUuid()+"";
				}else if(String.valueOf(curRole.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
					tmp = curRole.getUuid()+"";
				}
				if(writable){
					for(TeeUserRole role:roleOther){
						if(data.equals(role.getRoleName())){
							tmp = role.getUuid()+"";
							opts.append("<option selected value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
						}else{
							opts.append("<option value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
						}
					}
				}
				
				opts.append("</select>");
			}else if("18".equals(type)){//当前用户辅助部门选择
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
				List<TeeDepartment> deptOther = flowRunPrcs.getPrcsUser().getDeptIdOther();
				TeeDepartment curDept = flowRunPrcs.getPrcsUser().getDept();
				opts.append("<option selected value=\""+curDept.getDeptName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptName()+"</option>");
				if("".equals(data)){
					tmp = curDept.getUuid()+"";
				}else if(String.valueOf(curDept.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
					tmp = curDept.getUuid()+"";
				}
				if(writable){
					for(TeeDepartment dept:deptOther){
						if(data.equals(dept.getDeptName())){
							tmp = dept.getUuid()+"";
							opts.append("<option selected value=\""+dept.getDeptName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
						}else{
							opts.append("<option value=\""+dept.getDeptName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
						}
					}
				}
				
				opts.append("</select>");
			}else if("19".equals(type)){//当前用户辅助角色选择
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
				List<TeeUserRole> roleOther = flowRunPrcs.getPrcsUser().getUserRoleOther();
				TeeUserRole curRole = flowRunPrcs.getPrcsUser().getUserRole();
				opts.append("<option selected value=\""+curRole.getRoleName()+"\" extValue=\""+curRole.getUuid()+"\">"+curRole.getRoleName()+"</option>");
				if("".equals(data)){
					tmp = curRole.getUuid()+"";
				}else if(String.valueOf(curRole.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
					tmp = curRole.getUuid()+"";
				}
				
				if(writable){
					for(TeeUserRole role:roleOther){
						if(data.equals(role.getRoleName())){
							tmp = role.getUuid()+"";
							opts.append("<option selected value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
						}else{
							opts.append("<option value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
						}
					}
				}
				
				opts.append("</select>");
			}else if("20".equals(type)){//当前星期
				Calendar c = Calendar.getInstance();
				switch(c.get(Calendar.DAY_OF_WEEK)){
					case 1:
						defaultValue = "星期日";
						break;
					case 2:
						defaultValue = "星期一";
						break;
					case 3:
						defaultValue = "星期二";
						break;
					case 4:
						defaultValue = "星期三";
						break;
					case 5:
						defaultValue = "星期四";
						break;
					case 6:
						defaultValue = "星期五";
						break;
					case 7:
						defaultValue = "星期六";
						break;
					default:break;
				}
			}else if("21".equals(type)){//当前工作名称
				data = flowRunPrcs.getFlowRun().getRunName();
				tag.getAttributes().put("save_run_name", "");
			}
//			<option value="22">当前用户部门（层级）</option>
//			<option value="23">当前用户辅助部门（层级）</option>
//			<option value="24">流程发起人部门（层级）</option>
//			<option value="25">流程发起人辅助部门（层级）</option>
			else if("22".equals(type)){//当前用户部门（层级）
				defaultValue = ""+flowRunPrcs.getPrcsUser().getDept().getDeptFullName();
				defaultExtValue = ""+flowRunPrcs.getPrcsUser().getDept().getUuid();
				foreceRefresh = true;
				
			}else if("23".equals(type)){//当前用户辅助部门（层级）
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
				List<TeeDepartment> deptOther = flowRunPrcs.getPrcsUser().getDeptIdOther();
				TeeDepartment curDept = flowRunPrcs.getPrcsUser().getDept();
				opts.append("<option selected value=\""+curDept.getDeptFullName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptFullName()+"</option>");
				if("".equals(data)){
					tmp = curDept.getUuid()+"";
				}else if(String.valueOf(curDept.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
					tmp = curDept.getUuid()+"";
				}
				if(writable){
					for(TeeDepartment dept:deptOther){
						if(data.equals(dept.getDeptFullName())){
							tmp = dept.getUuid()+"";
							opts.append("<option selected value=\""+dept.getDeptFullName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
						}else{
							opts.append("<option value=\""+dept.getDeptFullName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
						}
					}
				}
				
				opts.append("</select>");
				
			}else if("24".equals(type)){//流程发起人部门（层级）
				defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getDept().getDeptFullName();
				defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getDept().getUuid();
				foreceRefresh = true;
				//System.out.println(type+"流程发起人部门（层级）:"+defaultValue+"  "+defaultExtValue);
			}else if("25".equals(type)){//流程发起人辅助部门（层级）
				foreceRefresh = true;
				opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
				List<TeeDepartment> deptOther = flowRunPrcs.getFlowRun().getBeginPerson().getDeptIdOther();
				TeeDepartment curDept = flowRunPrcs.getFlowRun().getBeginPerson().getDept();
				opts.append("<option selected value=\""+curDept.getDeptFullName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptFullName()+"</option>");
				if("".equals(data)){
					tmp = curDept.getUuid()+"";
				}else if(String.valueOf(curDept.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
					tmp = curDept.getUuid()+"";
				}
				if(writable){
					for(TeeDepartment dept:deptOther){
						if(data.equals(dept.getDeptFullName())){
							tmp = dept.getUuid()+"";
							opts.append("<option selected value=\""+dept.getDeptFullName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
						}else{
							opts.append("<option value=\""+dept.getDeptFullName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
						}
					}
				}
				opts.append("</select>");
			}else if("26".equals(type)){//当前用户姓名+当前日期
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				defaultValue = ""+flowRunPrcs.getPrcsUser().getUserName()+" "+sdf.format(new Date());
				defaultExtValue = ""+flowRunPrcs.getPrcsUser().getUuid();
				foreceRefresh = true;
			}else if("27".equals(type)){
				foreceRefresh = true;
			}
		}
		
		//针对可写字段进行赋值
		if(writable){
			//（如果开启自动赋值  并且 原数据为空的话）或者强制刷新的话   则获取默认数据
			if((auto && "".equals(data)) || foreceRefresh){
				attrs.put("value", defaultValue);
				extValue = defaultExtValue;
				if(!"".equals(tmp)){
					extValue = tmp;
				}
				
				if("27".equals(type)){//签名图
					attrs.put("value", flowRunPrcs.getPrcsUser().getUserName());
					TeeAttachment attach = flowRunPrcs.getPrcsUser().getAttach();
					if(attach!=null && attach.getSid()>0){
						InputStream in = null;
						byte[] data1 = null;
						//读取图片字节数组
						try 
						{
							in = new FileInputStream(attach.getFilePath());        
							data1 = new byte[in.available()];
							in.read(data1);
							in.close();
						} 
						catch (IOException e) 
						{
							e.printStackTrace();
						}
						//对字节数组Base64编码  
						BASE64Encoder encoder1 = new BASE64Encoder();  
						String encode = encoder1.encode(data1);//返回Base64编码过的字节数组字符串  
						encode="data:image/png;base64,"+encode.replaceAll("\r|\n", "");
						extValue=encode;
					}
				}
				
			}else{
				attrs.put("value", data);
			}
		}else{//针对不可写字段赋值
			attrs.put("value", data);
			if("27".equals(type)){//签名图
				attrs.put("value", "<img src=\""+extValue+"\" />");
			}
		}
		
		//自动赋值
//		if(!readonly && auto && "".equals(data)){
//			attrs.put("value", defaultValue);
//		}else{
//			attrs.put("value", data);
//		}
		
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		if(readonly){
			attrs.put("readonly", String.valueOf(readonly));
			tag.getAttributes().put("onfocus","");
		}
		
		String tagString = tag.toString();
		
		if("27".equals(type)){//如果是图标的话
			tagString = "<span style='display:none'>"+tagString+"</span><img src=\""+extValue+"\" />";
		}
		
		if(!writable){
			attrs.put("disabled", "disabled");
			opts.delete(0, opts.length());
			opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
			if(!"".equals(data)){
				opts.append("<option value=\""+data+"\" extValue=\""+extValue+"\">"+data+"</option>");
			}
			opts.append("</select>");
		}
		
		if(opts.length()!=0){
			return wrap(writable,opts.toString()+"<input type=\"hidden\" name=\"EXTRA_"+formItem.getItemId()+"\" id=\"EXTRA_"+formItem.getItemId()+"\" "+(writable?"writable":"disabled")+" value=\""+extValue+"\"/>",attrs.get("value"),attrs.get("style"),extValue,formItem.getItemId(),title);
		}
		
		return wrap(writable,tagString+"<input type=\"hidden\" name=\"EXTRA_"+formItem.getItemId()+"\" id=\"EXTRA_"+formItem.getItemId()+"\" "+(writable?"writable":"disabled")+" value=\""+extValue+"\"/>",attrs.get("value"),extValue,attrs.get("style"),formItem.getItemId(),title);
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun,TeeFormItem formItem,TeeFlowFormData flowFormData,Map<String,String> datas) {
		// TODO Auto-generated method stub
		TeeForm form = flowRun.getForm();
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		boolean writable = true;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		boolean readonly = false;//只读
		
		//获取模型
		/**
		 * <option value="1">当前年份</option>
		<option value="2">当前日期</option>
		<option value="3">当前时间</option>
		<option value="4">当前季度</option>
		<option value="5">流程名称</option>
		<option value="6">流程编号</option>
		<option value="7">流程发起人帐号</option>
		<option value="8">流程发起人姓名</option>
		<option value="9">流程发起人部门</option>
		<option value="10">流程发起人角色</option>
		<option value="11">当前用户帐号</option>
		<option value="12">当前用户姓名</option>
		<option value="13">当前用户部门</option>
		<option value="14">当前用户角色</option>
		<option value="15">当前用户IP</option>
		<option value="16">流程发起人辅助部门选择</option>
		<option value="17">流程发起人辅助角色选择</option>
		<option value="18">当前用户辅助部门选择</option>
		<option value="19">当前用户辅助角色选择</option>
		 */
		String model = formItem.getModel();
		String defaultValue = "";
		String extValue = "";
		StringBuffer opts = new StringBuffer();
		String tmp = "";
		if(model!=null){
			Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
			String type = modelObj.get("type");
			String format = modelObj.get("format");
			
			if("1".equals(type) || "2".equals(type) || "3".equals(type)){
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				defaultValue = sdf.format(new Date());
			}else if("5".equals(type)){//流程名称
				defaultValue = flowRun.getRunName();
			}else if("6".equals(type)){//流程编号
				defaultValue = ""+flowRun.getFlowType().getNumbering();
			}else if("7".equals(type)){//流程发起人帐号
				defaultValue = ""+flowRun.getBeginPerson().getUserId();
				extValue = ""+flowRun.getBeginPerson().getUuid();
			}else if("8".equals(type)){//流程发起人姓名
				defaultValue = ""+flowRun.getBeginPerson().getUserName();
				extValue = ""+flowRun.getBeginPerson().getUuid();
			}else if("9".equals(type)){//流程发起人部门
				defaultValue = ""+flowRun.getBeginPerson().getDept().getDeptName();
				extValue = ""+flowRun.getBeginPerson().getDept().getUuid();
			}else if("10".equals(type)){//流程发起人角色
				defaultValue = ""+flowRun.getBeginPerson().getUserRole().getRoleName();
				extValue = ""+flowRun.getBeginPerson().getUserRole().getUuid();
			}
		}
		
		//自动赋值
		if(auto && "".equals(data)){
			attrs.put("value", defaultValue);
		}else{
			attrs.put("value", data);
		}
		attrs.put("writable", String.valueOf(writable));
		attrs.put("required", String.valueOf(required));
		if(readonly){
			attrs.put("readonly", String.valueOf(readonly));
		}
		
		if(!writable){
			attrs.put("disabled", "disabled");
		}
		
		if(opts.length()!=0){
			return opts.toString()+"<input type=\"hidden\" name=\"EXTRA_"+formItem.getItemId()+"\" id=\"EXTRA_"+formItem.getItemId()+"\" "+(writable?"writable":"disabled")+" value=\""+extValue+"\"/>";
		}
		
		return tag.toString()+"<input type=\"hidden\" name=\"EXTRA_"+formItem.getItemId()+"\" id=\"EXTRA_"+formItem.getItemId()+"\" "+(writable?"writable":"disabled")+" value=\""+extValue+"\"/>";
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
				//表单控件分析
				TeeHTMLTag tag = new TeeHTMLInputTag();
				tag.getAttributes().put("style","");
				tag.analyse(formItem.getContent());
				//获取控件属性
				Map<String,String> attrs = tag.getAttributes();
				attrs.put("style", "");
				TeeJsonUtil jsonUtil = new TeeJsonUtil();
				String name = formItem.getName();
				String title = formItem.getTitle();
				String data = flowFormData.getData()==null?"":flowFormData.getData();
				
				boolean writable = false;//可写
				boolean required = false;//必填
				boolean hidden = false;//可见
				boolean auto = false;//自动赋值
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
				
//				//如果当前为回退的步骤，则宏控件是不生效的
//				if(flowRunPrcs.getBackFlag()==1){
//					auto = false;
//				}
				
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
				
				//获取模型
				/**
				 * <option value="1">当前年份</option>
				<option value="2">当前日期</option>
				<option value="3">当前时间</option>
				<option value="4">当前季度</option>
				<option value="20">当前星期</option>
				<option value="5">流程名称</option>
				<option value="6">流程编号</option>
				<option value="7">流程发起人帐号</option>
				<option value="8">流程发起人姓名</option>
				<option value="9">流程发起人部门</option>
				<option value="10">流程发起人角色</option>
				<option value="11">当前用户帐号</option>
				<option value="12">当前用户姓名</option>
				<option value="13">当前用户部门</option>
				<option value="14">当前用户角色</option>
				<option value="15">当前用户IP</option>
				<option value="16">流程发起人辅助部门选择</option>
				<option value="17">流程发起人辅助角色选择</option>
				<option value="18">当前用户辅助部门选择</option>
				<option value="19">当前用户辅助角色选择</option>
				 */
				String model = formItem.getModel();
				String defaultValue = "";
				String extValue = TeeStringUtil.getString(datas.get("EXTRA_"+formItem.getItemId()));
				String defaultExtValue = "";
				StringBuffer opts = new StringBuffer();
				String tmp = "";
				
				//标记是否为人员、部门、角色控件，如果为这些控件的话，则标记为true，强制在当前步骤刷新
				boolean foreceRefresh = false;
				String type = null;
				if(model!=null){
					Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
					type = modelObj.get("type");
					//System.out.println("type:"+type);
					String format = modelObj.get("format");
					
					if("1".equals(type) || "2".equals(type) || "3".equals(type)){
						SimpleDateFormat sdf = new SimpleDateFormat(format);
						defaultValue = sdf.format(new Date());
						tag.getAttributes().put("class", "Wdate");
						tag.getAttributes().put("onfocus", "WdatePicker({isShowClear:true,readOnly:true,dateFmt:'"+format+"'})");
					}else if("5".equals(type)){//流程名称
						defaultValue = flowRunPrcs.getFlowRun().getRunName();
					}else if("6".equals(type)){//流程编号
						defaultValue = ""+flowRunPrcs.getFlowRun().getFlowType().getNumbering();
					}else if("7".equals(type)){//流程发起人帐号
						defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUserId();
						defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUuid();
					}else if("8".equals(type)){//流程发起人姓名
						defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUserName();
						defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUuid();
					}else if("9".equals(type)){//流程发起人部门
						defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getDept().getDeptName();
						defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getDept().getUuid();
					}else if("10".equals(type)){//流程发起人角色
						defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUserRole().getRoleName();
						defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getUserRole().getUuid();
					}else if("11".equals(type)){//当前用户帐号
						defaultValue = ""+flowRunPrcs.getPrcsUser().getUserId();
						defaultExtValue = ""+flowRunPrcs.getPrcsUser().getUuid();
						foreceRefresh = true;
					}else if("12".equals(type)){//当前用户姓名
						defaultValue = ""+flowRunPrcs.getPrcsUser().getUserName();
						defaultExtValue = ""+flowRunPrcs.getPrcsUser().getUuid();
						foreceRefresh = true;
					}else if("13".equals(type)){//当前用户部门
						defaultValue = ""+flowRunPrcs.getPrcsUser().getDept().getDeptName();
						defaultExtValue = ""+flowRunPrcs.getPrcsUser().getDept().getUuid();
						foreceRefresh = true;
					}else if("14".equals(type)){//当前用户角色
						defaultValue = ""+flowRunPrcs.getPrcsUser().getUserRole().getRoleName();
						defaultExtValue = ""+flowRunPrcs.getPrcsUser().getUserRole().getUuid();
						foreceRefresh = true;
					}else if("15".equals(type)){//当前用户IP
						defaultValue = TeeRequestInfoContext.getRequestInfo().getIpAddress();
						foreceRefresh = true;
					}else if("16".equals(type)){//流程发起人辅助部门选择
						foreceRefresh = true;
						opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
						List<TeeDepartment> deptOther = flowRunPrcs.getFlowRun().getBeginPerson().getDeptIdOther();
						TeeDepartment curDept = flowRunPrcs.getFlowRun().getBeginPerson().getDept();
						opts.append("<option selected value=\""+curDept.getDeptName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptName()+"</option>");
						if("".equals(data)){
							tmp = curDept.getUuid()+"";
						}else if(String.valueOf(curDept.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
							tmp = curDept.getUuid()+"";
						}
						if(writable){
							for(TeeDepartment dept:deptOther){
								if(data.equals(dept.getDeptName())){
									tmp = dept.getUuid()+"";
									opts.append("<option selected value=\""+dept.getDeptName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
								}else{
									opts.append("<option value=\""+dept.getDeptName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
								}
							}
						}
						opts.append("</select>");
					}else if("17".equals(type)){//流程发起人辅助角色选择
						foreceRefresh = true;
						opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
						List<TeeUserRole> roleOther = flowRunPrcs.getFlowRun().getBeginPerson().getUserRoleOther();
						TeeUserRole curRole = flowRunPrcs.getFlowRun().getBeginPerson().getUserRole();
						opts.append("<option selected value=\""+curRole.getRoleName()+"\" extValue=\""+curRole.getUuid()+"\">"+curRole.getRoleName()+"</option>");
						if("".equals(data)){
							tmp = curRole.getUuid()+"";
						}else if(String.valueOf(curRole.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
							tmp = curRole.getUuid()+"";
						}
						if(writable){
							for(TeeUserRole role:roleOther){
								if(data.equals(role.getRoleName())){
									tmp = role.getUuid()+"";
									opts.append("<option selected value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
								}else{
									opts.append("<option value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
								}
							}
						}
						
						opts.append("</select>");
					}else if("18".equals(type)){//当前用户辅助部门选择
						foreceRefresh = true;
						opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
						List<TeeDepartment> deptOther = flowRunPrcs.getPrcsUser().getDeptIdOther();
						TeeDepartment curDept = flowRunPrcs.getPrcsUser().getDept();
						opts.append("<option selected value=\""+curDept.getDeptName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptName()+"</option>");
						if("".equals(data)){
							tmp = curDept.getUuid()+"";
						}else if(String.valueOf(curDept.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
							tmp = curDept.getUuid()+"";
						}
						if(writable){
							for(TeeDepartment dept:deptOther){
								if(data.equals(dept.getDeptName())){
									tmp = dept.getUuid()+"";
									opts.append("<option selected value=\""+dept.getDeptName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
								}else{
									opts.append("<option value=\""+dept.getDeptName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptName()+"</option>");
								}
							}
						}
						
						opts.append("</select>");
					}else if("19".equals(type)){//当前用户辅助角色选择
						foreceRefresh = true;
						opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
						List<TeeUserRole> roleOther = flowRunPrcs.getPrcsUser().getUserRoleOther();
						TeeUserRole curRole = flowRunPrcs.getPrcsUser().getUserRole();
						opts.append("<option selected value=\""+curRole.getRoleName()+"\" extValue=\""+curRole.getUuid()+"\">"+curRole.getRoleName()+"</option>");
						if("".equals(data)){
							tmp = curRole.getUuid()+"";
						}else if(String.valueOf(curRole.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
							tmp = curRole.getUuid()+"";
						}
						
						if(writable){
							for(TeeUserRole role:roleOther){
								if(data.equals(role.getRoleName())){
									tmp = role.getUuid()+"";
									opts.append("<option selected value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
								}else{
									opts.append("<option value=\""+role.getRoleName()+"\" extValue=\""+role.getUuid()+"\">"+role.getRoleName()+"</option>");
								}
							}
						}
						
						opts.append("</select>");
					}else if("20".equals(type)){//当前星期
						Calendar c = Calendar.getInstance();
						switch(c.get(Calendar.DAY_OF_WEEK)){
							case 1:
								defaultValue = "星期日";
								break;
							case 2:
								defaultValue = "星期一";
								break;
							case 3:
								defaultValue = "星期二";
								break;
							case 4:
								defaultValue = "星期三";
								break;
							case 5:
								defaultValue = "星期四";
								break;
							case 6:
								defaultValue = "星期五";
								break;
							case 7:
								defaultValue = "星期六";
								break;
							default:break;
						}
					}else if("21".equals(type)){//当前工作名称
						data = flowRunPrcs.getFlowRun().getRunName();
						tag.getAttributes().put("save_run_name", "");
					}
//					<option value="22">当前用户部门（层级）</option>
//					<option value="23">当前用户辅助部门（层级）</option>
//					<option value="24">流程发起人部门（层级）</option>
//					<option value="25">流程发起人辅助部门（层级）</option>
					else if("22".equals(type)){//当前用户部门（层级）
						defaultValue = ""+flowRunPrcs.getPrcsUser().getDept().getDeptFullName();
						defaultExtValue = ""+flowRunPrcs.getPrcsUser().getDept().getUuid();
						foreceRefresh = true;
						
					}else if("23".equals(type)){//当前用户辅助部门（层级）
						foreceRefresh = true;
						opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
						List<TeeDepartment> deptOther = flowRunPrcs.getPrcsUser().getDeptIdOther();
						TeeDepartment curDept = flowRunPrcs.getPrcsUser().getDept();
						opts.append("<option selected value=\""+curDept.getDeptFullName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptFullName()+"</option>");
						if("".equals(data)){
							tmp = curDept.getUuid()+"";
						}else if(String.valueOf(curDept.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
							tmp = curDept.getUuid()+"";
						}
						if(writable){
							for(TeeDepartment dept:deptOther){
								if(data.equals(dept.getDeptFullName())){
									tmp = dept.getUuid()+"";
									opts.append("<option selected value=\""+dept.getDeptFullName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
								}else{
									opts.append("<option value=\""+dept.getDeptFullName()+"\"  extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
								}
							}
						}
						
						opts.append("</select>");
						
					}else if("24".equals(type)){//流程发起人部门（层级）
						defaultValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getDept().getDeptFullName();
						defaultExtValue = ""+flowRunPrcs.getFlowRun().getBeginPerson().getDept().getUuid();
						foreceRefresh = true;
						//System.out.println(type+"流程发起人部门（层级）:"+defaultValue+"  "+defaultExtValue);
					}else if("25".equals(type)){//流程发起人辅助部门（层级）
						foreceRefresh = true;
						opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
						List<TeeDepartment> deptOther = flowRunPrcs.getFlowRun().getBeginPerson().getDeptIdOther();
						TeeDepartment curDept = flowRunPrcs.getFlowRun().getBeginPerson().getDept();
						opts.append("<option selected value=\""+curDept.getDeptFullName()+"\" extValue=\""+curDept.getUuid()+"\">"+curDept.getDeptFullName()+"</option>");
						if("".equals(data)){
							tmp = curDept.getUuid()+"";
						}else if(String.valueOf(curDept.getUuid()).equals(datas.get("EXTRA_"+formItem.getItemId()))){
							tmp = curDept.getUuid()+"";
						}
						if(writable){
							for(TeeDepartment dept:deptOther){
								if(data.equals(dept.getDeptFullName())){
									tmp = dept.getUuid()+"";
									opts.append("<option selected value=\""+dept.getDeptFullName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
								}else{
									opts.append("<option value=\""+dept.getDeptFullName()+"\" extValue=\""+dept.getUuid()+"\">"+dept.getDeptFullName()+"</option>");
								}
							}
						}
						opts.append("</select>");
					}else if("26".equals(type)){//当前用户姓名+当前日期
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						defaultValue = ""+flowRunPrcs.getPrcsUser().getUserName()+" "+sdf.format(new Date());
						defaultExtValue = ""+flowRunPrcs.getPrcsUser().getUuid();
						foreceRefresh = true;
					}else if("27".equals(type)){
						foreceRefresh = true;
					}
				}
				
				//针对可写字段进行赋值
				if(writable){
					//（如果开启自动赋值  并且 原数据为空的话）或者强制刷新的话   则获取默认数据
					if((auto && "".equals(data)) || foreceRefresh){
						attrs.put("value", defaultValue);
						extValue = defaultExtValue;
						if(!"".equals(tmp)){
							extValue = tmp;
						}
						
						if("27".equals(type)){//签名图
							attrs.put("value", flowRunPrcs.getPrcsUser().getUserName());
							TeeAttachment attach = flowRunPrcs.getPrcsUser().getAttach();
							if(attach!=null && attach.getSid()>0){
								InputStream in = null;
								byte[] data1 = null;
								//读取图片字节数组
								try 
								{
									in = new FileInputStream(attach.getFilePath());        
									data1 = new byte[in.available()];
									in.read(data1);
									in.close();
								} 
								catch (IOException e) 
								{
									e.printStackTrace();
								}
								//对字节数组Base64编码  
								BASE64Encoder encoder1 = new BASE64Encoder();  
								String encode = encoder1.encode(data1);//返回Base64编码过的字节数组字符串  
								encode="data:image/png;base64,"+encode.replaceAll("\r|\n", "");
								extValue=encode;
							}
						}
						
					}else{
						attrs.put("value", data);
					}
				}else{//针对不可写字段赋值
					attrs.put("value", data);
					if("27".equals(type)){//签名图
						attrs.put("value", "<img src=\""+extValue+"\" />");
					}
				}
				
				//自动赋值
//				if(!readonly && auto && "".equals(data)){
//					attrs.put("value", defaultValue);
//				}else{
//					attrs.put("value", data);
//				}
				
				attrs.put("writable", String.valueOf(writable));
				attrs.put("required", String.valueOf(required));
				if(readonly){
					attrs.put("readonly", String.valueOf(readonly));
					tag.getAttributes().put("onfocus","");
				}
				
				String tagString = tag.toString();
				
				if("27".equals(type)){//如果是图标的话
					tagString = "<span style='display:none'>"+tagString+"</span><img src=\""+extValue+"\" />";
				}
				
				if(!writable){
					attrs.put("disabled", "disabled");
					opts.delete(0, opts.length());
					opts.append("<select onchange=\"optChanged(this,"+formItem.getItemId()+")\" name=\""+name+"\" id=\""+name+"\" "+(writable?"writable":"disabled")+" title=\""+title+"\">");
					if(!"".equals(data)){
						opts.append("<option value=\""+data+"\" extValue=\""+extValue+"\">"+data+"</option>");
					}
					opts.append("</select>");
				}
				
				if(opts.length()!=0){
					return wrap(writable,opts.toString()+"<input type=\"hidden\" name=\"EXTRA_"+formItem.getItemId()+"\" id=\"EXTRA_"+formItem.getItemId()+"\" "+(writable?"writable":"disabled")+" value=\""+extValue+"\"/>",attrs.get("value"),"",extValue,formItem.getItemId(),title);
				}
				return wrap(writable,tagString+"<input type=\"hidden\" name=\"EXTRA_"+formItem.getItemId()+"\" id=\"EXTRA_"+formItem.getItemId()+"\" "+(writable?"writable":"disabled")+" value=\""+extValue+"\"/>",attrs.get("value"),extValue,"",formItem.getItemId(),title);
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
		String extValue = TeeStringUtil.getString(datas.get("EXTRA_"+formItem.getItemId()));
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		String data = TeeStringUtil.getString(flowFormData.getData());
		String model = attrs.get("model");
		String toString="<span style=\""+attrs.get("style")+"\">"+data+"</span>"+"<input class=\"FORM_PRINT\" type=\"hidden\" value=\""+data+"\" title=\""+formItem.getTitle()+"\" />";
		if(model!=null){
			Map<String,String> modelObj = jsonUtil.JsonStr2Map(model);
			String type = modelObj.get("type");
			if("27".equals(type)){
				toString="<span style=\"display:none;\">"+toString+"</span><img src=\""+extValue+"\"/>";
			}
		}
		   
		
		return toString;
	}

}
