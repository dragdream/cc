<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<title>申诉审批</title>
</head>
<body onload="" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/core/base/attend/img/icon_sxsp.png">
		<p class="title">申诉审批</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"待审批",url:contextPath+"/system/core/base/attend/complain/dsp.jsp"},
                              {title:"已批准",url:contextPath+"/system/core/base/attend/complain/ypz.jsp"},
                              {title:"未批准",url:contextPath+"/system/core/base/attend/complain/wpz.jsp"},
                              ]); 

</script>
</html>