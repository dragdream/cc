<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	Date curDate = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss");
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
<title>出差申请</title>
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
	showPhoneSmsForModule("sms","023",loginPersonId);
}


/**
 * 新建或者更新外出
 */
function doSaveOrUpdate(){
	if(checkFrom()){
		var url = contextPath + "/attendEvectionManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			 $.MsgBox.Alert_auto("保存成功！");
				//手机短信
				var toUserId=$("#leaderId").val();
				var smsContent="您有一个出差申请需要审批：申请人："+userName+",出差原因："+$("#evectionDesc").val();
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
	 return true;
}

/**
 * 获取请假 by Id
 */
function getAttendLeaveById(id){
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
	
function getDays(){
	var startTime = document.getElementById("startDate").value+" 00:00:00";
	var endTime = document.getElementById("endDate").value+" 23:59:59";
	if(startTime!=null && endTime!=null){
		var url=contextPath+"/TeeAttendDutyController/getTimeDiffByDutyConfig.action";
		var json=tools.requestJsonRs(url,{startTimeStr:startTime,endTimeStr:endTime});
		if(json.rtState){
			var data=json.rtData;
			$("#days").val(data);
		}
		
	}
}

function DateDiff(sDate1, sDate2){
    var iDays;
    sDate1 = sDate1.replace(/-/g,"/");
    var aDate = new Date(sDate1 );
    sDate2 = sDate2.replace(/-/g,"/");
    var oDate = new Date(sDate2 );
    iDays = parseInt(Math.abs(oDate - aDate) / 1000 / 60 / 60 /24); //把相差的毫秒數轉抽象為天數
    return iDays;
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
				style="width: 150px"
				name="startDate" size="18" maxlength="19"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})"
				class="Wdate" required
				value="<%=curDateStr.substring(0,10)%>" onchange="getDays()">
				 &nbsp;至&nbsp;
				<input type="text" id="endDate"
				name="endDate" size="18" maxlength="19"
				style="width: 150px"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})"
				class="Wdate" required 
				value="<%=curDateStr.substring(0,10)%>" onchange="getDays()">
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">出差天数：</td>
			<td class="TableData">
				<input type="text" id="days" name="days" class="BigInput easyui-validatebox" required="true"  size='8'/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">出差地址：</td>
			<td class="TableData">
				<textarea type="text" id="evectionAddress" name="evectionAddress" class="" required  cols="50" rows="2"></textarea></td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">出差原因：</td>
			<td class="TableData">
				<textarea type="text" id="evectionDesc" name="evectionDesc" class="" required cols="50" rows="4"></textarea></td>
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
