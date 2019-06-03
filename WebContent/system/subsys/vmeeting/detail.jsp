<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>视频会议组件设置</title>
<script type="text/javascript">
<%
	String meetNo = TeeStringUtil.getString(request.getParameter("meetNo"));
%>
var meetNo = "<%=meetNo%>";
function doInit(){
	var url = contextPath+"/vmeeting/getMeetingInfo.action?meetNo="+meetNo;
	var json = tools.requestJsonRs(url);
	bindJsonObj2Cntrl(json.rtData);
	$("#startTime").html(getFormatDateStr(json.rtData.startTime,"yyyy-MM-dd hh:mm"));
}
</script>
</head>
<body onload="doInit()" style="padding:10px;">
<br/>
<form  name="form1" id="form1" width="700px">
  <table class="TableBlock" width="700px" style="width:700px" align="center">
   <tr>
    <td colspan='2' class="TableHeader">
   		视频会议详情
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="width:100px">会议名称：</td>
    <td nowrap class="TableData" id="meetName" nowrap="no">
    </td>
   </tr>
    <tr>
    <td nowrap class="TableData">开始时间：</td>
    <td nowrap class="TableData" id="startTime" >
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">主持人：</td>
    <td nowrap class="TableData" id="mainUserName">
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">与会人员：</td>
    <td class="TableData" >
    	<div id="joinerName" style="width:500px;">
    	</div>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">会议主题：</td>
    <td nowrap class="TableData" id="subject">
    </td>
   </tr>
   <tr>
    <td colspan='2' style="text-align:Center">
   	 	<button class="btn btn-default" type="button" onclick="history.go(-1)">返回</button>
    </td>
   </tr>
</table>
<input type="hidden" name="meetNo" value="<%=meetNo%>"/>
</form>
</body>
</html>