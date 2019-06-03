<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<!DOCTYPE HTML>
<html>
<head>
<title>查询结果</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_WORKFLOW_APPID")%>";
var runId = "<%=request.getParameter("runId")%>";
var runName = "<%=request.getParameter("runName")%>";

var type="<%=request.getParameter("type")%>";
var status="<%=request.getParameter("status")%>";
var beginUser="<%=request.getParameter("beginUser")%>";
<%
String flowTypeId = TeeStringUtil.getString(request.getParameter("flowTypeId"));
%>
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
			var url =contextPath+"/workQuery/query.action?runNameOper=like2&flowId=<%=flowTypeId%>";
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:{rows:20,page:page++,runId:runId,runName:runName,type:type,status:status,beginUser:beginUser},
				timeout:10000,
				success:function(json){
					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
					json = eval("("+json+")");
					var render = [];
					for(var i=0;i<json.rows.length;i++){
						var item = json.rows[i];
						var readType = item.readType;
						render.push("<li class=\"mui-table-view-cell mui-media\" runId=\""+item.runId+"\" frpSid=\""+item.frpSid+"\" flowId=\""+item.flowId+"\">");
						
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						if(readType=="0"){
							render.push("<img src='readflag.png' style='height:15px'/>");
						}
						render.push("<span>["+item.runId+"]&nbsp;"+item.flowName+"</span></p>");
						
						render.push("<p style='font-size:12px'>");
						if(item.endTimeDesc!=""){
							render.push("<span style=\"color:red\">已结束</span>");
						}else{
							render.push("<span style=\"color:green\">执行中</span>");
						}
						render.push("&nbsp;&nbsp;"+item.runName+"</p>");
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
			
			var url = contextPath+"/workQuery/query.action?runNameOper=like2&flowId=<%=flowTypeId%>";
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:{rows:20,page:page++,runId:runId,runName:runName,type:type,status:status,beginUser:beginUser},
				timeout:10000,
				success:function(json){
					mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
					json = eval("("+json+")");
					var render = [];
					for(var i=0;i<json.rows.length;i++){
						var item = json.rows[i];
						var readType = item.readType;
						render.push("<li class=\"mui-table-view-cell mui-media\" runId=\""+item.runId+"\" frpSid=\""+item.frpSid+"\" flowId=\""+item.flowId+"\">");
						
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						if(readType=="0"){
							render.push("<img src='readflag.png' style='height:15px'/>");
						}
						render.push("<span>["+item.runId+"]&nbsp;"+item.flowName+"</span></p>");
						render.push("<p style='font-size:12px'>");
						if(item.endTimeDesc!=""){
							render.push("<span style=\"color:red\">已结束</span>");
						}else{
							render.push("<span style=\"color:green\">执行中</span>");
						}
						render.push("&nbsp;&nbsp;"+item.runName+"</p>");
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
			var runId0 = this.getAttribute("runId");
			var frpSid = this.getAttribute("frpSid");
			var flowId = this.getAttribute("flowId");
			window.location = "flow_run_view.jsp?runIdSearch="+runId+"&runId="+runId0+"&frpSid="+frpSid+"&flowId="+flowId+"&view=47&flowTypeId=<%=flowTypeId%>&runName="+encodeURI(runName);
		}
		
		mui.ready(function() {
			window.history.forward(1);
			mui('#pullrefresh').pullRefresh().pullupLoading();
			
			a1.addEventListener("tap",function(){
				var  url = "search_index.jsp?flowTypeId=<%=flowTypeId%>";
				window.location.href = url;
			});
		});
	</script>
</body>
</html>