<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
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
			url : '<%=request.getContextPath() %>/sms/getSendSmsList.action',
			toolbar : '#toolbar',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			//pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'smsBodySid',
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			columns : [ [ 
			 {
				 field:'smsBodySid',
				 checkbox:true
			},{
				field : 'toUsers',
				title : '收信人',
				width : 100
			},{
				field : 'content',
				title : '短信内容',
				width : 300,
				formatter : function(value, rowData, rowIndex) {
					return "<span title=\""+value+"\">"+value+"</span>";
				}
			},{
				field : 'sendTimeDesc',
				title : '发送时间',
				width :100
				//formatter : function(value, rowData, rowIndex) {
				//	return rowData;
				//}
			}  ,{
				field : 'sendFlag',
				title : '发送状态',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					switch (value) {
					case 0:
						value = "未发送";  
						break;
					case 1:
						value = "已发送";  
						break;
					default:
						break;
					}
					return value;
				}
			} ,{field:'_manage',title:'操作',width : 100,formatter:function(value,rowData,rowIndex){
			//	alert(rowData.uuid);
				return "<a href='#' onclick=\"deleteSmsBody('"+rowData.smsBodySid+"')\">删除</a>&nbsp;";
			}}
			] ],
			onLoadSuccess:function(){
			//	$("[title]").tooltips();
			}

		});
		
	});

    function deleteSmsBody(id){
    	$.MsgBox.Confirm("提示","确定要删除所选中消息？",function(){
    		var url = "<%=contextPath %>/sms/delSmsBody.action";
    		var jsonRs = tools.requestJsonRs(url,{'smsBodyIds':id});
			if(jsonRs.rtState){
				parent.$.MsgBox.Alert_auto("删除成功!");
				datagrid.datagrid('unselectAll');
				datagrid.datagrid('reload');
			}else{
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
			}
    	
		});
    } 
    function deleteBatch(){
    	var selections = $('#datagrid').datagrid('getSelections');
    	if(selections.length==0){
    		$.MsgBox.Alert_auto("至少选择一项!");
    		return ;
    	}
    		var ids = "";
    		for(var i=0;i<selections.length;i++){
    			ids+=selections[i].smsBodySid;
    			if(i!=selections.length-1){
    				ids+=",";
    			}
    		}
    		deleteSmsBody(ids);

    }
	</script>
</head>
<body>
<table id="datagrid" fit="true"></table>
<div>
	<div class="clearfix fl setHeight">
		<input class="btn-del-red fl" type="button" onclick="deleteBatch()" value="批量删除"/>
	</div>
</div>
</body>
</html>