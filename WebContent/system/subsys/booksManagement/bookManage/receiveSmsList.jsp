<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>sms管理</title>
	
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
			url : '<%=request.getContextPath() %>/sms/getReceiveSmsList.action',
			toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'smsSid',
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			onLoadSuccess:function(){
				$("[title]").tooltips();
			},
			columns : [ [ 
			 {field:'smsSid',checkbox:true},{
				field : 'fromUser',
				title : '发信人',
				width : 100,
				sortable : true
			},{
				field : 'content',
				title : '短信内容',
				width : 300,
				sortable : true,
				formatter : function(value, rowData, rowIndex) {
					return "<span title='"+value+"'>"+value+"</span>";
				}
			} ,{
				field : 'remindTimeDesc',
				title : '提醒时间',
				width :100,
				sortable : true
				//formatter : function(value, rowData, rowIndex) {
				//	return rowData;
				//}
			} ,{
				field : 'remindFlagDesc',
				title : '短信状态',
				width : 100,
				sortable : true
			} ,{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
			//	alert(rowData.uuid);
				return "&nbsp;<a href='#' onclick=\"deleteSmsList('"+rowData.smsSid+"')\">删除</a>&nbsp;";
			}}
			] ]
		});
		
	});

	 function deleteSmsList(id){

	    	var url = "<%=contextPath %>/sms/delSms.action";
			var jsonRs = tools.requestJsonRs(url,{'smsIds':id});
			if(jsonRs.rtState){
				top.$.jBox.tip("删除成功","info");
				datagrid.datagrid('unselectAll');
				datagrid.datagrid('reload');
			}else{
				alert(jsonRs.rtMsg);
			}
	    }
	 
	 function deleteBatch(){
	    	var selections = $('#datagrid').datagrid('getSelections');
	    	if(selections.length==0){
	    		alert("至少选择一项");
	    		return ;
	    	}
	    	if(confirm("确定要删除所选中消息？")){
	    		var ids = "";
	    		for(var i=0;i<selections.length;i++){
	    			ids+=selections[i].smsSid;
	    			if(i!=selections.length-1){
	    				ids+=",";
	    			}
	    		}
	    		deleteSmsList(ids);
	    	}

	    }
	</script>
</head>
<body>
<div id="toolbar">
	<button class="btn btn-one" onclick="deleteBatch()">批量删除</button>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>