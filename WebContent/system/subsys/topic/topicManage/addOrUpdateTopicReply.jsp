<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String topicId = TeeStringUtil.getString(request.getParameter("topicId"), "");
String uuid = TeeStringUtil.getString(request.getParameter("uuid"), "");
String topicOptFlag = TeeStringUtil.getString(request.getParameter("topicOptFlag"), "");//1-对topic操作；其他-对topic_reply
String yinYongFlag = TeeStringUtil.getString(request.getParameter("yinYongFlag"), "");//1-引用；0-编辑
String model = TeeAttachmentModelKeys.topicReply;
String topicSectionId=TeeStringUtil.getString(request.getParameter("topicSectionId"), "");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新的回复</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
var uEditorObj;
var uuid = "<%=uuid%>";
var topicOptFlag = "<%=topicOptFlag%>";
var yinYongFlag = "<%=yinYongFlag%>";
 var topicSectionId ="<%=topicSectionId%>";
var editor;
function doInit(){
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);

var url =   "<%=contextPath%>/TeeTopicSectionController/getInfoById.action";
var para = {uuid : topicSectionId,isEdit:"1"};
var jsonObj = tools.requestJsonRs(url, para);
if(jsonObj.rtData.anonymous==0){
	$("#anonymous1").hide();
}	
	/* //初始化fck
	editor = CKEDITOR.replace('content',{
		width : 'auto',
		height:200
	}); */
	//初始化附件
	new TeeSWFUpload({
		fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"attacheIds",//附件主键返回值容器，是个input
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
	if(uuid || topicOptFlag){
		if(topicOptFlag == "1"){
			getTopicInfoById();
		}else{
			getInfoById(uuid);
		}
		/* CKEDITOR.on('instanceReady', function (e) {
			editor = e.editor;
			if(topicOptFlag == "1"){
				getTopicInfoById();
			}else{
				getInfoById(uuid);
			}
		}); */
	}
  });
}


/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/TeeTopicReplyController/getInfoById.action";
	var para = {uuid : id,isEdit:"1"};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			//bindJsonObj2Cntrl(prc);
			var contentStr = prc.content;
			if(yinYongFlag =='1'){
				contentStr = "<div class=\"bbs_yinyong_frame\">引用" + prc.crUserName + "在" + prc.crTimeStr + "的发言：<div class=\"bbs_yinyong_inner\">" + prc.content+"</div></div><div></div>";
			}else{
				var attaches = prc.attacheModels;
				for(var i=0;i<attaches.length;i++){
					var fileItem = tools.getAttachElement(attaches[i]);
					$("#fileContainer").append(fileItem);
				}
			}
			uEditorObj.setContent(contentStr);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}
/* 获取话题详情 */
function getTopicInfoById(){
	var url =   "<%=contextPath%>/TeeTopicController/getInfoById.action";
	var para = {uuid : "<%=topicId%>"};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			var contentStr = prc.content;
			if(yinYongFlag =='1'){
				contentStr = "<div class=\"bbs_yinyong_frame\">引用" + prc.crUserName + "在" + prc.crTimeStr + "的发言：<div class=\"bbs_yinyong_inner\">" + prc.content+"</div></div><div></div>";
			}
			
			uEditorObj.setContent(contentStr);
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}


function checkForm(){
	
	if(!uEditorObj.getContent()){
		$.MsgBox.Alert_auto("请输入帖子内容！");
		return false; 
	}
	return true;
}

function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath %>/TeeTopicReplyController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		para["content"] = uEditorObj.getContent();
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			toReturn();
			$.MsgBox.Alert_auto("保存成功！");
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}



function toReturn(){
	history.back();
}
</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
   <div class="fl left">
        <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/tlq/icon_huifu.png">
        <span class="title">新的回复</span>
   </div>
   <div class="fr right">
        <input type="button"  value="保存" class="btn-win-white" onclick="doSaveOrUpdate();"/>
		<input type="button"  value="返回" class="btn-win-white" onclick="toReturn();"/>
   </div>
</div>
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="uuid" id="uuid" value="<%=uuid%>">
<input type="hidden" name="topicId" id="topicId" value="<%=topicId%>">
<table class="TableBlock_page" >
	<tr>
		<td  class="TableData" width="100px;" style="text-indent: 15px">附件上传：</td>
		<td class="TableData">
			<div id="fileContainer"></div> 
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" value=""/>
		</td>
	</tr>
	<tr>
		<td class="TableData" colspan="2" >
			<textarea rows="5" cols="83" id="content" name="content" class="BigTextarea" s></textarea>
		</td>
	</tr>
	<tr>
	<td>
	<input type="hidden" name="topicSectionId" id="topicSectionId" value="<%=topicSectionId%>">
	   <div id="anonymous1"><input type="checkbox" name="anonymous" id="anonymous" value="1"/>匿名发布 </div>
	</td>
	</tr>
</table>
</form>

</body>

</html>