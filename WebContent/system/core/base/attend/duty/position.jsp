<%@ page language="java"  import="java.util.*,java.text.SimpleDateFormat" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String pos = request.getParameter("pos");
	String address = request.getParameter("address");
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=N51ibLpYvdGGc5b0i5VZpONtyAeAqNkc"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
	<title>考勤定位</title>
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#allmap{height:100%;width:100%;}
		#BMapLib_stationText0{
			font:12px arial, 宋体, sans-serif;
		}
		
	</style>
</head>

<body>
	<div id="allmap"></div>
	
</body>
</html>
<script type="text/javascript">
	
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(<%=pos%>),17);
	map.enableScrollWheelZoom(true);
	
	map.clearOverlays(); 
	var new_point = new BMap.Point(<%=pos%>);
	var marker = new BMap.Marker(new_point);// 创建标注
	map.addOverlay(marker);// 将标注添加到地图中
	map.panTo(new_point);   
	marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	marker.addEventListener("click", function(e){
		searchInfoWindow3.open(marker); 
    });
	map.addControl(new BMap.NavigationControl()); //放大缩小
	map.addControl(new BMap.ScaleControl()); //显示距离
	map.addControl(new BMap.OverviewMapControl());    
	map.addControl(new BMap.MapTypeControl());//地图、卫星、三维
	
	//地址转换
	/* 
	var geoc = new BMap.Geocoder();  
	geoc.getLocation(new_point, function(rs){
		var addComp = rs.addressComponents;
		alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
	
	}); 
	 */
	
	
	 var content = '<div style="margin:0;line-height:20px;padding:2px;">' +
                    '地址：<%=address %>' +
                  '</div>';

	//样式3
	var searchInfoWindow3 = new BMapLib.SearchInfoWindow(map, content, {
		title: "签到位置", //标题
		width: 290, //宽度
		height: 40, //高度
		panel : "panel", //检索结果面板
		enableAutoPan : true, //自动平移
		searchTypes :[
		]
	});
	searchInfoWindow3.open(marker); 
</script>
