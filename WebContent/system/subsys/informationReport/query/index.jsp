<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上报信息查看</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
//初始化方法
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeTaskTemplateController/getMyViewTask.action",
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
			{field:'taskName',title:'任务名称',width:200},
			{field:'type',title:'上报类型',width:200,formatter:function(value,rowData,rowIndex){
                var taskType=rowData.taskType;
                if(taskType==1){
                	return "日报";
                }else if(taskType==2){
                	return "周报";
                }else if(taskType==3){
                	return "月报";
                }else if(taskType==4){
                	return "季报";
                }else if(taskType==5){
                	return "年报";
                }else if(taskType==6){
                	return "一次性";
                }
			}},
			{field:'modelDesc',title:'上报频次',width:200},
			{field:'crUserName',title:'发布人',width:200},
			{field:'opt_',title:'操作',width:150,formatter:function(value,rowData,rowIndex){
				var opt="<a href=\"#\" onclick=\"view("+rowData.sid+",'"+rowData.taskName+"')\">查看</a>&nbsp;&nbsp;&nbsp;";
                return opt;
			}},
		]]
	});
}


//查看汇总情况
function view(taskTemplateId,taskTemplateName){
	window.location.href=contextPath+"/system/subsys/informationReport/query/reportCollect.jsp?taskTemplateId="+taskTemplateId+"&taskTemplateName="+taskTemplateName;
}
</script>
</head>
<body  onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/informationReport/imgs/icon_sbxxck.png">
		<span class="title">上报信息查看</span>
	</div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>