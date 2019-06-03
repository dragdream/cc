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
	var url = contextPath+"/TeeHumanTrainController/getModelById.action?sid="+sid;
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
	<table  style="width:100%;font-size:12px">
		<tr>
			<td>
				<b>培训主题：</b>
			</td>
			<td>
				<div  id="traSubject" ></div>
			</td>
			<td>
				<b>培训费用：</b>
			</td>
			<td>
				<div id="traPays" ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>开始日期：</b>
			</td>
			<td>
				<div id='startTimeDesc' ></div>
			</td>
			<td>
				<b>结束日期：</b>
			</td>
			<td>
				<div id='endTimeDesc' ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>培训类型：</b>
			</td>
			<td>
				<div  id="traType" ></div>
			</td>
			<td>
				<b>培训证书：</b>
			</td>
			<td>
				<div id="traCert" ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>培训地点：</b>
			</td>
			<td colspan="3">
				<div  id="traPosition" ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>培训内容：</b>
			</td>
			<td colspan="3">
				<div id="traContent" style="width:425px;height:100px" ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<div  id="remark" style="width:425px;height:100px" ></div>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>