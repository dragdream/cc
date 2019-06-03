<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	String thread_local_archives = TeeStringUtil.getString(request.getParameter("thread_local_archives"));
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
<style>
table{
border-collapse:collapse;
}
.viewlist_td1{
width:110px;
border:1px solid #e2e2e2;
}
.viewlist_td2{
width:110px;
border:1px solid #e2e2e2;
}
.viewlist_td3{
border:1px solid #e2e2e2;
}
.viewlist_td4{
border:1px solid #e2e2e2;
}
#tbody td{
border:1px solid #e2e2e2;
}
</style>
<script>
var runId = "<%=runId%>";
var thread_local_archives = "<%=thread_local_archives%>";
var systemImagePath = "<%=systemImagePath%>";
var tbody;

function doInit(){
	var url = contextPath+"/flowInfoChar/getFlowRunViewGraphicsData.action";
	var json = tools.requestJsonRs(url,{runId:runId,thread_local_archives:thread_local_archives});
	tbody = $("#tbody");
	if(json.rtState){
		$("#runName").html(json.rtData.runName);
		$("#runId").html(json.rtData.runId);
		var type = json.rtData.type;
		
		//渲染并绘制步骤信息
		var prcsList = json.rtData.prcsList;
		renderFlow(prcsList);

		$("[title]").tooltips();
	}else{
		messageMsg(json.rtMsg,"container","error");
		return;
	}
}

function groupBy(prcsList){
	var group = new Array;
	var maxPrcsId = prcsList[prcsList.length-1].prcsId;
	for(var i=1;i<=maxPrcsId;i++){
		var set = {};
		for(var j=0;j<prcsList.length;j++){
			var prcsId = prcsList[j].prcsId;
			var flowPrcsId = prcsList[j].flowPrcsId;
			if(prcsId!=i){
				continue;
			}
			var id = prcsId+""+flowPrcsId;
			var arr = set[id];
			if(!arr){
				arr = new Array();
			}
			arr.push(prcsList[j]);
			set[id] = arr;
		}
		group.push(set);
	}
	return group;
}

function renderFlow(prcsList){
	tbody = $("#tbody");
	var group = groupBy(prcsList);
	var render = "";
	for(var i=0;i<group.length;i++){
		render+="<tr>";
		var set = group[i];
		var rows = 0;
		for(var key in set){
			rows++;
		}
		render+="<td rowspan="+rows+">第"+(i+1)+"步</td>";
		for(var key in set){
			var arr = set[key];
			if(arr[0].flowPrcsId!=0){
				render+="<td>步骤："+arr[0].prcsName+"</td>";
			}
			render+="<td style=\"text-align:left\">";
			var title = "";
			for(var j=0;j<arr.length;j++){
				var prcsInfo = arr[j];
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
				title+="<br/><br/>";
			}
			render+=title;
			render+="</td>";
			if(rows!=1){
				render+="</tr>";
				render+="<tr>";
			}
		}
		render+="</tr>";
	}
	tbody.html(render);
}

</script>
<style type="text/css">
td{
padding:5px;
}
</style>
</head>
<body onload="doInit()" style="margin:10px">
<center>
	<table style="width:90%;font-size:12px">
		<thead>
			<tr><td colspan="3" style="text-align:center;font-weight:bold;height:30px;border:1px solid #e2e2e2;background:#fafafa">流程开始</td></tr>
		</thead>
		<tbody id="tbody">
		</tbody>
		<tfoot>
			<tr><td colspan="3" style="text-align:center;font-weight:bold;height:30px;border:1px solid #e2e2e2;background:#fafafa">流程结束</td></tr>
		</tfoot>
	</table>
</center>
</body>
</html>