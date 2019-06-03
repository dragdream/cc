<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%
    String type = TeeStringUtil.getString(request.getParameter("type"), null);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
function query(params){

	var columns = [
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'customerName',title:'客户名称',width:80,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.customerName+"')\">"+rowData.customerName+"</a>";
			}},
			{field:'customerNum',title:'客户编号',width:80},
			{field:'managePersonName',title:'负责人',width:100},
			{field:'type',title:'客户性质',width:60},
			{field:'unitType',title:'单位性质',align: 'center',width:80},
			{field:'addressDesc',title:'定位信息',align: 'center',width:120},
			{field:'companyPhone',title:'固定电话',align: 'center',width:100},
			{field:'customerType',title:'客户类型',align: 'center',width:80},
			{field:'industryType',title:'所属行业',align: 'center',width:120},
			{field:'customerSource',title:'客户来源',width:80},
			{field:'addTime',title:'录入日期',align: 'center',width:120},
			
		];
	
		//获取自定义字段在列表中显示的值
		  var url1=contextPath+"/TeeCrmCustomerController/getShowFieldListById.action";
	      var json1=tools.requestJsonRs(url1,null);
	      if(json1.rtState){
	    	  var data1=json1.rtData;
	    	  if(data1.length>0){
	    		  for(var i=0;i<data1.length;i++){
	    			  columns.push({
							field:"EXTRA_"+data1[i].sid,
							title:data1[i].extendFiledName});
	    		  }
	    	  }     	  
	      }
	      
	      columns.push({field:'2',title:'操作',align: 'center',width:60,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"showDetail("+rowData.sid+",'"+rowData.customerName+"')\">详情</a>";
			}});
	  	
	  	datagrid = $('#datagrid').datagrid({
	  		url:contextPath+'/TeeCrmCustomerController/datagrid.action?type='+type,
	  		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
	  		pagination:true,
	  		singleSelect:true,
	  		queryParams:params,
	  		toolbar:'#toolbar',//工具条对象
	  		checkbox:false,
	  		border:false,
	  		//idField:'formId',//主键列
	  		fitColumns:true,//列是否进行自动宽度适应
	  		columns:[columns]
	  	});
}

function addExtend(){
	
	
}
//查看客户详情
function showDetail(sid,customerName){
	var url = contextPath+"/system/subsys/crm/core/customer/index.jsp?sid="+sid+"&customerName="+customerName;
	openFullWindow(url,"客户信息");
	//location.href=url;
}
//添加客户
function add(){
	var url = contextPath+"/system/subsys/crm/core/customer/addOrEditCustomer.jsp";
	location.href=url;
}


//删除
function delCustomer(sid){
  $.MsgBox.Confirm("提示", "确定删除这些客户吗？", function() {
	var url = contextPath+ "/TeeCrmCustomerController/delCustomer.action?sids="+sid;
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
		delCustomer(ids);

 }


</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div class="topbar clearfix" id="toolbar">
</div>

</body>
</html>