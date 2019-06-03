<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>


	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>角色管理</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
	<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
	
	<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
	
		datagrid = $('#datagrid').datagrid({
			url : '<%=request.getContextPath() %>/userRoleController.action?datagrid',
		//	toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			sortOrder: 'asc',
			striped: true,
			remoteSort: true,
			columns : [ [ 
			 {field:'ck',checkbox:true},{
				title : 'id',
				field : 'uuid',
				width : 200,
				hidden:true
				//sortable:true
				
			}, {
				field : 'roleName',
				title : '角色名称',
				width : 200
			},{
				field : 'roleNo',
				title : '角色排序',
				width : 200,
				sortable : true
				//formatter : function(value, rowData, rowIndex) {
				//	return rowData;
				//}
			} ,{field:'_manage',title:'操作',width : 200,formatter:function(value,rowData,rowIndex){
			//	alert(rowData.uuid);
				return "<a href='javascript:void(0);' onclick=\"edit('"+rowData.uuid+"')\">编辑</a>&nbsp;"
				 + "<a href='javascript:void(0);' onclick=\"deleteRole('"+rowData.uuid+"')\">删除</a>&nbsp;"
				/*  + "<a href='javascript:void(0);' onclick=\"toPrev("+rowData.uuid + "," + rowData.roleNo +")\">上移</a>&nbsp;"
				 + "<a href='javascript:void(0);' onclick=\"toNext("+rowData.uuid + "," + rowData.roleNo +")\">下移</a>"
				 */	
				 ;
			}}
			] ]
		});
		
	});

	function edit(id){
 		window.location.href = contextPath + "/system/core/org/role/editRole.jsp?uuid="+id;
    }
	/*删除*/
    function deleteRole(id){

    	var url = "<%=contextPath %>/userRoleController.action?del";
		var jsonRs = tools.requestJsonRs(url,{'ids':id});
		if(jsonRs.rtState){
		//	var data = jsonRs.rtData;
		$.messager.show({
			msg : '编辑角色成功!',
			title : '提示'
		});
		datagrid.datagrid('reload');
		}else{
			alert(jsonRs.rtMsg);
		}
    }
    
    
    /***
     *上移
     */
    function toPrev(id , roleNo){
		var url = contextPath+"/flowSort/doSortOrder.action";
		var json = tools.requestJsonRs(url,{type:1,flowSortId:flowSortId});
		$('#datagrid').datagrid('reload');
		window.event.cancelBubble=true;
	}
    
    /***
     *下移
     */
	function toNext(id ,roleNo){
		var url = contextPath+"/flowSort/doSortOrder.action";
		var json = tools.requestJsonRs(url,{type:2,flowSortId:flowSortId});
		$('#datagrid').datagrid('reload');
		window.event.cancelBubble=true;
	}

	</script>
</head>
<body class="easyui-layout" fit="true">
<table id="datagrid"></table>
</body>
</html>