<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//选择的文件或目录
	String fileIds = request.getParameter("sid");
	//根目录
	String rootFolderSid = request.getParameter("rootFolderSid");
	//操作类型0-复制、1-剪贴
	String optionFlag = request.getParameter("optionFlag");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>复制、剪贴文件目录</title>
<script type="text/javascript">



function getFileTree(){
	var url =  "<%=contextPath %>/fileNetdisk/getAllFolderTree.action?seleteSid=<%=fileIds%>&optionFlag=<%=optionFlag%>";
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
  if(checkForm("你要复制的文件已经存在于目标路径！")){
    var url = "<%=contextPath %>/fileNetdisk/copyFileToFolder.action";
    var para =  tools.formToJson($("#form1"));
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
//     	var prcs = jsonRs.rtData;
//     	if(prcs.length>0){
//     		parent.parent.frames["file_tree"].createZTreeNode($("#folderSid").val(),prcs);
//     	}
  		//top.$.jBox.tip("文件复制成功！", "info"); 
  		$.MsgBox.Alert_auto("文件复制成功！");
  		parent.parent.$("#file_tree")[0].contentWindow.location.reload();
  		return true;
    }else{
    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
    	//top.$.jBox.tip(jsonRs.rtMsg, "error"); 
      return false;
    }
  }
}


/**
 * 剪贴
 */
function doCutSubmit(){
  if(checkForm("你要移动的文件已经存在于目标路径！")){
    var url = "<%=contextPath %>/fileNetdisk/cutFileToFolder.action";
    var para =  tools.formToJson($("#form1"));
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
//     	parent.parent.frames["file_tree"].refreshFileTree($("#folderSid").val());
  		//top.$.jBox.tip("文件剪贴成功！", "info"); 
  		$.MsgBox.Alert_auto("文件剪贴成功！");
  		//parent.parent.frames["file_tree"].location.reload();
  		parent.parent.$("#file_tree")[0].contentWindow.location.reload();
  		return true;
    }else{
    	return false;
    }
  }
}


function checkForm(infoStr){
  if(!$("#folderSid").val()){
	$.MsgBox.Alert_auto("请选择目标路径！");
    //top.$.jBox.tip("请选择目标路径！", "error"); 
    return false;
  }
  if($("#folderSid").val() == '<%=rootFolderSid%>'){
	$.MsgBox.Alert_auto(infoStr);
	//top.$.jBox.tip(infoStr, "error"); 
    return false;
  }
  return true;
}


function doInit(){
  getFileTree();
}

</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
 <ul id="selectFolderZtree" class="ztree" style="overflow-x:hidden;border:0px;width:98%;height:200px;"></ul>
 
 <form id="form1" name="form1" action="" method="post">
 	<input type="hidden" id="fileIds" name="fileIds" value="<%=fileIds%>">
 	<input type="hidden" id="folderSid" name="folderSid" value="">
 </form>
 
 
 
</body>
</html>