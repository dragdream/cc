<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%
    String type = TeeStringUtil.getString(request.getParameter("type"), null);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线索列表</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var datagrid;
var type = <%=type%>;
function doInit(){
	query();
}

//查询
function query(params){
	getCrmCodeByParentCodeNo("CLUE_SOURCE","clueSource"); //线索来源
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmClueController/datagrid.action?type='+type,  //线索列表
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:false,
		queryParams:params,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'name',title:'姓名',width:80,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.name+"')\">"+rowData.name+"</a>";
			}   
			},
			{field:'companyName',title:'公司',width:80},
			{field:'managePersonName',title:'负责人',align: 'center',width:100},
			{field:'clueSourceDesc',title:'来源',align: 'center',width:100},
			{field:'telephone',title:'电话',align: 'center',width:80},
			{field:'culeDetail',title:'销售线索详情',align: 'center',width:120},
			{field:'clueStatusDesc',title:'状态',align: 'center',width:60},
			{field:'addPersonName',title:'创建人',align: 'center',width:100},
			{field:'createTimeDesc',title:'创建时间',align: 'center',width:120},
			{field:'2',title:'操作',align: 'center',width:100,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+")\">详情</a>&nbsp;&nbsp;&nbsp;";
			}},
		]]
	});
}

//查看线索详情
function showDetail(sid){
	var url = contextPath+"/system/subsys/crm/core/clue/clueInfoDetial.jsp?sid="+sid;
	openFullWindow(url);
}


//删除
function deleteById(sid){
  $.MsgBox.Confirm("提示", "确定删除选中线索嘛吗？", function() {
	var url = contextPath+ "/TeeCrmClueController/delById.action?sids="+sid;
	var json = tools.requestJsonRs(url, {sids : sid});
	if (json.rtState) {
		$.MsgBox.Alert_auto(json.rtMsg);
	} else {
		$.MsgBox.Alert_auto(json.rtMsg);
	}
	datagrid.datagrid("unselectAll");
	datagrid.datagrid("reload");
 });
}

//批量删除客户
function delAll(){
    	var selections = $('#datagrid').datagrid('getSelections');
    	if(selections.length==0){
    		$.MsgBox.Alert_auto("至少选择一项!");
    		return ;
    	}
    	var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=selections[i].sid;
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		deleteById(ids);

 }

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div class="topbar clearfix" id="toolbar">
    </div>

</body>
</html>