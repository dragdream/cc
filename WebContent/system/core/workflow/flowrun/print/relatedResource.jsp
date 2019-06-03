<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
   TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<head>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相关资源</title>
<style type="text/css">

ul{
		overflow: hidden;
		clear:both;
	}
li{
		width: 400px;
		height: 5px;
		line-height: 5px;
		margin-top: 10px;
		margin-left: 20px;
	}

</style>
<script type="text/javascript">
var runId=<%=runId %>;
var loginUserId=<%=loginUser.getUuid() %>;
//初始化
function doInit(){
	//渲染相关流程
	renderFlow();
    //渲染相关任务
    renderTask();
    //渲染相关客户
    renderCustomer();
    //渲染相关项目
    renderProject();
}

//渲染相关流程
function renderFlow(){
	var url=contextPath+"/TeeFlowRelatedResourceController/getRelatedResourceByRunId.action";
	var json=tools.requestJsonRs(url,{runId:runId,type:1});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
		    var render=[];
		    for(var i=0;i<data.length;i++){
		    	render.push("<li><span style=\"vertical-align:middle;display:inline-block; background:url(images/icon_bz.png) top left no-repeat; width:9px; height:9px;\"></span>&nbsp;<a href=\"#\" onclick=\"detail(1,'"+data[i].relatedId+"','"+data[i].relatedName+"')\">"+data[i].relatedName+"</a>");
		        
		        render.push("<li/>");
		    }
		    $("#relatedRunDiv").append(render.join(""));
		}else{
			messageMsg("暂无关联任何流程！", "mess1","info" );
		}
	}
}


//渲染相关任务
function renderTask(){
	var url=contextPath+"/TeeFlowRelatedResourceController/getRelatedResourceByRunId.action";
	var json=tools.requestJsonRs(url,{runId:runId,type:2});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
		    var render=[];
		    for(var i=0;i<data.length;i++){
		    	render.push("<li><span style=\"vertical-align:middle;display:inline-block; background:url(images/icon_bz.png) top left no-repeat; width:9px; height:9px;\"></span>&nbsp;<a href=\"#\" onclick=\"detail(2,'"+data[i].relatedId+"','"+data[i].relatedName+"')\">"+data[i].relatedName+"</a>");
		       
		        render.push("<li/>");
		    }
		    $("#relatedTaskDiv").append(render.join(""));
		}else{
			messageMsg("暂无关联任何任务！", "mess2","info" );
		}
	}
}

//渲染相关客户
function renderCustomer(){
	var url=contextPath+"/TeeFlowRelatedResourceController/getRelatedResourceByRunId.action";
	var json=tools.requestJsonRs(url,{runId:runId,type:3});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
		    var render=[];
		    for(var i=0;i<data.length;i++){
		    	render.push("<li><span style=\"vertical-align:middle;display:inline-block; background:url(images/icon_bz.png) top left no-repeat; width:9px; height:9px;\"></span>&nbsp;<a href=\"#\" onclick=\"detail(3,'"+data[i].relatedId+"','"+data[i].relatedName+"')\">"+data[i].relatedName+"</a>");
		        
		        render.push("<li/>");
		    }
		    $("#relatedCustomerDiv").append(render.join(""));
		}else{
			messageMsg("暂无关联任何客户！", "mess3","info" );
		}
	}
}



//渲染相关项目
function renderProject(){
	var url=contextPath+"/TeeFlowRelatedResourceController/getRelatedResourceByRunId.action";
	var json=tools.requestJsonRs(url,{runId:runId,type:4});
	if(json.rtState){
		var data=json.rtData;
		if(data.length>0){
		    var render=[];
		    for(var i=0;i<data.length;i++){
		    	render.push("<li><span style=\"vertical-align:middle;display:inline-block; background:url(images/icon_bz.png) top left no-repeat; width:9px; height:9px;\"></span>&nbsp;<a href=\"#\" onclick=\"detail(4,'"+data[i].relatedId+"','"+data[i].relatedName+"')\">"+data[i].relatedName+"</a>");
		       
		        render.push("<li/>");
		    }
		    $("#relatedProjectDiv").append(render.join(""));
		}else{
			messageMsg("暂无关联任何项目！", "mess4","info" );
		}
	}
}


//详情
function  detail(type,relatedId,relatedName){
	var url="";
	if(type==1){//相关流程
		url=contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+relatedId;
	}else if(type==2){//相关任务
		url=contextPath+"/system/subsys/cowork/detail.jsp?taskId="+relatedId;
	}else if(type==3){//相关客户
		url=contextPath+"/system/subsys/crm/core/customer/index.jsp?sid="+relatedId+"&customerName="+relatedName;
	}else if(type==4){//相关项目
		url=contextPath+"/system/subsys/project/projectdetail/index.jsp?uuid="+relatedId;
	}
	
	openFullWindow(url);
}
</script>


</head>


<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;">
<!-- 流程 -->
<div class="clearfix" style="padding-top: 5px;">
    <span style="vertical-align:middle;display:inline-block; background:url(images/icon_xglc.png) top left no-repeat; width:22px; height:28px;"></span> 
	<b style="font-size: 14px;vertical-align:middle;">相关流程</b>
 
	<span class="basic_border" style="padding-top: 2px;"></span>
	<div style="padding-top: 10px;" >
       <ul id="relatedRunDiv">
       
       </ul>
   </div>
   <div id="mess1" style="margin-top: 10px"></div>
</div>
<!-- 任务 -->
<!-- <div class="clearfix" style="padding-top: 5px;">
     <span style="vertical-align:middle;display:inline-block; background:url(images/icon_xgrw.png) top left no-repeat; width:22px; height:28px;"></span> 
	<b style="font-size: 14px;vertical-align:middle">相关任务</b>
	<span class="basic_border" style="padding-top: 2px;"></span>
	<div style="padding-top: 10px;">
       <ul  id="relatedTaskDiv"></ul>
   </div>
   <div id="mess2" style="margin-top: 10px"></div>
</div> -->

<!-- 客户-->
<!-- <div class="clearfix" style="padding-top: 5px;">
    <span style="vertical-align:middle;display:inline-block; background:url(images/icon_xgkh.png) top left no-repeat; width:22px; height:28px;"></span> 
	<b style="font-size: 14px;vertical-align:middle">相关客户</b>
	<span class="basic_border" style="padding-top: 2px;"></span>
	<div style="padding-top: 10px;">
       <ul  id="relatedCustomerDiv"></ul>
   </div>
   <div id="mess3" style="margin-top: 10px"></div>
</div> -->


<!-- 项目-->
<div class="clearfix" style="padding-top: 5px;">
<span style="vertical-align:middle;display:inline-block; background:url(images/icon_xgxm.png) top left no-repeat; width:22px; height:28px;"></span> 
	<b style="font-size: 14px;vertical-align:middle">相关项目</b>
	<span class="basic_border" style="padding-top: 2px;"></span>
	<div style="padding-top: 10px;" >
       <ul id="relatedProjectDiv"></ul>
   </div>
   <div id="mess4" style="margin-top: 10px"></div>
</div>
</body>


</html>