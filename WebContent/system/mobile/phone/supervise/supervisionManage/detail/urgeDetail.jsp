<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   //催办记录详情
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	//任务主键
	int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
	
	String typeName=TeeStringUtil.getString(request.getParameter("typeName"));
	int typeSid=TeeStringUtil.getInteger(request.getParameter("typeSid"), 0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>催办详情</title>
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

var supId=<%=supId%>;
var typeName="<%=typeName %>";
var typeSid=<%=typeSid%>;
//初始化方法
function doInit(){
	getInfoBySid();
	
	
	
	//返回
	backBtn.addEventListener("tap",function(){
		window.location = "urgeRecords.jsp?supId="+supId+"&&typeName="+typeName+"&&typeSid="+typeSid;
	}); 
}


//获取详情
function getInfoBySid(){
	var url=contextPath+"/supUrgeController/getInfoBySid.action";
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
				var isIncludeChildren=json.rtData.isIncludeChildren;
				var desc="";
				if(isIncludeChildren==1){
					desc="是";
				}else{
					desc="否";
				}
				$("#isIncludeChildren").html(desc);
			}else{
				alert("数据获取失败！");
			}
		}
	});

}




</script>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" onclick=""></span>
		<h1 class="mui-title">催办详情</h1>
	</header>
	<div id="muiContent" class="mui-content">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>发送人员</label>
		</div>
		<div class="app-row-content" id="createrName" >
		</div>	
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>发送时间</label>
		</div>
		<div class="app-row-content" id="createTimeStr">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>提醒下级任务</label>
		</div>
		<div class="app-row-content" id="isIncludeChildren">
			
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>催办内容</label>
		</div>
		<div class="app-row-content" id="content">
			
		</div>
	</div>
</div>

<br/><br/><br/>	
</body>
</html>