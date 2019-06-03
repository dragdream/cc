<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//日程Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>出差申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var sid = <%=sid%>;


function doInit()
{
	if(sid > 0){
		getAttendEvectionById(sid);
	}
	
}


/**
 * 新建或者更新外出
 */
function evectionComeBack(){
	if(checkFrom()){
		var url =  "<%=contextPath%>/attendEvectionManage/comeback.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			 $.MsgBox.Alert_auto("保存成功！");
			 return true;
		}else{
			 $.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	}
	return false;
}
/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").valid(); 
	 if(check ){
		 return true; 
	 }
	 return false;
}

/**
 * 获取请假 by Id
 */
function getAttendEvectionById(id){
	var url =   "<%=contextPath%>/attendEvectionManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#startDate").val(prc.startTimeStr);
			$("#endDate").val(prc.endTimeStr);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
		
	}
}
	

</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" >

		<%-- <tr class="TableData" id="userIdTr">
			<td nowrap>人员：</td>
			<td nowrap align="left">
				<input id="userIds" name="userIds" type="hidden" value='<%=userId %>'> 
				<textarea name="userNames" id="userNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"><%=userName %></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['userIds','userNames'],'3')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('userIds','userNames')">清空</a>
			</td>
		</tr> --%>
		<tr>
			<td nowrap class="TableData" width="100">出差时间：</td>
			<td class="TableData">
				<input type="text" id="startDate"
				name="startDate" size="18" maxlength="19"
				required
				value="" readonly="readonly">
				 &nbsp;至&nbsp;
				<input type="text" id="endDate"
				name="endDate" size="18" maxlength="19"
				required 
				value="" readonly="readonly">
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">出差原因：</td>
			<td class="TableData" id="evectionDesc" name="evectionDesc">
				<!-- <textarea type="text" id="evectionDesc" name="evectionDesc" class="" required cols="50" rows="4"></textarea> -->
			</td>
		</tr>
		
	</table>
	<input id="sid" name="sid" type="hidden" value="0"> 
	<input id="leaderId" name="leaderId" type="hidden" value="0"> 
</form>
</body>

</html>
