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
<title>新建/编辑产品</title>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@include file="/header/upload.jsp"%>
<%@ include file="/header/ztree.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/core/product/manager/js/product.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>


<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	doInitUpload();
	//getProductsTree();
	var url =  contextPath+"/teeCrmProductsTypeController/getProductTypeTree.action";
	ZTreeTool.comboCtrl($("#productsType"),{url:url});
	getCrmCodeByParentCodeNo("PRODUCTS_UNITS_TYPE","units");
	
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
		file_types:"*.jpg;*.png;*.gif;*.jpeg",
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
	
}

/* 查看详情 */
function getInfoById(id){
	var url =   "<%=contextPath%>/crmProductsController/getInfoById.action";
	var para = {sid : id,isEdit:1};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
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
	var check = $("#form1").form('validate'); 
	if(!check){
		return false; 
	}

	
	return true;
}
function doSaveOrUpdate(){
	if(checkForm()){
		var url = "<%=contextPath %>/crmProductsController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1"));
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			toReturn();
			top.$.jBox.tip("保存成功!",'info',{timeout:1500});
		}else{
			alert(jsonRs.rtMsg);
		}
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
		<td class="TableData" width="30%;" >
			<input type="text" name="productsNo" id="productsNo" size="" maxlength="50" class="BigInput  easyui-validatebox" required="true" size="15" >
		</td>
		<td nowrap class="TableData" width="15%;" >产品名称：<font style='color:red'>*</font></td>
		<td class="TableData"  >
			<input type="text" name="productsName" id="productsName" maxlength="100" class="BigInput  easyui-validatebox" required="true" value="" >
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >产品类别：</td>
		<td class="TableData"  >
			<input id="productsType" name="productsType"  type="text" style="display:none;"/>
		</td>
		<td nowrap class="TableData"   >规格型号 ：</td>
		<td class="TableData" >
			<input type="text" name="productsModel" id="productsModel" size="" class="BigInput  easyui-validatebox"  value="" maxlength="150">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">计量单位：</td>
		<td class="TableData" width="" >
			<select name="units" id="units"   class="BigSelect easyui-validatebox"  title="类型可在“客户关系管理”->“维护设置”->参数类型设置">
				<option value="">请选择</option>
			</select
		</td>
		<td nowrap class="TableData"   >宽：</td>
		<td class="TableData" >
			<input type="text" name="productsWide" id="productsWide" size="" class="BigInput  easyui-validatebox"  value="" maxlength="50">
		</td>
		
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">生产厂商：</td>
		<td class="TableData" >
			<input type="text" name="manufacturer" id="manufacturer" size="" class="BigInput  easyui-validatebox"  value="" maxlength="150">
		</td>
		<td nowrap class="TableData"   >生产地：</td>
		<td class="TableData"  >
			<input type="text" name="manufacturerAdress" id="manufacturerAdress" size="" class="BigInput  easyui-validatebox"  value="" maxlength="150">
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData">成本价格：</td>
		<td class="TableData" >
			<input type="text" name="price" id="price" size="" class="BigInput  easyui-validatebox"  validType='pointTwoNumber[]'  value="" maxlength="10">
		</td>
		<td nowrap class="TableData"   >销售价格：</td>
		<td class="TableData"  >
			<input type="text" name="salePrice" id="salePrice" size="" class="BigInput  easyui-validatebox"  validType='pointTwoNumber[]'  value="" maxlength="10">
		</td>
	</tr>
	<tr style="display:none">
		<td nowrap class="TableData"   >是否可用：</td>
		<td class="TableData" colspan="3">
			<select id="status" name="status" class="BigSelect easyui-validatebox" >
				<option value="1">可用</option>
				<option value="0">不可用</option>
			</select>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"   >存货下限：</td>
		<td class="TableData">
			<input type="text" name="minStock" id="minStock" size="" class="BigInput  easyui-validatebox"  validType='pointTwoNumber[]'  value="" maxlength="10">
		</td>
		<td nowrap class="TableData"   >存货上限：</td>
		<td class="TableData">
			<input type="text" name="maxStock" id="maxStock" size="" class="BigInput  easyui-validatebox"  validType='pointTwoNumber[]'  value="" maxlength="10">
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
			<a id="uploadHolder"class="add_swfupload"><img src="<%=systemImagePath %>/upload/batch_upload.png"/>批量上传</a> 
			<input id="attacheIds" name="attacheIds" type="hidden" value=""/>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData"  width="">备　　注：</td>
		<td class="TableData" width="" colspan="3">
			<textarea rows="5" cols="90" id="remark" name="remark" maxlength="200" class="BigTextarea"></textarea>
		</td>
	</tr>
	<tr>
	<tr class="TableHeader" style="display: <%if(sid<=0){out.print("none;");}%>">
		<td class="" colspan="4" style="text-align: left; "><b>记录信息</b></td>
	</tr>
	<tr style="display: <%if(sid<=0){out.print("none;");}%>">
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
	<tr style="display: <%if(sid<=0){out.print("none;");}%>">
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
			<input type="button"  value="保存" class="btn btn-primary" onclick="doSaveOrUpdate();"/>&nbsp;&nbsp;
			<input type="button"  value="返回" class="btn btn-default" onclick="toReturn();"/>&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
<br>








</body>
</html>