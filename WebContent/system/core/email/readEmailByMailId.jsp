<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String sid = request.getParameter("sid");
String mailType =  TeeStringUtil.getString(request.getParameter("mailType"), "0"); //邮件类型
String closeOptFlag =  TeeStringUtil.getString(request.getParameter("closeOptFlag"), "0"); //0-返回；1-关闭
%>
<!DOCTYPE html>
<html style="background-color:#f7f7f7;">
<head>
<title>邮件详情</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/system/core/email/js/email.js"></script>
<style>
	.modal-test{
		width: 564px;
		height: 230px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: 120px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
</style>
<script>
var mailType = "<%=mailType%>";
var closeOptFlag = "<%=closeOptFlag%>";
var mailTitle;
function doInit(){
	getInfoById("<%=sid%>");
	if(mailType == 5){//删除
		$("#destroyMails").show();
	}else{
		$("#reply").show();
		$("#replyAll").show();
		$("#deleteOpt").show();
		
	}
	if(parent.window.location==window.location){//显示 关闭
		$("#closeOptDiv").show();
	}else{//显示 返回
		$("#returnOptDiv").show();
	}
	
	$("#addFav").addFav(mailTitle,"/system/core/email/readEmailByMailId.jsp?sid=<%=sid%>");
	getEmailBoxList();
}


/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/emailController/readEmailDetailById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState){
		if(jsonObj.rtData==null){
			//alert("邮件已被删除或撤回");
			messageMsg("邮件已被删除或撤回","body","info");
		}else{
			var prc = jsonObj.rtData;
			if (prc && prc.sid) {
				mailTitle = prc.subject;
				bindJsonObj2Cntrl(prc);
				if(prc.ifWebMail ==0){
					$("#fromUserName").text(prc.fromUserName);
				}else{
					$("#fromUserName").text(prc.fromWebMail);
				}
				if(prc.toWebMail){
					$("#toWebmailTr").show();
					$("#toWebmail").text(prc.toWebMail);
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
						secretUserStr += emailImg + secretUserList[i].userName;
					}
				}
				if(copyUserStr){
					$("#copyUserName").html(copyUserStr);
					$("#copyUserNameTr").show();
				}
				//$("#secretName").text(secretUserStr);
				//alert(prc.attachMentModel.length);
				if(prc.attachMentModel.length>0){
					$("#attachTr").show();
					var attaches = prc.attachMentModel;
					for(var i=0;i<attaches.length;i++){
						var fileItem = tools.getAttachElement(attaches[i]);
						$("#attachments").append(fileItem);
					}
				}
			}
			
			
			
		}
		
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	} 
}


/**
 * 删除邮件（假删除）
 */
function delEmailByIdFunc(ids){
	
	$.MsgBox.Confirm ("提示", "确定删除该邮件？", function(){
		var url = contextPath + "/mail/delMail.action";
		var para = {mailIds:ids};
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
			//$.jBox.tip("删除成功！", "info", {timeout: 1800});
			$.MsgBox.Alert_auto("删除成功！");
				//跳转至删除管理页面
				window.parent.returnPageFunc(1);	
		}   
	  });
	
}

/**
 * 彻底删除
 */
function deleteObjFunc1(ids){
	$.MsgBox.Confirm ("提示", "彻底删除后邮件将无法恢复，您确定要删除吗？", function(){
		var url = contextPath + "/mail/destroyMails.action";
		var para = {mailIds:ids};
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！");
				//跳转至删除管理页面
			window.parent.returnPageFunc(5);
			//$.jBox.tip("删除成功！", "info", {timeout: 1800});
			
		}   
	  });
}

function moveMailFunc(mailBoxSId){
	
	$.MsgBox.Confirm ("提示", "是否确认移动邮件？", function(){
		var url = contextPath + "/mail/moveMail.action";
		var para = {mailIds:"<%=sid%>",boxId:mailBoxSId};
		var jsonRs = tools.requestJsonRs(url, para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("操作成功！");
				try{
					refreshEmailMainFunc();
				}catch(e){}
			
			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	
	});
	//top.jBox.tip("操作成功","success");
	
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
<body onload="doInit()" style="font-size:12px;background-color:#f7f7f7;padding:10px;height:100%" id="body">
<!-- 邮件主题 -->
<div id="subject" style="font-size:14px;font-weight:bold;margin-bottom:5px;margin-left: 5px"></div>
<div style="background:white;border:1px solid #dbe2e8;padding:10px;margin-bottom:10px">
<!-- 按钮栏 -->
	<div id="toolbar" class = "clearfix" style="margin-bottom: 10px;margin-right: 10px;" >
	<!-- <div class="fl" style="position:static;"></div> -->
	<div class = "right fr">
	<input type="button" class="btn-win-white" id="reply" style="display:none;"  onclick="sendEmailOpt('0','<%=sid%>','0');" value="回复"/>
	<input type="button" class="btn-win-white" id="replyAll" style="display:none;"   onclick="sendEmailOpt('1','<%=sid%>','0');" value="回复全部"/>
	<input type="button" class="btn-win-white" onclick="sendEmailOpt('2','<%=sid%>','0');" value="转发"/>
	<input type="button" class="btn-del-red" id="deleteOpt" style="display:none;"   onclick="delEmailByIdFunc('<%=sid%>')" value="删除"/>
	<input type="button" class="btn-del-red" id="destroyMails" style="display:none;"   onclick ="deleteObjFunc1('<%=sid%>')" value="彻底删除"/>
	&nbsp;&nbsp;
	<img src="<%=systemImagePath %>/favorite_click.png" class="favStyle" title="添加收藏夹" id="addFav"/>
	
	<div class="btn-group fl" style="display:none;">
	  <button type="button" class="btn-win-white btn-menu">
	    标记为<span class="caret-down"></span>
	  </button>
	  <ul class="btn-content" >
	    <li><a href="#">已读邮件</a></li>
	    <li><a href="#">未读邮件</a></li>
	    <li class="divider"></li>
	    <li><a href="#">星标邮件</a></li>
	    <li><a href="#">取消星标</a></li>
	  </ul>
	</div>
	
	<div class="btn-group fl">
	  <button type="button" class="btn-win-white btn-menu" >
	    移动到<span class="caret-down"></span>
	  </button>
	  <ul class="btn-content" id="mailBoxList">
	  </ul>
	</div>
	<input id="returnOptDiv" style="display:none;" type="button" class="btn-win-white" onclick="history.go(-1)" value="返回"/>
	<input id="closeOptDiv" style="display:none;" type="button" class="btn-win-white" onclick="CloseWindow();" value="关闭"/>
	<div style="clear:none;"></div>
    </div>
</div>
<!-- 详情 -->	
	<div>
		<table align="center" width="100%" class="TableBlock" style="background:#f1f5f4;border:1px solid #dbe2e8">
			<tr>
				<td class="TableData TableBG" style="width:120px;padding-left:10px">发件人：</td>
				<td class="TableData" style="text-align: left;"  >
					<span id="fromUserName"></span>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0);"  id="hasReceiver"  onclick="showrecTr();">查看收件人</a>
				</td>
			</tr>
			<tr style="display: none" id="recTr">
				<td class="TableData TableBG" style="width:120px;padding-left:10px">收件人：</td>
				<td class="TableData" style="text-align: left;"  >
					<span id="toUserName"></span>
				</td>
			</tr>
			<tr id="copyUserNameTr" style="display:none;;padding-left:10px">
				<td class="TableData TableBG"  style="width:120px;padding-left:10px">抄送人：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="copyUserName"></span>
				</td>
			</tr>
			<tr style="display:none;" >
				<td class="TableData TableBG" style="width:120px;padding-left:10px">密送人：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="secretName"></span>
				</td>
			</tr>
			<tr id="toWebmailTr" style="display:none;" >
				<td class="TableData TableBG" style="width:120px;padding-left:10px">外部收件人：</td>
				<td class="TableData" style="text-align: left;" >
					<span id="toWebmail"></span>
				</td>
			</tr>
			<tr>
				<td class=" TableBG" width="60px;" style="width:120px;padding-left:10px">时　间：</td>
				<td class="" style="text-align: left;" >
					<span id="sendTimeStr"></span>
					<!-- 2014年5月21日(星期三) 15:46 -->
				</td>
			</tr>
			<tr id="attachTr" style="display:none;">
				<td class="TableData TableBG" width="60px;" style="width:120px;padding-left:10px">附　件：</td>
				<td class="TableData" >
					<span id="attachments"></span>
				</td>
			</tr>
		</table>
		
		<div id="content" style="padding: 10px" ></div>
	</div>
</div>

<!-- Modal -->
 <div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			新建邮件箱
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
			<li class="clearfix">
				<span>邮件箱名称:</span>
				<input type="text" id="boxInput" name="boxInput" />
			</li>
			<li class="clearfix">
				<span>序号:</span>
				<input type="text"  id="boxNo" name="boxNo"/>
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="submitNewFolder();" value = '保存'/>
	</div>
</div>
</body>
</html>