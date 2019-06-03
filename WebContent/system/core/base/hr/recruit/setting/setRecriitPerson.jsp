<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String RECRUIT_PERSONS = "RECRUIT_PERSONS";
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>人事招聘专员设置</title>



<script type="text/javascript">
var paramName = "<%=RECRUIT_PERSONS%>";
function doInit(){
	//获取参数
	var params = getSysParamByNames(paramName);
	if(params.length > 0){
		var param = params[0];
		if(param.paraValue != "" ){
			var personInfo = getPersonNameAndUuidByUuids(param.paraValue);
			$("#paraValue").val(personInfo.sid);
			$("#userName").val(personInfo.userName);
		}
		
	}
}

/**
 * 保存
 */
function doSave(){
	var url = "<%=contextPath %>/sysPara/addOrUpdateSysPara.action";
	var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url,para);
	//alert(jsonRs);
	if(jsonRs.rtState){
		$.jBox.tip("保存成功！");
		
	}else{
		alert(jsonRs.rtMsg);
	}
}

</script>
 
</head>
<body onload="doInit()">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">人事招聘专员设置</span>
</div>

<form name="form1"  id="form1" >
<table class="none-table" style="width:100%">

   <tr>
    <td nowrap class="TableData" align="left">
      	 <input type="hidden" name="paraValue" id="paraValue" value="">
      	  <input type="hidden" name="paraName" id="paraName" value="<%=RECRUIT_PERSONS%>">
        <textarea cols=50 name="userName" id="userName" rows=6 readonly class="readonly BigTextarea" wrap="yes"  style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['paraValue', 'userName'])">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('paraValue','userName')">清空</a>
    </td>
   </tr>
   <tr>
	    <td nowrap  class="TableControl" colspan="2">
	        <input type="button" value="确定" class="btn btn-primary" title="确定" onclick="doSave()" >&nbsp;&nbsp;
	    </td>
   </tr>
   
</table>
  </form>

</body>
</html>
 