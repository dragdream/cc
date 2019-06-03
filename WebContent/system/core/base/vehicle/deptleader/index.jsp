<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    
    
<%
 
	String meetStatus  = TeeStringUtil.getString(request.getParameter("meetStatus"), "0"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	$("#layout").layout({auto:true});
	$("#group").group();
	changePage(meetStatus);
	getLeaderCount();
}


function changePage(meetStatus1){
	if(meetStatus1 == 5){
		meetingRoomDetail();
	}else{
		$("#frame0").attr("src",contextPath+"/system/core/base/vehicle/deptleader/approvalManege.jsp?status=" + meetStatus1);//审批管理
	}
}


/**
 * 获取待（已/未）审批车辆数量
 */
function getLeaderCount(){
	var url =  "<%=contextPath %>/vehicleUsageManage/getLeaderApproveCount.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		//alert("");
		var prc = jsonObj.rtData;
		$("#vehicleCount0").html(prc.vehicleCount0);
		$("#vehicleCount1").html(prc.vehicleCount1);
		$("#vehicleCount3").html(prc.vehicleCount3);
	}else{
		alert(jsonObj.rtMsg);
	}	
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
<div id="layout">
	<div layout="west" width="200">
		<div id="group" class="list-group">
		  <a href="javascript:void(0)" class="list-group-item active" onclick="changePage(0)">
		  	<i class="glyphicon glyphicon-play-circle"></i>
			&nbsp;待批车辆申请&nbsp;&nbsp;
			
			<i class="glyphicon " style="padding-left:25px;"><span class="badge" id="vehicleCount0"  style="background-color:rgb(255,99,67);"></span></i>
			<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  </a>
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(1)" >
		  	<i class="glyphicon glyphicon-record"></i>
		  	&nbsp;已批准车辆申请
		  	<i class="glyphicon "  style="padding-left:25px;"><span class="badge"  id="vehicleCount1"  style="background-color:rgb(255,99,67);"></span></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right"></i>
		  </a>
		   
		  <a href="javascript:void(0)" class="list-group-item" onclick="changePage(3)">
		  	<i class="glyphicon glyphicon-record"></i>
		  	&nbsp;未批准车辆申请
		  	<i class="glyphicon "  style="padding-left:25px;"><span class="badge" id="vehicleCount3"  style="background-color:rgb(255,99,67);"></span></i>
		  	<i class="glyphicon glyphicon-chevron-right pull-right">
		  	
		  	</i>
		  </a>
		</div>
	</div>
	<div layout="center" style="padding-left:10px">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</div>
</body>
</html>