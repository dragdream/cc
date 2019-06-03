<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<%
	String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
%>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
td{
padding:5px;
}
</style>
<script>
var uuid = "<%=uuid%>";
var data = [];
function doInit(){
	//获取视图
	var json = tools.requestJsonRs(contextPath+"/bisView/listBisView.action?rows=1000000&page=1");
	var rows = json.rows;
	var render = [];
	for(var i=0;i<rows.length;i++){
		render.push("<option value="+rows[i].identity+">"+rows[i].name+"</option>");
	}
	$("#bisViewId").html(render.join(""));
	
	//获取业务表
	var json = tools.requestJsonRs(contextPath+"/bisTable/datagrid.action?rows=1000000&page=1");
	var rows = json.rows;
	var render = [];
	for(var i=0;i<rows.length;i++){
		render.push("<option value="+rows[i].sid+">"+rows[i].tableName+"("+rows[i].tableDesc+")</option>");
	}
	$("#bisTableId").html(render.join(""));
	
	//获取表单
	ZTreeTool.comboCtrl($("#formId"),{url:contextPath+"/formSort/getSelectCtrlDataSource.action",callback:function(treeNode){
		resetDatagrid();
	}});
	
	if(uuid!=""){
		var json = tools.requestJsonRs(contextPath+"/bisModule/getBisModule.action?uuid="+uuid);
		bindJsonObj2Cntrl(json.rtData);
		try{
			data = eval("("+json.rtData.mapping+")");
		}catch(e){}
	}
	
	resetDatagrid();
}

function resetDatagrid(){
	//获取表单项
	var json = tools.requestJsonRs(contextPath+"/flowForm/getFormItemsByForm.action",{formId:$("#formId").val()});
	var rows = json.rtData;
	window.formItemDatas = [];
	for(var i=0;i<rows.length;i++){
		formItemDatas.push({val:rows[i].itemId,text:rows[i].title});
	}
	
	//获取业务表项
	var json = tools.requestJsonRs(contextPath+"/bisTableField/datagrid.action",{bisTableId:$("#bisTableId").val()});
	var rows = json.rows;
	window.tableItemDatas = [];
	for(var i=0;i<rows.length;i++){
		tableItemDatas.push({val:rows[i].fieldName,text:rows[i].fieldDesc});
	}
	
	$('#datagrid').datagrid({
		data:data,
		singleSelect:true,
		striped: true,
		border: false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		fitColumns:true,//列是否进行自动宽度适应
		onClickCell: onClickCell,
		columns:[[
			{field:'F_',title:'表单字段',width:100,editor:{
                type:'combobox',
                options:{
                    valueField:'val',
                    textField:'text',
                    data:formItemDatas,
                    required:false
            	}
			},formatter:function(data,rowData){
				for(var i=0;i<formItemDatas.length;i++){
					if((formItemDatas[i].val+"")==data){
						rowData.FII = data;
						rowData.FIT = formItemDatas[i].text;
						break;
					}
				}
				return rowData.FIT;
			}},
			{field:'T_',title:'业务字段',width:100,editor:{
                type:'combobox',
                options:{
                    valueField:'val',
                    textField:'text',
                    data:tableItemDatas,
                    required:false
            	}
			},formatter:function(data,rowData){
				for(var i=0;i<tableItemDatas.length;i++){
					if((tableItemDatas[i].val+"")==data){
						rowData.TFI = data;
						rowData.TFT = tableItemDatas[i].text;
						break;
					}
				}
				return rowData.TFT;
			}},
			{field:'TYPE',title:'字段类型',width:100,editor:{
                type:'combobox',
                options:{
                    valueField:'val',
                    textField:'text',
                    data:[{val:'文本输入',text:'文本输入'},
                          {val:'整型输入',text:'整型输入'},
                          {val:'下拉选择',text:'下拉选择'},
                          {val:'双精度浮点',text:'双精度浮点'},
                          {val:'单选人员',text:'单选人员'},
                          {val:'多选人员',text:'多选人员'},
                          {val:'单选角色',text:'单选角色'},
                          {val:'多选角色',text:'多选角色'},
                          {val:'单选部门',text:'单选部门'},
                          {val:'多选部门',text:'多选部门'},
                          {val:'日期选择',text:'日期选择'},
                          {val:'日期时间选择',text:'日期时间选择'}],
                    required:false
            	}
			}},
			{field:'MODEL',title:'数据模型（系统编码）',width:100,editor:"text"},
			{field:'_',title:'管理',width:50,formatter:function(data,rowData,index){
				var render = [];
				render.push("<a href='javascript:void(0)' onclick='delItem("+index+")'>删除</a>");
				return render.join("&nbsp;&nbsp;");
			}}
		]],
		showTips:false
	});
}

function commit(){
	if($("#moduleName").val()==""){
		$("#moduleName").focus();
		return alert("模块名称不能为空");
	}
	if($("#bisViewId").val()==""){
		$("#bisViewId").focus();
		return alert("请选择视图");
	}
	if($("#bisTableId").val()==""){
		$("#bisTableId").focus();
		return alert("请选择业务表");
	}
	if($("#formId").val()==""){
		$("#formId").focus();
		return alert("请选择表单");
	}
	
	var param = tools.formToJson($("#form"));
	var rows = $("#datagrid").datagrid("getRows");
	var listJson = tools.jsonArray2String(rows);
	param["mapping"] = listJson;
	var json;
	if(uuid!=""){
		json = tools.requestJsonRs(contextPath+"/bisModule/updateBisModule.action",param);
	}else{
		json = tools.requestJsonRs(contextPath+"/bisModule/addBisModule.action",param);
	}
	if(!json.rtState){
		alert(json.rtMsg);
	}else{
		alert("保存成功");
		window.location = "list.jsp";
	}
	
}

function goBack(){
	window.location = "list.jsp";
}

function del(sid){
	if(window.confirm("是否删除该视图项")){
		var json = tools.requestJsonRs(contextPath+"/bisView/delBisViewListItem.action",{sid:sid});
		$("#datagrid").datagrid("reload");
	}
}

function createItem(){
	$('#datagrid').datagrid('appendRow',{});
}

function delItem(index){
	$('#datagrid').datagrid('deleteRow',index);
}
</script>
</head>
<body onload="doInit()">
<div id="toolbar">
<table style="font-size:12px;" id="form">
	<input type="hidden" id="uuid" name="uuid" value="<%=uuid%>"/>
	<tr>
		<td class="TableData" width="80" >模块名称：</td>
		<td class="TableData" >
			<input type="text" id="moduleName" name="moduleName" class="BigInput"/>
		</td>
		<td class="TableData"  width="80">关联视图：</td>
		<td class="TableData">
			<select id="bisViewId" name="bisViewId" class="BigSelect">
				
			</select>
		</td>
	</tr>
	<tr>
		<td class="TableData"  width="80">关联业务表：</td>
		<td class="TableData">
			<select id="bisTableId" onchange="resetDatagrid()" name="bisTableId" class="BigSelect">
				
			</select>
		</td>
		<td class="TableData"  width="80">关联表单：</td>
		<td class="TableData">
			<input type="text" id="formId" name="formId" class="BigInput"/>
		</td>
	</tr>
	<tr>
		<td class="TableData"  width="80">创建权限：</td>
		<td class="TableData">
			<input type="hidden" id="createPrivIds" name="createPrivIds"/>
			<textarea class="BigTextarea readonly" readonly id="createPrivNames" name="createPrivNames"></textarea>
			<a href="javascript:void(0)" onclick="selectUser(['createPrivIds','createPrivNames'])">选择</a>
			&nbsp;
			<a href="javascript:void(0)" onclick="clearData('createPrivIds','createPrivNames')">清空</a>
		</td>
		<td class="TableData"  width="80">修改权限：</td>
		<td class="TableData">
			<input type="hidden" id="editPrivIds" name="editPrivIds"/>
			<textarea class="BigTextarea readonly" readonly id="editPrivNames" name="editPrivNames"></textarea>
			<a href="javascript:void(0)" onclick="selectUser(['editPrivIds','editPrivNames'])">选择</a>
			&nbsp;
			<a href="javascript:void(0)" onclick="clearData('editPrivIds','editPrivNames')">清空</a>
		</td>
	</tr>
	<tr>
		<td class="TableData"  width="80">删除权限：</td>
		<td class="TableData">
			<input type="hidden" id="delPrivIds" name="delPrivIds"/>
			<textarea class="BigTextarea readonly" readonly id="delPrivNames" name="delPrivNames"></textarea>
			<a href="javascript:void(0)" onclick="selectUser(['delPrivIds','delPrivNames'])">选择</a>
			&nbsp;
			<a href="javascript:void(0)" onclick="clearData('delPrivIds','delPrivNames')">清空</a>
		</td>
		<td class="TableData"  width="80"></td>
		<td class="TableData">
			
		</td>
	</tr>
</table>
<button class="btn btn-default" onclick="goBack()">返回</button>
&nbsp;&nbsp;
<button class="btn btn-primary" onclick="commit()">保存</button>
&nbsp;&nbsp;
<button class="btn btn-primary" onclick="createItem()">添加业务映射</button>
</div>
<table id="datagrid"  fit="true"></table>

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
</body>
</html>
