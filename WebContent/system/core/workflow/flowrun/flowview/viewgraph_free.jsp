<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		<script type="text/javascript" src="<%=contextPath%>/common/js/flowdesigner/flowdesigner1.0.js"></script>
		<script src="<%=contextPath %>/common/js/popupmenu/popupmenu.js"></script>
		<script src="<%=contextPath %>/common/tooltip/jquery.tooltip.min.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/js/popupmenu/res/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/js/flowdesigner/res/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=cssPath %>/style.css" />
<!-- Bootstrap通用UI组件 -->
<script src="<%=contextPath %>/common/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap.css"/>
<style>
</style>
<script>
var runId = "<%=runId%>";
var initTop = 80;
var initLeft = 50;
var systemImagePath = "<%=systemImagePath%>";
var container;

var fillColor = "#5c96bc";	
jsPlumb.Defaults.Connector = [ "Straight", { curviness:20 } ];
jsPlumb.Defaults.DragOptions = { cursor: "pointer", zIndex:0 };
jsPlumb.Defaults.PaintStyle = { strokeStyle:fillColor, lineWidth:1.5 };
jsPlumb.Defaults.EndpointStyle = { radius:1, fillStyle:fillColor };
jsPlumb.Defaults.ConnectionOverlays = [[ "Arrow", { 
	location:-3,
	id:"arrow",
    length:14,
    foldback:0.8
} ],[ "Label", { location:0.2,id:"label", cssClass:"aLabel" }]];

var anchors = [[ "Perimeter", { anchorCount:25, shape:"Rectangle"}]];
var aConnection = {	
	anchor:anchors
};		
var type;
var childRunList = new Array();
function doInit(){
	var url = contextPath+"/flowInfoChar/getFlowRunChildData.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		childRunList = json.rtData;
	}else{
		messageMsg(json.rtMsg,"container","error");
		return;
	}
	
	var url = contextPath+"/flowInfoChar/getFlowRunViewGraphicsData.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		$("#runName").html(json.rtData.runName);
		$("#runId").html(json.rtData.runId);
		type = json.rtData.type;
		
		//渲染并绘制步骤信息
		var prcsList = json.rtData.prcsList;
		renderFlow(prcsList);

		$("[title]").tooltips();
	}else{
		messageMsg(json.rtMsg,"container","error");
		return;
	}
}

//{'prcsId_flowPrcs':[{},{},{}]}
function getPrcsListByGroup(prcsId,prcsList){
	var infoData = {};
	for(var i=0;i<prcsList.length;i++){
		var info = prcsList[i];
		if(info.prcsId!=prcsId){
			continue;
		}
		var key = info.prcsId+"_"+info.flowPrcsId;
		var array = infoData[key];
		if(!array){
			array = new Array();
		}
		array.push(info);
		infoData[key] = array;
	}
	return infoData;
}

//渲染固定流程相同步骤ID节点
function renderFlowSamePrcsIdNode(topDelta,leftDelta,key,array){
	var node = $("#"+key);
	var title = "<div style=\"width:420px\">";
	var hasBegin = false;//是否是开始节点
	var firstNode;
	for(var i=0;i<array.length;i++){
		var prcsInfo = array[i];
		firstNode = prcsInfo;
		if(prcsInfo.topFlag==1){//主办
			title+="主办："+prcsInfo.prcsUserName+" ";
		}else{//经办
			title+="经办："+prcsInfo.prcsUserName+" ";
		}

		if(prcsInfo.flag==1){//未接收
			title+="<span style=\"color:gray\">未接收</span>";
		}else if(prcsInfo.flag==2){//办理中
			title+="<span style=\"color:red\">办理中</span> 用时："+prcsInfo.passedTime;
		}else if(prcsInfo.flag==3 || prcsInfo.flag==4){//已办结
			title+="<span style=\"color:green\">已办结</span> 用时："+prcsInfo.passedTime;
		}

		title+="<br/>";
		if(prcsInfo.beginTimeDesc!=""){
			title+="接收时间："+prcsInfo.beginTimeDesc;
		}
		if(prcsInfo.endTimeDesc!=""){
			title+="&nbsp;&nbsp;办结时间："+prcsInfo.endTimeDesc;
		}
		
		if(node.length==0){
			if(prcsInfo.prcsType==6){
				node = $("<div class='node'><img src='"+systemImagePath+"/workflow/child_node.png'><br/>第"+prcsInfo.prcsId+"步<br/>"+prcsInfo.prcsName+"</div>");
			}else{
				node = $("<div class='node'><img src='"+systemImagePath+"/workflow/simple_node.png'><br/>第"+prcsInfo.prcsId+"步<br/>"+prcsInfo.prcsName+"</div>");
			}
			node.css({left:initLeft+leftDelta,top:initTop+topDelta});
			node.attr("id",key);
			node.attr("prcsId",prcsInfo.prcsId);
			node.attr("flowPrcsId",prcsInfo.flowPrcsId);
			node.attr("parent",prcsInfo.parent);
			node.appendTo(container);
			var width = node.css("width");
			var height = node.css("height");
			node.css({width:width,height:height});
		}
		title+="<br/><br/>";
	}
	title+="</div>";

	//检测是否存在子流程
	var existChild = 0;
	for(var i=0;i<childRunList.length;i++){
		if((childRunList[i].pFlowPrcsId+"")==(firstNode.flowPrcsId+"")){
			title+="<div style='color:red'>";
			title+="子流程："+childRunList[i].runName+"&nbsp;&nbsp;发起人："+childRunList[i].beginUserName+"";
			title+="</div>";

			if(existChild==0){
				node.bind("dblclick",function(){
					top.bsWindow(contextPath+"/system/core/workflow/flowrun/flowview/childviews.jsp?runId="+runId+"&flowPrcsId="+firstNode.flowPrcsId,"子流程信息",{width:"800",height:"300"});
				});
				existChild++;
			}
		}
	}
	node.attr("title",title);
	
}

function renderFlow(prcsList){
	container = $("#container");
	var maxPrcsId = prcsList[prcsList.length-1].prcsId;
	var leftDelta=0;
	for(var i=1;i<=maxPrcsId;i++){
		var group = getPrcsListByGroup(i,prcsList);
		var topDelta = 0;
		for(var key in group){
			var array = group[key];
			renderFlowSamePrcsIdNode(topDelta,leftDelta,key,array);
			topDelta += 80;
		}
		leftDelta += 150;
	}

	//连线并且加入拖拽事件
	var nodes = container.find("div[class=node]");
	if(type==1){
		nodes.each(function(i,obj){
			var parent = $(obj).attr("parent");
			var prcsId = parseInt($(obj).attr("prcsId"));
			var parentArray = new Array();
			var sp;
			if(parent!="" && parent!=null){
				sp = parent.split(",");
				for(var j=0;j<sp.length;j++){
					parentArray.push(sp[j]);
				}
				for(var j=0;j<parentArray.length;j++){
					for(var i=prcsId-1;i>=0;i--){
						var parentNode = $("#"+(i)+"_"+parentArray[j]);
						if(parentNode.length!=0){
							jsPlumb.connect({source:parentNode, target:$(obj)},aConnection);
							break;
						}
					}
				}
			}
			jsPlumb.draggable($(obj));
		});
	}else{
		for(var i=0;i<nodes.length-1;i++){
			var cur = $(nodes[i]);
			var next = $(nodes[i+1]);
			jsPlumb.connect({source:cur, target:next},aConnection);
			jsPlumb.draggable($(cur));
			jsPlumb.draggable($(next));
		}
	}
}

</script>

</head>
<body onload="doInit()" style="margin:0px;background-image:url(<%=systemImagePath %>/workflow/flowdesignbg.png)">
	<div id="container">
		<div class="explanation" style="width:460px;font-size:12px">
			<SPAN ><b>流程标题：</b><span id="runName"></span>&nbsp;&nbsp;流水号：<span id="runId"></span></SPAN> 
			<div>
				<b>注：</b>如有子流程，请双击步骤信息查看子流程。
			</div>
		</div>
	</div>
</body>
</html>