<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
	String personName = request.getParameter("personName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
function doInit(){
	getHrCodeByParentCodeNo("PM_REHAB_TYPE" , "rehabType");
}

function commit(callback){
	if(checkForm()&& $("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		param["isDel"]=0;
		parent.$.MsgBox.Confirm ("提示", "是否恢复关联OA用户登录状态为允许登录？",function(){
			 param["isDel"]=1;
				var url = contextPath+"/TeeHumanRehabController/addHumanRehab.action";
				var json = tools.requestJsonRs(url,param);
				if(json.rtState){
					callback(json.rtState);
				}else{
					$.MsgBox.Alert_auto(json.rtMsg);
				}
		 });
	}
}
function checkForm(){
	if($("#pos").val()=="" || $("#pos").val()=="null" || $("#pos").val()==null){
		$.MsgBox.Alert_auto("请输入担任职务！");
		return false;
	}
	  if($("#regTimeDesc").val() && $("#planTimeDesc").val() && $("#realTimeDesc").val()){
		    if(document.getElementById("regTimeDesc").value > document.getElementById("planTimeDesc").value){
		    	$.MsgBox.Alert_auto("申请时间不能大于拟复职时间！");
		      return false;
			 }
		    if(document.getElementById("regTimeDesc").value > document.getElementById("realTimeDesc").value){
		    	$.MsgBox.Alert_auto("申请日期不能大于实际复职日期");
			    return false;
			    
		    }
		    if(document.getElementById("planTimeDesc").value > document.getElementById("realTimeDesc").value){
		    	$.MsgBox.Alert_auto("拟复职日期不能大于实际复职日期");
			    return false;
			    
		    }
	  }
	  if($("#regTimeDesc").val() && $("#planTimeDesc").val()){
		    if(document.getElementById("regTimeDesc").value > document.getElementById("planTimeDesc").value){
		    	$.MsgBox.Alert_auto("申请时间不能大于拟复职时间！");
		      return false;
			 }
	  }

	  if($("#planTimeDesc").val() && $("#realTimeDesc").val()){
		    if(document.getElementById("planTimeDesc").value > document.getElementById("realTimeDesc").value){
		    	$.MsgBox.Alert_auto("拟复职日期不能大于实际复职日期");
			    return false;
			    
		    }
	  }
	  if($("#regTimeDesc").val() && $("#realTimeDesc").val()){
		    if(document.getElementById("regTimeDesc").value > document.getElementById("realTimeDesc").value){
		    	$.MsgBox.Alert_auto("申请日期不能大于实际复职日期");
			    return false;
			    
		    }
	  }
	return true;
}
</script>
<style>
html{
   background-color: #f2f2f2;
}
	td{
		line-height:28px;
		min-height:28px;
	}
</style>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_dabj.png">
		<span class="title">新增/编辑  <%= personName %> 的复职信息</span>
	</div>
</div>
<form id="form1" name="form1">
	<table class="TableBlock" width="100%" align="center">
	    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	    </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				担任职务：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text"  required class="BigInput" id="pos" name="pos"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				复职类型：
			</td>
			<td>
				<select style="height: 23px;width: 200px;" class="BigSelect" id="rehabType" name="rehabType" >
				</select>
			</td>
		 </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				申请日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='regTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='regTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				拟复职日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='planTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='planTimeDesc' class="Wdate BigInput" />
			</td>
		 </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				实际复职日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='realTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='realTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				工资恢复日期：
			</td>
			<td>
				<input style="height: 23px;width: 200px;" type="text" id='payTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='payTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				复职部门：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="rehabDept" name="rehabDept"/>
			</td>
		 </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				复职手续办理：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="rehabDetail" name="rehabDetail"></textarea>
			</td>
		 </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				备注：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="remark" name="remark" ></textarea>
			</td>
		</tr>
	</table>
	<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" value="<%=humanDocSid%>"/>
</form>
</body>
</html>