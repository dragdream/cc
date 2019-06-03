<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    
    
<%
 
	String meetStatus  = TeeStringUtil.getString(request.getParameter("meetStatus"), "0"); 
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
<script type="text/javascript" src="<%=contextPath%>/system/core/base/vehicle/js/vehicle.js"></script>


<script>
var meetStatus = "<%=meetStatus%>";
/**
 * 查询类型  meetingStatus
0、	待批
1、	已批准
2、	进行中
3、	未批准
4、已结束
*/
function doInit(){
	$("#group").group();
	changePage(meetStatus);
	getLeaderCount();
}


function changePage(meetStatus){
	if(meetStatus == 5){
		//vehicleUseDetail();
		$("#frame0").attr("src",contextPath+"/system/core/base/vehicle/leader/vehicleUseManage.jsp");//审批管理
	}else{
		$("#frame0").attr("src",contextPath+"/system/core/base/vehicle/leader/approvalManege.jsp?status=" + meetStatus);//审批管理
	}
}


/**
 * 获取考勤审批数量
 */
function getLeaderCount(){
	var url =   "<%=contextPath %>/vehicleUsageManage/getLeaderApproveCount.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var prc = jsonObj.rtData;
		$("#vehicleCount0").html(prc.vehicleCount0);
		$("#vehicleCount1").html(prc.vehicleCount1);
		$("#vehicleCount2").html(prc.vehicleCount2);
		$("#vehicleCount3").html(prc.vehicleCount3);
		$("#vehicleCount4").html(prc.vehicleCount4);

		
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
	left:135px;
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
	left:181px;
	right:10px;
	bottom:0px;
}
</style>
</head>

<body onload="doInit()" style="overflow:hidden">
<div class="base_layout_left" style="text-align:center;width:180px;" >
     <br/>
	<img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/clgl/clsygl.png">
	<br/><br/>
	<ul class="my_nav" id="group">
		<li class='active' onclick="changePage(0)">待批车辆申请<span class="badge" id="vehicleCount0" ></span></li>
		<li onclick="changePage(1)">已批准车辆申请<span class="badge"  id="vehicleCount1"></span></li>
		<li onclick="changePage(2)">进行中车辆申请<span class="badge" id="vehicleCount2" ></span></li>
		<li onclick="changePage(3)">未批准车辆申请<span class="badge" id="vehicleCount3" ></span></li>
		<li onclick="changePage(4)">已结束车辆申请<span class="badge" id="vehicleCount4" ></span></li>
		<li onclick="changePage(5)">车辆申请使用情况</li>
	</ul>
</div>
<div class="base_layout_right" style="overflow:hidden;">
	<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
</div>
</body>
</html>