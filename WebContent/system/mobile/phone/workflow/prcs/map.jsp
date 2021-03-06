<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	String data=TeeStringUtil.getString(request.getParameter("data"));
	String extra=TeeStringUtil.getString(request.getParameter("extra"));
    String positionType=TeeStringUtil.getString(request.getParameter("positionType"));

%>
<!DOCTYPE HTML>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&&ak=jEtlvZ7UXrKl6FtnqikedMIz"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchControl/1.4/src/SearchControl_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchControl/1.4/src/SearchControl_min.css" />
<title>地图选点</title>
</head>
<style type="text/css">
    body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
<script type="text/javascript">
var data="<%=data %>";
var extra="<%=extra %>";
var positionType="<%=positionType %>";
var map;
var myValue;

//百度地图API功能
function G(id) {
	return document.getElementById(id);
}

//初始化地图
function doInit(){
	//百度地图API功能
	map = new BMap.Map("allmap");
	map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,11);	
	var geoc = new BMap.Geocoder();  
	
	
	//从父页面获取经纬度
	var xyStr=parent.document.getElementById(extra).value;
	if(xyStr!=null&&xyStr!=""&&xyStr!="undefine"){
		var xPoint=xyStr.split(",")[0];
		var yPoint=xyStr.split(",")[1];
		
		//重新设置中心点
		map.centerAndZoom(new BMap.Point(xPoint, yPoint),18);	
		
        var oMarker = new BMap.Marker(new BMap.Point(xPoint, yPoint));
		map.addOverlay(oMarker);    //添加标注
	}
	
	
	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
			{"input" : "searchInput"
			,"location" : map
		});
	ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
		var str = "";
			var _value = e.fromitem.value;
			var value = "";
			if (e.fromitem.index > -1) {
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			}    
			str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
			
			value = "";
			if (e.toitem.index > -1) {
				_value = e.toitem.value;
				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			}    
			str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
			G("searchResultPanel").innerHTML = str;
		});
	
	
	ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
	var _value = e.item.value;
		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
		
		setPlace();
	});
	
	//编写自定义函数,创建标注
	function addMarker(point){
	  var marker = new BMap.Marker(point);
	  map.addOverlay(marker);
	}
	
	map.addEventListener("click", function(e){        
		var pt = e.point;
		var btnArray=['取消','确定'];
		
		mui.confirm("确定选取该点吗？","",btnArray,function(e) {
			if(e.index==1){
				geoc.getLocation(pt, function(rs){
					var addComp = rs.addressComponents;
					var  desc=addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
					var  xaddr=pt.lng;
					var  yaddr=pt.lat;
			        var coordinate=xaddr+","+yaddr;
			        parent.document.getElementById(data).value=desc;
			        parent.document.getElementById("POS_"+data).innerText=desc;
			        parent.document.getElementById(extra).value=coordinate;
			        parent.layer.closeAll();
				}); 
			} 
	    });
	});
}


function setPlace(){
	map.clearOverlays();    //清除地图上所有覆盖物
	function myFun(){
		var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
		map.centerAndZoom(pp, 18);
		
		var addressDesc=local.getResults().getPoi(0).address;
		var points=local.getResults().getPoi(0).point.lng+","+local.getResults().getPoi(0).point.lat;
		
		var infoWindow = new BMap.InfoWindow("<div>"+addressDesc+"<br><a onclick=\"setAddress('"+addressDesc+"','"+points+"');\" style=\"float:right\" >设置为定位地点</a></div>");  // 创建信息窗口对象
		var marker = new BMap.Marker(pp);
		
		map.addOverlay(marker);    //添加标注
		marker.openInfoWindow(infoWindow);
	}
	var local = new BMap.LocalSearch(map, { //智能搜索
	  onSearchComplete: myFun
	});
	local.search(myValue);
}



//设置定位
function  setAddress(address,points){
	parent.document.getElementById(data).value=address;
	parent.document.getElementById("POS_"+data).innerText=address;
	parent.document.getElementById(extra).value=points;
    
	parent.layer.closeAll();
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
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">自由定位</h1>
	</header>
	<div id="muiContent" class="mui-content">
	   <div id="searchBox" >
	   	<input type="text" id="searchInput" style="width:100%;height:30px;"/>
	   </div>
    </div>
    <div id="allmap"></div>
	<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
</body>
</html>
