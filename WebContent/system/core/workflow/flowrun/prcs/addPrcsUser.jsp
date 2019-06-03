<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),
				0);
		int frpSid = TeeStringUtil.getInteger(request
				.getParameter("frpSid"), 0);
		int flowId = TeeStringUtil.getInteger(request
				.getParameter("flowId"), 0);
		String isNew = TeeStringUtil.getString(request
				.getParameter("isNew"), "");
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>添加加签人</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
	
<script>
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var flowId = <%=flowId%>;
// window.PRCSID = prcsId;
// window.FRPSID = frpSid;
var prcsId= "0";

var addPrcsUserPriv=1;//加签权限
var error = false;
function doInit(){
	//先通过frpSid获取相关依赖数据
	var json =  tools.requestJsonRs("/flowRun/getInfosByFrpSid.action",{frpSid:frpSid});
	prcsId = json.rtData.prcsId;
	addPrcsUserPriv=json.rtData.addPrcsUserPriv;
	
	
	var url = contextPath+"/flowRun/getCurPrcsUsers.action";
	var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid,flowId:flowId});
	if(json.rtState){
		var html = new Array();
		for(var i=0;i<json.rtData.length;i++){
			var item = json.rtData[i];
			html.push("<tr>");
			if(item.topFlag==1){
				html.push("<td><span style='color:green'>主办</span>&nbsp;");
			}else{
				html.push("<td><span style='color:red'>会签</span>&nbsp;");
			}
			html.push(item.userName+"</td>");
			
			if(item.flag==1){
				html.push("<td style='color:gray'>未接收</td>");
			}else if(item.flag==2){
				html.push("<td style='color:red'>接收未办理</td>");
			}else if(item.flag==3 || item.flag==4){
				html.push("<td style='color:green'>已办结</td>");
			}
			if(item.topFlag==1){
				html.push("<td></td>");
			}else{
				html.push("<td><img title='撤回' onclick='cancel("+item.frpSid+")' style='cursor:pointer' src='/common/images/upload/remove.png'/></td>");
			}
			html.push("</tr>");
		}
		$("#tbody").html(html.join(""));
	}
	
	var url = contextPath+"/flowRun/getPrcsUsersFilter.action";
	var json = tools.requestJsonRs(url,{frpSid:frpSid});
	window.filter = json.rtData;
	
	
	
	
	//根据加签权限 判断是当前步骤的办理人员  还是  全部人员
	if(addPrcsUserPriv==1){
		$("#add2").hide();
		$("#add1").show();
	}else if(addPrcsUserPriv==2){
		$("#add2").show();
		$("#add1").hide();
	}
}

function cancel(frpSid){
	if(window.confirm("是否撤回该人员的工作？")){
		var url = contextPath+"/flowRun/delFlowRunPrcsById.action";
		var json = tools.requestJsonRs(url,{frpSid:frpSid});
		alert("已成功撤回");
		window.location.reload();
	}
}

function commit(){
	if($("#prcsUser").val()==""){
		top.$.jBox.tip("请选择加签人","info");		
		return;
	}

	var url = contextPath+"/flowRun/addPrcsUser.action";
	var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid,flowId:flowId,prcsUser:$("#prcsUser").val()});
	json["prcsUserDesc"]=$("#prcsUserDesc").val();
	return json;
	/* if(json.rtState){
		return true;
	}else{
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
	} */
}

</script>
</head>
<body onload="doInit()" style="margin:0px;padding:0px;font-size:12px">
<div id="container" style="padding:5px">
	加签人：<textarea id="prcsUserDesc" class="BigTextarea readonly" readonly style="width:200px;height:80px;"></textarea>
	<input type="hidden" id="prcsUser" class="BigInput"/>
	&nbsp;<a href="javascript:void(0)" style="display: none;" id="add1" onclick="selectUserWorkFlow(['prcsUser','prcsUserDesc'],'','',prcsId+'_'+frpSid,'')">添加</a>
	      <a href="javascript:void(0)" style="display: none;" id="add2" onclick="selectUser(['prcsUser','prcsUserDesc'],'14')" >添加</a>
	<hr/>
	<table class="TableBlock" style="width:100%">
		<thead>
			<tr class="TableHeader">
				<td>当前步骤办理人</td>
				<td>办理情况</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody id="tbody">
			
		</tbody>
	</table>
</div>
</body>
</html>