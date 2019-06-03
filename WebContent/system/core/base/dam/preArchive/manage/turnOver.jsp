<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	String  ids=TeeStringUtil.getString(request.getParameter("ids"));
    // opt="single"  or "batch"
    String opt=TeeStringUtil.getString(request.getParameter("opt"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>档案移交</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript">
var ids = "<%=ids%>";
var opt="<%=opt %>";
function doInit(){
	renderPreArchiveType();
}

//渲染预归档分类
function renderPreArchiveType(){
	var url=contextPath+"/preArchiveTypeController/getAllPreArchiveType.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			 for(var i=0;i<data.length;i++){
				 $("#typeId").append("<option value="+data[i].sid+">"+data[i].typeName+"</option>");		 
			 }
		}
	}
}
/**
 * 移交
 */
function turnOver(){
	if(checkFrom()){
		var url = contextPath + "/TeeDamFilesController/turnOver.action";
		var para =  tools.formToJson($("#form1")) ;
		var json = tools.requestJsonRs(url,para);
		return json;
	}
}
/**
 * 校验
 */
function checkFrom(){
	 var typeId=$("#typeId").val();
	 if(typeId==""||typeId==null||typeId==0){
		 $.MsgBox.Alert_auto("请选择归档分类！");
		 return false;
	 }
	 return true;
}


	


</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" >
		<tr>
			<td nowrap class="TableData"  style="width: 100px;text-indent: 5px;">归档分类：</td>
			<td class="TableData">
				<select id="typeId" name="typeId">
				   <option value="">请选择</option>
				</select>
			</td>
		</tr>
		
	</table>
	<input id="ids" name="ids" type="hidden" value="<%=ids %>"> 
	<input id="opt" name="opt" type="hidden" value="<%=opt %>"> 
</form>
</body>
<script>
$("#form1").validate();
</script>
</html>
