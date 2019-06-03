<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
int personId=TeeStringUtil.getInteger(request.getParameter("personId"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>足迹查询</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<style>
</style>
<script>
var personId=<%=personId%>;
//验证
function check(){
	//获取开始时间和结束时间
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(startTime==""||startTime==null){
		$.MsgBox.Alert_auto("请填写开始时间！");
		return false;
	}
	if(endTime==""||endTime==null){
		$.MsgBox.Alert_auto("请填写结束时间！");
		return false;
	}
	return true;
}


function search(){
	//获取开始时间和结束时间
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if(check()){
		window.location.href="<%=contextPath %>/system/subsys/footprint/manage/result.jsp?startTime="+startTime+"&&endTime="+endTime+"&&personId="+personId;
	}
	
}
</script>
</head>
<body onload="" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/footprint/img/icon_zujichaxun.png">
		<span class="title">足迹查询</span>
	</div>
	<div class = "right fr clearfix">
	    <input class="btn-win-white" type="button" value="查询" onclick="search();"/>
	</div>
</div>

<form action="" method="post" id="form1">
	<table class="TableBlock" style="width:100%;background-color:#fff;" >
		
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				开始时间：
			</td>
			<td class="TableData">
				<input type="text" id='startTime' name='startTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate BigInput" value="<%=TeeDateUtil.format(new Date()).substring(0,(TeeDateUtil.format(new Date()).length()-8))+" 00:00"%>"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				结束时间：
			</td>
			<td class="TableData">
				
				<input type="text" id='endTime' name='endTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})" class="Wdate BigInput" value="<%=TeeDateUtil.format(new Date()).substring(0,(TeeDateUtil.format(new Date()).length()-3))%>"/>
			</td>
		</tr>
		
	</table>
</form>
</body>
</html>