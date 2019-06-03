<%@page import="com.tianee.oa.core.general.TeeSysCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%
    String typeId=TeeStringUtil.getString(request.getParameter("typeId"));
   //0 未读   1 已读
	int state = TeeStringUtil.getInteger(request.getParameter("state"), 0);
	String  stateDesc="";
	if(state==0){
		stateDesc="未读";  
	}else if(state==1){
		stateDesc="已读";  
	}
%>
<!DOCTYPE HTML>
<html>
<head>
<title>
<%
	if("".equals(typeId)){
		out.print("新闻信息");
	}else{
		out.print(TeeSysCodeManager.getChildSysCodeNameCodeNo("NEWS_TYPE", typeId));
	}
%>
</title>
<script>
var DING_APPID = "<%=TeeSysProps.getString("DING_NEWS_APPID")%>";
</script>
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
		<h1 class="mui-title"><%=stateDesc %>新闻</h1>
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
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?state=0';">未读新闻</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?state=1';">已读新闻</li>
		  </ul>
	</div>
	<script>
	    var state=<%=state%>;
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
			var url =contextPath+"/teeNewsController/getReadNews.action?state=<%=state%>";
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
						var readType = item.readType;
						render.push("<li class=\"mui-table-view-cell mui-media\" sid=\""+item.sid+"\" readType=\""+readType+"\">");
						var attachmentCount = item.attachmentCount;//附件数量
						
						if(attachmentCount!=0){
							render.push("<img class=\"mui-media-object mui-pull-right\" style='height:15px' src=\"attach.png\">");
						}
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						if(state==0){
							render.push("<img src='readflag.png' style='height:15px'/>");
						}
						render.push(item.subject+"</p>");
						render.push("<p style='font-size:12px'>"+item.provider1+"&nbsp;&nbsp;"+item.newsTimeStr+"&nbsp;&nbsp;点击次数："+item.clickCount+"</p>");
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
			
			var url = contextPath+"/teeNewsController/getReadNews.action?state=<%=state%>";
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
						var readType = item.readType;
						render.push("<li class=\"mui-table-view-cell mui-media\" sid=\""+item.sid+"\" readType=\""+readType+"\">");
						var attachmentCount = item.attachmentCount;//附件数量
						
						if(attachmentCount!=0){
							render.push("<img class=\"mui-media-object mui-pull-right\" style='height:15px' src=\"attach.png\">");
						}
						render.push("<div class=\"mui-media-body\">");
						render.push("<p class='mui-ellipsis' style='color:#000;font-size:14px'>");
						if(state==0){
							render.push("<img src='readflag.png' style='height:15px'/>");
						}
						render.push(item.subject+"</p>");
						render.push("<p style='font-size:12px'>"+item.provider1+"&nbsp;&nbsp;"+item.newsTimeStr+"&nbsp;&nbsp;点击次数："+item.clickCount+"</p>");
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
		
		function detail(){
			var sid = this.getAttribute("sid");
			var readType = this.getAttribute("readType");
			if(userAgent.indexOf("DingTalk")==-1 && userAgent.indexOf("MicroMessenger")==-1){//原生
				OpenWindow('',contextPath+"/system/mobile/phone/news/newsInfo.jsp?sid="+sid+"&isLooked="+readType);
			}else{
				window.location = "newsInfo.jsp?sid="+sid+"&isLooked="+readType;
			}
			
		}
		
		mui.ready(function() {
			mui('#pullrefresh').pullRefresh().pullupLoading();

				
		});
	</script>
</body>
</html>