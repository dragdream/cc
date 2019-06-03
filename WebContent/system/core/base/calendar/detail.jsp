<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<title>日程安排</title>
<script type="text/javascript">

function doInit(){
	var id = "<%=id%>";
	var url =  contextPath +  "/calendarManage/selectById.action";
	var para = {sid:id};
	var jsonObj = tools.requestJsonRs(url,para);
	//alert(jsonObj.rtState);
	if(jsonObj.rtState){
		var prc = jsonObj.rtData;
		 	var seqId = prc.sid;
		    var userId = prc.userId;
		    var calType = prc.calType;
		    var calLevel = prc.calLevel;
		    var content = prc.content;
		    var managerId = prc.managerId;
		    var managerName = prc.managerName;
		    var calLevel = prc.calLevel;
		    var actorNames = prc.actorNames;
	
		    var overStatus = prc.overStatus;  
		    var status = prc.status;
		    if(!calLevel){
		      calLevel = 0;
		    }
		    var statusName = '已完成';
		    if(status=='1'){
		      statusName='未开始';
		    }
		    if(status=='0'){
		      statusName='进行中';
		    }
		    if(status=='2'){
		      statusName='已超时';
		    }
		    if(overStatus=='1'){
		      status = 3;
		      statusName = '已完成';
		    }
		    if(managerName &&  managerName != null  ){
		        managerName = "安排人:("+managerName+")";
		        document.getElementById("managerName").innerHTML = managerName;
		    }
		    
		    if(actorNames &&  actorNames != null  ){
		    	 actorNames = "参与人:("+actorNames+")";
			     document.getElementById("actorNames").innerHTML = actorNames;
			}
		    
		    $("#userName").html("负责人:"+ prc.userName);

		    var colorTypes = ["#0000FF","#0000FF","#FF0000","#00AA00"];
		    var calLevelNames = ['未指定','重要/紧急','重要/不紧急','不重要/紧急','不重要/不紧急'];
		    document.getElementById("time").innerHTML = prc.startTimeStr + " 至 " +prc.endTimeStr;
		    document.getElementById("status").innerHTML=statusName;
/* 		    document.getElementById("calLevel").innerHTML = calLevelNames[calLevel];
		    document.getElementById("calLevel").className = 'CalLevel'+calLevel; */
		    document.getElementById("content").innerHTML = (content || "").replace(/\r\n/g, "<br/>").replace(/\n/g, "<br/>").replace(/\r/g, "<br/>");
		    document.getElementById("fontcolor").style.color = colorTypes[status];
		    
		//callback(json);
	}else{
		alert(jsonObj.rtMsg);
	}
	
}	
</script>
</head>
<body onload="doInit();" style="padding:10px 10px 0px 10px;">

	<div class="">
	<span id="time"></span><br>
	
	<div id="managerName"></div>
	
	<div id="actorNames"></div>
	
	<span id="userName"></span>
	 <!-- <span id="calLevel" >不重要/紧急</span>&nbsp; -->
	<font id="fontcolor" color='' style="margin-left:20px;"><b id="status"></b></font><hr>
	
	<span id="content"></span></div>


	<!-- <div align="center" style="margin-top:20px;">
		<input type="button" value="关闭" class="btn btn-primary" onclick="top.$.jBox.close();">&nbsp;&nbsp;
	</div>				 -->
</body>
</html>
