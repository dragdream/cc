<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String uuid = TeeStringUtil.getString(request.getParameter("uuid"), "");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/subsys/topic/js/topic.js" ></script>
<title>话题管理</title>
<script type="text/javascript">
var uuid = "<%=uuid%>";
function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeTopicController/getMyTopicList.action",
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
 * 新建编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/subsys/topic/topicManage/addOrUpdateTopic.jsp?topicSectionId=<%=uuid%>&uuid=" + sid;
	window.location.href = url;
}




/**
 * 详情信息 把topicSectionId传递给下一个页面
 */
function showInfoFunc(sid,topicSectionId){
  var url = contextPath + "/system/subsys/topic/topicManage/topicDetail.jsp?returnFlag=1&topicSectionId="+topicSectionId+"&uuid=" + sid;
  parent.location.href = url;
}

/**
 * 单个删除维护信息
 */
function deleteSingleFunc(sid){
	deleteObjFunc(sid);
}
/**
 * 批量删除
 */
function batchDeleteFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
	 $.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		 var url = contextPath + "/TeeTopicSectionController/deleteObjById.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			} 
	  });
}
/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].uuid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
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