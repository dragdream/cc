<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   Enumeration enu=request.getParameterNames(); 
   String param="{";
   while(enu.hasMoreElements()){
        String name=(String)enu.nextElement(); 
        String value=request.getParameter(name);
        if(value==null||("").equals(value)){
        	value="";
        }
        param=param+name+":'"+value+"',";
   }
   param=param.substring(0,param.length()-1)+"}";
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
<body>
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">查询结果</h1>
	</header>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="list">
				
			</ul>
		</div>
	</div>

	
	<script>
	//查询参数
	 var param="<%=param%>";

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
			var params=eval("("+param+")");
			params["rows"]=10;
			params["page"]=page++;
			var url=contextPath + "/projectController/query.action";
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:params,
				timeout:10000,
				success:function(json){
					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
					json = eval("("+json+")");
					var render = [];
					for(var i=0;i<json.rows.length;i++){
						var item = json.rows[i];
						render.push("<li class=\"mui-table-view-cell mui-media\"   uuid='"+item.uuid+"' >");
						
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push("<span>"+item.projectName+"</span></p>");
						render.push("<p style='font-size:12px'>"+item.projectNum+"</p>");
						render.push("<p style='font-size:12px'>"+item.beginTime+"&nbsp;~&nbsp;"+item.endTime+"</p>");
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
			var url=contextPath + "/projectController/query.action";
			var params=eval("("+param+")");
			params["rows"]=10;
			params["page"]=page++;
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:params,
				timeout:10000,
				success:function(json){
					mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
					json = eval("("+json+")");
					var render = [];
					for(var i=0;i<json.rows.length;i++){
						var item = json.rows[i];
						render.push("<li class=\"mui-table-view-cell mui-media\"  uuid='"+item.uuid+"' >");
						
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						
						render.push("<span>"+item.projectName+"</span></p>");
						render.push("<p style='font-size:12px'>"+item.projectNum+"</p>");
						render.push("<p style='font-size:12px'>"+item.beginTime+"&nbsp;~&nbsp;"+item.endTime+"</p>");
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
		
		
		
		//项目详情
		function detail(){
			var uuid = this.getAttribute("uuid");
			window.location = "projectDetail.jsp?uuid="+uuid;
		}
		
		
		
		
		mui.ready(function() {
			mui('#pullrefresh').pullRefresh().pullupLoading();
			
			backBtn.addEventListener("tap",function(){
				window.location.href = "index.jsp";
			});//返回
			
			
		});
	
	
	</script>
</body>
</html>