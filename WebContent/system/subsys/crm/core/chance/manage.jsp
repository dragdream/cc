<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售机会管理</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/afterSaleService/js/saleService.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript">
function doInit(){
	getInfoList();
}

var datagrid;
//列表
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/crmChanceController/getChanceInfoList.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:1},
			{field:'customerName',title:'客户名称',width:60},
			{field:'chanceName',title:'机会名称',width:80},
			/* {field:'forcastTime',title:'预计成交日期',width:35,formatter:function(data,rowData,index){
				return getFormatDateStr(data,"yyyy-MM-dd");
			}}, */
			{field:'forcastTimeStr',title:'预计成交日期',width:35},
			{field:'forcastCost',title:'预计成交金额',width:35},
			{field:'crUserName',title:'创建人姓名',width:40},
			{field:'createTimeStr',title:'创建时间',width:80},
			{field:'2',title:'操作',width:50,formatter:function(value, rowData, rowIndex){
				var str = "<a href='#' onclick='showInfoChance("+rowData.sid+")'>详情</a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='toAddOrUpdate("+rowData.sid+")'>编辑</a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='deleteSingleChance("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}

//删除单个
function deleteSingleChance(sid){
	$.jBox.confirm("是否确认删除？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/crmChanceController/deleteSingleChance.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.jBox.tip(json.rtMsg,"info");
				datagrid.datagrid('reload');
				datagrid.datagrid('unselectAll');
				return true;
			}
			$.jBox.tip(json.rtMsg,"error");
		}
	});	
}
//查看详情 
function showInfoChance(sid){
	var url = contextPath + "/system/subsys/crm/core/chance/chanceDetail.jsp?sid=" + sid;
	location.href = url;

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
 * 批量删除
 */
function batchDeleteChance(){
	
	var ids = getSelectItem();
	if(ids.length==0){
		alert("至少选择一项");
		return ;
	}
	deleteChances(ids);
}

/**
 * 批量删除维护信息
 */
function deleteChances(ids){
  $.jBox.confirm("确定要删除所选中记录？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/crmChanceController/deleteChances.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.jBox.tip("删除成功！", "info", {timeout: 1800});
				datagrid.datagrid('reload');
			}
		}
	});
}

/**
 * 编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/subsys/crm/core/chance/addOrUpdate.jsp?sid=" + sid;
	window.location.href = url;
}

</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div style="padding:10px;">
			<button class="btn btn-primary" onclick="window.location = 'addOrUpdate.jsp'">新增机会</button>&nbsp;&nbsp;
			<button class="btn btn-danger" onclick="batchDeleteChance()">批量删除</button>&nbsp;&nbsp;
		</div>
	</div>
</body>
</html>