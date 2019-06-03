<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	Date curDate = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormatWeek = new SimpleDateFormat("E");
	Calendar c = Calendar.getInstance();
	String curDateStr = dateFormat.format(curDate);
	int month = Integer.parseInt(curDateStr.substring(5, 7));
	int day = Integer.parseInt(curDateStr.substring(8, 10));
	int hour = Integer.parseInt(curDateStr.substring(11, 13));
	int minute = Integer.parseInt(curDateStr.substring(14, 16));
	int second = Integer.parseInt(curDateStr.substring(17, 19));
	String time = curDateStr.substring(11, 19);
	c.setTime(curDate);
	int week = c.get(Calendar.DAY_OF_WEEK);
	if (week == 1) {
		week = 7;
	} else {
		week = week - 1;
	}
	String weeks[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"};

	String  userId = TeeStringUtil.getString(request.getParameter("userId"), "");//用户Id
	if(!userId.equals("")){
		userId = userId + ",";
	}
	String userName = TeeStringUtil.getString(request.getParameter("userName"),"");
	if(!userName.equals("")){
		userName = userName + ",";
	}
	String date = TeeStringUtil.getString(request.getParameter("date"),TeeUtility.getCurDateTimeStr("yyyy-MM-dd"));//日期

	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//日程Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<title>日程事务</title>
<style type="text/css">
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/calendar/js/affair.js"></script>

<script type="text/javascript">
var userId  = '<%=userId%>';
var date = '<%=date%>';
var sid = <%=sid%>;
function doInit()
{
	$("#remindTime").popover();
	$("#remindTime3").popover();
	$("#remindTime4").popover();
	$("#remindTime5").popover(); 

	//得到提醒时间设置select
	getRemindTimeDataContent(<%=hour%>,<%=minute%>,<%=second%>);
	
	//处理提醒时间点击时间
	/* $("input[data-toggle='popover']").click(function(event){
		setRemindTime(this);
	}); */
	
	//处理提醒时间点击后处理样式
	$("input[data-toggle='popover']").on('shown.bs.popover', function () {//展示后执行触发时间
		  $(".popover .popover-content").css({"background-color":"#fbfafb"});
		  //设置时间
		  var remindType = $("#remindType").val();
		  var remindTimeIndex = "";
		  if(remindType != '2'){
			  remindTimeIndex = remindType;
		  }
		  
		  var remindTimeValue = $("#remindTime" + remindTimeIndex).val();
		  if(remindTimeValue && remindTimeValue != ""){
			  var remindTimeValueArr = remindTimeValue.split(":");
			  $("#remindTimeHour").val(remindTimeValueArr[0]);
			  $("#remindTimeMinute").val(remindTimeValueArr[1]);
			  $("#remindTimeSecond").val(remindTimeValueArr[2]);
		  }
	});
	toAddUpdateAffair(sid);
}

/**
 * 新建或者更新周期性事务
 */
function doSaveOrUpdateAffair(){
	if(checkFrom()){
		var url = contextPath + "/calendarManage/leaderAddOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			alert("保存成功！");
			return true;
		}else{
			alert(jsonObj.rtMsg);
		}
	}
	return false;
}
function checkFrom(){
	 var check = $("#form1").form('validate'); 
	 if(check ){
		 return true; 
	 }
	 return false;
}

function toAddUpdateAffair(id){
	if(id && id > 0){
		$("#userIdTr").hide();
		var url =   "<%=contextPath%>/affairManage/selectById.action";
		var para = {sid : id};
		var jsonObj = tools.requestJsonRs(url, para);
		if (jsonObj.rtState) {
			var prc = jsonObj.rtData;
			if (prc && prc.sid) {
				bindJsonObj2Cntrl(prc);
				$("#myModalLabel").html("编辑周期性事务");
					$("#startDate").val(prc.startTimeStr);
					$("#endDate").val(prc.endTimeStr);

					setCalAffType($("#calAffType")[0]);
					//bindJsonObj2Cntrl
					if (prc.remindType == 3 || prc.remindType == 4
							|| prc.remindType == 5) {//周、月、年
						$("#remindTime" + prc.remindType)
								.val(prc.remindTimeStr);
					} else {
						$("#remindTime").val(prc.remindTimeStr);
					}
					sel_change(prc.remindType);

					if (prc.remindType == 3) {
						$("#remindDate3").val(prc.remindDate);
					} else if (prc.remindType == 4) {
						$("#remindDate4").val(prc.remindDate);
					} else if (prc.remindType == 5) {
						var remindDateArray = prc.remindDate.split("-");
						$("#remindDate5Mon").val(remindDateArray[0]);
						$("#remindDate5Day").val(remindDateArray[1]);
					}

				}
			} else {
				alert(jsonObj.rtMsg);
			}
		}

	}

	/***
	 * 设置日程类型
	 * @param obj
	 */
	function setCalAffType(obj) {
		if (obj.checked == true) {
			$("#remindTypeTr").show();
			$("#remindTimeTr").show();
		} else {
			$("#remindTypeTr").hide();
			$("#remindTimeTr").hide();
		}
	}
</script>

</head>
<body onload="doInit();">
	<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="520px" align="center">

		<tr class="TableData" id="userIdTr">
			<td nowrap>人员：</td>
			<td nowrap align="left">
				<input id="userIds" name="userIds" type="hidden" value='<%=userId %>'> 
				<textarea name="userNames" id="userNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"><%=userName %></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['userIds','userNames'],'3')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('userIds','userNames')">清空</a>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">起始时间&nbsp;<font color="red">*</font>：</td>
			<td class="TableData"><input type="text" id="startDate"
				name="startDate" size="20" maxlength="19"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\')}'})"
				class="BigInput easyui-validatebox" required="true"
				value=""></td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">结束时间：</td>
			<td class="TableData"><input type="text" id="endDate"
				name="endDate" size="20" maxlength="19"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'})"
				class="BigInput" value=""></td>
		</tr>
		<tr class="TableData">
			<td nowrap>事务类型：</td>
			<td nowrap align="left"><select class="BigSelect" name="calType"
				id="calType">
					<option value="1">工作事务</option>
					<option value="2">个人事务</option>
			</select></td>
		</tr>
		<tr class="TableData">
			<td nowrap>是否重复：</td>
			<td nowrap align="left"><input name="calAffType" id='calAffType'
				type="checkbox" value="1" onclick='setCalAffType(this);'></label for='calAffType'></label></td>
		</tr>
		<tr id="remindTypeTr" style="display: none;">
			<td nowrap class="TableData">提醒类型：</td>
			<td class="TableData"><select id="remindType" name="remindType"
				class="BigSelect" onchange="sel_change(this.value)">
					<option value="2">按日提醒</option>
					<option value="3">按周提醒</option>
					<option value="4">按月提醒</option>
					<option value="5">按年提醒</option>
			</select> <span id="dayShow"> <input type="checkbox" name="isWeekend"
					id="isWeekend"  value='1'></input>&nbsp;选中为排除周六、日
			</span></td>
		</tr>
		<tr id="remindTimeTr" style="display: none;">
			<td nowrap class="TableData">提醒时间：</td>
			<td class="TableData"><span id="day"> <input
					id="remindTime" type='text' name="remindTime"
					onclick="setRemindTime(this);" style="width: 60px;"
					class="BigInput easyui-validatebox" validType="time[]"
					value="<%=time%>" data-placement="right" data-content=""
					data-toggle="popover" data-html="true"> &nbsp;&nbsp;为空为当前时间
			</span> <span id="week" style="display: none"> <select
					id="remindDate3" name="remindDate3" class="BigSelect">
						<%
							for (int i = 1; i <= 7; i++) {
								if (i == week) {
						%>
						<option value="<%=i%>" selected="selected"><%=weeks[i - 1]%></option>
						<%
							} else {
						%>
						<option value="<%=i%>"><%=weeks[i - 1]%></option>
						<%
							}
							}
						%>
				</select>&nbsp;&nbsp; <input id="remindTime3" type='text' name="remindTime3"
					onclick="setRemindTime(this);" style="width: 60px;"
					class="BigInput easyui-validatebox" value="<%=time%>"
					validType='time[]' data-placement="right" data-content=""
					data-toggle="popover" data-html="true"> &nbsp;&nbsp;为空为当前时间
			</span> <span id="mon" style="display: none"> <select
					id="remindDate4" name="remindDate4" class="BigSelect">
						<%
							for (int i = 1; i <= 31; i++) {
								if (i == day) {
						%>
						<option value="<%=i%>" selected="selected"><%=i%>日
						</option>
						<%
							} else {
						%>
						<option value="<%=i%>"><%=i%>日
						</option>
						<%
							}
							}
						%>
				</select>&nbsp;&nbsp; <input id="remindTime4" type='text' name="remindTime4"
					onclick="setRemindTime(this);" style="width: 60px;"
					class="BigInput" class="BigInput easyui-validatebox"
					value="<%=time%>" validType='time[]' data-placement="right"
					data-content="" data-toggle="popover" data-html="true">

					&nbsp;&nbsp;为空为当前时间
			</span> <span id="years" style="display: none"> <select
					id="remindDate5Mon" name="remindDate5Mon" class="BigSelect">
						<%
							for (int i = 1; i <= 12; i++) {
								if (i == month) {
						%>
						<option value="<%=i%>" selected="selected"><%=i%>月
						</option>
						<%
							} else {
						%>
						<option value="<%=i%>"><%=i%>月
						</option>
						<%
							}
							}
						%>
				</select>&nbsp;&nbsp; <select id="remindDate5Day" name="remindDate5Day"
					class="BigSelect">
						<%
							for (int i = 1; i <= 31; i++) {
								if (i == day) {
						%>
						<option value="<%=i%>" selected="selected"><%=i%>日
						</option>
						<%
							} else {
						%>
						<option value="<%=i%>"><%=i%>日
						</option>
						<%
							}
						}
						%>
				</select>&nbsp;&nbsp; <input id="remindTime5" type='text' name="remindTime5"
					onclick="setRemindTime(this);" size="10"
					class="BigInput easyui-validatebox" value="<%=time%>"
					validType='time[]' data-placement="top" data-content=""
					data-toggle="popover" data-html="true"> &nbsp;&nbsp;为空为当前时间

			</span></td>
		</tr>
		<tr>
			<td nowrap class="TableData">事务内容&nbsp;<font color="red">*</font>：</td>
			<td class="TableData"><textarea id="content" name="content"
					cols="42" rows="3" class="BigTextarea easyui-validatebox"
					required="true"></textarea></td>
		</tr>
		<tr class="TableData">
			<td nowrap>参与者：</td>
			<td nowrap align="left">
			    <input id="actorIds" name="actorIds" type="hidden"> 
			        <textarea name="actorNames" id="actorNames" class="SmallStatic BigTextarea" rows="2" cols="35" readonly="readonly"></textarea> 
			        &nbsp;&nbsp;<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['actorIds','actorNames'],'1')">选择</a>&nbsp;&nbsp;
			         <a href="javascript:void(0);" class="orgClear" onClick="clearData('actorIds','actorNames')">清空</a></td>
		</tr>
		<!--   <tr >
						    	<td colspan="2"  >
						    	 <div class="" align="center">
						    	 	<input id="sid" name="sid" type="hidden" value="0">
						    	 	<input id="calAffType" name="calAffType" type="hidden" value="1">
						    	 	
			       				 	<button type="button" class="btn btn-primary" onclick="doSaveOrUpdateAffair();">保存</button>
						    		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						    	</div>
						    	</td>
						    </tr> -->
	</table>

	<input id="sid" name="sid" type="hidden" value="0"> </form>
</body>

</html>
