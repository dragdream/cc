<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程办理情况统计</title>
<script src="<%=contextPath %>/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/data.src.js"></script>
<%
	String beginTime = TeeStringUtil.getString(request.getParameter("beginTime"));
	String endTime = TeeStringUtil.getString(request.getParameter("endTime"));
	String flowId = TeeStringUtil.getString(request.getParameter("flowId"));
	String target = TeeStringUtil.getString(request.getParameter("target"));
	String userIds = TeeStringUtil.getString(request.getParameter("userIds"));
	String deptIds = TeeStringUtil.getString(request.getParameter("deptIds"));
%>
<script>
var flowId = "<%=flowId%>";
var target = "<%=target%>";
function doInit(){
	var para = {beginTime:"<%=beginTime%>",endTime:"<%=endTime%>",flowId:"<%=flowId%>",target:"<%=target%>",userIds:"<%=userIds%>",deptIds:"<%=deptIds%>"};
	var beginTime = "<%=beginTime%>";
	var endTime = "<%=endTime%>";
	
	var flowName = "";
	if(flowId=="0"){
		flowName = "全部流程";
	}else{
		var url = contextPath+"/flowType/get.action";
		var json = tools.requestJsonRs(url,{flowTypeId:flowId});
		flowName = json.rtData.flowName;
	}
	
	if(target=="1"){
		$("#groupName").html("姓名");
	}else if(target=="2"){
		$("#groupName").html("部门");
	}else if(target=="3"){
		$("#groupName").html("月份");
	}
	
	
	tools.requestJsonRs(contextPath+"/flowStatistic/handle0Statistic.action",para,true,function(json){
		var list = json.rtData;
		var html = [];
		
		
		var categories = [];
		var data1 = [];
		var data2 = [];
		
		html.push("<tr>");
		html.push("<td>月份</td>");
		html.push("<td>待办数</td>");
		html.push("<td>48小时内办结数</td>");
		html.push("<td>办理准时率</td>");
		html.push("</tr>");
	
		for(var i=0;i<list.length;i++){
			var data = list[i];
			categories.push(data.key);
			data1.push(data.dbsl);
			data2.push(data.bj48);
			html.push("<tr>");
			html.push("<th>"+data.key+"</th>");
			html.push("<th>"+data.dbsl+"</th>");
			html.push("<th>"+data.bj48+"</th>");
			var rate = ((data.bj48/data.dbsl)*100).toFixed(2);
			if(isNaN(rate)){
				rate = 0;
			}
			html.push("<td>"+rate+"%</td>");
			html.push("</tr>");
		}
		
		$("#tbody").html(html.join(""));
		var h = 0;
		if(list.length<3){
			h = 400;
		}else{
			h = list.length*50;
		}
		
		$("#container").css({"height":h});
		
		$('#container').highcharts({                                           
	        chart: {                                                           
	            type: 'bar'                                                    
	        },                                                                 
	        title: {                                                           
	            text: beginTime+" 至 "+endTime+" "+flowName+"的流程办理情况统计"                    
	        },                                                                 
	        xAxis: {                                                           
	            categories: categories,
	            title: {                                                       
	                text: null                                                 
	            }                                                              
	        },                                                                 
	        yAxis: {                                                           
	            min: 0,                                                        
	            title: {                                                       
	                text: '',                             
	                align: 'high'                                              
	            },                                                             
	            labels: {                                                      
	                overflow: 'justify'                                        
	            }                                                              
	        },                                                                 
	        tooltip: {                                                         
	            valueSuffix: ' '                                       
	        },                                                                 
	        plotOptions: {                                                     
	            bar: {                                                         
	                dataLabels: {                                              
	                    enabled: true                                          
	                }                                                          
	            }                                                              
	        },                                                                 
	        legend: {                                                          
	            layout: 'vertical',                                            
	            align: 'right',                                                
	            verticalAlign: 'top',                                          
	            x: -40,                                                        
	            y: 80,                                                        
	            floating: true,                                                
	            borderWidth: 1,                                                
	            backgroundColor: '#FFFFFF',                                    
	            shadow: true                                                   
	        },                                                                 
	        credits: {                                                         
	            enabled: false                                                 
	        },                                                                 
	        series: [{                                                         
	            name: '任务数量',                                             
	            data: data1                                  
	        },{                                                         
	            name: '48小时内办结数',                                             
	            data: data2                                 
	        }]                                                                 
	    });                                
	});
}
</script>
<style>
table{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	font-weight:bold;
}
table tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8; 
}
</style>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div id="toolbar" class="clearfix" style="padding-top: 10px;">
    <div class="fl" style="position:static;">
		<h4 style="font-size: 16px;font-family: Microsoft YaHei">流程办理情况统计</h4>
	</div>
    <div class="fr" style="position:static">
	    <button class="btn-win-white" onclick="history.go(-1)">返回</button>&nbsp;
    </div>
	<span class="basic_border" ></span>
</div>
<center>
	<div id="container" style="width:90%;">
	</div>
	<div>
		
		<table id="table" width='80%' align='center' style='font-size:12px;'>
			<tbody id="tbody">
				
			</tbody>
		</table>
	</div>
</center>
</body>
</html>