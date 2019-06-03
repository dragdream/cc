<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
	int loginPersonId = person.getUuid();
%>

<!DOCTYPE HTML>
<html>
<head>
<title>客户</title>
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
var customerId = "<%=sid%>";
var customerName = "<%=customerName%>";
var loginPersonId = <%=loginPersonId%>;
var type = <%=type%>;

//初始化方法
function doInit(){
   
	renderCustomerField();
	if(customerId>0){
		getInfoByUuid(customerId);
	}

	//返回
 	backBtn.addEventListener("tap",function(){
		window.location = contextPath+'/system/mobile/phone/crm/customer/index.jsp?type='+type;
	}); 

}

//更换负责人
function changeCharge(){
	window.location = contextPath+'/system/mobile/phone/crm/customer/changeCharge.jsp?sid='+customerId+'&managerPerId='+managerPerId+'&managerPerName='+managerPerName;
}

//渲染操作
function renderOptBtns(data){
	var str;
	$("#topPopover1").empty();
 	if(managerPerId==loginPersonId){
		if(data == "已分配"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
		   '<li class="mui-table-view-cell" onclick="editCustomer();">编辑</li>'+
		   '<li class="mui-table-view-cell" onclick="changeCharge();">更换负责人</li>'+
		   '<li class="mui-table-view-cell" onclick="cancel('+<%=sid%>+');">作废</li>'+
		   ' </ul>';
		}else if(data == "已作废"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
			'<li class="mui-table-view-cell" onclick="deleteById('+customerId+');">删除</li>'+
			'<li class="mui-table-view-cell" onclick="recovery('+customerId+');">恢复</li>'+
			' </ul>';
		    }
		}else{
			$("#topTop").hide();
		}
 	$("#topPopover1").append(str);

}

//动态渲染自定义字段
function renderCustomerField(){
	$("#customTbody").html("");
	var url=contextPath+"/TeeCrmCustomerController/getListFieldByCustomer.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				for(var i=0;i<data.length;i++){
					var name="EXTRA_"+data[i].sid;
					if(data[i].filedType=="单行输入框"){
						$("#customTbody").append("<div class=\"mui-input-group\">"
								   +"<div class=\"mui-input-row\">"
								   +"<label>"+data[i].extendFiledName+"</label>"
								   +"</div>"
								   +"<div class=\"app-row-content\"  id='"+name+"'>"
								   +"</div>"
								   +"</div>");
					}else if(data[i].filedType=="多行输入框"){
						$("#customTbody").append("<div class=\"mui-input-group\">"
								   +"<div class=\"mui-input-row\">"
								   +"<label>"+data[i].extendFiledName+"</label>"
								   +"</div>"
								   +"<div class=\"app-row-content\"  id='"+name+"'>"
								   +"</div>"
								   +"</div>");
					}else if(data[i].filedType=="下拉列表"){
						/* var fieldCtrModel=data[i].fieldCtrModel;
						var j=tools.strToJson(fieldCtrModel); */
						if(data[i].codeType=="CRM系统编码"){
							$("#customTbody").append("<div class=\"mui-input-group\">"
									   +"<div class=\"mui-input-row\">"
									   +"<label>"+data[i].extendFiledName+"</label>"
									   +"</div>"
									   +"<div class=\"app-row-content\"  id='"+name+"'>"
									   +"</div>"
									   +"</div>");
							getCrmCodeByParentCodeNo(data[i].sysCode,name);
							//getSysCodeByParentCodeNo(j.value,name);
						}else if(data[i].codeType=="自定义选项"){
							//var values=j.value;
							var optionNames=data[i].optionName.split(",");
							var optionValues=data[i].optionValue.split(",");
							$("#customTbody").append("<div class=\"mui-input-group\">"
									   +"<div class=\"mui-input-row\">"
									   +"<label>"+data[i].extendFiledName+"</label>"
									   +"</div>"
									   +"<div class=\"app-row-content\" id='"+name+"'>"
									   +"</div>"
									   +"</div>");
							for(var j=0;j<optionNames.length;j++){
								$("#"+name).append("<option value="+optionValues[j]+">"+optionNames[j]+"</option>");
							}
							
						}
						
					}
					
				}
			}
		}
	});
}


var managerPerName = '';
var managerPerId = "";
//根据主键获取详情
function getInfoByUuid(customerId){
	var url=contextPath+"/TeeCrmCustomerController/getInfoBySid.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:customerId},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				bindJsonObj2Cntrl(data);
				//获取当前负责人
				managerPerName = data.managePersonName;
				managerPerId = data.managePersonId;
				customerStatus = data.customerStatus;
				renderOptBtns(customerStatus);
				
			}
		}
	});
}

//编辑
function editCustomer(){
	window.location = 'addOrUpdate.jsp?sid=<%=sid%>';
}


/**
 * 删除
 */
function deleteById(id){
	if(window.confirm("确定删除删除这个客户？删除客户将删除此客户下关联的数据，删除后不可恢复！")){
		var url = contextPath+ "/TeeCrmCustomerController/delCustomer.action";
		var param={sids:customerId};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("删除成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/customer/index.jsp?type="+type;
				}else {
					alert("删除失败！");
				}
			}
		});	
	}
}

//恢复
function recovery(sid){
	if(window.confirm("确定恢复到作废之前的状态？")){
		var url = contextPath+ "/TeeCrmCustomerController/recovery.action?sid="+sid;
		var param={sid:sid};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("已完成！");
					window.location.reload();
				}else {
					alert("恢复失败！");
				}
			}
		});	
	
	}
}


//作废
function cancel(sid){
	if(window.confirm("确定作废这个客户？")){
		var url=contextPath+"/TeeCrmCustomerController/cancel.action";
		var param={sid:sid,customerStatus:2};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("作废成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/customer/index.jsp?type="+type;
				}
			}
		});	
	}
	 
}

//线索列表
function  clueList(uuid){
	var url=contextPath+"/system/mobile/phone/crm/customer/customerClues/clueList.jsp?customerId="+uuid+"&type="+type;
	window.location.href=url;
}

//联系人列表
function contactsList(uuid){
	var url=contextPath+"/system/mobile/phone/crm/customer/customerContacts/contactsList.jsp?customerId="+uuid+"&type="+type+"&customerName="+customerName;
	window.location.href=url;
}

//商机列表
function chancesList(uuid){
	var url=contextPath+"/system/mobile/phone/crm/customer/customerChances/chancesList.jsp?customerId="+uuid+"&type="+type+"&customerName="+customerName;
	window.location.href=url;
}

//订单列表

function orderList(uuid){
	
}

//合同列表
function contractList(uuid){
	var url=contextPath+"/system/mobile/phone/crm/customer/crmContract/contractList.jsp?customerId="+uuid+"&type="+type+"&customerName="+customerName;
	window.location.href=url;
}

//回款列表
function paybackList(uuid){
	var url=contextPath+"/system/mobile/phone/crm/customer/crmPayback/paybackList.jsp?customerId="+uuid+"&type="+type+"&customerName="+customerName;
	window.location.href=url;
}

//回款列表
function drawbackList(uuid){
	var url=contextPath+"/system/mobile/phone/crm/customer/crmDrawback/drawbackList.jsp?customerId="+uuid+"&type="+type+"&customerName="+customerName;
	window.location.href=url;
}

//开票申请列表
function invoiceList(uuid){
	var url=contextPath+"/system/mobile/phone/crm/customer/crmInvoice/invoiceList.jsp?customerId="+uuid+"&type="+type+"&customerName="+customerName;
	window.location.href=url;
}


//拜访记录列表
function visitList(uuid){
	var url=contextPath+"/system/mobile/phone/crm/customer/customerVisit/visitList.jsp?customerId="+uuid+"&type="+type+"&customerName="+customerName;
	window.location.href=url;
}

</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" id="backBtn">
		    <span class="mui-icon mui-icon-left-nav" ></span>返回
		</button>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" id="moreOpt" ></a> 
	    <button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right"  href="#topPopover1" id="topTop" style="display: none;">
	        <span style="padding-right: 10px;"><a href="#topPopover1">操作</a></span>
	    </button> 
	    <h1 class="mui-title">客户详情</h1>
		
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户名称</label>
		</div>
		<div class="app-row-content" id="CUSTOMER_NAME">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户 编号</label>
		</div>
		<div class="app-row-content" id="CUSTOMER_NUM">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户级别</label>
		</div>
		<div class="app-row-content" id="customerType">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>来源</label>
		</div>
		<div class="app-row-content" id="customerSource" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属行业</label>
		</div>
		<div class="app-row-content" id="industryType">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户所属区域</label>
		</div>
		<div class="app-row-content" id='address'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>定位</label>
		</div>
		<div class="app-row-content" id='LOCATE_INFORMATION'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司规模</label>
		</div>
		<div class="app-row-content" id='companyScale'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户性质</label>
		</div>
		<div class="app-row-content" id='type'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>单位性质</label>
		</div>
		<div class="app-row-content" id='unitType'>
		
		</div>
	</div>
	<div class="mui-input-group" id="approverDiv" style="display: none;">
		<div class="mui-input-row">
			<label>公司地址</label>
		</div>
		<div class="app-row-content" id='COMPANY_ADDRESS'>
		
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>联系电话</label>
		</div>
		<div class="app-row-content" id='COMPANY_PHONE'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>邮编</label>
		</div>
		<div class="app-row-content" id='COMPANY_ZIPCODE'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司网址</label>
		</div>
		<div class="app-row-content" id='COMPANY_URL'>
			
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
			<label>共享人员</label>
		</div>
		<div class="app-row-content" id='sharePersonNames'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>状态</label>
		</div>
		<div class="app-row-content" id='customerStatus'>
			
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
		<div class="app-row-content" id='addTime'>
			
		</div>
			
	</div>
	<div id="customTbody">
	
	</div>

</div>

    <div id="topPopover1" class="mui-popover">
	
	</div>

	<div id="topPopover" class="mui-popover" >
		<ul class="mui-table-view">
		      <li class="mui-table-view-cell" onclick="clueList('<%=sid %>')" >线索</li>
		      <li class="mui-table-view-cell" onclick="contactsList('<%=sid%>')" >联系人</li>
		      <li class="mui-table-view-cell" onclick="chancesList('<%=sid %>')" >商机</li>
		      <li class="mui-table-view-cell" onclick="orderList('<%=sid %>')" >订单</li>
		      <li class="mui-table-view-cell" onclick="change('<%=sid %>')" id="changeLi" >退货单</li>
		      <li class="mui-table-view-cell" onclick="contractList('<%=sid %>')" >合同</li>
		      <li class="mui-table-view-cell" onclick="paybackList('<%=sid %>')">回款</li>
		      <li class="mui-table-view-cell" onclick="drawbackList('<%=sid %>')">退款</li>
		      <li class="mui-table-view-cell" onclick="invoiceList('<%=sid %>')" >开票信息</li>
		      <li class="mui-table-view-cell" onclick="visitList('<%=sid %>')" >拜访记录</li>
		  </ul>
	</div>
<br/><br/><br/>
</body>
</html>