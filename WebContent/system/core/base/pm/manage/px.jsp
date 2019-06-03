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
		url:contextPath+'/TeeHumanTrainController/datagrid.action?humanDocSid=<%=humanDocSid%>',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'traSubject',title:'培训主题',width:100},
			{field:'traContent',title:'培训内容',width:100},
			{field:'startTimeDesc',title:'开始日期',width:100},
			{field:'endTimeDesc',title:'结束日期',width:100},
			{field:'traPays',title:'培训费用',width:100},
			{field:'traPosition',title:'培训地点',width:100},
			{field:'traType',title:'培训类型',width:100},
			{field:'traCert',title:'培训证书',width:100},
			{field:'remark',title:'备注',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='javascript:void();' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void();' onclick='showDetail("+rowData.sid+")'>查看</a>";
			}}
		]]
	});
}
function showDetail(sid){
	datagrid.datagrid("unselectAll");
	bsWindow(contextPath+"/system/core/base/pm/manage/px_detail.jsp?sid="+sid,"查看详情",{width:"700",height:"300",buttons:[{name:"关闭",classStyle:"btn btn-primary"}],submit:function(v,h){return true;}});
}
function add(){
	var url = contextPath+"/system/core/base/pm/manage/px_add.jsp?humanDocSid=<%=humanDocSid%>";
	bsWindow(url,"添加培训信息",{width:"700",height:"300",submit:function(v,h){
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
	var url = contextPath+"/system/core/base/pm/manage/px_edit.jsp?sid="+sid;
	bsWindow(url,"修改培训信息",{width:"700",height:"300",submit:function(v,h){
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
					var url = contextPath+"/TeeHumanTrainController/delHumanTrain.action";
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
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;培训管理</b>
		</div>
		<div style="text-align:left;margin-bottom:5px;">
			<button class="btn btn-primary" onclick="add()">添加</button>
			<button class="btn btn-danger" onclick="del()">删除</button>
		</div>
	</div>
</body>
</html>