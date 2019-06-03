<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.core.org.bean.*"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String scheduleId = request.getParameter("scheduleId");
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	int type = TeeStringUtil.getInteger(request.getParameter("type"), 1);
	
	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<script>
var scheduleId = "<%=scheduleId%>";
var _type = <%=type%>;
var userId = <%=loginUser.getUuid()%>;
function doInit(){
	$.jBox.tip("正在加载……","loading");
	//获取权限
	var json = tools.requestJsonRs(contextPath+"/schedule/getPriv.action",{scheduleId:scheduleId});
	if(json.rtData.shenpi){
		$("#t1Div").show();
	}
	if(json.rtData.pinglun){
		$("#t2Div").show();
	}
	
	tools.requestJsonRs(contextPath+"/schedule/get.action",{uuid:scheduleId},true,function(json){
		if(!json.rtState){
			messageMsg("该计划不存在或已删除","bd","info");
			return;
		}
		
		if(json.rtData.flag!=0){
			$("#finishBtn").hide();
			$("#t1Div").hide();
			$("#t2Div").hide();
		}
		
		json.rtData.content = json.rtData.content.split("\n").join("<br/>");
		if(json.rtData.summarize){
			json.rtData.summarize = json.rtData.summarize.split("\n").join("<br/>");
		}
		
		if(userId==json.rtData.userId){
			$("#operDiv1").show();
		}
		
		bindJsonObj2Cntrl(json.rtData);
		if(json.rtData.flag==0){
			$("#flag").html("<span style='color:green'>进行中</span>");
		}else if(json.rtData.flag==1){
			$("#flag").html("<span style='color:red'>已完成</span>");
		}else{
			$("#flag").html("<span style='color:orange'>超时完成</span>");
		}
		
		if(json.rtData.rangeType==1){
			$("#type").html("日计划");
		}else if(json.rtData.rangeType==2){
			$("#type").html("周计划");
		}else if(json.rtData.rangeType==3){
			$("#type").html("月计划");
		}else if(json.rtData.rangeType==4){
			$("#type").html("季度计划");
		}else if(json.rtData.rangeType==5){
			$("#type").html("半年计划");
		}else if(json.rtData.rangeType==6){
			$("#type").html("年计划");
		}

		
		getAnnotations();
		getComment();
		loadTask();
		$.jBox.tip("加载完毕","success");
	});
}

function getAnnotations(){
	tools.requestJsonRs(contextPath+"/scheduleAnnotations/datagrid.action",{uuid:scheduleId},true,function(json){
		var rows = json.rows;
		var render = [];
		for(var i=0;i<rows.length;i++){
			render.push("<div style='margin-bottom:10px;'>");
			render.push("#"+(i+1)+"&nbsp;&nbsp;批示人:"+rows[i].userName+"&nbsp;&nbsp;批示时间："+rows[i].crTimeDesc);
			if(userId==rows[i].userId){
				render.push("&nbsp;&nbsp;<a style='color:red' href='javascript:void(0)' onclick=\"del1('"+rows[i].uuid+"')\">删除</a>");
			}
			render.push("<br/>");
			render.push(rows[i].content);
			render.push("</div>");
		}
		if(render.length==0){
			$("#annotationsDiv").html("无相关批示");
		}else{
			$("#annotationsDiv").html(render.join(""));
		}
	});
}

function getComment(){
	tools.requestJsonRs(contextPath+"/scheduleComment/datagrid.action",{uuid:scheduleId},true,function(json){
		var rows = json.rows;
		var render = [];
		for(var i=0;i<rows.length;i++){
			render.push("<div style='margin-bottom:10px;'>");
			render.push("#"+(i+1)+"&nbsp;&nbsp;评论人:"+rows[i].userName+"&nbsp;&nbsp;评论时间："+rows[i].crTimeDesc);
			if(userId==rows[i].userId){
				render.push("&nbsp;&nbsp;<a style='color:red' href='javascript:void(0)' onclick=\"del2('"+rows[i].uuid+"')\">删除</a>");
			}
			render.push("<br/>");
			render.push(rows[i].content);
			render.push("</div>");
		}
		if(render.length==0){
			$("#commentDiv").html("无相关评论");
		}else{
			$("#commentDiv").html(render.join(""));
		}
		
	});
}

function del1(uuid){
	if(window.confirm("是否删除该批示？")){
		var json = tools.requestJsonRs(contextPath+"/scheduleAnnotations/delete.action",{uuid:uuid});
		getAnnotations();
	}
}

function del2(uuid){
	if(window.confirm("是否删除该评论？")){
		var json = tools.requestJsonRs(contextPath+"/scheduleComment/delete.action",{uuid:uuid});
		getComment();
	}
}

function commitAnnotations(){
	if($("#t1").val()==""){
		alert("批示不能为空！");
		$("#t1").focus();
		return;
	}
	var json = tools.requestJsonRs(contextPath+"/scheduleAnnotations/save.action",
			{scheduleId:scheduleId,content:$("#t1").val()});
	$("#t1").val("");
	getAnnotations();
}

function commitComment(){
	if($("#t2").val()==""){
		alert("评论不能为空！");
		$("#t2").focus();
		return;
	}
	var json = tools.requestJsonRs(contextPath+"/scheduleComment/save.action",
			{scheduleId:scheduleId,content:$("#t2").val()});
	$("#t2").val("");
	getComment();
}

function finish(){
	$("#finishDiv").modal("show");
}

function ok(){
	var json = tools.requestJsonRs(contextPath+"/schedule/finish.action",
			{scheduleId:scheduleId,summerize:$("#summerize").val()});
	
	window.location.reload();
}

function addTask(){
	bsWindow(contextPath+"/system/subsys/cowork/addChildTask.jsp?scheduleId="+scheduleId,"创建任务",{width:"800",submit:function(v,h){
		var contentWindow = h[0].contentWindow;
		if(contentWindow.commit()){
			loadTask();
			return true;
		}
	}});
}

function loadTask(){
	tools.requestJsonRs(contextPath + '/coWork/getTaskRelations.action',{scheduleId:scheduleId},true,function(json){
		var rows = json.rtData;
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
		render+="审核通过";
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


function lookup(sid){
	window.location = contextPath+"/system/subsys/cowork/detail.jsp?taskId="+sid;
}

</script>
<style>
</style>
</head>
<body onload="doInit();" style="margin:0px;" id="bd">
<div class="moduleHeader">
	<div>
		<b><i class="glyphicon glyphicon-list-alt"></i>&nbsp;[计划]&nbsp;<span id="title"></span></b>
	</div>
</div>
<button class="btn btn-default" onclick="CloseWindow()">关闭</button>
<br/><br/>
<table style="font-size:12px;width:800px">
	<tr>
		<td align="center" colspan="6" style="background:#f0f0f0;padding:10px"><b>基本信息</b></td>
	</tr>
	<tr>
		<td align="right" style="width:120px">计划类型：</td>
		<td id="type"  style="width:110px"></td>
		<td align="right" style="width:120px">计划人：</td>
		<td id="userName" style="width:110px"></td>
		<td align="right" style="width:120px">计划状态：</td>
		<td id="flag" style="width:110px"></td>
	</tr>
	<tr>
		<td align="right">开始时间：</td>
		<td id="time1Desc"></td>
		<td align="right">结束时间：</td>
		<td id="time2Desc"></td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="center" colspan="6" style="background:#f0f0f0;padding:10px"><b>内容</b></td>
	</tr>
	<tr>
		<td align="left" colspan="6" id="content"></td>
	</tr>
	<tr>
		<td align="center" colspan="6" style="background:#f0f0f0;padding:10px"><b>总结</b></td>
	</tr>
	<tr>
		<td align="left" colspan="6" id="summarize"></td>
	</tr>
	<tr>
		<td align="center" colspan="6" style="background:#f0f0f0;padding:10px"><b>相关任务</b></td>
	</tr>
	<tr>
		<td align="left" colspan="6" style="padding:10px;">
	      	<table class="TableBlock" style="width:100%;margin-top:5px;">
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
		</td>
	</tr>
	<tr>
		<td align="center" colspan="6" style="background:#f0f0f0;padding:10px"><b>相关批示</b></td>
	</tr>
	<tr>
		<td align="left" colspan="6" id="annotationsDiv">
			
		</td>
	</tr>
	<tr>
		<td align="left" colspan="6" id="t1Div" style="display:none">
		</td>
	</tr>
	<tr>
		<td align="center" colspan="6" style="background:#f0f0f0;padding:10px"><b>相关评论</b></td>
	</tr>
	<tr>
		<td align="left" colspan="6" id="commentDiv">
			
		</td>
	</tr>
	<tr>
		<td align="left" colspan="6" id="t2Div" style="display:none">
		</td>
	</tr>
</table>
</div>

</body>
</html>