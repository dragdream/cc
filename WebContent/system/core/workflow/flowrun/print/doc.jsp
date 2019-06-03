<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
int view = TeeStringUtil.getInteger(request.getParameter("view"),0);
String thread_local_archives = TeeStringUtil.getString(request
		.getParameter("thread_local_archives"), "");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>正文</title>
</head>
<script type="text/javascript">
var runId = "<%=runId%>";
var frpSid = "<%=frpSid%>";
var view = "<%=view%>";
var thread_local_archives = "<%=thread_local_archives%>";

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
		var docInfo = json.rtData.docInfo;
		if(docInfo!=null && docInfo){
			$("#docinfo").show();
			renderDocInfo(docInfo);
		}else{
			parent.$("#printBtn").hide();
			parent.$("#messDiv").show();
			parent.messageMsg("暂无正文信息！","messDiv","info");
		}
	}else{
		parent.$("#messDiv").show();
		parent.messageMsg(json.rtMsg,"messDiv","info");
	}
	
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

</script>
<body onload="doInit()" style="overflow-y:hidden ">
	<div id="docinfo" style="margin-top:3px;display: none;width: 100%;height: 100%">
			<%=TeeSysProps.getString("NTKO_DOM") %>
	</div>
	
	<script>
	if(isNaN(Number(view))){
		view = tools.decode64(view.replace(/@/,"="));
	}
	
	function printit(){
		TANGER_OCX_OBJ.printout(true);
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