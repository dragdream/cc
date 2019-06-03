<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<title>档案文件销毁</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var datagrid;
function  doInit(){
	getList();
}
/**
 *查询管理
 */
function getList(){
	var params = tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeDamFilesController/getArchivedAndDeledFileList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		queryParams:params,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'title',title:'文件标题',width:200,formatter:function(value,rowData,rowIndex){
				var opt="<a  href='#' onclick='detail("+rowData.sid+")'>"+rowData.title+"</a>&nbsp;&nbsp;&nbsp;";
			    return opt;
			}},
			{field:'number',title:'编号',width:200},
			{field:'unit',title:'发/来文单位',width:200},
			{field:'retentionPeriodStr',title:'保管期限',width:200},
			{field:'mj',title:'密级',width:200},
			{field:'hj',title:'紧急程度',width:200},
			{field:'createTimeStr',title:'创建日期',width:250},
			{field:'opt_',title:'操作',width:300,formatter:function(value,rowData,rowIndex){
				var opt="<a  href='#' onclick='restore("+rowData.sid+")'>还原</a>&nbsp;&nbsp;&nbsp;";
				opt+="<a  href='#' onclick='destory("+rowData.sid+")'>销毁</a>&nbsp;&nbsp;&nbsp;";
			    return opt;
			}}
		]]
	});
	
}


//查看 
function detail(sid){
	var url=contextPath+"/system/core/base/dam/fileManagement/file/detail.jsp?sid="+sid;
    openFullWindow(url);
}

//销毁
function destory(sid){
	 $.MsgBox.Confirm ("提示", "是否确认销毁该档案？", function(){
		  var url = contextPath + "/TeeDamFilesController/destory.action";
			var para = {ids:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("销毁成功！");
				datagrid.datagrid('reload');
			}   
	  });
	
}



//批量销毁
function destoryBatch(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("未选中任何数据！");
		return;
	}else{
		var ids = [];
		for(var i=0;i<selections.length;i++){
			ids.push(selections[i].sid);
		}
		 $.MsgBox.Confirm ("提示", "是否确认销毁所选择的档案文件？", function(){
			  var url = contextPath + "/TeeDamFilesController/destory.action";
				var para = {ids:ids.join(",")};
				var json = tools.requestJsonRs(url,para);
				if(json.rtState){					
					$.MsgBox.Alert_auto("销毁成功！");
					datagrid.datagrid('reload');
				}   
		  });
		
	}
}



//还原 
function restore(sid){
	$.MsgBox.Confirm ("提示", "是否确认还原该档案？", function(){
		  var url = contextPath + "/TeeDamFilesController/restore.action";
			var para = {ids:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("还原成功！");
				datagrid.datagrid('reload');
			}   
	  });
}



//批量销毁
function restoreBatch(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		$.MsgBox.Alert_auto("未选中任何数据！");
		return;
	}else{
		var ids = [];
		for(var i=0;i<selections.length;i++){
			ids.push(selections[i].sid);
		}
		 $.MsgBox.Confirm ("提示", "是否确认还原所选择的档案文件？", function(){
			  var url = contextPath + "/TeeDamFilesController/restore.action";
				var para = {ids:ids.join(",")};
				var json = tools.requestJsonRs(url,para);
				if(json.rtState){					
					$.MsgBox.Alert_auto("还原成功！");
					datagrid.datagrid('reload');
				}   
		  });
		
	}
}
</script>
</head>
<body class="" onload="doInit();" style="padding-left: 10px;padding-right: 10px">
  <div id="toolbar" class = "topbar clearfix">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/dam/imgs/icon_dawjxh.png">
		<span class="title">档案文件销毁</span>
	</div>
    <div class="fr right">
	   <input type="button" value="批量还原" class="btn-win-white fr" onclick="restoreBatch();">
	   &nbsp;
	   <input type="button" value="批量销毁" class="btn-del-red fr" onclick="destoryBatch();"> 
    </div> 
  </div>
 
  <table id="datagrid" fit="true"></table>
</body>

</html>