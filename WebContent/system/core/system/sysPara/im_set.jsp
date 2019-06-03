<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>即时通讯管理</title>
<script type="text/javascript">


function doInit(){
	var OF_IP = getSysParamByNames("OF_IP");
	$("#OF_IP").val(OF_IP[0].paraValue);
	
	var OF_PORT = getSysParamByNames("OF_PORT");
	$("#OF_PORT").val(OF_PORT[0].paraValue);
	
	var OF_KEY = getSysParamByNames("OF_KEY");
	$("#OF_KEY").val(OF_KEY[0].paraValue);
	
	var ANDROID_CURR_VERSION = getSysParamByNames("ANDROID_CURR_VERSION");
	$("#ANDROID_CURR_VERSION").val(ANDROID_CURR_VERSION[0].paraValue);
	
	var PC_CURR_VERSION = getSysParamByNames("PC_CURR_VERSION");
	$("#PC_CURR_VERSION").val(PC_CURR_VERSION[0].paraValue);
}

/**
 * 保存
 */
function doSave(){
	tools.requestJsonRs(contextPath+"/sysPara/updateSysPara.action",{paraName:"OF_IP",paraValue:$("#OF_IP").val()});
	tools.requestJsonRs(contextPath+"/sysPara/updateSysPara.action",{paraName:"OF_PORT",paraValue:$("#OF_PORT").val()});
	tools.requestJsonRs(contextPath+"/sysPara/updateSysPara.action",{paraName:"OF_KEY",paraValue:$("#OF_KEY").val()});
	$.MsgBox.Alert_auto("保存成功");
}

function doSync(){
	if(window.confirm("确认要将OA用户同步到即时通讯服务器吗？")){
		$.MsgBox.Loading();
		tools.requestJsonRs(contextPath+"/openfire/syncUsers.action",{},true,function(json){
			if(json.rtState){
				$.MsgBox.Alert_auto("同步完成");
			}else{
				$.MsgBox.Alert_auto("错误："+json.rtMsg);
			}
			$.MsgBox.CloseLoading();
		});
	}
}

function doClear(){
	if(window.confirm("确认要清空即时通讯服务器的用户吗？")){
		$.MsgBox.Loading();
		tools.requestJsonRs(contextPath+"/openfire/clearUsers.action",{},true,function(json){
			if(json.rtState){
				$.MsgBox.Alert_auto("清空完成");
			}else{
				$.MsgBox.Alert_auto("错误："+json.rtMsg);
			}
			$.MsgBox.CloseLoading();
		});
	}
}

function upload(){
	$.MsgBox.Loading();
	$("#form2").ajaxSubmit({
        url: contextPath+"/systemAction/uploadImClient.action",
        iframe: true,
        data: {},
        success: function(json) {
        	if(json.rtState){
        		window.location.reload();
        	}else{
        		$.MsgBox.Alert_auto(json.rtMsg);
        	}
        },
        error: function(arg1, arg2, ex) {
	        $.MsgBox.Alert_auto("文件传输错误");
        },
        dataType: 'json'});
}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
   <img class="title_img" src="/common/zt_webframe/imgs/grbg/zhdap/icon_zhdap.png" alt="">
   &nbsp;<span class="title">即时通讯服务器配置<span id="totalMail"></span></span>
    <button  class="btn-del-red fr" style="margin-right:10px" onclick="doClear();">清空用户</button>
   <button  class="btn-alert-blue fr" style="margin-right:10px" onclick="doSync();">同步用户</button>
   <button  class="btn-win-white fr" style="margin-right:10px" onclick="doSave();">保存</button>
   
</div>
<form   method="post" name="form1" id="form1" style="padding:5px">
<table class="TableBlock_page" width="500px" align="center">
	<tr class="TableLine1">
		<td nowrap width="150">IP：</td>
		<td nowrap>
			<input type="text" class="BigInput" id="OF_IP"/>
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap>端口：</td>
		<td nowrap>
			<input type="text" class="BigInput" id="OF_PORT"/>
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap>密钥：</td>
		<td nowrap>
			<input type="text" class="BigInput" id="OF_KEY"/>
		</td>
	</tr>
</table>
</form>
<br/>
<img class="title_img" src="/common/zt_webframe/imgs/grbg/zhdap/icon_zhdap.png" alt="">
   &nbsp;<span class="title">客户端相关<span id="totalMail"></span></span>
<div style="padding:5px">
<form id="form2" action="" target="uploadFrm" method="post" enctype="multipart/form-data">
<table class="TableBlock_page" width="500px" align="center">
<!-- 	<tr class="TableLine1"> -->
<!-- 		<td nowrap width="150">安卓端版本号：</td> -->
<!-- 		<td nowrap> -->
<!-- 			<input type="text" style="width:100px"  readonly class="BigInput" id="ANDROID_CURR_VERSION"/> -->
<!-- 			<input type="file" name="android" /> -->
<!-- 			在此可上传增量安卓端升级补丁 -->
<!-- 		</td> -->
<!-- 	</tr> -->
	<tr class="TableLine1">
		<td nowrap>PC端版本号：</td>
		<td nowrap>
			<input type="text" style="width:100px"  readonly class="BigInput" id="PC_CURR_VERSION"/>
			<input type="file" name="pc" />
			在此可上传增量PC端升级补丁
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap colspan="2">
			<button type="button" class="btn-win-white" onclick="upload()">上传</button>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>