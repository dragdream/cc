<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程处理情况统计</title>
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
var beginTime="<%=beginTime %>";
var endTime="<%=endTime %>";
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
	
	
	
	
	tools.requestJsonRs(contextPath+"/flowStatistic/handleStatistic.action",para,true,function(json){
		var list = json.rtData;
		var html = [];
		var field = [];
		var ngs = [];
		var blzs = [];
		var asbjs = [];
		var csbjs = [];
		var wbjs = [];
		var jbzs = [];
		var yjbs = [];
		var djbs = [];
		var series = [];
		
		var h = 0;
		if(list.length<3){
			h = 400;
		}else{
			h = list.length*150;
		}
		
		$("#container").css({"height":h});
		
		
		html.push("<tr>");
		html.push("<td rowspan='2'>"+"序号"+"</td>");
		html.push("<td rowspan='2' id='groupName'>姓名</td>");
		html.push("<td rowspan='2'>拟稿数</td>");
		html.push("<td></td>");
		html.push("<td colspan='3'>主办情况</td>");
		html.push("<td colspan='3'>经办情况</td>");
		html.push("</tr>");
		html.push("<tr style='border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; '>");
		html.push("<td>办理总数</td>");
		html.push("<td>按时办结数</td>");
		html.push("<td>超时办结数</td>");
		html.push("<td>未办结数</td>");
		html.push("<td>经办总数</td>");
		html.push("<td>已经办数</td>");
		html.push("<td>待经办数</td>");
		html.push("</tr>");
		
		for(var i=0;i<list.length;i++){
			var data = list[i];
			field.push(data.key);
			ngs.push(data.ngs);
			blzs.push(data.blzs);
			asbjs.push(data.asbjs);
			csbjs.push(data.csbjs);
			wbjs.push(data.wbjs);
			jbzs.push(data.jbzs);
			yjbs.push(data.yjbs);
			djbs.push(data.djbs);
			
			html.push("<tr>");
			html.push("<td>"+(i+1)+"</td>");
			html.push("<td>"+data.key+"</td>");
			
			if(target=="1"){//人员
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData1("+data.uuid+",'ngs','"+target+"','拟稿数（"+data.key+"）');\">"+data.ngs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData1("+data.uuid+",'blzs','"+target+"','办理总数（"+data.key+"）');\">"+data.blzs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData1("+data.uuid+",'asbjs','"+target+"','按时办结数（"+data.key+"）');\">"+data.asbjs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData1("+data.uuid+",'csbjs','"+target+"','超时办结数（"+data.key+"）');\">"+data.csbjs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData1("+data.uuid+",'wbjs','"+target+"','未办结数（"+data.key+"）');\">"+data.wbjs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData1("+data.uuid+",'jbzs','"+target+"','经办总数（"+data.key+"）');\">"+data.jbzs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData1("+data.uuid+",'yjbs','"+target+"','已经办数（"+data.key+"）');\">"+data.yjbs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData1("+data.uuid+",'djbs','"+target+"','待经办数（"+data.key+"）');\">"+data.djbs+"</a></td>");
			}else if(target=="2"){//部门
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData2("+data.uuid+",'ngs','"+target+"','拟稿数（"+data.key+"）');\">"+data.ngs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData2("+data.uuid+",'blzs','"+target+"','办理总数（"+data.key+"）');\">"+data.blzs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData2("+data.uuid+",'asbjs','"+target+"','按时办结数（"+data.key+"）');\">"+data.asbjs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData2("+data.uuid+",'csbjs','"+target+"','超时办结数（"+data.key+"）');\">"+data.csbjs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData2("+data.uuid+",'wbjs','"+target+"','未办结数（"+data.key+"）');\">"+data.wbjs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData2("+data.uuid+",'jbzs','"+target+"','经办总数（"+data.key+"）');\">"+data.jbzs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData2("+data.uuid+",'yjbs','"+target+"','已经办数（"+data.key+"）');\">"+data.yjbs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData2("+data.uuid+",'djbs','"+target+"','待经办数（"+data.key+"）');\">"+data.djbs+"</a></td>");
			}else if(target=="3"){//月份
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData3('"+data.key+"','ngs','"+target+"','拟稿数（"+data.key+"）');\">"+data.ngs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData3('"+data.key+"','blzs','"+target+"','办理总数（"+data.key+"）');\">"+data.blzs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData3('"+data.key+"','asbjs','"+target+"','按时办结数（"+data.key+"）');\">"+data.asbjs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData3('"+data.key+"','csbjs','"+target+"','超时办结数（"+data.key+"）');\">"+data.csbjs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData3('"+data.key+"','wbjs','"+target+"','未办结数（"+data.key+"）');\">"+data.wbjs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData3('"+data.key+"','jbzs','"+target+"','经办总数（"+data.key+"）');\">"+data.jbzs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData3('"+data.key+"','yjbs','"+target+"','已经办数（"+data.key+"）');\">"+data.yjbs+"</a></td>");
				html.push("<td><a href=\"javaScript:void(0)\" onclick=\"getStatisticFlowData3('"+data.key+"','djbs','"+target+"','待经办数（"+data.key+"）');\">"+data.djbs+"</a></td>");
			}
			
			html.push("</tr>");
		}
		
		series.push({name:"拟稿数",data:ngs});
		series.push({name:"办理总数",data:blzs});
		series.push({name:"按时办理数",data:asbjs});
		series.push({name:"超时办理数",data:csbjs});
		series.push({name:"未办理数",data:wbjs});
		series.push({name:"经办总数",data:jbzs});
		series.push({name:"已经办数",data:yjbs});
		series.push({name:"待经办数",data:djbs});
		
		$("#tbody").html(html.join(""));
		if(target=="1"){
			$("#groupName").html("姓名");
		}else if(target=="2"){
			$("#groupName").html("部门");
		}else if(target=="3"){
			$("#groupName").html("月份");
		}
		
		$('#container').highcharts({                                           
	        chart: {                                                           
	            type: 'bar'                                                    
	        },                                                                 
	        title: {                                                           
	            text: beginTime+" 至 "+endTime+" "+flowName+"的流程处理情况统计"                   
	        },                                                                 
	        subtitle: {                                                        
	            text: ''                                  
	        },                                                                 
	        xAxis: {                                                           
	            categories: field,
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
	            valueSuffix: ''                                       
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
	        series: series                                                                 
	    });                                
	});
}



//查看流程数据详情(人员)
function getStatisticFlowData1(userId,type,target,typeDesc){
	var url=contextPath+"/system/core/workflow/flowstatistic/flowData.jsp?userId="+userId+"&type="+type+"&target="+target+"&beginTime="+beginTime+"&endTime="+endTime+"&flowId="+flowId+"&typeDesc="+typeDesc;
    openFullWindow(url);
}
//查看流程数据详情(部门)
function getStatisticFlowData2(deptId,type,target,typeDesc){
	var url=contextPath+"/system/core/workflow/flowstatistic/flowData.jsp?deptId="+deptId+"&type="+type+"&target="+target+"&beginTime="+beginTime+"&endTime="+endTime+"&flowId="+flowId+"&typeDesc="+typeDesc;
    openFullWindow(url);
}

//查看流程数据详情(月份)
function getStatisticFlowData3(month,type,target,typeDesc){
	var url=contextPath+"/system/core/workflow/flowstatistic/flowData.jsp?month="+month+"&type="+type+"&target="+target+"&beginTime="+beginTime+"&endTime="+endTime+"&flowId="+flowId+"&typeDesc="+typeDesc;
    openFullWindow(url);
}

//返回
function back(){
	window.location.href=contextPath+"/system/core/workflow/flowstatistic/index.jsp";
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
		<h4 style="font-size: 16px;font-family: Microsoft YaHei">流程处理情况统计</h4>
	</div>
    <div class="fr" style="position:static">
	    <button class="btn-win-white" onclick="back();">返回</button>&nbsp;
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