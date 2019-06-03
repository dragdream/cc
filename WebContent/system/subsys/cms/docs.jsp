<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String channelId = request.getParameter("channelId");
	String siteId = request.getParameter("siteId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>

<script>
var datagrid ;
var channelId = <%=channelId%>;
var siteId = <%=siteId%>;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/cmsDocument/getCheckDocs.action?channelId='+channelId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageList: [10,20,30],
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'docTitle',title:'文档标题',width:200,formatter:function(data,rowData){
				if(rowData.top==1){
					data = "<span style='color:red'>[置顶]&nbsp;"+data+"</span>";
				}				
				return "<a href='javascript:void(0)' onclick='view("+rowData.chnlDocId+","+rowData.docId+")'>"+data+"</a>";
			}},
			{field:'crTime',title:'创建时间',width:100,formatter:function(data,rowData){
				return getFormatDateStr(rowData.crTime,"yyyy-MM-dd HH:mm:ss");
			}},
			{field:'crUserName',title:'创建人',width:100},
			{field:'chnlName',title:'所在栏目',width:100},
			{field:'2',title:'操作',formatter:function(e,rowData){
				var html = [];
				var status=rowData.status;
			    html.push("&nbsp;<a href='javascript:void(0)' onclick='approve(3,"+rowData.docId+")'>通过</a>&nbsp;&nbsp;");
				html.push("&nbsp;<a href='javascript:void(0)' onclick='approve(5,"+rowData.docId+")'>退回</a>");
				return html.join("");
			}}
		]]
	});
}


function view(chnlDocId,docId){
	openFullWindow(contextPath+"/system/subsys/cms/viewDoc.jsp?channelId="+channelId+"&documentId="+docId+"&chnlDocId="+chnlDocId,"创建文档");
}


//审核   通过/拒绝
function approve(status,docId){
	var mess="";
	if(status==3){
		mess="是否确认通过此文档？";
	}else if(status==5){
		mess="是否确认退回此文档？";
	}
    
	  $.MsgBox.Confirm ("提示", mess, function(){
		  var json=tools.requestJsonRs(contextPath+"/cmsDocument/approveDoc.action",{docId:docId,status:status});
			if(json.rtState){
				$.MsgBox.Alert_auto("已审核！");
				datagrid.datagrid("reload");
			}  
	  });
}

</script>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
	
</div>
</body>

</html>