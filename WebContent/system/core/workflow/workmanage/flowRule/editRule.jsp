<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<title>工作委托</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>


<script type="text/javascript">
var sid = <%=request.getParameter("sid")%>;
function doInit(){
// 	var url = contextPath+"/workQuery/getHandableFlowType2SelectCtrl.action";
// 	ZTreeTool.comboCtrl($("#flowIdStr"),{url:url});

	var json = tools.requestJsonRs(contextPath+"/flowRule/getRule.action",{sid:sid});
	if(json.rtState){
		$("#userId").val(json.rtData.userId);
		$("#user").val(json.rtData.userName);
		$("#toUserId").val(json.rtData.toUser);
		$("#toUser").val(json.rtData.prcsName);
		$("#flowId").val(json.rtData.flowId);
		$("#flowName").val(json.rtData.flowName);
		if(json.rtData.status==0){
			$("#beginDate").val(getFormatDateStr(json.rtData.beginDate,"yyyy-MM-dd HH:mm"));
			$("#endDate").val(getFormatDateStr(json.rtData.endDate,"yyyy-MM-dd HH:mm"));
		}else{
			$("#status").attr("checked","");
		}
	}
}

//选择所属流程
function selectFlowType(){
	bsWindow(contextPath+"/system/core/workflow/workmanage/flowRule/flowTree.jsp","选择流程",{width:"400",height:"250",buttons:
		[{name:"选择",classStyle:"btn-alert-blue"} ,
	 	 {name:"关闭",classStyle:"btn-alert-gray"}],submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="选择"){
		    $("#flowId").val(h.contents().find("#flowId").val());
		    $("#flowName").val(h.contents().find("#flowName").val());
		    return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
	
	
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/flowRule/editRule.action";
		var para =  tools.formToJson($("#form1")) ;
		para["sid"] = sid;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			parent.$.MsgBox.Alert_auto(jsonRs.rtMsg);
			window.location = "manageRule.jsp";
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	var userListNames = document.getElementById("user");
	    if (!userListNames.value) {
	    	$.MsgBox.Alert_auto("委托人不能为空！");
	  	  userListNames.focus();
	  	  userListNames.select();
	  	  return false;
	    }
	    var toUserListNames = document.getElementById("toUser");
	    if (!toUserListNames.value) {
	    	$.MsgBox.Alert_auto("被委托人不能为空！");
	  	  toUserListNames.focus();
	  	  toUserListNames.select();
	  	  return false;
	    }
	    var  flowId = document.getElementById("flowId");
	   	if(flowId.value==""){
	   		$.MsgBox.Alert_auto("请选择流程定义！");
			return false;
		}
	   	
    return $("#form1").valid(); 
}

function backIndex(){

	window.location.href = "<%=contextPath %>/system/core/org/role/manageRole.jsp";
}





</script>

</head>
<body onload="doInit()" style="margin:0px">
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	boolean isAdmin = TeePersonService.checkIsAdminPriv(loginPerson);
	String uuid = loginPerson.getUuid()+"";
	String userName = loginPerson.getUserName();
	if(isAdmin){
		uuid = "";
		userName = "";
	}
%>

<form method="post" name="form1" id="form1">
<center>
<table class="TableBlock_page" width="90%">
<tr>
<td colspan="2" style="vertical-align: middle;padding-top: 10px;padding-bottom: 10px;text-indent: 10px;">
   <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/icon_mmxg.png" align="absMiddle">
   <span style="font-size: 14px;margin-left: 10px;"> 编辑委托规则 </span>
</td>
</tr>
	<tr>
		<td style="text-indent: 10px;" nowrap class="TableData" >委托人：</td>
		<td class="TableData" nowrap class="TableData" >
			<input type="hidden" name="userId" id="userId" required="true" > 
			<input name="user" id="user" rows="1" style="font-family: MicroSoft YaHei;overflow-y: auto;width: 200px;height:25px;border: 1px solid #dadada;" class="BigInput readonly" wrap="yes" readonly value="<%=userName%>"/> 
		</td>
	</tr>
	<tr>
		<td style="text-indent: 10px;" nowrap class="TableData">被委托人：</td>
		<td class="TableData" nowrap class="TableData" style="text-align:left;">
		<input type="hidden" name="toUserId" id="toUserId" required="true" value="" />
		<input name="toUser" id="toUser" rows="1" style="font-family: MicroSoft YaHei;overflow-y: auto;width: 200px;height:25px;border: 1px solid #dadada;" class="BigInput" wrap="yes" readonly />
		<span class='addSpan'>
			     <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/add.png" onClick="selectSingleUser(['toUserId', 'toUser'])" value="选择"/>
			     &nbsp;
			     <img src="<%=contextPath %>/common/zt_webframe/imgs/gzl/gzwt/clear.png" onClick="clearData('toUserId', 'toUser')" value="清空"/>
	        </span>	
		</td>	
	</tr>
	<tr>
		<td style="text-indent: 10px;" nowrap class="TableData" width="120">选择流程：<span style=""></span></td>
		<td class="TableData" nowrap class="TableData">
			<!-- <input type="hidden" readonly id="flowIdStr" name="flowIdStr" flowIdName/> -->
			<input style="width: 200px;height:25px;font-family: MicroSoft YaHei;" type="text" readonly id="flowName" name="flowName" onclick="selectFlowType();"/>
		    <input type="text" readonly id="flowId" name="flowId" style="display: none;" />
		</td>
	</tr>
	<tr>
		<td style="text-indent: 10px;" nowrap class="TableData">生效时间：</td>
		<td class="TableData" nowrap class="TableData">
		<input style="width: 200px;height:25px;" type="text" name="beginDate" id="beginDate" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDate\')}'})" class="Wdate BigInput">
		终止时间：
		<input style="width: 200px;height:25px;" type="text" name="endDate" id="endDate" size="20" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'beginDate\')}'})" class="Wdate BigInput">
		&nbsp;&nbsp;<input style="vertical-align:middle;" type="checkbox" name="status" id="status" value="1"/>&nbsp;<span> 一直生效</span></td>
	</tr>
	<tr>
	  <td style="text-align:center;" align="center" colspan='2'>
				<input style="width: 45px;height: 25px;margin-right:10px;" type="button" value="保存" class="btn-win-white" title="新建规则" onclick="doSave()"/>&nbsp;
				<input style="width: 45px;height: 25px;" type="button" value="返回" class="btn-win-white" title="返回" onclick="window.location = 'manageRule.jsp';"/>
				&nbsp;
	 </td>
	</tr>
</table>
</center>
</form>
</body>
</html>