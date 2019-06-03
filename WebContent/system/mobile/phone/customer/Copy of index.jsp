<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>客户管理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
.btn1{
	width:45px;
	height:45px;
}
.item{
	text-align:center;
	font-size:14px;
}
.panel{
	padding:20px;
	background:white;
	border-radius:10px;
}
.number{
	font-size:22px;
}
</style>
</head>
<body>
<div class="mui-content">
	<header class="mui-bar mui-bar-nav">
		<a class="mui-pull-left app-title">CRM
		</a>
		<a class="mui-btn mui-btn-link mui-btn-nav mui-pull-right"  href="#myPopover">
		    <span class="mui-icon mui-icon-plusempty"></span>创建
		</a>
	</header>
	<div class="mui-content" id="dataList" style="font-size:14px">
		<div id="itemDiv" style="padding:10px;padding-bottom:0px">
			<table style="width:100%">
				<tr>
					<td class="item"><img src="images/kehu.png" class="btn1" /><br/>客户</td>
					<td class="item"><img src="images/lianxiren.png"  class="btn1" /><br/>联系人</td>
					<td class="item"><img src="images/jihui.png"  class="btn1" /><br/>机会</td>
					<td class="item"><img src="images/gendan.png"  class="btn1" /><br/>跟单</td>
					<td class="item"><img src="images/hetong.png"  class="btn1" /><br/>合同</td>
					<td class="item"><img src="images/huikuan.png"  class="btn1" /><br/>回款</td>
				</tr>
			</table>
		</div>
		<div class="mui-content-padded" style="padding:5px;">
			<div class="panel">
				<table style="width:100%">
					<tr>
						<td style="width:35px">
							<img src="images/zhibiao.png" width="25px"/>
						</td>
						<td style="width:90px;color:#56abe4;font-size:16px">
							本月指标
						</td>
						<td align="right">
							<img src="images/right.png"  width="25px"/>
						</td>
					</tr>
				</table>
				<table style="width:100%;margin-top:20px">
					<tr>
						<td>
							<img width="10px" src="images/mb-color.png" />&nbsp;&nbsp;目标
							<br/>
							<span class="number">2000000</span>元
							<br/>
							<img style="margin-top:15px;" width="10px" src="images/yc-color.png" />&nbsp;&nbsp;预测
							<br/>
							<span class="number">2052</span>元
							<br/>
							<img style="margin-top:15px;" width="10px" src="images/cj-color.png" />&nbsp;&nbsp;成交
							<br/>
							<span class="number">3000</span>元
							<br/>
							<img style="margin-top:15px;" width="10px" src="images/hk-color.png" />&nbsp;&nbsp;回款
							<br/>
							<span class="number">20000</span>元
						</td>
						<td>
							
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="myPopover" class="mui-popover" style="width:120px">
		<ul class="mui-table-view">
		  <li class="mui-table-view-cell"><a href="#">客户</a></li>
		  <li class="mui-table-view-cell"><a href="#">联系人</a></li>
		  <li class="mui-table-view-cell"><a href="#">跟单</a></li>
		  <li class="mui-table-view-cell"><a href="#">合同</a></li>
		  <li class="mui-table-view-cell"><a href="#">回款</a></li>
		</ul>
	</div>
</div>
<script>
	
	(function($) {
		//启动加载完毕的逻辑		
	})(mui);
	
</script>
</body>
</html>