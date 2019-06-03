<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String model = TeeAttachmentModelKeys.CRM_COMPETITOR;
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
var model = '<%=model%>';
function doInit(){

	if( sid > 0){
		var url = "<%=contextPath%>/teeCrmCompetitorController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var prc = json.rtData;
			bindJsonObj2Cntrl(prc);

			$("#companyCreateDate").html(prc.companyCreateDate.substring(0,10));
		
			var  attachmodels = prc.attachmodels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			} 
			
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
			<td nowrap class="TableData" width="120">公司名称：<span style="color:red;">*</span></td>
		    <td nowrap class="TableData" name="company" >
		  	</td>
		    <td nowrap class="TableData" width="120">注册资本：<span style="color:red;"></span></td>
		    <td nowrap class="TableData" name="registerCapital">
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" >公司地址：</td>
		    <td nowrap class="TableData" name="companyAddress">
		    </td>
		    <td nowrap class="TableData" >公司邮箱：</td>
		    <td nowrap class="TableData" name="email">
		        
		    </td>
	   </tr>
	   <tr>
			<td nowrap class="TableData" > 公司网址：</td>
		    <td nowrap class="TableData" name="website">
		    </td>
		    <td nowrap class="TableData" >联系电话：</td>
		    <td nowrap class="TableData" name="telephone">
		    </td>
	   </tr> 
	   <tr>
	    	<td nowrap class="TableHeader" colspan="4"  style="text-align: left;"> <span  class="Big3">详细信息</span></td>
	    </tr>
	     <tr>
		   <td nowrap class="TableData" >公司性质：</td>
		   <td nowrap class="TableData"  name="companyNature">
		   </td>
		   <td nowrap class="TableData" >公司规模：</td>
		   <td nowrap class="TableData" name="companyScaleDesc">
		   </td>
	    </tr>
	     <tr>
		  
		   <td nowrap class="TableData" >销售额（万元）：</td>
		   <td nowrap class="TableData" name="companySales">
		   </td>
		     <td nowrap class="TableData" >主要产品：</td>
		   	<td nowrap class="TableData"  name="mainProduct">
		       
		    </td>
	    </tr>
	    <tr>
		 
		    <td nowrap class="TableData" >成立时间：</td>
		    <td nowrap class="TableData"  name="companyCreateDate"  id="companyCreateDate">
		  	  </td>
		    <td nowrap class="TableData" >所属区域：</td>
		    <td nowrap class="TableData">
		  		<span name="provinceName"></span>
			
			<span name="cityName"></span>
			
		    </td>
	    </tr>
	    <tr>
			
		    <td nowrap class="TableData" >备注</td>
		    <td nowrap class="TableData" colspan="3" name="remark">  
	
		    </td>
	    </tr>
	     <tr>
			<td nowrap class="TableData" > 附件：</td>
		    <td nowrap class="TableData" colspan="3">
		       	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
	
					<div id="fileContainer2"></div>
							    </td>
	     </tr>
	   
	</table>
</form>
<br>
</body>
</html>