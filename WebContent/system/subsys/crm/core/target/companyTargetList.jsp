<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<style>
</style>
<script>
var datagrid;
var userId = <%=loginPerson.getUuid()%>;
function doInit(){
	
	getInfoList();
}

function getInfoList(){
	
	datagrid = $('#datagrid').datagrid({		
	    url : contextPath + '/crmCompanyTargetController/getCompanyTargetList.action',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns : [ [
			{field:'year',title:'财年',width:80},
			{field:'m1',title:'1月',width:60},
			{field:'m2',title:'2月',width:60},
			{field:'m3',title:'3月',width:60},
			{field:'m4',title:'4月',width:60},
			{field:'m5',title:'5月',width:60},
			{field:'m6',title:'6月',width:60},
			{field:'m7',title:'7月',width:60},
			{field:'m8',title:'8月',width:60},
			{field:'m9',title:'9月',width:60},
			{field:'m10',title:'10月',width:60},
			{field:'m11',title:'11月',width:60},
			{field:'m12',title:'12月',width:60},
			{field:'total',title:'总额',width:80},
			{field:'createTimeStr',title:'创建时间',width:80},
			{field:'crUserName',title:'创建人',width:80},
			{field:'2',title:'操作',width:80,formatter:function(value, rowData, rowIndex){
				var str = "&nbsp;&nbsp;<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>";
				str += "&nbsp;&nbsp;<a href='#' onclick='deleteCompanyTarget("+rowData.sid+")'>删除</a>";
				return str;
			}}
		] ]
  });
}


function deleteCompanyTarget(sid){
	$.jBox.confirm("是否确认删除？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/crmCompanyTargetController/deleteCompanyTarget.action";
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


function addCompanyTarget(){
	window.location = contextPath+"/system/subsys/crm/core/target/addOrUpdateCompanyTarget.jsp";
}

function edit(sid){
	window.location = contextPath+"/system/subsys/crm/core/target/addOrUpdateCompanyTarget.jsp?sid="+sid;
}






</script>
</head>
<body onload="doInit();">
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<table width="100%">
			<tr>
				<td>
					<span class="easyui_h1"><i class="glyphicon glyphicon-list-alt"></i>&nbsp;公司目标</span>
				</td>
				<td align=right>
					<button class="btn btn-info" onclick="datagrid.datagrid('reload')"><i class="glyphicon glyphicon-refresh"></i>&nbsp;刷新</button>
					&nbsp;
					<button class="btn btn-info" onclick="addCompanyTarget()">新增目标</button>
					&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</div>
</div>
<table id="datagrid" fit="true" ></table>
	


</body>
</html>