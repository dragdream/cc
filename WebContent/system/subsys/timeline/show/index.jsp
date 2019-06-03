<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String timelineUuid = request.getParameter("timelineUuid")==null?"0":request.getParameter("timelineUuid");
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta charset="utf-8">
<title>大事记事件时间轴展示</title>
<link href="css/history.css" rel="stylesheet" />
<script src="js/jquery.js"></script>
<script src="<%=request.getContextPath() %>/common/js/tools.js"></script>
<script type="text/javascript">
var timelineUuid = "<%=timelineUuid%>";
var contextPath = "<%=request.getContextPath()%>";
function getTimeline(){
	var url = contextPath+"/TeeTimelineController/getById.action?uuid="+timelineUuid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		$("#timelineTitle").html(json.rtData.title);
	}
}
</script>
</head>

<body style="background:white">
<!-- 代码 开始 -->
<div class="head-warp">
  <div class="head">
        <div class="nav-box">
          <ul style='width:80%;margin:0 auto;padding-top:10px;padding-bottom:10px; border-bottom:2px solid #ddd;'>
              <li class="cur" style="text-align:center; font-size:30px; font-family:'微软雅黑', '宋体';" id="timelineTitle"></li>
          </ul>
        </div>
  </div>
</div>
<div class="main">
  <div class="history" id="history">
  </div>
</div>

 
<script>
getTimeline();
var url = contextPath+"/TeeTimelineEventController/getEvent.action?timelineUuid="+timelineUuid;
var json = tools.requestJsonRs(url);
if(json.rtState){
	var data = json.rtData;
	var html = [];
	//最小年限
	var minYear = 0;
	var maxYear = 0;
	if(data.length!=0){
		minYear = parseInt(data[data.length-1].startTimeDesc.split("-")[0]);
		maxYear = parseInt(data[0].startTimeDesc.split("-")[0]);
	}

	if(minYear!=0 && maxYear!=0){
		for(var i=maxYear;i>=minYear;i--){
			html.push("<div class=\"history-date\">");
			html.push("<ul class='first'>");
			if(i==maxYear){
				html.push("<h2 class=\"\" style='color:blue'><span style='display:inline-block;width:56px;height:24px;background-color:#fea500;color:#fff;text-align: center;line-height: 24px;font-size:18px;margin-top:18px;'>"+i+"</span></h2>");
			}else{
				html.push("<h2 class=\"date02\" style='color:blue'><span style='display:inline-block;width:56px;height:24px;background-color:#fea500;color:#fff;text-align: center;line-height: 24px;font-size:18px;margin-top:18px;'>"+i+"</span></h2>");
			}
			
			for(var j=0;j<data.length;j++){
				if(parseInt(data[j].startTimeDesc.split("-")[0])!=i){
					continue;
				}
				html.push("<li class=\"green\">");
				html.push("<h3>"+data[j].startTimeDesc.substring(5,7)+"-"+data[j].startTimeDesc.substring(8,10)+"<span>"+i+"</span></h3>");
				html.push("<ul style='text-indent:30px;'>");
				html.push("<li>"+"<span style='font-weight: bold;font-size: 18px;font-family: \"微软雅黑\"'>"+data[j].title+"</span>");
				html.push("<p style='font-weight: normal;font-size: 14px;font-family: \"微软雅黑\";color:#999;'>"+data[j].content+"</p>");
				html.push("</li>");
				html.push("</ul>");
				html.push("</li>");
			}
			html.push("</ul>");
			html.push("</div>");
		}
		$("#history").html(html.join(""));
	}
	
}
</script>
</body>
</html>
