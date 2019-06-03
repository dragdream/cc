<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加通讯组</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<%@ include file="/header/validator2.0.jsp"%>
<style>
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	font-weight:bold;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
/*     border: 1px solid #f2f2f2; */
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	/*font-weight:bold;*/
}
table tr:first-child{
	/* background-color: #f8f8f8;  */
}
</style>
<script type="text/javascript">

var id = '<%=id%>';
function doInit(){
	return ;
	if(id > '0'){
		var url = "<%=contextPath%>/teeAddressGroupController/addAddressGroup.action";
		var jsonRs = tools.requestJsonRs(url,{id:id});
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			if(data){
				bindJsonObj2Cntrl(data);
			}
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
	
}
/**
 * 保存
 */
function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/teeAddressGroupController/addAddressGroup.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			 //top.$.jBox.tip("保存成功！");
			 $.MsgBox.Alert_auto("保存成功！");
			 window.parent.location.reload();
			window.location.href ="<%=contextPath%>/system/core/base/address/private/group/index.jsp";
			}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
    return $("#form1").valid();
}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;padding:0 10px;box-sizing:border-box;">
<div class="topbar clearfix" style='padding:0!important;border-bottom:none!important;'>
	<table style="width:100%;">
		<tr>
			<td>
				<b><i class="glyphicon glyphicon-sound-stereo"></i>新增分组</b>
			</td>
			<td align=right>
			</td>
		</tr>
	</table>
</div>

<div class="time_info">
<form  method="post" name="form1" id="form1" >
<table class="none-table">

   <tr>
    <td nowrap class="TableData" width="120" >排序号：</td>
    <td nowrap class="TableData" align="left">
        <input type='text' name="orderNo" class="easyui-validatebox BigInput"  size='5' maxlength='5' required="true"  validType='positivIntege[]'/>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">分组名称：</td>
    <td nowrap class="TableData" align="left">
        <input type='text' name="groupName" class="easyui-validatebox BigInput"  maxlength="100" required="true" />
    </td>
   </tr>
   <tr>
	    <td nowrap  class="TableControl" colspan="2">
	        <input type="button" value="返回" class="btn-win-white fr" title="返回" onClick="history.go(-1);" style="margin-right:70px;margin-top:6px;">&nbsp;&nbsp;
				    <input type="button" value="保存" class="btn-win-white fr" title="保存" onclick="doSave()" style="margin-right:10px;margin-top:6px;">
	        <input type='hidden' value='<%=id %>' name='sid'/>
	    </td>
   </tr>
</table>
</form>
</div>

</body>
</html>
 