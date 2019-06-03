<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/validator2.0.jsp" %>
<script>
var sid = <%=sid%>;
function doInit(){
	if(sid!=null){
		var json = tools.requestJsonRs(contextPath+"/cmsChannelExt/get.action",{sid:sid});
		bindJsonObj2Cntrl(json.rtData);
		$("#fieldName").attr("readonly","readonly");
	}
}

function commit(){
	if(checkForm()){
		var url = contextPath+"/cmsChannelExt/addOrUpdate.action";
		var param = tools.formToJson($("#form"));
		var json = tools.requestJsonRs(url,param);
		return json;
	}
	
}

//验证
function checkForm(){
	if(!$("#form").valid()){
		return false;
	}else{
		//判断字段英文名称  是否合理
		var fieldName=$("#fieldName").val();
		var regx=/(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)/;
		if(!regx.test(fieldName)){
			$.MsgBox.Alert_auto("请输入正确格式的字段英文名称！");
			return false;
		}else{
			//判断该字段名称是否已经存在
			if(sid==0||sid==null||sid=="null"){
				if(checkIsExists(fieldName)){
					return true;
				}else{
					$.MsgBox.Alert_auto("该字段名称已经存在，请重新填写字段英文名称！");
					return false;
				}
			}
		}
	}
}



//判断字段名称是否已经存在
function checkIsExists(fieldName){
	var url = contextPath+"/cmsChannelExt/checkFieldNameIsExist.action";
	var json = tools.requestJsonRs(url,{fieldName:fieldName,sid:sid});
	return json.rtState;
}
</script>
<style>
table td{
padding:5px;
}
</style>
</head>
<body onload="doInit();" style="background-color: #f2f2f2;">
<form id="form">
	<table style="width:100%;font-size:12px;" class="TableBlock">
		<tr>
			<td class="TableHeader" colspan="2" nowrap="">
		      <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">基础信息</b>
		    </td>
		</tr>
		<tr>
			<td style="text-indent:10px">字段英文名称：</td>
			<td>
				<input type="text" class="BigInput" name="fieldName" id="fieldName" style="width:250px;height:23px;" required/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">字段显示标题：</td>
			<td>
			<input type="text" class="BigInput" name="fieldTitle" id="fieldTitle" style="width:250px;height:23px;" required/>
			</td>
		</tr>
	</table>
	<input type="hidden" id="sid" name="sid" value="<%=sid==null?0:sid%>"/>
</form>
</body>
<script>
   $("#form").validate();
</script>
</html>