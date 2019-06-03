<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/subsys/topic/js/topic.js" ></script>
<title>板块</title>
<script type="text/javascript">
function doInit(){
	getInfoList1();
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
	var url = contextPath + "/system/subsys/topic/topicManage/topicDetail.jsp?topicSectionId=" + topicSectionId + "&uuid=" + sid+"&returnFlag=5";
	parent.location.href = url;
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
	parent.window.location.href = url;
}
</script>
</head>
<body onload="doInit()" >
	<table id="datagrid" fit="true" ></table>
</body>
</html>