<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//1=人员  2=部门  3=月份
 int target=TeeStringUtil.getInteger(request.getParameter("target"),0);
//ngs=拟稿数  blzs=办理总数  asbjs=按时办理数   csbjs=超时办理数   wbjs=未办理数   jbzs=经办总数
//yjbs=已经办数  djbs=待经办数
 String type=TeeStringUtil.getString(request.getParameter("type"));
 String typeDesc=TeeStringUtil.getString(request.getParameter("typeDesc"));

 String beginTime = TeeStringUtil.getString(request.getParameter("beginTime"));
 String endTime = TeeStringUtil.getString(request.getParameter("endTime"));
 String flowId = TeeStringUtil.getString(request.getParameter("flowId"));
 
 int  userId=TeeStringUtil.getInteger(request.getParameter("userId"),0);
 int  deptId=TeeStringUtil.getInteger(request.getParameter("deptId"),0);
 String month=TeeStringUtil.getString(request.getParameter("month"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程数据详情</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var target=<%=target %>;
var type="<%=type %>";
var typeDesc="<%=typeDesc %>";
var beginTime="<%=beginTime %>";
var endTime="<%=endTime %>";
var flowId="<%=flowId %>";
var userId=<%=userId %>;
var deptId=<%=deptId %>;
var month="<%=month %>";
var datagrid;
//初始化
function doInit(){
	$("#title").html(typeDesc);
	
	var para = {};
	para["target"]=target;
	para["type"]=type;
	para["beginTime"]=beginTime;
	para["endTime"]=endTime;
	para["flowId"]=flowId;
	para["userId"]=userId;
	para["deptId"]=deptId;
	para["month"]=month;
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/flowStatistic/getStatisticFlowData.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		queryParams:para,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'runId',title:'流水号',width:50},
			{field:'runName',title:'工作名称',width:200,formatter:function(value,rowData,rowIndex){
				var runName=rowData.runName;
				return "<a href=\"#\" onclick=\"view("+rowData.runId+");\">"+runName+"</a>";
			}},
			{field:'flowName',title:'所属流程',width:200},
			{field:'createUserName',title:'流程发起人',width:200},
			{field:'step',title:'办理步骤',width:200},
		]]
	});
}


//查看
function  view(runId){
	var url=contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId;
	openFullWindow(url);
}
</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
	<div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/workflow/flowstatistic/imgs/icon_lcsj.png">
			<span class="title" id="title">流程数据</span>
		</div>
	</div>
	<table id="datagrid" fit="true"></table>
</div>
</body>
</html>