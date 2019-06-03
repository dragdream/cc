<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%
	String sid = request.getParameter("sid");
	String rootId = request.getParameter("rootId");
	String folder = request.getParameter("folder");
%>

<!DOCTYPE HTML>
<html>
<head>
<title>审批</title>
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
<script type="text/javascript">
  function doInit(){
	  $("body").on("tap",".pictureYunLan",function(){
		  var prev=$(this).prev().val();
		  openPhone(sid,prev);
	});
 }
</script>
</head>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" onclick="window.location.href='imgBaseindex.jsp'"></span>
		<h1 class="mui-title"></h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" style="display: none;"></a>
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
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=0';">未审批</li>
		    <li class="mui-table-view-cell" onclick="window.location = 'index.jsp?status=1';">已审批</li>
		  </ul>
	</div>
	
	<script>
	var nodeId = "";
	var curPage=1;
	var sid = "<%=sid%>";
	var rootId = "<%=rootId%>";
	var folder = "<%=folder%>";
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
		nodeId = sid;
		var sortType = "";
		var url="";
		url=contextPath+"/teeImgBaseController/getPictureList.action",
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{id:sid,sortType:sortType,pageSize:500,curPage:1},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
				json = eval("("+json+")");
				$(".mui-title").html(folder+"(共"+json.rtData.length+"张)");
				var render = [];
				render.push("<div>");
				for(var i=0;i<json.rtData.length;i++){
				var data = json.rtData[i];
				var filePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+data.thumbFilePath;
				render.push("<input type='hidden' value='"+data.thumbFilePath+"'/>");
				render.push("<img class='pictureYunLan' style='width:30%;height:120px;margin:10px 5px;' src='"+filePath+"'>");
				/* render.push("<li style='background:url("+filePath+")'>");
				render.push("</li>"); */
			 }
			render.push("</div>");
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
		url=contextPath+"/teeImgBaseController/getPictureList.action",
		nodeId = sid;
		var sortType = "";
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{id:sid,sortType:sortType,pageSize:500,curPage:1},
			timeout:10000,
			success:function(json){
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
				json = eval("("+json+")");
				$(".mui-title").html(folder+"(共"+json.rtData.length+"张)");
				var render = [];
				render.push("<div>");
				for(var i=0;i<json.rtData.length;i++){
					var data = json.rtData[i];
					var filePath = contextPath+"/teeImgBaseController/loadImage.action?filePath="+data.thumbFilePath;
					render.push("<input type='hidden' value='"+data.thumbFilePath+"'/>");
					render.push("<img class='pictureYunLan' style='width:30%;height:120px;margin:10px 5px;' src='"+filePath+"'>");
					
					/* render.push("<li style='background:url("+filePath+")'>");
					render.push("</li>"); */
				}
				render.push("</div>");
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
	
	//项目详情
	function detail(){
		var uuid = this.getAttribute("uuid");
		window.location.href="../cheDetail.jsp?sid="+uuid+"&status="+status;
	}
	
	mui.ready(function() {
		mui('#pullrefresh').pullRefresh().pullupLoading();
	});
	function openPhone(sid,thumbFilePath){
		var url = contextPath+"/system/mobile/phone/imgBase/show.jsp?sid="+sid+"&thumbFilePath="+thumbFilePath+"&rootId="+rootId+"&folder="+folder;
		window.location.href=url;
	}
	
	</script>
</body>
</html>