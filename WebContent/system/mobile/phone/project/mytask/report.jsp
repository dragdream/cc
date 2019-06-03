<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //任务主键
   int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"),0);
 
%>

<!DOCTYPE HTML>
<html>
<head>
<title>我的任务</title>
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
		<h1 class="mui-title">任务汇报</h1>
		<span class="mui-icon mui-icon-plusempty mui-pull-right" id="addBtn" style="display: none"></span>
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content">
		<div class="">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	
	<nav class="mui-bar mui-bar-tab" id="footerDiv" >
		<a id="a1" class="mui-tab-item mui-active" >
			<!-- <span class="mui-icon mui-icon-compose"></span> -->
			<span class="mui-tab-label" >详情</span>
		</a>
		<a id="a2" class="mui-tab-item mui-active" style="font-weight:bold">
			<!-- <span class="mui-icon mui-icon-search"></span> -->
			<span class="mui-tab-label" >汇报</span>
		</a>
		<a id="a3" class="mui-tab-item mui-active" onclick="">
			<!-- <span class="mui-icon mui-icon-search"></span> -->
			<span class="mui-tab-label" >问题</span>
		</a>
</nav>
	<script>
	var taskId=<%=taskId%>;
	var page = 1;
	//初始化方法
	function doInit(){
		
		isEnd();
		//详情
		a1.addEventListener("tap",function(){
			window.location = 'taskDetail.jsp?sid='+taskId;
		});
		
		//问题
		a3.addEventListener("tap",function(){
			window.location = 'question.jsp?taskId='+taskId;
		});
		getList();
	}
	
	
	//根据任务主键  判断任务状态  从而判断是否可以新增汇报
	function isEnd(){
		var url=contextPath+"/taskController/getInfoBySid.action";
		mui.ajax(url,{
			type:"POST",
			dataType:"JSON",
			data:{sid:taskId},
			timeout:10000,
			async:false,
			success:function(text){
				var json = eval("("+text+")");
				if(json.rtState){	
					var status=json.rtData.status;
					if(status==0){//办理中
						$("#addBtn").show();	
					}
				}else{
					alert("数据获取失败！");
				}
			}
		});
	}

	
	//加载数据
	function getList() {
		var url=contextPath+"/taskReportController/getReportListByTaskId.action?taskId="+taskId;
		
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			//data:{state:-1,rows:20,page:page++},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rtData.length;i++){
					var item = json.rtData[i];
					render.push("<li class=\"mui-table-view-cell mui-media\"  sid='"+item.sid+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<span style='position:absolute;right:10px;transform: rotate(25deg);font-size:15px;'>"+item.progress+"%</span>");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px;margin-right:25px'>");
					
					render.push("<span>"+item.content+"</span></p>");
					render.push("<p style='font-size:12px;margin-top:5px'>"+item.createTimeStr+"</p>");
					render.push("</div>");
					render.push("</li>");
				}
				$("#list").html(render.join("")+"<br/><br/>");
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
			},
			error:function(){
				
			}
		});
		
	}
	
	
	//汇报详情
	function detail(){
		var sid = this.getAttribute("sid");
		window.location = "reportDetail.jsp?sid="+sid;
	}
	
	
	
	
	mui.ready(function() {
		
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "index.jsp";
		});//返回
		
		addBtn.addEventListener("tap",function(){
			window.location.href = "addReport.jsp?taskId="+taskId;
		});//新建
		
	});
	</script>
</body>
</html>