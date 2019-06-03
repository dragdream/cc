<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	String data=TeeStringUtil.getString(request.getParameter("data"));
	String extra=TeeStringUtil.getString(request.getParameter("extra"));
    String positionType=TeeStringUtil.getString(request.getParameter("positionType"));
    int radius=TeeStringUtil.getInteger(request.getParameter("radius"),0);//半径
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
    
</style>
<script type="text/javascript">
var data="<%=data %>";
var extra="<%=extra %>";
var positionType="<%=positionType %>";
var radius=<%=radius %>;
var map;
var local;
var mPoint;
var myValue;

//百度地图API功能
function G(id) {
	return document.getElementById(id);
}

//初始化地图
function doInit(){
	//判断半径是不是为0
	if(radius==0){
		$("#searchSpan").hide();
	}else{
		$("#searchSpan").show();
	}
	
	//百度地图API功能
	map = new BMap.Map("allmap");
	map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
// 	var point = new BMap.Point(116.331398,39.897445);
	
	
	parent.GeoLocation(function(rs){
		var point = new BMap.Point(rs.lng,rs.lat);
		map.centerAndZoom(point,11);
		var mk = new BMap.Marker(point);
		map.addOverlay(mk);
		map.panTo(point);
		//重新设置中心点
		mPoint=point;
		map.centerAndZoom(point,18);
		
		if(radius>0){//半径大于0
			var circle = new BMap.Circle(point,radius,{fillColor:"blue", strokeWeight: 1 ,fillOpacity: 0.3, strokeOpacity: 0.3});
		    map.addOverlay(circle); 
		}
		//渲染附近点
		renderNearPoints();
		
		local =  new BMap.LocalSearch(map, {renderOptions: {autoViewport: false},onSearchComplete: function(results){
			// 判断状态是否正确
			if (local.getStatus() == BMAP_STATUS_SUCCESS){
				var s = [];
				for (var i = 0; i < results.getCurrentNumPois(); i ++){
					console.log(results.getPoi(i));
					s.push("<div   class=\"poiaddr\"   style=\"cursor:pointer;padding:10px;border-bottom:solid 1px #E0E0E0;\"  lng='"+results.getPoi(i).point.lng+"'   lat='"+results.getPoi(i).point.lat+"'  addr='"+results.getPoi(i).address+"'  name='"+results.getPoi(i).title+"' >"+results.getPoi(i).title+"<br><span style=\"font-size:12px;\">"+results.getPoi(i).address+"</span></div>");
				}
				document.getElementById("searchResultPanel").innerHTML = s.join("");
			    
				$(".poiaddr").each(function(i,obj){
					obj.removeEventListener("tap",setAddress);
					obj.addEventListener("tap",setAddress);
				});
			}
		}});  
	});
}

//原有的附近点
function renderNearPoints(){
	$("#searchResultPanel").html("");
	//渲染附近点
	var url=contextPath+"/system/mobile/phone/workflow/prcs/getPoiList.jsp?lat="+mPoint.lat+"&lng="+mPoint.lng;
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			for(var i=0;i<json.length;i++){
				$("#searchResultPanel").append("<div   class=\"poiaddr\"   style=\"cursor:pointer;padding:10px;border-bottom:solid 1px #E0E0E0;\"  lng='"+json[i].point.x+"'   lat='"+json[i].point.y+"'  addr='"+json[i].addr+"'  name='"+json[i].name+"' >"+json[i].name+"<br><span style=\"font-size:12px;\">"+json[i].addr+"</span></div>");
			}
			
			$(".poiaddr").each(function(i,obj){
				obj.removeEventListener("tap",setAddress);
				obj.addEventListener("tap",setAddress);
			});
		}
	});
}



//设置定位
function  setAddress(){
	var lng = this.getAttribute("lng");
	var lat = this.getAttribute("lat");
	var name = this.getAttribute("name");
	var addr = this.getAttribute("addr");
	
	parent.document.getElementById(data).value=addr+"("+name+")";
	parent.document.getElementById("POS_"+data).innerText=addr+"("+name+")";
	parent.document.getElementById(extra).value=lng+","+lat;
    
	setTimeout(function(){
		parent.layer.closeAll();
	},200);
}


mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		setTimeout(function(){
			parent.layer.closeAll();
		},200);
	});
});


//搜索
function search(){
	mui.prompt('请输入地址关键字','',"提示",function(e){
	    if(e.index == 1){
	    	if(e.value==""||e.value==null){
	    		renderNearPoints();
	    		
	    	}else{
	    		$("#searchResultPanel").html("");//清空之前的面板
	            local.searchNearby(e.value,mPoint,radius);
	    	} 
	    }
	});
}
</script>

<body onload="doInit();" style="overflow: hidden;">
   <header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn"></span>
		<h1 class="mui-title">当前位置</h1>
		<span id="searchSpan" class="mui-pull-right mui-icon mui-icon-search" onclick="search();" ></span>
	</header>
	
    <div id="allmap" style="position: absolute;top:0px;left: 0px;right: 0px;bottom:300px;"></div>
	<div id="searchResultPanel" style="position: absolute;left: 0px;right: 0px;bottom: 0px;height: 300px;overflow: auto;background-color: white;">
	    
	</div>
</body>
</html>
