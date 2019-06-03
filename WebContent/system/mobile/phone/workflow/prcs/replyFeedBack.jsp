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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/system/mobile/mui/header.jsp" %>
<script src="<%=contextPath %>/common/js/tools2.0.js"></script>
<title>回复会签意见</title>
<script type="text/javascript">

function save(){
	if(checkForm()){
		var param=tools.formToJson($("#form1"));
		var url=contextPath+"/feedBack/addReplyFeedBack.action";
		var json=tools.requestJsonRs(url,param);
		if(json.rtState){
			parent.loadFeedBack();
			parent.layer.closeAll(); 
			
		}else{
			Alert("回复失败！");
		}
	}
}

function checkForm(){
	var content=$("#content").val();
	if(content==null||content==""){
		Alert("请填写回复内容！");
		return false;
	}
	return true;
}


//取消
function cancel(){
	 parent.layer.closeAll(); 
}
</script>
</head>
<body style="width: 100%">
<header class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" onclick="parent.layer.closeAll(); "></span>
			<h1 class="mui-title">回复会签意见</h1>
		</header>
		<div class="mui-content">
			 <form id="form1" >
			      <input type="hidden" name="replyId" id="replyId" value="<%=replyId%>"/>
			      <input type="hidden" name="frpSid" id="frpSid" value="<%=frpSid%>"/>
			      <table class="TableBlock" width="100%">
			         <tr>
			            <td>
			               <textarea rows="10" cols="61" id="content" name="content"></textarea>
			            </td>
			         </tr>
			         <tr>
			            <td style="text-align: center;">
			               <input type="button" style=""  value="保存" onclick="save();"/>
			               &nbsp;&nbsp;
			               <input type="button"  value="取消" onclick="cancel();"/>
			            </td>
			         </tr>
			      </table>
			  </form>
	  </div>
</body>
</html>