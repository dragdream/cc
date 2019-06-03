<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/filter.js"></script>

<title>招聘筛选</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/hrFilterController/getHrFilterList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'hrPoolName',title:'应聘者姓名',width:60},
			{field:'position',title:'应聘岗位',width:80},
			{field:'employeeMajor',title:'	所学专业',width:60},
			{field:'employeePhone',title:'联系电话',width:40},
			{field:'sendPersonName',title:'发起人',width:40},
			{field:'sendPersonId',title:'发起人Id',hidden:true},
			{field:'filterState',title:'筛选状态',formatter:function(value, rowData, rowIndex){
				var filterStateDesc = "待审批";
				if(value == '1'){
					filterStateDesc = "<font color='green'>已通过</font>";
				}else if(value == '2'){
					filterStateDesc = "<font color='red'>未通过</font>";
				}
				return filterStateDesc;
			}},
			{field:'nextDatetimeStr',title:'面试时间'},
			
			{field:'2',title:'操作',width:80,formatter:function(value, rowData, rowIndex){
				var str = "<a href='javascript:void(0)' onclick='handleDetail("+rowData.sid+")'>详细信息 </a>";
				var  filterState = rowData.filterState;
				if(filterState == '0'){
					str += "&nbsp;&nbsp;<a href='#' onclick='toHandle("+rowData.sid+")'>办理</a>";
				}
				str += "&nbsp;&nbsp;<a href='#' onclick='toAddOrUpdate("+rowData.sid+")'>修改</a>";
			
				str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='deleteObjFunc("+rowData.sid+")'>删除</a>";
				
				return str;
			}}
		]]
	});
}




/**
 * 编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/core/base/hr/filter/addOrUpdate.jsp?sid=" + sid;
	window.location.href = url;
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
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
	  $.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		  var url = contextPath + "/hrFilterController/deleteObjById.action";
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

/**
 * 办理
 */
function toHandle(sid){
	window.location.href = "handle.jsp?sid=" + sid
}

</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		
		<div style="text-align:left;margin: 10px;">
			<button class="btn-win-white" onclick="toAddOrUpdate(0)">添加招聘筛选</button>&nbsp;&nbsp;
			<button class="btn-del-red" onclick="batchDeleteFunc()">批量删除</button>&nbsp;&nbsp;
			</div>

	</div>
</body>
</html>