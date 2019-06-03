<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>进行中</title>
</head>
<script>
var  datagrid;
function doInit(){
		datagrid = $('#datagrid').datagrid({
			url:contextPath + "/taskController/getTaskListByStatus.action?status=0",
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			checkbox:false,
			border:false,
			
			fitColumns:true,//列是否进行自动宽度适应
			columns:[[
				/* {field:'sid',checkbox:true,title:'ID',width:100}, */
				{field:'taskName',title:'任务名称',width:100,formatter:function(value,rowData,rowIndex){
				    return  "<a href=\"#\" onclick=\"detail("+rowData.sid+")\">"+rowData.taskName+"</a>";
			    }},
				{field:'projectName',title:'所属项目',width:100},
			    {field:'projectNo',title:'项目编码',width:100}, 	   
			    {field:'taskLevel',title:'任务级别',width:100},
			    {field:'progress',title:'任务进度',width:100,formatter:function(value,rowData,rowIndex){
				    return rowData.progress+"%";
			    }},
			    {field:'beginTimeStr',title:'计划开始时间',width:100},
			    {field:'endTimeStr',title:'计划结束时间',width:100},
			    {field:'days',title:'工期',width:100,formatter:function(value,rowData,rowIndex){
				    return rowData.days+"天";
			    }},
			    {field:'opt_',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				    return "<a href=\"#\" onclick=\"handle("+rowData.sid+")\">办理</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"end("+rowData.sid+")\">结束</a>"
			    }},
			    
	            
			]]
		});
		
}

//办理任务  
function  handle(sid){
	//先判断是不是第一次办理    设置实际开始时间
	var url=contextPath+"/taskController/begin.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var url=contextPath+"/system/subsys/project/taskdetail/index.jsp?sid="+sid;
		openFullWindow(url);
	}
	
	
}
//查看 任务详情 
function  detail(sid){
	
	var url=contextPath+"/system/subsys/project/taskdetail/index.jsp?sid="+sid;
	openFullWindow(url);
	
}

//结束任务
function end(sid){
	  $.MsgBox.Confirm ("提示", "是否确定结束该任务？", function(){
		  var url = contextPath + "/taskController/end.action";
		  var  json=tools.requestJsonRs(url,{sid:sid});
		  if(json.rtState){
			  $.MsgBox.Alert_auto("已结束！");
			  datagrid.datagrid("reload");
		  }
	  });
}

</script>

<body onload="doInit()" style="padding-right: 10px;padding-left: 10px">
   <table id="datagrid" fit="true"></table>
</body>
</html>