<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	String itemId = request.getParameter("itemId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script>
var itemId = "<%=itemId%>";

function doInit(){
	$('#datagrid').datagrid({
		url:contextPath+'/mobileSeal/myMobileSealsWithData.action',
		pagination:true,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sealName',title:'签章名称',width:150},
			{field:'userName',title:'签章使用人',width:150},
			{field:'deviceNo',title:'绑定设备号',width:150},
			{field:'sealData',title:'签章预览',width:150,formatter:function(value,rowData,rowIndex){
				return "<img src=\""+value+"\" style='height:50px;cursor:pointer' onclick=\"sealView("+rowIndex+")\"/>";
			}},
			{field:'_manage',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
				var render = [];
				render.push("<a style='cursor:pointer' onclick=\"$('#enterPwd').modal('show');window.rowIndex="+rowIndex+";\">盖章</a>");
				return render.join("&nbsp;/&nbsp;");
			}}
		]]
	});
}


function addSeal(){
	var rows = $('#datagrid').datagrid('getRows');
	var data = rows[window.rowIndex];
	if($("#pwd").val()!=data.pwd){
		alert("密码错误，请重新输入！");
		return;
	}
	
	xparent.doAddMobileSealCtrl(itemId,data.sealData);
	CloseWindow();
}


function sealView(rowIndex){
	var rows = $('#datagrid').datagrid('getRows');
	$("#sealImg").attr("src",rows[rowIndex]["sealData"]);
	$("#sealViewDiv").modal("show");
}

</script>
</head>
<body onload="doInit()" >
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span class="easyui_h1">移动签章</span>
	</div>
	<br/>
</div>
<table id="datagrid" fit="true"></table>


<div class="modal fade" id="sealViewDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">签章预览</h4>
			</div>
			<div class="modal-body" style="text-align:Center">
				<img id="sealImg" />
			</div>
			<div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		    </div>
		</div>
	</div>
</div>

<div class="modal fade" id="enterPwd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">签章确认</h4>
			</div>
			<div class="modal-body" style="text-align:Center">
				请输入签章密码：<input type="password" id="pwd" name="pwd" class="BigInput"/>
			</div>
			<div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		        <button type="button" class="btn btn-primary" onclick="addSeal()">确定</button>
		    </div>
		</div>
	</div>
</div>
</body>
</html>