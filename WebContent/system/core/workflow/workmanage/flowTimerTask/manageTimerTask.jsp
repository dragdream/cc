<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String flowTypeId = request.getParameter("flowTypeId") == null ? "" : request.getParameter("flowTypeId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>定时任务管理</title>
	<script type="text/javascript" charset="UTF-8">
	var flowTypeId = '<%=flowTypeId%>'; 
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
		var url1 = contextPath+"/flowTimerTask/getFlowName.action?flowTypeId="+flowTypeId;
		var jsonRs = tools.requestJsonRs(url1,{});
		var jsonObj = jsonRs.rtData;
		$("#flowNameSpan").html(jsonObj);
		var url = contextPath+"/flowTimerTask/getTimerTaskList.action?flowId="+flowTypeId;
		datagrid = $('#datagrid').datagrid({
			url : url,
			toolbar : '#toolbar',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns : [ [ {
				title : 'id',
				field : 'sid',
				width : 50,
				hidden:true
				//sortable:true
				
			},{
				title : '最后提醒时间',
				field : 'lastTime',
				width : 50,
				hidden:true
				//sortable:true
				
			},
			 {
				title : '定时类型',
				field : 'timerType',
				width : 50,
				sortable:true
				
			}, {
				field : 'userNames',
				title : '流程发起人',
				width : 200,
				sortable : true
			},{
				field : 'remindModelDesc',
				title : '发起时间',
				width : 200,
				sortable : true,
				
			} ,{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
			//	alert(rowData.uuid);
				//alert(formatDate(new Date(rowData.lastTime)));
				if(rowData.type==1&&formatDate(new Date(rowData.lastTime))!="0-1-1 0:0:0"){
					return "&nbsp;&nbsp;<a href='javascript:void(0);' onclick=\"deleteTask("+rowData.sid+")\">删除</a>&nbsp;";
				}else{
					return "&nbsp;<a href='javascript:void(0);' onclick=\"edit("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;<a href='#' onclick=\"deleteTask("+rowData.sid+")\">删除</a>&nbsp;";
				}
				
			}}
			] ]
		});
		
	});
	
	function   formatDate(now)   {     
        var   year=now.getYear();     
        var   month=now.getMonth()+1;     
        var   date=now.getDate();     
        var   hour=now.getHours();     
        var   minute=now.getMinutes();     
        var   second=now.getSeconds();     
        return   year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;     
    }        

	function edit(id){
 		window.location.href = "addOrUpdateTimerTask.jsp?sid="+id+"&flowTypeId="+flowTypeId;
    }
	
    function deleteTask(id){
    	var url = "<%=contextPath %>/flowTimerTask/deleteById.action";
		var jsonRs = tools.requestJsonRs(url,{'sid':id});
		if(jsonRs.rtState){
			$.jBox.tip("删除任务成功!","success");
			datagrid.datagrid('reload');
		}else{
			$.jBox.tip(jsonRs.rtMsg,"error");
		}
    }
    
    function changeStatus(select){
    	var para =  tools.formToJson($("#form1")) ;
    	$('#datagrid').datagrid('load', 
    		para
        );
    }
    function query(){
    	var para =  tools.formToJson($("#form1")) ;
    	$('#datagrid').datagrid('load', 
    		para
        );
    	
    }
    
    function toAddUpdate(){
    	
    	var url = "<%=contextPath%>/system/core/workflow/workmanage/flowTimerTask/addOrUpdateTimerTask.jsp?flowTypeId=" + flowTypeId;
    	window.location.href = url;
    }
	</script>
</head>
<body fit="true">
	<div id="toolbar" class="toolbar" style="padding:5px"> 
		<div class="moduleHeader">
			<b>定时任务管理&nbsp;/&nbsp;<span id="flowNameSpan"></span></b>
		</div>
		<button class="btn btn-primary" onclick="toAddUpdate()">添加定时任务</button>
	</div>
<table id="datagrid"></table>
</body>
</html>