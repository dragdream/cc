<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">

</style>

</head>
<body onload="" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/common/zt_webframe/imgs/grbg/grkq/sun.png">
		<p class="title">个人考勤</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"外出管理",url:contextPath+"/system/core/base/attend/out/manager.jsp"},
                              {title:"请假管理",url:contextPath+"/system/core/base/attend/leave/manager.jsp"},
                              {title:"出差管理",url:contextPath+"/system/core/base/attend/evection/manager.jsp"},
                              {title:"加班管理",url:contextPath+"/system/core/base/attend/overtime/manager.jsp"},
                              {title:"上下班登记",url:contextPath+"/system/core/base/attend/duty/index.jsp"},
                              {title:"上下班记录",url:contextPath+"/system/core/base/attend/duty/query.jsp"},
                              ]); 

</script>
</html>