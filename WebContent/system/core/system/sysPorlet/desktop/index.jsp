<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片库管理</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/teePortalTemplateController/datagrid.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'templateName',title:'模块名称',width:100},
			{field:'userPrivDesc',title:'人员权限',width:200},
			{field:'deptPrivDesc',title:'部门权限',width:300},
			{field:'rolePrivDesc',title:'角色权限',width:300},
			{field:'sortNo',title:'排序号',width:300},
			{field:'2',title:'操作',width:200,formatter:function(e,rowData,index){
				return "<a href='#' onclick='editPortalTemplate("+index+")'>修改</a>&nbsp;&nbsp;<a href='javascript:void();' onclick='designTemplate("+index+")'>设计</a>&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+index+")'>删除</a>";
			}}
		]]
	});
}

function del(index){
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要删除的模板","info");
		return;
	}
	var sid = selection.sid;
	$.jBox.confirm("确认删除该模板信息吗？","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/teePortalTemplateController/delPortalTemplate.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.jBox.tip(json.rtMsg,"success");
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			}else{
				$.jBox.tip(json.rtMsg,"error");
			}
		}
	});
}


function addPortalTemplate(){
	bsWindow(contextPath+"/system/core/system/sysPorlet/desktop/add.jsp"
			,"新建模板"
			,{height:"300",width:"650",submit:function(v,h){
				var cw = h[0].contentWindow;
				var json = cw.commit();
				if(json.rtState){
					datagrid.datagrid("reload");
					return true;
				}else{
					alert(json.rtMsg);
				}
			}});
}

function editPortalTemplate(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要修改的内容","info");
		return;
	}
	var sid = selection.sid;
	bsWindow(contextPath+"/system/core/system/sysPorlet/desktop/add.jsp?sid="+sid
			,"编辑模板"
			,{height:"350",width:"650",submit:function(v,h){
				var cw = h[0].contentWindow;
				var json = cw.commit();
				if(json.rtState){
					datagrid.datagrid("reload");
					return true;
				}else{
					alert(json.rtMsg);
				}
			}});
}

function designTemplate(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要修改的内容","info");
		return;
	}
	var sid = selection.sid;
	var url=contextPath+"/system/core/system/sysPorlet/desktop/design.jsp?sid="+sid+"&type=0";
	location.href = url;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div style="padding:10px">
			<button class="btn btn-success" onclick="addPortalTemplate()">新建</button>
		</div>
	</div>
</body>
</html>