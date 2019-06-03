<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   //流程主键
   int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>流程预归档</title>
<script type="text/javascript">
var runId=<%=runId  %>;
var hasBd=0;//表單
var hasZw=0;//正文
var hasBszw=0;//版式正文
var hasFj=0;//公共附件
var hasQpd=0;//签批单


function save(){
	
	if ($('#bd').attr('checked')) {
		hasBd=1;
	}else{
		hasBd=0;
	}
	
	if ($('#qpd').attr('checked')) {
		hasQpd=1;
	}else{
		hasQpd=0;
	}
	
	
	if ($('#zw').attr('checked')) {
		hasZw=1;
	}else{
		hasZw=0;
	}
	if ($('#bszw').attr('checked')) {
		hasBszw=1;
	}else{
		hasBszw=0;
	}
	if ($('#ggfj').attr('checked')) {
		hasFj=1;
	}else{
		hasFj=0;
	}
		
	if(check()){
		var url=contextPath+"/TeeDamFilesController/workFlowArchive.action";
		var json=tools.requestJsonRs(url,{hasQpd:hasQpd,hasBd:hasBd,hasZw:hasZw,hasBszw:hasBszw,hasFj:hasFj,runId:runId});
		
		return json;
	}
}


//验证
function check(){
	if(hasQpd==0&&hasBd==0&&hasZw==0&&hasBszw==0&&hasFj==0){
		$.MsgBox.Alert_auto("请选择将要归档的信息！");
		return false;
	}
	return true;
}


</script>
</head>

<body style="font-size: 12px;background-color: #f2f2f2">
    <form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" >
		<tr>
			<td nowrap class="TableData"  style="width: 100px;text-indent: 10px;">存档信息：</td>
			<td class="TableData">
				<input type="checkbox" name="bd" id="bd" >表单&nbsp;&nbsp;&nbsp;
				<input type="checkbox" name="qpd" id="qpd" >签批单&nbsp;&nbsp;&nbsp;
				<input type="checkbox" name="zw" id="zw" >正文&nbsp;&nbsp;&nbsp;
				<input type="checkbox" name="bszw" id="bszw" >版式正文&nbsp;&nbsp;&nbsp;
				<input type="checkbox" name="ggfj" id="ggfj" >公共附件
			</td>
		</tr>
	</table>
</form>
</body>
</html>