<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    //校验资源权限 参数1:菜单资源权限编码  ;；  参数2：是否检查 true-需要检查 false-不需要检查  ; 参数5：扩展字段
    //TeeResPrivServlet.checkResPriv("001001", true ,request ,response ,"");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>用户管理</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
</head>
<body topmargin="7">
<table border="0" style="padding-left:10px" width="100%"  cellspacing="0" cellpadding="3" class="">
  <tr >
    <td class="Big3" ><span class="big3">&nbsp;用户管理</span>
    </td>
    
    </tr>
</table>
</body>
</html>