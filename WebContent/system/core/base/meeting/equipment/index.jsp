<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/meetEquipmentManage/datagrid.action',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'equipmentName',title:'设备名称',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;";
			}}
		]]
	});
}
function add(){
	var url = contextPath+"/system/core/base/meeting/equipment/addOrUpdate.jsp";
	location.href=url;
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/core/base/meeting/equipment/addOrUpdate.jsp?sid="+sid;
	location.href=url;
}

function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
// 		top.$.jBox.tip("请选择需要删除项目","info");
		$.MsgBox.Alert_auto("请选择需要删除项目");
		return;
	}
	$.MsgBox.Confirm("提示", "确定删除所选记录,删除后将不可恢复！",function(){
// 		if(v=="ok"){
			var sids="";
			for(var i=0;i<selections.length;i++){
				 sids += selections[i].sid+",";
			}
			var url = contextPath+"/meetEquipmentManage/deleteById.action";
			var json = tools.requestJsonRs(url,{sids:sids});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
// 		}
	});
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" style="min-width:950px;padding-left: 10px;padding-right: 10px;border-bottom:none;">
		<div class="base_layout_top" style="position:static">
		    <img class = 'fl' style="margin-right: 10px; margin-top: 5px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hysbglq/icon_meeting_device.png"/>
		    <p class="title" style="padding-top: 4px;">会议设备管理</p>
		    <button class="btn-del-red fr" style="margin-top: -17px;margin-right: 50px;" onclick="del()">批量删除</button>
			&nbsp;
            <button class="btn-win-white fr" style="margin-top: -17px;margin-right: 15px;" onclick="add()">添加</button>
		</div>
		<span class="basic_border fl" style=" margin-top: -1px;"></span>
	</div>
</body>
</html>