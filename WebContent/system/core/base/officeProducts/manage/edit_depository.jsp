<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<% 
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var sid = "<%=sid%>";
function doInit(){
	var url = contextPath+"/officeDepositoryController/getDepository.action";
	var json = tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		messageMsg(json.rtMsg,"form1","error");
	}
	
	$("[title]").tooltips();
}

function commit(){
	if($("#form1").valid()){
		var params = tools.formToJson($("#form1"));
		var url = contextPath+"/officeDepositoryController/updateDepository.action";
		params["sid"] = sid;
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

</script>
<style type="text/css">
.tableFormLine
{
    font-size:14px;
}

</style>
</head>
<body onload="doInit()" style="font-size:12px;background: #f4f4f4;">
<form id="form1" name="form1" style="padding:10px;">
	<div class="tableFormLine">
		<b>库名称：</b>
		<br/>
		<input type="text" class="easyui-validatebox BigInput" required="true"  style="width:300px;height:20px;" id="deposName" name="deposName"/>
	</div>
	<div class="tableFormLine">
		<b>所属部门：</b>
		<br/>
		<input type="hidden"  class="BigInput" style="width:100px" id="deptsIds" name="deptsIds"/>
		<textarea  readonly class="readonly BigTextarea  easyui-validatebox" required="true" style="width:400px;height:100px" id="deptsNames"></textarea>
		&nbsp;<a href="javascript:void(0)" onclick="selectDept(['deptsIds','deptsNames'])">选择</a>&nbsp;<a href="javascript:void(0)" onclick="clearData('deptsIds','deptsNames')">清空</a>
	</div>
	<div class="tableFormLine">
		<b>库管理员：<i class="glyphicon glyphicon-question-sign" style="color:#428bca" title="库管理员可以在该库中添加用品类和用品"></i></b>
		<br/>
		<input type="hidden"  class="BigInput" style="width:100px" id="adminsIds" name="adminsIds"/>
		<textarea  readonly class="readonly BigTextarea easyui-validatebox" required="true"  style="width:400px;height:100px" id="adminsNames"></textarea>
		&nbsp;<a href="javascript:void(0)" onclick="selectUser(['adminsIds','adminsNames'])">选择</a>&nbsp;<a href="javascript:void(0)" onclick="clearData('adminsIds','adminsNames')">清空</a>
	</div>
	<div class="tableFormLine">
		<b>物品调度员：<i class="glyphicon glyphicon-question-sign" style="color:#428bca"  title="物品调度员负责物品采购和发放"></i></b>
		<br/>
		<input type="hidden"  class="BigInput" style="width:100px" id="operatorsIds" name="operatorsIds"/>
		<textarea readonly class="BigTextarea readonly easyui-validatebox" required="true"  style="width:400px;height:100px" id="operatorsNames"></textarea>
		&nbsp;<a href="javascript:void(0)" onclick="selectUser(['operatorsIds','operatorsNames'])">选择</a>&nbsp;<a href="javascript:void(0)" onclick="clearData('operatorsIds','operatorsNames')">清空</a>
	</div>
</form>
</body>
</html>