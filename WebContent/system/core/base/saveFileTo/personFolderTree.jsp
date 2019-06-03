<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//文件id
	String attachId = request.getParameter("attachId");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人网盘</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/saveFileTo/js/saveFileTo.js"></script>
<script type="text/javascript">
function doInit(){
	getAttachmentInfo("<%=attachId%>","fileNameSpan");
	getFileTree();
}

function getFileTree(){
	var url =  "<%=contextPath %>/saveFileToPersonController/getPersonFolderTree.action";
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
 * 转存至个人网盘
 */
function doSubmit(){
	if(checkForm()){
		var url = "<%=contextPath %>/saveFileToPersonController/saveToPersonFolder.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var resultFlag = jsonRs.rtData.resultFlag;
			var url = contextPath + "/system/core/base/saveFileTo/saveFileResult.jsp?resultFlag=" + resultFlag;
			location.href = url;
		}else{
			alert(jsonRs.rtMsg);
		 }
	}
}



function checkForm(){
  if(!$("#folderSid").val()){
    $.jBox.tip("请选择目标路径！", "error", {timeout: 1500}); 
    return false;
  }
  return true;
}
function goBackFunc(){
	var url = contextPath + "/system/core/base/saveFileTo/index.jsp?attachId=<%=attachId%>";
	location.href = url;
}

</script>

</head>
<body onload="doInit();" style="margin: 10px;">
 <table border="0" width="90%" cellspacing="0" cellpadding="3" class="small">
	<tr>
		<td class="Big"><img src="./images/save_file.gif" align="middle"><span class="" ><font size="4"> 文件转存 —<span id="fileNameSpan" /> </font></span>
		</td>
	</tr>
</table>
<form id="form1" name="form1" action="">
<input type="hidden" id="attachId" name="attachId" value="<%=attachId %>" >
<input type="hidden" id="folderSid" name="folderSid" value="">
<table align="center" width="90%" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left;background:#E0EBF9; "><b >请选择转存位置： </b></td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >
			<ul id="selectFolderZtree" class="ztree" style="overflow-x:hidden;border:0px;width:98%;height:200px;"></ul>
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData" colspan=4>
			<input type="button"  value="上一步" class="btn btn-primary" onclick="goBackFunc();"/>&nbsp;&nbsp;
			<input type="button"  value="转存" class="btn btn-primary" onclick="doSubmit();"/>&nbsp;&nbsp;
			<input type="button"  value="关闭" class="btn btn-primary" onclick="CloseWindow();"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>