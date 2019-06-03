
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

 String startTime=request.getParameter("startTime");
 String endTime=request.getParameter("endTime");
 int personId=TeeStringUtil.getInteger(request.getParameter("personId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&&ak=N51ibLpYvdGGc5b0i5VZpONtyAeAqNkc"></script>
<title>足迹图</title>
</head>
<style type="text/css">
    body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
<script type="text/javascript">
var startTime="<%=startTime%>";
var endTime="<%=endTime%>";
var personId=<%=personId%>;
//初始化地图
function doInit(){
	var url=contextPath + "/TeeFootPrintController/getListByPerson.action";
	var para = {startTime:startTime,endTime:endTime,personId:personId};
	var jsonObj = tools.requestJsonRs(url,para);
	var data1=jsonObj.rows;
	
	
	 var points = [];
	 for(var i=0;i<data1.length;i++){
		 points.push(new BMap.Point(data1[i].coordinateX,data1[i].coordinateY));
	 }
	 
	 //获取中心点的 坐标
	 var centerX=jsonObj.footer[0];
	 var centerY=jsonObj.footer[1];
	 // 百度地图API功能
	  var map = new BMap.Map("allmap");
	  map.enableScrollWheelZoom(true);
	  map.addControl(new BMap.NavigationControl()); //放大缩小
      map.addControl(new BMap.ScaleControl()); //显示距离
	  map.addControl(new BMap.OverviewMapControl());    
	  map.addControl(new BMap.MapTypeControl());//地图、卫星、三维
	  map.centerAndZoom(new BMap.Point(centerX, centerY), 14);
	  var polyline = new BMap.Polyline(points,{strokeColor:"#dc0303", strokeWeight:5, strokeOpacity:0.5});  
	  map.addOverlay(polyline);
	
	
	  for(var i=0;i<data1.length;i++){
		 var new_point = new BMap.Point(data1[i].coordinateX,data1[i].coordinateY);
		 var marker = new BMap.Marker(new_point);// 创建标注
		 map.addOverlay(marker);// 将标注添加到地图中
	  }
	
}






</script>

<body onload="doInit();">
   <div id="allmap"></div>
</body>
</html>