<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String taskId = request.getParameter("taskId");
	String fromTask = request.getParameter("fromTask");
%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<script type="text/javascript" src="script.js"></script>
<style>

</style>
<script>
var taskId = <%=taskId%>;
var fromTask = <%=fromTask%>;
var userId = <%=loginPerson.getUuid()%>;
var datagrid ;
function doInit(){
	
	loadInfo();
	loadEvents();
	loadDocs();
	loadChildTask();
	
	
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"valuesHolder2",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"coWork"}//后台传入值，model为模块标志
		});
}

function renderOper(priv){
	for(var i=0;i<=8;i++){
		var tmp = Math.pow(2,i);
		if((priv&tmp)==tmp){
			$("#b"+tmp).show();
		}
	}
}

function goback(){
	if(fromTask!=null){
		window.location = contextPath+"/system/subsys/cowork/detail.jsp?taskId="+fromTask;
	}else{
		window.location = contextPath+"/system/subsys/cowork/list.jsp";
	}
	
}

//提交文档
function uploadDoc(){
	var json = tools.requestJsonRs(contextPath+"/coWork/addDoc.action",{taskId:taskId,remark:$("#attachRemark").val(),attachIds:$("#valuesHolder2").val()});
	if(json.rtState){
		$.MsgBox.Alert_auto("保存成功");
		window.location.reload();
	}
}

function loadInfo(){
// 	$.jBox.tip("正在加载…","loading");
// 	$.MsgBox.Alert_auto("正在加载…");
	tools.requestJsonRs(contextPath+"/coWork/getTaskInfo.action?taskId="+taskId,{},true,function(json){
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			//渲染进度条
			$("#progress").progressBar(json.rtData.progress, {showText: true});
			
			if(json.rtData.chargerId!=userId){//如果当前登陆人不是负责人，则不允许显示
				$("#childTaskDiv").hide();
			}
			if(json.rtData.createUserId==userId){//如果当前登陆人是创建人，则显示子任务
				$("#childTaskDiv").show();
			}
			if(json.rtData.status!=4){
				$("#operDiv1").hide();
				$("#operDiv2").hide();
			}
			
			$("#delayDiv").find("[fid=delayTime]:first").attr("onFocus","WdatePicker({minDate:'"+json.rtData.endTimeDesc+"',dateFmt:'yyyy-MM-dd HH:mm:ss'})");
			
			//渲染当前状态
			var statusDesc;
			var statusColor = "";
			var status = json.rtData.status;
			switch(status)
			{
			case 0:
				statusDesc = "等待接收";
				statusColor = "red";
				break;
			case 1:
				statusDesc = "等待审批";
				statusColor = "red";
				break;
			case 2:
				statusDesc = "审批不通过";
				statusColor = "red";
				break;
			case 3:
				statusDesc = "拒绝接收";
				statusColor = "red";
				break;
			case 4:
				statusDesc = "进行中";
				statusColor = "blue";
				break;
			case 5:
				statusDesc = "提交审核";
				statusColor = "red";
				break;
			case 6:
				statusDesc = "审核不通过";
				statusColor = "red";
				break;
			case 7:
				statusDesc = "任务撤销";
				statusColor = "red";
				break;
			case 8:
				statusDesc = "已完成";
				statusColor = "green";
				break;
			case 9:
				statusDesc = "已失败";
				statusColor = "red";
				break;
			}
			$("#status").html("<span style='color:"+statusColor+"'>"+statusDesc+"</span>");
			
			//权限运算
			var priv = 0;
			rowData = json.rtData;
			if(rowData.status==0){//等待接收
				if(rowData.createUserId==userId){//布置人操作
					priv = 1+2+4+8+64;
					if(rowData.chargerId==userId){
						$("#alert1").show();
					}
				}else if(rowData.chargerId==userId){//负责人操作
					priv = 8;
					$("#alert1").show();
				}else{
					priv = 8;
				}
			}else if(rowData.status==1){//等待审批
				if(rowData.createUserId==userId){//布置人操作
					priv = 1+2+4+8+64;
					if(rowData.auditorId==userId){
						$("#alert2").show();
					}
				}else if(rowData.auditorId==userId){
					priv = 8;//查看
					$("#alert2").show();
				}else{
					priv = 8;//查看
				}
			}else if(rowData.status==2){//审批不通过
				if(rowData.createUserId==userId){
					priv = 1+2+4+8+64;
				}else{
					priv = 8;//查看
				}
			}else if(rowData.status==3){//拒绝接收
				if(rowData.createUserId==userId){
					priv = 1+2+4+8+64;
				}else{
					priv = 8;//查看
				}
			}else if(rowData.status==4){//进行中
				if(rowData.createUserId==userId){//布置人操作
					priv = 4+8+16+32+64+128+256;
				}else if(rowData.chargerId==userId){
					priv = 8+16+32+64+128+256;
				}else{
					priv = 8;//查看
				}
			}else if(rowData.status==5){//等待审核
				if(rowData.createUserId==userId){//布置人操作
					priv = 4+8+16+32+64;
					$("#alert3").show();
				}else{
					priv = 8;//查看
				}
			}else if(rowData.status==6){//审核不通过
				if(rowData.createUserId==userId){//布置人操作
					priv = 4+8+16+32+64+128+256;
				}else if(rowData.chargerId==userId){//责任人操作
					priv = 8+16+32+64+128+256;
				}else{
					priv = 8;//查看
				}
			}else if(rowData.status==7){//任务撤销
				if(rowData.createUserId==userId){//布置人操作
					priv = 2+8;
				}else{
					priv = 8;//查看
				}
			}else if(rowData.status==8){//已完成
				if(rowData.createUserId==userId){//布置人操作
					priv = 2+8;
				}else{
					priv = 8;//查看
				}
			}else if(rowData.status==9){//失败
				if(rowData.createUserId==userId){//布置人操作
					priv = 2+8;
				}else{
					priv = 8;//查看
				}
			}
			//渲染权限
			renderOper(priv);
			
// 			$.jBox.tip("加载完毕","success");
			$.MsgBox.Alert_auto("加载完毕");
		}
	});
}

function loadEvents(){
	//获取任务事件数据
	tools.requestJsonRs(contextPath+"/coWork/listEvents.action?taskId="+taskId,{},true,function(json){
		var list = json.rtData;
		$("#eventCount").html("&nbsp;["+list.length+"]");
		var arr = [];
		var template = "<tr>";
		template+="<td>{1}</td>";
		template+="<td>{2}</td>";
		template+="<td>{3}</td>";
		template+="</tr>";
		var color = "";
		for(var i=0;i<list.length;i++){
			var item = list[i];
			if(item.type==1 || item.type==2 || item.type==10){
				color = "green";
			}else if(item.type==3 || item.type==4 || item.type==5){
				color = "orange";
			}else if(item.type==6 || item.type==7 || item.type==9){
				color = "blue";
			}else {
				color = "red";
			}
			arr.push(template.replace("{1}","<span style='padding:3px;background:"+color+";color:#fff'>"+item.typeDesc+"</span>")
					.replace("{2}","<span>"+item.title+"</span><br/><span style='color:#bdbdbd'>"+(item.content!=null?item.content:"")+"</span>")
					.replace("{3}","<span>"+item.createUserName+"</span><br/><span style='color:#bdbdbd'>"+item.createTime+"</span>"));
		}
		
		$("#eventTbody").html(arr.join(""));
	});
}

function deleteDoc(docId){
	$.MsgBox.Confirm("提示","是否要删除该文档？",function(){
// 		if(v=="ok"){
			var json = tools.requestJsonRs(contextPath+"/coWork/deleteDoc.action",{sid:docId});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功");
				loadDocs();
			}
// 		}
	});
}

function loadDocs(){
	tools.requestJsonRs(contextPath+"/coWork/listDocs.action?taskId="+taskId,{},true,function(json){
		var list = json.rtData;
		var template = "";
		$("#docCount").html("&nbsp;["+list.length+"]");
		for(var i=0;i<list.length;i++){
			var item = list[i];
			template+="<tr>";
			template+="<td>";
			var attaches = item.attaches;
			for(var j=0;j<attaches.length;j++){
				var file = attaches[j];
				template+="<div clazz='fileItem' sid='"+file.sid+"' fileName='"+file.fileName+"' fileSize='"+file.fileSize+"' ext='"+file.ext+"' userId='"+file.userId+"'></div>";
			}
			template+="</td>";
			template+="<td>"+item.remark+"</td>";
			template+="<td>"+item.createUserName+"<br/>"+item.createTimeDesc+"</td>";
			if(item.createUserId==userId){
				template+="<td>";
				template+="<a href='javascript:void(0)' onclick='deleteDoc("+item.sid+")'>删除</a>";
				template+="</td>";
			}else{
				template+="<td></td>";
			}
			template+="</tr>";
		}
		$("#docTbody").html(template);
		
		$("div[clazz=fileItem]").each(function(i,obj){
			var fileJson = {sid:$(obj).attr("sid"),fileName:$(obj).attr("fileName"),fileSizeDesc:$(obj).attr("fileSize"),ext:$(obj).attr("ext")};
			fileJson.priv = 1+2;
			var fileItem = tools.getAttachElement(fileJson,{});
			$(obj).append(fileItem);
		});
		
	});
}

/**
 * 加载子任务
 */
function loadChildTask(){
	tools.requestJsonRs(contextPath + '/coWork/datagrid.action',{page:1,rows:1000,parentTaskId:taskId},true,function(json){
		var rows = json.rows;
		var html = [];
		for(var i=0;i<rows.length;i++){
			var rowData = rows[i];
			html.push("<tr>");
			html.push("<td>"+renderTitle(rowData)+"</td>");
			html.push("<td>"+renderStatus(rowData)+"</td>");
			html.push("<td>"+rowData.chargerName+"</td>");
			html.push("<td>"+rowData.createUserName+"</td>");
			html.push("<td>"+rowData.startTimeDesc+"</td>");
			html.push("<td>"+rowData.endTimeDesc+"</td>");
			html.push("<td><a href='javascript:void(0)' onclick='lookup("+rowData.sid+")'>查看</a></td>");
			html.push("</tr>");
		}
		$("#childTaskTbody").html(html.join(""));
		$(".progress_bar").each(function(i,obj){
			$(obj).progressBar(obj.getAttribute("value"), {showText: true});
		});
		$("#childCount").html("&nbsp;["+rows.length+"]");
	});
	
}

function lookup(sid){
	window.location = contextPath+"/system/subsys/cowork/detail.jsp?taskId="+sid+"&fromTask="+taskId;
}

function renderTitle(rowData){
	var render = "";
	render+="<b>"+rowData.taskTitle+"</b>"
	if(rowData.status==1){//等待审批
		if(rowData.auditorId==userId){//如果当前登陆人是审批人员
			render+="<br/><span style='color:red'>你是任务审批人，需要审批</span>";
		}else{
			render+="<br/><span style='color:red'>等待审批</span>";
		}
	}else if(rowData.status==0){//等待接收
		if(rowData.charger==userId){//
			render+="<br/><span style='color:red'>你是任务负责人，需要接收</span>";
		}
	}else if(rowData.status==2){//审批不通过
		if(rowData.createUserId==userId){//
			render+="<br/><span style='color:red'>你是任务布置人，请修改</span>";
		}
	}else if(rowData.status==3){//拒绝接收
		if(rowData.createUserId==userId){//
			render+="<br/><span style='color:red'>你是任务布置人，请修改</span>";
		}
	}else if(rowData.status==4){//进行中
	}else if(rowData.status==5){//等待审核
		if(rowData.createUserId==userId){//
			render+="<br/><span style='color:red'>你是任务布置人，需要审核</span>";
		}
	}
	return render;
}

function renderStatus(rowData){
	var status = rowData.status;
	var render = "";
	switch(status)
	{
	case 0:
		render+="等待接收";
		break;
	case 1:
		render+="等待审批";
		break;
	case 2:
		render+="审批不通过";
		break;
	case 3:
		render+="拒绝接收";
		break;
	case 4:
		render+="进行中";
		break;
	case 5:
		render+="提交审核";
		break;
	case 6:
		render+="审核不通过";
		break;
	case 7:
		render+="任务撤销";
		break;
	case 8:
		render+="已完成";
		break;
	case 9:
		render+="任务失败";
		break;
	}
	
	render+="<div class='progress_bar' value='"+rowData.progress+"'></div>";
	return render;
}

function addChildTask(){
	bsWindow(contextPath+"/system/subsys/cowork/addChildTask.jsp?parentTaskId="+taskId,"创建子任务",{width:"800",height:"400",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		var contentWindow = h[0].contentWindow;
		if(v == "确定"){
		if(contentWindow.commit()){
			d.remove();
			loadChildTask();
			return true;
		}
		}else if(v=="关闭"){
			return true;
		}
	}});
	
}

function edit(taskId){
	window.location = contextPath+"/system/subsys/cowork/editTask.jsp?taskId="+taskId+"&from=detail";
}
</script>
<style>
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	font-weight:bold;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
/*     border: 1px solid #f2f2f2; */
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	/*font-weight:bold;*/
}
table tr:first-child{
	/* background-color: #f8f8f8;  */
}
.modal-test {
		width: 600px;
		position: absolute;
		display: none;
		z-index: 999;
		}

		.modal-test .modal-header {
		width: 100%;
		height: 50px;
		background-color: #8ab0e6;
		}

		.modal-test .modal-header .modal-title {
		color: #fff;
		font-size: 16px;
		line-height: 50px;
		margin-left: 20px;
		float: left;
		}

		.modal-test .modal-header .modal-win-close {
		color: #fff;
		font-size: 16px;
		line-height: 50px;
		margin-right: 20px;
		float: right;
		cursor: pointer;
		}

		.modal-test .modal-body {
		width: 100%;
		height: 250px;
		background-color: #f4f4f4;
		}

		.modal-test .modal-body ul {
		overflow: hidden;
		clear: both;
		}

		.modal-test .modal-body ul li {
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
		}

		.modal-test .modal-body ul li span {
		display: inline-block;
		float: left;
		vertical-align: middle;
		}

		.modal-test .modal-body ul li input {
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
		}

		.modal-test .modal-footer {
		width: 100%;
		height: 60px;
		background-color: #f4f4f4;
		}

		.modal-test .modal-footer input {
		margin-top: 12px;
		float: right;
		margin-right: 20px;
		}
		.btn-win-white fl
		{
		    margin-top:5px;
		}
</style>
</head>
<body onload="doInit();" style="overflow-y:auto;overflow-x:hidden;padding-top:25px;">
	<div id="time_info" style="width:96%;margin-left:25px;">
	<!-- <div class="moduleHeader">
		<div>
			<b><i class="glyphicon glyphicon-list-alt"></i>&nbsp;[任务]&nbsp;<span id="taskTitle"></span></b>
		</div>
	</div> -->
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;">
			<td><i class="glyphicon glyphicon-list-alt"></i>&nbsp;[任务]&nbsp;<span id="taskTitle"></span>
			</td> 
		</tr>
	</table>
	
	<!-- 等待接收提示区 -->
	<%-- <div id="alert1" class="alert alert-warning alert-dismissable" style="display:none">
	  <strong>您是任务责任人，需要接收该任务！</strong>
	  <br/>
	  <button class="btn-win-white fl" onclick="receive(<%=taskId%>)">接收任务</button>
	  <button class="btn-win-white fl" onclick="noReceive(<%=taskId%>)">拒绝接收</button>
	</div> --%>
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small" id="alert1" style="display:none">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;">
			<td><span>您是任务责任人，需要接收该任务！</span>
			</td> 
			<td class="right fr clearfix">
			<button class="btn-win-white fl" style="margin-top:5px;" onclick="receive(<%=taskId%>)">接收任务</button>
	        <button class="btn-win-white fl" style="margin-top:5px;" onclick="noReceive(<%=taskId%>)">拒绝接收</button>
		    </td>
		</tr>
	</table>
	
	<!-- 等待审批提示区 -->
	<%-- <div id="alert2" class="alert alert-warning alert-dismissable" style="display:none">
	  <strong>您是任务审批人，需要审批该任务！</strong>
	  <br/>
	  <button class="btn-alert-blue" onclick="pass(<%=taskId%>)">审批通过</button>
	  <button class="btn-alert-blue" onclick="noPass(<%=taskId%>)">不通过</button>
	</div> --%>
	
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small" id="alert1" style="display:none">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;">
			<td><span>您是任务审批人，需要审批该任务！</span>
			</td> 
			<td class="right fr clearfix">
			<button class="btn-win-white fl" style="margin-top:5px;" onclick="pass(<%=taskId%>)">审批通过</button>
	        <button class="btn-win-white fl" style="margin-top:5px;" onclick="noPass(<%=taskId%>)">不通过</button>
		    </td>
		</tr>
	</table>
	
	<!-- 等待审核提示区 -->
	<%-- <div id="alert3" class="alert alert-warning alert-dismissable" style="display:none">
	  <strong>您是任务布置人，需要审核该任务！</strong>
	  <br/>
	  <button class="btn-alert-blue" onclick="pass1(<%=taskId%>)">审核通过</button>
	  <button class="btn-alert-blue" onclick="noPass1(<%=taskId%>)">不通过</button>
	</div> --%>
	
	<table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small" id="alert1" style="display:none">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;">
			<td><span>您是任务布置人，需要审核该任务！</span>
			</td> 
			<td class="right fr clearfix">
			<button class="btn-win-white fl" style="margin-top:5px;" onclick="pass1(<%=taskId%>)">审核通过</button>
	        <button class="btn-win-white fl" style="margin-top:5px;" onclick="noPass1(<%=taskId%>)">不通过</button>
		    </td>
		</tr>
	</table>
	
	<div class="panel panel-default">
<!-- 	  <div class="panel-heading"><b><a href="#"><i class="glyphicon glyphicon-barcode"></i>&nbsp;基本信息</a></b></div> -->
	  <table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;background:#eff8ff;">
			<td><span>基本信息</span>
			</td> 
			<td class="right fr clearfix" style="margin-top:5px;">
			<button class="btn-win-white fl" onclick="goback()">返回</button>
	      		<button style="display:none;" class="btn-win-white fl" id="b1" onclick="edit(<%=taskId%>)">修改任务</button>
				<button style="display:none" class="btn-win-white fl" id="b2" onclick="del(<%=taskId%>)">删除任务</button>
				<button style="display:none" class="btn-win-white fl" id="b4" onclick="redo(<%=taskId%>)">撤销任务</button>
				<button style="display:none" class="btn-win-white fl" id="b16" onclick="urge(<%=taskId%>)">督办任务</button>
				<button style="display:none" class="btn-win-white fl" id="b32" onclick="delay(<%=taskId%>)">任务延期</button>
				<button style="display:none" class="btn-win-white fl" id="b64" onclick="failed(<%=taskId%>)">任务失败</button>
				<button style="display:none" class="btn-win-white fl" id="b128" onclick="report(<%=taskId%>)">汇报进度</button>
				<button style="display:none" class="btn-win-white fl" id="b256" onclick="finish(<%=taskId%>)">完成任务</button>
			</td>
		</tr>
	</table>
<!-- 	  <div class="panel-body"> -->
<!-- 	   <div style="background:#e2e2e2;padding:10px;font-weight:bold;font-size:14px;">操作</div> -->
	      	<div style="padding:10px;">
	      		
	      	</div>
	      	<div style="padding:10px;font-weight:bold;font-size:14px;color:#2285c6;">信息</div>
	      	<div style="padding:10px;">
	      		<table style="font-size:12px;">
		      		<tr>
		      			<td align="right" style="width:105px">当前状态：</td>
		      			<td id="status"></td>
		      			<td align="right" style="width:105px">任务布置时间：</td>
		      			<td id="createTimeDesc"></td>
		      			<td style="width:105px"></td>
		      			<td></td>
		      		</tr>
		      		<tr>
		      			<td align="right">布置开始时间：</td>
		      			<td id="startTimeDesc"></td>
		      			<td align="right">布置结束时间：</td>
		      			<td id="endTimeDesc"></td>
		      			<td align="right">任务评分：</td>
		      			<td id="score"></td>
		      		</tr>
		      		<tr>
		      			<td align="right">实际开始时间：</td>
		      			<td id="relStartTimeDesc"></td>
		      			<td align="right">实际完成时间：</td>
		      			<td id="relEndTimeDesc"></td>
		      			<td align="right">任务进度：</td>
		      			<td id="progress">
		      				
		      			</td>
		      		</tr>
		      		<tr>
		      			<td align="right">布置人：</td>
		      			<td id="createUserName"></td>
		      			<td align="right">负责人：</td>
		      			<td id="chargerName"></td>
		      			<td align="right">审批人：</td>
		      			<td id="auditorName"></td>
		      		</tr>
		      		<tr>
		      			<td align="right">参与人：</td>
		      			<td id="joinerNames"></td>
		      			<td align="right">评估工时：</td>
		      			<td id="rangeTimes"></td>
		      			<td align="right">确认工时：</td>
		      			<td id="relTimes"></td>
		      		</tr>
		      	</table>
	      	</div>
	      	<div style="color:#2285c6;padding:10px;font-weight:bold;font-size:14px;">任务内容</div>
	      	<div style="padding:10px;" id="content"></div>
	      	<div style="color:#2285c6;padding:10px;font-weight:bold;font-size:14px;">奖惩标准</div>
	      	<div style="padding:10px;" id="standard"></div>
	      	<div style="color:#2285c6;padding:10px;font-weight:bold;font-size:14px;">备注批示</div>
	      	<div style="padding:10px;" id="leaderRemark"></div>
<!-- 	  </div> -->
	</div>
	
	<div class="panel-group" id="accordion">
	  <div class="panel panel-default">
<!-- 	    <div class="panel-heading"> -->
<!-- 	      <h4 class="panel-title"> -->
<!-- 	        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"> -->
<!-- 	          	<i class="glyphicon glyphicon-file"></i>&nbsp;任务事件<span id="eventCount" style="color:red"></span> -->
<!-- 	        </a> -->
              <table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;background:#eff8ff;">
			<td><span>任务事件</span>
			</td> 
	</table>
<!-- 	      </h4> -->
<!-- 	    </div> -->
	    <div id="collapseTwo" class="panel-collapse collapse">
	      <div class="" style="padding:10px;">
	      	<table class="none-table" style="width:100%">
	      		<thead>
		      		<tr class="TableHeader">
		      			<td style="width:80px">类型</td>
		      			<td>事件标题/内容</td>
		      			<td style="width:140px">操作人/时间</td>
		      		</tr>
	      		</thead>
	      		<tbody id="eventTbody">
	      			
	      		</tbody>
	      	</table>
	      </div>
	    </div>
	  </div>
	  <div class="panel panel-default" id="childTaskDiv">
	    <!-- <div class="panel-heading">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
	          	<i class="glyphicon glyphicon-log-in"></i>&nbsp;子任务<span id="childCount" style="color:red"></span>
	        </a>
	      </h4>
	    </div> -->
	    <table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;background:#eff8ff;">
			<td><span>子任务</span>
			</td> 
			<td class="right fr clearfix" style="margin-top:5px;">
	      		<span id="operDiv1">
	      	    <button class="btn-win-white fl" onclick="loadChildTask()" style="margin-right:10px;">刷新</button>
	      	    <button class="btn-win-white fl" onclick="addChildTask()">创建子任务</button>
	      	    </span>
			</td>
	    </table>
	    <div id="collapseThree" class="panel-collapse collapse">
	      <div class="" style="padding:10px">
	      	
	      	<table class="none-table" style="width:100%;margin-top:5px;">
	      		<thead>
		      		<tr class="TableHeader">
		      			<td>任务标题</td>
		      			<td>状态</td>
		      			<td>负责人</td>
		      			<td>布置人</td>
		      			<td>开始时间</td>
		      			<td>结束时间</td>
		      			<td>操作</td>
		      		</tr>
	      		</thead>
	      		<tbody id="childTaskTbody">
	      			
	      		</tbody>
	      	</table>
	      </div>
	    </div>
	  </div>
	  <div class="panel panel-default">
	    <!-- <div class="panel-heading">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
	          	<i class="glyphicon glyphicon-th"></i>&nbsp;任务汇报<span id="docCount" style="color:red"></span>
	        </a>
	      </h4>
	    </div> -->
	     <table border="0" width="100%" cellspacing="0" cellpadding="3"
		class="small">
		<tr style="font-size:16px;border-bottom: 2px solid #b0deff;background:#eff8ff;">
			<td><span>任务汇报</span>
			</td> 
			<td class="right fr clearfix" style="margin-top:5px;">
	      		<span id="operDiv1">
	      	    <button class="btn-win-white fl" onclick="loadDocs()" style="margin-right:10px;">刷新</button>
	      	    <button class="btn-win-white fl modal-menu-test" onclick="$(this).modal();" data-target="#uploadDiv" data-toggle="modal">提交文档</button>
	      	    </span>
			</td>
	    </table>
	    <div id="collapseFour" class="panel-collapse collapse">
	      <div class="panel-body" style="padding:10px;">
<!-- 	      	<span id="operDiv2"> -->
	      	<table class="none-table" style="width:100%;margin-top:5px;">
	      		<thead>
		      		<tr class="TableHeader">
		      			<td style="width:300px">文档</td>
		      			<td>备注</td>
		      			<td style="width:190px">发表人/时间</td>
		      			<td style="width:80px">操作</td>
		      		</tr>
	      		</thead>
	      		<tbody id="docTbody">
	      			
	      		</tbody>
	      	</table>
	      </div>
	    </div>
	  </div>
	</div>
	</div>
	
	
<!-- 撤销任务区 -->
<div id="redoDiv" style="display:none">
	<div style="padding:5px;">
		撤销理由：<br/>
		<textarea fid="redoTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 督办任务区 -->
<div id="urgeDiv" style="display:none">
	<div style="padding:5px;">
		督办内容：<br/>
		<textarea fid="urgeTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 任务失败区 -->
<div id="failedDiv" style="display:none">
	<div style="padding:5px;">
		失败原因：<br/>
		<textarea fid="failedTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 任务延期区 -->
<div id="delayDiv" style="display:none">
	<div style="padding:5px;">
		延期日期：<input type="text" class="BigInput easyui-validatebox Wdate" required fid="delayTime"/><br/>
		延期原因：<br/>
		<textarea fid="delayTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 进度汇报区 -->
<div id="reportDiv" style="display:none">
	<div style="padding:5px;">
		进度：<input type="text" class="BigInput easyui-validatebox" fid="progress" required validType='integeBetweenLength[0,100]' style="width:70px;"/> %<br/>
		汇报内容：<br/>
		<textarea fid="reportTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>

<!-- 任务完成区 -->
<div id="finishDiv" style="display:none">
	<div style="padding:5px;">
		任务评分：<input type="text" class="BigInput easyui-validatebox" fid="score" required validType='integeBetweenLength[0,100]'/><br/>
		确认工时：<input type="text" class="BigInput easyui-validatebox" fid="relTimes" required validType='integeBetweenLength[0,100]'/><br/>
		任务总结：<br/>
		<textarea fid="finishTextarea" style="width:380px;height:150px" class="BigTextarea"></textarea>
	</div>
</div>


<!-- 文档上传区 -->
<div class="modal fade" id="uploadDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-test">
<!--     <div class="modal-content"> -->
      <div class="modal-header">
      <!--   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">提交文档</h4> -->
        <p class="modal-title" id="myModalLabel">提交文档</p>
		<span class="modal-win-close">×</span>
      </div>
      <div class="modal-body">
        <div id="fileContainer2"></div>
		<div id="uploadHolder2" class="add_swfupload">
			<i class="glyphicon glyphicon-plus" style="margin-left:15px;"></i>添加附件
		</div>
		<input id="valuesHolder2" type="hidden"/>
		<div style="margin-top:10px;">
			<span style="margin-left:15px;"><b>备注：</b></span>
			<textarea id="attachRemark" style="height:150px;width:550px;margin-left:15px;margin-top:10px;" class="BigTextarea"></textarea>
		</div>
      </div>
      <div class="modal-footer clearfix">
        <button type="button" class="modal-btn-close btn-alert-gray fr" data-dismiss="modal" style="margin-right:10px;">取消</button>
        <button type="button" class="modal-save btn-alert-blue fr" onclick="uploadDoc()" style="margin-right:10px;">保存</button>
      </div>
<!--     </div>/.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>