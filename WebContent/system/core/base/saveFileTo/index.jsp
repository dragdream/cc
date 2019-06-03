<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String attachId=request.getParameter("attachId");
	if(attachId==null){
		attachId="0";
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件转存</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/saveFileTo/js/saveFileTo.js"></script>
<script type="text/javascript">

function doInit(){
	getAttachmentInfo("<%=attachId%>","fileNameSpan");
}

function toNext(){
	var module = $("input[name='module']:checked").val();
	var url = contextPath + "/system/core/base/saveFileTo/personFolderTree.jsp";
	if(module ==2){
		url = contextPath + "/system/core/base/saveFileTo/fileNetdiskFolderTree.jsp";
	}	
	$("#form1").attr('action',url);
	$("#form1").submit();
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
<form id="form1" name="form1" action="" method="get">
<input type="hidden" id="attachId" name="attachId" value="<%=attachId %>" >
<table align="center" width="90%" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" style="text-align: left;background:#E0EBF9; "><b >请选择转存位置： </b></td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >
			<input type="radio" name="module" id="module1" value="1" onclick=" " checked="checked">
			<label for="module1" title="将文件转存至个人网盘目录">个人网盘</label>&nbsp;&nbsp;
			<input type="radio" name="module" id="module2" value="2"  onclick="">
			<label for="module2" title="将文件转存至公共网盘">公共网盘</label>&nbsp;&nbsp;
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData" >
			<input type="button"  value="下一步" class="btn btn-primary" onclick="toNext();"/>&nbsp;&nbsp;
			<input type="button"  value="关闭" class="btn btn-primary" onclick="CloseWindow();"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>