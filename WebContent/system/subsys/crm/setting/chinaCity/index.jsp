<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>

<script>

function doInit(){
	
	$.addTab("tabs","tabs-content",{url:contextPath+"/system/subsys/crm/setting/chinaCity/provinceManage.jsp",title:"省（城市）管理",active:true});
// 	$.addTab("tabs","tabs-content",{url:contextPath+"/system/subsys/crm/setting/chinaCity/chinaCityResult.jsp",title:"省（城市）级联显示",active:false});
	//easyuiTools.addTab({src:contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/ext.jsp",title:"扩展功能",closable:false},$("#tabDiv"));
}

</script>

</head>
<body onload="doInit()"  style="margin-top:5px;overflow:hidden">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>

</body>
</html>