<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<title>三员日志管理</title>
</head>
<body onload="" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px;"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"登录访问日志",url:contextPath+"/system/core/sysLog/threePartManange.jsp?type=1"},
                              {title:"访问实时日志",url:contextPath+"/system/core/sysLog/threePartManange.jsp?type=2"},
                              {title:"异常日志",url:contextPath+"/system/core/sysLog/threePartManange.jsp?type=3"},
                              {title:"管理员访问日志",url:contextPath+"/system/core/sysLog/threePartManange.jsp?type=4"},
                              {title:"管理员登录日志",url:contextPath+"/system/core/sysLog/threePartManange.jsp?type=5"},
                              {title:"管理员操作日志",url:contextPath+"/system/core/sysLog/threePartManange.jsp?type=6"},
                              ]); 
$("#tab-content").css("top","35px");
</script>
</html>