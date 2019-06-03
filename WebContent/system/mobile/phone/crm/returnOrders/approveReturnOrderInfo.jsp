<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	int returnOrderStatus=TeeStringUtil.getInteger(request.getParameter("returnOrderStatus"), 1);
	
	
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	int loginPersonId = person.getUuid();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>退货单详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}

#topPopover1 {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}

.app-row{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
}
.app-row br{
	margin:0px;
}
.app-row-content{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
	color:gray;
}
#productList{
	font-size:12px;
}
#productList .mui-input-row{

}
.pro_item label{
	float:left;
	width:35%;
	height:25px;
}
.pro_item div{
	float:left;
	width:65%;
	line-height:25px;
	height:25px;
}

.pro_item input{
	line-height:25px;
	height:25px;
}

.mui-input-row select{
	margin-left:0px;
	font-size:12px;
}
#productList .mui-input-row input{
	padding-left:0px;
}
</style>
</head>

<script type="text/javascript">
var returnOrderStatus=<%=returnOrderStatus %>;
var sid = "<%=sid%>";
var customerName = "<%=customerName%>";
var loginPersonId = <%=loginPersonId%>;

//初始化方法
function doInit(){
   
	if(sid>0){
		getInfoByUuid(sid);
	}

	//返回
 	backBtn.addEventListener("tap",function(){
		window.location = contextPath+'/system/mobile/phone/crm/returnOrders/indexApprove.jsp?returnOrderStatus='+returnOrderStatus;
	}); 

}


var managerPerId = "";
//根据主键获取详情
function getInfoByUuid(sid){
	var url=contextPath+"/TeeCrmReturnOrderController/getInfoBySid.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				bindJsonObj2Cntrl(data);
				managerPerId = json.rtData.managePersonId;
				if(data.returnOrderStatus==3){
					$("#reasons").show();
				}else{
					$("#reasons").hide();
				}
				
				renderOptBtns(data);
				//渲染产品信息
				selectProductCallBackFunc1(data.returnProductsIds);
			}
		}
	});
}

//获取产品集合
function getProductList(sid){
	var url = "<%=contextPath%>/TeeCrmOrderController/getProductItem.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var productList = json.rtData;
	 	$.each( productList, function(i, item){
			addSaleFollowProcuctItem(item,2);
		}); 
	}
}


//渲染操作
function renderOptBtns(data){
	var str="";
	$("#topPopover1").empty();
	if(data.returnOrderStatus == 1){
		$("#topTop").show();
		str='<ul class="mui-table-view">'+
	   '<li class="mui-table-view-cell" onclick="agree();">同意</li>'+
	   '<li class="mui-table-view-cell" onclick="reject();">驳回</li>'+
	   ' </ul>';
	  }else{
		  $("#topTop").hide();
	  }
 	$("#topPopover1").append(str);

}

//编辑
function edit(){
	window.location = 'addOrUpdate.jsp?sid=<%=sid%>&customerName='+customerName+'&type='+type;
}


/**
 * 删除
 */
function deleteById(){
	if(window.confirm("确定删除此退货单？删除退货单将删除此退货单下关联的数据，删除后不可恢复！")){
		var url = contextPath+ "/TeeCrmReturnOrderController/delById.action";
		var param={sid:sid};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("删除成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/returnOrders/index.jsp?type="+type;
				}else {
					alert("删除失败！");
				}
			}
		});	
	}
}



function selectProductCallBackFunc1(data){
	if(data.length>0){
		var pro = data.split(",");
		 for(var i = 0 ; i<pro.length-1 ; i++){
			var sid = pro[i]; //orderProduct 的  sid
			var dataPro = "";
			var url=contextPath+"/TeeReturnOrderProductsController/getInfoBySid.action";
			mui.ajax(url,{
				type:"post",
				dataType:"html",
				data:{sid:sid},
				timeout:10000,
				async:false,
				success:function(json){
					json = eval("("+json+")");
					if(json.rtState){
						dataPro=json.rtData;
						addSaleFollowProcuctItem(dataPro ,1);
					}else{
						alert("查询失败！");
					}
				}
			});
		} 
	}	
}



/**
 * 新增产品明细行
 @param item ： 产品或者对象，
 @param type ： 操作类型   1-选择产品 2-数据加载 其他-点击新增
 i=代表第几个元素
 */
function addSaleFollowProcuctItem(item , optType){
	var sid=0;
	var product_no_value = "";
	var product_name_value = "";
	var priductsTypeName_value = "";
	var units_value = "";//产品计量单位描述
	var item_number_input_value = 1;
	var item_price_input_value = 0;
	var item_total_amount_input_value = 0;
	var productsId = 0;
	var units="";//产品计量单位
	
	var maxNum=0;
	if(item){
		sid=item.sid;
		product_no_value = item.productsNo;//产品编号
		product_name_value = item.productsName;//产品名称
		priductsTypeName_value = item.productsTypeName;
		units_value = item.unitsName;//产品计量单位
		units=item.units;
		item_number_input_value = item.productsNumber;//产品数量
		item_price_input_value = item.price;//产品单价
		item_total_amount_input_value = item.totalAmount;
		productsId = item.productsId;
		maxNum=item.maxNum;
		if(!item.productsId){//编辑
			productsId = item.sid;//选择产品
		}
	}
	
	if(optType == 1){//直接选择产品
		item_total_amount_input_value = item_number_input_value * item_price_input_value;
	}
	
	var render=[];
	render.push("<div class=\"mui-input-row\" style=\"height:inherit;\">"
	   // +"<span class=\"mui-icon mui-icon-minus\" style=\"position:absolute;right:0px\"  productSid="+item.productsId+"  onclick='removeProductItem(this);'></span>"
		+"<input type=\"hidden\" value='"+productsId+"'  />"
		+"<input type=\"hidden\" value='"+units+"'   />"
	    +"<div class=\"pro_item\" style=\"height:auto;\">"
		+"<label>产品编号/名称</label>"
		+"<div>"+product_no_value+"/"+product_name_value+"</div>"
		+"</div>"
		+"<div class=\"pro_item\" style=\"height:auto;\">"
		+"<label>类别/计量单位</label>"
		+"<div>"+priductsTypeName_value+"/"+units_value+"</div>"
		+"</div>"
		+"<div class=\"pro_item\" style=\"height:auto;\">"
		+"<label>数量</label>"
		+"<div>"
		+"<input  placeholder=\"请输入数量\" readonly   type=\"text\" value="+item_number_input_value+"  maxNum="+maxNum+"   onchange='changeNumber(this);' />"
		+"</div>"
		+"</div>"
		+"<div class=\"pro_item\" style=\"height:auto;\">"
		+"<label>单价</label>"
		+"<div>"
		+"<input placeholder=\"请输入单价\" readonly    type=\"text\" value="+item_price_input_value+"  onchange='changeNumOrPrice(this);' />"
		+"</div>"
		+"</div>"
		+"<div class=\"pro_item\" style=\"height:auto;\">"
		+"<label>合计</label>"
		+"<div>"
		+"<input  class='item_total_amount'  readonly type=\"text\" value="+item_total_amount_input_value+" />"
		+"</div>"
		+"</div>"
		+"</div>");
	
	$("#productList").append(render.join(""));
	
	if(optType == 1 || optType == 2){
		var old_product_total_amount = $("#product_total_amount").html();
		if(old_product_total_amount == ''){
			old_product_total_amount = 0;
		}
		$("#product_total_amount").html(item_total_amount_input_value + parseFloat(old_product_total_amount,10));
	}
	
}




/**
 * 同意
 */
function agree(){
	if(window.confirm("确定订单信息无误？")){
		var url = contextPath+ "/TeeCrmReturnOrderController/agree.action";
		var param={sid:sid,returnOrderStatus:2};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("操作成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/returnOrders/indexApprove.jsp?returnOrderStatus="+returnOrderStatus;
				}else {
					alert("操作失败！");
				}
			}
		});	
	}
}

/**
 * 拒绝
 */
function reject(){
	window.location.href = "reject.jsp?sid="+sid+"&returnOrderStatus="+returnOrderStatus;
}

</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" id="backBtn">
		    <span class="mui-icon mui-icon-left-nav" ></span>返回
		</button>
	    <button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right"  href="#topPopover1" id="topTop" style="display: none;">
	        <span style="padding-right: 10px;"><a href="#topPopover1">操作</a></span>
	    </button> 
	    <h1 class="mui-title">退货单详情</h1>
		
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户名称</label>
		</div>
		<div class="app-row-content" id="customerName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>销售订单编号</label>
		</div>
		<div class="app-row-content" id="orderNo">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>退单日期</label>
		</div>
		<div class="app-row-content" id="returnTimeDesc" >
		
		</div>
	</div>
		<div class="mui-input-group">
		<div class="mui-input-row">
			<label>退货原因</label>
		</div>
		<div class="app-row-content" id="returnReasonDesc" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="app-row-content" id='managePersonName'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>订单管理员</label>
		</div>
		<div class="app-row-content" id='orderApprovalName'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>退货备注</label>
		</div>
		<div class="app-row-content" id='returnRemark'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>状态</label>
		</div>
		<div class="app-row-content" id='returnOrderStatusDesc'>
			
		</div>
			
	</div>
	<div class="mui-input-group" id="reasons"  style="display: none">
		<div class="mui-input-row">
			<label>驳回原因</label>
		</div>
		<div class="app-row-content" id='rejectReason' name="rejectReason">
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>创建人</label>
		</div>
		<div class="app-row-content" id='addPersonName'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>创建时间</label>
		</div>
		<div class="app-row-content" id='createTimeDesc'>
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>最后变化时间</label>
		</div>
		<div class="app-row-content" id='lastEditTimeDesc'>
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>产品信息</label>
		</div>
		<div id="productList"></div>
		<div class="mui-input-row">
			<label>产品合计：</label>
			<span id="product_total_amount" name="product_total_amount" style="margin-left:0px;width:65%;font-size: 14px;line-height: 40px">0</span>
		</div>
	</div>
</div>

    <div id="topPopover1" class="mui-popover">
	</div>

<br/>
</body>
</html>