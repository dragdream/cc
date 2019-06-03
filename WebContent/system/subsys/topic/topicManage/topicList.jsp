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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/subsys/topic/css/topic.css"/>
<script type="text/javascript" src="<%=contextPath %>/system/subsys/topic/js/topic.js" ></script>
<title>话题管理</title>
<script type="text/javascript">
var uuid = "<%=uuid%>";
function doInit(){
	getInfoList();
	getInfoByIdFunc(uuid);
}

function getInfoByIdFunc(uuid){
	var prc = getInfoById(uuid);
	$("#topicSectionName").html(prc.sectionName);
	var topicTitleStr = "<a href='#' onclick='returnFunc();'>论坛</a>&nbsp;>>&nbsp;";
		topicTitleStr += prc.sectionName
		+"<span class='colorstyletwo' >(主题数：" + prc.topicCount + ")</span>&nbsp;本版版主：" +prc.managerName;
	$("#topicTitle").html(topicTitleStr);
	if(prc.crPrivFlag || prc.managerPrivFlag){
		$("#addTopicSpan").show();
	}
	
	
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeTopicController/getManageInfoList.action?topicSectionId=<%=uuid%>",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		idField:'uuid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
// 			{field:'uuid',checkbox:true,title:'ID',width:50},
			{field:'subject',title:'主题 ',width:120,formatter:function(value,rowData,rowIndex){
				var fileNameStr = value ;
				if(rowData.isTop == 1){
					fileNameStr = "<img src='<%=contextPath%>/system/subsys/topic/image/forum_top.gif'>&nbsp;" + value ;
				}else{
					fileNameStr = "<img src='<%=contextPath%>/system/subsys/topic/image/forum_comm.gif'>&nbsp;" + value ;
				}
				return "<a  href='#' onclick=showInfoFunc('" + rowData.uuid + "')>" +fileNameStr + "</a>";
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
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("请选择数据行！");
		return ;
	}
	var idsArray = ids.split(",");
	if(idsArray.length>1){
		$.MsgBox.Alert_auto("此操作只能选择一条数据！");
		return ;
	}
	toAddOrUpdate(ids);
	
}


/**
 * 新建编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/subsys/topic/topicManage/addOrUpdateTopic.jsp?topicSectionId=<%=uuid%>&uuid=" + sid;
	window.location.href = url;
}




/**
 * 详情信息
 */
function showInfoFunc(sid){
  var url = contextPath + "/system/subsys/topic/topicManage/topicDetail.jsp?topicSectionId=<%=uuid%>&uuid=" + sid;
  location.href = url;
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
 * 删除信息
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
	var url = contextPath + "/system/subsys/topic/index.jsp?option=板块";
	location.href = url;
}


</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
   <div id="toolbar" class="topbar clearfix">
      <div class="fl left">
          <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/tlq/icon_bankuailiebiao.png">
          <span id="topicSectionName" class="title"></span>&nbsp;&nbsp;（<span id="topicTitle"></span>）
      </div>
      <div class="fr right">
         <input id="addTopicSpan" style="display:none;" type='button' class="btn-win-white" onclick="toAddOrUpdate('')" value="发帖子"/>
		 <input type="button" class="btn-win-white" onclick="returnFunc();"value="返回"/>
      </div>
   </div>
   <table id="datagrid" fit="true" ></table>
</body>

</html>