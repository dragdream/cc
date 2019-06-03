<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String menuId = request.getParameter("menuId");
	int id = 0;
	try{
		id = Integer.parseInt(request.getParameter("id"));
	}catch(Exception e){
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background:#f0f0f0">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>编辑按钮</title>
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/validator2.0.jsp" %>
<%@include file="/header/easyui.jsp"%>

<script type="text/javascript">

function doInit(){
	var id = <%=id%>;
	if(id > 0){
		var url = "<%=contextPath %>/menuButton/getButtonById.action";
		var json = tools.requestJsonRs(url,{id:'<%=id%>'});
		if(json.rtState){
			var data = json.rtData;
			//$("#id").attr("value",data.id);
			//$("#roleNo").attr("value",data.roleNo);
			//$("#roleName").attr("value",data.roleName);
			//$("#deptName").attr("value",data.deptName);
			//$("#deptId").attr("value",data.deptId);
			bindJsonObj2Cntrl(data);
		}
	}
}

function doSaveOrUpdate(callback){
	if(!checkFrom()){
		return ;
	}

	var url = "<%=contextPath %>/menuButton/addOrUpdateButton.action";
	var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		callback(jsonRs);
	}else{
		alert(jsonRs.rtMsg);
	}
}

/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").valid();
	 if(!check ){
		 return false; 
	 }
	 return true; 
}

</script>

</head>
<body onload="doInit()" style="background:#f0f0f0">
<center style="width:100%;">
	<form  method="post" name="form1" id="form1" >
		<input type="hidden" name="menuId" id="menuId" value="<%=menuId%>"/>
		<input type="hidden" name="id" id="id" value="0"/>
		<table class="TableBlock" width="100%" align="center">
		   <tr>
		     <td nowrap class="TableData">顺序号：</td>
		     <td nowrap class="TableData">
		        <input type="text" name="sortNo" id="sortNo" class="easyui-validatebox BigInput" required newMaxLength="3" validtype="integeBetweenLength[1,999]" style="width:100px;">
		     </td>
		   </tr>
		   <tr>
		     <td nowrap class="TableData" width="120">按钮编号：</td>
		     <td nowrap class="TableData">
		     	<input type="text" name="buttonNo" id="buttonNo" required newMaxLength="20" style="width:200px;">
		     </td>
		   </tr>
		   <tr>
		     <td nowrap class="TableData" width="120">按钮名称：</td>
		     <td nowrap class="TableData">
		     	<input type="text" name="buttonName" id="buttonName" required newMaxLength="20" style="width:200px;">
		     </td>
		   </tr>
		   <tr>
		     <td nowrap class="TableData">按钮属性：</td>
		     <td nowrap class="TableData">
				<select name="buttonProp"  id="buttonProp" class="BigSelect" style="width:100px;">
					<option value="0">查询类</option>
					<option value="1">编辑类</option>
				</select>
		     </td>
		   </tr>
		   <tr>
		     <td nowrap class="TableData">访问地址：</td>
		     <td nowrap class="TableData">
		        <input type="text" name="buttonUrl" id="buttonUrl" required newMaxLength="100" style="width:300px;">
		     </td>
		   </tr>
		   <tr>
		     <td nowrap class="TableData">备注信息：<span style=""></span></td>
		     <td nowrap class="TableData">
		        <input type="text" name="remark" id="remark" newMaxLength="200" style="width:300px;">
		     </td>
		   </tr>
		</table>
 	 </form>
</center>
</body>

</html>
 