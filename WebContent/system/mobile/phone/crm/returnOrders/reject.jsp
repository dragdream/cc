<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	int returnOrderStatus=TeeStringUtil.getInteger(request.getParameter("returnOrderStatus"),1);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>订单驳回</title>
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
	
	<h1 class="mui-title">退货驳回</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1">

	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>驳回原因</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="rejectReason" id="rejectReason" placeholder="驳回原因" ></textarea>
		</div>
	</div>
 
</form>	
</div>



<script>
var sid = "<%=sid%>";
var returnOrderStatus=<%=returnOrderStatus%>;
function doInit(){
	
	
}



//保存/提交
function save(){
	var url=contextPath+"/TeeCrmReturnOrderController/reject.action";
	var param=formToJson("#form1");
	param['sid']=sid;
	param['returnOrderStatus']=3;
	mui.ajax(url,{
	type:"post",
	dataType:"html",
	data:param,
	timeout:10000,
	success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				window.location.href=contextPath+"/system/mobile/phone/crm/returnOrders/indexApprove.jsp?returnOrderStatus="+returnOrderStatus;
			}
		}
		});
}


mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		
			history.go(-1);
		
	});
	
	
});


</script>

</body>
</html>