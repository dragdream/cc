<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String parentTaskId = request.getParameter("parentTaskId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/sys.js"></script>
<style>
</style>
<script>
function doInit(){
	$("#layout").layout({auto:true});
	
	$.jBox.tip("正在加载…","loading");
	tools.requestJsonRs(contextPath+"/coWork/showTasksReport.action",{},true,function(json){
		var list = json.rtData;
		var render = [];
		for(var i=0;i<list.length;i++){
			var item = list[i];
			var clazz = item.finish?("item_finish"):(item.overtime?"item_over":"item_inner");
			render.push("<div class=\"item "+clazz+"\" onclick=\"graphics("+item.sid+")\">");
			render.push("<div class=\"item_title\">"+item.taskTitle+"</div>");
			render.push("<div class=\"item_content\">");
			render.push("负责人："+item.chargerName+"<br/>");
			render.push("开始时间："+item.startTime+"<br/>");
			render.push("结束时间："+item.endTime+"<br/>");
			render.push("</div>");
			render.push("<div class=\"item_probar\" progress='"+item.progress+"'>");
			render.push("</div>");
			render.push("</div>");
			
		}
		$("#container").append(render.join(""));
		
		$(".item_probar").each(function(i,obj){
			$(obj).progressBar(obj.getAttribute("progress"), {showText: true});
		});
		
		
		if(list.length==0){
			messageMsg("没有可监控的任务数据。","container","info");
		}
		
		$.jBox.tip("加载完毕","success");
		
	});
	
}

function graphics(taskId){
	openFullWindow(contextPath+"/system/subsys/cowork/taskNetGraphics.jsp?taskId="+taskId,"任务跟踪");
}

</script>
<style>
.item{
height:150px;
width:200px;
float:left;
margin:10px;
color:white;
padding:5px;
border-radius:3px;
-webkit-border-radius:3px;
-mozilla-border-radius:3px;
cursor:pointer;
}

.item_inner{
background:#27c1b9;
}

.item_over{
background:#bdc900;
}

.item_finish{
background:#e87151;
}

.item_title{
margin-top:10px;
font-size:16px;
font-weight:bold;
font-family:微软雅黑;
margin-bottom:10px;
}

.item_content{
font-size:12px;
color:white;
margin-bottom:10px;
}

.item_probar{

}

</style>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden;">
<div id="layout">
	<div layout="north" height="45">
		<div class="moduleHeader">
			<div>
				<b><i class="glyphicon glyphicon-list-alt"></i>&nbsp;任务汇总</b>
			</div>
		</div>
	</div>
	<div layout="center" id="container" style="padding:10px;overflow:auto">
		
	</div>
</div>
</div>
</body>
</html>