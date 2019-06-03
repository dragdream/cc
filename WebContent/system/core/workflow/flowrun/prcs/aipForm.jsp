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
<script src="/system/core/aip/jquery.jsonp.js"></script>
<script src="/system/core/aip/aip_server.js"></script>
<%

TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
 
int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
%>
<script type="text/javascript">
var runId = <%=runId%>;
var flowId=<%=flowId %>;
var frpSid=<%=frpSid %>;
var serverAddress = "<%=TeeSysProps.getString("DJ_ADDRESS")%>/Seal/general/interface/";
var aipVer = <%=TeeSysProps.getString("AIP_VER")%>;

//全局对象
var AIP;
var user = "<%=loginUser.getUuid()%>";//当前登陆人姓名
var attachmentId = 0;
function doInit(){
	renderTemplate();
	AIP = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
	$("#upload-message").show();


}

//打开盖章规则
function openSealRules(){
	dialog(contextPath+"/system/core/system/seal/list.jsp?frpSid="+frpSid+"&aipId="+$("#templateId").val(),250,380);
}

//远程盖章
function addRemoteSeal(sealId){
	Save();//先保存
	var json=tools.requestJsonRs("/flowRun/addRemoteSeal.action?sealId="+sealId+"&attachId="+attachmentId);
	changeTemplate();//重新打开文件
	
	
}

//渲染相关的aip签批模板
function renderTemplate(){
	var  url=contextPath+"/flowPrintTemplate/renderTemplate.action";
	var  json=tools.requestJsonRs(url,{flowId:flowId,frpSid:frpSid});
	if(json.rtState){
		var  data=json.rtData;
		var cy = data.cy;
		var qp = data.qp;
		var html = [];
		if(qp && qp.length>0){
			html.push("<optgroup label='可签批'>");
			for(var i=0;i<qp.length;i++){
				html.push("<option class='qp' value="+qp[i].sid+">"+qp[i].modulName+"</option>");
			}
			html.push("</optgroup>");
		}
		
		if(cy && cy.length>0){
			html.push("<optgroup label='可查阅'>");
			for(var n=0;n<cy.length;n++){
				html.push("<option class='cy' value="+cy[n].sid+">"+cy[n].modulName+"</option>");
			}
			html.push("</optgroup>");
		}
		$("#templateId").append(html.join(""));
	}
}

var attachmentId=0;

//改变模板
function changeTemplate(){
	if(!!window.ActiveXObject || "ActiveXObject" in window){
		AIP.CloseDoc(0);
	}else{
		CloseDoc(0,"rebCloseDoc","","");
	}
	
	//获取被选中的option的属性   是可查阅  还是可签批
	var optClass=$("#templateId option:selected").attr("class");
	
	if($("#templateId").val()==0){
		$("#upload-message").show();
		$("#content").hide();
		
		$("#optDiv1").hide();
		$("#optDiv2").hide();
	}else{
		
		if(optClass=="cy"){//查阅
			$("#optDiv1").hide();
			$("#optDiv2").hide();
		}else{//签批
			$("#optDiv1").show();
			$("#optDiv2").show();
		}
		
		
		var url=contextPath+"/flowRunAipTemplateController/isExist.action";
		var json=tools.requestJsonRs(url,{runId:runId,templateId:$("#templateId").val()});
		if(json.rtState){
			var data=json.rtData;
			attachmentId=data.attachId;
			
			$("#content").show();
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
		 		var attachinfo = tools.requestJsonRs("/attachmentController/getAttachInfo.action?id="+data.attachId).rtData;
		 		AIP.LoadFileEx("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/attachmentController/downFile.action?id="+data.attachId,attachinfo.ext,0,1);
	 		}else{
	 			SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
				LoadFile("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/attachmentController/downFile.action?id="+attachmentId,"rebLoadAip","","");
	 		}
	 		
	 		if(!!window.ActiveXObject || "ActiveXObject" in window){
	 			//关联表单数据
			    var u=contextPath+"/flowRun/getFlowRunDatasOnTitle.action";
			    var j=tools.requestJsonRs(u,{runId:runId});
			    if(j.rtState){
			    	var d=j.rtData;
			    	var pageCount=AIP.PageCount;//获取AIP页码
			    	for(var j=1;j<=pageCount;j++){
			    		for (var key in d) { // 遍历Array
				    	    AIP.SetValue("Page"+j+"."+key,"");
				    	    AIP.SetValue("Page"+j+"."+key,d[key]);
				    	}
			    		
			    	}
			    	
			    }
	 		}
		    
		}
	}
	
}



function Save(){
	var attachinfo = tools.requestJsonRs("/attachmentController/getAttachInfo.action?id="+attachmentId).rtData;
	if(attachinfo.ext=="pdf"){
		return;
	}
	//获取被选中的option的属性   是可查阅  还是可签批
	var optClass=$("#templateId option:selected").attr("class");
	if(optClass=="qp"){
		if(!!window.ActiveXObject || "ActiveXObject" in window){
			AIP.HttpInit();
			AIP.HttpAddPostCurrFile("file");
			AIP.SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
			AIP.HttpPost("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/ntko/updateFile.action?attachmentId="+attachmentId);
		}else{
			SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
			HttpPostFile("",1,"<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/ntko/updateFile.action?attachmentId="+attachmentId);
		}
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
// 	if(!!window.ActiveXObject || "ActiveXObject" in window){
		
// 	}else{
// // 		 ConnectServ("5;|;","rebPageCount",undefined,undefined);
// 		//关联表单数据
// 	    var u=contextPath+"/flowRun/getFlowRunDatasOnTitle.action";
// 	    var j=tools.requestJsonRs(u,{runId:runId});
// 	    if(j.rtState){
// 	    	var d=j.rtData;
// 	    	var pageCount=5;//默认回填5页
// 	    	for(var j=1;j<=pageCount;j++){
// 	    		for (var key in d) { // 遍历Array
// 	    			SetValue("Page"+j+"."+key,"");
// 		    	    SetValue("Page"+j+"."+key,d[key]);
// 		    	}
// 	    	}
	    	
// 	    }
// 	}
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
function rebPageCount(data){
	
}
</script>

<SCRIPT LANGUAGE=javascript FOR=TeeHWPostil EVENT=NotifyCtrlReady>
	
  	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
	AIP = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
	AIP.ShowToolBar = false;
	AIP.ShowDefMenu = 0;
	AIP.ShowScrollBarButton = 1;
// 	s = obj.Login("",3, 65535, "",serverAddress);
	//s = obj.Login("",1, 65535, "","");
// 	if(s==0){
// 		alert("用户登录成功");
// 	}else{
// 		alert("登录失败："+s);
// 	}
	$(window).resize(function(){
		$("#TeeHWPostil").css({height:$(window).height()});
	});
	$("#TeeHWPostil").css({height:$(window).height()});
</SCRIPT>

</head>
<body onload="doInit()" id="bodyContent" style="overflow:hidden">
<table width=100% height=100% class="small" cellspacing="1" cellpadding="3" align="center">
<tr width=100%>

	   

<td valign=top width="100px" style="width:100px;" >

    <select style="width:100%" id="templateId" name="templateId" onchange="changeTemplate();">
			<option value="0">请选择模板</option>
		</select>
	<div class="btn-group-vertical" style="width:100%;display: none"  id="optDiv1">
		<button id="doc_fun_open_local_file" type="button" class="btn btn-success" onclick="Save()"><i class="glyphicon glyphicon-floppy-disk"></i>&nbsp;保存</button>
		<button id="doc_fun_save" type="button" class="btn btn-default"  onclick="AIP.CurrAction = 0"><i class="glyphicon glyphicon-search"></i>&nbsp;浏览</button>
		<button id="aip_btn" type="button" class="btn btn-default"  onclick="AIP.CurrAction = 264"><i class="glyphicon glyphicon-pencil"></i>&nbsp;手写</button>
		<button id="doc_fun_save" type="button" class="btn btn-default"  onclick="AIP.CurrAction = 2568"><i class="glyphicon glyphicon-th-large"></i>&nbsp;本地盖章</button>
		<button id="doc_fun_save" type="button" class="btn btn-default"  onclick="openSealRules()"><i class="glyphicon glyphicon-th-large"></i>&nbsp;远程盖章</button>
		<button id="doc_fun_save" type="button" class="btn btn-default"  onclick="AIP.CurrAction = 16"><i class="glyphicon glyphicon-tag"></i>&nbsp;橡皮</button>
		<button id="doc_fun_pageSet" type="button" class="btn btn-default" onclick="AIP.PrintDoc(1,1)"><i class="glyphicon glyphicon-print"></i>&nbsp;打印</button>
	</div>
	<br/>
	<br/>
	<div class="btn-group-vertical" style="width:100%;display: none" id="optDiv2">
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
	<i class="icon-upload"></i>温馨提示</h3></div>
	<div id="upload_panel_body" class="panel-body" style="padding:10px;">
	请在左侧选择AIP签批模板
	</div>
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

