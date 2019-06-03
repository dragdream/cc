<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   int attId=TeeStringUtil.getInteger(request.getParameter("attId"), 0);

   TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>版式正文在线预览</title>
<%@ include file="/header/header2.0.jsp"%>
<script type="text/javascript">
var AIP;
var attId=<%=attId %>;//附件主键
var user = "<%=loginUser.getUuid()%>";//当前登陆人姓名
//初始化方法 
function doInit(){
	AIP = document.getElementById("TeeHWPostil");
	
	if(!isIE()){
		messageMsg("非IE内核无法查看AIP文件", "mess","info" );
	}else{
		$("#content").css({width:"100%"});
		var ret = AIP.Login( user , 1, 32767, "", "");
		if(ret!=0){
			AIP.Login( "HWSEALDEMO"+user , 4, 32767, "demo", "");
		}
		AIP.SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
		
		var attachinfo = tools.requestJsonRs("/attachmentController/getAttachInfo.action?id="+attId).rtData;
		AIP.LoadFileEx("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/attachmentController/downFile.action?id="+attId,attachinfo.ext,0,1);
	}


}


function isIE() { //ie?  
    if (!!window.ActiveXObject || "ActiveXObject" in window)  
        return true;  
    else  
        return false;  
} 

</script>

<SCRIPT LANGUAGE=javascript FOR=TeeHWPostil EVENT=NotifyCtrlReady>
	
  	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
	AIP = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
	AIP.ShowToolBar = true;
	AIP.ShowDefMenu = 0;
	AIP.ShowScrollBarButton = 1;
	$(window).resize(function(){
		$("#TeeHWPostil").css({height:$(window).height()});
	});
	$("#TeeHWPostil").css({height:$(window).height()});
</SCRIPT>
</head>
<body onload="doInit();">
<div id="content" style="position:absolute;width:0px;"> 
	<OBJECT id=TeeHWPostil align='middle' style='LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT:100%' classid=clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561 codebase=<%=contextPath %>/system/core/aip/HWPostil.cab#version=3,1,2,6>
		<PARAM NAME='_Version' VALUE='65536'>
		<PARAM NAME='_ExtentX' VALUE='17410'>
		<PARAM NAME='_ExtentY' VALUE='10874'>
		<PARAM NAME='_StockProps' VALUE='0'>
	</OBJECT>
</div>

<div id="mess" style="padding: 10px">
</div>
</body>
</html>