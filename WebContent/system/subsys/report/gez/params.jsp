<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>视频会议组件设置</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/colorPicker/css/colorPicker.css"/>
<script type="text/javascript">
function doInit(){
	var url = contextPath+"/gezReport/getParams.action";
	var json = tools.requestJsonRs(url);
	bindJsonObj2Cntrl(json.rtData);
}

/**
 * 保存
 */
function doSave(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/gezReport/updateParams.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			//top.$.jBox.tip(json.rtMsg,"info");
			alert("保存成功！");
			location.reload();
		}else{
			alert("保存错误！");
		}
	}
}

function test(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/gezReport/testConnect.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			//top.$.jBox.tip(json.rtMsg,"info");
			alert("连接成功！");
		}else{
			alert(json.rtMsg);
		}
	}
}

</script>
</head>
<body onload="doInit()" topmargin="5" style="padding:10px;">
<br/>
<form  name="form1" id="form1">
  <table class="TableBlock" width="700" align="center">
   <tr>
    <td colspan='2' class="TableHeader">报表系统参数设置</td>
   </tr>
   <tr>
    <td nowrap class="TableData">系统访问地址：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="GENZ_ADDR" class="BigInput" name="GENZ_ADDR" style="width:300px"/>
    </td>
   </tr>
    <tr>
    <td nowrap class="TableData">数据库Driver：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="GENZ_DB_DRIVER" class="BigInput" name="GENZ_DB_DRIVER" style="width:300px"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">数据库URL：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="GENZ_DB_URL" class="BigInput" name="GENZ_DB_URL" style="width:300px"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">数据库User：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="GENZ_DB_USER" class="BigInput" name="GENZ_DB_USER" style="width:300px"/>
    </td>
   </tr>
    <tr>
    <td nowrap class="TableData">数据库PassWord：</td>
    <td nowrap class="TableData" >
    	<input type='text' id="GENZ_DB_PWD" class="BigInput" name="GENZ_DB_PWD" style="width:300px"/>
    </td>
   </tr>
   <tr>
    <td colspan='2' style="text-align:Center" class="TableHeader">
   	 	<button class="btn btn-info" type="button" onclick="doSave()">保存</button>
   	 	<button class="btn btn-info" type="button" onclick="test()">连接测试</button>
    </td>
   </tr>
</table>
</form>
</body>
</html>