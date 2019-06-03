<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<script>

function doInit(){
	var summaryInfo = getSummaryLogInfo();
	var last10Info = getLast10LogInfo();
	$("#summaryInfo").html(summaryInfo);
	$("#last10Info").html(last10Info);
}

function getSummaryLogInfo(){
	var summaryInfo="<table class='TableBlock' width='80%'>";
	summaryInfo+="<tr class='TableHeader'><td colspan='2' style='text-align:left;'>日志概况</td></tr>";
	var url =contextPath+"/sysLogManage/getSummaryLogInfo.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var summary = json.rtData;
		summaryInfo+="<tr class='TableData'><td width='30%'>总访问量：</td><td>"+summary.total+"</td></tr>";
		summaryInfo+="<tr class='TableData'><td width='30%'>今年访问量：</td><td>"+summary.yearTotal+"</td></tr>";
		summaryInfo+="<tr class='TableData'><td width='30%'>本月访问量:</td><td>"+summary.monthTotal+"</td></tr>";
		summaryInfo+="<tr class='TableData'><td width='30%'>今日访问量：</td><td>"+summary.dayTotal+"</td></tr>";
		summaryInfo+="</table>";
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
	}
	return summaryInfo;
}

function getLast10LogInfo(){
	var last10Info="<table class='TableBlock' width='80%'>";
	last10Info+="<tr class='TableHeader'><td colspan='5' style='text-align:left;'>最近十条日志</td></tr>";
	var url =contextPath+"/sysLogManage/getLast10LogInfo.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		for(var i = 0;i<json.rtData.length;i++){
			var info = json.rtData[i];
			last10Info+="<tr class='TableData'><td>"+info.userName+"</td><td>"+info.timeDesc+"</td><td>"+info.ip+"</td><td>"+info.type+"</td><td>"+info.remark+"</td></tr>";
		}
		last10Info+="</table>";
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
	}
	return last10Info;
}
</script>

</head>
<body onload="doInit()"  style="margin:0 auto;">
<div id="container" style="width:100%;height:auto;margin:0 auto;">
<center>
	<div id="summaryInfo" style="min-height:200px; width:100%;margin:0 auto;"></div>
	<div id="last10Info" style="min-height:200px;height:auto; width:100%; margin:0 auto;"></div>
</center>
</div>
</body>
</html>

