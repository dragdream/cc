<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	%>
	<title>我的计划</title>
	<script>
	var runId = <%=runId%>;
	var userId = <%=loginUser.getUuid()%>;
	
	function doInit(){
		initDataGrid();
	}

	function initDataGrid(){
		$('#datagrid').datagrid({
			url:contextPath+'/runRel/listScheduleRel.action?runId='+runId,
			pagination:true,
			singleSelect:true,
			toolbar:'#toolbar',//工具条对象
			checkbox:false,
			border:false,
			idField:'uuid',//主键列
			fitColumns : false,
			sortOrder: 'desc',
			striped: true,
			pageList:[50,60,70,80,90,100,120,140,160],
			columns:[[
				{field:'uuid',checkbox:true,hidden:true},
				{field:'title',title:'计划名称'},
				{field:'_oper',title:'操作',formatter:function(data,rowData){
					var render = [];
					render.push("<a href='javascript:void(0)' onclick=\"detail('"+rowData.scheduleId+"')\">查看</a>");
					if(userId!=rowData.userId){
						return "";
					}
					render.push("<a href='javascript:void(0)' onclick=\"remove0('"+rowData.uuid+"')\">取消</a>");
					return render.join("&nbsp;&nbsp;");
				}}
			]],
			onLoadSuccess:function(){
				top.$("#schedule_count").html($("#datagrid").datagrid("getData").total);
			}
		});
	}
	
	function query(){
		$('#datagrid').datagrid("reload",{title:$("#title").val()});
	}
	
	function remove0(uuid){
		if(window.confirm("是否取消该相关计划？")){
			var json = tools.requestJsonRs(contextPath+"/runRel/delScheduleRel.action",{uuid:uuid});
			$("#datagrid").datagrid("reload");
		}
	}
	
	function detail(scheduleId){
		top.bsWindow(contextPath+"/system/subsys/schedule/manage/detail.jsp?scheduleId="+scheduleId,"相关计划",{buttons:[{name:"关闭",classStyle:"btn btn-default"}],submit:function(){
			return true;
		}});
	}
	
	function add(){
		dialog("schedule_list.jsp?runId="+runId,800,600);
	}

	</script>
</head>
<body onload="doInit()">
	<table id="datagrid" fit="true"></table>
	
	<!-- 声明工具条 -->
	<div id="toolbar" style="padding:5px">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-briefcase"></i>&nbsp;相关计划</b>
		</div>
		<button class="btn btn-primary" onclick="add()">添加相关计划</button>
	</div>
</body>

</html>