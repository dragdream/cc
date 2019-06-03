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
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
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
			$("#planRecvDate").html(getFormatDateTimeStr(prc.planRecvDate ,'yyyy-MM-dd'));
			$("#recvDate").html(getFormatDateTimeStr(prc.recvDate ,'yyyy-MM-dd'));
			$("#inviceDate").html(getFormatDateTimeStr(prc.inviceDate ,'yyyy-MM-dd'));
			$("#inviceSendDate").html(getFormatDateTimeStr(prc.inviceSendDate ,'yyyy-MM-dd'));
			var makeInviceDesc = "否";
			if(prc.makeInvice == '1'){
				makeInviceDesc = "是";
			}
			$("#makeInviceDesc").html(makeInviceDesc);
			var recvPaymentFlagDesc = "否";
			if(prc.recvPaymentFlag == '1'){
				recvPaymentFlagDesc = "是";
			}
			$("#recvPaymentFlagDesc").html(recvPaymentFlagDesc);
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
}



</script>
</head>
<body onload="doInit();" style="">
<form  method="post" name="form1" id="form1" >
	<table class="TableBlock" width="90%" align="center">
	    <tr>
	    	<td nowrap class="TableHeader" colspan="4"  style="text-align: left;"> <span  class="Big3">基本信息</span></td>
	    </tr>
	    <tr>
			<td nowrap class="TableData" width="120">合同：<span style="color:red;"></span></td>
		    <td nowrap class="TableData" name="contractName">
		     
		    </td>
		    <td nowrap class="TableData" width="120">合同编号：<span style="color:red;"></span></td>
		    <td nowrap class="TableData" name="contractNo">
		     </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 预计回款日期：</td>
		    <td nowrap class="TableData"  name="planRecvDate" id="planRecvDate">
		      </td>
		    <td nowrap class="TableData" >实际回款日期：</td>
		    <td nowrap class="TableData" name="recvDate" id="recvDate">
		 	    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 回款金额：</td>
		    <td nowrap class="TableData" name="recvPayAmount">
		    </td>
		    <td nowrap class="TableData" >回款百分比：</td>
		    <td nowrap class="TableData" name="recvPayParcent" >
		 	    </td>
	   </tr> 
	   <tr>
		  
		   <td nowrap class="TableData" >收款人：</td>
		   <td nowrap class="TableData" name="managerUserName" >
		   </td>
		   <td nowrap class="TableData" >回款标记：</td>
		   <td nowrap class="TableData" id="recvPaymentFlagDesc">
		   </td>
	    </tr>
	    
	     <tr>
	    	<td nowrap class="TableHeader" colspan="4"  style="text-align: left;"> <span  class="Big3">发票信息</span></td>
	    </tr>
	     <tr>
		  
		   <td nowrap class="TableData" >是否开发票：</td>
		   <td nowrap class="TableData" id="makeInviceDesc" name="makeInviceDesc">
		    		   </td>
		     <td nowrap class="TableData" >发票号：</td>
		   	<td nowrap class="TableData" name="inviceNumber">
		  	    </td>
	    </tr>
	    <tr>
		 
		    <td nowrap class="TableData" >发票日期：</td>
		    <td nowrap class="TableData" name="inviceDate" id="inviceDate">
		  	   </td>
		    <td nowrap class="TableData" >寄出日期：</td>
		    <td nowrap class="TableData"  name="inviceSendDate" id="inviceSendDate">
		     </td>
	    </tr>
	    <tr>
			
		    <td nowrap class="TableData" >备注</td>
		    <td nowrap class="TableData" colspan="3" name="remark">  
		      </td>
	    </tr>
	  
	</table>
</form>
<br></body>
</html>