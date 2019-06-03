<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%
	int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	int view = TeeStringUtil.getInteger(request.getParameter("view"),0);
	String thread_local_archives = TeeStringUtil.getString(request
			.getParameter("thread_local_archives"), "");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/tabs/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>

<script>
var runId = <%=runId%>;
var frpSid = <%=frpSid%>;
var view = <%=view%>;
var thread_local_archives = "<%=thread_local_archives%>";
var dynamicUrl = "";
function doInit(){
	if((view & 1)==1){
		$("#form").attr("checked",true);
	}
	if((view & 2)==2){
		$("#attach").attr("checked",true);
	}
	if((view & 4)==4){
		$("#feedback").attr("checked",true);
	}
	if((view & 8)==8){
		$("#graph").attr("checked",true);
	}
	if((view & 16)==16){
		$("#viewInfo").attr("checked",true);
	}
	if((view & 32)==32){
		$("#docInfo").attr("checked",true);
	}
	dynamicUrl = contextPath+"/system/core/workflow/flowrun/print/print.jsp?runId="+runId+"&view="+view+"&frpSid="+frpSid+"&thread_local_archives="+thread_local_archives;
	$("#frame0").attr("src",dynamicUrl);

	//获取版式文件打印模版
	var url = contextPath+"/flowPrintTemplate/selectModulByFlowRunPrcs.action";
	var json = tools.requestJsonRs(url,{frpSid:frpSid,thread_local_archives:thread_local_archives});
	if(json.rtState){
		var list = json.rtData;
		var options = "";
		for(var i=0;i<list.length;i++){
			var item = list[i];
			options+="<option value='AIP_"+item.sid+"'>"+item.modulName+"</option>";
		}
		$("#printTemplate").append(options);
	}
	
	//获取Word打印模版
	var url = contextPath+"/ntkoPrintTemplate/list.action?runId="+runId;
	var json = tools.requestJsonRs(url,{thread_local_archives:thread_local_archives});
	var list = json.rtData;
	var html = "";
	for(var i=0;i<list.length;i++){
		var item = list[i];
		html+="<option value='WORD_"+item.attach.sid+"'>"+item.modelName+"</option>";
	}
	$("#printTemplate").append(html);
	
	//如果是归档查询的话，则隐藏掉一些操作
	if(thread_local_archives!=""){
		$("#childFlow").hide();
		$("#moreBtn").hide();
	}
}

function refresh(obj){
	if(obj.checked){
		view+=parseInt(obj.value);
	}else{
		view-=parseInt(obj.value);
	}
	dynamicUrl = contextPath+"/system/core/workflow/flowrun/print/print.jsp?runId="+runId+"&view="+view+"&frpSid="+frpSid+"&thread_local_archives="+thread_local_archives;
	$("#frame0").attr("src",dynamicUrl);
}

function changeTemplate(obj){
	var val = obj.value;
	if(val.indexOf("AIP")!=-1){
		val = val.split("_")[1];
		$("#frame0").attr("src",contextPath+"/system/core/workflow/workmanage/flowprinttpl/modulPrint.jsp?runId="+runId+"&sid="+val+"&thread_local_archives="+thread_local_archives);
		$("#printBtn").hide();
		$("#exportWordBtn").hide();
		$("#exportHtmlBtn").hide();
		$("#printTmplBtn").show();
		$("#printTmplSimpleBtn").show();
		$("#printTmplBlankBtn").show();
		$("#wordPrintBtn").hide();
	}else if(val.indexOf("WORD")!=-1){
		$("#printBtn").hide();
		$("#exportWordBtn").hide();
		$("#exportHtmlBtn").hide();
		$("#printTmplBtn").hide();
		$("#printTmplSimpleBtn").hide();
		$("#printTmplBlankBtn").hide();
		$("#wordPrintBtn").show();
	}else{
		$("#frame0").attr("src",dynamicUrl);
		$("#printBtn").show();
		$("#exportWordBtn").show();
		$("#exportHtmlBtn").show();
		$("#printTmplBtn").hide();
		$("#printTmplSimpleBtn").hide();
		$("#printTmplBlankBtn").hide();
		$("#wordPrintBtn").hide();
	}
}

function wordPrint(){
	var attachId = $("#printTemplate").val().split("_")[1];
	$("#frame0").attr("src",contextPath+"/system/core/workflow/flowrun/print/indexNtkoPrint.jsp?attachId="+attachId+"&thread_local_archives="+thread_local_archives);
}

function doExport(){
	//$("#exportHtmlBtn").attr("title","刷新页面后可继续导出").attr("value","导出中请等待");
	//$("#exportHtmlBtn")[0].onclick=function(){};
	$("#frame1").attr("src",contextPath+"/flowRun/exportFlowRun.action?runId="+runId+"&view=15&frpSid="+frpSid+"&thread_local_archives="+thread_local_archives);
}

function childFlow(){
	openFullWindow(contextPath+"/system/core/workflow/flowrun/flowview/childviews.jsp?runId="+runId+"&thread_local_archives="+thread_local_archives,"子流程查看");
}

function toNotify(){
	openFullWindow(contextPath+"/system/core/workflow/flowrun/print/notify.jsp?runId="+runId+"&frpSid="+frpSid,"转公告");
}

function toMail(){
	openFullWindow(contextPath+"/system/core/workflow/flowrun/print/mail.jsp?runId="+runId+"&frpSid="+frpSid,"转邮件");
}

function print0(){
	$("#frame0")[0].contentWindow.printit();
}
</script>

</head>
<body onload="doInit()" style="margin:0px;overflow:hidden" id="content">
<div style="position:absolute;top:0px;left:0px;right:0px;height:35px;border-bottom:1px solid #cdcdcd;background:#f0f0f0">
	<table style="width:100%;height:100%;font-size:12px">
		<tr>
			<td>
				<b style="color:red">
					流水号：<%=runId%>&nbsp;&nbsp;
				</b>
				<input type="checkbox" id="form" value="1" onclick="refresh(this)"/><label for="form">&nbsp;表单</label>
				<input type="checkbox" id="attach" value="2" onclick="refresh(this)"/><label for="attach">&nbsp;附件</label>
				<input type="checkbox" id="docInfo" value="32" onclick="refresh(this)"/><label for="viewInfo">&nbsp;正文</label>
				<input type="checkbox" id="feedback" value="4" onclick="refresh(this)"/><label for="feedback">&nbsp;会签</label>
				<input type="checkbox" id="graph" value="8" onclick="refresh(this)"/><label for="graph">&nbsp;流程步骤</label>
				<input type="checkbox" id="viewInfo" value="16" onclick="refresh(this)"/><label for="viewInfo">&nbsp;查阅情况</label>
				<button type="button" id="childFlow" onclick="childFlow()" class="btn btn-link"/><i class="glyphicon glyphicon-random"></i>&nbsp;子流程</button>
			</td>
		</tr>
	</table>
</div>
<div style="position:absolute;top:36px;left:0px;right:0px;bottom:36px;">
	<iframe id="frame0" style="height:100%;width:100%" frameborder=0></iframe>
</div>
<div style="position:absolute;left:0px;right:0px;bottom:0px;height:35px;border-top:1px solid #cdcdcd;background:#f0f0f0">
	<table style="width:100%;height:100%;font-size:12px">
		<tr>
			<td>
				打印模版：
				<select style="margin-right:20px;" id="printTemplate" onchange="changeTemplate(this)" class="BigSelect">
					<option value="0">网页表单</option>
				</select>
			</td>
			<td style="text-align:right">
				<!-- Small button group -->
				<div id="moreBtn" class="btn-group  dropup">
				  <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    更多 <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" style="text-align:left">
				   	<li><a href="#" onclick="toNotify()">转公告</a></li>
				   	<li><a href="#" onclick="toMail()">转邮件</a></li>
				  </ul>
				</div>
				
				<!-- 原始表单按钮组 -->
				<button type="button" id="printBtn" class="btn btn-primary" onclick="window.frames['frame0'].focus();print0();"><i class="glyphicon glyphicon-print"></i>&nbsp;打印</button>
				<button type="button" id="printExploreBtn" class="btn btn-primary" onclick="openFullWindow('<%=contextPath %>/system/core/workflow/flowrun/print/print.jsp?runId=<%=runId %>&view=<%=view %>&frpSid=<%=frpSid%>&thread_local_archives=<%=thread_local_archives%>');"><i class="glyphicon glyphicon-search"></i>&nbsp;打印预览</button>
				<button type="button" id="exportHtmlBtn" class="btn btn-primary" onclick="doExport()"><i class="glyphicon glyphicon-download"></i>&nbsp;导出HTML</button>
				
				<!-- AIP表单按钮组 -->
				<button type="button" id="printTmplBtn" style="display:none" class="btn btn-primary" onclick="$('#frame0')[0].contentWindow.AIPPrint(0);"><i class="glyphicon glyphicon-print"></i>&nbsp;打印</button>
				<button type="button" id="printTmplSimpleBtn" style="display:none" class="btn btn-primary" onclick="$('#frame0')[0].contentWindow.AIPPrint(1);"><i class="glyphicon glyphicon-print"></i>&nbsp;套打</button>
				<button type="button" id="printTmplBlankBtn" style="display:none" class="btn btn-primary" onclick="$('#frame0')[0].contentWindow.AIPPrint(2);"><i class="glyphicon glyphicon-print"></i>&nbsp;打印模板</button>
				
				<!-- Word打印按钮组 -->
				<button type="button" id="wordPrintBtn" style="display:none" class="btn btn-primary" onclick="wordPrint()"><i class="glyphicon glyphicon-print"></i>&nbsp;Word打印</button>
				
				<button type="button" id="closeBtn" value="关闭" class="btn btn-primary" onclick="CloseWindow();"><i class="glyphicon glyphicon-remove"></i>&nbsp;关闭</button>
				
			</td>
		</tr>
	</table>
</div>
<iframe id="frame1" style="display:none"></iframe>
</body>
</html>