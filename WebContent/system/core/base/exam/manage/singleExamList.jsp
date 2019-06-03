<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var sid='<%=sid%>';
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamInfoController/getSingleExamList.action?sid="+sid,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'examName',title:'考试名称',width:100},
			{field:'ownId',hidden:true,title:'考试人Id',width:100},
			{field:'ownName',title:'参加考试人',width:200},
			{field:'1',title:'所在部门',width:200,formatter:function(e,rowData){
				var deptName = getDeptName(rowData.ownId);
				return deptName;
			}},
			{field:'2',title:'操作',width:300,formatter:function(e,rowData,index){
				return "<a href='#' onclick='check("+index+")'>阅卷</a>";
			}}
		]]
	});
}

function check(index){
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要查阅的内容");
		return;
	}
	var sid = selection.sid;
	var userId = selection.ownId;
	if(isAnswered(userId,sid)){
		var url = contextPath+"/system/core/base/exam/manage/examing.jsp?examInfoId="+sid+"&userId="+userId+"&type=3";
		location.href=url;
	}else{
		$.MsgBox.Alert_auto("当前用户没有考试，或者正在考试中");
	}
}

/**
 * 判断当前考生是否答题了
 */
function isAnswered(userId,sid){
	var flag=false;
	var url =contextPath+"/TeeExamInfoController/isCommited.action?userId="+userId+"&sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		if(json.rtData.isCommited){
			flag = true;
		}
	}else{
		flag=false;
	}
	return flag;
}

/**
 * 获取当前用户所在的部门
 */
function getDeptName(userId){
	var url =contextPath+"/personManager/getPersonById.action?uuid="+userId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data.deptIdName;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

function back(){
	var url = contextPath+"/system/core/base/exam/manage/examInfo.jsp";
	location.href=url;
}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="topbar clearfix">
	    <div class="fl" style="position:static;">
			<span class="title">阅卷</span>
		</div>
		<div class="fr right clearfix">
			<button class="btn-win-white" onclick="check()">阅卷</button>
			<button class="btn-win-white" onclick="back()">返回</button>
		</div>
	</div>
</body>
</html>