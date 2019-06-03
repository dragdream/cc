<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<% 
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<title>添加业务分类</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var sid = <%=sid%>;

function doInit(){
	if(sid!=0){
		var url = contextPath+"/bisCat/getModelById.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		bindJsonObj2Cntrl(json.rtData);
	}
}

function commit(){

	if($("#form1").valid()){
		var url;
		if(sid==0){
			url = contextPath+"/bisCat/addBisCat.action";
		}else{
			url = contextPath+"/bisCat/updateBisCat.action";
		}

		var params = tools.formToJson($("#form1"));
		var json = tools.requestJsonRs(url,params);
		return json;
	}

}
</script>
</head>
<body style="background-color: #f2f2f2" onload="doInit()" >
<form id="form1">
<center>
	<table class="TableBlock" style="width:100%">

		<tr>
			<td class="TableData" style="text-indent: 10px">分类名称：</td>
			<td class="TableData">
				<input type="text" class="BigInput" required="true" name="catName" style="width: 300px;height: 23px;"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px">排序号：</td>
			<td class="TableData">
				<input type="text" class="BigInput" required="true" positive_integer="true" name="sortNo" style="width:300px;height: 23px;"/>
			</td>
		</tr>
	</table>
	<input type="hidden" name="sid" value="<%=sid %>" />
</form>
</center>
</body>
<script>
	$("#form1").validate();
</script>
</html>
