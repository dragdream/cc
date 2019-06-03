<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>全部合同</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var datagrid;
function doInit(){
	query();
}	
function query(params){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeHumanContractController/queryAllContract.action',
		pagination:true,
		singleSelect:false,
		queryParams:params,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'conTitle',title:'合同标题',width:100,formatter:function(e,rowData,index){
				return "<a href='#' onclick=\"showDetail("+rowData.sid+")\">"+rowData.conTitle+"</a>";
			}},
			{field:'conCode',title:'合同编号',width:100},
			{field:'conTypeDesc',title:'合同类型',width:100},
			{field:'conAttrDesc',title:'合同属性',width:100},
			{field:'conStatusDesc',title:'合同状态',width:100},
			{field:'validTimeDesc',title:'生效日期',width:100},
			{field:'endTimeDesc',title:'结束时间',width:100},
			{field:'signCount',title:'签约次数',width:100},
			{field:'remark',title:'备注',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				if(rowData.renewDateDesc!="" && rowData.renewDateDesc!=null){
					return "<a href='javascript:void();' onclick='del("+rowData.sid+")'>删除</a>";
				}else{
					return "<a href='javascript:void();' onclick='renew("+rowData.sid+")'>续签</a>&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+rowData.sid+")'>删除</a>";
				}
			}}
		]]
	});
}
function showDetail(sid){
	var title="查看详情";
	var url = contextPath+"/system/core/base/pm/archivesManage/humanContracts/ht_detail.jsp?sid="+sid;
	bsWindow(url,title,{width:"700",height:"300",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
	     if(v=="关闭"){
	    	datagrid.datagrid("unselectAll");
			return true;
		}
	}});
}

/**
 * 合同续签
 */
function renew(sid){
	var title="合同续签";
	var url = contextPath+"/system/core/base/pm/archivesManage/humanContracts/ht_edit.jsp?sid="+sid+"&renew=1";
	bsWindow(url ,title,{width:"700",height:"300",buttons:
		[
		 {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			cw.commit(function(){
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
				window.location.reload();
			});
		}else if(v=="关闭"){
			datagrid.datagrid("unselectAll");
			return true;
		}
	}});
}

function del(sid){
	 $.MsgBox.Confirm ("提示", "确认删除选中信息嘛？删除后不可恢复！",function(){
					var url = contextPath+"/TeeHumanContractController/delHumanContract.action";
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

function back(){
	var url = contextPath+"/system/core/base/pm/archivesManage/humanContracts/index.jsp";
	location.href = url;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<br/>
	
	</div>
</body>
</html>