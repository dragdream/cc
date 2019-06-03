<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<script type="text/javascript" src="script.js"></script>
<style>
</style>
<script>
var datagrid;
function doInit(){
	
}

$(function() {
	datagrid = $('#datagrid').datagrid({
		url : contextPath + '/bisConfig/datagrid.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',
		title : '',
		iconCls : 'icon-save',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
// 		queryParams:para,
		fitColumns : true,
		nowrap : true,
		border : false,
		idField : 'sid',
		sortOrder: 'desc',
		striped: false,
		singleSelect:true,
		remoteSort: true,
		toolbar: '#toolbar',
		columns : [ [ 
		{
			field : 'type',
			title : '配置标识ID',
			width : 100,
			sortable : true
		},
		 {
			field : 'typeDesc',
			title : '配置描述',
			width : 100,
			sortable : true
		},
		{
			field : 'status',
			title : '状态',
			width : 100,
			sortable : true,
			formatter:function(value,rowData,rowIndex){
				var status = rowData.status;
				var render = "";
				switch(status)
				{
				case 0:
					render+="<span style='color:red'>关闭</span>&nbsp;&nbsp;";
					break;
				case 1:
					render+="<span style='color:green'>启用</span>";
					break;
				}
				return render;
			}
		},
		{
			field : '_charger',
			title : '操作',
			width : 100,
			formatter:function(value,rowData,rowIndex){
				return "<a href='javascript:void(0)' onclick='setStatus("+rowData.type+",1)'>启用</a>&nbsp;<a href='javascript:void(0)' onclick='setStatus("+rowData.type+",0)'>关闭</a>";
			}
		}
		] ]
	});
	
});

function setStatus(type,status){
	tools.requestJsonRs(contextPath+"/bisConfig/setStatus.action",{type:type,status:status});
	$.MsgBox.Alert_auto("操作成功！",function(){
		window.location.reload();
	});
	
}

</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">

<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/bisengine/img/icon_yqkg.png">
		<span class="title">引擎开关</span>
	</div>
 </div>
</body>
</html>