<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String model = TeeAttachmentModelKeys.CRM_PRODUCTS;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品详情</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/product/manager/js/product.js"></script>
<script type="text/javascript">

var sid = "<%=sid%>";
function doInit(){
	if(sid>0){
		getInfoById(sid);
	}
}


/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/crmProductsController/getInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#status").html(statusFunc(prc.status));
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




function toReturn(){
	window.location.href = contextPath + "/system/subsys/crm/core/product/manager/productManage.jsp";
}




</script>

</head>
<body onload="doInit();">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table class="TableBlock" width="100%">
	<tr class="TableHeader" >
		<td class="" colspan="4" style="text-align: left; ">
				基本信息
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="15%;" >产品编号：<font style='color:red'>*</font></td>
		<td class="TableData" width="30%;" id="productsNo">
		</td>
		<td nowrap class="TableData" width="15%;" >产品名称：<font style='color:red'>*</font></td>
		<td class="TableData"  id="productsName">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >产品类别：</td>
		<td class="TableData"  id="productsTypeName">
		</td>
		<td nowrap class="TableData"   >规格型号 ：</td>
		<td class="TableData" id="productsModel">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">计量单位：</td>
		<td class="TableData" width="" id="unitsName" >
		</td>
		<td nowrap class="TableData"   >宽：</td>
		<td class="TableData" id="productsWide">
		</td>
		
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">生产厂商：</td>
		<td class="TableData" id="manufacturer">
		</td>
		<td nowrap class="TableData"   >生产地：</td>
		<td class="TableData"  id="manufacturerAdress">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >成本价格：</td>
		<td class="TableData" id="price">
		</td>
		<td nowrap class="TableData"   >销售价格：</td>
		<td class="TableData" id="salePrice" >
		</td>
	</tr>
	<tr style="display:none">
		<td nowrap class="TableData"   >是否可用：</td>
		<td class="TableData" colspan="3" id="status">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >存货下限：</td>
		<td class="TableData" id="minStock">
		</td>
		<td nowrap class="TableData"   >存货上限：</td>
		<td class="TableData" id="maxStock" >
		</td>
	</tr>
	<tr class="TableHeader">
		<td class="" colspan="4" style="text-align: left; ">
				其他
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >产品图片：</td>
		<td class="TableData"  colspan="3">
			<div id="fileContainer"></div> 
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">备　　注：</td>
		<td class="TableData" width="" colspan="3" id="remark">
		</td>
	</tr>
	<tr>
	<tr class="TableHeader">
		<td class="" colspan="4" style="text-align: left; ">
				记录信息
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >创建人：</td>
		<td class="TableData"  >
			<input type="hidden" id="createUserId" name="createUserId">
			<input type="text" name="createUserName" id="createUserName" size="" class="BigInput easyui-validatebox BigStatic"  value="" readonly="readonly">
		</td>
		<td nowrap class="TableData"   >创建时间：</td>
		<td class="TableData" >
			<input type="text" name="createTimeStr" id="createTimeStr" class="BigInput easyui-validatebox BigStatic"  value="" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >最后修改人：</td>
		<td class="TableData"  >
			<input type="hidden" id="lastModifyUserId" name="lastModifyUserId">
			<input type="text" name="lastModifyUserName" id="lastModifyUserName" size="" class="BigInput easyui-validatebox BigStatic"  value="" readonly="readonly">
		</td>
		<td nowrap class="TableData"   >最后修改时间 ：</td>
		<td class="TableData" >
			<input type="text" name="lastModifyTimeStr" id="lastModifyTimeStr" size="" class="BigInput easyui-validatebox BigStatic"  value="" readonly="readonly">
		</td>
	</tr>
	
	<tr>
		<td nowrap class="TableData" colspan=4 align="center">
			<input type="button"  value="返回" class="btn btn-default" onclick="toReturn();"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
<br>








</body>
</html>