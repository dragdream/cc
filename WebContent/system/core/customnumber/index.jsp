<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>自定义编号设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/cusNumberController/datagrid.action',
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
            {field:'codeName',title:'编号名称',width:'400',sortable:true},
			{field:'userSetStyle',title:'编号样式',width:'400',sortable:true},
			
			{field:'_oper',title:'操作',width:200,formatter:function(data,rows,index){
					render = [
					              "<a href='#' onclick=\"edit('"+rows.uuid+"')\">编辑</a>",
					              "<a href='#' onclick=\"del('"+rows.uuid+"')\">删除</a>"];
			return render.join("&nbsp;&nbsp;");
				}}
		]]
	});
}

function add(){
	var url=contextPath+"/system/core/customnumber/addOrUpdate.jsp";
	//window.location = "addOrUpdate.jsp";
	bsWindow(url ,"添加编号",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.save();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("添加成功！");
		    	datagrid.datagrid("reload");
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("添加失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

function edit(uuid){

	var url=contextPath+"/system/core/customnumber/addOrUpdate.jsp?uuid="+uuid;
	bsWindow(url ,"编辑编号",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.save();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("编辑成功！");
		    	datagrid.datagrid("reload");
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("编辑失败！");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

function del(uuid){
	$.MsgBox.Confirm ("提示", "确认要删除该编号样式吗？", function(){
		var json = tools.requestJsonRs(contextPath+"/cusNumberController/delete.action",{uuid:uuid});
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！");
			$("#datagrid").datagrid("reload");
			$("#datagrid").datagrid("unselectAll");
		}
		
	});
}

</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/customnumber/imgs/zdbhgl.png">
		<span class="title">自动编号管理</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="add()" value="添加"/>
    </div>
 </div>
</body>
</html>
