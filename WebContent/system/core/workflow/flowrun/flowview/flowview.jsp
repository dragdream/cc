<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	String thread_local_archives = TeeStringUtil.getString(request.getParameter("thread_local_archives"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
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
				
		<link rel="stylesheet" href="<%=contextPath%>/common/jsPlumb/inst/css/demo-all.css" />
		<link rel="stylesheet" href="<%=contextPath%>/common/tooltip/jquery.tooltip.css" />
		
		<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
		 <!-- jquery jsPlumb adapter -->
		<script src="<%=contextPath%>/common/jsPlumb/src/jquery.jsPlumb.js"></script>
		<!-- /JS -->
		<script type="text/javascript" src="<%=contextPath%>/common/js/flowdesigner/flowdesignerForView.js?v=1"></script>
		<script src="<%=contextPath %>/common/js/popupmenu/popupmenu.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/js/popupmenu/res/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/js/flowdesigner/res/style.css?v=1" />
		<link rel="stylesheet" type="text/css" href="<%=cssPath %>/style.css" />
		<script src="<%=contextPath %>/common/tooltip/jquery.tooltip.min.js"></script>
<script>
var maxPrcsId=1;
var designer;//获取设计器
var startNode;
var endNode;
var flowId = <%=flowId%>;
var runId = "<%=runId%>";
var thread_local_archives = "<%=thread_local_archives%>";
var curFlowPrcs=0;
var isEnd=false;
window.onload=function(){
	getCurrentFlowProcess();
	designer = new Designer("notepad");
	
	//获取流程步骤
	var url = contextPath+"/flowProcess/getProcessList.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,thread_local_archives:thread_local_archives});
	var prcsList;
	if(json.rtState){
		prcsList = json.rtData;
	}

	//获取流程信息
	url = contextPath+"/flowType/get.action";
	json = tools.requestJsonRs(url,{flowTypeId:flowId,thread_local_archives:thread_local_archives});
	if(json.rtState){
		$("#flowName").html(json.rtData.flowName);
	}else{
		messageMsg(json.rtMsg,"layout","error");
		return;
	}

	if(!prcsList){
		alert("未定义的流程步骤");
		return;
	}

	
	//绘制步骤
	
	for(var i=0;i<prcsList.length;i++){
		var data = prcsList[i];
		var sid = data.sid;
		var prcsName = data.prcsName;
		var prcsId = data.prcsId;
		var x = data.x;
		var y = data.y;
		data.curFlowPrcsId=curFlowPrcs;
		data.isEnd=isEnd;
		if(data.prcsType==1){//开始节点
			startNode = designer.drawStart(x,y,data);
		}else if(data.prcsType==2){//结束节点
			endNode = designer.drawEnd(x,y,data);
		}else if(data.prcsType==3){//普通节点
			var node = designer.drawSimpleNode(x,y,data);
		}else if(data.prcsType==4){//并发节点
			var node = designer.drawParallelNode(x,y,data);
		}else if(data.prcsType==5){//聚合节点
			var node = designer.drawAggregationNode(x,y,data);
		}else if(data.prcsType==6){//子流程节点
			var node = designer.drawChildNode(x,y,data);
		}else if(data.prcsType==7){//柔性节点
			var node = designer.drawSoftNode(x,y,data);
		}
	}
	
	
	//绘制步骤
	for(var i=0;i<prcsList.length;i++){
		var data = prcsList[i];
		var sid = data.sid;
		var nexts = data.params.nextPrcs;
		var node = $("#"+sid);
		node.attr("title","步骤号："+data.prcsId+"<br/>"+data.prcsName);
		for(var j=0;j<nexts.length;j++){
			var cur = $("#"+nexts[j]);
			node.addNextNode(cur);
		}
	}
	
	designer.doLineTo();

	$("[title]").tooltips();
	
	/**
	//全局移动监听器
	var movedListener = function(nodes,event){
		designer.repaint();//重绘
		designer.doLineTo();//根据连线规则绘制连线
	};
	
	//添加监听器
	designer.setMovedListener(movedListener);
	designer.repaint();//重绘
	designer.doLineTo();
	**/
}
/**
 * 根据runId获取当前流程当前所处的设计步骤
 */
function getCurrentFlowProcess(){
	var url = contextPath+"/flowInfoChar/getFlowRunViewGraphicsData.action";
	var json = tools.requestJsonRs(url,{runId:runId,thread_local_archives:thread_local_archives});
	//渲染并绘制步骤信息
	if(json.rtState){
		var prcsList = json.rtData.prcsList;
		isEnd = json.rtData.isEnd;
		curFlowPrcs = prcsList[prcsList.length-1].flowPrcsId;
	}
}
</script>
<style type="text/css" media=print>
.noprint{display : none }
</style>
</head>
<body style="font-size:12px;margin:0px;background-image:url(<%=systemImagePath %>/workflow/flowdesignbg.png)" oncontextmenu="return false">
<div id="layout">
	<div layout="center" id="notepad" min=20 style="overflow:hidden">
		<div class="explanation">
			<SPAN class=big3>流程名称：<span id="flowName"></span></SPAN> 
			<DIV>
			标识说明：<img src="<%=systemImagePath %>/workflow/start_node.png" />
			开始 &nbsp;&nbsp;<img src="<%=systemImagePath %>/workflow/end_node.png" />
			结束 &nbsp;&nbsp;<img src="<%=systemImagePath %>/workflow/simple_node.png" />
			办理&nbsp;&nbsp;<img src="<%=request.getContextPath() %>/common/images/workflow/soft_node.png" />
			办理(循环)&nbsp;&nbsp;<img src="<%=request.getContextPath() %>/common/images/workflow/parallel_node.png" />
			并发 &nbsp;&nbsp;<img src="<%=systemImagePath %>/workflow/aggregation_node.png" />
			聚合&nbsp;&nbsp;<img src="<%=systemImagePath %>/workflow/child_node.png" />
			子流程
			</DIV>
		</div>
	</div>
</div>
</body>
</html>