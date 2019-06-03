<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String uuid = request.getParameter("uuid");


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重发</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">

/**
 * 新建或编辑
 */
function doSaveOrUpdate(callback){
	if(checkForm()){
		var url = contextPath + "/doc/reSendDocViewById.action?uuid=<%=uuid%>";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			callback();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsrg);
		}
	}
}


function checkForm(){
	if($("#sendUserIds").val()==""){
		$.MsgBox.Alert_auto("请选择人员！");
		return false;
	}
	return true;
}

</script>
<style>
.TableBlock tr>td>textarea{
	margin:0;
}

</style>

</head>
<body style="width: 100%;background-color: #f2f2f2;padding: 10px;">
<form id="form1" name="form1">
<table class="TableBlock">
   <tr>
     <td>
       <label style='font-size:14px'>选择要发送的人员：</label>
       <textarea class="BigTextarea" id="sendUserNames" name="sendUserNames" readonly style="width:500px;height:100px;font-family: MicroSoft YaHei;"></textarea>&nbsp;&nbsp;
		<input type="hidden" id="sendUserIds" name="sendUserIds" />
		<span class='addSpan'>
	          <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/gwgl/cyfk/add.png" onClick="selectUser(['sendUserIds','sendUserNames'])" value="选择"/>
			                &nbsp;&nbsp;
			  <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/gwgl/cyfk/clear.png" onClick="clearData('sendUserIds','sendUserNames')" value="清空"/>
	     </span>
     
     
     </td>
   </tr>



</table>

</form>

</body>
</html>