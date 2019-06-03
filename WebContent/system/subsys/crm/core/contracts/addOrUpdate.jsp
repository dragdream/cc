<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String cusName = TeeStringUtil.getString(request.getParameter("customerName"), null);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
var cusName = "<%=cusName%>";
function doInit(){
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	
	if( sid > 0){
		var url = contextPath+"/TeeCrmContractsController/getInfoBySid.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var prc = json.rtData;
			bindJsonObj2Cntrl(prc);

		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}

}

function selectCustomer(){
	var url = contextPath+"/system/subsys/crm/core/customer/query.jsp";
	  dialogChangesize(url, 860, 500);
}

var customerArray = null;
/**
 * 查询客户信息 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackFunc 回调方法参数 
 */
function selectCustomer(retArray ,callBackFunc) {
	 customerArray = retArray;
	var url = contextPath+"/system/subsys/crm/core/order/query.jsp";
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 900, 500);
    $("#orderId").val(" ");
    $("#orderNo").val(" ");
}


var orderInfoArray = null;
/**
 * 查询订单 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackFunc 回调方法参数 
 */
function selectOrderInfo(retArray ,callBackFunc) {
	 orderInfoArray = retArray;
	var customerName = document.getElementById("customerName").value;
	var customerId = document.getElementById("customerId").value;
	if(customerName=="" || customerName=="null" || customerName==null){
		$.MsgBox.Alert_auto("请先选择客户！");
	}else{
	var url = contextPath+"/system/subsys/crm/core/returnOrder/queryOrders.jsp?customerId="+customerId;
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 900, 500);
		
	}
}


/**
 * 保存
 */
function save(){
	var url="";
	//编辑开票
	if(sid>0){
		if(check()){
		    url=contextPath+"/TeeCrmContractsController/addOrUpdate.action?sid="+sid;
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
			    if(json.rtState){
			    	 $.MsgBox.Alert_auto(json.rtMsg,function(){
				        window.location.href=contextPath+"/system/subsys/crm/core/contracts/contractsInfo.jsp?sid="+sid+"&customerName="+cusName;
				        opener.datagrid.datagrid("unselectAll");
						opener.datagrid.datagrid('reload');
			    
			    		 
			    	 });
			    };	
		}
	}else{//新建开票
		if(check()){
			url=contextPath+"/TeeCrmContractsController/addOrUpdate.action";
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
		    if(json.rtState){
		    	     parent.$.MsgBox.Alert_auto(json.rtMsg,function(){
			         window.location.href=contextPath+"/system/subsys/crm/core/contracts/index.jsp";
		    	 });
		    	 };	
		}
	    
	};
	
}

/**
 * 验证
 */
function check(){
	 var checks= $("#form1").valid(); 
	  if(!checks){
	    	return false;
	    }
	if($("#customerId").val()=="" || $("#customerId").val()=="null" || $("#customerId").val()==null){
		$.MsgBox.Alert_auto("请选择所属客户！");
		return false;
	}
	if($("#orderId").val()=="" || $("#orderId").val()=="null" || $("#orderId").val()==null||$("#orderId").val()==0){
		$.MsgBox.Alert_auto("请选择销售订单！");
		return false;
	}
	if($("#contractsNo").val()=="" || $("#contractsNo").val()=="null" || $("#contractsNo").val()==null){
		$.MsgBox.Alert_auto("请填写合同编号！");
		return false;
	}
	if($("#contractsTitle").val()=="" || $("#contractsTitle").val()=="null" || $("#contractsTitle").val()==null){
		$.MsgBox.Alert_auto("请填写合同标题！");
		return false;
	}
	if($("#contractsAmount").val()=="" || $("#contractsAmount").val()=="null" || $("#contractsAmount").val()==null){
		$.MsgBox.Alert_auto("请填写合同金额！");
		return false;
	}
	if($("#managePersonId").val()=="" || $("#managePersonId").val()=="null" || $("#managePersonId").val()==null){
		$.MsgBox.Alert_auto("请选择负责人！");
		return false;
	}
	return true;
}


function goBack(){
	var url="";
	if(sid>0){
		history.go(-1);
	}else{
	    url= contextPath+"/system/subsys/crm/core/contracts/index.jsp";
	}
	location.href=url;
}

</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_xjht.png">
		<span class="title">新增/编辑合同</span>
	</div>
   <div class="fr right">
      <input type="button" value="保存" class="btn-win-white" onclick="save();"/>
      <input type="button" value="返回" class="btn-win-white" onclick="goBack();"/>
   </div>
</div>

<form method="post" name="form1" id="form1" >
	<table class="TableBlock_page" width="60%" align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				客户名称<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input requried style="height: 23px;width: 350px;" id='customerName' name='customerName' class="BigInput" type='text' readonly="readonly"/>
				<input id='customerId' name='customerId' class="BigInput" type='hidden'/>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerId','customerName'])">选择客户</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerId','customerName')">清空</a>
			</td>
		</tr>
		 <tr>
          <td class="TableData" width="150" style="text-indent:15px">销售订单编号 <span style="color:red;font-weight:bold;">*</span>：</td>
		   <td class="TableData">
		        <input requried style="height: 23px;width: 350px;" id='orderNo' name='orderNo' class="BigInput" type='text' readonly="readonly"/>
				<input id='orderId' name='orderId' class="BigInput" type='hidden'/>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectOrderInfo(['orderId','orderNo'])">选择订单</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('orderId','orderNo')">清空</a>
		   </td>
        </tr>
         <tr>
          <td class="TableData" width="150" style="text-indent:15px">合同编号 <span style="color:red;font-weight:bold;">*</span>：</td>
		   <td class="TableData">
				<input type="text" style="width: 200px;" requried id="contractsNo" name="contractsNo" class="BigSelect"></select>
		   </td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">合同标题<span style="color:red;font-weight:bold;">*</span>：</td>
		   <td class="TableData">
				<input type="text" style="width: 350px;" requried id="contractsTitle" name="contractsTitle" class="BigSelect"></select>
		   </td>
        </tr>
         <tr>
          <td class="TableData" width="150" style="text-indent:15px">开始日期：</td>
			<td>
				<input readonly="readonly" type="text" style="width: 200px;" id='contractsStartTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='contractsStartTimeDesc' class="Wdate BigInput" />
			</td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">结束日期：</td>
			<td>
				<input readonly="readonly" type="text" style="width: 200px;" id='contractsEndTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='contractsEndTimeDesc' class="Wdate BigInput" />
			</td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">合同金额（元） <span style="color:red;font-weight:bold;">*</span>：</td>
		   <td class="TableData">
				<input non_negative='true' type="text" style="width: 200px;" requried id="contractsAmount" name="contractsAmount" class="BigSelect"></select>
		   </td>
        </tr>
	    <tr>
	        <td class="TableData" width="150" style="text-indent:15px">负责人 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td class="TableData">
				<input type="hidden" name="managePersonId" id="managePersonId"> 
				<input requried name="managePersonName" id="managePersonName" style="height:23px;width:350px;border: 1px solid #dadada;"   class="BigInput" wrap="yes" readonly />
				   <span class='addSpan'>
			               <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectSingleUser(['managePersonId', 'managePersonName'],'14')" value="选择"/>
				           &nbsp;&nbsp;
				           <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('managePersonId', 'managePersonName')" value="清空"/>
			       </span>
			</td>
	   </tr>
	 </table>
	</br>
	</br>
</form>
</body>
<script>
	$("#form1").validate();
</script>
</html>