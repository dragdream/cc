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
		url:contextPath+"/TeeExamInfoController/getMyExamInfo.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'examName',title:'考试名称',width:200},
			{field:'ownId',hidden:true,title:'考试人Id',width:100},
			{field:'ownName',title:'参加考试人',width:200},
			{field:'startTimeDesc',title:'生效时间',width:200},
			{field:'endTimeDesc',title:'终止时间',width:200},
			{field:'scoreAll',title:'满分',width:200},
			{field:'2',title:'操作',width:300,formatter:function(e,rowData,index){
				var str="";
				var userId = rowData.ownId;
				var sid = rowData.sid;
				if(rowData.opt==1 || rowData.opt==2){
					if(isCommited(userId,sid)==2){// 判断是否已经提交过试卷,0 代表没有考过试，1，代表已经参加考试了，但是没有正常交卷，2代表惨叫考试了，并且正常交卷了
						str="<a href='#' onclick='attendExam(1,"+index+")'>查卷</a>";
					}else if(isCommited(userId,sid)==1){
						str="<a href='#' onclick='attendExam(0,"+index+")'>继续考试</a>";
					}else{
						str="<a href='#' onclick='attendExam(0,"+index+")'>考试</a>";
					}
					
				}
				return str;
			}}
		]]
	});
}

function attendExam(type,index){
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要参加的考试信息");
		return;
	}
	var examInfoId = selection.sid;
	var userId = selection.ownId;
	if(type==0 && isCommited(userId,examInfoId)==0){
		var url = contextPath+"/TeeExamDataController/addExamRecord.action?examInfoId="+examInfoId;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			//top.$.jBox.tip(json.rtMsg,"info");
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
	location.href=contextPath+"/system/core/base/exam/manage/examing.jsp?examInfoId="+examInfoId+"&type="+type+"&userId="+userId;
}

function isCommited(userId,sid){
	var flag=0;
	var url =contextPath+"/TeeExamInfoController/isCommited.action?userId="+userId+"&sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		flag=json.rtData.isCommited;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
	return flag;
}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_cjks.png">
			<span class="title">我的考试</span>
		</div>
		
    </div>
</body>
</html>