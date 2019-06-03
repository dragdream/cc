<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int formId = TeeStringUtil.getInteger(request.getParameter("formId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>版本列表</title>
	<script>
	var contextPath = "<%=contextPath%>";
	var formId = "<%=formId%>";

	function doInit(){
		var url = contextPath+"/flowForm/listVersions.action";
		var json = tools.requestJsonRs(url,{formId:formId});
		if(json.rtState){
			var html = "";
			for(var i=0;i<json.rtData.length;i++){
				var data = json.rtData[i];
				html+="<tr>";
				html+="<td>"+data.versionNo+"</td>";
				html+="<td>"+data.versionTime+"</td>";
				html+="<td>"+data.itemCount+"</td>";
				if(i!=json.rtData.length-1){
					html+="<td><a href='javascript:void(0)' onclick='formPrintExplore("+data.formId+")'>预览</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='exportForm("+data.formId+")'>导出</a>&nbsp;&nbsp;<a href='javascript:void()' onclick='createVersionForm("+data.formId+")'>恢复</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='design("+data.formId+")'>设计</a></td>";
				}else{
					html+="<td style='color:red;font-weight:bold'>当前版本</td>";
				}
				html+="</tr>";
			}
			$("#content").append(html);
		}
	}

	function formPrintExplore(formId){
		window.openWindow("../../formdesign/printHistoryExplore.jsp?formId="+formId,"",800,600);
	}

	
	//设计
	function design(formId){
		openFullWindow(contextPath+"/system/core/workflow/formdesign/index.jsp?formId="+formId);
	}
	
	function exportForm(formId){
		window.open(contextPath+"/flowForm/export.action?formId="+formId);
	}
	
	function goback(){
		window.location = "edit.jsp?formId="+formId;
	}

	function createVersionForm(formId){
		var url = contextPath+"/flowForm/createVersionForm.action";
		var json = tools.requestJsonRs(url,{formId:formId});
		if(json.rtState){
			alert("操作成功");
			window.location.reload();
		}else{
			alert(json.rtMsg);
		}
	}
	
	</script>
</head>
<body onload="doInit()">
<div class="base_layout_top">
	<span class="easyui_h1">表单历史版本</span>
	&nbsp;&nbsp;
	<input type="button" class="btn btn-default" value="返回" onclick="goback()" />
</div>
<div class="base_layout_center">
<br/>
<table class="TableBlock" width="90%" align="center">
   <thead id="content">
		<tr style="font-weight:bold">
			<td class="TableData TableBG">版本号</td>
			<td class="TableData TableBG">生成时间</td>
			<td class="TableData TableBG">字段数量</td>
			<td class="TableData TableBG">操作</td>
		</tr>
	</thead>
</table>
</div>
</body>
</html>