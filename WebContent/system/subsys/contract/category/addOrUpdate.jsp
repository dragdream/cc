<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%
	String sid = request.getParameter("sid");
%>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>分类设置</title>
	<script type="text/javascript" charset="UTF-8">
	var sid = "<%=sid%>";
	
	$(function() {
		if(sid!="null"){
			var json = tools.requestJsonRs(contextPath+"/contractCategory/get.action",{sid:sid});
			bindJsonObj2Cntrl(json.rtData);
		}
		
	});
	
	function check(){
		if($("#form1").form("validate")){
			return true;
		}
		return false;
	}
	
	function commit(){
		if(!check()){
			return;
		}
		
		var para = tools.formToJson($("#form1"));
		
		var url = "";
		if(sid!="null"){
			url = contextPath+"/contractCategory/update.action";
		}else{
			url = contextPath+"/contractCategory/add.action";
		}
		
		var json = tools.requestJsonRs(url,para);
		alert(json.rtMsg);
		if(json.rtState){
			window.location = "list.jsp";
		}
	}
	</script>
</head>
<body >
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1">分类设置</span>
</div>
	
	<table id="form1" class="TableBlock" style="margin:0 auto;margin-top:10px;width:60%;" >
		<input type="hidden" name="sid" id="sid" value="<%=sid==null?"0":sid %>" />
		<tr>
			<td class="TableData">分类名称：</td>
			<td class="TableData">
				<input type="text" name="name" id="name" class="BigInput easyui-validatebox" required />
			</td>
		</tr>
		<tr>
			<td class="TableData">查看权限：</td>
			<td class="TableData">
				<input type="hidden" name="viewPrivIds" id="viewPrivIds"/>
				<textarea class="BigTextarea readonly"  name="viewPrivNames" readonly id="viewPrivNames" style="width:250px;height:50px"></textarea>
				<a href="javascript:void(0)" onclick="selectUser(['viewPrivIds','viewPrivNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('viewPrivIds','viewPrivNames')">清空</a>
			</td>
		</tr>
		<tr>
			<td class="TableData">管理权限：</td>
			<td class="TableData">
				<input type="hidden" name="managePrivIds" id="managePrivIds"/>
				<textarea class="BigTextarea readonly"  name="managePrivNames" readonly id="managePrivNames" style="width:250px;height:50px"></textarea>
				<a href="javascript:void(0)" onclick="selectUser(['managePrivIds','managePrivNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('managePrivIds','managePrivNames')">清空</a>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button class="btn btn-primary" onclick="commit()">保存</button>
				&nbsp;
				<button class="btn btn-default" onclick="window.location = 'list.jsp'">返回</button>
			</td>
		</tr>
	</table>
</body>
</html>