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
<%@ include file="/header/header2.0.jsp"%>
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
//全局对象
var AIP;
var user = "<%=loginUser.getUuid()%>";//当前登陆人姓名
var attachmentId = 0;
function doInit(){
	AIP = document.getElementById("TeeHWPostil");//$("#TeeHWPostil")[0];
	
	//判断当前流程是否有关联的签批单信息
	var url=contextPath+"/flowRunAipTemplateController/getListByRunId.action";
	var json=tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		
		var data=json.rtData;
		if(data!=null&&data.length>0){
			renderTemplate();
		}else{
			$("#upload-message").show();
			$("#templateId").hide();
			$("#content").hide();
			messageMsg("暂无签批单！", "upload-message","info" );
		}
		
	}
}

//渲染相关的aip签批模板
function renderTemplate(){
	$("#upload-message").show();
	messageMsg("请选择AIP签批模板！", "upload-message","info" );
	$("#content").hide();
	
	var  url=contextPath+"/flowRunAipTemplateController/getListByRunId.action";
	var  json=tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		var  data=json.rtData;
		var html = [];
		if(data && data.length>0){
			for(var i=0;i<data.length;i++){
				html.push("<option  value="+data[i].templateId+"   attachmentId="+data[i].attachId+">"+data[i].templateName+"</option>");
			}
		}
		$("#templateId").append(html.join(""));
	}
}

var attachmentId=0;
//改变模板
function changeTemplate(){
	AIP.CloseDoc(0);
	if($("#templateId").val()==0){
		$("#upload-message").show();
		messageMsg("请选择AIP签批模板！", "upload-message","info" );
		$("#content").hide();
	}else{
		    //获取被选中的option的属性   是可查阅  还是可签批
		   attachmentId=$("#templateId option:selected").attr("attachmentId");
			
			$("#content").show();
	 		$("#content").css({width:"100%"});
	 		$("#upload-message").hide();
	 		var ret = AIP.Login(user , 1, 32767, "", "");
	 		if(ret!=0){
				AIP.Login( "HWSEALDEMO"+user , 4, 32767, "demo", "");
	 		}
	 		AIP.SetValue("SET_CURRENT_COOKIE","COOKIE:"+document.cookie);
	 		var attachinfo = tools.requestJsonRs("/attachmentController/getAttachInfo.action?id="+attachmentId).rtData;
	 		AIP.LoadFileEx("<%=request.getProtocol().toLowerCase().contains("https")?"https":"http"%>://<%=request.getServerName()%>:<%=request.getServerPort()%>/attachmentController/downFile.action?id="+attachmentId,"",0,1);
		
		    //关联表单数据
		    var  u=contextPath+"/flowRun/getFlowRunDatasOnTitle.action";
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



function printit(){
	AIP.PrintDoc(1,1);
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

<div style="position: absolute;top:0px;left: 0px;right: 0Px;height:30px">
<select style="" id="templateId" name="templateId" onchange="changeTemplate();">
		<option value="0">请选择模板</option>
	</select>
</div>
<div style="display:none;position:absolute;left:0px;right: 0px;top: 31px;bottom: 0px" class="td-messagebox panel panel-primary" id="upload-message">

</div>
<div id="content" style="position:absolute;left:0px;right: 0px;top: 31px;bottom: 0px"> 
	<OBJECT id=TeeHWPostil align='middle' style='LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT:100%' classid=clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561 codebase=<%=contextPath %>/system/core/aip/HWPostil.cab#version=3,1,2,6>
		<PARAM NAME='_Version' VALUE='65536'>
		<PARAM NAME='_ExtentX' VALUE='17410'>
		<PARAM NAME='_ExtentY' VALUE='10874'>
		<PARAM NAME='_StockProps' VALUE='0'>
	</OBJECT>
</div>
</body>
</html>

