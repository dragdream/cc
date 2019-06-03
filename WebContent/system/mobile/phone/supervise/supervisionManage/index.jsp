<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>督办管理 </title>
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
</style>
</head>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">督办管理</h1>
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
	var page = 1;
	/**
	 * 下拉刷新具体业务实现
	 */
	function doInit() {
		var url=contextPath+"/supTypeController/getSupTypeTree.action";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			//data:{state:-1,rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rtData.length;i++){
					var item = json.rtData[i];
					render.push("<li class=\"mui-table-view-cell mui-media\"   typeSid='"+item.id+"' typeName='"+item.name+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					
					render.push("<span>"+item.name+"</span></p>");
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
		var typeSid = this.getAttribute("typeSid");
		var typeName=this.getAttribute("typeName");
		window.location.href="list.jsp?typeSid="+typeSid+"&&typeName="+typeName;
	}
	
	
	
	
	mui.ready(function() {
		
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "../index.jsp";
		});//返回
		
	});
	</script>
</body>
</html>