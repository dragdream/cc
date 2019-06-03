<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<script>
var id = "<%=id%>";

function doInit(){
	//加载报表
	var json = tools.requestJsonRs(contextPath+"/seniorReport/datagrid.action",{rows:10000,page:1});
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		$("#ctReport").append("<option value='"+list[i].uuid+"'>"+list[i].tplName+"</option>");
	}
	
	//加载分类
	var json = tools.requestJsonRs(contextPath+"/seniorReportCat/datagrid.action",{rows:10000,page:1});
	var render = [];
	for(var i=0;i<json.rows.length;i++){
		render.push("<option value='"+json.rows[i].sid+"'>"+json.rows[i].name+"</option>");
	}
	$("#catId").append(render.join(""));
	
	var json;
	if(id!="null"){
		json = tools.requestJsonRs(contextPath+"/seniorReport/getReport.action",{uuid:id});
		bindJsonObj2Cntrl(json.rtData);
	}
	
}


function commit(){
	
	var para = {
			tplName:$("#tplName").val(),
			userPrivIds:$("#userPrivIds").val(),
			deptPrivIds:$("#deptPrivIds").val(),
			catId:$("#catId").val(),
			status:$("#status").val(),
			resId:$("#resId").val(),
			resName:$("#resName").val(),
			uuid:id=="null"?"":id};
	
	
	var url = "";
	if(id=="null"){
		url = contextPath+"/seniorReport/addReport.action";
	}else{
		url = contextPath+"/seniorReport/updateReport.action";
	}
	
	var json = tools.requestJsonRs(url,para);
	alert(json.rtMsg);
	if(json.rtState){
		if(id=="null"){
			window.location = "addOrUpdate_gez.jsp?id="+json.rtData;
		}
	}
}

function selectRes(){
	dialog(contextPath+"/system/subsys/report/senior_manage/gez_select.jsp",800,600);
}

</script>
</head>
<body onload="doInit()" style="margin:5px;">
	<div class="moduleHeader">
		<b>通用报表设置</b>
	</div>
	<button class="btn btn-primary" onclick="commit()">保存</button>
	&nbsp;&nbsp;
	<button class="btn btn-default" onclick="CloseWindow()">关闭</button>
	<table style="font-size:12px;margin:5px;width:800px" class="TableBlock">
		<tr>
			<td class="TableHeader">模版名称：</td>
			<td class="TableData"><input type="text" class="BigInput" id="tplName" name="tplName"/></td>
			<td class="TableHeader">报表分类：</td>
			<td class="TableData">
				<select id="catId" name="catId" class="BigSelect">
					<option value="0">默认分类</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableHeader">启用状态：</td>
			<td class="TableData">
				<select id="status" name="status" class="BigSelect">
					<option value="0">禁用</option>
					<option value="1" selected>启用</option>
				</select>
			</td>
			<td class="TableHeader">gez报表资源：</td>
			<td class="TableData">
				<input type="hidden" id="resId" name="resId"/>
				<input type="text" id="resName" name="resName" readonly class="BigInput readonly"/>
				<a href="#" onclick="selectRes()">选择</a>
			</td>
		</tr>
		<tr>
			<td class="TableHeader">人员权限：</td>
			<td class="TableData">
				<input type="hidden" name="userPrivIds" id="userPrivIds"/>
				<textarea id="userPrivNames" style="height:50px;width:150px;" readonly class="BigTextarea readonly"></textarea>
				<br/>
				<a href="javascript:void(0)" onclick="selectUser(['userPrivIds','userPrivNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('userPrivIds','userPrivNames')">清空</a>
			</td>
			<td class="TableHeader">部门权限：</td>
			<td class="TableData">
				<input type="hidden" name="deptPrivIds" id="deptPrivIds"/>
				<textarea id="deptPrivNames" style="height:50px;width:150px;" readonly class="BigTextarea readonly"></textarea>
				<br/>
				<a href="javascript:void(0)" onclick="selectDept(['deptPrivIds','deptPrivNames'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('deptPrivIds','deptPrivNames')">清空</a>
			</td>
		</tr>
	</table>
</body>
</html>