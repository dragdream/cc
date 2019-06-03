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
<%@ include file="/header/easyui2.0.jsp" %>
<title>外出详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<script type="text/javascript">

function doInit(){
	 var id = "<%=id%>";
	var url =   "<%=contextPath%>/attendOutManage/getById.action";
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
<body onload="doInit();" style="padding:10px 10px 0px 10px;background-color: #f2f2f2" >

	<table class="TableBlock" width="100%" align="center" >
		<tr>
			<td nowrap class="TableData" width="100">外出时间：</td>
			<td class="TableData">
			
				
				  从<span id="startTimeStr"></span>
				 至
				 <span id="endTimeStr"></span>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">外出天数：</td>
			<td class="TableData" id="days"></td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">外出原因：</td>
			<td class="TableData" id="outDesc">
		</tr>
		<tr class="TableData">
			<td nowrap>审批人：</td>
			<td nowrap align="left" id="leaderName"></td>
		</tr>
		<tr class="TableData">
			<td nowrap>申请时间：</td>
			<td nowrap align="left" id="createTimeStr"></td>
		</tr>
		<tr class="TableData">
			<td nowrap>不批准原因：</td>
			<td nowrap align="left" id="reason"></td>
		</tr>
		
	</table>				 
</body>
</html>
