<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script src="/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/data.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/funnel.js"></script>
<title>围栏监控</title>
<script type="text/javascript">
//初始化
function doInit(){
	//初始化图片
	renderImg();	
}

//改变日期范围
function changeSelType(){
	var selectType=$("#selectType").val();
	if(selectType==6){
		$("#dateSpan").show();
	}else{
		$("#dateSpan").hide();
		$("#beginDateStr").val("");
		$("#endDateStr").val("");
		renderImg();
	}
	
	
}

//渲染柱状图
function renderImg(){
	var url=contextPath+"/TeeFootPrintController/renderCrossImg.action";
	var param=tools.formToJson($("#form1"));
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		$("#container").highcharts({
	        chart: {
	            type: "column"    //column 柱狀  bar 條形圖  pie 餅圖  line 折綫圖 
	        },
	        title: {
	            text: "电子围栏报警情况统计"
	        },
	        xAxis: {
	            type:"category"
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '报警次数'
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
	                    	
	                    	columnClick(event.point.uuid,event.point.name);
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
	        series:data.series
	        	
	    });
	}
}

//点击柱状图
function columnClick(userId,userName){
	var selectType=$("#selectType").val();
	var beginDateStr=$("#beginDateStr").val();
	var endDateStr=$("#endDateStr").val();
	var url=contextPath+"/system/subsys/footprintrange/detail.jsp?userId="+userId+"&&selectType="+selectType+"&&beginDateStr="+beginDateStr+"&&endDateStr="+endDateStr+"&&userName="+userName+"";
    openFullWindow(url);
}

function changeDate(){
	var beginDateStr=$("#beginDateStr").val();
	var endDateStr=$("#endDateStr").val();
	if(beginDateStr!=""&&beginDateStr!=null&&beginDateStr!="null"&&endDateStr!=""&&endDateStr!=null&&endDateStr!="null"){
		renderImg();
	}
}
</script>
</head>

<body onload="doInit()">
   <form id="form1" name="form1" method="post">
	   <div style="position: absolute;left:0px;right:0px;top:0px;height:40px;border-bottom: 2px solid #f0f0f0">
	        
	       时间范围：<select id="selectType" name="selectType" class="BigSelect" style="margin-top: 10px;height: 23px" onchange="changeSelType()">
	            <option value="1">今日</option>
	            <option value="2">昨日</option>
	            <option value="3">本周</option>
	            <option value="4">本月</option>
	            <option value="5">本年</option>
	            <option value="6">指定范围</option>
	        </select>
	        
	       <span style="display: none" id="dateSpan">&nbsp;&nbsp;&nbsp;&nbsp;开始时间：<input type="text" id="beginDateStr" 	name="beginDateStr" size="12"
			onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDateStr\')}'})"
			class="Wdate BigInput"  onfocus="changeDate();"/>&nbsp;&nbsp;&nbsp;&nbsp;结束时间：<input type="text" id="endDateStr"	name="endDateStr" size="12"
			onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginDateStr\')}'})"
			class="Wdate BigInput"  onfocus="changeDate();"/></span>
	   </div>
	   <div id="container" style="position: absolute;left:0px;right:0px;top:41px;bottom:0px;border-bottom: 2px solid #f0f0f0">
	       
	   </div>
   </form>
</body>
</html>