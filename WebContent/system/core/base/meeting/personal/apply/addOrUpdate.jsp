<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
	 <%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("id"), 0);//会议申请Id
	String model = TeeAttachmentModelKeys.MEETINGATTEND;
	
	//从fullCalendar 直接申请
	int meetRoomId = TeeStringUtil.getInteger(request.getParameter("meetRoomId"), 0);//会议申请Id

	String meetDate = TeeStringUtil.getString(request.getParameter("meetDate"), "");//开始时间
	String startTime = TeeStringUtil.getString(request.getParameter("startTime"), "");//开始时间
	String endTime = TeeStringUtil.getString(request.getParameter("endTime"), "");//结束时间
	
	String isLeaderOpt = TeeStringUtil.getString(request.getParameter("isLeaderOpt"), "");//是否是会议管理修改 ---  1-是  其它-不是
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<%@ include file="/header/userheader.jsp" %>

<title>外出申请</title>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/meeting/js/meeting.js"></script>
<style type="text/css">
</style>
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

var sid = <%=sid%>;
var meetRoomId = <%=meetRoomId%>;

/**$.extend($.fn.validatebox.defaults.rules, {
	checkTimeComp: {   
	 	validator: function(value, param){   
	
  			 return  value >  param ; 
 	    },  
  	   message: '开始时间不能大于等于结束时间！'
    } 
});**/

function doInit()
{
	showPhoneSmsForModule("phoneSmsSpan","031",'<%=person.getUuid()%>');
	var meetRooms = selectPostMeetRoom();
	var roomOptions = "";
	for(var i = 0 ; i< meetRooms.length ; i++){
		roomOptions = roomOptions + "<option value=" +meetRooms[i].sid + ">" + meetRooms[i].mrName + "</option>";
	}
	$("#meetRoomId").append(roomOptions);
	if(meetRoomId > 0){
		$("#meetRoomId").val(meetRoomId);
	}
	
	//审批人员
	var paramName = "MEETING_MANAGER_TYPE";
	//获取参数
	var params = getSysParamByNames(paramName);
	if(params.length > 0){
		var param = params[0];
		if(param.paraValue != "" ){
			var personInfo = getPersonListByUuids(param.paraValue);
			var personOptions = "";
			for(var i =0; i < personInfo.length ; i ++){
				personOptions = personOptions +  "<option value='" + personInfo[i].uuid +  "'>" + personInfo[i].userName + "</option>"; 
			}
			$("#managerId").append(personOptions);
		}
		
	}
	if(sid > 0){
		getById(sid);
	}
	
	//多附件简单上传组件，随表单提交
	new TeeSimpleUpload({
		fileContainer:"attachments",//文件列表容器
		uploadHolder:"uploadHolder1",//上传按钮放置容器
		valuesHolder:"meetAttachmentIds",//附件主键返回值容器，是个input
		form:"form1",//随form表单提交
			renderFiles:true,//渲染附件
			post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
		});
	
	
}


/**
 * 新建或者更新
 */
function doSaveOrUpdate(){
	if(checkFrom()){
	
		var url = contextPath + "/meetManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		para['model'] = '<%=model%>';
		$("#form1").doUpload({
			url:url,
			success:function(json){
				if(!json.rtState){
					$.MsgBox.Alert_auto(json.rtMsg);
					return false;
				}else{
					//alert("保存成功！");
					$.MsgBox.Alert_auto("保存成功！");
					window.parent.location.reload();
					sendPhoneSmsFunc();
					//callback();
					//window.close();
<%-- 					window.location.href ="<%=contextPath%>/system/core/base/meeting/personal/manager_add.jsp"; --%>
					//刷新父页面
					try{
						if(typeof(eval(parent.onchangeMeetRoom)) == 'function'){
							//parent.onchangeMeetRoom('');
						}
					}catch(e){
						//alert("no function");
					}
					
					//parent.BSWINDOW.modal("hide");
					return true;
				}
				
			},
			post_params:para
		});
		
		
		/* var jsonObj = tools.requestJsonRs(url,para);
		if(jsonObj.rtState){
			//$.jBox.tip();
			alert("保存成功！");
			return true;
		}else{
			alert(jsonObj.rtMsg);
		} */
	}
	return false;
}

//发送短信
function sendPhoneSmsFunc(){
	var mettingDateStr =$("#meetDateStr").val() + " " + $("#startTimeStr").val() + "至" + $("#endTimeStr").val() + "点";
	var smsContent= $("#userName").val() + "申请了会议，会议时间从" + mettingDateStr + "，主题是：" + $("#subject").val() + "，请注意会议时间。";
	var toSmsUserIdStr =  $("#attendee").val();
	sendPhoneSms(toSmsUserIdStr,smsContent,'');//2014-11-09 18:42:00
}




/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").valid(); 
	 if(!check  ){
		 return false; 
	 }
	 if($("#managerId")[0].value == ""){
		 $.MsgBox.Alert_auto("审批人员是必填项!");
		 return false;
	 }
	 
	 if($("#meetRoomId")[0].value == ""){
		 $.MsgBox.Alert_auto("会议室是必填项!");
		 return false;
	 }
	 return true;
}

/**
 * 获取外出 by Id
 */
function getById(id){
	var url =   "<%=contextPath%>/meetManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#startTimeStr").val(prc.startTimeStr.split(" ")[1]);
			$("#endTimeStr").val(prc.endTimeStr.split(" ")[1]);
			//$("#meetDate").val(prc.meetDateStr);
			
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
	} else {
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
<body onload="doInit();" style="overflow:auto;padding:0 0px;box-sizing:border-box;">
<div class="time_info">
<form action=""  method="post" name="form1" id="form1">
<table align="center" width="100%" class="TableBlock" enctype="multipart/form-data">

	  <tr>
	    <td nowrap class="TableData" >名    称：<font style='color:red'>*</font></td>
	    <td class="TableData" colspan="3">
	      <input type="text" name="meetName" id="meetName" size="60" maxlength="100" class="BigInput easyui-validatebox" value="" required="true">
	    </td>
	  </tr> 
	  <tr>
	    <td nowrap class="TableData"> 主    题：<font style='color:red'>*</font></td>
	    <td class="TableData" colspan="3">
	      <input type="text" name="subject" id="subject" size="60" maxlength="100" class="BigInput easyui-validatebox" value="" required="true">
	    </td>
	  	</tr>
		
		<%
			if(isSuperAdmin){

		%>
		 <tr>
		    <td nowrap class="TableData"> 申请人：</td>
		    <td class="TableData" colspan="3">
		   		 <input type="hidden" name="userId" id="userId" value="<%=person.getUuid()%>"> 
			     <input name="userName" id="userName"  class="SmallStatic BigInput" wrap="yes" readonly value="<%=person.getUserName()%>" />
				 <a href="javascript:void(0)"  onClick="selectSingleUser(['userId', 'userName'] )">添加</a> 
				 <a href="javascript:void(0)" class="orgClear" onClick="clearData('userId', 'userName')">清空</a>
	        </td>
	  	</tr>
		
		<%
			}
		%>
		<tr>
		    <td nowrap class="TableData">会议纪要员：</td>
		    <td class="TableData" colspan="3">
		   		 <input type="hidden" name="recorderId" id="recorderId" value=""> 
			     <input name="recorderName" id="recorderName"  class="SmallStatic BigInput" wrap="yes" readonly value="" />
				 <a href="javascript:void(0)"  onClick="selectSingleUser(['recorderId', 'recorderName'] )">添加</a> 
				 <a href="javascript:void(0)" class="orgClear" onClick="clearData('recorderId', 'recorderName')">清空</a>
	        </td>
	  	</tr>
	  	<tr id="time_status0">
		    <td nowrap class="TableData" width="70"> 申请时间：<font style='color:red'>*</font></td>
		    <td class="TableData" colspan="3">
		    
		    	<input type="text" name="meetDateStr" id="meetDateStr" size="10" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="<%=meetDate %>">
		     	从
		      <input type="text" name="startTimeStr" id="startTimeStr" size="5" maxlength="5" class="BigInput easyui-validatebox" value="<%=startTime %>" required="true" validType="timeHourSecond[]"   onClick="WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'endTimeStr\')}'})">
		 		 至
		      <input type="text" name="endTimeStr" id="endTimeStr" size="5" maxlength="5" class="BigInput easyui-validatebox" value="<%=endTime%>"  required="true" validType="timeHourSecond[]&checkTimeComp[$('#startTimeStr').val()]"   onClick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'startTimeStr\')}'})">
		    
		    </td>
  		</tr> 
	  <tr>
	    <td nowrap class="TableData" width="50" >会  议  室：<font style='color:red'>*</font></td>
	    <td class="TableData"  colspan="3" >
	    	<select id="meetRoomId" name="meetRoomId" onchange="" class="BigSelect">
	      </select>
	      &nbsp;<a href="javascript:void(0);" onClick="toMeetRoom();">查看会议室详情</a>
	     </td>

	  </tr>
	  <tr>
    
  	 <td nowrap class="TableData" width="85"> 审批管理员：<font style='color:red'>*</font></td>
	  <td class="TableData" id="select_manager" >
	      <select name="managerId" id="managerId" class="BigSelect" >
	      </select>
	  </td> 	  	
  </tr>
	<tr>
    <td nowrap class="TableData"> 出席人员（外部）：</td>
    <td class="TableData" colspan="3">
      <textarea name="attendeeOut" id="attendeeOut" class="BigTextarea"  cols="60" rows="2"></textarea>
     </td>
  </tr>
  <tr>
    <td nowrap class="TableData"> 出席人员（内部）：</td>
    <td class="TableData" colspan="3">
      <input type="hidden" name="attendee" id="attendee" value="">
      <textarea cols=60 name="attendeeName" id="attendeeName" rows=3 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
      <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['attendee', 'attendeeName']);">添加</a>
      <a href="javascript:void(0);" class="orgClear" onClick="$('#attendee').val('');$('#attendeeName').val('');">清空</a>

    </td>
  <%-- </tr>
    <tr>
      <td nowrap class="TableData">查看范围（部门）：</td>
      <td class="TableData" colspan="3">
        <input type="hidden" name="toId" id="toId" value="">
	      <textarea cols=50 name="toIdDesc" id="toIdDesc" rows=2 class="BigStatic" wrap="yes" readonly></textarea>
	      <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['toId', 'toIdDesc']);">添加</a>
	      <a href="javascript:void(0);" class="orgClear" onClick="$('toId').value='';$('toIdDesc').value='';">清空</a>
      </td>
    </tr>
    <tr>
      <td nowrap class="TableData">查看范围（角色）：</td>
      <td class="TableData" colspan="3">
        <input type="hidden" name="privId" id="privId" value="">
	      <textarea cols=50 name="privIdDesc" id="privIdDesc" rows=2 class="BigStatic" wrap="yes" readonly></textarea>
	      <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['privId', 'privIdDesc']);">添加</a>
	      <a href="javascript:void(0);" class="orgClear" onClick="$('privId').value='';$('privIdDesc').value='';">清空</a>
      </td>
   </tr>
   <tr>
      <td nowrap class="TableData">查看范围（人员）：</td>
      <td class="TableData" colspan="3">
        <input type="hidden" name="SECRET_TO_ID" value="">
        <input type="hidden" name="secretToId" id="secretToId" value="">
	      <textarea cols=50 name="secretToIdDesc" id="secretToIdDesc" rows=2 class="BigStatic" wrap="yes" readonly></textarea>
	      <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['secretToId', 'secretToIdDesc']);">添加</a>
	      <a href="javascript:void(0);" class="orgClear" onClick="$('secretToId').value='';$('secretToIdDesc').value='';">清空</a>
      </td>
   </tr> --%>

<!--   <tr>
   
      <td nowrap class="TableData"> 在线调度人员：</td>
      <td class="TableData"  id="online_user">
      	<span id="onlineUser"></span>
		</td>
		
		<td nowrap class="TableData"> 提醒设置：</td>
   			 <td class="TableData">
    	
  		</td>
    </tr> -->
   
   <tr>
    <td nowrap class="TableData">会议室设备：</td>
    <td nowrap class="TableData" colspan="3">
    		<input id="equipmentIds" name="equipmentIds" type="hidden" value=''> 
				<textarea name="equipmentNames" id="equipmentNames" class="SmallStatic BigTextarea" rows="2" cols="60"readonly="readonly"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectMeetingEquipment(['equipmentIds','equipmentNames'],'')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('equipmentIds','equipmentNames')">清空</a>
    </td>
  </tr>
   <!--  <tr>
      <td nowrap class="TableData">会议纪要员：</td>
      <td  class="TableData" colspan="3">
        <input type="hidden" name="recorder" id="recorder" value="">
        <input type="text" name="recorderDesc" id="recorderDesc" size="20" readonly class="BigInput" maxlength="20"  value="">
       <a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['recorder', 'recorderDesc']);">添加</a>
       <a href="javascript:void(0);" class="orgClear" onClick="$('recorder').value='';$('recorderDesc').value='';">清空</a>
      </td>
    </tr> -->
  <tr>
    <td nowrap class="TableData" width="80">写入日程安排：</td>
    <td class="TableData" colspan="3">
    	<span id="isWrite"><input type="checkbox" name="isWriteCalendar" id="isWriteCalendar" value="1" checked>&nbsp;&nbsp;<label for="isWeiteCalendar">是</label></span>
    	（会议通过审批后写入日程安排）
    	</td>
  </tr>
  <tr>
    <td nowrap class="TableData" width="80">通知出席人员：</td>
    <td class="TableData" colspan="3">
    	<span id="smsRemindDiv"><input type="checkbox" name="smsRemind" id="smsRemind" value="1" checked><label for="smsRemind">使用内部短信提醒</label>&nbsp;&nbsp;</span>
    	<span id="phoneSmsSpan"></span>&nbsp;&nbsp;
    	 提前 <input type="text" name="resendHour" id="resendHour" size="3" maxlength="3" class="BigInput easyui-validatebox"  value=""    validType="integeZero[]"> 小时
   			<input type="text" name="resendMinute" id="resendMinute" size="3" maxlength="3" class="BigInput easyui-validatebox" value=""    validType="integeZero[]"> 分提醒（默认会议通过审批后提醒）
    	</td>
  </tr>
  <tr>
    <td nowrap class="TableData">附件：</td>
    <td nowrap class="TableData" colspan="3">
   		 <div id="attachments" style="margin-bottom:10px;"></div>
		 <input id="meetAttachmentIds" type="hidden"/>

		 <a id="uploadHolder1" class="add_swfupload"  >附件上传</a>
	</td>
  </tr>
 <!--  <tr height="25">
    <td nowrap class="TableData">附件选择：</td>
    <td class="TableData" colspan="3">
   			
	    </td>
  </tr>   -->
  <tr>
    <td nowrap class="TableData" colspan="1"><span id="ATTACH_LABEL">会议描述：</span></td>

    <td class="TableData" colspan="3">
		 
		   <textarea name="meetDesc" id="meetDesc" class="BigTextarea"  cols="75" rows="10"></textarea>
		 
	 <input type="hidden" name="sid" id="sid" value="<%=sid %>">
	 <input type="hidden" name="isLeaderOpt" id="isLeaderOpt" value="<%=isLeaderOpt %>">
	 
  </div>
 	</td>
  </tr>
  <!-- <tr class="TableControl">
    <td nowrap colspan="6" align="center">
     
      <input type="hidden" name="checkEquipmentes" id="checkEquipmentes" value="">
      <input type="button" value="确定" class="btn btn-primary" onclick="doSaveOrUpdate();">&nbsp;&nbsp;&nbsp;
    </td>
  </tr> -->
  </table>
</form>
</div>		
</body>

</html>
