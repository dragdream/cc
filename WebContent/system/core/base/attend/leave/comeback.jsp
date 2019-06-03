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
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
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
$.extend($.fn.validatebox.defaults.rules, {
	checkTimeComp: {   
	 	validator: function(value, param){   
  			return  value >=  param ; 
 	    },  
  	   message: '开始时间不能大于结束时间！'
    } 
});

function doInit()
{
	getRemindTime(currHour , currSecond );

	$("#startTimeDesc").popover();
	$("#endTimeDesc").popover();
	//toAddUpdateAffair(sid);
	//处理提醒时间点击后处理样式
	$("input[data-toggle='popover']").on('shown.bs.popover', function (evt) {//展示后执行触发时间
		  $(".popover .popover-content").css({"background-color":"#fbfafb"});
		  //设置时间
		 var remindTimeValue = this.value;
		 if(remindTimeValue && remindTimeValue != ""){
		      var remindTimeValueArr = remindTimeValue.split(":");
			  $(this).parent().find("[name='remindTimeHour']").val(remindTimeValueArr[0]);
			  $(this).parent().find("[name='remindTimeMinute']").val(remindTimeValueArr[1]);

		} 
	});
	
	if(sid > 0){
		getAttendOutById(sid);
	}
	
}



function getRemindTime(HOUR , MINUTE ){
	var HOURSELECT = "<select class='BigSelect' name='remindTimeHour' id=''>";//小时
	var MINUTESELECT = "<select class='BigSelect' name='remindTimeMinute' id=''>";//分钟、
	for ( var i = 0; i < 24; i++) {
		var iStr = i;
		if(i<10){
			iStr = "0"+ i;
		}
		var selected = "";
		if(HOUR == iStr){
			selected = "selected='selected'";
		}
		HOURSELECT = HOURSELECT + "<option value='" + iStr + "' " + selected + ">  " + iStr + "  </option>";
	}
	HOURSELECT = HOURSELECT + "</select>";
	
	for ( var i = 0; i < 60; i++) {
		var iStr = i;
		if(i<10){
			iStr = "0"+ i;
		}
		var selected = "";
		if(MINUTE == iStr){
			selected = "selected='selected'";
		}
		MINUTESELECT = MINUTESELECT + "<option value='" + iStr + "'  " + selected + ">  " + iStr + "  </option>";
	}
	MINUTESELECT = MINUTESELECT + "</select>";

	remindTimeDataContent = HOURSELECT + "&nbsp;&nbsp;" + MINUTESELECT + "&nbsp;&nbsp;" ;
	var remindTimeDataContent1 =  remindTimeDataContent + "&nbsp;&nbsp;<input type='button' value='确定' class='btn btn-primary'  onclick='setRemindTimeFun(this ,1);'></input>";

	var remindTimeDataContent2 =  remindTimeDataContent + "&nbsp;&nbsp;<input type='button' value='确定' class='btn btn-primary'  onclick='setRemindTimeFun(this,2);'></input>";

	$("#startTimeDesc").attr("data-content",remindTimeDataContent1);
	$("#endTimeDesc").attr("data-content",remindTimeDataContent2);
	//return remindTimeDataContent;
}
/**
 * 点击确定
 */
function setRemindTimeFun(thisObj , type){
	var remindTime =  $(thisObj).parent().children("[name='remindTimeHour']").val() + ":" +
					$(thisObj).parent().children("[name='remindTimeMinute']").val() ;
	//alert(remindType +":"+ $("#remindTime5")[0] +":"+ remindTime)
	if(type == '1'){//周
		$("#startTimeDesc").val(remindTime);
		$("#startTimeDesc").popover('toggle');
	}else if(type == '2'){//月
		$("#endTimeDesc").val(remindTime);
		$("#endTimeDesc").popover('toggle');
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
			alert("保存成功！");
			//window.location.reload();
			return true;
		}else{
			alert(jsonObj.rtMsg);
		}
	}
	return false;
}
/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").form('validate'); 
	 if(check ){
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
		alert(jsonObj.rtMsg);
	}
}
	

</script>

</head>
<body onload="doInit();">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" style="margin-top:10px;">

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
				
				class="BigInput easyui-validatebox SmallStatic" required="true"
				value="<%=curDateStr.substring(0,10)%>" readonly="readonly">
				 &nbsp;&nbsp;
				 
				 <span>
				  从<input
					id="startTimeDesc" type='text' name="startTimeStr"
					style="width: 60px;"
					class="BigInput easyui-validatebox" validType="timeHourSecond[]"
					value="<%=curDateStr.substring(11,16)%>" data-placement="bottom" data-content=""
					data-toggle="popover" data-html="true">
				</span>
				 至
				  <span>
				 <input
					id="endTimeDesc" type='text' name="endTimeStr" style="width: 60px;"
					class="BigInput easyui-validatebox" validType="timeHourSecond[]&checkTimeComp[$('#startTimeDesc').val()]"
					value="<%=curDateStr.substring(11,16)%>" data-placement="bottom" data-content=""
					data-toggle="popover" data-html="true">
				</span>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100">外出原因：</td>
			<td class="TableData">
				<textarea type="text" id="endDate" name="outDesc" class="BigTextarea easyui-validatebox" required="true" cols="50" rows="4"></textarea></td>
		</tr>
		
		
	</table>
	<input id="sid" name="sid" type="hidden" value="0"> 
</form>
</body>

</html>
