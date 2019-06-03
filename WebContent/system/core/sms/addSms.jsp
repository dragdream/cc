<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String userId = TeeStringUtil.getString(request.getParameter("userId"), ""); 
	String userName = TeeStringUtil.getString(request.getParameter("userName"), ""); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>发送内容短信</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">

function doInit(){
	
	
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/sms/addSms.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("发送成功!");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	
	var userListNames = document.getElementById("userListNames");
	    if (!userListNames.value) {
	    	$.MsgBox.Alert_auto("收信人不能为空！");
	  	  userListNames.focus();
	  	  userListNames.select();
	  	  return false;
	    }
    return $("#form1").valid(); 
}

function backIndex(){

	window.location.href = "<%=contextPath %>/system/core/org/role/manageRole.jsp";
}


</script>
<style>
	table{
		width:100%;
		border-collapse:collapse;
	}
	table tr {
		line-height:35px;
		width:100%;
		height:auto;
		border-bottom:1px solid #f2f2f2;
		padding:5px 0;
	}
	table tr input{
		line-height:16px;
	}
	table tr td:first-child{
		width:200px;
		text-align:left;
		text-indent:8px;
	}
	.btns{
		text-align:center!important;
	}
	textarea{
		margin:10px 0!important;
		margin-bottom:0!important;
	}
</style>

</head>
<body onload="doInit()" style="padding: 10px 10px 0 20px;">
<center>
<form  method="post" name="form1" id="form1" >
<br/>
<table class="'TableBlock_page'"  align="center">
   	<tr style="height: 50px;">
				<td  class="TableData" >收信人：</td>
				<td  class="TableData" style="text-align:left;">
				<input type="hidden" name="userListIds" id="userListIds" required value="<%=userId %>">
			    <input cols="45" name="userListNames" id="userListNames" rows="1" style="font-family: MicroSoft YaHei;font-size: 12px;height: 25px;width: 380px; border: 1px solid #dadada;" class="BigInput readonly" wrap="yes" readonly  value="<%=userName %>"/>
				&nbsp;&nbsp;
				<span name="addSpan" class='addSpan'>
				    <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/grbg/message/add.png"  onClick="selectUser(['userListIds', 'userListNames'])"></img>&nbsp;&nbsp;
			        <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/grbg/message/clear.png"  onClick="clearData('userListIds', 'userListNames')"></img>
			    </span>
				
			</tr>
   <tr style="height: 180px;">
    <td  class="TableData" width="120" >短信内容：<span style=""></span></td>
    <td  class="TableData" style="text-align:left;">
        <textarea style="font-family: MicroSoft YaHei;font-size: 12px;" name="content" id = "content" rows="10" cols="60" class="BigTextarea"  required></textarea>
    </td>
   </tr>
   <tr style="height: 50px;">
    <td  class="TableData" >发送时间：</td>
    <td  class="TableData" style="text-align:left;">
    <input style="width:380px;height: 25px;" type="text" name="sendTime" id="sendTime" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate BigInput readonly" readonly>
         *设为空就即时发送
         </td>
   </tr>
   <tr style="height: 45px;">
	    <td colspan="2"   style="text-align: center;">
	        <input style="width: 45px;height: 25px;" type="button" value="保存" class="btn-win-white" title="发送短信" onclick="doSave()" >
	    </td>
   </tr>
   
</table>
  </form>
  </center>
</body>
<script>
	$("#form1").validate();
</script>
</html>
 