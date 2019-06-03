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
<title>版式正文</title>
<script>

//全局对象
var AIP;
var user = "<%=loginUser.getUserName()%>";//当前登陆人姓名
function downLoadAip(){
	$("#frame0").attr("src",contextPath+"/attachmentController/downFile.action?id="+aipId);
}

function doInit(){
	//判断是否支持下载
	var isDownLoad=parent.data.isDownLoad;
	if(isDownLoad!=1&&isDownLoad!="1"){
		$("#bszwDownLoad").hide();
		
	}
}
</script>
</head>
<body onload="doInit()">
<div id="aipDiv" style="">
	<div style="padding: 5px">
		<button class="btn-win-white" onclick="AIP.PrintDoc(1,1)"
			id="bszwPrint">打印
		</button>
		<button class="btn-win-white" onclick="downLoadAip()"
			id="bszwDownLoad">
			下载
		</button>
	</div>
	<OBJECT id=TeeHWPostil align='middle'
		style='LEFT: 0px; WIDTH: 1px; TOP: 0px; HEIGHT: 1px'
		classid=clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561
		codebase=<%=contextPath%>
		/system/core/aip/HWPostil.cab#version=3,1,2,6>
		<PARAM NAME='_Version' VALUE='65536'>
			<PARAM NAME='_ExtentX' VALUE='17410'>
				<PARAM NAME='_ExtentY' VALUE='10874'>
					<PARAM NAME='_StockProps' VALUE='0'>
	</OBJECT>
</div>


	<SCRIPT LANGUAGE=javascript FOR=TeeHWPostil EVENT=NotifyCtrlReady>
	
  	// 控件"HWPostil1"的NotifyCtrlReady事件，一般在这个事件中完成初始化的动作
	AIP = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
	AIP.ShowToolBar = false;
	AIP.ShowDefMenu = 0;
	AIP.CurrAction = 0;
	AIP.DocProperty("DocReadonly") = "true";
	AIP.ShowScrollBarButton = 1;
	
	TeeHWPostil.JSEnv = 1;//触发JSNotifyBeforeAction
	
	//查看是否存在版式正文
	var url1 = contextPath +"/flowRunDocController/getFlowRunDocAipByRunId.action";
	var json1 = tools.requestJsonRs(url1,{runId:parent.runId});
	var data1 = json1.rtData;
	if(data1!=null){
		var ret = AIP.Login( user , 1, 0, "", "");
		if(ret!=0){
			AIP.Login( "HWSEALDEMO"+user , 4, 0, "demo", "");
		}
		AIP.SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
 		AIP.LoadFile("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/attachmentController/downFile.action?id="+data1);		aipId = data1;
	}else{
		messageMsg("暂无版式正文","aipDiv","info");
	}
	
	$(window).resize(function(){
		$("#TeeHWPostil").css({height:$(window).height()-40,width:"100%"});
	});
	$("#TeeHWPostil").css({height:$(window).height()-40,width:"100%"});
</SCRIPT>

 	<SCRIPT LANGUAGE=javascript FOR=TeeHWPostil EVENT=JSNotifyBeforeAction(lActionType,lType,strName,strValue)>
      //在这里判断打印条件，是否允许打印，禁止打印设置JSValue = 0

      if(lActionType==1){//判断是否是打印
		var jsonObj = tools.requestJsonRs(contextPath+"/doc/getViewInfo.action?uuid="+parent.uuid);
		
		var printNum1=jsonObj.rtData.printNum;//总打印份数
		var printedNum1=jsonObj.rtData.printedNum;//已打印份数
		var pNum1=printNum1-printedNum1;//剩余打印份数
		
		if(lType>pNum1){
			alert("您最多可打印"+pNum1+"份，请重新填写打印份数！");
			TeeHWPostil.JSValue = 0;//JSValue=0禁止打印，=1允许打印
			TeeHWPostil.JSEnv = 0;
			
		}else{
			//后台修改打印份数
			var j = tools.requestJsonRs(contextPath+"/doc/updateViewPrintedNum.action?uuid="+parent.uuid+"&&pNum="+lType);
			
		} 
	} 
</SCRIPT>

<iframe id="frame0" style="display:none"></iframe>
</body>
</html>