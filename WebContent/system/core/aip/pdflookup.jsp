<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>

<% 
	String id = request.getParameter("id");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script>

function doInit(){
	
}

</script>

</head>
<body onload="doInit()" style="margin:0px;overflow:hidden">
<div style="position:absolute;left:0px;right:0px;top:0px;bottom:0px">
	<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0"><!--IE--> 
      <param name="_Version" value="65539"> 
      <param name="_ExtentX" value="20108"> 
      <param name="_ExtentY" value="10866"> 
      <param name="_StockProps" value="0"> 
      <param name="src" value="<%="/attachmentController/downFile4Pdf.action?id="+id %>">
      <span style="color:red">请下载PDF查看器插件</span>
	</object> 
</div>
</body>
</html>