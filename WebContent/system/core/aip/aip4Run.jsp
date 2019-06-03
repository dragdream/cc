<%@page import="com.tianee.oa.oaconst.TeeConst"%>
<%@page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%@page import="java.util.Date"%>
<%@page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="com.tianee.oa.oaconst.TeeConst"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>AIP版式文件</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<script src="./jquery.jsonp.js"></script>
<script src="./aip_server.js"></script>
<%

TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
 
int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
int officePriv = TeeStringUtil.getInteger(request.getParameter("officePriv"),0);
int docId = TeeStringUtil.getInteger(request.getParameter("docId"),0);
%>
<script type="text/javascript">
var runId = <%=runId%>;
var officePriv = <%=officePriv%>;
var serverAddress = "<%=TeeSysProps.getString("DJ_ADDRESS")%>/Seal/general/interface/";
var aipVer = <%=TeeSysProps.getString("AIP_VER")%>;

//全局对象
var AIP;
var user = "<%=loginUser.getUuid()%>";//当前登陆人姓名
var attachmentId = 0;
var aipAttachId = 0;
var docId = <%=docId%>
function doInit(){
	AIP = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
	//获取流程版式正文
	var url = contextPath +"/flowRunDocController/getFlowRunDocAipByRunId.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	var data = json.rtData;
	
	
	if(docId==0){//从版式正文页签打开
		//如果不存在版式文件，则显示提交/打印/上传
		if(data==null){
			if((officePriv & 2)!=2){//无创建权限
				$("#upload_panel_body").html("您没有创建版式正文的权限");
			}
			$("#upload-message").show();
			
		}else{
			if((officePriv & 1)!=1){//无查看权限
				$("#upload_panel_body").html("您没有查看版式正文的权限");
				$("#upload-message").show();
				return;
			}
			attachmentId = data;
			$("#content").css({width:"100%"});
			$("#upload-message").hide();
			
			if(!!window.ActiveXObject || "ActiveXObject" in window){
				var ret = -1;
				if(aipVer==0){//单机版登录
					ret = AIP.Login("", 1, 65535, "", "");
				}else if(aipVer==1){//网络版登录
					ret = AIP.Login("",3, 65535, "",serverAddress);
				}
				if(ret!=0){
					AIP.Login( "HWSEALDEMO"+user , 4, 32767, "demo", "");
				}
				AIP.SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
		 		AIP.LoadFile("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/attachmentController/downFile.action?id="+attachmentId);
			}else{
				$("#upload_panel_body").html("<a href='#' onclick='rebLogin()'>点击查看文件</a>");
				$("#upload-message").show();
				SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
				LoadFile("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/attachmentController/downFile.action?id="+attachmentId,"rebLoadAip","","");
			}
			
		}
	}else{//从“转版式正文”按钮触发
		if((officePriv & 2)!=2){//无创建权限
			$("#upload_panel_body").html("您没有创建版式正文的权限");
			$("#upload-message").show();
			return;
		}
		if((officePriv & 1)!=1){//无查看权限
			$("#upload_panel_body").html("您没有查看版式正文的权限");
			$("#upload-message").show();
			return;
		}
		
		$("#content").css({width:"100%"});
		$("#upload-message").hide();
		var ret = -1;
		if(aipVer==0){//单机版登录
			ret = AIP.Login("", 1, 65535, "", "");
		}else if(aipVer==1){//网络版登录
			ret = AIP.Login("",3, 65535, "",serverAddress);
		}
		if(ret!=0){
			AIP.Login( "HWSEALDEMO"+user , 4, 32767, "demo", "");
		}
		AIP.SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
		var attachinfo = tools.requestJsonRs("/attachmentController/getAttachInfo.action?id="+docId).rtData;
 		AIP.LoadFileEx("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/attachmentController/downFile.action?id="+docId,attachinfo.ext,0,1);
	}
	
	
	if((officePriv & 4)!=4){//没有保存权限
		$("#doc_fun_open_local_file").hide();
		$("#aip_btn").hide();
		$("#doc_fun_save1").hide();
		$("#doc_fun_save2").hide();
	}else{
		$("#doc_fun_open_local_file").show();
		$("#aip_btn").show();
		$("#doc_fun_save1").show();
		$("#doc_fun_save2").show();
	}
	
}

/**
 * 转为版式正文
 */
function DOC_TO_AIP(){
	if (window.confirm("确认要将正文转为版式文件？")) {
	 	var url = contextPath +"/flowRunDocController/getFlowRunDocByRunId.action";
	 	var json = tools.requestJsonRs(url,{runId:runId});
	 	var flowRunDoc = json.rtData;
	 	if(flowRunDoc==null){//如果没有正文，则判断当前权限是否有创建正文的权限
	 		//alert("正文不存在，请添加或创建一个正文.doc");
	 	}else{//如果存在正文，则判断当前权限是否允许查看
	 		attachmentId = flowRunDoc.docAttach.sid;
	 	}
	 	if(attachmentId!=0){
	 		$("#upload-message").hide();
	 		$("#content").css({width:"100%"});
	 		var ret = -1;
			if(aipVer==0){//单机版登录
				ret = AIP.Login("", 1, 65535, "", "");
			}else if(aipVer==1){//网络版登录
				ret = AIP.Login("",3, 65535, "",serverAddress);
			}
			if(ret!=0){
				AIP.Login( "HWSEALDEMO"+user , 4, 32767, "demo", "");
			}
	 		AIP.LoadFileEx("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/attachmentController/downFile.action?id="+attachmentId,"doc",0,0);
	 	}else{
	 		alert("正文不存在，请添加或创建一个正文.doc");
	 	}
    }
}

function SelectFile(){
	$("#upload-message").hide();
	$("#content").css({width:"100%"});
	if(!!window.ActiveXObject || "ActiveXObject" in window){
		var ret = AIP.LoadFile('');
		if(ret==0){//失败
			$("#upload-message").show();
			$("#content").css({width:"0px"});
		}else{//成功
			var ret = -1;
			if(aipVer==0){//单机版登录
				ret = AIP.Login("", 1, 65535, "", "");
			}else if(aipVer==1){//网络版登录
				ret = AIP.Login("",3, 65535, "",serverAddress);
			}
			if(ret!=0){
				AIP.Login( "HWSEALDEMO"+user , 4, 32767, "demo", "");
			}
		}
	}else{
		LoadAip('');
	}
	
}

function Save(){
	if(!!window.ActiveXObject || "ActiveXObject" in window){
		AIP.HttpInit();
		AIP.HttpAddPostCurrFile("file");
		AIP.SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
		AIP.HttpPost("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/flowRunDocController/createOrUpdateFlowRunDocAip.action?runId="+runId);
	}else{
		SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
		HttpPostFile("",1,"<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/flowRunDocController/createOrUpdateFlowRunDocAip.action?runId="+runId);
	}
}


//扫描上传
function scanFile(){
	var ret = -1;
	if(aipVer==0){//单机版登录
		ret = AIP.Login("", 1, 65535, "", "");
	}else if(aipVer==1){//网络版登录
		ret = AIP.Login("",3, 65535, "",serverAddress);
	}
	if(ret!=0){
		AIP.Login( "HWSEALDEMO"+user , 4, 32767, "demo", "");
	}
	var r=AIP.InsertEmptyPage(0,3,0,0);
	if(r==0){//失败
		alert("读取扫描仪器失败！");
	    return;
	}
}

function InitAip(){
	SetShowToolBar(1,"","","");
}
function LoadWord(path,type){
	LoadOriginalFile(path,type,"rebLoadWord","","");
}
function rebLoadWord(){
	ShowAipWnd(DSP_MODE_PRIMARY_MAX,50,50,'','','');
}
function SaveWord(path,type,flog){
	SaveTo(path,type,flog,"rebSaveWord","","");
	HideAipWnd("","","");
}
function rebSaveWord(){
	HideAipWnd("","","");
}
function LoadBase64(strBase64){
	LoadFileBase64(strBase64,"rebLoadBase64","","");
}
function rebLoadBase64(){
	ShowAipWnd(DSP_MODE_PRIMARY_MAX,50,50,'','','');
}
function LoadAip(path){
	LoadFile(path,"rebLoadAip","","");
}
function rebLoadAip(){
	if(aipVer==0){//单机版登录
		Login("",1,65535,"","","rebLogin","","");
	}else if(aipVer==1){//网络版登录
		Login("",3,65535,"",serverAddress,"rebLogin","","");
	}
	
}
function rebLogin(){
	ShowAipWnd(DSP_MODE_PRIMARY_MAX,50,50,'','','');
}
function SaveAip(path,type,flog){
	SaveTo(path,type,flog,"rebSaveTo","","");
}
function rebSaveTo(){
	CloseDoc(0,"rebCloseDoc","","");
}
function rebCloseDoc(){
	HideAipWnd("","","");
}
function HttpPostFile(docid,type,url){
	var str=docid+"`"+type+"`"+url;
	HttpInit("rebHttpInit",str,"");
}
function rebHttpInit(data){
	var str=data.calldata;
	var strs=str.split("`");
	HttpAddPostString("docid",strs[0],"rebHttpAddPostString1",str,"");
}
function rebHttpAddPostString1(data){
	var str=data.calldata;
	var strs=str.split("`");
	HttpAddPostString("type",strs[1],"rebHttpAddPostString2",str,"");
}
function rebHttpAddPostString2(data){
	var str=data.calldata;
	HttpAddPostCurrFile("FileContent","rebHttpAddPostCurrFile",str,"");
}
function rebHttpAddPostCurrFile(data){
	var str=data.calldata;
	var strs=str.split("`");
	HttpPost(strs[2],"rebHttpPost","","");
}
function rebHttpPost(){
	HideAipWnd("","","");
}
function rebAIPping(id){
	alert(id);
}
</script>

<SCRIPT LANGUAGE=javascript FOR=TeeHWPostil EVENT=NotifyCtrlReady>
	
  	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
	AIP = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
	AIP.ShowToolBar = false;
	AIP.ShowDefMenu = 0;
	AIP.ShowScrollBarButton = 1;
	$(window).resize(function(){
		$("#TeeHWPostil").css({height:$(window).height()});
	});
	$("#TeeHWPostil").css({height:$(window).height()});
</SCRIPT>

</head>
<body onload="doInit()" id="bodyContent" style="overflow:hidden">
<table width=100% height=100% class="small" cellspacing="1" cellpadding="3" align="center">
<tr width=100%>
<td valign=top width="100px" style="width:100px">
	<div class="btn-group-vertical" style="width:100%">
	<button id="doc_fun_save" type="button" class="btn btn-default"  onclick="AIP.CurrAction = 0"><i class="glyphicon glyphicon-search"></i>&nbsp;浏览</button>
	<button id="doc_fun_pageSet" type="button" class="btn btn-default" onclick="AIP.PrintDoc(1,1)"><i class="glyphicon glyphicon-print"></i>&nbsp;打印</button>
	<button id="doc_fun_open_local_file" type="button" class="btn btn-success" onclick="Save()"><i class="glyphicon glyphicon-floppy-disk"></i>&nbsp;保存</button>
	<button id="aip_btn" type="button" class="btn btn-default"  onclick="AIP.CurrAction = 264"><i class="glyphicon glyphicon-pencil"></i>&nbsp;手写</button>
	<button id="doc_fun_save1" type="button" class="btn btn-default"  onclick="AIP.CurrAction = 2568"><i class="glyphicon glyphicon-th-large"></i>&nbsp;盖章</button>
	<button id="doc_fun_save2" type="button" class="btn btn-default"  onclick="AIP.CurrAction = 16"><i class="glyphicon glyphicon-tag"></i>&nbsp;橡皮</button>
		
	</div>
	<br/>
	<br/>
	<div class="btn-group-vertical" style="width:100%;">
		<button id="" type="button" class="btn btn-default" onclick="AIP.CurrPenWidth =3 ">笔记（细）</button>
		<button id="" type="button" class="btn btn-default" onclick="AIP.CurrPenWidth = 5 ">笔记（中）</button>
		<button id="" type="button" class="btn btn-default" onclick="AIP.CurrPenWidth = 8 ">笔记（粗）</button>
		<button id="" type="button" class="btn btn-default" onclick="AIP.CurrPenColor=255"><span style="color:red">■</span>（红）</button>
		<button id="" type="button" class="btn btn-default" onclick="AIP.CurrPenColor = 0"><span style="color:black">■</span>（黑）</button>
		<button id="" type="button" class="btn btn-default" onclick="AIP.CurrPenColor = 16711680"><span style="color:blue">■</span>（蓝）</button>
	</div>
</td>
<td valign="top">
<div style="display:none;margin:0px auto;width:500px;margin-top:30px;text-align:center" class="td-messagebox panel panel-primary" id="upload-message">
	<div class="panel-heading">
	<h3 class="panel-title">
	<i class="icon-upload"></i>制作版式文件</h3></div>
	<div id="upload_panel_body" class="panel-body" style="padding:10px;"><div class="btn-group">
	<button onclick="scanFile()" class="btn btn-danger" id="yw2" name="yt2" type="button"><i class="icon-file-upload"></i> 从扫描仪上传</button>
	<button  style="margin-left:20px" onclick="DOC_TO_AIP()" class="btn btn-danger" id="yw1" name="yt0" type="button"><i class="icon-file-upload"></i> 从正文转换</button>
	<button style="margin-left:20px" onclick="SelectFile()" class="btn btn-danger" id="yw2" name="yt1" type="button"><i class="icon-cursor"></i> 选择文件</button></div><br><br><span>可打开Word、PDF、图片等格式文件。</span></div>
</div>
<div id="content" style="position:absolute;width:0px;"> 
	<OBJECT id=TeeHWPostil align='middle' style='LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT:100%' classid=clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561 codebase=<%=contextPath %>/system/core/aip/HWPostil.cab#version=3,1,2,6>
		<PARAM NAME='_Version' VALUE='65536'>
		<PARAM NAME='_ExtentX' VALUE='17410'>
		<PARAM NAME='_ExtentY' VALUE='10874'>
		<PARAM NAME='_StockProps' VALUE='0'>
	</OBJECT>
</div>
</td>
</tr>
</table>
</body>
</html>

