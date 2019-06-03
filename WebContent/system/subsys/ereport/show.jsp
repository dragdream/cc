<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/header/header2.0.jsp" %>
<script src="/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/data.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/funnel.js"></script>
<title>报表查看</title>
<script type="text/javascript">
var sid=<%=sid %>;
function doInit(){
	
	renderGraphics();
	
}


function renderGraphics(){

	var url=contextPath+"/teeEreportController/renderGraph.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		
		if(data.chartTypeDesc=="pie"){//饼状图
			$('#container').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: data.title
		        },
		        tooltip: {
		            headerFormat: '{series.name}<br>',
		            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    }
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: data.title,
		            data: data.series
		        }]
		    });
		}else if(data.chartTypeDesc=="xy"){//双轴图
			$('#container').highcharts({
		        chart: {
		            zoomType: 'xy'
		        },
		        title: {
		            text: data.title
		        },
		        xAxis: [{
		            categories: data.categories,
		            crosshair: true
		        }],
		        yAxis: [{
		            labels: {
		                format: '{value}'
		            },
		            title: {
		                text: '主轴数值'   
		            }
		        }, {
		            
		            labels: {
		                format: '{value}'
		            },
		            title: {
		                text: '次轴数值'
		            },opposite:true
		        }],
		        
		        tooltip: {
		            shared: true
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'left',
		            x: 80,
		            verticalAlign: 'top',
		            y: 55,
		            floating: true,
		            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
		        },
		        series:data.series
		        	
		    });
		}else{
			$("#container").highcharts({
		        chart: {
		            type: data.chartTypeDesc   //column 柱狀  bar 條形圖  pie 餅圖  line 折綫圖 
		        },
		        title: {
		            text: data.title
		        },
		        xAxis: {
		            categories: data.categories
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '统计值'
		            }
		        },
		        legend: {
		            layout: 'vertical',
		            align: 'right',
		            verticalAlign: 'middle',
		            borderWidth: 0
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:12px;font-weight:bold">{point.key}</span><table style="font-size:12px">',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                '<td style="padding:0"><b>{point.y}</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        credits: {
		            enabled: false
		        },
		        plotOptions: {
		            column: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            },
		            bar: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            },
		            line: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            },
		            scatter: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            },
		            area: {
		            	cursor: 'pointer',
		                pointPadding: 0.2,
		                borderWidth: 0,
		                events: {
		                    click: function (event) {
		                    	//curObj.clickEvent(this.name,event);
		                    }
		                }
		            }
		        },
		        series: data.series
		    });
		}
		
	}
	
}


</script>

</head>


<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
    <div id="container"></div>
</body>
</html>