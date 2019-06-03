<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int status=TeeStringUtil.getInteger(request.getParameter("status"),0);//问题处理状态
   String  statusDesc="";
   if(status==0){
	   statusDesc="待解决";  
   }else if(status==1){
	   statusDesc="已解决";  
   }
%>

<!DOCTYPE HTML>
<html>
<head>
<title>项目问题</title>
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
		<h1 class="mui-title">项目问题（<%=statusDesc %>）</h1>
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
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=0';">待解决</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=1';">已解决</li>
		  </ul>
	</div>
	
	<script>
	var status=<%=status%>;//问题状态
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
		var url=contextPath+"/projectQuestionController/getQuestionListByStatus.action?status="+status;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{state:-1,rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					render.push("<li class=\"mui-table-view-cell mui-media\" sid="+item.sid+" >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					
					render.push("<span>"+item.projectName+"</span></p>");
					render.push("<p style='font-size:12px'>问题名称："+item.questionName+"</p>");
					render.push("<p style='font-size:12px'>提交人："+item.createrName+"</p>");
					render.push("<p style='font-size:12px'>优先级："+item.questionLevel+"</p>");
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
		var url=contextPath+"/projectQuestionController/getQuestionListByStatus.action?status="+status;
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{state:-1,rows:20,page:page++},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				json = eval("("+json+")");
				var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					render.push("<li class=\"mui-table-view-cell mui-media\" sid="+item.sid+"  >");
					
					render.push("<div class=\"mui-media-body\">");
					render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
					
					render.push("<span>"+item.projectName+"</span></p>");
					render.push("<p style='font-size:12px'>问题名称："+item.questionName+"</p>");
					render.push("<p style='font-size:12px'>提交人："+item.createrName+"</p>");
					render.push("<p style='font-size:12px'>优先级："+item.questionLevel+"</p>");
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
	
	
	
	//问题详情
	function detail(){
		var sid = this.getAttribute("sid");
        window.location.href="questionDetail.jsp?sid="+sid;			
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