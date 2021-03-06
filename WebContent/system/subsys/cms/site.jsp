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
		url:contextPath+'/cmsSite/datagrid.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageList: [50,60,70,80,90,100],
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'siteName',title:'站点名称',width:100},
			{field:'siteIdentity',title:'站点标识',width:100},
			{field:'sortNo',title:'排序号',width:100},
			{field:'folder',title:'存放位置',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				var html = [];
				html.push("<a href='javascript:void(0)' onclick='edit("+rowData.sid+")'>编辑</a>");
				html.push("&nbsp;<a href='javascript:void(0)' onclick='tpl("+rowData.sid+")'>模板</a>");
				html.push("&nbsp;<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				html.push("&nbsp;<a href='javascript:void(0)' onclick='toPub("+rowData.sid+")'>发布首页</a>");
				html.push("&nbsp;<a href='javascript:void(0)' onclick='toPubAll("+rowData.sid+")'>全站发布</a>");
				html.push("&nbsp;<a href='javascript:void(0)' onclick='toPreview("+rowData.sid+")'>预览</a>");
				return html.join("");
			}}
		]]
	});
}

function tpl(siteId){
	window.location = "tpl.jsp?siteId="+siteId;
}

function crSite(){
	bsWindow(contextPath+"/system/subsys/cms/sitemgr.jsp","创建站点",{height:"300",width:"600",buttons:
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
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function edit(sid){
	bsWindow(contextPath+"/system/subsys/cms/sitemgr.jsp?siteId="+sid,"编辑站点",{height:"300",width:"600",buttons:
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
			}
		}else if(v=="关闭"){
			return true;
		}
		
	}});
}

//删除站点
function del(sid){	
	  $.MsgBox.Confirm ("提示", "确认要删除该站点吗？", function(){
		  var url = contextPath+"/cmsSite/delSiteInfo.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid("load");
			}
	  });
}

function toPub(sid){
	 $.MsgBox.Confirm ("提示", "确认要发布该站点的首页吗？", function(){
		 var url = contextPath+"/cmsSitePub/toPub.action?siteId="+sid+"&pubAll=0";
		 bsWindow(url,"站点发布",{buttons:[],width:"300px",height:"200px"});
	  });
}

function toPubAll(sid){
	 $.MsgBox.Confirm ("提示", "确认要发布全站点吗？", function(){
		 var url = contextPath+"/cmsSitePub/toPub.action?siteId="+sid+"&pubAll=1";
		 bsWindow(url,"站点发布",{buttons:[],width:"300px",height:"200px"});
	  });
}

function toPreview(sid){
	var url = contextPath+"/cmsPub/portal.action?siteId="+sid;///&channelId=2&chnlDocId=3";
	window.open(url);
}

</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/cms/image/zd.png">
		<span class="title">站点管理</span>
	</div>
	<div class = "right fr clearfix">
	    <button type="button" class="btn-win-white" onclick="crSite()">创建站点</button>
		<!-- <button type="button" class="btn-win-white">导入站点</button> -->
	</div>
</div>
<div class="alertMessageBg">
<div class="messageDiv">
	<img src="image/wait.gif" />站点发布中,请稍后。。。。。。
</div>
</div>
</body>
</html>