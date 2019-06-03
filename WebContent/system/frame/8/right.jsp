<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/smsHeader.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<title>组织机构</title>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/menu.css">
<style>
.btn_span{
	width:119px;
	height:43px;
	line-height:43px;
	border-bottom:1px solid #f0f0f0;
	cursor:pointer;
	background:#fafafa;
	font-family:微软雅黑;
	position:absolute;
}
.btn_span_hot{
	border-bottom:0px;
	background:none;
}
</style>
<script type="text/javascript"src="<%=contextPath%>/system/frame/8/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js?s=8"></script>
<script type="text/javascript">
	function doInit() {
	/***
	 * 获取组织机构信息
	 */
		getORGInfo();

	}
</script>
</head>
<body onload="doInit();" style="overflow:hidden;">
	<div style="text-align: center; position:absolute;left:0px;right:0px;top:0px;height:43px;" class="c_right_tab clearfix">
		<span class="btn_span btn_span_hot" item='1' style="border-right:1px solid #f0f0f0;left:0px;top:0px;width:110px;">在线人员</span>&nbsp;&nbsp;<span class="btn_span" style="left:105px;width:110px;">全部人员</span>
	</div>
	<div layout="center" style="position:absolute;left:0px;right:0px;bottom:0px;top:43px;overflow-y:auto;overflow-x:hidden">
		<ul id="orgUserZtree" class="ztree"
			style="border: 0px; width: 95%;; overflow-y: hidden;"></ul>
		</a>
	</div>
	<script>
		$(".c_right_tab span").click(function() {
			$(".c_right_tab span").removeClass("btn_span_hot");
			$(this).addClass("btn_span_hot");
			var item = $(this).attr("item");
			if (item == '1') {
				onlineUser();
			} else {
				allUser();
			}
			//var index = $(this).index();

			/* $('.c_right_list').hide();
			
			$('.c_right_list').eq(index).show(); */
		});
	</script>
</body>
</html>
