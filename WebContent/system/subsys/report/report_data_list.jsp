<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<%
		String dfid = request.getParameter("dfid");
		Map params = request.getParameterMap();
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>数据列表</title>
<script>

var dfid = "<%=dfid%>";
var para = {};

<%
	Set<String> keySet = params.keySet();
	for(String key:keySet){
		%>
		para["<%=key%>"] = "<%=((String[])params.get(key))[0]%>";
		<%
	}
%>

function doInit(){
	para["identity"] = dfid;
	
	var json = tools.requestJsonRs(contextPath+"/bisView/listBisViewListItem.action",para);
	var metadata = json.rows;
	
	var opts = [];
	var html = [];
	var k=1;
	for(var i=0;i<metadata.length;i++){
		var field = metadata[i];
		if(field.isSearch==1){//查询字段
			html.push(field.title+"：<input type='text' class='BigInput' name='"+field.field+"_"+field.fieldType+"_SEARCH' style='width:80px'/>&nbsp;");
			if((k++)%4==0){
				html.push("<br/>");
			}
		}
		opts.push({
			formatterScript:field.formatterScript,
			field:field.field,
			title:field.title,
			width:field.width,
			formatter:function(data,rowData,index){
				if(this.formatterScript=="" || this.formatterScript==null || this.formatterScript=="null" || this.formatterScript==undefined){
					return data;
				}else{
					eval(this.formatterScript);
					return data;
				}
			}
		});
	}
	
	if(html.length!=0){
		html.push("<button class='btn btn-default' type='button' onclick='search()'>查询</button>");
	}
	
	$("#form1").html(html.join(""));
	
	para["dfid"] = dfid;
	
	$('#datagrid').datagrid({
		url:contextPath+"/bisView/dflist.action",
		queryParams:para,
		pagination:true,
		pageSize:30,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		//idField:'formId',//主键列
		fitColumns:false,//列是否进行自动宽度适应
		columns:[opts]
	});
	
}

function search(){
	var tmp = tools.formToJson($("#form1"));
	for(var key in tmp){
		para[key] = tmp[key];
	}
	
	$('#datagrid').datagrid("reload",para);
}

</script>
</head>
<body onload="doInit()">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar">
		<div class="base_layout_top" style="position:static;">
			<table style="width:100%">
				<tr>
					<td>
						<span class="easyui_h1">数据列表</span>
					</td>
					<td style="text-align:right">
						<button type="button" class="btn btn-default"  onclick="CloseWindow()">关闭</button>
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<div style="padding:10px">
			<form id="form1">
			</form>
		</div>
	</div>
</body>

</html>