<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sid= TeeStringUtil.getString(request.getParameter("sid"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script>
var sid = "<%=sid%>";
var contextPath = '<%=contextPath%>';
/* function doInit(){
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
 */
 function doInit(){
		var url = contextPath+"/CommonWord/getCommonWords.action";
		var json = tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}
 function commit(){
		if (checkForm()){
			var url = "";
			var para = tools.formToJson($("#form1"));
				//url = contextPath+"/pubTemplate/addTemplate.action";
				url = contextPath+"/CommonWord/updateCommonWord.action";
				                   
		
			var jsonRs = tools.requestJsonRs(url,para);
			             
			if(jsonRs.rtState){
				alert("修改成功！");
			    window.location=contextPath+"/system/core/person/commonword/NewFile.jsp";
			}else{
				top.$.jBox.tip(jsonRs.rtMsg,"error");
				return false;
			}
		}
	}

	function checkForm(){
	    return $("#form1").form('validate'); 
	}


</script>
<style>
table td{
padding:5px;
}
</style>
</head>
<body onload="doInit()"style="margin:0px;">
<div class="base_layout_top">
	<button class="btn btn-primary" onclick="commit()">保存</button>
	&nbsp;&nbsp;
	<button class="btn btn-default" onclick="CloseWindow()">关闭</button>
</div>
<form id="form1">
	<table style="width:100%;font-size:12px;">
		<tr>
			<td colspan="2" style="background:#f0f0f0" align="center"><b>基础信息</b></td>
		</tr>
		<tr>
			<td>常用语：</td>
			<td>
				<input type="text" class="BigInput" name="cyy" id="cyy"/>
			</td>
		</tr>
		<tr>
			<td>次数：</td>
			<td>
			<input type="text" class="BigInput" name="cis" id="cis"/>
			</td>
		</tr>
		
	</table>
	<input type="hidden" id="sid" name="sid" value="<%=sid%>"/>
</form>
</body>
</html>