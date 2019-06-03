<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String siteId = request.getParameter("siteId");
	String channelId = request.getParameter("channelId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>

<script>
var siteId = <%=siteId%>;
var channelId = <%=channelId%>;
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/cmsChannel/listChannelDataGrid.action?siteId="+siteId+"&&channelId="+channelId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'chnlName',title:'栏目名称',width:120,formatter:function(e,rowData){
				var chnlName=rowData.chnlName;
				var chnlId=rowData.sid;
				return "<a href='#' onclick=\"parent.clickIt("+siteId+","+chnlId+",'"+"chnl"+"');\">"+chnlName+"</a>";
			}},
			{field:'chnlIdentity',title:'栏目标识',width:200},
			{field:'opt_',title:'操作',width:80,formatter:function(e,rowData){
				var html = [];
				html.push("<a href='javascript:void(0)' onclick='editChannel("+rowData.sid+")'>编辑</a>");
				html.push("<a href='javascript:void(0)' onclick='toPreview("+rowData.sid+")'>预览</a>");
				return html.join("&nbsp;");
			}}
		]],onLoadSuccess:onLoadSuccessFunc
	});
}


function onLoadSuccessFunc(){
	var json = tools.requestJsonRs(contextPath+"/cmsChannel/listChannels.action",{siteId:siteId,channelId:channelId});
	var path = json.rtData.path;
	var idPath = json.rtData.idPath;
	var sp = path.split("/");
	var sp1 = idPath.split("/");
	var render = [];
	render.push("<a href='javascript:void(0)' onclick='openEvent(\""+sp1[1]+"\")'>"+sp[1]+"</a>");
	for(var i=2;i<sp.length;i++){
		render.push("<a href='javascript:void(0)' onclick='openEvent(\""+sp1[i]+"\")'>"+sp[i]+"</a>");
	}
	$("#path").html(render.join("&nbsp;/&nbsp;"));
	
}

function openEvent(id){
	var sp = id.split(".");
	if(sp[1]=="st"){//站点
		parent.clickIt(siteId,0,"site");
	}else if(sp[1]=="ch"){//栏目
		parent.clickIt(siteId,sp[0],"chnl");
	}
}

//创建栏目
function crChnl(){
	bsWindow(contextPath+"/system/subsys/cms/chnlmgr.jsp?siteId="+siteId+"&channelId="+channelId
			,"创建栏目"
			,{height:"300",width:"650",buttons:
				[{name:"保存",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ],submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var json = cw.commit();
					if(json.rtState){
						$.MsgBox.Alert_auto("创建成功！");
						datagrid.datagrid('reload');//加载
						parent.refreshSiteNode(siteId);
						return true;
					}else{
						$.MsgBox.Alert_auto(json.rtMsg);
					}
				}else if(v=="关闭"){
					return true;
				}
			}});
}

function editChannel(sid){
	bsWindow(contextPath+"/system/subsys/cms/chnlmgr.jsp?siteId="+siteId+"&channelId="+channelId+"&sid="+sid
			,"编辑栏目"
			,{height:"300",width:"650",buttons:
				[{name:"保存",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ],submit:function(v,h){
				var cw = h[0].contentWindow;
				
				if(v=="保存"){
					var json = cw.commit();
					if(json.rtState){
						$.MsgBox.Alert_auto("编辑成功！");
						datagrid.datagrid('reload');//加载
						parent.refreshSiteNode(siteId);
						return true;
					}else{
						$.MsgBox.Alert_auto(json.rtMsg);
					}
				}else if(v=="关闭"){
					return true;
				}
			}});
}

//删除
function moveToTrash(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("请选择至少一个栏目!");
		return;
	}
	$.MsgBox.Confirm ("提示", "确认要将所选栏目放置回收站吗？", function(){
		for(var i=0;i<selections.length;i++){
			tools.requestJsonRs(contextPath+"/cmsChannel/moveToTrash.action",{sid:selections[i].sid});
		}
		$.MsgBox.Alert_auto("刪除成功！");
		datagrid.datagrid('reload');//加载
		parent.refreshSiteNode(siteId); 
	  });
}

//移动
function moveChannel(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("请选择至少一个栏目!");
		return;
	}
	var channelIds="";
	for(var i=0;i<selections.length;i++){
		channelIds+=selections[i].sid;
		if(i!=selections.length-1){
			channelIds+=",";
		}
	}
	bsWindow(contextPath+"/system/subsys/cms/moveChannel.jsp?siteId="+siteId+"&channelId="+channelIds
			,"移动栏目"
			,{height:"300",width:"500",buttons:
				[
                 {name:"确定",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ],submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="确定"){
					if(cw.commit()){
						$.MsgBox.Alert_auto("移动成功!");
						datagrid.datagrid('reload');
						parent.refreshSiteNode(siteId);
						return true;
					}
				}else if(v=="关闭"){
					return true;
				}
				
			}});
}

function chnlTrash(){
	window.location = contextPath+"/system/subsys/cms/chTrash.jsp?siteId="+siteId+"&channelId="+channelId;
}

//栏目发布
function toPub(type){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("请选择至少一个栏目!");
		return;
	}
	
	$.MsgBox.Confirm ("提示", "确认要发布选中栏目吗？", function(){
			
			var channelIds="";
			for(var i=0;i<selections.length;i++){
				channelIds+=selections[i].sid+",";
			}
			var url = contextPath+"/cmsChannelPub/toPub.action?channelIds="+channelIds+"&pub="+type;
			bsWindow(url,"栏目发布",{buttons:[],width:"300px",height:"200px"});
	  });
}

//预览
function toPreview(chnlId){
	var url = contextPath+"/cmsPub/portal.action?siteId="+siteId+"&channelId="+chnlId;
	window.open(url);
}


//复制
function copyChannel(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("请选择至少一个栏目");
		return;
	}
	var channelIds="";
	for(var i=0;i<selections.length;i++){
		channelIds+=selections[i].sid;
		if(i!=selections.length-1){
			channelIds+=",";
		}
	}
	bsWindow(contextPath+"/system/subsys/cms/copyChannel.jsp?siteId="+siteId+"&channelId="+channelIds
			,"复制栏目"
			,{height:"300",width:"500",buttons:
				[
                 {name:"确定",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ],submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="确定"){
					if(cw.commit()){
						$.MsgBox.Alert_auto("复制成功！");
						datagrid.datagrid('reload');
						parent.refreshSiteNode(siteId);
						return true;
					}
				}else if(v=="关闭"){
					return true;
				}
				
			}});
}
</script>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden;padding-right: 10px">

<div id="toolbar" class="topbar clearfix">
	<div class = "clearfix">
	    <button class="btn-win-white" onclick="crChnl()">创建</button>
		<button class="btn-del-red" onclick="moveToTrash()">删除</button>
		<button class="btn-win-white" onclick="moveChannel()">移动</button>
		<button class="btn-win-white" onclick="copyChannel()">复制</button>
		<button class="btn-del-red" onclick="chnlTrash()">回收站</button>
		&nbsp;&nbsp;
		<button class="btn-win-white" onclick="toPub(1)">仅发布首页</button>
		<button class="btn-win-white" onclick="toPub(2)">发布栏目文档</button>
		<button class="btn-win-white" onclick="toPub(3)">发布整体栏目</button>
	</div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>