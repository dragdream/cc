<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	int loginPersonId = person.getUuid();
	String userName = person.getUserName();
    String title="";
    if(sid>0){
    	title="编辑合同";
    }else{
    	title="新建合同";	
    }
%>
<!DOCTYPE HTML>
<html>
<head>
<title><%=title %></title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#middlePopover {
	position: fixed;
	top: 100px;
	right: 200px;
	width: 200px;
}
#middlePopover .mui-popover-arrow {
	left: auto;
	right: 100px;
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
			<label>所属客户</label>
		</div>
		<div class="mui-input-row">
			
			<input type="hidden" id="customerId" name="customerId"/>
			<input type="text" id="customerName" name="customerName" placeholder="请选择所属客户" onclick="selectCustomer();"/>
			<iframe id="iframe1" src="" 
			  	style="display:none;;border: none;position: fixed;left: 10%;width:80%;
			  	top: 30%;height:40%;z-index: 10;" scrolling="yes"></iframe>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>销售订单编号</label>
		</div>
		<div class="mui-input-row">
            <input type="hidden" id="orderId" name="orderId"/>
			<select id="orderNo"  name="orderNo" style="width: 550px;font-size: 14px;">
	       		<option value="0">选择销售订单</option>
	        </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>合同编号</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="合同编号" name="contractsNo" id="contractsNo">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>合同标题</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="合同标题" name="contractsTitle" id="contractsTitle">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开始日期</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="开始日期" name="contractsStartTimeDesc"  id="contractsStartTimeDesc"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>结束日期</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="结束日期" name="contractsEndTimeDesc"  id="contractsEndTimeDesc"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>合同金额（元）</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="合同金额" name="contractsAmount"  id="contractsAmount"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id="managePersonName" name="managePersonName" readonly placeholder="请选择负责人" />
			<input type="hidden" id="managePersonId" name="managePersonId"/>
		</div>
	</div>
 
</form>	
</div>


<!--右上角弹出菜单-->

<script>
var sid = "<%=sid%>";
var cusName = "<%=customerName%>";
var type= "<%=type%>";
var loginPersonId = <%=loginPersonId%>;
var userName = "<%=userName%>";
function doInit(){
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	
	//selectCustomer();//初始化客户数据
	
	if(sid>0){
		getInfoBySid(sid);
	}
	
	contractsStartTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			contractsStartTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
	contractsEndTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			contractsEndTimeDesc.value = rs.text;	
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
	iframe_t.src='../orders/selectCustomers.jsp'; 
	
	//window.location = 'selectCustomers.jsp';

}

//选择销售订单
function selectOrders(){
	var cusId = document.getElementById("customerId").value;
	if(cusId=="" || cusId=="null" || cusId==null|| cusId=="0"){
		alert("请先选择客户！");
	}else{
		var url = contextPath+'/TeeCrmOrderController/selectOrders.action';
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:{cusId:cusId},
			timeout:10000,
			async:false,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					var prcs = json.rtData;
						var options = "";
						for ( var i = 0; i < prcs.length; i++) {
							options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].orderNo + "</option>";
						}
						$("#orderNo").html(options);
					return prcs;
				}else{
					alert("暂无订单！");
				}
			}
		});
	}

}


//根据主键获取详情
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmContractsController/getInfoBySid.action";
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
				selectOrders();
				$("#orderNo").val(data.orderId);
		
				
			}else{
				alert("查询失败！");
			}
		}
	});
}


//验证
function check(){
	var customerId=$("#customerId").val();
	var orderId=$("#orderNo").val();
	var amount = $("#contractsAmount").val();
	if(customerId==""||customerId==null||customerId=="null"||customerId=="0"){
		alert("请选择所属客户！");
		return false;	
	}

	if(orderId=="" || orderId=="null" || orderId==null||orderId==0){
		alert("请选择销售订单！");
		return false;
	}
	if($("#contractsNo").val()=="" || $("#contractsNo").val()=="null" || $("#contractsNo").val()==null){
		alert("请填写合同编号！");
		return false;
	}
	if($("#contractsTitle").val()=="" || $("#contractsTitle").val()=="null" || $("#contractsTitle").val()==null){
		alert("请填写合同标题！");
		return false;
	}
	//验证金额必须是正数
	var positive_integer = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
	if(amount==""||amount==null){
		alert("请填写合同金额！");
		return false;	
	}else if(!(positive_integer.test(amount))){
		alert("请输入正数！");
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
		var orderId = $("#orderNo").val();
		$("#orderId").val(orderId);
		var url=contextPath+"/TeeCrmContractsController/addOrUpdate.action";
			var param=formToJson("#form1");
			param['sid']=sid;
			param["isPhone"]=1;
			mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					if(sid>0){
						window.location.href=contextPath+"/system/mobile/phone/crm/contract/contractInfo.jsp?sid="+sid+"&customerName="+cusName+"&type="+type;
					}else{
						window.location.href=contextPath+"/system/mobile/phone/crm/contract/index.jsp?type="+type;
					}
				}
			}
		});	
		
	}
}

mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		if(sid>0){
			history.go(-1);
		}else{
			window.location.href=contextPath+"/system/mobile/phone/crm/contract/index.jsp?type="+type;
		}
	});
	
	//选择负责人
	managePersonName.addEventListener('tap', function() {
		selectSingleUser("managePersonId","managePersonName");
	}, false);
	
	//选择销售订单
 	orderNo.addEventListener('tap', function() {
 		selectOrders();
 	}, false);
	
});

function selectCustomerCallBackFunc(sid,name){
	$("#customerName").val(" ");
	$("#customerId").val();
	 if(sid>0){
		$("#customerName").val(name);
		$("#customerId").val(sid);
	} 
	
}

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
</script>

</body>
</html>