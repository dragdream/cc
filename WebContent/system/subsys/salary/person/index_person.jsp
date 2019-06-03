<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员管理</title>
</head>
  <frameset rows="*"  cols="260,*" frameborder="0" border="0" framespacing="0" id="frame2"  scrolling="no">
       <frame name="personListTree" src="<%=contextPath%>/system/subsys/salary/person/orgTree.jsp" scrolling="auto" noresize />
       <frame name="personinput" src="<%=contextPath%>/system/subsys/salary/person/query.jsp" scrolling="auto" frameborder="NO">
    </frameset>
</html>

