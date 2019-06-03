<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    
    
<%
 
	String attend  = TeeStringUtil.getString(request.getParameter("attend"), ""); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
	.glyphicon{
		line-height: 16px;
	}
</style>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var attend = "<%=attend%>";

function doInit(){
	$("#layout").layout({auto:true});
	$("#group").group();
	$("#group").find("a").removeClass("active");
	if(attend == 'leave'){
		changePage(2);
		$("#attendLeave").parent().parent("a").addClass("active");
	}else if(attend == 'evection'){
		changePage(3);
		$("#attendEvection").parent().parent("a").addClass("active");
	}else if(attend =='overtime'){
		changePage(4);
		$("#attendOvertime").parent().parent("a").addClass("active");
	}
	else{
		changePage(1);
		$("#attendOut").parent().parent("a").addClass("active");
	}

	getLeaderAttendCount();
}

function changePage(sel){
	if(sel==1){
		$("#frame0").attr("src",contextPath+"/system/core/base/attend/manager/out.jsp");//外出
	}else if(sel==2){
		$("#frame0").attr("src",contextPath+"/system/core/base/attend/manager/leave.jsp");//请假
	}else if(sel==3){
		$("#frame0").attr("src",contextPath+"/system/core/base/attend/manager/evection.jsp");//出差
	}else if(sel==4){
		$("#frame0").attr("src",contextPath+"/system/core/base/attend/manager/overTime.jsp");//加班
	}
}


/**
 * 获取考勤审批数量
 */
function getLeaderAttendCount(){
	var url =   "<%=contextPath %>/attendOutManage/getLeaderCount.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		//alert("");
		var prc = jsonObj.rtData;
		$("#attendOut").html(prc.attendOutCount);
		$("#attendLeave").html(prc.attendLeaveCount);
		$("#attendEvection").html(prc.attendEvectionCount);
		$("#attendOvertime").html(prc.attendOvertimeCount);
		
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}	
}
</script>
<style>
.list-group-item{
/* background-color: #f5f5f5; */
font-size:14px;
padding:8px 15px;
}
.base_layout_left{
	background-color: #eaedf1;
}
.list-group{
	margin:6px;
}
.my_nav li{
	line-height: 40px;
	text-align: left;
	text-indent: 25px;
	background-color: #eaedf1;
	cursor:pointer;
	margin-right:2px;
}
hr{
	border: none;
	height: 1px;
	color: #fff;
	background-color: #fff;
}
.badge{
	float: right;
	margin-right: 10px;
	margin-top: 10px;
	width: 30px;
	height: 20px;
	line-height: 20px;
	text-indent: 0!important;
	color: #fff;
	text-align: center;
	background-color:#27a9f3;
	border-radius:10px;
	-webkit-border-radius:10px;
	-moz-border-radius:10px;
	position:absolute;
	left:105px;
}
#xs img{
	vertical-align: middle;
}
.forScoll{
	overflow: auto;
}
li.active{
	background:#fff;
}
li.active .badge{
background:green;
}
.base_layout_left{
	height:100%;
}
.base_layout_right{
	position:absolute;
	top:0;
	left:151px;
	right:10px;
	bottom:0px;
}
</style>
</head>
<!-- <body onload="doInit()" style="overflow:hidden;font-size:12px;margin:5px 0px 0px 5px;">
<div id="layout">
	<div layout="west" width="200">
		<div id="group" class="list-group">
		  <a href="javascript:void(0)" class="list-group-item active" onclick="changePage(1)">
		  	<i class="glyphicon glyphicon-play-circle" style="float:left;"></i>
			<i class="glyphicon glyphicon-chevron-right pull-right"></i>
			<i class="glyphicon " style="margin-right:5px; float:right;"><span class="badge" id="attendOut"  style="background-color:rgb(255,99,67);"></span></i>
			&nbsp;外出管理
			<i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(2)" >
		  	<i class="glyphicon glyphicon-record" style="float:left;"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  	<i class="glyphicon "  style="margin-right:5px;float:right;"><span class="badge"  id="attendLeave"  style="background-color:rgb(255,99,67);"></span></i>
		  	&nbsp;请假管理
		  	<i style="clear:both;"></i>
		  </a>
		  
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(3)">
		  	<i class="glyphicon glyphicon-record" style="float:left"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right">	</i>
		  	<i class="glyphicon "  style="margin-right:5px;float:right;"><span class="badge" id="attendEvection"  style="background-color:rgb(255,99,67);"></span></i>
		  	&nbsp;出差管理
		  	 <i style="clear:both;"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(4)">
		  	<i class="glyphicon glyphicon-record" style="float:left"></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right">	</i>
		  	<i class="glyphicon "  style="margin-right:5px;float:right;"><span class="badge" id="attendOvertime"  style="background-color:rgb(255,99,67);"></span></i>
		  	&nbsp;加班管理
		  	 <i style="clear:both;"></i>
		  </a>
		</div>
	</div>
	<div layout="center" class="base_layout_right" style="padding-left:10px">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</div>
</body> -->


<body onload="doInit()" style="overflow:hidden;">
<div class="base_layout_left" style="text-align:center;width:150px;" >
	<br/>
	<img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/grkq/icon_kaoqinguanli.png">
	<br/><br/>
	<ul class="my_nav" id="group">
	   
		<li class='active' onclick="changePage(1)">&nbsp;&nbsp;外出管理<span class="badge"  id="attendOut"></span></li>
		<li onclick="changePage(2)">&nbsp;&nbsp;请假管理<span class="badge" id="attendLeave"></span></li>
		<li onclick="changePage(3)">&nbsp;&nbsp;出差管理<span class="badge" id="attendEvection" ></span></li>
		<li onclick="changePage(4)">&nbsp;&nbsp;加班管理<span class="badge" id="attendOvertime" ></span></li>
	</ul>
</div>
<div class="base_layout_right" style="overflow:hidden;">
	<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
</div>
</body>
</html>