<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@page import="com.tianee.oa.core.org.service.TeePersonService" %>
<%
	int sortId = TeeStringUtil.getInteger(request
			.getParameter("sortId"), 0);
	if(sortId==-1){
		sortId=0;
	}
	
	//获取当前登录的用户名
	TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	String userId=loginUser.getUserId();
	//判断当前的用户是不是系统管理员
	boolean isAdmin=TeePersonService.checkIsSuperAdmin(loginUser, userId);
	
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>Tenee办公自动化智能管理平台</title>
	<script>
	var sortId = <%=sortId%>;
	var contextPath = "<%=contextPath%>";
	var isAdmin=<%=isAdmin%>;
	function doInit(){
		var url = contextPath+"/formSort/getSortList.action";
		var json = tools.requestJsonRs(url,{});
		var html = "<option value='0'>默认分类</option>";
        //判断当前登录的用户是不是管理员
	    if(!isAdmin){
	    	$("#deptTr").hide();
	    }
		
		
		
		
		
		//获取流程所属分类
		if(json.rtState){
			var datas = json.rtData;
			for(var i=0;i<datas.length;i++){
				html+=("<option value='"+datas[i].sid+"'>"+datas[i].sortName+"</option>");
			}
			$("#sortId").html(html);
		}

		if(sortId!=0){
			$("#sortId").attr("value",sortId);
		}
	}

	function commit(){
		if(!$("#form").form("validate")){
			return;
		}
		
		var url = contextPath+"/flowForm/createForm.action";
		var para = tools.formToJson($("#form"));
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"success");
			var sortId = $("#sortId").val();
			var sortName = $("#sortId option[selected]:first").html();
			window.location.href="list.jsp?sortId="+$("#sortId").val()+"&sortName="+encodeURIComponent(sortName);
			parent.document.getElementById("left").contentWindow.location.reload();
		}else{
			top.$.jBox.tip(json.rtMsg,"error");
		}
	}
	</script>
</head>
<body onload="doInit()">
<div class="base_layout_top">
	 &nbsp;<input type="button" class="btn btn-success" value="保存表单" onclick="commit()" />
</div>
<div class="base_layout_center">
<br/>
<center>
<form id="form">
<table class="TableBlock" width="90%" align="center">
   <tr>
    <td nowrap class="TableData TableBG" width="120"><font color="red">*</font>表单名称：</td>
    <td nowrap class="TableData">
        <input type="text" style="width:300px" id="formName" name="formName" class="BigInput easyui-validatebox" required="true"/>
    </td>
   </tr>
   <tr id="deptTr">
    <td nowrap class="TableData TableBG">&nbsp;所属部门：</td>
    <td nowrap class="TableData" >
    	<input type="hidden" name="deptId" id="deptId"/>
		<input  name="deptName" id="deptName" class="readonly BigInput" readonly />
		<a href="javascript:void(0)" onclick="selectSingleDept(['deptId','deptName'])">选择</a>
		&nbsp;
		<a href="javascript:void(0)" onclick="clearData('deptId','deptName')">清除</a>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData TableBG"><font color="red">*</font>所属分类：</td>
    <td nowrap class="TableData" >
    	<select id="sortId" name="sortId" class="BigSelect"></select>
    </td>
   </tr>
</table>

</form>
</center>
</div>
</body>
</html>