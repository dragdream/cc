<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	//String model = TeeAttachmentModelKeys.CRM_AFTER_SALE_SERV;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售机会详情</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/afterSaleService/js/saleService.js"></script>


<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	if(sid>0){
		getInfoById(sid);
	}
}


/* 查看详情 */
function getInfoById(id){
	
	var url ="<%=contextPath%>/crmChanceController/getChanceById.action";
	var para = {sid :id};
	
	var jsonObj = tools.requestJsonRs(url, para);
	
	//alert(jsonObj);
	if (jsonObj.rtState) {	
		var chance = jsonObj.rtData;		
	    bindJsonObj2Cntrl(chance);		
	} else {
		
		alert(jsonObj.rtMsg);
	}
}



function toReturn(){
	history.go(-1);
	//window.location.href = contextPath + "/system/subsys/crm/core/afterSaleService/saleServiceManage.jsp";
}




</script>

</head>
<body onload="doInit();" style="margin-top: 10px;">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="90%" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left;background:#E0EBF9; "><b >销售机会详情</b></td>
	</tr>

	<tr>
		<td nowrap class="TableData" width="15%;" >客户名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;" id="customerName">
		</td>
		<td nowrap class="TableData" width="10%;"  >机会名称：</td>
		<td class="TableData" id="chanceName" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >预计成交日期 ：</td>
		<td class="TableData" id="forcastTimeStr" >
		</td>
		<td nowrap class="TableData"  width="">预计成交金额：</td>
		<td class="TableData" width="" id="forcastCost">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >创建人姓名：</td>
		<td class="TableData" id="crUserName">
		</td>
		<td nowrap class="TableData"  width="">创建时间：</td>
		<td class="TableData" id="createTimeStr">
		</td>
	</tr>
	
	
	<tr align="center">
		<td nowrap class="TableData" colspan=4>
			<input type="button"  value="返回" class="btn btn-primary" onclick="toReturn();"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
<br>








</body>
</html>