<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var sid = '<%=sid%>';
function doInit(){
	var url = contextPath+"/officeDepositoryController/datagrid.action";
	var json = tools.requestJsonRs(url);
	var datas = json.rows;

	var html = "<option value=\"0\">=请选择=</option>";
	for(var i=0;i<datas.length;i++){
		html+="<option value=\""+datas[i].sid+"\">"+datas[i].deposName+"</option>";
	}
	$("#officeDepositoryId").html(html);

	var url = contextPath+"/officeCategoryController/getCategory.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		messageMsg(json.rtMsg,"form1","info");
	}
}

function commit(){
	if($("#officeDepositoryId")[0].value=="0"){
// 		top.$.jBox.tip("请选择一个用品库","info");
		$.MsgBox.Alert_auto("请选择一个用品库");
		$("#officeDepositoryId").focus();
		return;
	}
	
	if($("#form1").valid()){
		var url = contextPath+"/officeCategoryController/editCategory.action";
		var params = tools.formToJson($("#form1"));
		var json = tools.requestJsonRs(url,params);
		if(json.rtState){
// 			top.$.jBox.tip(json.rtMsg,"success");
			parent.$.MsgBox.Alert_auto(json.rtMsg);
			return true;
		}else{
// 			top.$.jBox.tip(json.rtMsg,"error");
			parent.$.MsgBox.Alert_auto(json.rtMsg);
			return false;
		}
	}
}

function backTo(){
	window.location = contextPath+"/system/core/base/officeProducts/manage/settings_category.jsp";
}

</script>
<style type="text/css">
.tableFormLine
{
    font-size:14px;
}

</style>
</head>
<body onload="doInit()" style="font-size:12px;background: #f4f4f4;">
<form id="form1" name="form1" style="padding:10px">
	<div class="tableFormLine">
		<b>用品库：</b>
		<br/>
		<select id="officeDepositoryId" name="officeDepositoryId" class="BigSelect">
		</select>
	</div>
	<div class="tableFormLine">
		<b>类别名称：</b>
		<br/>
		<input type="text" id="catName" name="catName" required="true" style="height:20px;" class="easyui-validatebox BigInput"/>
	</div>
	<input type="hidden" name="sid" value="<%=sid %>"/>
</form>
</body>
</html>