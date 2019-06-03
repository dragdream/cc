<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<title>系统邮箱设置</title>
<script type="text/javascript">


function doInit(){
	var SYS_WEB_MAIL = eval("("+getSysParamByNames("SYS_WEB_MAIL")[0].paraValue+")");
	bindJsonObj2Cntrl(SYS_WEB_MAIL);
}

/**
 * 保存
 */
function doSave(){
	var para = tools.formToJson($("#form1"));
	tools.requestJsonRs(contextPath+"/sysPara/updateSysPara.action",{paraName:"SYS_WEB_MAIL",paraValue:tools.jsonObj2String(para)});
	$.MsgBox.Alert_auto("保存成功");
}

</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<div id="toolbar" class="topbar clearfix">
	   <img class="title_img" src="/system/core/system/imgs/xtyxsz.png" alt="">
	   &nbsp;<span class="title">系统外发邮箱设置</span>
	   <button  class="btn-win-white fr" style="margin-right:10px" onclick="doSave();">保存</button>
	   
	</div>

<form   method="post" name="form1" id="form1">
<table class="TableBlock_page" width="100%" align="center">
	<tr class="TableLine1" >
		<td nowrap style="text-indent: 10px">SMTP地址：</td>
		<td nowrap>
			<input type="text" class="BigInput" id="server" name="server"/>
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap style="text-indent: 10px">端口：</td>
		<td nowrap>
			<input type="text" class="BigInput" id="port" name="port"/>
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap style="text-indent: 10px">发送邮箱：</td>
		<td nowrap>
			<input type="text" class="BigInput" id="usermail" name="usermail"/>
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap style="text-indent: 10px">用户名：</td>
		<td nowrap>
			<input type="text" class="BigInput" id="user" name="user"/>
		</td>
	</tr>
	<tr class="TableLine1">
		<td nowrap style="text-indent: 10px">密码：</td>
		<td nowrap>
			<input type="text" class="BigInput" id="pass" name="pass"/>
		</td>
	</tr>
	
</table>
</form>
</body>
</html>