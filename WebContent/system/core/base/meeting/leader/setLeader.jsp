<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String MEETING_MANAGER_TYPE = "MEETING_MANAGER_TYPE";
    
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<title>会议管理员设置</title>



<script type="text/javascript">
var paramName = "<%=MEETING_MANAGER_TYPE%>";
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
		$.MsgBox.Alert_auto("保存成功！");
		
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
		
	}
}

</script>
 
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px;">
<%-- <div class="base_layout_top" style="position:static">
	<img class = 'fl' style="margin-right: 10px; margin-top: 3px;margin-left: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hyglysz/icon_meeting_officer.png">
    <p class="title" style="padding-top: 4px;">会议管理员设置</p>
</div>
<span class="basic_border fl" style=""></span> --%>
<div id="toolbar" class = "topbar clearfix">
<div class="fl" style="position:static;">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hyglysz/icon_meeting_officer.png">
		    <span class="title">会议管理员设置</span>
	    </div>
</div>
<form name="form1"  id="form1" >
<table class="none-table" style="width:100%">
<tr>
	<td nowrap class="TableData">
    	<!-- <fieldset>
			<legend style="margin-left:10px;font-size:16px;">会议申请审批管理员设置</legend>
		</fieldset> -->
    </td>
</tr>
   <tr>
    <td nowrap class="TableData" align="left">
      	 <input type="hidden" name="paraValue" id="paraValue" value="">
      	  <input type="hidden" name="paraName" id="paraName" value="<%=MEETING_MANAGER_TYPE%>">
        <textarea cols=50 name="userName" id="userName" rows=6 readonly class="readonly BigTextarea" wrap="yes"  style="margin-left:10px;margin-top:5px;" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['paraValue', 'userName'])">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('paraValue','userName')">清空</a>
    </td>
   </tr>
   <tr>
	    <td nowrap  class="TableControl" colspan="2">
	        <input type="button" value="确定" class="btn-alert-blue" title="确定" style="margin-left:335px;margin-top:5px;" onclick="doSave()" >&nbsp;&nbsp;
	    </td>
   </tr>
   
</table>
  </form>

</body>
</html>
 