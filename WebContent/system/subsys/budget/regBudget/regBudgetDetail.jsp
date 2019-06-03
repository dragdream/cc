<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String uuid = TeeStringUtil.getString(request.getParameter("uuid"), "");
	String queryOptFlag = TeeStringUtil.getString(request.getParameter("queryOptFlag"), "0");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请预算详情</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/budget/regBudget/js/regBudget.js"></script>
<%@ include file="/header/ztree.jsp" %>
<script type="text/javascript">
var uuid = "<%=uuid%>";
function doInit(){
	getSysCodeByParentCodeNo("BUDGET_REG_REASON" , "reason");
	if(uuid){
		getInfoById(uuid);
	}
}

/**
 * 查看详情 
 */
function getInfoById(uuid){
	var url = "<%=contextPath%>/budgetRegController/getInfoById.action";
	var para = {uuid : uuid};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
			//regTypeFunc(prc.regType);
			$("#regType").html(regTypeNameFunc(prc.regType));
			$("#type").html(typeFunc(prc.type));
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}



function regTypeFunc(str){
	if(str =='1'){
		$("#deptTr").hide();
		$("#personTr").show();
	}else {
		$("#personTr").hide();
		$("#deptTr").show();
	}
}

/**
 * 返回
 */
function toReturn(queryOptFlag){
	if(queryOptFlag ==1){
		window.location.href = contextPath + "/system/subsys/budget/regBudget/regBudgetManage.jsp";
	}else{
		window.location.href = contextPath + "/system/subsys/budget/regBudget/regBudgetManage.jsp";
	}
	
}

</script>
</head>
<body onload="doInit();" style="margin-top: 10px;margin-bottom: 10px;">
<form action=""  method="post" name="form1" id="form1">
<table align="center" width="700px" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left;"><b >申请预算</b></td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >申请类型：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;"  id="regType">
		</td>
		<td nowrap class="TableData"  width="15%;" >申请金额：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;"  id="amount" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >记录类型：</td>
		<td class="TableData" width="30%;"  id="type">
		</td>
		<td nowrap class="TableData"  width="15%;" >申请原由：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;"  id="reasonDesc">
		</td>
	</tr>
	<tr id="personTr" >
		<td nowrap class="TableData"  width="15%;" >人员名称：<font style='color:red'>*</font></td>
		<td class="TableData" colspan="3"  id="opUserName">
		</td>
	</tr>
	<tr id="deptTr"  style="display: ;">
		<td nowrap class="TableData"  width="15%;" >部门名称：<font style='color:red'>*</font></td>
		<td class="TableData" colspan="3"  id="opDeptName">
		</td>
	</tr>
	
	
	<tr >
		<td nowrap class="TableData"  width="15%;" >说明：<font style='color:red'>*</font></td>
		<td class="TableData" colspan="3"  id="remark">
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData" colspan=4>
			<input type="button"  value="返回" class="btn btn-default" onclick="toReturn('<%=queryOptFlag%>');"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>

</form>

</body>
</html>