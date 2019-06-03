<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<title>薪资详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
td{
	border:1px solid #cdcdcd;
}
</style>
<script>
var sid = "<%=sid%>";
/**
 * 初始化
 */
function doInit(){
	querySalary();
}

function querySalary(){
	var url = contextPath+"/teeSalItemController/querySalaryDetail.action?sid="+sid;
	var json = tools.requestJsonRs(url,{});
	var account = json.rtData;
	if(!json.rtState){
		messageMsg("该工资信息已删除或无权限查看","dataList","error");
		return;
	}
	var render = [];
	var tableHeaderName = account.tableHeaderName;
	var valueList = account.valueList;
	render.push("<table style='margin-top:5px;width:100%'>");
	var headers = tableHeaderName.split(",");
	for(var n=0;n<headers.length;n++){
		if(n==0){
//				render.push("<td nowrap ><input id='allbox_for' name='allbox' type='checkbox' onclick='checkAll(this)'/>全选</td>");
		}else{
			render.push("<tr>");
			render.push("<td nowrap align='right' style='padding:5px;width:10%'>"+headers[n]+"：</td><td style='padding:5px;' id='data_"+n+"'></td>");
			render.push("</tr>");
		}
	}
	
	render.push("</table>");
	$("#dataList").html(render.join(""));
	
	//数据行
	var values = valueList.split("*");
	for(var m=0;m<values.length;m++){
		if(m==0){
		}else{
			$("#data_"+m).html(values[m]);
		}
	}
	
	
}

</script>

</head>
<body onload="doInit();">
<div class="base_layout_top">
	<span class="easyui_h1">薪资详情</span>
</div>
<div class="base_layout_center"  style="padding:10px">
	<button class="btn btn-default" onclick="window.location = 'query.jsp'">返回列表</button>
	<div id='dataList'>
		
	</div>
</div>
</body>
</html>
