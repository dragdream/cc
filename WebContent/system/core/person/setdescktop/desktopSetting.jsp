<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片库管理</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
var desktop='<%=person.getDesktop()%>';
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/teePortalTemplateController/datagrid.action?type=1",
		pagination:true,
		singleSelect:false,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'templateName',title:'模块名称',width:120},
			{field:'userPrivDesc',title:'人员权限',width:200},
			{field:'deptPrivDesc',title:'部门权限',width:300},
			{field:'rolePrivDesc',title:'角色权限',width:300},
			{field:'sortNo',title:'排序号',width:100},
			{field:'2',title:'操作',width:300,formatter:function(e,rowData,index){
				if(rowData.sid == desktop){
					return "<a href='#' onclick='reset("+index+")'>重置</a>";
				}else{
					return "<a href='javascript:void(0);' onclick='setDefault("+index+")'>设置为默认</a>";
				}
				
				//<a href='javascript:void();' onclick='designTemplate("+index+")'>查看</a>&nbsp;&nbsp;&nbsp;
			}}
		]]
	});
}

function designTemplate(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要修改的内容!");
		//$.MsgBox.Alert("提示", "请选择需要修改的内容!");
		return;
	}
	var sid = selection.sid;
	var url=contextPath+"/system/core/system/sysPorlet/desktop/design.jsp?sid="+sid+"&type=1";
	location.href = url;
}

function reset(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要重置的内容!");
		//$.MsgBox.Alert("提示", "请选择需要重置的内容!");
		return;
	}
	var sid = selection.sid;
	var url = contextPath+"/teePortalTemplateUserDataController/reset.action?templateId="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
			datagrid.datagrid("reload");
		    $.MsgBox.Alert_auto("重置成功！");
	}else{
		$.MsgBox.Alert_auto("重置失败！");
		//$.MsgBox.Alert("提示", "重置失败！");
	}
}


function setDefault(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要重置的内容！");
		//$.MsgBox.Alert("提示", "请选择需要重置的内容！");
		return;
	}
	var sid = selection.sid;
	var url = contextPath+"/teePortalTemplateUserDataController/setDefault.action?templateId="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
	    datagrid.datagrid("reload");
		$.MsgBox.Alert_auto("设置成功！");
	}else{
		$.MsgBox.Alert_auto("设置失败！");
	}
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	 <div id="toolbar" class="topbar clearfix">
         <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_zmmblb.png" alt="" />
         &nbsp;<span class="title">桌面模板列表</span>
     </div>
</body>
</html>