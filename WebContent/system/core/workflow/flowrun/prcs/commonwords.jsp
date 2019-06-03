<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
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
	var itemId = '<%=itemId%>';
	var datagrid;
	
	//初始化
	function doInit(){
		datagrid = $('#datagrid').datagrid({
			url:contextPath + '/CommonWord/testDatagrid.action',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			pagination:true,
			singleSelect:false,
			toolbar:'#toolbar',//工具条对象
			checkbox:false,
			border:false,
			//idField:'formId',//主键列
			fitColumns:true,//列是否进行自动宽度适应
			columns:[[
				{field:'cyy',title:'常用语',width:150},
				{field:'cis',title:'使用频次',width:150},
				{field:'ope_',title:'操作',width:80,formatter:function(value,rowData,rowIndex){
					 return "<a href=\"#\" onclick=\"use("+rowIndex+")\">回填</a>";
				}},
			]]
		});
	}
	
	
	function use(index){
		var rowData = $('#datagrid').datagrid('getData').rows[index];
		var sid = rowData.sid;
		var cyy = rowData.cyy;
		
		var url = contextPath + "/CommonWord/wordCountPlus.action?sid="+sid;
		tools.requestJsonRs(url,{});
		parent.xparent.$("#DATA_"+itemId).val(cyy);
		parent.window.close();
	}

	</script>
</head>
<body onload="doInit()">
   <table id="datagrid" fit="true"></table>
</body>
</html>