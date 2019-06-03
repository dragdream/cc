<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String lawId = request.getParameter("lawId")==null?"0":request.getParameter("lawId");
	String id = request.getParameter("id")==null?"0":request.getParameter("id");
	String lawName = request.getParameter("lawName")==null?"0":request.getParameter("lawName");
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
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/lawList/js/law_conadd.js"></script>
<script type="text/javascript">
var lawId = '<%=id%>';
var id = '<%=lawId%>';
var lawName = '<%=lawName%>';
</script>

</head>
<body onload="doInit();" style="padding: 10px;background-color: #fff;">
<div title="新增">
	<form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
	    <input type="hidden" id="id" name = "id" value=""/>
	    <table class="TableBlock" style="width: 100%; background: #fff;">
	        <tr style="border:none!important;">
	            <td class="power-table-label" style="width: 60px;text-align:right;">章<span style="color:red;font-weight:bold;">*</span>:&nbsp&nbsp</td>
	            <td style=" width: 300px;">
	                <input name="detailChapter" id="detailChapter" class="easyui-textbox" style="width: 89%;" data-options="validType:'length[1,5]',novalidate:true,required:true, missingMessage:'请输入章',panelHeight:'auto'" />
	            </td>
	        </tr>
	        <tr style="border:none!important;">
	            <td class="power-table-label" style="width: 60px;text-align:right;">条<span style="color:red;font-weight:bold;">*</span>:&nbsp&nbsp</td>
	            <td style=" width: 300px;">
	                <input name="detailStrip" id="detailStrip" class="easyui-textbox" style="width: 89%;" data-options="validType:'length[1,5]',novalidate:true,required:true, missingMessage:'请输入条',panelHeight:'auto'" />
	            </td>
	        </tr>
	        <tr style="border:none!important;">
	            <td class="power-table-label" style="width: 60px;text-align:right;">款<span style="color:red;font-weight:bold;">*</span>:&nbsp&nbsp</td>
	            <td style="width: 300px;">
	           		<input name="detailFund" id="detailFund" style="width: 89%;"  class="easyui-textbox" data-options="validType:'length[1,5]',novalidate:true,required:true, missingMessage:'请输入款',panelHeight:'auto'">
	            </td>
	        </tr>
	        <tr style="border:none!important;">
	            <td class="power-table-label" style="width: 60px;text-align:right;">项<span style="color:red;font-weight:bold;">*</span>:&nbsp&nbsp</td>
	            <td style=" width: 300px;">
	            	<input  class="easyui-textbox" id='detailItem'  name='detailItem'  style="width: 89%;" data-options="validType:'length[1,5]',novalidate:true,required:true, missingMessage:'请输入项',panelHeight:'auto'" />
	            </td>
	        </tr>
	        <tr style="border:none!important;">
	            <td class="power-table-label" style="width: 60px;text-align:right;">目<span style="color:red;font-weight:bold;">*</span>:&nbsp&nbsp</td>
	            <td style="width: 300px;">
	           		<input name="detailCatalog" id="detailCatalog"  style="width: 89%;" class="easyui-textbox" data-options="validType:'length[1,5]',novalidate:true,required:true, missingMessage:'请输入目',panelHeight:'auto'">
	            </td>
	        </tr>
	        <tr style="border:none!important;">
	            <td class="power-table-label" style="width: 60px;text-align:right;">内容<span style="color:red;font-weight:bold;">*</span>:&nbsp&nbsp</td>
	            <td style=" width: 300px;">
					<input name="content" id="content"  style="width:395px;height: 100px;" class="easyui-textbox" data-options="validType:'length[1,4000]',multiline:true,novalidate:true,required:true, missingMessage:'请输入内容'" >
				</td>
	        </tr>
	       </table>
	    </form>
	</div>
</body>
</html>