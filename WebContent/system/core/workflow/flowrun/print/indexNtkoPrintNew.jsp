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
int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);

int attachId = TeeStringUtil.getInteger(request.getParameter("attachId"),0);

%>
<script type="text/javascript">
var attachId = <%=attachId%>;
var runId=<%=runId %>;
//全局对象
var DOC;

function doInit(){
	DOC = $("#TANGER_OCX")[0];
	var clientHeight = $(document).height(); ;
	$("#TANGER_OCX").height(clientHeight-5) ;
	
	//初始化文档信息
	DOC_INFO_INIT();
}

/**
 * 文档信息初始化
 */
function DOC_INFO_INIT(){
	//基础保护设置
	DOC.IsUseUTF8Data = true;
	DOC.FileNew = true;
	DOC.FileClose = false;
	DOC.TitleBars = true;
	DOC.IsShowToolMenu = true;
	DOC.Menubar = true;
	DOC.EnableFileCommand(0) = false;
	DOC.EnableFileCommand(1)=false;
	DOC.EnableFileCommand(2) = false;
	DOC.EnableFileCommand(3) = false;
	DOC.EnableFileCommand(4) = false;
	DOC.EnableFileCommand(5) = true;
	DOC.EnableFileCommand(6) = true;
	DOC.EnableFileCommand(7) = true;
	DOC.EnableFileCommand(8) = true;

	DOC.WebUserName = "<%=loginUser.getUserName()%>";
	
	$("#attachmentId").attr("value",attachId);

	var attachURL = contextPath+"/flowRun/freeMarker.action?attachId="+attachId+"&runId="+runId;
	
	DOC.OpenFromURL(attachURL, true);
}

/**
 * 保存文档
 */
function DOC_saveDoc(flag){
	var attachSaveURL = contextPath+"/ntko/updateFile.action";
	
	if((officePriv & 4)!=4){
		alert("您无权保存该文档！");
		return;
	}

	DOC.SaveToURL(attachSaveURL, "file", "","正文", "form1");
	
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
	    curSel.Cut();
//alert("dsd")
	  //  DOC.AddTemplateFromURL(URL);
		DOC.OpenFromURL(URL, true);
		IS_TAO_HONG = true;
	  } catch (err) {
	    alert("提示：套红文件可能不存在，或被转移，请联系管理员!");
	  }
}
function SET_ADD_TAO_HONG(){
	if(true){
		try {
			   var curSel = DOC.ActiveDocument.Application.Selection;
			    DOC.ActiveDocument.TrackRevisions = false;
		    	var BookMarkName = "sys_zhengwen";
// 		        if (!DOC.ActiveDocument.BookMarks.Exists(BookMarkName)) {
// 		          alert("Word 模板中不存在名称为：\"" + BookMarkName + "\"的书签！");
// 		          curSel.WholeStory();
// 		          curSel.Paste();
// 		          return;
// 		        }

// 		        var bkmkObj = DOC.ActiveDocument.BookMarks(BookMarkName);
// 		        var saverange = bkmkObj.Range;
// 		        var sizeTmp = saverange.Font.Size;
// 		        var fontTmp = saverange.Font.Name;
// 		        var color = saverange.Font.Color;
		        
// 		        saverange.Paste();
// 		        DOC.ActiveDocument.Bookmarks.Add(BookMarkName, saverange);
		        
// 		        bkmkObj = DOC.ActiveDocument.BookMarks(BookMarkName);
// 		        saverange = bkmkObj.Range
// 		        saverange.Font.Size = sizeTmp;
// 		        saverange.Font.Name= fontTmp;
// 		        saverange.Font.Color= color;
				
				
				//套入页面数据
				var bookmarks = DOC.ActiveDocument.Bookmarks;
				var bookmarksArray = [];
			    for(var i=1;i<=bookmarks.Count;i++)
			    {
			    	bookmarksArray.push(bookmarks(i));
			    }
			    for(var i in bookmarksArray){
					var bookmark_name = bookmarksArray[i].Name;
			        if(bookmark_name != "sys_zhengwen")
			        {   
			          var  val = parent.FORM_PRINT_DATA[bookmark_name];
			          if (val && val!=null) {
			        	  bookmarksArray[i].range.text = val;
			          }
			        }
			    }
			    
		        DOC.ActiveDocument.TrackRevisions = true;
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
/**
 * 本地打开文件
 */
function doc_open_local_file(){
	DOC.ShowDialog(1);
}
</script>
</head>
<body onload="doInit()" id="bodyContent">
<form name="form1" id="form1" method="post" action="<%=contextPath %>/ntko/updateFile.action" enctype="multipart/form-data">
<%=TeeSysProps.getString("NTKO_DOM") %>
<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
  //文档被关闭时执行的操作
  
</script>
<script language="JScript" for=TANGER_OCX event="OnWordWPSSelChange(obj)">
  //文档被关闭时执行的操作

  isMustSave = true;
 // TANGER_OCX_OnDocumentClosed();
</script>
<script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
DOC.ActiveDocument.CommandBars("Reviewing").Enabled = false;
DOC.ActiveDocument.CommandBars("Track Changes").Enabled = false;
DOC.ActiveDocument.AcceptAllRevisions();
SET_ADD_TAO_HONG();//套红
DOC.IsShowToolMenu = true; //关闭或打开工具菜单
DOC.SetReadOnly(true,"");
</script>
<input type="hidden" name="model" id="model"/>
<input type="hidden" name="attachmentId" id="attachmentId"/>
<input type="file" id="file" name="file" style="display:none"/>
</FORM>
</body>
</html>

