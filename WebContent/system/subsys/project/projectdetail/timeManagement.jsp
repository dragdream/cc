<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.tree.js'></script>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.treegrid.js'></script>
<title>时间管理</title>
</head>

<script>
var uuid="<%=uuid%>";
function doInit(){
	$("#treeGrid").treegrid({
		url: contextPath + "/taskController/getTaskListByProjectId.action?projectId="+uuid,
		/* view:window.EASYUI_DATAGRID_NONE_DATA_TIP, */ 
		method: 'get',
        idField: 'sid',
        treeField: 'taskName',
        pagination:false,
        border:false,
        columns:[[
      			{field:'taskName',rowspan:2,align:'center',title:'任务名称',width:200,formatter:function(value,rowData,rowIndex){
      			   return "<a href=\"#\" onclick=\"detail("+rowData.sid+")\">"+rowData.taskName+"</a>";
      			}},
      			{field:'taskNo',rowspan:2,align:'center',title:'任务序号',width:200},
      			{field:'managerName',rowspan:2,align:'center',title:'负责人',width:150},
      			{field:'status',rowspan:2,align:'center',title:'当前状态',width:200},
      			{field:'beginTime',colspan:2,align:'center',title:'开始时间',width:300},
      			{field:'endTime',colspan:2,align:'center',title:'结束时间',width:300},
      		],
      		[
   			{field:'beginTimeStr',align:'center',title:'计划开始时间',width:150},
   			{field:'realBeginTimeStr',align:'center',title:'实际开始时间',width:150},
   			{field:'endTimeStr',align:'center',title:'计划结束时间',width:150},
   			{field:'realEndTimeStr',align:'center',title:'实际结束时间',width:150}, 
   			
   		]],onLoadSuccess:onLoadSuccessFunc
        
	});
}

function onLoadSuccessFunc(){
	$('.datagrid-btable').find('div.datagrid-cell').css('text-align','center');
}



//查看任务详情
function detail(sid){
	var url=contextPath+"/system/subsys/project/projectdetail/basic/taskDetail.jsp?sid="+sid+"&&projectId="+uuid;
	bsWindow(url ,"任务详情",{width:"600",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="关闭"){
			return true;
		}
	}}); 
}
</script>
<body onload="doInit()">
    <table id="treeGrid" class="easyui-treegrid" fit="true" ></table>
</body>
</html>