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
var uuid = "<%=uuid%>";
var data;
var user = "<%=loginUser.getUserName()%>";//当前登陆人姓名
var runId;
var docId;
var aipId;
function doInit(){
	var json = tools.requestJsonRs(contextPath+"/doc/getViewInfo.action?uuid="+uuid);
	data = json.rtData;
	runId = data.runId;
	$("#bt").append(json.rtData.bt);
	
	
	var tabArray=[];
	if((data.contentPriv&1)==1){
		tabArray.push({title:"表单",url:contextPath+"/system/core/workflow/flowrun/print/print.jsp?runId="+json.rtData.runId+"&view=1"});
	}
	if((data.contentPriv&16)==16){//签批单 
		tabArray.push({title:"签批单",url:contextPath+"/system/core/workflow/flowrun/print/aipForm.jsp?runId="+json.rtData.runId+" "});
	}
	
	if((data.contentPriv&2)==2 && json.rtData.docId){
		tabArray.push({title:"正文",url:contextPath+"/system/subsys/doc/daiyue/print/zhengwen.jsp"});
	}
	if((data.contentPriv&4)==4){
		tabArray.push({title:"版式正文",url:contextPath+"/system/subsys/doc/daiyue/print/bszw.jsp"});
	}
	if((data.contentPriv&8)==8){
		tabArray.push({title:"附件",url:contextPath+"/system/subsys/doc/daiyue/print/fj.jsp"});
	}
	 $.addTab("tab","tab-content",tabArray); 

	if(json.rtData.flag==0){
		$("#recBtn").show();
		$("#notRecBtn").show();
	}
	
}

function rec(){
	if(window.confirm("确认要签收 "+data.bt+" 吗？")){
		tools.requestJsonRs(contextPath+"/doc/updateRecFlag.action?flag=1&uuid="+uuid);
		window.location.reload();
	}
}

function doNotRec(){
	if(window.confirm("确认要拒签 "+data.bt+" 吗？")){
		tools.requestJsonRs(contextPath+"/doc/updateRecFlag.action?flag=2&uuid="+uuid);
		window.location.reload();
	}
}
</script>

<body onload="doInit()" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="/common/zt_webframe/imgs/gwgl/gwds/icon_gw.png">
		<p id="bt" class="title"></p>
		<div class="fr" style="margin-right:10px;">
			<button id="recBtn" class="btn-win-white" style="display:none;margin-right:10px;" onclick="rec()">签收</button>
			<button id="notRecBtn" class="btn-del-red" style="display:none;" onclick="doNotRec()">拒签</button>
		</div>
		<ul id = 'tab' class = 'tab clearfix'>
			
		</ul>
		<span class="basic_border_grey fl"></span>
	</div>
	
	<div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	
</body>
</html>