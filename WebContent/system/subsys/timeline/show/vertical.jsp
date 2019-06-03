<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String timelineUuid = request.getParameter("timelineUuid")==null?"0":request.getParameter("timelineUuid");
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta charset="utf-8">
<meta name="keywords" content="jquery时间轴，jQuery Timelinr，jquery" />
<meta name="description" content="Helloweba演示平台，演示XHTML、CSS、jquery、PHP案例和示例" />
<title>事件垂直时间轴展示</title>
<link rel="stylesheet" type="text/css" href="main.css" />
<style type="text/css">
#timeline {width: 760px;height: 440px;overflow: hidden;margin: 40px auto;position: relative;background: url('dot.gif') 210px top repeat-y;}
#dates {width: 215px;height: 440px;overflow: hidden;float: left;}
#dates li {list-style: none;width: 200px;height: 100px;line-height: 100px;font-size: 14px; padding-right:20px; text-align:right; background: url('biggerdot.png') 208px center no-repeat;}
#dates a {line-height: 100px;padding-bottom: 10px;}
#dates .selected {font-size: 28px;}
#issues {width: 530px;height: 440px;overflow: hidden;float: right;}	
#issues li {width: 630px;height: 440px;list-style: none;}
#issues li h1 {color: #ffcc00;font-size: 42px; height:52px; line-height:52px; text-shadow: #000 1px 1px 2px;}
#issues li p {font-size: 14px;margin: 10px;line-height: 26px;}
</style>
<script type="text/javascript" src="js/jquery.timelinr-0.9.53.js"></script>
<script type="text/javascript">
var timelineUuid = "<%=timelineUuid%>";
function doInit(){
	var url = contextPath+"/TeeTimelineEventController/getEvent.action?timelineUuid="+timelineUuid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		var html="";
		var contents="";
		for(var i=0;i<data.length;i++){
			html+="<li><a href=\"#"+data[i].startTimeDesc+"\">"+data[i].startTimeDesc+"</a></li>";
			contents+=" <li id=\""+data[i].startTimeDesc+"\"><h1>"+data[i].title+"</h1><p>"+data[i].content+"</p></li>";
		}
		$("#dates").html(html);
		$("#issues").html(contents);
	    $().timelinr({
			orientation: 	'vertical',
			issuesSpeed: 	300,
			datesSpeed: 	100,
			arrowKeys: 		'true',
			startAt:		3
		});
	}else{
	}
}
</script>
</head>

<body onload="doInit();">
<div id="main">
   <p style="margin:20px; text-align:center"><a href="index.jsp">Demo1:水平滚动</a> &nbsp;&nbsp; <a href="vertical.jsp" style="font-size:16px; font-weight:bold">Demo2:垂直滚动</a> &nbsp;&nbsp;
    <a href="mousewheel.jsp">Demo3:滚轮滚动</a></p>
<div id="timeline">
   <ul id="dates">
   </ul>
   <ul id="issues">
   </ul>
   <a href="#" id="next"></a> <!-- optional -->
   <a href="#" id="prev"></a> <!-- optional -->
</div>
</div>
</body>
</html>
