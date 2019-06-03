<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<title>外出详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<script type="text/javascript">

function doInit(){
	 var id = "<%=id%>";
	var url =   "<%=contextPath%>/attendEvectionManage/getById.action";
	var para = {sid:id};
	var jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtState){
		var prc = jsonObj.rtData;
		bindJsonObj2Cntrl(prc);
		if(prc.days!=null&&prc.days!="null"&&prc.days!=""){
			$("#days").html(prc.days+" 天");
		}
		
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	} 
	
}	
</script>
</head>
<body onload="doInit();" style="background:#f4f4f4;">

	<table class="TableBlock" style="width:100%;font-size:12px">

		<tr>
			<td nowrap class="TableData" width="100">出差时间：</td>
			<td class="TableData">
			
				
				  <span id="startTimeStr"></span>
				 至
				 <span id="endTimeStr"></span>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">出差天数：</td>
			<td class="TableData" id="days">
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">出差地址：</td>
			<td class="TableData" id="evectionAddress">
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">出差原因：</td>
			<td class="TableData" id="evectionDesc">
		</tr>
		<tr class="TableData">
			<td nowrap>审批人：</td>
			<td nowrap align="left" id="leaderName"></td>
		</tr>
		
		<tr class="TableData" style="border-bottom:none;">
			<td nowrap>不批准原因：</td>
			<td nowrap align="left" id="reason"></td>
		</tr>
		
	</table>				 
</body>
</html>
