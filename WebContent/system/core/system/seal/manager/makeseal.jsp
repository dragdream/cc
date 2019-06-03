<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>


<script type="text/javascript">


function doInit(){
}


function doSave(){
	if (checkForm()){
		
		 var sealId = $('#sealId').val();
		 
		 var sealName = $('#SealName').val();
		
		 var url = "<%=contextPath%>/sealManage/addOrUpdateSeal.action";
		
		var para = {sealId:sealId,sealName:sealName};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			parent.$.MsgBox.Alert_auto("创建成功");
			window.location.reload();
			return true;
		}else{
			parent.$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	return $("#form1").valid();
}

</script>

</head>

<body topmargin="5" onload="doInit()">
<form name="form1" id="form1">
<table class="TableBlock_page" border=0 align="center" width=80%  style="margin-top: 5px;">
  <tr>
    <td class="TableData" style="text-indent: 10px">规则号</td>
    <td class="TableData">
    	<input type="text" style="width:70px" name="sealId" id="sealId" class="BigStatic  BigInput"  size=6 maxlength="6" value="">
    </td>
  </tr>
  <tr>
    <td  class="TableData" width=80 style="text-indent: 10px">规则名称</td>
    <td class="TableData">
    	<input type="text" name="SealName" id="SealName"  class="easyui-validatebox BigInput"  required="true"    maxlength="32">	
    <br></td>
  </tr>
  <tr>
    <td align="center" colspan=4 class="TableFooter">
    	<div align="center"><input class="btn-win-white" type="button" value="创建规则" LANGUAGE=javascript onclick="doSave()"/>&nbsp;</div>
      </td>
  </tr>
</table>
</form>

</body>
<script>
$(function(){
	$("#form1").validate();

});

</script> 
</html>
