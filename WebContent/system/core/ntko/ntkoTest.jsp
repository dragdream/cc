<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/header/header.jsp"%>
<%
  String op = request.getParameter("op") == null ? "4" : request.getParameter("op");
  String attachmentName = request.getParameter("attachmentName") == null ? "" : request.getParameter("attachmentName");
  String attachmentId = request.getParameter("attachmentId") == null ? "" : request.getParameter("attachmentId");
  String moudle = request.getParameter("moudle")== null ? "" : request.getParameter("moudle");
  String signKey = request.getParameter("signKey") == null ? "" : request.getParameter("signKey");
  String print = request.getParameter("print") == null ? "" : request.getParameter("print");
  moudle = "workFlow";
  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>在线文档编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css">
	<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
		<!-- 引入respond.js解决IE8显示问题 -->
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/respond.js"></script>
	<script type="text/javascript" charset="UTF-8"></script>
<script type="text/javascript" src="./tanger.js"></script>
<script type="text/javascript" src="./tangerUtil.js"></script>

<script type="text/javascript">
  var op = "<%=op %>"; 
  var print = "<%=print %>"; 
  var attachmentName = "<%=attachmentName %>";
  var attachmentId = "<%=attachmentId %>";
  var moudle = "<%=moudle %>";
  var TANGER_OCX_str;//文档路径或者URL
  var TANGER_OCX_obj;//文档的自动化接口
  var secOcRevision;//是否显示/隐藏痕迹
  var secOcMarkDefault;//默认不/保留痕迹
  var secOcMark;//保留痕迹设置
  var logPage ;//附件操作日志
 // TANGER_OCX_attachURL = "/oaop/ntko/newWord.action" ;
  function doInit(){
    TANGER_OCX_SetInfo();
  //此函数在网页装载时被调用。用来获取控件对象并保存到TANGER_OCX_OBJ
  //同时，可以设置初始的菜单状况，打开初始文档等等。

   
   
   //var URL = "/oaop/ntko/newWord.action";
  // $('#TANGER_OCX_attachURL')[0].innerHTML = URL;
  
   
    initMateTree();
    try{
		var div = parent.document.getElementById("jbox-content");
		$(div).css({overflowY:"hidden"});
	}catch(e){}
    
  }
  
  //window.attachEvent('onbeforeunload',ColseDoc);
</script>
<script type="text/javascript">
var showit = false;	
function showMate(){ 
  document.getElementById("sideBarContents").style.display="none";
	document.getElementById("imgshow").src = "images/slide-button.gif";
	document.getElementById("sideBarContents").style.display="none";
   if(showit == false){//没有显示
     document.getElementById("ifraClass").style.display="";
     document.getElementById("sideBarContents").style.display="";
     document.getElementById("imgshow").src = "images/slide-button-active.gif";
     showit = true;
   }else{
     document.getElementById("sideBarContents").style.display="none";
     document.getElementById("ifraClass").style.display="none";
     document.getElementById("imgshow").src = "images/slide-button.gif";
     showit = false;
   }
}

/**
 * 初始化树
 */
function initMateTree(){
  var guid = $("attachmentId").value; 
  if(guid){
    if (window["tree"]) {
      window["tree"].initMateTree(guid);
    }
  }
}
/**
 * 查看日程信息
 * @param state- 状态  0-未读  1- 已读
 */
function testModel()
{
	var url = contextPath + "/mail/readMailForOther.action?type=0&ifBox=0&mailId=455";
	
 	top.bsWindow(url ,"查看邮件详情",{width:"600",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}
</script>
</head>
<body onload="doInit()">
<form name="form1" id="form1" method="post" action="<%=request.getContextPath() %>/ntko/updateFile.action" enctype="multipart/form-data">
<table width=100% height=100% class="small" cellspacing="1" cellpadding="3" align="center">
<tr width=100%>
<td valign=top width=100>
	<div class="btn-group-vertical" style="width:100%">
		<button id="doc_fun_save" type="button" class="btn btn-info" onclick="testModel();">保存文件</button>
		<button id="doc_fun_pageSet" type="button" class="btn btn-default" onclick="TANGER_OCX_ChgLayout()">页面设置</button>
		<button id="doc_fun_print" type="button" class="btn btn-default" onclick="TANGER_OCX_PrintDoc()">打印</button>
	</div>
	<br/>
	<br/>
	<div class="btn-group-vertical" style="width:100%;display:none">
		<button id="doc_edit_saveMark" type="button" class="btn btn-default">保留痕迹</button>
		<button id="doc_edit_noMark" type="button" class="btn btn-default">不留痕迹</button>
		<button id="doc_edit_showRev" type="button" class="btn btn-default">显示痕迹</button>
		<button id="doc_edit_disRev" type="button" class="btn btn-default">隐藏痕迹</button>
		<button id="doc_edit_redWord" type="button" class="btn btn-default">文件套红</button>
	</div>
	<br/>
	<br/>
	<div class="btn-group-vertical" style="width:100%;display:none">
		<button id="e_id_check" type="button" class="btn btn-default">验证签名及印章</button>
		<button id="e_id_handSign2" type="button" class="btn btn-default">全屏手写签名</button>
		<button id="e_id_handDraw2" type="button" class="btn btn-default">全屏手工绘图</button>
		<button id="e_id_handSign1" type="button" class="btn btn-default">插入手写签名</button>
		<button id="e_id_handDraw1" type="button" class="btn btn-default">插入手工绘图</button>
		<button id="e_id_addSign1" type="button" class="btn btn-default">加盖电子印章</button>
	</div>
</td>
<td width=100% valign="top">
<div style="z-index:-1;"> 
<object  id="TANGER_OCX" classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404" codebase="./OfficeControl.cab#version=5,0,1,1" width="99%" height="800">

<param name="IsNoCopy" value="0">
<param name="FileSave" value="1">
<param name="FileSaveAs" value="1">

<param name="wmode" value="transparent">
<param name="BorderStyle" value="1">
<param name="BorderColor" value="14402205">
<param name="TitlebarColor" value="14402205">
<param name="TitlebarTextColor" value="0">
<param name="Caption" value="Office文档在线编辑">
<param name="IsShowToolMenu" value="-1">
<param name="IsHiddenOpenURL" value="0">
<param name="IsUseUTF8URL" value="1">
<param name="MakerCaption" value="中国兵器工业信息中心通达科技">
<param name="MakerKey" value="EC38E00341678B7549B46F19D4CAF4D89866B164">
<param name="ProductCaption" value="Office Anywhere">
<param name="ProductKey" value="460655BF84C22ADA846B8AC7E4B3089882E368B3">
<SPAN STYLE="color:red"><br>不能装载文档控件，请设置好IE安全级别为中或中低，不支持非IE内核的浏览器。</SPAN>
</object>
</div>
<div id="ocLog" style="display:none;padding-left:40px;" >
  <div align="center"><h2>操作日志</h2></div>
  <div id="list"style="display:none;"></div>
   <div id="msrg" align="center" ></div>
  <div align="center" style="padding-bottom:10px;">
  <input type="button" class="BigButton" onclick="getLog();" value="刷新日志">
  <input type="button" class="BigButton" onclick="ShowLog();" value="隐藏日志">
</div>
</div>
</td>
</tr>
</table>
<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
  //文档被关闭时执行的操作

  TANGER_OCX_OnDocumentClosed();
</script>
<script language="JScript" for=TANGER_OCX event="OnWordWPSSelChange(obj)">
  //文档被关闭时执行的操作

  isMustSave = true;
 // TANGER_OCX_OnDocumentClosed();
</script>
<script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
  //文档被关闭时执行的操作

  //TANGER_OCX_str 已被自动复值

  //alert(TANGER_OCX_obj);
  //TANGER_OCX_bDocOpen = true;
  TANGER_OCX_OnDocumentOpened(TANGER_OCX_str, TANGER_OCX_obj);
  TANGER_OCX_SetReadOnly(false);
  if(findStr(TANGER_OCX_filename,".doc")){
    if(secOcRevision == "1") {
      TANGER_OCX_ShowRevisions(true);
    } else{
      TANGER_OCX_ShowRevisions(false);
    } 
    if(op != 4 && op != 7){
      TANGER_OCX_SetReadOnly(true);
      if (op == 5) {
         TANGER_OCX_EnableFilePrintMenu(false);//禁止打印
      }
      //TANGER_OCX_EnableFilePrintMenu(false);
      TANGER_OCX_EnableFilePrintPreviewMenu(false);
    } 
    if (op == 7) {
      TANGER_OCX_EnableFilePrintMenu(false);//禁止打印
    }
    
  }
  if (secOcMark == "0" || secOcMark == "2") {//允许保留痕迹
    if (secOcMarkDefault == "0") {//默认不保留痕迹

      TANGER_OCX_SetMarkModify(false);
    } else if (secOcMarkDefault == "1") { //默认保留痕迹
      TANGER_OCX_SetMarkModify(true);
    }
  } else if (secOcMark == "1") {//强制保留痕迹
    TANGER_OCX_SetMarkModify(true);
  } else if (secOcMark == "3") {//强制不保留痕迹

    TANGER_OCX_SetMarkModify(false);
  }
</script>

<span id="TANGER_OCX_op" style="display:none"><%=op %></span>
<span id="TANGER_OCX_filename" style="display:none"><%=attachmentName %></span>
<span id="TANGER_OCX_attachName" style="display:none"><%=attachmentName %></span>
<span id="TANGER_OCX_attachURL" style="display:none"><%=contextPath %>/ntko/readFile.action?id=<%=attachmentId %>&model=<%=moudle %></span>
<span id="TANGER_OCX_user" style="display:none"><%=1 %></span>
<INPUT type=hidden NAME="attachmentId" id="attachmentId" value="<%=attachmentId %>">
<INPUT TYPE=hidden NAME="attachmentName" id="attachmentName" value="<%=attachmentName %>">
<INPUT TYPE=hidden NAME="moudle"  id="moudle" value="<%=moudle %>">
<input type="hidden" id="docSize" name="docSize" value="100">
<input style="display:none" type="file" name="file" />
</FORM>
</body>
</html>

