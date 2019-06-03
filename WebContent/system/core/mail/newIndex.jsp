<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<%@ include file="/header/header.jsp"%>
	<%@ include file="/header/upload.jsp"%>
	<title>内部邮件</title>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css"/>
	<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
		<!-- 引入respond.js解决IE8显示问题 -->
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/respond.js"></script>
	<script type="text/javascript" charset="UTF-8">
	
	 function checkEmail(adderss){
         //对电子邮件的验证
	      if (adderss.search(/^([a-zA-Z0-9_-]+[_|_|.]?)*[a-zA-Z0-9_-]+@([a-zA-Z0-9_-]+[_|_|.]?)*[a-zA-Z0-9_-]+\.(?:com|cn)$/)!= -1){
	          return true;   
          }
         return false;
     }
	 
	 function setWebMail(type){
		 var valueAll = "";
		 var mailAddress = document.getElementById('mailAddress').contentWindow.document;
		 var webMail = "${mailModel.toWebmail}";
		 $("#webmailCount").val("${mailModel.webmailCount}");
		 var lastIndex = webMail.lastIndexOf(';');
	     if(lastIndex > -1) {
	    	 webMail = webMail.substring(0, lastIndex) + webMail.substring(lastIndex + 1, webMail.length);
	     }
	     if("${mailModel.ifWebMail}"=="1"&&type==1){
	    	 webMail = "${mailModel.fromWebMail}";
	    	 webMail = webMail.split("&lt")[1];
	    	 webMail = webMail.substring(0, webMail.lastIndexOf('&gt'));
	    	 $("#webmailCount").val(webMail.split(";").length);
	     }
	     if("${mailModel.ifWebMail}"=="1"&&type==2){
	    	 webMail = "${mailModel.fromWebMail}";
	    	 webMail = webMail.split("&lt")[1];
	    	 webMail = webMail.substring(0, webMail.lastIndexOf('&gt'));
	    	 var toWebMail = "${mailModel.toWebMail}";
	    	 var toWebMailArray = toWebMail.split(",");
	    	 for(var x = 1;x <= toWebMailArray.length;x++){
	    		 toWebMail =  toWebMailArray[x-1].split("&lt")[1];
	    		 toWebMail = toWebMail.substring(0, toWebMail.lastIndexOf('&gt'));
	    		 webMail = webMail+";"+toWebMail;
	    	 }
	    	 $("#webmailCount").val(webMail.split(";").length);
	     }
	     var array = webMail.split(";");
	     //alert("${mailModel.webmailCount}");
	     
	     for(var i = 1;i <= array.length;i++){
        	 var id = "div_"+i;
        	 var aid = "a_"+i;
        	 var color = "${color}";
        	 var value = array[i-1];
        	 
        	 var title = "有效邮箱地址，点击可进行修改";
        	 if(!checkEmail(value)){
        		 color = "red";
        		 title = "无效邮件地址，点击可进行修改";
        	 }
        	 $(mailAddress.getElementById("webDiv")).append("<div id='"+id+"' onclick='editMailAddress("+i+",\""+value+"\");' "+
                	" title='"+title+"' style='margin-top: 4px;margin-bottom: 4px;margin-left: 2px;cursor:pointer;float:left;position:relative;background-color: "+color+";height:17px;width：auto;max-width:365px;'>"+
                	" <font color='white'>"+value+"&nbsp;</font></div>"+
                	" <div style='cursor:pointer;float:left;position:relative;background-color: red;height:17px;width：auto;margin-right: 2px;margin-top: 4px;margin-bottom: 4px;' "+
                	" id='"+aid+"' onclick='deleteDiv("+i+",\""+value+"\");' title='删除'>"+
                	" <font color='white'>×</font></div>");
        	 valueAll = valueAll+ value + ";" ;
	     }
	     //alert(valueAll);
	     $(mailAddress.getElementById("externalInput")).val(valueAll);
	 }
	function doInit(){
		$("[title]").tooltips();
		$(document).ready(function(){      
		    $("#subject").focusin(function() {
		        if($(this).val() =="添加标题"){  
		            $(this).val("");
		        }
		    });
		    $("#subject").focusout(function() {
		        if($(this).val() ==""){ 
		            $(this).val("添加标题");
		        }
		    });
		});
		//alert("${mailModel}");
				//初始化肤色
		$("#indexHeader").css("background","${color}");
		$("#indexHeader").css("border-color","${color}");
		$(".bs-docs-nav").css("background","${color}");
		$(".bs-docs-nav").css("border-color","${color}");
		$("#layout").layout({auto:true});
		 new TeeSimpleUpload({
			 	fileContainer:"fileContainer",//文件列表容器
				uploadHolder:"uploadHolder",//上传按钮放置容器
				valuesHolder:"valuesHolder",//附件主键返回值容器，是个input
				showUploadBtn:false,
				form:"form1",
				post_params:{model:"EMAIL"}//后台传入值，model为模块标志
		});
		var mailAddress = document.getElementById('mailAddress').contentWindow.document;
		//其他模块调用
		if("${otherModel}"!=""&&"${otherModel}"!=null&&"${otherModel}"!="null"){
			mailAddress.getElementById("userListIds").value = "${otherModel.userListIds}";
			mailAddress.getElementById("userListNames").value = "${otherModel.userListNames}";
			var subject = "${otherModel.subject}";
			$("#subject").val(subject);
		}
		if("${mailModel}"!=""&&"${mailModel}"!=null&&"${mailModel}"!="null"){
			var type = "${type}";
			var subject = "${mailModel.subject}";
			//$(mailAddress.getElementById("copyHeader")).trigger("click");
			var indexContent = '${mailModel.content}';
			//indexContent = indexContent.replace(/&apos;/g,"'");
			//indexContent = indexContent.replace(/&quot;/g,"\"");
			//indexContent = indexContent.replace(/&nbsp;&nbsp;/g,"\t");
			//indexContent = indexContent.replace(/&lt;/g,"<");
			//indexContent = indexContent.replace(/&gt;/g,">");
			//alert("${mailModel.toWebmail}");
			if(type=="0"){
				indexContent = "<p>re:</p><p>---------------------------------------------------------------------------------------------------------------------------------------------------------------------------</p><p>&nbsp;</p>" + indexContent;
				if("${mailModel.ifWebMail}"=="0"){
					mailAddress.getElementById("userListIds").value = "${mailModel.fromId.uuid}"+",";
					mailAddress.getElementById("userListNames").value = "${mailModel.fromId.userName}"+",";
					mailAddress.getElementById("externalInput").value = "${mailModel.toWebmail}";
					mailAddress.getElementById("copyUserListIds").value = "";
					mailAddress.getElementById("copyUserListNames").value = "";
					mailAddress.getElementById("secretUserListIds").value = "";
					mailAddress.getElementById("secretUserListNames").value = "";
					if("${mailModel.toWebmail}"!=""&&"${mailModel.toWebmail}"!=null&&"${mailModel.toWebmail}"!="null"){
						$(mailAddress.getElementById("externalHeader")).trigger("click");
						//$("#webmailCount").val("${mailModel.webmailCount}");
						setWebMail();
					}
				}else{
					$(mailAddress.getElementById("externalHeader")).trigger("click");
					setWebMail(1);
				}
				subject = "回复："+subject;
				//回复发件人
			}else if(type=="1"){
				indexContent = "<p>re:</p><p>---------------------------------------------------------------------------------------------------------------------------------------------------------------------------</p><p>&nbsp;</p>" + indexContent;
				//回复所有人
				if("${mailModel.ifWebMail}"=="0"){
					mailAddress.getElementById("userListIds").value = "${mailModel.fromId.uuid}"+",";
					mailAddress.getElementById("userListNames").value = "${mailModel.fromId.userName}"+",";				mailAddress.getElementById("externalInput").value = "${mailModel.toWebmail}";
					mailAddress.getElementById("copyUserListIds").value = "${mailModel.copyUserListIds}";
					mailAddress.getElementById("copyUserListNames").value = "${mailModel.copyUserListNames}";
					mailAddress.getElementById("secretUserListIds").value = "";
					mailAddress.getElementById("secretUserListNames").value = "";
					if("${mailModel.toWebmail}"!=""&&"${mailModel.toWebmail}"!=null&&"${mailModel.toWebmail}"!="null"){
						$(mailAddress.getElementById("externalHeader")).trigger("click");
						//$("#webmailCount").val("${mailModel.webmailCount}");
						setWebMail();
						//mailAddress.getElementById("webDiv").innerHTML = "${mailModel.webmailHtml}";
					}
					if("${mailModel.copyUserListIds}"!=""&&"${mailModel.copyUserListIds}"!=null&&"${mailModel.copyUserListIds}"!="null"){
						$(mailAddress.getElementById("copyHeader")).trigger("click");
					}
				}else{
					$(mailAddress.getElementById("externalHeader")).trigger("click");
					setWebMail(2);
				}
				subject = "回复："+subject;
			}else if(type=="2"){
				indexContent = "<p>re:</p><p>---------------------------------------------------------------------------------------------------------------------------------------------------------------------------</p><p>&nbsp;</p>" + indexContent;
				subject = "转发："+subject;
				mailAddress.getElementById("userListIds").value = "";
				mailAddress.getElementById("userListNames").value = "";
				mailAddress.getElementById("copyUserListIds").value = "";
				mailAddress.getElementById("copyUserListNames").value = "";
				mailAddress.getElementById("secretUserListIds").value = "";
				mailAddress.getElementById("secretUserListNames").value = "";
				mailAddress.getElementById("externalInput").value = "";
				//转发
			}else if(type=="3"||type=="4"){
				mailAddress.getElementById("userListIds").value = "${mailModel.userListIds}";
				mailAddress.getElementById("userListNames").value = "${mailModel.userListNames}";
				mailAddress.getElementById("externalInput").value = "${mailModel.toWebmail}";
				mailAddress.getElementById("copyUserListIds").value = "${mailModel.copyUserListIds}";
				mailAddress.getElementById("copyUserListNames").value = "${mailModel.copyUserListNames}";
				mailAddress.getElementById("secretUserListIds").value = "${mailModel.secretUserListIds}";
				mailAddress.getElementById("secretUserListNames").value = "${mailModel.secretUserListNames}";
				if("${mailModel.toWebmail}"!=""&&"${mailModel.toWebmail}"!=null&&"${mailModel.toWebmail}"!="null"){
					$(mailAddress.getElementById("externalHeader")).trigger("click");
					//$("#webmailCount").val("${mailModel.webmailCount}");
					setWebMail();
				}
				if("${mailModel.copyUserListIds}"!=""&&"${mailModel.copyUserListIds}"!=null&&"${mailModel.copyUserListIds}"!="null"){
					$(mailAddress.getElementById("copyHeader")).trigger("click");
				}
				if("${mailModel.secretUserListIds}"!=""&&"${mailModel.secretUserListIds}"!=null&&"${mailModel.secretUserListIds}"!="null"){
					$(mailAddress.getElementById("secretHeader")).trigger("click");
				}
				//继续编辑//再次发送
			}
			$("#indexContent").val(indexContent);
			$("#subject").val(subject);
			$("#sid").val("${mailModel.sid}");
			$("#ifBody").val("${mailModel.ifBody}");
			var sid = "${mailModel.sid}";
			var ifBody = "${mailModel.ifBody}";
			var url = "<%=contextPath%>/mail/getAttachModel.action?sid="+sid+"&ifBody="+ifBody;
			var json = tools.requestJsonRs(url);
			if(json.rtState){
				var attaches = json.rtData;
				for(var i=0;i<attaches.length;i++){
					var fileItem = tools.getAttachElement(attaches[i]);
					$("#attachments").append(fileItem);
				}
			}else{
				alert(json.rtMsg);
			}
		}
	}
	function setContent(){
		document.frames["mailBody"].editor.setContent($("#indexContent").val());
	}
	function returnIndex(){
		var url = "<%=contextPath %>/mail/mailIndex.action";
		window.location = url;
	}
	function sendMail(type){
		if(checkMail(type)){
			$.jBox.tip("邮件发送中","loading");
			var text = document.getElementById("webmailCount").value;
			//alert(text);
			var url = "<%=contextPath %>/mail/addOrUpdateMail.action?type="+type+"&webmailCount="+text;
			var para =  tools.formToJson($("#form1")) ;
			para["model"] = "EMAIL";
			$("#form1").doUpload({
				url:url,
				success:function(json){	
					$.jBox.closeTip(true);
					alert(json.rtMsg);
					window.location.reload();
				},
	            error: function() {
	            	$.jBox.closeTip(true);
		            alert("文件传输错误");
	            },
				post_params:para
			});
			
		}
	}
	function checkMail(type){
		var mailAddress = document.getElementById('mailAddress').contentWindow.document;
		var mailBody = document.getElementById('mailBody').contentWindow.document;
		$("#userListIds").val(mailAddress.getElementById("userListIds").value);
		$("#userListNames").val(mailAddress.getElementById("userListNames").value);
		$("#copyUserListIds").val(mailAddress.getElementById("copyUserListIds").value);
		$("#copyUserListNames").val(mailAddress.getElementById("copyUserListNames").value);
		$("#secretUserListIds").val(mailAddress.getElementById("secretUserListIds").value);
		$("#secretUserListNames").val(mailAddress.getElementById("secretUserListNames").value);
		$("#externalInput").val(mailAddress.getElementById("externalInput").value);
		$("#webmailCount").val(mailAddress.getElementById("webmailCount").value);
		//$("#webmailHtml").val(mailAddress.getElementById("webDiv").innerHTML);
		$("#content").val(mailBody.getElementById("content").value);
		var subject = document.getElementById("subject");
		if(subject.value==""&&type=="1"){
			alert("标题不能为空！");
			subject.focus();
			subject.select();
			return false;
		}
		if(mailAddress.getElementById("userListIds").value==""&&type=="1"&&mailAddress.getElementById("externalInput").value==""){
			alert("收件人不能为空！");
			mailAddress.getElementById("userListNames").focus();
			mailAddress.getElementById("userListNames").select();
			return false;
		}
		return true;
	}
	function toIndex(){
		var url = "<%=contextPath%>/mail/mailIndex.action?type=0";
		window.location = url;
	}
	function showRemark4(eventTag){
		id = "divRemark4";
		if(document.getElementById(id).style.display !="none"){
			document.getElementById(id).style.display = 'none';
		}else{
		    var event = eventTag || window.event;
		    var top = 40;
		    document.getElementById(id).style.top = top + 'px';
		    var right = 0;   
	        document.getElementById(id).style.right = right+'px';
		    document.getElementById(id).style.display = '';
		}
	}

	function noShowRemark4(){
		//alert("out");
	    document.getElementById("divRemark4").style.display = 'none';
	}
	function changeColor(value){
		var color = value.replace("#","");
		var url = "<%=contextPath %>/mail/setMailColor.action?color="+color;
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		$("#indexHeader").css("background",value);
		$("#indexHeader").css("border-color",value);
		noShowRemark4();
	}
	</script>
	<style type="text/css">
	body {
		font-family: "Microsoft Yahei",Verdana,Simsun,"Segoe UI","Segoe UI Web Regular","Segoe UI Symbol","Helvetica Neue","BBAlpha Sans","S60 Sans",Arial,sans-serif;
	}
	.dropdown{
		font-family: "Microsoft Yahei",Verdana,Simsun,"Segoe UI","Segoe UI Web Regular","Segoe UI Symbol","Helvetica Neue","BBAlpha Sans","S60 Sans",Arial,sans-serif;
		font-size: 12px;
	}
	.north{
	    height:40px;
	    background-color: #0072C6;
		border-color: #0072C6;
	}
	.west{
	    width:205px;
	}
	.center{
	
	}
	#sub-tabs{
	    background:#0077be;
	    overflow-y:hidden;
	}
	.navbar {
		min-height: 40px;
		height: 40px;
	}
	.container {
		width: 100%;
		height: 40px;
	}
	.bs-docs-nav {
		text-shadow: 0 -1px 0 rgba(0,0,0,.15);
	    background-color: #0072C6;
		border-color: #0072C6;
		box-shadow: 0 1px 0 rgba(255,255,255,.1);
	}
	.navbar-header {
		width: 180px;
		height: 40px;
		float: left;
		
	}
	.navbar-header :hover{

	}
	.navbar-inverse .navbar-brand {
		color: #fff;
	}
	.navbar-brand {
		padding: 10px 15px;
		
	}
	.navbar-nav>li ,active{
		float: left;
		height: 40px;
	}
	.nav>li>a {
		padding: 10px 15px;
	}
	.bs-docs-nav .navbar-nav > .active > a, .bs-docs-nav .navbar-nav :hover {
		 color: #ffffff;
	  	 background-color: #428bca;
	     border-color: #357ebd;
	}
	.bs-sidebar .nav > .active > a, .bs-sidebar .nav > .active:hover > a, .bs-sidebar .nav > .active:focus > a {
		font-weight: bold;
		color: #563d7c;
		background-color: transparent;
		border-right: 1px solid #563d7c;
	}
	.nav-pills>li+li {
		margin-left: 0px;
		margin-top: 0px;
		-webkit-border-radius: 0;
	    -moz-border-radius: 0;
	    border-radius: 0;
	}
	.nav>li {
		-webkit-border-radius: 0;
	    -moz-border-radius: 0;
	    border-radius: 0;
	}
	.bs-docs-nav .navbar-nav > li > a {
		color: #fff;
	}
	</style>
</head>
<body onload="doInit()" style="overflow:hidden;">
<form role="form" id= "form1" name = "form1" enctype="multipart/form-data" method="post">
<input type="hidden" value="" id="indexContent">
<input type="hidden" id= "sid" name ="sid" >
<input type="hidden" id= "ifBody" name ="ifBody" >
<input type="hidden" id= "webmailCount" name ="webmailCount" value="0" >
<input type="hidden" id= "webmailHtml" name ="webmailHtml" >
<div id="layout">
	<div layout="north" height="40" class="north" id="indexHeader" >
		<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner" style="height: 40px;">
		  <div class="container">
		    <div class="navbar-header">
		      <a href="javascript:toIndex();" class="navbar-brand"><font color="white"><i class="glyphicon glyphicon-envelope"></i> TeeneMail</font></a>
		    </div>
		    <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
			      <ul class="nav navbar-nav">
		        <li>
		           <a href="javascript:void(0);" onclick="sendMail(1);"><font color="white"><i class="glyphicon glyphicon-share-alt"></i> 发送</font></a>
		        </li>
		      </ul>
		      <ul class="nav navbar-nav">
		        <li >
		            <a href="javascript:void(0);"  id="uploadHolder"><font color="white"><i class="glyphicon glyphicon-paperclip"></i> 附件</font></a>
		            <!-- <a style="cursor:pointer;" id="uploadHolder"><i class="glyphicon glyphicon-paperclip"></i> 插入 </a> -->
		        </li>
		      </ul>
		      <ul class="nav navbar-nav">
		        <li>
		           <a href="javascript:void(0);" onclick="sendMail(0);"><font color="white"><i class="glyphicon glyphicon-floppy-saved"></i> 保存草稿</font></a>
		        </li>
		      </ul>
		      <ul class="nav navbar-nav">
		        <li>
		           <a href="javascript:returnIndex();"> <font color="white"><i class="glyphicon glyphicon-remove-sign"></i> 取消</font></a>
		        </li>
		      </ul>
		      <ul class="nav navbar-nav navbar-right">
		        <li>
		          <a href="javascript:void(0);" id="colorIcon" onclick="showRemark4(event);"><font color="white"><i class="glyphicon glyphicon-cog"></i>肤色</font></a>
		        </li>
		      </ul>
		    </nav>
   		       <div  id="divRemark4" style="z-index:1200;position:absolute;background:#FFF; margin:0; padding:5px 5px 5px 5px; min-height:50px;min-width:80px; border:1px solid ${color}; font-size:12px; line-height:20px; color:#5c5c5c; font-family:宋体;display:none;">
					<table>
						<tr>
							<td>
								<div onclick = "changeColor('#DC4FAD')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #DC4FAD;cursor: pointer;" title="粉红色"></div>
							</td>	
							<td>
								<div onclick = "changeColor('#AC193D')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #AC193D;cursor: pointer;" title="深红色"></div>
							</td>	
							<td>
								<div onclick = "changeColor('#D24726')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #D24726;cursor: pointer;" title="深橙色"></div>
							</td>
							<td>
								<div onclick = "changeColor('#FF8F32')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #FF8F32;cursor: pointer;" title="橙色"></div>
							</td>
							<td>
								<div onclick = "changeColor('#82BA00')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #82BA00;cursor: pointer;" title="浅绿色"></div>
							</td>
							<td>
								<div onclick = "changeColor('#008A17')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #008A17;cursor: pointer;" title="绿色"></div>
							</td>
						</tr>
						<tr>
							<td>
								<div onclick = "changeColor('#03B3B2')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #03B3B2;cursor: pointer;" title="浅青色"></div>
							</td>	
							<td>
								<div onclick = "changeColor('#008299')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #008299;cursor: pointer;" title="青色"></div>
							</td>	
							<td>
								<div onclick = "changeColor('#5DB2FF')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #5DB2FF;cursor: pointer;" title="浅蓝色"></div>
							</td>
							<td>
								<div onclick = "changeColor('#0072C6')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #0072C6;cursor: pointer;" title="蓝色"></div>
							</td>
							<td>
								<div onclick = "changeColor('#4617B4')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #4617B4;cursor: pointer;" title="深紫色"></div>
							</td>
							<td>
								<div onclick = "changeColor('#8C0095')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #8C0095;cursor: pointer;" title="紫色"></div>
							</td>
						</tr>
						<tr>
							<td>
								<div onclick = "changeColor('#004B8B')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #004B8B;cursor: pointer;" title="中度深蓝"></div>
							</td>	
							<td>
								<div onclick = "changeColor('#001940')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #001940;cursor: pointer;" title="深蓝"></div>
							</td>	
							<td>
								<div onclick = "changeColor('#570000')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #570000;cursor: pointer;" title="棕色"></div>
							</td>
							<td>
								<div onclick = "changeColor('#380000')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #380000;cursor: pointer;" title="深棕色"></div>
							</td>
							<td>
								<div onclick = "changeColor('#585858')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #585858;cursor: pointer;" title="中灰"></div>
							</td>
							<td>
								<div onclick = "changeColor('#000000')" style="padding:5px 10px 10px 5px; width:15px;height:15px;background-color: #000000;cursor: pointer;" title="黑色"></div>
							</td>
						</tr>
					</table>
				</div>
		  </div>
		</header>
	</div>  
<div layout="west" width="395" style="border-right:">
		<iframe src="<%=contextPath %>/mail/choseMailAddress.action" id="mailAddress" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
	<div id="center_panel" layout="center"  border="false" style="overflow:hidden">
		<div id="sub-tabs" layout="north" style="min-height:${fn:length(mailModel.mailAttachMent)*20+90}px;background-color:white; transparent;padding-left:10px;padding-top:5px;overflow-x:hidden;overflow-y:auto; ">      
			 <table style="width:100%;">
				 <tr>
					 <td>
						 <input type="text" size="100%"  placeholder="添加标题" id="subject" name="subject" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;font-size: 24px;" value="添加标题">
					 	 <input type="hidden" id="content" name="content">
					 </td>
				 </tr>
				
				 <table style="width:100%;display:none;">
					 <tr>
		  				 <td><h6><a href="javascript:void(0);" onClick="selectUser(['userListIds', 'userListNames'])">内部联系人</a>&nbsp;<a href="javascript:void(0);" class="orgClear" onClick="clearData('userListIds', 'userListNames')">清空</a></h5></td>
		  				 <td style="float: right;"><h6><a href="javascript:void(0);" id="copyHeader" onclick="showVariousMail(1);">抄送</a>&nbsp;&nbsp;<a href="javascript:void(0);" id="secretHeader" onclick="showVariousMail(2);">密送</a>&nbsp;&nbsp;<a href="javascript:void(0);" id="externalHeader" onclick="showVariousMail(3);">外部邮箱</a><h6></td>
		  			 </tr>
			  		 <tr>
			  			 <td colspan="2">
			  				<input type="hidden" name="userListIds" id="userListIds" required="true" value=""> 
			  				<textarea cols="58" name="userListNames" id="userListNames" rows="4" style="overflow-y: auto;"  class="BigInput" wrap="yes" readonly ></textarea>
			  				<!-- 
			  				<div contentEditable=false style="width: 375px;height:100px;background-color: white;overflow-y: scroll;">
			  					<li class="btn btn-primary" style="padding: 5px;margin: 2.5px;">联系人<a class="glyphicon glyphicon-remove" style="float: right;color: white;padding-left: 5px;"></a></li>
		  					</div>
		  					-->
			  			</td>
			  		</tr>
			  		<tbody id="copyMail" style="display: none;">
			  			<tr>
			  				<td><h6><a href="javascript:void(0);" onClick="selectUser(['copyUserListIds', 'copyUserListNames'])">抄送</a>&nbsp;<a href="javascript:void(0);" class="orgClear" onClick="clearData('copyUserListIds', 'copyUserListNames')">清空</a></h5></td>
			  			</tr>
				  		<tr>
				  			<td colspan="2">
				  				<input type="hidden" name="copyUserListIds" id="copyUserListIds" required="true" value=""> 
			  					<textarea cols="58" name="copyUserListNames" id="copyUserListNames" rows="4" style="overflow-y: auto;"  class="BigInput" wrap="yes" readonly ></textarea>
				  			</td>
				  		</tr>
			  		</tbody>
			  		<tbody id="secretMail"  style="display: none;">
			  			<tr>
			  				<td><h6><a href="javascript:void(0);" onClick="selectUser(['secretUserListIds', 'secretUserListNames'])"> 密送</a>&nbsp;<a href="javascript:void(0);" class="orgClear" onClick="clearData('secretUserListIds', 'secretUserListNames')">清空</a></h5></td>
			  			</tr>
				  		<tr>
				  			<td colspan="2">
				  				<input type="hidden" name="secretUserListIds" id="secretUserListIds" required="true" value=""> 
			  					<textarea cols="58" name="secretUserListNames" id="secretUserListNames" rows="4" style="overflow-y: auto;"  class="BigInput" wrap="yes" readonly ></textarea>
				  			</td>
				  		</tr>
			  		</tbody>
			  		<tbody id="externalMail"  style="display: none;">
			  			<tr>
			  				<td><h6><a href="javascript:void(0);">外部邮箱</a></h5></td>
			  			</tr>
				  		<tr>
				  			<td colspan="2"><textarea id="externalInput" name = "externalInput" rows="4" cols="58"></textarea></td>
				  		</tr>
			  		</tbody>
			  		</table>
		  		
			 </table>

			<hr style="margin-top: 10px;margin-bottom: 0px;width:100%;">
						 
			<table  align="center" style="width: 100%;height:auto;">
				<tr>
					<td>
						<div id='attachments'></div>
					</td>
				</tr>
				<tr>
					<div id ='attachs'></div>
					<div id="fileContainer"></div> 
					<input id="valuesHolder" type="hidden" /><a id="uploadHolder" class="add_swfupload"></a>
				</tr>
			</table>
        </div>
		<div id="tabs-content" layout="center" style="padding-left:10px;padding-top:0px;padding-right:-10px;">
			<iframe src="<%=contextPath %>/mail/mailBody.action" frameborder=0 id="mailBody" style="width:100%;height:100%"></iframe>
		</div>	
	</div>
</div>
</form>
</body>

</html>
