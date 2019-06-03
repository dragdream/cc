<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/layout/layout.js"></script>
<script>
var sid = <%=sid%>;
function doInit(){
	//获取当前站点些模板列表
	if(sid>0){
		var url = "<%=contextPath%>/teePortalTemplateController/getPortalTemplate.action";
		var jsonRs = tools.requestJsonRs(url,{"sid":sid});
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			bindJsonObj2Cntrl(data);
			$("#sid").val(sid);
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

function commit(){
	var url;
	if(sid!=null){//更新
		url = contextPath+"/teePortalTemplateController/editPortalTemplate.action";
	}else{//创建
		url = contextPath+"/teePortalTemplateController/addPortalTemplate.action";
	}
	var param = tools.formToJson($("#form1"));
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
<body onload="doInit();" style="margin:0px;">
<form id="form1">
	<table style="width:100%;font-size:12px" class="TableBlock">
		<tr>
			<td colspan="4" style="background:#f0f0f0" align="center"><b>基础信息</b></td>
		</tr>
		<tr>
			<td nowrap class="TableData" >模板名称：</td>
			<td nowrap class="TableData" >
				<input type="text" class="easyui-validatebox BigInput" name="templateName" id="templateName" required="true"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" >排序号：</td>
			<td nowrap class="TableData" >
			<input type="text" class="BigInput" name="sortNo" id="sortNo"/>
			</td>
		</tr>
		<tr >
   			 <td nowrap class="TableData"  >使用权限（部门）：</td>
   			 <td nowrap class="TableData" align="left">
      		 <input type="hidden" name="deptPriv" id="deptPriv" value=""/>
       		 <textarea cols=60 name="deptPrivDesc" id="deptPrivDesc" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
       		 <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['deptPriv','deptPrivDesc'],'','1')">选择</a>
       		 <a href="javascript:void(0);" class="orgClear" onClick="clearData('deptPriv','deptPrivDesc')">清空</a>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" >使用权限（角色）：</td>
    <td nowrap class="TableData" align="left">
        <input type="hidden" name="rolePriv" id="rolePriv" value="">
        <textarea cols=60 name="rolePrivDesc" id="rolePrivDesc" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['rolePriv','rolePrivDesc'],'','1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('rolePriv','rolePrivDesc')">清空</a>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">使用权限（人员）：</td>
    <td nowrap class="TableData" align="left">
       <input type="hidden" name="userPriv" id="userPriv" value="">
        <textarea cols=60 name="userPrivDesc" id="userPrivDesc" rows=3 class="SmallStatic BigTextarea" readonly style="background-color:rgb(224, 224, 224)" ></textarea>
        <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['userPriv', 'userPrivDesc'],'' , '1')">选择</a>
        <a href="javascript:void(0);" class="orgClear" onClick="clearData('userPriv','userPrivDesc')">清空</a>
    </td>
   </tr>
	</table>
	<input type="hidden" id="sid" name="sid" value="<%=sid==null?0:sid%>"/>
	<input type="hidden" id="portalModel" name="portalModel" />
	<input type="hidden" id="cols" name="cols" />
</form>
</body>
</html>