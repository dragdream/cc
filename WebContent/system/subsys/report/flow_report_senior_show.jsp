<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String reportId = request.getParameter("reportId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/seniorreport.js"></script>
<script src="<%=contextPath %>/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/data.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/funnel.js"></script>

<script>
var reportId = "<%=reportId%>";

function doInit(){
	window.report = new SeniorReport({reportId:reportId,target:$("#container"),success:function(){
		$("#toolbar").show();
		report.show();
		document.title = report.tplName;
		$("#chartType").val(report.type);
	}});
}

function changeTypeEvent(val){
	window.report.changeType(val);
}

</script>
</head>
<body onload="doInit()" style="margin:10px;">
<div id="toolbar" style="display:none">
	<button class="btn btn-primary" onclick="report.originGraphics()">矩阵还原</button>
	<button class="btn btn-info" onclick="report.reversegenGraphics()">矩阵转置</button>
	<button class="btn btn-warning" onclick="report.exportExcel()">导出Excel</button>
	<select id="chartType" class="BigSelect" onchange="changeTypeEvent(this.value)">
		<option value="table">表格</option>
		<option value="column">柱状图</option>
		<option value="bar">条形图</option>
		<option value="line">曲线图</option>
		<option value="scatter">散列图</option>
		<option value="area">区域图</option>
		<option value="polar">雷达图</option>
		<option value="pie">饼状图</option>
		<option value="funnel">漏斗图</option>
	</select>
</div>
	<div id="container" style="position:absolute;top:50px;left:0px;right:0px;bottom:0px">
	</div>
<!-- 	<table id="table" class="table table-bordered table-striped" style="font-size:12px;width:auto;margin-top:20px;word-break: keep-all;" nowrap="nowrap"> -->
<!-- 		<thead id="thead"> -->
<!-- 			<tr id="theadTr1"> -->
<!-- 				<td></td> -->
<!-- 			</tr> -->
<!-- 		</thead> -->
<!-- 		<tbody id="tbody"> -->
			
<!-- 		</tbody> -->
<!-- 		<tfoot id="tfoot"> -->
<!-- 			<tr id="tfootTr1"> -->
<!-- 				<td></td> -->
<!-- 			</tr> -->
<!-- 		</tfoot> -->
<!-- 	</table> -->
</body>
</html>