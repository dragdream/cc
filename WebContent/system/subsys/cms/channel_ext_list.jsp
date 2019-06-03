<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>

<style>
.alertMessageBg{
position:fixed;
_position:absolute;
width:100%;
height:100%;
left:0;
top:0;
background:#000;
opacity:0.5;
-moz-opacity:0.5;
filter:alpha(opacity=50);
z-index:97;
display:none;
}
.messageDiv{
	position:relative;
	width:100%;
	height:100%;
	line-height:100%;
	text-align:center;
	color:red;
	font-size:16px;
	weight:bolder;
	top:200px;
}
</style>
<script>
var datagrid;
function doInit(){
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/cmsChannelExt/list.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:false,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'fieldName',title:'字段名称',width:100},
			{field:'fieldTitle',title:'字段标题',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				var html = [];
				html.push("<a href='javascript:void(0)' onclick='edit("+rowData.sid+")'>编辑</a>");
				html.push("&nbsp;<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				return html.join("");
			}}
		]]
	});
}

function crSite(){
	bsWindow(contextPath+"/system/subsys/cms/channel_ext_mgr.jsp","添加字段",{height:"200",width:"500",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ],submit:function(v,h){
		var cw = h[0].contentWindow;
		var json = cw.commit();
		if(v=="保存"){
			if(json.rtState){
				$.MsgBox.Alert_auto("创建成功！");
				datagrid.datagrid("reload");
				return true;
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function edit(sid){
	bsWindow(contextPath+"/system/subsys/cms/channel_ext_mgr.jsp?sid="+sid,"编辑字段",{height:"200",width:"500",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ],submit:function(v,h){
		var cw = h[0].contentWindow;
		var json = cw.commit();
		if(v=="保存"){
			if(json.rtState){
				$.MsgBox.Alert_auto("编辑成功！");
				datagrid.datagrid("reload");
				return true;
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		}else if(v=="关闭"){
			return true;
		}
		
	}});
}

//删除站点
function del(sid){	
	  $.MsgBox.Confirm ("提示", "确认要删除该字段吗？", function(){
		  var url = contextPath+"/cmsChannelExt/remove.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid("load");
			}
	  });
}


</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/cms/image/zd.png">
		<span class="title">栏目模型管理</span>
	</div>
	<div class = "right fr clearfix">
	    <button type="button" class="btn-win-white" onclick="crSite()">创建字段</button>
	</div>
</div>
</body>
</html>