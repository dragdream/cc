<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var datagrid;
function doInit(){
	getCrmCodeByParentCodeNo("CUSTOMER_TYPE","customerType");
	getCrmCodeByParentCodeNo("CUSTOMER_SOURCE","customerSource");
	getCrmCodeByParentCodeNo("INDUSTRY_TYPE","industry");
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeCrmCustomerInfoController/datagrid.action?type=2',//type=3代表公海客户，type=2 代表所有的客户，type =1 共享客户  没有type代表我的客户
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='#' onclick='showDetail("+rowData.sid+")'>查看</a>";
			}},
			{field:'customerName',title:'客户名称',width:100},
			{field:'type',title:'客户性质',width:80,formatter:function(data){
				if(data==1){
					return "客户";
				}else{
					return "供应商";
				}
			}},
			{field:'unitTypeDesc',title:'单位性质',width:80},
			{field:'managePersonName',title:'负责人',width:100},
			{field:'companyPhone',title:'固定电话',width:100},
			{field:'companyMobile',title:'移动电话',width:100},
			{field:'customerTypeDesc',title:'客户类型',width:100},
			{field:'industryDesc',title:'所属行业',width:100},
			{field:'customerSourceDesc',title:'客户来源',width:100},
			{field:'addTimeDesc',title:'录入日期',width:100}
		]]
	});
}
function showDetail(sid){
	var url = contextPath+"/system/subsys/crm/core/customInfo/detail.jsp?sid="+sid+"&type=2";
	location.href=url;
}

//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
	  $('#searchDiv').modal('hide');
	  
}

function exportCustomerInfo(){
	var url = contextPath+"/TeeCrmCustomerInfoController/exportCustomerInfo.action";
	document.form1.action=url;
	document.form1.submit();
	datagrid.datagrid("reload");
	$('#searchDiv').modal('hide');
	return true;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;客户管理</b>
		</div>
		<div style="text-align:left;margin-bottom:5px;">
			<button class="btn btn-primary" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-zoom-in"></i>&nbsp;高级查询</button>
		</div>
	<form id="form1" name="form1">
		<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="myModalLabel">查询条件</h4>
		      </div>
		      <div class="modal-body">
		        <table class="SearchTable" style="text-align:left;">
		        				<tr>
									<td class="SearchTableTitle">所属行业：</td>
									<td>
										<select class="BigSelect" id='industry' name='industry' style="width:180px;">
											<option value='0'>全部</option>
										</select>
									</td>
									<td class="SearchTableTitle">客户来源：</td>
									<td>
										<select class="BigSelect" id='customerSource' name='customerSource' style="width:180px;">
											<option value='0'>全部</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="SearchTableTitle">客户名称：</td>
									<td>
										<input class="BigInput" type="text" id = "customerName" name='customerName' style="width:180px;"/>
									</td>
									<td class="SearchTableTitle">客户类型：</td>
									<td>
										<select class="BigSelect" id='customerType' name='customerType' style="width:180px;">
											<option value='0'>全部</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="SearchTableTitle">时间范围：</td>
									<td colspan='3'>
										<input type="text" id='fromTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='fromTime' class="Wdate BigInput" />至
										<input type="text" id='toTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='toTime' class="Wdate BigInput" />
									</td>
								</tr>
								<tr>
									<td colspan='4' align='right'>
										<input type="reset" class="btn btn-primary" value="清空">
										<input type="hidden" id="type" name="type" value="2"/>
										<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
										<button class="btn btn-primary" onclick="exportCustomerInfo();"><i></i>&nbsp;导出</button>
									</td>
								</tr>
							</table>
		      </div>
		    </div>
		  </div>
		</div>
		</form>
	</div>
</body>
</html>