<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
int box = TeeStringUtil.getInteger(request.getParameter("box"), 1);//邮箱类型
int status = TeeStringUtil.getInteger(request.getParameter("status"), 0);//邮件状态
String startTime = TeeStringUtil.getString(request.getParameter("startTime"));
String endTime = TeeStringUtil.getString(request.getParameter("endTime"));
int sendUser = TeeStringUtil.getInteger(request.getParameter("sendUser"), 0);
String webSendUser = TeeStringUtil.getString(request.getParameter("webSendUser"));
String subject = TeeStringUtil.getString(request.getParameter("subject"));
String content = TeeStringUtil.getString(request.getParameter("content"));

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
</style>
<script>
var box = "<%=box%>";
var status = "<%=status%>";
var startTime = "<%=startTime%>";
var endTime = "<%=endTime%>";
var sendUser = "<%=sendUser%>";
var webSendUser = "<%=webSendUser%>";
var subject = "<%=subject%>";
var content = "<%=content%>";

var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/emailController/mailSearch.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		queryParams:{box:box,status:status,startTime:startTime,endTime:endTime,sendUser:sendUser,webSendUser:webSendUser,subject:subject,content:content},
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		/* pageList: [50,60,70,80,90,100], */
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'fromUserName',title:'发件人',width:120,formatter:function(value,rowData,rowIndex){
				if(rowData.ifWebMail != "0"){
					value = rowData.fromWebMail;
				}
				if(value.length>45){
					value = value.substring(0,45) + "……";
				}
				var emailImg = "<img src='/common/styles/imgs/message_open.gif'/>&nbsp;";
				var attachImg = "&nbsp;&nbsp;";
				var readFlagStr = rowData.readFlag;
				if(rowData.attachMentModel.length>0){
					attachImg = "<span class='glyphicon glyphicon-paperclip' />&nbsp;";
				}
				return emailImg + attachImg + "<a href='#' onclick='readEmailByMailId(" + rowData.sid + ")'>" + value + "</a>";
			}},
			{field:'subject',title:'主题',width:200,formatter:function(value,rowData,rowIndex){
				var readFlagStr = rowData.readFlag;
				var level = rowData.emailLevelDesc;
				if(level!=null&&level!=""){
					if(level!="普通"){
						levelDesc = "<span style='color:red;'>（"+level+"）</span>&nbsp;&nbsp;"; 
					}else{
						levelDesc = "<span>（"+level+"）</span>&nbsp;&nbsp;";
					}
				}else{
					levelDesc = "<span></span>&nbsp;&nbsp;"; 
				}
				if(box=="1" || box=="4"){
					return levelDesc+"<a href='#' onclick='readEmailByMailId(" + rowData.sid + ")'>" + value  + "</a>";
				}else{
					return levelDesc+"<a href='#' onclick='readEmailByMailBodyId(" + rowData.mailBodySid + ")'>" + value  + "</a>";
				}
			}},
			{field:'sendTimeStr',title:'时间',width:80}
		]]
	});
}

function readEmailByMailId(sid){
	openFullWindow(contextPath + "/system/core/email/readEmailByMailId.jsp?sid=" + sid + "");
}

function readEmailByMailBodyId(sid){
	openFullWindow(contextPath + "/system/core/email/readEmailBody.jsp?sid=" + sid + "");
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>

<div id="toolbar" class = "topbar clearfix">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_youxiang.png">
		<span class="title">查询结果</span>
   </div>
   <div class = "right fr clearfix">
	    <input class="btn-win-white" type="button" value="返回" onclick="window.location='search.jsp';"/>
	</div>

</div>

</body>
</html>