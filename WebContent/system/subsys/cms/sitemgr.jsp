<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String siteId = request.getParameter("siteId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script>
var siteId = <%=siteId%>;
function doInit(){
	//获取当前站点些模板列表
	var render = [];
	var json = tools.requestJsonRs(contextPath+"/cmsSiteTemplate/listTemplates.action",{siteId:siteId});
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		render.push("<option value=\""+list[i].sid+"\">"+list[i].tplName+"</option>");
	}
	
	$("#indexTpl").html(render.join(""));
	$("#detailTpl").html(render.join(""));
	
	if(siteId!=null){
		var json = tools.requestJsonRs(contextPath+"/cmsSite/getSiteInfo.action",{siteId:siteId});
		bindJsonObj2Cntrl(json.rtData);
	}
}

function commit(){
	var url;
	if(siteId!=null){//更新
		url = contextPath+"/cmsSite/updateSiteInfo.action";
	}else{//创建
		url = contextPath+"/cmsSite/addSiteInfo.action";
	}
	
	var param = tools.formToJson($("#form"));
	var json = tools.requestJsonRs(url,param);
	return json;
}

</script>
<style>
table td{
padding:5px;
}
</style>
</head>
<body onload="doInit();" style="background-color: #f2f2f2;">
<form id="form">
	<table style="width:100%;font-size:12px;" class="TableBlock">
		<tr>
			<td class="TableHeader" colspan="2" nowrap="">
		      <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">基础信息</b>
		    </td>
		</tr>
		<tr>
			<td style="text-indent:10px">站点标识：</td>
			<td>
				<input type="text" class="BigInput" name="siteIdentity" id="siteIdentity" style="width:250px;height:23px;"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">站点名称：</td>
			<td>
			<input type="text" class="BigInput" name="siteName" id="siteName" style="width:250px;height:23px;"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">排序号：</td>
			<td>
			<input type="text" class="BigInput" name="sortNo" id="sortNo" style="width:250px;height:23px;"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">存放位置：</td>
			<td>
			<input type="text" class="BigInput" name="folder" id="folder" style="width:250px;height:23px;"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">网站根目录：</td>
			<td>
			<input type="text" class="BigInput" name="contextPath" id="contextPath" value="/" style="width:250px;height:23px;"/>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">页面发布类型：</td>
			<td>
			<select class="BigSelect" id="pubFileExt" name="pubFileExt" style="height:23px;">
				<option value="html">html</option>
				<option value="jsp">jsp</option>
				<option value="php">php</option>
				<option value="aspx">aspx</option>
			</select>
			</td>
		</tr>
		<tr>
			<td class="TableHeader" colspan="2" nowrap="">
		      <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">发布相关</b>
		    </td>
		</tr>
		<tr>
			<td style="text-indent:10px">首页模板：</td>
			<td>
			<select class="BigSelect" id="indexTpl" name="indexTpl" style="height:23px;" ></select>
			</td>
		</tr>
		<tr>
			<td style="text-indent:10px">细览模板：</td>
			<td>
			<select class="BigSelect" id="detailTpl" name="detailTpl" style="height:23px;"></select>
			</td>
		</tr>
	</table>
	<input type="hidden" id="sid" name="sid" value="<%=siteId==null?0:siteId%>"/>
</form>
</body>
</html>