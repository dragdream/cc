<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<title >日程安排查询</title>
<script type="text/javascript">
/**
 * 读取cookie
 */
function getCookie(){
  var arr = document.cookie.match(new RegExp("(^| )calendarQueryType=([^;]*)(;|$)"));
  if (arr != null){
    return unescape(arr[2]);
  }
  else{
    return null;
  }
}
function doOnload(){
  var calendarQueryType = getCookie();
  if(calendarQueryType=="day"){
    window.location.href = "<%=contextPath %>/system/core/base/calendar/leader/day.jsp";
  }else if(calendarQueryType=="week"){
    window.location.href = "<%=contextPath %>/system/core/base/calendar/leader/week.jsp";
  }else if(calendarQueryType=="month"){
    window.location.href = "<%=contextPath %>/system/core/base/calendar/leader/month.jsp";
  }else{
	  window.location.href = "<%=contextPath %>/system/core/base/calendar/leader/week.jsp";
  }
}
</script>
</head>
<body topmargin="5"  onload="doOnload();" style="padding-top:5px;">
</body>
</html>
