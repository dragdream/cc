<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/salary/js/account.js"></script>

<%
	int accountId = TeeStringUtil.getInteger(
			request.getParameter("accountId"), 0);//账套
	String accountName = TeeStringUtil.getString(
			request.getParameter("accountName"), "");//账套名称
%>

<title>员工账套设定</title>

	<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var title ="";
	var accountId = <%=accountId%>;
	$(function() {
		
		getAllAccount('accountId');
		userForm = $('#form1').form();
	
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeSalAccountController/getManageAccountPersonList.action' ,
			toolbar : '#toolbar',
			title : title,
			queryParams:{accountId:accountId},
			//iconCls : 'icon-save',
			iconCls:'',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'sid',
			singleSelect:false,

			columns : [ [
			     {field:'sid',checkbox:true},{
				field : 'personModel',
				title : '用户名',
				width : 80,
				formatter:function(value, rowData, rowIndex){
					return value.userId;
				}
			},  {
				field : 'personModel.userName',
				title : '姓名',
				width : 100,
				formatter:function(value, rowData, rowIndex){
					return rowData.personModel.userName;
				}
			}, {
				field : 'personModel.deptName',
				title : '部门',
				width : 120,
				formatter:function(value, rowData, rowIndex){
					return rowData.personModel.deptIdName;
				}
			}, {
				field : 'personModel.userRoleName',
				title : '角色',
				width :100,
				formatter:function(value, rowData, rowIndex){
					return rowData.personModel.userRoleStrName;
				}
			}
		  
		] ],onLoadSuccess:onLoadSuccessFunc
	});		
		
});
/**
 * 获取最大记录数
*/
function onLoadSuccessFunc(){
	  
	}
/**
 * 删除
 */
function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("少选择一条记录！","info");
		return;
	}
	var ids = "";
	for(var i=0;i<selections.length;i++){
		var sid = selections[i].sid;
		ids = ids + sid + ",";
	}
	$.jBox.confirm("确认移除所选人员吗？","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/teeSalAccountController/deletePersonByIdsAndAccountId.action";
			var json = tools.requestJsonRs(url,{ids:ids ,accountId:$("#accountId").val() });
			if(json.rtState){
				$.jBox.tip("移除成功！","success"  );
				datagrid.datagrid("reload");
			}else{
				alert(json.rtMsg);
			}
		}
	});
}
//编辑
function edit(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("至少选择一条记录！","info");
		return;
	}else{
		if(selections.length > 1){
			$.jBox.tip("请选择一条记录进行编辑！","info");
			return;
		}
	}
	var sid = selections[0].sid;
	toAddOrUpdate(sid);
}

/**
 * 条件查询
 */
function query(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);	
}

/**
 * 添加人员
 */
function addPersonFunc(){
	var userIds = $("#userIds").val();
	if($("#accountId").val() == ''){
		alert("请先选择工资账套后再添加人员！");
		return;
	}
	if(userIds != ''){
		var url = contextPath+"/teeSalAccountController/addPersonByAccount.action";
		var para =  {accountId:$("#accountId").val() , userIds: userIds};
		var json = tools.requestJsonRs(url , para);
		if(json.rtState){
			$.jBox.tip("添加帐套人员成功！", 'info' , {timeout:1500});
			$('#datagrid').datagrid('reload', para);
		}else{
			alert(json.rtMsg);
			return;
		}

	}
}
</script>
</head>
<body >		 
<table id="datagrid"></table>
<div id="toolbar" style=""> 
	 <div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1">员工帐套设置</span>
				</td>
			</tr>
		</table>
	</div>
  	<form id="form1" name="form1" style="padding:10px;" >
		<table>
   			<tr>
   				 <td nowrap class="TableData" colspan="2">
   				 	<button type="button" class="btn btn-default" onclick="window.location='index.jsp'">返回</button>
   				 	&nbsp;
					<input type="hidden" name="accountId" id="accountId" value="<%=accountId%>"/>
					查询条件：
					<input type="text" name="userName" value="" class="BigInput" onkeyup="query()" style="width:100px" placeholder="姓名/账号/部门/角色进行筛选">
					<input type="hidden" name="userIds" id="userIds"></input>
					<input type="hidden" name="userNames" id="userNames"></input>
					<button type="button" class="btn btn-primary" onclick="$('#userIds').val('');$('#userNames').val('');selectUser(['userIds' , 'userNames'],'','','0','','addPersonFunc')">增加人员</button>
					<button type="button" class="btn btn-danger" onclick="del()">移除人员</button>
  			</tr>	
   		</table>
	</form>
</div>
</body>
</html>
