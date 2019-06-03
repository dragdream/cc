<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	Date curDate = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm");
	SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
	Calendar c = Calendar.getInstance();
	String curDateStr = dateFormat.format(curDate);
	String  currHour = curDateStr.substring(11,13);
	String currSecond = curDateStr.substring(14, 16);
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//日程Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<title>请假申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript">
var sid = <%=sid%>;
var curDateStr="<%=curDateStr%>";


function doInit()
{
	/* 	审批人员 */
	getRuleApprovUser('leaderId');
	if(sid > 0){
		getAttendLeaveById(sid);
	}
	
	getDays();
	getAnnualLeaveInfo();
	showPhoneSmsForModule("sms","023",loginPersonId);
}


/**
 * 新建或者更新外出
 */
function doSaveOrUpdate(){
	if(checkFrom()){
		var url = contextPath + "/attendLeaveManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			$.MsgBox.Alert_auto("保存成功！");
				//手机短信
				var toUserId=$("#leaderId").val();
				var smsContent="您有一个请假申请需要审批：申请人："+userName+",请假原因："+$("#leaveDesc").val();
				var sendTime=curDateStr;
				sendPhoneSms(toUserId,smsContent,sendTime);
			
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
	 if(!check ){
		 return false; 
	 }
	 if($("#leaderId").val() == "" || $("#leaderId").val() == null ){
		 return false;
	 }
	 
	 if($("#leaveType").val()==3 && $("#remainDays").val()-$("#annualLeave").val()<0){
		 $.MsgBox.Alert_auto("请假天数大于剩余年假天数");
		 return false;
	 }
	 return true;
}

/**
 * 获取请假 by Id
 */
function getAttendLeaveById(id){
	var url =   "<%=contextPath%>/attendLeaveManage/getById.action";
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
	
function getDays(){
	var startTime = document.getElementById("startDate").value;
	var endTime = document.getElementById("endDate").value;
	if(startTime!=null && endTime!=null){
		
		var url=contextPath+"/TeeAttendDutyController/getTimeDiffByDutyConfig.action";
		var json=tools.requestJsonRs(url,{startTimeStr:startTime,endTimeStr:endTime});
		if(json.rtState){
			var data=json.rtData;
			$("#annualLeave").val(data);
		}
	}
}


function DateDiff(sDate1, sDate2)
{
    var iDays;
    sDate1 = sDate1.replace(/-/g,"/");
    var aDate = new Date(sDate1 );
    sDate2 = sDate2.replace(/-/g,"/");
    var oDate = new Date(sDate2 );
    //iDays = parseInt(Math.abs(oDate - aDate) / 1000 / 60 / 60 /24); //把相差的毫秒數轉抽象為天數
    iDays =(Math.abs(oDate - aDate) / 1000 / 60 / 60 /24).toFixed(2);
    return iDays;
}

/**
 * 年假提醒信息
 */
function getAnnualLeaveInfo(){
	var leaveType = $("#leaveType").val();
	if(leaveType=='3'){
		var url = "<%=contextPath%>/attendLeaveManage/getAnnualLeaveInfo.action";
		var jsonObj = tools.requestJsonRs(url);
		if (jsonObj.rtState) {
			var prc = jsonObj.rtData;
			var annualLeaveInfo = "您的总年假天数为："+prc.totalAnnualLeaveDays+"天，剩余年假天数为："+prc.remainLeaveDays+"天。";
			$("#remainDays").val(prc.remainLeaveDays);
			$("#tips").html(annualLeaveInfo);
		} else {
			$.MsgBox.Alert_auto(jsonObj.rtMsg);
		}
	}else{
		$("#tips").html("");
	}
}

</script>
</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form id="form1" name="form1" method="post" >
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
			<td nowrap class="TableData" width="100">请假时间：</td>
			<td class="TableData">
				<input type="text" id="startDate"
				name="startDate" size="18" maxlength="19"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDate\')}'})"
				class="Wdate" required
				style="width:150px"
				value="<%=curDateStr%>" onchange="getDays()">
				 &nbsp;至&nbsp;
				<input type="text" id="endDate"
				style="width:150px"
				name="endDate" size="18" maxlength="19"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startDate\')}'})"
				class="Wdate"  
				value="<%=curDateStr%>" onchange="getDays()">
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">请假天数：</td>
			<td class="TableData">
				<input type="text" id="annualLeave" name="annualLeave" class="BigInput easyui-validatebox" required="true"  size='8'/>
				<input type='hidden' id='remainDays' name = "remainDays"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">请假原因：</td>
			<td class="TableData">
				<textarea type="text" id="leaveDesc" name="leaveDesc" class="BigTextarea easyui-validatebox" required="true" cols="50" rows="4"></textarea></td>
		</tr>
		<tr class="TableData">
			<td nowrap>请假类型：</td>
			<td nowrap align="left">
				<select class="BigSelect" name="leaveType" id="leaveType" onchange="getAnnualLeaveInfo()">
					<option value="1">事假</option>
					<option value="2">病假</option>
					<option value="3">年假</option>
					<option value="5">工伤假</option>
					<option value="6">婚假</option>
					<option value="7">丧假</option>
					<option value="8">产假</option>
					<option value="9">探亲假</option>
					<option value="10">公假</option>
					<option value="4">其他</option>
				</select>
				<span id='tips' name = 'tips' style='color:red;'></span>
			</td>
		</tr>
		<tr class="TableData">
			<td nowrap>审批人：</td>
			<td nowrap align="left"><select class="BigSelect" name="leaderId" id="leaderId">
		
			</select></td>
		</tr>
		<tr class="TableData">
			<td nowrap>提醒：</td>
			<td nowrap align="left" id="sms">
				<input id="smsRemind" name="smsRemind" type="checkbox" value='1' checked="checked"> <label for='smsRemind'>是否使用内部短信</label>
		    </td>
		</tr>
	</table>
	<input id="sid" name="sid" type="hidden" value="0"> 
</form>
</body>

</html>
