<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//所属流程  用来区分菜单定义指南
	/* int flowId = TeeStringUtil.getInteger(
			request.getParameter("flowId"), 0); */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/inspection/inspModule/js/inspection_manage.js"></script>


<script type="text/javascript">
function advancedQueryBtn(btn){
	$.each($("#Tbl tr"), function(i){ 
	    if(i > 4){ 
	        this.style.display = 'none'; 
	    } 
	});
}
$(document).ready(function(e) {
    $(".but").click(function(e) {
        $(".abc").toggle();
    });
});
</script>

</head>
<body onload="doInit()" style="overflow: hidden; font-size: 12px；; padding-left: 10px; padding-right: 10px; padding-top: 5px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="titlebar clearfix">
	<div id="outwarp">
		<div class="fl left">
			<img id="img1" class='title_img'
				src="<%=contextPath%>/common/zt_webframe/imgs/gzl/gzcx/icon_gongzuochaxun.png"/>
			<span class="title">检查模块维护</span>
		</div>
		<div class="fr" id="button_div">
			<button class="easyui-linkbutton" onclick="add()"><i class="fa fa-plus"></i>&nbsp;新增</button>
<!-- 			<button class="easyui-linkbutton" onclick="deletes()"><i class="fa fa-pencil"></i>&nbsp;删除</button> -->
		</div>
		</div>
		<span class="basic_border"></span> 
		<div class="" style="padding-top: 5px; padding-bottom: 5px">
		<div>
			模块名称：<input type="text" id='moduleName' name='moduleName' class="easyui-textbox"  /> 
			<button  class="easyui-linkbutton" onclick="doInit()" style="text-align:right"><i class="fa fa-search"></i>查询</button>
			</div>
		</div>
	</div>
	
</body>
</html>