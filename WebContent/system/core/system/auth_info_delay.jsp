<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.tianee.webframe.util.auth.TeeAuthUtil"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<title><%=ieTitle%></title>
<style>

</style>
</head>
<% 
	Map<String,String> registInfo = TeeAuthUtil.getRegistInfo();
	Map<String,String> delayInfo = TeeAuthUtil.getDelayInfo();
	
%>
<body style="padding-left: 10px;padding-right: 10px">
    <div id="toolbar" class="topbar clearfix">
	   <img class="title_img" src="/system/core/system/imgs/icon_yqsqxx.png" alt="">
	   &nbsp;<span class="title">延期授权信息</span>
	   
	   <button class="btn-win-white fr" onclick="window.location='auth_info.jsp'">返回</button>	   
	</div>

	<table class="TableBlock_page" style="width:100%">
		<tr>
			<td>授权机器码：</td>
			<td><%=delayInfo==null?"":delayInfo.get("machineCode")%></td>
		</tr>
		<tr>
			<td>试用开始日期：</td>
			<td><%=delayInfo==null?"":delayInfo.get("beginTime")%></td>
		</tr>
		<tr>
			<td>试用结束日期：</td>
			<td><%=delayInfo==null?"":delayInfo.get("endTime")%></td>
		</tr>
		<tr>
			<td>移动端人数限制：</td>
			<td><%=delayInfo==null?"30":delayInfo.get("imLimit")%></td>
		</tr>
		<tr>
			<td>即时通讯PC端人数限制：</td>
			<td><%=delayInfo==null?"30":delayInfo.get("pcLimit")%></td>
		</tr>
		<tr>
			<td>OA用户数限制：</td>
			<td><%=delayInfo==null?"30":delayInfo.get("personLimit")%></td>
		</tr>
		<tr>
			<td>上传延期文件：</td>
			<td style="text-align:left">
				<form action="<%=contextPath %>/registAuth/uploadDelayFile.action" method="post" enctype="multipart/form-data">
					<input type="file" name="file" />
					<input type="submit" value="提交" class="btn-win-white"/>
				</form>
			</td>
		</tr>
	</table>

</body>
</html>
