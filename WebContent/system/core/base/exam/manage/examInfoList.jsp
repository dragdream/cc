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
		url:contextPath+"/TeeExamInfoController/datagrid.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'examName',title:'考试名称',width:100},
			{field:'attendUserNames',title:'参加考试人',width:200},
			//{field:'scoreTypeDesc',title:'考试时长',width:150},
			//{field:'scoreAll',title:'满分',width:200},
			{field:'startTimeDesc',title:'生效时间',width:200},
			{field:'endTimeDesc',title:'终止时间',width:200},
			{field:'checkDays',title:'查卷推迟天数',width:100},
			{field:'2',title:'操作',width:300,formatter:function(e,rowData,index){
				var html="<a href='#' onclick='edit("+index+")'>修改</a>";
				if(rowData.endTimeDesc==null || rowData.endTimeDesc=="" || rowData.endTimeDesc=="null"){
					html+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='stop("+index+")'>立即终止</a>"
				}
				if(isCheckUser(rowData.sid) && isHasSubjective(rowData.sid)){
					html+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='checkExam("+index+")'>阅卷</a>"
				}
				html+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='allScore("+index+")'>成绩统计</a>"
				html+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' onclick='del("+index+")'>删除</a>";
				return html;
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
	var url = contextPath+"/system/core/base/exam/manage/examInfo_edit.jsp?sid="+sid;
	location.href=url;
}

function del(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要删除的考试信息","info");
		return;
	}
	var sid = selection.sid;
	$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/TeeExamInfoController/delExamInfo.action";
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

function stop(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要终止考试信息","info");
		return;
	}
	var sid = selection.sid;
	$.jBox.confirm("确认终止该考试信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/TeeExamInfoController/stopExamInfo.action";
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
function sendExamInfo(){
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要发布的考试信息","info");
		return;
	}
	var sid = selection.sid;
	$.jBox.confirm("确认发布该考试信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/TeeExamInfoController/sendExamInfo.action";
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
function checkExam(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要查阅的考试信息","info");
		return;
	}
	var sid = selection.sid;
	location.href= contextPath+"/system/core/base/exam/manage/singleExamList.jsp?sid="+sid;
}
function isCheckUser(sid){
	var url = contextPath+"/TeeExamInfoController/isCheckUser.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		if(json.rtData.isCheckUser){
			return true;
		}
	}else{
		return false;
	}
}
function allScore(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.jBox.tip("请选择需要统计成绩的考试信息","info");
		return;
	}
	var sid = selection.sid;
	location.href= contextPath+"/system/core/base/exam/manage/userScoresList.jsp?sid="+sid;
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
				var url = contextPath+"/TeeExamInfoController/delExamInfo.action";
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

function isHasSubjective(sid){
	var url = contextPath+"/TeeExamDataController/isHasSubjective.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		if(json.rtData.flag){
			return true;
		}
	}else{
		return false;
	}
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="moduleHeader">
			<b>考试信息管理</b>
		</div>
		<div style="text-align:left;margin:10px 0px;">
			<button class="btn btn-danger" onclick="delAll()">批量删除</button>
		</div>
	</div>
</body>
</html>