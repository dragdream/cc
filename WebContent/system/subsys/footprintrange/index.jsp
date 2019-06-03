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
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/subsys/footprintrange/imgs/icon_dzwl.png">
		<p class="title">电子围栏</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	
</body>
<script>
 $.addTab("tab","tab-content",[{title:"基础设置",url:contextPath+"/system/subsys/footprintrange/setting.jsp"},
                              {title:"围栏监控",url:contextPath+"/system/subsys/footprintrange/monitor.jsp"},
                              ]); 

</script>
</html>