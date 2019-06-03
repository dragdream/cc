<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script>
//var parentWindowObj = window.dialogArguments;
var datagrid;
var cusId = "<%=customerId%>";
var customerId = "<%=customerId%>";
var customerName = "<%=customerName%>";
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmContractsController/datagrid.action?cusId='+cusId, //合同列表
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:false,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'contractsNo',title:'合同编号',width:80,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.customerName+"')\">"+rowData.contractsNo+"</a>";
			}},
			{field:'customerName',title:'客户名称',width:120},
			{field:'orderNo',title:'销售订单编号',width:120},
			{field:'contractsStatusDesc',title:'状态',width:80},
			{field:'contractsStartTimeDesc',title:'开始日期',width:100},
			{field:'contractsEndTimeDesc',title:'结束日期',width:100},
			{field:'contractsAmount',title:'合同金额（元）',width:100},
			{field:'managePersonName',title:'负责人',width:80},
			{field:'addPersonName',title:'创建人',width:100},
			{field:'createTimeDesc',title:'创建时间',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.customerName+"')\">查看</a>";
			}}
		]],
	});
}


function showDetail(sid,customerName){
	var url = contextPath+"/system/subsys/crm/core/customer/contracts/contractsInfo.jsp?sid="+sid+"&customerName="+customerName+"&customerId="+cusId;
	location.href=url;
}

//添加合同
function add(){
	  var title = "新建合同";
	  var url = contextPath+"/system/subsys/crm/core/customer/contracts/addOrUpdate.jsp?customerId="+cusId+"&customerName="+customerName;
	  bsWindow(url ,title,{width:"700",height:"300",buttons:
			[
			 {name:"确定",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="确定"){
				cw.save(function(){
				  window.location.reload();
				});
			}else if(v=="关闭"){
				return true;
			}
		}});
}


</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
<div class="topbar clearfix" id="toolbar">
   <div class="fl">
      <input type="button" value="新建合同" class="btn-win-white" onclick="add();"/>
   </div>
</div>

</body>

</html>