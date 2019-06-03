<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script> --%>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeAttendConfigController/datagrid.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'dutyName',title:'排班名称',width:100},
			{field:'dutyTimeDesc1',title:'第一次登记',width:80},
			{field:'dutyTimeDesc2',title:'第二次登记',width:80},
			{field:'dutyTimeDesc3',title:'第三次登记',width:80},
			{field:'dutyTimeDesc4',title:'第四次登记',width:80},
			{field:'dutyTimeDesc5',title:'第五次登记',width:80},
			{field:'dutyTimeDesc6',title:'第六次登记',width:80},
			/* {field:'centerPositionDesc',title:'签到区域中心点',width:300},
			{field:'radius',title:'签到区域半径',width:100,formatter:function(e,rowData,index){
				 return rowData.radius+ "米";
			}}, */
			{field:'2',title:'操作',width:80,formatter:function(e,rowData,index){
					return "<a href=\"javascript:void(0);\" onclick=\"edit("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"del("+index+")\">删除</a>";
			}}
		]]
	});
	$(".datagrid-header-row td div span").each(function(i,th){
		var val = $(th).text();
		 $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
	});
}

function edit(sid){
	datagrid.datagrid("unselectAll");
	
	var url = contextPath+"/system/core/base/attend/settings/duty/modify.jsp?sid="+sid;
	var title = "修改排班类型";
	bsWindow(url,title,{width:"600",height:"300",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v == "确定"){
			if(cw.commit()){
				d.remove();
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}
function add(){
	/* bsWindow(contextPath+"/system/core/base/attend/settings/duty/add.jsp","添加排班类型",{width:"800",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
	var url = contextPath+"/system/core/base/attend/settings/duty/add.jsp";
	var title = "添加排班类型";
	bsWindow(url,title,{width:"600",height:"300",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v == "确定"){
			if(cw.commit()){
				d.remove();
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
			};
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function setGeneral(sid){
	/* bsWindow(contextPath+"/system/core/base/attend/settings/duty/general.jsp?sid="+sid,"公休日设置",{width:"800",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
			return true;
		}
	}}); */
	
	var url = contextPath+"/system/core/base/attend/settings/duty/general.jsp?sid="+sid;
	var title = "公休日设置";
	bsWindow(url,title,{width:"600",height:"300",buttons:
		 [{name:"确定",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}]
	,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v == "确定"){
			if(cw.commit()){
				d.remove();
				$('#datagrid').datagrid('unselectAll');
				$('#datagrid').datagrid('reload');
			};
		}else if(v=="关闭"){
			return true;
		}
	}});
}


function del(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
// 		top.$.jBox.tip("请选择需要删除的信息","info");
		$.MsgBox.Alert_auto("请选择需要删除的信息");
		return;
	}
	var sid = selection.sid;
	$.MsgBox.Confirm("提示","确认删除选中信息吗",function(v){
// 		if(v=="ok"){
			var url = contextPath+"/TeeAttendConfigController/deleteById.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
// 				top.$.jBox.tip(json.rtMsg,"success");
				$.MsgBox.Alert_auto(json.rtMsg);
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			}else{
// 				top.$.jBox.tip(json.rtMsg,"error");
				$.MsgBox.Alert_auto(json.rtMsg);
			}
// 		}
	});
}
</script>

</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class = "topbar clearfix">
		<div class = "right fl clearfix">
			<button class="btn-win-white fr" onclick="add()">添加</button>
		</div>
	</div>
</body>
</html>