<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>立项中</title>
</head>
<script>
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/projectController/getProjectListByStatus.action?status=1",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'projectName',title:'项目名称',width:100},
			{field:'projectNum',title:'项目编码',width:100},
			{field:'endTime',title:'计划结束日期',width:100},
			{field:'managerName',title:'项目负责人',width:100},
			{field:'projectMemberNames',title:'项目成员',width:400},
			{field:'progress',title:'进度',width:100,formatter:function(value,rowData,rowIndex){
				return rowData.progress+"%";
			}},
			{field:'opt_',title:'操作',width:120,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"detail('"+rowData.uuid+"')\">详情</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"edit('"+rowData.uuid+"')\">编辑</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"tijiao('"+rowData.uuid+"')\">提交</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"del('"+rowData.uuid+"')\">删除</a>";
			}}
		]]
	});
	
}
//详情
function detail(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/info.jsp?uuid="+uuid;
	openFullWindow(url);
}

//编辑
function edit(uuid){
	window.parent.location.href=contextPath+"/system/subsys/project/myproject/addOrUpdate.jsp?uuid="+uuid;
}

//提交
function tijiao(uuid){
	 $.MsgBox.Confirm ("提示", "是否确认提交该项目？", function(){
		 var url=contextPath+"/projectController/submitProject.action";
			var param={uuid:uuid,status:2};
			var json=tools.requestJsonRs(url,param);
			
			if(json.rtState){
				$.MsgBox.Alert_auto("提交成功！");
				datagrid.datagrid('reload');
			} 
	  });
	
}

//删除
 function del(uuid){
	
	 $.MsgBox.Confirm ("提示", "是否确认删除该项目？", function(){
		 var url=contextPath+"/projectController/delByUUid.action";
			var param={uuid:uuid};
			var json=tools.requestJsonRs(url,param);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			}
	  });
	
}
</script>
<body onload="doInit()">
	<table id="datagrid" fit="true"></table>
</body>
</html>