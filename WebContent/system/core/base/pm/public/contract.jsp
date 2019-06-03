<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var datagrid;
function doInit(){
	getHrCodeByParentCodeNo("PM_CONTRACT_TYPE" , "conType");
	getHrCodeByParentCodeNo("PM_CONTRACT_STATUS" , "conStatus");
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeHumanContractController/queryDueToContract.action',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'conTitle',title:'合同标题',width:100},
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
					return "<a href='javascript:void();' onclick='showDetail("+rowData.sid+")'>查看</a>";
				}else{
					return "<a href='javascript:void();' onclick='renew("+rowData.sid+")'>续签</a>&nbsp;&nbsp;<a href='javascript:void();' onclick='showDetail("+rowData.sid+")'>查看</a>";
				}
			}}
		]]
	});
}
function showDetail(sid){
	datagrid.datagrid("unselectAll");
	bsWindow(contextPath+"/system/core/base/pm/manage/ht_detail.jsp?sid="+sid,"查看详情",{width:"700",height:"300",buttons:[{name:"关闭",classStyle:"btn btn-primary"}],submit:function(v,h){return true;}});
}

//根据条件查询
function doSearch(){
	  var queryParams=tools.formToJson($("#form1"));
	  datagrid.datagrid('options').queryParams=queryParams; 
	  datagrid.datagrid("reload");
}

/**
 * 合同续签
 */
function renew(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/core/base/pm/manage/ht_edit.jsp?sid="+sid+"&renew=1";
	bsWindow(url,"合同续签",{width:"700",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}

function back(){
	var url = contextPath+"/system/core/base/pm/manage/index.jsp";
	location.href = url;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div style="text-align:left;margin-bottom:5px;">
		</div>
	<form id="form1" name="form1">
		<div style="margin-top:10px;margin-bottom:10px;" id="searchDiv">
					<table class="TableBlock" style="text-align:left;margin:0 auto;width:80%;">
						<tr class='TableData'>
							<td class="SearchTableTitle">合同标题：</td>
							<td>
								<input class="BigInput" type="text" id = "conTitle" name='conTitle' style="width:250px;"/>
							</td>
							<td class="SearchTableTitle">合同编号：</td>
							<td>
								<input class="BigInput" type='text' id='conCode' name='conCode' style="width:165px;"/>
							</td>
						</tr>
						<tr class='TableData'>
							<td class="SearchTableTitle">合同类型：</td>
							<td>
								<select class="BigSelect" id='conType' name='conType' style="width:180px;">
									<option value="全部">全部</option>
								</select>
							</td>
							<td class="SearchTableTitle">合同状态：</td>
							<td>
								<select class="BigSelect" id='conStatus' name='conStatus' style="width:180px;">
									<option value="全部">全部</option>
								</select>
								<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
							</td>
						</tr>
					</table>
		</div>
		</form>
	</div>
</body>
</html>