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
			<h1 class="mui-title">用车管理</h1>
		</header>
		<div class="mui-content">
			<div id="wrapper">
				<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media">
					<a href="addOrUpdate.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_wdxm.png">
						<div class="mui-media-body">
							用车申请
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="daiPi/index.jsp?status=0" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_wdrw.png">
						<div class="mui-media-body">
							待批准用车
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="daiPi/index.jsp?status=1" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmcy.png">
						<div class="mui-media-body">
							已批准用车
						</div>
					</a>
				</li>
                <li class="mui-table-view-cell mui-media">
					<a href="daiPi/index.jsp?status=2" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmwd.png">
						<div class="mui-media-body">
							进行中用车
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="daiPi/index.jsp?status=3" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmwt.png">
						<div class="mui-media-body">
							未批用车
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="daiPi/index.jsp?status=4" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmcx.png">
						<div class="mui-media-body">
							已结束用车
						</div>
					</a>
				</li>
			
			</ul>
			</div>
		</div>
</body>
</html>