<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String frpSid = request.getParameter("frpSid");
	String itemId = request.getParameter("itemId");
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<title>快速数据回填</title>
</head>

<body onload="" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"常用语",url:contextPath+"/system/core/workflow/flowrun/prcs/commonwords.jsp?itemId=<%=itemId %>"},
                               {title:"历史数据",url:contextPath+"/system/core/workflow/flowrun/prcs/historydatas.jsp?frpSid=<%=frpSid %>&itemId=<%=itemId %>"}
                               ]); 

</script>
</html>