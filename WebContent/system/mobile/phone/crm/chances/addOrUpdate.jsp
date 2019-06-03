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
		<!-- <div id="middlePopover" class="mui-popover">
				<ul class="mui-table-view" id="aaa">
					
				</ul>
		</div> -->
<form id="form1" name="form1">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属客户</label>
		</div>
		<div class="mui-input-row">
		    <input type="text" id="customerName" name="customerName"  placeholder="请选择所属客户" onclick="selectCustomer()"/>
			<input type="hidden" id="customerId" name="customerId"/>
			 <iframe id="iframe1" src="" 
		  	style="display:none;border: none;position: fixed;left: 10%;width:80%;
		  	top: 30%;height:40%;z-index: 10;" scrolling="yes"></iframe>
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
            <input type="text" placeholder="请填写商机名称" name="chanceName" id="chanceName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>预计成交日期</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="请选择预计成交日期" name="forcastTimeDesc"  id="forcastTimeDesc" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>金额（元）</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="请填写金额" name="forcastCost"  id="forcastCost"/>
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
		  <textarea rows="6" style="width: 550px" name="remark" id="remark" placeholder="请填写备注" ></textarea>
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

	//初始化客户数据
	//selectCustomer();
	
	
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

//选择所属客户
function selectCustomer(){
	var url = contextPath+'/TeeCrmCustomerController/selectAllCustomer.action';
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var prcs = json.rtData;
					var options = "";
					for ( var i = 0; i < prcs.length; i++) {
						options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].customerName + "</option>";
					}
					$("#customerName").append(options);
				return prcs;
			}else{
				alert("数据查询失败！");
			}
		}
	});

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
				
				//$("#customerName").val(data.customerId);
				
			}else{
				alert("查询失败！");
			}
		}
	});
}


//验证
function check(){
	var customerId = $("#customerId").val();
	var forcastCost = $("#forcastCost").val();
	if(customerId=="" || customerId=="null" || customerId==null || customerId=="0" ){
		alert("请选择所属客户！");
		return false;
	}
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
						window.location.href=contextPath+"/system/mobile/phone/crm/chances/chancesInfo.jsp?sid="+sid+"&customerName="+cusName+"&type="+type;
					}else{
						window.location.href=contextPath+"/system/mobile/phone/crm/chances/index.jsp?type="+type;
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
			window.location.href=contextPath+"/system/mobile/phone/crm/chances/index.jsp?type="+type;
		}
	});
	
	//选择负责人
	managePersonName.addEventListener('tap', function() {
		selectSingleUser("managePersonId","managePersonName");
	}, false);

});



//选择所属客户
function selectCustomer(){
	var iframe_t = document.getElementById('iframe1');
 	if(iframe_t.style.display =='block'){
		return;
	}
	$(iframe_t).slideDown(); 
	
	$(".shadow").fadeIn();
	$("body").css("overflow","hidden");
	iframe_t.src='../orders/selectCustomers.jsp'; 
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

function selectCustomerCallBackFunc(sid,name){
	$("#customerName").val(name);
	$("#customerId").val(sid);
}

</script>

</body>
</html>