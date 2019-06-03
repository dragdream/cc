<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String model = TeeAttachmentModelKeys.CRM_AFTER_SALE_SERV;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后服务详情</title>
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
	var url =   "<%=contextPath%>/crmAfterSaleServController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			if(prc.handleStatus){
				$("#handleStatus").html(handleStatusFunc(prc.handleStatus));
			}
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#fileContainer").append(fileItem);
			}
		}
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
		<td class="" colspan="4" style="text-align: left;background:#E0EBF9; "><b >售后服务</b></td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >服务编号：<font style='color:red'>*</font></td>
		<td class="TableData" width="70%;"  colspan="3" id="serviceCode">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="15%;" >客户名称：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;" id="customerInfoName">
		</td>
		<td nowrap class="TableData" width="10%;"  >客户联系人：</td>
		<td class="TableData" id="contactUserName" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >售后服务类型 ：</td>
		<td class="TableData" id="serviceTypeName" >
		</td>
		<td nowrap class="TableData"  width="">紧急程度：</td>
		<td class="TableData" width="" id="emergencyDegree">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >受理人：</td>
		<td class="TableData" id="accteptUserName">
		</td>
		<td nowrap class="TableData"  width="">受理时间：</td>
		<td class="TableData" id="acceptDatetimeStr">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"   >附件：</td>
		<td class="TableData"  colspan="3">
			<div id="fileContainer"></div> 
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">问题描述：</td>
		<td class="TableData" width="" colspan="3" id="questionDesc">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">创建时间：</td>
		<td class="TableData" width="" colspan="3" id="createTimeStr">
		</td>
	</tr>
	<tr class="TableHeader">
		<td class="" colspan="4" style="text-align: left;background:#E0EBF9; "><b>处理信息</b></td>
	</tr>
	<tr >
		<td nowrap class="TableData"   >处理人：</td>
		<td class="TableData"  id="handleUserName">
		</td>
		<td nowrap class="TableData"   >处理时间：</td>
		<td class="TableData" id="handleDatetimeStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">是否完成：</td>
		<td class="TableData" width="" colspan="3" id="handleStatus">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">处理结果：</td>
		<td class="TableData" width="" colspan="3" id="handleDesc">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">反馈结果：</td>
		<td class="TableData" width="" colspan="3" id="feedback">
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