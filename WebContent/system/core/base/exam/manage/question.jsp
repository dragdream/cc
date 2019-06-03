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
	//渲染题库信息
	renderStore();
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamQuestionController/datagrid.action",
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
			{field:'storeName',title:'所属题库',width:100},
			{field:'qTypeDesc',title:'题型',width:120},
			{field:'qHardDesc',title:'难度',width:150},
			{field:'score',title:'分数',width:200},
			//{field:'storeName',title:'题号',width:200},
			{field:'content',title:'题目',width:300},
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
	var url = contextPath+"/system/core/base/exam/manage/question_edit.jsp?sid="+sid;
	bsWindow(url ,"修改试题",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    cw.commit();
		   /*  $.MsgBox.Alert_auto("修改成功！"); */
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

//新建试题
function add(){
	
	var url = contextPath+"/system/core/base/exam/manage/question_add.jsp";
	bsWindow(url ,"新建试题",{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    cw.commit();
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
		$.MsgBox.Alert_auto("请选择需要删除的试题");
		return;
	}
	var sid = selection.sid;
	
	$.MsgBox.Confirm ("提示", "确认删除该试题信息吗？", function(){
		var url = contextPath+"/TeeExamQuestionController/delExamQuestion.action";
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
	
	$.MsgBox.Confirm ("提示", "确认删除选中信息吗？", function(){
		for(var i=0;i<selections.length;i++){
		    var sid = selections[i].sid;
			var url = contextPath+"/TeeExamQuestionController/delExamQuestion.action";
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


//渲染题库信息
function  renderStore(){
	var url=contextPath+"/TeeExamStoreController/getAllExamStore.action";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var html=[];
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				html.push("<option value="+data[i].sid+">"+data[i].storeName+"</option>");
			}
			$("#storeId").append(html.join(""));
		}
	}
}

//查询
function query(){
	datagrid.datagrid('reload',{storeId:$("#storeId").val()});
}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_stgl.png">
			<span class="title">试题管理 </span>
		</div>
		<div class="fr clearfix right">
		    <span class="fl">
			             所属题库：
			    <select id="storeId" name="storeId" onchange="query();">
			        <option value="0">所有题库</option>
			    </select>
		    </span>        
		           
		    <input type="button" class="btn-win-white fl" onclick="add()" value="新建试题"/>
			<input type="button" class="btn-del-red fl" onclick="delAll()" value="批量删除"/>
		</div>
	</div>
</body>
</html>