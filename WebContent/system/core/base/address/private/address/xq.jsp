<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	//String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
String sid = request.getParameter("sid");
%>
<%
response.addHeader("X-UA-Compatible", "IE=EmulateIE9");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加通讯组</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

var sid = <%=sid%>;
function doInit(){
	loadData(sid);
}


function loadData(sid){
	var url = "<%=contextPath%>/teeAddressController/getPersonById.action";
	var jsonRs = tools.requestJsonRs(url,{"sid":sid});
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data){
			bindJsonObj2Cntrl(data);
			var sexDesc = "男";
			if(data.sex == '1'){
				sexDesc = "女";
			}
			$("#sex").html(sexDesc);
		
		}
	}else{
		alert(jsonRs.rtMsg);
	}
}
</script>
 
</head>
<body onload="doInit()" style="margin:5px;">
 <table class="TableBlock" width="100%" align="center">
 	<tr>
      <td nowrap class="TableHeader" colspan="5"><b>&nbsp;个人信息</b></td>
    </tr>
    <tr>
      <td nowrap class="TableData" width="100"> 姓名：</td>
      <td class="TableData" colspan=4 id="userName"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 性别：</td>
      <td class="TableData" colspan=4 id="sex"></td>
    </tr>
 
   
    <tr>
      <td nowrap class="TableData"> 角色：</td>
      <td class="TableData" colspan=4 id="userRoleStrName"></td>
    </tr>
    <tr>
      <td nowrap class="TableHeader" colspan="5"><b>&nbsp;联系方式（单位）</b></td>
    </tr>
    <tr>
      <td nowrap class="TableData" width="100"> 部门名称：</td>
      <td class="TableData" colspan=4 id="deptIdName"></td>
    </tr>
  
  </table>
 <table class="TableBlock" width="100%" align="center">
    <tr>
      <td nowrap class="TableHeader" colspan="5"><b>&nbsp;联系方式（个人）</b></td>
    </tr>
    <tr>
      <td nowrap class="TableData" width="100"> 个人地址：</td>
      <td class="TableData" colspan=4 id="addHome"></td>
    </tr>
    
    <tr>
      <td nowrap class="TableData"> 个人电话：</td>
      <td class="TableData" colspan=4 id="telNoDept"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 手机：</td>
      <td class="TableData" colspan=4 id="mobilNo"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 电子邮件：</td>
      <td class="TableData" colspan=4 id="email"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> QQ号码：</td>
      <td class="TableData" colspan=4 id="oicqNo"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 备注：</td>
      <td class="TableData" colspan=4 id="notes"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" colspan="5">
        
      </td>
    </tr>
  </table>

</body>
</html>
 