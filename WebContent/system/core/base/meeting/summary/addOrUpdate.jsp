<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
    <%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("id"), 0);//会议申请Id
	String model = TeeAttachmentModelKeys.MEETINGRECORD;
	
	int meetRoomId = TeeStringUtil.getInteger(request.getParameter("meetRoomId"), 0);//会议申请Id
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
<%-- <script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script> --%>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<style type="text/css">
.edui-editor-toolbarboxinner edui-default
{
    display:none;
}
.edui-editor  edui-default
{
   display:none;
}
</style>



<script type="text/javascript">

var sid = <%=sid%>;
var meetRoomId = <%=meetRoomId%>;
var editor;
var uEditorObj;
function doInit()
{
	uEditorObj = UE.getEditor('submary',{
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
	,initialFrameWidth:null});//获取编辑器对象
	
	var meetRooms = selectPostMeetRoom();
	var roomOptions = "";
	for(var i = 0 ; i< meetRooms.length ; i++){
		roomOptions = roomOptions + "<option value=" +meetRooms[i].sid + ">" + meetRooms[i].mrName + "</option>";
	}
	$("#meetRoomId").append(roomOptions);
	if(meetRoomId > 0){
		$("#meetRoomId").val(meetRoomId);
	}
	/* editor = CKEDITOR.replace('submary',{
		width : 'auto',
		height:300
	}); */
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(300);
		//uEditorObj.setWidth(100);
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
	});
}


/**
 * 新建或者更新
 */
function doSaveOrUpdate(fun){
	if(checkFrom()){
		var url = contextPath + "/meetManage/addSummary.action";
		var para =  tools.formToJson($("#form1")) ;
		para['model'] = '<%=model%>';
		$("#form1").doUpload({
			url:url,
			success:function(json){
				if(!json.rtState){
					$.MsgBox.Alert_auto(json.rtMsg);
					return false;
				}else{
 					//$.MsgBox.Alert_auto("保存成功！");
					//刷新父页面
// 					window.location.reload();
					try{
						if(typeof(eval(parent.onchangeMeetRoom)) == 'function'){
							parent.onchangeMeetRoom('');
						}
					}catch(e){
					}
					console.log("qqq");
					//parent.BSWINDOW.modal("hide");
					if(typeof(fun)=="function"){
						fun();
					}
					return true;
				}
				
			},
			post_params:para
		});
	}
	//return false;
}

/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").valid(); 
	 return check;
}

/**
 * 获取外出 by Id
 */
function getById(id){
	var url =   "<%=contextPath%>/meetManage/getById2.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			attendees = prc.attendeeName;
			readPeoples = prc.readPeopleNames;
			if(readPeoples.length==0){
				$("#readPeopleNames").val(attendees);
				$("#readPeopleIds").val(prc.attendee);
			}
			 else{
				$("#readPeopleNames").val(readPeoples);
				$("#readPeopleIds").val(prc.readPeopleIds);
			} 
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
	/* 		uEditorObj.on('instanceReady', function (e) {
				alert();
				editor = e.editor;
				alert(prc.submary);
				editor.setContent(prc.submary);
			}); */
			uEditorObj.setContent(prc.submary);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
	

</script>

</head>
<body onload="doInit();">
<form action=""  method="post" name="form1" id="form1">
<table align="center" width="100%" class="TableBlock" enctype="multipart/form-data">

	  <tr>
	    <td nowrap class="TableData"  style="width:120px;background:#f0f0f0">名    称：<font style='color:red'>*</font></td>
	    <td class="TableData" colspan="3">
	      <input type="text" name="meetName" id="meetName" size="58" maxlength="100" class="BigInput readonly" readonly="readonly">
	    </td>
	  </tr> 
  <tr>
    <td nowrap class="TableData" style="width:120px;background:#f0f0f0"> 指定读者：</td>
    <td class="TableData" colspan="3">
      <input type="hidden" name="readPeopleIds" id="readPeopleIds" value="">
      <textarea cols=60 name="readPeopleNames" id="readPeopleNames" rows=3 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>&nbsp;&nbsp;
       <span class='addSpan'>
			<img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hyjy/add.png" onClick="selectUser(['readPeopleIds', 'readPeopleNames']);" value="选择"/>
			 &nbsp;&nbsp;
			 <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/hyjy/clear.png" onClick="$('#readPeopleIds').val('');$('#readPeopleNames').val('');" value="清空"/>
		</span>
      
    </td>
    </tr>
   <!-- <tr>
    <td nowrap class="TableData" style="width:120px;background:#f0f0f0">缺席人员：</td>
    <td nowrap class="TableData" colspan="3">
    		<input id="attendeeNotIds" name="attendeeNotIds" type="hidden" value=''> 
				<textarea name="attendeeNotNames" id="attendeeNotNames" class="SmallStatic BigTextarea" rows="2" cols="60"readonly="readonly"></textarea> &nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['attendeeNotIds','attendeeNotNames'],'')">选择</a>&nbsp;&nbsp; 
				<a href="javascript:void(0);" class="orgClear"onClick="clearData('attendeeNotIds','attendeeNotNames')">清空</a>
    </td>
  </tr> -->
  <tr>
    <td nowrap class="TableData" style="width:120px;background:#f0f0f0">附件：</td>
    <td nowrap class="TableData" colspan="3">
   		 <div id="attachments" style="margin-bottom:10px;"></div>
		 <input id="meetAttachmentIds" type="hidden"/>

		 <a id="uploadHolder1" class="add_swfupload"  >附件上传</a>
	</td>
  </tr>
  <tr>
    <td nowrap class="TableData" colspan="1" style="width:120px;background:#f0f0f0"><span id="ATTACH_LABEL">纪要内容：</span></td>

    <td class="TableData" colspan="3">
	 <textarea name="submary" id="submary" class="BigTextarea"  cols="90" rows="10"></textarea>
	 <input type="hidden" name="sid" id="sid" value="<%=sid %>">
 	</td>
  </tr>
  </table>
</form>
		
</body>

</html>
