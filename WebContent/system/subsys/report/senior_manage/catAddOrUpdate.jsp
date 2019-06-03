<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/common/colorPicker/css/colorPicker.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/common/colorPicker/colorPicker.js"></script>
<script>
var sid = "<%=sid%>";
function doInit(){
	var color = "#f0f0f0";
	if(sid!="null"){
		var json = tools.requestJsonRs(contextPath+"/seniorReportCat/get.action",{sid:sid});
		bindJsonObj2Cntrl(json.rtData);
		
		$('#colorSelect').css('backgroundColor', json.rtData.color);
		color = json.rtData.color;
	}
	
	$('#colorSelect').ColorPicker({
		color: color,
		onShow: function (colpkr) {
			$(colpkr).fadeIn(500);
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(500);
			return false;
		},
		onChange: function (hsb, hex, rgb) {
			//$('#colorSelector div').css('backgroundColor', '#' + hex);
		},onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$(el).ColorPickerHide();
			$('#colorSelect').css('backgroundColor', '#' + hex);
			$("#color").val('#' + hex);
		} 
	});
}


function commit(){
	if(!$("#form1").form("validate")){
		return ;
	}
	
	var para = tools.formToJson($("#form1"));
	
	var url;
	if(sid!="null"){
		url = contextPath+"/seniorReportCat/update.action";
	}else{
		url = contextPath+"/seniorReportCat/add.action";
	}
	
	var json = tools.requestJsonRs(url,para);
	alert(json.rtMsg);
	window.location = "cat_list.jsp";
}

</script>
<style>
td{
padding:5px;
}
</style>
</head>
<body onload="doInit()" style="margin:5px;">
	<button class="btn btn-primary" onclick="commit()">保存</button>
	&nbsp;&nbsp;
	<button class="btn btn-default" onclick="window.location = 'cat_list.jsp'">返回</button>
	<table style="font-size:12px;margin:5px;" id="form1">
		<tr>
			<td>分类名称：</td>
			<td><input type="text" class="BigInput easyui-validatebox" required id="name" name="name"/></td>
		</tr>
		<tr>
			<td>排序号：</td>
			<td>
				<input type="text" class="BigInput easyui-validatebox"  id="sortNo" name="sortNo"/>
			</td>
		</tr>
		<tr>
			<td>颜色标记：</td>
			<td>
				<div id="colorSelect" style="background-color: #e2e2e2;height:20px;width:30px;"></div>
			</td>
		</tr>
		<input type="hidden" name="color" id="color" value="#e2e2e2">
		<input type="hidden" name="sid" id="sid" value="<%=sid==null?"0":sid%>"/>
	</table>
</body>
</html>