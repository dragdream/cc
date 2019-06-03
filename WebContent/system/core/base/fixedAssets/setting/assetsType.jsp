<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp"%>
<%@include file="/header/easyui2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeFixedAssetsCategoryController/datagrid.action",
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
			{field:'sortNo',title:'排序号',width:100},
			{field:'name',title:'资产类别名称',width:200},
			{field:'2',title:'操作',width:300,formatter:function(e,rowData,index){
				return "<a href='#' onclick='edit("+index+")'>修改</a>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+index+")'>删除</a>";
			}}
		]]
	});
}

function edit(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要修改的内容！");
		return;
	}
	var sid = selection.sid;
	var url = contextPath+"/system/core/base/fixedAssets/setting/assetsType_edit.jsp?sid="+sid;
	location.href=url;
}

function del(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要删除的类别信息！");
		return;
	}
	var sid = selection.sid;
	parent.$.MsgBox.Confirm ("提示", "确认删除该类别信息吗？",function(){
			var url = contextPath+"/TeeFixedAssetsCategoryController/delAssetsType.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
	});
}

function delAll(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.MsgBox.Alert_auto("请选择需要删除项目！");
		return;
	}
	parent.$.MsgBox.Confirm ("提示", "确认删除选中信息嘛？",function(){
			for(var i=0;i<selections.length;i++){
			    var sid = selections[i].sid;
					var url = contextPath+"/TeeFixedAssetsCategoryController/delAssetsType.action";
					var json = tools.requestJsonRs(url,{sid:sid});
					if(json.rtState){
						$.MsgBox.Alert_auto(json.rtMsg);
					}else{
						$.MsgBox.Alert_auto(json.rtMsg);
					}
				}
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
	});
}
</script>

</head>
<body onload="doInit();" style="font-size:12px">
	<table id="datagrid" fit="true"></table>
	  <div id="toolbar" class="clearfix">
	  <div class="setHeight fl" >
		<input style="width:45px;height:25px;" class="btn-win-white" type="button" onclick="window.location = 'assetsType_add.jsp'" value="添加"/>
	    <input style="width:65px;height:25px;" class="btn-del-red" type="button" onclick="delAll()" value="批量删除"/>
	 </div>
	</div>
	
</body>
</html>