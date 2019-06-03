<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   int replyId=TeeStringUtil.getInteger(request.getParameter("replyId"),0);
   int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<title>回复会签意见</title>
<script type="text/javascript">
var uEditorObj;//uEditor编辑器
function doInit(){
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	});
}


function save(){
	if(checkForm()){
		var param=tools.formToJson($("#form1"));
		var url=contextPath+"/feedBack/addReplyFeedBack.action";
		var json=tools.requestJsonRs(url,param);
		return json;
	}
}

function checkForm(){
	var content=uEditorObj.getContent();
	if(content==null||uEditorObj.getContent()==""){
		$.MsgBox.Alert_auto("请填写回复内容！");
		return false;
	}
	return true;
}
</script>
</head>
<body onload="doInit();" style="overflow: hidden;">
   <form id="form1" >
       <input type="hidden" name="replyId" id="replyId" value="<%=replyId%>"/>
       <input type="hidden" name="frpSid" id="frpSid" value="<%=frpSid%>"/>
       <table class="TableBlock">
          <tr>
             <td>
                <textarea rows="10" cols="45" id="content" name="content"></textarea>
             </td>
          </tr>
       </table>
   </form>
</body>
</html>