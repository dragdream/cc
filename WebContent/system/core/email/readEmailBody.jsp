<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String sid = request.getParameter("sid");
String isSendBox = request.getParameter("isSendBox");//是否为已发送
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f7f7f7;">
<head>
<title>邮件详情</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/system/core/email/js/email.js"></script>
<script>
var sid='<%=sid %>';
var datagrid ;
var isSendBox = "<%=isSendBox%>";
function doInit(){
	getInfoById("<%=sid%>");
	//parent.frames["frame0"].src = "<%=contextPath%>/system/core/email/sendEmailManage.jsp";
	
}


/* 查看详情 */
function getInfoById(id){
	var jsonObj = getEmailDetailByMailBodyId(id);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			if(prc.ifWebMail ==0){
				$("#fromUserName").text(prc.fromUserName);
			}else{
				$("#fromUserName").text(prc.fromWebMail);
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
						userNameStr += ",";
					}
					userNameStr += emailImg +userList[i].userName;
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
						copyUserStr += ",";
					}
					copyUserStr += emailImg + copyUserList[i].userName;
				}
			}
			if(secretUserList.length>0){
				for(var i=0;i<secretUserList.length;i++){
					var readFlagStr = secretUserList[i].readFlag;
					var emailImg = "<img src='<%=stylePath%>/imgs/message_open.gif'/>";
					if(readFlagStr =="0"){
						emailImg = "<img src='<%=stylePath%>/imgs/message.gif'/>";
					}
					if(secretUserStr){
						secretUserStr += ",";
					}
					secretUserStr += emailImg +secretUserList[i].userName;
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

function deleteEmailDraftBoxFunc(ids,optFlag){
	var text = "删除后草稿将无法恢复，您确定要删除吗？";
	var returnType = 3;
	if(optFlag==2){
		text = "删除后邮件将无法恢复，您确定要删除吗？";
		returnType = 4;
	}
	
	 $.MsgBox.Confirm ("提示", text, function(){
		 var url = contextPath + "/mail/delSingleMailBody.action";
			var para =  {id:ids,value:optFlag};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				//$.jBox.tip("已将邮件成功删除！", "info", {timeout: 1800});
				$.MsgBox.Alert_auto("已将邮件成功删除！");
			    window.parent.returnPageFunc(returnType);
				
			} 
	  });
}

//查看阅读详情
function viewInfos(){
	//获取邮件主题的值
	var subject=$("#subject").html();
	openFullWindow(contextPath+"/system/core/email/viewInfo.jsp?sid="+sid+"&&subject="+subject, "邮件查阅详情");
	
}


//显示隐藏收件人
function  showrecTr(){
	if($("#hasReceiver").text() == "查看收件人"){
		$("#recTr").show();
		$("#hasReceiver").text("隐藏收件人");
	}else{
		$("#recTr").hide();
		$("#hasReceiver").text("查看收件人");
	}
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;background-color:#f7f7f7;padding:10px;height:100%">
<!-- 邮件主题 -->
<div id="subject" style="font-size:14px;font-weight:bold;margin-bottom:5px;margin-left: 5px"></div>
<div style="background:white;border:1px solid #dbe2e8;padding:10px;margin-bottom:10px">
<!-- 按钮栏 -->
	<div id="toolbar" class = "clearfix" style="margin-bottom: 10px;margin-right: 10px;" >
	
	<div class = "right fr clearfix">
	<input type="button" class="btn-del-red" onclick="deleteEmailDraftBoxFunc('<%=sid%>','2');" value="删除"/>
	<input type="button" class="btn-win-white" onclick="sendEmailOpt('3','<%=sid%>','2');" value="再次编辑"/>
	<input type="button" class="btn-win-white" onclick="" style="display:none;" value="转发"/>
	
	<div class="btn-group" style="display:none;">
	  <button type="button" class="btn-win-white btn-menu" >
	    标记为<span class="caret-down"></span>
	  </button>
	  <ul class="btn-content">
	    <li><a href="#">已读邮件</a></li>
	    <li><a href="#">未读邮件</a></li>
	    <li class="divider"></li>
	    <li><a href="#">星标邮件</a></li>
	    <li><a href="#">取消星标</a></li>
	  </ul>
	</div>
	
	<div class="btn-group" style="display:none;">
	  <button type="button" class="btn-win-white btn-menu">
	    移动到<span class="caret-down"></span>
	  </button>
	  <ul class="btn-content" >
	    <li><a href="#">收件箱</a></li>
	    <li><a href="#">已发送</a></li>
	    <li><a href="#">分类1</a></li>
	    <li><a href="#">分类2</a></li>
	    <li><a href="#">分类3</a></li>
	  </ul>
	</div>
	<input style="" type="button" class="btn-win-white" onclick="history.back()" value="返回"/>
</div>
</div>
<!-- 详情 -->	
	<div>
		<table align="center" width="100%" class="TableBlock" style="background:#f1f5f4;border:1px solid #dbe2e8" >
			<tr>
				<td class="TableData" style="width:120px;padding-left:10px">发件人：</td>
				<td class="TableData" style="text-align: left;"  >
					<span id="fromUserName"></span>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);"  id="hasReceiver"  onclick="showrecTr();">查看收件人</a>
				</td>
			</tr>
			<tr id="recTr" style="display: none;">
				<td class="TableData" style="width:120px;padding-left:10px">收件人：</td>
				<td class="TableData" style="text-align: left;"  >
					<span id="toUserName"></span>
					<a href="#"  onclick="viewInfos();">查看邮件详情</a>
				</td>
			</tr>
			<tr id="copyUserNameTr" style="display:none;">
				<td class="TableData"  style="width:120px;padding-left:10px">抄送人：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="copyUserName"></span>
				</td>
			</tr>
			<tr id="secretNameTr" style="display:none;" >
				<td class="TableData" style="width:120px;padding-left:10px">密送人：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="secretName"></span>
				</td>
			</tr>
			<tr id="toWebmailTr" style="display:none;" >
				<td class="TableData" style="width:120px;padding-left:10px">外部收件人：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="toWebmail"></span>
				</td>
			</tr>
			
			
			<tr>
				<td class="TableData" width="60px;" style="width:120px;padding-left:10px">时　间：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="sendTimeStr"></span>
					<!-- 2014年5月21日(星期三) 15:46 -->
				</td>
			</tr>
			<tr id="attachTr" style="display:none;">
				<td class="TableData" width="60px;" style="width:120px;padding-left:10px">附　件：</td>
				<td class="TableData" >
					<span id="attachments"></span>
				</td>
			</tr>
		</table>
		
		<div id="content" style="padding: 10px" ></div>
	</div>
</div>
</body>
</html>