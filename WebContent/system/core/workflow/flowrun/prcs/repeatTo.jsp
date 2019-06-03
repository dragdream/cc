<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String attachId = request.getParameter("attachId");
	if (attachId == null) {
		attachId = "0";
	}
	
	String runId=request.getParameter("runId");
	//String rId=request.getParameter("runId");
    String repeatForm=request.getParameter("repeatForm");
    String repeatText=request.getParameter("repeatText");
	String repeatFormatText=request.getParameter("repeatFormatText");
	String repeatAttach=request.getParameter("repeatAttach");
	int  module=TeeStringUtil.getInteger(request.getParameter("module"), 1);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件转存</title>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<script type="text/javascript"
	src="<%=contextPath%>/system/core/base/saveFileTo/js/saveFileTo.js"></script>
<script type="text/javascript">
var runId="<%=runId%>";
var repeatForm1="<%=repeatForm%>";
var repeatText1="<%=repeatText%>";
var repeatFormatText1="<%=repeatFormatText%>";
var repeatAttach1="<%=repeatAttach%>";
var module=<%=module%>;
function doInit(){
	<%-- getAttachmentInfo("<%=attachId%>", "fileNameSpan"); --%>
	//初始化数据
	if(repeatForm1=="1"){
		$("#repeatForm").attr("checked","checked");	
	}
	if(repeatText1=="1"){
		$("#repeatText").attr("checked","checked");	
	}
	if(repeatFormatText1=="1"){
		$("#repeatFormatText").attr("checked","checked");	
	}
	if(repeatAttach1=="1"){
		$("#repeatAttach").attr("checked","checked");	
	}
	
	if(module==1){
		$("#module1").attr("checked","checked");		
	}else{
		$("#module2").attr("checked","checked");			
	}
}

	function toNext() {
		 //获取选中的复选框  表单
		var repeatForm=$("#repeatForm").attr("checked");
		if(repeatForm=="checked"){
			repeatForm="1";
		}else{
			repeatForm="0";
		}
		//正文
		var repeatText=$("#repeatText").attr("checked");
		if(repeatText=="checked"){
			repeatText="1";
		}else{
			repeatText="0";
		}
		//版式正文
		var repeatFormatText=$("#repeatFormatText").attr("checked");
		if(repeatFormatText=="checked"){
			repeatFormatText="1";
		}else{
			repeatFormatText="0";
		}
		//附件
		var repeatAttach=$("#repeatAttach").attr("checked");
		if(repeatAttach=="checked"){
			repeatAttach="1";
		}else{
			repeatAttach="0";
		}
		
		if(repeatForm=="0"&&repeatText=="0"&&repeatFormatText=="0"&&repeatAttach=="0"){
			alert("请选择转存项目！");
			return;
		}
		
		var module1 = $("input[name='module']:checked").val();
		var url = contextPath
				+ "/system/core/workflow/flowrun/prcs/repeatToPerson.jsp?runId="+runId+"&repeatForm="+repeatForm+"&repeatText="+repeatText+"&repeatFormatText="+repeatFormatText+"&repeatAttach="+repeatAttach;
		if (module1 == 2) {
			url = contextPath
					+ "/system/core/workflow/flowrun/prcs/repeatToPub.jsp?runId="+runId+"&&repeatForm="+repeatForm+"&&repeatText="+repeatText+"&&repeatFormatText="+repeatFormatText+"&&repeatAttach="+repeatAttach;
		}
		$("#form1").attr('action', url);
		$("#form1").submit();
	}
</script>

</head>
<body onload="doInit();" style="margin: 10px;">
	<table border="0" width="90%" cellspacing="0" cellpadding="3"
		class="small">
		<tr>
			<td class="Big"><img src="./images/save_file.gif" align="middle"><span
				class=""><font size="4"> 文件转存 —<span
						id="fileNameSpan" />
				</font></span></td>
		</tr>
	</table>
	<form id="form1" name="form1" action="" method="post">
		<input type="hidden" id="attachId" name="attachId"
			value="<%=attachId%>">
		<table align="center" width="90%" class="TableBlock">
			<tr class="TableHeader">
				<td class="" style="text-align: left; background: #E0EBF9;"><b>请需要转存的文件：
				</b></td>
			</tr>
			<tr>
				<td nowrap class="TableData" width="15%;">
				   <input type="checkbox" name="repeatForm" id="repeatForm" value=""/>
				   <label for="repeatForm" title="转存表单">表单</label>&nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="checkbox" name="repeatText" id="repeatText" value=""/>
				   <label for="repeatText" title="转存正文">正文</label>&nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="checkbox" name="repeatFormatText" id="repeatFormatText" value=""/>
				   <label for="repeatFormatText" title="转存版式正文">版式正文</label>&nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="checkbox" name="repeatAttach" id="repeatAttach" value=""/>
				   <label for="repeatAttach" title="转存附件">附件</label>&nbsp;&nbsp;
				</td>
			</tr>


			<tr class="TableHeader">
				<td class="" style="text-align: left; background: #E0EBF9;"><b>请选择转存位置：
				</b></td>
			</tr>
			<tr>
				<td nowrap class="TableData" width="15%;"><input type="radio" class="module"
					name="module" id="module1" value="1" onclick=" " checked="checked">
					<label for="module1" title="将文件转存至个人网盘目录">个人网盘</label>&nbsp;&nbsp;
					<input type="radio" class="module" name="module" id="module2" value="2" onclick="">
					<label for="module2" title="将文件转存至公共网盘">公共网盘</label>&nbsp;&nbsp;</td>
			</tr>
			<tr align="center">
				<td nowrap class="TableData"><input type="button" value="下一步"
					class="btn btn-primary" onclick="toNext();" />&nbsp;&nbsp; <input
					type="button" value="关闭" class="btn btn-primary"
					onclick="CloseWindow();" />&nbsp;&nbsp;</td>
			</tr>
		</table>
	</form>
</body>
</html>