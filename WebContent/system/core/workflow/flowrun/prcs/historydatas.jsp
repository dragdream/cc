<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String frpSid = request.getParameter("frpSid");
	String itemId = request.getParameter("itemId");
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
	<script type="text/javascript" charset="UTF-8">
	var frpSid = '<%=frpSid%>';
	var itemId = '<%=itemId%>';
	var datagrid;
	//初始化
	function  doInit(){
		datagrid = $('#datagrid').datagrid({
			url:contextPath + '/flowRun/getHistoryFlowRunDatas.action?itemId='+itemId+'&frpSid=' + frpSid,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			pagination:true,
			singleSelect:false,
			toolbar:'#toolbar',//工具条对象
			checkbox:false,
			border:false,
			//idField:'formId',//主键列
			fitColumns:true,//列是否进行自动宽度适应
			columns:[[
				{field:'RUN_ID',title:'流水号',width:50},
				{field:'RUN_NAME',title:'工作名称',width:150},
				{field:'DATA_'+itemId,title:'相关数据',width:150},
				{field:'ope_',title:'操作',width:80,formatter:function(value,rowData,rowIndex){
					 return "<a href=\"#\" onclick=\"fill("+itemId+","+rowIndex+")\">回填</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"fillAll("+rowIndex+")\">回填整表</a>";
				}},
			]]
		});
	}

		

	function fill(itemId,index){
		var rowData = $('#datagrid').datagrid('getData').rows[index];
		var data = rowData["DATA_"+itemId];
		parent.xparent.$("#DATA_"+itemId).val(data);
		parent.window.close();
	}
	
	function fillAll(index){
		var rowData = $('#datagrid').datagrid('getData').rows[index];
		var writableCtrls = parent.xparent.$("input[writable][xtype=xinput],textarea[writable][xtype=xtextarea]");
		for(var i=0;i<writableCtrls.length;i++){
			var id = $(writableCtrls[i]).attr("id");
			parent.xparent.$("#"+id).val(rowData[id]);
		}
		parent.window.close();
	}
	</script>
</head>
<body onload="doInit()">
<table id="datagrid"  fit="true"></table>

</body>
</html>