<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	String callBackFunc = request.getParameter("callBackFunc") == null ? "" : request.getParameter("callBackFunc")  ;
    callBackFunc = callBackFunc.replace("\"", "\\\"");
    String customerId = TeeStringUtil.getString(request.getParameter("customerId"), "0");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>

<title>客户查询</title>

<script type="text/javascript" charset="UTF-8">
var callBackFunc = "<%=callBackFunc%>";
var cusId = <%=customerId%>;
//var parentWindowObj = window.dialogArguments;
var xparent;
var datagrid;
var userForm;
var to_id_field ;
var to_name_field ;
$(function(){
 	var chanceInfoArray = xparent["chanceInfoArray"];
	var roleCntrl = chanceInfoArray[0];
    var roleDescCntrl = chanceInfoArray[1];
    to_id_field = xparent.document.getElementById(roleCntrl);
    to_name_field = xparent.document.getElementById(roleDescCntrl); 
   
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/crmChancesController/datagrid.action?cusId='+cusId,
		toolbar : '#toolbar',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		title :  "",
		iconCls:'',
		pagination : true,
		pageSize : 10,
		fit : true,
		fitColumns : false,
		nowrap : false,
		border : false,
		idField : 'sid',
		singleSelect:true,
		columns:[[
					{field:'sid',checkbox:true,title:'ID',width:100},
					{field:'2',title:'操作',width:40,formatter:function(e,rowData){
						return "<a href='javascript:void(0);' onclick='selectChances("+rowData.sid+",\""+rowData.chanceName+"\")'>选择</a>";
					}},
					{field:'chanceName',title:'商机名称',width:100},
					{field:'customerName',title:'客户',width:180},
					{field:'forcastTimeDesc',title:'预计成交日期',align: 'center',width:100},
					{field:'forcastCost',title:'金额',align: 'center',width:100},
					{field:'chanceStatusDesc',title:'状态',align: 'center',width:60},
					{field:'managePersonName',title:'负责人',align: 'center',width:100},
					{field:'createTimeDesc',title:'创建时间 ',align: 'center',width:150},
					{field:'addPersonName',title:'创建人',align: 'center',width:100},
				
		]]
	});
		
});

function selectChances(sid,chanceName){
	var chanceInfoArray = xparent["chanceInfoArray"];
	to_id_field.value=sid;
	to_name_field.value=chanceName;
	arguments.length = 2;//参数调用
	arguments[0] = sid;
	arguments[1] = chanceName;
	trigger_callback(callBackFunc,arguments); 
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
<body style="padding: 10px;">		 
<table id="datagrid"></table>
<!-- <div id="toolbar" style=""> 
	<div class="moduleHeader">
		<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;查询条件</b>
	</div>
  <form id="form1" name="form1">
			<div style="margin-top:10px;" id="searchDiv" >
				<table class="SearchTable" style="text-align:left;">
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
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
						</td>
					</tr>
	
				</table>
			</div>
		</form>
</div> -->
</body>
</html>