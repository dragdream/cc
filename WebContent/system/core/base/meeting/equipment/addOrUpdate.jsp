<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
</style>

<script>
var sid = "<%=sid%>";
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/meetEquipmentManage/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}
}

function commit(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/meetEquipmentManage/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
// 			top.$.jBox.tip(json.rtMsg,"success");
// 			parent.$.MsgBox.Alert_auto(json.rtMsg);
			var url = contextPath+"/system/core/base/meeting/equipment/index.jsp";
			location.href=url;
			$.MsgBox.Alert_auto(json.rtMsg);
		}else{
// 			top.$.jBox.tip("添加失败","error");
			$.MsgBox.Alert_auto("添加失败");
		}
		
	}
}

</script>
</head>
<body onload="doInit();" style="overflow:auto;padding:0 10px;box-sizing:border-box;">
<div class="topbar clearfix" style='padding:0!important;border-bottom:none!important;'>
	<span class="easyui_h1" style="text-indent:10px;font-weight:bold;font-size:14px;">会议设备信息</span>
</div>
<span class="basic_border fl"></span>
<form id="form1" name="form1">
	<table class='none-table'>
		<tr class='TableData' align='left'>
			<td>
				设备名称<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="height: 23px" type="text" id="equipmentName" name="equipmentName"   maxlength="100" class="BigInput easyui-validatebox"  required="true">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input id="sid" name="sid" type='hidden'value="<%=sid %>"/>
				<input id="saveInfo" name="saveInfo" type='button' class="btn-win-white" value="保存" onclick='commit();'/>&nbsp;&nbsp;
				<input id="back" name="back" type='button' class="btn-win-white" value='返回' onclick='history.go(-1);'/>
			</td>
		</tr>
	</table>
	<div id="control" >
		
	</div>
	<br/><br/>
</form>
</body>
</html>