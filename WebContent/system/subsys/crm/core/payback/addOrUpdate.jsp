<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String model = TeeAttachmentModelKeys.CRM_PAYBACK;
    String cusName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    String PAYMENT_PERSON_IDS = "PAYMENT_PERSON_IDS";
	String PAYMENT_PERSON_NAMES = "PAYMENT_PERSON_NAMES";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
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
var paymentPersonIds = "<%=PAYMENT_PERSON_IDS%>";
var paymentPersonNames = "<%=PAYMENT_PERSON_NAMES%>";
function doInit(){
	getCrmCodeByParentCodeNo("PAYBACK_STYLE","paybackStyle");//回款方式
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	
	getpaymentFinancial();//获取财务回款
	
	if( sid > 0){
		var url = contextPath+"/TeeCrmPaybackController/getInfoBySid.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var prc = json.rtData;
			bindJsonObj2Cntrl(prc);
			
			//附件
			var  attachmodels = prc.attachmodels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			}
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
	doInitUpload();

}

function selectCustomer(){
	var url = contextPath+"/system/subsys/crm/core/customer/query.jsp";
	  dialogChangesize(url, 860, 500);
}

//获取财务回款
function getpaymentFinancial(){
	//获取参数
	var params = getSysParamByNames(paymentPersonIds);
	
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
			$("#paymentFinancialId").append(html);

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
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
}


/**
 * 保存
 */
function save(){
	var url="";
	//编辑回款
	if(sid>0){
		if(check()){
		    url=contextPath+"/TeeCrmPaybackController/addOrUpdate.action?sid="+sid;
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
			    if(json.rtState){
			    	  parent.$.MsgBox.Alert_auto(json.rtMsg);
				        window.location.href=contextPath+"/system/subsys/crm/core/payback/paybackInfo.jsp?sid="+sid+"&customerName="+cusName;
				        opener.datagrid.datagrid("unselectAll");
						opener.datagrid.datagrid('reload');
			    
			    };	
		}
	}else{//新建回款
		if(check()){
			url=contextPath+"/TeeCrmPaybackController/addOrUpdate.action";
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
		    if(json.rtState){
		    	     parent.$.MsgBox.Alert_auto(json.rtMsg,function(){
			         window.location.href=contextPath+"/system/subsys/crm/core/payback/index.jsp";
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
	if($("#paybackNo").val()=="" || $("#paybackNo").val()=="null" || $("#paybackNo").val()==null){
			$.MsgBox.Alert_auto("请填写回款编号！");
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
	if($("#paybackTimeDesc").val()=="" || $("#paybackTimeDesc").val()=="null" || $("#paybackTimeDesc").val()==null){
		$.MsgBox.Alert_auto("请选择回款日期！");
		return false;
	}
	if($("#paybackAmount").val()=="" || $("#paybackAmount").val()=="null" || $("#paybackAmount").val()==null){
		$.MsgBox.Alert_auto("请填写回款金额！");
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
	    url= contextPath+"/system/subsys/crm/core/payback/index.jsp";
	}
	location.href=url;
}

</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_hkxq.png">
		<span class="title">新增/编辑回款</span>
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
				回款编号<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input requried style="height: 23px;width: 350px;" id='paybackNo' name='paybackNo' type='text'/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				所属客户<span style="color:red;font-weight:bold;">*</span>：
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
           <td class="TableData" width="150" style="text-indent:15px"> 附件：</td>
		    <td class="TableData">
		       	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
	
					<div id="fileContainer2"></div>
					<a id="uploadHolder2" class="add_swfupload">
						<img src="/common/images/upload/batch_upload.png"/>添加附件
					</a>
					<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		    </td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">提醒时间：</td>
			<td>
				<input readonly="readonly" type="text" style="width: 200px;" requried id='remindTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='remindTimeDesc' class="Wdate BigInput" />
			</td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">回款日期 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td>
				<input readonly="readonly" type="text" style="width: 200px;" requried id='paybackTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='paybackTimeDesc' class="Wdate BigInput" />
			</td>
        </tr>
         <tr>
          <td class="TableData" width="150" style="text-indent:15px">回款金额（元） <span style="color:red;font-weight:bold;">*</span>：</td>
		   <td class="TableData">
				<input non_negative='true' type="text" style="width: 350px;" requried id="paybackAmount" name="paybackAmount" class="BigSelect"></select>
		   </td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">回款方式<span style="color:red;font-weight:bold;">*</span>：</td>
		   <td class="TableData">
				<select style="height: 25px;width: 200px;" id="paybackStyle" name="paybackStyle" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
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
	        <td class="TableData" width="150" style="text-indent:15px">财务回款 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td class="TableData">
			       <select style="width: 200px;height: 25px;" id="paymentFinancialId" name="paymentFinancialId" class="BigSelect"></select>
			</td>
	   </tr>

	</table>
</form>
</body>
<script>
	$("#form1").validate();
</script>
</html>