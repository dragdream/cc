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
	<title>组织机构详情</title>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/xzfy/js/common/common.js"></script>
</head>
<body onload="doInit()" style="background-color: #f2f2f2">
	<table class="TableBlock" width="100%" align="center">
 		<tr>
        	<td nowrap class="TableHeader" colspan="7"><b>&nbsp;组织机构信息</b></td>
    	</tr>
    	<tr>
     		<td nowrap class="TableData" width="150" style="padding-left:20px">机关名称：</td>
      		<td class="TableData" colspan="4" id="orgName"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">机关编码：</td>
      		<td class="TableData" colspan="4" id="orgCode"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">机关层级：</td>
      		<td class="TableData" colspan="4" id="orgLevelName"></td>
   		</tr>
   		<tr>
      		<td nowrap class="TableData" style="padding-left:20px">法人：</td>
      		<td class="TableData" colspan="4" id="legalRepresentative"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">编制人数：</td>
      		<td class="TableData" colspan="4" id="compilersNum"></td>
   		</tr>
   		<tr>
      		<td nowrap class="TableData" width="100" style="padding-left:20px">联系人：</td>
      		<td class="TableData" colspan="4" id="contacts"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" width="100" style="padding-left:20px">联系人电话：</td>
      		<td class="TableData" colspan="4" id="contactsPhone"></td>
   	 	</tr>
   	 	
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">传真：</td>
      		<td class="TableData" colspan="4" id="fax"></td>
    	</tr>
    	<tr>
      		<td nowrap class="TableData" style="padding-left:20px">邮政编码：</td>
     		<td class="TableData" colspan="4" id="areaCode"></td>
    	</tr>
    	
    	<tr>
    	<td nowrap class="TableData" style="padding-left:20px">备注：</td>
     		<td class="TableData" colspan="4" id="remark"></td>
    	</tr>

  	</table>
	<script type="text/javascript" src="/xzfy/js/organ/queryDetail.js"></script>
</body>
</html>
 