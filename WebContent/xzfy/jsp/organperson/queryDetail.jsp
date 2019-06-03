<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
	response.addHeader("X-UA-Compatible", "IE=EmulateIE9");
%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>组织机构人员详情</title>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
</head>
<body onload="doInit()" style="background-color: #f2f2f2">
	<table class="TableBlock" width="100%" align="center">
 		<tr>
        	<td nowrap class="TableHeader" colspan="7"><b>&nbsp;组织机构人员信息</b></td>
    	</tr>
    	<tr>
     		<td nowrap class="TableData" width="100" style="padding-left:20px">机关名称：</td>
      		<td class="TableData" colspan=4 id="orgName"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">姓名：</td>
      		<td class="TableData" colspan=4 id="personName"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">性别：</td>
      		<td class="TableData" colspan=4 id="sex"></td>
   		</tr>
   		<tr>
      		<td nowrap class="TableData" style="padding-left:20px">身份证：</td>
      		<td class="TableData" colspan=4 id="idCard"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">人员编制：</td>
      		<td class="TableData" colspan=4 id="staffingName"></td>
   		</tr>
   		<tr>
      		<td nowrap class="TableData" width="100" style="padding-left:20px">职级：</td>
      		<td class="TableData" colspan=4 id="levelName"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" width="100" style="padding-left:20px">学历：</td>
      		<td class="TableData" colspan=4 id="educationName"></td>
   	 	</tr>
   	 	
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">是否获取法律证书：</td>
      		<td class="TableData" colspan=4 id="isLaw"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">是否党员：</td>
      		<td class="TableData" colspan=4 id="isParty"></td>
   		</tr>
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">电话：</td>
     		<td class="TableData" colspan=4 id="phone"></td>
    	</tr>
    	<tr>
      	<td nowrap class="TableData" style="padding-left:20px">邮箱：</td>
     		<td class="TableData" colspan=4 id="email"></td>
    	</tr>

  	</table>
	<script type="text/javascript" src="/xzfy/js/organperson/queryDetail.js"></script>
</body>
</html>
 