<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamPaperController/datagrid.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'title',title:'试卷标题',width:100},
			{field:'paperDesc',title:'试卷说明',width:120},
			{field:'scoreTypeDesc',title:'计分类型',width:150},
			{field:'scoreAll',title:'总分',width:200},
			{field:'qCount',title:'试题数量',width:200},
			//{field:'content',title:'出题日期',width:300},
			{field:'2',title:'操作',width:200,formatter:function(e,rowData,index){
				return "<a href='#' onclick='edit("+index+")'>修改</a>"
						+"&nbsp;&nbsp;<a href='javascript:void(0)' onclick='autoSelectQuestion("+index+")'>自动选题</a>"
						+"&nbsp;&nbsp;<a href='#' onclick='handleSelectQuestion("+index+")'>手动选题</a>"
				        +"&nbsp;&nbsp;<a href='javascript:void(0)' onclick='del("+index+")'>删除</a>";
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
		$.jBox.tip("请选择需要修改的内容","info");
		return;
	}
	var sid = selection.sid;
	var url = contextPath+"/system/core/base/exam/manage/paper_edit.jsp?sid="+sid;
	location.href=url;
}

function del(index){
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要删除的试卷","info");
		return;
	}
	var sid = selection.sid;
	$.jBox.confirm("确认删除该试卷信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/TeeExamPaperController/delExamPaper.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.jBox.tip(json.rtMsg,"success");
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			}else{
				$.jBox.tip(json.rtMsg,"error");
			}
		}
	});
}

function delAll(){
	var selections = datagrid.datagrid("getSelections");
	if(selections==null || selections==undefined || selections.length<=0){
		$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			for(var i=0;i<selections.length;i++){
			    var sid = selections[i].sid;
				var url = contextPath+"/TeeExamPaperController/delExamPaper.action";
				var json = tools.requestJsonRs(url,{sid:sid});
				if(json.rtState){
					$.jBox.tip(json.rtMsg,"success");
				}else{
					$.jBox.tip(json.rtMsg,"error");
				}
			}
					datagrid.datagrid("unselectAll");
					datagrid.datagrid("reload");
		}
	});
}


function autoSelectQuestion(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要选题的试卷","info");
		return;
	}
	var sid = selection.sid;
	var qCount = selection.qCount;
	var url = contextPath+"/TeeExamPaperController/autoSelectQuestion.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		$.jBox.tip(json.rtMsg,"success");
		var url = contextPath+"/system/core/base/exam/manage/examPaper_select.jsp?paperId="+sid+"&qCount="+qCount;
		location.href=url;
	}else{
		$.jBox.tip(json.rtMsg,"error");
	}
}



function handleSelectQuestion(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要选题的试卷","info");
		return;
	}
	var sid = selection.sid;
	var qCount = selection.qCount;
	var url = contextPath+"/system/core/base/exam/manage/examPaper_select.jsp?paperId="+sid+"&qCount="+qCount;
	location.href=url;
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div style="text-align:left;margin:10px 0px;">
			<button class="btn btn-danger" onclick="delAll()">批量删除</button>
		</div>
	</div>
</body>
</html>