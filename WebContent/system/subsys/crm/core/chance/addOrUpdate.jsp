<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String model = TeeAttachmentModelKeys.CRM_AFTER_SALE_SERV;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建/编辑销售机会</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/afterSaleService/js/saleService.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>

<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	
	if(sid>0){
		getInfoById(sid);
	}
	
}


 //查看详情
 function getInfoById(id){
		
		var url ="<%=contextPath%>/crmChanceController/getChanceById.action";
		var para = {sid :id};
		
		var jsonObj = tools.requestJsonRs(url, para);
		
		//alert(jsonObj);
		if (jsonObj.rtState) {	
			var chance = jsonObj.rtData;		
		    bindJsonObj2Cntrl(chance);		
		} else {
			
			alert(jsonObj.rtMsg);
		}
	}



function checkForm(){
	
	var contactUserName = $("#contactUserId option:selected").html();
	$("#contactUserName").val(contactUserName);
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}

	
	return true;
}
//增加或者修改
function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath %>/crmChanceController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			toReturn();
			top.$.jBox.tip("保存成功!",'success',{timeout:1500});
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}




/**
 * 联系人（选择客户名称后调用该回调函数）
 */
function getContactUser(customerId,customerName){
	var url = contextPath+"/TeeCrmContactUserController/getContactUserList.action";
	var json = tools.requestJsonRs(url,{customerId:customerId});
	if(json.rtState){
		var contactUserList = json.rtData;
		var html = "<option value=''>请选择</option>";
		for(var i=0;i<contactUserList.length;i++){
			html+="<option value=\""+contactUserList[i].sid+"\">"+contactUserList[i].name+"</option>";
		}
		$("#contactUserId").html(html);
	}
}


function toReturn(){
	window.location.href = contextPath + "/system/subsys/crm/core/chance/manage.jsp";
}


</script>
</head>
<body onload="doInit();" style="margin-top: 10px;">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="100%" class="TableBlock" >
	<tr>
		<td nowrap class="TableData" width="15%;" >客户名称：<font style='color:red'>*</font></td>
		<td class="TableData"  >
			<input class="BigInput" type="hidden" id = "customerId" name='customerId' />
			<input type="text" id ="customerName" name='customerName' readonly="readonly" class="BigInput  easyui-validatebox" required="true" />
			<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerId','customerName'],'getContactUser')">选择客户</a></a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerId','customerName')">清空</a>
			
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >机会名称：</td>
		<td class="TableData"  >
			<input type="text" id ="chanceName" name='chanceName' class="BigInput  easyui-validatebox" required="true" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >预计成交日期：</td>
		<td class="TableData"  >
			<input type="text" id ="forcastTimeStr" name='forcastTimeStr' class="Wdate BigInput  easyui-validatebox" required="true" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >预计成交金额：</td>
		<td class="TableData"  >
			<input type="text" id ="forcastCost" name='forcastCost' class="BigInput  easyui-validatebox" required="true" />
		</td>
	</tr>
	<tr align="center">
		<td nowrap class="TableData" colspan=4>
			<input type="button"  value="保存" class="btn btn-primary" onclick="doSaveOrUpdate();"/>&nbsp;&nbsp;
			<input type="button"  value="返回" class="btn btn-primary" onclick="toReturn();"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
<br>








</body>
</html>