<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<title>业务分类管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/bisCat/datagrid.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'catName',title:'分类名称',width:200},
			{field:'sortNo',title:'排序号',width:100},
			{field:'2',title:'操作',width:100,formatter:function(e,rowData){
				var html = [];
				html.push("<a href='#' onclick='edit("+rowData.sid+")'>编辑</a>");
				html.push("&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='del("+rowData.sid+")'>删除</a>");
				return html.join("");
			}}
		]]
	});
}

function add(){
	var url = "cat_addOrUpdate.jsp";
	bsWindow(url ,"添加分类",{width:"530",height:"130",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commit();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("添加成功！",function(){
		    		datagrid.datagrid("reload");
		    	});
		    	return  true;
		    }else{
		    	$.MsgBox.Alert_auto("添加失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

function edit(sid){
	var url = "cat_addOrUpdate.jsp?sid="+sid;
	bsWindow(url ,"编辑分类",{width:"530",height:"130",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commit();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("编辑成功！",function(){
		    		datagrid.datagrid("reload");
		    	});
		    	return  true;
		    }else{
		    	$.MsgBox.Alert_auto("编辑失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

function del(sid){
	 $.MsgBox.Confirm ("提示", "确认要删除该分类？<br/>提示：如果删除则会清除该分类下的所有所有业务数据。", function(){
		 var url = contextPath+"/bisCat/deleteBisCat.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！",function(){
					datagrid.datagrid("reload");
				});
				
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
	  });
}
</script>
</head>
<body style="padding-left: 10px;padding-right: 10px" onload="doInit()">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/bisengine/img/icon_ywflgl.png">
		<span class="title">业务分类管理</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="add()" value="添加分类"/>
    </div>
 </div>

</body>
</html>
