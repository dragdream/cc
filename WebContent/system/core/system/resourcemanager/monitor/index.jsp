<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/fusionCharts/FusionCharts.js"></script>
<script type="text/javascript">


function doInit() {
	 var url = "<%=contextPath%>/resourceManage/getResource.action";
	  var para = {};
	  var rtJsons = tools.requestJsonRs(url);
	  if(rtJsons.rtState){
		  var data = rtJsons.rtData;
		  $("#container").html(data.container + "&nbsp;");
		  $("#used").html(data.used + "&nbsp;");
		  $("#space").html(data.space + "&nbsp;");
		    
		  $("#spaceGb").html(data.spaceGb);
		  $("#usedGb").html(data.usedGb);
		  $("#containerGb").html(data.containerGb);
		  var chart = new FusionCharts("<%=contextPath%>/common/fusionCharts/Pie2D.swf", "ChartId", "500", "300", "0", "0");
		  chart.setJSONData({"chart": 
		     { "caption" : "系统资源管理" ,  "xAxisName" : "",   "yAxisName" : "已用空间" }, 
		        "data" :   [  { "label" : "可用空间", "value" : data.spaceGb }, 
		                   { "label" : "已用空间", "value" : data.usedGb }
		                  ]});	   
	 	 chart.render("chartdiv");
	  }else{
		  alert(rtJsons.rtMsg);
	  }

}
</script>
</head>
<body onload="doInit()">

<table border="0" width="100%" cellspacing="0" cellpadding="3" class="TableBlack">
  <tr>
    <td >
    	<span class="Big3"> OA所在分区硬盘空间使用情况</span>
    </td>
  </tr>
</table>

<table width="80%" align="center">
  <tr>
    <td style="height:2px;"> </td>
  </tr>
  <tr>
    <td>

<table class="TableBlock" width="500" align="center">
    <tr>
      <td width="80" class="TableData">已用空间：</td>
      <td class="TableData"> <span id="used"></span>B&nbsp;&nbsp;<b><span id="usedGb"></span>GB</b></td>
    </tr>
    <tr>
      <td class="TableData">可用空间：</td>
      <td class="TableData"> <span id="space"></span>B&nbsp;&nbsp;<b><span id="spaceGb"></span>GB</b></td>
    </tr>
    <tr class="">
      <td nowrap class="TableData">容量：</td>
      <td> <span id="container"></span>B&nbsp;&nbsp;<b><span id="containerGb"></span>GB</b></td>
    </tr>
</table>
    </td>

  </tr>

<tr>
  <td style="height:2px;">
  <div id="chartdiv" align="center"> 
         </div>
  
  </td>
</tr>
<tr>
  <td>
<center>
  
</center>
    </td>
  </tr>
</table>
</body>
</html>