<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>


<script src="/common/gantt/js/jquery.fn.gantt.js"></script>
<link href="/common/gantt/css/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目甘特图</title>
</head>
<script>
//项目主键
var uuid="<%=uuid%>";

function doInit(){
	$(".gantt").gantt({
		source: contextPath+"/taskController/gantt.action?projectId="+uuid+"&rand="+new Date().getTime(),
		scale: "days",
		minScale: "days",
		maxScale: "months",
		 itemsPerPage: 100000000,  
		onItemClick: function(dataObj) {
			
			detail(dataObj);
		}
	});
}


//获取任务详情
function detail(sid){
	var url=contextPath+"/system/subsys/project/projectdetail/basic/taskDetail.jsp?sid="+sid+"&&projectId="+uuid;
	bsWindow(url ,"任务详情",{width:"600",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="关闭"){
			return true;
		}
	}}); 
}
</script>
<style>
	.colorBlock{
		display:inline-block;
		width:15px;
		height:15px;
		vertical-align:bottom;
	}
	.colorBlock + a{
		margin:0 5px;
		color:#333;
		text-decoration: none;
	}
	.gantt{
		margin-top:30px;
	}
</style>
<body onload="doInit()">
<div id='color_type' class="setHeight fr" style="margin-top: -22px;">
	<span class='colorBlock' style='background-color:#d0e4fd;'></span>
	<a> 未开始</a>
	<span class='colorBlock' style='background-color:#d8eda3;'></span>
	<a> 进行中</a>
	<span class='colorBlock' style='background-color:#f9c4e1;'></span>
	<a> 已结束</a>
</div>
   <div class="gantt"></div>
</body>
</html>