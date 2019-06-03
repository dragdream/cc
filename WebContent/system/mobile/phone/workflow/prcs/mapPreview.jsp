<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	String lng=TeeStringUtil.getString(request.getParameter("lng"));
	String lat=TeeStringUtil.getString(request.getParameter("lat"));

%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&&ak=jEtlvZ7UXrKl6FtnqikedMIz"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchControl/1.4/src/SearchControl_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchControl/1.4/src/SearchControl_min.css" />
<title>地图预览</title>
</head>
<style type="text/css">
    body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
<script type="text/javascript">
var lng="<%=lng %>";
var lat="<%=lat %>";

//初始化地图
function doInit(){
	//百度地图API功能
	map = new BMap.Map("allmap");
	map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
	//重新设置中心点
	map.centerAndZoom(new BMap.Point(lng, lat),18);	
	//标注点
    var oMarker = new BMap.Marker(new BMap.Point(lng, lat));
	map.addOverlay(oMarker); 
}

mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		parent.layer.closeAll();
	});
});
</script>

<body onload="doInit();">
    <header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" style="cursor: pointer;"></span>
		<h1 class="mui-title">地图预览</h1>
	</header>
   <div id="allmap"></div>
   <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
</body>
</html>
