<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String catId = request.getParameter("catId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/js/seniorreport.js"></script>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script src="/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/data.src.js"></script>
<script type="text/javascript" src="/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="/common/highcharts/js/modules/funnel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>
<script>
var loader;
var catId = "<%=catId%>";
function doInit(){
	var json = tools.requestJsonRs(contextPath+"/seniorReportCat/datagrid.action",{rows:10000,page:1});
	var render = [];
	for(var i=0;i<json.rows.length;i++){
		render.push("<li  onclick='changed1("+json.rows[i].sid+")' class='"+((json.rows[i].sid+"")==catId?"active":"")+"'>"+json.rows[i].name+"</li>");
	}
	$("#catId").append(render.join("")).val(catId);
	
	if(catId=="null"){
		$("#catDefault").attr("class","active");
	}
	
	var i=0;
	var arr = [$("#column1"),$("#column2"),$("#column3")];
	if(!loader){
    	loader = new lazyLoader({
			url:contextPath+'/teeEreportController/datagridViews.action?catId='+catId,
			placeHolder:'loadMore',
			contentHolder:'content1',
			pageSize:6,
			rowRender:function(rowData){
				var render = [];
				render.push("<div class='portlet' style='border-color:"+rowData.catColor+"'>");
				render.push("<div class='portlet-header' style='background:"+rowData.catColor+"' onclick=\"openDetail('"+rowData.sid+"')\"><a>"+rowData.title+"</a></div>");
				render.push("<div class='portlet-content' style='overflow:auto' id='portlet"+rowData.sid+"'><center><img src='"+contextPath+"/common/styles/imgs/loading01.gif' /></center></div>");
				render.push("</div>");
				arr[(i++)%2].append(render.join(""));
				
				var report = new SeniorReport({reportId:rowData.sid,target:$("#portlet"+rowData.sid),showTitle:false});
				return "";
			},
			onLoadSuccess:function(){
				
			},
			onNoData:function(){
				$("#info").html("<center style='display:none;font-family:微软雅黑'><h4>无数据</h4></center>");
			}
		});
    }else{
    	loader.reload();
    }
	
	//添加scroll滚屏事件
	$(window).scroll(function(){
		if($(document).scrollTop()>=$(document).height()-$(window).height()){
			loader.load();
		}
	});
	
}

function openDetail(sid){
	openFullWindow(contextPath+"/system/subsys/ereport/show.jsp?sid="+sid);
}

function changed1(val){
	window.location = "mange_portal.jsp?catId="+val;
}

</script>
<style>
.column {
  float: left;
  padding-bottom: 100px;
}
.portlet {
  margin: 0 1em 1em 0;
  border:1px solid gray;
}
.portlet-header {
  padding: 5px;
  margin-bottom: 0.5em;
  position: relative;
  background:gray;
  font-weight:bold;
  color:white;
  font-size:14px;
  font-family:微软雅黑;
  color:white;
  cursor:pointer;
}
.portlet-header a{
  color:white;
  text-decoration:none;
}
.portlet-header:hover a{
color:yellow;

}
.portlet-toggle {
  position: absolute;
  top: 50%;
  right: 0;
  margin-top: -8px;
}
.portlet-content {
  padding: 0.4em;
  height:200px;
  overflow:hidden;
}
.portlet-placeholder {
  border: 1px dotted black;
  margin: 0 1em 1em 0;
  height: 50px;
}
ul{
padding:0px;
margin:0px;
list-style:none;
}
ul li{
padding:5px;
padding-left:15px;
padding-right:15px;
float:left;
font-weight:bold;
font-family:微软雅黑;
color:#7f7c77;
cursor:pointer;
}
.active{
color:white;
background:#adadad;
}
</style>
</head>
<body onload="doInit()" style="margin:10px">
<ul id="catId">
	<li id='catDefault' onclick="changed1(null)">全部</li>
</ul>
<div style="clear:both"></div>
	<div id="content" style="min-width:1024px;margin-top:10px;">
		<div id="column1" class="column" style="width:50%">
			
		</div>
		<div id="column2" class="column" style="width:50%">
			
		</div>
		
		<div id="info" style="clear:both"></div>
<!-- 		<center> -->
<!-- 			<div id="loadMore" style="background:#e2e2e2;padding:10px;width:200px;font-weight:bold;color:gray;cursor:pointer;"> -->
<!-- 				加载更多 -->
<!-- 			</div> -->
<!-- 		</center> -->
	</div>
</body>
</html>