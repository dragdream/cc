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
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeHumanShiftController/datagrid.action?humanDocSid=<%=humanDocSid%>',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'sTypeDesc',title:'调动类型',width:100},
			{field:'sTime1Desc',title:'调动日期',width:100},
			{field:'sTime2Desc',title:'调动生效日期',width:100},
			{field:'sLastCompany',title:'调动后单位',width:100},
			{field:'sLastDept',title:'调动后部门',width:100},
			{field:'sLastPost',title:'调动后职位',width:100},
			{field:'remark',title:'备注',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='javascript:void();' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void();' onclick='showDetail("+rowData.sid+")'>查看</a>";
			}}
		]]
	});
}
function showDetail(sid){
	datagrid.datagrid("unselectAll");
	bsWindow(contextPath+"/system/core/base/pm/manage/rsdd_detail.jsp?sid="+sid,"查看详情",{width:"700",height:"300",buttons:[{name:"关闭",classStyle:"btn btn-primary"}],submit:function(v,h){return true;}});
}
function add(){
	var url = contextPath+"/system/core/base/pm/manage/rsdd_add.jsp?humanDocSid=<%=humanDocSid%>";
	bsWindow(url,"添加人事调动信息",{width:"700",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/core/base/pm/manage/rsdd_edit.jsp?sid="+sid;
	bsWindow(url,"修改人事调动信息",{width:"700",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}
function del(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		top.$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			for(var i=0;i<selections.length;i++){
				var sid = selections[i].sid;
					var url = contextPath+"/TeeHumanShiftController/delHumanShift.action";
					var json = tools.requestJsonRs(url,{sid:sid});
					if(json.rtState){
						top.$.jBox.tip(json.rtMsg,"success");
					}else{
						top.$.jBox.tip(json.rtMsg,"error");
					}
			}
						datagrid.datagrid("unselectAll");
						datagrid.datagrid("reload");
		}
	});
}


</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;人事调动管理</b>
		</div>
		<div style="text-align:left;margin-bottom:5px;">
			<button class="btn btn-primary" onclick="add()">添加</button>
			<button class="btn btn-danger" onclick="del()">删除</button>
		</div>
	</div>
</body>
</html>