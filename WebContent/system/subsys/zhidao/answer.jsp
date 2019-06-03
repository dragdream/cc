<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   int questionSid=TeeStringUtil.getInteger(request.getParameter("questionSid"),0);
   String model=TeeAttachmentModelKeys.zhiDaoAnswer;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我来回答</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript">
var uEditorObj;//uEditor编辑器
function doInit(){
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	     
	
		//多附件快速上传
		swfUploadObj = new TeeSWFUpload({
			fileContainer:"fileContainer2",//文件列表容器
			uploadHolder:"uploadHolder2",//上传按钮放置容器
			valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
			quickUpload:true,//快速上传
			showUploadBtn:false,//不显示上传按钮
			queueComplele:function(){//队列上传成功回调函数，可有可无
				
			},
			renderFiles:true,//渲染附件
			post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
		});
	
	
	});
	
}


function checkForm(){
	var content=uEditorObj.getContent();
	if(content==null||content==""||content=="null"){
		$.MsgBox.Alert_auto("请填写内容！");
		return false;
	}
    return true;
}

function save(){
	if(checkForm()){
		var param=tools.formToJson($("#form1"));
		var url=contextPath+"/zhiDaoAnswerController/add.action";
		var json=tools.requestJsonRs(url,param);
		return json;
	}
	
}
</script>
</head>
<body  onload="doInit();" style="background-color: #f2f2f2;padding-right: 10px;padding-top: 10px">
<form id="form1" name="form1"  enctype="multipart/form-data" method="post">
  <input type="hidden" name="questionSid" id="questionSid" value="<%=questionSid %>"/>
  <table class="TableBlock" style="width: 100%">
    <tr>
       <td style="text-indent:10px;width: 10%">内容：</td>
       <td>
          <textarea rows="15" cols="65" name="content" id="content"></textarea>
       </td>
    </tr>
    <tr>
       <td style="text-indent:10px;width: 10%">附件：</td>
       <td>
            <div id="fileContainer2"></div>
			<a id="uploadHolder2" class="add_swfupload">
				<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
			</a>
			<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
       </td>
    </tr>
  </table>
</form>
</body>
</html>