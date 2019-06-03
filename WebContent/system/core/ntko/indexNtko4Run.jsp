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
<title>NTKO文档编辑器</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp"%>
<%

TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
 
int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
int officePriv = TeeStringUtil.getInteger(request.getParameter("officePriv"),0);
String docType = "doc";
String op = TeeStringUtil.getString(request.getParameter("op"));

int frpSid=TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
%>
<script type="text/javascript">
var runId = <%=runId%>;
var officePriv = <%=officePriv%>;
var docType = "<%=docType%>";
var model = "workFlow";
var contextPath = "<%=request.getContextPath()%>";
//全局对象
var DOC;
var user = "<%=loginUser.getUserName()%>";//当前登陆人姓名
var op = "<%=op%>";//操作码
var attachmentId = "";//附件id

var frpSid=<%=frpSid %>;
var prcsId=0;//流程序号(累加)

function doInit(){
	getPrcsId();
	
	
	DOC = $("#TANGER_OCX")[0];
	  var clientHeight = $(document).height(); ;
	  $("#TANGER_OCX").height(clientHeight-5) ;
	//获取正文对象flowRunDoc
	var url = contextPath +"/flowRunDocController/getFlowRunDocByRunId.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	var flowRunDoc = json.rtData;
	if(flowRunDoc==null){//如果没有正文，则判断当前权限是否有创建正文的权限
		if((officePriv & 2)==2){//如果有创建正文权限，则创建正文
			var url = contextPath + "/flowRunDocController/createNewOffice.action";
			var json = tools.requestJsonRs(url,{frpSid:frpSid,runId:runId,officePriv:officePriv,docType:docType});
			var flowRunDoc = json.rtData;
			op = 4;
			attachmentId = flowRunDoc.docAttach.sid;
		}else{
			messageMsg("您没有创建文档的权限！","bodyContent","error");
			return;
		}
	}else{//如果存在正文，则判断当前权限是否允许查看
		attachmentId = flowRunDoc.docAttach.sid;
	}

	//初始化文档信息
	DOC_INFO_INIT();
}

//获取当前步骤的流程序号
function getPrcsId(){
	var url=contextPath+"/flowRun/getFlowRunPrcsBySid.action";
	var json=tools.requestJsonRs(url,{frpSid:frpSid});
	if(json.rtState){
		var data=json.rtData;
		prcsId=data.prcsId;
	}
}
/**
 * 文档信息初始化
 */
function DOC_INFO_INIT(){
	//基础保护设置
	DOC.IsUseUTF8Data = true;
// 	DOC.FileNew = true;
// 	DOC.FileClose = false;
// 	DOC.TitleBars = false;
// 	DOC.IsShowToolMenu = false;
// 	DOC.Menubar = false;
// 	DOC.EnableFileCommand(0) = false;
// 	DOC.EnableFileCommand(1)=false;
// 	DOC.EnableFileCommand(2) = false;
// 	DOC.EnableFileCommand(3) = false;
// 	DOC.EnableFileCommand(4) = false;
// 	DOC.EnableFileCommand(5) = false;
// 	DOC.EnableFileCommand(6) = false;
// 	DOC.EnableFileCommand(7) = false;
// 	DOC.EnableFileCommand(8) = false;
    
	
	$("#model").attr("value",model);
	$("#attachmentId").attr("value",attachmentId);

	var attachURL = contextPath+"/ntko/readFile.action?id="+attachmentId+"&model="+model;

	if((officePriv & 1)!=1){//如果没有查看权限
		messageMsg("您没有查看文档的权限！","bodyContent","error");
		return ;
	}

	/* if((officePriv & 8)==8){//保留痕迹
		$("#doc_edit_saveMark").show();
	}

	if((officePriv & 16)==16){//不留痕迹
		$("#doc_edit_noMark").show();
	} */

	if((officePriv & 32)==32){//显示痕迹
		$("#doc_edit_showRev").show();
	}

	if((officePriv & 64)==64){//隐藏痕迹
		$("#doc_edit_disRev").show();
	}

	if((officePriv & 2048)==2048){//设置只读
		$("#doc_edit_readonly").show();
		$("#doc_edit_noreadonly").show();
	}
	if((officePriv & 16384)==16384){//套红设置
		$("#doc_edit_redWord").show();
	}
	if((officePriv & 262144)==262144){//创建版式正文
		$("#aip_btn").show();
	}

	if((officePriv & 32768)==32768){//严禁拷贝
		DOC.IsStrictNoCopy = true;
	}

	if((officePriv & 65536)==65536){//接受修订
		$("#doc_edit_receipt").show();
	}
	
	
	if((officePriv & 4)==4){//保存权限
		DOC.OpenFromURL(attachURL, false);
	}else{//无保存权限
		$("#doc_fun_save").hide();
		DOC.OpenFromURL(attachURL, true);
	}
	
}

/**
 * 保存文档
 */
function DOC_saveDoc(flag){
	var attachSaveURL = contextPath+"/ntko/updateFile.action";
	
	if((officePriv & 4)!=4){
		if(flag==0){
			alert("您无权保存该文档！");
		}
		return;
	}

	
	//判断是否需要生成版本
	var hasGeneVer=hasGenerateVersion();
	if(hasGeneVer==0){//未生成版本
		//alert(1);
		//请求后台接口，克隆一个附件   得到克隆后的附件id，接受修订，将word提交到后台  
		var u=contextPath+"/flowRunDocController/generateVersion.action";
	    var j=tools.requestJsonRs(u,{runId:runId,frpSid:frpSid});		
		if(j.rtState){
			var newAtt=j.rtData;
			if(newAtt){
				//重新赋值附件id
				$("#attachmentId").val(newAtt);
				//接受修订
				Accept();
				//提交后台
				DOC.SaveToURL(attachSaveURL, "file", "","正文", "form1");
			}
		}
	}else{//已经生成版本
		//alert(2);
		DOC.SaveToURL(attachSaveURL, "file", "","正文", "form1");
	}
}

//根据runId  prcsId  和    办理人  判断当前步骤是否已经生成版本
function  hasGenerateVersion(){
	var url=contextPath+"/flowRunDocController/hasGenerateVersion.action";
	var json=tools.requestJsonRs(url,{runId:runId,frpSid:frpSid});	
	if(json.rtState){
		var data=json.rtData;
		return data;
	}
}


/**
 * 页面设置
 */
function DOC_chgLayout(){
	DOC.ShowDialog(5);
}

/**
 * 打印设置
 */
function DOC_printDoc(){
	DOC.printout(true);
}

/**
 * 打开文件套红模板窗口
 */
function openWordModel(){
	dialog(contextPath+"/system/core/tpl/wordModel/list.jsp",250,380);
}

var IS_TAO_HONG = false;
function DOC_ADD_HEADER(attachId){
	try {
	    var URL = contextPath + "/attachmentController/downFile.action?id="+attachId;
	    //选择对象当前文档的所有内容
	    
	    var curSel = DOC.ActiveDocument.Application.Selection;
	    DOC.ActiveDocument.TrackRevisions = false;
	    curSel.WholeStory();
	    curSel.Copy();
	    window.IS_TAO_HONG = true;
		DOC.OpenFromURL(URL, false);
		
	  } catch (err) {
	    alert("错误代码："+err);
	  }
}
function SET_ADD_TAO_HONG(){
	if(IS_TAO_HONG){
		try {
			    DOC.ActiveDocument.TrackRevisions = false;
		    	var BookMarkName = "sys_zhengwen";
// 		        if (!DOC.ActiveDocument.BookMarks.Exists(BookMarkName)) {
// 		          alert("Word 模板中不存在名称为：\"" + BookMarkName + "\"的书签！");
// 		          curSel.WholeStory();
// 		          curSel.Paste();
// 		          return;
// 		        }
// 				if(!DOC.ActiveDocument.BookMarks.Exists(BookMarkName))
// 				{
// 					alert("书签不存在！");
// 					return;
// 				}
				if(DOC.ActiveDocument.ProtectionType != -1)
				{
// 					alert("文档已保护！");
					return;
				}
		        var bkmkObj = DOC.ActiveDocument.BookMarks.Item(BookMarkName);
		        bkmkObj.Select();
		        //alert("pt");
		        DOC.ActiveDocument.Application.Selection.Paste();

// 		        var saverange = bkmkObj.Range;
// 		        var sizeTmp = saverange.Font.Size;
// 		        var fontTmp = saverange.Font.Name;
// 		        var color = saverange.Font.Color;
		        
// 		        saverange.Paste();
// 		        DOC.ActiveDocument.Bookmarks.Add(BookMarkName, saverange);
		        
// 		        bkmkObj = DOC.ActiveDocument.BookMarks(BookMarkName);
// 		        saverange = bkmkObj.Range;
// 		        saverange.Font.Size = sizeTmp;
// 		        saverange.Font.Name= fontTmp;
// 		        saverange.Font.Color= color;
				
				
				//套入页面数据
				var bookmarks = DOC.ActiveDocument.Bookmarks;
				var bookmarksArray = [];
			    for(var i=1;i<=bookmarks.Count;i++)
			    {
			    	bookmarksArray.push(bookmarks.Item(i));
			    }
			    
			    for(var i in bookmarksArray){
			    	var bookmark_name = bookmarksArray[i].Name;
			        if(bookmark_name != "sys_zhengwen")
			        {   
			            var  ctrl = top.FORMDIV.find("[title='"+bookmark_name+"']:first");
			            if (ctrl.length!=0) {
			           	    var bookmark_value = ctrl.val();
			           	    if(bookmark_value ){//判断去null
			           	    	bookmarksArray[i].range.text = bookmark_value;
			                }
			            }
			        }
			    }
			    
				
		        DOC.ActiveDocument.TrackRevisions = false;
		    } catch (err) {
		      alert("错误：" + err.number + ":" + err.description);
		    }
		    IS_TAO_HONG = false;
	}
	   
}

//开始全屏手写签名

function DoHandSign2(key) {
	if (DOC.IsNTKOSecSignInstalled()){
      try{
    	  DOC.AddSecHandSign(user, 0, 0, 1);
      }catch (e){}
    }
    else {
      try{
    	  DOC.DoHandSign2(user, key, 0, 0, 0, 100);
      }catch (e){
        
      }
    }
}

function DoHandDraw2() {
	DOC.DoHandDraw2();
}

function AddSignFromLocal(key) {
	if (DOC.IsNTKOSecSignInstalled()){
      try{
    	  DOC.AddSecSignFromLocal(user, "", true, 0, 0, 1);
      }catch (e){}
    } else{
      try{
    	  DOC.AddSignFromLocal(user, "", true, 0, 0, key);
      }catch (e){}
    }
}

function AddPictureFromLocal() {
	DOC.AddPicFromLocal("", //路径
	        true,//是否提示选择文件
	        true,//是否浮动图片
	        100,//如果是浮动图片，相对于左边的Left 单位磅

	        100); //如果是浮动图片，相对于当前段落Top
}

function AddMobileSeal(){
	dialog(contextPath+"/system/core/ntko/mobileSeals.jsp",800,600);
}

function doAddMobileSeal(mobileSealId){
	DOC.AddPicFromURL("/mobileSeal/getBase64Image.action?uuid="+mobileSealId, //路径
	        true,//是否提示选择文件
	        0,//是否浮动图片
	        0,
	        1,
	        100,//如果是浮动图片，相对于左边的Left 单位磅
	        1); //如果是浮动图片，相对于当前段落Top
}

/**
 * 本地打开文件
 */
function doc_open_local_file(){
	DOC.ShowDialog(1);
}

function To_Aip(){
	if(window.confirm("是否要将Office正文转换为Aip版式正文？")){
		DOC_saveDoc(0);
		parent.docId = attachmentId;
		var url = contextPath+"/system/core/aip/aip4Run.jsp?runId="+runId+"&officePriv="+officePriv+"&docId="+attachmentId;
		parent.clearStyle();
		parent.$("#flowRunDocAipFrame").addClass("active").css("color","#fff");
		parent.$("#flowRunDocAipFrame").show();
		parent.$("#flowRunDocAipFrame").attr("src",url);
	}
}

/**
 * 套印
 */
function DOC_printDocTaoYin(){
	try {
		DOC.SetReadOnly(false,"");
	    DOC.ActiveDocument.TrackRevisions = false;
	    
		
	    ReplaceColor(255,16777215);
	  	
	  	
	  	if((officePriv & 8192)==8192){//自动取消只读
	  		DOC.SetReadOnly(false,"");
	  	}


	  	if((officePriv & 128)==128){//自动保留痕迹
	  		DOC.ActiveDocument.TrackRevisions=true;
	  	}

	  	if((officePriv & 4096)==4096){//自动只读
	  		DOC.SetReadOnly(true,"");
	  	}
	  	
	  	
	  	DOC.printout(true);
	  	
	  	DOC.SetReadOnly(false,"");
	    DOC.ActiveDocument.TrackRevisions = false;
	  	
	    ReplaceColor(16777215,255);
	  	
	  	if((officePriv & 8192)==8192){//自动取消只读
	  		DOC.SetReadOnly(false,"");
	  	}


	  	if((officePriv & 128)==128){//自动保留痕迹
	  		DOC.ActiveDocument.TrackRevisions=true;
	  	}

	  	if((officePriv & 4096)==4096){//自动只读
	  		DOC.SetReadOnly(true,"");
	  	}
	  	
    } catch (err) {
      	alert("错误：" + err.number + ":" + err.description);
    }
    
}


function ReplaceColor(a,b){
	//查找所有白色的字体，并设置为白色
	var find = DOC.ActiveDocument.Application.Selection.Find;
	find.Forward = true;
	find.ClearFormatting();
	find.Font.Color = a;
	find.MatchWholeWord = true;
	find.MatchCase = false;
	find.Wrap = 1;
	while(find.Execute()){
		var range = DOC.ActiveDocument.Application.Selection.Range;
		range.Font.Color = b;
	}

  	//遍历所有表格，将红色的表格边缘设置为白色
  	var tables = DOC.ActiveDocument.Tables;
  	for(var i=1;i<=tables.Count;i++){
  		var table = tables(i);
  		for(var j=-1;j>=-6;j--){
  			if(table.Borders(j).Color==a){
  				table.Borders(j).Color = b;
  			}
  		}
  	}
	
  	//遍历所有shape对象，将所有为红色的shape都设置为白色
  	var shapes = DOC.ActiveDocument.Shapes;
  	for(var i=1;i<=shapes.Count;i++){
  		var shape = shapes(i);
  		if(shape.Line.ForeColor==a){
  			shape.Line.ForeColor = b;
  		}
  	}	
}


/*
谷歌浏览器事件接管
*/
function OnComplete2(type,code,html)
{
	//alert(type);
	//alert(code);
	//alert(html);
// 	alert("SaveToURL成功回调");
}
function OnComplete(type,code,html)
{
	//alert(type);
	//alert(code);
	//alert(html);
// 	alert("OpenFromUrl成功回调");

}
function OnComplete3(str,doc)
{
// 	DOC.activeDocument.saved=true;//saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
	//	TANGER_OCX_OBJ.SetReadOnly(true,"");
		//TANGER_OCX_OBJ.ActiveDocument.Protect(1,true,"123");
	//获取文档控件中打开的文档的文档类型
// 	switch (DOC.doctype)
// 	{
// 		case 1:
// 			fileType = "Word.Document";
// 			fileTypeSimple = "wrod";
// 			break;
// 		case 2:
// 			fileType = "Excel.Sheet";
// 			fileTypeSimple="excel";
// 			break;
// 		case 3:
// 			fileType = "PowerPoint.Show";
// 			fileTypeSimple = "ppt";
// 			break;
// 		case 4:
// 			fileType = "Visio.Drawing";
// 			break;
// 		case 5:
// 			fileType = "MSProject.Project";
// 			break;
// 		case 6:
// 			fileType = "WPS Doc";
// 			fileTypeSimple="wps";
// 			break;
// 		case 7:
// 			fileType = "Kingsoft Sheet";
// 			fileTypeSimple="et";
// 			break;
// 		default :
// 			fileType = "unkownfiletype";
// 			fileTypeSimple="unkownfiletype";
// 	}
									
	//alert("ondocumentopened成功回调");
	
// 	DOC.IsShowToolMenu = false; //关闭或打开工具菜单
	DOC.ActiveDocument.TrackRevisions=true;//强制留痕
	DOC.ActiveDocument.ShowRevisions=false;//强制隐藏痕迹

	if((officePriv & 8192)==8192){//自动取消只读
		DOC.SetReadOnly(false,"");
	}

	if((officePriv & 131072)==131072){//自动接受修订
		DOC.ActiveDocument.AcceptAllRevisions();
	}

	
	/* if((officePriv & 128)==128){//自动保留痕迹
		DOC.ActiveDocument.TrackRevisions=true;
	}

	if((officePriv & 256)==256){//自动不留痕迹
		DOC.ActiveDocument.TrackRevisions=false;
	} */

	if((officePriv & 512)==512){//自动显示痕迹
		DOC.ActiveDocument.ShowRevisions=true;
	}

	if((officePriv & 1024)==1024){//自动隐藏痕迹
		DOC.ActiveDocument.ShowRevisions=false;
	}

	if((officePriv & 4096)==4096){//自动只读
		DOC.SetReadOnly(true,"");
	}
	SET_ADD_TAO_HONG();//套红
}


//抹去之前步骤的痕迹    生成版本
function Accept(){
	var rs=DOC.ActiveDocument.Revisions;
	var rcount=rs.Count;
	var rsinfor="";
	var arr = [];
	//alert(rcount);
	for(var i=1;i<=rcount;i++){
		//alert(rs(i).Author);
		if(rs(i).Author!=("["+prcsId+"]"+user)){
			arr.push(rs(i));
		}
	}
	for(var key in arr){
		arr[key].Accept();
	}
}



function historyVersion(){
	var url=contextPath+"/system/core/workflow/flowrun/prcs/docVersion.jsp?runId="+runId;
	dialog(url,800,600);
}
</script>
</head>
<body onload="doInit()" id="bodyContent">
<form name="form1" id="form1" method="post" action="<%=contextPath %>/ntko/updateFile.action" enctype="multipart/form-data">
<table width=100% height=100% class="small" cellspacing="1" cellpadding="3" align="center">
<tr width=100%>
<td valign=top width="100px" style="width:100px">
	<div class="btn-group-vertical" style="width:100%">
		<button id="doc_fun_open_local_file" type="button" class="btn btn-default" onclick="doc_open_local_file()">打开文件</button>
		<button id="doc_fun_save" type="button" class="btn btn-primary" onclick="DOC_saveDoc(0);">保存文件</button>
		<button style="display:none" id="aip_btn" type="button" class="btn btn-danger" onclick="To_Aip()">转版式文件</button>
		<button id="doc_fun_pageSet" type="button" class="btn btn-default" onclick="DOC_chgLayout()">页面设置</button>
		<button id="doc_fun_print" type="button" class="btn btn-default" onclick="DOC_printDoc()">打印</button>
		<button id="" type="button" class="btn btn-default" onclick="DOC_printDocTaoYin()">套印</button>
	</div>
	<br/>
	<br/>
	<div class="btn-group-vertical" style="width:100%;">
		<button id="doc_edit_saveMark" type="button" class="btn btn-default" style="display:none" onclick="DOC.ActiveDocument.TrackRevisions=true;">保留痕迹</button>
		<button id="doc_edit_noMark" type="button" class="btn btn-default" style="display:none" onclick="DOC.ActiveDocument.TrackRevisions=false;">不留痕迹</button>
		<button id="doc_edit_showRev" type="button" class="btn btn-default" style="display:none" onclick="DOC.ActiveDocument.ShowRevisions=true;">显示痕迹</button>
		<button id="doc_edit_disRev" type="button" class="btn btn-default" style="display:none" onclick="DOC.ActiveDocument.ShowRevisions=false;">隐藏痕迹</button>
		<button id="doc_edit_receipt" type="button" class="btn btn-default" style="display:none" onclick="DOC.ActiveDocument.AcceptAllRevisions();">接受修订</button>
		<button id="doc_edit_readonly" type="button" class="btn btn-default" style="display:none" onclick="DOC.SetReadOnly(true,'');">设置只读</button>
		<button id="doc_edit_noreadonly" type="button" class="btn btn-default" style="display:none" onclick="DOC.SetReadOnly(false,'');">取消只读</button>
		<button id="doc_edit_redWord" type="button" class="btn btn-default" style="display:none" onclick="openWordModel()">文件套红</button>
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
	<br/>
	<br/>
	<div class="btn-group-vertical" style="width:100%">
	     <button onclick="historyVersion();" type="button" class="btn btn-default">历史版本</button>
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
if(isActive){
	DOC.WebUserName = "["+prcsId+"]"+user;
	DOC.ActiveDocument.Application.UserName = "["+prcsId+"]"+user;
}
</script>
<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
  //文档被关闭时执行的操作
  
</script>
<script language="JScript" for=TANGER_OCX event="OnWordWPSSelChange(obj)">
  //文档被关闭时执行的操作

  isMustSave = true;
 // TANGER_OCX_OnDocumentClosed();
</script>
<script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
// DOC.ActiveDocument.CommandBars("Reviewing").Enabled = false;
// DOC.ActiveDocument.CommandBars("Track Changes").Enabled = false;
OnComplete3();
</script>
<input type="hidden" name="model" id="model"/>
<input type="hidden" name="attachmentId" id="attachmentId"/>
<input type="file" id="file" name="file" style="display:none"/>
</FORM>
</body>
</html>

