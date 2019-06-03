<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeSmsSendPhoneController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}
}
</script>
</head>
<body onload="doInit();" style="margin:0 auto;padding:0 auto;font-size:12px;margin-top:10px;">
<form id="form1" name="form1">
	<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;手机短信详情</b>
	</div>
	<table style="width:60%;font-size:12px;" class='TableBlock' align="center">
		<tr class='TableData' align='left'>
			<td style="width:20%;">
				发送时间：
			</td>
			<td>
				<div  id='sendTimeDesc' name='sendTimeDesc'></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td style="width:20%;">
				接收人手机号：
			</td>
			<td>
				<div name="phone" id="phone" ></div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td style="width:20%;">
				短信内容：
				</td>
			<td>
				<div id='content' name='content'></div>
			</td>
		</tr >
		<tr>
			<td colspan='2' style='text-align:center;'>
				<input id="sid" name="sid" type='hidden'value="<%=sid %>"/>
				<input id="back" name="back" type='button' class="btn btn-primary" value='返回' onclick='history.go(-1)'/>
			</td>
		</tr>
	</table>
	<br/>
</form>
</body>
</html>