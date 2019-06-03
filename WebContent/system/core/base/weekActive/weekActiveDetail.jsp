<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0) ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>周活动安排详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	if(sid>0){
		getInfoById(sid);
	}
}


/* 查看详情 */
function getInfoById(sid){
	var url =   "<%=contextPath%>/weekActiveController/getInfoById.action";
	var para = {sid : sid};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}





</script>
<style type="">
		.TableBlock_page{
			border:1px solid #f2f2f2;
		}
		.TableBlock_page td{
			border:1px solid #f2f2f2;
			text-align:center;
		}
</style>
</head>
<body onload="doInit();" style="overflow:hidden;padding-left: 150px;padding-right: 150px;">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<div align="center" style="font-size:26px;font-family:'楷体';margin-top: 50px;margin-bottom: 20px;>
	<h4 id="title">周活动安排详情</h4>
</div>
<table  width="80%" class="TableBlock_page">
	<tr>
		<td style="line-height:25px;font-size:14px;background-color:#f9fdff;width:200px;" >时间：</td>
		<td colspan="3" id="activeStart" style="text-align:left;padding-left: 10px;padding-right: 5px;">
		</td>
	</tr>
	<tr>
		<td style="line-height:25px;font-size:14px;background-color:#f9fdff;" >出席：</td>
		<td id="activeUserName" style="text-align:left;padding-left: 10px;padding-right: 5px;">
		</td>
	</tr>
	<tr>
		<td style="font-size:14px;background-color:#f9fdff;">内容：</td>
		<td colspan="3" id="activeContent" style="text-align:left;padding-left: 10px;padding-right: 5px;">
		</td>
	</tr>
</table>
</form>
</body>
</html>