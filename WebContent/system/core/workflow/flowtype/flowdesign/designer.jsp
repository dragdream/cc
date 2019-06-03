<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
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
	<script type="text/javascript" src="<%=contextPath%>/common/js/flowdesigner/flowdesigner1.0.js?v=1"></script>
	<script src="<%=contextPath %>/common/js/popupmenu/popupmenu.js"></script>
	<script src="<%=contextPath %>/common/tooltip/jquery.tooltip.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/js/popupmenu/res/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/js/flowdesigner/res/style.css?v=1" />
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css" />
	<!-- JBOX通用UI组件 -->
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/jbox-v2.3/jBox/Skins/Blue/jbox.css" />

<!-- Bootstrap通用UI组件 -->
<script src="<%=contextPath %>/common/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap.css"/>
<script>
var maxPrcsId=1;
var designer;//获取设计器
var startNode;
var endNode;
var flowId = <%=flowId%>;
window.onload=function(){
	designer = new Designer("notepad");
	repaint();
	top.flowId = flowId;
}

function repaint(){
	//保存
	
	
	$("#notepad").html("");
	
	//获取流程步骤
	var url = contextPath+"/flowProcess/getProcessList.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var prcsList;
	if(json.rtState){
		prcsList = json.rtData;
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
		
		if(data.prcsType==1){//开始节点
			startNode = designer.drawStart(x,y,data);
			//设置开始节点菜单
			startNode.mousedown(startEvent);
		}else if(data.prcsType==2){//结束节点
			endNode = designer.drawEnd(x,y,data);
		}else if(data.prcsType==3){//普通节点
			var node = designer.drawSimpleNode(x,y,data);
			node.mousedown(simpleNodeEvent);
		}else if(data.prcsType==4){//并发节点
			var node = designer.drawParallelNode(x,y,data);
			node.mousedown(parallelEvent);
		}else if(data.prcsType==5){//聚合节点
			var node = designer.drawAggregationNode(x,y,data);
			node.mousedown(aggregationEvent);
		}else if(data.prcsType==6){//子流程节点
			var node = designer.drawChildNode(x,y,data);
			node.mousedown(childEvent);
		}else if(data.prcsType==7){//柔性节点
			var node = designer.drawSoftNode(x,y,data);
			node.mousedown(simpleNodeEvent);
		}
		
		if(startNode){
			startNode[0].onmouseup = function(e){
				var e = window.event||e;
				if(e.button!=2 && e.button!=3){
					saveLayout(this);
				}
			};
		}
		if(endNode){
			endNode[0].onmouseup = function(e){
				var e = window.event||e;
				if(e.button!=2 && e.button!=3){
					saveLayout(this);
				}
			};
		}
		if(node){
			node[0].onmouseup = function(e){
				var e = window.event||e;
				if(e.button!=2 && e.button!=3){
					saveLayout(this);
				}
			};
		}
		
		
	}
	
	
	//绘制步骤
	for(var i=0;i<prcsList.length;i++){
		var data = prcsList[i];
		var sid = data.sid;
		var nexts = data.params.nextPrcs;
		var node = $("#"+sid);
		for(var j=0;j<nexts.length;j++){
			var cur = $("#"+nexts[j]);
			node.addNextNode(cur);
		}
	}
	
	designer.doLineTo();
}



/**
 * 添加并发节点
 */
function addParallelNode(node){
	var prcsName = window.prompt("请输入节点名称（为空则默认取名）","");
	if(prcsName==null || prcsName=="null"){
		return;
	}
	
	var x = node.position().left;
	var y = node.position().top+node.height()+30;
	var id = node.attr("id");
	var url = contextPath+"/flowProcess/createProcess.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,prcsType:4,parentId:id,x:x,y:y,name:prcsName});
	if(json.rtState){
		var parallelNode = designer.drawParallelNode(x,y,json.rtData);
		node.addNextNode(parallelNode);
		//designer.doLineTo();
		repaint();
		parallelNode.mousedown(parallelEvent);
		
	}else{
		alert(json.rtMsg);
	}
}

/**
* 添加子流程节点
*/
function addChildNode(node){
	var prcsName = window.prompt("请输入节点名称（为空则默认取名）","");
	if(prcsName==null || prcsName=="null"){
		return;
	}
	
	var x = node.position().left;
	var y = node.position().top+node.height()+30;
	var id = node.attr("id");
	var url = contextPath+"/flowProcess/createProcess.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,prcsType:6,parentId:id,x:x,y:y,name:prcsName});
	if(json.rtState){
		var childNode = designer.drawChildNode(x,y,json.rtData);
		node.addNextNode(childNode);
		//designer.doLineTo();
		repaint();
		childNode.mousedown(childNode);
		
	}else{
		alert(json.rtMsg);
	}
}

function parallelEvent(event){
	if(event.which!=3){
		return;
	}
	$(this).popupEmbedMenu([{text:'节点设置',title:'',func:top.openPropertyWindow},
	     	 	            {text:'添加普通节点',title:'',func:addSimpleNode},
	    		            {text:'删除节点',title:'',func:remove}]);
}

function startEvent(event){
	if(event.which!=3){
		return;
	}
	$(this).popupEmbedMenu([{text:'节点设置',title:'',func:top.openPropertyWindow},
	                      	{text:'添加普通节点',title:'',func:addSimpleNode},
	                      	{text:'添加并发节点',title:'',func:addParallelNode},
	                      	{text:'添加子流程节点',title:'',func:addChildNode},
	                        {text:'指向结束',title:'',func:toEnd}]);
}

/**
 * 添加普通节点
 */
function addSimpleNode(node){
	var prcsName = window.prompt("请输入节点名称（为空则默认取名）","");
	if(prcsName==null || prcsName=="null"){
		return;
	}
	var x = node.position().left;
	var y = node.position().top+node.height()+30;
	var id = node.attr("id");
	var url = contextPath+"/flowProcess/createProcess.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,prcsType:3,parentId:id,x:x,y:y,name:prcsName});
	if(json.rtState){
		var simpleNode = designer.drawSimpleNode(x,y,json.rtData);
		node.addNextNode(simpleNode);
		//designer.doLineTo();
		repaint();
		simpleNode.mousedown(simpleNodeEvent);
		
	}else{
		alert(json.rtMsg);
	}
}

function simpleNodeEvent(event){
	if(event.which!=3){
		return;
	}
	var menuItems = new Array();
	menuItems.push({text:'节点设置',title:'',func:top.openPropertyWindow});
	menuItems.push({text:'添加普通节点',title:'',func:addSimpleNode});

	//从该节点往上查找并发步骤及聚合步骤
	var patternNode = searchFirstPatternedNode($(this));
	histroy = new Array();
	if(patternNode){
		if(patternNode.attr('parallel')==''){//并发节点
			menuItems.push({text:'添加聚合节点',title:'',func:addAggregationNode});
			menuItems.push({text:'添加子流程节点',title:'',func:addChildNode});
		}else if(patternNode.attr('aggregation')==''){//聚合节点
			menuItems.push({text:'添加并发节点',title:'',func:addParallelNode});
			menuItems.push({text:'添加子流程节点',title:'',func:addChildNode});
			menuItems.push({text:'指向结束',title:'',func:toEnd});
		}
	}else{
		menuItems.push({text:'添加子流程节点',title:'',func:addChildNode});
		menuItems.push({text:'添加并发节点',title:'',func:addParallelNode});
		menuItems.push({text:'指向结束',title:'',func:toEnd});
	}
	menuItems.push({text:'删除节点',title:'',func:remove});
	$(this).popupEmbedMenu(menuItems);
}

/**
 * 搜索第一个匹配里程碑节点（主要搜索聚合节点和并发节点）
 */
var histroy = new Array();
var loop = 0;
function searchFirstPatternedNode(node){
	if(loop>100){
		loop = 0;
		return undefined;
	}
	for(var i=0;i<histroy.length;i++){
		if(histroy[i].attr("id")==node.attr("id")){
			histroy = new Array();
			return undefined;
		}
	}
	var prevNodes = node.getPrevNodes();
	if(!prevNodes || prevNodes==null){
		histroy = new Array();
		return undefined;
	}else if(prevNodes.length==0){
		histroy = new Array();
		return undefined;
	}
	//先判断第一层是否有并发
	for(var i=0;i<prevNodes.length;i++){
		var tmp = prevNodes[i];
		if(tmp.attr('aggregation')=='' || tmp.attr('parallel')==''){
			histroy = new Array();
			return tmp;
		}
	}
	
	for(var i=0;i<prevNodes.length;i++){
		var tmp = prevNodes[i];
		histroy.push(tmp);
		if(tmp.attr('aggregation')=='' || tmp.attr('parallel')==''){
			histroy = new Array();
			return tmp;
		}else{
			histroy = new Array();
			loop++;
			return searchFirstPatternedNode(tmp);
		}
	}
}

/**
 * 添加聚合节点
 */
function addAggregationNode(node){
	var prcsName = window.prompt("请输入节点名称（为空则默认取名）","");
	if(prcsName==null || prcsName=="null"){
		return;
	}
	
	var x = node.position().left;
	var y = node.position().top+node.height()+30;
	var id = node.attr("id");
	var url = contextPath+"/flowProcess/createProcess.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,prcsType:5,parentId:id,x:x,y:y,name:prcsName});
	if(json.rtState){
		var aggregationNode = designer.drawAggregationNode(x,y,json.rtData);
		node.addNextNode(aggregationNode);
		//designer.doLineTo();
		repaint();
		aggregationNode.mousedown(aggregationEvent);
		
	}else{
		alert(json.rtMsg);
	}
	
}

function aggregationEvent(event){
	if(event.which!=3){
		return;
	}
	$(this).popupEmbedMenu([{text:'节点设置',title:'',func:top.openPropertyWindow},
 	                      	{text:'添加普通节点',title:'',func:addSimpleNode},
	                      	{text:'添加并发节点',title:'',func:addParallelNode},
	                      	{text:'指向结束',title:'',func:toEnd},
	                      	{text:'删除节点',title:'',func:remove}]);
}

function childEvent(event){
	if(event.which!=3){
		return;
	}
	var menuItems = new Array();
	menuItems.push({text:'节点设置',title:'',func:top.openPropertyWindow});
	menuItems.push({text:'删除节点',title:'',func:remove});
	
	$(this).popupEmbedMenu(menuItems);
}

/**
 * 删除节点
 */
function remove(node){
	var id = node.attr("id");
	//判断该步骤 是否关联了流程任务  flow_run_prcs  如果关联了   则不允许删除
	var url1= contextPath+"/flowProcess/hasRelatedFlowRunPrcs.action";
	var json1=tools.requestJsonRs(url1,{sid:id});
	if(json1.rtState){
		var data=json1.rtData;
		var mess="";
		if(data==1){
			mess="该节点存被流程任务关联，强制删除会使当前节点上的任务无法正常办理和转交，确认要删除吗？";
		}else{
			mess="确定删除该节点吗？";
		}
		
		if(window.confirm(mess)){
			
			var url = contextPath+"/flowProcess/deleteProcess.action";
			var json = tools.requestJsonRs(url,{sid:id});
			if(json.rtState){
				window.location.reload();
			}else{
				alert(json.rtMsg);
			}
		}
		
	}
}

/**
 * 指向结束节点
 */
function toEnd(node){
	var id = node.attr("id");
	var url = contextPath+"/flowProcess/toEnd.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,prcsId:id});
	if(json.rtState){
		node.addNextNode(endNode);
		designer.doLineTo();
		repaint();
	}else{
		alert(json.rtMsg);
	}
}

//保存布局
function saveLayout(node){
// 	var nodes = $("#notepad [node='']");
// 	var arr = new Array();
// 	nodes.each(function(i,obj){
// 		var o = {};
// 		o.prcsSeqId = $(this).attr("id");
// 		o.x = $(this).position().left;
// 		o.y = $(this).position().top;
// 		arr.push(o);
// 	});
// 	var jsonModel = tools.parseJson2String(arr);
	var o = {};
	o.prcsSeqId = $(node).attr("id");
	o.x = $(node).position().left;
	o.y = $(node).position().top;
	var jsonModel = tools.parseJson2String(o);

	var url = contextPath+"/flowProcess/updateProcessLayout.action";
	tools.requestJsonRs(url,{jsonModel:jsonModel,flowId:flowId},true);
}
</script>
<style type="text/css" media=print>
.noprint{display : none }
</style>
</head>
<body style="margin:0px;background-image:url(res/bg.png)" oncontextmenu="return false">
<div class="explanation">
			<SPAN class=big3>流程名称：<span id="flowName"></span></SPAN> 
			<DIV>
			标识说明：<img src="<%=request.getContextPath() %>/common/images/workflow/start_node.png" />
			开始 &nbsp;&nbsp;<img src="<%=request.getContextPath() %>/common/images/workflow/end_node.png" />
			结束 &nbsp;&nbsp;<img src="<%=request.getContextPath() %>/common/images/workflow/simple_node.png" />
			办理&nbsp;&nbsp;<img src="<%=request.getContextPath() %>/common/images/workflow/soft_node.png" />
			办理(循环)&nbsp;&nbsp;<img src="<%=request.getContextPath() %>/common/images/workflow/parallel_node.png" />
			并发 &nbsp;&nbsp;<img src="<%=request.getContextPath() %>/common/images/workflow/aggregation_node.png" />
			聚合&nbsp;&nbsp;<img src="<%=request.getContextPath() %>/common/images/workflow/child_node.png" />
			子流程
			</DIV>
		</div>

<div id="layout">
<!--	<div class="noprint" layout="north" min=20 style="height:30px;background:#f0f0f0;padding-top:5px;padding-bottom:5px;padding-left:10px;border-bottom:1px solid #f0f0f0">-->
<!--		<input class="btn btn-default" value="保存布局" type="button" onclick="saveLayout()"/>-->
<!--		<input class="btn btn-default" value="刷新" type="button" onclick="window.location.reload()"/>-->
<!--		<input class="btn btn-default" value="打印" type="button" onclick="window.print();"/>-->
<!--	</div>-->
	<div layout="center" id="notepad" min=20 style="overflow:hidden">
	</div>
</div>
</body>
</html>