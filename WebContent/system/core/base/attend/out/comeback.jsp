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
<title>外出申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>



<script type="text/javascript">

var sid = <%=sid%>;
var currHour = '<%=currHour%>';
var currSecond = '<%=currSecond%>';


function doInit()
{
	
	//toAddUpdateAffair(sid);
	
	if(sid > 0){
		getAttendOutById(sid);
	}
	
}



/**
 * 新建或者更新外出
 */
function doComeBack(){
	if(checkFrom()){
		var url = contextPath + "/attendOutManage/comeBack.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			$.MsgBox.Alert_auto("保存成功！");
			//window.location.reload();
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

	 if(check){
		 return true; 
	 }
	 if($("#managerId").val == ""){
		 return false;
	 }
	 return false; 
}

/**
 * 获取外出 by Id
 */
function getAttendOutById(id){
	var url =   "<%=contextPath%>/attendOutManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#submitDate").val(prc.startTimeStr.substring(0,10));
			$("#startTimeDesc").val(prc.startTimeStr.substring(11,16));
			$("#endTimeDesc").val(prc.endTimeStr.substring(11,16));
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
	

</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2;">
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
			<td nowrap class="TableData" width="100">外出时间：</td>
			<td class="TableData"><input type="text" id="submitDate"
				name="submitDate" size="12" maxlength="19"
				class="" 
				value="<%=curDateStr.substring(0,10)%>" readonly>
				 &nbsp;&nbsp;
				 <span>
				  从&nbsp;<input
					id="startTimeDesc" type='text' name="startTimeStr"
					style="width: 70px;"
					required
					data-content=""
					value="<%=curDateStr.substring(11,16)%>" readonly="readonly">
				</span>
				 至&nbsp;
				  <span>
				 <input
					id="endTimeDesc" type='text' name="endTimeStr" 
					style="width: 70px;"
				    required
					data-content=""
					value="<%=curDateStr.substring(11,16)%>" readonly="readonly">
				</span>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">外出原因：</td>
			<td class="TableData" id="outDesc" name="outDesc">
				<!-- <textarea  id="endDate" name="outDesc" class="" required cols="50" rows="4"></textarea> -->
		     </td>
		</tr>
		
		
	</table>
	<input id="sid" name="sid" type="hidden" value="0"> 
</form>
</body>
<script>
    //启用validate验证
	$("#form1").validate();
</script>
</html>
