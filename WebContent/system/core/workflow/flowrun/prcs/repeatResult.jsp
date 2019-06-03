<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String resultFlag=request.getParameter("resultFlag");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件转存结果</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>

</head>
<body>

<table align="center" class="MessageBox" style="width: 320px;"> 
	<tr>
		<td class="msg info"><div><%="1".equals(resultFlag)? "文件转存成功！":"文件转存失败，可能源文件不存在！" %></div> </td> 
	</tr> 
</table>
<div class="" align="center">
	<input type="button"  value="关闭" class="btn btn-primary" onClick="CloseWindow();">
</div>

</body>
</html>