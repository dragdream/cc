<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>桌面模块设置</title>

<script type="text/javascript" charset="UTF-8">
function doInit(){
	var json = tools.requestJsonRs(contextPath+"/dingding/getBasicParam.action");
	$("#DD_URL").val(json.rtData.DD_URL);
	$("#DD_CORPID").val(json.rtData.DD_CORPID);
	$("#DD_CORPSECRET").val(json.rtData.DD_CORPSECRET);
}

function save(){
	var json = tools.requestJsonRs(contextPath+"/dingding/saveBasicParam.action",{
		DD_URL:$("#DD_URL").val(),
		DD_CORPID:$("#DD_CORPID").val(),
		DD_CORPSECRET:$("#DD_CORPSECRET").val()
	});
	alert("保存成功");
}

function test(){
	$("#testBtn").attr("disabled","disabled");
	tools.requestJsonRs(contextPath+"/dingding/testConnections.action",{DD_CORPID:$("#DD_CORPID").val(),DD_CORPSECRET:$("#DD_CORPSECRET").val()},true,function(json){
		$("#testBtn").removeAttr("disabled");
		if(json.rtState){
			alert("连接成功");
		}else{
			alert(json.rtMsg);
		}
	});
}
</script>
</head>
<body onload="doInit();">
	<fieldset>
		<legend>
			<h5 style="font-size:14px;font-weight:bold;font-family:微软雅黑">参数设置</h5>
		</legend>
	</fieldset>
	<form>
	  <div class="form-group">
	    <label for="DD_URL">OA外网地址</label>
	    <input type="text" class="form-control" id="DD_URL"  placeholder="http://xxx.xxx.xxx">
	    <span class="help-block">注：OA的外网地址格式为: <code>http://www.xxx.com</code> 或者 数字ip <code>http://123.23.12.XX</code>。</span>
	  </div>
	  <div class="form-group">
	    <label for="CorpID">CorpID</label>
	    <input type="text" class="form-control" id="DD_CORPID" name="DD_CORPID" placeholder="CorpID">
	  </div>
	  <div class="form-group">
	    <label for="CorpSecret">CorpSecret</label>
	    <input type="text" class="form-control" id="DD_CORPSECRET" name="DD_CORPSECRET" placeholder="CorpSecret">
	  </div>
	  <button type="button" class="btn btn-default" onclick="save()">保存</button>
	  &nbsp;
	  <button type="button" id="testBtn" class="btn btn-warning" onclick="test()">连接测试</button>
	  &nbsp;&nbsp;<a href="help1/index.jsp" target="_blank">帮助文档</a>
	</form>
</body>
</html>