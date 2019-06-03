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
		        //判断是不是当前登陆人创建的相关资源
		        if(data[i].createUserId==loginUserId){
		        	render.push("&nbsp;&nbsp;<img src=\"images/icon_del.png\" onclick=\"del(1,"+data[i].sid+");\">");
		        }
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
		        //判断是不是当前登陆人创建的相关资源
		        if(data[i].createUserId==loginUserId){
		        	render.push("&nbsp;&nbsp;<img src=\"images/icon_del.png\" onclick=\"del(2,"+data[i].sid+");\">");
		        }
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
		        //判断是不是当前登陆人创建的相关资源
		        if(data[i].createUserId==loginUserId){
		        	render.push("&nbsp;&nbsp;<img src=\"images/icon_del.png\" onclick=\"del(3,"+data[i].sid+");\">");
		        }
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
		        //判断是不是当前登陆人创建的相关资源
		        if(data[i].createUserId==loginUserId){
		        	render.push("&nbsp;&nbsp;<img src=\"images/icon_del.png\" onclick=\"del(4,"+data[i].sid+");\">");
		        }
		        render.push("<li/>");
		    }
		    $("#relatedProjectDiv").append(render.join(""));
		}else{
			messageMsg("暂无关联任何项目！", "mess4","info" );
		}
	}
}


//添加
function add(type){//type==1 相关流程、  type==2 相关任务、 type==3 相关客户、 type==4 相关项目
	var url="";
	var title="";
	if(type==1){
	  title="添加相关流程";	
	  url=contextPath+"/system/core/workflow/flowrun/prcs/relatedFlowRun.jsp?runId="+runId;
	}else if(type==2){
		title="添加相关任务";
		url=contextPath+"/system/core/workflow/flowrun/prcs/relatedTask.jsp?runId="+runId;
	}else if(type==3){
		title="添加相关客户";
		url=contextPath+"/system/core/workflow/flowrun/prcs/relatedCustomer.jsp?runId="+runId;
	}else if(type==4){
		title="添加相关项目";	
		url=contextPath+"/system/core/workflow/flowrun/prcs/relatedProject.jsp?runId="+runId;
	}
 	bsWindow(url ,title,{width:"800",height:"350",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.doSave();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("添加成功！",function(){	
		    		window.location.reload();
		    		return true;
		    	});
		    }else{
		    	$.MsgBox.Alert_auto("添加失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}




//删除
function del(type,sid){
	var mess="";
	if(type==1){
		mess="是否确认删除该相关流程?";
	}else if(type==2){
		mess="是否确认删除该相关任务?";
	}else if(type==3){
		mess="是否确认删除该相关客户?";
	}else if(type==4){
		mess="是否确认删除该相关项目?";
	}

	 $.MsgBox.Confirm ("提示", mess, function(){
		 var url = contextPath + "/TeeFlowRelatedResourceController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！",function(){
					window.location.reload();
				});
			} 
	  });
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
  
	<img class="fr" src="<%=contextPath %>/system/core/workflow/flowrun/prcs/images/icon_add.png" onclick="add(1);" style="padding-right: 10px">
	<span class="basic_border" style="padding-top: 2px;"></span>
	<div style="padding-top: 10px;" >
       <ul id="relatedRunDiv">
       
       </ul>
   </div>
   <div id="mess1" style="margin-top: 10px"></div>
</div>
<!-- 任务 -->
<%-- <div class="clearfix" style="padding-top: 5px;">
     <span style="vertical-align:middle;display:inline-block; background:url(images/icon_xgrw.png) top left no-repeat; width:22px; height:28px;"></span> 
	<b style="font-size: 14px;vertical-align:middle">相关任务</b>
	<img class="fr" src="<%=contextPath %>/system/core/workflow/flowrun/prcs/images/icon_add.png" onclick="add(2);" style="padding-right: 10px">
	<span class="basic_border" style="padding-top: 2px;"></span>
	<div style="padding-top: 10px;">
       <ul  id="relatedTaskDiv"></ul>
   </div>
   <div id="mess2" style="margin-top: 10px"></div>
</div> --%>

<!-- 客户-->
<%-- <div class="clearfix" style="padding-top: 5px;">
    <span style="vertical-align:middle;display:inline-block; background:url(images/icon_xgkh.png) top left no-repeat; width:22px; height:28px;"></span> 
	<b style="font-size: 14px;vertical-align:middle">相关客户</b>
	<img class="fr" src="<%=contextPath %>/system/core/workflow/flowrun/prcs/images/icon_add.png" onclick="add(3);" style="padding-right: 10px">
	<span class="basic_border" style="padding-top: 2px;"></span>
	<div style="padding-top: 10px;">
       <ul  id="relatedCustomerDiv"></ul>
   </div>
   <div id="mess3" style="margin-top: 10px"></div>
</div> --%>


<!-- 项目-->
<div class="clearfix" style="padding-top: 5px;">
<span style="vertical-align:middle;display:inline-block; background:url(images/icon_xgxm.png) top left no-repeat; width:22px; height:28px;"></span> 
	<b style="font-size: 14px;vertical-align:middle">相关项目</b>
	<img class="fr" src="<%=contextPath %>/system/core/workflow/flowrun/prcs/images/icon_add.png" onclick="add(4);" style="padding-right: 10px">
	<span class="basic_border" style="padding-top: 2px;"></span>
	<div style="padding-top: 10px;" >
       <ul id="relatedProjectDiv"></ul>
   </div>
   <div id="mess4" style="margin-top: 10px"></div>
</div>
</body>


</html>