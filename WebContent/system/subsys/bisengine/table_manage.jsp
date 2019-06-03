<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<title>业务表管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=request.getContextPath() %>/common/js/layout/layout.js"></script>
<script>
function doInit(){
	$("#layout").layout({auto:true});

	//获取分类标记
	var url = contextPath+"/bisCat/datagrid.action?rows=1000&page=1";
	var json = tools.requestJsonRs(url);
	var rows = json.rows;
	var html = [];
	html.push("<a href=\"javascript:void(0)\" class=\"list-group-item \" onclick=\"changePage(0)\">");
	html.push("<i class=\"glyphicon glyphicon-map-marker\"></i>");
	html.push("&nbsp;默认分类");
	html.push("<i class=\"glyphicon glyphicon-chevron-right pull-right\"></i>");
	html.push("</a>");
	for(var i=0;i<rows.length;i++){
		html.push("<a href=\"javascript:void(0)\" class=\"list-group-item \" onclick=\"changePage("+rows[i].sid+")\">");
		html.push("<i class=\"glyphicon glyphicon-map-marker\"></i>");
		html.push("&nbsp;"+rows[i].catName);
		html.push("<i class=\"glyphicon glyphicon-chevron-right pull-right\"></i>");
		html.push("</a>");
	}
	
	$("#group").html(html.join("")).group();
}

function changePage(catId){
	$("#frame0").attr("src","table_list.jsp?catId="+catId);
}
</script>
</head>
<body style="margin:5px;font-size:12px;overflow:hidden" onload="doInit()">
<div id="layout">
	<div layout="west" width="200">
		<div id="group" class="list-group">
		  
		</div>
	</div>
	<div layout="center" style="padding-left:10px">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%" src="common_info.jsp"></iframe>
	</div>
</div>
</body>
</html>
