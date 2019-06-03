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
<title>联系人详情</title>
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
		window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerContacts/contactsList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
	}); 

}


var managerPerName = '';
var managerPerId = "";
//根据主键获取详情
function getInfoByUuid(sid){
	var url=contextPath+"/TeeCrmContactsController/getContactsInfoBySid.action";
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
				contactsStatusDesc = data.contactsStatusDesc;
				
				var  attachments = data.attachmodels;
				  if(attachments.length > 0){
					  $.each(attachments, function(index, item){  
						  $("#attachList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;"+"</a><div>");
					  });
				  }
				  
				renderOptBtns(contactsStatusDesc);
				
			}
		}
	});
}


//渲染操作
function renderOptBtns(data){
	var str="";
	$("#topPopover1").empty();
 	if(managerPerId==loginPersonId){
 		if(data == "正常"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
		   '<li class="mui-table-view-cell" onclick="edit();">编辑</li>'+
		   '<li class="mui-table-view-cell" onclick="changeCharge();">更换负责人</li>'+
		   '<li class="mui-table-view-cell" onclick="cancel();">作废</li>'+
		   ' </ul>';
 		}else if(data == "已作废"){
			$("#topTop").show();
			str='<ul class="mui-table-view">'+
			'<li class="mui-table-view-cell" onclick="deleteById();">删除</li>'+
			'<li class="mui-table-view-cell" onclick="recovery();">恢复</li>'+
			' </ul>';
		    }
		}else{
			$("#topTop").hide();
		}
 	$("#topPopover1").append(str);

}

//编辑
function edit(){
	window.location = contextPath+"/system/mobile/phone/crm/customer/customerContacts/addOrUpdate.jsp?customerId="+customerId+"&customerName="+customerName+"&type="+type+"&sid="+sid;
}

//更换负责人
function changeCharge(){
	window.location = contextPath+'/system/mobile/phone/crm/contacts/changeCharge.jsp?sid='+sid+'&managerPerId='+managerPerId+'&managerPerName='+managerPerName+"&customerName="+customerName;
}


/**
 * 删除
 */
function deleteById(){
	if(window.confirm("确定删除此联系人？")){
		var url = contextPath+ "/TeeCrmContactsController/delContacts.action";
		var param={sids:sid};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("删除成功！");
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerContacts/contactsList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
				}else {
					alert("删除失败！");
				}
			}
		});	
	}
}

//恢复
function recovery(){
	if(window.confirm("确定恢复到作废之前的状态？")){
		var url = contextPath+ "/TeeCrmContactsController/recovery.action";
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
function cancel(){
	if(window.confirm("确定作废此联系人？")){
		 var url=contextPath+"/TeeCrmContactsController/cancel.action";
		var param={sid:sid,contactsStatus:1};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("作废成功！");
					window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerContacts/contactsList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
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
	    <h1 class="mui-title">联系人详情</h1>
		
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属客户</label>
		</div>
		<div class="app-row-content" id="customerName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>名片</label>
		</div>
		<div class="app-row-content" id="attachList">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>姓名</label>
		</div>
		<div class="app-row-content" id="contactName">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属部门</label>
		</div>
		<div class="app-row-content" id="department" >
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>职务</label>
		</div>
		<div class="app-row-content" id="duties">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>关键决策人</label>
		</div>
		<div class="app-row-content" id='keyPersonDesc'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>电话</label>
		</div>
		<div class="app-row-content" id='telephone'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>手机</label>
		</div>
		<div class="app-row-content" id='mobilePhone'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>邮箱</label>
		</div>
		<div class="app-row-content" id='email'>
		
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>地址</label>
		</div>
		<div class="app-row-content" id='address'>
		
		</div>
	</div>
	<div class="mui-input-group" id="approverDiv">
		<div class="mui-input-row">
			<label>出生日期</label>
		</div>
		<div class="app-row-content" id='birthdayDesc'>
		
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>性别</label>
		</div>
		<div class="app-row-content" id='genderDesc'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司名称</label>
		</div>
		<div class="app-row-content" id='companyName'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>介绍人</label>
		</div>
		<div class="app-row-content" id='introduceName'>
			
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
			<label>负责人</label>
		</div>
		<div class="app-row-content" id='managePersonName'>
			
		</div>
			
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>状态</label>
		</div>
		<div class="app-row-content" id='contactsStatusDesc'>
			
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
</div>

    <div id="topPopover1" class="mui-popover">
	</div>

<br/>
</body>
</html>