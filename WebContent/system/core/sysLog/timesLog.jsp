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

function doInit(){
	var timesInfo = getLogInfoByhoure();
	$("#timesInfo").html(timesInfo);
	query();
}


function getLogInfoByhoure(){
	var last10Info="<table class='TableBlock' width='80%'>";
	var url =contextPath+"/sysLogManage/getLogInfoByHour.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		last10Info+="<tr class='TableHeader'><td colspan='3' style='text-align:left;'>当月总访问量小时分布数据</td></tr>";
		for(var i = 0;i<json.rtData.length;i++){
			var info = json.rtData[i];
			last10Info+="<tr class='TableData'><td>"+(i)+"</td><td>"+info.timesPer+"</td><td>"+info.timesTotal+"</td></tr>";
		}
		last10Info+="</table>";
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
	}
	return last10Info;
}



function query(){
	var url =contextPath+"/sysLogManage/getGraphDataByTimes.action";
	var json = tools.requestJsonRs(url);
 	if(json.rtState){
	  var choseStr = "Column3D.swf";
	  var chart1 = new FusionCharts("<%=contextPath%>/common/fusionCharts/"+choseStr+"", "chart1Id", "800","400", "0", "0");
	  chart1.setTransparent("false");
	  chart1.setDataXML(json.rtData.graphData);
	  chart1.render("chart1div");
 	} else {
 		top.$.jBox.tip(json.rtMsg,"error");
	 }
}
</script>

</head>
<body onload="doInit()"  style="margin:0 auto;">
<div id="container" style="width:100%;height:auto;margin:0 auto;">
<center>
	<div id="chart1div" style="min-height:200px; width:100%;margin:0 auto;"></div>
	<div id="timesInfo" style="min-height:200px;height:auto; width:100%; margin:0 auto;"></div>
</center>
</div>
</body>
</html>

