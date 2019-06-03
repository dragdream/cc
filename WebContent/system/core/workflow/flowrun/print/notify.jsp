<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>工作流转发至公告</title>
<%@ include file="/header/header.jsp" %>
<%
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;

function forward(){
	var val = 0;
	$(".x").each(function(i,obj){
		if(obj.checked){
			val+=parseInt(obj.value);
		}
	});
	window.location = contextPath+"/system/core/base/notify/manage/addNotify.jsp?runId="+runId+"&frpSid="+frpSid+"&view="+val;
}
</script>

</head>
<body onload="doInit()" style="margin:0px;overflow:hidden">
<div class="base_layout_top">
	<span class="easyui_h1">工作流转发至公告</span>
</div>
<div class="base_layout_center">
	<br/>
	<table class="TableBlock" style="width:100%">
		<tr>
			<td class="TableData TableHeader" style="width:100px;">
				转发内容：
			</td>
			<td class="TableData">
				<input checked class="x" type="checkbox" id="form" value="1" onclick="refresh(this)"/><label for="form">&nbsp;表单</label>
				<input checked class="x" type="checkbox" id="attach" value="2" onclick="refresh(this)"/><label for="attach">&nbsp;附件</label>
<!-- 				<input class="x" type="checkbox" id="docInfo" value="32" onclick="refresh(this)"/><label for="docInfo">&nbsp;正文</label> -->
				<input checked class="x" type="checkbox" id="feedback" value="4" onclick="refresh(this)"/><label for="feedback">&nbsp;会签</label>
				<input checked class="x" type="checkbox" id="graph" value="8" onclick="refresh(this)"/><label for="graph">&nbsp;流程步骤</label>
			</td>
		</tr>
	</table>
	<br/>
	<center>
		<button class="btn btn-default" onclick="forward()">确定</button>
		&nbsp;&nbsp;
		<button class="btn btn-default" onclick="CloseWindow();">关闭</button>
	</center>
</div>
</body>
</html>