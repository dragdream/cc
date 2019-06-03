<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%
  Date date = new Date();
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
  SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
  SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
  String dateStr = dateFormat.format(date);
  String monthStr = monthFormat.format(date);
  int year = Integer.parseInt(dateStr.substring(0,4));
  int month = Integer.parseInt(monthStr);
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/tabs/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/fusionCharts/FusionCharts.js"></script>

<script>
var chart1=null;
var chart2=null;
function doInit(){
	var monthInfo = getLogInfoByYearAndMonth();
	var yearInfo = getLogInfoByYear();
	$("#monthInfo").html(monthInfo);
	$("#yearInfo").html(yearInfo);
	query();
	queryOfMonth();
}


function getLogInfoByYear(){
	var year = $("#year").val();
	var last10Info="<table class='TableBlock' width='80%'>";
	var url =contextPath+"/sysLogManage/getLogInfoByYear.action?year="+year;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		last10Info+="<tr class='TableHeader'><td colspan='3' style='text-align:left;'>"+year+"年度按月访问数据</td></tr>";
		for(var i = 0;i<json.rtData.length;i++){
			var info = json.rtData[i];
			last10Info+="<tr class='TableData'><td>"+(i+1)+"月</td><td>"+info.monthPer+"</td><td>"+info.monthTotal+"</td></tr>";
		}
		last10Info+="</table>";
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
	}
	return last10Info;
}

function changeYear(){
	var yearInfo=getLogInfoByYear();
	$("#yearInfo").html(yearInfo);
	var monthInfo = getLogInfoByYearAndMonth();
	$("#monthInfo").html(monthInfo);
	query();
	queryOfMonth();
}


function changeMonth(){
	var yearInfo=getLogInfoByYear();
	$("#yearInfo").html(yearInfo);
	var monthInfo = getLogInfoByYearAndMonth();
	$("#monthInfo").html(monthInfo);
	query();
	queryOfMonth();
}


function query(){
	var year = $("#year").val();
	var month = $("#month").val();
	var url =contextPath+"/sysLogManage/getGraphDataByYearAndMonth.action?year="+year+"&month="+month;
	var json = tools.requestJsonRs(url);
 	if(json.rtState){
	  var choseStr = "Column3D.swf";
	  if(null == FusionCharts.items["monthChart"]) {
		  CollectGarbage();  
	      chart1 = new FusionCharts("<%=contextPath%>/common/fusionCharts/"+choseStr+"", "monthChart", "800","400", "0", "0");
	  } else{  
		  chart1 = FusionCharts.items["monthChart"];  
      }  
	  chart1.setTransparent("false");
	  //chart1.setDataXML(json.rtData.graphData);
	  chart1.setJSONData(json.rtData.graphData); 
	  chart1.render("yearDiv");
 	} else {
 		top.$.jBox.tip(json.rtMsg,"error");
	 }
}


function queryOfMonth(){
	var year = $("#year").val();
	var month = $("#month").val();
	var url =contextPath+"/sysLogManage/getEverydayByYearAndMonth.action?year="+year+"&month="+month;
	var json = tools.requestJsonRs(url);
 	if(json.rtState){
	  var choseStr = "Column3D.swf";
	  if(null == FusionCharts.items["dayChart"]) {
		  CollectGarbage();  
		  chart2 = new FusionCharts("<%=contextPath%>/common/fusionCharts/"+choseStr+"", "dayChart", "800","400", "0", "0");
	  } else{  
		  chart2 = FusionCharts.items["dayChart"];  
      }  
	  chart2.setTransparent("false");
	  //chart1.setDataXML(json.rtData.graphData);
	  chart2.setJSONData(json.rtData.graphData); 
	  chart2.render("monthDiv");
 	} else {
 		top.$.jBox.tip(json.rtMsg,"error");
	 }
}

function getLogInfoByYearAndMonth(){
	var year = $("#year").val();
	var month = $("#month").val()
	var last10Info="<table class='TableBlock' width='80%'>";
	var url =contextPath+"/sysLogManage/getLogInfoByYearAndMonth.action?year="+year+"&month="+month;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		last10Info+="<tr class='TableHeader'><td colspan='3' style='text-align:left;'>"+year+"年"+month+"月访问数据</td></tr>";
		for(var i = 0;i<json.rtData.length;i++){
			var info = json.rtData[i];
			last10Info+="<tr class='TableData'><td>"+(i+1)+"天</td><td>"+info.daysPer+"</td><td>"+info.daysTotal+"</td></tr>";
		}
		last10Info+="</table>";
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
	}
	return last10Info;
}
</script>

</head>
<body onload="doInit()"  style="margin:0 auto;">
<div id="container" style="width:100%;height:auto;margin:0 auto;">
<div id="yearsandMonths">
<b>年度数据</b>
 <select id="year" name="year" onchange="changeYear()">
     <%
       for(int i=2000;i<2050;i++){
         if(i==year){
     %>
     <option value="<%=i %>" selected="selected"><%=i %>年</option>
       <%}else{ %>
     <option value="<%=i %>"><%=i %>年</option>
       <%
           }
        }
       %>
   </select>
    <select id="month" name="month" onchange="changeMonth()">
     <%
       for(int i=1;i<=12;i++){
         if(i==month){
     %>
     <option value="<%=i %>" selected="selected"><%=i %>月</option>
       <%}else{ %>
     <option value="<%=i %>"><%=i %>月</option>
       <%
           }
        }
       %>
   </select>
</div>
<center>
	<div id="yearDiv" style="min-height:200px; width:100%;margin:0 auto;"></div>
	<div id="yearInfo" style="min-height:200px;height:auto; width:100%; margin:0 auto;"></div>
	<div id="monthDiv" style="min-height:200px; width:100%;margin:0 auto;"></div>
	<div id="monthInfo" style="min-height:200px;height:auto; width:100%; margin:0 auto;"></div>
</center>
</div>
</body>
</html>

