<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
   String projectName=TeeStringUtil.getString(request.getParameter("projectName"));
%>

<!DOCTYPE HTML>
<html>
<head>
<title>项目文档 </title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}


.mui-media-body{
	line-height:42px;
}

</style>
</head>
<body onload="doInit();">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title"><%=projectName %></h1>
		
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content">
		<div class="">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	
	
	<script>
	var projectId="<%=projectId%>";
	var page = 1;
	
	//初始化
	function doInit() {
		var url=contextPath+"/projectController/getProjectTree.action?id="+projectId;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rtData.length;i++){
					var item = json.rtData[i];
					render.push("<li class=\"mui-table-view-cell mui-media\"   sid="+item.id+" folderName='"+item.name+"' >");
					
					render.push("<img class=\"mui-media-object mui-pull-left\" src=\"../imgs/icon_wjj.png\">");
			        render.push("<div class=\"mui-media-body\">");    
			        render.push("<p class='mui-ellipsis'>"+item.name+"</p>");        
			        render.push("</div>");    
			        
					render.push("</li>");
				}
				$("#list").html(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//项目详情
	function detail(){
		var sid = this.getAttribute("sid");
		var folderName = this.getAttribute("folderName");
		
		window.location.href="file.jsp?diskId="+sid+"&&diskName="+folderName+"&&projectId="+projectId;
	}
	
	
	
	
	mui.ready(function() {
	
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "index.jsp";
		});//返回
		
		
		
	});
	</script>
</body>
</html>