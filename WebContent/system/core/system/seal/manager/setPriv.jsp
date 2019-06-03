<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
   String sid = request.getParameter("sid");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<title>设置印章权限</title>
<script type="text/javascript" src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/orgselect.js"></script>




<script type="text/javascript">
var sid = "<%=sid%>";
var sealName = "";
function doInit(){
	var url = "<%=contextPath%>/sealManage/selectById.action";
	var para =  {sid : sid };
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		data = jsonRs.rtData;
		bindJsonObj2Cntrl(data);
		var sealName = data.sealName;
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/sealManage/setSealPriv.action?";
		var userStr = $("#userStr").val();
		var userStrDesc = $("#userStrDesc").val();
		var para =  {sid : sid ,sealName : sealName, userStr : userStr , userStrDesc: userStrDesc};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("权限设置成功！");
			backIndex();//返回
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	    return true;
}

function backIndex(){
	window.location.href = "<%=contextPath %>/system/core/system/seal/manager/manage.jsp";
}



</script>

</head>
<body onload="doInit()">
<form  method="post" name="form1" id="form1" >
<table class="TableBlock_page" width="95%" align="center">

   
   <tr>
    <td nowrap class="TableData" width="120" align="left" style="text-indent: 15px;">印章名称：</td>
    <td nowrap class="TableData" id="sealName"  align="left">
      
    </td>
   </tr>
   <tr>
   	 	<td nowrap class="TableData" align="left" style="text-indent: 15px;">授权范围：</td>
    	<td nowrap class="TableData" align="left">
  	 		 <input name="userStr" type="hidden" id="userStr">
   			<textarea readonly name="userStrDesc" id="userStrDesc" class="BigTextarea" rows="5" cols="30" class="Static"> </textarea>
    
            <span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectUser(['userStr', 'userStrDesc'])" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="clearData('userStr', 'userStrDesc')" value="清空"/>
			</span>
         </td>
   </tr>
   <tr>
	    <td nowrap  class="TableControl" colspan="2" style="text-indent: 10px">
	          <input type="button" value="保存" class="btn-win-white" title="保存用户" onclick="doSave()" >&nbsp;&nbsp;
	          <input type="button" value="返回" class="btn-win-white" title="返回" onClick="backIndex();">
	    </td>
   </tr>
   
</table>
</form>
</body>
</html>
 