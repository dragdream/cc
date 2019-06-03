<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String topicSectionId = TeeStringUtil.getString(request.getParameter("topicSectionId"), "");
String uuid = TeeStringUtil.getString(request.getParameter("uuid"), "");
//返回标识。1-返回我的帖子;其他值-返回topicList.jsp;2-返回最新发帖
String returnFlag = TeeStringUtil.getString(request.getParameter("returnFlag"),"");
String model = TeeAttachmentModelKeys.topic;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发新帖</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
var uuid = "<%=uuid%>";
var editor;
var uEditorObj;
var topicSectionId = "<%=topicSectionId%>";
function doInit(){
	
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	    //设置默认字体大小
	
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
	if(uuid!=""){
		/* CKEDITOR.on('instanceReady', function (e) {
			editor = e.editor;
			
		}); */
		getInfoById(uuid);
	}
  });	
}
/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/TeeTopicController/getInfoById.action";
	var para = {uuid : id,isEdit:"1"};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
			uEditorObj.setContent(prc.content);
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#fileContainer").append(fileItem);
			}
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}


function checkForm(){
	var check = $("#form1").valid(); 
	if(!check){
		return false; 
	}
	return true;
}

function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath %>/TeeTopicController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		//alert(tools.jsonObj2String(para));
		
		
		para["content"] = uEditorObj.getContent();
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			toReturn();
			$.MsgBox.Alert_auto("保存成功!");
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}



function toReturn(){
	if(uuid==""){
		location.href = contextPath + "/system/subsys/topic/topicManage/topicList.jsp?uuid=<%=topicSectionId%>";
	}else{
		location.href = contextPath + "/system/subsys/topic/topicManage/topicDetail.jsp?returnFlag=<%=returnFlag%>&topicSectionId=<%=topicSectionId%>&uuid=<%=uuid%>";
	}
	
}
</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
   <div class="topbar clearfix" id="toolbar">
      <div class="fl left">
         <span style="font-size: 12px;font-weight: bold;">新建/编辑帖子</span>
      </div>
      <div class="fr right">
         <input type="button"  value="保存" class="btn-win-white" onclick="doSaveOrUpdate();"/>
	     <input type="button"  value="返回" class="btn-win-white" onclick="toReturn();"/>
      </div>  
   </div>
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="uuid" id="uuid" value="<%=uuid%>">
<input type="hidden" name="topicSectionId" id="topicSectionId" value="<%=topicSectionId%>">
<table class="TableBlock_page" >
	<tr>
		<td nowrap class="TableData"  width="8%;" style="text-indent: 15px;">主题：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="subject" id="subject" size="80" class="BigInput  easyui-validatebox" required/>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" style="text-indent: 15px;">附件上传：</td>
		<td class="TableData">
			<div id="fileContainer"></div> 
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" value=""/>
		</td>
	</tr>
	<tr>
		<td class="TableData" colspan="2" style="text-indent:15px" >
			<textarea rows="5" cols="83" id="content" name="content" class="BigTextarea" ></textarea>
		</td>
	</tr>
	<tr>
	  <td>
	     <div id="anonymous1"><input type="checkbox" name="anonymous" id="anonymous" value="1"/>&nbsp;&nbsp;匿名发布</div>
	  </td>
	</tr>
</table>
</form>
</body>
<%-- <body onload="doInit();">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">新建/编辑帖子</span>
</div>
		
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="uuid" id="uuid" value="<%=uuid%>">
<input type="hidden" name="topicSectionId" id="topicSectionId" value="<%=topicSectionId%>">
<table class="none-table" >
	<tr>
		<td nowrap class="TableData"  width="8%;">主题：<font style='color:red'>*</font></td>
		<td class="TableData" width="35%;" >
			<input type="text" name="subject" id="subject" size="80" class="BigInput  easyui-validatebox" required="true" value="" maxlength="150">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >附件上传：</td>
		<td class="TableData">
			<div id="fileContainer"></div> 
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" value=""/>
		</td>
	</tr>
	<tr>
		<td class="TableData" colspan="2" >
			<textarea rows="5" cols="83" id="content" name="content" class="BigTextarea" ></textarea>
		</td>
	</tr>
	<tr>
	  <td>
	     <div id="anonymous1"><input type="checkbox" name="anonymous" id="anonymous" value="1"/>&nbsp;&nbsp;匿名发布</div>
	  </td>
	</tr>
	<tr >
		<td colspan="2" >
			<input type="button"  value="保存" class="btn btn-primary" onclick="doSaveOrUpdate();"/>&nbsp;&nbsp;
			<input type="button"  value="返回" class="btn btn-default" onclick="toReturn();"/>&nbsp;&nbsp;
			
		</td>
	</tr>
</table>
</form>




</body> --%>
</html>