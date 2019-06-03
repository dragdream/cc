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
	var url = contextPath+"/TeeHumanCertController/getModelById.action?sid="+sid;
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
				<b>证照编号：</b>
			</td>
			<td>
				<div id="certCode"></div>
			</td>
			<td>
				<b>证照类型：</b>
			</td>
			<td>
				<div id="certTypeDesc" >
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<b>证照名称：</b>
			</td>
			<td>
				<div id="certName"></div>
			</td>
			<td>
				<b>取证日期：</b>
			</td>
			<td>
				<div id='getTimeDesc' ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>生效日期：</b>
			</td>
			<td>
				<div  id='validTimeDesc' ></div>
			</td>
			<td>
				<b>结束日期：</b>
			</td>
			<td>
				<div id='endTimeDesc'></div>
			</td>
		</tr>
			<tr>
			<td>
				<b>证件属性：</b>
			</td>
			<td colspan="3">
				<div  id="certAttrDesc">
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<b>发证机构：</b>
			</td>
			<td colspan="3">
				<div  id="certOrg" ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<div  id="remark" style="width:425px;height:100px"  ></div>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>