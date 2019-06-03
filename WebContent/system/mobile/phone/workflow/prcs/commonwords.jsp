<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.oa.core.org.bean.TeeDepartment" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<%@ include file="/system/mobile/mui/header.jsp" %>
<%
	String itemId = TeeStringUtil.getString(request.getParameter("itemId"));
%>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script> 
<title></title>
</head>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="doSelect()">
		    <span class="mui-icon mui-icon-checkmarkempty"></span>确定
		</button>
		<h1 class="mui-title" >常用语选择</h1>
	</header>
	<div class="mui-content" id="content">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="dataList">
				
			</ul>
		</div>
	</div>
<style>
.selected{
	background:#428bca;
	color:white;
}
.item{
	font-size:14px;
}
</style>

<script>
var itemId = "<%=itemId%>";
var data = "";

function doInit(){
	var json = tools.requestJsonRs(contextPath + '/CommonWord/testDatagrid.action',{page:1,rows:100000});
	var rows = json.rows;
	for(var i=0;i<rows.length;i++){
		//渲染node
		var render = [];
		render.push("<li class=\"mui-table-view-cell mui-media\" onclick=\"clickIt(this,'"+rows[i].cyy+"')\" >");
		render.push("<div class=\"mui-media-body\">");
		render.push(rows[i].cyy);
		render.push("</div>");
		render.push("</li>");
		$("#dataList").append(render.join(""));
	}
}



function clickIt(obj,cyy){
	if($(obj).attr("class").indexOf("selected")!=-1){//如果是已被选中的，则取消选中
		$(obj).removeClass("selected");
		data = "";
	}else{//如果未被选中，则开启选中
		cancelAll();
		$(obj).addClass("selected");
		data = cyy;
	}
}

function doSelect(){
	window.top.userSelectDiv.style.display = "none";
	window.top.$("#"+itemId).val(data);
}


/**
 * 取消选择所有项
 */
function cancelAll(){
	$(".selected").each(function(i,obj){
		$(obj).removeClass("selected");
	});
}

</script>
</body>
</html>