<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<% 
	String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"));
	String bisKey = TeeStringUtil.getString(request.getParameter("bisKey"));
%>
<title>全质量开发平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var moduleId = "<%=moduleId%>";
var bisKey = "<%=bisKey%>";
function doInit(){
	$.jBox.tip("数据加载中……","loading");
	tools.requestJsonRs(contextPath+"/myModule/getFormHtml.action",{moduleId:moduleId,bisKey:bisKey},true,function(json){
		var form = json.rtData;
		$("#form").html(form);
		$.jBox.tip("加载完毕","success");
	});
}

function commit(){
	if(!$("#form").form("validate")){
		return false;
	}
	
	var url = contextPath+"/myModule/saveOrUpdate.action";

	var params = tools.formToJson($("#form"));
	params["moduleId"] = moduleId;
	params["bisKey"] = bisKey;
	var json = tools.requestJsonRs(url,params);
	alert(json.rtMsg);
	if(json.rtState){
		window.location = "list.jsp?moduleId="+moduleId;
	}
}

function backTo(){
	window.location = "list.jsp?moduleId="+moduleId;
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden">
<div style="background:#f0f0f0;position:absolute;top:0px;height:30px;left:0px;right:0px;padding:5px;border-bottom:1px solid #e2e2e2">
	<button class="btn btn-primary" onclick="commit()">保存</button>
	&nbsp;&nbsp;
	<button class="btn btn-default" onclick="backTo()">返回</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<div id="form" style="font-size:12px;position:absolute;top:50px;bottom:0px;left:0px;right:0px;padding:5px;overflow:auto">
	
</div>
</body>
</html>
