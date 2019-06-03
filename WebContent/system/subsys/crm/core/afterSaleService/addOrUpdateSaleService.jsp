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
<title>新建/编辑售后服务</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/afterSaleService/js/saleService.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>

<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	getCrmCodeByParentCodeNo("CUSTOMER_SERVICE_TYPE","serviceType");
	getCrmCodeByParentCodeNo("CUSTOMER_SERVICE_EMERGENCY","emergencyDegree");
	doInitUpload();
	if(sid>0){
		getInfoById(sid);
	}
	
}

function doInitUpload(){
	//初始化附件
	new TeeSWFUpload({
		fileContainer:"fileContainer",//文件列表容器
		uploadHolder:"uploadHolder",//上传按钮放置容器
		valuesHolder:"attacheIds",//附件主键返回值容器，是个input
		queueComplele:function(){//队列上传成功回调函数，可有可无
		},
		renderFiles:true,//渲染附件
		//file_types:"*.jpg;*.png;*.gif;*.jpeg",
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
	
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/crmAfterSaleServController/getInfoById.action";
	var para = {sid : id,isEdit:1};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			if(prc.customerInfoId){
				getContactUser(prc.customerInfoId);
			}
			bindJsonObj2Cntrl(prc);
			if($("#handleUserName").val()==""){
				$("#handleUserId").val("");
			}
			if($("#accteptUserName").val()==""){
				$("#accteptUserId").val("");
			}
			var attaches = prc.attacheModels;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#fileContainer").append(fileItem);
			}
		}
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
function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath %>/crmAfterSaleServController/addOrUpdate.action";
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
	window.location.href = contextPath + "/system/subsys/crm/core/afterSaleService/saleServiceManage.jsp";
}


</script>
</head>
<body onload="doInit();" style="margin-top: 10px;">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="90%" class="TableBlock" >
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left;background:#E0EBF9; "><b >售后服务</b></td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >服务编号：<font style='color:red'>*</font></td>
		<td class="TableData" width="70%;"  colspan="3">
			<input type="text" name="serviceCode" id="serviceCode" size="" class="BigInput  easyui-validatebox" required="true" size="15" maxlength="50" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="15%;" >客户名称：<font style='color:red'>*</font></td>
		<td class="TableData"  >
			<input class="BigInput" type="hidden" id = "customerInfoId" name='customerInfoId' />
			<input type="text" id ="customerInfoName" name='customerInfoName' readonly="readonly" class="BigInput  easyui-validatebox" required="true" />
			<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerInfoId','customerInfoName'],'getContactUser')">选择客户</a></a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerInfoId','customerInfoName')">清空</a>
			
		</td>
		<td nowrap class="TableData"   >客户联系人：</td>
		<td class="TableData"  >
			<input type="hidden" id="contactUserName" name="contactUserName">
			<select id="contactUserId" name="contactUserId" class="BigSelect easyui-validatebox" >
				<option value="">请选择</option>
			</select>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >售后服务类型 ：</td>
		<td class="TableData" >
			<select name="serviceType" id="serviceType"   class="BigSelect easyui-validatebox"  title="类型可在“客户关系管理”->“维护设置”->参数类型设置">
				<option value="">请选择</option>
			</select
		</td>
		<td nowrap class="TableData"  width="">紧急程度：</td>
		<td class="TableData" width="" >
			<select name="emergencyDegree" id="emergencyDegree"   class="BigSelect easyui-validatebox"  title="类型可在“客户关系管理”->“维护设置”->参数类型设置">
			</select
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" >受理人：</td>
		<td class="TableData" >
			<input type=hidden name="accteptUserId" id="accteptUserId" value="">
			<input  name="accteptUserName" id="accteptUserName" class="BigStatic BigInput" size="10"  readonly value=""></input>
			<span>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['accteptUserId', 'accteptUserName']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#accteptUserId').val('');$('#accteptUserName').val('');">清空</a>
			</span>
		</td>
		<td nowrap class="TableData"  width="">受理时间：</td>
		<td class="TableData" >
			<input type="text" name="acceptDatetimeStr" id="acceptDatetimeStr" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData"   >附件上传：</td>
		<td class="TableData"  colspan="3">
			<div id="fileContainer"></div> 
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" value=""/>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">问题描述：<font style='color:red'>*</font></td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="5" cols="90" id="questionDesc" name="questionDesc"  class="BigTextarea  easyui-validatebox" required="true"></textarea>
		</td>
	</tr>
	<tr class="TableHeader">
		<td class="" colspan="4" style="text-align: left;background:#E0EBF9; "><b>处理信息</b></td>
	</tr>
	<tr >
		<td nowrap class="TableData"   >处理人：</td>
		<td class="TableData"  >
			<input type=hidden name="handleUserId" id="handleUserId" value="">
			<input  name="handleUserName" id="handleUserName" class="BigStatic BigInput" size="10"  readonly value=""></input>
			<span>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['handleUserId', 'handleUserName']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#handleUserId').val('');$('#handleUserName').val('');">清空</a>
			</span>
		</td>
		<td nowrap class="TableData"   >处理时间：</td>
		<td class="TableData" >
			<input type="text" name="handleDatetimeStr" id="handleDatetimeStr" size="" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">是否完成：</td>
		<td class="TableData" width="" colspan="3">
			<select name="handleStatus" id="handleStatus"   class="BigSelect easyui-validatebox"  title="类型可在“CRM管理”->CRM编码设置。">
				<option value="0">未完成</option>
				<option value="1">已完成</option>
			</select
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">处理结果：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="5" cols="90" id="handleDesc" name="handleDesc"  class="BigTextarea"></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">反馈结果：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="5" cols="90" id="feedback" name="feedback"  class="BigTextarea" ></textarea>
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