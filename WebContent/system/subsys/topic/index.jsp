<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String options=TeeStringUtil.getString(request.getParameter("option"));
 if(TeeUtility.isNullorEmpty(options)){
	 options="板块";
 }

TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>论坛</title>
<script type="text/javascript">
var option="<%=options%>";
function doInit(){
	//getInfoList1();
}

var datagrid;
function getInfoList1(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/TeeTopicSectionController/getManageInfoList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:false,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		columns:[[
			//{field:'uuid',checkbox:true,title:'ID',width:10},
			{field:'sectionName',nowrap:false,title:'板块',width:100,formatter:function(value,rowData,rowIndex){
				var valueStr = "";
				if(value){
					valueStr = "<a href='#' onclick=showInfoFunc('" + rowData.uuid + "')>" + value + "</a>";
					valueStr += "<br><span style='color:gray' title='"+rowData.remark+"'>" + rowData.remark+"</span>";
				}
				return valueStr;
			}},
			{field:'topicCount',title:'主题',width:20},
			{field:'topicReplyCount',title:'文章',width:20,formatter:function(value,rowData,rowIndex){
				if(value){
					value += "";
				}
				return value;
			}},
			{field:'requDeptStrName',title:'最后发表 ',width:70,formatter:function(value, rowData, rowIndex){
				var postDesc = "<span class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>┌ 主题：</span><a href='#' onclick=showTopicFunc('" + rowData.uuid + "','" +  rowData.lastTopicId + "')>" +  rowData.lastTopicSubject + "</a>"
				+ "<br><font class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>├ 作者 ：</font>" + rowData.lastReplyUserName
				+ "<br><font class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>└ 时间 ：</font>" + rowData.lastReplyTimeStr;
				
				return postDesc;
			}},
			{field:'managerName',title:'版主 ',width:100,formatter:function(value, rowData, rowIndex){
				return "<span title='"+value+"'>"+value+"</span>";
			}},
		]]
	});
}

/**
 * 进入话题
 */
function showTopicFunc(topicSectionId,sid){
	var url = contextPath + "/system/subsys/topic/topicManage/topicDetail.jsp?topicSectionId=" + topicSectionId + "&uuid=" + sid;
	location.href = url;
}

/* 获取附件模块列表 */
function getInfoList(){
	var url = "<%=contextPath%>/TeeTopicSectionController/getTopicSectionList.action";
	var jsonObj = tools.requestJsonRs(url);
	if (jsonObj.rtState) {
		var prcs = jsonObj.rtData;
		//alert(prcs[0].sectionName + ">>" + prcs[0].remark);
		if(prcs.length>0){
			jQuery.each(prcs,function(i,sysPara){
				var postDesc = "<span class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>┌ 主题：</span>" + sysPara.lastTopicName
				+ "<br><font class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>├ 作者 ：</font>" + sysPara.lastTopicAuthor
				+ "<br><font class='ui-state-highlight ui-corner-al'  style='font-weight:bold;'>└ 时间 ：</font>" + sysPara.lastTopicTime;
			$("#tbody").append("<tr class='TableData' >"
								+"<td align='left'><a href='javascript:void(0);' onclick=showInfoFunc('" + sysPara.uuid + "')>" + sysPara.sectionName + "</a><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + sysPara.remark +"</td>"
								+"<td align='center'>" + sysPara.topicCount + "</td>"
								+"<td align='center'>" + sysPara.topicReplyCount + "</td>"
								+"<td align='left'>" + postDesc + "</td>"
								+"<td align='left'>" + sysPara.manager + "</td>"
							+ "</tr>"); 
			});
			
			
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

//进入话题界面
function showInfoFunc(sid){
	var  url = contextPath + "/system/subsys/topic/topicManage/topicList.jsp?uuid=" + sid;
	window.location.href = url;
}
//我的帖子
function myTopicFunc(){
	var  url = contextPath + "/system/subsys/topic/myTopicList.jsp";
	window.location.href = url;
}
//最新帖子
function latelyTopicFunc(){
	var  url = contextPath + "/system/subsys/topic/latelyTopicList.jsp";
	window.location.href = url;
}
//周热门贴
function weekTopicFunc(){
	var  url = contextPath + "/system/subsys/topic/weekTopicList.jsp";
	window.location.href = url;
}
//月热门贴
function monthTopicFunc(){
	var  url = contextPath + "/system/subsys/topic/monthTopicList.jsp";
	window.location.href = url;
}


/**
 * 新建编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/core/base/hr/recruit/requirements/addOrUpdateRecruitRequire.jsp?sid=" + sid;
	//window.location.href = url;
}




function topicManage(){
	var url = contextPath + "/system/subsys/topic/topicSectionManage/topicSectionManage.jsp";
	location.href = url;
}


</script>
</head>
<body onload="doInit()" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class="titlebar clearfix">
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/tlq/icon_tlq.png">
		<p class="title">讨论区</p>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
</div>	
<div id="tab-content" style="padding-left: 10px;padding-right:10px;"></div>
	
</body>
<script>
var arr;
 <%
	if(isAdmin){
	%>
	 arr=[{title:"板块",url:contextPath+"/system/subsys/topic/topicSection.jsp"},
          {title:"我的帖子",url:contextPath + "/system/subsys/topic/myTopicList.jsp"},
          {title:"最新发帖",url:contextPath + "/system/subsys/topic/latelyTopicList.jsp"},
          {title:"周热门贴",url:contextPath + "/system/subsys/topic/weekTopicList.jsp"},
          {title:"月热门贴",url:contextPath + "/system/subsys/topic/monthTopicList.jsp"},
          {title:"板块管理",url:contextPath + "/system/subsys/topic/topicSectionManage/topicSectionManage.jsp"}];
	 for(var i=0,l=arr.length;i<l;i++){
		if(option == arr[i].title){
			arr[i].active=true;
		}
	 }
	 $.addTab("tab","tab-content",arr); 
	<%
	}else{
	%>
	arr=[{title:"板块",url:contextPath+"/system/subsys/topic/topicSection.jsp"},
         {title:"我的帖子",url:contextPath + "/system/subsys/topic/myTopicList.jsp"},
         {title:"最新发帖",url:contextPath + "/system/subsys/topic/latelyTopicList.jsp"},
         {title:"周热门贴",url:contextPath + "/system/subsys/topic/weekTopicList.jsp"},
         {title:"月热门贴",url:contextPath + "/system/subsys/topic/monthTopicList.jsp"}];
	for(var i=0,l=arr.length;i<l;i++){
		if(option== arr[i].title){
			arr[i].active=true;
		}
	 }
	$.addTab("tab","tab-content",arr); 	
	
	<%	
	}
	%>
</script>
</html>