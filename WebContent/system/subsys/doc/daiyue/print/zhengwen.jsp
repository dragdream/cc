<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/header2.0.jsp"%>
<%
	String uuid = TeeStringUtil.getString(request.getParameter("uuid"));
	TeePerson loginUser = (TeePerson) session
			.getAttribute(TeeConst.LOGIN_USER);
%>
<script>
function doInit(){
	//判断是否支持下载
	var isDownLoad=parent.data.isDownLoad;
	if(isDownLoad!=1&&isDownLoad!="1"){
		$("#zwDownLoad").hide();
	}
	
	if(parent.data.docId){
		$('#TANGER_OCX').css({height:$("#center").height()});
		  TANGER_OCX_OBJ = $('#TANGER_OCX')[0];
		  TANGER_OCX_OBJ.IsUseUTF8Data = true;
		  TANGER_OCX_OBJ.FileNew = false;
		  TANGER_OCX_OBJ.FileClose = false;
		  TANGER_OCX_OBJ.FileSave = false;
		  TANGER_OCX_OBJ.FileSaveAs = false;
		  TANGER_OCX_OBJ.FilePrint = true;
		  TANGER_OCX_OBJ.FilePrintPreview = true;
		  docId = parent.data.docId;
		  try {
				  TANGER_OCX_OBJ.OpenFromURL(contextPath+"/attachmentController/downFile.action?id="+parent.data.docId, true);
		  } catch (err) {
			  alert("错误：" + err.number + ":" + err.description);
			  var ee = err;
			  var ss = '';
		  } finally {
		  }
	}
}

function downLoadDoc(){
	$("#frame0").attr("src",contextPath+"/attachmentController/downFile.action?id="+docId);
}
</script>

<body onload="doInit()" style="overflow: hidden;">
<div id="ntkoDiv">
	<button class="btn-win-white" onclick="downLoadDoc()" id="zwDownLoad"><i class="glyphicon glyphicon-print" ></i>&nbsp;下载</button>
	<%=TeeSysProps.getString("NTKO_DOM") %>
</div>

<script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
with (TANGER_OCX_OBJ.ActiveDocument) {
    appName = new String(Application.Name);
    if ((appName.toUpperCase()).indexOf("WORD") > -1) //Word
    {
    	Protect(2, true, "");
    } 
  }
</script>



	</script>
	
	<script language="JScript" for="TANGER_OCX" event="OnFileCommand(cmd,canceled)">
	if (cmd==5)
	{
		var pp=TANGER_OCX_OBJ.ActiveDocument.Application.Dialogs.item(88);
		if (pp.display()==-1)
		{
			
			var j = tools.requestJsonRs(contextPath+"/doc/getViewInfo.action?uuid="+parent.uuid);
			
			var printNum=j.rtData.printNum;//总打印份数
			var printedNum=j.rtData.printedNum;//已打印份数
			var pNum=printNum-printedNum;//剩余打印份数
			
			if (pp.NumCopies>pNum)
			{
				alert("您最多可打印"+pNum+"份，请重新填写打印份数！");
			}
			else
			{
				pp.Execute();
				// +1
				var j = tools.requestJsonRs(contextPath+"/doc/updateViewPrintedNum.action?uuid="+parent.uuid+"&&pNum="+pp.NumCopies);
			}
		}
		TANGER_OCX_OBJ.CancelLastCommand=true;
	}
</script>

<iframe id="frame0" style="display: none"></iframe>
</body>
</html>