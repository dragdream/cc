<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //申请主键
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
  
%>

<!DOCTYPE HTML>
<html>
<head>
<title>申请详情</title>
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
//回复主键
var sid=<%=sid %>;

//初始化方法
function doInit(){
	getInfoBySid();
	
	
	
	/* //返回
	backBtn.addEventListener("tap",function(){
		window.location = history.go(-1);
	}); */
}


//获取详情
function getInfoBySid(){
	var url=contextPath+"/supervisionApplyController/getInfoBySid.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{sid:sid},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){	
				bindJsonObj2Cntrl(json.rtData);
				var type=json.rtData.type;
				var typeDesc="";
				if(type==1){
					typeDesc="暂停申请";
				}else if(type==2){
				    typeDesc="恢复申请";
				}else{
					typeDesc="办结申请";
				}
				
				
				 var status=json.rtData.status;
				 var statusDesc="";
				 if(status==0){
					 statusDesc="待审批";
				 }else if(status==1){
					 statusDesc="已同意";
				 }else if(status==2){
					 statusDesc="已拒绝";
				 }
				$("#type").html(typeDesc);
				$("#status").html(statusDesc);
			}else{
				alert("数据获取失败！");
			}
		}
	});

}




</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" onclick="history.go(-1);"></span>
		<h1 class="mui-title">申请详情</h1>
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>申请人</label>
		</div>
		<div class="app-row-content" id="createrName" >
		</div>	
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>申请类型</label>
		</div>
		<div class="app-row-content" id="type">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>申请状态</label>
		</div>
		<div class="app-row-content" id="status">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>申请时间</label>
		</div>
		<div class="app-row-content" id="createTimeStr">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>申请内容</label>
		</div>
		<div class="app-row-content" id="content">
			
		</div>
	</div>
</div>

<br/><br/><br/>	
</body>
</html>