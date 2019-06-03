<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%
	String sid = request.getParameter("sid");
	if(sid==null||sid==""){
		sid = "0";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>管理员设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">

var sid = '<%=sid%>';

function doInit(){
	
	var url = "<%=contextPath%>/bookManage/getBookManager.action";
	var para = {sid:sid};
	var jsonRs = tools.requestJsonRs(url,para);
	var json = jsonRs.rtData;
	if(jsonRs.rtState){
		$("#postUserIds").val(json.postUserIds);
		$("#postUserNames").val(json.postUserNames);
		$("#postDeptIds").val(json.postDeptIds);
		$("#postDeptNames").val(json.postDeptNames);
	}
}

function doSave(){
	var url = "<%=contextPath%>/bookManage/addOrUpdateManager.action";
	var para =  tools.formToJson($("#form1"));
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		alert(jsonRs.rtMsg);
		window.close();
		//toReturn();
	}
}

function toReturn(){
	location.href="<%=contextPath%>/bookManage/setManager.action";
}
</script>

</head>
<body onload="doInit();" style="background-color: #f4f4f4;">
<form action=""  method="post" name="form1" id="form1">
<!-- <div class="base_layout_top" style="position:static">
	<span class="easyui_h1">设置管理员</span>
</div> -->
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<br>
	<table class="none-table">
	    <tr>
	      <td nowrap class="TableData">管理员：</td>
	      <td class="TableData" colspan="3">
	        <input type="hidden" name="postUserIds" id="postUserIds" value="">
		      <textarea cols=50 name="postUserNames" id="postUserNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
		      <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['postUserIds', 'postUserNames']);">添加</a>
		      <a href="javascript:void(0);" class="orgClear" onClick="$('#postUserIds').val('');$('#postUserNames').val('');">清空</a>
	      </td>
	    </tr> 
		<tr>
		    <td nowrap class="TableData">所管部门：</td>
		    <td class="TableData" colspan="3">
		   		 <input type="hidden" name="postDeptIds" id="postDeptIds" value="">
		  		 <textarea cols=50 name="postDeptNames" id="postDeptNames" rows=10 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
		  		 <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['postDeptIds', 'postDeptNames']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#postDeptIds').val('');$('#postDeptNames').val('');">清空</a><br>
				注：所管部门指图书所属部门。
		    </td>
		</tr>
	    <!--  <tr>
		      <td nowrap class="TableData" colspan="4" >
				 <input id="button" type="button" value="保存" onclick="doSave();" class="btn btn-primary"/>&nbsp;
				 <input id="button" type="button" value="返回" onclick="toReturn();" class="btn btn-default"/>
			  </td>
		   </tr> -->
    </table>
<br>
</form>
</body>
</html>
