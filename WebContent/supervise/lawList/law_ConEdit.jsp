<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	String lawId = request.getParameter("lawId");
	String lawName = request.getParameter("lawName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/lawList/js/law_ConEdit.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>

<script type="text/javascript">
var id = '<%=id%>';
var lawId = '<%=lawId%>';
var lawName = '<%=lawName%>';
</script>

</head>
<body onload="doInit();" style="padding: 10px;background-color: #f2f2f2;">
<fieldset style="margin:10px;display:none">
	<legend>编辑</legend>
</fieldset>
<form id="form1" name="form1">
	<table class='TableBlock' width="100%">
		<tr> 
			<td class='TableData' style="text-indent: 10px;">
				修改编号
				</td>
			<td class='TableData' >
			<input class="easyui-textbox" style="width: 99%;" id="detailSeries" name="detailSeries" required />		
	      
			</td>
		</tr >
		<tr align='left'>
			<td class='TableData' style="text-indent: 10px;" width="100px">
				章<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td class='TableData'>
				<input class="easyui-textbox" style="width: 99%;" id="detailChapter" name="detailChapter" required />
			</td>
		</tr>
		<tr align='left'>
			<td class='TableData' style="text-indent: 10px;" width="100px">
				条<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td class='TableData'>
				<input class="easyui-textbox" style="width: 99%;" id="detailStrip" name="detailStrip" required />
			</td>
		</tr>
		<tr align='left'>
			<td class='TableData' style="text-indent: 10px;" width="100px">
				款<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td class='TableData'>
				<input class="easyui-textbox" style="width: 99%;" id="detailFund" name="detailFund" required />
			</td>
		</tr>
		<tr align='left'>
			<td class='TableData' style="text-indent: 10px;" width="100px">
				项<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td class='TableData'>
				<input class="easyui-textbox" style="width: 99%;" id="detailItem" name="detailItem" required />
			</td>
		</tr>
		<tr align='left'>
			<td class='TableData' style="text-indent: 10px;" width="100px">
				目<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td class='TableData'>
				<input class="easyui-textbox" style="width: 99%;" id="detailCatalog" name="detailCatalog" required />
			</td>
		</tr>
		<tr>
			<td class='TableData'>
				内容<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td class='TableData'>
				<input class="easyui-textbox" id="content"  name="content" data-options="multiline:true" style="font-family: MicroSoft YaHei;width:404px;height: 100px;margin-bottom: 5px;" validType="maxLength[100]"/>
			</td>
		</tr>
	</table>
	<br/>
	<div id="control" style='margin:0 auto;height:28px;line-height:28px;width:"90%"'>
		<input id="id" name="id" type='hidden'value="<%=id %>"/>
	</div> 
</form>

</body>
</html>