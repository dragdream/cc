<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程超时统计</title>
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
	
	tools.requestJsonRs(contextPath+"/flowStatistic/timeoutStatistic.action",para,true,function(json){
		var list = json.rtData;
		var html = [];
		html.push("<tr>");
		html.push("<td width='600px;'>"+''+"</td>");
		html.push("<td>"+"超时工作"+"</td>");
		html.push("</tr>");
		for(var i=0;i<list.length;i++){
			var data = list[i];
			html.push("<tr>");
			html.push("<td>"+data.key+"</td>");
			html.push("<td>"+data.value+"</td>");
			html.push("</tr>");
		}
		$("#tbody").html(html.join(""));
		
		$('#container').highcharts({
	        data: {
	            table: document.getElementById('table')
	        },
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: beginTime+" 至 "+endTime+" "+flowName+"的超时统计"
	        },
	        yAxis: {
	            allowDecimals: false,
	            title: {
	                text: ''
	            }
	        },
	        tooltip: {
	            formatter: function() {
	                return '<b>'+ this.series.name +'</b><br>'+
	                    this.y;
	            }
	        }
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
		<h4 style="font-size: 16px;font-family: Microsoft YaHei">超时统计</h4>
	</div>
    <div class="fr" style="position:static">
	    <button class="btn-win-white" onclick="history.go(-1)">返回</button>&nbsp;
    </div>
	<span class="basic_border" ></span>
</div>
<center>
	<div id="container" style="width:90%">
		
	</div>
	<div>
		<table id="table" width='80%' align='center' style='font-size:12px;'>
			<tbody id="tbody">
				<tr>
					<td></td>
					<td>超时工作</td>
				</tr>
			</tbody>
		</table>
	</div>
</center>
</body>
</html>