<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	String thread_local_archives = TeeStringUtil.getString(request.getParameter("thread_local_archives"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/tabs/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tabs/tabs.js"></script>
<!-- Bootstrap通用UI组件 -->
<script src="<%=contextPath %>/common/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/bootstrap/css/bootstrap.css"/>

<script>
var maxPrcsId=1;
var designer;//获取设计器
var startNode;
var endNode;
var runId = <%=runId%>;
var flowId = <%=flowId%>;
var thread_local_archives = "<%=thread_local_archives%>";
window.onload=function(){
	var url = contextPath+"/flowType/get.action";
	var json = tools.requestJsonRs(url,{flowTypeId:flowId});
	if(json.rtState){
		var data = json.rtData;
		if(data.type==1){//固定流程
			$.addTab("tabs","tabs-content",{title:"图形视图",url:contextPath+"/system/core/workflow/flowrun/flowview/viewgraph.jsp?runId="+runId+"&flowId="+flowId+"&thread_local_archives="+thread_local_archives,active:true});
		}else{//自由流程
			$.addTab("tabs","tabs-content",{title:"图形视图",url:contextPath+"/system/core/workflow/flowrun/flowview/viewgraph_free.jsp?runId="+runId+"&flowId="+flowId+"&thread_local_archives="+thread_local_archives,active:true});
		}
		$.addTab("tabs","tabs-content",{title:"列表视图",url:contextPath+"/system/core/workflow/flowrun/flowview/viewlist.jsp?runId="+runId+"&flowId="+flowId+"&thread_local_archives="+thread_local_archives,active:false});
		$.addTab("tabs","tabs-content",{title:"流程日志",url:contextPath+"/system/core/workflow/flowrun/flowview/runlog.jsp?runId="+runId+"&flowId="+flowId+"&thread_local_archives="+thread_local_archives,active:false});

	}else{
		messageMsg("未找到指定流程类型","layout","error");
		return;
	}
	
	
}
</script>
</head>
<body style="overflow:hidden">
<div id="tabs" class="base_layout_top"></div>
<div id="tabs-content" class="base_layout_center"></div>
</body>
</html>