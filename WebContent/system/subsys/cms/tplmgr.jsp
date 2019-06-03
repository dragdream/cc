<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	String siteId = request.getParameter("siteId");
	String isNew = request.getParameter("isNew");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<link rel="stylesheet" type="text/css" href="/common/codemirror/codemirror.css">
<script src="/common/codemirror/codemirror.js"></script>
<script src="/common/codemirror/xml.js"></script>
<script src="/common/codemirror/javascript.js"></script>
<script src="/common/codemirror/css.js"></script>
<script src="/common/codemirror/vbscript.js"></script>
<script src="/common/codemirror/htmlmixed.js"></script>

<script>
var siteId = <%=siteId%>;
var sid = <%=sid%>;
var isNew = <%=isNew%>;
function doInit(){
	$("#layout").layout({auto:true});
	
	if(isNew!=true){
		var json = tools.requestJsonRs(contextPath+"/cmsSiteTemplate/getTemplate.action",{sid:sid});
		$("#tplFileName").attr("readonly","readonly").addClass("readonly");
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}
	initEditor();
}

function initEditor() {
	var mixedMode = {
      name: "htmlmixed",
      scriptTypes: [{matches: /\/x-handlebars-template|\/x-mustache/i,
                     mode: null},
                    {matches: /(text|application)\/(x-)?vb(a|script)/i,
                     mode: "vbscript"}]
    };
	
	window.editor = CodeMirror.fromTextArea(document.getElementById("content"), {
	  value: "",
	  mode: mixedMode,
	  styleActiveLine: true,
	  lineNumbers: true,
	  lineWrapping: true,
	  tabindex: 4,
	  indentUnit: 4
	});
}


function save(flag){
	var param = tools.formToJson($("#form"));
	param["content"] = editor.getValue();
	
	if(flag==1){//保存
		if(isNew==true){//新建
			var json = tools.requestJsonRs(contextPath+"/cmsSiteTemplate/createTemplate.action",param);
			try{
				opener.datagrid.datagrid("reload");
			}catch(e){}
			window.location = "tplmgr.jsp?siteId="+siteId+"&sid="+json.rtData;
		}else{//仅更新
			tools.requestJsonRs(contextPath+"/cmsSiteTemplate/updateTemplate.action",param);
			alert("保存成功");
			try{
				opener.datagrid.datagrid("reload");
			}catch(e){}
		}
	}else if(flag==2){//保存并新建
		if(isNew==true){//新建
			var json = tools.requestJsonRs(contextPath+"/cmsSiteTemplate/createTemplate.action",param);
			try{
				opener.datagrid.datagrid("reload");
			}catch(e){}
			window.location = "tplmgr.jsp?isNew=true&siteId="+siteId;
		}else{//仅更新
			tools.requestJsonRs(contextPath+"/cmsSiteTemplate/updateTemplate.action",param);
			try{
				opener.datagrid.datagrid("reload");
			}catch(e){}
			window.location = "tplmgr.jsp?isNew=true&siteId="+siteId;
		}
	}else if(flag==3){//保存并关闭
		if(isNew==true){//新建
			var json = tools.requestJsonRs(contextPath+"/cmsSiteTemplate/createTemplate.action",param);
			try{
				opener.datagrid.datagrid("reload");
			}catch(e){}
			CloseWindow();
		}else{//仅更新
			tools.requestJsonRs(contextPath+"/cmsSiteTemplate/updateTemplate.action",param);
			try{
				opener.datagrid.datagrid("reload");
			}catch(e){}
			CloseWindow();
		}
	}
}

</script>
</head>
<body onload="doInit();" style="margin:0px;overflow:hidden;background:#f0f0f0">
<form id="form">
<div id="layout">
	<div layout="north" height="40">
		
	</div>
	<div layout="east" width="230" style="padding:5px">
		<table>
			<tr>
				<td>模板名：</td>
				<td><input type="text" name="tplName" id="tplName"/></td>
			</tr>
			<tr>
				<td>文件名：</td>
				<td><input type="text" name="tplFileName" id="tplFileName"/></td>
			</tr>
			<tr>
				<td>描述：</td>
				<td>
					<textarea name="tplDesc" id="tplDesc" style="width:180px;height:100px"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div layout="south" height="40" style="padding:5px">
		<center>
			<button type="button" class="btn btn-success" onclick="save(1)">保存</button>
			<button type="button"  class="btn btn-one" onclick="save(2)">保存并新建</button>
			<button type="button"  class="btn btn-one" onclick="save(3)">保存并关闭</button>
			<button type="button"  class="btn btn-one" onclick="CloseWindow()">关闭</button>
		</center>
	</div>
	<div layout="center" style="overflow:auto;border:1px solid gray">
		<textarea style="width:100%;height:100%;border:0px" name="content" id="content"></textarea>
	</div>
</div>
<input type="hidden" value="<%=siteId%>" name="siteId" />
<input type="hidden" value="<%=sid%>" name="sid" />
</form>
</body>
</html>