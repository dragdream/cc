<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String flowId = request.getParameter("flowId");
	String conditionId = request.getParameter("conditionId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
</style>
<script>
var flowId = "<%=flowId%>";
var conditionId = "<%=conditionId%>";

function doInit(){
	//根据流程获取表单字段
	var formItems = loadItems();
	
	if(conditionId!="null"){
		var json = tools.requestJsonRs(contextPath+"/report/getCondition.action",{conditionId:conditionId});
		bindJsonObj2Cntrl(json.rtData);
	}
	
	$('#datagrid').datagrid({
		data:formItems,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		onClickCell: onClickCell,
		columns:[[
			{field:'title',title:'控件名称',width:150},
			{field:'oper',title:'条件',width:50,editor:{
                type:'combobox',
                options:{
                    valueField:'val',
                    textField:'text',
                    data:[{val:"无",text:"无"},
                          {val:"包含",text:"包含"},
                          {val:"等于",text:"等于"},
                          {val:"大于",text:"大于"},
                          {val:"小于",text:"小于"},
                          {val:"大于等于",text:"大于等于"},
                          {val:"小于等于",text:"小于等于"},
                          {val:"开始为",text:"开始为"},
                          {val:"结束为",text:"结束为"},
                          {val:"不包含",text:"不包含"}],
                    required:false
            	}
			}},
			{field:'val',title:'值',width:50,editor:'text'}
		]]
	});
}

function loadItems(){
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var items = json.rtData;
	var formItems = [];
	for(var i=0;i<items.length;i++){
		items[i]["oper"] = "无";
		items[i]["val"] = "";
		items[i]["model"] = "";
		items[i]["content"] = "";
		formItems.push(items[i]);
	}
	
	if(conditionId!="null"){
		var json = tools.requestJsonRs(contextPath+"/report/getConditionItems.action",{conditionId:conditionId});
		var list = json.rtData;
		for(var i=0;i<list.length;i++){
			for(var j=0;j<items.length;j++){
				if(list[i].itemId==items[j].itemId){
					items[j]["oper"] = list[i].oper;
					items[j]["val"] = list[i].val;
					break;
				}
			}
		}
	}
	
	return formItems;
}

function commit(){
	var para = tools.formToJson($("#toolbar"));
	
	//组合列表数据
	var rows = $("#datagrid").datagrid("getRows");
	var filter = [];
	for(var i=0;i<rows.length;i++){
		if(rows[i].oper!="无"){
			filter.push(rows[i]);
		}
	}
	
	var listJson = tools.jsonArray2String(filter);
	para["listJson"] = listJson;
	
	var url = "";
	if(conditionId=="null"){//保存
		url = contextPath+"/report/saveCondition.action";
	}else{
		url = contextPath+"/report/updateCondition.action";	
	}
	
	var json = tools.requestJsonRs(url,para);
	alert("保存成功");
	
	if(conditionId=="null"){//新增
		window.location = "conditionAddOrUpdate.jsp?flowId="+flowId+"&conditionId="+json.rtData;
	}
}
</script>
</head>
<body onload="doInit()">
<div id="toolbar">
	<input name="flowId" type="hidden" value="<%=flowId %>" />
	<input name="conditionId" type="hidden" value="<%=conditionId %>" />
	<table id="table" style="font-size:12px;width:800px">
		<tr>
			<td>条件名称：</td>
			<td>
				<input type="text" class="BigInput" name="conditionName" id="conditionName"/>
			</td>
			<td>流程状态：</td>
			<td>
				<select id="flowFlag" name="flowFlag" class="BigSelect">
					<option value="1">已完成</option>
					<option value="2">办理中</option>
					<option value="3">已完成与办理中</option>
					<option value="4">所有流程（包含已删除的流程）</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>发起人：</td>
			<td>
				<input type="hidden" class="BigInput" name="beginUser" id="beginUser"/>
				<input type="text" class="BigInput readonly" name="beginUserNames" id="beginUserNames" readonly/>
				<a href="javascript:void(0)" onclick="selectUser(['beginUser','beginUserNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('beginUser','beginUserNames')">清空</a>				
			</td>
			<td>发起人部门：</td>
			<td>
				<input type="hidden" class="BigInput" name="beginDept" id="beginDept"/>
				<input type="text" class="BigInput readonly" name="beginDeptNames" id="beginDeptNames" readonly/>
				<a href="javascript:void(0)" onclick="selectDept(['beginDept','beginDeptNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('beginDept','beginDeptNames')">清空</a>		
			</td>
		</tr>
		<tr>
			<td>发起人角色：</td>
			<td>
				<input type="hidden" class="BigInput" name="beginRole" id="beginRole"/>
				<input type="text" class="BigInput readonly" name="beginRoleNames" id="beginRoleNames" readonly/>
				<a href="javascript:void(0)" onclick="selectRole(['beginRole','beginRoleNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('beginRole','beginRoleNames')">清空</a>		
			</td>
			<td>时间范围：</td>
			<td>
				<select id="timeRange" name="timeRange" class="BigSelect">
					<option value="0">无</option>
					<option value="1">以发起时间为准</option>
					<option value="2">以结束时间为准</option>
					<option value="3">以发起时间和结束时间为准</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				起始时间：
			</td>
			<td>
				<input type="text" class="BigInput Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="time1Desc" id="time1Desc" readonly/>
			</td>
			<td>
				结束时间：
			</td>
			<td>
				<input type="text" class="BigInput Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="time2Desc" id="time2Desc" readonly/>
			</td>
		</tr>
		<tr>
			<td>
				<button class="btn btn-primary" type="button" onclick="commit()">保存</button>
			</td>
			<td>
			</td>
			<td></td>
			<td>
			</td>
		</tr>
	</table>
</div>
<table id="datagrid"  fit="true"></table>
</body>
<script type="text/javascript">
$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});

var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#datagrid').datagrid('validateRow', editIndex)){
        $('#datagrid').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickCell(index, field){
    if (endEditing()){
        $('#datagrid').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
        editIndex = index;
    }
}
</script>
</html>