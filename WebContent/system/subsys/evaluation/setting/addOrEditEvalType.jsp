<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var sid = "<%=sid%>";
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeEvalTypeController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}
}

function commit(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeEvalTypeController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"success");
			return true;
		}
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
	}
}

</script>
</head>
<body onload="doInit();">
<form id="form1" name="form1">
	<table class='none-table'>
		<tr class='TableData' align='left'>
			<td width="80px">
				类型名称<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input type="text" id="name" name="name" required style="width:180px"class="BigInput easyui-validatebox" validType="maxLength[100]"/>
				<input type='hidden' id='sid' name='sid' value='<%= sid%>'/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>