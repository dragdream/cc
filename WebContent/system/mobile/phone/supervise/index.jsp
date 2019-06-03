<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>督查督办</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
	.mui-media-body{
		line-height:42px;
	}
</style>
</head>
<body>
		<header class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" onclick="CloseWindow()"></span>
			<h1 class="mui-title">督查督办</h1>
		</header>
		<div class="mui-content">
			<div id="wrapper">
				<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media">
					<a href="mySupervision/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_dbrw.png">
						<div class="mui-media-body">
							督办任务
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="supervisionManage/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_dbgl.png">
						<div class="mui-media-body">
							督办管理
						</div>
					</a>
				</li>
				
			</ul>
			</div>
		</div>
</body>
</html>