<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

String model = TeeAttachmentModelKeys.EMAIL;
String sid =  TeeStringUtil.getString(request.getParameter("sid"), "0"); 
String optFlag =  TeeStringUtil.getString(request.getParameter("optFlag"), "0"); // 0-回复;1-回复全部；2-转发；3-编辑
String isEditFlag =  TeeStringUtil.getString(request.getParameter("isEditFlag"), "0"); //是否为编辑草稿，0-否；1-是;2-已发邮件再次编辑发送
String toUsers = TeeStringUtil.getString(request.getParameter("toUsers"), "0");
String noteId = TeeStringUtil.getString(request.getParameter("noteId"), "0");
int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
int view = TeeStringUtil.getInteger(request.getParameter("view"),0);
String dbSid = "0";
if("1".equals(isEditFlag)){
	dbSid = sid;
}
String cloneAttachFlag = "0";
if("2".equals(optFlag) || "2".equals(isEditFlag)){
	cloneAttachFlag = "1";
}


%>
<!DOCTYPE html>
<html >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/email/js/email.js?v=2018073001"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>

<script>
var sid = "<%=sid%>";
var optFlag = "<%=optFlag%>";
var isEditFlag = "<%=isEditFlag%>";
var ckEditorObj;
var uEditorObj;//uEditor编辑器
var toUsers = "<%=toUsers%>";
var noteId = "<%=noteId%>";
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var view = <%=view%>;

var loginUserId=<%=person.getUuid() %>;
function doInit(){
	uEditorObj = UE.getEditor('content');//获取编辑器对象
	uEditorObj.ready(function(){//初始化方法
		uEditorObj.setHeight(200);
	    //设置默认字体大小
		getSysCodeByParentCodeNo("EMAIL_JJCD","emailLevel");
		
		//如果是工作流转公告，则先获取流程数据，并加载到
		if(runId!=0 || frpSid!=0){
			var url = contextPath+"/flowRun/getFlowRunFormStream.action";
			var json = tools.requestJsonRs(url,{runId:runId,view:view,frpSid:frpSid});
			if(json.rtState){
				var form = json.rtData;
				//$("#content").val(form);
				uEditorObj.setContent(form);
			}
		}
		
		if(noteId!="0"){
			var json = tools.requestJsonRs(contextPath+"/noteManage/getById.action",{sid:noteId});
			$("#content").val(json.rtData.content.replace(/\r\n/gi,"<br/>"));
		}
		showPhoneSmsForModule("phoneSmsSpan","019",'<%=person.getUuid()%>');
		
		/* ckEditorObj = CKEDITOR.replace('content',{
			width : 'auto',
			height:300
		}); */
		doInitUpload();
		if(sid>0){
			getEmailInfoByIdFunc(sid);
		}
		
		if(toUsers!="0"){
			var json = tools.requestJsonRs(contextPath+"/personManager/getPersonListByUuids.action",{uuid:toUsers});
			var list = json.rtData;
			var ids = [];
			var names = [];
			for(var i=0;i<list.length;i++){
				ids.push(list[i].uuid);
				names.push(list[i].userName);
			}
			$("#userListIds").val(ids.join(""));
			$("#userListNames").val(names.join(""));
		}
	});
	
	
	
	changeType();
	
}
/**
 * 初始化附件上传
 */
function doInitUpload(){
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
		},
		uploadSuccess:function(files){//上传成功
		    if($("#subject").val()==""||$("#subject").val()==null){
		    	var fileName=files.fileName;
		    	$("#subject").val(fileName.substring(0, fileName.lastIndexOf(".")));
		    }
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
}



/**
 * 查看详情
 */
function getEmailInfoByIdFunc(sid){
	var jsonObj = "";
	if(isEditFlag == 1 || isEditFlag == 2){//根据mailBody 的sid 
		jsonObj = getEmailDetailByMailBodyId(sid);
	}else{//根据mail 的sid 
		jsonObj = getEmailInfoById(sid);
	}

	if (jsonObj.rtState){
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			//alert(prc.smsRemind);
			bindJsonObj2Cntrl(prc);
			var userList = prc.userList;
			var copyUserList = prc.copyUserList;
			var secretUserList = prc.secretUserList;
			
			var pubType=prc.pubType;// 发布范围  0=指定人员  1=所有人员
			
			if(prc.receipt ==1){
				$("input[name='receipt']").attr("checked","checked"); 
			}
			
			var fromUserName = "";
			
			if(prc.ifWebMail ==0){//是否来自外部邮箱
				fromUserName = prc.fromUserName;
			}else{
				fromUserName = prc.fromWebMail;
			}
			var toUserIdStr1="";
			var toUserNameStr1="";
			
			var toUserIdStr="";
			var toUserNameStr="";
			
			var copyUserIdStr="";
			var copyUserStr="";
			
			var fromUserIdStr = prc.fromUserId;
			if(userList.length>0){//收件人
				for(var i=0;i<userList.length;i++){
					if(toUserNameStr){
						toUserNameStr += ",";
						toUserIdStr += ",";
					}
					if(toUserNameStr1){
						toUserIdStr1 +=",";
						toUserNameStr1 += ",";
					}
					
					
					toUserIdStr += userList[i].sid;
					toUserNameStr += userList[i].userName;
					
					var toUserIdStrTemp = userList[i].sid;
	
					if(toUserIdStrTemp && toUserIdStrTemp!= fromUserIdStr && toUserIdStrTemp!=loginUserId){//收件人不是发件人   //并且收件人不是当前登陆人（回复全部要除了自己） 
						toUserIdStr1 += toUserIdStrTemp;
						toUserNameStr1 += userList[i].userName;
					}
				}
			}
			if(copyUserList.length>0){//抄送
				for(var i=0;i<copyUserList.length;i++){
					if(copyUserStr){
						copyUserIdStr += ",";
						copyUserStr += ",";
					}
					copyUserIdStr += copyUserList[i].sid;
					copyUserStr += copyUserList[i].userName;
				}
			}
			var secretUserIdStr="";
			var secretUserNameStr = "";
			if(secretUserList.length>0){//密送
				for(var i=0;i<secretUserList.length;i++){
					if(secretUserIdStr){
						secretUserIdStr += ",";
						secretUserNameStr += ",";
					}
					secretUserIdStr += secretUserList[i].sid;
					secretUserNameStr += secretUserList[i].userName;
				}
			}
			
			if(prc.toWebMail){//外部邮箱发送人
				toUserNameStr = prc.toWebMail;
			}
			if(copyUserStr){//抄送
				$("#copyUserName").show();
			}
			var subject = prc.subject;
			var htmlStr ="";
			if(isEditFlag == 1 || isEditFlag == 2){//1-草稿（编辑邮件）;2-草稿（再次编辑发送邮件）
			    if(pubType==0){//指定人员
			    	$("#sendUserTr").show();
			    	$("#userListIds").val(toUserIdStr);
					$("#userListNames").val(toUserNameStr);
			    }else{
			    	$("#sendUserTr").hide();
			    	
			    }
				
				
				if(copyUserList.length){
					showVariousMail(1);
					$("#copyUserListIds").val(copyUserIdStr);
					$("#copyUserListNames").val(copyUserStr);
				}
				if(secretUserList.length>0){
					showVariousMail(2);
					$("#secretUserListIds").val(secretUserIdStr);
					$("#secretUserListNames").val(secretUserNameStr);
				}
				if(prc.toWebmail){
					showVariousMail(3);
					$("#externalInput").val(prc.toWebmail);
				}
				
				htmlStr = prc.content;
				
				if(prc.attachMentModel.length>0){
					var dbAttachSid = "";
					$("#attachTr").show();
					var attaches = prc.attachMentModel;
					for(var i=0;i<attaches.length;i++){
						//alert(attaches[i].sid);
						var fileItem = tools.getAttachElement(attaches[i]);
						$("#attachments").append(fileItem);
						if(dbAttachSid){
							dbAttachSid += ",";
						}
						dbAttachSid += attaches[i].sid;
					}
					$("#dbAttachSid").val(dbAttachSid);
				}
				
			}else{//回复、回复全部、转发功能（创建新邮件）
				if(optFlag =="0"){//0-回复;
					//showVariousMail(1);
					subject = "回复："+ prc.subject;
					
					//回复  设置为指定人员
					$("#pubType").val(0);
					
					$("#userListIds").val(prc.fromUserId);
					$("#userListNames").val(prc.fromUserName);
					
					
					if(prc.ifWebMail==1){
						var regexp = /&lt;.*&gt;/gi;
						var match = regexp.exec(prc.fromWebMail);
						if(match!=null && match && match.length!=0){
							$("#externalInput").val(match[0].replace("&lt;","").replace("&gt;",""));
						}
						$("#externalMail").show();
						$("#externalHeader").text("删除外部邮箱");
					}
				}else if(optFlag =="1"){//1-回复全部；
					if(copyUserList.length){
						showVariousMail(1);
					}
					var fromUserNameStr = prc.fromUserName;
					var tempStr = "";
					if(toUserIdStr1 && fromUserIdStr){
						tempStr = ",";
						tempStr = ",";
					}
					var userListIds = fromUserIdStr + tempStr +  toUserIdStr1;
					var userListNames = fromUserNameStr + tempStr+ toUserNameStr1;
					
					if(pubType==0){//指定人员
						$("#sendUserTr").show();
						$("#userListIds").val(userListIds);
						$("#userListNames").val(userListNames);
					}else{//全部人员
						$("#sendUserTr").hide();
					}
					
					
					$("#copyUserListIds").val(copyUserIdStr);
					$("#copyUserListNames").val(copyUserStr);
					
					if(prc.ifWebMail==1){
						var regexp = /&lt;.*&gt;/gi;
						var match = regexp.exec(prc.fromWebMail);
						if(match!=null && match && match.length!=0){
							$("#externalInput").val(match[0].replace("&lt;","").replace("&gt;",""));
						}
						$("#externalMail").show();
						$("#externalHeader").text("删除外部邮箱");
					}
				}else if(optFlag =="2"){//2-转发；
					subject = "转发："+ prc.subject;

					//转发  设置为指定人员
					$("#pubType").val(0);
				    
					if(prc.attachMentModel.length>0){
						var dbAttachSid = "";
						$("#attachTr").show();
						var attaches = prc.attachMentModel;
						for(var i=0;i<attaches.length;i++){
							//alert(attaches[i].sid);
							var fileItem = tools.getAttachElement(attaches[i]);
							$("#attachments").append(fileItem);
							if(dbAttachSid){
								dbAttachSid += ",";
							}
							dbAttachSid += attaches[i].sid;
						}
						$("#dbAttachSid").val(dbAttachSid);
					}
				}
				htmlStr = "<p>"
							+ "<p>"
							+ "<div style='padding-top: 2px; padding-right: 0px; padding-bottom: 2px; padding-left: 0px; font-family: Arial Narrow; font-size: 14px;'>------------------&nbsp;原始邮件&nbsp;------------------</div>"
							+"<div style='background:white;border:1px solid #dbe2e8;font-size: 14px;'>"
							+"<table style='background:#f1f5f4;border:1px solid #dbe2e8' align='center' width='100%'>"
							+ 	"<tr><td style='width:120px;padding:5px;border-bottom:1px solid #dbe2e8;font-size: 12px;'>发&nbsp;&nbsp;件&nbsp;&nbsp;人：</td><td style='border-bottom:1px solid #dbe2e8;font-size: 12px;'>" + fromUserName +  "</td></tr>" 
							+ 	"<tr><td style='width:120px;padding:5px;border-bottom:1px solid #dbe2e8;font-size: 12px;'>发送时间：</td><td style='border-bottom:1px solid #dbe2e8;font-size: 12px;'>" + prc.sendTimeStr + "</td></tr>" 
							+ 	"<tr><td style='width:120px;padding:5px;border-bottom:1px solid #dbe2e8;font-size: 12px;'>收&nbsp;&nbsp;件&nbsp;&nbsp;人：</td><td style='border-bottom:1px solid #dbe2e8;font-size: 12px;'>" + toUserNameStr +  "</td></tr>" 
							+ 	"<tr id='copyUserName' style='display:none;'><td style='width:120px;padding:5px;border-bottom:1px solid #dbe2e8;font-size:12px;'>抄&nbsp;&nbsp;&nbsp;&nbsp;送：</td><td style='border-bottom:1px solid #dbe2e8;font-size: 12px;'>" + copyUserStr + "</td></tr>" 
							+ 	"<tr><td style='width:120px;padding:5px;font-size: 12px;'>主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</td><td style='font-size: 12px;'>" + prc.subject + "</td></tr>" 
							+ "</table>"
							+ "<div style='padding:10px;font-size: 14px;'>" + prc.content + "</div>"
							+"</div>";
							;
			}
			
			
			$("#subject").val(subject);
			
			//ckEditorObj.setData(htmlStr);
			uEditorObj.setContent(htmlStr);
		}else{
			$.MsgBox.Alert_auto("邮件未找到！");
		}
		
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}


function showVariousMail(id){
	if(id==1){//添加抄送
		if($("#copyHeader").text() == "添加抄送"){
			$("#copyMail").show();
			$("#copyHeader").text("删除抄送");
		}else{
			$("#copyMail").hide();
			$("#copyHeader").text("添加抄送");
		}
	}else if(id==2){//添加密送
		if($("#secretHeader").text() == "添加密送"){
			$("#secretMail").show();
			$("#secretHeader").text("删除密送");
		}else{
			$("#secretMail").hide();
			$("#secretHeader").text("添加密送");
		}
	}else if(id==3){//添加外部收件人
		if($("#externalHeader").text() == "添加外部邮箱"){
			$("#externalMail").show();
			$("#externalHeader").text("删除外部邮箱");
		}else{
			$("#externalMail").hide();
			$("#externalHeader").text("添加外部邮箱");
		}
		
	}
}

/**
 * 发送邮件
 * type 0-草稿；1-发送
 */
function sendEmail(type){
	
	//存草稿
	if(type==0){
		if(!$("#subject").val()){
			$("#subject").val("无标题"+getFormatDateStr(null,"yyyy-MM-dd HH:mm:ss"));
		}
	}
	
	if(checkForm(type)){
		
		if($("#copyHeader").text() =="添加抄送"){
			$("#copyUserListIds").val("");
			$("#copyUserListNames").val("");
		}
		if($("#secretHeader").text() == "添加密送"){
			$("#secretUserListIds").val("");
			$("#secretUserListNames").val("");
		}
		if($("#externalHeader").text() == "添加外部邮箱"){
			$("#externalInput").val("");
		}
		//var contentStr = ckEditorObj.getData();
		var contentStr=uEditorObj.getContent();
		//$.jBox.tip("邮件发送中","loading");
		//$.MsgBox.Alert("提示", "邮件发送中");
		var text = "";
		var url = "<%=contextPath%>/emailController/addOrUpdateMail.action?type="+type+"&webmailCount="+text;
		var para =  tools.formToJson($("#form1")) ;
		
		//alert(para["receipt"]);
		
		para["cloneAttachFlag"] = "<%=cloneAttachFlag%>";
		para["content"] = contentStr;
		if(isEditFlag=="1"){
			para['sid']=sid;
		}else{
			para['sid']="0";
		}
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			var sid = data.sid;
			//top.$.jBox.tip(jsonRs.rtMsg ,'info' , {timeout:2000});
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
				if(runId!=0 || frpSid!=0){//如果是工作流转发，则关闭窗体
	    			CloseWindow();
	    		}				
				if(type == "1"){
					sendPhoneSmsFunc();
					location.href = contextPath + "/system/core/email/emailReceiveManage.jsp";
				}else{
					sendEmailOpt(3,sid,1);//跳转
				}
				//刷新左侧列表邮件主界面
				refreshEmailMainFunc();

			
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
		
	}
}

//发送短信
function sendPhoneSmsFunc(){
	var personName ='<%=person.getUserName()%>';
	var smsContent="您有一封新邮件，来自" + personName + " 的邮件！主题：" + $("#subject").val();
	var toSmsUserIdStr =  $("#userListIds").val();
	if($("#copyUserListIds").val()){
		toSmsUserIdStr += "," + $("#copyUserListIds").val();
	}
	if($("#secretUserListIds").val()){
		toSmsUserIdStr +=  "," + $("#secretUserListIds").val();
	}
	sendPhoneSms(toSmsUserIdStr,smsContent,'');
}

//修改收件人规则
function changeType(){
	var pubType=$("#pubType").val();
	if(pubType==0){
		$("#sendUserTr").show();
	}else{
		$("#sendUserTr").hide();
	}
	
}

</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_xieyoujian.png">
		<span class="title">写邮件</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="sendEmail(1)" value="发送"/>
		<input type="button" class="btn-win-white fl" onclick="sendEmail(0)" value="存草稿"/>
		<input type="button" class="btn-win-white fl" onclick="history.go(-1)" value="返回"/>
    </div>
</div>

<div class="base_layout_center">
	<br/>
	<form role="form" id= "form1" name = "form1" enctype="multipart/form-data" method="post">
		<input type="hidden" id="sid" name="sid" value="<%=dbSid%>">
		<table style="width:100%" class="TableBlock_page">
		
		    <tr>
		        <td width="80px;" align=right>收件人范围：</td>
		        <td>
		           <select id="pubType" name="pubType" onchange="changeType()">
		              <option value="0">指定人员</option>
		              <option value="1">所有人员</option>
		           </select>
		           
		           
					<span style="padding-left: 20px;">
						<a href="javascript:void(0);" title="什么是抄送：同时将这一封邮件发送给其他联系人。" id="copyHeader"  onclick="showVariousMail(1)">添加抄送</a>&nbsp;-&nbsp;
						<a href="javascript:void(0);" title="什么是密送：同时将这一封邮件发送给其他联系人，但收件人及抄送人不会看到密送人。" id="secretHeader" onclick="showVariousMail(2)">添加密送</a>&nbsp;|&nbsp;
						<a href="javascript:void(0);" title="什么是外部邮箱：本系统之外的收件人邮箱地址，用分号隔开结尾（如abc@163.com;）。" id="externalHeader" onclick="showVariousMail(3)">添加外部邮箱</a>
					</span>
		        </td>
		    </tr>
			<tr id="sendUserTr" style="display: none">
				<td width="80px;" align=right>收件人：</td>
				<td>
					<input name="userListIds" id="userListIds" type="hidden"/>
					<textarea class="BigTextarea readonly" id="userListNames" name="userListNames" style="height:45px;width:550px"  readonly></textarea>
					<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['userListIds','userListNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('userListIds','userListNames')" value="清空"/>
					</span>
					
					<!-- <a class="orgAdd" onclick="selectUser(['userListIds','userListNames'],'14')" href="javascript:void(0);">选择</a>&nbsp;&nbsp;
					<a class="orgClear" onclick="clearData('userListIds','userListNames')" href="javascript:void(0);">清空</a> -->
					
				</td>
			</tr>
			<tr id="copyMail" style="display: none;">
				<td align=right>抄送：</td>
				<td>
					<input name="copyUserListIds" id="copyUserListIds" type="hidden"/>
					<textarea id="copyUserListNames" name="copyUserListNames" style="height:25px;width:550px" class="BigTextarea readonly" readonly></textarea>
					<span class="addSpan">
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['copyUserListIds','copyUserListNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('copyUserListIds','copyUserListNames')" value="清空 "/>
					</span>
					<!-- <a class="orgAdd" onclick="selectUser(['copyUserListIds','copyUserListNames'],'14')" href="javascript:void(0);">选择</a>&nbsp;&nbsp;
					<a class="orgClear" onclick="clearData('copyUserListIds','copyUserListNames')" href="javascript:void(0);">清空</a> -->
				</td>
			</tr>
			<tr id="secretMail" style="display: none;">
				<td align=right>密送：</td>
				<td>
					<input name="secretUserListIds" id="secretUserListIds" type="hidden"/>
					<textarea id="secretUserListNames" name="secretUserListNames" style="height:25px;width:550px" class="BigTextarea readonly" readonly></textarea>
					<span class="addSpan">
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['secretUserListIds','secretUserListNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('secretUserListIds','secretUserListNames')" value="清空"/>
					</span>
					<!-- <a class="orgAdd" onclick="selectUser(['secretUserListIds','secretUserListNames'],'14')" href="javascript:void(0);">选择</a>&nbsp;&nbsp;
					<a class="orgClear" onclick="clearData('secretUserListIds','secretUserListNames')" href="javascript:void(0);">清空</a> -->
				</td>
			</tr>
			<tr id="externalMail" style="display: none;">
				<td align=right>外部邮箱：</td>
				<td>
					<input id="externalInput" name="externalInput" type="text" style="width:550px" class="BigInput" />
				</td>
			</tr>
			<tr>
				<td align=right>主题：</td>
				<td>
					<input id="subject" name="subject" type="text" style="width:550px" class="BigInput" />
				</td>
			</tr>
			<tr>
				<td align=right>内容：</td>
				<td>
					<textarea id="content" name="content" style="width:850px"></textarea>
				</td>
			</tr>
			<tr id="attachTr" style="display:none;">
				<td class="TableData" align=right>附　件：</td>
				<td class="TableData" >
					<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
				</td>
			</tr>
			<tr>
				<td class="TableData" align=right>级    别：</td>
				<td class="TableData">
				<select style="width: 200px;height: 25px;" id="emailLevel" name="emailLevel" class="BigSelect">
				</select>
			</td>
			</tr>
			<tr>
				<td align=right>附件上传：</td>
				<td>
					<div id="fileContainer2"></div>
					<a id="uploadHolder2" class="add_swfupload">
						<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
					</a>
					<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
				</td>
			</tr>
			<tr>
				<td align=right>提醒：</td>
				<td >
					<input type="checkbox" id="smsRemind" name="smsRemind"   checked="checked"/> &nbsp;
					<label for="smsRemind">发送事务提醒</label>&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="phoneSmsSpan"></span>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td align=right>回执：</td>
				<td>
					<input type="checkbox" id="receipt" name="receipt" value="1" />&nbsp;
					<label for="receipt">发送事务提醒请求阅读收条 (收件人第一次阅读邮件时，向发件人发送事务提醒消息)</label> 
				</td>
			</tr>
		</table>
		<br>
		</form>
</div>
</body>
</html>