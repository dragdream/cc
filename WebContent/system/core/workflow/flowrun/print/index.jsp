<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%
    int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"),0);
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	int view = TeeStringUtil.getInteger(request.getParameter("view"),0);
	String thread_local_archives = TeeStringUtil.getString(request
			.getParameter("thread_local_archives"), "");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys2.0.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/core/email/css/email.css">
<title>流程查阅</title>
<script>
var flowId=<%=flowId %>;
var runId = "<%=runId%>";
var frpSid = "<%=frpSid%>";
var view = "<%=view%>";
var thread_local_archives = "<%=thread_local_archives%>";

function doInit(){
	//渲染流水号信息
	$("#runIdDiv").append(runId);
	
	 //获取Word打印模版
	getPrintTemplate();
	
	 //根据runId获取flowTypeId
	 var url=contextPath+"/flowRun/getFlowIdByRunId.action";
	 var j=tools.requestJsonRs(url,{runId:runId});
	 if(j.rtState){
		 flowId=j.rtData;
	 }
	 
	 //根据flowType的文档类型  判断正文和版式正文的页签显示情况
	 var url=contextPath+"/flowType/get.action";
	 var json=tools.requestJsonRs(url,{flowTypeId:flowId,runId:runId});
	 if(json.rtState){
		 var data=json.rtData;
		 if(data.hasDoc==1||data.hasDoc==3){//有正文
			 $("#doc").show();
		 }else{
			 $("#doc").hide();
		 }
		 
		 if(data.hasDoc==2||data.hasDoc==3){//有版式正文
			 $("#aip").show();
		 }else{
			 $("#aip").hide();
		 }
	 }
	 
	//如果是归档查询的话，则隐藏掉一些操作
	if(thread_local_archives!=""){
		$("#child").hide();//隐藏子流程
		$("#moreBtnDiv").hide();//隐藏更多按钮
	}
	
	
	//点击表单的事件
	$("#form").click(function(){
		parent.$("#messDiv").hide();
		clearStyle();
		$(this).addClass("select");
		$("#commonFrm").show();
		$("#commonFrm").attr("src",contextPath+"/system/core/workflow/flowrun/print/print.jsp?runId="+runId+"&view=1&frpSid="+frpSid+"&thread_local_archives="+thread_local_archives);
		$("#printBtn").show();
		$("#printExploreBtn").show();
		$("#prcsInfoDiv").show();
		//重置Word打印模版
		$("select:first option:first").attr("selected",true);
	});
	
	
	//签批单
	$("#aipForm").click(function(){
		$("#printBtn").show();
		$("#printExploreBtn").hide();
		var url=contextPath+"/system/core/workflow/flowrun/print/aipForm.jsp?flowId="+flowId+"&runId="+runId+"&frpSid="+frpSid;
		clearStyle();
		$(this).addClass("select");
		$("#aipFrm1").show();
		if($("#aipFrm1").attr("src")==""){
			$("#aipFrm1").attr("src",url);
		}
	});
	
	
	//点击附件的事件
	$("#attach").click(function(){
		parent.$("#messDiv").hide();
		clearStyle();
		$(this).addClass("select");
		$("#commonFrm").show();
		$("#commonFrm").attr("src",contextPath+"/system/core/workflow/flowrun/print/print.jsp?runId="+runId+"&view=2&frpSid="+frpSid+"&thread_local_archives="+thread_local_archives);
		$("#printBtn").show();
		$("#printExploreBtn").show();
		
	});
	
	//点击会签的事件
	$("#feedback").click(function(){
		parent.$("#messDiv").hide();
		clearStyle();
		$(this).addClass("select");
		$("#commonFrm").show();
		$("#commonFrm").attr("src",contextPath+"/system/core/workflow/flowrun/print/print.jsp?runId="+runId+"&view=4&frpSid="+frpSid+"&thread_local_archives="+thread_local_archives);
		$("#printBtn").show();
		$("#printExploreBtn").show();
	});
	
	//点击流程步骤的事件
	$("#prcs").click(function(){
		parent.$("#messDiv").hide();
		clearStyle();
		$(this).addClass("select");
		$("#commonFrm").show();
		$("#commonFrm").attr("src",contextPath+"/system/core/workflow/flowrun/print/print.jsp?runId="+runId+"&view=8&frpSid="+frpSid+"&thread_local_archives="+thread_local_archives);
		$("#printBtn").show();
		$("#printExploreBtn").show();
	});
	
	//点击查阅情况
	$("#lookup").click(function(){
		parent.$("#messDiv").hide();
		clearStyle();
		$(this).addClass("select");
		$("#commonFrm").show();
		$("#commonFrm").attr("src",contextPath+"/system/core/workflow/flowrun/print/print.jsp?runId="+runId+"&view=16&frpSid="+frpSid+"&thread_local_archives="+thread_local_archives);
		$("#printBtn").show();
		$("#printExploreBtn").show();
	});
	
	//子流程
	$("#child").click(function(){
		parent.$("#messDiv").hide();
		clearStyle();
		$(this).addClass("select");
		$("#childFrm").show();
		$("#childFrm").attr("src",contextPath+"/system/core/workflow/flowrun/flowview/childviews.jsp?runId="+runId+"&thread_local_archives="+thread_local_archives);
		$("#printBtn").hide();
		$("#printExploreBtn").hide();
	});
	
	//正文
	$("#doc").click(function(){
		parent.$("#messDiv").hide();
		clearStyle();
		$(this).addClass("select");
		$("#docFrm").show();
		$("#docFrm").attr("src",contextPath+"/system/core/workflow/flowrun/print/doc.jsp?runId="+runId+"&thread_local_archives="+thread_local_archives+"&view=32&frpSid="+frpSid);
		$("#printBtn").show();
		$("#printExploreBtn").hide();
	});
	
	//版式正文
	$("#aip").click(function(){
		parent.$("#messDiv").hide();
		clearStyle();
		$(this).addClass("select");
		$("#aipFrm").show();
		$("#aipFrm").attr("src",contextPath+"/system/core/workflow/flowrun/print/aip.jsp?runId="+runId+"&thread_local_archives="+thread_local_archives+"&view=32&frpSid="+frpSid);
		$("#printBtn").show();
		$("#printExploreBtn").hide();
	});
	
	
	//相关资源
	$("#relatedResource").click(function(){
		var url = contextPath+"/system/core/workflow/flowrun/print/relatedResource.jsp?runId="+runId;
		clearStyle();
		$(this).addClass("select");
		$("#runFrm").show().attr("src",url);
		
	});
	
	
	//第一次加载显示表单信息
	$("#form").click();
}

//获取word打印模板   Html打印模板
function getPrintTemplate(){
	//获取Word打印模版
	var url = contextPath+"/ntkoPrintTemplate/list.action?runId="+runId;
	var json = tools.requestJsonRs(url,{thread_local_archives:thread_local_archives});
	var list = json.rtData;
	var html = "";
	for(var i=0;i<list.length;i++){
		var item = list[i];
		html+="<option value='WORD_"+item.attach.sid+"'>"+item.modelName+"</option>";
	}
	
	//获取Html打印模板
	var url1 = contextPath+"/teeHtmlPrintTemplateController/listByRunId.action?runId="+runId;
	var json1 = tools.requestJsonRs(url1,{thread_local_archives:thread_local_archives});
	var list1 = json1.rtData;
	for(var j=0;j<list1.length;j++){
		var item = list1[j];
		html+="<option value='HTML_"+item.sid+"'>"+item.templateName+"</option>";
	}
	
	$("#printTemplate").append(html);
}


//清除导航按钮样式
function clearStyle(){
	$("#tab li").each(function(i,obj){
		$(obj).removeClass("select");
	});
	$("iframe").hide();
	$("#prcsInfoDiv").hide();
	$("#wordPrintBtn").hide();
}

function wordPrint(){
	$("#wordPrintFrm")[0].contentWindow.DOC_printDoc();
}

//打印
function print0(){
	$("iframe:visible")[0].contentWindow.printit();
}

function printexplore(){
	$("iframe:visible")[0].contentWindow.printexplore();
}

//转公告
function toNotify(){
	openFullWindow(contextPath+"/system/core/workflow/flowrun/print/notify.jsp?runId="+runId+"&frpSid="+frpSid,"转公告");
}

//转邮件
function toMail(){
	openFullWindow(contextPath+"/system/core/workflow/flowrun/print/mail.jsp?runId="+runId+"&frpSid="+frpSid,"转邮件");
}
//导出HTML
function doExport(){
	$("#exportFrm").attr("src",contextPath+"/flowRun/exportFlowRun.action?runId="+runId+"&view=15&frpSid="+frpSid+"&thread_local_archives="+thread_local_archives);
}


function changeTemplate(obj){
	var val = obj.value;
	clearStyle();
	$("#prcsInfoDiv").show();
	if(val.indexOf("AIP")!=-1){
// 		val = val.split("_")[1];
// 		$("#frame0").attr("src",contextPath+"/system/core/workflow/workmanage/flowprinttpl/modulPrint.jsp?runId="+runId+"&sid="+val+"&thread_local_archives="+thread_local_archives);
// 		$("#printBtn").hide();
// 		$("#exportWordBtn").hide();
// 		$("#exportHtmlBtn").hide();
// 		$("#printTmplBtn").show();
// 		$("#printTmplSimpleBtn").show();
// 		$("#printTmplBlankBtn").show();
// 		$("#wordPrintBtn").hide();
	}else if(val.indexOf("WORD")!=-1){
		$("#printBtn").hide();
		$("#exportWordBtn").hide();
		$("#wordPrintBtn").show();
		$("#printExploreBtn").hide();
		$("iframe").hide();
		var attachId = $("#printTemplate").val().split("_")[1];
		$("#wordPrintFrm").show().attr("src",contextPath+"/system/core/workflow/flowrun/print/indexNtkoPrintNew.jsp?attachId="+attachId+"&thread_local_archives="+thread_local_archives+"&runId="+runId);
	}else if(val.indexOf("HTML")!=-1){//html打印模板
		$("#printBtn").show();
		$("#exportWordBtn").hide();
		$("#wordPrintBtn").hide();
		$("iframe").hide();
		
		var sid = $("#printTemplate").val().split("_")[1];
		//$("#commonFrm").show().attr("src",contextPath+"/teeHtmlPrintTemplateController/printExplore.action?sid="+sid+"&runId="+runId+"&thread_local_archives="+thread_local_archives);
		$("#commonFrm").show().attr("src",contextPath+"/system/core/workflow/flowrun/print/htmlPrint.jsp?sid="+sid+"&runId="+runId+"&thread_local_archives="+thread_local_archives);
	}else{
		$("#formBtn").click();
		$("#printBtn").show();
		$("#wordPrintBtn").hide();
	}
}

</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">

	<!-- North -->
	<div style="position:absolute;left:0px;top:0px;right:0px;height:35px;background:#f4f4f4;border-bottom:1px solid #cecece">
		<table style="width:100%;height:100%;margin-left: 10px">
			<tr>
				<td style="text-align:left;">
					<div id="runIdDiv" style="font-weight:bold;font-size:14px;color:red;">流水号：</div>
				</td>
				<td style="text-align:right;padding-right:30px;">
					<div id="prcsInfoDiv">
						打印模板：
						<select id="printTemplate" onchange="changeTemplate(this)" class="BigSelect" >
							<option value="0">网页表单</option>
						</select>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<!-- North1 -->
	<div style="position:absolute;left:10px;top:50px;right:10px;height:100px;">
		<div class="titlebar clearfix">
			<img class="tit_img" style="margin-right:10px;margin-left:0px" src="/common/zt_webframe/imgs/gzl/workflow_banli.png">
			<p class="title" id="runNameTitle">流程查阅</p>
			<ul id="tab" class="tab clearfix fl">
				<li id="form" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="select">表单</li>
<!-- 				<li id="aipForm" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">签批单</li> -->
				<li id="doc" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">正文</li>
<!-- 				<li id="aip" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">版式正文</li> -->
				<li id="attach" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">相关材料</li>
				<li id="feedback" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">办理意见</li>
				<li id="prcs" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">流程步骤</li>
				<li id="lookup" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">查阅情况</li>
				<li id="child" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">子流程</li>
				<li id="relatedResource" style="float:left;margin-right:20px;line-height:25px;cursor: pointer;" class="">相关资源</li>
			</ul>
			<div class="fr right clearfix" style="margin-right:10px;">
				<!-- 原始表单按钮组 -->
				<button type="button" id="printBtn" class="btn-win-white fl" onclick="window.frames['commonFrm'].focus();print0();">打印</button>
				<button type="button" id="printExploreBtn" class="btn-win-white fl" onclick="printexplore()">打印预览</button>
				<button type="button" id="exportHtmlBtn" class="btn-win-white fl" onclick="doExport()">导出HTML</button>
				
				<!-- AIP表单按钮组 -->
				<button type="button" id="printTmplBtn" style="display:none" class="btn-win-white fl" onclick="$('#frame0')[0].contentWindow.AIPPrint(0);">打印</button>
				<button type="button" id="printTmplSimpleBtn" style="display:none" class="btn-win-white fl" onclick="$('#frame0')[0].contentWindow.AIPPrint(1);">套打</button>
				<button type="button" id="printTmplBlankBtn" style="display:none" class="btn-win-white fl" onclick="$('#frame0')[0].contentWindow.AIPPrint(2);">打印模板</button>
				
				<!-- Word打印按钮组 -->
				<button type="button" id="wordPrintBtn" style="display:none" class="btn-win-white fl" onclick="wordPrint()">Word打印</button>
				
			
			    <div class="btn-group fl" id="moreBtnDiv">
				  <button type="button" class="btn-win-white btn-menu">
				     更多<span class="caret-down"></span>
				  </button>
				  <ul class="btn-content" id="mailBoxList">
				    <li onclick="toNotify();"><a href="#" >转公告</a></li>
<!-- 				    <li onclick="toMail();"><a href="#" >转邮件</a></li> -->
				  </ul>
			    </div>
			
			
			    <button type="button" id="closeBtn" value="关闭" class="btn-del-red fl" onclick="CloseWindow();">关闭</button>			
			</div>
			
			
			<span class="basic_border_grey fl"></span>
		</div>
	</div>

	<!-- Center -->
	<div style="position:absolute;left:10px;right:10px;top:110px;bottom:0px;">
	    <div style="display:none;" id="messDiv"></div>
		<iframe id="commonFrm" style="width:100%;height:100%" frameborder=0></iframe>
		<iframe id="docFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="aipFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="childFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>
		<iframe id="wordPrintFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>
<!-- 		<iframe id="HtmlPrintFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe> -->
	    <iframe id="runFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>
	    <iframe id="aipFrm1"  src="" style="display:none;width:100%;height:100%" frameborder=0></iframe>
	</div>
	<iframe id="exportFrm" style="display:none;width:100%;height:100%" frameborder=0></iframe>

</body>
</html>