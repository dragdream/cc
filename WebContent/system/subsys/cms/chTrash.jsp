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
		url:contextPath+"/cmsChannel/listTrashChannelsDatagrid.action?siteId="+siteId+"&&channelId="+channelId,
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
				return "<a href='#'>"+chnlName+"</a>";
			}},
			{field:'chnlIdentity',title:'栏目标识',width:200}
		]],onLoadSuccess:onLoadSuccessFunc
	});
}


function onLoadSuccessFunc(){
	var json = tools.requestJsonRs(contextPath+"/cmsChannel/listTrashChannels.action",{siteId:siteId,channelId:channelId});
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


//还原
function recycle(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("请选择至少一个栏目!");
		return;
	}
	
	$.MsgBox.Confirm ("提示", "确认要还原所选栏目吗？", function(){
		for(var i=0;i<selections.length;i++){
			tools.requestJsonRs(contextPath+"/cmsChannel/recycle.action",{sid:selections[i].sid});
		}
		$.MsgBox.Alert_auto("还原成功！");
		datagrid.datagrid('reload');//加载
		parent.refreshSiteNode(siteId);
	});
}

//刪除
function removeChannel(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("请选择至少一个栏目!");
		return;
	}
	
	$.MsgBox.Confirm ("提示", "确认要彻底删除所选栏目吗？", function(){
		for(var i=0;i<selections.length;i++){
			tools.requestJsonRs(contextPath+"/cmsChannel/delChannelInfo.action",{sid:selections[i].sid});
		}
		$.MsgBox.Alert_auto("刪除成功！");
		datagrid.datagrid('reload');//加载
		parent.refreshSiteNode(siteId);
	});
}



//清空回收站
function clearTrash(){
	$.MsgBox.Confirm ("提示", "确定要清空该栏目回收站吗？", function(){
		var json = tools.requestJsonRs(contextPath+"/cmsChannel/clearTrash.action",{siteId:siteId,channelId:channelId});
		if(json.rtState){
			$.MsgBox.Alert_auto("清空成功！");
			datagrid.datagrid('reload');
		}  
	  });
}

//返回
function goBack(){
	window.location = contextPath+"/system/subsys/cms/channels.jsp?siteId="+siteId+"&channelId="+channelId;
}

</script>

</head>
<body onload="doInit();" style="margin:0px;overflow:hidden;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
     <div class="fl" style="position:static;">
		<span>&nbsp;&nbsp;<span style="color:red;font-weight:bold">（栏目回收站）</span>当前位置：</span><span id="path"></span>
	</div>
	<div class = "right fr clearfix">
	    <button class="btn-win-white" onclick="goBack()">返回</button>
		<button class="btn-win-white" onclick="recycle()">还原</button>
		<button class="btn-del-red" onclick="removeChannel()">删除栏目</button>
		<button class="btn-del-red" onclick="clearTrash()">清空回收站</button>
	</div>
</div>
<table id="datagrid" fit="true"></table>

</body>
</html>