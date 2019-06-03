<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已上报</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeTaskPubRecordItemController/getMyReport.action?flag=1",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'taskTemplateName',title:'任务名称',width:200},
			{field:'taskTypeDesc',title:'任务类型',width:120,formatter:function(value,rowData,rowIndex){
                  var taskType=rowData.taskType;
                  if(taskType==1){// 1=日报   2=周报    3=月报     4=季报     5=年报    6=一次性
                	  return "日报"; 
                  }else if(taskType==2){
                	  return "周报";
                  }else if(taskType==3){
                	  return  "月报";
                  }else if(taskType==4){
                	  return "季报";
                  }else if(taskType==5){
                	  return "年报";
                  }else{
                	  return "一次性";
                  }
			
			}},
			{field:'pubTypeDesc',title:'发布类型',width:120},
			{field:'pc',title:'上报频次',width:150},
			{field:'createTimeStr',title:'发布时间',width:150},
			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
				 var opt="<a href=\"#\" onclick=\"detail("+rowData.sid+",'"+rowData.taskTemplateName+"',"+rowData.taskTemplateId+")\">详情</a>";
				 return opt;
			}},
		]]
	});

}

//詳情
function detail(taskPubRecordItemId,taskTemplateName,taskTemplateId){
	var url=contextPath+"/system/subsys/informationReport/myReport/detail.jsp?taskPubRecordItemId="+taskPubRecordItemId+"&taskTemplateName="+taskTemplateName+"&&taskTemplateId="+taskTemplateId;	
    openFullWindow(url);
}





</script>
</head>
<body onload="doInit();">
   <table id="datagrid" fit="true"></table>
</body>
</html>