<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>通讯簿</title>
<%@ include file="/system/mobile/header.jsp" %>
<%
	String name = request.getParameter("name");
	String sex = request.getParameter("sex");
	String phoneNo = request.getParameter("phoneNo");
	String seqIds = request.getParameter("seqIds");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/docs.min.css"/>
<script type="text/javascript">
var name = "<%=name%>";
var sex = "<%=sex%>";
var phoneNo = "<%=phoneNo%>";
var seqIds = "<%=seqIds%>";

var list;
function doInit(){
	
	$("#content").html("<center>正在加载数据……</center>");
	tools.requestJsonRs(contextPath+"/teeAddressController/getAddresesByGroupId.action?page=1&rows=10000000&",{psnName:name,sex:sex,mobilNo:phoneNo,seqIds:seqIds},true,function(json){
		var render = [];
		list = json.rows;
		for(var i=0;i<list.length;i++){
// 			render.push("<div class='bg-info'><b>姓名：</b>"+list[i].psnName+"&nbsp;&nbsp;&nbsp;&nbsp;<b>手机：</b>"+list[i].mobilNo+"</div>");
			$("#content").html("");
			if(list[i].sex==1){//女
				render.push("<div class=\"bs-callout bs-callout-danger\">");
			}else{//男
				render.push("<div class=\"bs-callout bs-callout-info\">");
			}
			render.push("<h4>"+list[i].psnName+"</h4>");
			render.push("<p>工作电话：<a href='tel:"+list[i].telNoDept+"'>"+list[i].telNoDept+"</a></p>");
			render.push("<p>手机：<a href='tel:"+list[i].mobilNo+"'>"+list[i].mobilNo+"</a></p>");
			render.push("<p>邮箱：<a href='mailto:"+list[i].email+"'>"+list[i].email+"</a></p>");
			render.push("</div>");
		}
		if(list.length!=0){
			$("#content").append(render.join(""));
		}else{
			$("#content").html("");
			$("#content").append("<center>无符合条件的数据</center>");
		}
		
	});
	
}

function goBack(){
	window.history.go(-1);
}


</script>
<style>
.bg-info{
padding:10px;
font-size:16px;
background:#d9edf7;
}
</style>
</head>
<body onload="doInit()" style="padding:10px">
<button type="button" class="btn btn-default btn-lg btn-block" onclick="goBack()">返&nbsp;&nbsp;回</button>
<div id="content">
</div>
<button type="button" class="btn btn-default btn-lg btn-block" onclick="goBack()">返&nbsp;&nbsp;回</button>
</body>
</html>