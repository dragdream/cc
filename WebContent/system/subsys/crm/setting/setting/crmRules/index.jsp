<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String ORDER_MANAGER_IDS = "ORDER_MANAGER_IDS";
	String ORDER_MANAGER_NAMES = "ORDER_MANAGER_NAMES";
	String PAYMENT_PERSON_IDS = "PAYMENT_PERSON_IDS";
	String PAYMENT_PERSON_NAMES = "PAYMENT_PERSON_NAMES";
	String DRAWBACK_PERSON_IDS = "DRAWBACK_PERSON_IDS";
	String DRAWBACK_PERSON_NAMES = "DRAWBACK_PERSON_NAMES";
	String BILLING_PERSON_IDS = "BILLING_PERSON_IDS";
	String BILLING_PERSON_NAMES = "BILLING_PERSON_NAMES";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>CRM审批规则管理</title>
<script type="text/javascript">
var orderManagerIds = "<%=ORDER_MANAGER_IDS%>";
var orderManagerNames = "<%=ORDER_MANAGER_NAMES%>";
var paymentPersonIds = "<%=PAYMENT_PERSON_IDS%>";
var paymentPersonNames = "<%=PAYMENT_PERSON_NAMES%>";
var drawbackPersonIds = "<%=DRAWBACK_PERSON_IDS%>";
var drawbackPersonNames = "<%=DRAWBACK_PERSON_NAMES%>";
var billingPersonIds = "<%=BILLING_PERSON_IDS%>";
var billingPersonNames = "<%=BILLING_PERSON_NAMES%>";

//初始化审批规则
function doInit(){
	//获取参数
	var params = getSysParamByNames(orderManagerIds);
	if(params.length > 0){
		var param = params[0];
		if(param.paraValue != "" ){
			var personInfo = getPersonNameAndUuidByUuids(param.paraValue);
			$("#orderManagerIds").val(personInfo.sid);
			$("#orderManagerNames").val(personInfo.userName);
		}
		
	}
	var params1 = getSysParamByNames(paymentPersonIds);
	if(params1.length > 0){
		var param1 = params1[0];
		if(param1.paraValue != "" ){
			var personInfo = getPersonNameAndUuidByUuids(param1.paraValue);
			$("#paymentPersonIds").val(personInfo.sid);
			$("#paymentPersonNames").val(personInfo.userName);
		}
		
	}
	var params2 = getSysParamByNames(drawbackPersonIds);
	if(params2.length > 0){
		var param2 = params2[0];
		if(param2.paraValue != "" ){
			var personInfo = getPersonNameAndUuidByUuids(param2.paraValue);
			$("#drawbackPersonIds").val(personInfo.sid);
			$("#drawbackPersonNames").val(personInfo.userName);
		}
		
	}
	var params3 = getSysParamByNames(billingPersonIds);
	if(params3.length > 0){
		var param3 = params3[0];
		if(param3.paraValue != "" ){
			var personInfo = getPersonNameAndUuidByUuids(param3.paraValue);
			$("#billingPersonIds").val(personInfo.sid);
			$("#billingPersonNames").val(personInfo.userName);
		}
		
	}

}

function commit(){
	if($("#form1").valid()&&checkForm()){
		var url= contextPath+"/TeeCrmRulesController/saveCrmRulePerson.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}else{
			$.MsgBox.Alert_auto("设置失败！");
		}
	}
}


/**
 * 校验
 */
function checkForm(){
	 if($("#orderManagerIds").val() == "" || $("#orderManagerIds").val() == null){
		 $.MsgBox.Alert_auto("请选择订单管理员！");
		 return false;
	 }
	 if($("#paymentPersonIds").val() == "" || $("#paymentPersonIds").val() == null){
		 $.MsgBox.Alert_auto("请选择回款财务！");
		 return false;
	 }
	 if($("#orderManagerIds").val() == "" || $("#orderManagerIds").val() == null){
		 $.MsgBox.Alert_auto("请选择退款财务！");
		 return false;
	 }
	 if($("#billingPersonIds").val() == "" || $("#billingPersonIds").val() == null){
		 $.MsgBox.Alert_auto("请选择开票财务！");
		 return false;
	 }
	 return true;
}

</script>
<style>

table{
	border-collapse: collapse;
/*     border: 1px solid #f2f2f2; */
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	/*font-weight:bold;*/
}
table tr:first-child{
	/* background-color: #f8f8f8;  */
}
</style>

</head>
<body  onload="doInit();" style="overflow-y:auto;overflow-x:hidden;padding-left: 10px;padding-right: 10px;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_spgz.png">
		<span class="title">审批权限</span>
	</div>
</div>
	<form id="form1" name="form1" >
	<table class="none-table" width="60%" align="center" style="margin-top:10px;">
		
		<tr class="TableData" id="">
			<td nowrap>订单管理员：</td>
			<td nowrap align="left">
				<input id="orderManagerIds" name="orderManagerIds" type="hidden"> 
				<textarea required style="width:300px;height:100px;background:#f0f0f0;padding:5px" name="orderManagerNames" id="orderManagerNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
			     <span class='addSpan'>
			               <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectUser(['orderManagerIds','orderManagerNames'],'')" value="选择"/>
				           &nbsp;&nbsp;
				           <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('orderManagerIds','orderManagerNames')" value="清空"/>
			       </span>
			
			</td>
		</tr> 
		<tr class="TableData" id="">
			<td nowrap>回款财务：</td>
			<td nowrap align="left">
				<input id="paymentPersonIds" name="paymentPersonIds" type="hidden"> 
				<textarea required style="width:300px;height:100px;background:#f0f0f0;padding:5px" name="paymentPersonNames" id="paymentPersonNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
			     <span class='addSpan'>
			               <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectUser(['paymentPersonIds','paymentPersonNames'],'')" value="选择"/>
				           &nbsp;&nbsp;
				           <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('paymentPersonIds','paymentPersonNames')" value="清空"/>
			       </span>
			</td>
		</tr> 
	    <tr class="TableData" id="">
			<td nowrap>退款财务：</td>
			<td nowrap align="left">
				<input id="drawbackPersonIds" name="drawbackPersonIds" type="hidden"> 
				<textarea required style="width:300px;height:100px;background:#f0f0f0;padding:5px" name="drawbackPersonNames" id="drawbackPersonNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
			     <span class='addSpan'>
			               <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectUser(['drawbackPersonIds','drawbackPersonNames'],'')" value="选择"/>
				           &nbsp;&nbsp;
				           <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('drawbackPersonIds','drawbackPersonNames')" value="清空"/>
			       </span>
			</td>
		</tr> 
		<tr class="TableData" id="">
			<td nowrap>开票财务：</td>
			<td nowrap align="left">
				<input id="billingPersonIds" name="billingPersonIds" type="hidden" > 
				<textarea required style="width:300px;height:100px;background:#f0f0f0;padding:5px" name="billingPersonNames" id="billingPersonNames" class="SmallStatic BigTextarea" rows="2" cols="35"readonly="readonly"></textarea> &nbsp;&nbsp;
				  <span class='addSpan'>
			               <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectUser(['billingPersonIds','billingPersonNames'],'')" value="选择"/>
				           &nbsp;&nbsp;
				           <img style="cursor: pointer;" src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('billingPersonIds','billingPersonNames')" value="清空"/>
			       </span>
			</td>
		</tr> 
		</tr> 
		<tr style="border-bottom:none;">
			<td colspan="2" class="TableData" align="center">
					<input type="button" class="btn-win-white fr" onClick="commit()" value="保存设置"/>
			</td>
		</tr>

	</table>
	</form>
</body>

</html>