<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/validator2.0.jsp" %>
	<%@ include file="/header/upload.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>创建表单</title>
	<script>
	var contextPath = "<%=contextPath%>";
	var formId = <%=formId%>;
	//初始化方法
	function doInit(){	
		//获取表单详情
		initFormInfo();

	}

	//获取表单详情
	function initFormInfo(){
		var url = contextPath+"/bisFormController/getFormInfoById.action";
		var json = tools.requestJsonRs(url,{sid:formId});
		 //动态添加所属表格
		if(json.rtState){
			var target = json.rtData;		
		    bindJsonObj2Cntrl(target);	
		}
	}

	//保存
	function commit(){
		if($("#form").valid()){
			var url = contextPath+"/bisFormController/addOrUpdateBisForm.action";
			var para = tools.formToJson($("#form"));
			var json = tools.requestJsonRs(url,para);
			return json;
		}
		
	}

	
	</script>
</head>
<body onload="doInit()" style="background-color: #f2f2f2">


<form id="form">
<table class="TableBlock" width="100%" align="center">
   <tr>
    <td class="TableData TableBG" width="120"><font color="red">*</font>表单名称：</td>
    <td class="TableData">
        <input type="text" style="width:300px;height: 23px" id="formName" name="formName" class="BigInput" required="true"/>
    </td>
   </tr>
   <tr>
    <td class="TableData TableBG"><font color="red">*</font>排序号：</td>
    <td class="TableData">
    	<input type="text" style="width:300px;height: 23px" id="sortNo" name="sortNo" class="BigInput" positive_integer="true" required="true"  />
    </td>
   </tr>
   <tr>
    <td class="TableData TableBG"><font color="red">*</font>所属表格：</td>
    <td class="TableData">
    	<input type="text" id="bisTableName" name="bisTableName" class="BigInput" readonly="readonly" style="width: 300px;height: 23px">
    </td>
   </tr>
   
</table>
<input type="hidden" name="sid" value="<%=formId %>" />
</form>


</body>
</html>