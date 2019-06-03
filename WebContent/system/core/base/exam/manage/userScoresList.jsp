<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String sid = request.getParameter("sid");
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
var sid='<%=sid%>';
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeExamInfoController/getSingleExamList.action?sid="+sid,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		remoteSort:false, 
		pagination:false,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		/* sortName:"realScore",
		sortOrder:"desc", */
		columns:[[
// 			{field:'sid',checkbox:true,title:'ID',width:100},
// 			{field:'examName',title:'考试名称',width:100},
			{field:'ownId',hidden:true,title:'考试人Id',width:100},
			{field:'ownName',title:'参加考试人',width:200},
			{field:'1',title:'所在部门',width:200,formatter:function(e,rowData){
				var deptName = getDeptName(rowData.ownId);
				return deptName;
			}},
			{field:'realScore',title:'成绩',width:300},
			{field:'opt_',title:'操作',width:200,formatter:function(e,rowData){
				return  "<a href=\"#\" onclick=\"viewDetail("+rowData.sid+","+rowData.ownId+")\" >查看详情</a>";
			}},
			/* {field:'2',title:'成绩',width:300,formatter:function(e,rowData){
				var realScore = getRealScore(rowData.ownId,rowData.sid);
				return realScore;
			}} */
		]]
	});
}
/**
 * 查看试卷详情
 */
function viewDetail(examInfoId,userId){
	location.href=contextPath+"/system/core/base/exam/manage/examing.jsp?examInfoId="+examInfoId+"&type=1&userId="+userId;
}

/**
 * 获取当前考生实际得分
 */
function getRealScore(userId,examInfoId){
	var url =contextPath+"/TeeExamDataController/getRealScore.action?userId="+userId+"&examInfoId="+examInfoId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data.realScore;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}

/**
 * 获取当前用户所在的部门
 */
function getDeptName(userId){
	var url =contextPath+"/personManager/getPersonById.action?uuid="+userId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var data = json.rtData;
		return data.deptIdName;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
}
function exportExcel (sid){
	$("#exportIframe").attr("src",contextPath+"/TeeExamInfoController/exportExcel.action?sid="+sid);

}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="topbar clearfix">
	    <div class="fl" style="position:static;">
			<span class="title">成绩统计</span>
		</div>
		<div class="fr right clearfix">
			<button class="btn-win-white" onclick="exportExcel('<%=sid%>')">导出Excel</button>
		    <button class="btn-win-white" onclick="history.go(-1)">返回</button>		
		</div>
	</div>
	<iframe id="exportIframe" style="display:none"></iframe>
</body>
</html>