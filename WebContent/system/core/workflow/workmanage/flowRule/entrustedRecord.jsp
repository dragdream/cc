<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%
	String entrustStatus = request.getParameter("entrustStatus") == null ? "" : request.getParameter("entrustStatus");
    int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>被委托记录</title>
	<script type="text/javascript" src="<%=contextPath%>/system/core/person/js/person.js"></script>
	
	<script type="text/javascript" charset="UTF-8">
	var flowId=<%=flowId %>;
	var entrustStatus = '<%=entrustStatus%>'; 
	//alert(userName);
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	$(function() {
        var para={};
        if(flowId>0){
        	para["flowId"]=flowId;
        }
		var url = contextPath+"/flowRule/getEntrustedRecordList.action";
		datagrid = $('#datagrid').datagrid({
			url : url,
			toolbar : '#toolbar',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			//pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'id',
			sortOrder: 'desc',
			striped: true,
			remoteSort: false,
			queryParams:para,
			columns : [ [ {
				title : '流水号',
				field : 'sid',
				width : 50,
				sortable:true
			},
			 {
				title : '工作名称/文号',
				field : 'workName',
				width : 200,
				sortable:true,
				formatter:function(data,rowData){
					return "<a href='#' onclick=\"openFullWindow('<%=request.getContextPath() %>/system/core/workflow/flowrun/print/index.jsp?runId="+rowData.runId+"&view=1')\">"+data+"</a>";
				}
				
			}, {
				field : 'prcsName',
				title : '步骤名称',
				width : 150,
				sortable : true
			},{
				field : 'flowStatus',
				title : '当前状态',
				width : 100,
				sortable : true
			},{
				field : 'userName',
				title : '委托人',
				width : 100,
				sortable : true
			} ,{
				field : 'entrustTime',
				title : '委托时间',
				width : 100,
				sortable : true,
				formatter:function(data,rowData){
					return getFormatDateStr(data,"yyyy-MM-dd hh:mm");
				}
			}
			] ]
		});
		
	});

	function edit(id){
 		window.location.href = contextPath + "/system/core/org/role/editRole.jsp?uuid="+id;
    }
    function deleteRole(id){

    	var url = "<%=contextPath %>/userRoleController.action?del";
		var jsonRs = tools.requestJsonRs(url,{'ids':id});
		if(jsonRs.rtState){
		//	var data = jsonRs.rtData;
		$.messager.show({
			msg : '编辑角色成功！',
			title : '提示'
		});
		datagrid.datagrid('reload');
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
    }

    function query(){
    	var para =  tools.formToJson($("#form1")) ;
    	if(flowId>0){
    		para["flowId"]=flowId;
    	}
    	$('#datagrid').datagrid('load', 
    		para
        );
    }
	</script>
</head>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
	String uuid = loginPerson.getUuid()+"";
	String userName = loginPerson.getUserName();
	if(isAdmin){
		uuid = "";
		userName = "";
	}
%>
<body style="overflow:hidden;font-family:MicroSoft YaHei;">
<table id="datagrid"></table>
	<div id="toolbar" style="padding-bottom: 5px;padding-top: 5px;"> 
		<form id="form1" name="form1">
		<table style="width:90%">
		<tr>
			<td>
				工作名称/文号 ：
				<input style="height: 25px;" type="text" id="qs" name="qs" class="BigInput"/>
				&nbsp;&nbsp;
				&nbsp;<input style="height: 25px;width: 45px;" type='button' class='btn-win-white' value='查询' onclick='query();' >
			</td>
		</tr>
		</table>
		</form>
	</div>
</body>
</html>