<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamStoreController/datagrid.action",
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
			{field:'storeCode',title:'题库编号',width:100},
			{field:'storeName',title:'题库名称',width:200},
			{field:'storeDesc',title:'题库说明',width:300},
			{field:'2',title:'操作',width:200,formatter:function(e,rowData,index){
				return "<a href='#' onclick='edit("+index+")'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+index+")'>删除</a>";
			}}
		]]
	});
}

function edit(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
	    $.MsgBox.Alert_auto("请选择需要修改的内容");
		return;
	}
	var sid = selection.sid;
	var url = contextPath+"/system/core/base/exam/manage/store_edit.jsp?sid="+sid;
	//location.href=url;
	bsWindow(url ,"修改题库",{width:"600",height:"250",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commit();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("编辑成功！");
		    	datagrid.datagrid("reload");
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("编辑失败！");
		    	return false;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}



//新建题库
function add(){
	
	var url = contextPath+"/system/core/base/exam/manage/store_add.jsp";
	//location.href=url;
	bsWindow(url ,"新增题库",{width:"600",height:"250",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.commit();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("新建成功！");
		    	datagrid.datagrid("reload");
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("新建失败！");
		    	return false;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}


function del(index){
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要删除的题库");
		return;
	}
	var sid = selection.sid;
	
	$.MsgBox.Confirm ("提示", "确认删除该题库信息吗？对应的试题，考试信息也会被删除", function(){
		var url = contextPath+"/TeeExamStoreController/delExamStore.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			$.MsgBox.Alert_auto(json.rtMsg);
			datagrid.datagrid("unselectAll");
			datagrid.datagrid("reload");
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	});
}

function delAll(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.MsgBox.Alert_auto("请选择需要删除项目");
		return;
	}
	
	$.MsgBox.Confirm ("提示", "确认删除该题库信息吗？对应的试题，考试信息也会被删除", function(){
		for(var i=0;i<selections.length;i++){
		    var sid = selections[i].sid;
			var url = contextPath+"/TeeExamStoreController/delExamStore.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		}
		datagrid.datagrid("unselectAll");
		datagrid.datagrid("reload");
	});

}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class = "topbar clearfix">
	   <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_tkgl.png">
		  <span class="title">题库管理</span>
	   </div>
	   <div class = "right fr clearfix">
	      <input type="button" class="btn-win-white fl" onclick="add()" value="新建题库"/>
		  <input type="button" class="btn-del-red fl" onclick="delAll()" value="批量删除"/>
	   </div>
	</div>
</body>
</html>