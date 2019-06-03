<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	int loginPersonId = person.getUuid();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>商机详情</title>
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

</style>
</head>

<script type="text/javascript">
var sid = "<%=sid%>";
var customerName = "<%=customerName%>";
var loginPersonId = <%=loginPersonId%>;
var customerId = "<%=customerId%>";
var type = <%=type%>;
//初始化方法
function doInit(){
   
	if(sid>0){
		getInfoByUuid(sid);
	}

	//返回
 	backBtn.addEventListener("tap",function(){
		window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerChances/chancesList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
	}); 


}


var managerPerName = '';
var managerPerId = "";
//根据主键获取详情
function getInfoByUuid(sid){
	var url=contextPath+"/crmChancesController/getInfoBySid.action";
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
				//获取当前负责人
				managerPerName = data.managePersonName;
				managerPerId = data.managePersonId;
				chanceStatus = data.chanceStatusDesc;
				  
				renderOptBtns(chanceStatus);
				
			}
		}
	});
}


//渲染操作
function renderOptBtns(data){
	var str="";
	$("#topPopover1").empty();
 	if(managerPerId==loginPersonId){
 		if(data == "未处理"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
		   '<li class="mui-table-view-cell" onclick="lostOrder();">输单</li>'+
		   '<li class="mui-table-view-cell" onclick="winOrder();">赢单</li>'+
		   '<li class="mui-table-view-cell" onclick="invalid();">无效</li>'+
		   '<li class="mui-table-view-cell" onclick="edit();">编辑</li>'+
		   '<li class="mui-table-view-cell" onclick="deleteById();">删除</li>'+
		   ' </ul>';
 		}else if(data =="无效"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
			'<li class="mui-table-view-cell" onclick="deleteById();">删除</li>'+
			' </ul>';
		}else{
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
			'<li class="mui-table-view-cell" onclick="deleteById();">删除</li>'+
			' </ul>';
		    }
		}else{
			$("#topTop").hide();
		}
 	$("#topPopover1").append(str);

}

//输单
function lostOrder(){
	if(window.confirm("确定将此商机状态置为输单？")){
		var url = contextPath+ "/crmChancesController/lostOrder.action";
		var param={sid:sid,chanceStatus:3};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("操作成功！");
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerChances/chancesList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
				}else {
					alert("操作失败！");
				}
			}
		});	
	}
}

//赢单
function winOrder(){
	if(window.confirm("确定将此商机状态置为赢单？")){
		var url = contextPath+ "/crmChancesController/winOrder.action";
		var param={sid:sid,chanceStatus:2};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("操作成功！");
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerChances/chancesList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
				}else {
					alert("操作失败！");
				}
			}
		});	
	}
}

//无效
function invalid(){
	if(window.confirm("确定将此商机的状态置为无效？")){
		var url = contextPath+ "/crmChancesController/invalid.action";
		var param={sid:sid,chanceStatus:4};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("操作成功！");
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerChances/chancesList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
				}else {
					alert("操作失败！");
				}
			}
		});	
	}
}

//编辑
function edit(){
	window.location = contextPath+"/system/mobile/phone/crm/customer/customerChances/addOrUpdate.jsp?customerId="+customerId+"&customerName="+customerName+"&type="+type+"&sid="+sid;
}


/**
 * 删除
 */
function deleteById(){
	if(window.confirm("确定删除此商机？删除商机将删除此商机下关联的订单，删除后不可恢复！")){
		var url = contextPath+ "/crmChancesController/delById.action";
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
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerChances/chancesList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
				}else {
					alert("删除失败！");
				}
			}
		});	
	}
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
	    <h1 class="mui-title">商机详情</h1>
		
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
			<label>商机名称</label>
		</div>
		<div class="app-row-content" id="chanceName">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>预计成交日期</label>
		</div>
		<div class="app-row-content" id="forcastTimeDesc" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>金额（元）</label>
		</div>
		<div class="app-row-content" id="forcastCost">
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
			<label>备注</label>
		</div>
		<div class="app-row-content" id='remark'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>状态</label>
		</div>
		<div class="app-row-content" id='chanceStatusDesc'>
			
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
</div>

    <div id="topPopover1" class="mui-popover">
	</div>

<br/>
</body>
</html>