<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);//线索主键
   int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
%>
<!DOCTYPE HTML>
<html>
<head>
<title>线索转换</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn" onclick="history.go(-1)"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="commit()"></span>
	
	<h1 class="mui-title">线索转换</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<div class="title1" style="line-height:25px;padding-top: 5px;padding-left: 10px;padding-right: 5px;">
    <input type="checkbox" id="transCustomer" name ="transCustomer" value="1" checked="checked" disabled="disabled"/>
    &nbsp;&nbsp;<span style="font-size: 14px;line-height: 14px;color: #0050aa;font-family: 'MicroSoft YaHei';">转换为客户</span>
    <span style="float:right;margin-right:20px;font-size:14px;color:#0050aa;cursor:pointer;" class="open1 open fr" id="open1">收起</span>
</div>
<div  class="table1 open">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>客户名称</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="客户名称" name="companyName" id="companyName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司地址</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="公司地址" name="address" id="address">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>电话</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="电话" name="telephone" id="telephone">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>网址</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="网址" name="url" id="url">
		</div>
	</div>
</div>
<div class="title2" style="line-height:25px;padding-top: 5px;padding-left: 10px;padding-right: 5px;">
    <input id='transContact' type="checkbox" name ="transContact"/>
    &nbsp;&nbsp;<span style="font-size: 14px;line-height: 14px;color: #0050aa;font-family: 'MicroSoft YaHei';">同时转换为联系人</span>
     <span style="float:right;margin-right:20px;font-size:14px;color:#0050aa;cursor:pointer;" class="open2 fr" id="open2">展开</span>
</div>
<div class="table2" style="display: none;">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>姓名</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="姓名" name="name" id="name">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>部门</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="部门" name="department" id="department">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>职务</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="职务" name="duties" id="duties">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>手机</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="手机" name="mobilePhone" id="mobilePhone">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>邮件</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="邮件" name="email" id="email">
		</div>
	</div>
</div>
<div class="title3" style="line-height:25px;padding-top: 5px;padding-left: 10px;padding-right: 5px;">
    <input id='transChances' type="checkbox" name ="transChances"/>
    &nbsp;&nbsp;<span style="font-size: 14px;line-height: 14px;color: #0050aa;font-family: 'MicroSoft YaHei';">同时转换为商机</span>
     <span style="float:right;margin-right:20px;font-size:14px;color:#0050aa;cursor:pointer;" class="open3 fr" id="open3">展开</span>
</div>
<div class="table3" style="display: none;">
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
		<div class="mui-input-row">
			<input type="text" id='forcastTimeDesc' placeholder="预计成交日期" name='forcastTimeDesc' class="Wdate BigInput" style="width: 160px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>金额</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="金额" name="forcastCost" id="forcastCost">
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
</div>
</form>
</div>

<script>
var sid="<%=sid%>"; 
var type="<%=type%>"; 
function doInit(){
	getInfoById(sid);
	
	//开始时间
	forcastTimeDesc.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
			forcastTimeDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
}

//获取详情
function getInfoById(sid){
	var url=contextPath+"/TeeCrmClueController/getInfoBySid.action";
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
				$("#chanceName").val(data.name);
			}else{
				alert("查询错误！");
			}
		}
	});
}
//验证
function checkForm(){
	var companyName = $("#companyName").val();
	if(companyName==""||companyName==null){
		alert("请输入客户名称！");
		return false;	
	}
	var isChecked = $("#transContact").prop("checked");
	if(isChecked){
		$("#transContact").val("1");
		var name = $("#name").val();
		if(name==""||name==null){
			alert("请输入联系人姓名！");
			return false;	
		}
	}else{
		$("#transContact").val("0");
	}
	//验证转换为商机必填
	var isChecked2 = $("#transChances").prop("checked");
	if(isChecked2){
		$("#transChances").val("1");
		var chanceName = $("#chanceName").val();
		var forcastTimeDesc = $("#forcastTimeDesc").val();
		var forcastCost = $("#forcastCost").val();
		var managePersonId = $("#managePersonId").val();
		if(chanceName==""||chanceName==null){
			alert("请输入商机名称！");
			return false;	
		}
		if(forcastTimeDesc==""||forcastTimeDesc==null){
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
		if(managePersonId==""||managePersonId==null){
			alert("请选择负责人！");
			return false;	
		}
	}else{
		$("#transChances").val("0");
	}
	return true;
}


//保存
function commit() {
	if (checkForm()) {
		var url=contextPath+"/TeeCrmClueController/transfromClue.action";
		var param = formToJson("#form1");
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if (json.rtState) {
					alert("操作成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/clue/clueInfo.jsp?sid="+sid+"&type="+type;
				}else{
					alert("操作失败！");
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
			window.location.href=contextPath+"/system/mobile/phone/crm/clue/index.jsp?type="+type;
		}
	});
	
	//选择负责人
	managePersonName.addEventListener('tap', function() {
		selectSingleUser("managePersonId","managePersonName");
	}, false);
	
	open1.addEventListener('tap', function() {
		if($(this).hasClass("open")){
			$(this).removeClass("open");
			$(this).text("展开");
			$(".table1").hide();
		}else{
			$(this).addClass("open");
			$(this).text("收起");
			$(".table1").show();
		}
	}, false);
	
	open2.addEventListener('tap', function() {
		if($(this).hasClass("open")){
			$(this).removeClass("open");
			$(this).text("展开");
			$(".table2").hide();
		}else{
			$(this).addClass("open");
			$(this).text("收起");
			$(".table2").show();
		}
	}, false);
	
	open3.addEventListener('tap', function() {
		if($(this).hasClass("open")){
			$(this).removeClass("open");
			$(this).text("展开");
			$(".table3").hide();
		}else{
			$(this).addClass("open");
			$(this).text("收起");
			$(".table3").show();
		}
	}, false);
	
	transContact.addEventListener('tap', function() {
		$(this).siblings(".fr").addClass("open");
		$(this).siblings(".fr").text("收起");
		$(".table2").show();
	}, false);
	
	transChances.addEventListener('tap', function() {
		$(this).siblings(".fr").addClass("open");
		$(this).siblings(".fr").text("收起");
		$(".table3").show();
	}, false);
	
});

</script>

</body>
</html>