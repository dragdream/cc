<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.servlet.TeeResPrivServlet"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="com.tianee.webframe.util.global.*"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%
	String contextPath = request.getContextPath();
	String thread_local_archives = TeeStringUtil.getString(request
		.getParameter("thread_local_archives"), "");
%>

<%
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	String view = TeeStringUtil.getString(request.getParameter("view"));
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- jQuery库 -->
<script src="<%=contextPath %>/common/easyui/jquery.min.js"></script>
<script src="<%=contextPath %>/common/js/grayscale.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jQuery.print.js"></script>
<script type="text/javascript" >
window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>

<!-- JBOX通用UI组件 -->
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/jbox-v2.3/jBox/Skins/Blue/jbox.css" />

<!-- 其他工具库类 -->
<script src="<%=contextPath%>/common/js/md5.js"></script>
<script src="<%=contextPath %>/common/js/tools.js"></script>
<script src="<%=contextPath %>/common/js/sys2.0.js"></script>
<script src="<%=contextPath %>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>

<!-- jQuery Tooltip -->
<script type="text/javascript" src="<%=contextPath%>/common/tooltip/jquery.tooltip.min.js"></script>
<link rel="stylesheet" href="<%=contextPath %>/common/tooltip/jquery.tooltip.css" type="text/css"/>

<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>
<script src="<%=contextPath %>/common/ckeditor/ckeditor.js"></script>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/ckeditor/contents.css">


<style>
#这是浮动div组建 用的div样式 2013-10-07 zhp
.attach_name:link,.attach_name:hover,.attach_name:active,.attach_name:visited{color:#0D3A73;}
.attach_div{width:110px;border:#cccccc 1px solid;position:absolute;padding:0px;z-index:10001;background:#FFFFFF;}
.attach_div a{display:block !important;padding:0px 10px;height:25px;line-height:25px;text-decoration:none;color:#393939;}
.attach_div a:hover{background:#5d99da;color:#fff;text-decoration:none;}
p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol
{
	font-size:14px;
	margin:0px;
	padding:0px;
}
table{
border-collapse:collapse;
font-size:12px;
}
#tbody_feedback p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#viewinfo p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#attach p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#feedback p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
#graph p,span,a,div,td,tr,h1,h2,h3,h4,h5,h6,ul,li,ol,table{
font-size:12px;
}
</style>
<script>
var userId = <%=loginPerson.getUuid()%>;
var deptId = <%=loginPerson.getDept().getUuid()%>;
var roleId = <%=loginPerson.getUserRole().getUuid()%>;
var contextPath = "<%=contextPath %>";
var systemImagePath = "<%=contextPath%>/common/images";
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var view = "<%=view%>";
var thread_local_archives = "<%=thread_local_archives%>";
var stores = [];
var storeDatas = [];
var sealSignDatas = [];
var sealSignValideDatas = [];
var sealSignPos = [];

var ctrlSignArray = [];//控件签章data数组
var ctrlRandArray = [];//控件随机数数组
var ctrlRandArray4Pic = [];//控件随机数数组
var ctrlRandPngArray = [];
var ctrlRandPngArray4Pic = [];
var ctrlRandPngPos = [];

var mobileStores = [];
var mobileMD5Datas = [];
var mobileSealSignDatas = [];

var mobileHwStores = [];//移动签批数据数组
var mobileHwArray = [];//移动签批数据数组

var h5HwStores = [];//h5手写签批数组
var h5HwArray = [];//h5手写签批数组

function doInit(){
	if(window.external && window.external.setTitle){
		window.external.setTitle("表单详情");
	}
	
	//如果是归档查询的话，则隐藏掉一些操作
	if(thread_local_archives==""){
		//查阅
		var url = contextPath+"/flowRun/flowRunLookup.action";
		var json = tools.requestJsonRs(url,{runId:runId,thread_local_archives:thread_local_archives});
	}
	
	
	var url = contextPath+"/flowRun/getFormPrintData.action";
	var json = tools.requestJsonRs(url,{runId:runId,view:view,frpSid:frpSid,thread_local_archives:thread_local_archives});
	if(json.rtState){
		var form = json.rtData.form;
		var css = json.rtData.css;
		var attach = json.rtData.attach;
		var feedback = json.rtData.feedback;
		var graph = json.rtData.graph;
		var viewInfo = json.rtData.viewInfo;
		var docInfo = json.rtData.docInfo;
		var runName=json.rtData.runName;
		//渲染父页面的runName
		parent.$("#runNameTitle").html(runName);
		
		if(form){
			$("#css").html("<style>"+css+"</style>");
			$("#form").html(form).show();
		}
		if(attach){
			if(attach.length!=0){
				$("#attach").show();
				renderAttach(attach);
			}else{
				parent.$("#messDiv").show();
				parent.messageMsg("暂无相关材料信息！","messDiv","info");
			}
			
		}
		if(feedback){
			if(feedback.length!=0){
				$("#feedback").show();
				renderFeedback(feedback);
			}else{
				parent.$("#messDiv").show();
				parent.messageMsg("暂无办理意见！","messDiv","info");
			}	
		}
		
		if(graph){
			if(graph.length!=0){
				$("#graph").show();
				renderGraph(graph);
			}else{
				parent.$("#messDiv").show();
				parent.messageMsg("暂无流程步骤！","messDiv","info");
			}	
		}
		
		
		if(viewInfo){
			if(viewInfo.length!=0){
				$("#viewinfo").show();
				renderViewInfo(viewInfo);
			}else{
				parent.$("#messDiv").show();
				parent.messageMsg("暂无查阅情况！","messDiv","info");
			}	
		}
		
		
		if(docInfo!=null && docInfo){
			$("#docinfo").show();
			renderDocInfo(docInfo);
		}

		//签章检验与加载
// 		try{
// 			loadSignData();
// 		}catch(e){}
// 		try{
// 			loadCtrlFeedbackDatas();
// 		}catch(e){}
// 		loadSignDataPng();
// 		loadCtrlSignDataPng();
// 		LoadMobileHwData();
// 		LoadH5HwData();
// 		try{
// 			loadMobileSealData();
// 		}catch(e){}
	}else{
		parent.$("#messDiv").show();
		alert(parent.location);
		parent.messageMsg(json.rtMsg,"messDiv","info");
	}
	
	//将表单数据导入parent框架中
	parent.FORM_PRINT_DATA = {};
	$(".FORM_PRINT").each(function(i,obj){
		var title = $(obj).attr("title");
		var val = $(obj).val();
		parent.FORM_PRINT_DATA[title] = val;
	});
}


/**
 * 处理附件宏标记
 */
function handleMacroAttach(){
	var macroatt=$(".macroatt");
	if(macroatt.length>0){
		for(var i=0;i<macroatt.length;i++){
			var att={};
			att["fileName"]=$(macroatt[i]).attr("filename");
			att["ext"]=$(macroatt[i]).attr("ext");
			att["sid"]=$(macroatt[i]).attr("sid");
			att["priv"]=3;
			
			var attachElement = tools.getAttachElement(att,{});
			//attachElement.append("&nbsp;&nbsp;<span style=color:#0080ff;font-size:12px>("+$(macroatt[i]).attr("sizedesc")+")&nbsp;&nbsp;"+$(macroatt[i]).attr("username")+"&nbsp;&nbsp;"+$(macroatt[i]).attr("createtimedesc")+"</span>");
			$(macroatt[i]).html(attachElement);
		}
	}
}

//打印預覽
function printexplore(){
	openFullWindow(window.location.href);
}


//加载移动签批图片
function LoadH5HwData(){
	for(var i=0;i<h5HwArray.length;i++){
		var itemId = h5HwArray[i];
		var targetItem = $("#"+itemId);
		var targetObject = $("#H5_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
			targetObject = $("<img id=\"H5_HW_POS_IMG_"+itemId+"\" height=\""+targetItem.attr("h")+"\" width=\""+targetItem.attr("w")+"\" onerror=\"this.style.display='none';\"/>").appendTo($("#H5_HW_POS_"+itemId));
		}
		targetObject.attr("src",h5HwStores[i]).show();
		targetObject.css({height:targetItem.attr("h"),width:targetItem.attr("w")});
		if(h5HwStores[i]==""){
			targetObject.hide();
		}
	}
}

function loadSignDataPng(){
for(var i=0;i<stores.length;i++){
var itemId = stores[i];
var signData = sealSignDatas[i];
var pos = sealSignPos[i].split(",");
$("<img onerror=\"this.style.display='none';\"/>").attr("src",signData).css({position:"absolute",left:pos[0]+"px",top:pos[1]+"px"}).appendTo($("#SIGN_POS_"+itemId).css({position:"absolute"}));
}
}
function loadCtrlSignDataPng(){
	for(var i=0;i<ctrlRandArray.length;i++){
		var sp = ctrlRandArray[i].split("_");
		var itemId = sp[0]+"_"+sp[1];
		var random = sp[2];
		var signData = ctrlRandPngArray[i];
		var pos = ctrlRandPngPos[i].split(",");
		$("<img onerror=\"this.style.display='none';\"/>").attr("src",signData).css({position:"absolute",left:pos[0]+"px",top:pos[1]+"px"}).appendTo($("#SIGN_POS_CTRL_"+itemId+"_"+random).css({position:"absolute"}));
	}
	
	
	for(var i=0;i<ctrlRandArray4Pic.length;i++){
		var name = ctrlRandArray4Pic[i];
		$("#SIGN_POS_CTRL_"+name).css({position:"absolute"});
		var signData = ctrlRandPngArray4Pic[i];
		if(signData==""){
			continue;
		}
		var pos = signData.split(",");
		$("<img onerror=\"this.style.display='none';\"/>").attr("src",pos[0]+","+pos[1]).css({position:"absolute",left:pos[3]+"px",top:pos[4]+"px"}).appendTo($("#SIGN_POS_CTRL_"+name).css({position:"absolute"}));
	}
}

function loadMobileSealData(){
	for(var i=0;i<mobileStores.length;i++){
		var itemId = mobileStores[i];
		var sealDatas = mobileSealSignDatas[i].split(",");
		var md5 = mobileMD5Datas[i];
		
		var targetObject = $("#MOBILE_SEAL_IMG_"+itemId);
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_SEAL_IMG_"+itemId+"\" target=\""+itemId+"\" onerror=\"this.style.display='none'\"/>").appendTo($("#MOBILE_SIGN_POS_"+itemId));
		}
		targetObject.attr("src",sealDatas[0]+","+sealDatas[1]).css({"position":"absolute",left:sealDatas[3]+"px",top:sealDatas[4]+"px"});
		
		if(MD5(md5)==sealDatas[2]){//验证生效
			
		}else{//验章失效
// 			targetObject.css({opacity:0.1});
			grayscale(targetObject);
		}
	}
}


//加载移动签批图片
function LoadMobileHwData(){
	for(var i=0;i<mobileHwArray.length;i++){
		var itemId = mobileHwArray[i];
		
		var targetObject = $("#MOBILE_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_HW_POS_IMG_"+itemId+"\" onerror=\"this.style.display = 'none'\"/>").appendTo($("#MOBILE_HW_POS_"+itemId));
		}
		targetObject.attr("src",mobileHwStores[i]).css({"position":"absolute"}).show();
		
	}
}

function loadSignData(){
	var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    for(var i=0;i<storeDatas.length;i++) {
	    	DWebSignSeal.SetStoreData(storeDatas[i]);
	    }
	    DWebSignSeal.ShowWebSeals();


	    for(var i=0;i<stores.length;i++){
	    	var hw = stores[i]+"_hw";
	    	var seal = stores[i]+"_seal";
	    	var strObjectName = DWebSignSeal.FindSeal(hw,2);
	    	if(strObjectName!=""){
	    		DWebSignSeal.SetSealSignData(strObjectName,sealSignValideDatas[i]);
	    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
	    		DWebSignSeal.LockSealPosition(strObjectName);
			}
	    	strObjectName = DWebSignSeal.FindSeal(seal,2);
	    	if(strObjectName!=""){
	    		DWebSignSeal.SetSealSignData(strObjectName,sealSignValideDatas[i]);
	    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
	    		DWebSignSeal.LockSealPosition(strObjectName);
			}
	    	
		}
	  } catch (err) {
		    //alert(err);
	 }
}

function loadCtrlFeedbackDatas(){
	var DWebSignSeal = document.getElementById("DWebSignSeal");
	for(var i=0;i<ctrlRandArray.length;i++){
		var name = ctrlRandArray[i];
		var ctrlSign = $("#CTRL_SIGN_"+name).val();
		var ctrlContent = $("#CTRL_CONTENT_"+name).val();

		if(ctrlSign && ctrlContent){
			//加载签章数据
	        DWebSignSeal.SetStoreData(ctrlSign);
		}
	}
	DWebSignSeal.ShowWebSeals();
	
	for(var i=0;i<ctrlRandArray.length;i++){
		var datas = ctrlRandArray[i];
		var name = datas.split("_")[0]+"_"+datas.split("_")[1];
		var rand0 = datas.split("_")[2];
		var ctrlSign = $("#CTRL_SIGN_"+datas).val();
		var ctrlContent = $("#CTRL_CONTENT_"+datas).val();
		var hw = name+"_hw_"+rand0;
		var seal = name+"_seal_"+rand0;
		var strObjectName = DWebSignSeal.FindSeal(hw,2);
    	if(strObjectName!=""){
    		DWebSignSeal.SetSealSignData(strObjectName,ctrlContent);
    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
    		DWebSignSeal.LockSealPosition(strObjectName);
		}
    	strObjectName = DWebSignSeal.FindSeal(seal,2);
    	if(strObjectName!=""){
    		DWebSignSeal.SetSealSignData(strObjectName,ctrlContent);
    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
    		DWebSignSeal.LockSealPosition(strObjectName);
		}
	}
}

/**
 * 渲染附件
 */
function renderAttach(attach){
	var tbody_attach = $("#tbody_attach");
	for(var i=0;i<attach.length;i++){
		var item = attach[i];
		//render+="<div><b>第"+item.prcsId+"步</b></div>";
		item.priv = 1+2;
		var fileItem = tools.getAttachElement(item);
		tbody_attach.append(fileItem);
	}
}

/**
 * 渲染会签
 */
function renderFeedback(feedback){
	var sHtml = "";
	for(var i=0;i<feedback.length;i++){
		if(feedback[i].replayId==0){	
			var attachList = feedback[i].attachList;
			sHtml+="<div style=\"font-size:12px;padding:10px;background:#f2f2f2;border-left:2px solid #176fd1;margin-top:10px;\">";
			sHtml+="<div>"
				 +"<div><span style=\"font-weight:bold;\">第"+feedback[i].prcsId+"步&nbsp;&nbsp;"+feedback[i].prcsName+"</span>&nbsp;&nbsp;"+(feedback[i].backFlag==1?"<img src='/common/images/workflow/goback.png' title='退回步骤'/>":"")+"&nbsp;&nbsp;<span style=\"font-weight:bold;color:#4188d6;\">"+feedback[i].userName+"</span>";
			if(feedback[i].voiceId!="" && feedback[i].voiceId && feedback[i].voiceId!=null){
				sHtml = sHtml+"&nbsp;&nbsp;<video type='video/mp3' style=\"cursor:pointer\" onclick='this.play();' src='/attachmentController/downFile.action?id="+feedback[i].voiceId+"' title=\"点击播放语音\" width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\"></video>";
			}
			sHtml+="</div>";  
			
			sHtml+="<div>"+feedback[i].content+"</div>";
			sHtml+="<div id=\"attach_"+feedback[i].sid+"\"></div>";
			
			sHtml+="<div><span style=\"color:#cbcbcb;\">"+feedback[i].editTimeDesc+"</span>";
			
			sHtml+="</div>";
			sHtml+="</div>";
			
			//渲染回复
			sHtml+=renderFeedBackReply(feedback,feedback[i].sid);
			
			sHtml+="</div>";
			
			$("#tbody_feedback").append(sHtml);
			sHtml="";
			for(var j=0;j<attachList.length;j++){
				var att = attachList[j];
				att.priv = 1+2;
				var attachElement = tools.getAttachElement(att,{params:[feedback[i].sid,att.sid],deleteEvent:function(attachModel,params){
					var url = contextPath+"/feedBack/deleteFeedBackAttach.action";
					var para = {};
					para['fid'] = params[0];
					para['aid'] = params[1];
					var json = tools.requestJsonRs(url,para);
					if(json.rtState){
						window.location.reload();
					}else{
						alert(json.rtMsg);
					}
				}});
				$("#attach_"+feedback[i].sid).append(attachElement);
			}
		}
		/* var item = feedback[i];
		var tr = $("<tr></tr>");
		var td1 = $("<td style=\"text-align:left;border:1px solid #e2e2e2;padding:5px;\"></td>");
		td1.append("<div><b>第"+item.prcsId+"步&nbsp;"+item.userName+"&nbsp;&nbsp;"+item.editTimeDesc+"</b></div>");
		if(item.voiceId!="" && item.voiceId && item.voiceId!=null){
			td1.append("语音：<video type='video/mp3' style=\"cursor:pointer\" onclick='this.play();' src='/attachmentController/downFile.action?id="+item.voiceId+"' title=\"点击播放语音\" width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\"></video>");
		}
		td1.append("<div>"+item.content+"</div>");
		
		var attachList = item.attachList;
		if(attachList!=null){
			for(var j=0;j<attachList.length;j++){
				var attach = attachList[j];
				attach.priv = 1+2;
				var fileItem = tools.getAttachElement(attach);
				td1.append(fileItem);
			}
		}
		tr.append(td1);
		tbody_feedback.append(tr); */
	}
}

function renderFeedBackReply(feedback,pSid){
	var sHtml="";
	for(var i=0;i<feedback.length;i++){
		if(feedback[i].replayId==pSid){
			sHtml+="<div style=\"margin-left:20px;border-left:1px dotted #bbbbbb;padding:10px;\">"
				 +"<div>"
				 +"<div><span style=\"font-weight:bold;color:#4188d6;\">"+feedback[i].userName+"</span>&nbsp;&nbsp;<span style=\"font-weight:bold;\">回复：<span></div>"
				 +"<div>"+feedback[i].content+"</div>";
			sHtml+="<div><span style=\"color:#cbcbcb;\">"+feedback[i].editTimeDesc+"</span>";			
			sHtml+="</div>";
			sHtml+="</div>";
			//渲染回复
			sHtml+=renderFeedBackReply(feedback,feedback[i].sid);
			
			sHtml+="</div>";
		}
	}
	return sHtml;
}


function PrintShowFile(obj){
	var ext = obj.getAttribute("ext");
	var attachId = obj.getAttribute("attach_id");
	var attachName = obj.getAttribute("file_name");
	if(isImage(ext)||isOffice(ext)||this.isPdf(ext)||this.isMedia(ext)){
		if(window.confirm("是否下载文件？")){
			if(!window._downloadFrame){
				window._downloadFrame = document.createElement("iframe");
				window._downloadFrame.style.display = "none";
				document.body.appendChild(window._downloadFrame);
			}
			var url = contextPath +"/attachmentController/downFile.action?id="+attachId;
			window._downloadFrame.src = url;
		}else{
			if(isImage(ext)){//如果是图片
				var url = contextPath +"/system/core/attachment/picExplore.jsp?id="+attachId;
				openFullWindow(url,"在线预览");
			}else if(isOffice(ext)){//如果是office文档
				var ntkoURL = contextPath +  "/system/core/ntko/indexNtko.jsp?attachmentId="+attachId+"&attachmentName="+attachName+"&moudle=workFlow&op=7";
				openFullWindow(encodeURI(ntkoURL),"在线文档阅读");
			}else if(this.isPdf(ext)){//是否为pdf
				var ntkoURL = contextPath +  "/system/core/aip/aip4view.jsp?docId="+attachId;
				openFullWindow(encodeURI(ntkoURL),"在线文档阅读");
			}else if(this.isMedia(ext)){
				openFullWindow(contextPath+"/common/CuPlayer/play.jsp?id="+attachId+"&fileName="+encodeURI(attachName),"正在播放"+attachName);
			}
		}
	}else{
		if(!window._downloadFrame){
			window._downloadFrame = document.createElement("iframe");
			window._downloadFrame.style.display = "none";
			document.body.appendChild(window._downloadFrame);
		}
		var url = contextPath +"/attachmentController/downFile.action?id="+attachId;
		window._downloadFrame.src = url;
	}
	
}

function isImage(ext){
	ext = ext.toLowerCase();
	if(ext=="gif" || ext=="jpg" || ext=="png" || ext=="bmp"){
		return true;
	}
	return false;
}
function isOffice(ext){
	ext = ext.toLowerCase();
	if(ext=="doc" || ext=="xls" || ext=="ppt" || ext=="docx" || ext=="xlsx" || ext=="pptx"){
		return true;
	}
	return false;
}
function isMedia(ext){
	ext = ext.toLowerCase();
	if(ext=="flv" || ext=="3gp" || ext=="mov" || ext=="mp4" || ext=="f4v"){
		return true;
	}
	return false;
}
function isPdf(ext){
	ext = ext.toLowerCase();
	if(ext=="pdf"){
		return true;
	}
	return false;
}

/**
 * 流程图
 */
function renderGraph(prcsList){
	var tbody = $("#tbody_graph");
	var group = groupBy(prcsList);
	var render = "";
	for(var i=0;i<group.length;i++){
		render+="<tr>";
		var set = group[i];
		var rows = 0;
		for(var key in set){
			rows++;
		}
		render+="<td rowspan="+rows+" style='border:1px solid #e2e2e2;;padding:5px;'>第"+(i+1)+"步</td>";
		for(var key in set){
			var arr = set[key];
			if(arr[0].flowPrcsId!=0){
				render+="<td style='border:1px solid #e2e2e2;;padding:5px;'>步骤："+arr[0].prcsName+"</td>";
			}
			render+="<td style=\"text-align:left;border:1px solid #e2e2e2;padding:5px;\">";
			var title = "";
			for(var j=0;j<arr.length;j++){
				var prcsInfo = arr[j];
				if(prcsInfo.topFlag==1){//主办
					title+="主办："+prcsInfo.prcsUserName+" ";
				}else{//经办
					title+="经办："+prcsInfo.prcsUserName+" ";
				}

				if(prcsInfo.flag==1){//未接收
					title+="<span style=\"color:gray\">未接收</span>";
				}else if(prcsInfo.flag==2){//办理中
					title+="<span style=\"color:red\">办理中</span> 用时："+prcsInfo.passedTime;
				}else if(prcsInfo.flag==3 || prcsInfo.flag==4){//已办结
					title+="<span style=\"color:green\">已办结</span> 用时："+prcsInfo.passedTime;
				}

				title+="<br/>";
				if(prcsInfo.beginTimeDesc!=""){
					title+="接收时间："+prcsInfo.beginTimeDesc;
				}
				if(prcsInfo.endTimeDesc!=""){
					title+="&nbsp;&nbsp;办结时间："+prcsInfo.endTimeDesc;
				}
				title+="<br/><br/>";
			}
			render+=title;
			render+="</td>";
			if(rows!=1){
				render+="</tr>";
				render+="<tr>";
			}
		}
		render+="</tr>";
	}
	tbody.append(render);
}

function renderViewInfo(viewInfos){
	var tbody = $("#tbody_viewinfo");
	var render = "";
	for(var i=0;i<viewInfos.length;i++){
		var info = viewInfos[i];
		render+="<tr>";
		render+="<td style='border:1px solid #e2e2e2;;padding:5px;'>"+info.viewUsername+"</td>";
		render+="<td style='border:1px solid #e2e2e2;;padding:5px;'>"+(info.viewFlag==0?"<span style='color:red'>未查看</span>":"<span style='color:green'>已查看</span>")+"</td>";
		render+="<td style='border:1px solid #e2e2e2;;padding:5px;'>"+info.viewTimeDesc+"</td>";
		render+="</tr>";
	}
	tbody.append(render);
}

function renderDocInfo(docInfo){
	  var info;
	  TANGER_OCX_OBJ = $('#TANGER_OCX')[0];
	  TANGER_OCX_OBJ.IsUseUTF8Data = true;
	  TANGER_OCX_OBJ.FileNew = true;
	  TANGER_OCX_OBJ.FileClose = false;
	  try {
		  setTimeout(function(){
			  TANGER_OCX_OBJ.BeginOpenFromURL(contextPath+"/attachmentController/downFile.action?id="+docInfo.sid, true, false);
	      },1000);
	  } catch (err) {
		  alert("错误：" + err.number + ":" + err.description);
		  var ee = err;
		  var ss = '';
	  } finally {
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



function printit(){
	$("#content").print({
    	globalStyles: true,
    	mediaPrint: false,
    	stylesheet: null,
    	noPrintSelector: ".no-print",
    	iframe: true,
    	append: null,
    	prepend: null,
    	manuallyCopyFormValues: true,
    	deferred: $.Deferred(),
    	timeout: 750,
    	title: null,
    	doctype: '<!doctype html>'
    }); 
// 	if (!!window.ActiveXObject || "ActiveXObject" in window){
// 		try{
// 			var myDoc = {
// 	         		documents: document,
// 	         		copyrights: '杰创软件拥有版权  www.jatools.com'    
// 	         	};
// 			document.getElementById("jatoolsPrinter").printPreview(myDoc);   // 打印预览	
// 		}catch(e){
// 			$("#content").print({
// 		    	globalStyles: true,
// 		    	mediaPrint: false,
// 		    	stylesheet: null,
// 		    	noPrintSelector: ".no-print",
// 		    	iframe: true,
// 		    	append: null,
// 		    	prepend: null,
// 		    	manuallyCopyFormValues: true,
// 		    	deferred: $.Deferred(),
// 		    	timeout: 750,
// 		    	title: null,
// 		    	doctype: '<!doctype html>'
// 		    }); 
// 		}
// 	}else{
// 		$("#content").print({
// 	    	globalStyles: true,
// 	    	mediaPrint: false,
// 	    	stylesheet: null,
// 	    	noPrintSelector: ".no-print",
// 	    	iframe: true,
// 	    	append: null,
// 	    	prepend: null,
// 	    	manuallyCopyFormValues: true,
// 	    	deferred: $.Deferred(),
// 	    	timeout: 750,
// 	    	title: null,
// 	    	doctype: '<!doctype html>'
// 	    }); 
// 	}
} 



//地图预览
function  previewPosition(spanId){
		//获取坐标点
		var points=document.getElementById(spanId).nextSibling.nextSibling.value;
		var lng=points.split(",")[0];
		var lat=points.split(",")[1];
		
		var url=contextPath+"/system/core/workflow/flowrun/prcs/mapPreview.jsp?lng="+lng+"&lat="+lat;
		
		if(top.location==window.location){//自身
			dialog(url,"600px","300px");
		}else{
			top.bsWindow(url ,"地图预览",{width:"600",height:"300",buttons:
				[
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
				
				}else if(v=="关闭"){
					return true;
				}
			}});
		}
		
	}
</script>
</head>
<body onload="doInit()" style="margin:0px" id="page1">
<div id="css"></div>
<div id="content">
	<div id="form" style="display:none" class="wf"></div>
	<center>
	<div id="attach" style="display:none;margin-top:10px;">
		<table style="width:90%;border-collapse:collapse;;font-size:12px">
			<tbody>
				<tr>
					<td style="text-align:center;;background:#f0f0f0;padding:10px;border:1px solid #e2e2e2" colspan=2><b>相关材料</b></td>
				</tr>
				<tr>
					<td  id="tbody_attach" style="text-align:left;border:1px solid #e2e2e2;padding:5px;">
						
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="feedback" style="display:none;margin-top:10px;font-size:12px;margin-bottom: 10px">
		<table style="width:90%;border-collapse:collapse;;font-size:12px">
			<tbody>
				<tr>
					<td style="text-align:center;background:#f0f0f0;padding:10px;border:1px solid #e2e2e2"><b>办理意见</b></td>
				</tr>
				<tr>
				    <td>
				       <div  id="tbody_feedback"> </div>
				    </td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="graph" style="display:none;margin-top:10px;;font-size:12px">
		<table style="width:90%;border-collapse:collapse;;font-size:12px" class="table table-bordered table-condensed">
			<tbody id="tbody_graph">
				<tr>
					<td style="text-align:center;background:#f0f0f0;padding:10px;border:1px solid #e2e2e2" colspan=3><b>流程列表</b></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="viewinfo" style="display:none;margin-top:10px;">
		<table style="width:90%;border-collapse:collapse;;font-size:12px" class="">
			<tbody id="tbody_viewinfo">
				<tr>
					<td style="text-align:center;;background:#f0f0f0;padding:10px;border:1px solid #e2e2e2" colspan=3><b>查阅情况</b></td>
				</tr>
				<tr>
					<td style="text-align:center;border:1px solid #e2e2e2">查阅人</td>
					<td style="text-align:center;border:1px solid #e2e2e2">查阅状态</td>
					<td style="text-align:center;border:1px solid #e2e2e2">查阅时间</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="docinfo" style="display:none;margin-top:10px;">
		<table style="width:90%;border-collapse:collapse;;font-size:12px" class="">
			<tbody id="tbody_viewinfo">
				<tr>
					<td style="text-align:center;;background:#f0f0f0;padding:10px;border:1px solid #e2e2e2"><b>正文</b></td>
				</tr>
				<tr>
					<td style="text-align:center;border:1px solid #e2e2e2">
						<%=TeeSysProps.getString("NTKO_DOM") %>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</center>
</div>
<script>
	if(isNaN(Number(view))){
		view = tools.decode64(view.replace(/@/,"="));
	}
</script>
<script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
with (TANGER_OCX_OBJ.ActiveDocument) {
    appName = new String(Application.Name);
    if ((appName.toUpperCase()).indexOf("WORD") > -1) //Word
    {
    	Protect(2, true, "");
    } 
  }
</script>
</body>
</html>