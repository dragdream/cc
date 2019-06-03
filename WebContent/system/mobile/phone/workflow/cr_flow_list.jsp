<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<!DOCTYPE HTML>
<html>
<head>
<title>新建工作-选择流程</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_WORKFLOW_APPID")%>";
var sortId = "<%=request.getParameter("sortId")%>";
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body>
	<!--下拉刷新容器-->
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<!--数据列表-->
				<ul class="mui-table-view" id="list">
					
				</ul>
			</div>
		</div>
		<!-- 底部操作栏 -->
		<nav class="mui-bar mui-bar-tab">
		  <a id="a1" class="mui-tab-item mui-active" >
				<span class="mui-icon mui-icon-undo"></span>
				<span  class="mui-tab-label" >返回</span>
			</a>
		</nav>
	<script>
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
			var url =contextPath+"/mobileWorkflow/getFlowTypeBySortAndPriv.action?sortId="+sortId;
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:{rows:100,page:page++},
				timeout:10000,
				success:function(json){
					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
					json = eval("("+json+")");
					var render = [];
					for(var i=0;i<json.rows.length;i++){
						var item = json.rows[i];
						render.push("<li class=\"mui-table-view-cell mui-media\" flowId=\""+item.flowId+"\"  runNamePriv="+item.runNamePriv+">");
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push("<img src='file_64.png' style='height:15px'/>");
						render.push("&nbsp;&nbsp;"+item.flowName+"</p>");
						render.push("</div>");
						render.push("</li>");
					}
					$("#list").html(render.join(""));
					
					$(".mui-table-view-cell").each(function(i,obj){
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
			
			var url = contextPath+"/mobileWorkflow/getFlowTypeBySortAndPriv.action?sortId="+sortId;
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:{rows:100,page:page++},
				timeout:10000,
				success:function(json){
					mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
					json = eval("("+json+")");
					var render = [];
					for(var i=0;i<json.rows.length;i++){
						var item = json.rows[i];
						render.push("<li class=\"mui-table-view-cell mui-media\" flowId=\""+item.flowId+"\" runNamePriv="+item.runNamePriv+">");
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push("<img src='file_64.png' style='height:15px'/>");
						render.push("&nbsp;&nbsp;"+item.flowName+"</p>");
						render.push("</div>");
						render.push("</li>");
					}
					$("#list").append(render.join(""));
					
					$(".mui-table-view-cell").each(function(i,obj){
						obj.removeEventListener("tap",detail);
						obj.addEventListener("tap",detail);
					});
				},
				error:function(){
					
				}
			});
			
		}
		
		/**
		 * 跳转至查看详情界面
		 */
		function detail(){
			var flowId = this.getAttribute("flowId");
			var runNamePriv = this.getAttribute("runNamePriv");
			window.location = "cr_page.jsp?flowId="+flowId+"&sortId="+sortId+"&runNamePriv="+runNamePriv;
		}
		
		mui.ready(function() {
			window.history.forward(1);
			mui('#pullrefresh').pullRefresh().pullupLoading();
			
			a1.addEventListener("tap",function(){
				var  url = "cr_sort_list.jsp";
				window.location.href = url;
			});
		});
	</script>
</body>
</html>