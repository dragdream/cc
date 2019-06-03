<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>客户管理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
	.mui-media-body{
		line-height:42px;
	}
</style>
<script type="text/javascript">
//初始化方法
function  doInit(){
	var url=contextPath+"/teeMenuGroup/getPrivSysMenu.action";
	mui.ajax(url,{
	type:"post",
	dataType:"html",
	data:null,
	timeout:10000,
	success:function(json){
		json = eval("("+json+")");
		if(json.rtState){
			var data=json.rtData;
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					if(data[i].menuCode=="/system/subsys/crm/core/customer/cutomerIndex.jsp"){//客户
						$("#customer").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/clue/index.jsp"){//线索
						$("#clue").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/linkman/index.jsp"){//联系人
						$("#contacts").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/chances/index.jsp"){//商机
						$("#chances").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/order/index.jsp"){//订单
						$("#orders").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/returnOrder/index.jsp"){//退货单
						$("#returnOrders").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/contracts/index.jsp"){//合同
						$("#contract").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/payback/index.jsp"){//回款
						$("#payback").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/drawback/index.jsp"){//退款
						$("#drawback").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/invoice/index.jsp"){//开票
						$("#invoice").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/visit/index.jsp"){//拜访
						$("#visit").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/order/indexApprove.jsp"){//订单审批
						$("#ordersApprove").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/returnOrder/indexApprove.jsp"){//退货审批
						$("#returnOrdersApprove").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/payback/indexApprove.jsp"){//回款审批
						$("#paybackApprove").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/drawback/indexApprove.jsp"){//退款审批
						$("#drawbackApprove").show();
					}else if(data[i].menuCode=="/system/subsys/crm/core/invoice/indexApprove.jsp"){//开票审批
						$("#invoiceApprove").show();
					}
					
				}
			}
		}
	}
});	
	
	
}


</script>
</head>
<body onload="doInit();">
		<header class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" onclick="CloseWindow()"></span>
			<h1 class="mui-title">客户管理</h1>
		</header>
		<div class="mui-content">
			<div id="wrapper">
				<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media" id="customer" style="display: none;">
					<a href="customer/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_khgl.png">
						<div class="mui-media-body">
							客户
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="clue" style="display: none;">
					<a href="clue/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xl.png">
						<div class="mui-media-body">
							线索
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="contacts" style="display: none;">
					<a href="contacts/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_lxr.png">
						<div class="mui-media-body">
							联系人
						</div>
					</a>
				</li>
                <li class="mui-table-view-cell mui-media" id="chances" style="display: none;">
					<a href="chances/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_sj.png">
						<div class="mui-media-body">
							商机
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="orders" style="display: none;">
					<a href="orders/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_dd.png">
						<div class="mui-media-body">
							订单
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="returnOrders" style="display: none;">
					<a href="returnOrders/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_thd.png">
						<div class="mui-media-body">
							退货单
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="contract" style="display: none;">
					<a href="contract/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_ht.png">
						<div class="mui-media-body">
							合同
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="payback" style="display: none;">
					<a href="payback/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_hk.png">
						<div class="mui-media-body">
							回款
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="drawback" style="display: none;">
					<a href="drawback/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_tk.png">
						<div class="mui-media-body">
							退款
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="invoice" style="display: none;">
					<a href="invoice/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_kp.png">
						<div class="mui-media-body">
							开票
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="visit" style="display: none;">
					<a href="visit/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_bf.png">
						<div class="mui-media-body">
							拜访
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="ordersApprove" style="display: none;">
					<a href="orders/indexApprove.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_ddsp.png">
						<div class="mui-media-body">
							订单审批
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="returnOrdersApprove" style="display: none;">
					<a href="returnOrders/indexApprove.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_thsp.png">
						<div class="mui-media-body">
							退货审批
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="paybackApprove" style="display: none;">
					<a href="payback/indexApprove.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_hksp.png">
						<div class="mui-media-body">
							回款审批
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="drawbackApprove" style="display: none;">
					<a href="drawback/indexApprove.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_tksp.png">
						<div class="mui-media-body">
							退款审批
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="invoiceApprove" style="display: none;">
					<a href="invoice/indexApprove.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_kpsp.png">
						<div class="mui-media-body">
							开票审批
						</div>
					</a>
				</li>
			</ul>
			</div>
		</div>
</body>
</html>