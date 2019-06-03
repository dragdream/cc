<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
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
<!--加载鼠标绘制工具-->
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
<title>地图选点</title>
</head>
<style type="text/css">
    body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
<script type="text/javascript">
var sid=<%=sid %>;
//初始化地图
var overlays = [];
var map ;
var drawingManager;
function doInit(){
	// 百度地图API功能
	map = new BMap.Map("allmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,11);
	map.enableScrollWheelZoom();//鼠标滚动事件
	var geoc = new BMap.Geocoder();  
	
	//创建检索控件
	var searchControl = new BMapLib.SearchControl({
	    container : "searchBox" //存放控件的容器
	    , map     : map  //关联地图对象
	    , type    : "LOCAL_SEARCH"  //检索类型
	});
	
	
		var overlaycomplete = function(e){
	        overlays.push(e.overlay);
	    };
	    var styleOptions = {
	        strokeColor:"red",    //边线颜色。
	        fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。
	        strokeWeight: 3,       //边线的宽度，以像素为单位。
	        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
	        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
	        strokeStyle: 'solid' //边线的样式，solid或dashed。
	    }
	    
	    //实例化鼠标绘制工具
	    drawingManager = new BMapLib.DrawingManager(map, {
	        isOpen: true, //是否开启绘制模式
	        enableDrawingTool: false, //是否显示工具栏
	        drawingType: BMAP_DRAWING_CIRCLE,
	        drawingToolOptions: {
	            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
	            offset: new BMap.Size(5, 5), //偏离值
	            drawingTypes : [
					BMAP_DRAWING_POLYGON,
					BMAP_DRAWING_CIRCLE,
					BMAP_DRAWING_POLYLINE,
					BMAP_DRAWING_MARKER,
					BMAP_DRAWING_RECTANGLE 
                 ]
	        },
	        circleOptions: styleOptions, //圆的样式
	        polylineOptions: styleOptions, //线的样式
	        polygonOptions: styleOptions, //多边形的样式
	        rectangleOptions: styleOptions //矩形的样式
	    });  
	    
	    drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
		 //添加鼠标绘制工具监听事件，用于获取绘制结果
	    drawingManager.addEventListener('overlaycomplete', overlaycomplete);

		
		//初始化电子围栏 
	    render();
		 
}

//初始化电子围栏
function render(){
	var url=contextPath+"/TeeFootPrintRangeController/getInfoBySid.action";
    var json=tools.requestJsonRs(url,{sid:sid});
    if(json.rtState){
    	var data=json.rtData;
    	var rangeStr=tools.string2JsonObj(data.rangeStr);
    	if(rangeStr.length>0){//之前已经设置了电子围栏
    		var arr=[];
    	    for(var n=0;n<rangeStr.length;n++){
    	    	arr.push(new BMap.Point(rangeStr[n].LNG,rangeStr[n].LAT));
    	    }
    		var polygon = new BMap.Polygon(arr, {strokeColor:"red", fillColor:"red",  strokeWeight:3, strokeOpacity:0.8});  //创建多边形
    		 
    		 map.addOverlay(polygon);  
    		 
    		 overlays.push(polygon);
    		 
    		 //关闭可编辑
    		 drawingManager.close();
    		 
    	}
    }
}


function clearAll() {
	for(var i = 0; i < overlays.length; i++){
        map.removeOverlay(overlays[i]);
    }
    overlays=[];   
    drawingManager.open();
    drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
}

var tempoint = [];
function save(){
	allPoint();
	var param={};
	param["sid"]=sid;
	param["rangeStr"]=tools.jsonArray2String(tempoint);

	var url=contextPath+"/TeeFootPrintRangeController/design.action";
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		$.MsgBox.Alert_auto("保存成功！");
	}else{
		$.MsgBox.Alert_auto("保存失败！");
	}
	
}

//获取点
function allPoint() {
	tempoint=[];
    var allOverlay = map.getOverlays();
    for (var i = 0; i < allOverlay.length ; i++) {
        if (allOverlay[i].toString() == "[object Polygon]") {
            for (var num = 0; num < allOverlay[i].getPath().length; num++) {
                if (num != allOverlay[i].getPath().length - 1) {
                    tempoint.push({"LNG":allOverlay[i].getPath()[num].lng,"LAT":allOverlay[i].getPath()[num].lat});
                } else {
                    tempoint.push({"LNG":allOverlay[i].getPath()[num].lng,"LAT":allOverlay[i].getPath()[num].lat});
                }
            }
        }
    }
}
</script>

<body onload="doInit();">
   <div id="searchBox" style="height: 8%"></div>
   <div id="allmap" style="height: 87%"></div>
   <div id="result" style="height: 5%">
		<input type="button" value="清除电子围栏" onclick="clearAll()"/>
		<input type="button" value="保存电子围栏" onclick="save()"/>
	</div>
</body>
</html>
