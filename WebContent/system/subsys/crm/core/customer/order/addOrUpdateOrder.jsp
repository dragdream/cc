<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
    String ORDER_MANAGER_IDS = "ORDER_MANAGER_IDS";
	String ORDER_MANAGER_NAMES = "ORDER_MANAGER_NAMES";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建销售订单</title>
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
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
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
var orderManagerIds = "<%=ORDER_MANAGER_IDS%>";
var orderManagerNames = "<%=ORDER_MANAGER_NAMES%>";

var CRM_PRODUCTS_UNITS_STR = "";
function doInit(){
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	$("#customerName").val(customerName);
	$("#customerId").val(customerId);
	getCrmCodeByParentCodeNo("FOLLOW_TYPE", "followType");
	var list = getCrmCodeByParentCodeNo("PRODUCTS_UNITS_TYPE","");// 计量单位
	for(var i = 0; i <list.length ; i++){
		var prcTemp = list[i];
		CRM_PRODUCTS_UNITS_STR = CRM_PRODUCTS_UNITS_STR + "<option value='"+prcTemp.codeNo+"'>" + prcTemp.codeName + "</option>";
	}
	
	getOrderManagerList();//获取订单管理员
	
	if( sid > 0){
		var url = contextPath+"/TeeCrmOrderController/getInfoBySid.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var prc = json.rtData;
			bindJsonObj2Cntrl(prc);
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
		
		getProductList(sid);
	}

}


//获取订单管理员
function getOrderManagerList(){
	//获取参数
	var params = getSysParamByNames(orderManagerIds);
	
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
			$("#orderApprovalId").append(html);

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
    $("#chanceId").val(0);
    $("#chanceName").val(" ");
    $("#receiverId").val(0);
    $("#receiverName").val(" ");
}


var chanceInfoArray = null;
/**
 * 查询商机信息 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackFunc 回调方法参数 
 */
function selectChanceInfo(retArray ,callBackFunc) {
	chanceInfoArray = retArray;
	var customerName = document.getElementById("customerName").value;
	var customerId = document.getElementById("customerId").value;
	if(customerName=="" || customerName=="null" || customerName==null){
		$.MsgBox.Alert_auto("请先选择客户！");
	}else{
	var url = contextPath+"/system/subsys/crm/core/order/queryChances.jsp?customerId="+customerId;
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 900, 500);
		
	}
}

var receiverInfoArray = null;
/**
 * 查询收货人 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackFunc 回调方法参数 
 */
function selectReceiver(retArray ,callBackFunc) {
	 receiverInfoArray = retArray;
	var customerName = document.getElementById("customerName").value;
	var customerId = document.getElementById("customerId").value;
	if(customerName=="" || customerName=="null" || customerName==null){
		$.MsgBox.Alert_auto("请先选择客户！");
	}else{
	var url = contextPath+"/system/subsys/crm/core/order/queryContacts.jsp?customerId="+customerId;
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 900, 500);
		
	}
}

/**
 * 获取订单管理员
 */
function getRuleApprovUser(id){
	var url = contextPath + "/attendLeaderRuleManage/selectRuleLeaderPerson.action";
	var para =  {} ;
	var jsonObj = tools.requestJsonRs(url,para);
	if(jsonObj.rtState){
		var data = jsonObj.rtData;
		var userIds =data.userIds;
		var userNames = data.userNames;
		var userIdArray = userIds.split(",");
		var userNameArray = userNames.split(",");
		var options = "";
		for(var i = 0;i<userIdArray.length; i++){
			if(userIdArray[i] != ""){
				options = options +  "<option value='" + userIdArray[i] + "'>" + userNameArray[i] + "</option>";
			}
		}
		$("#" + id).append(options);
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

/**
 * 保存
 */
function save(callback){
	var url="";
	//编辑订单
	if(sid>0){
		if(check()){
		    url=contextPath+"/TeeCrmOrderController/addOrUpdate.action?sid="+sid;
		    var param=tools.formToJson("#form1");
		    var itemList = getProductItem();//产品明细
			var str = tools.jsonArray2String(itemList);
			param["productsList"] = str;
		    var json=tools.requestJsonRs(url,param);
			    if(json.rtState){
			    	 callback(json.rtState);
		          }else{
		    	    	$.MsgBox.Alert(json.rtMsg);
		    	    }
		}
	}else{//新建销售订单
		if(check()){
			var param=tools.formToJson("#form1");
			var itemList = getProductItem();//产品明细
			var str = tools.jsonArray2String(itemList);
			param["productsList"] = str;
			url=contextPath+"/TeeCrmOrderController/addOrUpdate.action";
		    var json=tools.requestJsonRs(url,param);
		    if(json.rtState){
		    	 callback(json.rtState);
	          }else{
	    	    	$.MsgBox.Alert(json.rtMsg);
	    	    }	
}
		}
	    
	};
	

function getProductList(saleFollowId){
	var url = "<%=contextPath%>/TeeCrmOrderController/getProductItem.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var productList = json.rtData;
		$.each( productList, function(i, item){
			addSaleFollowProcuctItem(item,2);
		});
	}
}

/**
 *获取产品明细列表转JSON数组
 */
function getProductItem(){
	var itemList = $("#productList").find("tr");
	var productItem = new Array();
	for(var i =0; i <itemList.length ; i++){
		
		var itemTemp = itemList[i];
		var productsNo = $(itemTemp.cells[0]).find("input").val();
		var productsName = $(itemTemp.cells[1]).find("input").val();
		var productsModel = $(itemTemp.cells[2]).find("input").val();
		var units = $(itemTemp.cells[3]).find("select").val();
		var productsNumber = $(itemTemp.cells[4]).find("input").val();
		var price = $(itemTemp.cells[5]).find("input").val();
		
		var productsId = $(itemTemp.cells[7]).find("input[name='productsId']").val();
		var totalAmount = $(itemTemp.cells[6]).find("input").val();
		var itemp = {productsNo : productsNo , productsName: productsName , productsModel: productsModel,
				units:units,productsNumber:productsNumber,price:price,productsId:productsId, totalAmount:totalAmount};
		productItem.push(itemp);
	}
	return productItem;
}

/**
 * 点击产品调用函数
 */
function selectProductCallBackFunc(dataList){
	for(var i = 0 ; i<dataList.length ; i++){
		var item = dataList[i];
		item["productsNumber"] = 1;
		addSaleFollowProcuctItem(item , 1);
	}
}


/**
 * 新增产品明细行
 @param item ： 产品或者对象，
 @param type ： 操作类型   1-选择产品 2-数据加载 其他-点击新增
 */
function addSaleFollowProcuctItem(item , optType){
	var product_no_value = "";
	var product_name_value = "";
	var priductsModel_value = "";
	var units_value = "";
	var item_number_input_value = 1;
	var item_price_input_value = 0;
	var item_total_amount_input_value = 0;
	var productsId = 0;
	if(item){
		product_no_value = item.productsNo;
		product_name_value = item.productsName;
		priductsModel_value = item.productsModel;
		units_value = item.units;
		item_number_input_value = item.productsNumber;
		item_price_input_value = item.price;
		item_total_amount_input_value = item.totalAmount;
		productsId = item.productsId;
		if(!item.productsId){//编辑
			productsId = item.sid;//选择产品
		}
	}
	//产品编号
	var product_no_input = $("<input disabled='true' type='text' name='a1' required style='line-height:20px;width:120px;'>").addClass("").val(product_no_value);
	var productNo=$("<td required>").append(product_no_input);
/* 	product_no_input.validatebox({ 
		required:true 
	});  */
	//产品名称 
	var product_name_input = $("<input disabled='true' type='text' name='a2' required style='line-height:20px;width:120px;'>").addClass("").val(product_name_value);
	var productName=$("<td>").append(product_name_input);
/* 	product_name_input.validatebox({ 
		required:true 
	}); */
	
	//产品类型
	var priductsModel=$("<td>").append($("<input disabled='true' type='text' required style='line-height:20px;width:120px;'>").addClass("").val(priductsModel_value));
	
	var units=$("<td>").append($("<select>").append(CRM_PRODUCTS_UNITS_STR).addClass("").val(units_value));
	
	//数量
	var item_number_input = $("<input positive_integer='true' type='text' required style='line-height:20px;width:60px;'>").addClass("")
					.attr({"size":"5" , "name":"item_number","maxlength":"10"})
					.val(item_number_input_value)
					.blur( function () {onBlurFunc(this);countAll(); } )
	var productsNumber=$("<td>").append(item_number_input);
/* 	item_number_input.validatebox({ 
		required:true ,
		validType:'integeZero[]'
	}); */ 

	//价格
	var item_price_input = $("<input non_negative='true' type='text' required style='line-height:20px;width:60px;'>").addClass("")
		.attr({"size":"5" , "name":"item_price","maxlength":"10"})
		.val(item_price_input_value)
		.blur( function () {onBlurFunc(this);countAll(); }) ;
	var price = $("<td>").append(item_price_input);
/* 	item_price_input.validatebox({ 
		required:true ,
		validType:'pointTwoNumber[]'
	});  */
	
	if(optType == 1){//直接选择产品
		item_total_amount_input_value = item_number_input_value * item_price_input_value;
	}
	//合计
	var item_total_amount_input = $("<input no_negative_number='true' type='text' required style='line-height:20px;width:130px;'>").addClass("")
		.attr({"size":"5" , "name":"item_total_amount"})
		.val(item_total_amount_input_value)
		.blur( function () {
			getproduct_total_amount()
			}) ;
	var totalAmount=$("<td>").append(item_total_amount_input);
	var price=$("<td>").append(item_price_input);
	/* item_total_amount_input.validatebox({ 
		required:true ,
		validType:'pointTwoNumber[]'
	});  */
	
	//private String contractId;// 合同ID
	var productsIdInput = "<input type='hidden' name='productsId' value='" + productsId + "'>";
	var delTd=$("<td>").append($("<input>",{
		type:'button',
		value:'-'
	}).addClass("btn-win-white").addClass("btn").click(function(){
		$(this).parent("td").parent("tr").remove();
		getproduct_total_amount();
	})).append(productsIdInput);
	var tr=$("<tr>");
	tr.append(productNo);
	tr.append(productName);
	tr.append(priductsModel);
	tr.append(units);
	tr.append(productsNumber);
	tr.append(price);
	tr.append(totalAmount);
	tr.append(delTd);
	$("#productList").append(tr);
	
	if(optType == 1 || optType == 2){
		var old_product_total_amount = $("#product_total_amount").html();
		if(old_product_total_amount == ''){
			old_product_total_amount = 0;
		}
		$("#product_total_amount").html(item_total_amount_input_value + parseFloat(old_product_total_amount,10));
	}
	
}

/**
 * 事件
 */
 function onBlurFunc(obj){
		var item_number = $(obj).parent().parent().find("input[name='item_number']");
		var item_price = $(obj).parent().parent().find("input[name='item_price']");
		var item_total_amount = $(obj).parent().parent().find("input[name='item_total_amount']");
		item_total_amount.val(item_number.val() * item_price.val());
		getproduct_total_amount();
	}

/**
 * 获取合同总金额
 */
function getproduct_total_amount(){	
	var itemList = $("#productList").find("input[name='item_total_amount']");
	var product_total_amount = 0;
	for(var i = 0; i <itemList.length ; i++){
		var item = itemList[i];
		var total_amount = 0;
		var temp_total_amount = item.value;
		if(temp_total_amount == ""){
			temp_total_amount = 0;
		}
		total_amount = parseFloat(temp_total_amount,10);
		product_total_amount = product_total_amount + total_amount;
	}
	$("#product_total_amount").empty();
	$("#product_total_amount").html(product_total_amount);
}

/**
 * 验证
 */
function check(){
	 var checks= $("#form1").valid(); 
	  if(!checks){
	    	return false;
	    }
	if($("#orderNo").val()=="" || $("#orderNo").val()=="null" || $("#orderNo").val()==null){
			$.MsgBox.Alert_auto("请填写订单编号！");
			return false;
		}
	if($("#customerId").val()=="" || $("#customerId").val()=="null" || $("#customerId").val()==null){
		$.MsgBox.Alert_auto("请选择所属客户！");
		return false;
	}
	if($("#orderTimeDesc").val()=="" || $("#orderTimeDesc").val()=="null" || $("#orderTimeDesc").val()==null){
		$.MsgBox.Alert_auto("请选择下单日期！");
		return false;
	}
 	if($("#productList").html()=="" || $("#productList").html()=="null" || $("#productList").html()==null){
		$.MsgBox.Alert_auto("请选择产品！");
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
	    url= contextPath+"/system/subsys/crm/core/order/index.jsp";
	}
	location.href=url;
}

</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_addfield.jpg">
		<span class="title">新增/编辑销售订单</span>
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
	      <td class="TableData" width="150" style="text-indent:15px">订单编号 <span style="color:red;font-weight:bold;">*</span>：</td>
	      <td>
	        <input style="height: 23px;width: 350px;" id='orderNo' name='orderNo' class="BigInput" type='text'/>
	      </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				所属客户 <span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" id='customerName' name='customerName' class="BigInput" type='text' readonly="readonly"/>
				<input id='customerId' name='customerId' class="BigInput" type='hidden'/>
			</td>
		</tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">商机名称：</td>
		   <td class="TableData">
		        <input style="height: 23px;width: 350px;" id='chanceName' name='chanceName' class="BigInput" type='text' readonly="readonly"/>
				<input id='chanceId' name='chanceId' class="BigInput" type='hidden'/>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectChanceInfo(['chanceId','chanceName'])">选择商机</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('chanceId','chanceName')">清空</a>
		   </td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">下单日期 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td>
				<input readonly="readonly" type="text" style="width: 200px;" requried id='orderTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='orderTimeDesc' class="Wdate BigInput" />
			</td>
        </tr>
  </table>
  <table class="TableBlock" width="100%" align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">订单明细</B>
		   </td>
	   </tr>
	   	<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				产品<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				 <div style="padding:0px 25px 0px 25px;">
	    		 	 <input type="button" value="产品查询" class="btn-win-white" title="产品查询" onclick="selectProductInfo('[]' , 'selectProductCallBackFunc')" >&nbsp;&nbsp;
	    			 <table class="TableBlock" style="width:90%" align="center" >
		    		 	<tr>
		    		 		<td class="TableData" align="center">产品编号</td>
		    		 		<td  class="TableData" align="center">产品名称</td>
		    		 		<td class="TableData" align="center">类别</td>
		    		 		<td class="TableData" align="center">计量单位</td>
		    		 		<td class="TableData" align="center">数量</td>
		    		 		<td class="TableData" align="center">单价</td>
		    		 		<td class="TableData" align="center">合计</td>
		    		 	</tr>
		    		 	<tbody id="productList"></tbody>
		    		 	<tr>
		    		 		<td colspan="8">产品合计：<span id="product_total_amount" name="product_total_amount">0</span></td>
		    		 	</tr>
	    		 	</table>
	    		 </div>
			</td>
		</tr>
  </table>
  
    <table class="TableBlock" width="100%" align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">收货信息</B>
		   </td>
	   </tr>
	    <tr>
	      	<td class="TableData" width="150" style="text-indent:15px">收货人：</td>
			<td>
				<input style="height: 23px;" id='receiverName' name='receiverName' class="BigInput" type='text' readonly="readonly"/>
				<input id='receiverId' name='receiverId' class="BigInput" type='hidden'/>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectReceiver(['receiverId','receiverName'])">选择收货人</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('receiverId','receiverName')">清空</a>
			</td>
	   </tr>
	    <tr>
          <td class="TableData" width="150" style="text-indent:15px">收货人电话：</td>
		   <td class="TableData">
				<input type="text" style="width: 350px;height: 23px;" requried id="receiverTelephone" name="receiverTelephone" class="BigSelect"></select>
		   </td>
        </tr>
          <tr>
          <td class="TableData" width="150" style="text-indent:15px">交货日期 ：</td>
			<td>
				<input readonly="readonly" type="text" style="width: 200px;height: 23px;" requried id='transactionsTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='transactionsTimeDesc' class="Wdate BigInput" />
			</td>
        </tr>
   </table>
   <table class="TableBlock" width="100%" align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">补充信息</B>
		   </td>
	   </tr>
        
        <tr>
	        <td class="TableData" width="150" style="text-indent:15px">负责人 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td class="TableData">
				<input type="hidden" name="managePersonId" id="managePersonId"> 
				<input name="managePersonName" id="managePersonName" style="height:23px;width:350px;border: 1px solid #dadada;"   class="BigInput" wrap="yes" readonly />
				   <span class='addSpan'>
			               <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectSingleUser(['managePersonId', 'managePersonName'],'14')" value="选择"/>
				           &nbsp;&nbsp;
				           <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('managePersonId', 'managePersonName')" value="清空"/>
			       </span>
			</td>
	   </tr>
	   <tr>
	      <td class="TableData" width="150" style="text-indent:15px">备注：</td>
	      <td class="tableData">
			   <textarea style="width: 350px;" requried rows="5" cols="8" id="remark" name="remark"></textarea>
			
			</td>
	   </tr>
	    <tr>
	        <td class="TableData" width="150" style="text-indent:15px">订单管理员 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td class="TableData">
			      <!--  <input id="orderManageUserName" name="orderManageUserName" type="hidden" /> -->
			       <select style="width: 200px;height: 25px;" id="orderApprovalId" name="orderApprovalId" class="BigSelect"></select>
			</td>
	   </tr>
	 </table>
	
</form>
<script>
	$("#form1").validate({
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		}
	});
</script>
</body>
</html>