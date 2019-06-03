<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.servlet.TeeResPrivServlet"%>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ contextPath + "/";
	//获取主题的索引号
	int styleIndex = 1;
	Integer styleInSession = (Integer) request.getSession().getAttribute("STYLE_TYPE_INDEX");
	
	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	
	
	if (styleInSession != null) {
		styleIndex = styleInSession;
	}
	String stylePath = contextPath + "/common/styles";
	String imgPath = stylePath + "/style" + styleIndex + "/img";
	String cssPath = stylePath + "/style" + styleIndex + "/css";
	String systemImagePath = contextPath+"/common/images";
	
	
	String loginOutText = (String) request.getSession().getAttribute("LOGIN_OUT_TEXT") == null ? "" : (String) request.getSession().getAttribute("LOGIN_OUT_TEXT");//退出提示语
	String ieTitle = (String) request.getSession().getAttribute("IE_TITLE") == null ? "" : (String) request.getSession().getAttribute("IE_TITLE");//主界面IEtitle
	String secUserMem = (String) request.getSession().getAttribute("SEC_USER_MEM") == null ? "" : (String) request.getSession().getAttribute("SEC_USER_MEM");//是否记忆用户名
%>
<!-- jQuery库 -->
<script src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>

<script type="text/javascript" >
window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
</script>

<!-- JBOX通用UI组件 -->
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/jbox-v2.3/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/jbox-v2.3/jBox/Skins/Blue/jbox.css" />

<!-- 其他工具库类 -->
<script src="<%=contextPath%>/common/js/tools.js"></script>
<script src="<%=contextPath%>/common/js/sys.js"></script>
<script src="<%=contextPath%>/common/js/src/orgselect.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>

<!-- jQuery Tooltip -->
<script type="text/javascript" src="<%=contextPath%>/common/tooltip/jquery.tooltip.min.js"></script>
<link rel="stylesheet" href="<%=contextPath%>/common/tooltip/jquery.tooltip.css" type="text/css"/>

<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/common/jqueryui/jquery.layout-latest.js"></script>
<script src="<%=contextPath%>/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript">

/** 常量定义 **/
var TDJSCONST = {
  YES: 1,
  NO: 0
};
/** 变量定义 **/
var contextPath = "<%=contextPath%>";
var imgPath = "<%=imgPath%>";
var cssPath = "<%=cssPath%>";
var stylePath = "<%=stylePath%>";
var loginOutText = "<%=loginOutText%>";
var uploadFlashUrl = "<%=contextPath%>/common/swfupload/swfupload.swf";
var commonUploadUrl = "<%=contextPath%>/attachmentController/commonUpload.action";
var systemImagePath = "<%=systemImagePath%>";
var rand = new Date().getTime()*parseInt((Math.random()*10000+"").split("\\.")[0]);

var loadFirst = true;
var xparent;
if(window.dialogArguments){
	xparent = window.dialogArguments;
}else if(window.opener){
	xparent = opener;
}else{
	xparent = window;
}
function parseNumber(value, defValue) {
  if (isNaN(value)) {
    return defValue;
  }
  return value * 1;
}
</script>


	<%@ include file="/header/upload.jsp" %>
	<%@ include file="/header/userheader.jsp" %>
	<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>流程数据编辑</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/images/workflow/index.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/workflowUtils.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
	<script>window.UEDITOR_HOME_URL = "<%=contextPath%>/common/ueditor/";</script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/lang/zh-cn/zh-cn.js"></script>
	
<script>

var contextPath = "<%=contextPath%>";
var runId = <%=runId%>;
var flowId = <%=flowId%>
var imgPath = '<%=imgPath%>';
var fileImgPath = imgPath +'/dll.gif';
var fileDelImgPath = imgPath +'/remove.png';
var delfeedbackPath = imgPath +'/delete.gif';
var lookfeedbackPath = imgPath +'/focus.gif';
var runName;
var sealForm = 2;
var userName = "<%=person.getUserName()%>";
var userIdDesc = "<%=person.getUserId()%>";
var feedback = 1;
var attachPriv = 1;
var flowPrcs;
var userId = <%=loginUser.getUuid()%>;
//#######################重要的签章 参数 zhp 20131114 解决表单印章 盖章 验章 参数################################//
var signJson = {};//需要验章的 字段
var signArray = new Array();//盖了章的 data
var noSignArray =  new Array();//没有盖章的 data

var ctrlSignArray = [];//控件签章data数组
var ctrlRandArray = [];//控件随机数数组

//#######################重要的签章 参数 结束##################################################################//
var officePriv = 1+2+4+8+16+32+64+128+256;//office文档权限

var pubAttachUploader;//公共附件上传者
var feedbackAttachUploader;//会签附件上传者
var flowType;
var frpModel;
var attachmentId;

function loadFlowRunDoc(){
	//获取正文对象flowRunDoc
	var url = contextPath +"/flowRunDocController/getFlowRunDocByRunId.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	var flowRunDoc = json.rtData;
	if(flowRunDoc!=null){//如果没有正文，则判断当前权限是否有创建正文的权限
		attachmentId = flowRunDoc.docAttach.sid;
		$("#editDocBtn").show();
	}
}

function editFlowRunDoc(){
	openFullWindow(contextPath+"/system/core/ntko/indexNtko.jsp?attachmentId="+attachmentId+"&attachmentName=&moudle=workFlow&op=4");
}

function doInit(){
		
	loadFlowRunDoc();
// 	try{	
		reload();
		
		var para = {runId:runId};
		//创建公共附件快速上传组件
		pubAttachUploader = new TeeSWFUpload({
			fileContainer:"pulicAttachments",//文件列表容器
			renderContainer:"pulicAttachmentsTemp",//临时列表容器
			uploadHolder:"pubUpfile",//上传按钮放置容器
			valuesHolder:"pubUpfileValues",//附件主键返回值容器，是个input
			showUploadBtn:false,//不显示上传按钮
			quickUpload:true,//快速上传
			queueComplele:function(){//队列上传成功回调函数
				
			},
			renderFiles:true,//渲染附件
			url:contextPath+"/teeWorkflowAttachmentController/addWorkFlowAttachments.action",
			post_params:para//后台传入值，model为模块标志
		});
		
// 	}catch(e){
		
// 	}
}

function reload(){
	//top.$.jBox.tip("正在加载","loading");
	if(loadHandlerInfo()){
		//获取会签控件数据
		loadCtrlFeedbackDatas();
		/**
		 *获取公共附件
		 */
		loadWorkFlowAttach();
		LoadSignData();
	}
	

}

function loadCtrlFeedbackDatas(){
	try{
		for(var i=0;i<ctrlSignArray.length;i++){
			var item = ctrlSignArray[i];
			var itemId = item.replace("DATA_","");
			$("#"+item).after($("<div id=\"SIGN_POS_CTRL_"+item+"_"+rand+"\"></div>"));
		}
	
		var DWebSignSeal = document.getElementById("DWebSignSeal");
		for(var i=0;i<ctrlRandArray.length;i++){
			var name = ctrlRandArray[i];
			var ctrlSign = $("#CTRL_SIGN_"+name).val();
			var ctrlContent = $("#CTRL_CONTENT_"+name).val();
	
			if(ctrlSign && ctrlContent){
				//加载签章数据
				DWebSignSeal.SetCurrUser(userName+"["+userIdDesc+"]");
		        DWebSignSeal.SetStoreData(ctrlSign);
			}
		}
		DWebSignSeal.ShowWebSeals();
	
		for(var i=0;i<ctrlRandArray.length;i++){
			var datas = ctrlRandArray[i];
			var name = datas.split("_")[0]+"_"+datas.split("_")[1];
			var rand0 = datas.split("_")[2];
			var ctrlSign = $("#CTRL_SIGN_"+datas).val();
			var ctrlContent = $("#CTRL_CONTENT_"+datas).val();
			var hw = name+"_hw_"+rand0;
			var seal = name+"_seal_"+rand0;
			var strObjectName = DWebSignSeal.FindSeal(hw,2);
	    	if(strObjectName!=""){
	    		DWebSignSeal.SetSealSignData(strObjectName,ctrlContent);
	    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
			}
	    	strObjectName = DWebSignSeal.FindSeal(seal,2);
	    	if(strObjectName!=""){
	    		DWebSignSeal.SetSealSignData(strObjectName,ctrlContent);
	    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
			}
		}
	}catch(e){}
}

//加载处理数据
function loadHandlerInfo(){
	var url = contextPath+"/flowRun/getEditHtmlModel.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	if(json.rtState){
		$("#formDiv").html(json.rtData.form+"<script>"+json.rtData.script+"<\/script><style>"+json.rtData.css+"<\/style>");
		flowType = json.rtData.flowType;
		if(loadFirst){
			initialize();
			loadFirst = false;
		}
		return true;
	}else{
		messageMsg(json.rtMsg,"center-container","error");
		return false;
	}
}



function saveFlowRunData(flag,parentCallback){
	//组合列表数据控件
	xCalculating();
	xlistCalculating();
	var validateList = listViewPreSaving($("#formDiv"));
	if(validateList==false){
		return;
	}

	var sign_val = "";
	try{
		//网页签章保存
// 		for(var i=0;i<signArray.length;i++){
// 			var oldstr="";
// 		    var objName_hw = DWebSignSeal.FindSeal(signArray[i]+"_hw",2);
// 		    var objName_seal = DWebSignSeal.FindSeal(signArray[i]+"_seal",2);
// 		    if(objName_hw=="" && objName_seal=="")
// 	            sign_val="";
// 	        else
// 	    	    sign_val=DWebSignSeal.GetStoreDataEx(signArray[i]+"_hw"+";"+signArray[i]+"_seal");
// 			$("#"+signArray[i]).attr("value",sign_val);
// 		}
	}catch(e){
		
	}
	

	if(flag){
		top.$.jBox.tip("正在保存","loading");
	}

	var url = contextPath+"/flowRun/editFlowRunData.action";
	var para = tools.formToJson($("#form"));
	para["runId"] = runId;
	para["flowId"] = flowId;
	
	//处理含有ckeditor的textarea
	var txts = $("textarea[disabled!=disabled][rich=1][ck][xtype='xtextarea']");
	for(var i=0;i<txts.length;i++){
		var tmp = $(txts);
		var ckInstance = eval("window."+tmp.attr("name")+"_CK");
		para[tmp.attr("name")] = ckInstance.getData();
	}
	
	var json = tools.requestJsonRs(url,para);
	if(!json.rtState){
		top.$.jBox.tip(json.rtMsg,"error");
		return;
	}
	
	//表单控件会签意见保存
// 	for(var i=0;i<ctrlSignArray.length;i++){
// 		var item = ctrlSignArray[i];
// 		var itemId = ctrlSignArray[i].replace("DATA_","");
// 		var itemData = $("#"+item).val();
// 		var objName_hw = DWebSignSeal.FindSeal(item+"_hw_"+rand,2);
// 	    var objName_seal = DWebSignSeal.FindSeal(item+"_seal_"+rand,2);
// 	    if(objName_hw=="" && objName_seal==""){
// 	    	sign_val = "";
// 		}else{
// 			sign_val = DWebSignSeal.GetStoreDataEx(item+"_hw_"+rand+";"+item+"_seal_"+rand);
// 		}
// 		if(itemData!=""){//保存盖章数据
// 			var url = contextPath+"/flowRun/saveCtrlFeedback.action";
// 			var json = tools.requestJsonRs(url,{runId:runId,itemId:itemId,content:itemData,rand:rand,signData:sign_val});
// 		}
// 	}
	

	alert("保存成功！");
	
	return true;
}
/**
 * 获取工作流 公共附件 zhp 20131020
 */
function loadWorkFlowAttach(){
	var url = contextPath+"/teeWorkflowAttachmentController/getTeeWorkFlowAttachment.action";
	var para  = {};
	para["runId"] = runId;
	$("#pulicAttachments").html("");
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var attachList = json.rtData;
		var attStrHtml = "";
		$(parent.attach_count).html(attachList.length);
		for(var j=0;j<attachList.length;j++){
			var att = attachList[j];
			if(attachPriv==0){
				att.priv = 3;
			}else{
				if(att.userId==userId){
					att.priv = 1+2+4+8+16+32;
				}else{
					att.priv = 3;
				}
			}
			var attachElement = tools.getAttachElement(att,{});
			attachElement.append("&nbsp;&nbsp;<span style=color:#0080ff>("+att.sizeDesc+")&nbsp;&nbsp;"+att.userName+"&nbsp;&nbsp;"+att.createTimeDesc+"</span>");
			$("#pulicAttachments").append(attachElement);
		}
	}else{
		alert(json.rtMsg);
	}
}

/**
 * 新建附件 zhp 20131020
 */
function newAttach(){
	var newType = $("#new_type").val();
	var newName = $("#new_name").val();

	var para  = {};
	para["runId"] = runId;
	para["newType"] = newType;
	para["newName"] = newName;

	var newAttachUrl = contextPath +"/teeWorkflowAttachmentController/createNewAttach.action";
	var json = tools.requestJsonRs(newAttachUrl,para);
	if(json.rtState){
		loadWorkFlowAttach();
		var attachId = json.rtData.attachId;
		var attachName = json.rtData.attachName;
		var ntkoURL = contextPath +  "/system/core/ntko/indexNtko.jsp?attachmentId="+attachId+"&attachmentName="+attachName+"&moudle=workFlow&op=4";
		top.bsWindow(encodeURI(ntkoURL),"在线文档编辑",{buttons:[]});
	}else{
		top.$.jBox.tip(json.rtMsg,"info");
	}
}


</script>
</head>
<body onload="doInit()" style="margin:0px;overflow:hidden;">
<div style="position:absolute;height:50px;left:0px;right:0px;bottom:0px;background:#f0f0f0;text-align:center;border-top:1px solid gray;z-index:1000">
	<button style="margin-top:10px;" onclick="saveFlowRunData()">保存</button>
	&nbsp;&nbsp;
	<button style="margin-top:10px;display:none" id="editDocBtn" onclick="editFlowRunDoc()">编辑正文</button>
</div>
<!-- Center -->
<div id="center-container" class="pane ui-layout-center" style="position:absolute;left:0px;right:0px;top:0px;bottom:50px;overflow:auto;">
	<!-- 表单渲染区域 -->
	<form id="form">
		<div id="formDiv"  class="wf"></div>
	</form>
	
	<!-- 公共附件区开始 zhp 20131020 -->
	<div name="pulicAttachmentForm" id="pulicAttachmentForm" method="post" enctype="multipart/form-data">
		<div id="workFlowAttach">
			<table style="width:90%" align="center" class="TableBlock">
				<thead>
					<tr>
						<td class="TableHeader"  id="attaHeader">公共附件</td>
					</tr>
				</thead>
				<tbody>
					<tr height="25">
						<td class="TableData">
						<div id="pulicAttachments"></div>
						<div id="pulicAttachmentsTemp"></div>
						</td>
					</tr>
					<tr height="25" id="newAttachTR">
						<td class="TableData"><input id="new_type1" name="doc_type"
							onclick="document.all('new_type').value='doc'" checked="checked"
							value="" type="radio"> <label for="new_type1">word文档</label>
						<input id="new_type2" onclick="document.all('new_type').value='xls'"
							name="doc_type" value="" type="radio"> <label
							for="new_type2">excel文档</label> <input id="new_type3"
							onclick="document.all('new_type').value='ppt'" name="doc_type"
							value="" type="radio"> <label for="new_type3">ppt文档</label>&nbsp;&nbsp;
						<b>附件名：</b> <input class="BigInput" name="new_name" id="new_name" value="新建文档"> 
							<button type="button" class="btn btn-success" onclick="newAttach()">在线创建附件</button> <input name="new_type" id="new_type" type="hidden" value="doc" />
						</td>
					</tr>
					<tr id="addAttachTR">
						<td class="TableData">
						<div id="pubUpfile" class="add_swfupload">
							<a href="javascript:void(0)"><span>添加附件</span></a>
						</div>
						<input type="hidden" id="pubUpfileValues" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>	
	<!--公共附件区结束-->
</div>
<script>
/**
 * 
 * @param item
 * @param callback - 回掉函数
 * @return
 */
function show_seal(item,callback)
{
  var URL = contextPath + "/system/core/workflow/websign/sel_seal/index.jsp?item=" + item +"&callback=" + callback;
  dialog(URL,  470, 400);
}

function show_ctrl_seal(item,callback){
	var URL = contextPath + "/system/core/workflow/websign/sel_seal/index1.jsp?item=" + item +"&callback=" + callback;
	dialog(URL,  470, 400);
}

function lookSeal(sid){
	var sealUrl = contextPath + "/system/core/workflow/flowrun/prcs/feedbackSealLook.jsp?sid="+sid;
	top.$.jBox.open("iframe:"+sealUrl,"查看签章",500,300);
}
function WebSignAddSeal(item,seal_id) {
	  var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    DWebSignSeal.SetCurrUser(userName+"["+userIdDesc+"]");
	    DWebSignSeal.SetSignData("-");
	    DWebSignSeal.SetSealSignData("SIGN_INFO","ZHONGAN");
	    DWebSignSeal.SetPosition(10,-30,"SIGN_INFO_FEEDBACK");
	    if (sealForm == 1 )  {
	      DWebSignSeal.addSeal("", "SIGN_INFO");
	    } else {
	      if(typeof seal_id=="undefined") {
	        show_seal(item,"WebSignAddSeal");
	      } else {
	        var URL = contextPath+"/sealManage/stampPriv.action?sid=" + seal_id;
	        var obj_name = DWebSignSeal.AddSeal(URL, "SIGN_INFO");
	       
	      }
	    }
	    //DWebSignSeal.SetMenuItem(obj_name,0x8000);
	    //DWebSignSeal.SetMenuItem(obj_name,0x100);
	   DWebSignSeal.SetMenuItem(obj_name,5);
	  } catch (err) {
	    alert('websign控件没有加载成功！')
	  }
	}
function signSubmit() {
	  var DWebSignSeal=document.getElementById("DWebSignSeal");
	  var sing_info_str="";
	  try {
	   var strObjectName = DWebSignSeal.FindSeal("",0);
	   while(strObjectName)  {
	      //判断是属于会签意见签章 并且会签名称不能是宏标记里调用显示的，即新添加的手写或签章

	     // if(strObjectName.indexOf("SIGN_INFO")>=0 && sign_info_object.indexOf(strObjectName+",")<0) {
		 if(strObjectName.indexOf("SIGN_INFO")>=0) {     
	        sing_info_str += strObjectName+";";
	      }
	      strObjectName = DWebSignSeal.FindSeal(strObjectName,0);
	    }
	    if(sing_info_str!="") {
	      $('#FEEDBACK_SIGN_DATA')[0].value=DWebSignSeal.GetStoreDataEx(sing_info_str);
	    } else {
	      $('#FEEDBACK_SIGN_DATA')[0].value="";
	    }
	  } catch (err) {
	    //alert('websign控件没有加载成功！')
	  }
	  return sing_info_str;
	}

function WebSignHandWritePop() {
	  var DWebSignSeal=document.getElementById("DWebSignSeal");
	  try {
	    DWebSignSeal.SetCurrUser(userName+"["+userIdDesc+"]");
	    DWebSignSeal.SetSignData("-");
	    DWebSignSeal.SetPosition(0,-30,"SIGN_INFO_FEEDBACK");
	    var obj_name=DWebSignSeal.HandWritePop(0,255,0,0,0,"SIGN_INFO");
	    DWebSignSeal.SetSealSignData("SIGN_INFO","ZHONGAN");
	    DWebSignSeal.SetMenuItem(obj_name,5);
	  } catch (err) {
	   // alert('websign控件没有加载成功！')
	  }
	}

function handWrite(item  , color) {
	  var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    if(DWebSignSeal.FindSeal(item+"_hw",2)!="") {
	      top.$.jBox.tip("您已经签章，请先删除已有签章！","error");
	      return;
	    }
	    var fontColor = 255;
	    if(typeof(color) == "string" && color)
	    {
	      fontColor = parseInt(color);
	    }
	    var str=SetStore(item);
	    DWebSignSeal.SetCurrUser(userName+"["+userIdDesc+"]");//设置用户名
	    DWebSignSeal.SetPosition(0,0,"SIGN_POS_"+item);
	    DWebSignSeal.HandWritePop(0,255,0, 400,300,item+"_hw");
	    DWebSignSeal.SetSealSignData(item+"_hw",str);
	    //DWebSignSeal.SetMenuItem(item+"_hw",261);
	    //$("#"+item).attr("value",DWebSignSeal.GetStoreData());
	  } catch (err) {
	    alert(err)
	  }
	}

function handCtrlWrite(item ,color) {
	  var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    if(DWebSignSeal.FindSeal(item+"_hw_"+rand,2)!="") {
	      top.$.jBox.tip("您已经签章，请先删除已有签章！","error");
	      return;
	    }
	    var fontColor = 255;
	    if(typeof(color) == "string" && color)
	    {
	      fontColor = parseInt(color);
	    }
	    var str=$("#"+item).val();
	    DWebSignSeal.SetSignData("-");
	    DWebSignSeal.SetSignData("+DATA:" + str);
	    DWebSignSeal.SetCurrUser(userName+"["+userIdDesc+"]");//设置用户名
	    DWebSignSeal.SetPosition(0,0,"SIGN_POS_CTRL_"+item+"_"+rand);
	    DWebSignSeal.HandWritePop(0,255,0, 400,300,item+"_hw_"+rand);
	    DWebSignSeal.SetSealSignData(item+"_hw_"+rand,str);
	    //DWebSignSeal.SetMenuItem(item+"_hw",261);
	    //$("#"+item).attr("value",DWebSignSeal.GetStoreData());
	  } catch (err) {
	    alert(err)
	  }
	}

function addSeal(item,seal_id) {
	  var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    if(DWebSignSeal.FindSeal(item+"_seal",2)!="") {
	  	alert("您已经签章，请先删除已有签章！");
	      return;
	    }
	    var str = SetStore(item);
	    DWebSignSeal.SetCurrUser(userName+"["+userIdDesc+"]");//设置用户名
	    DWebSignSeal.SetPosition(0,0,"SIGN_POS_"+item);
	    if (sealForm == 1 ) {
	      DWebSignSeal.addSeal("", item+"_seal");
	    } else {
	      
	      if(typeof seal_id == "undefined") {
	        show_seal(item,"addSeal");
	      } else {
	    	  var URL = "http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=contextPath%>/sealManage/stampPriv.action?sid=" + seal_id;
	    	  DWebSignSeal.AddSeal(URL, item+"_seal");
	      }
	    }
	    //DWebSignSeal.SetCurrUserName(userName);
	    DWebSignSeal.SetSealSignData(item+"_seal",str); 
	    //DWebSignSeal.SetMenuItem(item+"_seal",261);
	    WebSign_Submit();
	  } catch (err) {
	    //alert('websign控件没有加载成功！')
	  }
	}

function addCtrlSeal(item,seal_id) {
	  var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    if(DWebSignSeal.FindSeal(item+"_seal_"+rand,2)!="") {
	  	alert("您已经签章，请先删除已有签章！");
	      return;
	    }
	    var str=$("#"+item).val();
	    DWebSignSeal.SetSignData("-");
	    DWebSignSeal.SetSignData("+DATA:" + str);
	    DWebSignSeal.SetCurrUser(userName+"["+userIdDesc+"]");//设置用户名
	    DWebSignSeal.SetPosition(0,0,"SIGN_POS_CTRL_"+item+"_"+rand);
	    DWebSignSeal.SetSealSignData(item+"_seal_"+rand,str);
	    
	    if (sealForm == 1 ) {
	      DWebSignSeal.addSeal("", item+"_seal_"+rand);
	    } else {
	      
	      if(typeof seal_id == "undefined") {
	        show_ctrl_seal(item,"addCtrlSeal");
	      } else {
	    	  var URL = "http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=contextPath%>/sealManage/stampPriv.action?sid=" + seal_id;
	    	  DWebSignSeal.AddSeal(URL, item+"_seal_"+rand);
	      }
	    }
	    //DWebSignSeal.SetCurrUserName(userName);
	    DWebSignSeal.SetSealSignData(item+"_seal_"+userId,str); 
	    //DWebSignSeal.SetMenuItem(item+"_seal",261);
	    WebSign_Submit();
	  } catch (err) {
	    //alert('websign控件没有加载成功！')
	  }
	}

function SetStore(item)
{
  var DWebSignSeal = document.getElementById("DWebSignSeal");
  try {
	  var str= GetDataStr(item);
	    DWebSignSeal.SetSignData("-");
	    DWebSignSeal.SetSignData("+DATA:" + str);
		return str;
	return str;
  } catch (err) {
    alert('SetStore方法报错!websign控件没有加载成功！')
  }
}

function LoadSignData() {
	
	  var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    for(var i=0;i<signArray.length;i++) {
	      if(signArray[i] && $("#"+signArray[i])){
	    	DWebSignSeal.SetCurrUser(userName+"["+userIdDesc+"]");
	        DWebSignSeal.SetStoreData($("#"+signArray[i])[0].value);
	      }
	    }
	    DWebSignSeal.ShowWebSeals();
	    var strObjectName ;
	    for(var i=0;i<signArray.length;i++) {
	      if(signArray[i] && $("#"+signArray[i])){
	    	  var hw = signArray[i]+"_hw";
		    	var seal = signArray[i]+"_seal";
		    	var strObjectName = DWebSignSeal.FindSeal(hw,2);
		    	str = GetDataStr(signArray[i]);
		    	if(strObjectName!=""){
		    		DWebSignSeal.SetSealSignData(strObjectName,str);
		    		DWebSignSeal.SetMenuItem(strObjectName,1+2+4+8+16+32+64+128+256);
				}
		    	strObjectName = DWebSignSeal.FindSeal(seal,2);
		    	if(strObjectName!=""){
		    		DWebSignSeal.SetSealSignData(strObjectName,str);
		    		DWebSignSeal.SetMenuItem(strObjectName,1+2+4+8+16+32+64+128+256);
				}
	      }
	    }
	    
	    /**
	    strObjectName = DWebSignSeal.FindSeal("",0);
	    while(strObjectName) {
	    	if(strObjectName.endWith("_hw"))
		         item_arr = strObjectName.split("_hw");
		     else if(strObjectName.endWith("_seal"))
		         item_arr = strObjectName.split("_seal");
		    if(item_arr){
		    	str = GetDataStr(item_arr[0]);
		    	DWebSignSeal.SetSealSignData(strObjectName,str);
			 }
			 alert();
	      DWebSignSeal.SetMenuItem(strObjectName,1+2+4+8+16+32+64+128+256);
	      strObjectName = DWebSignSeal.FindSeal("",0);
	    }
	    **/
	  } catch (err) {
		    //alert('websign控件没有加载成功！')
	 }
}
function LoadSignData22() {
	  var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    for(var i=0;i<signArray.length;i++) {
	      if(signArray[i] && $(signArray[i])){
	        DWebSignSeal.SetStoreData($("#"+signArray[i])[0].value);
	      }
	    }
	    DWebSignSeal.ShowWebSeals();
	   
	    var str= "";
	    var strObjectName ;
	    var item_arr;
	    strObjectName = DWebSignSeal.FindSeal("",0);
	    while(strObjectName  != "")
	    {
	      if(strObjectName.indexOf("_hw")>0)
	         item_arr = strObjectName.split("_hw");
	      else if(strObjectName.indexOf("_seal")>0)
	         item_arr = strObjectName.split("_seal");
	      if(item_arr)
	      {
	        //str = GetDataStr(item_arr[0]);
	        //DWebSignSeal.SetSealSignData(strObjectName,"");
	        var tmp = "4";
	        if (opFlag == 1) {
	          tmp = '261';
	        }
	        DWebSignSeal.SetMenuItem(strObjectName , tmp);
	      }
	      strObjectName = DWebSignSeal.FindSeal(strObjectName,0);
	    }
	  //  DWebSignSeal.SetCurrUser(1 + "["+ 11 +"]");
	    //加载完成标识
	    SignLoadFlag = true;
	  } catch (err) {
	    //alert('websign控件没有加载成功！')
	  }
	}
function GetDataStr(item) {
	  if(typeof item == 'undefined') {
	    return; 
	  }
	  var str="";
	  var separator = "::";  // 分隔符
	  var TO_VAL = signJson[item];
	  if(TO_VAL!="") {
	    var item_array = TO_VAL.split(",");
	    for (i=0; i < item_array.length; i++) {
	      var MyValue="";
	      if (item_array[i] && $("#"+item_array[i])){
	        var obj =  $("#"+item_array[i])[0];
	        if(obj.type=="checkbox"){
	          if(obj.checked==true) {
	            MyValue="on";
	          } else {
	            MyValue="";
	          }
	        } else {
	          MyValue=obj.value;
	        }
	        str += obj.name + "separator" + MyValue + "\n";
	      }
	    }
	  }
	  return str;
	}
function WebSign_Submit(){ 
	  var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    var sign_val;
	    for(var i=0;i<noSignArray.length;i++)
	    {
	      if(noSignArray[i]!="")
	      {
	        var oldstr="";
	        var objName_hw = DWebSignSeal.FindSeal(noSignArray[i]+"_hw",2);
	        var objName_seal = DWebSignSeal.FindSeal(noSignArray[i]+"_seal",2);
	        //保存兼容老数据，老数据存在本次可写的第一个字段里。
	          if(objName_hw=="" && objName_seal=="")
	            sign_val="";
	          else
	            sign_val=DWebSignSeal.GetStoreDataEx(noSignArray[i]+"_hw"+";"+noSignArray[i]+"_seal");
		     //alert(noSignArray[i]+"::"+sign_val);
	        $("#"+noSignArray[i])[0].value=sign_val ;
	      }
	    }
	  } catch (err) {
	    alert('websign控件没有加载成功！')
	  }
	}
</script>
<div id="attachDiv" style="overflow: hidden"></div>
</body>
</html>