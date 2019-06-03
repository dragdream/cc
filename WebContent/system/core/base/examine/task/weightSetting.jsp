<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//sId
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<title>权重设置</title>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/examine/js/examine.js"></script>
	
<script type="text/javascript">
var sid = <%=sid%>;
function doInit()
{
	var json = tools.requestJsonRs(contextPath+"/TeeExamineTaskManage/getTaskWeightList.action?taskId="+sid);
	var list = json.rtData;
	var render = [];
	for(var i=0;i<list.length;i++){
		render.push("<tr>");
		render.push("<td>"+list[i].name+"</td>");
		render.push("<td><input type='text' class='weight_input BigInput' uid='"+list[i].uid+"' value='"+list[i].w+"' /></td>");
		render.push("</tr>");
	}
	$("#tbody").html(render.join(""));
}

/**
 * 新建或者更新
 */
function doSaveOrUpdate(){
	var weightModel = [];
	$(".weight_input").each(function(i,obj){
		weightModel.push({uid:obj.getAttribute("uid"),w:parseInt(obj.value)});
	});
	weightModel = tools.jsonArray2String(weightModel);
	tools.requestJsonRs(contextPath+"/TeeExamineTaskManage/updateTaskWeight.action?taskId="+sid,{weightModel:weightModel});
}
</script>

</head>
<body onload="doInit();">
<table class="TableBlock" style="width:100%">
<thead class="TableHeader">
	<tr>
		<td>考核人</td>
		<td>权重</td>
	</tr>
</thead>
<tbody id="tbody">
</tbody>
</table>
</body>
</html>
