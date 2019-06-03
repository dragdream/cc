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
	var url = contextPath+"/TeeHumanSocialController/getModelById.action?sid="+sid;
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
				<b>成员名称：</b>
			</td>
			<td>
				<div   id="memberName" ></div>
			</td>
			<td>
				<b>与本人关系：</b>
			</td>
			<td>
				<div   id="relationDesc"  >
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<b>出生日期：</b>
			</td>
			<td>
				<div  id='birthdayDesc'   ></div>
			</td>
			<td>
				<b>政治面貌：</b>
			</td>
			<td>
				<div id="policyDesc"   >
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<b>职业：</b>
			</td>
			<td>
				<div   id="occupation"  ></div>
			</td>
			<td>
				<b>担任职务：</b>
			</td>
			<td>
				<div  id="post" ></div>
			</td>
		</tr>
			<tr>
			<td>
				<b>联系电话（个人）：</b>
			</td>
			<td>
				<div  id="telNoPersonal" ></div>
			</td>
			<td>
				<b>联系电话（单位）：</b>
			</td>
			<td>
				<div  id="telNoCompany" ></div>
			</td>
		</tr>
			<tr>
			<td>
				<b>工作单位：</b>
			</td>
			<td colspan="3">
				<div id="workAt"  ></div>
			</td>
		</tr>
			<tr>
			<td>
				<b>单位地址：</b>
			</td>
			<td colspan="3">
				<div   id="workAddress"   ></div>
			</td>
		</tr>
			<tr>
			<td>
				<b>家庭住址：</b>
			</td>
			<td colspan="3">
				<div id="homeAddress"  ></div>
			</td>
		</tr>
		<tr>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<div id="remark" style="width:425px;height:100px" ></div>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>