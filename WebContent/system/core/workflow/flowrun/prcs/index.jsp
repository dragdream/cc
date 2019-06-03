<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header2.0.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),
				0);
		int frpSid = TeeStringUtil.getInteger(request
				.getParameter("frpSid"), 0);
		int flowId = TeeStringUtil.getInteger(request
				.getParameter("flowId"), 0);
		String isNew = TeeStringUtil.getString(request
				.getParameter("isNew"), "");
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>流程办理</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
<style>
	.modal-docsenddiv{
		width: 600px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-docsenddiv .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-docsenddiv .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-docsenddiv .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-docsenddiv .modal-body{
		width: 100%;
		height: 300px;
		overflow:auto;
		background-color:#f4f4f4;
	}
	.modal-docsenddiv .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-docsenddiv .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-docsenddiv .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-docsenddiv .modal-body ul li input{
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
	}
	.modal-docsenddiv .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-docsenddiv .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
</style>
<style>
	.modal-docviewdiv{
		width: 600px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-docviewdiv .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-docviewdiv .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-docviewdiv .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-docviewdiv .modal-body{
		width: 100%;
		height: 300px;
		overflow:auto;
		background-color:#f4f4f4;
	}
	.modal-docviewdiv .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-docviewdiv .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-docviewdiv .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-docviewdiv .modal-body ul li input{
		display: inline-block;
		float: right;
		width: 400px;
		height: 25px;
	}
	.modal-docviewdiv .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-docviewdiv .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
</style>
<script>
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var flowId = <%=flowId%>;
var isNew = "<%=isNew%>";
var isSaved = 0;
var prcsId=0;
$.jBox = {
	tip:function(msg,info){
		$.MsgBox.CloseLoading();
		if(info=="loading"){
			$.MsgBox.Loading();
		}else{
			$.MsgBox.Alert_auto(msg);
		}
	},
	closeTip:function(){
		$.MsgBox.CloseLoading();
	}
};

function doInit(){
	frm = $("#frm")[0].contentWindow;
	
	//创建导航页签事件
	$("#form").click(function(){
		clearStyle();
		$(this).addClass("select");
		$("#frmGraph").hide();
		$("#frm").show();
		$("#childGraph").hide();
		$("#flowRunDocFrame").hide();
		frm.focusForm();
	});

	//签批单
	$("#aipForm").click(function(){
		var url=contextPath+"/system/core/workflow/flowrun/prcs/aipForm.jsp?flowId="+flowId+"&runId="+runId+"&frpSid="+frpSid;
		clearStyle();
		$(this).addClass("select");
		$("#aipFrm").show();
		//alert($("#aipFrm").attr("src"));
		if($("#aipFrm").attr("src")==""){
			$("#aipFrm").attr("src",url);
		}
	});
	
	
	//正文页签事件
	$("#flowRunDoc").click(function(){
		var url = contextPath+"/system/core/ntko/indexNtko4Run.jsp?runId="+runId+"&officePriv="+officePriv+"&frpSid="+frpSid ;
		clearStyle();
		$(this).addClass("select");
		$("#flowRunDocFrame").show();
		if($("#flowRunDocFrame").attr("src")==""){
			$("#flowRunDocFrame").attr("src",url);
		}
	});
	
	//版式正文页签事件
	$("#flowRunDocAip").click(function(){
		var url = contextPath+"/system/core/aip/aip4Run.jsp?runId="+runId+"&officePriv="+officePriv+"&docId="+window.docId;
		clearStyle();
		$(this).addClass("select");
		$("#flowRunDocAipFrame").show();
		if($("#flowRunDocAipFrame").attr("src")==""){
			$("#flowRunDocAipFrame").attr("src",url);
		}
	});

	$("#attach").click(function(){
		clearStyle();
		$(this).addClass("select");
		$("#frm").show();
		frm.focusAttachment();
	});

	$("#feedback").click(function(){
		clearStyle();
		$(this).addClass("select");
		$("#frm").show();
		frm.focusFeedback();
	});

	$("#child").click(function(){
		var url = contextPath+"/system/core/workflow/flowrun/flowview/childviews.jsp?runId="+runId;
		clearStyle();
		$(this).addClass("select");
		$("#childGraph").show().attr("src",url);
	});
	
	$("#graph").click(function(){
		var url = contextPath+"/system/core/workflow/flowrun/flowview/index.jsp?runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
		clearStyle();
		$(this).addClass("select");
		$("#frmGraph").show().attr("src",url);
	});

	//相关资源
	$("#relatedResource").click(function(){
		var url = contextPath+"/system/core/workflow/flowrun/prcs/relatedResource.jsp?runId="+runId;
		clearStyle();
		$(this).addClass("select");
		$("#runFrm").show().attr("src",url);
		
	});
	
	//layout.addToggleBtn("#toggleBtn", "west");

	//加载完毕后显示body主体内容
	var formUrl = "form.jsp?runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
	$("#frm").attr("src",formUrl);
	
	findPrcsId();

}
//获取当前步骤号
function findPrcsId(){
	var json=tools.requestJsonRs(contextPath+"/flowRun/findPrcsId.action",{frpSid:frpSid});
	if(json.rtState){
		prcsId=json.rtData;
	}
}
//清除导航按钮样式
function clearStyle(){
	$("#tab li").each(function(i,obj){
		$(obj).removeClass("select");
	});
	$("iframe").hide();
}

function saveRunName(){
	var runNameInput = parent.$("#runNameInput");
	if(runNameInput.length!=0 && runNameInput.val()==""){
		alert("请填写流程名称！");
		runNameInput.focus();
		return false;
	}else if(runNameInput.length!=0){
		//提交流程名称
		tools.requestJsonRs(contextPath+"/flowRun/updateRunName.action",{runId:runId,runName:runNameInput.val()});
	}
	return true;
}

function saveDocAndHide(){
	if($("#flowRunDocFrame").attr("src")!=""){
		try{
			$("#flowRunDocFrame")[0].contentWindow.DOC_saveDoc(1);
		}catch(e){
			
		}
		$("#flowRunDocFrame").hide();
	}
	if($("#flowRunDocAipFrame").attr("src")!=""){
		try{
			$("#flowRunDocAipFrame")[0].contentWindow.Save();
		}catch(e){
		
		}
	}
	clearStyle();
	$("#form").addClass("select");
	$("#frm").show();
	$("#frm").focus();
}

function saveDoc(){
	if($("#flowRunDocFrame").attr("src")!=""){
		try{
			$("#flowRunDocFrame")[0].contentWindow.DOC_saveDoc(1);
		}catch(e){
			
		}
	}
}

function saveDocAip(){
	if($("#flowRunDocAipFrame").attr("src")!=""){
		try{
			$("#flowRunDocAipFrame")[0].contentWindow.Save();
		}catch(e){
		
		}
	}
}



function  saveAipForm(){
	if($("#aipFrm").attr("src")!=""){
		try{
			$("#aipFrm")[0].contentWindow.Save();
		}catch(e){
		
		}
	}
	
}


function saveForm(){
	isSaved = 1;
	if(!saveRunName()){
		return;
	}
	saveDoc();
	saveDocAip();
	//保存签批单
	saveAipForm();
	var levelSelectCtrl = parent.$("#level");
	if(levelSelectCtrl.length!=0 && !levelSelectCtrl.attr("disabled")){
		//保存流程级别
		tools.requestJsonRs(contextPath+"/flowRun/updateRunLevel.action",{runId:runId,level:levelSelectCtrl.val()});
	}
	
	
	frm.saveFlowRunData(true,1,function(){
		frm.location.reload();
		top.$.jBox.closeTip();
	});
}

function turnNext(prcsEvent){
	if(window.hasChildUnhandledFlow=="" && !window.confirm("有子流程未办理完毕，是否强制转交？")){
		return ;
	}
	
	isSaved = 1;
	if(!saveRunName()){
		return;
	}
	saveDocAndHide();
	saveDocAip();
	saveAipForm();
	var levelSelectCtrl = parent.$("#level");
	if(levelSelectCtrl.length!=0 && !levelSelectCtrl.attr("disabled")){
		//保存流程级别
		tools.requestJsonRs(contextPath+"/flowRun/updateRunLevel.action",{runId:runId,level:levelSelectCtrl.val()});
	}
	
	frm.saveFlowRunData(false,1,function(){
		frm.location.reload();
		top.$.jBox.closeTip();
		
		var url = contextPath+"/flowRun/preTurnHandlerData.action";
		var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
		if(prcsEvent){
			para+="&prcsEvent="+encodeURI(prcsEvent);
		}
		var json = tools.requestJsonRs(url,{runId:runId,frpSid:frpSid,flowId:flowId});
		if(json.rtState){
			var opCode = json.rtData.opCode;
			if(opCode=="1"){
				var url = contextPath + "/system/core/workflow/flowrun/prcs/turn/turnFixedNext.jsp?"+para;
				bsWindow(url,"发送",{buttons:[{classStyle:"btn-win-white",name:"确定"},{classStyle:"btn-win-white",name:"取消"}],width:"800",height:"300",submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="确定"){
						if(cw.wfCluster.commit()){
							alert("发送成功");
							doPageHandler();
						}
					}else{
						return true;
					}
				}});
			}else if(opCode=="2"){
				var url = contextPath + "/system/core/workflow/flowrun/prcs/turn/turnFreeNext.jsp?"+para;
				bsWindow(url,"发送",{buttons:[{classStyle:"btn-win-white",name:"确定"},{classStyle:"btn-win-white",name:"取消"}],width:"800",height:"300",submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="确定"){
						if(cw.turnNext()){
							alert("发送成功");
							doPageHandler();
						}
					}else{
						return true;
					}
				}});
			}else{
				if(doPageHandler){
					alert("办理完毕");
					doPageHandler();
				}
			}
		}else{
			if(doPageHandler){
				doPageHandler();
			}
		}
	},true);
}

function backTo(){
	saveDocAndHide();
	frm.saveFlowRunData(false,2,function(){
		top.$.jBox.closeTip();
		var url = contextPath+"/system/core/workflow/flowrun/prcs/backTo.jsp?frpSid="+frpSid+"&runId="+runId+"&flowId="+flowId;
		bsWindow(url,"选择回退步骤",{buttons:[{classStyle:"btn-win-white",name:"确定"},{classStyle:"btn-win-white",name:"取消"}],width:"400",height:"220",submit:function(v,h){
			if(v=="确定"){
				var cw = h[0].contentWindow;
				if(cw.commit()){
					doPageHandler();
					return true;
				}
			}else{
				return true;
			}
		}});
	});
}

function backToOther(){
	saveDocAndHide();
	frm.saveFlowRunData(false,2,function(){
		top.$.jBox.closeTip();
		
		var url = contextPath+"/system/core/workflow/flowrun/prcs/backToOther.jsp?frpSid="+frpSid+"&runId="+runId+"&flowId="+flowId;
		bsWindow(url,"选择回退步骤",{buttons:[{classStyle:"btn-win-white",name:"确定"},{classStyle:"btn-win-white",name:"取消"}],width:"400",height:"200",submit:function(v,h){
			if(v=="确定"){
				var cw = h[0].contentWindow;
				if(cw.commit()){
					doPageHandler();
					return true;
				}
			}else{
				return true;
			}
		}});
	});
}

//回退到指定的步骤
function backToFixed(){
	//alert("回退到指定的步骤");
	 saveDocAndHide();
	frm.saveFlowRunData(false,2,function(){
		top.$.jBox.closeTip();
		
		var url = contextPath+"/system/core/workflow/flowrun/prcs/backToFixed.jsp?frpSid="+frpSid+"&runId="+runId+"&flowId="+flowId;
		bsWindow(url,"选择回退步骤",{buttons:[{classStyle:"btn-win-white",name:"确定"},{classStyle:"btn-win-white",name:"取消"}],width:"400",height:"200",submit:function(v,h){
			if(v=="确定"){
				var cw = h[0].contentWindow;
				if(cw.commit()){
					doPageHandler();
					return true;
				}
			}else{
				return true;
			}
		}}); 
	});
		
}

function turnEnd(){
	frm.turnEnd();
}

function doPageHandler(){
	try{
		if(xparent.doPageHandler && xparent!=self){
			xparent.doPageHandler();
		}
	}catch(e){}
	//跨窗口刷新指定IM框架的页面
	try{
		window.external.IM_RefreshForm("/system/core/workflow/flowrun/list/index.jsp");
	}catch(e){}
	CloseWindow();
}

function addPrcsUser(){
	saveDocAndHide();
	var url = contextPath+"/system/core/workflow/flowrun/prcs/addPrcsUser.jsp?frpSid="+frpSid+"&runId="+runId+"&flowId="+flowId;
	bsWindow(url,"添加加签人",{buttons:[{classStyle:"btn-win-white",name:"确定"},{classStyle:"btn-win-white",name:"取消"}],width:"400",height:"200",submit:function(v,h){
		if(v=="确定"){
			var cw = h[0].contentWindow;
			var json=cw.commit();
			if(json.rtState){
				var prcsUserDesc=json.prcsUserDesc;
				$.MsgBox.Alert_auto("已加签给"+prcsUserDesc,function(){
					window.close();
					return true;
				});
				
			}
			/* if(cw.commit()){
				top.$.jBox.tip("成功添加经办人","success");
				return true;
			} */
		}else{
			return true;
		}
	}});
}

function doDelegate(rowIndex){
	var url = contextPath+"/system/core/workflow/flowrun/prcs/delegate.jsp?frpSid="+frpSid;
	top.bsWindow(url,"委托",{buttons:[{classStyle:"btn-win-white",name:"确定"},{classStyle:"btn-win-white",name:"取消"}],width:"800",height:"200",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			if(cw.commit()){
				doPageHandler();
				return true;
			}
		}else{
			return true;
		}
	}});
}

//关注该流程
function concern(){
	saveDocAndHide();
	$.MsgBox.Confirm("确认","是否关注此工作？",function(){
		var url = contextPath+"/flowRunConcern/concern.action";
		var json = tools.requestJsonRs(url,{runId:runId});
		if(json.rtState){
			top.$.jBox.tip("已关注工作","success");
		}
	});
}

function suspend(){
	saveDocAndHide();
	$.MsgBox.Confirm("确认","是否将该工作挂起？",function(){
		var url = contextPath+"/workflow/suspend.action";
		var json = tools.requestJsonRs(url,{frpSid:frpSid});
		if(json.rtState){
			if(opener.doPageHandler){
				opener.doPageHandler();
				//跨窗口刷新指定IM框架的页面
				try{
					window.external.IM_RefreshForm("/system/core/workflow/flowrun/list/index.jsp");
				}catch(e){}
				CloseWindow();
			}
		}
	});
}

//归档（部门归档）
function archives(){
	saveDocAndHide();
	if(window.confirm("是否要将该工作归档？（归档前请注意保存表单！）")){
		//获取流程附件
		/* var json = tools.requestJsonRs(contextPath+"/flowRun/getFlowRunAttaches4Archives.action?runId="+runId);
		var attaches = [];
		for(var i=0;i<json.rtData.length;i++){
			attaches.push(json.rtData[i].sid);
		}
		openFullWindow(contextPath+"/system/core/base/dam/files/add.jsp?attaches="+attaches.join(",")+"&title="+encodeURI(runName)); */
		var url=contextPath+"/system/core/workflow/flowrun/prcs/deptArchive.jsp?runId="+runId;
		bsWindow(url ,"部门归档",{width:"400",height:"200",buttons:
			[
             {name:"确定",classStyle:"btn-alert-blue"},
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="确定"){
			     var  json=cw.archive();
			     if(json.rtState){
			    	 $.MsgBox.Alert_auto("归档成功！");
			     }
			     return true;
			}else if(v=="关闭"){
				return true;
			}
		}}); 
	     
	}
}
//转存
function repeat(){
	//saveDocAndHide();
	openWindow(contextPath+"/system/core/workflow/flowrun/prcs/repeatTo.jsp?runId="+runId,"流程文件转存",480,350);
}

function doPrint(){
	openFullWindow(contextPath+"/system/core/workflow/flowrun/print/index.jsp?runId="+runId+"&view=1&frpSid="+frpSid+"&flowId="+flowId);
}


//传阅
function view(){
	var url=contextPath+"/system/core/workflow/flowrun/prcs/viewTo.jsp?runId="+runId+"&&frpSid="+frpSid;
	bsWindow(url ,"流程传阅",{width:"600",height:"140",buttons:
		[
         {name:"传阅",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="传阅"){
		    var a=cw.commit();
		    if(a){
		    	$.MsgBox.Alert_auto("传阅成功！");
		    	return true;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

function turnEndIdFunc(){
	frm.turnEndIdFunc();
}
</script>
</head>
<body onload="doInit()" style="overflow:hidden;margin:0px;font-size:12px">

	<!-- North -->
	<div style="position:absolute;left:0px;top:0px;right:0px;height:35px;background:#f4f4f4;border-bottom:1px solid #cecece">
		<table style="width:100%;height:100%">
			<tr>
				<td style="text-align:left;">
					<div id="runNameDiv" style="font-weight:bold;font-size:14px;color:red;"></div>
				</td>
				<td style="text-align:right;padding-right:30px;color:red">
					<div id="prcsInfoDiv">
						<b >流程级别：</b>
						<select id="level" class="BigSelect" disabled>
							<option value="1">普通</option>
							<option value="2">紧急</option>
							<option value="3">加急</option>
						</select>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<!-- North1 -->
	<div style="position:absolute;left:0px;top:50px;right:0px;height:100px;">
		<div class="titlebar clearfix">
			<img class="tit_img" style="margin-right:10px;margin-left:10px" src="/common/zt_webframe/imgs/gzl/workflow_banli.png">
			<p class="title">工作办理</p>
			<ul id="tab" class="tab clearfix fl">
				<li id="form" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="select">表单信息</li>
				<li id="aipForm" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;display: none;" class="" >签批单</li>
				<li id="flowRunDoc" style="display:none;float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">正文信息</li>
				<li id="flowRunDocAip" style="display:none;float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">版式正文</li>
				<li id="attach" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">相关材料</li>
				<li id="feedback" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">办理意见</li>
				<li id="child" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">子流程</li>
				<li id="graph" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">流程图</li>
			    <li id="relatedResource" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">相关资源</li>
			</ul>
			<div class="fr" style="margin-right:10px;">
				<button id="turnNextBtn" class="btn-win-white" style="display:none;margin-right:10px;" onclick="turnNext()">发送</button>
				<span id="prcsEventDefSpan">
				</span>
				<button id="prcsFinishBtn" class="btn-win-white" style="display:none;margin-right:10px;" onclick="turnNext()">办理完毕</button>
				<button id="turnEndIdBtn" class="btn-win-white" style="display:none;margin-right:10px;" onclick="turnEndIdFunc()">结束</button>
				<button id="backToBtn" class="btn-win-white" style="display:none;margin-right:10px;" onclick="backTo()">回退</button>
				<button id="backToOtherBtn" class="btn-win-white" style="display:none;margin-right:10px;" onclick="backToOther()">回退</button>
				<button id="backToFixedBtn" class="btn-win-white" style="display:none;margin-right:10px;" onclick="backToFixed()">回退</button>
				<button id="docSendBtn" class="btn-win-white modal-menu-docsenddiv" style="display:none;margin-right:10px;" onclick="saveForm();saveDocAndHide();$(this).modal();">公文分发</button>
				<button id="docViewBtn" class="btn-win-white modal-menu-docviewdiv" style="display:none;margin-right:10px;" onclick="saveForm();saveDocAndHide();$(this).modal();">公文传阅</button>
				<button id="endBtn" class="btn-win-white" style="display:none;margin-right:10px;" onclick="turnEnd()">结束</button>
				<button id="saveBtn" class="btn-win-white" onclick="saveForm()" style=";margin-right:10px;display:none">保存</button>
				<button id="closeBtn" class="btn-win-white" onclick="CloseWindow()" style=";margin-right:10px;">关闭</button>
				<button class="btn-alert-blue" onclick="$('#quickOperDiv').toggle();">更多</button>
			</div>
			<span class="basic_border_grey fl"></span>
		</div>
	</div>
	
	<!-- Center -->
	<div style="position:absolute;left:0px;right:0px;top:100px;bottom:0px;" >
		<iframe id="frm" style="width:100%;height:100%" frameborder=0></iframe>
		<iframe id="frmGraph" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="childGraph" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="flowRunDocFrame" src="" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="flowRunDocAipFrame" src="" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="scheduleFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="taskFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="customerFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="runFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>
	    <iframe id="aipFrm" src="" style="display:none;width:100%;height:100%" frameborder=0></iframe>
	</div>

<script>


//发送公文
function doSendDoc(){
	if($("#sendDeptIds").val()==""){
		alert("请选择部门");
		return;
	}
	
	if(!checkDeliveryNum()){
		alert("打印份数格式错误！");
		return;
	} 
	
	//是否消息提醒
	var isRecRemind="";
	if($("#isRecRemind").prop("checked")==true){
		isRecRemind="1";	
	}else{
		isRecRemind="0";
	}
	//是否进行手机短信提醒
	var isPhoneRemind="";
	if($("#isPhoneRemind").prop("checked")==true){
		isPhoneRemind="1";	
	}else{
		isPhoneRemind="0";
	}
	//公文分发的 时候拼接部门打印份数和是否允许下载的json字符串
	var deptArray=$(".deptClazz");
	var str="[";
	var deptId=0;
	var printNum=0;
	var isDownLoad=0;
	for(var i=0;i<deptArray.length;i++){
		deptId=deptArray[i].children[0].title;
		if(deptArray[i].children[1].children[0].value!=null&&deptArray[i].children[1].children[0].value!=""){
			printNum=deptArray[i].children[1].children[0].value;
		}else{
			printNum=0;
		}
		
		if(deptArray[i].children[2].children[0].checked==true){
			isDownLoad=1;	
		}else{
			isDownLoad=0;	
		} 
		if(i==deptArray.length-1){
			str+="{deptId:"+deptId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"}";	
		}else{
			str+="{deptId:"+deptId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"},";
		}
		
	}
	   str+="]";
	   //alert(str);
	
	   
	   
	//获取发送的内容权限
	var content=$(".sendContent");
	var contentPriv=0;
	for(var i=0;i<content.length;i++){
		if($(content[i]).prop("checked")==true){
			contentPriv=contentPriv+parseInt($(content[i]).val());
		}
	}
	if(contentPriv==0){
		alert("请至少选择一项发送内容！");
		return;
	}
	var json = tools.requestJsonRs(contextPath+"/doc/sendDoc.action",{runId:runId,sendDeptIds:$("#sendDeptIds").val(),isRecRemind:isRecRemind,jsonStr:str,isPhoneRemind:isPhoneRemind,contentPriv:contentPriv});
	if(json.rtState){
		alert("公文已成功下发");
		//公文下发成功后清空表格的行
		$("#deptTabdelivery").children().remove();
		//清空批量设置打印份数和批量设置下载的数据
		$("#deliveryBatchPrintNum")[0].value="";
		$("#deliveryBatchDownLoad")[0].checked=false;
		//清除之前选中的部门
		$("#sendDeptIds").val("");
		$("#sendDeptNames").val("");
// 		$(".doc_send_div").modal("hide");
		$(".modal-win-close").click();
	}else{
		alert(json.rtMsg);
	}

}

//点击清空  清空公文下发部门显示列表
function clearDeptsTab(){
	$("#deptTabdelivery").children().remove();
	
}

//点击清空  清空公文部门传阅显示列表
function clearUsersTab(){
	$("#userTabView").children().remove();
	
}
//传阅公文
function doViewDoc(){
	if($("#sendUserIds").val()==""){
		alert("请选择人员");
		return;
	}
	
	
	 if(!checkViewNum()){
		alert("打印份数格式错误！");
		return;
	}
	
	
	
	//是否消息提醒
	var isReadRemind="";
	if($("#isReadRemind").prop("checked")==true){
		isReadRemind="1";	
	}else{
		isReadRemind="0";
	}
	
	//是否进行短信提醒
	var isPhoneRemind="";
	if($("#isPhoneRemind1").prop("checked")==true){
		isPhoneRemind="1";	
	}else{
		isPhoneRemind="0";
	}
	//公文传阅的 时候拼接部门打印份数和是否允许下载的json字符串
	var userArray=$(".userClazz");
	var str="[";
	var userId=0;
	var printNum=0;
	var isDownLoad=0;
	for(var i=0;i<userArray.length;i++){
		userId=userArray[i].children[0].title;
		if(userArray[i].children[1].children[0].value!=null&&userArray[i].children[1].children[0].value!=""){
			printNum=userArray[i].children[1].children[0].value;
		}else{
			printNum=0;
		}
		
		if(userArray[i].children[2].children[0].checked==true){
			isDownLoad=1;	
		}else{
			isDownLoad=0;	
		} 
		if(i==userArray.length-1){
			str+="{userId:"+userId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"}";	
		}else{
			str+="{userId:"+userId+",printNum:"+printNum+",isDownLoad:"+isDownLoad+"},";
		}
		
	}
	   str+="]";
	
	
	
	 //获取发送的内容权限
	 var content=$(".viewContent");
	 var contentPriv=0;
	 for(var i=0;i<content.length;i++){
		if($(content[i]).prop("checked")==true){
			contentPriv=contentPriv+parseInt($(content[i]).val());
		}
	}
	if(contentPriv==0){
		alert("请至少选择一项传阅内容！");
		return;
	}
	
	var json = tools.requestJsonRs(contextPath+"/doc/viewDoc.action",{runId:runId,sendUserIds:$("#sendUserIds").val(),isReadRemind:isReadRemind,jsonStr:str,isPhoneRemind:isPhoneRemind,contentPriv:contentPriv});
	if(json.rtState){
		alert("公文已成功传阅");
		//公文传阅成功后清空表格的行
		$("#userTabView").children().remove();
		//清空批量设置打印份数和批量设置下载的数据
		$("#viewBatchPrintNum")[0].value="";
		$("#viewBatchDownLoad")[0].checked=false;
		//清除之前选中的人员
		$("#sendUserIds").val("");
		$("#sendUserNames").val("");
		
// 		$("#docViewDiv").modal("hide");
		$(".modal-win-close").click();
	}else{
		alert(json.rtMsg);
	}

}
</script>

<!-- Modal -->
<div class="modal-docsenddiv">
	<div class="modal-header">
		<p class="modal-title">
			公文分发
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body">
		<div style="margin:10px;">
		    <b style='font-size:14px'>选择要发送的内容:</b>
		    <div style="margin-top: 10px;margin-bottom: 10px">
		    <input type="checkbox" value="1" class="sendContent"/>表单&nbsp;&nbsp;&nbsp;
		    <input type="checkbox" value="16" class="sendContent"/>签批单&nbsp;&nbsp;&nbsp;
		    <input type="checkbox" value="2" class="sendContent"/>正文&nbsp;&nbsp;&nbsp;
		    <input type="checkbox" value="4" class="sendContent"/>版式正文&nbsp;&nbsp;&nbsp;
		    <input type="checkbox" value="8" class="sendContent"/>附件
		    </div>
		 
			<b style='font-size:14px'>选择要发送的部门:</b>
			<div style="margin-top: 10px;margin-bottom: 10px">
			    <textarea class="readonly" id="sendDeptNames" readonly style="width:80%;height:80px"></textarea>
			    <input type="hidden" id="sendDeptIds" />
			    <a href="javascript:void(0)" onclick="selectDept(['sendDeptIds','sendDeptNames'],undefined,undefined,undefined,'showDeptTab')">选择</a>
			    &nbsp;
			    <a href="javascript:void(0)" onclick="clearData('sendDeptIds','sendDeptNames');clearDeptsTab();">清空</a>
			    <br>
			</div>
			<div style="margin-top: 10px;margin-bottom: 10px">
			   <input type="checkbox"  id="isRecRemind" name="isRecRemind"/>&nbsp;&nbsp;签收是否进行消息提醒
			   &nbsp;&nbsp;&nbsp;&nbsp;
			    <input type="checkbox"  id="isPhoneRemind" name="isPhoneRemind"/>&nbsp;&nbsp;是否进行手机短信提醒
			</div>
			
			<table  style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page' >
			  <thead>
			      <tr class='TableHeader' style='background-color:#e8ecf9'>
			      <td class="TableData" style="text-align: center;">部门名称</td>
			      <td class="TableData" style="text-align: center;">打印份数&nbsp;&nbsp;<input type="text" id="deliveryBatchPrintNum" style="width: 25px" onblur="checkBatchPrintNum(this);" />
			        &nbsp;&nbsp;<button type="button" class="btn btn-default" style="width: 50px" onclick="setBatchDeliveryPrintNum();"/>批量</button>
			      </td>
			      <td class="TableData" style="text-align: center;">是否允许下载&nbsp;&nbsp;<input type="checkBox" id="deliveryBatchDownLoad" onclick="setBatchDeliveryDownLoad();"/></td>
			      </tr>
			   </thead>
			   <tbody id="deptTabdelivery"></tbody>
			</table>
		</div>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="doSendDoc()" value = '保存'/>
	</div>
</div>

<script>
//选择部门后  调用回掉函数
function showDeptTab(itemId,itemName,itemIds,itemNames,obj,optType){
	//alert(itemId+"  "+itemName+"  "+optType);
	if(optType=="ADD_ITEM"){//增加
		var id="deptTr_"+itemId;
		//搜索行是否已经存在
		if($("#"+id).length==0){
			$("#deptTabdelivery").append("<tr id="+id+" class='deptClazz'>"+
					"<td class='TableData' style='text-align: center;' title="+itemId+">"+itemName+"</td>"+
					"<td class='TableData' style='text-align: center;'><input type='text' class='delivertPrintNum' /></td>"+
					"<td class='TableData' style='text-align: center;'><input type='checkbox' class='deliveryDownLoad'/></td>"+
			        "</tr>");			
		}		
	}else if(optType=="REMOVE_ITEM"){//移除
	   $("#deptTr_"+itemId).remove();
	}
}

//批量设置打印的份数
function setBatchDeliveryPrintNum(){
	//获取批量打印数量的数值
	var batchPrintNum=$("#deliveryBatchPrintNum")[0].value;
	
	var str= "^\\d+$";
	var reg=new RegExp(str); 
	if(!reg.test(batchPrintNum)){
		alert("批量打印份数格式错误！");
		return;	
	}
	
	
	if(batchPrintNum==""||batchPrintNum==null){
		batchPrintNum=0;
	}
	//获取所有部门打印分数的input标签
	var deliveryPrintNumArray=$(".delivertPrintNum");
	for(var i=0;i<deliveryPrintNumArray.length;i++){
		deliveryPrintNumArray[i].value=batchPrintNum;
	}
}




//批量设置是否下载
function  setBatchDeliveryDownLoad(){
	//获取所有部门是否可以下载的复选框数组
	var deliveryDownLoadArry=$(".deliveryDownLoad");
	//获取是否批量下载
	if($("#deliveryBatchDownLoad")[0].checked==true){
		for(var i=0;i<deliveryDownLoadArry.length;i++){			
			deliveryDownLoadArry[i].checked=true;
		}	
	}else{
		for(var i=0;i<deliveryDownLoadArry.length;i++){			
			deliveryDownLoadArry[i].checked=false;
		}		
	}
	
}

//验证输入的值是非负整数
function checkDeliveryNum(){
	var str= "^\\d+$";
	var reg=new RegExp(str); 
	//获取所有部门打印分数的input标签
	var delivertPrintNumArray=$(".delivertPrintNum");
	for(var i=0;i<delivertPrintNumArray.length;i++){
		if(!reg.test(delivertPrintNumArray[i].value)){
			//alert("打印份数格式错误！");
			return false;
		}else{
			return true;
		}
	}
}

//公文分发  检验批量打印份数的格式
function checkBatchPrintNum(obj){
	var str= "^\\d+$";
	var reg=new RegExp(str); 
	if(!reg.test(obj.value)){
		alert("批量打印份数格式错误！");
	}
}


//
</script>

<!-- 公文传阅窗口 -->
<!-- Modal -->
<div class="modal-docviewdiv">
	<div class="modal-header">
		<p class="modal-title">
			公文传阅
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body">
		<div style="margin:10px;">
		   <b style='font-size:14px'>选择要传阅的内容:</b>
		   <div style="margin-top: 10px;margin-bottom: 10px">
		     <input type="checkbox" value="1" class="viewContent"/>表单&nbsp;&nbsp;&nbsp;
		     <input type="checkbox" value="16" class="viewContent"/>签批单&nbsp;&nbsp;&nbsp;
		     <input type="checkbox" value="2" class="viewContent"/>正文&nbsp;&nbsp;&nbsp;
		     <input type="checkbox" value="4" class="viewContent"/>版式正文&nbsp;&nbsp;&nbsp;
		     <input type="checkbox" value="8" class="viewContent"/>附件
		    </div>
		
		
			<b style='font-size:14px'>选择要传阅的人员:</b>
			<div style="margin-bottom: 10px;margin-top: 10px">
			   <textarea class="BigTextarea readonly" id="sendUserNames" readonly style="width:480px;height:80px"></textarea>
			   <input type="hidden" id="sendUserIds" />
			   <a href="javascript:void(0)" onclick="selectUser(['sendUserIds','sendUserNames'],undefined,undefined,undefined,'showUserTab')">选择</a>
			   &nbsp;
			   <a href="javascript:void(0)" onclick="clearData('sendUserIds','sendUserNames');clearUsersTab();">清空</a>
			</div>
			
	        <div style="margin-top: 10px;margin-bottom: 10px">
	           <input type="checkbox"  id="isReadRemind" name="isReadRemind"/>&nbsp;&nbsp;签阅是否进行消息提醒
			   &nbsp;&nbsp;&nbsp;&nbsp;
			   <input type="checkbox"  id="isPhoneRemind1" name="isPhoneRemind1"/>&nbsp;&nbsp;是否进行短信提醒
	        </div>
			
	        <table  style='border:#dddddd 2px solid;' width='100%' class='TableBlock_page'  >
			   <thead>
			   <tr class='TableHeader' style='background-color:#e8ecf9'>
			      <td class="TableData" style="text-align: center;">人员名称</td>
			      <td class="TableData" style="text-align: center;">打印份数&nbsp;&nbsp;<input type="text" id="viewBatchPrintNum" style="width: 25px" onblur="checkBatchPrintNum(this);"/>
			        &nbsp;&nbsp;<button type="button" class="btn btn-default" style="width: 50px" onclick="setBatchViewPrintNum();"/>批量</button></td>
			      <td class="TableData" style="text-align: center;">是否允许下载&nbsp;&nbsp;<input type="checkBox" id="viewBatchDownLoad" onclick="setBatchViewDownLoad();"/></td>
			   </tr>
			   </thead>
			   <tbody id="userTabView">
			   
			   </tbody>
			</table>   
		</div>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="doViewDoc()" value = '保存'/>
	</div>
</div>

<script>
//选择人员后  调用回掉函数
function showUserTab(itemId,itemName,itemIds,itemNames,obj,optType){
	//alert(itemId+"  "+itemName+"  "+optType);
	if(optType=="ADD_ITEM"){//增加
		var id="userTr_"+itemId;
	
        if($("#"+id).length==0){
        	$("#userTabView").append("<tr id="+id+" class='userClazz'>"+
    				"<td class='TableData' style='text-align: center;' title="+itemId+">"+itemName+"</td>"+
    				"<td class='TableData' style='text-align: center;'><input type='text' class='viewPrintNum'  /></td>"+
    				"<td class='TableData' style='text-align: center;'><input type='checkbox' class='viewDownLoad'/></td>"+
    		        "</tr>");		
        }
		
	}else if(optType=="REMOVE_ITEM"){//移除
	   $("#userTr_"+itemId).remove();
	}
}


//批量设置打印的份数
function setBatchViewPrintNum(){
	//获取批量打印数量的数值
	var batchPrintNum=$("#viewBatchPrintNum")[0].value;
	
	var str= "^\\d+$";
	var reg=new RegExp(str); 
	if(!reg.test(batchPrintNum)){
		alert("批量打印份数格式错误！");
		return;	
	}
	
	if(batchPrintNum==""||batchPrintNum==null){
		batchPrintNum=0;
	}
	//获取所有部门打印分数的input标签
	var viewPrintNumArray=$(".viewPrintNum");
	for(var i=0;i<viewPrintNumArray.length;i++){
		viewPrintNumArray[i].value=batchPrintNum;
	}
}




//批量设置是否下载
function  setBatchViewDownLoad(){
	//获取所有部门是否可以下载的复选框数组
	var viewDownLoadArry=$(".viewDownLoad");
	//获取是否批量下载
	if($("#viewBatchDownLoad")[0].checked==true){
		for(var i=0;i<viewDownLoadArry.length;i++){			
			viewDownLoadArry[i].checked=true;
		}	
	}else{
		for(var i=0;i<viewDownLoadArry.length;i++){			
			viewDownLoadArry[i].checked=false;
		}		
	}
	
}


//验证输入的值是非负整数
function checkViewNum(obj){
	var str= "^\\d+$";
	var reg=new RegExp(str); 
	//获取所有部门打印分数的input标签
	var viewPrintNumArray=$(".viewPrintNum");
	for(var i=0;i<viewPrintNumArray.length;i++){
		if(!reg.test(viewPrintNumArray[i].value)){
			//alert("打印份数格式错误！");
			return false;
		}else{
			return true;
		}
	}
}


</script>

<div id="quickOperDiv" style="width:180px;position:absolute;top:130px;right:20px;background:#fff;display:none">
<table style="width:100%;text-align:center;font-size:12px;">
	<tr>
		<td id="prcsUserBtn" style="text-align:center;cursor:pointer" onclick="addPrcsUser()">
			<img src="<%=systemImagePath %>/workflow/prcsUser.png" title="查看或增加本步骤经办人" />
			<br>
			<span>加签</span>
		</td>
		<td style="text-align:center;cursor:pointer" onclick="concern()">
			<img src="<%=systemImagePath %>/workflow/concern.png" title="关注该工作" />
			<br>
			<span>关注</span>
		</td>
		<td style="text-align:center;cursor:pointer" onclick="suspend()">
			<img src="<%=systemImagePath %>/workflow/suspend.png" title="将工作挂起" />
			<br>
			<span>挂起</span>
		</td>
	</tr>
	<tr>
		<td id="archivesBtn" style="text-align:center;cursor:pointer;" onclick="archives()">
			<img src="<%=systemImagePath %>/workflow/archives.png" title="归档" />
			<br>
			<span>归档</span>
		</td>
		<td style="text-align:center;cursor:pointer;" onclick="doPrint()">
			<img src="<%=systemImagePath %>/workflow/print.png" title="打印" />
			<br>
			<span>打印</span>
		</td>
		<td id="delegateBtn" style="text-align:center;cursor:pointer;" onclick="doDelegate()">
			<img src="<%=systemImagePath %>/workflow/delegate.png" title="委托" />
			<br>
			<span>委托</span>
		</td>
	</tr>
		<tr>
		<td id="repeatBtn" style="text-align:center;cursor:pointer;" onclick="repeat()">
			<img src="<%=systemImagePath %>/workflow/restore.png" title="转存" />
			<br>
			<span>转存</span>
		</td>
		<td  style="text-align:center;cursor:pointer;" onclick="view()">
			<img src="<%=systemImagePath %>/workflow/chuanyue.png" title="传阅" />
			<br>
			<span>传阅</span>
		</td>
		<td></td>
	</tr>
</table>
</div>
</body>
</html>