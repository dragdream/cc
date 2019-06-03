<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeBeanFactory" %>
<%@ page import="com.tianee.oa.core.workflow.flowrun.service.TeeFlowInfoChartService" %>
<%@ include file="/header/header.jsp" %>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

	String runId = request.getParameter("runId");
	if(runId == null || "".equals(runId)){
		runId = "0";
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<title>jsPlumb 1.4.1 - Hierarchy Demonstration - jQuery</title>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		
		 <!-- DEP -->
	    <script src="<%=contextPath%>/common/jsPlumb/lib/jquery-1.9.0.js"></script>
		<script src="<%=contextPath%>/common/jsPlumb/lib/jquery-ui-1.9.2-min.js"></script>
        <script src="<%=contextPath%>/common/jsPlumb/lib/jquery.ui.touch-punch.min.js"></script>
		<!-- /DEP -->
		<!-- JS -->
		<!-- support lib for bezier stuff -->
		<script src="<%=contextPath%>/common/jsPlumb/lib/jsBezier-0.6.js"></script>        
		<!-- jsplumb util -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-util.js"></script>
        <!-- base DOM adapter -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-dom-adapter.js"></script>
        <!-- jsplumb drag-->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-drag.js"></script>
		<!-- main jsplumb engine -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb.js"></script>
        <!-- endpoint -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-endpoint.js"></script>                
        <!-- connection -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-connection.js"></script>
        <!-- anchors -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-anchors.js"></script>        
		<!-- connectors, endpoint and overlays  -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-defaults.js"></script>
		<!-- state machine connectors -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-connectors-statemachine.js"></script>
        <!-- flowchart connectors -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-connectors-flowchart.js"></script>
		<!-- SVG renderer -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-renderers-svg.js"></script>
		<!-- canvas renderer -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-renderers-canvas.js"></script>
		<!-- vml renderer -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jsPlumb-renderers-vml.js"></script>
		<!--  demo helper code -->
		<script src="<%=contextPath%>/common/jsPlumb/inst/js/demo-helper-jquery.js"></script>
				
		<script type="text/javascript" src="<%=contextPath%>/common/jsPlumb/inst/js/chartDemo.js"></script>
		<link rel="stylesheet" href="<%=contextPath%>/common/jsPlumb/inst/css/demo-all.css">
		<link rel="stylesheet" href="<%=contextPath%>/common/jsPlumb/inst/css/chartDemo.css">
		
		<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
		
		
		
		<script type="text/javascript">
		var contextPath = "<%=contextPath%>";
		var _runId = '<%=runId%>';
		var userId = "<%=person.getUserId()%>";
		var userName = "<%=person.getUserName()%>";
		$(function(){
			var url = contextPath+"/flowInfoChar/getCharDate.action";
			var json = tools.requestJsonRs(url,{runId:_runId});
			if(json.rtState){
				var data = json.rtData;
				 var _left = 10;
				  $.each(data, function(i,row){
					  if(i==1){
						  alert(row.runName);
						$("#runName").html(row.runName);
						}
					  var nodeDiv = document.createElement('div');
					    var nodeId = "window"+row.prcsId;
					    $(nodeDiv)
				        .attr("id",nodeId)
				        .addClass("window")
				        .css({"left":_left,"top":100})
				        .attr("title",row.prcsName)
				        .html("第"+row.prcsId+"步"+row.prcsName+"("+row.userName+")");
					    $("#prcsList").append(nodeDiv);
					    _left = _left + 190;
					    if(row.flowFlag ==1){
					    	 $(nodeDiv).addClass("window1");
						 }else if(row.flowFlag ==3){
							  $(nodeDiv).addClass("window3");
						}else if(row.flowFlag ==2){
							  $(nodeDiv).addClass("window2");
						}
				 });
			}

		//	jsPlumb.connect({source:"window1", target:this.id},aConnection);
			
		});
		
		
		
		</script>
	</head>
	<%
	
	
	
	%>
	<body style="margin:0px;background-image:url(<%=contextPath%>/system/core/workflow/flowtype/flowdesign/res/bg.png)" >

		
        <div id="prcsList">
			

		</div>
	
		
         <!-- jquery jsPlumb adapter -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jquery.jsPlumb.js"></script>
		<!-- /JS -->
		
		<!--  demo code -->
		<script src="<%=contextPath%>/common/jsPlumb/inst/js/chartDemo.js"></script>
		
	<div class="explanation">
		<SPAN class=big3><IMG align=absMiddle src="<%=imgPath%>/workflow.gif"><span id="runName"> </span>流水号：<%=runId %></SPAN> 
		<DIV>
		颜色标识说明：<SPAN style="COLOR: #ffbc18">■</SPAN>
		未接收 &nbsp;&nbsp;<SPAN style="COLOR: #50c625">■</SPAN>
		办理中 &nbsp;&nbsp;<SPAN style="COLOR: #7d26cd">■</SPAN>
		挂起中 &nbsp;&nbsp;<SPAN style="COLOR: #f4a8bd">■</SPAN>
		办理完毕 &nbsp;&nbsp;<SPAN style="COLOR: #d7d7d7">■</SPAN>
		预设步骤 &nbsp;&nbsp;<SPAN style="COLOR: #70a0dd">■</SPAN>
		子流程 &nbsp;&nbsp;注：子流程可双击步骤查看流程图&nbsp;&nbsp; 
		</DIV>
	</DIV>		
	</body>
</html>
