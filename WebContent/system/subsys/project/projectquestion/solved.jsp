<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已解决</title>
</head>
<script>
var datagrid;
//初始化方法
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/projectQuestionController/getQuestionListByStatus.action?status=1",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'projectName',title:'项目名称',width:100,formatter:function(value,rowData,rowIndex){
				
				return "<a href='#' onclick=\"projectDetail('"+rowData.projectId+"')\">"+rowData.projectName+"</a>";
			}},
			{field:'questionName',title:'问题名称',width:100},
			{field:'createrName',title:'提交人',width:100},
			{field:'questionLevel',title:'优先级',width:200},
			{field:'opt_',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				return "<a href='#' onclick=\"questionDetail("+rowData.sid+")\">详情</a>&nbsp;&nbsp;&nbsp;";
			}},
            
		]]
	});
}
//项目详情
function projectDetail(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/index.jsp?uuid="+uuid;
	openFullWindow(url);
}
//问题详情
function questionDetail(sid){
	var url=contextPath+"/system/subsys/project/taskdetail/questionDetail.jsp?sid="+sid;
	bsWindow(url ,"问题详情",{width:"600",height:"290",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		 if(v=="关闭"){
			return true;
		}
	}});
	
}

</script>

<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
   <table id="datagrid" fit="true"></table>
</body>
</html>