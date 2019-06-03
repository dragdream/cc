<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.diaryTable{
	border-collapse:collapse;
}
.diaryTable td{
	border:1px solid #e2e2e2;
	font-size:14px;
	padding:5px;
}
</style>
<script>
var sid='<%=sid%>';
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/diaryController/getDiaryById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			var attaches = json.rtData.attacheModels;
			for(var i=0;i<attaches.length;i++){
				attaches[i].priv=1+2;
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}else{
			alert(json.rtMsg);
		}
	}
}
</script>
</head>
<body onload="doInit();">
    <form method="post" name="form1" id="form1">
		<table style="width:80%;font-size:12px" align="center" class='TableBlock'>
			<tr>
				<td class="TableHeader" width="20%;" >日志标题:</td>
				<td class="TableData"><div id='title'></div></td>
			</tr>
			<tr>
				<td class="TableHeader"  width="20%;">类型：</td>
				<td class="TableData">
					<div id='typeDesc'></div>
				</td>
			</tr>
			<tr>
				<td class="TableHeader"  width="20%;">日志内容：</td>
				<td class="TableData">
					<div id='content'></div>
				</td>
			</tr>
			<tr>
				<td class="TableHeader"  width="20%;">附件内容：</td>
				<td class="TableData">
					<div id='attachments'></div>
				</td>
			</tr>	
		</table>
		<input type="hidden" id='sid' name="sid" value="0"/>
		<input type="hidden" id="createTimeDesc" name="createTimeDesc"/>
	</form>
</body>
</html>