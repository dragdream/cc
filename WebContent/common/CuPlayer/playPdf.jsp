<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>在线PDF预览</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="shortcut icon" href="/favicon.ico" >
<style type="text/css">
</style>
</head>

<body>
<embed src="<%=request.getContextPath()%>/attachmentController/downFile.action?id=<%=request.getParameter("id")%>"> </embed>
<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="800" height="600" border="0"> 
<param name="SRC" value="">
</object> 
</body>
</html>

