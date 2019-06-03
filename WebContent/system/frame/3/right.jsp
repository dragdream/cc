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
<link href="css/index.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/menu.css">
<script type="text/javascript"src="<%=contextPath%>/system/frame/default/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js?s=11"></script>
<style>
<!--
.hd{
    width: 235px;
    height: 56px;
    background: #FFF;
    padding: 0px;
    margin: 0px;

}
.hd li.on {
    width: 70px;
    background: #FFF;
    border-bottom: 1px #FFF solid;
    border-left: 1px #e9e9e9 solid;
    border-right: 1px #e9e9e9 solid;
    position: relative;
    left: -1px;
    color: #263543;
}
.hd li {
    width: 70px;
    float: left;
    text-align: center;
    line-height: 40px;
    font-family: "Microsoft YaHei";
    color: #6e6e6e;
    border-bottom: 1px #e9e9e9 solid;
    background: #fafafa;
    padding: 0px;
}

-->
</style>

			<script type="text/javascript">
				function doInit() {
					//设置高度
					/* var clientHeight = parent.$(".c_right fl").clientHeight;
					$("#orgUserZtree").css("height",clientHeight-47); */
					getORGInfo();

				}
			</script>
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
</head>

<body onload="doInit();" style="overflow:hidden;">

	<div style="text-align: center; position:absolute;left:0px;right:0px;top:0px;height:43px;" class="c_right_tab clearfix">
		<span class="btn_span btn_span_hot" item='1' style="border-right:1px solid #f0f0f0;left:0px;top:0px;">在线人员</span>&nbsp;&nbsp;<span class="btn_span" style="left:120px">全部人员</span>
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
