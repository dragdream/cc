<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int flowTypeId=TeeStringUtil.getInteger(request.getParameter("flowTypeId"),0);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>流程自定义菜单</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
	.mui-media-body{
		line-height:42px;
	}
</style>
<script type="text/javascript">
var flowTypeId=<%=flowTypeId %>;
function doInit(){
	$.ajax({
		  type: 'POST',
		  url: contextPath+"/flowType/get.action",
		  data: {flowTypeId:flowTypeId},
		  timeout: 6000,
		  success: function(data){
			  var json = eval('(' + data + ')');
			  var flowName=json.rtData.flowName;
			  $("#title1").html(flowName);
		  },
		  error: function(xhr, type){
		    
		  }
		});
	
}

//新建流程
function create(){
	$.ajax({
		  type: 'POST',
		  url: contextPath+"/flowRun/createNewWork.action",
		  data: {fType:flowTypeId},
		  timeout: 6000,
		  success: function(data){
			  var json = eval('(' + data + ')');
			  var runId = json.rtData.runId;
			  var frpSid = json.rtData.frpSid;
			  if(json.rtState){
				   // window.location = "../prcs/form.jsp?runId="+runId+"&frpSid="+frpSid+"&flowId="+flowTypeId+"&flowTypeId="+flowTypeId;	 
				   OpenWindow("流程办理","/system/mobile/phone/workflow/prcs/form.jsp?runId="+runId+"&frpSid="+frpSid+"&flowId="+flowTypeId+"&flowTypeId="+flowTypeId,true);
			  }else{
					Alert(json.rtMsg);
			  }
		  },
		  error: function(xhr, type){
		    
		  }
		});
}

</script>
</head>
<body onload="doInit();">
		<header class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" onclick="CloseWindow()"></span>
			<h1 class="mui-title" id="title1"></h1>
		</header>
		<div class="mui-content">
			<div id="wrapper">
				<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media">
					<a href="#" onclick="create();" class="">
						<img class="mui-media-object mui-pull-left" src="../images/icon_xjgz.png">
						<div class="mui-media-body">
							新建工作
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="daiban.jsp?flowTypeId=<%=flowTypeId %>" class="">
						<img class="mui-media-object mui-pull-left" src="../images/icon_wddb.png">
						<div class="mui-media-body">
							我的待办
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="yibanjie.jsp?flowTypeId=<%=flowTypeId %>" class="">
						<img class="mui-media-object mui-pull-left" src="../images/icon_wybj.png">
						<div class="mui-media-body">
							我已办结
						</div>
					</a>
				</li><li class="mui-table-view-cell mui-media">
					<a href="search.jsp?flowTypeId=<%=flowTypeId %>" class="">
						<img class="mui-media-object mui-pull-left" src="../images/icon_gzcx.png">
						<div class="mui-media-body">
							工作查询
						</div>
					</a>
				</li>
			</ul>
			</div>
		</div>
</body>
</html>