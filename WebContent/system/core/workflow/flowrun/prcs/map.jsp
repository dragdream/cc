<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	String data=TeeStringUtil.getString(request.getParameter("data"));
	String extra=TeeStringUtil.getString(request.getParameter("extra"));
    String positionType=TeeStringUtil.getString(request.getParameter("positionType"));

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
	var xyStr=window.opener.document.getElementById(extra).value;
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
		 $.MsgBox.Confirm ("提示", "确定选取该点吗？", function(){
			 geoc.getLocation(pt, function(rs){
					var addComp = rs.addressComponents;
					var  desc=addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
					var  xaddr=pt.lng;
					var  yaddr=pt.lat;
			        var coordinate=xaddr+","+yaddr;
			        window.opener.document.getElementById(data).value=desc;
			        window.opener.document.getElementById("POS_"+data).innerText=desc;
			        window.opener.document.getElementById(extra).value=coordinate;
			        window.close();
				});  
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
	window.opener.document.getElementById(data).value=address;
	window.opener.document.getElementById("POS_"+data).innerText=address;
    window.opener.document.getElementById(extra).value=points;
    
    window.close();
}
</script>

<body onload="doInit();">
   <div id="searchBox" >
   	<input id="searchInput" style="width:100%;height:30px;"/>
   </div>
   <div id="allmap"></div>
   <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
</body>
</html>
