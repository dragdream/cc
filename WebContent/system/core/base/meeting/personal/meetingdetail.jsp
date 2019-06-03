<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("id"), 0);//会议申请Id
	int showConfirmFlag = TeeStringUtil.getInteger(request.getParameter("showConfirmFlag"), 0);//是否显示参确认 1-不显示（短消息提醒显示）

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>会议详情</title>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/meeting/js/meeting.js"></script>
<%-- <script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script> --%>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<style type="text/css">
</style>



<script type="text/javascript">

var sid = <%=sid%>;

function doInit()
{
	uEditorObj = UE.getEditor('content',{
		toolbars:[[
		            'fullscreen','undo', 'redo', '|'],[
		                                               'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
		                                               'bold', 'italic', 'underline', 'fontborder', 'strikethrough',  'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
		                                               'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
		                                               'indent', '|',
		                                               'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|'
		                                           ],[
		                                              'link', 'unlink', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
		                                              'map',  'insertframe','pagebreak', '|',
		                                              'horizontal', 'date', 'time', 'spechars', 'snapscreen', '|',
		                                              'inserttable', 'deletetable', 'insertparagraphbeforetable', '|',
		                                              'searchreplace']]
	});//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	updateReadFlagFunc(sid);
	if(sid > 0){
		getById(sid);
	}
	
	/* CKEDITOR.replace('content',{
		width : 'auto',
		height:200
	}); */
	getMeetingTopic(sid);
  });
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
			if('<%=showConfirmFlag%>' !=1){
				var isConfirmFlag = isConfirmFunc(id);
				if((prc.status ==1 || prc.status==2) && isConfirmFlag ==0 ){
					$("#meetingConfirmDiv").show();
				}
			}
			
			$("#startTimeStr").val(prc.startTimeStr.split(" ")[1]);
			$("#endTimeStr").val(prc.endTimeStr.split(" ")[1]);
			//$("#meetDate").val(prc.meetDateStr);
			
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var item = attaches[i];
				item['priv'] = 3;
				var fileItem = tools.getAttachElement(item);
				
				$("#attachments").append(fileItem);
			}
			
			$("#addFav").addFav("(会议详情) "+prc.subject,"/system/core/base/meeting/personal/meetingdetail.jsp?id="+id);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

function meetingConfirmFunc(status){
	setMeetingConfirmFunc('<%=sid%>',status);
}

/**
 * 参加会议或缺席
 */
function setMeetingConfirmFunc(sid,status){
	if(status == '1'){//参会
		var url = "<%=contextPath %>/TeeMeetingAttendConfirmController/updateAttendFlag.action?meetingId=" + sid + "&attendFlag=" + status ;
	    var para =  tools.formToJson($("#form1"));
	    var jsonRs = tools.requestJsonRs(url,para);
	    if(jsonRs.rtState){
	    	$.MsgBox.Alert_auto("确认成功！");
	      	//datagrid.datagrid('reload');
	      	window.location.reload();
	    }else{
	    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
	    }
		return;
	}
  var title = "缺席说明";
  var buttonName = "缺席确认";
  var url = contextPath + "/system/core/base/meeting/personal/setMeetingConfirm.jsp?meetingId=" + sid + "&attendFlag=" + status;
  bsWindow(url ,title,{width:"750",height:"320",buttons:
		[
		 {name:buttonName,classStyle:"modal-save btn-alert-blue"},
	 	 {name:"关闭",classStyle:"modal-btn-close btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="缺席确认" ){
			cw.doSaveOrUpdate(function(){
				//$.jBox.tip("保存成功！", "info", {timeout: 1800});
				$.MsgBox.Alert_auto("保存成功！");
				window.location.reload();
				//BSWINDOW.modal("hide");
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
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
	 	 {name:"关闭",classStyle:"modal-btn-close btn-alert-gray"}
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


function getMeetingTopic(sid){
	var url =   "<%=contextPath%>/meetManage/getMeetingTopic.action";
	var para = {sid : sid};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var html="";
		for(var i=0;i<jsonObj.rtData.length;i++){
			html+="<table style=\"width:100%;margin-bottom:10px;border:1px solid #dddad5\" >"
    		+"<tr style=\"background:#f0f0f0\">"
			+"<td>用户名："+jsonObj.rtData[i].crUserName+"</td>"
			+"<td style=\"text-align:right\">"+jsonObj.rtData[i].crTimeDesc+"</td></tr><tr><td colspan=\"2\">"+jsonObj.rtData[i].content+"</td></tr></table>";
		}
		$("#topicList").html(html);
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

function addMeetingTopic(){
	//var content =CKEDITOR.instances.content.getData();
	var content = uEditorObj.getContent();
	if(!content){
		$.MsgBox.Alert_auto("请输入内容！");
		return;
	}
	var url =   "<%=contextPath%>/meetManage/addMeetingTopic.action?sid="+sid;
	var jsonObj = tools.requestJsonRs(url,{content:content});
	if (jsonObj.rtState) {
		getMeetingTopic(sid);
		//CKEDITOR.instances.content.setData("");
		uEditorObj.setContent("");
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
</script>

</head>
<body onload="doInit();" style="padding:0 0px; ">
<div style=" padding-bottom:10px; display: none; " align="center" id="meetingConfirmDiv">
	<input type="button" class="btn-alert-blue" onclick="meetingConfirmFunc(1);" value="出席"/>&nbsp;&nbsp;
	<input type="button" class="btn-alert-blue" onclick="meetingConfirmFunc(2);" value="缺席"/>&nbsp;&nbsp;
</div>
<form action=""  method="post" name="form1" id="form1">
<table align="center" width="97%" class="TableBlock" enctype="multipart/form-data">

	  <tr>
	    <td nowrap class="TableData"  style="width:120px;background:#f0f0f0">名    称：</td>
	    <td class="TableData"  id="meetName"></td>
	    <td class="TableData" rowspan="3"  style="width:134px;">
	      <img id="erweima" style="display:inline-block;margin:0 auto;" src="<%=contextPath %>/meetManage/qrCodeDownload.action?meetingId=<%=sid%>"/>
	    </td>
	     
	  </tr> 
	  <tr>
	    <td nowrap class="TableData" style="width:120px;background:#f0f0f0"> 主    题：</td>
	    <td class="TableData"   id="subject" ></td>
	    
	 </tr>
	 <tr>
	     <td nowrap class="TableData" style="width:120px;background:#f0f0f0"> 申请人：</td>
	  	 <td class="TableData"   id="userName" > </td>
	  	 
	  	</tr>
		
	  	<tr id="time_status0">
		    <td nowrap class="TableData" width="70" style="width:120px;background:#f0f0f0"> 会议时间</td>
		    <td class="TableData" colspan="2">
		    
		    	从<span id="startTimeStr" ></span>
		    	至<span id="endTimeStr" ></span>
		    	<%-- <input type="text" name="meetDateStr" id="meetDateStr" size="10" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true" value="<%=meetDate %>">
		      --%>	
		     
		    </td>
  		</tr> 
	  <tr>
	    <td nowrap class="TableData" width="50"  style="width:120px;background:#f0f0f0">会  议  室：</td>
	    <td class="TableData"  colspan="2" >
	    	<span id="meetRoomName"></span>
	    	<input type="hidden" id="meetRoomId" name="meetRoomId" onchange="" class="">
	      &nbsp;<a href="javascript:void(0);" onClick="toMeetRoom();">查看会议室详情</a>
	     </td>

	  </tr>
	  <tr>
    
  	 <td nowrap class="TableData" width="85" style="width:120px;background:#f0f0f0"> 审批管理员：</td>
	  <td class="TableData" id="managerName" colspan="2">
	     
	  </td> 	  	
    </td>
  </tr>
  <tr>
  	 <td nowrap class="TableData" width="85" style="width:120px;background:#f0f0f0"> 会议纪要员：</td>
	  <td class="TableData" colspan="2" id="recorderName" >
  </tr>
	<tr>
    <td nowrap class="TableData" style="width:120px;background:#f0f0f0"> 出席人员（外部）：</td>
    <td class="TableData" colspan="2" id="attendeeOut">
 
     </td>
  </tr>
  <tr>
    <td nowrap class="TableData" style="width:120px;background:#f0f0f0"> 出席人员（内部）：</td>
    <td class="TableData" colspan="2" id="attendeeName" >
     
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
    <td nowrap class="TableData" style="width:120px;background:#f0f0f0">会议室设备：</td>
    <td nowrap class="TableData" colspan="2">
    
    	<div id="equipmentNames" ></div>
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
    <td nowrap class="TableData" style="width:120px;background:#f0f0f0">附件：</td>
    <td nowrap class="TableData" colspan="2">
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
    <td nowrap class="TableData" colspan="1" style="width:120px;background:#f0f0f0"><span id="ATTACH_LABEL">会议描述：</span></td>

    <td class="TableData" colspan="2" id="meetDesc">
				 
	 
  </div>
 	</td>
  </tr>

  </table>
  
  
  <table align="center" width="97%" class="TableBlock">
	  <tr>
	    <td nowrap class="TableData"  style="background:#f0f0f0"><b>讨论区</b></td>
	  </tr> 
	  <tr>
	    <td id="topicList">

	    </td>
	  </tr> 
	  <tr>
	    <td nowrap class="TableData"  style="width:120px;background:#f0f0f0"><b>意见讨论</b></td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" >
	    	<div style="padding:10px;">
	    		<input type="button" class="modal-save btn-alert-blue" onclick="addMeetingTopic();" value="提交"/>
	    	</div>
	    	<textarea name="content" id="content"></textarea>
	    </td>
	  </tr>
  </table>
</form>
<img src="<%=systemImagePath %>/favorite_click.png" class="favStyle" style="position: absolute;right: 0;top: 0;" title="添加收藏夹" id="addFav"/>		
</body>

</html>
