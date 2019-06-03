<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String reportId = TeeStringUtil.getString(request.getParameter("reportId") );
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
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

function doInit(yearValue){
	if(!yearValue){
		yearValue = <%=year%>;
	}
	//$("#container").empty();
	window.report = new SeniorReport({reportId:reportId,target:$("#container"),params:{year:yearValue},success:function(){
		report.show();
		report.showTable($("#containerTable"));
	}});
	
}

</script>
</head>
<body onload="doInit()" style="margin:10px;">
	&nbsp;&nbsp;年份：
	<select name="contractYear" id="contractYear" onchange="doInit(this.value)" class="BigSelect">
		<%
		for(int i =2000 ; i<=2100 ; i++ ){	
			if(year == i){
		%>	
			<option value='<%=i%>' selected="selected"> <%=i %>年</option>	
		<% 
			}else{
		%>
			<option value='<%=i%>'> <%=i %>年</option>	
			
		<%}} %>
	</select>
	<button class="btn btn-info" onclick="report.originGraphics()">矩阵还原</button>
	<button class="btn btn-info" onclick="report.reversegenGraphics()">矩阵转置</button>
	<button class="btn btn-warning" onclick="report.exportExcel()">导出Excel</button>
	<div id="container" style="margin-top:20px;">
	</div>
 	<div id="containerTable" > 
		
 	</div> 
</body>
</html>