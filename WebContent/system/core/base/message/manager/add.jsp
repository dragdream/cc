<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%

	String personId = request.getParameter("personId") == null ? "" : request.getParameter("personId") ;
	String personName = request.getParameter("personName") == null ? "" : request.getParameter("personName") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<title>发送消息</title>
<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>


<script type="text/javascript">

function doInit(){
	
	
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/messageManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert("发送成功！");
			//$("#")
			window.location.reload();
		//	var data = jsonRs.rtData;
		
		
	
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	
	var userListNames = document.getElementById("toIds");
	    if (!userListNames.value) {
	  	  alert("收信人不能为空！");
	  	  userListNames.focus();
	  	  userListNames.select();
	  	  return false;
	    }
    return $("#form1").form('validate'); 
}

function backIndex(){
	window.location.href = "<%=contextPath %>/system/core/org/role/manageRole.jsp";
}




</script>

</head>
<body onload="doInit()">
<table border="0" width="100%" cellspacing="0" cellpadding="3"
	class="small" align="center">
	<tr>
		<td class="Big"><span><span class="Big3">发送信息</span></td>
	</tr>
</table>
<br>
<center style="width:100%;">

<form  method="post" name="form1" id="form1" >
	<table class="TableBlock" width="80%" align="center">

   <tr>
    <td nowrap class="TableHeader" align="left" colspan="2"><img src="<%=imgPath%>/green_arrow.gif" align="absMiddle">发送内部短信</td>
   </tr>
   <tr>
		<td nowrap class="TableData">收信人：</td>
		<td nowrap class="TableData"  align="left">
			<input type="hidden" name="toIds" id="toIds" value="<%=personId%>">
			 <textarea cols="45" name="toIdNames" id="toIdNames" rows="1"
						style="overflow-y: auto;"  class="SmallStatic BigTextarea" wrap="yes" readonly><%=personName%></textarea>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['toIds', 'toIdNames'],'','','','{aa:\'11\',bb:33}')">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('toIds', 'toIdNames')">清空</a>
		</td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">短信内容：<span style=""></span></td>
    <td nowrap class="TableData" align="left">
        <textarea name="content" id = "content" rows="10" cols="80" class="easyui-validatebox BigTextarea"  required="true" ></textarea>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">发送时间：</td>
    <td nowrap class="TableData" align="left">
    <input type="text" name="sendTimeDesc" id="sendTimeDesc" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" >
         *设为空就即时发送
         </td>
   </tr>
   <tr>
	    <td nowrap  class="TableControl" colspan="2" align="center">
	        <input type="button" value="保存" class="btn btn-primary" title="发送短信" onclick="doSave()" >&nbsp;&nbsp;
	        <input type="hidden" value="返回" class="btn btn-primary" title="返回" onClick="backIndex();">
	    </td>
   </tr>
   
</table>
  </form>
  </center>
</body>
</html>
 