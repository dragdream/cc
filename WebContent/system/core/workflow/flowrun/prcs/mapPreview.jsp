<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	String lng=TeeStringUtil.getString(request.getParameter("lng"));
	String lat=TeeStringUtil.getString(request.getParameter("lat"));

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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

</script>

<body onload="doInit();">
   <div id="allmap"></div>
   <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
</body>
</html>
