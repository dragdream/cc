<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>

<script>

function doInit(){
	
// 	$.addTab("tab","tab-content",{url:contextPath+"/system/core/base/calendar/manage.jsp",title:"日程安排",active:true});
// 	$.addTab("tab","tab-content",{url:contextPath+"/system/core/base/calendar/affair/index.jsp",title:"周期性事务",active:false});
// 	$.addTab("tab","tab-content",{url:contextPath+"/system/core/base/calendar/importExtport.jsp",title:"导入/导出",active:false});

    $.addTab("tab","tab-content",[{title:"日程安排",url:contextPath+"/system/core/base/calendar/manage.jsp"},
                              {title:"周期性事务",url:contextPath+"/system/core/base/calendar/affair/index.jsp"},
                              {title:"导入/导出",url:contextPath+"/system/core/base/calendar/importExtport.jsp"},
                              ]); 

	//easyuiTools.addTab({src:contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/ext.jsp",title:"扩展功能",closable:false},$("#tabDiv"));
}

</script>

</head>
<body onload="doInit()"  style="overflow: hidden;padding-left: 10px;padding-right: 10px;padding-top: 5px">
<div class="titlebar clearfix">
		<img class = 'tit_img' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/grbg/rcap/schedule_arrange.png">
		<p class="title">日程安排</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right: 10px"></div>

</body>
</html>

