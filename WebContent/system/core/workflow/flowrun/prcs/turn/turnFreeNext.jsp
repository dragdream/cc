<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		String runName =request.getParameter("runName");
		String prcsId =request.getParameter("prcsId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title>转交工作</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>




<script type="text/javascript">
var runId = <%=runId%>;
var flowId = <%=flowId%>;
var frpSid = <%=frpSid%>;
var runName = '<%=runName%>';
var prcsId = 0;

var maxPrc = 0;
/**
 * 控制字段
 */
var fieldCtrlArray = null;
/**
 * 由于是自由流程 所以 选择人员不过滤
 */
function doInit(){
		//$("#runName").html(runName);
		//$("#runId").html(runId);
		//$("#currUserId").html(userId);
	//	var prcsText = getPrcsText("");
		//addPre(1);
	//	addNextPrc();
	//	alert($("#preListTbody").children().size());
		initPrcsInfo();
		try{
			var div = parent.document.getElementById("jbox-content");
			$(div).css({overflowY:"hidden"});
		}catch(e){}
		
		document.body.onclick=function(){
			$('#opFlagOptsPanel').remove();
		}
}

function initPrcsInfo(){
	var para = {};
	para['runId'] = runId;
	para['flowId'] =  flowId;
	para['frpSid'] =  frpSid;
	var url = contextPath+"/flowRun/getTurnHandlerData.action";
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var infoData = json.rtData;
		var temMaxPrc = infoData.maxPrc;
		var runName = null;
		runName = infoData.runName;
		$("#runNameSpan").html(runName);
		$("#flowNumber").html(runId);
		$("#msg").val("您有新的工作需要办理，流水号："+runId+"，工作名称/文号："+runName);
		
		var freePreset = infoData.freePreset;
		if(freePreset==0){
			$("#addBtn").hide();
		}
		
		prcsId = infoData.prcsId;
		maxPrc = prcsId + 1;
		/**
		 *初始化 老的步骤
		 */
		if(infoData.oldPrcs){
		  $.each(infoData.oldPrcs, function(i,oPrc){      
			  addOldPrc(oPrc);
		  });
		}
		if(temMaxPrc == prcsId ){
			addNextPrc();
		}
		if(temMaxPrc > prcsId ){
			var nectPrcsId = infoData.nextPrcs.prcsId;
			var nextTopFlag = infoData.nextPrcs.topFlag;
			var nextOpUser = infoData.nextPrcs.prcsOpUser;
			var nextPrcsOpUserDesc = infoData.nextPrcs.prcsOpUserDesc;
			var nextPrcsUser = infoData.nextPrcs.prcsUser;
			var nextPrcsUserDesc = infoData.nextPrcs.prcsUserDesc;
			setNextPrc(nectPrcsId,nextTopFlag,nextOpUser,nextPrcsOpUserDesc,nextPrcsUser,nextPrcsUserDesc);
			/**
			 *隐藏 增加按钮
			 */
			 $("#addBtn").css('display','none');
		}
		if(temMaxPrc >prcsId + 1 ){
			setAllPrePrcs(infoData.nextOtherPrePrcs);
		}
	}else{
		alert(json.rtMsg);
	}
}
function turnNext(){
	var reVuser = validOpUser();
	if(!reVuser){
		return ;
	}
	var para = {};
	para = tools.formToJson("#turnForm");
	para['runId'] = runId;
	para['flowId'] =  flowId;
	para['frpSid'] =  frpSid;
	para['maxPrc'] =  maxPrc;
	para['prcsId'] =  prcsId;
	
	var nextPrcsAlert = 0;
	var beginUserAlert = 0;
	var allPrcsUserAlert = 0;
	
	var nextPrcsAlertElements = $("[clazz=nextPrcsAlert]");
	for(var i=0;i<nextPrcsAlertElements.length;i++){
		if(nextPrcsAlertElements[i].checked){
			nextPrcsAlert+=parseInt(nextPrcsAlertElements[i].value);
		}
	}
	
	var beginUserAlertElements = $("[clazz=beginUserAlert]");
	for(var i=0;i<beginUserAlertElements.length;i++){
		if(beginUserAlertElements[i].checked){
			beginUserAlert+=parseInt(beginUserAlertElements[i].value);
		}
	}
	
	var allPrcsUserAlertElements = $("[clazz=allPrcsUserAlert]");
	for(var i=0;i<allPrcsUserAlertElements.length;i++){
		if(allPrcsUserAlertElements[i].checked){
			allPrcsUserAlert+=parseInt(allPrcsUserAlertElements[i].value);
		}
	}
	
	para['nextPrcsAlert'] =  nextPrcsAlert;
	para['beginUserAlert'] =  beginUserAlert;
	para['allPrcsUserAlert'] =  allPrcsUserAlert;
	
	var url = contextPath+"/flowRun/turnFreeNext.action";
/* 	alert(tools.jsonObj2String(para)); */
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		return true;
	}else{
		return false;
	} 
}
/**
 * 验证 是否填写主办人 或者 预设步骤主办人
 */
function validOpUser(){
	var topFlag =  $("#topFlag").val();
	var vVal = $("#prcsOpUser").val();
	var vPrcsVal = $("#prcsUser").val();
	if(parseInt(topFlag) == 1){
		if(!vVal){
			alert("请填写下一步骤主办人!");
			return false;
		}
	 }else{
		if(!vPrcsVal){
			alert("请填写下一步骤经办人!");
			return false;
		}
	}
	for(var i=prcsId+2;i<=maxPrc;i++){
		var _topFlag =  $("#topFlag"+i).val();
		var _vVal = $("#prcsOpUser"+i).val();
		var _vPrcsVal = $("#prcsUser"+i).val();
		if(parseInt(_topFlag) == 1){
			if(!_vVal){
				alert("请填写第"+i+"步骤主办人!");
				return false;
			}
		}else{
			if(!_vPrcsVal){
				alert("请填写第"+i+"步骤经办人!");
				return false;
			}
		}
	}
	return true;
}
function turnNextPrcs(){
	var opUser = $('#opUser').val();
	if(!opUser){
		alert("请选择主办人!");
		return ;
	}
	var url = contextPath+"/flowRun/turnNextHandler.action";
	var para = {};
	para['runId'] =  runId;
	para['flowId'] =  flowId;
	para['frpSid'] =  frpSid;
	para['opUser'] = $('#opUser').val();
	para['prcsUser'] = $('#prcsUser').val();
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		alert(json.rtMsg);
		CloseWindow();
		window.location = contextPath +"/system/core/workflow/flowrun/list/index.jsp";
	}
}

/**
* 设置预设步骤
* @param i
* @return
*/
function getPrcsText(i) {
 var tmp = new Array();
 tmp[0] = '<div style="line-height:18px;">';
 tmp[1] = '<a id="topFlagA'+i+'" href="javascript:void(0);"><span id="topFLagSpan'+i+'" onclick="showOpFlagOptsPanel(this,\''+i+'\')">主办人：</span></a>';
 tmp[2] = '<input type="text" name=topFlag'+i+' id=topFlag'+i+'  value=1 hidden/>';
 tmp[3] = '<input class="BigInput" id="prcsOpUserNameSpan'+i+'" readonly name="prcsOpUserNameSpan'+i+'" /><input type="text" name="prcsOpUser'+i+'" id="prcsOpUser'+i+'" hidden>';
 tmp[4] = '<input type="button" value="选择" id="selecteOpUserButton'+i+'" onclick="selectSingleUserWorkFlow(['+'\'prcsOpUser'+i+'\','+'\'prcsOpUserNameSpan'+i+'\'])" class="btn btn-default"/>';
 tmp[5] ='</div>';
 tmp[6] ='<div style="line-height:18px;">经办人：<textarea id="prcsUserNameSpan'+i+'" name="prcsUserNameSpan'+i+'" class="BigTextarea" style="height:80px;width:450px" readonly></textarea>';
 tmp[7] ='<input type="text" name="prcsUser'+i+'" id="prcsUser'+i+'" hidden/>';
 tmp[8] ='<input type="button" value="选择" id="selecteUserButton'+i+'"  onclick="selectUserWorkFlow(['+'\'prcsUser'+i+'\','+'\'prcsUserNameSpan'+i+'\'])" class="btn btn-default"/>';
 tmp[9] ='</div>';
 tmp[10] ='<a  class="orgClear" href="javascript:void(0);"  onclick="clearData(\'prcsUser'+i+'\',\'prcsUserNameSpan'+i+'\')">清空</a>&nbsp;&nbsp;';
 tmp[11] ='<a href="javascript:void(0);" onclick="showFieldctrlWin(['+'\'freeItem'+i+'\','+'\'prcsOpUserNameSpan'+i+'\',\'attachCtrlModel'+i+'\'])"  >可写字段</a><input type="hidden" id="freeItem'+i+'" name="freeItem'+i+'" value="[]"/><input type="hidden" id="attachCtrlModel'+i+'" name="attachCtrlModel'+i+'" value="[]"/>';
 var prcsText= tmp.join("");
 return prcsText;
}

function fieldctrl(id,value){
	alert(id);
	//$("#"+id)[0].value = "";
}

function showFieldctrlWin(frArray)
{
  fieldCtrlArray = frArray;
  var URL = contextPath + "/system/core/workflow/flowrun/prcs/turn/fieldctrl.jsp?flowId="+flowId+"&frpSid="+frpSid;
  dialog(URL,800, 400);
}
/**
* 设置预设步骤 带人员信息的 zhp 20131105
* @param i
* @return
*/
function setPrcsText(i,topFlag,opUser,opUserDesc,prcsUser,prcsUserDesc) {
 var tmp = new Array();
 tmp[0] = '<div style="line-height:18px;">';
 if(opUser==0 || opUser=="0"){
	 opUser = "";
	 opUserDesc = "";
 }
 if(prcsUser==0 || prcsUser=="0"){
	 prcsUser = "";
	 prcsUserDesc = "";
 }
 if(topFlag == 1){
	 tmp[1] = '<a id="topFlagA'+i+'" href="javascript:void(0);"><span id="topFLagSpan'+i+'" onclick="showOpFlagOptsPanel(this,\''+i+'\')">主办人：</span></a>';
	 tmp[2] = '<input type="text" name=topFlag'+i+' id=topFlag'+i+'  value=1 hidden/>';
	 tmp[3] = '<input id="prcsOpUserNameSpan'+i+'" name="prcsOpUserNameSpan'+i+'" value="'+opUserDesc+'" class="BigInput"  readonly/><input type="text" name="prcsOpUser'+i+'" id="prcsOpUser'+i+'" value="'+opUser+'" hidden>';
	 tmp[4] = '<input type="button" value="选择" id="selecteOpUserButton'+i+'" onclick="selectSingleUserWorkFlow(['+'\'prcsOpUser'+i+'\','+'\'prcsOpUserNameSpan'+i+'\'])" class="btn btn-default"/>';
}else if(topFlag == 2){
	 tmp[1] = '<a id="topFlagA'+i+'" href="javascript:void(0);"><span id="topFLagSpan'+i+'" onclick="showOpFlagOptsPanel(this,\''+i+'\')">无主办会签：</span></a>';
	 tmp[2] = '<input type="text" name=topFlag'+i+' id=topFlag'+i+'  value=2 hidden/>';
	 tmp[3] = '<input id="prcsOpUserNameSpan'+i+'" name="prcsOpUserNameSpan'+i+'"  class="BigInput"  readonly/><input hidden type="text" name="prcsOpUser'+i+'" id="prcsOpUser'+i+'" >';
	 tmp[4] = '<input type="button" disabled="disabled" value="选择" id="selecteOpUserButton'+i+'" onclick="selectSingleUserWorkFlow(['+'\'prcsOpUser'+i+'\','+'\'prcsOpUserNameSpan'+i+'\'])" class="btn btn-default"/>';
}else if(topFlag == 3){
	 tmp[1] = '<a id="topFlagA'+i+'" href="javascript:void(0);"><span id="topFLagSpan'+i+'" onclick="showOpFlagOptsPanel(this,\''+i+'\')">先接收先办：</span></a>';
	 tmp[2] = '<input type="text" name=topFlag'+i+' id=topFlag'+i+'  value=3 hidden/>';
	 tmp[3] = '<input id="prcsOpUserNameSpan'+i+'" name="prcsOpUserNameSpan'+i+'" class="BigInput"  readonly/><input hidden type="text" name="prcsOpUser'+i+'" id="prcsOpUser'+i+'" hidden>';
	 tmp[4] = '<input type="button" disabled="disabled" value="选择" id="selecteOpUserButton'+i+'" onclick="selectSingleUserWorkFlow(['+'\'prcsOpUser'+i+'\','+'\'prcsOpUserNameSpan'+i+'\'])" class="btn btn-default"  />';
}
 tmp[5] ='</div>';
 tmp[6] ='<div style="line-height:18px;">经办人：<textarea id="prcsUserNameSpan'+i+'" name="prcsUserNameSpan'+i+'"    style="height:80px;width:440px" class="BigTextarea" readonly>'+prcsUserDesc+'</textarea>';
 tmp[7] ='<input type="text" name="prcsUser'+i+'" id="prcsUser'+i+'"  value="'+prcsUser+'" hidden />';
 tmp[8] ='<input type="button" value="选择" id="selecteUserButton'+i+'"  onclick="selectUserWorkFlow(['+'\'prcsUser'+i+'\','+'\'prcsUserNameSpan'+i+'\'])" class="btn btn-default"/>';
 tmp[9] ='</div>';
 tmp[10] ='<a  class="orgClear" href="javascript:void(0);" onclick="clearData(\'prcsUser'+i+'\',\'prcsUserNameSpan'+i+'\')">清空</a>&nbsp;&nbsp;';
 tmp[11] ='<a href="javascript:void(0);">可写字段</a><input type="hidden" id="freeItem'+i+'" name="freeItem'+i+'" value="[]"/>';
 var prcsText= tmp.join("");
 return prcsText;
}

function delFeedBackAttach(){}
/**
* 创建浮动菜单 zhp 20131020
*/
function showDiv(i){
	i = i || "";
		var menuData = new Array();
	  menuData.push({ name:'主办人：',action:changTopFlag,extData:[1,i,'主办人：']});
	  menuData.push({ name:'无主办会签：',action:changTopFlag ,extData:[2,i,'无主办会签：']});
	  menuData.push({ name:'先接收先办：',action:changTopFlag,extData:[3,i,'先接收先办：']});
	$("#topFLagSpan"+i).TeeMenu({menuData:menuData,eventPosition:false,left:-60});
}
/**
 * 改变 主办 无主办 先到先办 的状态
 */
function changTopFlag(flag,i,text){
	if(flag == 2 ||  flag == 3){
		$("#prcsOpUser"+i).val("");
		$("#prcsOpUserNameSpan"+i).val("");
		$("#selecteOpUserButton"+i).attr("disabled",true);
	}else{
		$("#selecteOpUserButton"+i).attr("disabled",false);
	}
	$("#topFlag"+i).val(flag);
	$("#topFLagSpan"+i).html(text);
}
/**
 * 增加预设步骤
 */
function addPre(){
	  maxPrc = maxPrc + 1;
	var tmp = new Array();
	tmp[0] = '<tr class="TableData1" >';
	tmp[1] = '<td>第<span class="big4" style="color:red">'+maxPrc+'</span>步：(预设步骤)<br><font id="preSetDiv" color="red" style="display:none">本步骤为预设步骤</font></td>';
	tmp[2] = '</td>';
	tmp[3] = '<td>';
	tmp[4] = getPrcsText(maxPrc);
	tmp[5] = '</td>';
	tmp[6] = '</tr>';
     var prcsText= tmp.join("");
     $("#preListTbody").append($(prcsText));
     if( $("#preListTbody").children().size() > 0){
    	 $("#delBtn").css("display","");
       }
}

/**
* 增加预设步骤 带人员信息  zhp 20131105
* 
*/
function setPre(i,topFlag,opUser,opUserDesc,prcsUser,prcsUserDesc){
	var tmp = new Array();
	tmp[0] = '<tr class="TableData1" >';
	tmp[1] = '<td>第<span class="big4" style="color:red">'+i+'</span>步：(预设步骤)<br><font id="preSetDiv" color="red" style="display:none">本步骤为预设步骤</font></td>';
	tmp[2] = '</td>';
	tmp[3] = '<td>';
	tmp[4] = setPrcsText(i,topFlag,opUser,opUserDesc,prcsUser,prcsUserDesc);
	tmp[5] = '</td>';
	tmp[6] = '</tr>';
    var prcsText= tmp.join("");
    $("#preListTbody").append($(prcsText));
}
/**
 * zhp 20131105
 */
function setAllPrePrcs(prePrcsArray){
	if(prePrcsArray){
		for(var i=0;i<prePrcsArray.length;i++){
			var prcsData = prePrcsArray[i];
			var nectPrcsId = prcsData.prcsId;
			var nextTopFlag = prcsData.topFlag;
			var nextOpUser = prcsData.prcsOpUser;
			var nextPrcsOpUserDesc = prcsData.prcsOpUserDesc;
			var nextPrcsUser = prcsData.prcsUser;
			var nextPrcsUserDesc = prcsData.prcsUserDesc;
			setPre(nectPrcsId,nextTopFlag,nextOpUser,nextPrcsOpUserDesc,nextPrcsUser,nextPrcsUserDesc);
		}
	}
}

/**
 * 添加之前的步骤
 */
function addOldPrc(dataJ){
	if(!dataJ){
		return;
	}
	 var tmp = new Array();
	tmp[0] = '<TR class="TableContent">';
	tmp[1] = '<TD width="20%" >第<B><SPAN class=big4>'+dataJ.prcsId+'</SPAN></B>步：</TD>';
// 	if(dataJ.flag == 1 || dataJ.flag == 2){
// 		tmp[2] = '<TD><FONT color=red>'+dataJ.opUserDesc+'[主办人](办理中)</FONT></TD>';
// 	}else{
// 		tmp[2] = '<TD><FONT color=green>'+dataJ.opUserDesc+'[主办人](已办结)</FONT></TD>';
// 	}
	var render = [];
	if(dataJ.opUserDesc!=""){
		render.push("<span style=color:green>主办："+dataJ.opUserDesc+"</span>");
	}
	if(dataJ.prcsUserDesc!=""){
		render.push("<span style=color:orange>经办："+dataJ.prcsUserDesc+"</span>");
	}
	tmp[2] = "<td>"+render.join("&nbsp;&nbsp;&nbsp;&nbsp;")+"</td>";
	tmp[3] = '</TR>';
     var prcsText= tmp.join("");
     $("#prcsListTbody").append($(prcsText));
     return prcsText;
}

function showOpFlagOptsPanel(obj,prcsId){//显示经办类型选择面板,obj-点击的按钮对象,userLock,prcsId
	var panel = $('#opFlagOptsPanel');
	if(panel.length==0){
		panel = $('<div id=\'opFlagOptsPanel\' class=\'opFlagOptsPanel\'><div onclick="opFlagOptsCallback(1,\''+prcsId+'\')">主办人员</div><div onclick="opFlagOptsCallback(2,\''+prcsId+'\')">无主办会签</div><div onclick="opFlagOptsCallback(3,\''+prcsId+'\')">先接收转交</div></div>').css({left:$(obj).offset().left,top:$(obj).offset().top+20}).appendTo($('body'));
	}
	window.event.cancelBubble = true;
}

function opFlagOpts(flag){
	switch(flag){
		case 1:return '主办人员：';
		case 2:return '无主办会签：';
		case 3:return '先接收转交：';
	}
	return '';
}

function opFlagOptsCallback(opFlag,prcsId){//经办类型值回填
	var panel = $('#opFlagOptsPanel');
	panel.remove();
	var opFlagSpan = $('#topFLagSpan'+prcsId);
	opFlagSpan.html(opFlagOpts(opFlag));
	if((opFlag+"")=='1'){//如果为指定主办人，则开放设置主办人按钮
		$('#prcsOpUserNameSpan'+prcsId).removeAttr('disabled');
		$("#selecteOpUserButton"+prcsId).removeAttr('disabled');
	}else{
		$('#prcsOpUserNameSpan'+prcsId).attr('disabled','disabled');
		$("#selecteOpUserButton"+prcsId).attr('disabled','disabled');
	}
	$("#prcsOpUser"+prcsId).val("");
	$("#prcsOpUserNameSpan"+prcsId).val("");
	$("#topFlag"+prcsId).val(opFlag);
}
/**zhp 20131105
 * 增加下一步
 */
function addNextPrc(){
	var nextPrcsId = parseInt(prcsId) + 1; 
	 var tmp = new Array();
	tmp[0] = '<tr class="TableData">';
	tmp[1] = '<td>第<span class="big4" style="color:red">'+nextPrcsId+'</span>步：(下一步骤)<br><font id="preSetDiv" color="red" style="display:none">本步骤为预设步骤</font></td>';
	tmp[2] = '</td>';
	tmp[3] = '<td>';
	tmp[4] = getPrcsText("");
	tmp[5] = '</td>';
	tmp[6] = '</tr>';
     var prcsText= tmp.join("");
     $("#prcsListTbody").append($(prcsText));
     return prcsText;
}

/**zhp 20131105
* 设置下一步
*/
function setNextPrc(i,topFlag,opUser,opUserDesc,prcsUser,prcsUserDesc){
	 var tmp = new Array();
	tmp[0] = '<tr class="TableData">';
	tmp[1] = '<td>第<span class="big4" style="color:red">'+i+'</span>步：(下一步骤)<br><font id="preSetDiv" color="red" style="display:none">本步骤为预设步骤</font></td>';
	tmp[2] = '</td>';
	tmp[3] = '<td>';
	tmp[4] = setPrcsText("",topFlag,opUser,opUserDesc,prcsUser,prcsUserDesc);
	tmp[5] = '</td>';
	tmp[6] = '</tr>';
    var prcsText= tmp.join("");
    $("#prcsListTbody").append($(prcsText));
    return prcsText;
}
/**zhp 20131105
 * 删除一个预设步骤
 */
function delPre(){
//	 var id = $("#prcsListTbody tr:last").attr("id");
	var lasDom = $("#preListTbody tr:last-child")[0];
	if(lasDom){
		$(lasDom).remove();
		 maxPrc = maxPrc - 1;
	}
	if($("#preListTbody").children().size() == 0){
   		 $("#delBtn").css("display","none");
      }
}
</script>
<style>
.opFlagOptsPanel {
	width: 100px;
	position: absolute;
	background: white;
	border: 1px solid green;
}

.opFlagOptsPanel div {
	padding: 5px;
	text-align: center;
	cursor: pointer;
}

.opFlagOptsPanel div:hover {
	background: #e5f1fa;
}
</style>
</head>
<body onload="doInit()">
<form name="turnForm" id="turnForm" method="post" action="">
<table id="prcsTable" class="TableBlock" width="100%">
   <tbody id="prcsListTbody">
   </tbody>
    <tbody id="preListTbody"></tbody>
   <tr class="TableControl" height="30" id="previewDiv" >
      <td colspan="2">
      <span id="addBtn" >
       <input type="button" value="+ 增加下一个预设步骤"  onClick="addPre()" title="预设更多后续步骤的经办人和主办人" name="button" class="btn btn-default">
       </span>
       <span id="delBtn" style="display:none" >
       <input type="button" value="- 删除最后一个预设步骤"  onClick="delPre()" title="删除最后一个预设步骤" name="button" class="btn btn-default">
      </span>
      </td>
    </tr>
 	   <tr>
      <td colspan="2" >
      	<fieldset >
			<legend style="margin:0px;padding:0px;font-size:16px">事务提醒：</legend>
		</fieldset>
    	<div>
			下一步办理人：
			<input clazz="nextPrcsAlert" id="nextPrcsAlert1" checked type="checkbox" value="1"/><label for="nextPrcsAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png" title="内部消息"/></label>
			<input clazz="nextPrcsAlert" id="nextPrcsAlert2" type="checkbox"  value="2"/><label for="nextPrcsAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
			<input clazz="nextPrcsAlert" id="nextPrcsAlert3" type="checkbox"  value="4"/><label for="nextPrcsAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
			<br/>
			流程发起人：
			<input clazz="beginUserAlert" id="beginUserAlert1" type="checkbox"  value="1"/><label for="beginUserAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png"  title="内部消息"/></label>
			<input clazz="beginUserAlert" id="beginUserAlert2" type="checkbox"  value="2"/><label for="beginUserAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
			<input clazz="beginUserAlert" id="beginUserAlert3" type="checkbox"  value="4"/><label for="beginUserAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
			<br/>
			全部经办人：
			<input clazz="allPrcsUserAlert" id="allPrcsUserAlert1" type="checkbox"  value="1"/><label for="allPrcsUserAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png" title="内部消息"/></label>
			<input clazz="allPrcsUserAlert" id="allPrcsUserAlert2" type="checkbox"  value="2"/><label for="allPrcsUserAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
			<input clazz="allPrcsUserAlert" id="allPrcsUserAlert3" type="checkbox"  value="4"/><label for="allPrcsUserAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
		</div>
<!--       	<div style="margin-bottom:5px;font-weight:bold;display:none">消息提醒</div> -->
<%-- 		    <span id="sms2RemindSpan" style="display:none"><input type="checkbox" name="sms2Remind" id="sms2Remind"><img src="<%=contextPath %>/common/images/workflow/sms.png"></span> --%>
<%-- 		    <input value="1" type="checkbox" name="smsRemind" id="smsRemind" checked="checked" ><img src="<%=contextPath %>/common/images/workflow/sms.png" title="内部短信" /> --%>
<%-- 		    <input value="1" type="checkbox" name="phoneRemind" id="phoneRemind"  ><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/> --%>
<%-- 		    <input value="1" type="checkbox" name="mailRemind" id="mailRemind"  ><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="外部邮件"/> --%>
<!-- 	    </div> -->
		</td>
    </tr>
</table>
<div style="display:none">
<br/>
		消息内容：<input type="text" onmouseover="this.focus()" onfocus="this.select()" id="msg" name="msg" value="" style="width:450px"  maxlength="100" class="BigInput">
     
</div>
</form>
<div id="attachDiv" style="overflow: hidden"></div>
</body>
</html>
