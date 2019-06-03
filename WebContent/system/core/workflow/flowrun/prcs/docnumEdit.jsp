<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>文号修改</title>
<script>

var contextPath = "<%=contextPath%>";
var runId = <%=runId%>;
var flowId = <%=flowId%>;
function doInit(){
	var url = contextPath+"/docNumController/getCurDocAndMaxDoc.action?runId="+runId+"&flowId="+flowId;
	var json = tools.requestJsonRs(url);
	$("#curNum").val(json.rtData.curNum);
	$("#maxNum").val(json.rtData.maxNum);
	$("#editNum").val(json.rtData.curNum);
}

function generate(){
	var item = $("#editNum");
	var editNum = Number(item.val());
	if(editNum==""){
		alert("请输入大于等于0的编号数");
		return false;
	}
	if(isNaN(editNum)){
		alert("请输入大于等于0的编号数");
		return false;
	}
	editNum = parseInt(editNum);
	if(editNum<0){
		alert("请输入大于等于0的编号数");
		return false;
	}

	if(window.confirm("确认修改成当前编号吗？","确认")){
		var url = contextPath+"/docNumController/diynamicEditDocNum.action";
		var json = tools.requestJsonRs(url,{runId:runId,flowId:flowId,editNum:editNum});
		if(!json.rtState){
			alert(json.rtMsg);
			return false;
		}
		return json.rtData;
	}
	return false;
	
}

</script>
</head>
<body onload="doInit()" style="font-size:14px;">
<br/>
当前编号值：<input type="text" readonly class="BigInput readonly" id="curNum"/>
<br/><br/>
最大编号值：<input type="text" readonly class="BigInput readonly" id="maxNum"/>
<br/><br/>
编号修改值：<input type="text" class="BigInput" id="editNum"/>
<br/>
</body>

</html>