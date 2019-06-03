<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

int personId=TeeStringUtil.getInteger(request.getParameter("personId"),0);
String startTime=request.getParameter("startTime");
String endTime=request.getParameter("endTime");

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
var startTime="<%=startTime%>";
var endTime="<%=endTime%>";
var personId=<%=personId%>;
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeFootPrintController/getListByPerson.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		queryParams:{startTime:startTime,endTime:endTime,personId:personId},
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		/* pageList: [50,60,70,80,90,100], */
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'userName',title:'用户名',width:100},
			{field:'addressDescription',title:'地理位置',width:100},
			{field:'crTimeStr',title:'时间',width:100}
		]],onLoadSuccess:function(data){
	        if(data.total > 0){ 
	            $("#imgbtn").show();
	          } ;
		}
	});
}


//图形化展示
function viewImg(){
	var url="<%=contextPath%>/system/subsys/footprint/manage/footPrintImgByPerson.jsp?startTime="+startTime+"&&endTime="+endTime+"&&personId="+personId;
	openFullWindow(url);
}

//返回
function back(){
	var url="<%=contextPath%>/system/subsys/footprint/manage/query.jsp?personId="+personId;
	window.location.href=url;
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>

<div id="toolbar" class = "topbar clearfix">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/footprint/img/icon_chaxunjieguo.png">
		<span class="title">查询结果</span>
   </div>
   <div class = "right fr clearfix">
        <input id="imgbtn" style="display: none;" class="btn-win-white" type="button" value="图形化展示" onclick="viewImg();"/>
	    <input class="btn-win-white" type="button" value="返回" onclick="back();"/>
	</div>

</div>

</body>
</html>