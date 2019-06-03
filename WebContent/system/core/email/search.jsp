<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<style>
</style>
<script>
function search(){
	//进行表单提交
	$("#form1").submit();
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_chaxun.png">
		<span class="title">查询邮件</span>
	</div>
	<div class = "right fr clearfix">
	    <input class="btn-win-white" type="button" value="查询" onclick="search();"/>
	</div>
</div>

<form action="searchlist.jsp" method="post" id="form1">
	<table class="TableBlock" style="width:100%;background-color:#fff;" >
		<tr>
			<td class="TableData" style="width:200px;text-indent: 15px;">
				邮箱：
			</td>
			<td class="TableData">
				<select class="BigSelect" name="box" id="box" style="width:150px">
					<option  value="1">收件箱</option>
					<option  value="2">草稿箱</option>
					<option  value="3">已发送</option>
					<option  value="4">已删除</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				状态：
			</td>
			<td class="TableData">
				<select class="BigSelect" name="status" id="status" style="width:150px">
					<option value="0">所有</option>
					<option value="1">未读</option>
					<option value="2">已读</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				日期：
			</td>
			<td class="TableData">
				<input type="text" id='startTime' name='startTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate BigInput" />
				&nbsp;至&nbsp;
				<input type="text" id='endTime' name='endTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})" class="Wdate BigInput" value="<%=TeeDateUtil.format(new Date()).substring(0,(TeeDateUtil.format(new Date()).length()-3))%>"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				发件人：
			</td>
			<td class="TableData">
				<input type="text" id='sendUserName' readonly name='sendUserName' class="BigInput readonly" />
				<input type="hidden" id='sendUser' name='sendUser' class="BigInput" />
				&nbsp;<img id="img1" onclick="selectSingleUser(['sendUser','sendUserName'])"
				 class = '' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png">
				<!-- <a href="javascript:void(0)" onclick="selectSingleUser(['sendUser','sendUserName'])">选择</a> -->
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				外部发件人：
			</td>
			<td class="TableData">
				<input type="text" id='webSendUser' name='webSendUser' class="BigInput" />
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				邮件主题包含文字：
			</td>
			<td class="TableData">
				<input type="text" id='subject' name='subject' class="BigInput" />
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 15px;">
				邮件内容包含文字：
			</td>
			<td class="TableData">
				<input type="text" id='content' name='content' class="BigInput" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>