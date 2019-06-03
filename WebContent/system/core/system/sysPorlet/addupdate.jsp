<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header.jsp" %>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统参数</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/common/codemirror/codemirror.css">
<script src="<%=contextPath %>/common/codemirror/codemirror.js"></script>
<script src="<%=contextPath %>/common/codemirror/htmlembedded.js"></script>
<style>
/* BASICS */
 
.CodeMirror {
  /* Set height, width, borders, and global font properties here */
    font-family: monospace;
    height: 150px;
    width:640px;
    border: 0px solid #ccc; /*add by jackqqxu*/
    font-family: Monaco, Menlo, Consolas, 'COURIER NEW', monospace;/*add by jackqqxu*/
    font-size: 12px;
    border:1px solid #e2e2e2;
}
.CodeMirror-scroll {
  /* Set scrolling behaviour here */
  overflow: auto;
}
</style>

<script type="text/javascript">
var sid = <%=sid%>;
var editor;
function doInit(){
	if(sid!=0){
		var json = tools.requestJsonRs(contextPath+"/portlet/getPortlet.action",{sid:sid});
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			views($("#modelIcons")[0]);
		}
	}
	
	editor = CodeMirror.fromTextArea(document.getElementById("contentTpl"), { 
		lineNumbers: true, 
		mode: "application/x-jsp", 
		indentUnit: 4, 
		indentWithTabs: true 
	}); 
}

/**
 * 保存
 */
function doSave(){
	var url = contextPath+"/portlet/addOrUpdatePortlet.action";
	var para =  tools.formToJson($("#form1"));
	para["contentTpl"] = editor.getValue();
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		alert("保存成功");
	}else{
		alert(jsonRs.rtMsg);
	}
}

function views(obj){
	$("#explore").attr("src",obj.value);
}


</script>

</head>
<body onload="doInit()">
<div class="moduleHeader">
<b>桌面模块管理</b>
</div>
<form   method="post" name="form1" id="form1">
<table class="TableBlock" width="800px" align="center">
			<tr class="TableLine1">
				<td nowrap width="120px">模块标题：</td>
				<td nowrap><INPUT type=text class="BigInput" name="modelTitle" id="modelTitle" ></td>
		    </tr>
			<tr class="TableLine2">
				<td nowrap>排序号：</td>
				<td nowrap><input type="text" class="BigInput" name="sortNo" id="sortNo"></td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>分页大小：</td>
				<td nowrap><input type="text" class="BigInput" name="rows" id="rows"></td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>自动刷新时长（单位秒）：</td>
				<td nowrap><input type="text" class="BigInput" name="autoRefresh" id="autoRefresh">&nbsp;注：0为不刷新</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>更多链接：</td>
				<td nowrap><input type="text" class="BigInput" name="moreUrl" id="moreUrl"></td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>模块图标：</td>
				<td nowrap>
				<input type="text" class="BigInput" name="modelIcons" id="modelIcons" onchange="views(this)">
				<br/>
				预览效果：<img id="explore" />
				</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>模板类型：</td>
				<td nowrap>
					<select class="BigSelect" id="contentType" name="contentType">
						<option value="1">置标模板</option>
						<option value="2">Java模板</option>
					</select>
				</td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>模板内容：</td>
				<td nowrap><textarea class="BigTextarea" name="contentTpl" id="contentTpl"></textarea></td>
			</tr>
			<tr class="TableLine2" style="display:none">
				<td nowrap>无数据模板内容：</td>
				<td nowrap><textarea class="BigTextarea" name="noDataTpl" id="noDataTpl" style="width:645px;height:100px"></textarea></td>
			</tr>
			<tr class="TableLine2">
				<td nowrap>备注：</td>
				<td nowrap><textarea class="BigTextarea" name="remark" id="remark" style="width:645px;height:100px"></textarea></td>
			</tr>

			<tr class="TableControl">
				<td colspan="2" align="center">
				<input type="button" value="保存" class="btn btn-primary" onclick="doSave()">&nbsp;&nbsp;
				<input type="button" value="返回" class="btn btn-default" onclick="window.location = 'modules.jsp'">&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		<input type="hidden" name="sid" value="<%=sid %>" />
		<input type="hidden" name="viewType" id="viewType"/>
   </form>
</body>
</html>