<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//选择的文件/目录
	String fileIds = request.getParameter("sid");
	//根目录
	String rootFolderSid = request.getParameter("rootFolderSid");
	//操作类型0-复制、1-剪贴
	String optionFlag = request.getParameter("optionFlag");	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件目录</title>
<script type="text/javascript">

function getFileTree(){
	var url =  "<%=contextPath %>/fileNetdiskPerson/getAllFolderTree.action?seleteSid=<%=fileIds%>&optionFlag=<%=optionFlag%>";
	var config = {
			zTreeId:"selectFolderZtree",
			requestURL:url,
           	onClickFunc:onClickTree,
			async:false
			
		};
	zTreeObj = ZTreeTool.config(config); 	
}
/**
 *  点击
 */
function onClickTree(event, treeId, treeNode) {
	//alert(treeId + " treeNode>>" + treeNode);
	var sid = treeNode.id;
	var folderName = treeNode.name;
	//alert(sid + " folderName>>" + folderName);
	$("#folderSid").val(sid);
}

/**
 * 复制
 */
function doCopySubmit(){
  var folderSid = $("#folderSid").val();
  if(folderSid==""){
	  $.MsgBox.Alert_auto("请选择文件目录");
	  return;
  }
  var url = "<%=contextPath %>/fileNetdiskPerson/copyFileToFolder.action";
  var para =  tools.formToJson($("#form1"));
  var jsonRs = tools.requestJsonRs(url,para);
  if(jsonRs.rtState){
// 	var prcs = jsonRs.rtData;
// 	if(prcs.length>0){
		parent.parent.frames["file_tree"].refreshFileTree();
// 	}
	$.MsgBox.Alert_auto("复制文件成功！");
	return true;
  }else{
	$.MsgBox.Alert_auto(jsonRs.rtMsg);
    return false;
  }
}


/**
 * 剪贴/移动
 */
function doCutSubmit(){
  if(checkForm()){
	var folderSid = $("#folderSid").val();
	if(folderSid==""){
		$.MsgBox.Alert_auto("请选择文件目录");
		  return;
	  }
	var fileIds = "<%=fileIds%>";
    var url = "<%=contextPath %>/fileNetdiskPerson/cutFileToFolder.action";
    var para =  tools.formToJson($("#form1"));
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
  		var prcs = jsonRs.rtData;
  		parent.parent.frames["file_tree"].refreshFileTree(fileIds);
  		/**
  		parent.parent.frames["file_tree"].deledteZTreeNode(fileIds);
  		parent.parent.frames["file_tree"].createZTreeNode(folderSid,prcs);
  		**/
  		$.MsgBox.Alert_auto("移动文件成功！");
  		return true;
    }else{
      $.MsgBox.Alert_auto(jsonRs.rtMsg);
      return false;
    }
  }
}


function checkForm(){
  if(!$("#folderSid").val()){
	  $.MsgBox.Alert_auto("请选择目标路径！");
    return false;
  }
  if($("#folderSid").val() == '<%=rootFolderSid%>'){
	  $.MsgBox.Alert_auto("你要移动的文件已经存在于目标路径！");
    return false;
  }
  
  return true;
}



function doInit(){
  getFileTree();
}

</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2;padding-left: 20px">
 <ul id="selectFolderZtree" class="ztree" style="overflow-x:hidden;border:0px;width:98%;height:200px;"></ul>
 
 <form id="form1" name="form1" action="" method="post">
 	<input type="hidden" id="fileIds" name="fileIds" value="<%=fileIds%>">
 	<input type="hidden" id="folderSid" name="folderSid" value="">
 </form>
 
 
 
</body>
</html>