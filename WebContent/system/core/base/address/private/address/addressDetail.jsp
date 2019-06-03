<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
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
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

var id = '<%=id%>';
function doInit(){
	loadData(id);
}


function loadData(id){
	var url = "<%=contextPath%>/teeAddressController/getAddressById.action";
	var jsonRs = tools.requestJsonRs(url,{"id":id});
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data){
			bindJsonObj2Cntrl(data);
			var sexDesc = "男";
			if(data.sex == '1'){
				sexDesc = "女";
			}
			$("#sex").html(sexDesc);
			
			var nickName = "";
			if(data.nickName){
				nickName = data.nickName;
			}
			$("#nickName").html(nickName);
			
			
			var birthday = "";
			if(data.birthday){
				birthday = data.birthday;
			}
			$("#birthday").html(birthday);
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}
</script>
 
</head>
<body onload="doInit()" style="background-color: #f2f2f2">
 <table class="TableBlock" width="100%" align="center">
 	<tr>
      <td nowrap class="TableHeader" colspan="5"><b>&nbsp;个人信息</b></td>
    </tr>
    <tr>
      <td nowrap class="TableData" width="100" style="padding-left:20px"> 姓名：</td>
      <td class="TableData" colspan=4 id="psnName"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 性别：</td>
      <td class="TableData" colspan=4 id="sex"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 生日：</td>
      <td class="TableData" colspan=4 id="birthday"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 昵称：</td>
      <td class="TableData" colspan=4 id="nickName"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 职务：</td>
      <td class="TableData" colspan=4 id="ministration"></td>
    </tr>
    <tr>
      <td nowrap class="TableHeader" colspan="5"><b>&nbsp;联系方式（单位）</b></td>
    </tr>
    <tr>
      <td nowrap class="TableData" width="100" style="padding-left:20px"> 单位名称：</td>
      <td class="TableData" colspan=4 id="deptName"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 单位地址：</td>
      <td class="TableData" colspan=4 id="addDept"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 单位邮编：</td>
      <td class="TableData" colspan=4 id="postNoDept"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 工作电话：</td>
      <td class="TableData" colspan=4 id="telNoDept"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 工作传真：</td>
      <td class="TableData" colspan=4 id="faxNoDept"></td>
    </tr>
  </table>
 <table class="TableBlock" width="100%" align="center">
    <tr>
      <td nowrap class="TableHeader" colspan="5"><b>&nbsp;联系方式（个人）</b></td>
    </tr>
    <tr>
      <td nowrap class="TableData" width="100" style="padding-left:20px"> 个人地址：</td>
      <td class="TableData" colspan=4 id="addHome"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 个人邮编：</td>
      <td class="TableData" colspan=4 id="postNoHome"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 个人电话：</td>
      <td class="TableData" colspan=4 id="telNoHome"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 手机：</td>
      <td class="TableData" colspan=4 id="mobilNo"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 电子邮件：</td>
      <td class="TableData" colspan=4 id="email"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> QQ号码：</td>
      <td class="TableData" colspan=4 id="oicqNo"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="padding-left:20px"> 备注：</td>
      <td class="TableData" colspan=4 id="notes"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" colspan="5">
        
      </td>
    </tr>
  </table>

</body>
</html>
 