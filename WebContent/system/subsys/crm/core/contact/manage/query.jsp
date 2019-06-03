<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	String callBackFunc = request.getParameter("callBackFunc") == null ? "" : request.getParameter("callBackFunc")  ;
     callBackFunc = callBackFunc.replace("\"", "\\\"");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>

<title>合同查询</title>

<script type="text/javascript" charset="UTF-8">
var callBackFunc = "<%=callBackFunc%>";
var parentWindowObj = window.dialogArguments;
var to_id_field ;
var to_name_field ;
var datagrid;
var userForm;
var title ="";
$(function() {
	userForm = $('#form1').form();
	datagrid = $('#datagrid').datagrid({
		url : contextPath + '/teeCrmContractController/manager.action' ,
		toolbar : '#toolbar',
		title : title,
		//iconCls : 'icon-save',
		iconCls:'',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : false,
		nowrap : false,
		border : false,
		idField : 'sid',
		singleSelect:true,

		columns : [ [
		     {field:'sid',title:"选择",width:"80",align:"center",
		    	 formatter : function(value, rowData, rowIndex) {
		    		 var itemStr =  "<input type='button' size='5' onclick='selectData("+rowData.sid+" , this);' contractNo='" + rowData.contractNo+ "' contractName='" + rowData.contractName + "' value='选择数据' class='btn btn-warning btn-xs'/>";
		    		 return itemStr;
				} 
		     },{
			field : 'contractNo',
			title : '合同编号',
			width : 90,
			formatter : function(value, rowData, rowIndex) {
				return "<center><a href='javascript:void(0);'   onclick=\"toDataInfo("  +rowData.sid   + ");\">" + value + "</a></center>";
			}
		},  {
			field : 'contractName',
			title : '合同名称',
			width : 180
		}, {
			field : 'customerInfoName',
			title : '客户名称',
			width : 150
		},  {
			field : 'paymentMethodDesc',
			title : '支付方式',
			width : 80
		 }, {
			field : 'contractAmount',
			title : '合同金额',
			width : 70,
			align:'center'

		 }, {
			field : 'responsibleUserName',
			title : '负责人',
			width : 70,
			align:'center'
		 } ,{
				field : 'contractSignDate',
				title : '签订日期',
				width : 70	,
				formatter : function(value, rowData, rowIndex) {
					return getFormatDateTimeStr(value ,'yyyy-MM-dd');
				},
				align:'center'
		 }
	  
	] ],onLoadSuccess:onLoadSuccessFunc
});		
	
});
/**
* 获取最大记录数
*/
function onLoadSuccessFunc(){

}


function showDetail(sid){
	var url = contextPath+"/system/subsys/crm/core/customInfo/detail.jsp?sid="+sid;
	location.href=url;
}
function selectData(sid , obj){
	var contractNo = $(obj).attr("contractNo");
	var contractName = $(obj).attr("contractName");
	var customerInfoArray = xparent["contractInfoArray"];
	var id = xparent.document.getElementById(customerInfoArray[0]);
	id.value = sid;
	var contNo = xparent.document.getElementById(customerInfoArray[1]);
	contNo.value = contractNo;
	var contName = xparent.document.getElementById(customerInfoArray[2]);
	contName.value = contractName;
	CloseWindow();
}
//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
}

</script>
</head>
<table id="datagrid"></table>
<div id="toolbar" style=""> 
  	  <form id="form1" name="form1">
		<table class="TableBlock" width="60%" align="center" style="padding:5px;" >
   			<tr>
   				 <td nowrap class="TableData" colspan="2">
			  		合同编号:
					<input type="text" name="contractNo"  value="" class="BigInput"> 
					合同名称:
					<input type="text" name="contractName" value="" class="BigInput" > 
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="查询" class="btn btn-primary" onclick="doSearch()">
  			</tr>	
   		</table>
   		
	</form>
	<br>
	
	
</div>
</body>
</html>
</body>
</html>