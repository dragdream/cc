<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header/header.jsp" %>
<script src="<%=contextPath %>/common/js/flowdesigner/res/jquery.js"></script>
<script src="<%=contextPath %>/common/js/flowdesigner/res/svg.js"></script>
<script src="<%=contextPath %>/common/js/flowdesigner/flowdesigner.js"></script>
<script src="<%=contextPath %>/common/js/popupmenu/popupmenu.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/js/flowdesigner/res/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/js/popupmenu/res/style.css" />
<link rel="stylesheet" type="text/css" href="<%=cssPath %>/style.css" />
<script type="text/javascript" src="<%=contextPath %>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/sys.js"></script>
<script>
var maxPrcsId=1;
var designer;
var startNode;
var endNode;
var flowId = <%=flowId%>;
window.onload=function(){
	$("#layout").layout({auto:true});
	var paper = Raphael(notepad);
	designer = new FlowDesigner(paper);

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
			startNode = designer.drawStart(x,y,sid);
			//设置开始节点菜单
			startNode.mousedown(startEvent);
		}else if(data.prcsType==2){//结束节点
			endNode = designer.drawEnd(x,y,sid);
		}else if(data.prcsType==3){//普通节点
			var node = designer.drawSimpleNode(x,y,sid,prcsId,prcsName);
			node.mousedown(simpleNodeEvent);
		}else if(data.prcsType==4){//并发节点
			var node = designer.drawParallelNode(x,y,sid,prcsId,prcsName);
			node.mousedown(parallelEvent);
		}else if(data.prcsType==5){//聚合节点
			var node = designer.drawAggregationNode(x,y,sid,prcsId,prcsName);
			node.mousedown(aggregationEvent);
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
	
	
	//全局移动监听器
	var movedListener = function(nodes,event){
		designer.repaint();//重绘
		designer.doLineTo();//根据连线规则绘制连线
	};
	
	//添加监听器
	designer.setMovedListener(movedListener);
	designer.repaint();//重绘
	designer.doLineTo();
}

/**
 * 打开属性窗口
 */
function openPropertyWindow(node){
	var id = node.attr("id");
	if(node.attr("start")=="" || node.attr("aggregation")=="" || node.attr("simple")==""){
		window.openWindow("setprops/index.jsp?prcsId="+id+"&flowId="+flowId,"节点设置",570,450);
	}
}

/**
 * 添加并发节点
 */
function addParallelNode(node){
	var x = node.position().left;
	var y = node.position().top+node.height()+30;
	var id = node.attr("id");
	var url = contextPath+"/flowProcess/createProcess.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,prcsType:4,parentId:id,x:x,y:y});
	if(json.rtState){
		var parallelNode = designer.drawParallelNode(x,y,json.rtData.sid,json.rtData.prcsId,json.rtData.prcsName);
		node.addNextNode(parallelNode);
		designer.doLineTo();

		parallelNode.mousedown(parallelEvent);
		
	}else{
		alert(json.rtMsg);
	}

}

function parallelEvent(event){
	if(event.which!=3){
		return;
	}
	$(this).popupEmbedMenu([{text:'基本信息',title:'',func:openPropertyWindow},
	     	 	            {text:'添加普通节点',title:'',func:addSimpleNode},
	    		            {text:'删除该节点',title:'',func:remove}]);
}

function startEvent(event){
	if(event.which!=3){
		return;
	}
	$(this).popupEmbedMenu([{text:'基本信息',title:'',func:openPropertyWindow},
	                      	{text:'添加普通节点',title:'',func:addSimpleNode},
	                      	{text:'添加并发节点',title:'',func:addParallelNode}]);
}

/**
 * 添加普通节点
 */
function addSimpleNode(node){
	var x = node.position().left;
	var y = node.position().top+node.height()+30;
	var id = node.attr("id");
	var url = contextPath+"/flowProcess/createProcess.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,prcsType:3,parentId:id,x:x,y:y});
	if(json.rtState){
		var simpleNode = designer.drawSimpleNode(x,y,json.rtData.sid,json.rtData.prcsId,json.rtData.prcsName);
		node.addNextNode(simpleNode);
		designer.doLineTo();

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
	menuItems.push({text:'基本信息',title:'',func:openPropertyWindow});
	menuItems.push({text:'添加普通节点',title:'',func:addSimpleNode});

	//从该节点往上查找并发步骤及聚合步骤
	var patternNode = searchFirstPatternedNode($(this));
	if(patternNode){
		if(patternNode.attr('parallel')==''){//并发节点
			menuItems.push({text:'添加聚合节点',title:'',func:addAggregationNode});
		}else if(patternNode.attr('aggregation')==''){//聚合节点
			menuItems.push({text:'添加并发节点',title:'',func:addParallelNode});
			menuItems.push({text:'指向结束',title:'',func:toEnd});
		}
	}else{
		menuItems.push({text:'添加并发节点',title:'',func:addParallelNode});
		menuItems.push({text:'指向结束',title:'',func:toEnd});
	}
	menuItems.push({text:'删除该节点',title:'',func:remove});
	
	$(this).popupEmbedMenu(menuItems);
}

/**
 * 搜索第一个匹配里程碑节点（主要搜索聚合节点和并发节点）
 */
function searchFirstPatternedNode(node){
	var prevNodes = node.getPrevNodes();
	if(!prevNodes || prevNodes==null){
		return undefined;
	}else if(prevNodes.length==0){
		return undefined;
	}
	for(var i=0;i<prevNodes.length;i++){
		var tmp = prevNodes[i];
		if(tmp.attr('aggregation')=='' || tmp.attr('parallel')==''){
			return tmp;
		}else{
			return searchFirstPatternedNode(tmp);
		}
	}
}

/**
 * 添加聚合节点
 */
function addAggregationNode(node){
	var x = node.position().left;
	var y = node.position().top+node.height()+30;
	var id = node.attr("id");
	var url = contextPath+"/flowProcess/createProcess.action";
	var json = tools.requestJsonRs(url,{flowId:flowId,prcsType:5,parentId:id,x:x,y:y});
	if(json.rtState){
		var aggregationNode = designer.drawAggregationNode(x,y,json.rtData.sid,json.rtData.prcsId,json.rtData.prcsName);
		node.addNextNode(aggregationNode);
		designer.doLineTo();

		aggregationNode.mousedown(aggregationEvent);
		
	}else{
		alert(json.rtMsg);
	}
	
}

function aggregationEvent(event){
	if(event.which!=3){
		return;
	}
	$(this).popupEmbedMenu([{text:'基本信息',title:'',func:openPropertyWindow},
 	                      	{text:'添加普通节点',title:'',func:addSimpleNode},
	                      	{text:'添加并发节点',title:'',func:addParallelNode},
	                      	{text:'指向结束',title:'',func:toEnd},
	                      	{text:'删除',title:'',func:remove}]);
}

/**
 * 删除节点
 */
function remove(node){
	var id = node.attr("id");
	var url = contextPath+"/flowProcess/deleteProcess.action";
	var json = tools.requestJsonRs(url,{sid:id});
	if(json.rtState){
		designer.removeNode(node);
		designer.repaint();
		designer.doLineTo();
	}else{
		alert(json.rtMsg);
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
		designer.repaint();
		designer.doLineTo();
	}else{
		alert(json.rtMsg);
	}
}

//保存布局
function saveLayout(){
	var nodes = $("#notepad [node='']");
	var arr = new Array();
	nodes.each(function(i,obj){
		var o = {};
		o.prcsSeqId = $(this).attr("id");
		o.x = $(this).position().left;
		o.y = $(this).position().top;
		arr.push(o);
	});
	var jsonModel = tools.parseJson2String(arr);

	var url = contextPath+"/flowProcess/updateProcessLayout.action";
	var json = tools.requestJsonRs(url,{jsonModel:jsonModel,flowId:flowId});
	if(!json.rtState){
		alert(json.rtMsg);
	}
}
</script>
<style type="text/css" media=print>
.noprint{display : none }
</style>
</head>
<body style="margin:0px;background-image:url(res/bg.png)" oncontextmenu="return false">
<div id="layout">
	<div class="noprint" layout="north" min=20 style="height:30px;background:#f0f0f0;padding-top:5px;padding-left:10px;border-bottom:1px solid gray">
		<input class="SmallButtonB" value="保存布局" type="button" onclick="saveLayout()"/>
		<input class="SmallButtonB" value="刷新" type="button" onclick="window.location.reload()"/>
		<input class="SmallButtonB" value="打印" type="button" onclick="window.print();"/>
	</div>
	<div layout="center" id="notepad" min=20 style="overflow:hidden">
	</div>
</div>
</body>
</html>