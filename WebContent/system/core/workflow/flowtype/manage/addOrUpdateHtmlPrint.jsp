<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	String flowId = request.getParameter("flowId") == null ? "0" : request.getParameter("flowId");
	String sid = request.getParameter("sid") == null? "": request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>Word打印模版</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/upload.jsp"%>
<style type="text/css">
.imgMiddle{
	float:left;
	margin-top:5px;
}
.imgMiddleSpan{
	float:left;
	margin-top:4px;
}
</style>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
var flowId = '<%=flowId%>';
var sid = '<%=sid%>';	
function doInit(){
	if(sid != "" && sid != '0'){
		getInfoBySid();
	}
	
}


function getInfoBySid(){
	var url=contextPath+"/teeHtmlPrintTemplateController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		$("#templateName").val(data.templateName);
	}
}

/**
 * 保存
 */
function doSave(callback){
	if (check()){
		var para =  {};
		para["templateName"] = $("#templateName").val();
		para["flowTypeId"] = flowId;
		para["sid"] = sid;
		
		var url=contextPath+"/teeHtmlPrintTemplateController/addOrUpdate.action";
		var json=tools.requestJsonRs(url,para);
		return json;
		
	}
	return false;
}

function check() {
	var modelName = $("#templateName").val();
	if(modelName == ''){
		alert("请输入模版名称");
		return false ;
	}
	
	return true;
	
}
</script>

</head>
<body onload="doInit()">

<form  method="post" name="form1" id="form1" enctype="multipart/form-data">
<table class="none-table">
   <tr>
    <td nowrap class="TableData">模板名称：</td>
    <td nowrap class="TableData">
    	<input type="text" id="templateName" name="templateName" class="BigInput"/>
    </td>
   </tr>

</table>
  </form>
</body>
</html>
 