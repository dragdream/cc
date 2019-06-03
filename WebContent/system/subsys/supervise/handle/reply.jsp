<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%

//反馈主键
  int feedBackId=TeeStringUtil.getInteger(request.getParameter("feedBackId"), 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>回复</title>
</head>
<script>
var feedBackId=<%=feedBackId%>;

function check(){
	var content=$("#content").val();
	if(content==null||content==""){
		$.MsgBox.Alert_auto("请填写回复内容！");
		return false;
	}
	return  true;
}
function save(){
	if(check()){
		var url=contextPath+"/supFeedBackReplyController/addOrUpdate.action";
		var param=tools.formToJson("#form1");
		var json=tools.requestJsonRs(url,param);
		return json.rtState;
	}
	
}
</script>
<body style="background-color: #f2f2f2">
<form id="form1">
<input type="hidden" name="feedBackId" id="feedBackId" value="<%=feedBackId%>"/>
<table class="TableBlock" style="width: 100%">
      <tr>
         <td style="text-indent:10px">内容：</td>
         <td>
           <textarea name="content" id="content" style="width: 400px;height: 100px"></textarea>
         </td>
      </tr>
   </table>
</form>
   
</body>
</html>