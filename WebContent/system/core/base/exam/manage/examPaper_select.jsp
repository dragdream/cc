<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String paperId = request.getParameter("paperId");
	String qCount = request.getParameter("qCount");	
%>
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
var paperId = "<%=paperId %>";
var qCount="<%=qCount%>";
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamPaperController/getQuestionList.action?rows=1000000&paperId="+paperId,
		pagination:false,
		//pageSize:9999,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'content',title:'题目类容',width:200},
			{field:'qTypeDesc',title:'题型',width:20},
			{field:'qHardDesc',title:'难度',width:20}
		]],
		 onLoadSuccess:function(data){
		 	for(var i=0;i<data.rows.length;i++){
				if(data.rows[i].check){
					datagrid.datagrid("selectRow",i);
				}
			 }
		}
	});
}


// 选题后确定
function save(){
	var selectedList ="";
	var selections = datagrid.datagrid("getSelections");
	if(selections.length>qCount){
		$.MsgBox.Alert_auto("试题数量大于要求");
		return;
	}
	for(var i=0;i<selections.length;i++){
	    var sid = selections[i].sid;
	    selectedList+=sid+",";
	}
	if(selectedList.length>1){
		selectedList=selectedList.substring(0,selectedList.length-1);
	}
	var url = contextPath+"/TeeExamPaperController/saveSelectedQuestion.action?selectedList="+selectedList+"&paperId="+paperId;
	var json = tools.requestJsonRs(url);
	/* if(json.rtState){
		$.MsgBox.Alert_auto(json.rtMsg);
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	} */
	return json;
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true"></table>
	<!-- <div id="toolbar">
		<div style="text-align:left;">
			<button class="btn btn-primary" onclick="save()">保存试题</button>
			<button class="btn btn-primary" onclick="history.go(-1)">返回</button>
		</div>
	</div> -->
</body>
</html>