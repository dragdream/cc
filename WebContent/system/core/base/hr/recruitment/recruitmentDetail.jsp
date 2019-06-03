<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//会议申请Id

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<title>招聘录用详细信息</title>
<script type="text/javascript">

function doInit(){
	getInfoById("<%=sid%>");
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/recruitmentController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}


</script>
</head>
<body onload="doInit();">
<table align="center" width="96%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="15%;" >计划名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" id="planName"  >
		</td>
		<td nowrap class="TableData"  width="15%;" >应聘者姓名：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" id="hrPoolName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >招聘岗位：</td>
		<td class="TableData" width="35%;" id="position">
		</td>
		<td nowrap class="TableData"  width="15%;" >OA中用户名 ：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" id="oaName" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">录用负责人：</td>
		<td class="TableData" width="" id="recordPersonName">
		</td>
		<td nowrap class="TableData"  width="" >录入日期：</td>
		<td class="TableData" id="recordTimeStr" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="" >招聘部门：</td>
		<td class="TableData"  colspan="3" name="deptName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >员工类型：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;"  id="employeeTypeName" >
		</td>
		<td nowrap class="TableData"  width="15%;" >行政等级 ：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" id="administrationLevel">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >职务：</td>
		<td class="TableData" width="35%;" id="jobPosition">
		</td>
		<td nowrap class="TableData"  width="15%;" >职称：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" id="presentPositionName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >正式入职时间：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" id="onBoardingTimeStr" >
		</td>
		<td nowrap class="TableData"  width="15%;" >正式起薪时间：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" id="startingSalaryTimeStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">备　　注：</td>
		<td class="TableData" width="" colspan="3" id="remark">
		</td>
	</tr>
	<tr>
</table>
		
</body>
</html>