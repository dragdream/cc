<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/subsys/topic/js/topic.js" ></script>
<title>月热门贴</title>
<script type="text/javascript">
function doInit(){
	getInfoList();
}


var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeTopicController/getMonthTopicList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'uuid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			//{field:'uuid',checkbox:true,title:'ID',width:50},
			{field:'topicSectionName',title:'所属版块 ',width:20,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'subject',title:'主题 ',width:120,formatter:function(value,rowData,rowIndex){
				var fileNameStr = value ;
				fileNameStr = "<img src='<%=contextPath%>/system/subsys/topic/image/forum_comm.gif'>&nbsp;" + value ;
				return "<a  href='#' onclick=showInfoFunc('" + rowData.uuid + "','"+rowData.topicSectionId+"')>" +fileNameStr + "</a>";
			}},
			{field:'crUserName',title:'作者  ',width:30,align:'center',formatter:function(value,rowData,rowIndex){
				return value + "<br>" + rowData.crTimeStr.substr(0,10);
			}},
			{field:'replyAmount',title:'回复/人气',width:30,align:'center',formatter:function(value,rowData,rowIndex){
				return  value + "   /  " + rowData.clickAccount;
			}},
			{field:'lastReplyUserName',title:'最后发表  ',width:60,align:'center',formatter:function(value,rowData,rowIndex){
				return value + "<br>" + rowData.lastReplyTimeStr;
			}},
		]]
	});
}
function updateFunc(){
	
}





/**
 * 详情信息
 */
function showInfoFunc(sid,topicSectionId){
  var url = contextPath + "/system/subsys/topic/topicManage/topicDetail.jsp?returnFlag=4&topicSectionId="+topicSectionId+"&uuid=" + sid;
  parent.location.href = url;
}

function returnFunc(){
	var url = contextPath + "/system/subsys/topic/index.jsp";
	location.href = url;
}


</script>
</head>
<body onload="doInit()" >
	<table id="datagrid" fit="true" ></table>
</body>
</html>