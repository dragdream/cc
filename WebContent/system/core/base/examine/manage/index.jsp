<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);//考核任务Id
	int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<title>考核</title>
</head>
  <frameset rows="*"  cols="200,*" frameborder="0" border="0" framespacing="0" id="frame2"  scrolling="no">
       <frame name="left" src="<%=contextPath%>/system/core/base/examine/manage/left.jsp?taskId=<%=taskId %>&groupId=<%=groupId %>" scrolling="auto" noresize />
       <frame name="center" src="<%=contextPath%>/system/core/base/examine/manage/examine.jsp?taskId=<%=taskId %>&groupId=<%=groupId %>" scrolling="auto" frameborder="NO">
    </frameset>
</html>
