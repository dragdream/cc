<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String channelId = request.getParameter("channelId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script>
var datagrid ;
var channelId = <%=channelId%>;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/cmsDocument/datagridTrash.action?channelId='+channelId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageList: [30,40,50,60,70,80,90,100],
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'docTitle',title:'文档标题',width:200},
			{field:'crTime',title:'创建时间',width:100,formatter:function(data,rowData){
				return getFormatDateStr(rowData.crTime,"yyyy-MM-dd HH:mm:ss");
			}},
			{field:'crUserName',title:'创建人',width:100},
			{field:'chnlName',title:'所在栏目',width:100},
			{field:'status',title:'状态',width:100,formatter:function(data,rowData){
				var render = "";
				switch(data){
				case 1:
					render = "新稿";
					break;
				case 2:
					render = "待审";
					break;
				case 3:
					render = "待发";
					break;
				case 4:
					render = "已发";
					break;
				case 5:
					render = "已拒";
					break;
				}
				return render;
			}}
		]]
	});
}

function del(){
	var rows = datagrid.datagrid('getSelections');
	if(rows.length==0){
		$.MsgBox.Alert_auto("请选择至少一篇文档！");
		return;
	}
	
	 $.MsgBox.Confirm ("提示", "是否要将所选文档彻底删除？", function(){
		 for(var i=0;i<rows.length;i++){
			tools.requestJsonRs(contextPath+"/cmsDocument/deleteDocumentByDocId.action",{docId:rows[i].docId});
	     }
		 $.MsgBox.Alert_auto("删除成功！");
		 datagrid.datagrid("reload");   
	  });
}

function recycle(){
	var rows = datagrid.datagrid('getSelections');
	if(rows.length==0){
		$.MsgBox.Alert_auto("请选择至少一篇文档！");
		return;
	}
	
	 $.MsgBox.Confirm ("提示", "是否要将所选文档还原？", function(){
		 for(var i=0;i<rows.length;i++){
			tools.requestJsonRs(contextPath+"/cmsDocument/recycle.action",{docId:rows[i].docId});
		 }
		    $.MsgBox.Alert_auto("还原成功 ！");
			datagrid.datagrid("reload");
	  });
}

function clearTrash(){
	$.MsgBox.Confirm ("提示", "是否要清空所有文档吗？", function(){
		tools.requestJsonRs(contextPath+"/cmsDocument/clearTrash.action",{channelId:channelId});
		$.MsgBox.Alert_auto("清空成功！");
		datagrid.datagrid("reload");
	  });
}

</script>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class="topbar clearfix">
	<div class="fl left clearfix">
		<button class="btn-win-white" onclick="window.location='documents.jsp?channelId=<%=channelId%>'">返回</button>
		<button class="btn-win-white" onclick="recycle()">还原</button>
		<button class="btn-del-red" onclick="del()">删除</button>
		<button class="btn-del-red" onclick="clearTrash()">清空回收站</button>
	</div>
</div>
</body>
</html>