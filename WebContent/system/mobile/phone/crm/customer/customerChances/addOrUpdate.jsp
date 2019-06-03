<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	int loginPersonId = person.getUuid();
	String userName = person.getUserName();
    String title="";
    if(sid>0){
    	title="编辑商机";
    }else{
    	title="新建商机";	
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
			<!-- <a href="#middlePopover"  class="mui-action-menu mui-icon" style="line-height: 25px;font-size: 14px;padding-left: 10px;">选择所属客户</a> -->
			<!-- <input type="button" id="customerName" name="customerName" readonly value="选择所属客户"  onclick="selectCustomer()" style="width: 550px;line-height: 27px;text-align: left;"/> 
			 -->
			<input type="hidden" id="customerId" name="customerId"/>
			<input  id='customerName' name='customerName' type='text' readonly="readonly"/>
			<!-- <select id="customerName"  name="customerName" style="width: 550px;font-size: 14px;">
	       		<option value="0">选择所属客户</option>
	        </select> -->
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>商机名称</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="商机名称" name="chanceName" id="chanceName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>预计成交日期</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="预计成交日期" name="forcastTimeDesc"  id="forcastTimeDesc" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>金额（元）</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="金额" name="forcastCost"  id="forcastCost"/>
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
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="remark" id="remark" placeholder="备注" ></textarea>
		</div>
	</div>
</form>	
</div>


<!--右上角弹出菜单-->

<script>
var sid = "<%=sid%>";
var customerName ="<%=customerName%>";
var customerId = "<%=customerId%>";
var loginPersonId = <%=loginPersonId%>;
var userName = "<%=userName%>";
var type=<%=type%>;
function doInit(){
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	$("#customerName").val(customerName);
	$("#customerId").val(customerId);

	
	if(sid>0){
		getInfoBySid(sid);
	}
	
	
	//开始时间
	forcastTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			forcastTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
	
}


//根据主键获取详情
function getInfoBySid(sid){
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
				
			}else{
				alert("查询失败！");
			}
		}
	});
}


//验证
function check(){
	var forcastCost = $("#forcastCost").val();
	if($("#chanceName").val()=="" || $("#chanceName").val()=="null" || $("#chanceName").val()==null){
		alert("请输入商机名称！");
		return false;
	}
	if($("#forcastTimeDesc").val()=="" || $("#forcastTimeDesc").val()=="null" || $("#forcastTimeDesc").val()==null){
		alert("请选择预计成交日期！");
		return false;
	}

	//验证金额必须是正数
	var positive_integer = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
	if(forcastCost==""||forcastCost==null){
		alert("请填写金额！");
		return false;	
	}else if(!(positive_integer.test(forcastCost))){
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
			var url=contextPath+"/crmChancesController/addOrUpdate.action";
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
						window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerChances/chancesDetail.jsp?sid="+sid+"&customerName="+customerName+"&type="+type+"&customerId="+customerId;
					}else{
						window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerChances/chancesList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
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
			window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerChances/chancesList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
		}
	});
	
	//选择负责人
	managePersonName.addEventListener('tap', function() {
		selectSingleUser("managePersonId","managePersonName");
	}, false);

});


</script>

</body>
</html>