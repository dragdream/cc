<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //回复主键
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
   
   String typeName=TeeStringUtil.getString(request.getParameter("typeName"));
   int typeSid=TeeStringUtil.getInteger(request.getParameter("typeSid"),0);

 //反馈情况的主键
   int fbId=TeeStringUtil.getInteger(request.getParameter("fbId"), 0);

 //任务主键
   int supId=TeeStringUtil.getInteger(request.getParameter("supId"), 0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>回复详情</title>
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
var typeName="<%=typeName%>";
var typeSid=<%=typeSid%>;
var supId=<%=supId%>;
var fbId=<%=fbId %>;
//初始化方法
function doInit(){
	getInfoBySid();
	
	
	
	//返回
	backBtn.addEventListener("tap",function(){
		window.location = "fbReplyRecords.jsp?typeName="+typeName+"&&typeSid="+typeSid+"&&supId="+supId+"&&fbId="+fbId;
	});
}


//获取详情
function getInfoBySid(){
	var url=contextPath+"/supFeedBackReplyController/getInfoBySid.action";
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
			}else{
				alert("数据获取失败！");
			}
		}
	});

}




</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" "></span>
		<h1 class="mui-title">回复详情</h1>
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>回复人员</label>
		</div>
		<div class="app-row-content" id="createrName" >
		</div>	
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>回复时间</label>
		</div>
		<div class="app-row-content" id="createTimeStr">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>回复内容</label>
		</div>
		<div class="app-row-content" id="content">
			
		</div>
	</div>
	
</div>

<br/><br/><br/>	
</body>
</html>