<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   //任务模板主键
   int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"),0);
   //任务模板名称
   String taskTemplateName=TeeStringUtil.getString(request.getParameter("taskTemplateName"));
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>上报汇总</title>
<script type="text/javascript">
var taskTemplateId=<%=taskTemplateId  %>;
var taskTemplateName="<%=taskTemplateName %>";
//初始化
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeTaskPubRecordController/getRecordListByTaskTemplateId.action?taskTemplateId="+taskTemplateId,
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
			{field:'reportTime',title:'上报日期',width:200},
			{field:'sumNum',title:'应报数量',width:200},
			{field:'ybNum',title:'已报数量',width:200},
			{field:'wbNum',title:'未报数量',width:200},
			{field:'rate',title:'上报率',width:200},
			{field:'opt_',title:'操作',width:150,formatter:function(value,rowData,rowIndex){
				var opt="<a href=\"#\" onclick=\"detail("+rowData.recordId+",'"+rowData.reportTime+"')\">已报详情</a>&nbsp;&nbsp;&nbsp;";
                return opt;
			}},
		]]
	});
}


//返回
function  back(){
	history.go(-1);
}


//已报详情
function detail(recordId,reportTime){
	var url=contextPath+"/system/subsys/informationReport/query/detail.jsp?taskPubRecordId="+recordId+"&reportTime="+reportTime+"&taskTemplateName="+taskTemplateName+"&taskTemplateId="+taskTemplateId;
    window.location.href=url;
}
</script>
</head>
<body  onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
	<div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/informationReport/imgs/icon_sbhz.png">
			<span class="title"><%=taskTemplateName %>--上报汇总</span>
		</div>
		<div class = "right fr clearfix">
		    <input type="button" class="btn-win-white fl" onclick="back();" value="返回"/>
        </div>
	</div>
    <table id="datagrid" fit="true"></table>
</body>
</html>