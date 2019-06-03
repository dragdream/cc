<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title></title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
	.mui-media-body{
		line-height:42px;
	}
</style>
<script type="text/javascript">
//初始化方法
function  doInit(){

}


</script>
</head>
<body onload="doInit();">
		<header class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" onclick="CloseWindow()"></span>
			<h1 class="mui-title">用品管理</h1>
		</header>
		<div class="mui-content">
			<div id="wrapper">
				<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media">
					<a href="addOrUpdate.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_wdxm.png">
						<div class="mui-media-body">
							用品登记
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="list/index.jsp?status=1" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_wdrw.png">
						<div class="mui-media-body">
							待审批列表
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="list/index.jsp?status=2" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmcy.png">
						<div class="mui-media-body">
							审批通过列表
						</div>
					</a>
				</li>
                <li class="mui-table-view-cell mui-media">
					<a href="list/index.jsp?status=3" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmwd.png">
						<div class="mui-media-body">
							审批未通过列表
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="list/index.jsp?status=4" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmwt.png">
						<div class="mui-media-body">
							调度中列表
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="list/index.jsp?status=5" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmcx.png">
						<div class="mui-media-body">
							作废列表
						</div>
					</a>
				</li>
			
			</ul>
			</div>
		</div>
</body>
</html>