<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int rootFolderPriv = TeeStringUtil.getInteger(request.getParameter("rootFolderPriv"), 0);
	String sid = request.getParameter("sid");
	if(sid == null){
    	sid = "0";
	}
	String model = TeeAttachmentModelKeys.FILE_NET_DISK;
	//项目主键
	String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<script type="text/javascript">
var projectId="<%=projectId%>";
function doInit(){
		new TeeSWFUpload({
			fileContainer:"fileContainer",//文件列表容器
			uploadHolder:"uploadHolder",//上传按钮放置容器
			valuesHolder:"valuesHolder",//附件主键返回值容器，是个input
			uploadSuccess:function(fileInfo){	 
				  var url = contextPath+"/fileNetdisk/uploadNetdiskFile.action";
				  var para = {sid:<%=sid%>,valuesHolder:fileInfo.sid,rootFolderPriv:<%=rootFolderPriv%>,isRemind:0};
				  var jsonRs = tools.requestJsonRs(url,para);
				  var fileId=jsonRs.rtData;
				  
				  //存入项目文档表
				  var url1=contextPath+"/projectFileController/upload.action";
				  var json1=tools.requestJsonRs(url1,{fileId:fileId,projectId:projectId});
				  
			},
			queueComplele:function(){//队列上传成功回调函数，可有可无
				
				//调用发送消息的action
				var url2=contextPath+"/projectFileController/sendMessage.action";
                var json2=tools.requestJsonRs(url2,{projectId:projectId,sid:<%=sid%>});
                
				
				
				parent.parent.$.MsgBox.Alert_auto("附件上传成功！");
				parent.location.reload();
			},
			post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
		});
}
function checkForm(){
  var valuesHolder = $("#valuesHolder").val();
  if(!valuesHolder){
	$.MsgBox.Alert_auto("请上传附件！");
   // $.jBox.tip("请上传附件！", "error", {timeout: 1500}); 
    return false;
  }
  return true;
}
function doSave(){
	if (checkForm()){
		var para =  tools.formToJson($("#form1")) ;
		var url = "<%=contextPath%>/fileNetdisk/uploadNetdiskFile.action";
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			/* $.jBox.tip("附件上传成功！", "info", {timeout: 1800}); 
			setTimeout(function(){
				parent.location.reload();
			},1800); */
			parent.$.MsgBox.Alert_auto("附件上传成功！");
			parent.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}
</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form method="post" name="form1" id="form1" >
<input type="hidden" id='sid' name="sid" value="<%=sid %>" /> 
	<table style="width: 100%; font-size: 12px" align="center"class='TableBlock' style="height:auto;">
		<tr>
			<td class="TableHeader" width="100px" style="text-indent:15px">附件：</td>
			<td class="TableData">
				<div id="fileContainer"></div> 
				<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
				<input id="valuesHolder" name="valuesHolder" type="hidden" />
				<input id="rootFolderPriv" name="rootFolderPriv" value=<%=rootFolderPriv %> type="hidden"/>
			</td>
		</tr>
		<!-- <tr>
			<td class="TableHeader" width="100px" style="text-indent:15px">
			  <input type="checkbox" name="isRemind" id="isRemind" />
			</td>
			<td class="TableData" style="">
				是否提醒相关人员
			</td>
		</tr> -->
	</table>

</form>



</body>
</html>