<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/header/header.jsp"%>
<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		String runName =request.getParameter("runName");
		String prcsId =request.getParameter("prcsId");
		prcsId = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>转交工作</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">


<script type="text/javascript"
	src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/ZTreeSync.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>

<script type="text/javascript"
	src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/src/orgselect.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>




<script type="text/javascript">
var runId = <%=runId%>;
var flowId = <%=flowId%>;
var frpSid = <%=frpSid%>;
var runName = '<%=runName%>';
var prcsId = 0;

var maxPrc = 0;

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
		prcsId = infoData.prcsId;
		maxPrc = prcsId + 1;
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
	var url = contextPath+"/flowRun/turnFreeNext.action";
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		alert(json.rtMsg);
		CloseWindow();
		window.location = contextPath +"/system/core/workflow/flowrun/list/index.jsp";
	}else{
		alert(json.rtMsg);
	}
}
/**
 * 验证 是否填写主办人 或者 预设步骤主办人
 */
function validOpUser(){
	var vVal = $("#prcsOpUser").val();
	if(!vVal){
		alert("请填写下一步骤主办人!");
		return false;
	}
	for(var i=prcsId+2;i<=maxPrc;i++){
		var _vVal = $("#prcsOpUser"+i).val();
		if(!_vVal){
			alert("请填写第"+i+"步骤主办人!");
			return false;
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
 tmp[1] = '<a id="topFlagA'+i+'" href="javascript:void(0);"><span id="topFLagSpan'+i+'" onclick="showDiv('+i+')">主办人：</span> </a>';
 tmp[2] = '	<input type="text" name=topFlag'+i+' id=topFlag'+i+'  value=1 />';
 tmp[3] = '<input id="prcsOpUserNameSpan'+i+'" name="prcsOpUserNameSpan'+i+'" /><input type="text" name="prcsOpUser'+i+'" id="prcsOpUser'+i+'" >';
 tmp[4] = '<input type="button" value="设置" id="selecteOpUserButton'+i+'" onclick="selectSingleUser(['+'\'prcsOpUser'+i+'\','+'\'prcsOpUserNameSpan'+i+'\'])" class="SmallButtonA"/>';
 tmp[5] ='</div>';
 tmp[6] ='<div style="line-height:18px;">经办人：<input id="prcsUserNameSpan'+i+'" name="prcsUserNameSpan'+i+'"  />';
 tmp[7] ='<input type="text" name="prcsUser'+i+'" id="prcsUser'+i+'" />';
 tmp[8] ='<input type="button" value="设置" id="selecteUserButton'+i+'"  onclick="selectUser(['+'\'prcsUser'+i+'\','+'\'prcsUserNameSpan'+i+'\'])" class="SmallButtonA"/>';
 tmp[9] ='</div>';
 tmp[10] ='<a  class="orgClear" href="javascript:void(0);">清空</a>&nbsp;&nbsp;';
 tmp[11] ='<a href="javascript:void(0);">可写字段</a><input type="hidden" id="freeItem'+i+'" name="freeItem'+i+'" value=""/>';
 var prcsText= tmp.join("");
 return prcsText;
}


/**
* 设置预设步骤 带人员信息的 zhp 20131105
* @param i
* @return
*/
function setPrcsText(i,topFlag,opUser,opUserDesc,prcsUser,prcsUserDesc) {
 var tmp = new Array();
 tmp[0] = '<div style="line-height:18px;">';
 if(topFlag == 1){
	 tmp[1] = '<a id="topFlagA'+i+'" href="javascript:void(0);"><span id="topFLagSpan'+i+'" onclick="showDiv('+i+')">主办人：</span> </a>';
	 tmp[2] = '	<input type="text" name=topFlag'+i+' id=topFlag'+i+'  value=1 />';
	 tmp[3] = '<input id="prcsOpUserNameSpan'+i+'" name="prcsOpUserNameSpan'+i+'" value="'+opUserDesc+'" /><input type="text" name="prcsOpUser'+i+'" id="prcsOpUser'+i+'" value="'+opUser+'">';
	 tmp[4] = '<input type="button" value="设置" id="selecteOpUserButton'+i+'" onclick="selectSingleUser(['+'\'prcsOpUser'+i+'\','+'\'prcsOpUserNameSpan'+i+'\'])" class="SmallButtonA"/>';
}else if(topFlag == 2){
	 tmp[1] = '<a id="topFlagA'+i+'" href="javascript:void(0);"><span id="topFLagSpan'+i+'" onclick="showDiv('+i+')">无主办会签：</span> </a>';
	 tmp[2] = '	<input type="text" name=topFlag'+i+' id=topFlag'+i+'  value=2 />';
	 tmp[3] = '<input id="prcsOpUserNameSpan'+i+'" name="prcsOpUserNameSpan'+i+'"  /><input type="text" name="prcsOpUser'+i+'" id="prcsOpUser'+i+'" >';
	 tmp[4] = '<input type="button" disabled="disabled" value="设置" id="selecteOpUserButton'+i+'" onclick="selectSingleUser(['+'\'prcsOpUser'+i+'\','+'\'prcsOpUserNameSpan'+i+'\'])" class="SmallButtonA"/>';
}else if(topFlag == 3){
	 tmp[1] = '<a id="topFlagA'+i+'" href="javascript:void(0);"><span id="topFLagSpan'+i+'" onclick="showDiv('+i+')">先接收先办：</span> </a>';
	 tmp[2] = '	<input type="text" name=topFlag'+i+' id=topFlag'+i+'  value=3 />';
	 tmp[3] = '<input id="prcsOpUserNameSpan'+i+'" name="prcsOpUserNameSpan'+i+'" /><input type="text" name="prcsOpUser'+i+'" id="prcsOpUser'+i+'" >';
	 tmp[4] = '<input type="button" disabled="disabled" value="设置" id="selecteOpUserButton'+i+'" onclick="selectSingleUser(['+'\'prcsOpUser'+i+'\','+'\'prcsOpUserNameSpan'+i+'\'])" class="SmallButtonA"  />';
}
 tmp[5] ='</div>';
 tmp[6] ='<div style="line-height:18px;">经办人：<input id="prcsUserNameSpan'+i+'" name="prcsUserNameSpan'+i+'"   value="'+prcsUserDesc+'" />';
 tmp[7] ='<input type="text" name="prcsUser'+i+'" id="prcsUser'+i+'"  value="'+prcsUser+'" />';
 tmp[8] ='<input type="button" value="设置" id="selecteUserButton'+i+'"  onclick="selectUser(['+'\'prcsUser'+i+'\','+'\'prcsUserNameSpan'+i+'\'])" class="SmallButtonA"/>';
 tmp[9] ='</div>';
 tmp[10] ='<a  class="orgClear" href="javascript:void(0);">清空</a>&nbsp;&nbsp;';
 tmp[11] ='<a href="javascript:void(0);">可写字段</a><input type="hidden" id="freeItem'+i+'" name="freeItem'+i+'" value=""/>';
 var prcsText= tmp.join("");
 return prcsText;
}

function delFeedBackAttach(){}
/**
* 创建浮动菜单 zhp 20131020
*/
function showDiv(i){
	//topFlag
	//prcsOpUser prcsOpUserNameSpan  selecteOpUserButton
	//prcsUser prcsUserNameSpan selecteUserButton
	i = i || "";
		var menuData = new Array();
	  menuData.push({ name:'主办人',action:changTopFlag,extData:[1,i,'主办人']});
	  menuData.push({ name:'无主板会签',action:changTopFlag ,extData:[2,i,'无主板会签']});
	  menuData.push({ name:'先接收先办',action:changTopFlag,extData:[3,i,'先接收先办']});
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
</style>
</head>
<body onload="doInit()">
<center>
<form name="turnForm" id="turnForm" method="post" action="">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><img src="<%=imgPath %>/green_arrow.gif" align="absmiddle">&nbsp;&nbsp;<span class="big3" id="flowNameSpan">  - 转交下一步骤</span><br>
    </td>
  </tr>
</table>
<br>
<table id="prcsTable" class="TableList" width="95%">
    <tr>
      <td  class="TableHeader" nowrap colspan="2"><div style="float:left;font-weight:bold"><img src="<%=imgPath %>/workflow.gif" align="absmiddle"> 流水号流水号 - <span id="runNameSpan"></span></div></td>
    </tr>
   <tbody id="prcsListTbody">
   <!-- 
   <tr class="TableData">
       <td>第<span class="big4" style="color:red"></span>步：(下一步骤)<br><font id="preSetDiv" color="red" style="display:none">本步骤为预设步骤</font></td>
       <td id="prcsText123">
       
       
       <div style="line-height:18px;">
		<a id="topFlagA" href="javascript:void(0);"><span id="topFLagSpan" onclick="showDiv()">主办人：</span> </a>
			<input type="text" name=topFlag id=topFlag  value=0 />
			<input id="prcsOpUserNameSpan" /><input type="text" name="prcsOpUser" id="prcsOpUser">
			<input type="button" value="设置" id="selecteOpUserButton" onclick="selectSingleUser(['prcsOpUserNameSpan','prcsOpUser'])" class="SmallButtonA"/>
		</div>
		
		<div style="line-height:18px;">经办人：<input id="prcsUserNameSpan" />
			<input type="text" name="prcsUser" id="prcsUser" />
			<input type="button" value="设置" id="selecteUserButton" onclick="selectUser(['prcsUserNameSpan','prcsUser'])" class="SmallButtonA"/>
		</div>
		<a  class="orgClear" href="javascript:void(0);">清空</a>&nbsp;&nbsp;
		<a href="javascript:void(0);">可写字段</a><input type="hidden" id="freeItem" name="freeItem" value=""/>
    	
    	
       </td>
    </tr>
    -->
   </tbody>
   
    <tbody id="preListTbody"></tbody>
   <tr class="TableControl" height="30" id="previewDiv" >
      <td colspan="2">
      <span id="addBtn" >
       <input type="button" value="+ 增加下一个预设步骤"  onClick="addPre()" title="预设更多后续步骤的经办人和主办人" name="button">
       </span>
       <span id="delBtn" style="display:none" >
       <input type="button" value="- 删除最后一个预设步骤"  onClick="delPre()" title="删除最后一个预设步骤" name="button">
      </span>
      </td>
    </tr>
   
 	   <tr class="TableContent">
      <td colspan="2" height="20px"><div style="margin-bottom:5px;font-weight:bold"> 短信提醒</div>
       <div>
       <span id="sms2RemindSpan" style="display:none"><input type="checkbox" name="sms2Remind" id="sms2Remind"><img src="<%=imgPath %>/mobile_sms.gif"><label for="sms2Remind"><u style="cursor:pointer">手机短信</u></label></span>
       <input type="checkbox" name="smsRemind" id="smsRemind"><img src="<%=imgPath %>/sms.gif"><label for="smsRemind"><u style="cursor:pointer">内部短信</u></label>

    <input type="checkbox" name="webMailRemind" id="webMailRemind"  >
    <img src="<%=imgPath %>/webmail.gif"  ><label for="webMailRemind" ><u style="cursor:pointer">Internet邮件</u></label></div>
短信内容：<input type="text" onmouseover="this.focus()" onfocus="this.select()" id="smsContent" name="smsContent" value="" size="100" maxlength="100" class="SmallInput">
     </td>
    </tr>
    <tr class="TableContent">
      <td nowrap colspan="2">
      <font color=red>说明：</font>主办人是某步骤的负责人，可以编辑表单、公共附件和转交流程
      </td>
    </tr>
    <tr class="TableControl">
      <td nowrap align="center" colspan="2">
        <input type="button"  value="确认" onclick="turnNext()" class="SmallButtonW" name="mybutton">&nbsp;&nbsp;
     
      <input type="button" onclick="location='../inputform/index.jsp'" class=SmallButtonW value="编辑表单"/>  
  
     <input type="button" onclick="TurnNext_forwardPage()"  class=SmallButtonW value="返回列表"/>  
         <a id="view" style="display:none"  href="javascript:void(0)" ><span>更多操作</span></a>
      </td>
    </tr>
</table>
</form>
<input type="button" value="测试" onclick="turnNext()"/>
</center>
<div id="attachDiv" style="overflow: hidden"></div>
</body>
</html>
