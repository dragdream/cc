<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
	String personName = request.getParameter("personName");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/TeeHumanTrainController/datagrid.action?humanDocSid=<%=humanDocSid%>',
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'traSubject',title:'培训主题',width:100,formatter:function(e,rowData,index){
				return "<a href='#' onclick=\"showDetail("+rowData.sid+")\">"+rowData.traSubject+"</a>";
			}},
			{field:'traContent',title:'培训内容',width:100},
			{field:'startTimeDesc',title:'开始日期',width:100},
			{field:'endTimeDesc',title:'结束日期',width:100},
			{field:'traPays',title:'培训费用',width:100},
			{field:'traPosition',title:'培训地点',width:100},
			{field:'traType',title:'培训类型',width:100},
			{field:'traCert',title:'培训证书',width:100},
			{field:'remark',title:'备注',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				return "<a href='javascript:void();' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+rowData.sid+")'>删除</a>";
			}}
		]]
	});
}
function showDetail(sid){
	var title="查看详情";
	var url = contextPath+"/system/core/base/pm/archivesManage/px_detail.jsp?sid="+sid+"&personName="+personName;
	bsWindow(url,title,{width:"700",height:"300",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
	     if(v=="关闭"){
	    	datagrid.datagrid("unselectAll");
			return true;
		}
	}});
}
function add(){
	var title="添加培训信息";
	var url = contextPath+"/system/core/base/pm/archivesManage/px_add.jsp?humanDocSid="+humanDocSid+"&personName="+personName;
	bsWindow(url ,title,{width:"700",height:"300",buttons:
		[
		 {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			cw.commit(function(){
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			    window.location.reload();
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function edit(sid){
	var title="修改培训信息";
	datagrid.datagrid("unselectAll");
	var url = contextPath+"/system/core/base/pm/archivesManage/px_edit.jsp?sid="+sid+"&humanDocSid="+humanDocSid+"&personName="+personName;
	bsWindow(url ,title,{width:"700",height:"300",buttons:
		[
		 {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			cw.commit(function(){
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
				window.location.reload();
			});
		}else if(v=="关闭"){
			datagrid.datagrid("unselectAll");
			return true;
		}
	}});
}

function del(sid){
	$.MsgBox.Confirm ("提示", "确认删除选中信息嘛？删除后不可恢复！",function(){
					var url = contextPath+"/TeeHumanTrainController/delHumanTrain.action";
					var json = tools.requestJsonRs(url,{sid:sid});
					if(json.rtState){
						datagrid.datagrid("unselectAll");
						datagrid.datagrid("reload");
						$.MsgBox.Alert_auto(json.rtMsg);
					}else{
						$.MsgBox.Alert_auto(json.rtMsg);
					}
	});
}


</script>

</head>
<body onload="doInit();" style="overflow:hidden;font-size:12px;padding-left: 10px;padding-right: 10px;">
	<table id="datagrid" fit="true"></table>
	<div class="topbar clearfix" id="toolbar">
   <div class="fl">
      <input type="button" value="添加培训纪录" class="btn-win-white" onclick="add();"/>
   </div>
</div>
</body>
</html>