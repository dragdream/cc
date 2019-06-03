<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	String ORDER_MANAGER_IDS = "ORDER_MANAGER_IDS";
	String ORDER_MANAGER_NAMES = "ORDER_MANAGER_NAMES";
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	int loginPersonId = person.getUuid();
	String userName = person.getUserName();
    String title="";
    if(sid>0){
    	title="编辑销售订单";
    }else{
    	title="新建销售订单";	
    }
%>
<!DOCTYPE HTML>
<html>
<head>
<title><%=title %></title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/system/mobile/js/tools.js"></script>
<style>
body{
	position:relative;
}
.shadow{
	position:absolute;
	top:0;
	left:0;
	right:0;
	bottom:0;
	background-color:rgba(0,0,0,0.6);
	z-index:2;
	display:none;
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
.mui-input-row input{
	padding-left:0px;
}
</style>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">

    <button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" id="backBtn">
	    <span class="mui-icon mui-icon-left-nav" ></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick='save()'>
	    保存
	</button>
	
	<h1 class="mui-title"><%=title %></h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
	     <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">基本信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>订单编号</label>
			<input type="text" placeholder="请输入订单编号" name="orderNo" id="orderNo">
		</div>
		<div class="mui-input-row">
			<label>所属客户</label>
			<input type="text" id="customerName"  name="customerName" style="font-size: 14px;"  placeholder="请选择所属客户"   onclick="selectCustomer();" >
		    <input type="hidden" id="customerId" name="customerId"/>
		</div>
		<div class="mui-input-row">
			<label>商机名称</label>
			<input type="text" id="chanceName"  name="chanceName" style="font-size: 14px;" placeholder="请选择商机"  onclick="selectChances();">
		    <input type="hidden" id="chanceId" name="chanceId"/>
		</div>
		<div class="mui-input-row">
			<label>下单日期</label>
			<input type="text" placeholder="请选择下单日期" name="orderTimeDesc" id="orderTimeDesc">
		</div>
	</div>
	
	
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">订单明细</span></label>
		</div>
		<div class="mui-input-row">
			<label>产品</label>
			<a href="javascript:void(0)" onclick="selectProducts()" style="margin-left:0px;width:65%;font-size: 14px;line-height: 40px">选择产品</a>
		</div>
		<iframe id="iframe1" src="" 
			  	style="display:none;;border: none;position: fixed;left: 10%;width:80%;
			  	top: 30%;height:40%;z-index: 10;" scrolling="yes"></iframe>
		<div id="productList">
			<!-- <div class="mui-input-row" style="height:inherit;">
			    <span class="mui-icon mui-icon-minus" style="position:absolute;right:0px"></span>
				<div class="pro_item" style="height:auto;">
					<label>产品编号/名称</label>
					<div>[SA39933]机械品制造</div>
				</div>
				<div class="pro_item" style="height:auto;">
					<label>类别/计量单位</label>
					<div>机械类/个</div>
				</div>
				<div class="pro_item" style="height:auto;">
					<label>数量</label>
					<div>
						<input  placeholder="请输入单价" type="text" />
					</div>
				</div>
				<div class="pro_item" style="height:auto;">
					<label>单价</label>
					<div>
						<input placeholder="请输入单价"  type="text" />
					</div>
				</div>
				<div class="pro_item" style="height:auto;">
					<label>合计</label>
					<div>
						<input readonly type="text" />
					</div>
				</div>
			</div> -->
		</div>
		<div class="mui-input-row">
			<label>产品合计：</label>
			<span id="product_total_amount" name="product_total_amount" style="margin-left:0px;width:65%;font-size: 14px;line-height: 40px">0</span>
		</div>
	</div>
	
	
	
	<div class="mui-input-group">
	     <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">交货信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>收货人</label>
			<select id="receiverName"  name="receiverName" style="margin-left:0px;width:65%;font-size: 14px;" placeholder="选择收货人">
	        </select>
	        <input type="hidden" id="receiverId" name="receiverId"/>
		</div>
		<div class="mui-input-row">
			<label>收货人电话</label>
			<input type="text" placeholder="请填写收货人电话" name="receiverTelephone"  id="receiverTelephone"/>
		</div>
		<div class="mui-input-row">
			<label>交货日期</label>
			<input type="text" placeholder="请选择交货日期" name="transactionsTimeDesc"  id="transactionsTimeDesc"/>
		</div>
	</div>
	
	
	<div class="mui-input-group">
	
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">补充信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>负责人</label>
			<input type="text" id="managePersonName" name="managePersonName" readonly placeholder="请选择负责人" />
			<input type="hidden" id="managePersonId" name="managePersonId"/>
		</div>
		<div class="mui-input-row">
			<label>订单管理员</label>
			<select id="orderApprovalId"  name="orderApprovalId" style="margin-left:0px;width:65%;font-size: 14px;">
	        </select>
		</div>
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="4" style="width: 550px" name="remark" id="remark" placeholder="请填写备注" ></textarea>
		</div>
		
	</div>
</form>	
</div>

<div class="shadow" id="shadow"></div>
<!--右上角弹出菜单-->

<script>
var sid = "<%=sid%>";
var cusName = "<%=customerName%>";
var type= "<%=type%>";
var loginPersonId = <%=loginPersonId%>;
var userName = "<%=userName%>";
var orderManagerIds = "<%=ORDER_MANAGER_IDS%>";
var orderManagerNames = "<%=ORDER_MANAGER_NAMES%>";

function doInit(){
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName);
	
	$(".shadow").on("click",function(){
		$(this).hide();
		$("#iframe1").hide();
		$("body").css("overflow","auto");
	});
	//selectCustomer();//初始化客户数据
	//selectReceiver();//初始化收货人数据
	getOrderManagerList(orderManagerIds);//获取订单管理员
	
	if(sid>0){
		getInfoBySid(sid);
	}
	
	orderTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			orderTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
	transactionsTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			transactionsTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
	
}

//选择所属客户
function selectCustomer(){
	//$("#iframe2")[0].contentWindow
	var iframe_t = document.getElementById('iframe1');
 	if(iframe_t.style.display =='block'){
		return;
	}
	$(iframe_t).slideDown(); 
	//iframe_t.style.display='block'; 
	$(".shadow").fadeIn();
	$("body").css("overflow","hidden");
	iframe_t.src='selectCustomers.jsp'; 
	
	//window.location = 'selectCustomers.jsp';

}

function selectCustomerCallBackFunc(sid,name){
	$("#customerName").val(" ");
	$("#customerId").val();
	 if(sid>0){
		$("#customerName").val(name);
		$("#customerId").val(sid);
	} 
	
}

//选择商机
function selectChances(){
	 var cusId = document.getElementById("customerId").value;
	if(cusId=="" || cusId=="null" || cusId==null|| cusId=="0"){
		alert("请先选择客户！");
	}else{
		
		var iframe_t = document.getElementById('iframe1');
	 	if(iframe_t.style.display =='block'){
			return;
		}
		$(iframe_t).slideDown(); 
		$(".shadow").fadeIn();
		$("body").css("overflow","hidden");
		iframe_t.src='selectChances.jsp?cusId='+cusId; 
	}
	 
	

}

function selectChancesCallBackFunc(sid,name){
	 if(sid>0){
		$("#chanceName").val(name);
		$("#chanceId").val(sid);
	
	} 
	
}

//选择商机
/* function selectChances(){
	var cusId = document.getElementById("customerId").value;
	if(cusId=="" || cusId=="null" || cusId==null|| cusId=="0"){
		alert("请先选择客户！");
	}else{
		var url = contextPath+'/crmChancesController/selectChances.action';
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{cusId:cusId},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					var prcs = json.rtData;
						var options = "";
						for ( var i = 0; i < prcs.length; i++) {
							options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].chanceName + "</option>";
						}
						$("#chanceName").html(options);
					return prcs;
				}else{
					alert("数据查询失败！");
				}
			}
		});
	}

} */

function operPage(){
	changeShadow();
	//显示隐藏有一个异步的过程 执行该行代码是隐藏尚未完成
	if(!$("#shadow").is(":hidden")){
		$("body").css("overflow","auto");
	}
}
function changeShadow(){//这里要处理下 看是谁返回的 不然都宣布显示了
	$("#shadow").fadeToggle();
	$("#iframe1").fadeToggle();
}

//选择产品
function selectProducts(){
	/* var iframe_t = document.getElementById('iframe1');
	iframe_t.src='selectProducts.jsp';
	iframe_t.style.display='block';  */
	
	$("body").css("overflow","hidden");
	var iframe_t = document.getElementById('iframe1');
 	if(iframe_t.style.display =='block'){
		return;
	}
 	
	$(iframe_t).slideDown(); 
	//iframe_t.style.display='block'; 
	$(".shadow").fadeIn();
	iframe_t.src='selectProducts.jsp'; 
	/* setTimeout(function(){
		iframe_t.style.display='block'; 
	},200); */
	 
} 

var max=0;
function selectProductCallBackFunc(data){
	if(data.length>0){
		var pro = data.split(",");
		 for(var i = 0 ; i<pro.length-1 ; i++){
			max++;
			var sid = pro[i];
			var dataPro = "";
			var url=contextPath+"/crmProductsController/getInfoById.action";
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
						dataPro["productsNumber"] = 1;
						addSaleFollowProcuctItem(dataPro ,1,max);
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
	    +"<span class=\"mui-icon mui-icon-minus\" style=\"position:absolute;right:0px\" onclick='removeProductItem(this);'></span>"
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
		+"<input  placeholder=\"请输入数量\"  id=\"num_"+max+"\" type=\"text\" value="+item_number_input_value+"  onchange='changeNumOrPrice(this);' />"
		+"</div>"
		+"</div>"
		+"<div class=\"pro_item\" style=\"height:auto;\">"
		+"<label>单价</label>"
		+"<div>"
		+"<input placeholder=\"请输入单价\"  id=\"price_"+max+"\"  type=\"text\" value="+item_price_input_value+"  onchange='changeNumOrPrice(this);' />"
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
//移除已经选择的产品
function removeProductItem(obj){
	$(obj).parent().remove();
	
	//重新计算总的合计
	getproduct_total_amount();
	
}
//改变单价或者数量
function changeNumOrPrice(obj){
   var num=$(obj).attr("id").split("_")[1];//num表示追加的第几个产品
   
   var price=$("#price_"+num).val();
   var number=$("#num_"+num).val();
   var  amount= price*number;//合计
   $("#amount_"+num).val(amount);
	
   
   //重新回去最大的那个产品合计
   getproduct_total_amount();
}

//获取所有商品的合计
function getproduct_total_amount(){	
	var itemList = $("#productList").find("input[class='item_total_amount']");
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


//选择收货人
function selectReceiver(){
	var cusId = document.getElementById("customerId").value;
	if(cusId=="" || cusId=="null" || cusId==null|| cusId=="0"){
		alert("请先选择客户！");
	}else{
		var url = contextPath+'/TeeCrmContactsController/selectReceiver.action';
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{cusId:cusId},
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					var prcs = json.rtData;
						var options = "";
						for ( var i = 0; i < prcs.length; i++) {
							options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].contactName + "</option>";
						}
						$("#receiverName").html(options);
					return prcs;
				}else{
					alert("数据查询失败！");
				}
			}
		});
	}

}


//获取订单管理员
function getOrderManagerList(orderManagerIds){
	var params="";
	var url =   contextPath + "/sysPara/getSysParaList.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{paraNames:orderManagerIds},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var prcs = json.rtData;
				if(prcs.length > 0){
					params =prcs[0].paraValue;
					if(params != "" ){
						var personId="";
						var personName="";
						var url =   contextPath + "/personManager/getPersonNameAndUuidByUuids.action";
						mui.ajax(url,{
							type:"post",
							dataType:"html",
							data:{uuid:params},
							timeout:10000,
							success:function(json){
							   json = eval("("+json+")");
								if(json.rtState){
									personId=json.rtData.sid.split(",");
									personName =json.rtData.userName.split(",");
									var html ="";
									for(var i=0;i<personId.length;i++){
										 html +="<option value='"+personId[i]+"'>"+personName[i]+"</option>";
									}
									$("#orderApprovalId").append(html);
								}else{
									alert(json.rtMsg);
								}
							}
						});

					}
				}
			}else{
				alert(json.rtMsg);
			}
		}
	});
	
}

//根据主键获取详情
function getInfoBySid(sid){
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
				$("#customerName").val(data.customerId);
				$("#introduceName").val(data.introduceId);
		
				
			}else{
				alert("查询失败！");
			}
		}
	});
	
	getProductList(sid);
}


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


//验证
function check(){
	var customerId=$("#customerId").val();

	if($("#orderNo").val()=="" || $("#orderNo").val()=="null" || $("#orderNo").val()==null){
		alert("请填写订单编号！");
		return false;
	}
	if(customerId==""||customerId==null||customerId=="null"||customerId=="0"){
		alert("请选择所属客户！");
		return false;	
	}
	if($("#orderTimeDesc").val()=="" || $("#orderTimeDesc").val()=="null" || $("#orderTimeDesc").val()==null){
		alert("请选择下单日期！");
		return false;
	}
 	if($("#productList").html()=="" || $("#productList").html()=="null" || $("#productList").html()==null){
			alert("请选择产品！");
		return false;
	}   
	if($("#managePersonId").val()=="" || $("#managePersonId").val()=="null" || $("#managePersonId").val()==null){
		alert("请选择负责人！");
		return false;
	}
	
	return true;
}




//保存/提交
function save(){
	if(check()){
		var itemList = getProductItem();//产品明细
		var str = tools.jsonArray2String(itemList);
		var url=contextPath+"/TeeCrmOrderController/addOrUpdate.action";
			var param=formToJson("#form1");
			param['sid']=sid;
			param["isPhone"]=1;
			param["productsList"] = str;
			mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					if(sid>0){
						window.location.href=contextPath+"/system/mobile/phone/crm/orders/ordersInfo.jsp?sid="+sid+"&customerName="+cusName+"&type="+type;
					}else{
						window.location.href=contextPath+"/system/mobile/phone/crm/orders/index.jsp?type="+type;
					}
				}
			}
		}); 	 
		
	}
}



/**
 *获取产品明细列表转JSON数组
 */
function getProductItem(){
	var itemList = $("#productList").find("div[class=mui-input-row]");
	//alert(itemList.length);
	var productItem = new Array();
	for(var i =0; i <itemList.length ; i++){
		var itemTemp = itemList[i];
		//产品编号
		var productsNo =$(itemTemp).find("div[class=pro_item]:eq(0)").find("div").html().split("/")[0];
		//产品名称
		var productsName = $(itemTemp).find("div[class=pro_item]:eq(0)").find("div").html().split("/")[1];
		//产品id
		var productsId = $(itemTemp).find("input:eq(0)").val();
		//计量单位
		var units = $(itemTemp).find("input:eq(1)").val();
		//产品类型
		var productsModel = $(itemTemp).find("div[class=pro_item]:eq(1)").find("div").html().split("/")[0];
		//价格
		var price =  $(itemTemp).find("div[class=pro_item]:eq(3)").find("div").find("input").val();
		//数量
		var productsNumber=$(itemTemp).find("div[class=pro_item]:eq(2)").find("div").find("input").val();
		//总价格
		var totalAmount = $(itemTemp).find("div[class=pro_item]:eq(4)").find("div").find("input").val();
		
		
		var itemp = {productsNo : productsNo , productsName: productsName , productsModel: productsModel,
				units:units,productsNumber:productsNumber,price:price,productsId:productsId, totalAmount:totalAmount};
		productItem.push(itemp);
	}
	return productItem;
}

mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		if(sid>0){
			history.go(-1);
		}else{
			window.location.href=contextPath+"/system/mobile/phone/crm/orders/index.jsp?type="+type;
		}
	});
	
	//选择负责人
	managePersonName.addEventListener('tap', function() {
		selectSingleUser("managePersonId","managePersonName");
	}, false);
	
/* 	//选择商机
	chanceName.addEventListener('tap', function() {
		selectChances();
	}, false); */
	
	//选择收货人
	receiverName.addEventListener('tap', function() {
		selectReceiver();
	}, false);
	

});


</script>

</body>
</html>