<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>在线文档编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%
  String op = request.getParameter("op") == null ? "4" : request.getParameter("op");
  String attachmentName = request.getParameter("attachmentName") == null ? "" : request.getParameter("attachmentName");
  String attachmentId = request.getParameter("attachmentId") == null ? "" : request.getParameter("attachmentId");
  String moudle = request.getParameter("moudle")== null ? "" : request.getParameter("moudle");
  String signKey = request.getParameter("signKey") == null ? "" : request.getParameter("signKey");
  String print = request.getParameter("print") == null ? "" : request.getParameter("print");
  moudle = "workFlow";
  
  TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
  
%>

<script type="text/javascript" src="./tanger.js?v=2"></script>
<script type="text/javascript" src="./tangerUtil.js?v=2"></script>

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
  var contextPath = "<%=request.getContextPath()%>";
  var user = "<%=loginUser.getUserName()%>";
 // TANGER_OCX_attachURL = "<%=request.getContextPath() %>/ntko/newWord.action" ;
 
 
 function close_doc()
{
   document.getElementById("TANGER_OCX").setAttribute("IsNoCopy",false);
   if(TANGER_OCX_bDocOpen)
   {
      if(op=="4" && !TANGER_OCX_OBJ.ActiveDocument.saved){
        msg='是否保存对[选择题.doc]的修改？';
        if(window.confirm(msg))
           TANGER_OCX_SaveDoc(0);
        else
           TANGER_OCX_bDocOpen = false;
      }else{
        TANGER_OCX_bDocOpen = false;
      }
   }
}
 
 
  function doInit(){
	  var clientHeight = $(document).height(); ;
	  $("#TANGER_OCX").height(clientHeight-5) ;
	  TANGER_OCX_SetInfo();
  //此函数在网页装载时被调用。用来获取控件对象并保存到TANGER_OCX_OBJ
  //同时，可以设置初始的菜单状况，打开初始文档等等。
   
   //var URL = "<%=request.getContextPath() %>/ntko/newWord.action";
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

function AddMobileSeal(){
	dialog(contextPath+"/system/core/ntko/mobileSeals.jsp",800,600);
}

function doAddMobileSeal(mobileSealId){
	TANGER_OCX_OBJ.AddPicFromURL("/mobileSeal/getBase64Image.action?uuid="+mobileSealId, //路径
	        true,//是否提示选择文件
	        0,//是否浮动图片
	        0,
	        1,
	        100,//如果是浮动图片，相对于左边的Left 单位磅
	        1); //如果是浮动图片，相对于当前段落Top
}

function OnComplete3(str,doc)
{
	 TANGER_OCX_bDocOpen = true;
	  TANGER_OCX_OnDocumentOpened(TANGER_OCX_str, TANGER_OCX_obj);
	  TANGER_OCX_SetReadOnly(false);
	  if(findStr(TANGER_OCX_filename,".doc")){
	    if(op != 4 && op != 7){
	      TANGER_OCX_SetReadOnly(true);
	      if (op == 5) {
	         TANGER_OCX_EnableFilePrintMenu(false);//禁止打印
	      }
	      //TANGER_OCX_EnableFilePrintMenu(false);
	      TANGER_OCX_EnableFilePrintPreviewMenu(false);
	    } 
	    if (op == 7) {
	      TANGER_OCX_EnableFilePrintMenu(true);//禁止打印
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
}
</script>
</head>
<body onload="doInit()" onbeforeunload="close_doc()">
<form name="form1" id="form1" method="post" action="<%=request.getContextPath() %>/ntko/updateFile.action" enctype="multipart/form-data">
<table width=100% height=100% class="small" cellspacing="1" cellpadding="3" align="center">
<tr width=100%>
<td valign=top width=100 style="width:100px">
	<div class="btn-group-vertical" style="width:100%">
		<button id="doc_fun_save" type="button" class="btn btn-info" onclick="TANGER_OCX_SaveDoc(0);">保存文件</button>
		<button id="doc_fun_pageSet" type="button" class="btn btn-default" onclick="TANGER_OCX_ChgLayout()">页面设置</button>
		<button id="doc_fun_print" type="button" class="btn btn-default" onclick="TANGER_OCX_PrintDoc()">打印</button>
	</div>
	<br/>
	<br/>
	<div class="btn-group-vertical" style="width:100%">
		<button id="doc_edit_saveMark" type="button" class="btn btn-default" onclick="TANGER_OCX_OBJ.ActiveDocument.TrackRevisions=true;">保留痕迹</button>
		<button id="doc_edit_noMark" type="button" class="btn btn-default"  onclick="TANGER_OCX_OBJ.ActiveDocument.TrackRevisions=false;">不留痕迹</button>
		<button id="doc_edit_showRev" type="button" class="btn btn-default" onclick="TANGER_OCX_OBJ.ActiveDocument.ShowRevisions=true;">显示痕迹</button>
		<button id="doc_edit_disRev" type="button" class="btn btn-default"  onclick="TANGER_OCX_OBJ.ActiveDocument.ShowRevisions=false;">隐藏痕迹</button>
		<button id="doc_edit_receipt" type="button" class="btn btn-default" onclick="TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();">接受修订</button>
		<button id="doc_edit_readonly" type="button" class="btn btn-default"  onclick="TANGER_OCX_OBJ.SetReadOnly(true,'');">设置只读</button>
		<button id="doc_edit_noreadonly" type="button" class="btn btn-default"  onclick="TANGER_OCX_OBJ.SetReadOnly(false,'');">取消只读</button>
<!-- 		<button id="doc_edit_redWord" type="button" class="btn btn-default"  onclick="openWordModel()">文件套红</button> -->
	</div>
	<br/>
	<br/>
	<div class="btn-group-vertical" style="width:100%">
<!-- 		<button id="e_id_check" type="button" class="btn btn-default">验证签名及印章</button> -->
		<button onclick="DoHandSign2(false)" type="button" class="btn btn-default">手写签名</button>
		<button onclick="AddMobileSeal()" type="button" class="btn btn-default">加盖签章</button>
<!-- 		<button onclick="DoHandDraw2()" type="button" class="btn btn-default">手工绘图</button> -->
<!-- 		<button onclick="AddSignFromLocal(false)" type="button" class="btn btn-default">插入签名</button> -->
		<button onclick="AddPictureFromLocal()" type="button" class="btn btn-default">插入绘图</button>
<!-- 		<button id="e_id_addSign1" type="button" class="btn btn-default">加盖电子印章</button> -->
	</div>
</td>
<td valign="top">
<div style="z-index:-1;" id="ntkoContainer"> 
<%=TeeSysProps.getString("NTKO_DOM") %>
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
<script type="text/javascript" language="javascript" for="TANGER_OCX" event="OnDocActivated(isActive)">
if(isActive)
{
	TANGER_OCX_OBJ.WebUserName = user;
	TANGER_OCX_OBJ.ActiveDocument.Application.UserName = user;
}
</script>
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
OnComplete3();
</script>

<span id="TANGER_OCX_op" style="display:none"><%=op %></span>
<span id="TANGER_OCX_filename" style="display:none"><%=attachmentName %></span>
<span id="TANGER_OCX_attachName" style="display:none"><%=attachmentName %></span>
<span id="TANGER_OCX_attachURL" style="display:none"><%=contextPath %>/attachmentController/downFile.action?id=<%=attachmentId %>&model=<%=moudle %></span>
<span id="TANGER_OCX_user" style="display:none"><%=loginUser.getUserName() %></span>
<INPUT type=hidden NAME="attachmentId" id="attachmentId" value="<%=attachmentId %>">
<INPUT TYPE=hidden NAME="attachmentName" id="attachmentName" value="<%=attachmentName %>">
<INPUT TYPE=hidden NAME="moudle"  id="moudle" value="<%=moudle %>">
<input type="hidden" id="docSize" name="docSize" value="100">
<input style="display:none" type="file" name="file" />
</FORM>
</body>
</html>

