<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
    String DRAWBACK_PERSON_IDS = "DRAWBACK_PERSON_IDS";
   	String DRAWBACK_PERSON_NAMES = "DRAWBACK_PERSON_NAMES";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建退款</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<style>
html{
   background-color: #f2f2f2;
}
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
var customerName ="<%=customerName%>";
var customerId = "<%=customerId%>";
var drawbackPersonIds = "<%=DRAWBACK_PERSON_IDS%>";
var drawbackPersonNames = "<%=DRAWBACK_PERSON_NAMES%>";
function doInit(){
	getCrmCodeByParentCodeNo("DRAWBACK_STYLE","drawbackStyle");//退款方式
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	$("#customerName").val(customerName);
	$("#customerId").val(customerId);
	getRefundFinancal();//获取退款财务
	
	if( sid > 0){
		var url = contextPath+"/TeeCrmDrawbackController/getInfoBySid.action?sid="+sid;
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

//获取退款财务
function getRefundFinancal(){
	//获取参数
	var params = getSysParamByNames(drawbackPersonIds);
	
	if(params.length > 0){
		var param = params[0];
		if(param.paraValue != "" ){
			var personInfo = getPersonNameAndUuidByUuids(param.paraValue);
			var personId = personInfo.sid.split(",");
			var personName =personInfo.userName.split(",");
			var html ="";
			for(var i=0;i<personId.length;i++){
				 html +="<option value='"+personId[i]+"'>"+personName[i]+"</option>";
			}
			$("#refundFinancialId").append(html);

		}
		
	}
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
function save(callback){
	var url="";
	//编辑退款
	if(sid>0){
		if(check()){
			url=contextPath+"/TeeCrmDrawbackController/addOrUpdate.action?sid="+sid;
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
			    if(json.rtState){
			    	 callback(json.rtState);
		          }else{
		    	    	$.MsgBox.Alert(json.rtMsg);
		    	    }
		}
	}else{//新建退款
		if(check()){
			url=contextPath+"/TeeCrmDrawbackController/addOrUpdate.action";
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
		    if(json.rtState){
		   	 callback(json.rtState);
	          }else{
	    	    	$.MsgBox.Alert(json.rtMsg);
	    	    }
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
	if($("#drawbackNo").val()=="" || $("#drawbackNo").val()=="null" || $("#drawbackNo").val()==null){
			$.MsgBox.Alert_auto("请填写退款编号！");
			return false;
		}

	if($("#orderId").val()=="" || $("#orderId").val()=="null" || $("#orderId").val()==null||$("#orderId").val()==0){
		$.MsgBox.Alert_auto("请选择销售订单！");
		return false;
	}
	if($("#drawbackTimeDesc").val()=="" || $("#drawbackTimeDesc").val()=="null" || $("#drawbackTimeDesc").val()==null){
		$.MsgBox.Alert_auto("请选择退款日期！");
		return false;
	}
	if($("#drawbackAmount").val()=="" || $("#drawbackAmount").val()=="null" || $("#drawbackAmount").val()==null){
		$.MsgBox.Alert_auto("请填写退款金额！");
		return false;
	}
	if($("#managePersonId").val()=="" || $("#managePersonId").val()=="null" || $("#managePersonId").val()==null){
		$.MsgBox.Alert_auto("请选择负责人！");
		return false;
	}
	return true;
}


</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_addfield.jpg">
		<span class="title">新增/编辑退款</span>
	</div>
</div>

<form method="post" name="form1" id="form1" >
	<table class="TableBlock" width="100%" align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	   </tr>
	   <tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				退款编号<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input requried style="height: 23px;width: 350px;" id='drawbackNo' name='drawbackNo' type='text'/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				所属客户<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" id='customerName' name='customerName' class="BigInput" type='text' readonly="readonly"/>
				<input id='customerId' name='customerId' class="BigInput" type='hidden'/>
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
          <td class="TableData" width="150" style="text-indent:15px">退款日期 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td>
				<input readonly="readonly" type="text" style="width: 200px;" requried id='drawbackTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='drawbackTimeDesc' class="Wdate BigInput" />
			</td>
        </tr>
         <tr>
          <td class="TableData" width="150" style="text-indent:15px">退款金额（元） <span style="color:red;font-weight:bold;">*</span>：</td>
		   <td class="TableData">
				<input type="text" non_negative='true' style="height:23px;;width: 200px;" requried id="drawbackAmount" name="drawbackAmount" class="BigSelect"></select>
		   </td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">退款方式<span style="color:red;font-weight:bold;">*</span>：</td>
		   <td class="TableData">
				<select style="height: 25px;width: 200px;" id="drawbackStyle" name="drawbackStyle" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
		   </td>
        </tr>
	   <tr>
	       <td class="TableData" width="150" style="text-indent:15px">备注：</td>
			<td>
				<textarea id="remark" name="remark" class="BigTextarea" cols='60' rows='6'>
				</textarea>
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
	   <tr>
	        <td class="TableData" width="150" style="text-indent:15px">退款财务 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td class="TableData">
			       <select style="width: 200px;height: 25px;" id="refundFinancialId" name="refundFinancialId" class="BigSelect"></select>
			</td>
	   </tr>

	</table>
</form>
</body>
</html>