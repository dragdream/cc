<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page import="com.alibaba.dingtalk.openapi.demo.auth.AuthHelper"%>
<%@ page import="com.tianee.oa.oaconst.TeeModuleConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ contextPath + "/";
String mobilePath = contextPath + "/system/mobile";

TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>工作详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="<%=mobilePath%>/phone/style/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/system/mobile/mui/css/app.css?v=2">
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=mobilePath %>/phone/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/workflowUtils.js?v=1"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js?v2"></script> 
<script type="text/javascript" src="form.js?version=251"></script>
<script src="<%=request.getContextPath() %>/system/mobile/js/api.js?v=261"></script>
<script>
var  contextPath = "<%=contextPath %>";
var mobilePath ="<%=mobilePath%>";
var userId = <%=loginUser.getUuid()%>;
var topFlag = 0;
var attachOtherPriv = 0;
var userAgent = "<%=request.getHeader("user-agent")%>";
</script>
<%
	String runId = request.getParameter("runId");
	String flowId = request.getParameter("flowId");
	String frpSid = request.getParameter("frpSid");
	String flowTypeId = TeeStringUtil.getString(request.getParameter("flowTypeId"));
%>
<style type="text/css">
.buzhou{
		width: 100%;
		min-height: 140px;
		height:auto;
		/* background: #fff; */
		margin: 0 auto;
}
.buzhou_head{
	width: 100%;
	height: 36px;
	line-height: 36px;
	border-bottom: 1px solid #f0f0f0;
	color: #333;
}
ul li{
	list-style: none;
	color: #717171;
	font-size: 15px;
	line-height: 30px;
}
</style>


<style type="text/css" media="all">
<%

    //水印
    int waterMark=TeeSysProps.getInt("WATER_MARK");
	if(waterMark==1){
		%>
		body{
			background-image:url('/systemAction/generateWaterMark.action');
		}
		<%
	}
%>
</style>

<script type="text/javascript">
var runId = "<%=runId%>";
var runName;
var delegate = "0";
var archivesPriv="0";//归档权限
var feedback;
var formValidModel;
var signJson = {};//需要验章的 字段
var signArray = new Array();//盖了章的 data
var noSignArray =  new Array();//没有盖章的 data

var mobileSignJson = {};//需要验章的 字段
var mobileSignArray = new Array();//盖了章的 data

var mobileHwStores = [];//移动签批数据数组
var mobileHwArray = [];//移动签批数据数组

var h5HwStores = [];//h5手写签批数组
var h5HwArray = [];//h5手写签批数组

var ctrlSignArray = [];//控件签章data数组
var ctrlRandArray = [];//控件随机数数组
var rand = new Date().getTime()*parseInt((Math.random()*10000+"").split("\\.")[0]);
var mobileStores = [];//控件随机数数组
var mobileMD5Datas = [];//控件随机数数组
var mobileSealSignDatas = [];

</script>
<script>
function doInit(){
tools.requestJsonRs(contextPath+"/mobileWorkflow/getFormHanderData4Print.action",{runId:runId},true,function(json){
		
		if(!json.rtState){
			
			$("#content").html("<p align='center' style='color: red;margin-top:20px'>提示："+json.rtMsg+"</p>");	
		    $("#saveBtn").attr("style","display:none");
		}else{
			//流程信息控制模型
			var workFlowCtrModel=tools.string2JsonObj(json.rtData.workFlowCtrModel);
			runName=json.rtData.runName;//给流程名称赋值
			if(json.rtData.isOpen==1 && workFlowCtrModel!=""&&workFlowCtrModel!=null&&workFlowCtrModel!="null"){//固定流程
				if(workFlowCtrModel[0]["workName"]==true){
					$("#title").html("("+json.rtData.runId+")&nbsp;"+json.rtData.runName);
				}else{
					$("#gzmc").hide();
				}
				
				if(workFlowCtrModel[0]["fromUser"]==true){
					$("#beginUser").html(json.rtData.beginUser);
				}else{
					$("#lcfqr").hide();
				}
				if(workFlowCtrModel[0]["beginTime"]==true){
					$("#beginTime").html(json.rtData.beginTime);
				}else{
					$("#kssj").hide();
				}
				if(workFlowCtrModel[0]["currStep"]==true){
					$("#prcsDesc").html(json.rtData.prcsDesc);
				}else{
					$("#dqbz").hide();
				}
				
				
			}else{//自由流程
				$("#title").html("("+json.rtData.runId+")&nbsp;"+json.rtData.runName);
				$("#beginUser").html(json.rtData.beginUser);
				$("#beginTime").html(json.rtData.beginTime);
				$("#prcsDesc").html(json.rtData.prcsDesc);
				
			}
			
			
			topFlag = json.rtData.topFlag;
			attachOtherPriv = json.rtData.attachOtherPriv;
			$("#level").html(json.rtData.level);
			
			$("#form").html(json.rtData.form);
			
			if(json.rtData.docId){
				$("#docDiv").show().html("正文：<span onclick=\"GetFile("+json.rtData.docId+",'"+(json.rtData.docId+json.rtData.docFileName)+"','"+json.rtData.docAttachName+"')\" style='color:red'>(点击查看)</span>");
			}
			
			if(json.rtData.aipDocId){
				$("#aipDiv").show().html("版式正文：<span onclick=\"OpenAipFile("+json.rtData.aipDocId+",'"+(json.rtData.runId)+"','1')\" style='color:red'>(点击查看)</span>");
			}
			
			if(json.rtData.hasAipFile==1){//有aip簽批文件
				$("#qpd").show();
				renderTemplate();
			}else{
				$("#qpd").hide();
			}
			
			delegate = json.rtData.delegate;
			
			try{
				initialize();//初始化动态表单
			}catch(e){}
			try{
				loadWorkFlowAttach();//加载公共附件
			}catch(e){}
			try{
				loadFeedBack();//加载会签意见
			}catch(e){}
			try{
// 				loadOperations();//初始化操作按钮
			}catch(e){}
			
			//加载移动签章
			loadMobileSealData();
			//加载手写签批
			LoadMobileHwData();
			//加载手写签批
			LoadH5HwData();
			//加载上传控件
			LoadXUploadCtrl();

		}
	});
	
	
	
	//渲染流程办理情况
    renderFlowStep();
}

function loadFeedBack(){
	/* var url = contextPath+"/feedBack/getFeedBackList.action";
	var para  = {};
	para["runId"] = runId;
	$("#feedbackDiv").html("");
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var fdDate = json.rtData;
		var sHtml = [];
		for(var i=0;i<fdDate.length;i++){
			var attachList = fdDate[i].attachList;
			sHtml = ["<div style='border-bottom:1px solid #e2e2e2'>"];
			sHtml.push("<p style='border-bottom:1px solid #e2e2e2'><span style='color:orange'>●</span>&nbsp;第"+fdDate[i].prcsId+"步&nbsp;"+fdDate[i].prcsName+"&nbsp;&nbsp;");
			if(fdDate[i].voiceId!="" && fdDate[i].voiceId && fdDate[i].voiceId!=null){
				sHtml.push("<video type='video/mp3' style=\"cursor:pointer\" onclick='this.play();' src='/attachmentController/downFile.action?id="+fdDate[i].voiceId+"' title=\"点击播放语音\" width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\"></video>");
			}
			sHtml.push("</p><p style='padding-top:0px;'><p style='float:left;font-size:14px'>"+fdDate[i].userName+"</p><p style='float:right;font-size:14px'>"+fdDate[i].editTimeDesc+"</p></p>");
			sHtml.push("<p style='clear:both;padding-bottom:0px'></p>"); 
			sHtml.push("<div style='font-size:14px;padding:5px;padding-top:0px'>"+fdDate[i].content+"</div>");
			sHtml.push("</div>");
			$("#feedbackDiv").append(sHtml.join(""));
		}
	}else{
		Alert(json.rtMsg);
	} */
	var url = contextPath+"/feedBack/getFeedBackList.action";
	var para  = {};
	para["runId"] = runId;
	$("#feedbackDiv").html("");
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var fdDate = json.rtData;
		var sHtml = "";
		//$(parent.feedback_count).html(fdDate.length);
		for(var i=0;i<fdDate.length;i++){
			//先渲染第一级 	
			if(fdDate[i].replayId==0){	
				var attachList = fdDate[i].attachList;
				sHtml+="<div style=\"font-size:14px;padding:10px;background:#f2f2f2;border-left:2px solid #176fd1;margin-top:10px;\">";
				sHtml+="<div>"
					 +"<div><span style=\"font-weight:bold;\">第"+fdDate[i].prcsId+"步&nbsp;&nbsp;"+fdDate[i].prcsName+"</span>&nbsp;&nbsp;"+(fdDate[i].backFlag==1?"<img src='/common/images/workflow/goback.png' title='退回步骤'/>":"")+"&nbsp;&nbsp;<span style=\"font-weight:bold;color:#4188d6;\">"+fdDate[i].userName+"</span>";
				if(fdDate[i].voiceId!="" && fdDate[i].voiceId && fdDate[i].voiceId!=null){
					sHtml = sHtml+"&nbsp;&nbsp;<video type='video/mp3' style=\"cursor:pointer\" onclick='this.play();' src='/attachmentController/downFile.action?id="+fdDate[i].voiceId+"' title=\"点击播放语音\" width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\"></video>";
				}
				sHtml+="</div>";  
				
				sHtml+="<div>"+fdDate[i].content+"</div>";
				sHtml+="<div id=\"attach_"+fdDate[i].sid+"\"></div>";
				
				sHtml+="<div><span style=\"color:#cbcbcb;font-size:12px;\">"+fdDate[i].editTimeDesc+"</span>";
				
				sHtml+="</div>";
				sHtml+="</div>";
				
				//渲染回复
				sHtml+=renderFeedBackReply(fdDate,fdDate[i].sid);
				
				sHtml+="</div>";
				
				$("#feedbackDiv").append(sHtml);
				sHtml="";
				for(var j=0;j<attachList.length;j++){
					var att = attachList[j];
					var attUserId = att.userId;
					var domHtml="";
					if(this.isImage(att.ext)){//如果是图片
						domHtml = ["<p style=color:#0080ff;font-size:12px><img class='pic' attachId="+att.sid+"  attachName='"+att.attachmentName+"'   src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+att.fileName+"("+att.sizeDesc+")"+"</p>"];
					}else{
						domHtml = ["<p style=color:#0080ff;font-size:12px><img  attachId="+att.sid+"  attachName='"+att.attachmentName+"'  src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+att.fileName+"("+att.sizeDesc+")"+"</p>"];
					}
					
					domHtml.push("<button class=\"btn btn-primary btn-sm\" style='font-size:12px;padding:2px;' onclick=\"GetFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">查看</button>");
				    if(att.ext.indexOf("doc")!=-1 ||att.ext.indexOf("docx")!=-1||att.ext.indexOf("xls")!=-1 ||att.ext.indexOf("xlsx")!=-1){
				    	domHtml.push("<button class=\"btn btn-warning btn-sm\" style='font-size:12px;padding:2px;' onclick=\"quickView("+att.sid+")\">快速预览</button>");    	
				    }
					
					
					$("#attach_"+fdDate[i].sid).append(domHtml.join("&nbsp;&nbsp;"));
				}
			}
			
		}
		
	}else{
		alert(json.rtMsg);
		
	}
}

function isImage(ext){
	ext = ext.toLowerCase();
	if(ext=="gif" || ext=="jpg" || ext=="png" || ext=="bmp"){
		return true;
	}
	return false;
}

function renderFeedBackReply(fdDate,pSid){
	var sHtml="";
	for(var i=0;i<fdDate.length;i++){
		if(fdDate[i].replayId==pSid){
			sHtml+="<div style=\"margin-left:20px;border-left:1px dotted #bbbbbb;padding:10px;\">"
				 +"<div>"
				 +"<div><span style=\"font-weight:bold;color:#4188d6;\">"+fdDate[i].userName+"</span>&nbsp;&nbsp;<span style=\"font-weight:bold;\">回复：<span></div>"
				 +"<div>"+fdDate[i].content+"</div>";
			sHtml+="<div><span style=\"color:#cbcbcb;font-size:12px;\">"+fdDate[i].editTimeDesc+"</span>";
			
			sHtml+="</div>";
			sHtml+="</div>";
			//渲染回复
			sHtml+=renderFeedBackReply(fdDate,fdDate[i].sid);
			
			sHtml+="</div>";
		}
	}
	return sHtml;
}

//加载上传控件
function LoadXUploadCtrl(){
	var xuploadItem = $(".upload_ctrl_item");
	for(var i=0;i<xuploadItem.length;i++){
		xuploadItem[i].setAttribute("href","javascript:void(0)");
		xuploadItem[i].onclick=function(){
			var attachId = this.getAttribute("attach_id");
			var attachName = this.getAttribute("attach_name");
			var fileName = this.getAttribute("file_name");
			GetFile(attachId,attachId+"_"+attachName,attachId+"_"+fileName);
		};
		
		$(xuploadItem[i]).after("&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick='quickView("+xuploadItem[i].getAttribute("attach_id")+")'>快速预览</a>&nbsp;&nbsp;&nbsp;&nbsp;");
	}
}

//加载移动签批图片
function LoadH5HwData(){
	for(var i=0;i<h5HwArray.length;i++){
		var itemId = h5HwArray[i];
		var targetItem = $("#"+itemId);
		$("#H5_HW_POS_"+itemId).css({position:"inherit"});
		var targetObject = $("#H5_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
			targetObject = $("<img id=\"H5_HW_POS_IMG_"+itemId+"\" height=\""+targetItem.attr("h")+"\" width=\""+targetItem.attr("w")+"\"/>").appendTo($("#H5_HW_POS_"+itemId));
		}
		targetObject.attr("src",h5HwStores[i]).show();
		targetObject.css({height:targetItem.attr("h"),width:targetItem.attr("w")});
	}
}

function loadMobileSealData(){
	for(var i=0;i<mobileStores.length;i++){
		var itemId = mobileStores[i];
		var sealDatas = mobileSealSignDatas[i].split(",");
		var md5 = mobileMD5Datas[i];
		
		$("#MOBILE_SIGN_POS_"+itemId).css({position:"inherit"});
		var targetObject = $("#MOBILE_SEAL_IMG_"+itemId);
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_SEAL_IMG_"+itemId+"\" target=\""+itemId+"\" onerror=\"this.style.display='none'\"/>").appendTo($("#MOBILE_SIGN_POS_"+itemId));
		}
		targetObject.attr("src",sealDatas[0]+","+sealDatas[1]);
		
// 		if(MD5(md5)==sealDatas[2]){//验证生效
			
// 		}else{//验章失效
// 			grayscale(targetObject);
// 		}
	}
}


//加载移动签批图片
function LoadMobileHwData(){
	for(var i=0;i<mobileHwArray.length;i++){
		var itemId = mobileHwArray[i];
		$("#MOBILE_HW_POS_"+itemId).css({position:"inherit"});
		var targetObject = $("#MOBILE_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_HW_POS_IMG_"+itemId+"\" />").appendTo($("#MOBILE_HW_POS_"+itemId));
		}
		targetObject.attr("src",mobileHwStores[i]).show();
		
	}
}


function loadWorkFlowAttach(){
	var url = contextPath+"/teeWorkflowAttachmentController/getTeeWorkFlowAttachment.action";
	var para  = {};
	para["runId"] = runId;
	$("#pulicAttachments").html("");
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var attachList = json.rtData;
		var attStrHtml = "";
		for(var j=0;j<attachList.length;j++){
			var att = attachList[j];
			var attUserId = att.userId;
			var domHtml = ["<p style=color:#0080ff;font-size:12px><img src='"+window.contextPath+"/common/images/filetype/defaut.gif' />&nbsp;"+att.fileName+"("+att.sizeDesc+")"];
			domHtml.push("<span onclick=\"GetFile("+att.sid+",'"+att.fileName+"','"+att.attachmentName+"')\">查看</span>");
			domHtml.push("<span onclick=\"quickView("+att.sid+")\">快速预览</span>");
			
			$("#pulicAttachments").append(domHtml.join("&nbsp;&nbsp;"));
		}
	}else{
		Alert(json.rtMsg);
	}
}

//快速预览
function quickView(attId){
    var url =contextPath+"/system/core/ntko/officePreview.jsp?attachId="+attId;
    OpenWindow("快速预览",url);

}


function ShowForm(){
	if(userAgent.indexOf("DingTalk")!=-1 || userAgent.indexOf("MicroMessenger")!=-1){
		window.location = "/system/mobile/phone/workflow/print.jsp?runId="+runId+"&view=5";
	}else{
		OpenWindow("原始表单","/system/mobile/phone/workflow/print.jsp?runId="+runId+"&view=5");
	}
}



//渲染流程办理情况
function renderFlowStep(){
	
	var url = contextPath+"/flowInfoChar/getFlowRunViewGraphicsData.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		//渲染步骤信息
		var prcsList = json.rtData.prcsList;
		//alert(prcsList.length);
		render(prcsList);
		
	}
}

var tbody;
function render(prcsList){
	tbody = $("#tbody");
	var group = groupBy(prcsList);
	var render="";
	render += "<div class='buzhou'>";
	for(var i=0;i<group.length;i++){
	
		var set = group[i];
		var rows = 0;
		for(var key in set){
			rows++;
		}
		render+="<div class='buzhou_head'>第"+(i+1)+"步：";
		for(var key in set){
			var arr = set[key];
			if(arr[0].flowPrcsId!=0){
				render+=arr[0].prcsName+"</div>";
			}
			render+="<div class='buzhou_content'>";
			var title = "";
			for(var j=0;j<arr.length;j++){
				var prcsInfo = arr[j];
				title+="<ul style='padding-left: 20px;border-bottom: 1px solid #F0F0F0;padding-bottom: 20px;'>";
				if(prcsInfo.topFlag==1){//主办
					title+="<li>主办："+prcsInfo.prcsUserName+"</li>";
				}else{//经办
					title+="<li>经办："+prcsInfo.prcsUserName+"</li>";
				}

				if(prcsInfo.flag==1){//未接收
					title+="<li><span style='color:gray;margin-right: 20px;'>未接收</span></li>";
				}else if(prcsInfo.flag==2){//办理中
					title+="<li><span style='color:red;margin-right: 20px;'>办理中</span> 用时："+prcsInfo.passedTime+"</li>";
				}else if(prcsInfo.flag==3 || prcsInfo.flag==4){//已办结
					title+="<li><span style='color:green;margin-right: 20px;'>已办结</span> 用时："+prcsInfo.passedTime+"</li>";
				}

				if(prcsInfo.beginTimeDesc!=""){
					title+="<li>接收时间："+prcsInfo.beginTimeDesc+"</li>";
				}
				if(prcsInfo.endTimeDesc!=""){
					title+="<li>办结时间："+prcsInfo.endTimeDesc+"</li>";
				}
				title+="</ul>";
			}
			render+=title;
			
			render+="</div>";
			
		}
		
		render+="</div>";	  
		
		
	}
	render+="</div>";	  
	tbody.html(render);
}

//渲染相关的aip签批模板
function renderTemplate(){
	
	var  url=contextPath+"/flowRunAipTemplateController/getListByRunId.action";
	var  json=tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		var  data=json.rtData;
		var html = [];
		if(data && data.length>0){
			for(var i=0;i<data.length;i++){
				html.push("<option  value="+data[i].templateId+"  templateName='"+data[i].templateName+"'  attachmentId="+data[i].attachId+">"+data[i].templateName+"</option>");
			}
		}
		$("#templateId").append(html.join(""));
	}
}


var attachmentId=0;
var templateName="";
//改变模板
function changeTemplate(){
	if($("#templateId").val()==0){
	
	}else{
	    //获取被选中的option的属性  
	   attachmentId=$("#templateId option:selected").attr("attachmentId");	
	   templateName=$("#templateId option:selected").attr("templateName");
	    //关联表单数据
	    var  u=contextPath+"/flowRun/getFlowRunDatasOnTitle.action";
	    var j=tools.requestJsonRs(u,{runId:runId});
	    if(j.rtState){
	    	var d=j.rtData;
	    	OpenAip(attachmentId,templateName+".aip",1,d);
	    }
	}
	
}







function groupBy(prcsList){
	var group = new Array;
	var maxPrcsId = prcsList[prcsList.length-1].prcsId;
	for(var i=1;i<=maxPrcsId;i++){
		var set = {};
		for(var j=0;j<prcsList.length;j++){
			var prcsId = prcsList[j].prcsId;
			var flowPrcsId = prcsList[j].flowPrcsId;
			if(prcsId!=i){
				continue;
			}
			var id = prcsId+""+flowPrcsId;
			var arr = set[id];
			if(!arr){
				arr = new Array();
			}
			arr.push(prcsList[j]);
			set[id] = arr;
		}
		group.push(set);
	}
	return group;
}
</script>
<style>
p{
font-size:16px;
padding:5px;
}
p input{
border:1px solid gray;
}
.readonly{
background:#e2e2e2;
}
.title{
font-size:16px;
font-weight:bold;
background:#428bca;
padding:10px;
color:white;
}
textarea{
border:1px solid gray;
}
.img{
border:1px solid gray;
border-radius:10px;
padding:5px;
}
.item{
	border-bottom:1px solid #e2e2e2;
	font-size:14px;
	padding-top:10px;
	padding-bottom:10px;
}

.fixed_div{
      position:fixed;
      right:0px;
      bottom:0px;
      left:0px;
      background:white;
      border-top:1px solid #cdcdcd;
}
</style>
</head>
<body onload="doInit();" style="overflow-x:hidden;">
<div id="content">
<div style='padding:5px;margin-top:5px;border-top:1px solid ;border-bottom:1px solid #e2e2e2;color:#428bca;font-weight:bold;'>
	<p style="font-size:14px" id="lcjb">流程级别：<span id="level"></span></p>
	<p style="font-size:14px" id="gzmc">工作名称：<span id="title"></span></p>
	<p style="font-size:14px" id="lcfqr">流程发起人：<span id="beginUser"></span></p>
	<p style="font-size:14px" id="kssj">开始时间：<span id="beginTime"></span></p>
	<p style="font-size:14px">原始表单：<img onclick="ShowForm()" src="/common/images/workflow/application-document.png"></a></p>
	<p id="docDiv" style="display:none;font-size:14px"></p>
	<p id="aipDiv" style="display:none;font-size:14px"></p>
	<p style="font-size:14px" id="ysbd">相关资源：<img onclick="OpenWindow('相关资源','/system/mobile/phone/workflow/relatedResource.jsp?runId=<%=runId%>')" src="/common/images/workflow/application-document.png"></a></p>
    <p style="font-size:14px;display: none;" id="qpd">签批单：
	   <select id="templateId" name="templateId" onchange="changeTemplate();">
	      <option value="0">请选择AIP文件</option>
	   </select>
	</p>
</div>
<div class="title">表单</div>
<div id="form" style='padding:5px;margin-top:5px;border-top:1px solid #e2e2e2;border-bottom:1px solid #e2e2e2;'></div>
<div class="title">公共附件</div>
<div id="pulicAttachments" style="padding:10px;"></div>
<div id="publicAttachUploadContainer" style="padding:10px;display:none;"></div>
<div id="publicAttachUploadButtons" style="padding:10px;display:none;">
	<button class="btn btn-primary" onclick="doUploadPublicAttach()">上传图片</button>
</div>
<div class="title">会签意见</div>
<p id="feedbackDiv"></p>
<p id="feedbackForm" style="display:none">
	点击录制语音：<img style="cursor:pointer" title="点击录制语音" onclick="RecordVoice('FEEDBACK_VOICE')" src="/common/images/workflow/voice.png"/>
	<input type="hidden" id="FEEDBACK_VOICE" name="voiceId" value=""/>
	<br/>
	<textarea id="feedbackTextarea" name="feedbackTextarea" ontouchstart="quickFillData(this)" style="height:100px;width:99%"></textarea>
<!-- 	<div id="publicFeedbackUploadContainer" style="padding:10px;"></div> -->
<!-- 	<div style="padding:10px;"> -->
<!-- 		<button class="btn btn-primary" onclick="doUploadFeedbackAttach()">上传图片</button> -->
<!-- 	</div> -->
</p>


<!-- 流程办理步骤 -->
<div class="title" style="margin-top: 10px;">流程步骤</div>
     <div id="tbody" style="background: none;">
     
     </div>
</div>
</body>
</html>