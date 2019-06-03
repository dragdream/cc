<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   String managerPerName=TeeStringUtil.getString(request.getParameter("managerPerName"),"");//负责人名称
   int managerPerId=TeeStringUtil.getInteger(request.getParameter("managerPerId"), 0);//负责人主键
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);//客户主键
%>
<!DOCTYPE HTML>
<html>
<head>
<title>更换负责人</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn" onclick="history.go(-1)"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="commit()"></span>
	
	<h1 class="mui-title">更换负责人</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>负责人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id="managerPerName" name="managerPerName" readonly placeholder="请选择负责人" />
			<input type="hidden" id="managerPerId" name="managerPerId"/>
		</div>
	</div>
</form>
</div>

<script>
var managerPersonId="<%=managerPerId%>"; 
var managerPersonName="<%=managerPerName%>"; 
var sid="<%=sid%>"; 
//当前任务 主键  当新增任务的时候  为0
function doInit(){
	//更换负责人前获取当前负责人
	setValue();

}

//更换负责人前获取当前负责人
function setValue(){
	$("#managerPerName").val(managerPersonName);
	$("#managerPerId").val(managerPersonId);
}


//验证
function check(){
	var managerPerId=$("#managerPerId").val();

	if(managerPerId==""||managerPerId==null){
		alert("请选择负责人！");
		return false;	
	}
	return true;
}


//保存
function commit() {
	if (check()) {
		var url=contextPath+"/TeeCrmCustomerController/changeManage.action?sid="+sid;
		var param = formToJson("#form1");
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if (json.rtState) {
					alert("更换负责人成功！");
					window.location.href = "<%=contextPath%>/system/mobile/phone/crm/customer/customerInfo.jsp?sid="+sid;
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
			window.location.href=contextPath+"/system/mobile/phone/crm/customer/index.jsp?type=1";
		}
	});
	
	managerPerName.addEventListener("tap", function() {
		selectSingleUser("managerPerId","managerPerName");
	}, false);
	
});

</script>


</body>
</html>