<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String sid = request.getParameter("sid");
String isSendBox = request.getParameter("isSendBox");//是否为已发送
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>消息提醒邮件详情</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/email/js/email.js"></script>
<script>
var isSendBox = "<%=isSendBox%>";
function doInit(){
	getInfoById("<%=sid%>");
	
}


/* 查看详情 */
function getInfoById(id){
	setEmailReadFlagBySid(id,1);
	var jsonObj = getEmailInfoById(id);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#mailSid").val(prc.sid);
			if(prc.ifWebMail ==0){
				$("#fromUserName").html(prc.fromUserName);
			}else{
				$("#fromUserName").html(prc.fromWebMail);
			}
			/* 
			if(prc.toWebMail){
				$("#toWebmailTr").show();
				$("#toWebmail").text(prc.toWebMail);
			}
			 */
			if(prc.toWebmail){
				$("#toWebmailTr").show();
				$("#toWebmail").text(prc.toWebmail);
			}
			var userList = prc.userList;
			var copyUserList = prc.copyUserList;
			var secretUserList = prc.secretUserList;
			
			var userNameStr="";
			var copyUserStr="";
			var secretUserStr="";
			if(userList.length>0){
				for(var i=0;i<userList.length;i++){
					var readFlagStr = userList[i].readFlag;
					var emailImg = "<img src='<%=stylePath%>/imgs/message_open.gif'/>";
					if(readFlagStr =="0"){
						emailImg = "<img src='<%=stylePath%>/imgs/message.gif'/>";
					}
					if(userNameStr){
						userNameStr += ";";
					}
					userNameStr += emailImg + userList[i].userName;
				}
			}
			$("#toUserName").html(userNameStr);
			if(copyUserList.length>0){
				for(var i=0;i<copyUserList.length;i++){
					var readFlagStr = copyUserList[i].readFlag;
					var emailImg = "<img src='<%=stylePath%>/imgs/message_open.gif'/>";
					if(readFlagStr =="0"){
						emailImg = "<img src='<%=stylePath%>/imgs/message.gif'/>";
					}
					if(copyUserStr){
						copyUserStr += ";";
					}
					copyUserStr += emailImg +copyUserList[i].userName;
				}
			}
			if(secretUserList.length>0){
				for(var i=0;i<secretUserList.length;i++){
					if(secretUserStr){
						secretUserStr += ";";
					}
					secretUserStr += secretUserList[i].userName;
				}
			}
			if(copyUserStr){
				$("#copyUserName").html(copyUserStr);
				$("#copyUserNameTr").show();
			}
			if(isSendBox == "1" && secretUserList.length>0){//是否为已发送邮件
				$("#secretNameTr").show();
				$("#secretName").text(secretUserStr);
			}
			if(prc.attachMentModel.length>0){
				$("#attachTr").show();
				var attaches = prc.attachMentModel;
				for(var i=0;i<attaches.length;i++){
					var fileItem = tools.getAttachElement(attaches[i]);
					$("#attachments").append(fileItem);
				}
			}
			
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}

function deleteEmailDraftBoxFunc(){
	var text = "您确定要删除该邮件吗？";
	var ids= $("#mailSid").val();
	top.$.jBox.confirm(text,"确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/mail/delMail.action";
			var para =  {mailIds:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.jBox.tip("已将邮件成功删除！", "info", {timeout: 1800});
				CloseWindow();
			}
		}
	});
}


</script>
</head>
<body onload="doInit()" style="font-size:12px">
<div id="toolbar" style="padding-top: 5px;">
	<input type="hidden" id="mailSid" name="mailSid" value="" />
	<button type="button" class="btn btn-one" onclick="deleteEmailDraftBoxFunc();">删除</button>
	<button type="button" class="btn btn-one" onclick="sendEmailOpt('3','<%=sid%>','0');">再次编辑</button>
	<button type="button" class="btn btn-one" onclick="" style="display:none;">转发</button>
	
	<div class="btn-group" style="display:none;">
	  <button type="button" class="btn btn-one dropdown-toggle" data-toggle="dropdown">
	    标记为<span class="caret"></span>
	  </button>
	  <ul class="dropdown-menu" role="menu">
	    <li><a href="#">已读邮件</a></li>
	    <li><a href="#">未读邮件</a></li>
	    <li class="divider"></li>
	    <li><a href="#">星标邮件</a></li>
	    <li><a href="#">取消星标</a></li>
	  </ul>
	</div>
	
	<div class="btn-group" style="display:none;">
	  <button type="button" class="btn btn-one dropdown-toggle" data-toggle="dropdown">
	    移动到<span class="caret"></span>
	  </button>
	  <ul class="dropdown-menu" role="menu">
	    <li><a href="#">收件箱</a></li>
	    <li><a href="#">已发送</a></li>
	    <li><a href="#">分类1</a></li>
	    <li><a href="#">分类2</a></li>
	    <li><a href="#">分类3</a></li>
	  </ul>
	</div>
	<button style="" type="button" class="btn btn-one" onclick="CloseWindow();">关闭</button>
</div>

<div>
	<div id="read_email_head" style="padding-top:10px;">
		<table align="center" width="100%" class="TableBlock" >
			<tr >
				<td class="TableData" width="60px;">主　题：</td>
				<td class="TableData" style="text-align: left;" ><span  id="subject"></span>  </td>
			</tr>
			<tr>
				<td class="TableData">发件人：</td>
				<td class="TableData" style="text-align: left;"  >
					<span id="fromUserName"></span>
				</td>
			</tr>
			<tr>
				<td class="TableData">收件人：</td>
				<td class="TableData" style="text-align: left;"  >
					<span id="toUserName"></span>
				</td>
			</tr>
			<tr id="copyUserNameTr" style="display:none;">
				<td class="TableData" >抄送人：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="copyUserName"></span>
				</td>
			</tr>
			<tr id="secretNameTr" style="display:none;" >
				<td class="TableData">密送人：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="secretName"></span>
				</td>
			</tr>
			<tr id="toWebmailTr" style="display:none;" >
				<td class="TableData">外部收件人：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="toWebmail"></span>
				</td>
			</tr>
			
			
			<tr>
				<td class="TableData" width="60px;">时　间：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="sendTimeStr"></span>
					<!-- 2014年5月21日(星期三) 15:46 -->
				</td>
			</tr>
			<tr id="attachTr" style="display:none;">
				<td class="TableData" width="60px;">附　件：</td>
				<td class="TableData" >
					<span id="attachments"></span>
				</td>
			</tr>
		</table>
	</div>
	<hr style="width: 100%; margin-top: 5px; margin-bottom: 2px;"/>
	<div  style="width: 96%;margin-left: 20px;" >
		<div id="content"></div>
	</div>

</div>

</body>
</html>