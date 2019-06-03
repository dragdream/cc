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
	var url = contextPath+"/TeeHumanEducationController/getModelById.action?sid="+sid;
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
				<b>所学专业：</b>
			</td>
			<td>
				<div  id="eduMajor"  ></div>
			</td>
			<td>
				<b>所获学历：</b>
			</td>
			<td>
				<div id="eduProject"  ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>开始日期：</b>
			</td>
			<td>
				<div   id='startTimeDesc'   ></div>
			</td>
			<td>
				<b>结束日期：</b>
			</td>
			<td>
				<div  id='endTimeDesc'   ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>学位：</b>
			</td>
			<td>
				<div  id="eduDegree"  ></div>
			</td>
			<td>
				<b>曾任班干：</b>
			</td>
			<td>
				<div   id="eduLeader" ></div>
			</td>
		</tr>
			<tr>
			<td>
				<b>证明人：</b>
			</td>
			<td colspan="3">
				<div   id="eduProver"   ></div>
			</td>
		</tr>
			<tr>
			<td>
				<b>所在院校：</b>
			</td>
			<td colspan="3">
				<div id="eduSchool" ></div>
			</td>
		</tr>
			<tr>
			<td>
				<b>院校所在地：</b>
			</td>
			<td colspan="3">
				<div  id="eduSchoolPos"  ></div>
			</td>
		</tr>
			<tr>
			<td>
				<b>院校联系方式：</b>
			</td>
			<td colspan="3">
				<div id="eduSchoolContact"  ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<div  id="remark"  style="width:425px;height:100px" ></div>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>