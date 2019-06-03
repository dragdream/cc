<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>视图导入</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<style>
</style>
<script>
function doImport(obj){
	if(document.getElementById("file").value.indexOf(".xml")==-1){
		$.MsgBox.Alert_auto("仅能上传xml后缀名模板文件！");
		return false;
	}
	$("#uploadBtn").attr("value","上传中").attr("disabled","");
	return true;
}


function upload(){
	$("#uploadForm").submit();
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">

<form id="uploadForm" onsubmit="return doImport()" target="frame0" action="<%=contextPath %>/bisView/import.action" name="uploadForm" method="post" enctype="multipart/form-data">
	<table class="TableBlock" style="width:100%;" >
		
		<tr>
			<td class="TableData" style="">
			  <span style="color:red">1.导入的格式为*.xml，且必须是兼容本系统的视图文件格式。</span><br/>
			  <input style="border:0px" type="file" name="file" id="file"/>
			</td>
		</tr>
		
		
	</table>
</form>
</body>
</html>