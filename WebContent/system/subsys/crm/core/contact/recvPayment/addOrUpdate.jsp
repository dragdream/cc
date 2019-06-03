<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String model = TeeAttachmentModelKeys.CRM;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script>
var sid = "<%=sid%>";
function doInit(){
	if( sid > 0){
		var url = "<%=contextPath%>/crmContractRecvPaymentsController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var prc = json.rtData;
			bindJsonObj2Cntrl(prc);
			$("#planRecvDate").val(getFormatDateTimeStr(prc.planRecvDate ,'yyyy-MM-dd'));
			$("#recvDate").val(getFormatDateTimeStr(prc.recvDate ,'yyyy-MM-dd'));
			$("#inviceDate").val(getFormatDateTimeStr(prc.inviceDate ,'yyyy-MM-dd'));
			$("#inviceSendDate").val(getFormatDateTimeStr(prc.inviceSendDate ,'yyyy-MM-dd'));
			if($("#managerUserName").val()==""){
				$("#managerUserId").val("");
			}
			/* //附件
			var  attachmodels = prc.attachmodels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			} */
		}else{
			alert(json.rtMsg);
		}
	}
	//doInitUpload();
}

/**
 * 初始化附件上传
 */
function doInitUpload(){
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"crm"}//后台传入值，model为模块标志
	});
}

/**
 * 保存
 */
function commit(){
	if($("#form1").form("validate")){
		if($("#recvPaymentFlag").val() == "1" && $("#managerUserName").val() == ""){
			alert("回款标记为已回款时，收款人为必填项！");
			$("#managerUserName")[0].select();
			return ;
		}
		var param = tools.formToJson($("#form1"));
		var url = "<%=contextPath%>/crmContractRecvPaymentsController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"success" ,{timeout:1500});
			returnBackFunc();
		}else{
			top.$.jBox.tip(json.rtMsg,"error");
		}
	}
}
function returnBackFunc(){
	location.href = contextPath + "/system/subsys/crm/core/contact/recvPayment/recvPaymentManage.jsp";
}

</script>
</head>
<body onload="doInit();" style="">
<div class="Big3" style="padding:3px 10px">
	<%if(sid > 0){ %>  
		编辑回款信息
	<%}else{ %>
		新建回款信息
	<%}%>
</div>
<form  method="post" name="form1" id="form1" >
	<table class="TableBlock" width="90%" align="center">
	    <tr>
	    	<td nowrap class="TableHeader" colspan="4"  style="text-align: left;"> <span  class="Big3">基本信息</span></td>
	    </tr>
	    <tr>
			<td nowrap class="TableData" width="120">合同：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		        <input type="hidden" name="contractId" id="contractId" class="easyui-validatebox BigInput"   />&nbsp;
		        <input type="text" name="contractName" id="contractName" class="easyui-validatebox BigInput SmallStatic" required="true"  readonly="readonly"/>&nbsp;
		    	<%
		    	if(sid <= 0){  
		    	%>
		    	<a href="javascript:void(0);" class="orgAdd" onClick="selectContractInfo(['contractId','contractNo','contractName'])">选择合同</a>
	        	<a href="javascript:void(0);" class="orgClear" onClick="$('#contractId').val('');$('#contractName').val('');$('#contractNo').val('');">清空</a>
				<%} %>
		    </td>
		    <td nowrap class="TableData" width="120">合同编号：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		        <input type="text" name="contractNo" id="contractNo" class="easyui-validatebox BigInput"     disabled="disabled"/>&nbsp;
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 预计回款日期：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		       	<input type="text" name="planRecvDate" id="planRecvDate"  maxlength="10" required="true" value="" class="BigInput easyui-validatebox" onClick="WdatePicker()"  />&nbsp;&nbsp;
		    </td>
		    <td nowrap class="TableData" >实际回款日期：</td>
		    <td nowrap class="TableData">
		        
		        <input type="text" name="recvDate" id="recvDate"   maxlength="10" value="" class="BigInput" onClick="WdatePicker()"  />&nbsp;&nbsp;
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 回款金额：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		       	<input type="text" name="recvPayAmount" id="recvPayAmount" required="true" value="0" class="BigInput easyui-validatebox" validType="pointTwoNumber[]"/>
		    </td>
		    <td nowrap class="TableData" >回款百分比：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData">
		    	<input type="text" name="recvPayParcent" id="" maxlength="10" required="true" value="0" class="BigInput easyui-validatebox" validType="numberBetweenLength[0,100]"/>&nbsp;&nbsp;
		    </td>
	   </tr> 
	   <tr>
		  
		   <td nowrap class="TableData" >收款人：</td>
		   <td nowrap class="TableData">
		      	<input id='managerUserName' name='managerUserName' class="BigInput SmallStatic" type='text' readonly="readonly"/>
				<input id='managerUserId' name='managerUserId' class="BigInput" type='hidden' value=""/>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['managerUserId','managerUserName'])">选择</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('managerUserId','managerUserName')">清空</a>
		   </td>
		   <td nowrap class="TableData" >回款标记：</td>
		   <td nowrap class="TableData">
		  		<select id="recvPaymentFlag" name="recvPaymentFlag" class="BigSelect">
		       		<option value='0'>否</option>
		       		<option value="1">是</option>
		       	</select>
		   </td>
	    </tr>
	    
	     <tr>
	    	<td nowrap class="TableHeader" colspan="4"  style="text-align: left;"> <span  class="Big3">发票信息</span></td>
	    </tr>
	     <tr>
		  
		   <td nowrap class="TableData" >是否开发票：</td>
		   <td nowrap class="TableData">
		      <select id="makeInvice" name="makeInvice" class="BigSelect">
		       		<option value='0'>否</option>
		       		<option value='1'>是</option>
		       	</select>
		   </td>
		     <td nowrap class="TableData" >发票号：</td>
		   	<td nowrap class="TableData">
		      <input type="text" name="inviceNumber" id="inviceNumber"  maxlength="40" value="" class="BigInput"   />&nbsp;&nbsp;
		       
		    </td>
	    </tr>
	    <tr>
		 
		    <td nowrap class="TableData" >发票日期：</td>
		    <td nowrap class="TableData">
		  		<input type="text" name="inviceDate" id="inviceDate" maxlength="10" value="" class="BigInput" onClick="WdatePicker()"  />&nbsp;&nbsp;
		    </td>
		    <td nowrap class="TableData" >寄出日期：</td>
		    <td nowrap class="TableData">
		  		<input type="text" name="inviceSendDate" id="inviceSendDate" smaxlength="10" value="" class="BigInput" onClick="WdatePicker()"  />&nbsp;&nbsp;
		    </td>
	    </tr>
	    <tr>
			
		    <td nowrap class="TableData" >备注</td>
		    <td nowrap class="TableData" colspan="3">  
		    	<textarea rows="5" cols="80" name="remark" class="BigTextarea"></textarea>
		    </td>
	    </tr>
	     <tr>
		    <td nowrap  class="TableControl" colspan="4" align="center">
		        <input type="hidden" id="sid" name="sid"  value="0">
		        <input type="button" value="保存" class="btn btn-primary" title="保存" onclick="commit()" >&nbsp;&nbsp;
		        <input type="button" value="返回" class="btn btn-primary" title="返回" onClick="returnBackFunc()">
		    </td>
		</tr>
	</table>
</form>
<br>
</body>
</html>