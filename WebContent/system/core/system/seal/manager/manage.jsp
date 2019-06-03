<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
	<title>印章管理</title>
	<script type="text/javascript" src="<%=contextPath%>/system/core/system/seal/js/sealmanage.js"></script>
	
	<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var title ="";
	$(function() {
		//userForm = $('#form1').form();
	    userForm=tools.formToJson("#form1");
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/sealManage/getSealList.action?sort=createTime' ,
			toolbar : '#toolbar',
			title : title,
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			//iconCls : 'icon-save',
			iconCls:'',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
			fit : true,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'sid',
			singleSelect:false,
			columns : [ [
			     {field:'sid',checkbox:true},{
				field : 'sealId',
				title : '规则号',
				width:100
			},{
				field : 'sealName',
				title : '规则名称',
				width:500
		}
		  
		] ],onLoadSuccess:onLoadSuccessFunc
	});		
		
});
/**
 * 获取最大记录数
*/
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData');
}
/**
 * 查看印章
 */

function  toSealDataInfo(sid){
	var url=contextPath+"/system/core/system/seal/manager/sealInfo.jsp?sid="+sid;
	bsWindow(url ,"印章信息",{width:"600",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

function toSettingPriv(sid){
	window.location.href = "<%=contextPath%>/system/core/system/seal/manager/setPriv.jsp?sid=" + sid;
}

/**
 * 点击修改密码
 */

function reSetPassword(sid){
	var url=contextPath+"/system/core/system/seal/manager/updateSealInfo.jsp?sid="+sid;
	bsWindow(url ,"印章密码修改",{width:"600",height:"320",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var b=cw.save();
		    if(b){
		    	$.MsgBox.Alert_auto("修改成功！");
		    	return  true;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
	
	
	
}



/**
 * 条件查询
 */
function query(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);	
}

</script>
</head>
<body >
<div id="toolbar" style=""> 
<form id="form1" style="padding:10px">
<table class="none_table">
	<tr>
		 <td nowrap class="TableData">
		         规则号：
		 </td>
		 <td>        
		    <input type="text" name="sealId" id="sealId"  class="BigInput" style="height: 23px;width: 170px;"/> 
		 </td>
		 <td>
		      规则名称:
		 </td>
		 <td>
		    <input type="text" name="sealName" id="sealName"  class="BigInput" style="height: 23px;width: 170px;"/> 
		 </td>
		<td class="TableData">
			<input type="button" value="查询" class="btn-win-white" onclick="query()"/>&nbsp;
<!-- 			<input type="button" value="启用" class="btn-win-white" onclick="openSeal();"/>&nbsp; -->
<!-- 		    <input type="button" value="停用" class="btn-win-white" onclick="stopSeal();"/>&nbsp; -->
		    <input type="button" value="删除" class="btn-win-white" onclick="deleteSeal();"/>
		</td>
	</tr>
</table>
</form>
</div>
	
		 
<table id="datagrid"></table>



	
<div id="updateSealInfo" class="easyui-dialog" closed="true">
		<div id="apply_body_manage" class="body" align="" style="display: none;">
			<div class="" style="padding: 8px 0px 5px 10px;">
				<span id="title" class=""><b>印章信息</b></span>
			</div>
			
	</div>
</div>
	
	

</body>
</html>