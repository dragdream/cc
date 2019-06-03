<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String VEHICLE_MANAGER_TYPE = "VEHICLE_MANAGER_TYPE";
    
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<title>车辆调度员设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
var paramName = "<%=VEHICLE_MANAGER_TYPE%>";
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
	if(jsonRs.rtState){
		$.MsgBox.Alert_auto("保存成功！");
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

</script>
 
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">

<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/clgl/sz.png">
		<span class="title">车辆调度员设置</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" value="确定" class="btn-win-white" title="确定" onclick="doSave()" >
	</div>
</div>

<form name="form1"  id="form1" >
<table class="TableBlock_page">
   <tr>
    <td nowrap class="TableData" >车辆调度员设置：
    </td>
    <td nowrap class="TableData" align="left">
      	 <input type="hidden" name="paraValue" id="paraValue" value="">
      	  <input type="hidden" name="paraName" id="paraName" value="<%=VEHICLE_MANAGER_TYPE%>">
        <textarea cols=80 name="userName" id="userName" readonly rows=6 class="BigTextarea" wrap="yes" ></textarea>
        
        <span class='addSpan'>
			<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectUser(['paraValue', 'userName'])" value="选择"/>
			 &nbsp;&nbsp;
			<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="clearData('paraValue','userName')" value="清空"/>
		</span>
   
    </td>
   </tr>
   
</table>
</form>

</body>
</html>
 