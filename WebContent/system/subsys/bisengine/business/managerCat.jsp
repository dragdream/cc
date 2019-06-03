<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>建模分类</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/businessCatController/datagrid.action',
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageSize : 30,
		pageList: [30,60,70,80,90,100],
		//idField:'formId',//主键列
		fitColumns:false,//列是否进行自动宽度适应
		remoteSort:true,
		columns:[[
			{field:'catName',title:'分类名称',width:'460',sortable:true},
			{field:'sortNo',title:'排序号',width:460},
			{field:'_oper',title:'操作',width:440,formatter:function(data,rows,index){
				var render ="";
					render = ["<a href='#' onclick=\"addOrUpdate('"+rows.sid+"')\">编辑</a>",
					          "<a href='#' onclick=\"del('"+rows.sid+"')\">删除</a>"];
				return render.join("&nbsp;&nbsp;");
			}}
		]]
	});
}

/**
 * 新增或者更新
 */
function addOrUpdate(sid){
	var title = "新建分类";
	var mess="新建";
	if(sid > 0){
		 title = "编辑分类";
		 mess="编辑";
	}
	var  url = contextPath + "/system/subsys/bisengine/business/addOrUpdateCat.jsp?sid=" + sid;
	bsWindow(url ,title,{width:"500",height:"100",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var json=cw.doSaveOrUpdate();
			if(json.rtState){
				$.MsgBox.Alert_auto(mess+"成功！",function(){
					$('#datagrid').datagrid("reload");
				});
				return true;
			}else{
				$.MsgBox.Alert_auto(mess+"失败！");
			}
		}else if(v=="关闭"){
			return true;
		}
	}});
}

//删除类别
function del(uuid){
	 $.MsgBox.Confirm ("提示", "是否确认删除？", function(){
		 var url = contextPath
			+ "/businessCatController/delete.action";
	     var json = tools.requestJsonRs(url, {sid : uuid});
	     if (json.rtState) {
			 $.MsgBox.Alert_auto(json.rtMsg,function(){
				 datagrid.datagrid('reload');
				 datagrid.datagrid('unselectAll');
			 });	 
	     }else{
	    	 $.MsgBox.Alert_auto(json.rtMsg);
	     }  
	  });
}



</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/bisengine/img/icon_jmflgl.png">
		<span class="title">建模分类管理 </span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white" onclick="addOrUpdate(0)" value="新建分类"/>
    </div>
</div>
</body>
</html>
