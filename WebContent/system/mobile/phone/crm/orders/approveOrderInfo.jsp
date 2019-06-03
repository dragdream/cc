<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    
	int orderStatus=TeeStringUtil.getInteger(request.getParameter("orderStatus"),0);
	
	
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	int loginPersonId = person.getUuid();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>订单详情</title>
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

.mui-input-row input{
	padding-left:0px;
}
</style>
</head>

<script type="text/javascript">
var orderStatus=<%=orderStatus %>;
var sid = "<%=sid%>";
var customerName = "<%=customerName%>";
var loginPersonId = <%=loginPersonId%>;
//初始化方法
function doInit(){
	if(sid>0){
		getInfoByUuid(sid);
		//getProductList(sid);
	}

	//返回
 	backBtn.addEventListener("tap",function(){
		window.location = contextPath+'/system/mobile/phone/crm/orders/indexApprove.jsp?orderStatus='+orderStatus;
	}); 

}


var managerPerName = '';
var managerPerId = "";
var orderManagerId = "";
//根据主键获取详情
function getInfoByUuid(sid){
	var url=contextPath+"/TeeCrmOrderController/getInfoBySid.action";
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
				managerPerName = json.rtData.managePersonName;
				managerPerId = json.rtData.managePersonId;
				orderManagerId = json.rtData.orderApprovalId;
				//orderStatus = json.rtData.orderStatusDesc;
				if(data.orderStatus==3){
					$("#rejects").show();
					$("#rejects1").show();
				}else{
					$("#rejects").hide();
					$("#rejects1").hide();
				}
				
				renderOptBtns(data);
				
			}
		}
	});
	
	getProductList(sid);
}

//获取产品集合
function getProductList(saleFollowId){
	var url = "<%=contextPath%>/TeeCrmOrderController/getProductItem.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:saleFollowId},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtData.length>0){
				var productList = json.rtData;
				$.each( productList, function(i, item){
					addSaleFollowProcuctItem(item,2);
				});		
			}
			
				
		}
	});
}


function addSaleFollowProcuctItem(item , optType , max){
	var product_no_value = "";
	var product_name_value = "";
	var priductsTypeName_value = "";
	var units_value = "";//产品计量单位描述
	var item_number_input_value = 1;
	var item_price_input_value = 0;
	var item_total_amount_input_value = 0;
	var productsId = 0;
	var units="";//产品计量单位
	if(item){
		product_no_value = item.productsNo;//产品编号
		product_name_value = item.productsName;//产品名称
		priductsTypeName_value = item.productsTypeName;
		units_value = item.unitsName;//产品计量单位
		units=item.units;
		item_number_input_value = item.productsNumber;//产品数量
		item_price_input_value = item.price;//产品单价
		item_total_amount_input_value = item.totalAmount;
		productsId = item.productsId;
		if(!item.productsId){//编辑
			productsId = item.sid;//选择产品
		}
	}
	
	if(optType == 1){//直接选择产品
		item_total_amount_input_value = item_number_input_value * item_price_input_value;
	}
	
	var render=[];
	render.push("<div class=\"mui-input-row\" style=\"height:inherit;\">"
	   // +"<span class=\"mui-icon mui-icon-minus\" style=\"position:absolute;right:0px\" onclick='removeProductItem(this);'></span>"
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
		+"<input readonly  id=\"num_"+max+"\" type=\"text\" value="+item_number_input_value+"  onchange='changeNumOrPrice(this);' />"
		+"</div>"
		+"</div>"
		+"<div class=\"pro_item\" style=\"height:auto;\">"
		+"<label>单价</label>"
		+"<div>"
		+"<input readonly  id=\"price_"+max+"\"  type=\"text\" value="+item_price_input_value+"  onchange='changeNumOrPrice(this);' />"
		+"</div>"
		+"</div>"
		+"<div class=\"pro_item\" style=\"height:auto;\">"
		+"<label>合计</label>"
		+"<div>"
		+"<input  class='item_total_amount' id=\"amount_"+max+"\"  readonly type=\"text\" value="+item_total_amount_input_value+" />"
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


//渲染操作
function renderOptBtns(data){
	
	var status=data.orderStatus;
	var str="";
	$("#topPopover1").empty();
	  if(status == 1){//待确认
		$("#topTop").show();
		str='<ul class="mui-table-view">'+
	    '<li class="mui-table-view-cell" onclick="agree();">同意</li>'+
	    '<li class="mui-table-view-cell" onclick="reject();">驳回</li>'+
	   ' </ul>';
	  }else{//已确认   已驳回
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
	if(window.confirm("确定删除此订单？删除订单将删除此订单下相关联的数据，删除后不可恢复！")){
		var url = contextPath+ "/TeeCrmOrderController/delById.action";
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
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/orders/index.jsp?type="+type;
				}else {
					alert("删除失败！");
				}
			}
		});	
	}
}





/**
 * 同意
 */
function agree(){
	if(window.confirm("确定订单信息无误？")){
		var url = contextPath+ "/TeeCrmOrderController/agree.action";
		var param={sid:sid,orderStatus:2};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("操作成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/orders/indexApprove.jsp?orderStatus="+orderStatus;
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
	window.location.href = "reject.jsp?sid="+sid+"&orderStatus="+orderStatus;
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
	    <h1 class="mui-title">订单详情</h1>
		
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">基本信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>客户名称</label>
			<div  id="customerName" style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>商机名称</label>
			<div  id="chanceName" style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>销售订单编号</label>
			<div  id="orderNo" style="line-height: 40px">	</div>
		</div>
		<div class="mui-input-row">
			<label>下单日期</label>
			<div  id="orderTimeDesc" style="line-height: 40px"></div>
		</div>
	</div>
	
	
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">订单明细</span></label>
		</div>
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
	
	
	
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">交货信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>收货人</label>
			<div  id='receiverName' style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>收货人电话</label>
			<div id='receiverTelephone' style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>交货日期</label>
			<div  id='transactionsTimeDesc' style="line-height: 40px"></div>
		</div>
	</div>
	
	
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">补充信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>负责人</label>
			<div  id='managePersonName' style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>订单管理员</label>
			<div  id='orderApprovalName' style="line-height: 40px"></div>
		</div>
		
		<div class="mui-input-row">
			<label>状态</label>
			<div  id='orderStatusDesc' style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row" id="rejects" style="display: none">
			<label>驳回原因</label>
		</div>
		<div class="mui-input-row" id="rejects1"  style="display: none">
			<div  id='rejectReason' style="padding-left: 15px;line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>创建人</label>
			<div  id='addPersonName' style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>创建时间</label>
			<div id='createTimeDesc' style="line-height: 40px"></div>
		</div>	
		<div class="mui-input-row">
			<label>最后变化时间</label>
			<div id='lastEditTimeDesc' style="line-height: 40px"></div>
		</div>
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <div class="app-row-content" id='remark'></div>
		</div>
	</div>
</div>

    <div id="topPopover1" class="mui-popover">
	</div>

<br/>
</body>
</html>