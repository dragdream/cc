<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>图形统计</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<script src="<%=contextPath %>/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/data.src.js"></script>
<script>
function doInit(){
	var statCols = xparent.statCols;
	var groupBy = xparent.groupBy; 
	var rows = xparent.rows;
	var height = 150;
	var group = [];
	for(var i=0;i<rows.length;i++){
		group.push(rows[i][groupBy]);
		height+=(30*statCols.length);
	}
	
	for(var i=0;i<statCols.length;i++){
		statCols[i].name = statCols[i].defName;
		var data = [];
		for(var j=0;j<rows.length;j++){
			data.push(Number(rows[j][statCols[i].item]));
		}
		statCols[i].data = data;
	}
	
	$('#container').css({height:height}).highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: '分组统计'
        },
        xAxis: {
            categories: group
        },
        yAxis: {
            min: 0,
            title: {
                text: '统计列'
            }
        },
        tooltip: {
        	shared: false,
	        pointFormat: '<span style="color:{series.color}">{series.name}: <b>{point.y}</b><br>'
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: statCols
    });
}
</script>
</head>
<body onload="doInit()" >
<div id="container" ></div>
</body>
</html>