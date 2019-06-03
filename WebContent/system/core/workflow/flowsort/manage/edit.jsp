<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String flowSortId = request.getParameter("flowSortId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>编辑流程分类</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<script>
		
		function doInit(){
			var url = "<%=contextPath%>/flowSort/get.action";
			var json = tools.requestJsonRs(url,{flowSortId:<%=flowSortId%>});
			if(json.rtState){
				$("#sortName").attr("value",json.rtData.sortName);
			}
		}
		
		function commit(){
			if(!$("#form").form('validate')){
				return false;
			}
			
			var url = "<%=contextPath%>/flowSort/update.action";
			var para = tools.formToJson($("#form"));
			para["flowSortId"] = <%=flowSortId%>;
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				top.$.jBox.tip(json.rtMsg, 'success');
			}else{
				top.$.jBox.tip(json.rtMsg, 'error');
			}
			return true;
		}
	</script>
</head>
<body onload="doInit()">
<br/>
<form id="form">
<table class="TableBlock" width="95%" align="center">
 
   <tr>
    <td nowrap class="TableHeader" colspan="2" style='vertical-align: middle;'>
    <img src="<%=request.getContextPath() %>/common/styles/style1/img/green_arrow.gif" align="absMiddle" class="imgMiddle"> <span  class="imgMiddleSpan">编辑分类</span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120"><font color="red">*</font>分类名称：</td>
    <td nowrap class="TableData">
       <input type="text" name="sortName" id="sortName" class="BigInput easyui-validatebox" required/>
    </td>
   </tr>
    <tr>
    <td nowrap  class="TableControl" colspan="2" align="center" style="display:none">
       <input type="button" class="btn btn-default" value="确定" onclick="commit()"/>
    </td>
 	</tr>
</table>
</form>
</body>

</html>