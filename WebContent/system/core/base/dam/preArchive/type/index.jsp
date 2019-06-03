<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>预归档分类管理</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>

var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/preArchiveTypeController/datagrid.action",
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
			{field:'sortNo',title:'排序号',width:200},
			{field:'typeName',title:'分类名称',width:200},
			{field:'managerName',title:'档案管理员',width:200},
			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
				var opt="<a  href='#' onclick='edit("+rowData.sid+")'>编辑</a>&nbsp;&nbsp;&nbsp;";
				opt+="<a  href='#' onclick='del("+rowData.sid+")'>删除</a>";
			    return opt;
			}}
		]]
	});

}



//新建分类
function add(){
	var url=contextPath+"/system/core/base/dam/preArchive/type/addOrUpdate.jsp";
	bsWindow(url ,"新建分类",{width:"550",height:"110",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.doSaveOrUpdate();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("新建成功！",function(){
		    		datagrid.datagrid("reload");
		    	});
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("新建失败");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
	
}



//编辑分类
function edit(sid){
	var url=contextPath+"/system/core/base/dam/preArchive/type/addOrUpdate.jsp?sid="+sid;
	bsWindow(url ,"编辑分类",{width:"550",height:"110",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.doSaveOrUpdate();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("编辑成功！",function(){
		    		datagrid.datagrid("reload");
		    	});
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("编辑失败");
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}


//删除分类
function del(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该分类？", function(){
		  var url = contextPath + "/preArchiveTypeController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}   
	  });
	
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">

<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/dam/imgs/icon_ygdflgl.png">
		<span class="title">预归档分类管理</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="add();" value="新建分类"/>
     </div>
</div>
<table id="datagrid" fit="true"></table>

</body>
</html>