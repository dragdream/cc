<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" import="com.tianee.webframe.util.*"%>
<%@ include file="/header/header.jsp" %>
<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script>
var prcsId = <%=prcsId%>;

function doInit(){
	//获取步骤信息
	var url = contextPath+"/flowProcess/getProcessInfo.action";
	var json = tools.requestJsonRs(url,{prcsSeqId:prcsId});
	var fpInfo = json.rtData;
	bindJsonObj2Cntrl(fpInfo);

	//填充数据
	if(fpInfo.prcsType==1){
		$("#prcsType").html("开始节点");
	}else if(fpInfo.prcsType==3){
		$("#prcsType").html("普通节点");
	}else if(fpInfo.prcsType==5){
		$("#prcsType").html("聚合节点");
	}
	
	var beginUserAlert = fpInfo.beginUserAlert;
	var sp = beginUserAlert.split(",");
	for(var i=1;i<=sp.length;i++){
		if(sp[i-1]=='1'){
			$("#beginUserAlert"+i).attr("checked","checked");
		}
	}

	var allPrcsUserAlert = fpInfo.allPrcsUserAlert;
	var sp = allPrcsUserAlert.split(",");
	for(var i=1;i<=sp.length;i++){
		if(sp[i-1]=='1'){
			$("#allPrcsUserAlert"+i).attr("checked","checked");
		}
	}

	var nextPrcsAlert = fpInfo.nextPrcsAlert;
	var sp = nextPrcsAlert.split(",");
	for(var i=1;i<=sp.length;i++){
		if(sp[i-1]=='1'){
			$("#nextPrcsAlert"+i).attr("checked","checked");
		}
	}

	//绘制下一步骤
	var html = "";
	var prcsList = fpInfo.params.prcsList;
	var nextPrcsList = fpInfo.params.nextPrcsList;
	var nextPrcsArray = new Array();
	for(var i=0;i<nextPrcsList.length;i++){
		nextPrcsArray.push(nextPrcsList[i].sid);
	}
	for(var i=0;i<prcsList.length;i++){
		var data = prcsList[i];
		if(tools.findInSet(data.sid,nextPrcsArray)){
			html+="<input type='checkbox' value='"+data.sid+"' id='prcs_"+data.sid+"' checked/>"+data.prcsName+"&nbsp;&nbsp;";
		}else{
			html+="<input type='checkbox' value='"+data.sid+"' id='prcs_"+data.sid+"' />"+data.prcsName+"&nbsp;&nbsp;";
		}
	}
	$("#nextPrcs").html(html);
}

//提交
function commit(){
	var url = contextPath+"/flowProcess/updateProcessInfo.action";
	var para = tools.formToJson($("#body"));
	para["prcsSeqId"] = prcsId;
	para["nextPrcsAlert"] = getNextPrcsAlert();
	para["beginUserAlert"] = getBeginUserAlert();
	para["allPrcsUserAlert"] = getAllPrcsUserAlert();

	var chekcedPrcs = $("#nextPrcs input[type=checkbox][checked]");
	var nextPrcsIds = new Array();
	chekcedPrcs.each(function(i,obj){
		nextPrcsIds.push(obj.value);
	});
	para["nextPrcs"] = nextPrcsIds+"";
	
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		alert(json.rtMsg);
		window.opener.saveLayout();
		window.opener.location.reload();
	}else{
		alert(json.rtMsg);
	}
	
}

//获取下一步经办人提醒权限字符串
function getNextPrcsAlert(){
	var str = "";
	if($("#nextPrcsAlert1").attr("checked")){
		str+="1,";
	}else{
		str+="0,";
	}

	if($("#nextPrcsAlert2").attr("checked")){
		str+="1,";
	}else{
		str+="0,";
	}

	if($("#nextPrcsAlert3").attr("checked")){
		str+="1";
	}else{
		str+="0";
	}
	return str;
}
//获取发起人提醒权限字符串
function getBeginUserAlert(){
	var str = "";
	if($("#beginUserAlert1").attr("checked")){
		str+="1,";
	}else{
		str+="0,";
	}

	if($("#beginUserAlert2").attr("checked")){
		str+="1,";
	}else{
		str+="0,";
	}

	if($("#beginUserAlert3").attr("checked")){
		str+="1";
	}else{
		str+="0";
	}
	return str;
}
//获取所有经办人提醒权限字符串
function getAllPrcsUserAlert(){
	var str = "";
	if($("#allPrcsUserAlert1").attr("checked")){
		str+="1,";
	}else{
		str+="0,";
	}

	if($("#allPrcsUserAlert2").attr("checked")){
		str+="1,";
	}else{
		str+="0,";
	}

	if($("#allPrcsUserAlert3").attr("checked")){
		str+="1";
	}else{
		str+="0";
	}
	return str;
}
</script>

</head>
<body onload="doInit()" id="body">
<div id="tabDiv" class="easyui-tabs" fit="true" border="0" tools="#tools">
<!-- 基本属性设置 -->
<div title="基本属性" style="overflow:auto">

</div>

<!-- 经办权限设置 -->
<div title="经办权限" style="overflow:auto">

</div>


<div title="流转设置">

</div>


<div title="事务提醒">
	
</div>


<div title="其他设置">
	<table class="formFrame" style="height:100px;width:100%">
<tr><td class="formFrame_left_top"></td>
	<td class="formFrame_top"></td>
	<td class="formFrame_right_top"></td>
	</tr>
	<tr>
		<td class="formFrame_left"></td>
		<td class="formFrame_center">
			<table style="font-size:12px">
				
			</table>
		</td>
	<td class="formFrame_right"></td>
</tr>
<tr>
	<td class="formFrame_left_bottom"></td><td class="formFrame_bottom"></td><td class="formFrame_right_bottom"></td></tr>
</table>
</div>


</div>
<div id="tools" style="border-left:0px;border-top:0px;">
	<input type="button" class="BigButtonA" value="保存"  onclick="commit()"/>
	<input type="button" class="BigButtonA" value="关闭"  onclick="CloseWindow()" />
</div>
</body>
</html>