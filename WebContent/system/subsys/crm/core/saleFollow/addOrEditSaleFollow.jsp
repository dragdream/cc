<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String model = TeeAttachmentModelKeys.CRM;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/upload.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
</style>

<script>
var sid = "<%=sid%>";
var CRM_PRODUCTS_UNITS_STR = "";
function doInit(){
	getCrmCodeByParentCodeNo("FOLLOW_TYPE", "followType");
	var list = getCrmCodeByParentCodeNo("PRODUCTS_UNITS_TYPE","");// 计量单位
	for(var i = 0; i <list.length ; i++){
		var prcTemp = list[i];
		CRM_PRODUCTS_UNITS_STR = CRM_PRODUCTS_UNITS_STR + "<option value='"+prcTemp.codeNo+"'>" + prcTemp.codeName + "</option>";
	}
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeCrmSaleFollowController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
			getContactUser(json.rtData.customerId);
			$("#contantsId").val(json.rtData.contantsId);
			$("#nextFollowUserId").val(json.rtData.nextFollowUserId);
			if(json.rtData.isRemind==1){
				$("#isRemind").attr("checked","true");
			}
			/**
			*处理附件
			*/
			var  attachmodels = json.rtData.attacheModels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			}
			/**
			*
			*获取产品列表
			*/
			getProductList(sid);
		}
	}
	doInitUpload();
	showPhoneSmsForModule("sms","041",loginPersonId);
}

function commit(){
	if($("#form1").form("validate") && check()){
		var param = tools.formToJson($("#form1"));
		var itemList = getProductItem();//产品明细
		var str = tools.jsonArray2String(itemList);
		param["productsList"] = str;
		var url = contextPath+"/TeeCrmSaleFollowController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			//手机短信提醒自己
			var toUserId=""+loginPersonId;
			var smsContent="您有一个客户跟单："+$("#customerName").val();
			var sendTime=$("#nextFollowTimeDesc").val();
			sendPhoneSms(toUserId,smsContent,sendTime);
			$.jBox.tip(json.rtMsg,"success");
			returnBackFunc();
		}else{
			$.jBox.tip("添加失败！","error");
		}
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
		post_params:{model:"crm"}//后台传入值，model为模块标志
	});
}

function getProductList(saleFollowId){
	var url = "<%=contextPath%>/TeeCrmSaleFollowController/getProductItem.action?saleFollowId="+saleFollowId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var productList = json.rtData;
		$.each( productList, function(i, item){
			addSaleFollowProcuctItem(item,2);
		});
	}
}


function getContactUser(customerId,customerName){
	var url = contextPath+"/TeeCrmContactUserController/getContactUserList.action";
	var json = tools.requestJsonRs(url,{customerId:customerId});
	if(json.rtState){
		var contactUserList = json.rtData;
		var html = "<option value=\"0\"></option>";
		for(var i=0;i<contactUserList.length;i++){
			html+="<option value=\""+contactUserList[i].sid+"\">"+contactUserList[i].name+"</option>";
		}
		$("#contantsId").html(html);
		$("#nextFollowUserId").html(html);
		
	}
	
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
	if(item){
		product_no_value = item.productsNo;
		product_name_value = item.productsName;
		priductsModel_value = item.productsModel;
		units_value = item.units;
		item_number_input_value = item.productsNumber;
		item_price_input_value = item.price;
		item_total_amount_input_value = item.totalAmount;
	}
	//产品编号
	var product_no_input = $("<input>").addClass("BigInput easyui-validatebox").val(product_no_value);
	var productNo=$("<td>").append(product_no_input);
	product_no_input.validatebox({ 
		required:true 
	}); 
	//产品名称 
	var product_name_input = $("<input>").addClass("BigInput easyui-validatebox").val(product_name_value);
	var productName=$("<td>").append(product_name_input);
	product_name_input.validatebox({ 
		required:true 
	});
	
	//产品类型
	var priductsModel=$("<td>").append($("<input>").addClass("BigInput").val(priductsModel_value));
	
	var units=$("<td>").append($("<select>").append(CRM_PRODUCTS_UNITS_STR).addClass("BigSelect").val(units_value));
	
	//数量
	var item_number_input = $("<input>").addClass("BigInput easyui-validatebox")
					.attr({"size":"5" , "name":"item_number","maxlength":"10"})
					.val(item_number_input_value)
					.blur( function () {onBlurFunc(this) } )
	var productsNumber=$("<td>").append(item_number_input);
	item_number_input.validatebox({ 
		required:true ,
		validType:'integeZero[]'
	}); 

	//价格
	var item_price_input = $("<input>").addClass("BigInput easyui-validatebox")
		.attr({"size":"5" , "name":"item_price","maxlength":"10"})
		.val(item_price_input_value)
		.blur( function () {onBlurFunc(this) }) ;
	var price = $("<td>").append(item_price_input);
	item_price_input.validatebox({ 
		required:true ,
		validType:'pointTwoNumber[]'
	}); 
	
	if(optType == 1){//直接选择产品
		item_total_amount_input_value = item_number_input_value * item_price_input_value;
	}
	//合计
	var item_total_amount_input = $("<input>").addClass("BigInput")
		.attr({"size":"5" , "name":"item_total_amount"})
		.val(item_total_amount_input_value)
		.blur( function () {
			getproduct_total_amount()
			}) ;
	var totalAmount=$("<td>").append(item_total_amount_input);
	var price=$("<td>").append(item_price_input);
	item_total_amount_input.validatebox({ 
		required:true ,
		validType:'pointTwoNumber[]'
	}); 
	
	//private String contractId;// 合同ID
	var delTd=$("<td>").append($("<input>",{
		type:'button',
		value:'-'
	}).addClass("btn-primary").addClass("btn").click(function(){
		$(this).parent("td").parent("tr").remove();
		getproduct_total_amount();
	}));
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
		
		var totalAmount = $(itemTemp.cells[6]).find("input").val();
		var itemp = {productsNo : productsNo , productsName: productsName , productsModel: productsModel,
				units:units,productsNumber:productsNumber,price:price , totalAmount:totalAmount};
		productItem.push(itemp);
	}
	return productItem;
}

function check(){
	if($("#customerId").val()=="" || $("#customerId").val()=="null" || $("#customerId").val()==null){
		$.jBox.tip("跟踪客户客户不能为空！","info");
		$("#customerId").focus();
		return false;
	}
	return true;
}
function returnBackFunc(){
	location.href = contextPath + "/system/subsys/crm/core/saleFollow/saleFollowManage.jsp";
}
</script>
</head>
<body onload="doInit();" style="margin:0 auto;padding:0 auto;text-align:center;font-size:12px;margin-top:10px;">
<form id="form1" name="form1">
	<table style="width:90%;font-size:12px;" class='TableBlock' align="center">
		<tr class="TableHeaer" align='left'>
			<td  class="TableHeader" colspan="4"  align="left" style="text-align: left;">
					本次跟踪信息：
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				跟踪客户<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input class="BigInput" type="text" id ="customerName" name='customerName' readonly="readonly" />
				<input class="BigInput" type="hidden" id = "customerId" name='customerId' />
				<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerId','customerName'],'getContactUser')">选择客户</a></a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerId','customerName')">清空</a>
			</td>
			<td>
				跟踪方式：
				</td>
			<td>
				<select id="followType" name="followType" class="BigSelect"></select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				联系人：
				</td>
			<td>
				<select id="contantsId" name="contantsId" class="BigSelect"></select>
			</td>
			<td>
				跟踪时间：
				</td>
			<td>
				<input type="text" id='followDateDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='followDateDesc' class="Wdate BigInput" />
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				跟踪状态：
			</td>
			<td colspan='3'>
				<select id="followResult" name="followResult" class="BigSelect" >
					<option value='0'>跟单中</option>
					<option value='1'>已成交</option>
					<option value='2'>已作废</option>
				</select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				附件：
			</td>
			<td colspan="3">
		       	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
	
					<div id="fileContainer2"></div>
					<a id="uploadHolder2" class="add_swfupload">
						<img src="<%=systemImagePath %>/upload/batch_upload.png"/>添加附件
					</a>
					<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		    </td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				跟踪内容：
			</td>
			<td colspan='3'>
				<textarea id="followContent" name="followContent" class="BigTextarea" cols='60' rows='10' validType="maxLength[1000]"></textarea>
			</td>
		</tr>
		<tr class="TableHeaer" align='left'>
			<td  class="TableHeader" colspan="4"  align="left" style="text-align: left;">
					下次跟踪信息：
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				下次联系人：
			</td>
			<td>
				<select type="text" class="BigSelect" name="nextFollowUserId" id="nextFollowUserId" ></select>
			</td>
			<td>
				下次跟踪时间：
				</td>
			<td>
				<input type="text" id='nextFollowTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='nextFollowTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				下次跟踪内容：
			</td>
			<td colspan='3'>
				<textarea id="nextFollowContent" name="nextFollowContent" class="BigTextarea" cols='60' rows='10' validType="maxLength[1000]"></textarea>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				是否提醒：
			</td>
			<td colspan='3' id='sms'>
				<input type="checkbox"  name="isRemind" id="isRemind" />内部短信
			</td>
		</tr>
		  	<tr align="left">
	    	<td nowrap class="TableHeader" colspan="4"  align="left" style="text-align: left;"> 产品信息
	    	</td>
	    </tr>
	    <tr align="left">
	    	<td nowrap class="" colspan="4"  align="left" style="text-align: left;"> 

	    		 <div style="padding:0px 25px 0px 25px;">
	    		 	 <input type="button" value="产品查询" class="btn btn-warning btn-xs" title="产品查询" onclick="selectProductInfo('[]' , 'selectProductCallBackFunc')" >&nbsp;&nbsp;
	    			 <table class="TableList" style="width:100%" align="center" >
		    		 	<tr>
		    		 		<td class="TableData" align="center">产品编号</td>
		    		 		<td  class="TableData" align="center">产品名称</td>
		    		 		<td class="TableData" align="center">类别</td>
		    		 		<td class="TableData" align="center">计量单位</td>
		    		 		<td class="TableData" align="center">数量</td>
		    		 		<td class="TableData" align="center">单价</td>
		    		 		<td class="TableData" align="center">合计</td>
		    		 		<td class="TableData" align='center' style="padding-left:5px;"><input type="button" name="addUser" id="addUser" required="true" value="+" class="btn btn-primary" onclick="addSaleFollowProcuctItem();"/> </td>
		    		 	</tr>
		    		 	<tbody id="productList">
		    		 	
		    		 	</tbody>
		    		 	<tr>
		    		 		<td colspan="8">产品合计：<span id="product_total_amount"></span></td>
		    		 	</tr>
	    		 	</table>
	    		 </div>	
	    	</td>
	    </tr>
	</table>
	<br/>
	<div id="control" style='margin:0 auto;text-align:center; height:28px;line-height:28px;width:"90%"'>
		<input id="sid" name="sid" type='hidden'value="<%=sid %>"/>
		<input id="saveInfo" name="saveInfo" type='button' class="btn btn-primary" value="保存" onclick='commit();'/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="back" name="back" type='button' class="btn btn-primary" value='返回' onclick='returnBackFunc()'/>
	</div>
	<br/><br/>
</form>
</body>
</html>