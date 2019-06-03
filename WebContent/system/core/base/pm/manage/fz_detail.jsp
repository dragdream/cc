<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" ></meta>
<link rel="stylesheet" href="<%=contextPath%>/system/core/base/pm/css/style.css" type="text/css">
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var sid='<%=sid%>';
function doInit(){
	var url = contextPath+"/TeeHumanRehabController/getModelById.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		alert(json.rtMsg);
	}
}

</script>

</head>
<body onload="doInit()" >
<form id="form1" name="form1">
	<table  style="width:100%;font-size:12px" class="TableBlock">
		<tr class='TableData'>
			<td class='TableData'>
				<b>担任职务：</b>
			</td>
			<td class='TableData'>
				<div id="pos"></div>
			</td>
			<td class='TableData'>
				<b>复职类型：</b>
			</td>
			<td class='TableData'>
				<div id="rehabTypeDesc" ></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td class='TableData'>
				<b>申请日期：</b>
			</td>
			<td class='TableData'>
				<div id='regTimeDesc' ></div>
			</td>
			<td class='TableData'>
				<b>拟复职日期：</b>
			</td>
			<td class='TableData'>
				<div id='planTimeDesc'  ></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td class='TableData'>
				<b>实际复职日期：</b>
			</td>
			<td>
				<div  id='realTimeDesc'  ></div>
			</td class='TableData'>
			<td class='TableData'>
				<b>工资恢复日期：</b>
			</td>
			<td class='TableData'>
				<div  id='payTimeDesc'  ></div>
			</td>
		</tr>
			<tr class='TableData'>
			<td class='TableData'>
				<b>复职部门：</b>
			</td>
			<td colspan="3" class='TableData'>
				<div  id="rehabDept"></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td class='TableData'>
				<b>复职手续办理：</b>
			</td>
			<td colspan="3" class='TableData'>
				<div id="rehabDetail" style="width:425px;height:100px" ></div>
			</td>
		</tr>
		<tr class='TableData'>
			<td class='TableData'>
				<b>备注：</b>
			</td>
			<td colspan="3" class='TableData'>
				<div  id="remark" style="width:425px;height:100px" ></div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>