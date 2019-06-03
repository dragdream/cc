<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int status=TeeStringUtil.getInteger(request.getParameter("status"),3);//项目状态   3进行中   5已结束
   String  statusDesc="";
   if(status==3){
	   statusDesc="进行中";  
   }else if(status==5){
	   statusDesc="已结束";  
   }
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
</style>
</head>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">项目文档（<%=statusDesc %>）</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content">
		<div class="">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=3';">进行中</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=5';">已结束</li>
		  </ul>
	</div>
	
	<script>
	var status=<%=status%>;
	var page = 1;
	
	
	/**
	 * 下拉刷新具体业务实现
	 */
	function doInit() {
		var url=contextPath+"/projectController/getProjectTree.action?status="+status;
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
					render.push("<li class=\"mui-table-view-cell mui-media\"   uuid='"+item.id+"' projectName='"+item.name+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					
					render.push("<span>"+item.name+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.params.num+"</p>");
					render.push("<p style='font-size:12px'>"+item.params.beginTime+"&nbsp;~&nbsp;"+item.params.endTime+"</p>");
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
		var uuid = this.getAttribute("uuid");
		var projectName=this.getAttribute("projectName");
		window.location.href="fileFolder.jsp?projectId="+uuid+"&&projectName="+projectName;
	}
	
	
	
	
	mui.ready(function() {
		
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "../index.jsp";
		});//返回
		
		
		
	});
	</script>
</body>
</html>