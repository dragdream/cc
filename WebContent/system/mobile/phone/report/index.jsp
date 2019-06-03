<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>报表门户</title>
<%@ include file="/system/mobile/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=mobilePath %>/js/iscroll.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/index.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/listview.js"></script>

<script type="text/javascript">
var loader;
function doInit(){
	loader = new lazyLoader({
		url:contextPath+"/seniorReportCat/datagrid.action",
		placeHolder:"",
		contentHolder:'thelist',
		param:{},
		pageSize:100,//初始化分页大小
		rowRender:function(data){
			var render = [];
			render.push("<li onclick='detail("+data.sid+")' style='height:inherit;padding:10px;font-size:16px'>");
			render.push("<span>"+data.name+"</span>");
			render.push("<span class='span_bg'>&gt;</span>");
			render.push("</li>");
			return render.join("");
		},
		onNoData:function(){
			$("#fetchMore").html("无更多数据");
		},
		onLoadSuccess:function(){
			myScroll.refresh();
		}
	});
	loaded();
}

/**
 * 上方获取最新
 */
function pullDownAction () {
	setTimeout(function () {
		loader.reload();
		myScroll.refresh();
	}, 500);
}

function pullUpAction () {
	setTimeout(function () {
		loader.load();
		myScroll.refresh();
	}, 500);
}

/**
 * 跳转至查看详情界面
 */
function detail(catId){
	window.location.href = contextPath + "/system/mobile/phone/report/reports.jsp?catId="+catId;
}

//document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);
</script>


</head>
<body onload="doInit();">
<div id="wrapper" style="top:0px;bottom:0px;">
	<div id="scroller">
		<div id="pullDown" style="display:none">
			<span class="pullDownIcon"></span><span class="pullDownLabel">下拉更新</span>
		</div>
		<ul id="thelist">
		</ul>
		<div id="pullUp" style="display:none">
			<span class="pullUpIcon"></span><span class="pullUpLabel" id="fetchMore">获取更多</span>
		</div>
	</div>
</div>
</body>
</html>