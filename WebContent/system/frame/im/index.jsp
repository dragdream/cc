<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<script type="text/javascript" charset="UTF-8">
function doInit(){
	queryOnlineUserCount();
	
	setInterval("queryOnlineUserCount()",1000*30);
}

/**
 * 获取在线人员
 */
function queryOnlineUserCount(){
	var url = contextPath + "/personManager/queryOnlineUserCount.action?";
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var data  = jsonRs.rtData;
		$("#onlineUserCount").html('<span>当前&nbsp;&nbsp;<a href="javascript:void(0)" style="color:red">' + data.onlineUserCount + '</a>&nbsp;&nbsp;人在线</span>');
	}else{
		//alert(jsonRs.rtMsg);
	}
}

//刷新notes
function RefreshNotes(){
	$("#frame4")[0].contentWindow.location.reload();
}

function change(obj,sel){
	$(".btn01").removeClass("btn01select");
	$("iframe").hide();
	if(sel==1){
		$("#frame1").show();
	}else if(sel==2){
		$("#frame2").show();
	}else if(sel==3){
		$("#frame3").show();
	}else if(sel==4){
		$("#frame4").show();
	}
	$(obj).addClass("btn01select");
}
</script>
<style>
.btn01{
	background:#056db4;
	color:white;
	font-size:14px;
	cursor:pointer;
}
.btn01:hover{
	background:#f5f5f5;
	color:#000;
}
.btn01select{
	background:#f5f5f5;
	color:#000;
}
</style>
</head>
<body onload="doInit()" style="padding:0px;margin:0px;overflow:hidden">
<div style="position:absolute;left:0px;right:0px;top:0px;height:30px;;overflow:hidden">
<table style="border-collapse:collapse;width:100%;border:0px;height:100%;text-align:center;">
<tr style="font-size:16px;font-family:微软雅黑">
	<td class="btn01 btn01select" onclick="change(this,1)">导航</td>
	<td class="btn01" onclick="change(this,2)">组织</td>
	<td class="btn01" onclick="change(this,3)">群组</td>
	<td class="btn01" onclick="change(this,4)">便签</td>
</tr>
</table>
</div>
<div style="position:absolute;left:0px;right:0px;bottom:0px;height:30px;background:#f5f5f5;overflow:hidden">
	<p id="onlineUserCount" style="line-height:30px;font-size:12px;padding-left:10px;border-top:1px solid #cdcdcd;"></p>
</div>
<div style="position:absolute;left:0px;right:0px;top:30px;bottom:30px;background:#f5f5f5;overflow:hidden;">
<iframe id="frame1" style="width:100%;height:100%;" frameborder="0" src="menu.jsp"></iframe>
<iframe id="frame2" style="width:100%;height:100%;display:none" frameborder="0" src="org.jsp"></iframe>
<iframe id="frame3" style="width:100%;height:100%;display:none" frameborder="0" src="group.jsp"></iframe>
<iframe id="frame4" style="width:100%;height:100%;display:none" frameborder="0" src="bianqian.jsp"></iframe>
</div>
</body>
</html>
		        