<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<!DOCTYPE HTML>
<html>
<head>
<title>工作日志</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_DIARY_APPID")%>";
</script>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>

</style>
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
		 	<a id="a1" class="mui-tab-item mui-active">
				<span class="mui-icon mui-icon-person"></span>
				<span  class="mui-tab-label" >我的日志</span>
			</a>
			<a id="a2" class="mui-tab-item">
				<span class="mui-icon mui-icon-location"></span>
				<span  class="mui-tab-label" >共享日志</span>
			</a>
			<a id="a3" class="mui-tab-item">
				<span class="mui-icon mui-icon-compose"></span>
				<span  class="mui-tab-label" >撰写日志</span>
			</a>
		</nav>
	<script>
		var page = 1;
		var url = contextPath+"/mobileDiaryController/getListDiary.action";
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
						render.push("<li class=\"mui-table-view-cell mui-media\" sid=\""+item.sid+"\">");
						var attachmentCount = item.attachmentCount;//附件数量
						if(attachmentCount!=0){
							render.push("<img class=\"mui-media-object mui-pull-right\" style='height:15px' src=\"attach.png\">");
						}
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push(item.title+"</p>");
						render.push("<p style='font-size:12px'>"+item.createUserName+"&nbsp;&nbsp;"+item.createTimeDesc+"</p>");
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
						render.push("<li class=\"mui-table-view-cell mui-media\" sid=\""+item.sid+"\">");
						var attachmentCount = item.attachmentCount;//附件数量
						if(attachmentCount!=0){
							render.push("<img class=\"mui-media-object mui-pull-right\" style='height:15px' src=\"attach.png\">");
						}
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						render.push(item.title+"</p>");
						render.push("<p style='font-size:12px'>"+item.createUserName+"&nbsp;&nbsp;"+item.createTimeDesc+"</p>");
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
		
		function detail(){
			var sid = this.getAttribute("sid");
			window.location = "diaryInfo.jsp?sid="+sid;
		}
		
		mui.ready(function() {
			mui('#pullrefresh').pullRefresh().pullupLoading();
			a1.addEventListener("tap",function(){
				url = contextPath+"/mobileDiaryController/getListDiary.action";
				pulldownRefresh();
			});
			a2.addEventListener("tap",function(){
				url = contextPath+"/mobileDiaryController/getListShareDiary.action";
				pulldownRefresh();
			});
			a3.addEventListener("tap",function(){
				window.location = "addOrUpdate.jsp";
			});
		});
	</script>
</body>
</html>