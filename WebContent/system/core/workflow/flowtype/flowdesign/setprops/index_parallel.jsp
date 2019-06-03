<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<script>
var prcsId = <%=prcsId%>;
var flowId = <%=flowId%>;

function doInit(){
	$("#layout").layout({auto:true});
	//$.addTab("tabs","tabs-content",{title:"基本信息",url:contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/basic.jsp?prcsId="+prcsId+"&flowId="+flowId,active:true});
	$("#group").group();
	changePage(1);
}

function changePage(sel){
	$("iframe").hide();
	var iframe = $("#frame"+sel).show();
	if(sel==1){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/basic.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}else if(sel==2){
		if(!iframe.attr("src")){
			iframe.attr("src",contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/condition.jsp?prcsId="+prcsId+"&flowId="+flowId);
		}
	}
}

function commit(){
	$("iframe").each(function(i,obj){
		if($(obj).attr("src")){
			obj.contentWindow.commit();
		}
	});
	alert("保存成功");
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden">
<div id="layout">
<div layout="west" width="170">
	<div id="group" class="list-group">
	  <a href="#" class="list-group-item active" onclick="changePage(1)">
	  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	基本信息
	  </a>
	  <a href="#" class="list-group-item" onclick="changePage(2)">
	  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
	  	转交条件
	  </a>
	</div>
</div>
<div layout="center" style="padding-left:10px;">
	<iframe id="frame1" frameborder=0 style="width:100%;height:100%"></iframe>
	<iframe id="frame2" frameborder=0 style="width:100%;height:100%;display:none"></iframe>
</div>
</div>
</body>
</html>