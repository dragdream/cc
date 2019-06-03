<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<!DOCTYPE HTML>
<html>
<head>
<title>日程</title>
<script>
var DING_APPID = "<%=com.tianee.oa.oaconst.TeeModuleConst.MODULE_SORT_DD_APP_ID.get("022")%>";
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
				<span class="mui-icon mui-icon-compose"></span>
				<span  class="mui-tab-label" >新建日程</span>
			</a>
		</nav>
	<script>
	history.forward(1);
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
			var url =contextPath+"/mobileCalendarController/getList.action";
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
// 						var attachmentCount = item.attachmentCount;//附件数量
// 						var readFlagStr = item.readFlag;
// 						if(attachmentCount!=0){
// 							render.push("<img class=\"mui-media-object mui-pull-right\" style='height:15px' src=\"attach.png\">");
// 						}
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
// 						if(readFlagStr=="0"){
// 							render.push("<img src='readflag.png' style='height:15px'/>");
// 						}
						render.push(item.content+"</p>");
						render.push("<p style='font-size:12px'>"+item.calendarTimeDecs+"</p>");
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
			
			var url = contextPath+"/mobileCalendarController/getList.action";
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
// 						var attachmentCount = item.attachmentCount;//附件数量
// 						var readFlagStr = item.readFlag;
// 						if(attachmentCount!=0){
// 							render.push("<img class=\"mui-media-object mui-pull-right\" style='height:15px' src=\"attach.png\">");
// 						}
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
// 						if(readFlagStr=="0"){
// 							render.push("<img src='readflag.png' style='height:15px'/>");
// 						}
						render.push(item.content+"</p>");
						render.push("<p style='font-size:12px'>"+item.calendarTimeDecs+"</p>");
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
			window.location = "detail.jsp?sid="+sid;
		}
		
		mui.ready(function() {
			mui('#pullrefresh').pullRefresh().pullupLoading();
			a1.addEventListener("tap",function(){
				window.location = "addOrUpdate.jsp";
			});
		});
	</script>
</body>
</html>