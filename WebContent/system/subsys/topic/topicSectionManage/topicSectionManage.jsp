<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>板块管理</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeTopicSectionController/getTopicSectionList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:false,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'uuid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			{field:'uuid',checkbox:true,title:'ID',width:10},
			{field:'sectionName',title:'版块名称',width:50,formatter:function(value,rowData,rowIndex){
				return "<a href='#' onclick=showInfoFunc('" + rowData.uuid + "')>" +value + "</a>";
			}},
			{field:'managerName',title:'版主 ',width:80},
			{field:'crPrivName',title:'可发帖人员',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'viewPrivName',title:'版块可见人员  ',width:70},
			{field:'remark',title:'说明  ',width:50,formatter:function(value, rowData, rowIndex){
				return value;
			}},
			{field:'orderNo',title:'排序  ',width:20,formatter:function(value, rowData, rowIndex){
				return value;
			}},
		]]
	});
}

function updateFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("请选择数据行！");
		return ;
	}
	var idsArray = ids.split(",");
	if(idsArray.length>1){
		$.MsgBox.Alert_auto("此操作只能选择一条数据！");
		return ;
	}
	toAddOrUpdate(ids);
	
}


/**
 * 新建编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/subsys/topic/topicSectionManage/addOrUpdateTopicSection.jsp?uuid=" + sid;
	parent.window.location.href = url;
}




/**
 * 详情信息
 */
function showInfoFunc(sid){
  var url = contextPath + "/system/subsys/topic/topicSectionManage/topicSectionDetail.jsp?uuid=" + sid;
  parent.location.href = url;
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
		$.MsgBox.Alert_auto("请选择数据行！");
		return ;
	}
	
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
	 $.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		 var url = contextPath + "/TeeTopicSectionController/deleteObjById.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
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
		ids+=selections[i].uuid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}

function returnTopic(){
	var url = contextPath + "/system/subsys/topic/index.jsp";
	location.href = url;
}


</script>
</head>
<body onload="doInit()" >
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar" class="clearfix">
		<div class="fr right setHeight" style="position:static">
			
			<button class="btn-win-white" onclick="toAddOrUpdate('')">新建</button>
			<button class="btn-win-white" onclick="updateFunc()">修改</button>
			<button class="btn-del-red" onclick="batchDeleteFunc()">删除</button>
		</div>
		<br/>
	</div>
</body>
</html>