<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>查询结果</title>
<script type="text/javascript">
var word = "";

function setWord(_word){
	word = _word;
}

function doSearchUser(){
	tools.requestJsonRs(contextPath+"/quickSearch/quickSearchUser.action",{word:word},true,function(json){
		var list = json.rtData;
		var render = [];
		if(list){
			for(var i=0;i<list.length;i++){
				render.push("<li onclick=\"userFunc('"+list[i].uuid+"','"+list[i].userId+"','"+list[i].userName+"')\"><img src='blue.png'>&nbsp;&nbsp;"+list[i].userName+"&nbsp;&nbsp;("+list[i].deptName+")</li>");
			}
		}
		$("#user_data").html(render.join(""));		
	});
	
	tools.requestJsonRs(contextPath+"/quickSearch/quickSearchCount.action",{word:word},true,function(countData){
		$("#task").html(countData.task);
		$("#schedule").html(countData.schedule);
		$("#doc").html(countData.doc);
		$("#customer").html(countData.customer);
		$("#workflow").html(countData.workflow);
		$("#user").html(countData.user);
	});
	
	$("#work_data").html("<center><a href=\"javascript:void(0)\" onclick='doSearchWork()'>点击搜索工作名称和流水号</a></center>");
	$("#task_data").html("<center><a href=\"javascript:void(0)\" onclick='doSearchTask()'>点击搜索任务</a></center>");
	$("#schedule_data").html("<center><a href=\"javascript:void(0)\" onclick='doSearchSchedule()'>点击搜索计划</a></center>");
	$("#doc_data").html("<center><a href=\"javascript:void(0)\" onclick='doSearchDoc()'>点击搜索文档</a></center>");
	$("#customer_data").html("<center><a href=\"javascript:void(0)\" onclick='doSearchCustomer()'>点击搜索客户</a></center>");
}

function doSearchWork(){
	tools.requestJsonRs(contextPath+"/quickSearch/quickSearchWork.action",{word:word},true,function(json){
		var list = json.rtData;
		var render = [];
		if(list){
			for(var i=0;i<list.length;i++){
				render.push("<li onclick=\"workFunc('"+list[i].runId+"')\" title='"+list[i].runName+"'><img src='orange.png'>&nbsp;&nbsp;("+list[i].runId+")"+list[i].runName+"</li>");
			}
		}
		$("#work_data").html(render.join(""));		
	});
}

function doSearchTask(){
	tools.requestJsonRs(contextPath+"/quickSearch/quickSearchList.action",{word:word,type:1,rows:100000},true,function(json){
		var list = json.rows;
		var render = [];
		if(list){
			for(var i=0;i<list.length;i++){
				render.push("<li onclick=\"taskFunc('"+list[i].sid+"')\" title='"+list[i].taskTitle+"'><img src='green.png'>&nbsp;&nbsp;"+list[i].taskTitle+"</li>");
			}
		}
		$("#task_data").html(render.join(""));		
	});
}

function doSearchSchedule(){
	tools.requestJsonRs(contextPath+"/quickSearch/quickSearchList.action",{word:word,type:2,rows:100000},true,function(json){
		var list = json.rows;
		var render = [];
		if(list){
			for(var i=0;i<list.length;i++){
				render.push("<li onclick=\"scheduleFunc('"+list[i].uuid+"')\" title='"+list[i].title+"'><img src='pink.png'>&nbsp;&nbsp;"+list[i].title+"</li>");
			}
		}
		$("#schedule_data").html(render.join(""));		
	});
}

function doSearchDoc(){
	tools.requestJsonRs(contextPath+"/quickSearch/quickSearchList.action",{word:word,type:3,rows:100000},true,function(json){
		var list = json.rows;
		var render = [];
		if(list){
			for(var i=0;i<list.length;i++){
				render.push("<li fileName=\""+list[i].fileName+"\" ext=\""+list[i].ext+"\" attachId=\""+list[i].attachId+"\" title='"+list[i].fileName+"'><img src='red.png'>&nbsp;&nbsp;"+list[i].fileName+"</li>");
			}
		}
		$("#doc_data").html(render.join(""));
		
		$("li[fileName]").each(function(i,obj){
			var att = {priv:1+2,fileName:obj.getAttribute("fileName"),ext:obj.getAttribute("ext"),sid:obj.getAttribute("attachId")};
			var attach = tools.getAttachElement(att,{});
			$(obj).html("").append(attach);
		});
	});
}

function doSearchCustomer(){
	tools.requestJsonRs(contextPath+"/quickSearch/quickSearchList.action",{word:word,type:4,rows:100000},true,function(json){
		var list = json.rows;
		var render = [];
		if(list){
			for(var i=0;i<list.length;i++){
				render.push("<li onclick=\"customerFunc('"+list[i].sid+"')\" title='"+list[i].customerName+"'><img src='gblue.png'>&nbsp;&nbsp;"+list[i].customerName+"</li>");
			}
		}
		$("#customer_data").html(render.join(""));		
	});
}


function userFunc(uuid,userId,userName){
	if(window.external.IM_OA){
		window.external.IM_OpenDialog(userId,userName);
	}else{
		top.msgFrame.createDlg(userId);
	}
}

function workFunc(runId){
	openFullWindow(contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=1");
}

function taskFunc(sid){
	openFullWindow(contextPath+"/system/subsys/cowork/detail.jsp?taskId="+sid);
}

function scheduleFunc(uuid){
	openFullWindow(contextPath+"/system/subsys/schedule/manage/detail.jsp?scheduleId="+uuid);
}

function customerFunc(sid){
	openFullWindow(contextPath+"/system/subsys/crm/core/customInfo/detail.jsp?sid="+sid);
}
</script>
<style>
table{
font-family:微软雅黑;
font-size:12px;
border-collapse:collapse;
border-top:1px solid #e3e3e3;
border-bottom:1px solid #e3e3e3;
width:100%;
padding:0px;
margin:0px;
}
.user_title{
color:#1084d1;
}
.work_title{
color:#f09723;
}
.task_title{
color:#30ba26;
}
.schedule_title{
color:#c139b3;
}
.doc_title{
color:#d83f3f;
}
.customer_title{
color:#44a4bf;
}
ul{
padding:0px;
margin:0px;
margin-top:5px;
margin-bottom:5px;
}
li{
padding:7px;
color:black;
cursor:pointer;
font-size:12px;
padding-left:15px;
overflow:hidden; 
text-overflow:ellipsis; 
white-space:nowrap;
}
li:hover{
background:#b6b2b1;
color:white;
}
a{
font-size:12px;
}
</style>
</head>
<body style="padding:0px;margin:0px;" onload="doSearchUser()">
<table class="user_title">
	<tr>
		<td style="width:20px;height:20px;background:#1084d1;"></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<b>用户</b>&nbsp;(<span id="user"></span>)</td>
	</tr>
</table>
<ul id="user_data">
	
</ul>

<table class="work_title">
	<tr>
		<td style="width:20px;height:20px;background:#f09723;"></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<b>工作流</b>&nbsp;(<span id="workflow"></span>)</td>
	</tr>
</table>
<ul id="work_data">
	<center><a href="javascript:void(0)" onclick='doSearchWork()'>点击搜索工作名称和流水号</a></center>
</ul>

<table class="task_title">
	<tr>
		<td style="width:20px;height:20px;background:#30ba26;"></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<b>任务</b>&nbsp;(<span id="task"></span>)</td>
	</tr>
</table>
<ul id="task_data">
	<center><a href="javascript:void(0)" onclick='doSearchTask()'>点击搜索任务</a></center>
</ul>

<table class="schedule_title">
	<tr>
		<td style="width:20px;height:20px;background:#c139b3;"></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<b>计划</b>&nbsp;(<span id="schedule"></span>)</td>
	</tr>
</table>
<ul id="schedule_data">
	<center><a href="javascript:void(0)" onclick='doSearchSchedule()'>点击搜索计划</a></center>
</ul>

<table class="doc_title">
	<tr>
		<td style="width:20px;height:20px;background:#d83f3f;"></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<b>文档</b>&nbsp;(<span id=doc></span>)</td>
	</tr>
</table>
<ul id="doc_data">
	<center><a href="javascript:void(0)" onclick='doSearchDoc()'>点击搜索文档</a></center>
</ul>

<table class="customer_title">
	<tr>
		<td style="width:20px;height:20px;background:#44a4bf;"></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<b>客户</b>&nbsp;(<span id="customer"></span>)</td>
	</tr>
</table>
<ul id="customer_data">
	<center><a href="javascript:void(0)" onclick='doSearchCustomer()'>点击搜索客户</a></center>
</ul>

</body>
</html>