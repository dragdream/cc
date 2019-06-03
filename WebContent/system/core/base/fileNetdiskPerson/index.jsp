<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	//int folderCapacity = TeeStringUtil.getInteger(person.getFolderCapacity(), 0);
	String fileNetDiskPerson = TeeUtility.null2Empty(TeeAttachmentModelKeys.FILE_NET_DISK_PERSON);
	String seqId = TeeUtility.null2Empty(request.getParameter("sortId"));
	

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人网盘</title>
<link rel="stylesheet" href="<%=contextPath%>/system/core/base/fileNetdiskPerson/css/filefolder.css">
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdiskPerson/js/fileNetdiskPerson.js"></script>

<script type="text/javascript">
window.onresize = initFrame;
var fileNetDiskPerson = "<%=fileNetDiskPerson%>";
function initFrame() {
	  //var h =  document.body.layout.clientHeight - 35;
	 var h  = $("#layout")[0].clientHeight-8;
	 if (h < 400 || isNaN(h)) {
	   h = 400;
	 }
	 $('#frameTable')[0].style.height = h + "px";
	 $('#file_tree')[0].style.height = h - 100 + "px"; 
}
function hide_tree(){
	var frame2 = parent.document.getElementById('frame2');
	if(frame2.cols=='0,*'){
	    frame2.cols = '200,*';
	    document.getElementById('btn').innerHTML='<<隐藏目录树';
	 }else{
	    frame2.cols = '0,*';
	    document.getElementById('btn').innerHTML='显示目录树>>';
	 }
}

function doInit(){
	getAvailableSize();
	treeSize();
	$("#layout").layout({auto:true});
  initFrame();
  var menu_id=0,menu=document.getElementById("navMenu");
  if(!menu) return;   
  for(var i=0; i<menu.childNodes.length;i++){
    if(menu.childNodes[i].tagName!="A"){
       continue;
    }
    if(menu_id==0){
       menu.childNodes[i].className="active";      
    }
    menu.childNodes[i].onclick=function(){
      var menu=document.getElementById("navMenu");
      for(var i=0; i<menu.childNodes.length;i++){
        if(menu.childNodes[i].tagName!="A"){
          continue;
        }
        menu.childNodes[i].className="";
      }
      this.className="active";
    }
   menu_id++;
  }
   
var navScroll = document.getElementById("navScroll");
  if(navScroll){
  navScroll.onclick = function(){
    if(menu.scrollTop + menu.clientHeight >= menu.scrollHeight || menu.scrollTop + menu.clientHeight*2 > menu.scrollHeight){
      menu.scrollTop = 0;
    }else{
      menu.scrollTop += menu.clientHeight;
    }
  }
      var panel = document.getElementById("navPanel");
      panel.onmouseover = function()
      {
         if(menu.scrollHeight >= menu.clientHeight*2)
            navScroll.style.display = '';
      }
      panel.onmouseout  = function()
      {
         navScroll.style.display = 'none';
      }
   }
   onresize();
   
};

window.onresize = function()
{
  var navScroll = document.getElementById("navScroll");
  if(navScroll)
  {
     var panel = document.getElementById("navPanel");
     var menu=document.getElementById("navMenu");
     panel.style.width = "100%";
     if(menu.clientWidth >= panel.clientWidth)
        menu.style.width = panel.clientWidth - navScroll.clientWidth - 70 + "px";
  }
}
function treeSize() {
  var spaceInfo = tools.spaceInfo(fileNetDiskPerson);
  $("#tree_size").text(spaceInfo.usedDesc);
  /* var fileSize = getModuleFileSize();
  $("#tree_size").text(fileSize); */
}




function doActive(index) {
  if (index == 0) {
    $('#top_menu .left a').addClass("active");
    $('#top_menu .right a').removeClass("active");
  }else if (index == 1) {
    $('#top_menu .left a').removeClass("active");
    $('#top_menu .right a').addClass("active");
  }
}

function collapse() {
  if (collapse.flag) {
	  $('#treeTd').show();
	  $('#colBtn').addClass("scroll-left");
  }
  else {
	  $('#treeTd').hide();
	  $('#colBtn').addClass("scroll-right");
  }
  collapse.flag = !collapse.flag;
}


/**
 * 获取文件大小
 */
function getAvailableSize(){
	var fileSize = 0;
	var url = contextPath + "/fileNetdiskPerson/getFolderCapacity.action";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var folderCapacity = json.rtData.folderCapacity;
		if(folderCapacity<=0){
			$("#folderCapacityDiv").hide();
		}else{
			$("#folderCapacityDiv").show();
			$("#folderCapacity").text(folderCapacity);
		}
	}
	return fileSize;
}




</script>



</head>
<body onload="doInit()">
<div id="layout">

<div layout="west" width="0">
</div>
<div layout="center" style="padding-left:10px;width:100%;height:100%;" >
  <table class="FrameTable" width="100%" id="frameTable">
    <tr>
      <td width="268px" id="treeTd">
        <div class="PageHeader" id="left_top"></div>
        <div id="top_menu">
          <div class="left"><a onclick="doActive(0)" class="active" index="1" href="personFolderTree.jsp" target="file_tree"><span>个人网盘</span></a></div>
          <div class="right"><a onclick="doActive(1)" index="2" href="shareFolderTree.jsp" target="file_tree" ><span>共享网盘 </span></a></div>
        </div>
        <div id="tree_container">
          <iframe id="file_tree" name="file_tree" src="personFolderTree.jsp"  allowTransparency="true" frameborder="0" style="border:none;width:100%;">
          </iframe>
        </div>
        <table class="BlockBottom2">
          <tr>
            <td class="left"></td>
            <td class="center"></td>
            <td class="right"></td>
          </tr>
        </table>
        <div id="folderCapacityDiv" class="capacity" style="display:none">最大<span id="folderCapacity"></span>MB，已用<span id="tree_size"> </span>&nbsp;<a href="javascript:treeSize();" class="A1">刷新</a></div>
      </td>
      <td id="colBtn" class="scroll-left" onclick="collapse()">
      </td>
      <td style="height:100%">
       <!--  <iframe src="fileNetdiskPersonList.jsp" id="file_main" name="file_main" allowTransparency="true" frameborder="0" style="border:none;width:100%;height:100%">
        </iframe> -->
        <iframe src="" id="file_main" name="file_main" allowTransparency="true" frameborder="0" style="border:none;width:100%;height:100%">
        </iframe>
      </td>
    </tr>
  </table>
  
  </div>
  </div>
</body>
</html>