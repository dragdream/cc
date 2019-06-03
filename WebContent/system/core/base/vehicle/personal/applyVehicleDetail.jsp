<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("id"), 0);//会议申请Id

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>车辆申请详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath %>/system/core/base/meeting/js/meeting.js"></script>
<style type="text/css">
</style>



<script type="text/javascript">
var sid = <%=sid%>;
function doInit(){
  if(sid > 0){
		getInfoById(sid);
	}
}
	

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/vehicleUsageManage/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#vuUserId").text(prc.vuUserName);
			$("#vehicleId").text(prc.vehicleName);
			$("#vuOperatorId").text(prc.vuOperatorName);
		}
	} else {
		//alert(jsonObj.rtMsg);
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
	
/**
 * 查看会议室详情
 */
function meetRoomDetail(){
	var roomId = $("#meetRoomId").val();
	var url = "<%=contextPath%>/system/core/base/meeting/room/detail.jsp?id="+ roomId;
	var title = "查看会议室详情";
	bsWindow(url ,title,{width:"600",height:"360",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function toMeetRoom(){
	var meetRoomId = $("#meetRoomId").val();
	if(meetRoomId){
		meetingRoomJBoxDetail(meetRoomId);
	}
	
}
</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<table align="center" width="100%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData"  width="15%;" style="text-indent:10px">司机：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;"  id="vuDriver">
		</td>
		<td nowrap class="TableData" width="15%;" >使用人：<font style='color:red'>*</font></td>
		<td class="TableData" id="vuUserId" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">起始时间：<font style='color:red'>*</font></td>
		<td class="TableData" width="" id="vuStartStr">
		</td>
		<td nowrap class="TableData" width="" >结束时间：<font style='color:red'>*</font></td>
		<td class="TableData" id="vuEndStr">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">目 的 地：<font style='color:red'>*</font></td>
		<td class="TableData" width="" id="vuDestination">
		</td>
		<td nowrap class="TableData" width="" >里　　程：<font style='color:red'>*</font></td>
		<td class="TableData" id="vuMileage">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="display: none;">部门审批人：</td>
		<td class="TableData" width="" style="display: none;" colspan="3" id="deptManagerId">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">选择车辆：<font style='color:red'>*</font></td>
		<td class="TableData" width="" id="vehicleId">
		</td>
		<td nowrap class="TableData" width="" >调 度 员：<font style='color:red'>*</font></td>
		<td class="TableData"  id="vuOperatorId">
		</td>
	</tr>
<!-- 	<tr>
		<td nowrap class="TableData"  width="">内部短信提醒：</td>
		<td class="TableData" width="" colspan="3">
			<input type="checkbox" name="smsRemind" id="smsRemind" size="" maxlength="100"  value="1">提醒调度人
		</td>
	</tr> -->
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">事　　由：<font style='color:red'>*</font></td>
		<td class="TableData" width="" colspan="3" id="vuReason">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="" style="text-indent:10px">备　　注：</td>
		<td class="TableData" width="" colspan="3" id="vuRemark">
		</td>
	</tr>
</table>
		
</body>

</html>
