<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" import="com.tianee.webframe.util.*"%>
<%@ include file="/header/header.jsp" %>
<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script>
var prcsId = <%=prcsId%>;

function doInit(){
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessInfo.action";
	var json = tools.requestJsonRs(url,{prcsSeqId:prcsId});
	var fp = json.rtData;
	
	$("#pluginClass").attr("value",fp.pluginClass);
	$("#triggerUrl").attr("value",fp.triggerUrl);
}

//提交
function commit(){
	var url = contextPath+"/flowProcess/updateExtInfo.action";
	var para = {pluginClass:$("#pluginClass").val(),triggerUrl:$("#triggerUrl").val(),prcsId:prcsId};
	var json = tools.requestJsonRs(url,para);
// 	alert(json.rtMsg);
}

</script>

</head>
<body onload="doInit()">
<center>
	<table class="TableBlock" style="width:80%">
		<tr class="TableHeader">
			<td colspan="2">触发器</td>
		</tr>
		<tr>
			<td class="TableData">接口路径：</td>
			<td class="TableData">
				<input type="text" name="triggerUrl" id="triggerUrl" class="BigInput" style="width:280px" />
			</td>
		</tr>
		<tr class="TableHeader">
			<td colspan="2">业务插件</td>
		</tr>
		<tr>
			<td class="TableData">插件类路径：</td>
			<td class="TableData">
				<input type="text" name="pluginClass" id="pluginClass" class="BigInput" style="width:280px" />
			</td>
		</tr>
		<tr>
			<td class="TableData" colspan="2">
				发文插件：
				<br/>com.tianee.oa.core.workflow.plugins.doc.TeeDocSendWfPlugin
				<br/><br/>
				传阅插件：
				<br/>com.tianee.oa.core.workflow.plugins.doc.TeeDocViewWfPlugin
				<br/><br/>
				发文与传阅插件：
				<br/>com.tianee.oa.core.workflow.plugins.doc.TeeDocSendAndViewWfPlugin
				<br/><br/>
				公告通知审批插件：
				<br/>com.tianee.oa.core.workflow.plugins.NotifyAuditPlugin
				<br/><br/>
				新闻审批插件：
				<br/>com.tianee.oa.core.workflow.plugins.NewsAuditPlugin
				<br/><br/>
				部门费用计划申请审批插件：
				<br/>com.tianee.oa.core.workflow.plugins.BudgetDeptAuditPlugin
				<br/><br/>
				个人费用计划申请审批插件：
				<br/>com.tianee.oa.core.workflow.plugins.BudgetUserAuditPlugin
			    <br/><br/>
			            外出申请插件：
			    <br/>com.tianee.oa.core.workflow.plugins.AttendOutPlugin
			    <br/><br/>
			            请假申请插件：
			    <br/>com.tianee.oa.core.workflow.plugins.AttendLeavePlugin
			    <br/><br/>
			            出差申请插件：
			    <br/>com.tianee.oa.core.workflow.plugins.AttendEvectionPlugin
			    <br/><br/>
			            加班申请插件：
			    <br/>com.tianee.oa.core.workflow.plugins.AttendOvertimePlugin
			</td>
		</tr>
	</table>
</center>
</body>
</html>