<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int status=TeeStringUtil.getInteger(request.getParameter("status"),0);//默认办理中
   String  statusDesc="";
   if(status==0){
	   statusDesc="办理中";  
   }else if(status==1){
	   statusDesc="已结束";  
   }
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
<body>
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">我的任务（<%=statusDesc %>）</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>
	
	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=0';">办理中</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=1';">已结束</li>
		  </ul>
	</div>
	
	<script>
	var status=<%=status%>;
	var page = 1;
	mui.init({
		pullRefresh: {
			container: '#pullrefresh',
			down: {
				callback: pulldownRefresh
			},
			up: {
				contentrefresh: '正在加载...',
				callback: pullupRefresh
			}
		}
	});
	
	/**
	 * 下拉刷新具体业务实现
	 */
	function pulldownRefresh() {
		page = 1;
		var url=contextPath + "/taskController/getTaskListByStatus.action?status="+status;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<span style='position:absolute;right:10px;transform: rotate(25deg);font-size:15px;'>"+item.progress+"%</span>");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.taskName+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.taskNo+"</p>");
					render.push("<p style='font-size:12px'>"+item.beginTimeStr+"&nbsp;~&nbsp;"+item.endTimeStr+"&nbsp;&nbsp;&nbsp;共"+item.days+"天</p>");
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
	
	/**
	 * 上拉加载具体业务实现
	 */
	function pullupRefresh() {
		var url=contextPath + "/taskController/getTaskListByStatus.action?status="+status;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					render.push("<li class=\"mui-table-view-cell mui-media\"   sid='"+item.sid+"' >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<span style='position:absolute;right:10px;transform: rotate(25deg);font-size:15px;'>"+item.progress+"%</span>");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					render.push("<span>"+item.taskName+"</span></p>");
					render.push("<p style='font-size:12px'>"+item.taskNo+"</p>");
					render.push("<p style='font-size:12px'>"+item.beginTimeStr+"&nbsp;~&nbsp;"+item.endTimeStr+"&nbsp;&nbsp;&nbsp;共"+item.days+"天</p>");
					render.push("</div>");
					render.push("</li>");
				}
				$("#list").append(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",detail);
					obj.addEventListener("tap",detail);
				});
			},
			error:function(){
				
			}
		});
		
	}
	
	
	
	//任务详情
	function detail(){
		
		var sid = this.getAttribute("sid");
		
		//先判断是不是第一次办理    设置实际开始时间
		var url=contextPath+"/taskController/begin.action";
		
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{sid:sid},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					window.location = "taskDetail.jsp?sid="+sid;
				}	
			}
		});	
	}
	
	
	
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
		
		backBtn.addEventListener("tap",function(){
			window.location.href = "../index.jsp";
		});//返回
		
		
		
	});
	</script>
</body>
</html>