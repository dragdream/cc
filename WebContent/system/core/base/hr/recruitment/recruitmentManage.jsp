<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>招聘录用管理</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/recruitmentController/getRecruitList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:10},
			{field:'planName',title:'计划编号',width:50},
			{field:'position',title:'招聘岗位',width:80},
			{field:'hrPoolName',title:'应聘人姓名',width:50},
			{field:'recordPersonName',title:'录用负责人',width:50},
			{field:'recordTimeStr',title:'录入日期',width:50,formatter:function(value, rowData, rowIndex){
				if(value){
					value = value.substring(0,10);
				}
				return value;
			}},
			{field:'2',title:'操作',width:100,formatter:function(value, rowData, rowIndex){
				var str = "<a href='#' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>&nbsp;&nbsp;";
				//str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='editFunc("+rowData.sid+")'>建人事档案</a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='toAddOrUpdate("+rowData.sid+")'>编辑</a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}





/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "招聘录用详细信息";
  var url = contextPath + "/system/core/base/hr/recruitment/recruitmentDetail.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"850",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		if(v=="关闭"){
			return true;
		}
	}});
}

/**
 * 单个删除维护信息
 */
function deleteSingleFunc(sid){
	deleteObjFunc(sid);
}
/**
 * 批量删除
 */
function batchDeleteFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("请至少选择一项！");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
	$.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		var url = contextPath + "/recruitmentController/deleteObjById.action";
		var para = {sids:ids};
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！",function(){
				datagrid.datagrid('reload');
			});
		}  
	  });
}
/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}
//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
}

/**
 * 新建编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/core/base/hr/recruitment/addOrUpdate.jsp?sid=" + sid;
	window.location.href = url;
}

</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div style="text-align:left; margin:10px;">
			<button class="btn-win-white" onclick="toAddOrUpdate(0)">添加录用信息</button>&nbsp;&nbsp;
			<button class="btn-del-red" onclick="batchDeleteFunc()">批量删除</button>&nbsp;&nbsp;
			</div>

	</div>
</body>
</html>