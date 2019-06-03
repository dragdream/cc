<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //项目主键
   String projectId=TeeStringUtil.getString(request.getParameter("projectId")); 
%>

<!DOCTYPE HTML>
<html>
<head>
<title>项目查询</title>
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
		<h1 class="mui-title">任务列表</h1>
	
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content ">
		<div class="">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>

	<nav class="mui-bar mui-bar-tab" id="footerDiv">
		   <a id="a1" class="mui-tab-item mui-active">
				<!-- <span class="mui-icon mui-icon-compose"></span> -->
				<span class="mui-tab-label" >项目详情</span>
			</a>
			<a id="a2" class="mui-tab-item mui-active" style="font-weight:bold">
				<!-- <span class="mui-icon mui-icon-search"></span> -->
				<span class="mui-tab-label" >任务列表</span>
			</a>
	</nav>
	<script>
	var projectId="<%=projectId%>";
	var originalData = [];//全部的数据
	
	/**
	 * 下拉刷新具体业务实现
	 */
	function doInit() {
		var url=contextPath + "/taskController/getTaskListByProjectId.action?projectId="+projectId;
		
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{},
			timeout:10000,
			async:false,
			success:function(json){
				originalData = eval("("+json+")");
			},
			error:function(){
				
			}
		});
		
		renderList("");
	}
	
	//渲染指定层级节点下的children
	function renderList(parentLevel){
		$("#list").html("");
		var render = [];
		var arr = originalData;
		if(parentLevel==""){//渲染第一层节点
			arr = originalData;
		}else{//渲染指定层的节点  2,3
			var sp = parentLevel.split(",");
			for(var i=0;i<sp.length;i++){
				var index = parseInt(sp[i]);
				arr = arr[index].children;
			}
		}
		
		for(var i=0;i<arr.length;i++){
			var item = arr[i];
			//var readType = item.readType;
			var hasChild = 0;
			if(item.children.length!=0){
				hasChild = 1;
			}
			render.push("<li class=\"mui-table-view-cell mui-media\"   uuid='"+item.sid+"' level='"+(parentLevel==""?i:(parentLevel+","+i))+"' hasChild='"+hasChild+"'>");
			
			render.push("<div class=\"mui-media-body\">");
			render.push("<span style='position:absolute;right:10px;transform: rotate(25deg);font-size:15px;'>"+item.progress+"%</span>");
			render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
			
			render.push("<span>"+item.taskName+"</span></p>");
			render.push("<p style='font-size:12px'>负责："+item.managerName+"</p>");
			render.push("<p style='font-size:12px'>"+item.beginTimeStr+"&nbsp;~&nbsp;"+item.endTimeStr+"&nbsp;&nbsp;&nbsp;共"+item.days+"天</p>");
			render.push("</div>");
			render.push("</li>");
		}
		
		$("#list").html(render.join("")+"<br/><br/>");
		
		$(".mui-media").each(function(i,obj){
			obj.removeEventListener("tap",detail);
			obj.addEventListener("tap",detail);
		});
	}
	
	var taskUuid = "";
	var taslLevel = "";
	//项目详情
	function detail(){
		taskUuid = this.getAttribute("uuid");
		taslLevel = this.getAttribute("level");
		var hasChild = this.getAttribute("hasChild");
		if(hasChild=="1"){//如果有孩子的话，那就提示一个信息
			mui('#sheet1').popover('toggle');
		
		}else{
			taskDetail(taskUuid); 
		}
		//window.location = "projectDetail.jsp?uuid="+uuid;
	}
	
	function goBack(){
		var sp = taslLevel.split(",");
		if(taslLevel==""){
			//history.go(-1);
			window.location.href="index.jsp";
		}else{
			if(sp.length==1){
				renderList("");
				taslLevel = "";
			}else{
				sp = sp.splice(sp.length-1,2);
				taslLevel = sp.join(",");
				renderList(taslLevel);
			}
		}
	}
	
	mui.ready(function() {
		//返回
		backBtn.addEventListener("tap",function(){
			goBack();
		});
		
		//项目详情
		a1.addEventListener("tap",function(){
			window.location = 'projectDetail.jsp?uuid=<%=projectId%>';
		});
		
	});
	
	function taskDetail(uuid){
		mui('#sheet1').popover('toggle');
		window.location.href="taskDetail.jsp?uuid="+uuid;
		
	}
	</script>
	
	
	<div id="sheet1" class="mui-popover mui-popover-bottom mui-popover-action ">
	    <!-- 可选择菜单 -->
	    <ul class="mui-table-view">
	      <li class="mui-table-view-cell" onclick="taskDetail(taskUuid)">
	        <a href="#">任务详情</a>
	      </li>
	      <li class="mui-table-view-cell" onclick="mui('#sheet1').popover('toggle');renderList(taslLevel)">
	        <a href="#">子任务列表</a>
	      </li>
	    </ul>
	    <!-- 取消菜单 -->
	    <ul class="mui-table-view">
	      <li class="mui-table-view-cell">
	        <a href="#sheet1"><b>取消</b></a>
	      </li>
	    </ul>
	</div>
</body>
</html>