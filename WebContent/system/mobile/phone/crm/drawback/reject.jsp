<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	int drawbackStatus=TeeStringUtil.getInteger(request.getParameter("drawbackStatus"),1);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>回款驳回</title>
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
	
	<h1 class="mui-title">退款驳回</h1>
	
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


<!--右上角弹出菜单-->

<script>
var sid = "<%=sid%>";
var drawbackStatus=<%=drawbackStatus%>;
function doInit(){
	
	if(sid>0){
		getInfoBySid(sid);
	}
	
}

//根据主键获取详情
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmDrawbackController/getInfoBySid.action";
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


//保存/提交
function save(){
	var url=contextPath+"/TeeCrmDrawbackController/reject.action";
	var param=formToJson("#form1");
	param['sid']=sid;
	param['drawbackStatus']=3;
	param["isPhone"]=1;
	mui.ajax(url,{
	type:"post",
	dataType:"html",
	data:param,
	timeout:10000,
	success:function(json){
		json = eval("("+json+")");
		if(json.rtState){
			window.location.href=contextPath+"/system/mobile/phone/crm/drawback/indexApprove.jsp?drawbackStatus="+drawbackStatus;
		}
			}
		});
}


mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		if(sid>0){
			history.go(-1);
		}else{
			window.location.href=contextPath+"/system/mobile/phone/crm/drawback/indexApprove.jsp?drawbackStatus="+drawbackStatus;
		}
	});
	
	
});


</script>

</body>
</html>