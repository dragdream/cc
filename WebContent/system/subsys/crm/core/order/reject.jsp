<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0) ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>订单驳回</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
		getInfoById(sid);
}


/* 查看详情 */
function getInfoById(sid){
	var url=contextPath+"/TeeCrmOrderController/getInfoBySid.action";
	var para = {sid : sid};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
		}
	} else {
		$.MsgBox.Alert(jsonObj.rtMsg);
	}
}


/**
 * 保存数据
 */
function doSaveOrUpdate(callback){
	var url=contextPath+"/TeeCrmOrderController/reject.action?sid="+sid+"&orderStatus=3";
    var para =  tools.formToJson($("#form1"));
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
      callback(jsonRs.rtState);
    }else{
    	$.MsgBox.Alert(jsonRs.rtMsg);
    }
}

</script>
<style>
.TableBlock tr>td>textarea{
	margin:0;
}

</style>
</head>
<body onload="doInit();" style="overflow: hidden;padding: 10px;background-color: #f2f2f2;">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="100%" class="TableBlock" >
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData"  width="10%;" >驳回原因：</td>
		<td class="TableData" width="60%;" colspan="3">
		      <textarea id="rejectReason" name="rejectReason" style="font-family: MicroSoft YaHei; font-size: 12px;width: 350px;" rows="5" cols="50"></textarea>
		</td>
	</tr>

</table>
</form>
</body>
<script>
	$("#form1").validate();
</script>
</html>