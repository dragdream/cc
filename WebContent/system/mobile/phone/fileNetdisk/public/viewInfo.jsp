<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/system/mobile/header.jsp" %>
<%
int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
int rootFolderPriv = TeeStringUtil.getInteger(request.getParameter("rootFolderPriv"), 0);
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="">

<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	if(window.external){
		window.external.setTitle("文件详情");
	}
	
	//签阅
	signReadFunc(sid);
	
	var json = tools.requestJsonRs(contextPath+"/fileNetdisk/getFileNetdiskById.action?sid="+sid);
	if (json.rtState) {
		var prc = json.rtData;
		if (prc && prc.sid) {
			
			bindJsonObj2Cntrl(prc);
			
			var signReadDesc = "<font color='red'>未签阅</font>";
			if(prc.isSignRead =='1'){
				signReadDesc = "<font color=' #007500 '>已签阅</font>";
			}
			
			$("#signReadDesc").html("(" + signReadDesc + ")");
			 var priv = '<%=rootFolderPriv%>';
			
			if((priv & 4) == 4){//去掉删除浮动菜单
		    	  priv-=4;
			} 
			var attaches = prc.attacheModels;
			if(attaches){
				attaches.priv = priv;
				$("#fileContainer").append("<p onclick=\"GetFile("+attaches.sid+",'"+attaches.fileName+"','"+attaches.attachmentName+"')\" style=color:#0080ff;font-size:12px><img src='<%=request.getContextPath() %>/common/images/filetype/defaut.gif' />&nbsp;"+attaches.fileName+"("+attaches.sizeDesc+")</p>");
				
			}
		}
	}
}



/**
 * 签阅
 * @param sid
 */
function signReadFunc(sid){
  var url = contextPath + "/TeeFileNetdiskReaderController/addOrUpdate.action?fileNetdiskId=" + sid;
  var jsonRs = tools.requestJsonRs(url);
  if(jsonRs.rtState){
	  
  }else{
    alert(jsonRs.rtMsg);
  }
}
</script>


</head>
<body onload="doInit()" style="text-align:left">
	
	<p><b>文件详情<span id="signReadDesc"></span></b></p>
	<div id="content"></div>
	<p>文件：<div id="fileContainer"></div> </p>
</body>
</html>