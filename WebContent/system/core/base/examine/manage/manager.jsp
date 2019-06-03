<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script>
var datagrid;

/**
 * 初始化
 */
function doInit(){
	//getLogList();
}
var datagrid;
var userDialog;
var userForm;
var passwordInput;
var userRoleDialog;
var userRoleForm;
var title ="";
$(function() {
	userForm = $('#userForm').form();

	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamineDataManage/getPostExamineTask.action",
		toolbar : '#toolbar',
		title : title,
		//iconCls : 'icon-save',
		iconCls:'',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : false,
		nowrap : false,
		border : false,
		idField : 'sid',
		singleSelect:true,
		columns:[[
					//{field:'sid',checkbox:true,title:'ID',width:40},
					{field:'groupId',title:'考核集Id',width:150,hidden:true},
					{field:'taskTitle',title:'考核任务名称',width:250},
					{field:'rankmanNames',title:'考核人',width:240},
					{field:'participantNames',title:'被考核人',width:300},
					{field:'groupName',title:'考核指标集',width:120},
					{field:'anonymityDesc',title:'匿名',width:50},
					{field:'taskBeginStr',title:'生效日期',width:80},
					{field:'taskEndStr',title:'终止日期',width:80},
					/* {field:'examineDesc',title:'状态',width:60,formatter:function(e,rowData,index){
						return "未考核完";
						}
					}, */
					{field:'2',title:'操作',width:50,formatter:function(e,rowData,index){
						var groupId = rowData.groupId;
						var tempStr = "&nbsp;&nbsp;<a href='#' onclick='toExamine(" + rowData.sid + "," +  groupId + ")'>考核</a>";
						return tempStr;
					}}
				]],onLoadSuccess:onLoadSuccessFunc
	});
	
	
});
/**
 * 获取最大记录数
 */
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData');
	$("#totalPerson").empty();
}


/**
 * 跳转至考核项目
 */
function toExamine(taskId , groupId){
	window.location.href = "<%=contextPath%>/system/core/base/examine/manage/index.jsp?taskId=" + taskId + "&groupId=" + groupId;
}
</script>

</head>

<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden;">
	
	<div id="toolbar" class="datagrid-toolbar" style="height: auto;background: #ffffff;"> 
		<div class="moduleHeader">
			<b>考核</b>
		</div>

      <div>
	    </div>
	  <!--   <div style="padding-top:10px;padding-left:10px;">

	       </div> -->
	</div>

	<table id="datagrid"></table>
</div>
</body>
</html>

