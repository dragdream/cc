<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("id"), 0);//会议申请Id

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>会议详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath %>/system/core/base/meeting/js/meeting.js"></script>
<style type="text/css">
</style>



<script type="text/javascript">

var sid = <%=sid%>;

function doInit()
{
	
	if(sid > 0){
		getById(sid);
	}
	
	
	
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
		alert(jsonObj.rtMsg);
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
	 	 {name:"关闭",classStyle:"btn btn-primary"}
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
<body onload="doInit();">
<form action=""  method="post" name="form1" id="form1">
<table align="center" width="96%" class="TableBlock" enctype="multipart/form-data">

	  <tr>
	    <td nowrap class="TableData" >名    称：</td>
	    <td class="TableData" colspan="3" id="meetName">
	     </td>
	  </tr> 
	  <tr>
	    <td nowrap class="TableData"> 主    题：</td>
	    <td class="TableData" colspan="3"  id="subject" ></td>
	 </tr>
	 <tr>
	     <td nowrap class="TableData"> 申请人：</td>
	  	 <td class="TableData" colspan="3"  id="userName" > </td>
	  	</tr>
		
	  	<tr id="time_status0">
		    <td nowrap class="TableData" width="70"> 会议时间</td>
		    <td class="TableData" colspan="3">
		    
		    	从<span id="startTimeStr" ></span>
		    	至<span id="endTimeStr" ></span>
		    	<%-- <input type="text" name="meetDateStr" id="meetDateStr" size="10" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="<%=meetDate %>">
		      --%>	
		     
		    </td>
  		</tr> 
	  <tr>
	    <td nowrap class="TableData" width="50" >会  议  室：</td>
	    <td class="TableData"  colspan="3" >
	    	<span id="meetRoomName"></span>
	    	<input type="hidden" id="meetRoomId" name="meetRoomId" onchange="" class="">
	      &nbsp;<a href="javascript:void(0);" onClick="toMeetRoom();">查看会议室详情</a>
	     </td>

	  </tr>
	  <tr>
    
  	 <td nowrap class="TableData" width="85"> 审批管理员：</td>
	  <td class="TableData" id="managerName" >
	     
	  </td> 	  	
    </td>
  </tr>
	  
	<tr>
    <td nowrap class="TableData"> 出席人员（外部）：</td>
    <td class="TableData" colspan="3" id="attendeeOut">
 
     </td>
  </tr>
  <tr>
    <td nowrap class="TableData"> 出席人员（内部）：</td>
    <td class="TableData" colspan="3" id="attendeeName" >
     
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
    
    	<div id="equipment" ></div>
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
    <td nowrap class="TableData">附件：</td>
    <td nowrap class="TableData" colspan="3">
   		 <div id="attachments" style="margin-bottom:10px;"></div>
		 <input id="meetAttachmentIds" type="hidden"/>

	
	</td>
  </tr>
 <!--  <tr height="25">
    <td nowrap class="TableData">附件选择：</td>
    <td class="TableData" colspan="3">
   			
	    </td>
  </tr>   -->
  <tr>
    <td nowrap class="TableData" colspan="1"><span id="ATTACH_LABEL">会议描述：</span></td>

    <td class="TableData" colspan="3" id="meetDesc">
				 
	 
  </div>
 	</td>
  </tr>

  </table>
</form>
		
</body>

</html>
