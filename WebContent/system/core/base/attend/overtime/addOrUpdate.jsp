<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//日程Id
	Date curDate = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm");
	SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
	Calendar c = Calendar.getInstance();
	String curDateStr = dateFormat.format(curDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<title>加班申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>


<script type="text/javascript">
var curDateStr="<%=curDateStr%>";
var sid = <%=sid%>;


function doInit()
{

	/* 	审批人员 */
	getRuleApprovUser('leaderId');
	if(sid > 0){
		getAttendOvertimeById(sid);
	}
	
	getHours();
	showPhoneSmsForModule("sms","023",loginPersonId);
}



/**
 * 新建或者更新加班
 */
function doSaveOrUpdate(){
	if(checkFrom()){
		var url = contextPath + "/attendOvertimeManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		
		var submitDate=$("#submitDate").val();
		var startTime =$("#startTime").val();
		var endTime = $("#endTime").val();
		
		
		var startTimeDesc=submitDate+" "+startTime;
		var endTimeDesc=submitDate+" "+endTime;
		
		para['startTimeDesc']=startTimeDesc;
		para['endTimeDesc']=endTimeDesc;
		
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			$.MsgBox.Alert_auto("保存成功！");
				//手机短信提醒
				var toUserId=$("#leaderId").val();
				var smsContent="您有一个加班申请需要审批：申请人："+userName+",加班原因："+$("#overtimeDesc").val();
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

	 if($("#leaderId").val() == "" || $("#leaderId").val() == null){
		 $.MsgBox.Alert_auto("审批人是必填项！");
		 return false;
	 }

	 if($("#submitDate").val() == "" || $("#submitDate").val() == null||$("#submitDate").val() == "null"){
		 $.MsgBox.Alert_auto("请选择加班日期！");
		 return false;
	 }
	 
	 if($("#startTime").val()==null || $("#startTime").val()=="" ||$("#startTime").val()=="null"){
		  $.MsgBox.Alert_auto("开始时间不能为空！");
		  return false;
	 }
	 if($("#endTime").val()==null || $("#endTime").val()=="" ||$("#endTime").val()=="null" ){
		  $.MsgBox.Alert_auto("结束时间不能为空！");
		  return false;
	 }
	 
	 return true;
}

/**
 * 获取加班 by Id
 */
function getAttendOvertimeById(id){
	var url =   "<%=contextPath%>/attendOvertimeManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			
			var startTimeDesc=prc.startTimeDesc;
			var endTimeDesc=prc.endTimeDesc;
			$("#submitDate").val(startTimeDesc.split(" ")[0]);
			$("#startTime").val(startTimeDesc.split(" ")[1]);
			$("#endTime").val(endTimeDesc.split(" ")[1]);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
	
function getHours(){
	var submitDate=$("#submitDate").val();
	var startTime =$("#startTime").val();
	var endTime = $("#endTime").val();
	
	if(submitDate!=null && submitDate!="null" && submitDate!=""){
		if(startTime!=null&&startTime!="null"&&startTime!=""){
			if(endTime!=null&&endTime!=null&&endTime!=""){
				var startTimeDesc=submitDate+" "+startTime;
				var endTimeDesc=submitDate+" "+endTime;
				var hours = HoursDiff(startTimeDesc,endTimeDesc);
				$("#overHours").val(hours);
			}
		}
	}

}


function HoursDiff(sDate1, sDate2)
{
    var hours;
    sDate1 = sDate1.replace(/-/g,"/");
    var aDate = new Date(sDate1 );
    sDate2 = sDate2.replace(/-/g,"/");
    var oDate = new Date(sDate2 );
    //iDays = parseInt(Math.abs(oDate - aDate) / 1000 / 60 / 60 /24); //把相差的毫秒數轉抽象為天數
    hours =(Math.abs(oDate - aDate) / 1000 / 60 / 60 ).toFixed(2);
    return hours;
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
		<%-- <tr>
			<td nowrap class="TableData" width="100">加班开始时间：</td>
			<td>
				<input type="text" id='startTimeDesc' value="<%=curDateStr%>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTimeDesc\')}'})" name='startTimeDesc' class="Wdate" onblur="getHours()" required/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">加班结束时间：</td>
			<td>
				<input type="text" id='endTimeDesc' value="<%=curDateStr%>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTimeDesc\')}'})" name='endTimeDesc' class="Wdate" onblur="getHours()" required/>
			</td>
		</tr> --%>
		
		<tr>
		  <td nowrap class="TableData" width="100">加班时间：</td>
		  <td>
		     <input type="text" id="submitDate"
				name="submitDate" size="12" maxlength="19"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
				class="" required
				value="<%=curDateStr.substring(0,10)%>"  />
				 &nbsp;&nbsp;
				 
				 <span>
				  从<input
					id="startTime" type='text' name="startTime"
					style="width: 70px;"
					class="Wdate" required
					onclick="WdatePicker({dateFmt:'HH:mm',minDate:'00:00',maxDate:'#F{$dp.$D(\'endTime\')}'})"
					value="<%=curDateStr.substring(11,16)%>" onchange="getHours();" />
				</span>
				 至
				  <span>
				 <input
					id="endTime" type='text' name="endTime" 
					style="width: 70px;"
					class="Wdate" required
					onclick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'23:59'})"
					value="<%=curDateStr.substring(11,16)%>" onchange="getHours();"  />
				</span>
		  
		  </td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">加班时长：</td>
			<td>
				<input type="text" id='overHours' name='overHours' class="BigInput"  required/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">加班内容：</td>
			<td class="TableData">
				<textarea type="text" id="overtimeDesc" name="overtimeDesc" class="" required cols="50" rows="4"></textarea></td>
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
